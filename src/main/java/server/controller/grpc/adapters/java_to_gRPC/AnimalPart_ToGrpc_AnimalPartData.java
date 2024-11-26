package server.controller.grpc.adapters.java_to_gRPC;

import grpc.*;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import server.model.persistence.entities.AnimalPart;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a application entities into a gRPC compatible formats</p> */
public class AnimalPart_ToGrpc_AnimalPartData
{
  /** <p>Converts a AnimalPart entity into a gRPC compatible AnimalPartData format</p> */
  public AnimalPartData convertToAnimalPartData(AnimalPart animalPart) {

    if (animalPart == null)
      return null;

    // Convert the java data fields:
    AnimalPartData.Builder animalbuilder = AnimalPartData.newBuilder();
    animalbuilder.setAnimalPartId(LongId_ToGrpc_Id.convertToAnimalPartId(animalPart.getPartId()));
    animalbuilder.setPartWeight(animalPart.getWeight_kilogram().toString());
    animalbuilder.setPartTypeId(LongId_ToGrpc_Id.convertToPartTypeId(animalPart.getType().getTypeId()));
    animalbuilder.setAnimalId(LongId_ToGrpc_Id.convertToAnimalId(animalPart.getAnimal().getId()));
    animalbuilder.setTrayId(LongId_ToGrpc_Id.convertToTrayId(animalPart.getTray().getTrayId()));
    if(animalPart.getProduct() != null)
      animalbuilder.setProductId(LongId_ToGrpc_Id.convertToProductId(animalPart.getProduct().getProductId()));

    return animalbuilder.build();
  }


  /** <p>Converts a List of AnimalParts into the gRPC compatible AnimalPartsData format</p> */
  public AnimalPartsData convertToAnimalPartsDataList(List<AnimalPart> animalParts) {
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
