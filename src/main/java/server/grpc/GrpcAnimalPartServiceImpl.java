package server.grpc;

import grpc.AnimalPartData;
import grpc.AnimalPartId;
import grpc.EmptyMessage;
import grpc.SlaughterHouseServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import server.controller.grpc.grpc_to_java.GrpcAnimalPartData_To_AnimalPart;
import server.controller.grpc.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData;
import server.service.AnimalPartRegistryInterface;
import shared.model.entities.AnimalPart;
import shared.model.exceptions.CreateFailedException;

@GrpcService
public class GrpcAnimalPartServiceImpl extends SlaughterHouseServiceGrpc.SlaughterHouseServiceImplBase
{
  private final AnimalPartRegistryInterface animalPartService;

  @Autowired
  public GrpcAnimalPartServiceImpl(AnimalPartRegistryInterface animalPartService) {
    super();
    this.animalPartService = animalPartService;
  }


  @Override
  public void registerAnimalPart(AnimalPartData request, StreamObserver<AnimalPartData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types, and
      // attempt to register the AnimalPart:
      AnimalPart createdAnimalPart = animalPartService.registerAnimalPart(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(request));

      // If animal creation fails
      if (createdAnimalPart == null)
        throw new CreateFailedException("AnimalPart could not be created");

      // Translate the created AnimalPart into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(createdAnimalPart));
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error registering AnimalPart").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readAnimalPart(AnimalPartId request, StreamObserver<grpc.AnimalPartData> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void updateAnimalPart(AnimalPartData request, StreamObserver<grpc.EmptyMessage> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void removeAnimalPart(AnimalPartData request, StreamObserver<grpc.EmptyMessage> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void getAnimalParts(EmptyMessage request, StreamObserver<grpc.AnimalPartsData> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }
}
