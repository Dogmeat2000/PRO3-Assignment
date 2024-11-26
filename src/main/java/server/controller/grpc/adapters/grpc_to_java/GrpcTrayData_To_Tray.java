package server.controller.grpc.adapters.grpc_to_java;

import grpc.*;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import server.model.persistence.entities.*;
import server.model.persistence.service.AnimalPartService;
import server.model.persistence.service.PartTypeService;
import server.model.persistence.service.ProductService;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.exceptions.persistance.NotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a gRPC connection data entries into application compatible entities</p> */
@Component
public class GrpcTrayData_To_Tray
{
  private final AnimalPartService animalPartService;
  private final PartTypeService partTypeService;
  private final ProductService productService;

  @Autowired
  public GrpcTrayData_To_Tray(AnimalPartService animalPartService, PartTypeService partTypeService, ProductService productService){
    this.animalPartService = animalPartService;
    this.partTypeService = partTypeService;
    this.productService = productService;
  }

  /** <p>Converts gRPC compatible TrayData information into an application compatible Tray entity</p> */
  public Tray convertToTray(TrayData trayData) throws NotFoundException, DataIntegrityViolationException, PersistenceException {

    if (trayData == null)
      return null;

    // Convert the gRPC data fields, excluding any lists of other entities. These are queried from the repository based on the provided ids:
    long id = GrpcId_To_LongId.ConvertToLongId(trayData.getTrayId());
    BigDecimal maxWeight_kilogram = trayData.getMaxWeightKilogram().isEmpty() ? BigDecimal.ZERO : new BigDecimal(trayData.getMaxWeightKilogram());

    // Query database for the referenced PartType entities, for proper Object Relational Model (ORM) behavior:
    /*PartType trayType = null;
    try {
      trayType = partTypeService.readPartType(GrpcId_To_LongId.ConvertToLongId(trayData.getTrayTypeId()));
    } catch (NotFoundException ignored) {}*/

    // Query database for the referenced AnimalPart entities, for proper Object Relational Model (ORM) behavior:
    List<AnimalPart> animalParts = new ArrayList<>();
    for (AnimalPartId animalPartId : trayData.getAnimalPartIdsList())
      animalParts.add(animalPartService.readAnimalPart(GrpcId_To_LongId.ConvertToLongId(animalPartId)));

    // Query database for the referenced Product and Transfer entities, for proper Object Relational Model (ORM) behavior:
    List<Product> products = new ArrayList<>();
    List<TrayToProductTransfer> transfers = new ArrayList<>();
    try {
      for (ProductId productId : trayData.getProductidsList()){
        Product product = productService.readProduct(GrpcId_To_LongId.ConvertToLongId(productId));
        products.add(product);
        transfers.addAll(product.getTraySupplyJoinList());
      }
    } catch (NotFoundException ignored) {}

    // Construct new Tray entity:
    // Note: The tray weight is initially set to zero, since each execution of the addAnimalPart() method adds to the current weight.
    Tray tray = new Tray(id, maxWeight_kilogram);
    tray.addAllAnimalParts(animalParts);
    for (Product product : products)
      tray.addProduct(product);

    for (TrayToProductTransfer transfer: transfers)
      tray.addTransfer(transfer);

    return tray;
  }


  public List<Tray> convertToTrayList(TraysData data) {
    // Return an empty list, if received list is null or empty.
    if(data == null || data.getTraysList().isEmpty())
      return new ArrayList<>();

    // Convert List of TraysData to a java compatible list by iteration through each entry and running the method previously declared:
    List<Tray> trayList = new ArrayList<>();
    for (TrayData trayData : data.getTraysList())
      trayList.add(convertToTray(trayData));

    // return a new List of Tray entities:
    return trayList;
  }
}
