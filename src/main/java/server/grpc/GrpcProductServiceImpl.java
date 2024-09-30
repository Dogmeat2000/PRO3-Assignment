package server.grpc;

import grpc.SlaughterHouseServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcProductServiceImpl extends SlaughterHouseServiceGrpc.SlaughterHouseServiceImplBase
{
  private static final Logger logger = LoggerFactory.getLogger("Service");

  public GrpcProductServiceImpl() {
    super();
  }


  @Override
  public void registerProduct(grpc.ProductData request, io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void readProduct(grpc.ProductId request, io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void updateProduct(grpc.ProductData request, io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void removeProduct(grpc.ProductData request, io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void getAllProducts(grpc.EmptyMessage request, io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }
}
