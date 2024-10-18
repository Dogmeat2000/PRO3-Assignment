package server.controller.grpc.grpc_to_java;

import grpc.*;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcAnimalData_To_Animal
{
  /** Converts database/gRPC compatible AnimalData information into a application compatible Animal entity */
  public static Animal convertToAnimal(AnimalData animalData,
      Map<String, AnimalPart> animalPartCache,
      Map<String, Animal> animalCache,
      Map<String, PartType> partTypeCache,
      Map<String, Product> productCache,
      Map<String, Tray> trayCache) {

    if (animalData == null)
      return null;

    // Utilize an embedded cache, to avoid both stale data and infinite recursion. In cases where other conversion algorithms
    // call this conversion, the value inside the cache will simply be used once converted,
    // instead of an infinite recursive attempt:
    String hashKey = "" + animalData.getAnimalId();
    if(animalCache.containsKey(hashKey))
      return animalCache.get(hashKey);

    // If not already cached, create an initial placeholder cached version, to avoid infinite recursion:
    long id = animalData.getAnimalId();
    BigDecimal weight = animalData.getAnimalWeight().isEmpty() ? BigDecimal.ZERO : new BigDecimal(animalData.getAnimalWeight());
    Animal animalPlaceHolder = new Animal(id, weight);

    // Cache the placeholder:
    animalCache.put(hashKey, animalPlaceHolder);

    // Convert the remaining gRPC data fields:
    List<AnimalPart> animalPartList = new ArrayList<>();
    for (AnimalPartData animalPartData : animalData.getAnimalPartListList())
      animalPartList.add(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartData, animalPartCache, animalCache, partTypeCache, productCache, trayCache));

    // Construct a new Animal entity with the above read attributes set:
    Animal animal = animalPlaceHolder.copy();
    animal.getPartList().addAll(animalPartList);

    // Put the final entity into the cache:
    animalCache.put(hashKey, animal);

    return animal;
  }


  public static List<Animal> convertToAnimalList(AnimalsData data) {
    List<Animal> animalList = new ArrayList<>();

    // Convert List of AnimalsData to a java compatible list by iteration through each entry and running the method previously declared:
    for (AnimalData animalData : data.getAnimalsList())
      animalList.add(convertToAnimal(animalData, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    // return a new List of Animal entities:
    return animalList;
  }
}
