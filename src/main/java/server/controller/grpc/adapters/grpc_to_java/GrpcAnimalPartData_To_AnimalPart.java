package server.controller.grpc.adapters.grpc_to_java;

import grpc.AnimalPartData;
import grpc.AnimalPartsData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
@Component
@Scope("singleton")
public class GrpcAnimalPartData_To_AnimalPart
{
  private GrpcAnimalData_To_Animal animalConverter = null;
  private GrpcTrayData_To_Tray trayConverter = null;
  private GrpcProductData_To_Product productConverter = null;
  private GrpcPartTypeData_To_PartType partTypeConverter = null;

  /** <p>Converts database/gRPC compatible AnimalPartData information into a application compatible AnimalPart entity</p> */
  public AnimalPart convertToAnimalPart(AnimalPartData animalPartData, int maxNestingDepth) {

    if (animalPartData == null || maxNestingDepth < 0)
      return null;

    int currentNestingDepth = maxNestingDepth-1;

    // Lazy instantiate the required converters as needed:
    if(animalConverter == null)
      animalConverter = new GrpcAnimalData_To_Animal();
    if(trayConverter == null)
      trayConverter = new GrpcTrayData_To_Tray();
    if(productConverter == null)
      productConverter = new GrpcProductData_To_Product();
    if(partTypeConverter == null)
      partTypeConverter = new GrpcPartTypeData_To_PartType();

    // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
    long partId = GrpcId_To_LongId.ConvertToLongId(animalPartData.getAnimalPartId());
    BigDecimal weight = animalPartData.getPartWeight().isEmpty() ? BigDecimal.ZERO : new BigDecimal(animalPartData.getPartWeight());
    Animal animal = animalConverter.convertToAnimal(animalPartData.getAnimal(), currentNestingDepth);
    Tray tray = trayConverter.convertToTray(animalPartData.getTray(), currentNestingDepth);
    PartType partType = partTypeConverter.convertToPartType(animalPartData.getPartType(), currentNestingDepth);
    Product product = productConverter.convertToProduct(animalPartData.getProduct(), currentNestingDepth);

    // Construct and return a new AnimalPart entity with the above read attributes set:
    return new AnimalPart(
        partId,
        weight,
        partType,
        animal,
        tray,
        product
    );
  }


  public List<AnimalPart> convertToAnimalPartList(AnimalPartsData data, int maxNestingDepth) {
    // Return an empty list, if received list is null or empty.
    if(data == null || data.getAnimalPartsList().isEmpty())
      return new ArrayList<>();

    // Convert List of AnimalPartsData to a java compatible list by iteration through each entry and running the method previously declared:
    List<AnimalPart> animalPartList = new ArrayList<>();
    for (AnimalPartData animalPartData : data.getAnimalPartsList())
      animalPartList.add(convertToAnimalPart(animalPartData, maxNestingDepth));

    // return a new List of AnimalPart entities:
    return animalPartList;
  }
}
