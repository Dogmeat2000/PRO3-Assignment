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
    public static TrayData convertToTrayData(Tray tray,
        Map<String, AnimalPartData> animalPartDataCache,
        Map<String, AnimalData> animalDataCache,
        Map<String, PartTypeData> partTypeDataCache,
        Map<String, ProductData> productDataCache,
        Map<String, TrayData> trayDataCache) {

      if (tray == null)
        return null;

      // Utilize an embedded cache, to avoid both stale data and infinite recursion. In cases where other conversion algorithms
      // call this conversion, the value inside the cache will simply be used once converted,
      // instead of an infinite recursive attempt:
      String hashKey = "" + tray.getTrayId();
      if(trayDataCache.containsKey(hashKey))
        return trayDataCache.get(hashKey);

      // If not already cached, create an initial placeholder cached version, to avoid infinite recursion:
      TrayData.Builder trayDataPlaceHolderBuilder = TrayData.newBuilder()
          .setTrayId(tray.getTrayId())
          .setMaxWeightKilogram(tray.getMaxWeight_kilogram().toString())
          .setWeightKilogram(tray.getWeight_kilogram().toString());

      // Cache the placeholder:
      trayDataCache.put(hashKey, trayDataPlaceHolderBuilder.build());

      // Convert the remaining data fields:
      List<AnimalPart> partList = tray.getContents();
      List<TrayToProductTransfer> transferList = tray.getDeliveredToProducts();

      if(partList == null)
        partList = new ArrayList<>();

      List<AnimalPartData> animalPartDataList = new ArrayList<>();
      for (AnimalPart animalPart : partList)
        animalPartDataList.add(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart, animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache));


      if(transferList == null)
        transferList = new ArrayList<>();

      List<TrayToProductTransferData> transferDataList = new ArrayList<>();
      for (TrayToProductTransfer transfer : transferList)
        transferDataList.add(TrayToProductTransfer_ToGrpc_TrayToProductTransferData.convertToTrayToProductTransferData(transfer, animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache));

      TrayData trayData = trayDataPlaceHolderBuilder
          .addAllAnimalParts(animalPartDataList)
          .addAllTrayToProducts(transferDataList)
          .build();

      // Replace the cached placeholder with the final version of this entity:
      trayDataCache.put(hashKey, trayData);

      return trayData;
    }


    /** <p>Converts a List of Trays into the gRPC compatible TraysData format</p>
     * @param trays A list containing all the Tray entities to convert.
     * @return A gRPC compatible TraysData data type, containing all the converted entities.*/
    public static TraysData convertToTraysDataList(List<Tray> trays) {
      List<TrayData> trayDataList = new ArrayList<>();

      // Convert List of Trays to a gRPC compatible list by iteration through each entry and running the method previously declared:
      for (Tray tray : trays)
        trayDataList.add(convertToTrayData(tray, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

      // Construct and return a new List of TrayData entities:
      return TraysData.newBuilder().addAllTrays(trayDataList).build();
    }
}
