package Client.Station1_AnimalRegistration.network.services.gRPC;

import Client.common.model.adapters.gRPC_to_java.GrpcAnimalData_To_AnimalDto;
import Client.common.model.adapters.java_to_gRPC.AnimalDto_ToGrpc_AnimalData;
import Client.common.services.gRPC.GrpcConnection;
import grpc.*;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.AnimalDto;
import shared.model.exceptions.persistance.NotFoundException;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import Client.common.model.adapters.GrpcFactory;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static io.grpc.Status.INTERNAL;
import static io.grpc.Status.NOT_FOUND;

public class AnimalRegistrationServiceImpl extends GrpcConnection implements AnimalRegistrationService
{
  private final GrpcAnimalData_To_AnimalDto grpcAnimalDataConverter = new GrpcAnimalData_To_AnimalDto();
  private final AnimalDto_ToGrpc_AnimalData animalDtoConverter = new AnimalDto_ToGrpc_AnimalData();

  public AnimalRegistrationServiceImpl(String grpcServerAddress, int grpcServerPort) {
    super(grpcServerAddress, grpcServerPort);
  }


  @Override public AnimalDto registerNewAnimal(BigDecimal weightInKilogram, String origin, Date arrivalDate) throws CreateFailedException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalServiceGrpc.AnimalServiceBlockingStub animalStub = AnimalServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Animal (AnimalData)
      AnimalData data = GrpcFactory.buildGrpcAnimalData(1, weightInKilogram, origin, arrivalDate, new ArrayList<>());

      // Prompt gRPC to register the Animal:
      AnimalData createdAnimal = animalStub.registerAnimal(data);

      // Convert response to java format:
      AnimalDto response = grpcAnimalDataConverter.convertToAnimalDto(createdAnimal);

      // Read and return the created Animal to get the latest changes:
      return response;

    } catch (StatusRuntimeException e) {
      //e.printStackTrace();
      throw new CreateFailedException("Failed to register animal with weight '" + weightInKilogram + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public AnimalDto readAnimal(long animalId) throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalServiceGrpc.AnimalServiceBlockingStub animalStub = AnimalServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of AnimalId (AnimalData)
      AnimalId id = LongId_ToGrpc_Id.convertToAnimalId(animalId);

      // Prompt gRPC to read the Animal:
      AnimalData foundAnimal = animalStub.readAnimal(id);

      // Convert and return the AnimalData that was read from the DB into an application compatible format:
      return grpcAnimalDataConverter.convertToAnimalDto(foundAnimal);

    } catch (StatusRuntimeException e) {
      e.printStackTrace();
      throw new NotFoundException("No animal found with id '" + animalId + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public void updateAnimal(AnimalDto data) throws UpdateFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalServiceGrpc.AnimalServiceBlockingStub animalStub = AnimalServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Animal (Convert Animal to AnimalData)
      AnimalData animal = animalDtoConverter.convertToAnimalData(data);

      // Prompt gRPC to update the Animal:
      EmptyMessage updated = animalStub.updateAnimal(animal);

      if(updated == null && data != null)
        throw new UpdateFailedException("Failed to update Animal with id '" + data.getAnimalId() + "'");

    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No animal found with id '" + data.getAnimalId() + "'");

      if(e.getStatus().equals(INTERNAL))
        throw new UpdateFailedException("Critical Error encountered. Failed to Update Animal with id '" + data.getAnimalId() + "' (" + e.getMessage() + ")");
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
      AnimalServiceGrpc.AnimalServiceBlockingStub animalStub = AnimalServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Attempt to find an Animal with the given animal_id:
      AnimalDto animal = readAnimal(animal_id);

      // Create a gRPC compatible version of animal (Convert Animal to AnimalData)
      AnimalData animalData = animalDtoConverter.convertToAnimalData(animal);

      // Prompt gRPC to delete the Animal:
      EmptyMessage deleted = animalStub.removeAnimal(animalData);

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


  @Override public List<AnimalDto> getAllAnimals() throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      AnimalServiceGrpc.AnimalServiceBlockingStub animalStub = AnimalServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Prompt gRPC to retrieve all Animals from Database:
      AnimalsData animalsData = animalStub.getAllAnimals(GrpcFactory.buildGrpcEmptyMessage());

      // Convert and return the received data:
      return grpcAnimalDataConverter.convertToAnimalDtoList(animalsData);

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
