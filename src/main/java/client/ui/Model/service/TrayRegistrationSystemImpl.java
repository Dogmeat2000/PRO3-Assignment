package client.ui.Model.service;

import client.interfaces.TrayRegistrationSystem;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import server.controller.grpc.adapters.GrpcFactory;
import server.controller.grpc.adapters.grpc_to_java.GrpcAnimalPartData_To_AnimalPart;
import server.controller.grpc.adapters.grpc_to_java.GrpcProductData_To_Product;
import server.controller.grpc.adapters.grpc_to_java.GrpcTrayData_To_Tray;
import server.controller.grpc.adapters.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData;
import server.controller.grpc.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import server.controller.grpc.adapters.java_to_gRPC.Product_ToGrpc_ProductData;
import server.controller.grpc.adapters.java_to_gRPC.Tray_ToGrpc_TrayData;
import shared.model.entities.*;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.grpc.Status.NOT_FOUND;
import static io.grpc.Status.INTERNAL;

public class TrayRegistrationSystemImpl extends Client implements TrayRegistrationSystem
{
  private final int maxNestingDepth;
  private final GrpcTrayData_To_Tray grpcTrayDataConverter = new GrpcTrayData_To_Tray();

  public TrayRegistrationSystemImpl(String host, int port, int maxNestingDepth) {
    super(host, port);
    this.maxNestingDepth = maxNestingDepth;
  }


  @Transactional
  @Override
  public Tray registerNewTray(BigDecimal maxWeight_kilogram, BigDecimal currentWeight_kilogram) throws CreateFailedException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      TrayServiceGrpc.TrayServiceBlockingStub trayStub = TrayServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Tray (TrayData)
      TrayData data = GrpcFactory.buildGrpcTrayData(maxWeight_kilogram, currentWeight_kilogram, new ArrayList<>(), new ArrayList<>());

      // Prompt gRPC to register the Tray:
      TrayData createdTray = trayStub.registerTray(data);

      // Convert, and return, the Tray that was added to the DB into an application compatible format:
      return grpcTrayDataConverter.convertToTray(createdTray,maxNestingDepth);

    } catch (StatusRuntimeException e) {
      throw new CreateFailedException("Failed to register Tray with maxWeight '" + maxWeight_kilogram + "kg' and currentWeight '" + currentWeight_kilogram + "kg'. (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override
  public Tray readTray(long trayId) throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      TrayServiceGrpc.TrayServiceBlockingStub trayStub = TrayServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);
      ProductServiceGrpc.ProductServiceBlockingStub productStub = ProductServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of trayId (TrayId)
      TrayId id = LongId_ToGrpc_Id.convertToTrayId(trayId);

      // Prompt gRPC to read the Tray:
      TrayData foundTrayData = trayStub.readTray(id);

      // Convert the TrayData that was read from the DB into an application compatible format:
      Tray tray = grpcTrayDataConverter.convertToTray(foundTrayData,maxNestingDepth);

      // Populate Tray with the proper relationships, to have a proper Object Relational Model.
      // Object relations are lost during gRPC conversion (due to cyclic relations, i.e. both Tray and AnimalPart have relations to each other), so must be repopulated:
      // TODO: Instead of accepting significant dataloss, instead refactor adapters/converters and define a max-nesting depth, so that at least 2-3 levels of objects get transferred correctly.
      /*try {
        // Read all Products associated with this Tray:
        AnimalPartsData animalPartsData = animalPartStub.readAnimalPartsByTrayId(LongId_ToGrpc_Id.convertToTrayId(tray.getTrayId()));

        // Convert to java language, and attach to Tray Object:
        tray.clearAnimalPartContents();
        tray.addAllAnimalParts(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPartList(animalPartsData));
      } catch (StatusRuntimeException e) {
        if(!e.getStatus().getCode().equals(NOT_FOUND.getCode()))
          // No AnimalParts found assigned to this Tray:
          throw new RuntimeException("Critical Error encountered. Failed to Query for all AnimalParts associated with Tray_id '" + tray.getTrayId() + "' (" + e.getMessage() + ")");
      }

      try {
        // Read all Products associated with this Tray:
        ProductData productData;
        tray.setProductList(new ArrayList<>());
        for (TrayToProductTransfer transfer : tray.getTransferList()){
          productData = productStub.readProduct(LongId_ToGrpc_Id.convertToProductId(transfer.getProduct().getProductId()));

          // Convert to java language, and attach to Tray Object:
          tray.getProductList().add(GrpcProductData_To_Product.convertToProduct(productData, maxNestingDepth));
        }

      } catch (StatusRuntimeException e) {
        if(!e.getStatus().getCode().equals(NOT_FOUND.getCode()))
          throw new RuntimeException("Critical Error encountered. Failed to Query for all Products associated with Tray_id '" + tray.getTrayId() + "' (" + e.getMessage() + ")");
      }*/

      // Return:
      return tray;

    } catch (StatusRuntimeException e) {
      throw new NotFoundException("No Tray found with id '" + trayId + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Transactional
  @Override
  public void updateTray(Tray data) throws UpdateFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      TrayServiceGrpc.TrayServiceBlockingStub trayStub = TrayServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Tray (Convert Tray to TrayData)
      TrayData tray = Tray_ToGrpc_TrayData.convertToTrayData(data,maxNestingDepth);

      // Prompt gRPC to update the Tray:
      EmptyMessage updated = trayStub.updateTray(tray);

      if(updated == null && data != null)
        throw new UpdateFailedException("Failed to update Tray with id '" + data.getTrayId() + "'");

    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No Tray found with id '" + data.getTrayId() + "'");

      if(e.getStatus().equals(INTERNAL))
        throw new UpdateFailedException("Critical Error encountered. Failed to Update Tray with id '" + data.getTrayId() + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Transactional
  @Override
  public boolean removeTray(long trayId) throws DeleteFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      TrayServiceGrpc.TrayServiceBlockingStub trayStub = TrayServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);
      ProductServiceGrpc.ProductServiceBlockingStub productStub = ProductServiceGrpc.newBlockingStub(channel);

      // Attempt to find a Tray with the given trayId:
      Tray tray = readTray(trayId);

      // Create a gRPC compatible version of Tray (Convert Tray to TrayData)
      TrayData trayData = Tray_ToGrpc_TrayData.convertToTrayData(tray,maxNestingDepth);

      // Prompt gRPC to delete the Tray:
      EmptyMessage deleted = trayStub.removeTray(trayData);

      if(deleted == null && trayData != null)
        throw new DeleteFailedException("Failed to delete Tray with id '" + trayId + "'");

      // Check if there are any remaining AnimalParts associated with this Tray, if so delete these too:
      if(!tray.getAnimalPartIdList().isEmpty()) {
        // Prompt gRPC to delete the AnimalPart:
        for (AnimalPart animalPart : tray.getContents()) {
          deleted = animalPartStub.removeAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart));

          if(deleted == null && animalPart != null)
            throw new DeleteFailedException("Failed to delete AnimalPart with id '" + animalPart.getPart_id() + "' associated with Tray_id '" + trayId + "'");
        }
      }

      // Check if there are any remaining Products associated with this Tray, if so delete these too:
      if(!tray.getTransferList().isEmpty()) {
        // Prompt gRPC to delete the Product:
        for (Product product : tray.getProductList()) {
          deleted = productStub.removeProduct(Product_ToGrpc_ProductData.convertToProductData(product, maxNestingDepth));

          if(deleted == null && product != null)
            throw new DeleteFailedException("Failed to delete Product with id '" + product.getProductId() + "' associated with Tray_id '" + trayId + "'");
        }
      }

      return true;
    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No Tray found with id '" + trayId + "'");
      else
        throw new DeleteFailedException("Critical Error encountered. Failed to delete Tray with id '" + trayId + "' (" + e.getMessage() + ")");
    }
    finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override
  public List<Tray> getAllTrays() throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      TrayServiceGrpc.TrayServiceBlockingStub trayStub = TrayServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);
      ProductServiceGrpc.ProductServiceBlockingStub productStub = ProductServiceGrpc.newBlockingStub(channel);

      // Prompt gRPC to retrieve the Trays from the Database:
      TraysData traysData = trayStub.getAllTrays(GrpcFactory.buildGrpcEmptyMessage());

      // Convert received data to java language:
      List<Tray> trays = grpcTrayDataConverter.convertToTrayList(traysData, maxNestingDepth);

      // Populate each Tray with the proper relationships, to have a proper Object Relational Model.
      // Object relations are lost during gRPC conversion (due to cyclic relations, i.e. both Tray and AnimalPart have relations to each other), so must be repopulated:
      // TODO: Instead of accepting significant dataloss, instead refactor adapters/converters and define a max-nesting depth, so that at least 2-3 levels of objects get transferred correctly.
      /*for (Tray tray : trays) {
        try {
          // Read all AnimalParts associated with this Tray:
          AnimalPartsData animalPartsData = animalPartStub.readAnimalPartsByTrayId(LongId_ToGrpc_Id.convertToTrayId(tray.getTrayId()));

          // Convert to java language, and attach to Tray Object:
          tray.clearAnimalPartContents();
          tray.addAllAnimalParts(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPartList(animalPartsData));
        } catch (StatusRuntimeException e) {
          if(!e.getStatus().getCode().equals(NOT_FOUND.getCode()))
            // No AnimalParts found assigned to this Tray:
            throw new RuntimeException("Critical Error encountered. Failed to Query for all AnimalParts associated with Tray_id '" + tray.getTrayId() + "' (" + e.getMessage() + ")");
        }

        try {
          // Read all Products associated with this Tray:
          ProductData productData;
          tray.setProductList(new ArrayList<>());
          for (TrayToProductTransfer transfer : tray.getTransferList()){
            productData = productStub.readProduct(LongId_ToGrpc_Id.convertToProductId(transfer.getProduct().getProductId()));

            // Convert to java language, and attach to Tray Object:
            tray.getProductList().add(GrpcProductData_To_Product.convertToProduct(productData, maxNestingDepth));
          }

        } catch (StatusRuntimeException e) {
          if(!e.getStatus().getCode().equals(NOT_FOUND.getCode()))
            // No Products found assigned to this Tray:
            throw new RuntimeException("Critical Error encountered. Failed to Query for all Products associated with Tray_id '" + tray.getTrayId() + "' (" + e.getMessage() + ")");
        }
      }*/

      // Return data
      return trays;

    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No Trays found in database");
      else
        throw new RuntimeException("Critical Error encountered. Failed to Query all Trays from the Database (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }
}
