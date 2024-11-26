package server.model.persistence.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.model.validation.TrayValidation;
import server.model.persistence.repository.TrayRepository;
import server.model.persistence.repository.TrayToProductTransferRepository;
import server.model.persistence.entities.Tray;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.*;

@Service
public class TrayService implements TrayRegistryInterface
{
  private final TrayRepository trayRepository;
  //private final Map<Long, Tray> trayCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(TrayService.class);
  private final TrayToProductTransferRepository trayToProductTransferRepository;
  //private final AnimalPartRepository animalPartRepository;

  @Autowired
  public TrayService(TrayRepository trayRepository, TrayToProductTransferRepository trayToProductTransferRepository/*, AnimalPartRepository animalPartRepository*/) {
    this.trayRepository = trayRepository;
    this.trayToProductTransferRepository = trayToProductTransferRepository;
    //this.animalPartRepository = animalPartRepository;
  }


  @Transactional
  @Override public Tray registerTray(Tray data) throws PersistenceException, DataIntegrityViolationException {

    // Validate received data, before passing to repository/database:
    TrayValidation.validateTray(data);

    //Overwrite any set id for the Tray, since the database handles this automatically:
    data.setTrayId(0);

    // Attempt to add Tray to DB:
    try {
      Tray newTray = trayRepository.save(data);
      logger.info("Tray added to DB with ID: {}", newTray.getTrayId());

      // Ensure that all TrayToProductTransfer transfers are registered and/or updated:
      trayToProductTransferRepository.saveAll(data.getTransferList());
      /*System.out.println("\n\n\n: Tray registered with maxSize: " + newTray.getMaxWeight_kilogram());
      System.out.println("\n\n\n: Tray registered with curSize: " + newTray.getWeight_kilogram());*/

      // Attempt to add Tray to local cache:
      /*trayCache.put(newTray.getTrayId(), newTray);
      logger.info("Tray saved to local cache with ID: {}", newTray.getTrayId());*/

      return readTray(newTray.getTrayId());

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register Tray in DB with maxWeight: {}, Reason: {}", data.getMaxWeight_kilogram(), e.getMessage());
      throw new DataIntegrityViolationException("Invalid Tray provided. Incompatible with database!");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Tray with ID {}: {}", data.getTrayId(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override public Tray readTray(long trayId) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received id, before passing to repository/database:
    TrayValidation.validateId(trayId);

    // Attempt to read Tray from local cache first:
    /*if(trayCache.containsKey(trayId)) {
      logger.info("Tray read from local cache with ID: {}", trayId);
      return trayCache.get(trayId);
    }*/

    // Tray not found in local cache. Attempt to read from DB:
    try {
      logger.info("Looking up Tray with ID: {} in database...", trayId);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      Tray tray = trayRepository.findById(trayId).orElseThrow(() -> new NotFoundException("No Tray found in database with matching id=" + trayId));

      //System.out.println("\n\nRead this Tray from DB: " + tray + "\n\n");

      logger.info("Tray read from database with ID: {}", trayId);

      /*System.out.println("\n\n\n: Tray loaded with maxSize: " + tray.getMaxWeight_kilogram());
      System.out.println("\n\n\n: Tray loaded with curSize: " + tray.getWeight_kilogram());
      System.out.println("\n\n\n: Tray loaded with contents: " + tray.getAnimalPartList());*/

      // Load all associated AnimalParts: //TODO: Shouldn't be needed? JPA should be doing this already!
      /*List<AnimalPart> animalParts = new ArrayList<>();
      try {
        animalParts = animalPartRepository.findAnimalPartsByType_typeId(tray.getTrayId()).orElseThrow(() -> new NotFoundException("No associated AnimalParts found in database with matching tray_id=" + tray.getTrayId()));
      } catch (NotFoundException ignored) {}

      if(!animalParts.isEmpty())
        tray.addAllAnimalParts(animalParts);*/

      // Populate the id association list:
      /*List<Long> animalPartIds = new ArrayList<>();
      for (AnimalPart animalPart : tray.getAnimalPartList())
        animalPartIds.add(animalPart.getPart_id());
      tray.setAnimalPartIdList(animalPartIds);*/


      // Load all associated Transfers: //TODO: Shouldn't be needed? JPA should be doing this already!
      /*List<TrayToProductTransfer> transfers = new ArrayList<>();
      try {
        transfers = trayToProductTransferRepository.findTrayToProductTransferByTray_TrayId(tray.getTrayId()).orElseThrow(() -> new NotFoundException("No associated Transfer found in database matching tray_id=" + tray.getTrayId()));
      } catch (NotFoundException ignored) {}

      if(!transfers.isEmpty())
        tray.setTransferList(transfers);*/

      // Populate the id association list:
      /*List<Long> transferIds = new ArrayList<>();
      for (TrayToProductTransfer transfer : tray.getTransferList())
        transferIds.add(transfer.getTransferId());
      tray.setTransferIdList(transferIds);*/

      // Populate the transient TrayType:
      /*if(!tray.getAnimalPartList().isEmpty())
        tray.setTrayType(tray.getAnimalPartList().get(0).getType());*/


      // Add found Tray to local cache, to improve performance next time Tray is requested.
      /*trayCache.put(tray.getTrayId(), tray);
      logger.info("Tray added to local cache with ID: {}", tray.getTrayId());*/
      return tray;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Tray with ID {}: {}", trayId, e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional
  @Override public List<Tray> readTraysByTransferId(long transferId) throws PersistenceException, NotFoundException, DataIntegrityViolationException {
    try {
      List<Tray> trays = trayRepository.findByTransferList_TransferId(transferId).orElseThrow(() -> new NotFoundException("No Trays found in database associated with transferId=" + transferId));

      // TODO: Add methods to get lists of Products and Transfers (Transient data)

      // Populate the transient id association list:
      /*for (Tray tray : trays) {
        List<Long> animalPartIds = new ArrayList<>();
        for (AnimalPart animalPart : tray.getAnimalPartList())
          animalPartIds.add(animalPart.getPart_id());
        tray.setAnimalPartIdList(animalPartIds);
      }*/

      // Populate the transient id association list:
      /*for (Tray tray : trays) {
        List<Long> transferIds = new ArrayList<>();
        for (TrayToProductTransfer transfer : tray.getTransferList())
          transferIds.add(transfer.getTransferId());
        tray.setTransferIdList(transferIds);
      }*/

      // Populate the transient TrayType:
      /*for (Tray tray : trays) {
        if(!tray.getAnimalPartList().isEmpty())
          tray.setTrayType(tray.getAnimalPartList().get(0).getType());
      }*/

      // Add all the found Trays to local cache, to improve performance next time a Tray is requested.
      /*for (Tray tray : trays) {
        if(tray != null)
          trayCache.put(tray.getTrayId(), tray);
      }
      logger.info("Added all Trays associated with transferId '{}' from Database to Local Cache", transferId);*/
      return trays;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional
  @Override public boolean updateTray(Tray data) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received data, before passing to repository/database:
    TrayValidation.validateTray(data);

    // Attempt to update Tray in database:
    try {
      // Verify that this Tray already exists in DB:
      trayRepository.findById(data.getTrayId()).orElseThrow(() -> new NotFoundException("No Tray found in database with matching id=" + data.getTrayId()));

      // Save the modified entity back to database:
      Tray tray = trayRepository.save(data);
      logger.info("Tray updated in database with ID: {}", tray.getTrayId());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update Tray in DB with maxWeight: {}, Reason: {}", data.getMaxWeight_kilogram(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating Tray with ID {}: {}", data.getTrayId(), e.getMessage());
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
      Optional<Tray> deletedTray = trayRepository.findById(data.getTrayId());

      if(deletedTray.isPresent()) {
        // Tray was not removed from database.
        logger.info("Tray was NOT deleted from database with ID: {}", data.getTrayId());
        throw new PersistenceException("Tray with ID " + data.getTrayId() + " was not deleted!");
      }

      logger.info("Tray deleted from database with ID: {}", data.getTrayId());
      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete Tray in DB with id: {}, Reason: {}", data.getTrayId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override public List<Tray> getAllTrays() throws PersistenceException {
    try {
      return trayRepository.findAll();

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }
}
