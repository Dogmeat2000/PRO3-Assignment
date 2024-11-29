package DataServer.controller.grpc.adapters.java_to_gRPC;

import grpc.*;
import DataServer.model.persistence.entities.Product;
import DataServer.model.persistence.entities.Tray;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a application entities into a gRPC compatible formats</p> */
public class Tray_ToGrpc_TrayData
{

    /** <p>Converts a Tray entity into a gRPC compatible TrayData format</p>
     * @param tray The Tray entity to convert
     * @return a gRPC compatible TrayData data type.
     * */
    public TrayData convertToTrayData(Tray tray) {

      if (tray == null)
        return null;

      // Convert the java data fields:
      TrayData.Builder builder = TrayData.newBuilder();
      builder.setTrayId(LongId_ToGrpc_Id.convertToTrayId(tray.getTrayId()));
      builder.setMaxWeightKilogram(tray.getMaxWeight_kilogram().toString());
      builder.setWeightKilogram(tray.getWeight_kilogram().toString());

      if(tray.getTrayType() != null)
        builder.setTrayTypeId(LongId_ToGrpc_Id.convertToPartTypeId(tray.getTrayType().getTypeId()));

      // Convert AnimalPartIds:
      for (long id : tray.getAnimalPartIdList())
        builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(id));

      // Convert TransferIds:
      for (long id : tray.getTransferIdList())
        builder.addTransferIds(LongId_ToGrpc_Id.convertToTrayToProductTransferId(id));

      // Convert ProductIds:
      for (Product product : tray.getProductList())
        builder.addProductids(LongId_ToGrpc_Id.convertToProductId(product.getProductId()));

      return builder.build();
    }

    /** <p>Converts a List of Trays into the gRPC compatible TraysData format</p>
     * @param trays A list containing all the Tray entities to convert.
     * @return A gRPC compatible TraysData data type, containing all the converted entities.*/
    public TraysData convertToTraysDataList(List<Tray> trays) {
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
