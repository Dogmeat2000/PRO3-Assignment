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
    AnimalPartData.Builder animalbuilder = AnimalPartData.newBuilder();
    animalbuilder.setAnimalPartId(LongId_ToGrpc_Id.convertToAnimalPartId(animalPart.getPart_id()));
    animalbuilder.setAnimalPartId(LongId_ToGrpc_Id.convertToAnimalPartId(animalPart.getPart_id()));
    animalbuilder.setPartWeight(animalPart.getWeight_kilogram().toString());
    animalbuilder.setAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animalPart.getAnimal()));
    animalbuilder.setPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(animalPart.getType()));
    animalbuilder.setTray(Tray_ToGrpc_TrayData.convertToTrayData(animalPart.getTray()));

    // Only set a Product, if the value is not null. AnimalParts do not necessarily have a Product associated with them:
    if(animalPart.getProduct() != null)
      animalbuilder.setProduct(Product_ToGrpc_ProductData.convertToProductData(animalPart.getProduct()));

    return animalbuilder.build();
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
    // Return an empty list, if received list is null or empty.
    if(animalParts == null || animalParts.isEmpty())
      return AnimalPartsData.newBuilder().addAllAnimalParts(new ArrayList<>()).build();

    // Convert List of AnimalParts to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<AnimalPartData> animalPartsDataList = new ArrayList<>();
    for (AnimalPart animalPart : animalParts)
      animalPartsDataList.add(convertToAnimalPartData(animalPart));

    // Construct and return a new List of AnimalPartData entities:
    return AnimalPartsData.newBuilder().addAllAnimalParts(animalPartsDataList).build();
  }
}
