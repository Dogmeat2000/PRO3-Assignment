package Client.common.model.adapters.java_to_gRPC;

import grpc.PartTypeData;
import grpc.PartTypesData;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.PartTypeDto;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a client dto into a database/gRPC compatible formats</p> */
public class PartTypeDto_ToGrpc_PartTypeData
{
  /** <p>Converts a PartTypeDto entity into a gRPC compatible PartTypeData format</p>
   * @param dto The PartTypeDto entity to convert
   * @return a gRPC compatible PartTypeData data type.
   * */
  public PartTypeData convertToPartTypeData(PartTypeDto dto) {

    if (dto == null)
      return null;

    // Convert the java data fields:
    PartTypeData.Builder builder = PartTypeData.newBuilder()
        .setPartTypeId(LongId_ToGrpc_Id.convertToPartTypeId(dto.getTypeId()))
        .setPartDesc(dto.getTypeDesc());

    for (long id : dto.getAnimalPartIdList())
      builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(id));

    return builder.build();
  }


  /** <p>Converts a List of PartTypeDtos into the gRPC compatible PartTypesData format</p>
   * @param partTypeDtos A list containing all the PartTypeDto entities to convert.
   * @return A gRPC compatible PartTypesData data type, containing all the converted entities.*/
  public PartTypesData convertToPartTypesDataList(List<PartTypeDto> partTypeDtos) {
    // Return an empty list, if received list is null or empty.
    if(partTypeDtos == null || partTypeDtos.isEmpty())
      return PartTypesData.newBuilder().addAllPartTypes(new ArrayList<>()).build();

    // Convert List of PartTypeDtos to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<PartTypeData> partTypeDataList = new ArrayList<>();
    for (PartTypeDto dto : partTypeDtos)
      partTypeDataList.add(convertToPartTypeData(dto));

    // Construct and return a new List of PartTypeData entities:
    return PartTypesData.newBuilder().addAllPartTypes(partTypeDataList).build();
  }
}
