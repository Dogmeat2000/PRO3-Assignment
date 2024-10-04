package server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.repository.AnimalRepository;
import server.repository.exceptions.DBInsertionException;
import server.repository.exceptions.DBPrimaryKeyMatchNotFound;
import server.repository.exceptions.DBPrimaryKeyRetrievalException;
import shared.model.entities.Animal;
import shared.model.entities.Product;
import shared.model.exceptions.AnimalNotFoundException;
import shared.model.exceptions.UpdateFailedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  @Override public Animal registerAnimal(Animal data) {
    // Perform Data Validation:
    // if (weightInKilogram.compareTo(BigDecimal.ZERO) < 0) throw new ValidationException("price must be greater than zero");
    // TODO: More data validation?

    // Attempt to add Animal to DB:
    try {
      logger.debug("Adding Animal to DB with weight: {}", data.getWeight());
      Animal newAnimal = animalRepository.addNewAnimalToDatabase(data);
      logger.info("Animal added to DB with ID: {}", newAnimal.getId());

      // Attempt to add Animal to local cache:
      animalCache.put(newAnimal.getId(), newAnimal);
      logger.info("Animal saved to local cache with ID: {}", newAnimal.getId());

      return newAnimal;
    } catch (DBInsertionException e) {
      // TODO: Implement proper exception handling.
      throw new RuntimeException(e);
    } catch (DBPrimaryKeyRetrievalException e) {
      // TODO: Implement proper exception handling.
      throw new RuntimeException(e);
    }

  }


  @Override public Animal readAnimal(long animalId) {
    // Perform Data Validation:
    // TODO: Implement validation

    // Attempt to read Animal from local cache first:
    if(animalCache.containsKey(animalId)) {
      logger.info("Animal read from local cache with ID: {}", animalId);
      return animalCache.get(animalId);
    }

    // Animal not found in local cache. Attempt to read from DB:
    try {
      Animal animal = animalRepository.getAnimalByIdFromDatabase(animalId);
      logger.info("Animal read from database with ID: {}", animalId);

      // Add found Animal to local cache, to improve performance next time Animal is requested.
      if(animal != null) {
        animalCache.put(animalId, animal);
        logger.info("Animal added to local cache with ID: {}", animalId);
      }
      return animal;
    } catch (DBPrimaryKeyRetrievalException e) {
      // TODO: Implement proper exception handling.
      logger.info("ERROR: Unable to find Animal with ID: {}", animalId);
      throw new AnimalNotFoundException("No Animal found in database with matching id.");
    }
  }


  @Override public boolean updateAnimal(Animal data) {
    // Perform Data Validation:
    // TODO: Implement validation

    // Attempt to update Animal in database:
    try {
      if(animalRepository.updateExistingAnimalInDatabase(data)) {
        logger.info("Animal updated in database with ID: {}", data.getId());
        // Update Animal in local cache also, overwriting any existing animal associated with given key:
        animalCache.put(data.getId(), data);
        logger.info("Animal updated in local cache with ID: {}", data.getId());
        return true;
      }
      // Update same Animal in local cache, if exists:
    } catch (DBPrimaryKeyMatchNotFound e) {
      logger.info("ERROR: Unable to find Animal with ID: {}", data.getId());
      throw new AnimalNotFoundException("No Animal found in database with matching id.");
    } catch (DBInsertionException e) {
      logger.info("ERROR: Unable to Update Animal with ID: {}", data.getId() + ". Multiple entries with this ID found.");
      throw new UpdateFailedException("Animal Update failed. Found multiple entries of the same animal_id");
    }
    return false;
  }


  @Override public boolean removeAnimal(Animal data) {
    //TODO Not implemented yet
    return false;
  }


  @Override public List<Animal> getAllAnimals() {
    // Compare last entry in database with last entry in local cache.
    // If both are same, then no need to query database. Else query database for updated list,
    // but only get the entries that are missing between local cache last entry and last entry in the database.
    // TODO: Implement above!

    //logger.info("Checking if local cache has the same number of Animal data entries as the database");
    //logger.info("Database has more entries, than local cache. Retrieving the missing entries from database to local cache.");

    // Below is temporary 'easy' implementation. TODO: Refactor the below code!
    try {
      List<Animal> animals = animalRepository.getAllAnimalsFromDatabase();

      // Add found Animals to local cache, to improve performance next time Animal is requested.
      animalCache.clear();
      for (Animal animal : animals) {
        // Clear cache.
        if(animal != null) {
          animalCache.put(animal.getId(), animal);
          logger.info("Animal added to local cache with ID: {}", animal.getId());
        }
      }
      return animals;
    } catch (DBPrimaryKeyRetrievalException e) {
      // TODO: Implement proper exception handling.
      logger.info("ERROR: Exception occurred while attempting to query Animals from database.");
      throw new AnimalNotFoundException("Did not find any Animals in the database!");
    }
  }


  @Override public List<Animal> getAllAnimalsInProduct(long productId) {
    //TODO Not implemented yet
    return List.of();
  }


  @Override public List<Product> getAllProductsThatContainAnimal(long animalId) {
    //TODO Not implemented yet
    return List.of();
  }
}
