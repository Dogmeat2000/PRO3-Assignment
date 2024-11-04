package server.model.persistence.service;

import jakarta.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import shared.model.entities.AnimalPart;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.List;

// TODO: Add javaDocs description
public interface AnimalPartRegistryInterface
{
  // Create:
  AnimalPart registerAnimalPart (AnimalPart data) throws PersistenceException, DataIntegrityViolationException;

  // Read:
  AnimalPart readAnimalPart (long part_id) throws NotFoundException, DataIntegrityViolationException, PersistenceException;

  List<AnimalPart> readAnimalPartsByAnimalId(long animalId) throws PersistenceException, NotFoundException, DataIntegrityViolationException;


  List<AnimalPart> readAnimalPartsByPartTypeId(long partTypeId) throws PersistenceException, NotFoundException, DataIntegrityViolationException;


  List<AnimalPart> readAnimalPartsByProductId(long productId) throws PersistenceException, NotFoundException, DataIntegrityViolationException;


  List<AnimalPart> readAnimalPartsByTrayId(long trayId) throws PersistenceException, NotFoundException, DataIntegrityViolationException;

  // Update:
  boolean updateAnimalPart (AnimalPart data) throws NotFoundException, DataIntegrityViolationException, PersistenceException;

  // Delete:
  boolean removeAnimalPart (AnimalPart data) throws PersistenceException, DataIntegrityViolationException;

  // Get All:
  List<AnimalPart> getAllAnimalParts() throws PersistenceException;
}
