package server.model.persistence.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.model.persistence.entities.AnimalPart;
import server.model.persistence.entities.Product;
import server.model.persistence.entities.Tray;
import server.model.persistence.entities.TrayToProductTransfer;
import server.model.validation.ProductValidation;
import server.model.persistence.repository.AnimalPartRepository;
import server.model.persistence.repository.ProductRepository;
import server.model.persistence.repository.TrayRepository;
import server.model.persistence.repository.TrayToProductTransferRepository;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.*;

@Service
public class ProductService implements ProductRegistryInterface
{
  private final ProductRepository productRepository;
  private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
  private final TrayToProductTransferRepository trayToProductTransferRepository;
  private final TrayRepository trayRepository;
  private final AnimalPartRepository animalPartRepository;

  @Autowired
  public ProductService(ProductRepository productRepository, TrayToProductTransferRepository trayToProductTransferRepository, TrayRepository trayRepository, AnimalPartRepository animalPartRepository) {
    this.productRepository = productRepository;
    this.trayToProductTransferRepository = trayToProductTransferRepository;
    this.trayRepository = trayRepository;
    this.animalPartRepository = animalPartRepository;
  }


  @Transactional
  @Override public Product registerProduct(Product data) {

    // Validate received data, before passing to repository/database:
    ProductValidation.validateProductRegistration(data);

    // Reset productId, since the database handles this assignment:
    data.setProductId(0);

    // Attempt to add Product to DB:
    try {
      // Read associated AnimalPart and Tray Data:
      List<AnimalPart> associatedAnimalParts = new ArrayList<>();
      List<Tray> associatedTrays = new ArrayList<>();

      for (Long animalPartId : data.getAnimalPartIdList()) {
        AnimalPart animalPart = animalPartRepository.findById(animalPartId).orElseThrow(() -> new NotFoundException("Invalid AnimalPart provided to Product. Must provide an AnimalPart that already exists in DB."));
        Tray tray = trayRepository.findById(animalPart.getTray().getTrayId()).orElseThrow(() -> new NotFoundException("Invalid Tray provided to Product. Must provide an Tray that already exists in DB."));
        associatedAnimalParts.add(animalPart);

        // Check for duplicate AnimalPart entries and reassign, so that only 1 representation of each database entity persists:
        for (int i = 0; i < tray.getAnimalPartList().size(); i++) {
          if(tray.getAnimalPartList().get(i).getPartId() == animalPart.getPartId()) {
            tray.removeAnimalPart(tray.getAnimalPartList().get(i));
            tray.addAnimalPart(animalPart);
          }
        }
        associatedTrays.add(tray);
      }
      data.clearAnimalPartList();
      data.clearTraySuppliersList();
      data.addAllAnimalParts(associatedAnimalParts);
      data.addAllTraysToTraySuppliersList(associatedTrays);

      // Save the Product to DB, without the transfers:
      Product newProduct = productRepository.save(data);

      // Register a new transfer for each Tray providing AnimalParts to this Product:
      for (Tray tray : data.getTraySuppliersList()) {
        TrayToProductTransfer newTransfer = new TrayToProductTransfer(0, tray, newProduct);
        newProduct.addTransfer(newTransfer);
      }

      // Save the Product to DB again, now with the proper Transfers assigned:
      newProduct = productRepository.save(newProduct);

      logger.info("Product added to DB with ID: {}", newProduct.getProductId());

      return newProduct;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register Product in DB, Reason: {}", e.getMessage());
      throw new DataIntegrityViolationException("Invalid Product provided. Incompatible with database! Cause (" + e.getMessage() + ")");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Product: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override public Product readProduct(long productId) {
    // Validate received id, before passing to repository/database:
    ProductValidation.validateId(productId);

    // Attempt to read from DB:
    try {
      logger.info("Looking up Product with ID: '{}' in database...", productId);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("No Product found in database with matching id=" + productId));

      logger.info("Product read from database with ID: {}", productId);

      // Fill in transient data:
      for (TrayToProductTransfer transfer : product.getTraySupplyJoinList())
        product.addTrayToTraySuppliersList(transfer.getTray());

      return product;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Product with ID {}: {}", productId, e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional
  @Override public boolean updateProduct(Product data) {
    // Validate received data, before passing to repository/database:
    ProductValidation.validateProduct(data);

    // Attempt to update Product in database:
    try {
      // Fetch the existing Entity from DB:
      Product product = productRepository.findById(data.getProductId()).orElseThrow(() -> new NotFoundException("No Product found in database with matching id=" + data.getProductId()));

      // Modify the database Entity locally:
      // AnimalPart List
      product.clearAnimalPartList();
      for (AnimalPart animalPart : data.getAnimalPartList()) {
        try {
          AnimalPart animalPartToAdd = animalPartRepository.findById(animalPart.getPartId()).orElseThrow(() -> new NotFoundException(""));
          product.addAnimalPart(animalPartToAdd);
        } catch (NotFoundException ignored) {}
      }

      // TrayToProductTransfer List
      product.clearTraySupplyJoinList();
      for (TrayToProductTransfer transfer : data.getTraySupplyJoinList()) {
        try {
          TrayToProductTransfer transferToAdd = trayToProductTransferRepository.findById(transfer.getTransferId()).orElseThrow(() -> new NotFoundException(""));
          product.addTransfer(transferToAdd);
        } catch (NotFoundException ignored) {}
      }

      // Check if there are any 'Hanging' AnimalParts, that have not been documented in a transfer.
      // Any such must be new transfers, and we should ensure that the needed transfers are created:
      List<AnimalPart> newAnimalParts = new ArrayList<>();
      for (AnimalPart animalPart : data.getAnimalPartList()) {
        for (TrayToProductTransfer transfer : product.getTraySupplyJoinList()){
          if(!transfer.getTray().getAnimalPartIdList().contains(animalPart.getPartId())){
            // This AnimalPart has not been registered in a transfer:
            newAnimalParts.add(animalPart);
          }
        }
      }

      // For all AnimalParts without registered transfers, create and register these:
      for (AnimalPart animalPart : newAnimalParts) {
        AnimalPart animalPartToRegister = animalPartRepository.findById(animalPart.getPartId()).orElseThrow(() -> new NotFoundException(""));
        TrayToProductTransfer newTransfer = new TrayToProductTransfer(0, animalPartToRegister.getTray(), product);
        newTransfer = trayToProductTransferRepository.save(newTransfer);
        product.addTransfer(newTransfer);
      }

      // Save the modified entity back to database:
      product = productRepository.save(product);
      logger.info("Product updated in database with ID: {}", product.getProductId());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update Product in DB with id: {}, Reason: {}", data.getProductId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating Product with ID {}: {}", data.getProductId(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional
  @Override public boolean removeProduct(Product data) {
    // Validate received data, before passing to repository/database:
    ProductValidation.validateProduct(data);

    try {
      // Check if Product exists:
      Product product = productRepository.findById(data.getProductId()).orElseThrow(() -> new NotFoundException(""));

      // Attempt to delete the given Product:
      productRepository.delete(product);

      // Confirm that the entity has been removed:
      Optional<Product> deletedProduct = productRepository.findById(data.getProductId());

      if(deletedProduct.isPresent()) {
        // Product was not removed from database.
        logger.info("Product was NOT deleted from database with ID: {}", data.getProductId());
        throw new PersistenceException("Product with ID '" + data.getProductId() + "' was not deleted!");
      }

      // Ensure that all TrayToProductTransfer transfers are deleted:
      trayToProductTransferRepository.deleteAll(data.getTraySupplyJoinList());

      logger.info("Product deleted from database with ID: {}", data.getProductId());
      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete Product in DB with id: {}, Reason: {}", data.getProductId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override public List<Product> getAllProducts() {
    try {
      List<Product> products = productRepository.findAll();

      // Populate transient data:
      // Populate transient values for each Product:
      for (Product product : products) {
        this.populateTransientTrayList(product);
      }

      return products;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }

  private void populateTransientTrayList(Product product) {
    // Populate the transient traySuppliers association list:
    List<Tray> traySupplierList = new ArrayList<>();
    for (Long transferId : product.getTransferIdList()){
      List<Tray> supplierList = new ArrayList<>();

      try {
        supplierList = trayRepository.findByTransferList_TransferId(transferId).orElseThrow(() -> new NotFoundException("No Trays found in database associated with transferId=" + transferId));
      } catch (NotFoundException ignored) {}

      traySupplierList.addAll(supplierList);
    }
    product.clearTraySuppliersList();
    product.addAllTraysToTraySuppliersList(traySupplierList);
  }
}
