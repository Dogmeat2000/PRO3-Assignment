package server.service;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.AnimalPart;
import shared.model.exceptions.NotFoundException;

import java.util.List;

// TODO: Add javaDocs description
public interface AnimalPartRegistryInterface
{
  // Create:
  AnimalPart registerAnimalPart (AnimalPart data) throws PersistenceException, DataIntegrityViolationException;

  // Read:
  AnimalPart readAnimalPart (long part_id, long animalId, long typeId, long trayId) throws NotFoundException, DataIntegrityViolationException, PersistenceException;

  // Update:
  boolean updateAnimalPart (AnimalPart data) throws NotFoundException, DataIntegrityViolationException, PersistenceException;

  // Delete:
  boolean removeAnimalPart (AnimalPart data) throws PersistenceException, DataIntegrityViolationException;

  // Get All:
  List<AnimalPart> getAllAnimalParts() throws PersistenceException;
}
