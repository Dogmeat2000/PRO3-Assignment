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
  public AnimalPartService(AnimalPartRepository animalPartRepository, AnimalRepository animalRepository, TrayRegistryInterface trayRepository, PartTypeRegistryInterface partTypeRepository,
      ProductRegistryInterface productRepository, EntityManager entityManager, TrayToProductTransferRepository trayToProductTransferRepository) {
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

      Product managedProduct = null;
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
          System.out.println("\n\nCREATED NEW TRANSFER: " + newTransfer); // TODO: DELETE LINE

          // Update both the Tray and the Product:
          data.getTray().addTransfer(newTransfer);
          //managedTray = trayRepository.readTray(data.getTray().getTrayId());
          //data.setTray(entityManager.merge(managedTray));

          data.getProduct().addTransfer(newTransfer);
          //managedProduct = productRepository.readProduct(data.getProduct().getProductId());
          //data.setProduct(entityManager.merge(managedProduct));
        }

      } else {
        data.setProduct(null);
      }


      // Register the AnimalPart:
      AnimalPart newAnimalPart = animalPartRepository.save(data);
      logger.info("AnimalPart added to DB with ID: {}", newAnimalPart.getPartId());

      // Attempt to add AnimalPart to local cache:
      //animalPartCache.put(newAnimalPart.getPart_id(), newAnimalPart);
      //logger.info("AnimalPart saved to local cache with ID: {}", newAnimalPart.getPart_id());

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

      System.out.println("Read this AnimalPart from db: " + animalPart);

      // Read the proper associated data types, for full ORM behavior:
      /*if(animalPart.getAnimal() != null)
        animalPart.setAnimal(animalRepository.findById(animalPart.getAnimal().getId()).orElseThrow(() -> new PersistenceException("Exception occurred")));

      if(animalPart.getType() != null)
        animalPart.setType(partTypeRepository.readPartType(animalPart.getType().getTypeId()));

      if(animalPart.getTray() != null)
        animalPart.setTray(trayRepository.readTray(animalPart.getTray().getTrayId()));

      if(animalPart.getProduct() != null)
        animalPart.setProduct(productRepository.readProduct(animalPart.getProduct().getProductId()));*/

      System.out.println("Read this AnimalPart from db 2: " + animalPart);

      // Populate transient AnimalPart data:
      // Transient AnimalPartID list embedded inside the embedded Animal
      /*List<Long> animalPartIdListFromAnimal = new ArrayList<>();
      for (AnimalPart animalPartEmbedded : animalPart.getAnimal().getAnimalPartList())
        animalPartIdListFromAnimal.add(animalPartEmbedded.getPart_id());
      animalPart.getAnimal().setAnimalPartIdList(animalPartIdListFromAnimal);*/

      // Transient AnimalPartID list embedded inside the embedded PartType
      /*List<Long> animalPartIdListFromPartType = new ArrayList<>();
      for (AnimalPart animalPartEmbedded : animalPart.getType().getAnimalPartList())
        animalPartIdListFromPartType.add(animalPartEmbedded.getPart_id());
      animalPart.getType().setAnimalPartIdList(animalPartIdListFromPartType);*/

      // Transient PartType embedded inside the embedded Tray
      //animalPart.getTray().setTrayType(partTypeRepository.readPartType(animalPart.getTray().getTrayType().getTypeId()));

      // Transient AnimalPartID list embedded inside the embedded Tray
      /*List<Long> animalPartIdListFromTray = new ArrayList<>();
      for (AnimalPart animalPartEmbedded : animalPart.getTray().getAnimalPartList())
        animalPartIdListFromTray.add(animalPartEmbedded.getPart_id());
      animalPart.getTray().setAnimalPartIdList(animalPartIdListFromTray);*/

      // Transient transferId list embedded inside the embedded Tray
      /*List<Long> transferIdListFromTray = new ArrayList<>();
      for (TrayToProductTransfer transferEmbedded : animalPart.getTray().getTransferList())
        transferIdListFromTray.add(transferEmbedded.getTransferId());
      animalPart.getTray().setTransferIdList(transferIdListFromTray);*/

      if(animalPart.getProduct() != null) {
        // Transient transferId list embedded inside the embedded Product
        //List<Long> transferIdListFromProduct = new ArrayList<>();
        List<Tray> trayListFromProduct = new ArrayList<>();
        for (TrayToProductTransfer transferEmbedded : animalPart.getProduct().getTraySupplyJoinList()){
          //transferIdListFromProduct.add(transferEmbedded.getTransferId());
          trayListFromProduct.add(trayRepository.readTray(transferEmbedded.getTray().getTrayId()));
        }
        //animalPart.getProduct().setTransferIdList(transferIdListFromProduct);
        animalPart.getProduct().addAllTraysToTraySuppliersList(trayListFromProduct);

        // Transient AnimalPartID list embedded inside the embedded Product
        /*List<Long> animalPartIdListFromProduct = new ArrayList<>();
        for (AnimalPart animalPartEmbedded : animalPart.getProduct().getAnimalPartList())
          animalPartIdListFromProduct.add(animalPartEmbedded.getPart_id());
        animalPart.getProduct().setAnimalPartIdList(animalPartIdListFromProduct);*/
      }

      System.out.println("Read this AnimalPart from db 3: " + animalPart);

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
      //data.setAnimal(managedModifiedAnimal);

      Tray managedModifiedTray = entityManager.merge(trayRepository.readTray(data.getTray().getTrayId()));
      //data.setTray(managedModifiedTray);

      PartType managedModifiedPartType = entityManager.merge(partTypeRepository.readPartType(data.getType().getTypeId()));
      //data.setType(managedModifiedPartType);

      Product managedModifiedProduct = null;
      if(data.getProduct() != null && data.getProduct().getProductId() > 0)
        managedModifiedProduct = productRepository.readProduct(data.getProduct().getProductId());
      //data.setProduct(managedModifiedProduct);

      System.out.println("\n\nAttempting to update AnimalPart Weight from '" + oldAnimalPart.getWeight_kilogram() + "' to '" + data.getWeight_kilogram() + "\n\n");

      // Modify the database Entity locally:
      oldAnimalPart.setWeight_kilogram(data.getWeight_kilogram());
      oldAnimalPart.setAnimal(managedModifiedAnimal);
      oldAnimalPart.setTray(managedModifiedTray);
      oldAnimalPart.setType(managedModifiedPartType);
      oldAnimalPart.setProduct(managedModifiedProduct);


      System.out.println("Modified AnimalPart Before Save has weight of: " + oldAnimalPart.getWeight_kilogram() + "\n\n");

      // Save modified AnimalPart to Db:
      System.out.println("SAVING!!!!");
      oldAnimalPart = animalPartRepository.save(oldAnimalPart);
      System.out.println("SAVED!!!!");
      logger.info("AnimalPart updated in database with ID: {}", oldAnimalPart.getPartId());

      System.out.println("\n\nSaved AnimalPart was: " + animalPartRepository.findById(oldAnimalPart.getPartId()));

      // Check if there are any of the associated entities, that changed. If so, update the old/void ones, to remove this AnimalPart from their associations:
      // TODO: Should not be necessary since Cascade.All is set in entity.
      // Check associated Animal:
      /*if(modifiedAnimalPart.getAnimal().getId() != oldAnimalPart.getAnimal().getId()) {
        //Old Animal no longer has association to this AnimalPart. Remove it:
        oldAnimalPart.getAnimal().removeAnimalPart(oldAnimalPart);
        animalRepository.save(oldAnimalPart.getAnimal());
      }

      // Check associated PartType:
      if(modifiedAnimalPart.getType().getTypeId() != oldAnimalPart.getType().getTypeId()) {
        //Old PartType no longer has association to this AnimalPart. Remove it:
        oldAnimalPart.getType().removeAnimalPart(oldAnimalPart);
        partTypeRepository.updatePartType(oldAnimalPart.getType());
      }

      // Check associated Tray:
      if(modifiedAnimalPart.getTray().getTrayId() != oldAnimalPart.getTray().getTrayId()) {
        //Old Tray no longer has association to this AnimalPart. Remove it:
        oldAnimalPart.getType().removeAnimalPart(oldAnimalPart);
        trayRepository.updateTray(oldAnimalPart.getTray());
      }

      // Check associated Tray:
      if(oldAnimalPart.getProduct() != null && oldAnimalPart.getProduct().getProductId() > 0) {
        if(modifiedAnimalPart.getProduct().getProductId() != oldAnimalPart.getProduct().getProductId()) {
          //Old Product no longer has association to this AnimalPart. Remove it:
          oldAnimalPart.getProduct().removeAnimalPart(oldAnimalPart);
          productRepository.updateProduct(oldAnimalPart.getProduct());
        }
      }*/

      return true;

    } catch (IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Unable to update AnimalPart in DB with id: {}, Reason: {}", data.getPartId(), e.getMessage());
      throw new DataIntegrityViolationException(e.getMessage());

    } catch (PersistenceException e) {
      logger.error("Persistence exception occurred while updating AnimalPart with ID {}: {}", data.getPartId(), e.getMessage());
      throw new PersistenceException(e);
    }
  }


  @Transactional // @Transactional is specified, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Override public boolean removeAnimalPart(AnimalPart data) throws PersistenceException, DataIntegrityViolationException {
    // Validate received data, before passing to repository/database:
    AnimalPartValidation.validateAnimalPart(data);

    // Read most recent version of AnimalPart:
    //AnimalPart animalPartToRemove = readAnimalPart(data.getPartId());

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

      // Update associated entities:
      // Parent Animal:
      /*Animal parentAnimal = data.getAnimal();
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
      }*/

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
