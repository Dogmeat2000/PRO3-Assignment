package server.controller.grpc.grpc_to_java;

import grpc.AnimalData;
import grpc.AnimalsData;
import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    List<AnimalPart> animalPartList = new ArrayList<>(animalData.getAnimalPartListList().stream().map(GrpcAnimalPartData_To_AnimalPart::convertToAnimalPart).toList());

    // Construct and return a new Animal entity with the above read attributes set:
    Animal animal = new Animal(id, weight);
    animal.getPartList().addAll(animalPartList);
    return animal;
  }

  public static List<Animal> convertToAnimalList(AnimalsData data) {

    // Construct and return a new List of Animal entities:
    return data.getAnimalsList().stream().map(GrpcAnimalData_To_Animal::convertToAnimal).toList();
  }
}
