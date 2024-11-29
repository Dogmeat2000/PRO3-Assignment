package Client.common.model.adapters.gRPC_to_java;

import grpc.AnimalPartId;
import grpc.PartTypeData;
import grpc.PartTypesData;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.dto.PartTypeDto;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a gRPC connection data entries into a client compatible dto</p> */
public class GrpcPartTypeData_To_PartTypeDto
{

  /** <p>Converts gRPC compatible PartTypeData information into the client compatible PartTypeDto entity</p>*/
  public PartTypeDto convertToPartTypeDto(PartTypeData grpcData) {

    if (grpcData == null)
      return null;

    // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
    long id = GrpcId_To_LongId.ConvertToLongId(grpcData.getPartTypeId());
    String desc = grpcData.getPartDesc();

    // Construct a new PartType entity with the above read attributes set:
    PartTypeDto dto = new PartTypeDto(id, desc);

    // Add the AnimalPartIds:
    for (AnimalPartId animalPartId : grpcData.getAnimalPartIdsList())
      dto.addAnimalPartId(GrpcId_To_LongId.ConvertToLongId(animalPartId));

    return dto;
  }


  public List<PartTypeDto> convertToPartTypeDtoList(PartTypesData grpcData) {
    // Return an empty list, if received list is null or empty.
    if(grpcData == null || grpcData.getPartTypesList().isEmpty())
      return new ArrayList<>();

    // Convert List of PartTypesData to a java compatible list by iteration through each entry and running the method previously declared:
    List<PartTypeDto> partTypeDtoList = new ArrayList<>();
    for (PartTypeData partTypeData : grpcData.getPartTypesList())
      partTypeDtoList.add(convertToPartTypeDto(partTypeData));

    // Return a new List of PartTypeDto entities:
    return partTypeDtoList;
  }
}
