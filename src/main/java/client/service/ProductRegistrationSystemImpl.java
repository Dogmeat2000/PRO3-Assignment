package client.service;

import client.interfaces.ProductRegistrationSystem;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import server.controller.grpc.GrpcFactory;
import server.controller.grpc.grpc_to_java.GrpcProductData_To_Product;
import server.controller.grpc.java_to_gRPC.LongId_ToGrpc_Id;
import server.controller.grpc.java_to_gRPC.Product_ToGrpc_ProductData;
import shared.model.entities.AnimalPart;
import shared.model.entities.Product;
import shared.model.entities.Tray;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.UpdateFailedException;

import java.util.ArrayList;
import java.util.List;

import static io.grpc.Status.INTERNAL;
import static io.grpc.Status.NOT_FOUND;

public class ProductRegistrationSystemImpl extends Client implements ProductRegistrationSystem
{
  public ProductRegistrationSystemImpl(String host, int port) {
    super(host, port);
  }


  @Override public Product registerNewProduct(List<AnimalPart> animalPartContentList, List<Tray> receivedPartsFromTrayList) throws CreateFailedException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Product (ProductData), with an initially empty list of Tray relations
      ProductData data = GrpcFactory.buildGrpcProductData(0, animalPartContentList, new ArrayList<>());

      // Prompt gRPC to register the ProductData:
      ProductData createdProduct = stub.registerProduct(data);

      // Add associations between this Product and all the specified Trays delivering parts:
      for (Tray tray : receivedPartsFromTrayList) {
        createdProduct.getTrayToProductTransfersListList().add(GrpcFactory.buildGrpcTrayToProductTransferData(GrpcProductData_To_Product.convertToProduct(createdProduct), tray));
      }

      // Update the repository with the updated connections:
      updateProduct(GrpcProductData_To_Product.convertToProduct(createdProduct));

      // Convert, and return, the ProductData that was added to the DB into an application compatible format:
      return GrpcProductData_To_Product.convertToProduct(createdProduct);

    } catch (StatusRuntimeException e) {
      throw new CreateFailedException("Failed to register Product");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public Product readProduct(long productId) throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of productId (ProductId)
      ProductId id = LongId_ToGrpc_Id.convertToProductId(productId);

      // Prompt gRPC to read the PartType:
      ProductData foundProduct = stub.readProduct(id);

      // Convert, and return, the PartTypeData that was read from the DB into an application compatible format:
      return GrpcProductData_To_Product.convertToProduct(foundProduct);

    } catch (StatusRuntimeException e) {
      throw new NotFoundException("No Product found with id '" + productId + "'");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public void updateProduct(Product data) throws UpdateFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Product (Convert Product to ProductData)
      ProductData product = Product_ToGrpc_ProductData.convertToProductData(data);

      // Prompt gRPC to update the Product:
      EmptyMessage updated = stub.updateProduct(product);

      if(updated == null && data != null)
        throw new UpdateFailedException("Failed to update Product with id '" + data.getProduct_id() + "'");

      // TODO: Update all TrayToProductTransfers this product is involved in:

    } catch (StatusRuntimeException e) {
      if(e.getStatus().equals(NOT_FOUND))
        throw new NotFoundException("No Product found with id '" + data.getProduct_id() + "'");

      if(e.getStatus().equals(INTERNAL))
        throw new UpdateFailedException("Critical Error encountered. Failed to Update Product with id '" + data.getProduct_id() + "'");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public boolean removeProduct(long productId) throws DeleteFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Attempt to find an PartType with the given typeId:
      Product product = readProduct(productId);

      // Create a gRPC compatible version of PartType (Convert PartType to PartTypeData)
      ProductData productData = Product_ToGrpc_ProductData.convertToProductData(product);

      // Prompt gRPC to delete the PartType:
      EmptyMessage deleted = stub.removeProduct(productData);

      if(deleted == null && productData != null)
        throw new DeleteFailedException("Failed to delete Product with id '" + productId + "'");

      // TODO: Delete all TrayToProductTransfers this product is involved in:

      return true;
    } catch (StatusRuntimeException e) {
      if(e.getStatus().equals(NOT_FOUND))
        throw new NotFoundException("No Product found with id '" + productId + "'");
      else
        throw new DeleteFailedException("Critical Error encountered. Failed to delete Product with id '" + productId + "'");
    }
    finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public List<Product> getAllProducts() throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      SlaughterHouseServiceGrpc.SlaughterHouseServiceBlockingStub stub = SlaughterHouseServiceGrpc.newBlockingStub(channel);

      // Prompt gRPC to retrieve the AnimalParts from the Database:
      ProductsData products = stub.getAllProducts(GrpcFactory.buildGrpcEmptyMessage());

      // Convert, and return, the PartTypeData that was added to the DB into an application compatible format:
      return GrpcProductData_To_Product.convertToProductList(products);

    } catch (StatusRuntimeException e) {
      if(e.getStatus().equals(NOT_FOUND))
        throw new NotFoundException("No Products found in database");
      else
        throw new RuntimeException("Critical Error encountered. Failed to Query all Products from the Database");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }
}
