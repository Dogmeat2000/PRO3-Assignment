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
    public static Tray convertToTray(TrayData trayData,
        Map<String, AnimalPart> animalPartCache,
        Map<String, Animal> animalCache,
        Map<String, PartType> partTypeCache,
        Map<String, Product> productCache,
        Map<String, Tray> trayCache) {

      if (trayData == null)
        return null;

      // Utilize an embedded cache, to avoid both stale data and infinite recursion. In cases where other conversion algorithms
      // call this conversion, the value inside the cache will simply be used once converted,
      // instead of an infinite recursive attempt:
      String hashKey = "" + trayData.getTrayId();
      if(trayCache.containsKey(hashKey))
        return trayCache.get(hashKey);

      // If not already cached, create an initial placeholder cached version, to avoid infinite recursion:
      long id = trayData.getTrayId();
      BigDecimal maxWeight_kilogram = trayData.getMaxWeightKilogram().isEmpty() ? BigDecimal.ZERO : new BigDecimal(trayData.getMaxWeightKilogram());
      BigDecimal weight_kilogram = trayData.getWeightKilogram().isEmpty() ? BigDecimal.ZERO : new BigDecimal(trayData.getWeightKilogram());
      Tray trayPlaceHolder = new Tray(id, maxWeight_kilogram, weight_kilogram, null, null);

      // Cache the placeholder:
      trayCache.put(hashKey, trayPlaceHolder);

      // Convert the remaining gRPC data fields:
      List<AnimalPart> animalPartList = new ArrayList<>();
      for (AnimalPartData animalDataPart : trayData.getAnimalPartsList())
        animalPartList.add(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalDataPart, animalPartCache, animalCache, partTypeCache, productCache, trayCache));

      List<TrayToProductTransfer> transferList = new ArrayList<>();
      for (TrayToProductTransferData transfer : trayData.getTrayToProductsList())
        transferList.add(GrpcTrayToProductTransferData_To_TrayToProductTransfer.convertToTrayToProductTransfer(transfer, animalPartCache, animalCache, partTypeCache, productCache, trayCache));


      // Construct a new Tray entity with the above read attributes set:
      Tray tray = trayPlaceHolder.copy();
      if(!animalPartList.isEmpty())
        tray.addAllAnimalParts(animalPartList);
      tray.getDeliveredToProducts().addAll(transferList);

      // Put the final entity into the cache:
      trayCache.put(hashKey, tray);
      return tray;
    }


    public static List<Tray> convertToTrayList(TraysData data) {
      List<Tray> trayList = new ArrayList<>();

      // Convert List of TraysData to a java compatible list by iteration through each entry and running the method previously declared:
      for (TrayData trayData : data.getTraysList())
        trayList.add(convertToTray(trayData, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

      // return a new List of Tray entities:
      return trayList;
    }
}
