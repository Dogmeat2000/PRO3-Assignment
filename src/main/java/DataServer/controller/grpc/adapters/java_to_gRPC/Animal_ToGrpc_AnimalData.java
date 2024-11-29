package DataServer.controller.grpc.adapters.java_to_gRPC;

import grpc.*;
import DataServer.model.persistence.entities.Animal;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a application entities into a gRPC compatible formats</p> */
public class Animal_ToGrpc_AnimalData
{

  /** <p>Converts an Animal entity into a gRPC compatible AnimalData format</p> */
  public AnimalData convertToAnimalData(Animal animal) {

    if (animal == null)
      return null;

    // Convert the java data fields:
    AnimalData.Builder builder = AnimalData.newBuilder()
        .setAnimalId(animal.getId())
        .setAnimalWeight(animal.getWeight_kilogram().toString())
        .setOrigin(animal.getOrigin())
        .setArrivalDate(animal.getArrivalDate().toString());

    // Convert AnimalPartIds:
    for (long id : animal.getAnimalPartIdList())
      builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(id));

    return builder.build();
  }


  /** <p>Converts a List of Animals into the gRPC compatible AnimalsData format</p> */
  public AnimalsData convertToAnimalsDataList(List<Animal> animals) {
    // Return an empty list, if received list is null or empty.
    if(animals == null || animals.isEmpty())
      return AnimalsData.newBuilder().addAllAnimals(new ArrayList<>()).build();

    // Convert List of Animals to a gRPC compatible list by iterating through each entry and running the method previously declared:
    List<AnimalData> animalDataList = new ArrayList<>();
    for (Animal animal : animals)
      animalDataList.add(convertToAnimalData(animal));

    // Construct and return a new List of AnimalData entities:
    return AnimalsData.newBuilder().addAllAnimals(animalDataList).build();
  }
}
