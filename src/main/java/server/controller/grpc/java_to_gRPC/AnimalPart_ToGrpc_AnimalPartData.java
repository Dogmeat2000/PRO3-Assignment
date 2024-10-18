package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.AnimalPart;

import java.util.ArrayList;
import java.util.List;

/** Responsible for converting a application entities into a database/gRPC compatible formats */
public class AnimalPart_ToGrpc_AnimalPartData
{
  /** Converts a AnimalPart entity into a database/gRPC compatible AnimalPartData format */
  public static AnimalPartData convertToAnimalPartData(AnimalPart animalPart) {

    if (animalPart == null)
      return null;

    // Convert the java data fields, excluding any lists of other entities. These need to be queried separately by the receiving service layer:
    return AnimalPartData.newBuilder()
        .setAnimalPartId(LongId_ToGrpc_Id.convertToAnimalPartId(animalPart.getPart_id()))
        .setPartWeight(animalPart.getWeight_kilogram().toString())
        .setPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(animalPart.getType()))
        .setAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animalPart.getAnimal()))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(animalPart.getTray()))
        .setProduct(Product_ToGrpc_ProductData.convertToProductData(animalPart.getProduct()))
        .build();
  }


  public static UpdatedAnimalPartData covertToUpdatedAnimalPartData(AnimalPart oldData, AnimalPart newData) {
    if (oldData == null || newData == null)
      return null;

    UpdatedAnimalPartData.Builder updatedAnimalPartBuilder = UpdatedAnimalPartData.newBuilder()
        .setOldData(convertToAnimalPartData(oldData))
        .setNewData(convertToAnimalPartData(newData));

    return updatedAnimalPartBuilder.build();
  }


  /** Converts a List of AnimalParts into the gRPC compatible AnimalPartsData format */
  public static AnimalPartsData convertToAnimalPartsDataList(List<AnimalPart> animalParts) {
    List<AnimalPartData> animalPartsDataList = new ArrayList<>();

    // Convert List of AnimalParts to a gRPC compatible list by iteration through each entry and running the method previously declared:
    for (AnimalPart animalPart : animalParts)
      animalPartsDataList.add(convertToAnimalPartData(animalPart));

    // Construct and return a new List of AnimalPartData entities:
    return AnimalPartsData.newBuilder().addAllAnimalParts(animalPartsDataList).build();
  }
}
