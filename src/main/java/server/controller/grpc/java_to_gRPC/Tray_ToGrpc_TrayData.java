package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.Tray;
import shared.model.entities.TrayToProductTransfer;

import java.util.ArrayList;
import java.util.List;

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
      TrayData.Builder builder = TrayData.newBuilder();
      builder.setTrayId(tray.getTrayId());
      builder.setMaxWeightKilogram(tray.getMaxWeight_kilogram().toString());
      builder.setWeightKilogram(tray.getWeight_kilogram().toString());

      if(tray.getAnimalPartIdList() != null && !tray.getAnimalPartIdList().isEmpty())
        builder.addAllAnimalPartIds(tray.getAnimalPartIdList());

      if(tray.getTransferIdList() != null && !tray.getTransferIdList().isEmpty())
        builder.addAllTransferIds(tray.getTransferIdList());

      if(tray.getTransferList() != null && !tray.getTransferList().isEmpty()) {
        for (TrayToProductTransfer transfer : tray.getTransferList())
          builder.addTransfersData(TrayToProductTransfer_ToGrpc_TrayToProductTransferData.convertToTrayToProductTransferData(transfer));
      }

      return builder.build();
    }


    /** <p>Converts a List of Trays into the gRPC compatible TraysData format</p>
     * @param trays A list containing all the Tray entities to convert.
     * @return A gRPC compatible TraysData data type, containing all the converted entities.*/
    public static TraysData convertToTraysDataList(List<Tray> trays) {
      // Return an empty list, if received list is null or empty.
      if(trays == null || trays.isEmpty())
        return TraysData.newBuilder().addAllTrays(new ArrayList<>()).build();

      // Convert List of Trays to a gRPC compatible list by iteration through each entry and running the method previously declared:
      List<TrayData> trayDataList = new ArrayList<>();
      for (Tray tray : trays)
        trayDataList.add(convertToTrayData(tray));

      // Construct and return a new List of TrayData entities:
      return TraysData.newBuilder().addAllTrays(trayDataList).build();
    }
}
