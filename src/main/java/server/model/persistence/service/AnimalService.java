package server.model.persistence.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.model.validation.AnimalValidation;
import server.model.persistence.repository.AnimalPartRepository;
import server.model.persistence.repository.AnimalRepository;
import server.model.persistence.entities.Animal;
import server.model.persistence.entities.AnimalPart;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.*;

@Service
public class AnimalService implements AnimalRegistryInterface
{
  private final AnimalRepository animalRepository;
  //private final Map<Long, Animal> animalCache = new HashMap<>();
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
      /*animalCache.put(newAnimal.getId(), newAnimal);
      logger.info("Animal saved to local cache with ID: {}", newAnimal.getId());*/

      return readAnimal(newAnimal.getId());

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

    // Animal not found in local cache. Attempt to read from DB:
    try {
      logger.info("Looking up animal with ID: {} in database...", animalId);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new NotFoundException("No Animal found in database with matching id=" + animalId));

      logger.info("Animal read from database with ID: {}", animalId);

      // Populate the id association list:
      /*List<Long> animalPartIds = new ArrayList<>();
      for (AnimalPart animalPart : animal.getAnimalPartList())
        animalPartIds.add(animalPart.getPart_id());
      animal.setAnimalPartIdList(animalPartIds);*/

      // Add found Animal to local cache, to improve performance next time Animal is requested.
      //animalCache.put(animal.getId(), animal);
      //logger.info("Animal added to local cache with ID: {}", animal.getId());
      return animal;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering Animal with ID {}: {}", animalId, e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override
  public boolean updateAnimal(Animal data) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received data, before passing to repository/database:
    AnimalValidation.validateAnimal(data);

    // Attempt to update Animal in database:
    try {
      // Fetch the existing Entity from DB:
      Animal animal = animalRepository.findById(data.getId()).orElseThrow(() -> new NotFoundException("No Animal found in database with matching id=" + data.getId()));

      // Modify the database Entity locally:
      animal.setWeight_kilogram(data.getWeight_kilogram());
      animal.setOrigin(data.getOrigin());
      animal.setArrivalDate(data.getArrivalDate());
      animal.clearAnimalPartList();
      for (AnimalPart animalPart : data.getAnimalPartList()) {
        try {
          AnimalPart animalPartToAdd = animalPartRepository.findById(animalPart.getPartId()).orElseThrow(() -> new NotFoundException(""));
          animal.addAnimalPart(animalPartToAdd);
        } catch (NotFoundException ignored) {}
      }

      // Save the modified entity back to database:
      animal = animalRepository.save(animal);
      logger.info("Animal updated in database with ID: {}", animal.getId());

      // Attempt to add updated Animal to local cache:
      /*Animal updatedAnimal = readAnimal(animal.getId());
      animalCache.put(updatedAnimal.getId(), updatedAnimal);
      logger.info("Animal saved to local cache with ID: {}", animal.getId());*/

      // Get a list of AnimalParts that are no longer associated with this Animal:
      // TODO: Should not be necessary since Cascade.All is set in entity.
      /*List<AnimalPart> listOfAnimalPartsNotInUpdatedAnimal = new ArrayList<>(data.getPartList());

      for (AnimalPart oldAnimalPart : data.getPartList()) {
        for (AnimalPart newAnimalPart : animal.getPartList()) {
          if(oldAnimalPart.getPart_id() == newAnimalPart.getPart_id()) {
            listOfAnimalPartsNotInUpdatedAnimal.remove(oldAnimalPart);
          }
        }
      }

      // Update all still-existing AnimalPart compositions:
      List<AnimalPart> threadSafeAnimalParts = new CopyOnWriteArrayList<>(animal.getPartList());
      for (AnimalPart animalPart : threadSafeAnimalParts) {
        animalPart.setAnimal(animal);
        animalPartRepository.save(animalPart);
      }

      // Delete any AnimalPart objects that are no longer associated with any Animal entity:
      for (AnimalPart voidAnimalPart : listOfAnimalPartsNotInUpdatedAnimal)
        animalPartRepository.findById(voidAnimalPart.getPart_id()).ifPresent(animalPartRepository::delete);*/

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
      /*animalCache.remove(data.getId());
      logger.info("Animal deleted from local cache with ID: {}", data.getId());*/

      // Attempt to delete all associated AnimalPart entities:
      // TODO: Should not be necessary due to JPA Cascade.All set.
      //animalPartRepository.deleteAll(data.getAnimalPartList());

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

      // Populate the id association list:
      /*for (Animal animal : animals) {
        List<Long> animalPartIds = new ArrayList<>();
        for (AnimalPart animalPart : animal.getAnimalPartList())
          animalPartIds.add(animalPart.getPart_id());
        animal.setAnimalPartIdList(animalPartIds);
      }*/

      // Add all the found Animals to local cache, to improve performance next time an Animal is requested.
      /*animalCache.clear();
      for (Animal animal : animals) {
        if(animal != null)
          animalCache.put(animal.getId(), animal);
      }
      logger.info("Added all Animals from Database to Local Cache");*/
      return animals;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }

  @Override public List<Animal> getAllAnimalsByOriginAndDate(String origin, Date arrivalDate) throws PersistenceException {
    try {
      // Load all Animals from repository:
      List<Animal> animals = animalRepository.findAnimalsByOriginAndArrivalDate(origin, arrivalDate).orElseThrow(() -> new NotFoundException("No matching animals found in repository."));

      // Populate the id association list:
      /*for (Animal animal : animals) {
        List<Long> animalPartIds = new ArrayList<>();
        for (AnimalPart animalPart : animal.getAnimalPartList())
          animalPartIds.add(animalPart.getPart_id());
        animal.setAnimalPartIdList(animalPartIds);
      }*/

      // Add all the found Animals to local cache, to improve performance next time an Animal is requested.
      /*animalCache.clear();
      for (Animal animal : animals) {
        if(animal != null)
          animalCache.put(animal.getId(), animal);
      }
      logger.info("Added all Animals from Database to Local Cache");*/
      return animals;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }

  @Override public List<Animal> getAllAnimalsByOrigin(String origin) throws PersistenceException {
    try {
      // Load all Animals from repository:
      List<Animal> animals = animalRepository.findAnimalsByOrigin(origin).orElseThrow(() -> new NotFoundException("No matching animals found in repository."));

      // Populate the id association list:
      /*for (Animal animal : animals) {
        List<Long> animalPartIds = new ArrayList<>();
        for (AnimalPart animalPart : animal.getAnimalPartList())
          animalPartIds.add(animalPart.getPart_id());
        animal.setAnimalPartIdList(animalPartIds);
      }*/

      // Add all the found Animals to local cache, to improve performance next time an Animal is requested.
      /*animalCache.clear();
      for (Animal animal : animals) {
        if(animal != null)
          animalCache.put(animal.getId(), animal);
      }
      logger.info("Added all Animals from Database to Local Cache");*/
      return animals;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }

  @Override public List<Animal> getAllAnimalsByDate(Date arrivalDate) throws PersistenceException {
    try {
      // Load all Animals from repository:
      List<Animal> animals = animalRepository.findAnimalsByArrivalDate(arrivalDate).orElseThrow(() -> new NotFoundException("No matching animals found in repository."));

      // Populate the id association list:
      /*for (Animal animal : animals) {
        List<Long> animalPartIds = new ArrayList<>();
        for (AnimalPart animalPart : animal.getAnimalPartList())
          animalPartIds.add(animalPart.getPart_id());
        animal.setAnimalPartIdList(animalPartIds);
      }*/

      // Add all the found Animals to local cache, to improve performance next time an Animal is requested.
      /*animalCache.clear();
      for (Animal animal : animals) {
        if(animal != null)
          animalCache.put(animal.getId(), animal);
      }
      logger.info("Added all Animals from Database to Local Cache");*/
      return animals;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }
}
