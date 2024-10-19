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

import java.util.HashMap;
import java.util.List;

@GrpcService
public class GrpcAnimalPartServiceImpl extends AnimalPartServiceGrpc.AnimalPartServiceImplBase
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
      responseObserver.onError(Status.INTERNAL.withDescription("Error registering AnimalPart, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readAnimalPart(AnimalPartId request, StreamObserver<AnimalPartData> responseObserver) {
    try {
      // Attempt to read the AnimalPart with the provided ID:
      AnimalPart animalPart = animalPartService.readAnimalPart(GrpcId_To_LongId.ConvertToLongId(request));

      // If AnimalPart read failed:
      if (animalPart == null)
        throw new NotFoundException("AnimalPart not found");

      // Translate the found AnimalPart into a gRPC compatible type, before transmitting back to client:
      responseObserver.onNext(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("AnimalPart with id " + request.getAnimalPartId() + "not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading AnimalPart, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readAnimalPartsByAnimalId(AnimalId request, StreamObserver<AnimalPartsData> responseObserver) {
    try {
      // Attempt to retrieve all AnimalParts:
      List<AnimalPart> animalParts = animalPartService.readAnimalPartsByAnimalId(GrpcId_To_LongId.ConvertToLongId(request));

      // If no AnimalParts were found:
      if (animalParts == null || animalParts.isEmpty())
        throw new NotFoundException("No AnimalParts associated with animal_id '" + request.getAnimalId() + "' found.");

      // Translate the found AnimalPart into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartsDataList(animalParts));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all AnimalParts associated with animal_id '" + request.getAnimalId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readAnimalPartsByPartTypeId(PartTypeId request, StreamObserver<AnimalPartsData> responseObserver) {
    try {
      // Attempt to retrieve all AnimalParts:
      List<AnimalPart> animalParts = animalPartService.readAnimalPartsByPartTypeId(GrpcId_To_LongId.ConvertToLongId(request));

      // If no AnimalParts were found:
      if (animalParts == null || animalParts.isEmpty())
        throw new NotFoundException("No AnimalParts associated with partType_id '" + request.getPartTypeId() + "' found.");

      // Translate the found AnimalPart into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartsDataList(animalParts));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all AnimalParts associated with partType_id '" + request.getPartTypeId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readAnimalPartsByProductId(ProductId request, StreamObserver<AnimalPartsData> responseObserver) {
    try {
      // Attempt to retrieve all AnimalParts:
      List<AnimalPart> animalParts = animalPartService.readAnimalPartsByProductId(GrpcId_To_LongId.ConvertToLongId(request));

      // If no AnimalParts were found:
      if (animalParts == null || animalParts.isEmpty())
        throw new NotFoundException("No AnimalParts associated with product_id '" + request.getProductId() + "' found.");

      // Translate the found AnimalPart into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartsDataList(animalParts));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all AnimalParts associated with product_id '" + request.getProductId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readAnimalPartsByTrayId(TrayId request, StreamObserver<AnimalPartsData> responseObserver) {
    try {
      // Attempt to retrieve all AnimalParts:
      List<AnimalPart> animalParts = animalPartService.readAnimalPartsByTrayId(GrpcId_To_LongId.ConvertToLongId(request));

      // If no AnimalParts were found:
      if (animalParts == null || animalParts.isEmpty())
        throw new NotFoundException("No AnimalParts associated with tray_id '" + request.getTrayId() + "' found.");

      // Translate the found AnimalPart into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartsDataList(animalParts));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all AnimalParts associated with tray_id '" + request.getTrayId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void updateAnimalPart(UpdatedAnimalPartData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // TODO: Probably need to re-fetch all associated entity lists here, so that the object to update has all the proper object relations.

      // Translate received gRPC information from the client, into Java compatible types:
      AnimalPart oldAnimalPart = GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(UpdatedAnimalPartData.newBuilder().getOldData());
      AnimalPart newAnimalPart = GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(UpdatedAnimalPartData.newBuilder().getNewData());
      // and attempt to update the AnimalPart with the provided ID:
      if (!animalPartService.updateAnimalPart(oldAnimalPart, newAnimalPart)) {
        // If AnimalPart update failed:
        throw new UpdateFailedException("Error occurred while updated AnimalPart with id='" + request.getOldData().getAnimalPartId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("AnimalPart not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error occurred while attempting to update AnimalPart with id '" + request.getOldData().getAnimalPartId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
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
      responseObserver.onError(Status.INTERNAL.withDescription("Error deleting AnimalPart, " + e.getMessage()).withCause(e).asRuntimeException());
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
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all AnimalParts, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }
}
