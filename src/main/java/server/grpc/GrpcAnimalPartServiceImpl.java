package server.grpc;

import grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import server.controller.grpc.grpc_to_java.GrpcAnimalPartData_To_AnimalPart;
import server.controller.grpc.grpc_to_java.GrpcId_To_LongId;
import server.controller.grpc.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData;
import server.service.AnimalPartRegistryInterface;
import shared.model.entities.AnimalPart;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.UpdateFailedException;

import java.util.List;

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

      // If animalPart creation fails
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
  public void readAnimalPart(AnimalPartId request, StreamObserver<AnimalPartData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types:
      long animalPartId = request.getAnimalPartId();
      long animalId = GrpcId_To_LongId.ConvertToLongId(request.getAnimalId());
      long typeId = GrpcId_To_LongId.ConvertToLongId(request.getAnimalId());
      long trayId = GrpcId_To_LongId.ConvertToLongId(request.getAnimalId());

      // Attempt to read the AnimalPart with the provided ID:
      AnimalPart animalPart = animalPartService.readAnimalPart(animalPartId, animalId, typeId, trayId);

      // If AnimalPart read failed:
      if (animalPart == null)
        throw new NotFoundException("AnimalPart not found");

      // Translate the found AnimalPart into a gRPC compatible type, before transmitting back to client:
      responseObserver.onNext(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("AnimalPart with id " + request.getAnimalPartId() + "not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading AnimalPart").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void updateAnimalPart(AnimalPartData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to update the AnimalPart with the provided ID:
      if (!animalPartService.updateAnimalPart(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(request))) {
        // If AnimalPart update failed:
        throw new UpdateFailedException("Error occurred while updated AnimalPart with id='" + request.getAnimalPartId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("AnimalPart not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error occurred while attempting to update AnimalPart with id '" + request.getAnimalPartId() + "'").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void removeAnimalPart(AnimalPartData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to delete the AnimalPart with the provided ID:
      if(!animalPartService.removeAnimalPart(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(request))) {
        // If AnimalPart deletion failed:
        throw new DeleteFailedException("Error occurred while deleting AnimalPart with id='" + request.getAnimalPartId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("AnimalPart not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error deleting AnimalPart").withCause(e).asRuntimeException());
    }
  }


  @Override
  public void getAnimalParts(EmptyMessage request, StreamObserver<AnimalPartsData> responseObserver) {
    try {
      // Attempt to retrieve all Animals:
      List<AnimalPart> animalParts = animalPartService.getAllAnimalParts();

      // If Animal read failed:
      if (animalParts == null)
        throw new NotFoundException("AnimalParts not found");

      // Translate the found Animal into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartsDataList(animalParts));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("No AnimalParts found").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all AnimalParts").withCause(e).asRuntimeException());
    }
  }
}
