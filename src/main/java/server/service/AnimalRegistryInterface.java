package server.service;

import shared.model.entities.Animal;
import shared.model.exceptions.AnimalNotFoundException;

import java.util.List;

public interface AnimalRegistryInterface
{
  // Create:
  Animal registerAnimal (Animal data);

  // Read:
  Animal readAnimal (long animalId) throws AnimalNotFoundException;

  // Update:
  int updateAnimal (Animal data);

  // Delete:
  int removeAnimal (Animal data);

  // Get All:
  List<Animal> getAllAnimals();
}
