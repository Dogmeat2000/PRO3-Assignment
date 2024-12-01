package Client.Station3_Packing.model;

import Client.common.model.BaseModel;
import Client.Station3_Packing.network.services.gRPC.ProductRegistrationSystem;
import Client.common.model.QueueManager;
import Client.common.services.gRPC.TrayRegistrationSystem;
import shared.model.dto.AnimalPartDto;
import shared.model.dto.ProductDto;
import shared.model.dto.TrayDto;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.ArrayList;
import java.util.List;

public class Station3Model implements BaseModel
{
  private final List<AnimalPartDto> receivedAnimalPartsList;
  private final List<TrayDto> trayDtoCache = new ArrayList<>();
  private final ProductRegistrationSystem productRegistrationSystem;
  private final TrayRegistrationSystem trayRegistrationSystem;
  private final QueueManager queueManager;

  public Station3Model(ProductRegistrationSystem productRegistrationSystem,
      QueueManager queueManager,
      TrayRegistrationSystem trayRegistrationSystem) {
    this.receivedAnimalPartsList = new ArrayList<>();
    this.productRegistrationSystem = productRegistrationSystem;
    this.queueManager = queueManager;
    this.trayRegistrationSystem = trayRegistrationSystem;
  }


  public void registerNewProduct(List<AnimalPartDto> animalPartContentList, List<TrayDto> receivedPartsFromTrayList) throws CreateFailedException {
    // TODO: Initial validation of data

    // Create necessary data:
    List<Long> animalPartIdList = new ArrayList<>();
    for (AnimalPartDto animalPartDto : animalPartContentList) {
      animalPartIdList.add(animalPartDto.getPartId());
    }

    List<Long> trayIdList = new ArrayList<>();
    for (TrayDto trayDto : receivedPartsFromTrayList) {
      trayIdList.add(trayDto.getTrayId());
    }

    // Create a new ProductDto:
    ProductDto newProduct = new ProductDto(0,
        animalPartIdList,
        trayIdList,
        null
    );

    // Hand over to responsible Queue:
    queueManager.addToUnregisteredQueue(newProduct);

    // Remove the associated AnimalParts from the received AnimalParts Queue:
    List<AnimalPartDto> dtosToRemove = new ArrayList<>();
    for (AnimalPartDto animalPartDto : receivedAnimalPartsList) {
      for (AnimalPartDto nestedDto : animalPartContentList){
        if(nestedDto.getPartId() == animalPartDto.getPartId()){
          dtosToRemove.add(animalPartDto);
        }
      }
    }
    receivedAnimalPartsList.removeAll(dtosToRemove);
  }

  public ProductDto readProduct(long productId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    try {
      return productRegistrationSystem.readProduct(productId);
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading Product '" + productId + "'. Database server could not be reached. Please try again later...");
      return null;
    }
  }

  public void updateProduct(ProductDto data) throws UpdateFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Retrieve the un-modified Product:
    ProductDto unmodifiedDto = this.readProduct(data.getProductId());

    // Attempt to update, using gRPC connection:
    productRegistrationSystem.updateProduct(data);

    // If associated AnimalPart has changed, we need to remove that from the list of valid AnimalParts:
    for (long animalPartId : data.getAnimalPartIdList()){
      if(!unmodifiedDto.getAnimalPartIdList().contains(animalPartId)){
        // This AnimalPart is a new addition. Remove it from the local queue/list:
        this.removeEntityFromReceivedEntityList(animalPartId);
      }
    }
  }

  public boolean removeProduct(long productId) throws DeleteFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Attempt to delete, using gRPC connection:
    try {
      return productRegistrationSystem.removeProduct(productId);
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error removing Product '" + productId + "'. Database server could not be reached. Please try again later...");
      return false;
    }
  }

  public List<ProductDto> getAllProducts() throws NotFoundException {
    // Attempt to read all, using gRPC connection:
    try {
      return productRegistrationSystem.getAllProducts();
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading all Products. Database server could not be reached. Please try again later...");
      return new ArrayList<>();
    }
  }

  @Override public AnimalPartDto readEntityFromReceivedEntityList(long animalPartId) {
    List<AnimalPartDto> registeredAnimalParts = new ArrayList<>(receivedAnimalPartsList);
    for (AnimalPartDto animalPart : registeredAnimalParts) {
      if(animalPart.getPartId() == animalPartId) {
        return animalPart;
      }
    }
    return null;
  }

  @Override public void addEntityToReceivedEntityList(Object obj) {
    if(obj instanceof AnimalPartDto)
      receivedAnimalPartsList.add((AnimalPartDto) obj);
  }

  public List<AnimalPartDto> getAllEntitiesFromReceivedEntityList() {
    return receivedAnimalPartsList;
  }

  private boolean removeEntityFromReceivedEntityList(long id){
    for (AnimalPartDto animalPart : getAllEntitiesFromReceivedEntityList()) {
      if(animalPart.getAnimalId() == id) {
        receivedAnimalPartsList.remove(animalPart);
        return true;
      }
    }
    return false;
  }

  private boolean removeMultipleEntitiesFromReceivedEntityList(List<Long> animalPartIds) {
    boolean success = true;
    for (long id : animalPartIds) {
      if(!removeEntityFromReceivedEntityList(id))
        success = false;
    }
    return success;
  }


  // Tray related operations:
  public TrayDto readTray(long trayId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    try {
      return trayRegistrationSystem.readTray(trayId);
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading Tray '" + trayId + "'. Database server could not be reached. Reading data from local cache instead.");
      for (TrayDto trayDto : trayDtoCache) {
        if(trayDto.getTrayId() == trayId) {
          return trayDto;
        }
      }
      return null;
    }
  }

  public List<TrayDto> getAllTrays(){
    // Attempt to read all, using gRPC connection:
    try {
      List<TrayDto> traysLoaded = trayRegistrationSystem.getAllTrays();
      trayDtoCache.clear();
      trayDtoCache.addAll(traysLoaded);
      return traysLoaded;

    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading all Trays. Database server could not be reached. Reading data from local cache instead.");
      return trayDtoCache;
    }
  }
}
