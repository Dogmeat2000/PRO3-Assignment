package server.service;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.Product;
import shared.model.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

// TODO: Add javaDocs description
public interface ProductRegistryInterface
{
  // Create:
  Product registerProduct (Product data);

  // Read:
  Product readProduct (long productId);

  // Update:
  boolean updateProduct (Product data);

  // Delete:
  boolean removeProduct (Product data);

  // Get All:
  List<Product> getAllProducts();
}
