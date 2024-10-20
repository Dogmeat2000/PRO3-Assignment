package server.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Transient;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.model.validation.ProductValidation;
import server.repository.AnimalPartRepository;
import server.repository.ProductRepository;
import server.repository.TrayRepository;
import server.repository.TrayToProductTransferRepository;
import shared.model.entities.*;
import shared.model.exceptions.NotFoundException;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ProductRegistryService implements ProductRegistryInterface
{
  private final ProductRepository productRepository;
  private final Map<Long, Product> productCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(ProductRegistryService.class);
  private final TrayToProductTransferRepository trayToProductTransferRepository;
  private final TrayRepository trayRepository;
  private final AnimalPartRepository animalPartRepository;
  private final EntityManager entityManager;

  @Autowired
  public ProductRegistryService(ProductRepository productRepository, TrayToProductTransferRepository trayToProductTransferRepository, TrayRepository trayRepository, AnimalPartRepository animalPartRepository, EntityManager entityManager) {
    this.productRepository = productRepository;
    this.trayToProductTransferRepository = trayToProductTransferRepository;
    this.trayRepository = trayRepository;
    this.animalPartRepository = animalPartRepository;
    this.entityManager = entityManager;
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public Product registerProduct(Product data) {

    // Validate received data, before passing to repository/database:
    ProductValidation.validateProductRegistration(data);

    // Reset productId, since the database handles this assignment:
    data.setProductId(0);

    // Attempt to add Product to DB:
    try {
      // First retrieve most recent versions of associated entities:
      /*List<AnimalPart> managedAnimalParts = new ArrayList<>();
      for (Long animalPartId : data.getAnimalPartIdList())
        managedAnimalParts.add(entityManager.merge(animalPartRepository.findById(animalPartId).orElseThrow(() -> new NotFoundException("Animal part " + animalPartId + " not found"))));

      data.setAnimalParts(managedAnimalParts);

      List<Tray> managedTrays = new ArrayList<>();
      for (Tray tray : data.getTraySuppliersList())
        managedTrays.add(entityManager.merge(trayRepository.findById(tray.getTrayId()).orElseThrow(() -> new NotFoundException("Animal part " + tray.getTrayId() + " not found"))));

      data.setTraySuppliersList(managedTrays);*/

      // Read associated AnimalPart and Tray Data:
      List<AnimalPart> associatedAnimalParts = new ArrayList<>();
      List<Tray> associatedTrays = new ArrayList<>();

      for (Long animalPartId : data.getAnimalPartIdList()) {
        AnimalPart animalPart = animalPartRepository.findById(animalPartId).get();
        Tray tray = trayRepository.findById(animalPart.getTray().getTrayId()).get();
        associatedAnimalParts.add(animalPart);

        // Check for duplicate AnimalPart entries and reassign, so that only 1 representation of each database entity persists:
        for (int i = 0; i < tray.getContents().size(); i++) {
          if(tray.getContents().get(i).getPart_id() == animalPart.getPart_id()) {
            tray.removeAnimalPart(tray.getContents().get(i));
            tray.addAnimalPart(animalPart);
          }
        }
        associatedTrays.add(tray);
      }
      data.getContentList().clear();
      data.getTraySuppliersList().clear();
      data.getContentList().addAll(associatedAnimalParts);
      data.getTraySuppliersList().addAll(associatedTrays);

      Product newProduct = productRepository.save(data);

      logger.info("Product added to DB with ID: {}", newProduct.getProductId());

      // Update all the associated AnimalParts:
      for (AnimalPart animalPart : newProduct.getContentList()){
        animalPart.setProduct(newProduct);
        animalPartRepository.save(animalPart);
      }

      // Register the transfers, before registering the Product:
      List<TrayToProductTransfer> transfers = new ArrayList<>();
      for (Tray tray : data.getTraySuppliersList()) {
        transfers.add(trayToProductTransferRepository.save(new TrayToProductTransfer(0, tray, newProduct)));
      }
      data.getTraySupplyJoinList().clear();
      data.getTraySupplyJoinList().addAll(transfers);


      // Resave the Product, with the added TrayToProductTransfer associations:
      data.setProductId(newProduct.getProductId());
      newProduct = productRepository.save(data);
      logger.info("Product added to DB with ID: {}", newProduct.getProductId());

      // Initialize transient data associated with the created Product:
      List<Long> animalPartIdList = new ArrayList<>();
      List<Long> transferIdList = new ArrayList<>();
      List<Tray> traySuppliersList = new ArrayList<>();
      for (AnimalPart animalPart : newProduct.getContentList())
        animalPartIdList.add(animalPart.getPart_id());
      newProduct.setAnimalPartIdList(animalPartIdList);

      for (TrayToProductTransfer trayToProductTransfer : transfers)
        transferIdList.add(trayToProductTransfer.getTransferId());
      newProduct.setTransferIdList(transferIdList);

      for (Long transferId : newProduct.getTransferIdList())
        traySuppliersList.addAll(trayRepository.findByTransferList_TransferId(transferId).get());

      // Attempt to add Product to local cache:
      productCache.put(newProduct.getProductId(), newProduct);
      logger.info("Product saved to local cache with ID: {}", newProduct.getProductId());

      // Ensure that all TrayToProductTransfer transfers are registered and/or updated:
      //trayToProductTransferRepository.saveAll(data.getTraySupplyJoinList());

      // Ensure that Tray also get an updated transferId:
      for (Tray tray : data.getTraySuppliersList()) {
        Tray loadedTray = trayRepository.findById(tray.getTrayId()).orElse(null);
        if(loadedTray != null) {
          loadedTray.getTransferList().clear();
          loadedTray.getTransferList().addAll(transfers);
          trayRepository.save(loadedTray);
        }
      }

      return newProduct;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register Product in DB, Reason: {}", e.getMessage());
      throw new DataIntegrityViolationException("Invalid Product provided. Incompatible with database!");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Product: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override public Product readProduct(long productId) {
    // Validate received id, before passing to repository/database:
    ProductValidation.validateId(productId);

    // Attempt to read Product from local cache first:
    /*if(productCache.containsKey(productId)) {
      logger.info("Product read from local cache with ID: {}", productId);
      return productCache.get(productId);
    }*/

    // Product not found in local cache. Attempt to read from DB:
    try {
      logger.info("Product not found in local cache with ID: {}. Looking up in database...", productId);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("No Product found in database with matching id=" + productId));

      logger.info("Product read from database with ID: {}", productId);

      // Load all associated AnimalParts: //TODO: Should be loaded automatically by JPA!
      /*List<AnimalPart> animalParts = new ArrayList<>();
      try {
        animalParts = animalPartRepository.findAnimalPartsByType_typeId(product.getProductId()).orElseThrow(() -> new NotFoundException("No associated AnimalParts found in database with matching product_id=" + product.getProductId()));
      } catch (NotFoundException ignored) {}

      if(!animalParts.isEmpty())
        product.setAnimalParts(animalParts);*/

      // Populate the transient animalId association list:
      List<Long> animalPartIds = new ArrayList<>();
      for (AnimalPart animalPart : product.getContentList())
        animalPartIds.add(animalPart.getPart_id());
      product.setAnimalPartIdList(animalPartIds);

      // Load all associated Transfers: //TODO: Should be loaded automatically by JPA!
      /*List<TrayToProductTransfer> transfers = new ArrayList<>();
      try {
        transfers = trayToProductTransferRepository.findTrayToProductTransferByProduct_ProductId(product.getProductId()).orElseThrow(() -> new NotFoundException("No associated Transfer found in database matching product_id=" + product.getProductId()));
      } catch (NotFoundException ignored) {}

      if(!transfers.isEmpty())
        product.setTraySupplyJoinList(transfers);*/

      // Populate the transient transferId association list:
      List<Long> transferIds = new ArrayList<>();
      for (TrayToProductTransfer transfer : product.getTraySupplyJoinList())
        transferIds.add(transfer.getTransferId());
      product.setTransferIdList(transferIds);

      // Add found Product to local cache, to improve performance next time Product is requested.
      productCache.put(product.getProductId(), product);
      logger.info("Product added to local cache with ID: {}", product.getProductId());
      return product;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Product with ID {}: {}", productId, e.getMessage());
      throw new PersistenceException(e);
    }
  }

  @Override public List<Product> readProductsByTransferId(long transferId) throws PersistenceException, NotFoundException, DataIntegrityViolationException {
    // Validate received id, before passing to repository/database:
    ProductValidation.validateId(transferId);

    // Attempt to read from DB:
    try {
      List<Product> products = productRepository.findByTraySupplyJoinList_TransferId(transferId).orElseThrow(() -> new NotFoundException("No Products found in database associated with transferId=" + transferId));

      logger.info("Products associated with transferId {} read from database.", transferId);

      // Populate transient values for each Product:
      for (Product product : products) {

        // Populate the transient animalId association list:
        List<Long> animalPartIds = new ArrayList<>();
        for (AnimalPart animalPart : product.getContentList())
          animalPartIds.add(animalPart.getPart_id());
        product.setAnimalPartIdList(animalPartIds);

        // Populate the transient transferId association list:
        List<Long> transferIds = new ArrayList<>();
        for (TrayToProductTransfer transfer : product.getTraySupplyJoinList())
          transferIds.add(transfer.getTransferId());
        product.setTransferIdList(transferIds);

        // Populate the transient traySuppliers association list:
        List<Tray> traySupplierList = new ArrayList<>();
        for (Long transfer_id : product.getTransferIdList()){
          List<Tray> supplierList = new ArrayList<>();
          try {
            supplierList = trayRepository.findByTransferList_TransferId(transfer_id).orElseThrow(() -> new NotFoundException("No Products found in database associated with transferId=" + transferId));
          } catch (NotFoundException ignored) {}
          traySupplierList.addAll(supplierList);
        }
        product.setTraySuppliersList(traySupplierList);

        // Add found Product to local cache, to improve performance next time Product is requested.
        productCache.put(product.getProductId(), product);
        logger.info("Product added to local cache with ID: {}", product.getProductId());
      }

      return products;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while retrieving all Products associated with transferId {}: {}", transferId, e.getMessage());
      throw new PersistenceException(e);
    }
  }

  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean updateProduct(Product data) {
    // Validate received data, before passing to repository/database:
    ProductValidation.validateProduct(data);

    // Attempt to update Product in database:
    try {
      // Fetch the existing Entity from DB:
      Product product = productRepository.findById(data.getProductId()).orElseThrow(() -> new NotFoundException("No Product found in database with matching id=" + data.getProductId()));

      // Modify the database Entity locally:
      // AnimalPart List
      product.getContentList().clear();
      for (AnimalPart animalPart : data.getContentList()) {
        try {
          AnimalPart animalPartToAdd = animalPartRepository.findById(animalPart.getPart_id()).orElseThrow(() -> new NotFoundException(""));
          product.addAnimalPart(animalPartToAdd);
        } catch (NotFoundException ignored) {}
      }

      // TrayToProductTransfer List
      product.getTraySupplyJoinList().clear();
      for (TrayToProductTransfer transfer : data.getTraySupplyJoinList()) {
        try {
          TrayToProductTransfer transferToAdd = trayToProductTransferRepository.findById(transfer.getTransferId()).orElseThrow(() -> new NotFoundException(""));
          product.getTraySupplyJoinList().add(transferToAdd);
        } catch (NotFoundException ignored) {}
      }

      // Save the modified entity back to database:
      product = productRepository.save(product);
      logger.info("Product updated in database with ID: {}", product.getProductId());

      // Attempt to add Product to local cache:
      Product updatedProduct = readProduct(product.getProductId());
      productCache.put(updatedProduct.getProductId(), updatedProduct);
      logger.info("Product saved to local cache with ID: {}", updatedProduct.getProductId());

      // Get a list of AnimalParts that are no longer associated with this Product:
      List<AnimalPart> listOfAnimalPartsNotInUpdatedProduct = new ArrayList<>(data.getContentList());

      for (AnimalPart oldAnimalPart : data.getContentList())
        for (AnimalPart newAnimalPart : updatedProduct.getContentList())
          if(oldAnimalPart.getPart_id() == newAnimalPart.getPart_id())
            listOfAnimalPartsNotInUpdatedProduct.remove(oldAnimalPart);

      // Update all still-existing AnimalPart compositions:
      List<AnimalPart> threadSafeAnimalParts = new CopyOnWriteArrayList<>(updatedProduct.getContentList());
      for (AnimalPart animalPart : threadSafeAnimalParts) {
        animalPart.setProduct(updatedProduct);
        animalPartRepository.save(animalPart);
      }

      // Update any AnimalPart objects that are no longer associated with any Product entity:
      for (AnimalPart voidAnimalPart : listOfAnimalPartsNotInUpdatedProduct){
        try {
          AnimalPart animalPartVoid = animalPartRepository.findById(voidAnimalPart.getPart_id()).orElseThrow(() -> new NotFoundException(""));
          animalPartVoid.setProduct(null);
          animalPartRepository.save(animalPartVoid);
        } catch (NotFoundException ignored) {}
      }

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update Product in DB with id: {}, Reason: {}", data.getProductId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating Product with ID {}: {}", data.getProductId(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean removeProduct(Product data) {
    // Validate received data, before passing to repository/database:
    ProductValidation.validateProduct(data);

    try {
      // Attempt to delete the given Tray:
      productRepository.delete(data);

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
      // Product was removed from database. Now ensure that is it also removed from the local cache:
      productCache.remove(data.getProductId());
      logger.info("Product deleted from local cache with ID: {}", data.getProductId());
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

      // Load all associated AnimalParts, for each Product:  //TODO: Shouldn't be needed? JPA should be doing this already!
      for (Product product : products) {
        /*List<AnimalPart> animalParts = new ArrayList<>();
        try {
          animalParts = animalPartRepository.findAnimalPartsByType_typeId(product.getProductId()).orElseThrow(() -> new NotFoundException("No associated AnimalParts found in database with matching product_id=" + product.getProductId()));
        } catch (NotFoundException ignored) {}

        if(!animalParts.isEmpty()){
          product.setAnimalParts(animalParts);*/

          // Populate the transient id association list:
          List<Long> animalPartIds = new ArrayList<>();
          for (AnimalPart animalPart : product.getContentList())
            animalPartIds.add(animalPart.getPart_id());
          product.setAnimalPartIdList(animalPartIds);
        }

      //}

      // Load all associated TrayToProductTransfer, for each Product:  //TODO: Shouldn't be needed? JPA should be doing this already!
      for (Product product : products) {
        List<TrayToProductTransfer> transfers = new ArrayList<>();
        /*try {
          transfers = trayToProductTransferRepository.findTrayToProductTransferByTray_TrayId(product.getProductId()).orElseThrow(() -> new NotFoundException("No associated Transfers found in database matching product_id=" + product.getProductId()));
        } catch (NotFoundException ignored) {}

        if(!transfers.isEmpty()) {
          product.setTraySupplyJoinList(transfers);*/

          // Populate the transient id association list:
          List<Long> transferIds = new ArrayList<>();
          for (TrayToProductTransfer transfer : product.getTraySupplyJoinList())
            transferIds.add(transfer.getTransferId());
          product.setTransferIdList(transferIds);
        }
      //}

      // Add all the found Products to local cache, to improve performance next time a Tray is requested.
      productCache.clear();
      for (Product product : products) {
        if(product != null)
          productCache.put(product.getProductId(), product);
      }
      logger.info("Added all Products from Database to Local Cache");
      return products;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }
}
