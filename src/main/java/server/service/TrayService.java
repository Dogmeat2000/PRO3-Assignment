package server.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.repository.TrayRepository;
import shared.model.entities.Tray;
import shared.model.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TrayService implements TrayRegistryInterface
{
  private final TrayRepository trayRepository;
  private final Map<Long, Tray> trayCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(TrayService.class);

  @Autowired
  public TrayService(TrayRepository trayRepository) {
    this.trayRepository = trayRepository;
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public Tray registerTray(Tray data) throws PersistenceException, DataIntegrityViolationException {
    //Overwrite any set id for the Tray, since the database handles this automatically:
    if(data != null)
      data.setTray_id(1);

    // Validate received data, before passing to repository/database:
    validateTray(data);

    // Attempt to add Tray to DB:
    try {
      Tray newTray = trayRepository.save(data);
      logger.info("Tray added to DB with ID: {}", newTray.getTray_id());

      // Attempt to add Tray to local cache:
      trayCache.put(newTray.getTray_id(), newTray);
      logger.info("Tray saved to local cache with ID: {}", newTray.getTray_id());

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
    validateId(trayId);

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
    validateTray(data);

    // Attempt to update Tray in database:
    try {
      // Fetch the existing Entity from DB:
      Tray tray = trayRepository.findById(data.getTray_id()).orElseThrow(() -> new NotFoundException("No Tray found in database with matching id=" + data.getTray_id()));

      // Modify the database Entity locally:
      tray.setMaxWeight_kilogram(data.getMaxWeight_kilogram());
      tray.setWeight_kilogram(data.getWeight_kilogram());
      tray.getContents().clear();
      tray.getContents().addAll(data.getContents());
      tray.getDeliveredToProducts().clear();
      tray.getDeliveredToProducts().addAll(data.getDeliveredToProducts());

      // Save the modified entity back to database:
      trayRepository.save(tray);
      logger.info("Tray updated in database with ID: {}", tray.getTray_id());

      // Attempt to add Animal to local cache:
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
    validateTray(data);

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


  private void validateTray(Tray tray) throws DataIntegrityViolationException {
    // Tray cannot be null:
    if(tray == null)
      throw new DataIntegrityViolationException("Tray is null");

    // Validate trayId:
    validateId(tray.getTray_id());

    // Tray maxWeight must be 0 or larger:
    validateWeight(tray.getMaxWeight_kilogram());

    // Tray weight must be 0 or larger:
    validateWeight(tray.getWeight_kilogram());

    // Validation passed:
  }


  private void validateId(long trayId) throws DataIntegrityViolationException {
    // trayId must be larger than 0:
    if(trayId <= 0)
      throw new DataIntegrityViolationException("trayId is invalid (0 or less)");

    // Validation passed:
  }


  private void validateWeight(BigDecimal weight) throws DataIntegrityViolationException {
    // Tray weight must be larger than, or equal to 0:
    if(weight.compareTo(BigDecimal.valueOf(0)) < 0)
      throw new DataIntegrityViolationException("weight_kilogram is invalid (less than zero)");

    // Validation passed:
  }
}
