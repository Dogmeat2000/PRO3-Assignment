package server.controller.grpc;

import grpc.AnimalData;
import shared.model.entities.Animal;

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

  //TODO MISSING IMPLEMENTATION
}
