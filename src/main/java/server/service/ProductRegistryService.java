package server.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.model.validation.ProductValidation;
import server.repository.ProductRepository;
import server.repository.TrayToProductTransferRepository;
import shared.model.entities.*;
import shared.model.exceptions.NotFoundException;

import java.util.*;

@Service
public class ProductRegistryService implements ProductRegistryInterface
{
  private final ProductRepository productRepository;
  private final Map<Long, Product> productCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(ProductRegistryService.class);
  private final TrayToProductTransferRepository trayToProductTransferRepository;

  @Autowired
  public ProductRegistryService(ProductRepository productRepository, TrayToProductTransferRepository trayToProductTransferRepository) {
    this.productRepository = productRepository;
    this.trayToProductTransferRepository = trayToProductTransferRepository;
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public Product registerProduct(Product data) {

    // Validate received data, before passing to repository/database:
    ProductValidation.validateProduct(data);

    // Reset productId, since the database handles this assignment:
    data.setProductId(0);

    // Attempt to add Product to DB:
    try {
      Product newProduct = productRepository.save(data);
      logger.info("Product added to DB with ID: {}", newProduct.getProductId());

      // Attempt to add Product to local cache:
      productCache.put(newProduct.getProductId(), newProduct);
      logger.info("Product saved to local cache with ID: {}", newProduct.getProductId());

      // Ensure that all TrayToProductTransfer transfers are registered and/or updated:
      trayToProductTransferRepository.saveAll(data.getTraySupplyJoinList());

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
    if(productCache.containsKey(productId)) {
      logger.info("Product read from local cache with ID: {}", productId);
      return productCache.get(productId);
    }

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


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean updateProduct(Product data) {
    // TODO: Not finished implemented yet.
    return false;

    // Validate received data, before passing to repository/database:
    /*ProductValidation.validateProduct(data);

    // Attempt to update Product in database:
    try {
      // Fetch the existing Entity from DB:
      Product product = productRepository.findById(data.getProductId()).orElseThrow(() -> new NotFoundException("No Product found in database with matching id=" + data.getProductId()));

      // Modify the database Entity locally:
      product.getContentList().clear();
      product.getContentList().addAll(data.getContentList());

      product.getTraySupplyJoinList().clear();
      product.getTraySupplyJoinList().addAll(data.getTraySupplyJoinList());

      // Ensure that all TrayToProductTransfer transfers are registered and/or updated:
      //TrayToProductTransferId transferId = new TrayToProductTransferId(transfer.getProduct_id(), transfer.getTray_id());
      //trayToProductTransferRepository.save(transferId);
      trayToProductTransferRepository.saveAll(product.getTraySupplyJoinList());

      // Ensure that all associated AnimalPart entities are updated:
      for (AnimalPart animalPart : data.getContentList()){
        animalPart.setProduct(product);
        animalPartRepository.save(animalPart);
      }

      // Save the modified entity back to database:
      productRepository.save(product);
      logger.info("Product updated in database with ID: {}", product.getProductId());

      // Attempt to add PartType to local cache:
      productCache.put(product.getProductId(), product);
      logger.info("Product saved to local cache with ID: {}", product.getProductId());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update Product in DB with id: {}, Reason: {}", data.getProductId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating Product with ID {}: {}", data.getProductId(), e.getMessage());
      throw new PersistenceException(e);
    }*/
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
