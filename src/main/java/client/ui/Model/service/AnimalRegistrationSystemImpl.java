package client.ui.Model.service;

import client.interfaces.AnimalRegistrationSystem;
import grpc.*;
import server.controller.grpc.java_to_gRPC.Animal_ToGrpc_AnimalData;
import server.controller.grpc.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.exceptions.NotFoundException;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import server.controller.grpc.grpc_to_java.GrpcAnimalData_To_Animal;
import server.controller.grpc.GrpcFactory;
import shared.model.entities.Animal;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.UpdateFailedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.grpc.Status.INTERNAL;
import static io.grpc.Status.NOT_FOUND;

public class AnimalRegistrationSystemImpl extends Client implements AnimalRegistrationSystem
{
  public AnimalRegistrationSystemImpl(String host, int port) {
    super(host, port);
  }


  @Override public Animal registerNewAnimal(BigDecimal weightInKilogram) throws CreateFailedException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalServiceGrpc.AnimalServiceBlockingStub stub = AnimalServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Animal (AnimalData)
      AnimalData data = GrpcFactory.buildGrpcAnimal(weightInKilogram, new ArrayList<>());

      // Prompt gRPC to register the Animal:
      AnimalData createdAnimal = stub.registerAnimal(data);

      // Convert, and return, the AnimalData that was added to the DB into an application compatible format:
      return GrpcAnimalData_To_Animal.convertToAnimal(createdAnimal, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());

    } catch (StatusRuntimeException e) {
      throw new CreateFailedException("Failed to register animal with weight '" + weightInKilogram + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public Animal readAnimal(long animalId) throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalServiceGrpc.AnimalServiceBlockingStub stub = AnimalServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of AnimalId (AnimalData)
      AnimalId id = LongId_ToGrpc_Id.convertToAnimalId(animalId);

      // Prompt gRPC to read the Animal:
      AnimalData foundAnimal = stub.readAnimal(id);

      // Convert, and return, the AnimalData that was read from the DB into an application compatible format:
      return GrpcAnimalData_To_Animal.convertToAnimal(foundAnimal, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());

    } catch (StatusRuntimeException e) {
      throw new NotFoundException("No animal found with id '" + animalId + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public void updateAnimal(Animal data) throws UpdateFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalServiceGrpc.AnimalServiceBlockingStub stub = AnimalServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Animal (Convert Animal to AnimalData)
      AnimalData animal = Animal_ToGrpc_AnimalData.convertToAnimalData(data, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());

      // Prompt gRPC to update the Animal:
      EmptyMessage updated = stub.updateAnimal(animal);

      if(updated == null && data != null)
        throw new UpdateFailedException("Failed to update Animal with id '" + data.getId() + "'");

    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No animal found with id '" + data.getId() + "'");

      if(e.getStatus().equals(INTERNAL))
        throw new UpdateFailedException("Critical Error encountered. Failed to Update Animal with id '" + data.getId() + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public boolean removeAnimal(long animal_id) throws DeleteFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalServiceGrpc.AnimalServiceBlockingStub stub = AnimalServiceGrpc.newBlockingStub(channel);

      // Attempt to find an Animal with the given animal_id:
      Animal animal = readAnimal(animal_id);

      // Create a gRPC compatible version of animal (Convert Animal to AnimalData)
      AnimalData animalData = Animal_ToGrpc_AnimalData.convertToAnimalData(animal, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());

      // Prompt gRPC to delete the Animal:
      EmptyMessage deleted = stub.removeAnimal(animalData);

      if(deleted == null && animal != null)
        throw new DeleteFailedException("Failed to delete Animal with id '" + animal_id + "'");

      return true;
    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No animal found with id '" + animal_id + "'");
      else
        throw new DeleteFailedException("Critical Error encountered. Failed to deleted Animal with id '" + animal_id + "' (" + e.getMessage() + ")");
    }
    finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public List<Animal> getAllAnimals() throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalServiceGrpc.AnimalServiceBlockingStub stub = AnimalServiceGrpc.newBlockingStub(channel);

      // Prompt gRPC to retrieve the Animals from Database:
      AnimalsData animals = stub.getAllAnimals(GrpcFactory.buildGrpcEmptyMessage());

      // Convert, and return, the AnimalData that was added to the DB into an application compatible format:
      return GrpcAnimalData_To_Animal.convertToAnimalList(animals);

    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No animals found in database");
      else
        throw new RuntimeException("Critical Error encountered. Failed to Query for all Animals from the Database (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }
}
