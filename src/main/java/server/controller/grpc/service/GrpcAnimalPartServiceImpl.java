package server.controller.grpc.service;

import grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import server.controller.grpc.adapters.grpc_to_java.GrpcAnimalPartData_To_AnimalPart;
import server.model.persistence.entities.*;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import server.controller.grpc.adapters.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData;
import server.model.persistence.service.*;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.List;

@GrpcService
public class GrpcAnimalPartServiceImpl extends AnimalPartServiceGrpc.AnimalPartServiceImplBase
{
  private final AnimalPartRegistryInterface animalPartService;
  /*private final ProductRegistryInterface productService;
  private final TrayRegistryInterface trayService;
  private final AnimalRegistryInterface animalService;
  private final PartTypeRegistryInterface partTypeService;*/
  private final GrpcAnimalPartData_To_AnimalPart grpcAnimalPartDataConverter;
  private final AnimalPart_ToGrpc_AnimalPartData animalPartConverter = new AnimalPart_ToGrpc_AnimalPartData();
  //private final int maxNestingDepth;

  @Autowired
  public GrpcAnimalPartServiceImpl(AnimalPartRegistryInterface animalPartService,
      AnimalRegistryInterface animalService,
      PartTypeRegistryInterface partTypeService,
      TrayRegistryInterface trayService,
      ProductRegistryInterface productService,
      GrpcAnimalPartData_To_AnimalPart grpcAnimalPartDataConverter/*,
      @Value("${maxNestingDepth}") int maxNestingDepth*/) {
    super();
    this.animalPartService = animalPartService;
    /*this.animalService = animalService;
    this.partTypeService = partTypeService;
    this.trayService = trayService;
    this.productService = productService;*/
    this.grpcAnimalPartDataConverter = grpcAnimalPartDataConverter;
    //this.maxNestingDepth = maxNestingDepth;
  }


  @Override
  public void registerAnimalPart(AnimalPartData request, StreamObserver<AnimalPartData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types
      AnimalPart animalPartReceived = grpcAnimalPartDataConverter.convertToAnimalPart(request);

      // Query database for the data lost during gRPC transmission:
      // Read associated Tray Data:  // TODO: Shouldn't be needed with the new adjusted converters.
      /*Tray associatedTray = trayService.readTray(animalPartReceived.getTray().getTrayId());

      // Read associated Animal Data:
      Animal associatedAnimal = animalService.readAnimal(animalPartReceived.getAnimal().getId());

      // Read associated PartType Data:
      PartType associatedPartType = partTypeService.readPartType(animalPartReceived.getType().getTypeId());

      // Read associated PartType Data:
      Product associatedProduct = null;
      if(animalPartReceived.getProduct() != null && animalPartReceived.getProduct().getProductId() > 0)
        associatedProduct = productService.readProduct(animalPartReceived.getProduct().getProductId());

      // Assign the entities with proper data:
      animalPartReceived.setType(associatedPartType);
      animalPartReceived.setProduct(associatedProduct);
      animalPartReceived.setAnimal(associatedAnimal);
      animalPartReceived.setTray(associatedTray);*/

      // Register the AnimalPart:
      AnimalPart createdAnimalPart = animalPartService.registerAnimalPart(animalPartReceived);
      //System.out.println("[GprcAnimalPartService] Registered this AnimalPart: " + createdAnimalPart);

      // If AnimalPart creation fails
      if (createdAnimalPart == null)
        throw new CreateFailedException("AnimalPart could not be created");

      // Update associated entities:
      // Parent Animal:
      /*Animal parentAnimal = createdAnimalPart.getAnimal();
      if(!parentAnimal.getAnimalPartList().contains(createdAnimalPart))
        parentAnimal.addAnimalPart(createdAnimalPart);
      animalService.updateAnimal(parentAnimal);

      // Parent Tray:
      Tray parentTray = createdAnimalPart.getTray();
      if(!parentTray.getAnimalPartList().contains(createdAnimalPart)) {
        parentTray.addAnimalPart(createdAnimalPart);
        //parentTray.setWeight_kilogram(parentTray.getWeight_kilogram().add(createdAnimalPart.getWeight_kilogram()));
      }
      trayService.updateTray(parentTray);

      // Parent PartType:
      PartType parentPartType = createdAnimalPart.getType();
      if(!parentPartType.getAnimalPartList().contains(createdAnimalPart))
        parentPartType.addAnimalPart(createdAnimalPart);
      partTypeService.updatePartType(parentPartType);

      // Parent Product:
      if(createdAnimalPart.getProduct() != null && createdAnimalPart.getProduct().getProductId() != 0) {
        Product parentProduct = createdAnimalPart.getProduct();
        if(!parentProduct.getAnimalPartList().contains(createdAnimalPart))
          parentProduct.addAnimalPart(createdAnimalPart);
        productService.updateProduct(parentProduct);
      }*/

      //System.out.println("[GprcAnimalPartService] Registered this AnimalPart 2: " + createdAnimalPart);
      //System.out.println("[GprcAnimalPartService] Converting this AnimalPart 3: " + grpcAnimalPartDataConverter.convertToAnimalPart(animalPartConverter.convertToAnimalPartData(createdAnimalPart, maxNestingDepth), maxNestingDepth));

      // Translate the created Product into a gRPC compatible type, before transmitting back to client:
      responseObserver.onNext(animalPartConverter.convertToAnimalPartData(createdAnimalPart));
      responseObserver.onCompleted();
    } catch (Exception e) {
      e.printStackTrace(); // TODO: DELETE LINE
      responseObserver.onError(Status.INTERNAL.withDescription("Error registering AnimalPart, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readAnimalPart(AnimalPartId request, StreamObserver<AnimalPartData> responseObserver) {
    try {
      // Attempt to read the AnimalPart with the provided ID:
      AnimalPart animalPart = animalPartService.readAnimalPart(GrpcId_To_LongId.ConvertToLongId(request));
      //System.out.println("[GprcAnimalPartService] Read this AnimalPart 1: " + animalPart);

      // If AnimalPart read failed:
      if (animalPart == null)
        throw new NotFoundException("AnimalPart not found");

      // Translate the found AnimalPart into a gRPC compatible type, before transmitting back to client:
      //System.out.println("[GprcAnimalPartService] Sending this back this AnimalPart 2: " + animalPartConverter.convertToAnimalPartData(animalPart, maxNestingDepth));
      responseObserver.onNext(animalPartConverter.convertToAnimalPartData(animalPart));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("AnimalPart with id '" + request.getAnimalPartId() + "' not found in DB").withCause(e).asRuntimeException());
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
      responseObserver.onNext(animalPartConverter.convertToAnimalPartsDataList(animalParts));
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
      responseObserver.onNext(animalPartConverter.convertToAnimalPartsDataList(animalParts));
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
      responseObserver.onNext(animalPartConverter.convertToAnimalPartsDataList(animalParts));
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
      responseObserver.onNext(animalPartConverter.convertToAnimalPartsDataList(animalParts));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription(e.getMessage()).withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all AnimalParts associated with tray_id '" + request.getTrayId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void updateAnimalPart(AnimalPartData request, StreamObserver<EmptyMessage> responseObserver) {
    try {

      // Translate received gRPC information from the client, into Java compatible types:
      AnimalPart modifiedAnimalPart = grpcAnimalPartDataConverter.convertToAnimalPart(request);

      // Query database for the data lost during gRPC transmission:
      // Read associated Tray Data:  // TODO: Shouldn't be needed with the new adjusted converters.
      /*Tray associatedTray = trayService.readTray(modifiedAnimalPart.getTray().getTrayId());

      // Read associated Animal Data:
      Animal associatedAnimal = animalService.readAnimal(modifiedAnimalPart.getAnimal().getId());

      // Read associated PartType Data:
      PartType associatedPartType = partTypeService.readPartType(modifiedAnimalPart.getType().getTypeId());

      // Read associated PartType Data:
      Product associatedProduct = null;
      if(modifiedAnimalPart.getProduct() != null && modifiedAnimalPart.getProduct().getProductId() > 0)
        associatedProduct = productService.readProduct(modifiedAnimalPart.getProduct().getProductId());

      // Assign the entities with proper data:
      modifiedAnimalPart.setType(associatedPartType);
      modifiedAnimalPart.setProduct(associatedProduct);
      modifiedAnimalPart.setAnimal(associatedAnimal);
      modifiedAnimalPart.setTray(associatedTray);*/

      // Attempt to update the AnimalPart with the provided ID:
      if (!animalPartService.updateAnimalPart(modifiedAnimalPart)) {
        // If AnimalPart update failed:
        throw new UpdateFailedException("Error occurred while updated AnimalPart with id='" + request.getAnimalPartId().getAnimalPartId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("AnimalPart not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error occurred while attempting to update AnimalPart with id '" + request.getAnimalPartId().getAnimalPartId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void removeAnimalPart(AnimalPartData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      AnimalPart animalPartReceived = grpcAnimalPartDataConverter.convertToAnimalPart(request);

      // Query database for the data lost during gRPC transmission:
      // Read associated Tray Data:  // TODO: Shouldn't be needed with the new adjusted converters.
      /*Tray associatedTray = trayService.readTray(animalPartReceived.getTray().getTrayId());

      // Read associated Animal Data:
      Animal associatedAnimal = animalService.readAnimal(animalPartReceived.getAnimal().getId());

      // Read associated PartType Data:
      PartType associatedPartType = partTypeService.readPartType(animalPartReceived.getType().getTypeId());

      // Read associated PartType Data:
      Product associatedProduct = null;
      if(animalPartReceived.getProduct() != null && animalPartReceived.getProduct().getProductId() > 0)
        associatedProduct = productService.readProduct(animalPartReceived.getProduct().getProductId());

      // Assign the entities with proper data:
      animalPartReceived.setType(associatedPartType);
      animalPartReceived.setProduct(associatedProduct);
      animalPartReceived.setAnimal(associatedAnimal);
      animalPartReceived.setTray(associatedTray);*/

      // Attempt to delete the AnimalPart with the provided ID:
      if(!animalPartService.removeAnimalPart(animalPartReceived)) {
        // If AnimalPart deletion failed:
        throw new DeleteFailedException("Error occurred while deleting AnimalPart with id='" + request.getAnimalPartId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("AnimalPart not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      e.printStackTrace(); // TODO: DELETE
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
      responseObserver.onNext(animalPartConverter.convertToAnimalPartsDataList(animalParts));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("No AnimalParts found").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all AnimalParts, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }
}
