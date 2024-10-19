package server.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.model.validation.AnimalPartValidation;
import server.repository.*;
import shared.model.entities.*;
import shared.model.exceptions.NotFoundException;

import java.util.*;

@Service
public class AnimalPartService implements AnimalPartRegistryInterface
{
  private final AnimalPartRepository animalPartRepository;
  private final Map<Long, AnimalPart> animalPartCache = new HashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(AnimalPartService.class);
  private final AnimalRegistryInterface animalRepository;
  private final TrayRegistryInterface trayRepository;
  private final PartTypeRegistryInterface partTypeRepository;
  private final ProductRegistryInterface productRepository;
  private final EntityManager entityManager;


  @Autowired
  public AnimalPartService(AnimalPartRepository animalPartRepository, AnimalRegistryInterface animalRepository, TrayRegistryInterface trayRepository, PartTypeRegistryInterface partTypeRepository,
      ProductRegistryInterface productRepository, EntityManager entityManager) {
    this.animalPartRepository = animalPartRepository;
    this.animalRepository = animalRepository;
    this.trayRepository = trayRepository;
    this.partTypeRepository = partTypeRepository;
    this.productRepository = productRepository;
    this.entityManager = entityManager;
  }


  @Transactional
  @Override public AnimalPart registerAnimalPart(AnimalPart data) throws PersistenceException, DataIntegrityViolationException {

    // Validate received data, before passing to repository/database:
    AnimalPartValidation.validateAnimalPart(data);

    // Override any set part_id, since the database automatically assigns this when creating:
    data.setPart_id(0);

    // Attempt to add AnimalPart to DB:
    try {
      // First retrieve most recent versions of associated entities:
      Animal managedAnimal = entityManager.merge(animalRepository.readAnimal(data.getAnimal().getId()));
      data.setAnimal(managedAnimal);

      Tray managedTray = entityManager.merge(trayRepository.readTray(data.getTray().getTrayId()));
      data.setTray(managedTray);

      PartType managedPartType = entityManager.merge(partTypeRepository.readPartType(data.getType().getTypeId()));
      data.setType(managedPartType);

      Product managedProduct = null;
      if(data.getProduct() != null && data.getProduct().getProductId() > 0)
        managedProduct = productRepository.readProduct(data.getProduct().getProductId());
      data.setProduct(managedProduct);

      // Register the AnimalPart:
      AnimalPart newAnimalPart = animalPartRepository.save(data);
      logger.info("AnimalPart added to DB with ID: {}", newAnimalPart.getPart_id());

      // Update associated entities:
      // Parent Animal:
      Animal parentAnimal = data.getAnimal();
      if(!parentAnimal.getPartList().contains(newAnimalPart))
        parentAnimal.addAnimalPart(newAnimalPart);
      animalRepository.updateAnimal(parentAnimal);

      // Parent Tray:
      Tray parentTray = data.getTray();
      if(!parentTray.getContents().contains(newAnimalPart)) {
        parentTray.addAnimalPart(newAnimalPart);
        parentTray.setWeight_kilogram(parentTray.getWeight_kilogram().add(newAnimalPart.getWeight_kilogram()));
      }
      trayRepository.updateTray(trayRepository.readTray(parentTray.getTrayId()));

      // Parent PartType:
      PartType parentPartType = data.getType();
      if(!parentPartType.getPartList().contains(newAnimalPart))
        parentPartType.addAnimalPart(newAnimalPart);
      partTypeRepository.updatePartType(parentPartType);

      // Parent Product:
      if(data.getProduct() != null && data.getProduct().getProductId() != 0) {
        Product parentProduct = data.getProduct();
        if(!parentProduct.getContentList().contains(newAnimalPart))
          parentProduct.addAnimalPart(newAnimalPart);
        productRepository.updateProduct(parentProduct);
      }

      // Read the most recent AnimalPart version from repository:
      AnimalPart finalAnimalPart = readAnimalPart(newAnimalPart.getPart_id());

      // Attempt to add AnimalPart to local cache:
      animalPartCache.put(newAnimalPart.getPart_id(), newAnimalPart);
      logger.info("AnimalPart saved to local cache with ID: {}", newAnimalPart.getPart_id());

      return finalAnimalPart;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register AnimalPart in DB with weight: {}, Reason: {}", data.getWeight_kilogram(), e.getMessage());
      throw new DataIntegrityViolationException("Invalid AnimalPart provided. Incompatible with database!");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering AnimalPart with ID {}: {}", data.getPart_id(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override public AnimalPart readAnimalPart(long part_id) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received id, before passing to repository/database:
    AnimalPartValidation.validateId(part_id);

    // Attempt to read AnimalPart from local cache first:
    if(animalPartCache.containsKey(part_id)) {
      logger.info("AnimalPart read from local cache with ID: {}", part_id);
      return animalPartCache.get(part_id);
    }

    // AnimalPart not found in local cache. Attempt to read from DB:
    try {
      logger.info("AnimalPart not found in local cache with ID: {}. Looking up in database...", part_id);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      AnimalPart animalPart = animalPartRepository.findById(part_id).orElseThrow(() -> new NotFoundException("No AnimalPart found in database with matching id=" + part_id));

      logger.info("AnimalPart read from database with ID: {}", part_id);

      // Add found AnimalPart to local cache, to improve performance next time AnimalPart is requested.
      animalPartCache.put(animalPart.getPart_id(), animalPart);
      logger.info("AnimalPart added to local cache with ID: {}", part_id);
      return animalPart;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering AnimalPart with ID {}: {}", part_id, e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional(readOnly = true)
  @Override public List<AnimalPart> readAnimalPartsByAnimalId(long animalId) throws PersistenceException, NotFoundException, DataIntegrityViolationException {
    // Validate received id, before passing to repository/database:
    AnimalPartValidation.validateId(animalId);

    try {
      List<AnimalPart> animalParts = animalPartRepository.findAnimalPartsByAnimal_animalId(animalId).orElseThrow(() -> new NotFoundException("No AnimalParts found in database associated with animal_ID: " + animalId));

      // Add all the found AnimalParts to local cache, to improve performance next time an AnimalPart is requested.
      for (AnimalPart animalPart : animalParts) {
        if(animalPart != null)
          animalPartCache.put(animalPart.getPart_id(), animalPart);

      }
      logger.info("Added all AnimalParts associated with animalId '" + animalId + "' to Local Cache");
      return animalParts;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional(readOnly = true)
  @Override public List<AnimalPart> readAnimalPartsByPartTypeId(long typeId) throws PersistenceException, NotFoundException, DataIntegrityViolationException {
    // Validate received id, before passing to repository/database:
    AnimalPartValidation.validateId(typeId);

    try {
      List<AnimalPart> animalParts = animalPartRepository.findAnimalPartsByType_typeId(typeId).orElseThrow(() -> new NotFoundException("No AnimalParts found in database associated with type_ID: " + typeId));

      // Add all the found AnimalParts to local cache, to improve performance next time an AnimalPart is requested.
      for (AnimalPart animalPart : animalParts) {
        if(animalPart != null)
          animalPartCache.put(animalPart.getPart_id(), animalPart);

      }
      logger.info("Added all AnimalParts associated with type_ID '" + typeId + "' to Local Cache");
      return animalParts;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional(readOnly = true)
  @Override public List<AnimalPart> readAnimalPartsByProductId(long productId) throws PersistenceException, NotFoundException, DataIntegrityViolationException {
    // Validate received id, before passing to repository/database:
    AnimalPartValidation.validateId(productId);

    try {
      List<AnimalPart> animalParts = animalPartRepository.findAnimalPartsByProduct_productId(productId).orElseThrow(() -> new NotFoundException("No AnimalParts found in database associated with product_id: " + productId));

      // Add all the found AnimalParts to local cache, to improve performance next time an AnimalPart is requested.
      for (AnimalPart animalPart : animalParts) {
        if(animalPart != null)
          animalPartCache.put(animalPart.getPart_id(), animalPart);

      }
      logger.info("Added all AnimalParts associated with product_id '" + productId + "' to Local Cache");
      return animalParts;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional(readOnly = true)
  @Override public List<AnimalPart> readAnimalPartsByTrayId(long trayId) throws PersistenceException, NotFoundException, DataIntegrityViolationException {
    // Validate received id, before passing to repository/database:
    AnimalPartValidation.validateId(trayId);

    try {
      List<AnimalPart> animalParts = animalPartRepository.findAnimalPartsByTray_trayId(trayId).orElseThrow(() -> new NotFoundException("No AnimalParts found in database associated with tray_id: " + trayId));

      // Add all the found AnimalParts to local cache, to improve performance next time an AnimalPart is requested.
      for (AnimalPart animalPart : animalParts) {
        if(animalPart != null)
          animalPartCache.put(animalPart.getPart_id(), animalPart);

      }
      logger.info("Added all AnimalParts associated with tray_id '" + trayId + "' to Local Cache");
      return animalParts;

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean updateAnimalPart(AnimalPart oldData, AnimalPart newData) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // TODO: Not finished implemented yet.

    // Validate received data, before passing to repository/database:
    AnimalPartValidation.validateAnimalPart(oldData);
    AnimalPartValidation.validateAnimalPart(newData);

    // Attempt to update AnimalPart in database:
    try {
      // Fetch the existing Entity from DB:
      AnimalPart oldAnimalPart = animalPartRepository.findById(oldData.getPart_id()).orElseThrow(() -> new NotFoundException("No AnimalPart found in database with matching id=" + oldData.getPart_id()));

      // Modify the database Entity locally:
      AnimalPart newAnimalPart = oldAnimalPart.copy();
      newAnimalPart.setWeight_kilogram(newData.getWeight_kilogram());
      newAnimalPart.setAnimal(newData.getAnimal());
      newAnimalPart.setType(newData.getType());
      newAnimalPart.setTray(newData.getTray());
      newAnimalPart.setProduct(newData.getProduct());

      // Save modified AnimalPart to Db:
      newAnimalPart = animalPartRepository.save(newAnimalPart);
      logger.info("AnimalPart updated in database with ID: {}", newAnimalPart.getPart_id());


      // Attempt to update all associated Animal entities:
     /* Animal oldAnimal = oldAnimalPart.getAnimal();
      oldAnimal.getPartList().remove(oldAnimalPart);
      animalRepository.updateAnimal(oldAnimal);

      Animal newAnimal = newAnimalPart.getAnimal();
      newAnimal.getPartList().add(newAnimalPart);
      animalRepository.updateAnimal(newAnimal);


      // Attempt to update all associated Tray entities:
      Tray oldTray = oldAnimalPart.getTray();
      oldTray.removeAnimalPart(oldAnimalPart);
      trayRepository.updateTray(oldTray);

      Tray newTray = newAnimalPart.getTray();
      newTray.removeAnimalPart(oldAnimalPart);
      trayRepository.updateTray(newTray);


      // Attempt to update all associated PartType entities:
      PartType oldPartType = oldAnimalPart.getType();
      oldPartType.getPartList().remove(oldAnimalPart);
      partTypeRepository.updatePartType(oldPartType);

      PartType newPartType = newAnimalPart.getType();
      newPartType.getPartList().add(newAnimalPart);
      partTypeRepository.updatePartType(newPartType);


      // Attempt to update all associated Product entities:
      if(oldAnimalPart.getProduct() != null) {
        Product oldProduct = oldAnimalPart.getProduct();
        oldProduct.getContentList().remove(oldAnimalPart);
        productRepository.updateProduct(oldProduct);
      }*/

      if(newAnimalPart.getProduct() != null) {
        Product newProduct = newAnimalPart.getProduct();
        newProduct.getContentList().remove(newAnimalPart);
        productRepository.updateProduct(newProduct);
      }

      // Reset the local cache, since updates done inside the other service classes might not be downloaded from the Database:
      animalPartCache.clear();

      // Attempt to add new AnimalPart to local cache:
      animalPartCache.put(newAnimalPart.getPart_id(), newAnimalPart);
      logger.info("AnimalPart saved to local cache with ID: {}", newAnimalPart.getPart_id());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update AnimalPart in DB with id: {}, Reason: {}", oldData.getPart_id(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating AnimalPart with ID {}: {}", oldData.getPart_id(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean removeAnimalPart(AnimalPart data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    AnimalPartValidation.validateAnimalPart(data);

    // Read most recent version of AnimalPart:
    AnimalPart animalPartToRemove = readAnimalPart(data.getPart_id());

    try {
      // Attempt to delete the given AnimalPart:
      animalPartRepository.delete(data);

      // Confirm that the entity has been removed:
      Optional<AnimalPart> deletedAnimalPart = animalPartRepository.findById(data.getPart_id());

      if(deletedAnimalPart.isPresent()) {
        // AnimalPart was not removed from database.
        logger.info("AnimalPart was NOT deleted from database with ID: {}", data.getPart_id());
        throw new PersistenceException("AnimalPart with ID " + data.getPart_id() + " was not deleted!");
      }

      logger.info("AnimalPart deleted from database with ID: {}", data.getPart_id());
      // AnimalPart was removed from database. Now ensure that is it also removed from the local cache:
      animalPartCache.remove(data.getPart_id());
      logger.info("AnimalPart deleted from local cache with ID: {}", data.getPart_id());

      // Update associated entities:
      // Parent Animal:
      Animal parentAnimal = data.getAnimal();
      if(parentAnimal.getPartList().contains(animalPartToRemove))
        parentAnimal.removeAnimalPart(animalPartToRemove);
      animalRepository.updateAnimal(parentAnimal);

      // Parent Tray:
      Tray parentTray = data.getTray();
      if(!parentTray.getContents().contains(animalPartToRemove)) {
        parentTray.removeAnimalPart(animalPartToRemove);
        parentTray.setWeight_kilogram(parentTray.getWeight_kilogram().subtract(animalPartToRemove.getWeight_kilogram()));
      }
      trayRepository.updateTray(trayRepository.readTray(parentTray.getTrayId()));

      // Parent PartType:
      PartType parentPartType = data.getType();
      if(!parentPartType.getPartList().contains(animalPartToRemove))
        parentPartType.removeAnimalPart(animalPartToRemove);
      partTypeRepository.updatePartType(parentPartType);

      // Parent Product:
      if(data.getProduct() != null) {
        Product parentProduct = data.getProduct();
        if(!parentProduct.getContentList().contains(animalPartToRemove))
          parentProduct.removeAnimalPart(animalPartToRemove);
        productRepository.updateProduct(parentProduct);
      }

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete AnimalPart in DB with id: {}, Reason: {}", data.getPart_id(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override public List<AnimalPart> getAllAnimalParts() throws PersistenceException {
    try {
      List<AnimalPart> animalParts = animalPartRepository.findAll();

      // Add all the found AnimalParts to local cache, to improve performance next time an AnimalPart is requested.
      animalPartCache.clear();
      for (AnimalPart animalPart : animalParts) {
        if(animalPart != null){
          animalPartCache.put(animalPart.getPart_id(), animalPart);
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
