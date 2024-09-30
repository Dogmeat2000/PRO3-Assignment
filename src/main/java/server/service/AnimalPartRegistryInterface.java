package server.service;

import shared.model.entities.AnimalPart;

import java.util.List;

public interface AnimalPartRegistryInterface
{
  // Create:
  AnimalPart registerAnimalPart (AnimalPart data);

  // Read:
  AnimalPart readAnimalPart (String animalId);

  // Update:
  void updateAnimalPart (AnimalPart data);

  // Delete:
  void removeAnimalPart (AnimalPart data);

  // Get All:
  List<AnimalPart> getAllAnimalParts();
}
