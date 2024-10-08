package server.grpc;

import grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import server.controller.grpc.java_to_gRPC.Animal_ToGrpc_AnimalData;
import server.controller.grpc.grpc_to_java.GrpcAnimalData_To_Animal;
import server.controller.grpc.grpc_to_java.GrpcId_To_LongId;
import server.service.AnimalRegistryInterface;
import shared.model.entities.Animal;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.UpdateFailedException;

import java.util.List;

@GrpcService
public class GrpcAnimalServiceImpl extends SlaughterHouseServiceGrpc.SlaughterHouseServiceImplBase
{
  private final AnimalRegistryInterface animalService;

  @Autowired
  public GrpcAnimalServiceImpl(AnimalRegistryInterface animalService) {
    super();
    this.animalService = animalService;
  }


  @Override
  public void registerAnimal(AnimalData request, StreamObserver<AnimalData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types, and
      // attempt to register the Animal:
      Animal createdAnimal = animalService.registerAnimal(GrpcAnimalData_To_Animal.convertToAnimal(request));

      // If animal creation fails
      if (createdAnimal == null)
        throw new CreateFailedException("Animal could not be created");

      // Translate the created Animal into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(Animal_ToGrpc_AnimalData.convertToAnimalData(createdAnimal));
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error registering animal").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readAnimal(AnimalId request, StreamObserver<AnimalData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible type,
      // and attempt to read the Animal with the provided ID:
      Animal animal = animalService.readAnimal(GrpcId_To_LongId.ConvertToLongId(request));

      // If Animal read failed:
      if (animal == null)
        throw new NotFoundException("Animal not found");

      // Translate the found Animal into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(Animal_ToGrpc_AnimalData.convertToAnimalData(animal));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Animal with id " + request.getAnimalId() + "not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading animal").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void updateAnimal(AnimalData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to update the Animal with the provided ID:
      if (!animalService.updateAnimal(GrpcAnimalData_To_Animal.convertToAnimal(request))) {
        // If Animal update failed:
        throw new UpdateFailedException("Error occurred while updated animal with id='" + request.getAnimalId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Animal not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error occurred while attempting to update Animal with id '" + request.getAnimalId() + "'").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void removeAnimal(AnimalData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to delete the Animal with the provided ID:
      if(!animalService.removeAnimal(GrpcAnimalData_To_Animal.convertToAnimal(request))) {
        // If Animal deletion failed:
        throw new DeleteFailedException("Error occurred while deleting animal with id='" + request.getAnimalId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
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
        throw new NotFoundException("Animal not found");

      // Translate the found Animal into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(Animal_ToGrpc_AnimalData.convertToAnimalsDataList(animals));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("No Animals found").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all animals").withCause(e).asRuntimeException());
    }
  }
}
