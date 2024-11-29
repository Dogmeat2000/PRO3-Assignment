package Client.model.Station2;

import Client.model.BaseModel;
import Client.model.QueueManager;
import Client.network.services.gRPC.AnimalPartRegistrationService;
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
  private final QueueManager registeredAnimalPartsQueueManager;
  private final AnimalPartRegistrationService animalPartRegistrationService;

  // TODO: Refactor, so the it does not require a QueueManager, but merely a Queue (or List) for the received Animals

  public Station2Model(QueueManager registeredAnimalPartsQueueManager, AnimalPartRegistrationService animalPartRegistrationService) {
    this.receivedAnimalsList = new ArrayList<>();
    this.registeredAnimalPartsQueueManager = registeredAnimalPartsQueueManager;
    this.animalPartRegistrationService = animalPartRegistrationService;

  }

  // AnimalPart related operations:
  public AnimalPartDto registerNewAnimalPart(AnimalDto animal, PartTypeDto type, TrayDto tray, BigDecimal weightInKilogram) throws CreateFailedException {
    // TODO: Initial validation of data

    // Attempt to register, using gRPC connection:
    AnimalPartDto registeredAnimalPart = animalPartRegistrationService.registerNewAnimalPart(animal, type, tray, weightInKilogram);

    // Add the registered entity to the proper Queue for indirect transmission to the next station:
    if(!registeredAnimalPartsQueueManager.addLast(registeredAnimalPart)){
      // Registered entity was not properly added to the queue. Throw an exception.
      throw new RuntimeException("Critical Failure: Failed to add the registered AnimalPart to the queue, before transmission via RabbitMq.");
    }

    // Remove the added Animal from the received Animals list:
    this.removeEntityFromReceivedEntityList(registeredAnimalPart.getAnimalId());

    return registeredAnimalPart;
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

  public List<AnimalPartDto> readAnimalPartsByPartTypeId(long partTypeId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    return animalPartRegistrationService.readAnimalPartsByPartTypeId(partTypeId);
  }

  public List<AnimalPartDto> readAnimalPartsByProductId(long productId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    return animalPartRegistrationService.readAnimalPartsByProductId(productId);
  }

  public List<AnimalPartDto> readAnimalPartsByTrayId(long trayId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    return animalPartRegistrationService.readAnimalPartsByTrayId(trayId);
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
}
