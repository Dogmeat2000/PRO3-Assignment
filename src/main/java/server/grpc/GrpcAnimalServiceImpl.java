package server.grpc;

import grpc.AnimalData;
import grpc.AnimalId;
import grpc.EmptyMessage;
import grpc.SlaughterHouseServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.controller.grpc.Animal_ToGrpc_AnimalData;
import server.service.AnimalRegistryInterface;
import shared.model.entities.Animal;
import shared.model.exceptions.AnimalNotFoundException;

import java.math.BigDecimal;

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
      // TODO: Implement proper error handling.
      responseObserver.onError(Status.INTERNAL
          .withDescription("Error registering animal")
          .withCause(e) // Optional: include the original exception
          .asRuntimeException());
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
      // Return an animal with id -1, when no Animals matching this Id were found.
      AnimalData response = Animal_ToGrpc_AnimalData.ConvertToAnimalData(new Animal(-1, BigDecimal.ZERO));
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } catch (Exception e) {
      // TODO: Implement proper exception handling.
      responseObserver.onError(Status.INTERNAL
          .withDescription("Error reading animal")
          .withCause(e) // Optional: include the original exception
          .asRuntimeException());
    }
  }


  @Override
  public void updateAnimal(AnimalData request, io.grpc.stub.StreamObserver<EmptyMessage> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void removeAnimal(AnimalData request, io.grpc.stub.StreamObserver<EmptyMessage> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void getAllAnimals(EmptyMessage request, io.grpc.stub.StreamObserver<AnimalData> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }
}
