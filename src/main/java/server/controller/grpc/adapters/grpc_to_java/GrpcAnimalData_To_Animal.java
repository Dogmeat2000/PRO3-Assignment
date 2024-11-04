package server.controller.grpc.adapters.grpc_to_java;

import grpc.*;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcAnimalData_To_Animal
{
  /** Converts database/gRPC compatible AnimalData information into a application compatible Animal entity */
  public static Animal convertToAnimal(AnimalData animalData) {

    if (animalData == null)
      return null;

    // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
    long id = animalData.getAnimalId();
    BigDecimal weight = animalData.getAnimalWeight().isEmpty() ? BigDecimal.ZERO : new BigDecimal(animalData.getAnimalWeight());
    String origin = animalData.getOrigin();
    Timestamp arrivalDate = Timestamp.valueOf(animalData.getArrivalDate());
    List<Long> animalPartIdList = new ArrayList<>(animalData.getAnimalPartIdsList());

    // Construct a new Animal entity with the above read attributes set:
    Animal animal = new Animal(id, weight, origin, arrivalDate);
    animal.setAnimalPartIdList(animalPartIdList);

    return animal;
  }


  public static List<Animal> convertToAnimalList(AnimalsData data) {
    // Return an empty list, if received list is null or empty.
    if(data == null || data.getAnimalsList().isEmpty())
      return new ArrayList<>();

    // Convert List of AnimalsData to a java compatible list by iteration through each entry and running the method previously declared:
    List<Animal> animalList = new ArrayList<>();
    for (AnimalData animalData : data.getAnimalsList())
      animalList.add(convertToAnimal(animalData));

    // return a new List of Animal entities:
    return animalList;
  }
}
