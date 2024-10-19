package server.grpc;

import grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import server.controller.grpc.grpc_to_java.GrpcId_To_LongId;
import server.controller.grpc.grpc_to_java.GrpcTrayData_To_Tray;
import server.controller.grpc.java_to_gRPC.Tray_ToGrpc_TrayData;
import server.service.TrayRegistryInterface;
import shared.model.entities.Tray;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.UpdateFailedException;

import java.util.HashMap;
import java.util.List;

@GrpcService
public class GrpcTrayServiceImpl extends TrayServiceGrpc.TrayServiceImplBase
{
  private final TrayRegistryInterface trayService;

  @Autowired
  public GrpcTrayServiceImpl(TrayRegistryInterface trayService) {
    super();
    this.trayService = trayService;
  }

  @Override
  public void registerTray(TrayData request, StreamObserver<TrayData> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types, and
      // attempt to register the Tray:
      Tray createdTray = trayService.registerTray(GrpcTrayData_To_Tray.convertToTray(request));

      // If animal creation fails
      if (createdTray == null)
        throw new CreateFailedException("Tray could not be created");

      // Translate the created Tray into gRPC compatible types, before transmitting back to client:
      responseObserver.onNext(Tray_ToGrpc_TrayData.convertToTrayData(createdTray));
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
      responseObserver.onNext(Tray_ToGrpc_TrayData.convertToTrayData(tray));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("Tray with id " + request.getTrayId() + "not found in DB").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error reading tray, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }


  @Override
  public void updateTray(TrayData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to update the Tray with the provided ID:
      if (!trayService.updateTray(GrpcTrayData_To_Tray.convertToTray(request))) {
        // If Tray update failed:
        throw new UpdateFailedException("Error occurred while updated tray with id='" + request.getTrayId() + "'");
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


  @Override
  public void removeTray(TrayData request, StreamObserver<EmptyMessage> responseObserver) {
    try {
      // Translate received gRPC information from the client, into Java compatible types,
      // and attempt to delete the Tray with the provided ID:
      if(!trayService.removeTray(GrpcTrayData_To_Tray.convertToTray(request))) {
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
      responseObserver.onNext(Tray_ToGrpc_TrayData.convertToTraysDataList(trays));
      responseObserver.onCompleted();
    } catch (NotFoundException e) {
      responseObserver.onError(Status.NOT_FOUND.withDescription("No Trays found").withCause(e).asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.withDescription("Error retrieving all trays, " + e.getMessage()).withCause(e).asRuntimeException());
    }
  }
}
