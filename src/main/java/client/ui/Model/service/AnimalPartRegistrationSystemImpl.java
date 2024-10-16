package client.ui.Model.service;

import client.interfaces.AnimalPartRegistrationSystem;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import server.controller.grpc.GrpcFactory;
import server.controller.grpc.grpc_to_java.GrpcAnimalPartData_To_AnimalPart;
import server.controller.grpc.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData;
import server.controller.grpc.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;
import shared.model.entities.PartType;
import shared.model.entities.Tray;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.UpdateFailedException;

import java.math.BigDecimal;
import java.util.List;

import static io.grpc.Status.INTERNAL;
import static io.grpc.Status.NOT_FOUND;

public class AnimalPartRegistrationSystemImpl extends Client implements AnimalPartRegistrationSystem
{
  public AnimalPartRegistrationSystemImpl(String host, int port) {
    super(host, port);
  }


  @Override public AnimalPart registerNewAnimalPart(Animal animal, PartType type, Tray tray, BigDecimal weightInKilogram) throws CreateFailedException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub stub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of AnimalPart (AnimalPartData)
      AnimalPartData data = GrpcFactory.buildGrpcAnimalPartData(1, animal, type, tray, weightInKilogram);

      // Prompt gRPC to register the AnimalPart:
      AnimalPartData createdAnimalPart = stub.registerAnimalPart(data);

      // Convert, and return, the AnimalData that was added to the DB into an application compatible format:
      return GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(createdAnimalPart);

    } catch (StatusRuntimeException e) {
      throw new CreateFailedException("Failed to register AnimalPart with weight '" + weightInKilogram + "'");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public AnimalPart readAnimalPart(long animalPartId, Animal animal, PartType type, Tray tray) throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub stub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of AnimalPart (AnimalPartId):
      AnimalPartId id = LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId, animal.getId(), type.getTypeId(), tray.getTray_id());

      // Prompt gRPC to read the AnimalPart:
      AnimalPartData foundAnimalPart = stub.readAnimalPart(id);

      // Convert, and return, the AnimalPartData that was read from the DB into an application compatible format:
      return GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(foundAnimalPart);

    } catch (StatusRuntimeException e) {
      throw new NotFoundException("No animalPart found with id '" + animalPartId + "'");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public void updateAnimalPart(AnimalPart oldData, AnimalPart newData) throws UpdateFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub stub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of AnimalPart (Convert AnimalPart to UpdatedAnimalPartData)
      UpdatedAnimalPartData updateData = AnimalPart_ToGrpc_AnimalPartData.covertToUpdatedAnimalPartData(oldData, newData);

      // Prompt gRPC to update the Animal:
      EmptyMessage updated = stub.updateAnimalPart(updateData);

      if(updated == null && updateData != null)
        throw new UpdateFailedException("Failed to update AnimalPart with id '" + oldData.getPart_id() + "'");

    } catch (StatusRuntimeException e) {
      if(e.getStatus().equals(NOT_FOUND))
        throw new NotFoundException("No AnimalPart found with id '" + oldData.getPart_id() + "'");

      if(e.getStatus().equals(INTERNAL))
        throw new UpdateFailedException("Critical Error encountered. Failed to Update AnimalPart with id '" + oldData.getPart_id() + "'");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public boolean removeAnimalPart(AnimalPart data) throws DeleteFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub stub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Attempt to find an Animal with the given animal_id:

      AnimalPart animalPart = readAnimalPart(data.getAnimal_id(), data.getAnimal(), data.getType(), data.getTray());

      // Create a gRPC compatible version of AnimalPart (Convert AnimalPart to AnimalPartData)
      AnimalPartData animalPartData = AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart);

      // Prompt gRPC to delete the AnimalPart:
      EmptyMessage deleted = stub.removeAnimalPart(animalPartData);

      if(deleted == null && animalPart != null)
        throw new DeleteFailedException("Failed to delete AnimalPart with id '" + data.getPart_id() + "'");

      return true;
    } catch (StatusRuntimeException e) {
      if(e.getStatus().equals(NOT_FOUND))
        throw new NotFoundException("No AnimalPart found with id '" + data.getPart_id() + "'");
      else
        throw new DeleteFailedException("Critical Error encountered. Failed to deleted AnimalPart with id '" + data.getPart_id() + "'");
    }
    finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public List<AnimalPart> getAllAnimalParts() throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub stub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Prompt gRPC to retrieve the AnimalParts from Database:
      AnimalPartsData animalParts = stub.getAnimalParts(GrpcFactory.buildGrpcEmptyMessage());

      // Convert, and return, the AnimalPartData that was added to the DB into an application compatible format:
      return GrpcAnimalPartData_To_AnimalPart.convertToAnimalPartList(animalParts);

    } catch (StatusRuntimeException e) {
      if(e.getStatus().equals(NOT_FOUND))
        throw new NotFoundException("No AnimalParts found in database");
      else
        throw new RuntimeException("Critical Error encountered. Failed to Query for all AnimalParts from the Database");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }
}
