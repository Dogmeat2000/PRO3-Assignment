package DataServer.controller.grpc.service;

import grpc.EmptyMessage;
import grpc.PartTypeData;
import grpc.PartTypeId;
import grpc.PartTypeServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import DataServer.controller.grpc.adapters.grpc_to_java.GrpcPartTypeData_To_PartType;
import DataServer.controller.grpc.adapters.java_to_gRPC.PartType_ToGrpc_PartTypeData;
import DataServer.model.persistence.service.PartTypeRegistryInterface;
import DataServer.model.persistence.entities.PartType;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.List;

@GrpcService
public class GrpcPartTypeServiceImpl extends PartTypeServiceGrpc.PartTypeServiceImplBase
{
  private final PartTypeRegistryInterface partTypeService;
  private final GrpcPartTypeData_To_PartType grpcPartTypeDataConverter;
  private final PartType_ToGrpc_PartTypeData partTypeConverter = new PartType_ToGrpc_PartTypeData();

  @Autowired
  public GrpcPartTypeServiceImpl(PartTypeRegistryInterface partTypeService,
      GrpcPartTypeData_To_PartType grpcPartTypeDataConverter) {
    super();
    this.partTypeService = partTypeService;
    this.grpcPartTypeDataConverter = grpcPartTypeDataConverter;
  }


  @Transactional
  @Override
  public void registerPartType(PartTypeData request, StreamObserver<PartTypeData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types, and
      // attempt to register the PartType:
      PartType createdPartType = partTypeService.registerPartType(grpcPartTypeDataConverter.convertToPartType(request));

      // If partType creation fails
      if (createdPartType == null)
        throw new CreateFailedException("PartType could not be created");

      // Translate the created PartType into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(partTypeConverter.convertToPartTypeData(createdPartType));
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error registering partType, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Transactional
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
      responseObserver.onNext(partTypeConverter.convertToPartTypeData(partType));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("PartType with id " + request.getPartTypeId() + " not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading partType, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Transactional
  @Override
  public void updatePartType(PartTypeData request, StreamObserver<grpc.EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into a Java compatible type:
      PartType partTypeReceived = grpcPartTypeDataConverter.convertToPartType(request);

      // Attempt to update the PartType:
      if (!partTypeService.updatePartType(partTypeReceived)) {
        // If Animal update failed:
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


  @Transactional
  @Override
  public void removePartType(PartTypeData request, StreamObserver<grpc.EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to delete the PartType with the provided ID:
      if(!partTypeService.removePartType(grpcPartTypeDataConverter.convertToPartType(request))) {
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


  @Transactional
  @Override
  public void getAllPartTypes(EmptyMessage request, StreamObserver<grpc.PartTypesData> responseObserver) {
    try {
      // Attempt to retrieve all PartTypes:
      List<PartType> partTypes = partTypeService.getAllPartTypes();

      // If PartType read failed:
      if (partTypes == null)
        throw new NotFoundException("PartTypes not found");

      // Translate the found PartType into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(partTypeConverter.convertToPartTypesDataList(partTypes));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("No PartTypes found").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all PartTypes, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }
}
