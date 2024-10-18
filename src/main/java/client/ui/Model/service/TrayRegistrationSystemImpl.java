package client.ui.Model.service;

import client.interfaces.TrayRegistrationSystem;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import server.controller.grpc.GrpcFactory;
import server.controller.grpc.grpc_to_java.GrpcTrayData_To_Tray;
import server.controller.grpc.java_to_gRPC.LongId_ToGrpc_Id;
import server.controller.grpc.java_to_gRPC.Tray_ToGrpc_TrayData;
import shared.model.entities.Tray;
import shared.model.exceptions.CreateFailedException;
import shared.model.exceptions.DeleteFailedException;
import shared.model.exceptions.NotFoundException;
import shared.model.exceptions.UpdateFailedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.grpc.Status.INTERNAL;
import static io.grpc.Status.NOT_FOUND;

public class TrayRegistrationSystemImpl extends Client implements TrayRegistrationSystem
{
  public TrayRegistrationSystemImpl(String host, int port){
    super(host, port);
  }


  @Override public Tray registerNewTray(BigDecimal maxWeight_kilogram, BigDecimal currentWeight_kilogram) throws CreateFailedException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      TrayServiceGrpc.TrayServiceBlockingStub stub = TrayServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Tray (TrayData)
      TrayData data = GrpcFactory.buildGrpcTrayData(maxWeight_kilogram, currentWeight_kilogram, new ArrayList<>(), new ArrayList<>());

      // Prompt gRPC to register the Tray:
      TrayData createdTray = stub.registerTray(data);

      // Convert, and return, the Tray that was added to the DB into an application compatible format:
      return GrpcTrayData_To_Tray.convertToTray(createdTray);

    } catch (StatusRuntimeException e) {
      throw new CreateFailedException("Failed to register Tray with maxWeight '" + maxWeight_kilogram + "kg' and currentWeight '" + currentWeight_kilogram + "kg'. (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public Tray readTray(long trayId) throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      TrayServiceGrpc.TrayServiceBlockingStub stub = TrayServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of trayId (TrayId)
      TrayId id = LongId_ToGrpc_Id.convertToTrayId(trayId);

      // Prompt gRPC to read the Tray:
      TrayData foundTray = stub.readTray(id);

      // Convert, and return, the TrayData that was read from the DB into an application compatible format:
      return GrpcTrayData_To_Tray.convertToTray(foundTray);

    } catch (StatusRuntimeException e) {
      throw new NotFoundException("No Tray found with id '" + trayId + "' (" + e.getMessage() + ")");
    } finally {
      // Always shut down the channel after use, to reduce server congestion and 'application hanging'.
      channel.shutdown();
    }
  }


  @Override public void updateTray(Tray data) throws UpdateFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      TrayServiceGrpc.TrayServiceBlockingStub stub = TrayServiceGrpc.newBlockingStub(channel);

      // Create a gRPC compatible version of Tray (Convert Tray to TrayData)
      TrayData tray = Tray_ToGrpc_TrayData.convertToTrayData(data, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());

      // Prompt gRPC to update the Tray:
      EmptyMessage updated = stub.updateTray(tray);

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


  @Override public boolean removeTray(long trayId) throws DeleteFailedException, NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      TrayServiceGrpc.TrayServiceBlockingStub stub = TrayServiceGrpc.newBlockingStub(channel);

      // Attempt to find a Tray with the given trayId:
      Tray tray = readTray(trayId);

      // Create a gRPC compatible version of Tray (Convert Tray to TrayData)
      TrayData trayData = Tray_ToGrpc_TrayData.convertToTrayData(tray, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());

      // Prompt gRPC to delete the Tray:
      EmptyMessage deleted = stub.removeTray(trayData);

      if(deleted == null && trayData != null)
        throw new DeleteFailedException("Failed to delete Tray with id '" + trayId + "'");

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


  @Override public List<Tray> getAllTrays() throws NotFoundException {
    // Create a managed channel to connect to the gRPC server:
    ManagedChannel channel = channel();

    try {
      // Create a BlockingStub, which is auto-generated by the proto file, and in essence is the client connection manager!
      TrayServiceGrpc.TrayServiceBlockingStub stub = TrayServiceGrpc.newBlockingStub(channel);

      // Prompt gRPC to retrieve the Trays from the Database:
      TraysData trays = stub.getAllTrays(GrpcFactory.buildGrpcEmptyMessage());

      // Convert, and return, the TraysData that was added to the DB into an application compatible format:
      return GrpcTrayData_To_Tray.convertToTrayList(trays);

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
