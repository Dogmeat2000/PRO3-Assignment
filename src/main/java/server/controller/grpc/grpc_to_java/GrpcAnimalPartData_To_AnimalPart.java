package server.controller.grpc.grpc_to_java;

import grpc.AnimalPartData;
import grpc.AnimalPartsData;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcAnimalPartData_To_AnimalPart
{
  /** Converts database/gRPC compatible AnimalPartData information into a application compatible AnimalPart entity */
  public static AnimalPart convertToAnimalPart(AnimalPartData animalPartData,
      Map<String, AnimalPart> animalPartCache,
      Map<String, Animal> animalCache,
      Map<String, PartType> partTypeCache,
      Map<String, Product> productCache,
      Map<String, Tray> trayCache) {

    if (animalPartData == null)
      return null;

    // Utilize an embedded cache, to avoid both stale data and infinite recursion. In cases where other conversion algorithms
    // call this conversion, the value inside the cache will simply be used once converted,
    // instead of an infinite recursive attempt:
    String hashKey = "" + animalPartData.getAnimalPartId().getAnimalPartId();
    if(animalPartCache.containsKey(hashKey))
      return animalPartCache.get(hashKey);

    // If not already cached, create an initial placeholder cached version, to avoid infinite recursion:
    long partId = GrpcId_To_LongId.ConvertToLongId(animalPartData.getAnimalPartId());
    BigDecimal weight = animalPartData.getPartWeight().isEmpty() ? BigDecimal.ZERO : new BigDecimal(animalPartData.getPartWeight());
    AnimalPart animalPartPlaceHolder = new AnimalPart(partId, weight, null, null, null, null);

    // Cache the placeholder:
    animalPartCache.put(hashKey, animalPartPlaceHolder);

    // Convert the remaining gRPC data fields:
    Animal animal = GrpcAnimalData_To_Animal.convertToAnimal(animalPartData.getAnimal(), animalPartCache, animalCache, partTypeCache, productCache, trayCache);
    Tray tray = GrpcTrayData_To_Tray.convertToTray(animalPartData.getTray(), animalPartCache, animalCache, partTypeCache, productCache, trayCache);
    PartType partType = GrpcPartTypeData_To_PartType.convertToPartType(animalPartData.getPartType(), animalPartCache, animalCache, partTypeCache, productCache, trayCache);
    Product product = GrpcProductData_To_Product.convertToProduct(animalPartData.getProduct(), animalPartCache, animalCache, partTypeCache, productCache, trayCache);

    // Construct and return a new AnimalPart entity with the above read attributes set:
    //AnimalPart animalPart = animalPartPlaceHolder.copy();
    AnimalPart animalPart = new AnimalPart(
        partId,
        weight,
        partType,
        animal,
        tray,
        product
    );
    /*animalPart.setAnimal(animal);
    animalPart.setType(partType);
    animalPart.setTray(tray);
    animalPart.setProduct(product);*/

    // Put the final entity into the cache:
    animalPartCache.put(hashKey, animalPart);

    return animalPart;
  }


  public static List<AnimalPart> convertToAnimalPartList(AnimalPartsData data) {
    List<AnimalPart> animalPartList = new ArrayList<>();

    // Convert List of AnimalPartsData to a java compatible list by iteration through each entry and running the method previously declared:
    for (AnimalPartData animalPartData : data.getAnimalPartsList())
      animalPartList.add(convertToAnimalPart(animalPartData, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    // return a new List of AnimalPart entities:
    return animalPartList;
  }
}
