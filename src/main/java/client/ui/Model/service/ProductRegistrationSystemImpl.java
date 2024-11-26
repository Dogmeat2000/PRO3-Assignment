package client.ui.Model.service;

import client.interfaces.ProductRegistrationSystem;
import client.ui.Model.adapters.gRPC_to_java.GrpcProductData_To_ProductDto;
import client.ui.Model.adapters.java_to_gRPC.ProductDto_ToGrpc_ProductData;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import client.ui.Model.adapters.GrpcFactory;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.AnimalPartDto;
import shared.model.dto.ProductDto;
import shared.model.dto.TrayDto;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.List;

import static io.grpc.Status.INTERNAL;
import static io.grpc.Status.NOT_FOUND;

public class ProductRegistrationSystemImpl extends Client implements ProductRegistrationSystem
{
  private final GrpcProductData_To_ProductDto grpcProductDataConverter = new GrpcProductData_To_ProductDto();
  private final ProductDto_ToGrpc_ProductData productConverter = new ProductDto_ToGrpc_ProductData();

  public ProductRegistrationSystemImpl(String host, int port) {
    super(host, port);
  }


  @Override
  public ProductDto registerNewProduct(List<AnimalPartDto> animalPartContentList, List<TrayDto> receivedPartsFromTrayList) throws CreateFailedException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      ProductServiceGrpc.ProductServiceBlockingStub productStub = ProductServiceGrpc.newBlockingStub(channel);
      ProductData data = GrpcFactory.buildGrpcProductData(animalPartContentList, receivedPartsFromTrayList);

      System.out.println("\n\n[ProductRegistrationSystemImpl] Line 1");

      // Prompt gRPC to register the ProductData:
      ProductData createdProduct = productStub.registerProduct(data);

      System.out.println("\n\n[ProductRegistrationSystemImpl] Line 2");

      // Convert the ProductData that was read from the DB into an application compatible format:
      return grpcProductDataConverter.convertToProductDto(createdProduct);

    } catch (StatusRuntimeException e) {
      throw new CreateFailedException("Failed to register Product (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override
  public ProductDto readProduct(long productId) throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      ProductServiceGrpc.ProductServiceBlockingStub productStub = ProductServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of productId (ProductId)
      ProductId id = LongId_ToGrpc_Id.convertToProductId(productId);

      // Prompt gRPC to read the PartType:
      ProductData foundProduct = productStub.readProduct(id);

      // Convert the TrayData that was read from the DB into an application compatible format:
      return grpcProductDataConverter.convertToProductDto(foundProduct);

    } catch (StatusRuntimeException e) {
      throw new NotFoundException("No Product found with id '" + productId + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override
  public void updateProduct(ProductDto data) throws UpdateFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      ProductServiceGrpc.ProductServiceBlockingStub productStub = ProductServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Product (Convert Product to ProductData)
      ProductData product = productConverter.convertToProductData(data);

      // Prompt gRPC to update the Product:
      EmptyMessage updated = productStub.updateProduct(product);

      if(updated == null && data != null)
        throw new UpdateFailedException("Failed to update Product with id '" + data.getProductId() + "'");

    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No Product found with id '" + data.getProductId() + "'");

      if(e.getStatus().equals(INTERNAL))
        throw new UpdateFailedException("Critical Error encountered. Failed to Update Product with id '" + data.getProductId() + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override
  public boolean removeProduct(long productId) throws DeleteFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      ProductServiceGrpc.ProductServiceBlockingStub productStub = ProductServiceGrpc.newBlockingStub(channel);
      TrayServiceGrpc.TrayServiceBlockingStub trayStub = TrayServiceGrpc.newBlockingStub(channel);
      AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Attempt to find an PartType with the given typeId:
      ProductDto product = readProduct(productId);

      // Create a gRPC compatible version of PartType (Convert PartType to PartTypeData)
      ProductData productData = productConverter.convertToProductData(product);

      // Prompt gRPC to delete the PartType:
      EmptyMessage deleted = productStub.removeProduct(productData);

      if(deleted == null && productData != null)
        throw new DeleteFailedException("Failed to delete Product with id '" + productId + "'");

      // Check if there are any remaining AnimalParts associated with this Product, if so update these:
      // TODO: Shouldn't be needed with JPA.
      /*if(!product.getAnimalPartIdList().isEmpty()) {
        // Prompt gRPC to update the AnimalPart:
        for (long animalPartId : product.getAnimalPartIdList()) {
          animalPart.setProduct(null);
          animalPartStub.updateAnimalPart(animalPartConverter.convertToAnimalPartData(animalPart, maxNestingDepth));

          if(deleted == null)
            throw new UpdateFailedException("Failed to update AnimalPart with id '" + animalPart.getPartId() + "' associated with Product_id '" + productId + "'");
        }
      }*/

      // Check if there are any remaining Trays associated with this Product, if so update these:
      // TODO: Shouldn't be needed with JPA.
      /*if(!product.getTraySuppliersList().isEmpty()) {
        // Prompt gRPC to delete the Product:
        for (Tray tray : product.getTraySuppliersList()) {
          tray.getProductList().remove(product);
          trayStub.updateTray(trayConverter.convertToTrayData(tray,maxNestingDepth));

          if(deleted == null)
            throw new UpdateFailedException("Failed to update Tray with id '" + product.getProductId() + "' associated with Tray_id '" + productId + "'");
        }
      }*/

      return true;
    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No Product found with id '" + productId + "'");
      else
        throw new DeleteFailedException("Critical Error encountered. Failed to delete Product with id '" + productId + "' (" + e.getMessage() + ")");
    }
    finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override
  public List<ProductDto> getAllProducts() throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      ProductServiceGrpc.ProductServiceBlockingStub productStub = ProductServiceGrpc.newBlockingStub(channel);
      //TrayServiceGrpc.TrayServiceBlockingStub trayStub = TrayServiceGrpc.newBlockingStub(channel);
      //AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);

      // Prompt gRPC to retrieve the AnimalParts from the Database:
      ProductsData productsData = productStub.getAllProducts(GrpcFactory.buildGrpcEmptyMessage());

      // Convert received data to java language:
      return grpcProductDataConverter.convertToProductDtoList(productsData);

      // Populate each Product with the proper relationships, to have a proper Object Relational Model.
      // Object relations are lost during gRPC conversion (due to cyclic relations, i.e. both Product and AnimalPart have relations to each other), so must be repopulated:
      // TODO: Instead of accepting significant dataloss, instead refactor adapters/converters and define a max-nesting depth, so that at least 2-3 levels of objects get transferred correctly.
      /*for (Product product : products) {
        try {
          // Read all AnimalParts associated with this Product:
          AnimalPartsData animalPartsData = animalPartStub.readAnimalPartsByProductId(LongId_ToGrpc_Id.convertToProductId(product.getProductId()));

          // Convert to java language, and attach to Product Object:
          product.getContentList().clear();
          product.setAnimalParts(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPartList(animalPartsData));
        } catch (StatusRuntimeException e) {
          if(!e.getStatus().getCode().equals(NOT_FOUND.getCode()))
            // No AnimalParts found assigned to this Product:
            throw new RuntimeException("Critical Error encountered. Failed to Query for all AnimalParts associated with Product_id '" + product.getProductId() + "' (" + e.getMessage() + ")");
        }

        try {
          // Read all Trays associated with this Product:
          TrayData trayData;
          product.setTraySuppliersList(new ArrayList<>());
          for (TrayToProductTransfer transfer : product.getTraySupplyJoinList()){
            trayData = trayStub.readTray(LongId_ToGrpc_Id.convertToTrayId(transfer.getTray().getTrayId()));

            // Convert to java language, and attach to Product Object:
            product.getTraySuppliersList().add(GrpcTrayData_To_Tray.convertToTray(trayData,3));
          }

        } catch (StatusRuntimeException e) {
          if(!e.getStatus().getCode().equals(NOT_FOUND.getCode()))
            // No Products found assigned to this Tray:
            throw new RuntimeException("Critical Error encountered. Failed to Query for all Trays associated with Product_id '" + product.getProductId() + "' (" + e.getMessage() + ")");
        }
      }*/

      // Return data
      //return products;

    } catch (StatusRuntimeException e) {
      if(e.getStatus().getCode().equals(NOT_FOUND.getCode()))
        throw new NotFoundException("No Products found in database");
      else
        throw new RuntimeException("Critical Error encountered. Failed to Query all Products from the Database (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }
}
