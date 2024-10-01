package server.controller.grpc;

import grpc.AnimalData;
import grpc.AnimalsData;
import shared.model.entities.Animal;

import java.math.BigDecimal;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcAnimalData_To_Animal
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

  public static List<Animal> convertToAnimalList(AnimalsData data) {

    // Construct and return a new List of Animal entities:
    return data.getAnimalsList().stream().map(GrpcAnimalData_To_Animal::convertToAnimal).toList();
  }
}
