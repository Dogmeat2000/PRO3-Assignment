package server.controller.grpc;

import grpc.AnimalData;
import shared.model.entities.Animal;

import java.math.BigDecimal;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcAnimalDataToAnimal
{
  /** Converts database/gRPC compatible AnimalData information into a application compatible Animal entity */
  public static Animal convertToAnimal(AnimalData animalData) {
    if (animalData == null)
      return null;

    // Read AnimalData information from the gRPC data:
    long id = animalData.getAnimalId();
    BigDecimal weight = new BigDecimal(animalData.getAnimalWeight());

    // Construct and return a new Animal entity with the above read attributes set:
    return new Animal(id, weight);
  }

  //TODO MISSING IMPLEMENTATION
}
