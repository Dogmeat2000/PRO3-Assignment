package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class Animal_ToGrpc_AnimalData
{
  /** Converts a Animal entity into a database/gRPC compatible AnimalData format */
  public static AnimalData convertToAnimalData(Animal animal,
      Map<String, AnimalPartData> animalPartDataCache,
      Map<String, AnimalData> animalDataCache,
      Map<String, PartTypeData> partTypeDataCache,
      Map<String, ProductData> productDataCache,
      Map<String, TrayData> trayDataCache) {

    if (animal == null)
      return null;

    // Utilize an embedded cache, to avoid both stale data and infinite recursion. In cases where other conversion algorithms
    // call this conversion, the value inside the cache will simply be used once converted,
    // instead of an infinite recursive attempt:
    String hashKey = "" + animal.getId();
    if(animalDataCache.containsKey(hashKey))
      return animalDataCache.get(hashKey);

    // If not already cached, create an initial placeholder cached version, to avoid infinite recursion:
    AnimalData.Builder animalDataPlaceHolderBuilder = AnimalData.newBuilder()
        .setAnimalId(animal.getId())
        .setAnimalWeight(animal.getWeight_kilogram().toString());

    // Cache the placeholder:
    animalDataCache.put(hashKey, animalDataPlaceHolderBuilder.build());

    // Convert the remaining data fields:
    List<AnimalPart> partList = animal.getPartList();

    if(partList == null)
      partList = new ArrayList<>();

    List<AnimalPartData> animalPartDataList = new ArrayList<>();
    for (AnimalPart animalPart : partList)
      animalPartDataList.add(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart, animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache));

    AnimalData animalData = animalDataPlaceHolderBuilder
        .addAllAnimalPartList(animalPartDataList)
        .build();

    // Replace the cached placeholder with the final version of this entity:
    animalDataCache.put(hashKey, animalData);
    return animalData;
  }


  /** Converts a List of Animals into the gRPC compatible AnimalsData format */
  public static AnimalsData convertToAnimalsDataList(List<Animal> animals) {
    List<AnimalData> animalDataList = new ArrayList<>();

    // Convert List of Animals to a gRPC compatible list by iteration through each entry and running the method previously declared:
    for (Animal animal : animals)
      animalDataList.add(convertToAnimalData(animal, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    // Construct and return a new List of AnimalData entities:
    return AnimalsData.newBuilder().addAllAnimals(animalDataList).build();
  }
}
