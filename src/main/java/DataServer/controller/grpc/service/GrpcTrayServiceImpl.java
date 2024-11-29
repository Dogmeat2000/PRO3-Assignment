package DataServer.controller.grpc.service;

import grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import DataServer.controller.grpc.adapters.grpc_to_java.GrpcTrayData_To_Tray;
import DataServer.controller.grpc.adapters.java_to_gRPC.Tray_ToGrpc_TrayData;
import DataServer.model.persistence.service.TrayRegistryInterface;
import DataServer.model.persistence.entities.Tray;
import shared.model.exceptions.persistance.CreateFailedException;
import shared.model.exceptions.persistance.DeleteFailedException;
import shared.model.exceptions.persistance.NotFoundException;
import shared.model.exceptions.persistance.UpdateFailedException;

import java.util.List;

@GrpcService
public class GrpcTrayServiceImpl extends TrayServiceGrpc.TrayServiceImplBase
{
  private final TrayRegistryInterface trayService;
  private final GrpcTrayData_To_Tray grpcTrayDataConverter;
  private final Tray_ToGrpc_TrayData trayConverter = new Tray_ToGrpc_TrayData();

  @Autowired
  public GrpcTrayServiceImpl(TrayRegistryInterface trayService,
      GrpcTrayData_To_Tray grpcTrayDataConverter) {
    super();
    this.trayService = trayService;
    this.grpcTrayDataConverter = grpcTrayDataConverter;
  }


  @Transactional
  @Override
  public void registerTray(TrayData request, StreamObserver<TrayData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types, and
      // attempt to register the Tray:
      Tray createdTray = trayService.registerTray(grpcTrayDataConverter.convertToTray(request));

      // If animal creation fails
      if (createdTray == null)
        throw new CreateFailedException("Tray could not be created");

      // Translate the created Tray into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(trayConverter.convertToTrayData(createdTray));
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error registering tray, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void readTray(TrayId request, StreamObserver<TrayData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible type,
      // and attempt to read the Tray with the provided ID:
      Tray tray = trayService.readTray(GrpcId_To_LongId.ConvertToLongId(request));

      // If Tray read failed:
      if (tray == null)
        throw new NotFoundException("Tray not found");

      // Translate the found Tray into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(trayConverter.convertToTrayData(tray));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Tray with id " + request.getTrayId() + " not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading tray, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Transactional
  @Override
  public void updateTray(TrayData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into a Java compatible type:
      Tray trayReceived = grpcTrayDataConverter.convertToTray(request);

      // Attempt to update the Tray:
      if (!trayService.updateTray(trayReceived)) {
        // If Tray update failed:
        throw new UpdateFailedException("Error occurred while updated Tray with id='" + request.getTrayId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Tray not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error occurred while attempting to update Tray with id '" + request.getTrayId() + "', " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Transactional
  @Override
  public void removeTray(TrayData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to delete the Tray with the provided ID:
      if(!trayService.removeTray(grpcTrayDataConverter.convertToTray(request))) {
        // If Tray deletion failed:
        throw new DeleteFailedException("Error occurred while deleting tray with id='" + request.getTrayId() + "'");
      }

      // Signal to client to complete the gRPC operation:
      responseObserver.onNext(EmptyMessage.newBuilder().build());
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Tray not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error deleting tray, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void getAllTrays(EmptyMessage request, StreamObserver<TraysData> responseObserver) {
    try {
      // Attempt to retrieve all Trays:
      List<Tray> trays = trayService.getAllTrays();

      // If Tray read failed:
      if (trays == null)
        throw new NotFoundException("Tray not found");

      // Translate the found Tray into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(trayConverter.convertToTraysDataList(trays));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("No Trays found").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all trays, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }
}
