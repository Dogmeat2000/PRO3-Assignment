package Client.Station2_Dissection.model;

import Client.common.model.BaseModel;
import Client.common.model.QueueManager;
import Client.Station2_Dissection.network.services.gRPC.AnimalPartRegistrationService;
import Client.common.services.gRPC.PartTypeRegistrationSystem;
import Client.common.services.gRPC.TrayRegistrationSystem;
import shared.model.dto.AnimalDto;
import shared.model.dto.AnimalPartDto;
import shared.model.dto.PartTypeDto;
import shared.model.dto.TrayDto;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Station2Model implements BaseModel
{
  private final List<AnimalDto> receivedAnimalsList = new ArrayList<>();
  private final List<TrayDto> trayDtoCache = new ArrayList<>();
  private final List<PartTypeDto> partTypeDtoCache = new ArrayList<>();
  private final QueueManager queueManager;
  private final AnimalPartRegistrationService animalPartRegistrationService;
  private final PartTypeRegistrationSystem partTypeRegistrationSystem;
  private final TrayRegistrationSystem trayRegistrationSystem;

  public Station2Model(AnimalPartRegistrationService animalPartRegistrationService,
      QueueManager queueManager,
      PartTypeRegistrationSystem partTypeRegistrationSystem,
      TrayRegistrationSystem trayRegistrationSystem) {
    this.queueManager = queueManager;
    this.animalPartRegistrationService = animalPartRegistrationService;
    this.partTypeRegistrationSystem = partTypeRegistrationSystem;
    this.trayRegistrationSystem = trayRegistrationSystem;
  }

  // AnimalPart related operations:
  public void registerNewAnimalPart(AnimalDto animal, PartTypeDto type, TrayDto tray, BigDecimal weightInKilogram) throws CreateFailedException {
    // TODO: Initial validation of data

    // Create a new AnimalPartDto:
    AnimalPartDto newAnimalPart = new AnimalPartDto(
        0,
        weightInKilogram,
        type.getTypeId(),
        animal.getAnimalId(),
        tray.getTrayId(),
        0
    );

    // Hand over to responsible Queue:
    queueManager.addToUnregisteredQueue(newAnimalPart);
  }

  public AnimalPartDto readAnimalPart(long animalPartId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    try {
      return animalPartRegistrationService.readAnimalPart(animalPartId);
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading AnimalPart '" + animalPartId + "'. Database server could not be reached. Please try again later...");
      return null;
    }
  }

  public List<AnimalPartDto> readAnimalPartsByAnimalId(long animalId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    try {
      return animalPartRegistrationService.readAnimalPartsByAnimalId(animalId);
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading AnimalPart by Animal Id'" + animalId + "'. Database server could not be reached. Please try again later...");
      return null;
    }
  }

  public void updateAnimalPart(AnimalPartDto data) throws UpdateFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Retrieve the un-modified AnimalPart:
    AnimalPartDto unmodifiedDto = this.readAnimalPart(data.getAnimalId());

    // Attempt to update, using gRPC connection:
    try {
      animalPartRegistrationService.updateAnimalPart(data);

      // If associated Animal has changed, we need to remove that from the list of valid Animals:
      if(unmodifiedDto.getAnimalId() != data.getAnimalId()) {
        // Animal has changed. Find and consume it.
        this.removeEntityFromReceivedEntityList(data.getAnimalId());
      }
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error updating AnimalPart '" + data.getPartId() + "'. Database server could not be reached. Please try again later...");
    }

  }

  public boolean removeAnimalPart(AnimalPartDto data) throws DeleteFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Attempt to delete, using gRPC connection:
    try {
      return animalPartRegistrationService.removeAnimalPart(data);
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error removing AnimalPart '" + data.getPartId() + "'. Database server could not be reached. Please try again later...");
      return false;
    }
  }

  public List<AnimalPartDto> getAllAnimalParts() throws NotFoundException {
    // Attempt to read all, using gRPC connection:
    try {
      return animalPartRegistrationService.getAllAnimalParts();
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading all AnimalParts. Database server could not be reached. Please try again later...");
      return new ArrayList<>();
    }
  }



  // Animal related operations:
  @Override public AnimalDto readEntityFromReceivedEntityList(long animalId) {
    List<AnimalDto> registeredAnimals = new ArrayList<>(receivedAnimalsList);
    for (AnimalDto animal : registeredAnimals) {
      if(animal.getAnimalId() == animalId) {
        return animal;
      }
    }
    return null;
  }

  @Override public void addEntityToReceivedEntityList(Object obj) {
    if(obj instanceof AnimalDto)
      receivedAnimalsList.add((AnimalDto) obj);
  }

  public List<AnimalDto> getAllEntitiesFromReceivedEntityList() {
    return receivedAnimalsList;
  }

  private boolean removeEntityFromReceivedEntityList(long id){
    for (AnimalDto animal : getAllEntitiesFromReceivedEntityList()) {
      if(animal.getAnimalId() == id) {
        receivedAnimalsList.remove(animal);
        return true;
      }
    }
    return false;
  }



  // PartType related operations:
  public PartTypeDto readPartType(long partTypeId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    try {
      return partTypeRegistrationSystem.readPartType(partTypeId);
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading PartType '" + partTypeId + "'. Database server could not be reached. Reading data from local cache instead.");
      for (PartTypeDto partType : partTypeDtoCache) {
        if(partType.getTypeId() == partTypeId) {
          return partType;
        }
      }
      return null;
    }
  }

  public List<PartTypeDto> getAllPartTypes(){
    // Attempt to read all, using gRPC connection:
    try {
      List<PartTypeDto> partTypesLoaded = partTypeRegistrationSystem.getAllPartTypes();
      partTypeDtoCache.clear();
      partTypeDtoCache.addAll(partTypesLoaded);
      return partTypesLoaded;

    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading all PartTypes. Database server could not be reached. Reading data from local cache instead.");
      return partTypeDtoCache;
    }
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
