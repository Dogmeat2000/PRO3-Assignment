package server.service;

import shared.model.entities.Animal;
import shared.model.entities.Product;
import shared.model.exceptions.AnimalNotFoundException;

import java.util.List;

public interface AnimalRegistryInterface
{
  // Create:
  Animal registerAnimal (Animal data);

  // Read:
  Animal readAnimal (long animalId) throws AnimalNotFoundException;

  // Update:
  boolean updateAnimal (Animal data) throws AnimalNotFoundException;

  // Delete:
  boolean removeAnimal (Animal data);

  // Get All:
  List<Animal> getAllAnimals();

  // Recall functions:
  List<Animal> getAllAnimalsInProduct(long productId);
  List<Product> getAllProductsThatContainAnimal(long animalId);
}
