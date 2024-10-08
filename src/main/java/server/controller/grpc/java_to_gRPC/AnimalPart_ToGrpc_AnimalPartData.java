package server.controller.grpc.java_to_gRPC;

import grpc.AnimalData;
import grpc.AnimalPartData;
import grpc.AnimalPartsData;
import grpc.AnimalsData;
import shared.model.entities.Animal;
import shared.model.entities.AnimalPart;

import java.util.List;

/** Responsible for converting a application entities into a database/gRPC compatible formats */
public class AnimalPart_ToGrpc_AnimalPartData
{
  /** Converts a AnimalPart entity into a database/gRPC compatible AnimalPartData format */
  public static AnimalPartData convertToAnimalPartData(AnimalPart animalPart) {
    if (animalPart == null)
      return null;

    AnimalPartData.Builder animalPartBuilder = AnimalPartData.newBuilder()
        .setAnimalId(animalPart.getPart_id())
        .setPartTypeId(animalPart.getType().getTypeId())
        .setTrayId(animalPart.getTray().getTray_id())
        .setPartWeight(animalPart.getWeight().toString());

    return animalPartBuilder.build();
  }


  /** Converts a List of AnimalParts into the gRPC compatible AnimalPartsData format */
  public static AnimalPartsData convertToAnimalPartsDataList(List<AnimalPart> animalParts) {
    // Convert List of Animals to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<AnimalPartData> animalPartsDataList = animalParts.stream().map(AnimalPart_ToGrpc_AnimalPartData::convertToAnimalPartData).toList();

    // Construct and return a new List of AnimalData entities:
    return AnimalPartsData.newBuilder().addAllAnimalParts(animalPartsDataList).build();
  }
}
