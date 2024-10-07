package server.grpc;

import grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import server.controller.grpc.Animal_ToGrpc_AnimalData;
import server.controller.grpc.GrpcAnimalData_To_Animal;
import server.service.AnimalRegistryInterface;
import shared.model.entities.Animal;
import shared.model.exceptions.AnimalNotFoundException;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.UpdateFailedException;

import java.math.BigDecimal;
import java.util.List;

@GrpcService
public class GrpcAnimalServiceImpl extends SlaughterHouseServiceGrpc.SlaughterHouseServiceImplBase
{
  private final AnimalRegistryInterface animalService;

  public GrpcAnimalServiceImpl(AnimalRegistryInterface animalService) {
    super();
    this.animalService = animalService;
  }


  @Override
  public void registerAnimal(AnimalData request, StreamObserver<AnimalData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types:
      long animalId = request.getAnimalId();
      BigDecimal animalWeight = new BigDecimal(request.getAnimalWeight());

      // Attempt to register the Animal:
      Animal createdAnimal = animalService.registerAnimal(new Animal(animalId, animalWeight));

      // If animal creation fails
      if (createdAnimal == null)
        throw new CreateFailedException("Animal could not be created");

      // Translate the created Animal into gRPC compatible types, before transmitting back to client:
      AnimalData response = Animal_ToGrpc_AnimalData.ConvertToAnimalData(createdAnimal);
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error registering animal").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readAnimal(AnimalId request, StreamObserver<AnimalData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types:
      long animalId = request.getAnimalId();

      // Attempt to read the Animal with the provided ID:
      Animal animal = animalService.readAnimal(animalId);

      // If Animal read failed:
      if (animal == null)
        throw new AnimalNotFoundException("Animal not found");

      // Translate the found Animal into gRPC compatible types, before transmitting back to client:
      AnimalData response = Animal_ToGrpc_AnimalData.ConvertToAnimalData(animal);
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } catch (AnimalNotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Animal with id " + request.getAnimalId() + "not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading animal").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void updateAnimal(AnimalData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types:
      Animal animal = GrpcAnimalData_To_Animal.convertToAnimal(request);

      // Attempt to update the Animal with the provided ID:
      if (!animalService.updateAnimal(animal)) {
        // If Animal update failed:
        throw new UpdateFailedException("Error occurred while updated animal with id='" + animal.getId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (AnimalNotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Animal not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error occurred while attempting to update Animal with id '" + request.getAnimalId() + "'").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void removeAnimal(AnimalData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types:
      Animal animal = GrpcAnimalData_To_Animal.convertToAnimal(request);

      // Attempt to delete the Animal with the provided ID:
      if(!animalService.removeAnimal(animal)) {
        // If Animal deletion failed:
        throw new DeleteFailedException("Error occurred while deleting animal with id='" + animal.getId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (AnimalNotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Animal not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error deleting animal").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void getAllAnimals(EmptyMessage request, StreamObserver<AnimalsData> responseObserver) {
    try {
      // Attempt to retrieve all Animals:
      List<Animal> animals = animalService.getAllAnimals();

      // If Animal read failed:
      if (animals == null)
        throw new AnimalNotFoundException("Animal not found");

      // Translate the found Animal into gRPC compatible types, before transmitting back to client:
      AnimalsData response = Animal_ToGrpc_AnimalData.convertToAnimalsDataList(animals);
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } catch (AnimalNotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("No Animals found").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all animals").withCause(e).asRuntimeException());
    }
  }
}
