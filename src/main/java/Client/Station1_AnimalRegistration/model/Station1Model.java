package Client.Station1_AnimalRegistration.model;

import Client.common.model.BaseModel;
import Client.common.model.QueueManager;
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

  public void registerNewAnimal(BigDecimal weightInKilogram, String origin, Date arrivalDate) {
    // TODO: Initial validation of data

    // Create a new AnimalDto:
    AnimalDto newAnimal = new AnimalDto();
    newAnimal.setWeight_kilogram(weightInKilogram);
    newAnimal.setOrigin(origin);
    newAnimal.setArrivalDate(arrivalDate);

    // Hand over to responsible Queue:
    queueManager.addToUnregisteredQueue(newAnimal);
  }

  public AnimalDto readAnimal(long animalId) throws NotFoundException {
    // TODO: Initial validation of data

    // Attempt to read, using gRPC connection:
    try {
      return animalRegistrationService.readAnimal(animalId);
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading animal '" + animalId + "'. Database server could not be reached. Please try again later...");
      return null;
    }
  }

  public void updateAnimal(AnimalDto data) throws UpdateFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Attempt to update, using gRPC connection:
    try {
      animalRegistrationService.updateAnimal(data);
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error updating animal '" + data.getAnimalId() + "'. Database server could not be reached. Please try again later...");
    }
  }

  public boolean removeAnimal(long animalId) throws DeleteFailedException, NotFoundException {
    // TODO: Initial validation of data

    // Attempt to delete, using gRPC connection:
    try {
      return animalRegistrationService.removeAnimal(animalId);
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error removing animal '" + animalId + "'. Database server could not be reached. Please try again later...");
      return false;
    }
  }

  public List<AnimalDto> getAllAnimals() throws NotFoundException {
    // Attempt to read all, using gRPC connection:
    try {
      return animalRegistrationService.getAllAnimals();
    } catch (RuntimeException e) {
      // Database server cannot be reached.
      System.err.println("Error reading all animals. Database server could not be reached. Please try again later...");
      return new ArrayList<>();
    }
  }

  @Override public Object readEntityFromReceivedEntityList(long Id) throws NullPointerException {
    throw new NullPointerException("This method should never be called, since this station never receives any entities from RabbitMQ Connection");
  }

  @Override public void addEntityToReceivedEntityList(Object obj) throws NullPointerException {
    throw new NullPointerException("This method should never be called, since this station never receives any entities from RabbitMQ Connection");
  }
}
