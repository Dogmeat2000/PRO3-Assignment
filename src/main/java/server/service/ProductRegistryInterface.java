package server.service;

import shared.model.entities.Product;

import java.util.List;

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
