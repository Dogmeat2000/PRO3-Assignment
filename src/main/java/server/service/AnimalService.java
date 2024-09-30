package server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.repository.AnimalRepository;
import server.repository.exceptions.DBInsertionException;
import server.repository.exceptions.DBPrimaryKeyRetrievalException;
import shared.model.entities.Animal;
import shared.model.exceptions.AnimalNotFoundException;

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


  @Override public int updateAnimal(Animal data) {
    //TODO Not implemented yet
    return -1;
  }


  @Override public int removeAnimal(Animal data) {
    //TODO Not implemented yet
    return -1;
  }


  @Override public List<Animal> getAllAnimals() {
    //TODO Not implemented yet
    return List.of();
  }
}
