package server.controller.grpc.java_to_gRPC;

import grpc.AnimalPartData;
import grpc.AnimalPartsData;
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
        .setAnimalPartId(animalPart.getPart_id())
        .setPartWeight(animalPart.getWeight_kilogram().toString())
        .setPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(animalPart.getType()))
        .setAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animalPart.getAnimal()))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(animalPart.getTray()))
        .setProduct(Product_ToGrpc_ProductData.convertToProductData(animalPart.getProduct()));
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
