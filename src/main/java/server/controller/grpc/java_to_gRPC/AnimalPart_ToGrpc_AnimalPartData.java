package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.AnimalPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for converting a application entities into a database/gRPC compatible formats */
public class AnimalPart_ToGrpc_AnimalPartData
{
  /** Converts a AnimalPart entity into a database/gRPC compatible AnimalPartData format */
  public static AnimalPartData convertToAnimalPartData(AnimalPart animalPart,
      Map<String, AnimalPartData> animalPartDataCache,
      Map<String, AnimalData> animalDataCache,
      Map<String, PartTypeData> partTypeDataCache,
      Map<String, ProductData> productDataCache,
      Map<String, TrayData> trayDataCache) {
    if (animalPart == null)
      return null;

    // Utilize an embedded cache, to avoid both stale data and infinite recursion. In cases where other conversion algorithms
    // call this conversion, the value inside the cache will simply be used once converted,
    // instead of an infinite recursive attempt:
    String hashKey = "" + animalPart.getPart_id() + animalPart.getAnimal().getId() + animalPart.getType().getTypeId() + animalPart.getTray().getTrayId();
    if(animalPartDataCache.containsKey(hashKey))
      return animalPartDataCache.get(hashKey);

    // If not already cached, create an initial placeholder cached version, to avoid infinite recursion:
    AnimalPartData.Builder animalPartDataPlaceHolderBuilder = AnimalPartData.newBuilder()
        .setAnimalPartId(LongId_ToGrpc_Id.convertToAnimalPartId(animalPart.getPart_id()))
        .setPartWeight(animalPart.getWeight_kilogram().toString());

    // Cache the placeholder:
    animalPartDataCache.put(hashKey, animalPartDataPlaceHolderBuilder.build());

    // Convert the remaining data fields:
    AnimalPartData animalPartData;
    if(animalPart.getProduct() != null) {
      animalPartData = animalPartDataPlaceHolderBuilder
          .setPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(animalPart.getType(), animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache))
          .setAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animalPart.getAnimal(), animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache))
          .setTray(Tray_ToGrpc_TrayData.convertToTrayData(animalPart.getTray(), animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache))
          .setProduct(Product_ToGrpc_ProductData.convertToProductData(animalPart.getProduct(), animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache))
          .build();
    } else {
      animalPartData = animalPartDataPlaceHolderBuilder
          .setPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(animalPart.getType(), animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache))
          .setAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animalPart.getAnimal(), animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache))
          .setTray(Tray_ToGrpc_TrayData.convertToTrayData(animalPart.getTray(), animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache))
          .build();
    }


    // Replace the cached placeholder with the final version of this entity:
    animalPartDataCache.put(hashKey, animalPartData);
    return animalPartData;
  }


  public static UpdatedAnimalPartData covertToUpdatedAnimalPartData(AnimalPart oldData, AnimalPart newData) {
    if (oldData == null || newData == null)
      return null;

    UpdatedAnimalPartData.Builder updatedAnimalPartBuilder = UpdatedAnimalPartData.newBuilder()
        .setOldData(convertToAnimalPartData(oldData, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()))
        .setNewData(convertToAnimalPartData(newData, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    return updatedAnimalPartBuilder.build();
  }


  /** Converts a List of AnimalParts into the gRPC compatible AnimalPartsData format */
  public static AnimalPartsData convertToAnimalPartsDataList(List<AnimalPart> animalParts) {
    List<AnimalPartData> animalPartsDataList = new ArrayList<>();

    // Convert List of AnimalParts to a gRPC compatible list by iteration through each entry and running the method previously declared:
    for (AnimalPart animalPart : animalParts)
      animalPartsDataList.add(convertToAnimalPartData(animalPart, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    // Construct and return a new List of AnimalPartData entities:
    return AnimalPartsData.newBuilder().addAllAnimalParts(animalPartsDataList).build();
  }
}
