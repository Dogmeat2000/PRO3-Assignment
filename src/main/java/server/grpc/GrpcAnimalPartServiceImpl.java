package server.grpc;

import grpc.SlaughterHouseServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@GrpcService
public class GrpcAnimalPartServiceImpl extends SlaughterHouseServiceGrpc.SlaughterHouseServiceImplBase
{
  private static final Logger logger = LoggerFactory.getLogger("Service");

  public GrpcAnimalPartServiceImpl() {
    super();
  }


  @Override
  public void registerAnimalPart(grpc.AnimalPartData request, io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void readAnimalPart(grpc.AnimalPartId request, io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void updateAnimalPart(grpc.AnimalPartData request, io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void removeAnimalPart(grpc.AnimalPartData request, io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }


  @Override
  public void getAnimalParts(grpc.EmptyMessage request, io.grpc.stub.StreamObserver<grpc.AnimalPartsData> responseObserver) {
    //TODO MISSING IMPLEMENTATION
  }
}
