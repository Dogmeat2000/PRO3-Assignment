package server.controller.grpc.service;

import grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import server.controller.grpc.adapters.grpc_to_java.GrpcAnimalPartData_To_AnimalPart;
import server.controller.grpc.adapters.java_to_gRPC.Animal_ToGrpc_AnimalData;
import server.controller.grpc.adapters.grpc_to_java.GrpcAnimalData_To_Animal;
import server.controller.grpc.adapters.grpc_to_java.GrpcId_To_LongId;
import server.model.persistence.service.AnimalPartRegistryInterface;
import server.model.persistence.service.AnimalRegistryInterface;
import shared.model.entities.Animal;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.List;

@GrpcService
public class GrpcAnimalServiceImpl extends AnimalServiceGrpc.AnimalServiceImplBase
{
  private final AnimalRegistryInterface animalService;
  private final AnimalPartRegistryInterface animalPartService;
  private final GrpcAnimalData_To_Animal grpcAnimalDataConverter = new GrpcAnimalData_To_Animal();
  private final int maxNestingDepth;

  @Autowired
  public GrpcAnimalServiceImpl(AnimalRegistryInterface animalService,
      AnimalPartRegistryInterface animalPartService/*,
      GrpcAnimalData_To_Animal grpcAnimalDataConverter*/,
      @Value("${maxNestingDepth}") int maxNestingDepth) {
    super();
    this.animalService = animalService;
    this.animalPartService = animalPartService;
    this.maxNestingDepth = maxNestingDepth;
    //this.grpcAnimalDataConverter = grpcAnimalDataConverter;
  }


  @Transactional
  @Override
  public void registerAnimal(AnimalData request, StreamObserver<AnimalData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types, and
      // attempt to register the Animal:
      Animal createdAnimal = animalService.registerAnimal(grpcAnimalDataConverter.convertToAnimal(request, maxNestingDepth));

      // If animal creation fails
      if (createdAnimal == null)
        throw new CreateFailedException("Animal could not be created");

      // Translate the created Animal into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(Animal_ToGrpc_AnimalData.convertToAnimalData(createdAnimal));
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error registering animal, " + e.getMessage()).withCause(e).asRuntimeException());
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
      responseObserver.onError(Status.NOT_FOUND.withDescription("Animal with id " + request.getAnimalId() + " not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading animal, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Transactional
  @Override
  public void updateAnimal(AnimalData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into a Java compatible type:
      Animal animalReceived = grpcAnimalDataConverter.convertToAnimal(request, maxNestingDepth);

      // To combat the data loss in entity relations during gRPC conversion, re-populate entity associations:
      animalReceived.getPartList().clear();
      for (Long animalPartId : animalReceived.getAnimalPartIdList())
        animalReceived.addAnimalPart(animalPartService.readAnimalPart(animalPartId));

      // Attempt to update the Animal:
      if (!animalService.updateAnimal(animalReceived)) {
        // If Animal update failed:
        throw new UpdateFailedException("Error occurred while updated animal with id='" + request.getAnimalId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Animal not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error occurred while attempting to update Animal with id '" + request.getAnimalId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Transactional
  @Override
  public void removeAnimal(AnimalData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to delete the Animal with the provided ID:
      if(!animalService.removeAnimal(grpcAnimalDataConverter.convertToAnimal(request, maxNestingDepth))) {
        // If Animal deletion failed:
        throw new DeleteFailedException("Error occurred while deleting animal with id='" + request.getAnimalId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Animal not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error deleting animal, " + e.getMessage()).withCause(e).asRuntimeException());
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
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all animals, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }
}
