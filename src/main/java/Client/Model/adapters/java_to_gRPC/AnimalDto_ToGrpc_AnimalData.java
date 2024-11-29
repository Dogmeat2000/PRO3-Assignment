package Client.model.adapters.java_to_gRPC;

import grpc.AnimalData;
import grpc.AnimalsData;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.AnimalDto;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a client dto into a database/gRPC compatible formats</p> */
public class AnimalDto_ToGrpc_AnimalData
{
    /** <p>Converts a AnimalDto entity into a database/gRPC compatible AnimalData format</p> */
    public AnimalData convertToAnimalData(AnimalDto dto) {

      if (dto == null)
        return null;

      // Convert the java data fields, excluding any lists of other entities. These need to be queried separately by the receiving service layer:
      AnimalData.Builder builder = AnimalData.newBuilder()
          .setAnimalId(dto.getAnimalId())
          .setAnimalWeight(dto.getWeight_kilogram().toString())
          .setOrigin(dto.getOrigin())
          .setArrivalDate(dto.getArrivalDate().toString());

      // Add all associated AnimalPartIds:
      if(dto.getAnimalPartIdList() != null) {
        for (long id : dto.getAnimalPartIdList()) {
          builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(id));
        }
      }


      return builder.build();
    }


    /** <p>Converts a List of AnimalDtos into the gRPC compatible AnimalsData format</p> */
    public AnimalsData convertToAnimalsDataList(List<AnimalDto> dtos) {
      // Return an empty list, if received list is null or empty.
      if(dtos == null || dtos.isEmpty())
        return AnimalsData.newBuilder().addAllAnimals(new ArrayList<>()).build();

      // Convert List of Animals to a gRPC compatible list by iteration through each entry and running the method previously declared:
      List<AnimalData> animalDataList = new ArrayList<>();
      for (AnimalDto dto : dtos)
        animalDataList.add(convertToAnimalData(dto));

      // Construct and return a new List of AnimalData entities:
      return AnimalsData.newBuilder().addAllAnimals(animalDataList).build();
    }
}
