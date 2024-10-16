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
import server.repository.JPA_CompositeKeys.TrayToProductTransferId;
import server.repository.ProductRepository;
import server.repository.TrayToProductTransferRepository;
import shared.model.entities.*;
import shared.model.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    // Reset productId, since the database handles this assignment:
    data.setProduct_id(1);

    // Validate received data, before passing to repository/database:
    ProductValidation.validateProduct(data);

    // Attempt to add Product to DB:
    try {
      Product newProduct = productRepository.save(data);
      logger.info("Product added to DB with ID: {}", newProduct.getProduct_id());

      // Attempt to add Product to local cache:
      productCache.put(newProduct.getProduct_id(), newProduct);
      logger.info("Product saved to local cache with ID: {}", newProduct.getProduct_id());

      // Ensure that all TrayToProductTransfer transfers are registered and/or updated:
      for (TrayToProductTransfer transfer : data.getTraySupplyJoinList()) {
        TrayToProductTransferId transferId = new TrayToProductTransferId(transfer.getProduct_id(), transfer.getTray_id());
        trayToProductTransferRepository.save(transferId);
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

      // Add found Product to local cache, to improve performance next time Product is requested.
      productCache.put(product.getProduct_id(), product);
      logger.info("Product added to local cache with ID: {}", product.getProduct_id());
      return product;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Product with ID {}: {}", productId, e.getMessage());
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
      Product product = productRepository.findById(data.getProduct_id()).orElseThrow(() -> new NotFoundException("No Product found in database with matching id=" + data.getProduct_id()));

      // Modify the database Entity locally:
      product.getContentList().clear();
      product.getContentList().addAll(data.getContentList());

      product.getTraySupplyJoinList().clear();
      product.getTraySupplyJoinList().addAll(data.getTraySupplyJoinList());

      // Ensure that all TrayToProductTransfer transfers are registered and/or updated:
      for (TrayToProductTransfer transfer : product.getTraySupplyJoinList()) {
        TrayToProductTransferId transferId = new TrayToProductTransferId(transfer.getProduct_id(), transfer.getTray_id());
        trayToProductTransferRepository.save(transferId);
      }

      // Ensure that all associated AnimalPart entities are updated:
      // TODO: Missing implementation

      // Save the modified entity back to database:
      productRepository.save(product);
      logger.info("Product updated in database with ID: {}", product.getProduct_id());

      // Attempt to add PartType to local cache:
      productCache.put(product.getProduct_id(), product);
      logger.info("Product saved to local cache with ID: {}", product.getProduct_id());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update Product in DB with id: {}, Reason: {}", data.getProduct_id(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating Product with ID {}: {}", data.getProduct_id(), e.getMessage());
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
      Optional<Product> deletedProduct = productRepository.findById(data.getProduct_id());

      if(deletedProduct.isPresent()) {
        // Product was not removed from database.
        logger.info("Product was NOT deleted from database with ID: {}", data.getProduct_id());
        throw new PersistenceException("Product with ID '" + data.getProduct_id() + "' was not deleted!");
      }

      // Ensure that all TrayToProductTransfer transfers are deleted:
      for (TrayToProductTransfer transfer : data.getTraySupplyJoinList()) {
        TrayToProductTransferId transferId = new TrayToProductTransferId(transfer.getProduct_id(), transfer.getTray_id());
        trayToProductTransferRepository.delete(transferId);
      }

      logger.info("Product deleted from database with ID: {}", data.getProduct_id());
      // Product was removed from database. Now ensure that is it also removed from the local cache:
      productCache.remove(data.getProduct_id());
      logger.info("Product deleted from local cache with ID: {}", data.getProduct_id());
      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete Product in DB with id: {}, Reason: {}", data.getProduct_id(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Override public List<Product> getAllProducts() {
    try {
      List<Product> products = productRepository.findAll();

      // Add all the found Products to local cache, to improve performance next time a Tray is requested.
      productCache.clear();
      for (Product product : products) {
        if(product != null)
          productCache.put(product.getProduct_id(), product);
      }
      logger.info("Added all Products from Database to Local Cache");
      return products;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }
}
