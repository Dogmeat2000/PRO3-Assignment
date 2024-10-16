package server.controller.grpc.grpc_to_java;

import grpc.AnimalPartData;
import grpc.AnimalPartsData;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcAnimalPartData_To_AnimalPart
{
  /** Converts database/gRPC compatible AnimalPartData information into a application compatible AnimalPart entity */
  public static AnimalPart convertToAnimalPart(AnimalPartData animalPartData) {
    if (animalPartData == null)
      return null;

    // Read AnimalPartData information from the gRPC data:
    long partId = GrpcId_To_LongId.ConvertToLongId(animalPartData.getAnimalPartId());
    BigDecimal weight = new BigDecimal(animalPartData.getPartWeight());
    PartType partType = GrpcPartTypeData_To_PartType.convertToPartType(animalPartData.getPartType());
    Animal animal = GrpcAnimalData_To_Animal.convertToAnimal(animalPartData.getAnimal());
    Tray tray = GrpcTrayData_To_Tray.convertToTray(animalPartData.getTray());
    Product product = GrpcProductData_To_Product.convertToProduct(animalPartData.getProduct());


    // Construct and return a new Animal entity with the above read attributes set:
    return new AnimalPart(partId, weight, partType, animal, tray, product);
  }


  public static List<AnimalPart> convertToAnimalPartList(AnimalPartsData data) {

    // Construct and return a new List of AnimalPart entities:
    return data.getAnimalPartsList().stream().map(server.controller.grpc.grpc_to_java.GrpcAnimalPartData_To_AnimalPart::convertToAnimalPart).toList();
  }
}
