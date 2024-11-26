package client.ui.Model.adapters.gRPC_to_java;

import grpc.AnimalData;
import grpc.AnimalPartId;
import grpc.AnimalsData;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.dto.AnimalDto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** <p>Responsible for converting a gRPC connection data entries into a client compatible dto</p> */
public class GrpcAnimalData_To_AnimalDto
{
  /** <p>Converts database/gRPC compatible AnimalData information into a client compatible AnimalDto entity</p> */
  public AnimalDto convertToAnimalDto(AnimalData grpcData) {

    if (grpcData == null)
      return null;

    // Convert the gRPC data fields:
    long id = grpcData.getAnimalId();
    BigDecimal weight = grpcData.getAnimalWeight().isEmpty() ? BigDecimal.ZERO : new BigDecimal(grpcData.getAnimalWeight());
    String origin = grpcData.getOrigin();
    Date arrivalDate;

    if(!grpcData.getArrivalDate().isEmpty())
      arrivalDate = Timestamp.valueOf(grpcData.getArrivalDate());
    else
      arrivalDate = null;

    List<Long> animalPartIds = new ArrayList<>();
    for (AnimalPartId animalPartId : grpcData.getAnimalPartIdsList())
      animalPartIds.add(GrpcId_To_LongId.ConvertToLongId(animalPartId));

    // Construct and return a new AnimalDto entity with the above read attributes set:
    return new AnimalDto(id, weight, origin, arrivalDate, animalPartIds);
  }


  public List<AnimalDto> convertToAnimalDtoList(AnimalsData grpcData) {
    // Return an empty list, if received list is null or empty.
    if(grpcData == null || grpcData.getAnimalsList().isEmpty())
      return new ArrayList<>();

    // Convert List of AnimalsData to a java compatible list by iteration through each entry and running the method previously declared:
    List<AnimalDto> animalDtoList = new ArrayList<>();
    for (AnimalData animalData : grpcData.getAnimalsList())
      animalDtoList.add(convertToAnimalDto(animalData));

    // return a new List of Animal entities:
    return animalDtoList;
  }
}
