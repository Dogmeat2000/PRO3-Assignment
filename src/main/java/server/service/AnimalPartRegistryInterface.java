package server.service;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;
import shared.model.entities.PartType;
import shared.model.entities.Tray;
import shared.model.exceptions.NotFoundException;

import java.util.List;

// TODO: Add javaDocs description
public interface AnimalPartRegistryInterface
{
  // Create:
  AnimalPart registerAnimalPart (AnimalPart data) throws PersistenceException, DataIntegrityViolationException;

  // Read:
  AnimalPart readAnimalPart (long part_id, Animal animal, PartType type, Tray tray) throws NotFoundException, DataIntegrityViolationException, PersistenceException;

  // Update:
  void updateAnimalPart (AnimalPart data) throws NotFoundException, DataIntegrityViolationException, PersistenceException;

  // Delete:
  void removeAnimalPart (AnimalPart data) throws PersistenceException, DataIntegrityViolationException;

  // Get All:
  List<AnimalPart> getAllAnimalParts() throws PersistenceException;
}
