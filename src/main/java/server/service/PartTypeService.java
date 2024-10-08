package server.service;

import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.repository.PartTypeRepository;
import shared.model.entities.PartType;
import shared.model.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PartTypeService implements PartTypeRegistryInterface
{
  private final PartTypeRepository partTypeRepository;
  private final Map<Long, PartType> partTypeCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(PartTypeService.class);

  @Autowired
  public PartTypeService(PartTypeRepository partTypeRepository) {
    this.partTypeRepository = partTypeRepository;
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override
  public PartType registerPartType (PartType data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    validateDesc(data.getTypeDesc());

    // Attempt to add PartType to DB:
    try {
      PartType newPartType = partTypeRepository.save(data);
      logger.info("PartType added to DB with ID: {}", data.getTypeId());

      // Attempt to add PartType to local cache:
      partTypeCache.put(newPartType.getTypeId(), newPartType);
      logger.info("PartType saved to local cache with ID: {}", newPartType.getTypeId());

      return newPartType;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register PartType in DB with weight: {}, Reason: {}", data.getTypeDesc(), e.getMessage());
      throw new DataIntegrityViolationException("Invalid PartType provided. Incompatible with database!");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering PartType with ID {}: {}", data.getTypeId(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Override
  public PartType readPartType (long typeId) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received id, before passing to repository/database:
    validateId(typeId);

    // Attempt to read PartType from local cache first:
    if(partTypeCache.containsKey(typeId)) {
      logger.info("PartType read from local cache with ID: {}", typeId);
      return partTypeCache.get(typeId);
    }

    // PartType not found in local cache. Attempt to read from DB:
    try {
      logger.info("PartType not found in local cache with ID: {}. Looking up in database...", typeId);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      PartType partType = partTypeRepository.findById(typeId).orElseThrow(() -> new NotFoundException("No PartType found in database with matching id=" + typeId));

      logger.info("PartType read from database with ID: {}", typeId);

      // Add found PartType to local cache, to improve performance next time PartType is requested.
      partTypeCache.put(partType.getTypeId(), partType);
      logger.info("PartType added to local cache with ID: {}", partType.getTypeId());
      return partType;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering PartType with ID {}: {}", typeId, e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override
  public boolean updatePartType (PartType data) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received data, before passing to repository/database:
    validatePartType(data);

    // Attempt to update PartType in database:
    try {
      // Fetch the existing Entity from DB:
      PartType partType = partTypeRepository.findById(data.getTypeId()).orElseThrow(() -> new NotFoundException("No PartType found in database with matching id=" + data.getTypeId()));

      // Modify the database Entity locally:
      partType.setTypeDesc(data.getTypeDesc());
      partType.getPartList().clear();
      partType.getPartList().addAll(data.getPartList());

      // Save the modified entity back to database:
      partTypeRepository.save(partType);
      logger.info("PartType updated in database with ID: {}", partType.getTypeId());

      // Attempt to add PartType to local cache:
      partTypeCache.put(partType.getTypeId(), partType);
      logger.info("PartType saved to local cache with ID: {}", partType.getTypeId());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update PartType in DB with desc: {}, Reason: {}", data.getTypeDesc(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating PartType with ID {}: {}", data.getTypeDesc(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override
  public boolean removePartType (PartType data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    validatePartType(data);

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
      // PartType was removed from database. Now ensure that is it also removed from the local cache:
      partTypeCache.remove(data.getTypeId());
      logger.info("PartType deleted from local cache with ID: {}", data.getTypeId());
      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete PartType in DB with id: {}, Reason: {}", data.getTypeId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Override
  public List<PartType> getAllPartTypes() throws PersistenceException {

    try {
      List<PartType> partTypes = partTypeRepository.findAll();

      // Add all the found PartTypes to local cache, to improve performance next time a PartType is requested.
      partTypeCache.clear();
      for (PartType partType : partTypes) {
        if(partType != null)
          partTypeCache.put(partType.getTypeId(), partType);
      }
      logger.info("Added all PartTypes from Database to Local Cache");
      return partTypes;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  private void validatePartType(PartType partType) throws DataIntegrityViolationException {
    // PartType cannot be null:
    if(partType == null)
      throw new DataIntegrityViolationException("PartType is null");

    // Validate typeId:
    validateId(partType.getTypeId());

    // Desc weight must not be empty, null or blank:
    validateDesc(partType.getTypeDesc());

    // Validation passed:
  }


  private void validateId(long typeId) throws DataIntegrityViolationException {
    // Animal_id must be larger than 0:
    if(typeId <= 0)
      throw new DataIntegrityViolationException("typeId is invalid (0 or less)");

    // Validation passed:
  }


  private void validateDesc(String desc) throws DataIntegrityViolationException {
    // Animal weight must be larger than 0:
    if(desc == null || desc.isEmpty() || desc.isBlank())
      throw new DataIntegrityViolationException("desc is empty or blank");

    // Validation passed:
  }
}
