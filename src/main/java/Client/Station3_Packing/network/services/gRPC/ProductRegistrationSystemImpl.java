package Client.Station3_Packing.network.services.gRPC;

import Client.common.model.adapters.gRPC_to_java.GrpcProductData_To_ProductDto;
import Client.common.model.adapters.java_to_gRPC.ProductDto_ToGrpc_ProductData;
import Client.common.services.gRPC.GrpcConnection;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import Client.common.model.adapters.GrpcFactory;
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

public class ProductRegistrationSystemImpl extends GrpcConnection implements ProductRegistrationSystem
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

      // Prompt gRPC to register the ProductData:
      ProductData createdProduct = productStub.registerProduct(data);

      // Convert the ProductData that was read from the DB into an application compatible format:
      return grpcProductDataConverter.convertToProductDto(createdProduct);

    } catch (StatusRuntimeException e) {
      e.printStackTrace();
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

      // Attempt to find an PartType with the given typeId:
      ProductDto product = readProduct(productId);

      // Create a gRPC compatible version of PartType (Convert PartType to PartTypeData)
      ProductData productData = productConverter.convertToProductData(product);

      // Prompt gRPC to delete the PartType:
      EmptyMessage deleted = productStub.removeProduct(productData);

      if(deleted == null && productData != null)
        throw new DeleteFailedException("Failed to delete Product with id '" + productId + "'");

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

      // Prompt gRPC to retrieve the AnimalParts from the Database:
      ProductsData productsData = productStub.getAllProducts(GrpcFactory.buildGrpcEmptyMessage());

      // Convert received data to java language:
      return grpcProductDataConverter.convertToProductDtoList(productsData);

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