package server.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.repository.AnimalPartRepository;
import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;
import shared.model.entities.PartType;
import shared.model.entities.Tray;
import shared.model.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnimalPartService implements AnimalPartRegistryInterface
{
  private final AnimalPartRepository animalPartRepository;
  private final Map<Long, AnimalPart> animalPartCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(AnimalPartService.class);

  @Autowired
  public AnimalPartService(AnimalPartRepository animalPartRepository) {
    this.animalPartRepository = animalPartRepository;
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public AnimalPart registerAnimalPart(AnimalPart data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    //validateWeight(data.getWeight_kilogram());

    // Attempt to add AnimalPart to DB:
    try {
      AnimalPart newAnimalPart = animalPartRepository.save(data);
      logger.info("AnimalPart added to DB with ID: {}", newAnimalPart.getPart_id());

      // Attempt to add AnimalPart to local cache:
      animalPartCache.put(newAnimalPart.getPart_id(), newAnimalPart);
      logger.info("Animal saved to local cache with ID: {}", newAnimalPart.getPart_id());

      return newAnimalPart;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register AnimalPart in DB with weight: {}, Reason: {}", data.getWeight(), e.getMessage());
      throw new DataIntegrityViolationException("Invalid AnimalPart provided. Incompatible with database!");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering AnimalPart with ID {}: {}", data.getPart_id(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Override public AnimalPart readAnimalPart(long part_id, Animal animal, PartType type, Tray tray) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    return null;
  }


  @Override public void updateAnimalPart(AnimalPart data) throws NotFoundException, DataIntegrityViolationException, PersistenceException {

  }


  @Override public void removeAnimalPart(AnimalPart data) throws PersistenceException, DataIntegrityViolationException {

  }


  @Override public List<AnimalPart> getAllAnimalParts() throws PersistenceException {
    return List.of();
  }
}
