package server.controller.grpc.grpc_to_java;

import grpc.*;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcTrayData_To_Tray
{
    /** Converts database/gRPC compatible TrayData information into an application compatible Tray entity */
    public static Tray convertToTray(TrayData trayData) {

      if (trayData == null)
        return null;

      // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
      long id = trayData.getTrayId();
      BigDecimal maxWeight_kilogram = trayData.getMaxWeightKilogram().isEmpty() ? BigDecimal.ZERO : new BigDecimal(trayData.getMaxWeightKilogram());
      BigDecimal weight_kilogram = trayData.getWeightKilogram().isEmpty() ? BigDecimal.ZERO : new BigDecimal(trayData.getWeightKilogram());
      List<Long> animalPartIdList = new ArrayList<>(trayData.getAnimalPartIdsList());
      List<Long> transferIdList = new ArrayList<>(trayData.getTransferIdsList());

      // Construct a new Tray entity with the above read attributes set:
      return new Tray(id, maxWeight_kilogram, weight_kilogram, animalPartIdList, transferIdList);
    }


    public static List<Tray> convertToTrayList(TraysData data) {
      // Return an empty list, if received list is null or empty.
      if(data == null || data.getTraysList().isEmpty())
        return new ArrayList<>();

      // Convert List of TraysData to a java compatible list by iteration through each entry and running the method previously declared:
      List<Tray> trayList = new ArrayList<>();
      for (TrayData trayData : data.getTraysList())
        trayList.add(convertToTray(trayData));

      // return a new List of Tray entities:
      return trayList;
    }
}
