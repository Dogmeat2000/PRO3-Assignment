package Client.Station1_AnimalRegistration.model;

import Client.model.BaseModel;
import Client.model.QueueManager;
import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationService;
import shared.model.dto.AnimalDto;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.math.BigDecimal;
import java.util.*;

public class Station1Model implements BaseModel
{
  private final QueueManager queueManager;
  private final AnimalRegistrationService animalRegistrationService;

  public Station1Model(AnimalRegistrationService animalRegistrationService, QueueManager queueManager){
    this.animalRegistrationService = animalRegistrationService;
    this.queueManager = queueManager;
  }

  public AnimalDto registerNewAnimal(BigDecimal weightInKilogram, String origin, Date arrivalDate) {
    // TODO: Initial validation of data

    // Attempt to register, using gRPC connection:
    AnimalDto registeredAnimal = animalRegistrationService.registerNewAnimal(weightInKilogram, origin, arrivalDate);

    // Add the registered entity to the proper Queue for indirect transmission to the next station:
    if(!queueManager.addLast(registeredAnimal)){
      // Registered entity was not properly added to the queue. Throw an exception.
      throw new RuntimeException("Critical Failure: Failed to add the registered Animal to the queue, before transmission via RabbitMq.");
    }

    return registeredAnimal;
  }

  public AnimalDto readAnimal(long animalId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    return animalRegistrationService.readAnimal(animalId);
  }

  public void updateAnimal(AnimalDto data) throws UpdateFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Attempt to update, using gRPC connection:
    animalRegistrationService.updateAnimal(data);
  }

  public boolean removeAnimal(long animalId) throws DeleteFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Attempt to delete, using gRPC connection:
    return animalRegistrationService.removeAnimal(animalId);
  }

  public List<AnimalDto> getAllAnimals() throws NotFoundException {
    // Attempt to read all, using gRPC connection:
    return animalRegistrationService.getAllAnimals();
  }

  @Override public Object readEntityFromReceivedEntityList(long Id) throws NullPointerException {
    throw new NullPointerException("This method should never be called, since this station never receives any entities from RabbitMQ Connection");
  }

  @Override public void addEntityToReceivedEntityList(Object obj) throws NullPointerException {
    throw new NullPointerException("This method should never be called, since this station never receives any entities from RabbitMQ Connection");
  }
}
