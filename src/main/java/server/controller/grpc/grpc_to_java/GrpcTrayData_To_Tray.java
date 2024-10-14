package server.controller.grpc.grpc_to_java;

import grpc.TrayData;
import grpc.TraysData;
import shared.model.entities.AnimalPart;
import shared.model.entities.Tray;
import shared.model.entities.TrayToProductTransfer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcTrayData_To_Tray
{
    /** Converts database/gRPC compatible TrayData information into an application compatible Tray entity */
    public static Tray convertToTray(TrayData trayData) {
      if (trayData == null)
        return null;

      // Read Tray information from the gRPC data:
      long id = trayData.getTrayId();
      BigDecimal maxWeight_kilogram = new BigDecimal(trayData.getMaxWeightKilogram());
      BigDecimal weight_kilogram = new BigDecimal(trayData.getWeightKilogram());
      List<AnimalPart> animalParts = new ArrayList<>(trayData.getAnimalPartsList().stream().map(GrpcAnimalPartData_To_AnimalPart::convertToAnimalPart).toList());
      List<TrayToProductTransfer> transfers = new ArrayList<>(trayData.getTrayToProductsList().stream().map(GrpcTrayToProductTransferData_To_TrayToProductTransfer::convertToTrayToProductTransfer).toList());

      // Construct and return a new Tray entity with the above read attributes set:
      return new Tray(id, maxWeight_kilogram, weight_kilogram, animalParts, transfers);
    }


    public static List<Tray> convertToTrayList(TraysData data) {

      // Construct and return a new List of Animal entities:
      return data.getTraysList().stream().map(server.controller.grpc.grpc_to_java.GrpcTrayData_To_Tray::convertToTray).toList();
    }
}
