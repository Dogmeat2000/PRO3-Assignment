package server.controller.grpc.grpc_to_java;

import grpc.AnimalPartData;
import grpc.AnimalPartsData;
import shared.model.entities.AnimalPart;

import java.math.BigDecimal;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcAnimalPartData_To_AnimalPart
{
  /** Converts database/gRPC compatible AnimalPartData information into a application compatible AnimalPart entity */
  public static AnimalPart convertToAnimalPart(AnimalPartData animalPartData) {
    if (animalPartData == null)
      return null;

    // Read AnimalData information from the gRPC data:
    long id = animalPartData.getAnimalId();
    long animalId = animalPartData.getAnimalId();
    long trayId = animalPartData.getTrayId();
    long typeId = animalPartData.getPartTypeId();
    BigDecimal weight = new BigDecimal(animalPartData.getPartWeight());

    // Construct and return a new Animal entity with the above read attributes set:
    return new AnimalPart(id, weight, typeId, animalId, trayId);
  }

  public static List<AnimalPart> convertToAnimalPartList(AnimalPartsData data) {

    // Construct and return a new List of AnimalPart entities:
    return data.getAnimalPartsList().stream().map(server.controller.grpc.grpc_to_java.GrpcAnimalPartData_To_AnimalPart::convertToAnimalPart).toList();
  }
}
