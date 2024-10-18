package server.controller.grpc.grpc_to_java;

import grpc.AnimalPartData;
import grpc.PartTypeData;
import grpc.PartTypesData;
import shared.model.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcPartTypeData_To_PartType
{
    /** Converts database/gRPC compatible PartTypeData information into an application compatible PartType entity */
    public static PartType convertToPartType(PartTypeData partTypeData,
        Map<String, AnimalPart> animalPartCache,
        Map<String, Animal> animalCache,
        Map<String, PartType> partTypeCache,
        Map<String, Product> productCache,
        Map<String, Tray> trayCache) {

      if (partTypeData == null)
        return null;

      // Utilize an embedded cache, to avoid both stale data and infinite recursion. In cases where other conversion algorithms
      // call this conversion, the value inside the cache will simply be used once converted,
      // instead of an infinite recursive attempt:
      String hashKey = "" + partTypeData.getPartTypeId();
      if(partTypeCache.containsKey(hashKey))
        return partTypeCache.get(hashKey);

      // If not already cached, create an initial placeholder cached version, to avoid infinite recursion:
      long id = partTypeData.getPartTypeId();
      String desc = partTypeData.getPartDesc();
      PartType partPlaceHolder = new PartType(id, desc);

      // Cache the placeholder:
      partTypeCache.put(hashKey, partPlaceHolder);

      // Convert the remaining gRPC data fields:
      List<AnimalPart> animalPartList = new ArrayList<>();
      for (AnimalPartData animalPartData : partTypeData.getAnimalPartsOfThisTypeListList())
        animalPartList.add(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartData, animalPartCache, animalCache, partTypeCache, productCache, trayCache));


      // Construct a new PartType entity with the above read attributes set:
      PartType partType = partPlaceHolder.copy();
      partType.getPartList().addAll(animalPartList);

      // Put the final entity into the cache:
      partTypeCache.put(hashKey, partType);

      return partType;
    }


    public static List<PartType> convertToPartTypeList(PartTypesData data) {
      List<PartType> partTypeList = new ArrayList<>();

      // Convert List of PartTypesData to a java compatible list by iteration through each entry and running the method previously declared:
      for (PartTypeData partTypeData : data.getPartTypesList())
        partTypeList.add(convertToPartType(partTypeData, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

      // return a new List of PartType entities:
      return partTypeList;
    }
}
