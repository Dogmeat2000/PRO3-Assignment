package DataServer.controller.grpc.adapters.java_to_gRPC;

import grpc.*;
import DataServer.model.persistence.entities.PartType;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a application entities into a gRPC compatible formats</p> */
public class PartType_ToGrpc_PartTypeData
{
  /** <p>Converts a PartType entity into a gRPC compatible PartTypeData format</p>
   * @param partType The PartType entity to convert
   * @return a gRPC compatible PartTypeData data type.
   * */
  public PartTypeData convertToPartTypeData(PartType partType) {

    if (partType == null)
      return null;

    // Convert the java data fields:
    PartTypeData.Builder builder = PartTypeData.newBuilder()
        .setPartTypeId(LongId_ToGrpc_Id.convertToPartTypeId(partType.getTypeId()))
        .setPartDesc(partType.getTypeDesc());

    for (long id : partType.getAnimalPartIdList())
      builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(id));

    return builder.build();
  }


  /** <p>Converts a List of PartTypes into the gRPC compatible PartTypesData format</p>
   * @param partTypes A list containing all the PartType entities to convert.
   * @return A gRPC compatible PartTypesData data type, containing all the converted entities.*/
  public PartTypesData convertToPartTypesDataList(List<PartType> partTypes) {
    // Return an empty list, if received list is null or empty.
    if(partTypes == null || partTypes.isEmpty())
      return PartTypesData.newBuilder().addAllPartTypes(new ArrayList<>()).build();

    // Convert List of PartTypes to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<PartTypeData> partTypeDataList = new ArrayList<>();
    for (PartType partType : partTypes)
      partTypeDataList.add(convertToPartTypeData(partType));

    // Construct and return a new List of PartTypeData entities:
    return PartTypesData.newBuilder().addAllPartTypes(partTypeDataList).build();
  }
}
