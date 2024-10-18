package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.AnimalPart;
import shared.model.entities.PartType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class PartType_ToGrpc_PartTypeData
{
  /** <p>Converts a PartType entity into a database/gRPC compatible PartTypeData format</p>
   * @param partType The PartType entity to convert
   * @return a gRPC compatible PartTypeData data type.
   * */
  public static PartTypeData convertToPartTypeData(PartType partType,
      Map<String, AnimalPartData> animalPartDataCache,
      Map<String, AnimalData> animalDataCache,
      Map<String, PartTypeData> partTypeDataCache,
      Map<String, ProductData> productDataCache,
      Map<String, TrayData> trayDataCache) {

    if (partType == null)
      return null;

    // Utilize an embedded cache, to avoid both stale data and infinite recursion. In cases where other conversion algorithms
    // call this conversion, the value inside the cache will simply be used once converted,
    // instead of an infinite recursive attempt:
    String hashKey = "" + partType.getTypeId();
    if(partTypeDataCache.containsKey(hashKey))
      return partTypeDataCache.get(hashKey);

    // If not already cached, create an initial placeholder cached version, to avoid infinite recursion:
    PartTypeData.Builder partTypeDataPlaceHolderBuilder = PartTypeData.newBuilder()
        .setPartTypeId(partType.getTypeId())
        .setPartDesc(partType.getTypeDesc());

    // Cache the placeholder:
    partTypeDataCache.put(hashKey, partTypeDataPlaceHolderBuilder.build());

    // Convert the remaining data fields:
    List<AnimalPart> partList = partType.getPartList();

    if(partList == null)
      partList = new ArrayList<>();

    List<AnimalPartData> animalPartDataList = new ArrayList<>();
    for (AnimalPart animalPart : partList)
      animalPartDataList.add(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart, animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache));

    PartTypeData partTypeData = partTypeDataPlaceHolderBuilder
        .addAllAnimalPartsOfThisTypeList(animalPartDataList)
        .build();

    // Replace the cached placeholder with the final version of this entity:
    partTypeDataCache.put(hashKey, partTypeData);
    return partTypeData;
  }


  /** <p>Converts a List of PartTypes into the gRPC compatible PartTypesData format</p>
   * @param partTypes A list containing all the PartType entities to convert.
   * @return A gRPC compatible PartTypesData data type, containing all the converted entities.*/
  public static PartTypesData convertToPartTypesDataList(List<PartType> partTypes) {
    List<PartTypeData> partTypeDataList = new ArrayList<>();

    // Convert List of PartTypes to a gRPC compatible list by iteration through each entry and running the method previously declared:
    for (PartType partType : partTypes)
      partTypeDataList.add(convertToPartTypeData(partType, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    // Construct and return a new List of PartTypeData entities:
    return PartTypesData.newBuilder().addAllPartTypes(partTypeDataList).build();
  }
}
