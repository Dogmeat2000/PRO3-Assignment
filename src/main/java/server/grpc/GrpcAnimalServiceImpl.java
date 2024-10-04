package server.grpc;

import grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.controller.grpc.Animal_ToGrpc_AnimalData;
import server.controller.grpc.GrpcAnimalData_To_Animal;
import server.service.AnimalRegistryInterface;
import shared.model.entities.Animal;
import shared.model.exceptions.AnimalNotFoundException;

import java.math.BigDecimal;
import java.util.List;

@GrpcService
public class GrpcAnimalServiceImpl extends SlaughterHouseServiceGrpc.SlaughterHouseServiceImplBase
{
  private final AnimalRegistryInterface animalService;
  private static final Logger logger = LoggerFactory.getLogger("Service");

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

      if (createdAnimal == null) {
        // If animal creation fails
        responseObserver.onError(Status.INTERNAL.withDescription("Failed to register animal in the database").asRuntimeException());
        return;
      }

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
      if (animal == null) {
        responseObserver.onError(Status.INTERNAL.withDescription("Failed to read animal with id '" + animalId + "' in the database").asRuntimeException());
        return;
      }

      // Translate the found Animal into gRPC compatible types, before transmitting back to client:
      AnimalData response = Animal_ToGrpc_AnimalData.ConvertToAnimalData(animal);
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } catch (AnimalNotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Animal not found in DB").withCause(e).asRuntimeException());
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
      boolean updateAnimal = animalService.updateAnimal(animal);

      // If Animal update failed:
      if (!updateAnimal) {
        responseObserver.onError(Status.INTERNAL.withDescription("Failed to update animal with id '" + animal.getId() + "' in the database").asRuntimeException());
        return;
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
  public void removeAnimal(AnimalData request, io.grpc.stub.StreamObserver<EmptyMessage> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void getAllAnimals(grpc.EmptyMessage request, io.grpc.stub.StreamObserver<grpc.AnimalsData> responseObserver) {
    try {
      // Attempt to retrieve all Animals:
      List<Animal> animals = animalService.getAllAnimals();

      // If Animal read failed:
      if (animals == null) {
        responseObserver.onError(Status.INTERNAL.withDescription("Failed to retrieve all animals from the database").asRuntimeException());
        return;
      }

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
