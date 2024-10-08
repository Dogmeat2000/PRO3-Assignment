package server.controller.grpc.java_to_gRPC;

import grpc.PartTypeData;
import grpc.PartTypesData;
import shared.model.entities.PartType;

import java.util.List;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class PartType_ToGrpc_PartTypeData
{
  /** <p>Converts a PartType entity into a database/gRPC compatible PartTypeData format</p>
   * @param partType The PartType entity to convert
   * @return a gRPC compatible PartTypeData data type.
   * */
  public static PartTypeData convertToPartTypeData(PartType partType) {
    if (partType == null)
      return null;

    PartTypeData.Builder builder = PartTypeData.newBuilder()
        .setPartTypeId(partType.getTypeId())
        .setPartDesc(partType.getTypeDesc());

    return builder.build();
  }


  /** <p>Converts a List of PartTypes into the gRPC compatible PartTypesData format</p>
   * @param partTypes A list containing all the PartType entities to convert.
   * @return A gRPC compatible PartTypesData data type, containing all the converted entities.*/
  public static PartTypesData convertToPartTypesDataList(List<PartType> partTypes) {
    // Convert List of Animals to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<PartTypeData> list = partTypes.stream().map(PartType_ToGrpc_PartTypeData::convertToPartTypeData).toList();

    // Construct and return a new List of AnimalData entities:
    return PartTypesData.newBuilder().addAllPartTypes(list).build();
  }
}
