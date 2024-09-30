package server.service;

import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;
import shared.model.entities.Product;

import java.util.List;

public interface ProductRegistryInterface
{
  // Create:
  Product registerProduct (Product data);

  // Read:
  Product readProduct (String productId);

  // Update:
  void updateProduct (Product data);

  // Delete:
  void removeProduct (Product data);

  // Get All:
  List<Product> getAllProducts();
}
