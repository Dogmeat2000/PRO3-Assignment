package server.controller.grpc.java_to_gRPC;

import grpc.TrayData;
import grpc.TraysData;
import shared.model.entities.Tray;

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

      TrayData.Builder builder = TrayData.newBuilder()
          .setTrayId(tray.getTray_id())
          .setMaxWeightKilogram(tray.getMaxWeight_kilogram().toString())
          .setWeightKilogram(tray.getWeight_kilogram().toString());

      return builder.build();
    }


    /** <p>Converts a List of Trays into the gRPC compatible TraysData format</p>
     * @param trays A list containing all the Tray entities to convert.
     * @return A gRPC compatible TraysData data type, containing all the converted entities.*/
    public static TraysData convertToTraysDataList(List<Tray> trays) {
      // Convert List of Animals to a gRPC compatible list by iteration through each entry and running the method previously declared:
      List<TrayData> list = trays.stream().map(server.controller.grpc.java_to_gRPC.Tray_ToGrpc_TrayData::convertToTrayData).toList();

      // Construct and return a new List of AnimalData entities:
      return TraysData.newBuilder().addAllTrays(list).build();
    }
}
