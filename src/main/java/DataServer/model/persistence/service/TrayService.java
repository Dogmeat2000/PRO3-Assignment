package DataServer.model.persistence.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import DataServer.model.persistence.entities.Product;
import DataServer.model.persistence.repository.ProductRepository;
import DataServer.model.validation.TrayValidation;
import DataServer.model.persistence.repository.TrayRepository;
import DataServer.model.persistence.repository.TrayToProductTransferRepository;
import DataServer.model.persistence.entities.Tray;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.*;

@Service
public class TrayService implements TrayRegistryInterface
{
  private final TrayRepository trayRepository;
  private final ProductRepository productRepository;
  private static final Logger logger = LoggerFactory.getLogger(TrayService.class);
  private final TrayToProductTransferRepository trayToProductTransferRepository;

  @Autowired
  public TrayService(TrayRepository trayRepository, TrayToProductTransferRepository trayToProductTransferRepository, ProductRepository productRepository) {
    this.trayRepository = trayRepository;
    this.trayToProductTransferRepository = trayToProductTransferRepository;
    this.productRepository = productRepository;
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

    // Attempt to read from DB:
    try {
      logger.info("Looking up Tray with ID: {} in database...", trayId);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      Tray tray = trayRepository.findById(trayId).orElseThrow(() -> new NotFoundException("No Tray found in database with matching id=" + trayId));
      logger.info("Tray read from database with ID: {}", trayId);

      // Ensure the transient Products are read into the loaded Tray, for proper ORM behavior:
      this.populateTransientProductList(tray);

      return tray;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Tray with ID {}: {}", trayId, e.getMessage());
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


  @Transactional
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
      List<Tray> trays = trayRepository.findAll();

      // Ensure the transient Products are read into the loaded Tray, for proper ORM behavior:
      for(Tray tray : trays)
        this.populateTransientProductList(tray);

      return trays;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }

  private void populateTransientProductList(Tray tray) {
    // Populate the transient productlist:
    List<Product> productsList = new ArrayList<>();
    for (Long transferId : tray.getTransferIdList()){
      List<Product> localProductList = new ArrayList<>();

      try {
        localProductList = productRepository.findByTraySupplyJoinList_TransferId(transferId).orElseThrow(() -> new NotFoundException("No Products found in database associated with transferId=" + transferId));
      } catch (NotFoundException ignored) {}

      productsList.addAll(localProductList);
    }
    tray.clearProductList();
    tray.addAllProducts(productsList);
  }
}
