package DataServer.controller.grpc.adapters.grpc_to_java;

import grpc.AnimalPartId;
import grpc.PartTypeData;
import grpc.PartTypesData;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import DataServer.model.persistence.entities.PartType;
import DataServer.model.persistence.service.AnimalPartService;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.ArrayList;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
@Component
public class GrpcPartTypeData_To_PartType
{
  private final AnimalPartService animalPartService;

  @Autowired
  public GrpcPartTypeData_To_PartType(AnimalPartService animalPartService){
    this.animalPartService = animalPartService;
  }

  /** <p>Converts gRPC compatible PartTypeData information into an application compatible PartType entity</p>*/
  public PartType convertToPartType(PartTypeData partTypeData) throws DataIntegrityViolationException, PersistenceException {

    if (partTypeData == null)
      return null;

    // Convert the gRPC data fields, excluding any lists of other entities. These are queried from the repository based on the provided ids:
    long id = GrpcId_To_LongId.ConvertToLongId(partTypeData.getPartTypeId());
    String desc = partTypeData.getPartDesc();

    // Construct a new PartType entity with the above read attributes set:
    PartType partType = new PartType(id, desc);

    // Convert the attached AnimalParts, for proper Object Relational Model (ORM) behavior:
    try {
      for (AnimalPartId animalPartId : partTypeData.getAnimalPartIdsList()) {
        animalPartService.readAnimalPart(GrpcId_To_LongId.ConvertToLongId(animalPartId));
      }
    } catch (NotFoundException e) {
      partType.setAnimalParts(new ArrayList<>());
    }

    return partType;
  }

  public List<PartType> convertToPartTypeList(PartTypesData data) {
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
