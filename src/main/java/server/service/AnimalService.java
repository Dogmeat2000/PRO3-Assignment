package server.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.model.validation.AnimalValidation;
import server.repository.AnimalPartRepository;
import server.repository.AnimalRepository;
import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;
import shared.model.exceptions.NotFoundException;

import java.util.*;

@Service
public class AnimalService implements AnimalRegistryInterface
{
  private final AnimalRepository animalRepository;
  private final Map<Long, Animal> animalCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);
  private final AnimalPartRepository animalPartRepository;

  @Autowired
  public AnimalService(AnimalRepository animalRepository, AnimalPartRepository animalPartRepository) {
    this.animalRepository = animalRepository;
    this.animalPartRepository = animalPartRepository;
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override
  public Animal registerAnimal(Animal data) throws PersistenceException, DataIntegrityViolationException {

    // Validate received data, before passing to repository/database:
    AnimalValidation.validateAnimal(data);

    // Override any set animal_id, since the database is responsible for setting this value:
    data.setId(0);

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


  @Transactional (readOnly = true)
  @Override
  public Animal readAnimal(long animalId) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received id, before passing to repository/database:
    AnimalValidation.validateId(animalId);

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

      // Load all associated AnimalParts: //TODO: Shouldn't be needed? JPA should be doing this already!
      List<AnimalPart> animalParts = new ArrayList<>();
      try {
        animalParts = animalPartRepository.findAnimalPartsByAnimal_animalId(animal.getId()).orElseThrow(() -> new NotFoundException("No associated AnimalParts found in database with matching id=" + animal.getId()));
      } catch (NotFoundException ignored) {}

      if(!animalParts.isEmpty())
        animal.setAnimalParts(animalParts);

      // Populate the id association list:
      List<Long> animalPartIds = new ArrayList<>();
      for (AnimalPart animalPart : animal.getPartList())
        animalPartIds.add(animalPart.getPart_id());
      animal.setAnimalPartIdList(animalPartIds);

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
  @Override
  public boolean updateAnimal(Animal data) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // TODO: Not finished implemented yet.
    // Validate received data, before passing to repository/database:
    AnimalValidation.validateAnimal(data);

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

      // Get a list of AnimalParts that are no longer associated with this Animal:
      List<AnimalPart> listOfAnimalPartsNotInUpdatedAnimal = new ArrayList<>(data.getPartList());
      listOfAnimalPartsNotInUpdatedAnimal.removeAll(animal.getPartList());

      // Update all still-existing AnimalPart compositions:
      for (AnimalPart animalPart : animal.getPartList()) {
        AnimalPart oldAnimalPart = animalPart.copy();
        animalPart.setAnimal(animal);
        //animalPartRepository.updateAnimalPart(oldAnimalPart, animalPart);
        animalPartRepository.save(animalPart);
      }

      // Delete any AnimalPart objects that are no longer associated with any Animal entity:
      animalPartRepository.deleteAll(listOfAnimalPartsNotInUpdatedAnimal);

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
  @Override
  public boolean removeAnimal(Animal data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    AnimalValidation.validateAnimal(data);

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

      // Attempt to delete all associated AnimalPart entities:
      animalPartRepository.deleteAll(data.getPartList());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete Animal in DB with id: {}, Reason: {}", data.getId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override
  public List<Animal> getAllAnimals() throws PersistenceException {
    try {
      // Load all Animals from repository:
      List<Animal> animals = animalRepository.findAll();

      // Load all associated AnimalParts, for each Animal: //TODO: Shouldn't be needed? JPA should be doing this already!
      for (Animal animal : animals) {
        List<AnimalPart> animalParts = new ArrayList<>();
        try {
          animalParts = animalPartRepository.findAnimalPartsByAnimal_animalId(animal.getId()).orElseThrow(() -> new NotFoundException("No associated AnimalParts found in database with matching id=" + animal.getId()));
        } catch (NotFoundException ignored) {}

        if(!animalParts.isEmpty())
          animal.setAnimalParts(animalParts);
      }

      // Populate the id association list:
      for (Animal animal : animals) {
        List<Long> animalPartIds = new ArrayList<>();
        for (AnimalPart animalPart : animal.getPartList())
          animalPartIds.add(animalPart.getPart_id());
        animal.setAnimalPartIdList(animalPartIds);
      }

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
}
