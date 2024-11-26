package server.controller.grpc.adapters.grpc_to_java;

import grpc.AnimalPartData;
import grpc.AnimalPartsData;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import server.model.persistence.entities.*;
import server.model.persistence.service.AnimalService;
import server.model.persistence.service.PartTypeService;
import server.model.persistence.service.ProductService;
import server.model.persistence.service.TrayService;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.exceptions.persistance.NotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a gRPC connection data entries into application compatible entities</p> */
@Component
public class GrpcAnimalPartData_To_AnimalPart
{
  private final AnimalService animalService;
  private final PartTypeService partTypeService;
  private final ProductService productService;
  private final TrayService trayService;

  @Autowired
  public GrpcAnimalPartData_To_AnimalPart(AnimalService animalService,
      PartTypeService partTypeService,
      ProductService productService,
      TrayService trayService) {

    this.animalService = animalService;
    this.partTypeService = partTypeService;
    this.productService = productService;
    this.trayService = trayService;
  }

  /** <p>Converts gRPC compatible AnimalPartData information into a application compatible AnimalPart entity</p> */
  public AnimalPart convertToAnimalPart(AnimalPartData animalPartData) throws NotFoundException, DataIntegrityViolationException, PersistenceException {

    if (animalPartData == null)
      return null;

    // Convert the gRPC data fields, excluding any lists of other entities. These are queried from the repository based on the provided ids:
    long partId = GrpcId_To_LongId.ConvertToLongId(animalPartData.getAnimalPartId());
    BigDecimal weight = animalPartData.getPartWeight().isEmpty() ? BigDecimal.ZERO : new BigDecimal(animalPartData.getPartWeight());

    // Query database for the referenced PartType entity, for proper Object Relational Model (ORM) behavior:
    PartType partType = partTypeService.readPartType(GrpcId_To_LongId.ConvertToLongId(animalPartData.getPartTypeId()));

    // Query database for the referenced Animal entity, for proper Object Relational Model (ORM) behavior:
    Animal animal = animalService.readAnimal(GrpcId_To_LongId.ConvertToLongId(animalPartData.getAnimalId()));

    // Query database for the referenced Tray entity, for proper Object Relational Model (ORM) behavior:
    Tray tray = trayService.readTray(GrpcId_To_LongId.ConvertToLongId(animalPartData.getTrayId()));

    // Query database for the referenced Product entity, for proper Object Relational Model (ORM) behavior:
    Product product = null;
    if(GrpcId_To_LongId.ConvertToLongId(animalPartData.getProductId()) > 0)
      product = productService.readProduct(GrpcId_To_LongId.ConvertToLongId(animalPartData.getProductId()));

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


  public List<AnimalPart> convertToAnimalPartList(AnimalPartsData data) {
    // Return an empty list, if received list is null or empty.
    if(data == null || data.getAnimalPartsList().isEmpty())
      return new ArrayList<>();

    // Convert List of AnimalPartsData to a java compatible list by iteration through each entry and running the method previously declared:
    List<AnimalPart> animalPartList = new ArrayList<>();
    for (AnimalPartData animalPartData : data.getAnimalPartsList())
      animalPartList.add(convertToAnimalPart(animalPartData));

    // return a new List of AnimalPart entities:
    return animalPartList;
  }
}
