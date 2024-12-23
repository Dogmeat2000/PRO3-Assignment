package Client.common.services.gRPC;

import Client.common.model.adapters.gRPC_to_java.GrpcPartTypeData_To_PartTypeDto;
import Client.common.model.adapters.java_to_gRPC.PartTypeDto_ToGrpc_PartTypeData;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import jakarta.transaction.Transactional;
import Client.common.model.adapters.GrpcFactory;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.PartTypeDto;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.ArrayList;
import java.util.List;

import static io.grpc.Status.INTERNAL;
import static io.grpc.Status.NOT_FOUND;

public class PartTypeRegistrationSystemImpl extends GrpcConnection implements PartTypeRegistrationSystem
{
  private final GrpcPartTypeData_To_PartTypeDto grpcPartTypeDataConverter = new GrpcPartTypeData_To_PartTypeDto();
  private final PartTypeDto_ToGrpc_PartTypeData partTypeDtoConverter = new PartTypeDto_ToGrpc_PartTypeData();

  public PartTypeRegistrationSystemImpl(String host, int port) {
    super(host, port);
  }


  @Transactional
  @Override public PartTypeDto registerNewPartType(String desc) throws CreateFailedException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      PartTypeServiceGrpc.PartTypeServiceBlockingStub stub = PartTypeServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of PartType (PartTypeData)
      PartTypeData data = GrpcFactory.buildGrpcPartTypeData(1L, desc, new ArrayList<>());

      // Prompt gRPC to register the PartType:
      PartTypeData createdPartType = stub.registerPartType(data);

      // Convert, and return, the PartType that was added to the DB into an application compatible format:
      return grpcPartTypeDataConverter.convertToPartTypeDto(createdPartType);

    } catch (StatusRuntimeException e) {
      throw new CreateFailedException("Failed to register PartType with desc '" + desc + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public PartTypeDto readPartType(long typeId) throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      PartTypeServiceGrpc.PartTypeServiceBlockingStub partTypeStub = PartTypeServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of PartTypeId (PartTypeId)
      PartTypeId id = LongId_ToGrpc_Id.convertToPartTypeId(typeId);

      // Prompt gRPC to read the PartType:
      PartTypeData foundPartType = partTypeStub.readPartType(id);

      // Convert the PartTypeData that was read from the DB into java compatible format:
      return grpcPartTypeDataConverter.convertToPartTypeDto(foundPartType);

    } catch (StatusRuntimeException e) {
      throw new NotFoundException("No PartType found with id '" + typeId + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Transactional
  @Override public void updatePartType(PartTypeDto data) throws UpdateFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      PartTypeServiceGrpc.PartTypeServiceBlockingStub partTypeStub = PartTypeServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of PartType (Convert PartType to PartTypeData)
      PartTypeData partType = partTypeDtoConverter.convertToPartTypeData(data);

      // Prompt gRPC to update the Animal:
      EmptyMessage updated = partTypeStub.updatePartType(partType);

      if(updated == null && data != null)
        throw new UpdateFailedException("Failed to update PartType with id '" + data.getTypeId() + "'");

    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No PartType found with id '" + data.getTypeId() + "'");

      if(e.getStatus().equals(INTERNAL))
        throw new UpdateFailedException("Critical Error encountered. Failed to Update PartType with id '" + data.getTypeId() + "' (" + e.getMessage() + ")");

    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Transactional
  @Override public boolean removePartType(long typeId) throws DeleteFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      PartTypeServiceGrpc.PartTypeServiceBlockingStub partTypeStub = PartTypeServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Attempt to find an PartType with the given typeId:
      PartTypeDto partType = readPartType(typeId);

      // Create a gRPC compatible version of PartType (Convert PartType to PartTypeData)
      PartTypeData partTypeData = partTypeDtoConverter.convertToPartTypeData(partType);

      // Prompt gRPC to delete the PartType:
      EmptyMessage deleted = partTypeStub.removePartType(partTypeData);

      if(deleted == null && partTypeData != null)
        throw new DeleteFailedException("Failed to delete PartType with id '" + typeId);

      return true;
    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No PartType found with id '" + typeId + "'");
      else
        throw new DeleteFailedException("Critical Error encountered. Failed to delete PartType with id '" + typeId + "' (" + e.getMessage() + ")");
    }
    finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public List<PartTypeDto> getAllPartTypes() throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      PartTypeServiceGrpc.PartTypeServiceBlockingStub partTypeStub = PartTypeServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Prompt gRPC to retrieve the AnimalParts from the Database:
      PartTypesData partTypesData = partTypeStub.getAllPartTypes(GrpcFactory.buildGrpcEmptyMessage());

      // Convert received data to java language:
      return grpcPartTypeDataConverter.convertToPartTypeDtoList(partTypesData);

    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No PartTypes found in database");
      else
        throw new RuntimeException("Critical Error encountered. Failed to Query all PartTypes from the Database (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }
}
