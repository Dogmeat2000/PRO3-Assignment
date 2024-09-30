package server.service;

import shared.model.entities.Animal;

import java.util.List;

public interface AnimalRegistryInterface
{
  // Create:
  Animal registerAnimal (Animal data);

  // Read:
  Animal readAnimal (String animalId);

  // Update:
  int updateAnimal (Animal data);

  // Delete:
  int removeAnimal (Animal data);

  // Get All:
  List<Animal> getAllAnimals();
}
