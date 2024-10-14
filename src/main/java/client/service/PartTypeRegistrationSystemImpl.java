package client.service;

import client.interfaces.PartTypeRegistrationSystem;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import server.controller.grpc.GrpcFactory;
import server.controller.grpc.grpc_to_java.GrpcPartTypeData_To_PartType;
import server.controller.grpc.java_to_gRPC.LongId_ToGrpc_Id;
import server.controller.grpc.java_to_gRPC.PartType_ToGrpc_PartTypeData;
import shared.model.entities.PartType;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.UpdateFailedException;

import java.util.List;

import static io.grpc.Status.INTERNAL;
import static io.grpc.Status.NOT_FOUND;

public class PartTypeRegistrationSystemImpl extends Client implements PartTypeRegistrationSystem
{
  public PartTypeRegistrationSystemImpl(String host, int port) {
    super(host, port);
  }


  @Override public PartType registerNewPartType(String desc) throws CreateFailedException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of PartType (PartTypeData)
      PartTypeData data = GrpcFactory.buildGrpcPartTypeData(desc);

      // Prompt gRPC to register the PartType:
      PartTypeData createdPartType = stub.registerPartType(data);

      // Convert, and return, the PartType that was added to the DB into an application compatible format:
      return GrpcPartTypeData_To_PartType.convertToPartType(createdPartType);

    } catch (StatusRuntimeException e) {
      throw new CreateFailedException("Failed to register PartType with desc '" + desc + "'");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public PartType readPartType(long typeId) throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of PartTypeId (PartTypeId)
      PartTypeId id = LongId_ToGrpc_Id.convertToPartTypeId(typeId);

      // Prompt gRPC to read the PartType:
      PartTypeData foundPartType = stub.readPartType(id);

      // Convert, and return, the PartTypeData that was read from the DB into an application compatible format:
      return GrpcPartTypeData_To_PartType.convertToPartType(foundPartType);

    } catch (StatusRuntimeException e) {
      throw new NotFoundException("No PartType found with id '" + typeId);
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public void updatePartType(PartType data) throws UpdateFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of PartType (Convert PartType to PartTypeData)
      PartTypeData partType = PartType_ToGrpc_PartTypeData.convertToPartTypeData(data);

      // Prompt gRPC to update the Tray:
      EmptyMessage updated = stub.updatePartType(partType);

      if(updated == null && data != null)
        throw new UpdateFailedException("Failed to update PartType with id '" + data.getTypeId() + "'");

    } catch (StatusRuntimeException e) {
      if(e.getStatus().equals(NOT_FOUND))
        throw new NotFoundException("No PartType found with id '" + data.getTypeId() + "'");

      if(e.getStatus().equals(INTERNAL))
        throw new UpdateFailedException("Critical Error encountered. Failed to Update PartType with id '" + data.getTypeId() + "'");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public boolean removePartType(long typeId) throws DeleteFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Attempt to find an PartType with the given typeId:
      PartType partType = readPartType(typeId);

      // Create a gRPC compatible version of PartType (Convert PartType to PartTypeData)
      PartTypeData partTypeData = PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType);

      // Prompt gRPC to delete the PartType:
      EmptyMessage deleted = stub.removePartType(partTypeData);

      if(deleted == null && partTypeData != null)
        throw new DeleteFailedException("Failed to delete PartType with id '" + typeId);

      return true;
    } catch (StatusRuntimeException e) {
      if(e.getStatus().equals(NOT_FOUND))
        throw new NotFoundException("No PartType found with id '" + typeId + "'");
      else
        throw new DeleteFailedException("Critical Error encountered. Failed to delete PartType with id '" + typeId + "'");
    }
    finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public List<PartType> getAllPartTypes() throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Prompt gRPC to retrieve the AnimalParts from the Database:
      PartTypesData partTypes = stub.getAllPartTypes(GrpcFactory.buildGrpcEmptyMessage());

      // Convert, and return, the PartTypeData that was added to the DB into an application compatible format:
      return GrpcPartTypeData_To_PartType.convertToPartTypeList(partTypes);

    } catch (StatusRuntimeException e) {
      if(e.getStatus().equals(NOT_FOUND))
        throw new NotFoundException("No PartTypes found in database");
      else
        throw new RuntimeException("Critical Error encountered. Failed to Query all PartTypes from the Database");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }
}
