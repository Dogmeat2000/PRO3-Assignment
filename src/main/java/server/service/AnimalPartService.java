package server.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.model.validation.AnimalPartValidation;
import server.repository.AnimalPartRepository;
import server.repository.JPA_CompositeKeys.AnimalPartId;
import shared.model.entities.AnimalPart;
import shared.model.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AnimalPartService implements AnimalPartRegistryInterface
{
  private final AnimalPartRepository animalPartRepository;
  private final Map<String, AnimalPart> animalPartCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(AnimalPartService.class);

  @Autowired
  public AnimalPartService(AnimalPartRepository animalPartRepository) {
    this.animalPartRepository = animalPartRepository;
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public AnimalPart registerAnimalPart(AnimalPart data) throws PersistenceException, DataIntegrityViolationException {
    // Override any set part_id, since the database automatically assigns this when creating:
    data.setPart_id(1);

    // Validate received data, before passing to repository/database:
    AnimalPartValidation.validateAnimalPart(data);

    // Attempt to add AnimalPart to DB:
    try {
      AnimalPart newAnimalPart = animalPartRepository.save(data);
      logger.info("AnimalPart added to DB with ID: {}", newAnimalPart.getPart_id());

      // Attempt to add AnimalPart to local cache:
      String hashMapKey = "" + newAnimalPart.getPart_id() + newAnimalPart.getAnimal_id() + newAnimalPart.getType_id() + newAnimalPart.getTray_id();
      animalPartCache.put(hashMapKey, newAnimalPart);
      logger.info("Animal saved to local cache with ID: {}", hashMapKey);

      return newAnimalPart;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register AnimalPart in DB with weight: {}, Reason: {}", data.getWeight_kilogram(), e.getMessage());
      throw new DataIntegrityViolationException("Invalid AnimalPart provided. Incompatible with database!");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering AnimalPart with ID {}: {}", data.getPart_id(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Override public AnimalPart readAnimalPart(long part_id, long animalId, long typeId, long trayId) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received id, before passing to repository/database:
    AnimalPartValidation.validateId(part_id, animalId, typeId, trayId);

    // Attempt to read AnimalPart from local cache first:
    String hashMapKey = "" + part_id + animalId + typeId + trayId;
    if(animalPartCache.containsKey(hashMapKey)) {
      logger.info("AnimalPart read from local cache with ID: {}", hashMapKey);
      return animalPartCache.get(hashMapKey);
    }

    // AnimalPart not found in local cache. Attempt to read from DB:
    try {
      logger.info("AnimalPart not found in local cache with ID: {}. Looking up in database...", hashMapKey);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      AnimalPartId animalPartCompositeKey = new AnimalPartId(part_id, animalId, typeId, trayId);
      AnimalPart animalPart = animalPartRepository.findById(animalPartCompositeKey).orElseThrow(() -> new NotFoundException("No AnimalPart found in database with matching id=" + animalPartCompositeKey));

      logger.info("AnimalPart read from database with ID: {}", animalPartCompositeKey);

      // Add found AnimalPart to local cache, to improve performance next time AnimalPart is requested.
      hashMapKey = "" + animalPart.getPart_id() + animalPart.getAnimal_id() + animalPart.getType_id() + animalPart.getType_id();
      animalPartCache.put(hashMapKey, animalPart);
      logger.info("AnimalPart added to local cache with ID: {}", hashMapKey);
      return animalPart;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering AnimalPart with ID {}: {}", hashMapKey, e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean updateAnimalPart(AnimalPart data) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received data, before passing to repository/database:
    AnimalPartValidation.validateAnimalPart(data);

    // Attempt to update AnimalPart in database:
    try {
      // Fetch the existing Entity from DB:
      AnimalPartId animalPartCompositeKey = new AnimalPartId(data.getPart_id(), data.getAnimal_id(), data.getType_id(), data.getTray_id());
      AnimalPart animalPart = animalPartRepository.findById(animalPartCompositeKey).orElseThrow(() -> new NotFoundException("No AnimalPart found in database with matching id=" + animalPartCompositeKey));

      // Modify the database Entity locally:
      animalPart.setWeight_kilogram(data.getWeight_kilogram());
      animalPart.setAnimal(data.getAnimal());
      animalPart.setType(data.getType());
      animalPart.setTray(data.getTray());
      animalPart.setProduct(data.getProduct());

      // Save the modified entity back to database:
      animalPartRepository.save(animalPart);
      logger.info("AnimalPart updated in database with ID: {}", animalPartCompositeKey);

      // Attempt to update all associated Animal, Tray, PartType and Product entities:
      // TODO: Missing implementation

      // Attempt to add AnimalPart to local cache:
      String hashMapKey = "" + animalPart.getPart_id() + animalPart.getAnimal_id() + animalPart.getType_id() + animalPart.getType_id();
      animalPartCache.put(hashMapKey, animalPart);
      logger.info("AnimalPart saved to local cache with ID: {}", animalPartCompositeKey);

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update AnimalPart in DB with id: {}, Reason: {}", data.getPart_id(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating AnimalPart with ID {}: {}", data.getPart_id(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean removeAnimalPart(AnimalPart data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    AnimalPartValidation.validateAnimalPart(data);

    try {
      // Attempt to delete the given AnimalPart:
      animalPartRepository.delete(data);

      // Confirm that the entity has been removed:
      AnimalPartId animalPartCompositeKey = new AnimalPartId(data.getPart_id(), data.getAnimal_id(), data.getType_id(), data.getTray_id());
      Optional<AnimalPart> deletedAnimalPart = animalPartRepository.findById(animalPartCompositeKey);

      if(deletedAnimalPart.isPresent()) {
        // AnimalPart was not removed from database.
        logger.info("AnimalPart was NOT deleted from database with ID: {}", animalPartCompositeKey);
        throw new PersistenceException("AnimalPart with ID " + animalPartCompositeKey + " was not deleted!");
      }

      logger.info("AnimalPart deleted from database with ID: {}", animalPartCompositeKey);
      // AnimalPart was removed from database. Now ensure that is it also removed from the local cache:
      String hashMapKey = "" + data.getPart_id() + data.getAnimal_id() + data.getType_id() + data.getType_id();
      animalPartCache.remove(hashMapKey);
      logger.info("AnimalPart deleted from local cache with ID: {}", hashMapKey);

      // Attempt to update all associated Animal, Tray, PartType and Product entities:
      // TODO: Missing implementation

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete AnimalPart in DB with id: {}, Reason: {}", data.getPart_id(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Override public List<AnimalPart> getAllAnimalParts() throws PersistenceException {
    try {
      List<AnimalPart> animalParts = animalPartRepository.findAll();

      // Add all the found AnimalParts to local cache, to improve performance next time an AnimalPart is requested.
      animalPartCache.clear();
      for (AnimalPart animalPart : animalParts) {
        if(animalPart != null){
          String hashMapKey = "" + animalPart.getPart_id() + animalPart.getAnimal_id() + animalPart.getType_id() + animalPart.getType_id();
          animalPartCache.put(hashMapKey, animalPart);
        }
      }
      logger.info("Added all AnimalParts from Database to Local Cache");
      return animalParts;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


}
