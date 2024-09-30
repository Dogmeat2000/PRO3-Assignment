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
import server.controller.grpc.AnimalToGrpcAnimalData;
import server.service.AnimalRegistryInterface;
import server.service.AnimalService;
import shared.model.entities.Animal;

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
      long animalId = request.getAnimalId();
      BigDecimal animalWeight = new BigDecimal(request.getAnimalWeight());
      Animal createdAnimal = animalService.registerAnimal(new Animal(animalId, animalWeight));

      if (createdAnimal == null) {
        // If animal creation fails
        responseObserver.onError(Status.INTERNAL.withDescription("Failed to register animal in the database").asRuntimeException());
        return;
      }

      AnimalData response = AnimalToGrpcAnimalData.ConvertToAnimalData(createdAnimal);
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
  public void readAnimal(AnimalId request, io.grpc.stub.StreamObserver<AnimalData> responseObserver) {
    //TODO MISSING IMPLEMENTATION
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
