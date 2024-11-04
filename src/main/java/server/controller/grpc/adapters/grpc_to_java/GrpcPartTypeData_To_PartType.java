package server.controller.grpc.adapters.grpc_to_java;

import grpc.PartTypeData;
import grpc.PartTypesData;
import shared.model.entities.*;

import java.util.ArrayList;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcPartTypeData_To_PartType
{
    /** Converts database/gRPC compatible PartTypeData information into an application compatible PartType entity */
    public static PartType convertToPartType(PartTypeData partTypeData) {

      if (partTypeData == null)
        return null;

      // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
      long id = partTypeData.getPartTypeId();
      String desc = partTypeData.getPartDesc();
      List<Long> animalPartIdList = new ArrayList<>(partTypeData.getAnimalPartIdsList());

      // Construct a new PartType entity with the above read attributes set:
      PartType partType = new PartType(id, desc);
      partType.setAnimalPartIdList(animalPartIdList);

      return partType;
    }


    public static List<PartType> convertToPartTypeList(PartTypesData data) {
      // Return an empty list, if received list is null or empty.
      if(data == null || data.getPartTypesList().isEmpty())
        return new ArrayList<>();

      // Convert List of PartTypesData to a java compatible list by iteration through each entry and running the method previously declared:
      List<PartType> partTypeList = new ArrayList<>();
      for (PartTypeData partTypeData : data.getPartTypesList())
        partTypeList.add(convertToPartType(partTypeData));

      // return a new List of PartType entities:
      return partTypeList;
    }
}
