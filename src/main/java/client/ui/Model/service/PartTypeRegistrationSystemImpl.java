package client.ui.Model.service;

import client.interfaces.PartTypeRegistrationSystem;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import jakarta.transaction.Transactional;
import server.controller.grpc.GrpcFactory;
import server.controller.grpc.grpc_to_java.GrpcAnimalPartData_To_AnimalPart;
import server.controller.grpc.grpc_to_java.GrpcPartTypeData_To_PartType;
import server.controller.grpc.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData;
import server.controller.grpc.java_to_gRPC.Animal_ToGrpc_AnimalData;
import server.controller.grpc.java_to_gRPC.LongId_ToGrpc_Id;
import server.controller.grpc.java_to_gRPC.PartType_ToGrpc_PartTypeData;
import shared.model.entities.AnimalPart;
import shared.model.entities.PartType;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.UpdateFailedException;

import java.util.ArrayList;
import java.util.List;

import static io.grpc.Status.INTERNAL;
import static io.grpc.Status.NOT_FOUND;

public class PartTypeRegistrationSystemImpl extends Client implements PartTypeRegistrationSystem
{
  public PartTypeRegistrationSystemImpl(String host, int port) {
    super(host, port);
  }


  @Transactional
  @Override public PartType registerNewPartType(String desc) throws CreateFailedException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      PartTypeServiceGrpc.PartTypeServiceBlockingStub stub = PartTypeServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of PartType (PartTypeData)
      PartTypeData data = GrpcFactory.buildGrpcPartTypeData(desc, new ArrayList<>());

      // Prompt gRPC to register the PartType:
      PartTypeData createdPartType = stub.registerPartType(data);

      // Convert, and return, the PartType that was added to the DB into an application compatible format:
      return GrpcPartTypeData_To_PartType.convertToPartType(createdPartType);

    } catch (StatusRuntimeException e) {
      throw new CreateFailedException("Failed to register PartType with desc '" + desc + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Transactional
  @Override public PartType readPartType(long typeId) throws NotFoundException {
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
      PartType partType = GrpcPartTypeData_To_PartType.convertToPartType(foundPartType);

      // Populate PartType with the proper relationships, to have a proper Object Relational Model.
      // Object relations are lost during gRPC conversion (due to cyclic relations, i.e. both PartType and AnimalPart have relations to each other), so must be repopulated:
      try {
        // Read all animalParts associated with this PartType:
        AnimalPartsData animalPartsData = animalPartStub.readAnimalPartsByPartTypeId(LongId_ToGrpc_Id.convertToPartTypeId(partType.getTypeId()));

        // Convert to java language, and attach to PartType Object:
        partType.setAnimalParts(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPartList(animalPartsData));
      } catch (StatusRuntimeException e) {
        if(!e.getStatus().getCode().equals(NOT_FOUND.getCode()))
          // No AnimalParts found with this PartType:
          throw new RuntimeException("Critical Error encountered. Failed to Query for all AnimalParts associated with partType_id '" + partType.getTypeId() + "' (" + e.getMessage() + ")");
      }

      // Return:
      return partType;

    } catch (StatusRuntimeException e) {
      throw new NotFoundException("No PartType found with id '" + typeId + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Transactional
  @Override public void updatePartType(PartType data) throws UpdateFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      PartTypeServiceGrpc.PartTypeServiceBlockingStub partTypeStub = PartTypeServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of PartType (Convert PartType to PartTypeData)
      PartTypeData partType = PartType_ToGrpc_PartTypeData.convertToPartTypeData(data);

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
      PartType partType = readPartType(typeId);

      // Create a gRPC compatible version of PartType (Convert PartType to PartTypeData)
      PartTypeData partTypeData = PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType);

      // Prompt gRPC to delete the PartType:
      EmptyMessage deleted = partTypeStub.removePartType(partTypeData);

      if(deleted == null && partTypeData != null)
        throw new DeleteFailedException("Failed to delete PartType with id '" + typeId);

      // Check if there are any remaining AnimalParts associated with this PartType, if so delete these too:
      if(!partType.getAnimalPartIdList().isEmpty()) {
        // Prompt gRPC to delete the AnimalPart:
        for (AnimalPart animalPart : partType.getPartList()) {
          deleted = animalPartStub.removeAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart));

          if(deleted == null && animalPart != null)
            throw new DeleteFailedException("Failed to delete AnimalPart with id '" + animalPart.getPart_id() + "' associated with PartType_id '" + typeId + "'");
        }
      }

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


  @Transactional
  @Override public List<PartType> getAllPartTypes() throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      PartTypeServiceGrpc.PartTypeServiceBlockingStub partTypeStub = PartTypeServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Prompt gRPC to retrieve the AnimalParts from the Database:
      PartTypesData partTypesData = partTypeStub.getAllPartTypes(GrpcFactory.buildGrpcEmptyMessage());

      // Convert received data to java language:
      List<PartType> partTypes = GrpcPartTypeData_To_PartType.convertToPartTypeList(partTypesData);

      // Populate each PartType with the proper relationships, to have a proper Object Relational Model.
      // Object relations are lost during gRPC conversion (due to cyclic relations, i.e. both PartType and AnimalPart have relations to each other), so must be repopulated:
      for (PartType partType : partTypes) {
        try {
          // Read all animalParts associated with this PartType:
          AnimalPartsData animalPartsData = animalPartStub.readAnimalPartsByPartTypeId(LongId_ToGrpc_Id.convertToPartTypeId(partType.getTypeId()));

          // Convert to java language, and attach to PartType Object:
          partType.setAnimalParts(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPartList(animalPartsData));
        } catch (StatusRuntimeException e) {
          if(!e.getStatus().getCode().equals(NOT_FOUND.getCode()))
            // No AnimalParts found with this PartType:
            throw new RuntimeException("Critical Error encountered. Failed to Query for all AnimalParts associated with partType_id '" + partType.getTypeId() + "' (" + e.getMessage() + ")");
        }
      }

      // Return data
      return partTypes;

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
