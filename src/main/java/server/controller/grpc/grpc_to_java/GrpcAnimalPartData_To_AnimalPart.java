package server.controller.grpc.grpc_to_java;

import grpc.AnimalPartData;
import grpc.AnimalPartsData;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcAnimalPartData_To_AnimalPart
{
  /** Converts database/gRPC compatible AnimalPartData information into a application compatible AnimalPart entity */
  public static AnimalPart convertToAnimalPart(AnimalPartData animalPartData) {

    if (animalPartData == null)
      return null;

    // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
    long partId = GrpcId_To_LongId.ConvertToLongId(animalPartData.getAnimalPartId());
    BigDecimal weight = animalPartData.getPartWeight().isEmpty() ? BigDecimal.ZERO : new BigDecimal(animalPartData.getPartWeight());
    Animal animal = GrpcAnimalData_To_Animal.convertToAnimal(animalPartData.getAnimal());
    Tray tray = GrpcTrayData_To_Tray.convertToTray(animalPartData.getTray());
    PartType partType = GrpcPartTypeData_To_PartType.convertToPartType(animalPartData.getPartType());
    Product product = GrpcProductData_To_Product.convertToProduct(animalPartData.getProduct());

    // Construct and return a new AnimalPart entity with the above read attributes set:

    return new AnimalPart(
        partId,
        weight,
        partType,
        animal,
        tray,
        product
    );
  }


  public static List<AnimalPart> convertToAnimalPartList(AnimalPartsData data) {
    List<AnimalPart> animalPartList = new ArrayList<>();

    // Convert List of AnimalPartsData to a java compatible list by iteration through each entry and running the method previously declared:
    for (AnimalPartData animalPartData : data.getAnimalPartsList())
      animalPartList.add(convertToAnimalPart(animalPartData));

    // return a new List of AnimalPart entities:
    return animalPartList;
  }
}
