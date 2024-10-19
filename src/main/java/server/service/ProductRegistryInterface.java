package server.service;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.Product;
import shared.model.exceptions.NotFoundException;

import java.util.List;

// TODO: Add javaDocs description
public interface ProductRegistryInterface
{
  // Create:
  Product registerProduct (Product data);

  // Read:
  Product readProduct (long productId);

  // TODO: add javaDocs
  List<Product> readProductsByTransferId(long transferId) throws PersistenceException, NotFoundException, DataIntegrityViolationException;

  // Update:
  boolean updateProduct (Product data);

  // Delete:
  boolean removeProduct (Product data);

  // Get All:
  List<Product> getAllProducts();
}
