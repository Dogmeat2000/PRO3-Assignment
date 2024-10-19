package server.grpc;

import grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import server.controller.grpc.grpc_to_java.GrpcId_To_LongId;
import server.controller.grpc.grpc_to_java.GrpcProductData_To_Product;
import server.controller.grpc.java_to_gRPC.Product_ToGrpc_ProductData;
import server.service.ProductRegistryInterface;
import shared.model.entities.Product;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.UpdateFailedException;

import java.util.HashMap;
import java.util.List;

@GrpcService
public class GrpcProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase
{
  private final ProductRegistryInterface productService;

  @Autowired
  public GrpcProductServiceImpl(ProductRegistryInterface productService) {
    super();
    this.productService = productService;
  }


  @Override
  public void registerProduct(ProductData request, StreamObserver<ProductData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types, and
      // attempt to register the Tray:
      Product createdProduct = productService.registerProduct(GrpcProductData_To_Product.convertToProduct(request));

      // If animal creation fails
      if (createdProduct == null)
        throw new CreateFailedException("Product could not be created");

      // Translate the created Product into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(Product_ToGrpc_ProductData.convertToProductData(createdProduct));
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error registering Product, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readProduct(ProductId request, StreamObserver<ProductData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible type,
      // and attempt to read the Product with the provided ID:
      Product product = productService.readProduct(GrpcId_To_LongId.ConvertToLongId(request));

      // If Tray read failed:
      if (product == null)
        throw new NotFoundException("Product not found");

      // Translate the found Tray into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(Product_ToGrpc_ProductData.convertToProductData(product));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Product with id " + request.getProductId() + "not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading Product, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void updateProduct(ProductData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to update the Product with the provided ID:
      if (!productService.updateProduct(GrpcProductData_To_Product.convertToProduct(request))) {
        // If Product update failed:
        throw new UpdateFailedException("Error occurred while updated Product with id='" + request.getProductId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Product not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error occurred while attempting to update Product with id '" + request.getProductId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void removeProduct(ProductData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to delete the Product with the provided ID:
      if(!productService.removeProduct(GrpcProductData_To_Product.convertToProduct(request))) {
        // If Tray deletion failed:
        throw new DeleteFailedException("Error occurred while deleting Product with id='" + request.getProductId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Product not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error deleting Product, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void getAllProducts(EmptyMessage request, StreamObserver<ProductsData> responseObserver) {
    try {
        // Attempt to retrieve all Products:
        List<Product> products = productService.getAllProducts();

        // If Product read failed:
        if (products == null)
          throw new NotFoundException("Products not found");

        // Translate the found Product into gRPC compatible types, before transmitting back to client:
        responseObserver.onNext(Product_ToGrpc_ProductData.convertToProductsDataList(products));
        responseObserver.onCompleted();
      } catch (NotFoundException e) {
        responseObserver.onError(Status.NOT_FOUND.withDescription("No Products found").withCause(e).asRuntimeException());
      } catch (Exception e) {
        responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all Products, " + e.getMessage()).withCause(e).asRuntimeException());
      }
  }
}
