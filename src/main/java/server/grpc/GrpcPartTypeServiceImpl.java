package server.grpc;

import grpc.EmptyMessage;
import grpc.PartTypeData;
import grpc.PartTypeId;
import grpc.PartTypeServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import server.controller.grpc.grpc_to_java.GrpcId_To_LongId;
import server.controller.grpc.grpc_to_java.GrpcPartTypeData_To_PartType;
import server.controller.grpc.java_to_gRPC.PartType_ToGrpc_PartTypeData;
import server.service.PartTypeRegistryInterface;
import shared.model.entities.PartType;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.UpdateFailedException;

import java.util.List;

@GrpcService
public class GrpcPartTypeServiceImpl extends PartTypeServiceGrpc.PartTypeServiceImplBase
{
  private final PartTypeRegistryInterface partTypeService;

  @Autowired
  public GrpcPartTypeServiceImpl(PartTypeRegistryInterface partTypeService) {
    super();
    this.partTypeService = partTypeService;
  }


  @Override
  public void registerPartType(PartTypeData request, StreamObserver<PartTypeData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types, and
      // attempt to register the PartType:
      PartType createdPartType = partTypeService.registerPartType(GrpcPartTypeData_To_PartType.convertToPartType(request));

      // If partType creation fails
      if (createdPartType == null)
        throw new CreateFailedException("PartType could not be created");

      // Translate the created PartType into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(PartType_ToGrpc_PartTypeData.convertToPartTypeData(createdPartType));
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error registering partType, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readPartType(PartTypeId request, StreamObserver<grpc.PartTypeData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible type,
      // and attempt to read the PartType with the provided ID:
      PartType partType = partTypeService.readPartType(GrpcId_To_LongId.ConvertToLongId(request));

      // If PartType read failed:
      if (partType == null)
        throw new NotFoundException("PartType not found");

      // Translate the found PartType into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("PartType with id " + request.getPartTypeId() + "not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading partType, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void updatePartType(PartTypeData request, StreamObserver<grpc.EmptyMessage> responseObserver) {
    try {
      // TODO: Probably need to re-fetch all associated entity lists here, so that the object to update has all the proper object relations.

      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to update the PartType with the provided ID:
      if (!partTypeService.updatePartType(GrpcPartTypeData_To_PartType.convertToPartType(request))) {
        // If PartType update failed:
        throw new UpdateFailedException("Error occurred while updated partType with id='" + request.getPartTypeId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("PartType not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error occurred while attempting to update PartType with id '" + request.getPartTypeId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void removePartType(PartTypeData request, StreamObserver<grpc.EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to delete the PartType with the provided ID:
      if(!partTypeService.removePartType(GrpcPartTypeData_To_PartType.convertToPartType(request))) {
        // If PartType deletion failed:
        throw new DeleteFailedException("Error occurred while deleting partType with id='" + request.getPartTypeId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("PartType not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error deleting PartType, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void getAllPartTypes(EmptyMessage request, StreamObserver<grpc.PartTypesData> responseObserver) {
    try {
      // Attempt to retrieve all PartTypes:
      List<PartType> partTypes = partTypeService.getAllPartTypes();

      // If PartType read failed:
      if (partTypes == null)
        throw new NotFoundException("PartTypes not found");

      // Translate the found PartType into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(PartType_ToGrpc_PartTypeData.convertToPartTypesDataList(partTypes));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("No PartTypes found").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all PartTypes, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }
}
