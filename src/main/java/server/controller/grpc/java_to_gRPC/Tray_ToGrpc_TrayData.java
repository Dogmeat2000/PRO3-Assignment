package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.AnimalPart;
import shared.model.entities.Tray;
import shared.model.entities.TrayToProductTransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class Tray_ToGrpc_TrayData
{
    /** <p>Converts a Tray entity into a database/gRPC compatible TrayData format</p>
     * @param tray The Tray entity to convert
     * @return a gRPC compatible TrayData data type.
     * */
    public static TrayData convertToTrayData(Tray tray) {

      if (tray == null)
        return null;

      // Convert the java data fields, excluding any lists of other entities. These need to be queried separately by the receiving service layer:
      return TrayData.newBuilder()
          .setTrayId(tray.getTrayId())
          .setMaxWeightKilogram(tray.getMaxWeight_kilogram().toString())
          .setWeightKilogram(tray.getWeight_kilogram().toString())
          .addAllAnimalPartIds(tray.getAnimalPartIdList())
          .addAllTransferIds(tray.getTransferIdList())
          .build();
    }


    /** <p>Converts a List of Trays into the gRPC compatible TraysData format</p>
     * @param trays A list containing all the Tray entities to convert.
     * @return A gRPC compatible TraysData data type, containing all the converted entities.*/
    public static TraysData convertToTraysDataList(List<Tray> trays) {
      List<TrayData> trayDataList = new ArrayList<>();

      // Convert List of Trays to a gRPC compatible list by iteration through each entry and running the method previously declared:
      for (Tray tray : trays)
        trayDataList.add(convertToTrayData(tray));

      // Construct and return a new List of TrayData entities:
      return TraysData.newBuilder().addAllTrays(trayDataList).build();
    }
}
