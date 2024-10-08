package server.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.repository.AnimalRepository;
import shared.model.entities.Animal;
import shared.model.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AnimalService implements AnimalRegistryInterface
{
  private final AnimalRepository animalRepository;
  private final Map<Long, Animal> animalCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);

  @Autowired
  public AnimalService(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public Animal registerAnimal(Animal data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    validateWeight(data.getWeight_kilogram());

    // Attempt to add Animal to DB:
    try {
      Animal newAnimal = animalRepository.save(data);
      logger.info("Animal added to DB with ID: {}", newAnimal.getId());

      // Attempt to add Animal to local cache:
      animalCache.put(newAnimal.getId(), newAnimal);
      logger.info("Animal saved to local cache with ID: {}", newAnimal.getId());

      return newAnimal;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register Animal in DB with weight: {}, Reason: {}", data.getWeight_kilogram(), e.getMessage());
      throw new DataIntegrityViolationException("Invalid Animal provided. Incompatible with database!");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Animal with ID {}: {}", data.getId(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Override public Animal readAnimal(long animalId) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received id, before passing to repository/database:
    validateId(animalId);

    // Attempt to read Animal from local cache first:
    if(animalCache.containsKey(animalId)) {
      logger.info("Animal read from local cache with ID: {}", animalId);
      return animalCache.get(animalId);
    }

    // Animal not found in local cache. Attempt to read from DB:
    try {
      logger.info("Animal not found in local cache with ID: {}. Looking up in database...", animalId);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new NotFoundException("No Animal found in database with matching id=" + animalId));

      logger.info("Animal read from database with ID: {}", animalId);

      // Add found Animal to local cache, to improve performance next time Animal is requested.
      animalCache.put(animal.getId(), animal);
      logger.info("Animal added to local cache with ID: {}", animal.getId());
      return animal;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Animal with ID {}: {}", animalId, e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean updateAnimal(Animal data) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received data, before passing to repository/database:
    validateAnimal(data);

    // Attempt to update Animal in database:
    try {
      // Fetch the existing Entity from DB:
      Animal animal = animalRepository.findById(data.getId()).orElseThrow(() -> new NotFoundException("No Animal found in database with matching id=" + data.getId()));

      // Modify the database Entity locally:
      animal.setWeight_kilogram(data.getWeight_kilogram());
      animal.getPartList().clear();
      animal.getPartList().addAll(data.getPartList());

      // Save the modified entity back to database:
      animalRepository.save(animal);
      logger.info("Animal updated in database with ID: {}", animal.getId());

      // Attempt to add Animal to local cache:
      animalCache.put(animal.getId(), animal);
      logger.info("Animal saved to local cache with ID: {}", animal.getId());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update Animal in DB with weight: {}, Reason: {}", data.getWeight_kilogram(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating Animal with ID {}: {}", data.getId(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean removeAnimal(Animal data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    validateAnimal(data);

    try {
      // Attempt to delete the given Animal:
      animalRepository.delete(data);

      // Confirm that the entity has been removed:
      Optional<Animal> deletedAnimal = animalRepository.findById(data.getId());

      if(deletedAnimal.isPresent()) {
        // Animal was not removed from database.
        logger.info("Animal was NOT deleted from database with ID: {}", data.getId());
        throw new PersistenceException("Animal with ID " + data.getId() + " was not deleted!");
      }

      logger.info("Animal deleted from database with ID: {}", data.getId());
      // Animal was removed from database. Now ensure that is it also removed from the local cache:
      animalCache.remove(data.getId());
      logger.info("Animal deleted from local cache with ID: {}", data.getId());
      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete Animal in DB with id: {}, Reason: {}", data.getId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Override public List<Animal> getAllAnimals() throws PersistenceException {

    try {
      List<Animal> animals = animalRepository.findAll();

      // Add all the found Animals to local cache, to improve performance next time an Animal is requested.
      animalCache.clear();
      for (Animal animal : animals) {
        if(animal != null)
          animalCache.put(animal.getId(), animal);
      }
      logger.info("Added all Animals from Database to Local Cache");
      return animals;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  private void validateAnimal(Animal animal) throws DataIntegrityViolationException {
    // Animal cannot be null:
    if(animal == null)
      throw new DataIntegrityViolationException("Animal is null");

    // Validate animal_id:
    validateId(animal.getId());

    // Animal weight must be larger than 0:
    validateWeight(animal.getWeight_kilogram());

    // Validation passed:
  }


  private void validateId(long animal_id) throws DataIntegrityViolationException {
    // Animal_id must be larger than 0:
    if(animal_id <= 0)
      throw new DataIntegrityViolationException("animal_id is invalid (0 or less)");

    // Validation passed:
  }


  private void validateWeight(BigDecimal weight) throws DataIntegrityViolationException {
    // Animal weight must be larger than 0:
    if(weight.compareTo(BigDecimal.valueOf(0)) <= 0)
      throw new DataIntegrityViolationException("weight_kilogram is invalid (0 or less)");

    // Validation passed:
  }
}
