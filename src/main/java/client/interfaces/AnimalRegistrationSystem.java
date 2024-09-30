package client.interfaces;

import shared.model.entities.Animal;

import java.math.BigDecimal;
import java.util.List;

public interface AnimalRegistrationSystem
{
  // Create:
  Animal registerNewAnimal (BigDecimal weightInKilogram) throws InterruptedException;

  // Read:
  Animal readAnimal (String animalId);

  // Update:
  int updateAnimal (Animal data);

  // Delete:
  int removeAnimal (Animal data);

  // Get All:
  List<Animal> getAllAnimals();
}
