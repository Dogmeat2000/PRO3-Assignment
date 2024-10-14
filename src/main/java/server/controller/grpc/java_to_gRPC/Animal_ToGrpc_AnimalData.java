package server.controller.grpc.java_to_gRPC;

import grpc.AnimalData;
import grpc.AnimalsData;
import shared.model.entities.Animal;

import java.util.List;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class Animal_ToGrpc_AnimalData
{
  /** Converts a Animal entity into a database/gRPC compatible AnimalData format */
  public static AnimalData convertToAnimalData(Animal animal) {
    if (animal == null)
      return null;

    AnimalData.Builder animalBuilder = AnimalData.newBuilder()
        .setAnimalId(animal.getId())
        .setAnimalWeight(animal.getWeight_kilogram().toString())
        .addAllAnimalPartList(animal.getPartList().stream().map(AnimalPart_ToGrpc_AnimalPartData::convertToAnimalPartData).toList());
    return animalBuilder.build();
  }


  /** Converts a List of Animals into the gRPC compatible AnimalsData format */
  public static AnimalsData convertToAnimalsDataList(List<Animal> animals) {
    // Convert List of Animals to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<AnimalData> animalsDataList = animals.stream().map(Animal_ToGrpc_AnimalData::convertToAnimalData).toList();

    // Construct and return a new List of AnimalData entities:
    return AnimalsData.newBuilder().addAllAnimals(animalsDataList).build();
  }
}
