package server.controller.grpc.grpc_to_java;

import grpc.PartTypeData;
import grpc.PartTypesData;
import shared.model.entities.PartType;

import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcPartTypeData_To_PartType
{
    /** Converts database/gRPC compatible PartTypeData information into an application compatible PartType entity */
    public static PartType convertToPartType(PartTypeData partTypeData) {
      if (partTypeData == null)
        return null;

      // Read PartType information from the gRPC data:
      long id = partTypeData.getPartTypeId();
      String desc = partTypeData.getPartDesc();

      // Construct and return a new PartType entity with the above read attributes set:
      return new PartType(id, desc);
    }


    public static List<PartType> convertToPartTypeList(PartTypesData data) {

      // Construct and return a new List of Animal entities:
      return data.getPartTypesList().stream().map(server.controller.grpc.grpc_to_java.GrpcPartTypeData_To_PartType::convertToPartType).toList();
    }
}
