package DataServer.model.persistence.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import DataServer.model.validation.PartTypeValidation;
import DataServer.model.persistence.repository.AnimalPartRepository;
import DataServer.model.persistence.repository.PartTypeRepository;
import DataServer.model.persistence.entities.AnimalPart;
import DataServer.model.persistence.entities.PartType;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.*;

@Service
public class PartTypeService implements PartTypeRegistryInterface
{
  private final PartTypeRepository partTypeRepository;
  private static final Logger logger = LoggerFactory.getLogger(PartTypeService.class);
  private final AnimalPartRepository animalPartRepository;

  @Autowired
  public PartTypeService(PartTypeRepository partTypeRepository, AnimalPartRepository animalPartRepository) {
    this.partTypeRepository = partTypeRepository;
    this.animalPartRepository = animalPartRepository;
  }


  @Transactional
  @Override
  public PartType registerPartType (PartType data) throws PersistenceException, DataIntegrityViolationException {

    // Validate received data, before passing to repository/database:
    PartTypeValidation.validatePartType(data);

    // Override any set partTypeId, since the database is responsible for setting this value:
    data.setTypeId(0);

    // Attempt to add PartType to DB:
    try {
      PartType newPartType = partTypeRepository.save(data);
      logger.info("PartType added to DB with ID: {}", data.getTypeId());

      return newPartType;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register PartType in DB with weight: {}, Reason: {}", data.getTypeDesc(), e.getMessage());
      throw new DataIntegrityViolationException("Invalid PartType provided. Incompatible with database!");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering PartType with ID {}: {}", data.getTypeId(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override
  public PartType readPartType (long typeId) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received id, before passing to repository/database:
    PartTypeValidation.validateId(typeId);

    // Attempt to read from DB:
    try {
      logger.info("Looking up PartType with ID: {} in database...", typeId);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      PartType partType = partTypeRepository.findById(typeId).orElseThrow(() -> new NotFoundException("No PartType found in database with matching id=" + typeId));
      logger.info("PartType read from database with ID: {}", typeId);

      return partType;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering PartType with ID {}: {}", typeId, e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional
  @Override
  public boolean updatePartType (PartType data) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received data, before passing to repository/database:
    PartTypeValidation.validatePartType(data);

    // Attempt to update PartType in database:
    try {
      // Fetch the existing Entity from DB:
      PartType partType = partTypeRepository.findById(data.getTypeId()).orElseThrow(() -> new NotFoundException("No PartType found in database with matching id=" + data.getTypeId()));

      // Modify the database Entity locally:
      partType.setTypeDesc(data.getTypeDesc());
      partType.clearAnimalPartList();
      for (AnimalPart animalPart : data.getAnimalPartList()) {
        try {
          AnimalPart animalPartToAdd = animalPartRepository.findById(animalPart.getPartId()).orElseThrow(() -> new NotFoundException(""));
          partType.addAnimalPart(animalPartToAdd);
        } catch (NotFoundException ignored) {}
      }

      // Save the modified entity back to database:
      partType = partTypeRepository.save(partType);
      logger.info("PartType updated in database with ID: {}", partType.getTypeId());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update PartType in DB with desc: {}, Reason: {}", data.getTypeDesc(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating PartType with ID {}: {}", data.getTypeDesc(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional
  @Override
  public boolean removePartType (PartType data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    PartTypeValidation.validatePartType(data);

    try {
      // Attempt to delete the given PartType:
      partTypeRepository.delete(data);

      // Confirm that the entity has been removed:
      Optional<PartType> deletedPartType = partTypeRepository.findById(data.getTypeId());

      if(deletedPartType.isPresent()) {
        // PartType was not removed from database.
        logger.info("PartType was NOT deleted from database with ID: {}", data.getTypeId());
        throw new PersistenceException("PartType with ID " + data.getTypeId() + " was not deleted!");
      }

      logger.info("PartType deleted from database with ID: {}", data.getTypeId());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete PartType in DB with id: {}, Reason: {}", data.getTypeId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override
  public List<PartType> getAllPartTypes() throws PersistenceException {
    try {
      // Load all PartTypes from repository:
      return partTypeRepository.findAll();

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }
}
