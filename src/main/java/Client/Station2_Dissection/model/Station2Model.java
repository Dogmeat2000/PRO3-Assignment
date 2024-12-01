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
  private final List<AnimalDto> receivedAnimalsList;
  private final QueueManager queueManager;
  private final AnimalPartRegistrationService animalPartRegistrationService;
  private final PartTypeRegistrationSystem partTypeRegistrationSystem;
  private final TrayRegistrationSystem trayRegistrationSystem;

  public Station2Model(AnimalPartRegistrationService animalPartRegistrationService,
      QueueManager queueManager,
      PartTypeRegistrationSystem partTypeRegistrationSystem,
      TrayRegistrationSystem trayRegistrationSystem) {
    this.receivedAnimalsList = new ArrayList<>();
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
    return animalPartRegistrationService.readAnimalPart(animalPartId);
  }

  public List<AnimalPartDto> readAnimalPartsByAnimalId(long animalId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    return animalPartRegistrationService.readAnimalPartsByAnimalId(animalId);
  }

  public void updateAnimalPart(AnimalPartDto data) throws UpdateFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Retrieve the un-modified AnimalPart:
    AnimalPartDto unmodifiedDto = this.readAnimalPart(data.getAnimalId());

    // Attempt to update, using gRPC connection:
    animalPartRegistrationService.updateAnimalPart(data);

    // If associated Animal has changed, we need to remove that from the list of valid Animals:
    if(unmodifiedDto.getAnimalId() != data.getAnimalId()) {
      // Animal has changed. Find and consume it.
      this.removeEntityFromReceivedEntityList(data.getAnimalId());
    }
  }

  public boolean removeAnimalPart(AnimalPartDto data) throws DeleteFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Attempt to delete, using gRPC connection:
    return animalPartRegistrationService.removeAnimalPart(data);
  }

  public List<AnimalPartDto> getAllAnimalParts() throws NotFoundException {
    // Attempt to read all, using gRPC connection:
    return animalPartRegistrationService.getAllAnimalParts();
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
    return partTypeRegistrationSystem.readPartType(partTypeId);
  }

  public List<PartTypeDto> getAllPartTypes(){
    // Attempt to read all, using gRPC connection:
    return partTypeRegistrationSystem.getAllPartTypes();
  }



  // Tray related operations:
  public TrayDto readTray(long trayId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    return trayRegistrationSystem.readTray(trayId);
  }

  public List<TrayDto> getAllTrays(){
    // Attempt to read all, using gRPC connection:
    return trayRegistrationSystem.getAllTrays();
  }
}
