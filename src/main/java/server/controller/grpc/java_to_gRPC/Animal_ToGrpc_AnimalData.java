package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.Animal;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class Animal_ToGrpc_AnimalData
{
  /** Converts a Animal entity into a database/gRPC compatible AnimalData format */
  public static AnimalData convertToAnimalData(Animal animal) {

    if (animal == null)
      return null;

    // Convert the java data fields, excluding any lists of other entities. These need to be queried separately by the receiving service layer:
    return AnimalData.newBuilder()
        .setAnimalId(animal.getId())
        .setAnimalWeight(animal.getWeight_kilogram().toString())
        .setOrigin(animal.getOrigin())
        .setArrivalDate(animal.getArrival_date().toString())
        .addAllAnimalPartIds(animal.getAnimalPartIdList())
        .build();
  }


  /** Converts a List of Animals into the gRPC compatible AnimalsData format */
  public static AnimalsData convertToAnimalsDataList(List<Animal> animals) {
    // Return an empty list, if received list is null or empty.
    if(animals == null || animals.isEmpty())
      return AnimalsData.newBuilder().addAllAnimals(new ArrayList<>()).build();

    // Convert List of Animals to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<AnimalData> animalDataList = new ArrayList<>();
    for (Animal animal : animals)
      animalDataList.add(convertToAnimalData(animal));

    // Construct and return a new List of AnimalData entities:
    return AnimalsData.newBuilder().addAllAnimals(animalDataList).build();
  }
}
