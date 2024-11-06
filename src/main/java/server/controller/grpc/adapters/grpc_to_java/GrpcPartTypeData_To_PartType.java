package server.controller.grpc.adapters.grpc_to_java;

import grpc.AnimalPartData;
import grpc.PartTypeData;
import grpc.PartTypesData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shared.model.entities.*;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.ArrayList;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
@Component
@Scope("singleton")
public class GrpcPartTypeData_To_PartType
{
  private GrpcAnimalPartData_To_AnimalPart animalPartConverter = null;

    /** <p>Converts database/gRPC compatible PartTypeData information into an application compatible PartType entity</p>*/
    public PartType convertToPartType(PartTypeData partTypeData, int maxNestingDepth) {

      if (partTypeData == null || maxNestingDepth < 0)
        return null;

      int currentNestingDepth = maxNestingDepth-1;

      // Lazy instantiate the required converters as needed:
      if(animalPartConverter == null)
        animalPartConverter = new GrpcAnimalPartData_To_AnimalPart();

      // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
      long id = partTypeData.getPartTypeId();
      String desc = partTypeData.getPartDesc();
      List<Long> animalPartIdList = new ArrayList<>(partTypeData.getAnimalPartIdsList());

      // Construct a new PartType entity with the above read attributes set:
      PartType partType = new PartType(id, desc);
      partType.setAnimalPartIdList(animalPartIdList);

      // Convert the attached AnimalParts, for proper Object Relational Model (ORM) behavior:
      try {
        for (AnimalPartData animalPartData : partTypeData.getAnimalPartListList()) {
          partType.addAnimalPart(animalPartConverter.convertToAnimalPart(animalPartData, currentNestingDepth));
        }
      } catch (NotFoundException e) {
        partType.setAnimalParts(new ArrayList<>());
      }

      return partType;
    }


    public List<PartType> convertToPartTypeList(PartTypesData data, int maxNestingDepth) {
      // Return an empty list, if received list is null or empty.
      if(data == null || data.getPartTypesList().isEmpty())
        return new ArrayList<>();

      // Convert List of PartTypesData to a java compatible list by iteration through each entry and running the method previously declared:
      List<PartType> partTypeList = new ArrayList<>();
      for (PartTypeData partTypeData : data.getPartTypesList())
        partTypeList.add(convertToPartType(partTypeData, maxNestingDepth));

      // return a new List of PartType entities:
      return partTypeList;
    }
}
