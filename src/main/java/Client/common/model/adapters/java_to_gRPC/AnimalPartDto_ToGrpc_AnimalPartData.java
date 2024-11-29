package Client.common.model.adapters.java_to_gRPC;

import grpc.*;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.AnimalPartDto;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a client dto into a database/gRPC compatible formats</p> */
public class AnimalPartDto_ToGrpc_AnimalPartData
{
  /** <p>Converts a AnimalPartDto Data Transfer Object into the database/gRPC compatible AnimalPartData format</p> */
  public AnimalPartData convertToAnimalPartData(AnimalPartDto dto) {

    if (dto == null)
      return null;

    // Convert the java data fields:
    AnimalPartData.Builder animalBuilder = AnimalPartData.newBuilder();
    animalBuilder.setAnimalPartId(LongId_ToGrpc_Id.convertToAnimalPartId(dto.getPartId()));
    animalBuilder.setPartWeight(dto.getWeight_kilogram().toString());
    animalBuilder.setPartTypeId(LongId_ToGrpc_Id.convertToPartTypeId(dto.getTypeId()));
    animalBuilder.setAnimalId(LongId_ToGrpc_Id.convertToAnimalId(dto.getAnimalId()));
    animalBuilder.setTrayId(LongId_ToGrpc_Id.convertToTrayId(dto.getTrayId()));
    if(dto.getProductId() != 0)
      animalBuilder.setProductId(LongId_ToGrpc_Id.convertToProductId(dto.getProductId()));
    else
      animalBuilder.setProductId(LongId_ToGrpc_Id.convertToProductId(0));

    return animalBuilder.build();
  }


  /** <p>Converts a List of AnimalParts into the gRPC compatible AnimalPartsData format</p> */
  public AnimalPartsData convertToAnimalPartsDataList(List<AnimalPartDto> animalPartDtos) {
    // Return an empty list, if received list is null or empty.
    if(animalPartDtos == null || animalPartDtos.isEmpty())
      return AnimalPartsData.newBuilder().addAllAnimalParts(new ArrayList<>()).build();

    // Convert List of AnimalPartDtos to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<AnimalPartData> animalPartsDataList = new ArrayList<>();
    for (AnimalPartDto animalPartDto : animalPartDtos)
      animalPartsDataList.add(convertToAnimalPartData(animalPartDto));

    // Construct and return a new List of AnimalPartData entities:
    return AnimalPartsData.newBuilder().addAllAnimalParts(animalPartsDataList).build();
  }
}
