package client.service;

import client.interfaces.AnimalPartRegistrationSystem;
import grpc.AnimalPartData;
import grpc.SlaughterHouseServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import server.controller.grpc.GrpcFactory;
import server.controller.grpc.grpc_to_java.GrpcAnimalPartData_To_AnimalPart;
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
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of AnimalPart (AnimalPartData)
      AnimalPartData data = GrpcFactory.buildGrpcAnimalPartData(animal, type, tray, weightInKilogram);

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


  @Override public AnimalPart readAnimalPart(Animal animal, PartType type, Tray tray) throws NotFoundException {
    return null;
  }


  @Override public void updateAnimalPart(AnimalPart data) throws UpdateFailedException, NotFoundException {

  }


  @Override public boolean removeAnimalPart(AnimalPart data) throws DeleteFailedException, NotFoundException {
    return false;
  }


  @Override public List<AnimalPart> getAllAnimalParts() throws NotFoundException {
    return List.of();
  }
}