package server.controller.grpc.service;

import grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import server.controller.grpc.adapters.grpc_to_java.GrpcProductData_To_Product;
import server.controller.grpc.adapters.java_to_gRPC.Product_ToGrpc_ProductData;
import server.model.persistence.service.AnimalPartRegistryInterface;
import server.model.persistence.service.ProductRegistryInterface;
import server.model.persistence.service.TrayRegistryInterface;
import server.model.persistence.entities.Product;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.List;

@GrpcService
public class GrpcProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase
{
  private final ProductRegistryInterface productService;
  /*private final TrayRegistryInterface trayService;
  private final AnimalPartRegistryInterface animalPartService;*/
  private final GrpcProductData_To_Product grpcProductDataConverter;
  private final Product_ToGrpc_ProductData productConverter = new Product_ToGrpc_ProductData();
  //private final int maxNestingDepth;

  @Autowired
  public GrpcProductServiceImpl(ProductRegistryInterface productService,
      /*TrayRegistryInterface trayService,
      AnimalPartRegistryInterface animalPartService,*/
      GrpcProductData_To_Product grpcProductDataConverter/*,
      @Value("${maxNestingDepth}") int maxNestingDepth*/) {
    super();
    this.productService = productService;
    /*this.trayService = trayService;
    this.animalPartService = animalPartService;*/
    this.grpcProductDataConverter = grpcProductDataConverter;
    //this.maxNestingDepth = maxNestingDepth;
  }


  @Override
  public void registerProduct(ProductData request, StreamObserver<ProductData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types
      System.out.println("\n\n[GrpcProductServiceImpl] Line 1");

      Product productReceived = grpcProductDataConverter.convertToProduct(request);
      System.out.println("\n\n[GrpcProductServiceImpl] Line 2");

      // Register the Product:
      Product createdProduct = productService.registerProduct(productReceived);
      System.out.println("\n\n[GrpcProductServiceImpl] Line 3");

      // If animal creation fails
      if (createdProduct == null)
        throw new CreateFailedException("Product could not be created");

      System.out.println("\n\n[GrpcProductServiceImpl] Line 4");

      // Translate the created Product into gRPC a compatible type, before transmitting back to client:
      responseObserver.onNext(productConverter.convertToProductData(createdProduct));
      responseObserver.onCompleted();
    } catch (Exception e) {
      e.printStackTrace(); // TODO: DELETE LINE
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
      responseObserver.onNext(productConverter.convertToProductData(product));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Product with id " + request.getProductId() + " not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading Product, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void updateProduct(ProductData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into a Java compatible type:
      Product productReceived = grpcProductDataConverter.convertToProduct(request);

      // Attempt to update the Product:
      if (!productService.updateProduct(productReceived)) {
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
      if(!productService.removeProduct(grpcProductDataConverter.convertToProduct(request))) {
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
        responseObserver.onNext(productConverter.convertToProductsDataList(products));
        responseObserver.onCompleted();
      } catch (NotFoundException e) {
        responseObserver.onError(Status.NOT_FOUND.withDescription("No Products found").withCause(e).asRuntimeException());
      } catch (Exception e) {
        responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all Products, " + e.getMessage()).withCause(e).asRuntimeException());
      }
  }
}
