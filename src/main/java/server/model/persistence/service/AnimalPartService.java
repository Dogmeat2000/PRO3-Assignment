package server.model.persistence.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.model.persistence.entities.*;
import server.model.persistence.repository.TrayToProductTransferRepository;
import server.model.validation.AnimalPartValidation;
import server.model.persistence.repository.AnimalPartRepository;
import server.model.persistence.repository.AnimalRepository;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.*;

@Service
public class AnimalPartService implements AnimalPartRegistryInterface
{
  private final AnimalPartRepository animalPartRepository;
  private static final Logger logger = LoggerFactory.getLogger(AnimalPartService.class);
  private final AnimalRepository animalRepository;
  private final TrayRegistryInterface trayRepository;
  private final PartTypeRegistryInterface partTypeRepository;
  private final ProductRegistryInterface productRepository;
  private final EntityManager entityManager;
  private final TrayToProductTransferRepository trayToProductTransferRepository;

  @Autowired
  public AnimalPartService(AnimalPartRepository animalPartRepository,
      AnimalRepository animalRepository,
      TrayRegistryInterface trayRepository,
      PartTypeRegistryInterface partTypeRepository,
      ProductRegistryInterface productRepository,
      EntityManager entityManager,
      TrayToProductTransferRepository trayToProductTransferRepository) {

    this.animalPartRepository = animalPartRepository;
    this.animalRepository = animalRepository;
    this.trayRepository = trayRepository;
    this.partTypeRepository = partTypeRepository;
    this.productRepository = productRepository;
    this.entityManager = entityManager;
    this.trayToProductTransferRepository = trayToProductTransferRepository;
  }


  @Transactional
  @Override public AnimalPart registerAnimalPart(AnimalPart data) throws PersistenceException, DataIntegrityViolationException {

    // Validate received data, before passing to repository/database:
    AnimalPartValidation.validateAnimalPart(data);

    // Override any set part_id, since the database automatically assigns this when creating:
    data.setPartId(0);

    // Attempt to add AnimalPart to DB:
    try {
      // First retrieve most recent versions of associated entities:
      Animal managedAnimal = animalRepository.findById(data.getAnimal().getId()).orElseThrow(() -> new NotFoundException("Animal not found"));
      data.setAnimal(entityManager.merge(managedAnimal));

      Tray managedTray = trayRepository.readTray(data.getTray().getTrayId());
      data.setTray(entityManager.merge(managedTray));

      PartType managedPartType = partTypeRepository.readPartType(data.getType().getTypeId());
      data.setType(entityManager.merge(managedPartType));

      Product managedProduct;
      if(data.getProduct() != null && data.getProduct().getProductId() > 0) {
        managedProduct = productRepository.readProduct(data.getProduct().getProductId());
        data.setProduct(entityManager.merge(managedProduct));

        // Check if there already exists proper Transfers between the given Tray and given Product:
        boolean transferExists = false;
        for (long id : data.getTray().getTransferIdList()) {
          if(data.getProduct().getTransferIdList().contains(id)) {
            // The given transfer ID exists in both Tray and Product.
            transferExists = true;
          }
        }

        if(!transferExists) {
          logger.info("No existing Transfer found for the given AnimalPart, Tray and Product. Creating a new transfer");
          // A transfer does not already exist. We must create one.
          TrayToProductTransfer newTransfer = new TrayToProductTransfer(0, data.getTray(), data.getProduct());
          newTransfer = trayToProductTransferRepository.save(newTransfer);

          // Update both the Tray and the Product:
          data.getTray().addTransfer(newTransfer);

          data.getProduct().addTransfer(newTransfer);
        }

      } else {
        data.setProduct(null);
      }


      // Register the AnimalPart:
      AnimalPart newAnimalPart = animalPartRepository.save(data);
      logger.info("AnimalPart added to DB with ID: {}", newAnimalPart.getPartId());

      return readAnimalPart(newAnimalPart.getPartId());

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to register AnimalPart in DB with weight: {}, Reason: {}", data.getWeight_kilogram(), e.getMessage());
      throw new DataIntegrityViolationException("Invalid AnimalPart provided. Incompatible with database!");

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while registering AnimalPart with ID {}: {}", data.getPartId(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override public AnimalPart readAnimalPart(long part_id) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received id, before passing to repository/database:
    AnimalPartValidation.validateId(part_id);

    // Attempt to read from DB:
    try {
      logger.info("Looking up AnimalPart with ID: {} in database...", part_id);

      // Causes the repository to query the database. If no match is found, an error is thrown immediately.
      AnimalPart animalPart = animalPartRepository.findById(part_id).orElseThrow(() -> new NotFoundException("No AnimalPart found in database with matching id=" + part_id));
      logger.info("AnimalPart read from database with ID: {}", part_id);

      if(animalPart.getProduct() != null) {
        // Transient transferId list embedded inside the embedded Product
        List<Tray> trayListFromProduct = new ArrayList<>();
        for (TrayToProductTransfer transferEmbedded : animalPart.getProduct().getTraySupplyJoinList()){
          trayListFromProduct.add(trayRepository.readTray(transferEmbedded.getTray().getTrayId()));
        }
        animalPart.getProduct().addAllTraysToTraySuppliersList(trayListFromProduct);
      }

      return animalPart;
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while reading AnimalPart with ID {}: {}", part_id, e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional(readOnly = true)
  @Override public List<AnimalPart> readAnimalPartsByAnimalId(long animalId) throws PersistenceException, NotFoundException, DataIntegrityViolationException {
    // Validate received id, before passing to repository/database:
    AnimalPartValidation.validateId(animalId);

    try {
      return animalPartRepository.findAnimalPartsByAnimal_animalId(animalId).orElseThrow(() -> new NotFoundException("No AnimalParts found in database associated with animal_ID: " + animalId));

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
      return animalPartRepository.findAnimalPartsByType_typeId(typeId).orElseThrow(() -> new NotFoundException("No AnimalParts found in database associated with type_ID: " + typeId));
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
      return animalPartRepository.findAnimalPartsByProduct_productId(productId).orElseThrow(() -> new NotFoundException("No AnimalParts found in database associated with product_id: " + productId));
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
      return animalPartRepository.findAnimalPartsByTray_trayId(trayId).orElseThrow(() -> new NotFoundException("No AnimalParts found in database associated with tray_id: " + trayId));
    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional
  @Override public boolean updateAnimalPart(AnimalPart data) throws NotFoundException, DataIntegrityViolationException, PersistenceException {
    // Validate received data, before passing to repository/database:
    AnimalPartValidation.validateAnimalPart(data);

    // Attempt to update AnimalPart in database:
    try {
      // Fetch existing entity from DB:
      AnimalPart oldAnimalPart = animalPartRepository.findById(data.getPartId()).orElseThrow(() -> new NotFoundException("No AnimalPart found in database with matching id=" + data.getPartId()));

      // Retrieve most recent repository versions of associated entities, for the modified AnimalPart:
      Animal managedModifiedAnimal = entityManager.merge(animalRepository.findById(data.getAnimal().getId()).orElseThrow(() -> new NotFoundException("Animal not found")));
      Tray managedModifiedTray = entityManager.merge(trayRepository.readTray(data.getTray().getTrayId()));
      PartType managedModifiedPartType = entityManager.merge(partTypeRepository.readPartType(data.getType().getTypeId()));

      Product managedModifiedProduct = null;
      if(data.getProduct() != null && data.getProduct().getProductId() > 0)
        managedModifiedProduct = productRepository.readProduct(data.getProduct().getProductId());

      // Modify the database Entity locally:
      oldAnimalPart.setWeight_kilogram(data.getWeight_kilogram());
      oldAnimalPart.setAnimal(managedModifiedAnimal);
      oldAnimalPart.setTray(managedModifiedTray);
      oldAnimalPart.setType(managedModifiedPartType);
      oldAnimalPart.setProduct(managedModifiedProduct);

      // Save modified AnimalPart to Db:
      oldAnimalPart = animalPartRepository.save(oldAnimalPart);
      logger.info("AnimalPart updated in database with ID: {}", oldAnimalPart.getPartId());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update AnimalPart in DB with id: {}, Reason: {}", data.getPartId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating AnimalPart with ID {}: {}", data.getPartId(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional
  @Override public boolean removeAnimalPart(AnimalPart data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    AnimalPartValidation.validateAnimalPart(data);

    try {
      // Attempt to delete the given AnimalPart:
      animalPartRepository.deleteAnimalPartByPartId(data.getPartId());

      // Confirm that the entity has been removed:
      Optional<AnimalPart> deletedAnimalPart = animalPartRepository.findById(data.getPartId());

      if(deletedAnimalPart.isPresent()) {
        // AnimalPart was not removed from database.
        logger.info("AnimalPart was NOT deleted from database with ID: {}", data.getPartId());
        throw new PersistenceException("AnimalPart with ID " + data.getPartId() + " was not deleted!");
      }

      logger.info("AnimalPart deleted from database with ID: {}", data.getPartId());

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to delete AnimalPart in DB with id: {}, Reason: {}", data.getPartId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional (readOnly = true)
  @Override public List<AnimalPart> getAllAnimalParts() throws PersistenceException {
    try {
      return animalPartRepository.findAll();

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred: {}", e.getMessage());
      throw new PersistenceException(e);
    }
  }
}
