package server.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.model.validation.TrayValidation;
import server.repository.AnimalPartRepository;
import server.repository.JPA_CompositeKeys.TrayToProductTransferId;
import server.repository.TrayRepository;
import server.repository.TrayToProductTransferRepository;
import shared.model.entities.AnimalPart;
import shared.model.entities.Tray;
import shared.model.entities.TrayToProductTransfer;
import shared.model.exceptions.NotFoundException;

import java.util.*;

@Service
public class TrayService implements TrayRegistryInterface
{
  private final TrayRepository trayRepository;
  private final Map<Long, Tray> trayCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(TrayService.class);
  private final TrayToProductTransferRepository trayToProductTransferRepository;
  private final AnimalPartRepository animalPartRepository;

  @Autowired
  public TrayService(TrayRepository trayRepository, TrayToProductTransferRepository trayToProductTransferRepository, AnimalPartRepository animalPartRepository) {
    this.trayRepository = trayRepository;
    this.trayToProductTransferRepository = trayToProductTransferRepository;
    this.animalPartRepository = animalPartRepository;
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public Tray registerTray(Tray data) throws PersistenceException, DataIntegrityViolationException {
    //Overwrite any set id for the Tray, since the database handles this automatically:
    if(data != null)
      data.setTray_id(1);

    // Validate received data, before passing to repository/database:
    TrayValidation.validateTray(data);

    // Attempt to add Tray to DB:
    try {
      Tray newTray = trayRepository.save(data);
      logger.info("Tray added to DB with ID: {}", newTray.getTray_id());

      // Ensure that all TrayToProductTransfer transfers are registered and/or updated:
      //TrayToProductTransferId transferId = new TrayToProductTransferId(transfer.getProduct_id(), transfer.getTray_id());
      //trayToProductTransferRepository.save(transferId);
      trayToProductTransferRepository.saveAll(data.getDeliveredToProducts());

      // Attempt to add Tray to local cache:
      trayCache.put(newTray.getTray_id(), newTray);
      logger.info("Tray saved to local cache with ID: {}", newTray.getTray_id());

      // Update any associated AnimalPart with this new Tray info:
      for (AnimalPart animalPart : data.getContents()) {
        AnimalPart oldAnimalPart = animalPart.copy();
        animalPart.setTray(newTray);
        //animalPartRepository.updateAnimalPart(oldAnimalPart, animalPart);
        animalPartRepository.save(animalPart);
      }

      return newTray;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register Tray in DB with maxWeight: {}, Reason: {}", data.getMaxWeight_kilogram(), e.getMessage());
      throw new DataIntegrityViolationException("Invalid Tray provided. Incompatible with database!");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Tray with ID {}: {}", data.getTray_id(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Override public Tray readTray(long trayId) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received id, before passing to repository/database:
    TrayValidation.validateId(trayId);

    // Attempt to read Tray from local cache first:
    if(trayCache.containsKey(trayId)) {
      logger.info("Tray read from local cache with ID: {}", trayId);
      return trayCache.get(trayId);
    }

    // Tray not found in local cache. Attempt to read from DB:
    try {
      logger.info("Tray not found in local cache with ID: {}. Looking up in database...", trayId);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      Tray tray = trayRepository.findById(trayId).orElseThrow(() -> new NotFoundException("No Tray found in database with matching id=" + trayId));

      logger.info("Tray read from database with ID: {}", trayId);

      // Add found Tray to local cache, to improve performance next time Tray is requested.
      trayCache.put(tray.getTray_id(), tray);
      logger.info("Tray added to local cache with ID: {}", tray.getTray_id());
      return tray;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Tray with ID {}: {}", trayId, e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean updateTray(Tray data) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received data, before passing to repository/database:
    TrayValidation.validateTray(data);

    // Attempt to update Tray in database:
    try {
      // Fetch the existing Entity from DB:
      Tray tray = trayRepository.findById(data.getTray_id()).orElseThrow(() -> new NotFoundException("No Tray found in database with matching id=" + data.getTray_id()));

      // Modify the database Entity locally:
      tray.setMaxWeight_kilogram(data.getMaxWeight_kilogram());
      tray.setWeight_kilogram(data.getWeight_kilogram());
      tray.clearAnimalPartContents();
      tray.addAllAnimalParts(data.getContents());
      tray.getDeliveredToProducts().clear();
      tray.getDeliveredToProducts().addAll(data.getDeliveredToProducts());

      // Save the modified entity back to database:
      trayRepository.save(tray);
      logger.info("Tray updated in database with ID: {}", tray.getTray_id());

      // Ensure that all TrayToProductTransfer transfers are registered and/or updated:
      //TrayToProductTransferId transferId = new TrayToProductTransferId(transfer.getProduct_id(), transfer.getTray_id());
      //trayToProductTransferRepository.save(transferId);
      trayToProductTransferRepository.saveAll(data.getDeliveredToProducts());

      // Identify any AnimalParts that were removed from the Tray:
      List<AnimalPart> listOfRemovedAnimalParts = new ArrayList<>(data.getContents());
      listOfRemovedAnimalParts.removeAll(tray.getContents());

      // Update all still-existing AnimalParts in this Tray:
      for (AnimalPart animalPart : listOfRemovedAnimalParts) {
        AnimalPart oldAnimalPart = animalPart.copy();
        animalPart.setTray(tray);
        //animalPartRepository.updateAnimalPart(oldAnimalPart, animalPart);
        animalPartRepository.save(animalPart);
      }

      // Remove all AnimalParts that no longer have any valid associations to a Tray:
      for (AnimalPart animalPart : listOfRemovedAnimalParts) {
        //animalPartRepository.removeAnimalPart(animalPart);
        animalPartRepository.delete(animalPart);
      }


      // Attempt to add Tray to local cache:
      trayCache.put(tray.getTray_id(), tray);
      logger.info("Tray saved to local cache with ID: {}", tray.getTray_id());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update Tray in DB with maxWeight: {}, Reason: {}", data.getMaxWeight_kilogram(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating Tray with ID {}: {}", data.getTray_id(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean removeTray(Tray data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    TrayValidation.validateTray(data);

    try {
      // Attempt to delete the given Tray:
      trayRepository.delete(data);

      // Confirm that the entity has been removed:
      Optional<Tray> deletedTray = trayRepository.findById(data.getTray_id());

      if(deletedTray.isPresent()) {
        // Tray was not removed from database.
        logger.info("Tray was NOT deleted from database with ID: {}", data.getTray_id());
        throw new PersistenceException("Tray with ID " + data.getTray_id() + " was not deleted!");
      }

      logger.info("Tray deleted from database with ID: {}", data.getTray_id());
      // Tray was removed from database. Now ensure that is it also removed from the local cache:
      trayCache.remove(data.getTray_id());
      logger.info("Tray deleted from local cache with ID: {}", data.getTray_id());

      // Ensure that all associated TrayToProductTransfer transfers are removed:
      //TrayToProductTransferId id = new TrayToProductTransferId(transfer.getProduct_id(), transfer.getTray_id());
      //trayToProductTransferRepository.delete(id);
      trayToProductTransferRepository.deleteAll(data.getDeliveredToProducts());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete Tray in DB with id: {}, Reason: {}", data.getTray_id(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Override public List<Tray> getAllTrays() throws PersistenceException {
    try {
      List<Tray> trays = trayRepository.findAll();

      // Add all the found Trays to local cache, to improve performance next time a Tray is requested.
      trayCache.clear();
      for (Tray tray : trays) {
        if(tray != null)
          trayCache.put(tray.getTray_id(), tray);
      }
      logger.info("Added all Trays from Database to Local Cache");
      return trays;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }
}
