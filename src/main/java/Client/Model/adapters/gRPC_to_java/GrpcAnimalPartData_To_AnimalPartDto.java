package Client.model.adapters.gRPC_to_java;

import grpc.AnimalPartData;
import grpc.AnimalPartsData;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.dto.AnimalPartDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a gRPC connection data entries into a client compatible dto</p> */
public class GrpcAnimalPartData_To_AnimalPartDto
{

  /** <p>Converts database/gRPC compatible AnimalPartData information into the client compatible AnimalPartDto entity</p> */
  public AnimalPartDto convertToAnimalPartDto(AnimalPartData grpcData) {

    if (grpcData == null)
      return null;

    // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
    long partId = GrpcId_To_LongId.ConvertToLongId(grpcData.getAnimalPartId());
    BigDecimal weight = grpcData.getPartWeight().isEmpty() ? BigDecimal.ZERO : new BigDecimal(grpcData.getPartWeight());
    long typeId = GrpcId_To_LongId.ConvertToLongId(grpcData.getPartTypeId());
    long animalId = GrpcId_To_LongId.ConvertToLongId(grpcData.getAnimalId());
    long trayId = GrpcId_To_LongId.ConvertToLongId(grpcData.getTrayId());
    long productId = GrpcId_To_LongId.ConvertToLongId(grpcData.getProductId());

    return new AnimalPartDto(
        partId,
        weight,
        typeId,
        animalId,
        trayId,
        productId
    );
  }


  public List<AnimalPartDto> convertToAnimalPartDtoList(AnimalPartsData grpcData) {
    // Return an empty list, if received list is null or empty.
    if(grpcData == null || grpcData.getAnimalPartsList().isEmpty())
      return new ArrayList<>();

    // Convert List of AnimalPartsData to a java compatible list by iteration through each entry and running the method previously declared:
    List<AnimalPartDto> animalPartDtoList = new ArrayList<>();
    for (AnimalPartData animalPartData : grpcData.getAnimalPartsList())
      animalPartDtoList.add(convertToAnimalPartDto(animalPartData));

    // return a new List of AnimalPartDto entities:
    return animalPartDtoList;
  }
}
