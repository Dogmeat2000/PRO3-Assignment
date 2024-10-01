package server.controller.grpc;

import grpc.AnimalData;
import grpc.AnimalsData;
import shared.model.entities.Animal;

import java.util.List;

/** Responsible for converting a application entities into a database/gRPC compatible formats */
public class Animal_ToGrpc_AnimalData
{
  /** Converts a Animal entity into a database/gRPC compatible AnimalData format */
  public static AnimalData ConvertToAnimalData(Animal animal) {
    if (animal == null)
      return null;

    AnimalData.Builder animalBuilder = AnimalData.newBuilder()
        .setAnimalId(animal.getId())
        .setAnimalWeight(animal.getWeight().toString());

    return animalBuilder.build();
  }


  /** Converts a List of Animals into the gRPC compatible AnimalsData format */
  public static AnimalsData convertToAnimalsDataList(List<Animal> animals) {
    // Convert List of Animals to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<AnimalData> animalsDataList = animals.stream().map(Animal_ToGrpc_AnimalData::ConvertToAnimalData).toList();

    // Construct and return a new List of AnimalData entities:
    return AnimalsData.newBuilder().addAllAnimals(animalsDataList).build();
  }

  //TODO MISSING IMPLEMENTATION
}
