package DataServer.controller.grpc.adapters.grpc_to_java;

import grpc.*;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import DataServer.model.persistence.entities.AnimalPart;
import DataServer.model.persistence.entities.Product;
import DataServer.model.persistence.entities.Tray;
import DataServer.model.persistence.entities.TrayToProductTransfer;
import DataServer.model.persistence.service.AnimalPartService;
import DataServer.model.persistence.service.TrayService;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a gRPC connection data entries into application compatible entities</p> */
@Component
public class GrpcProductData_To_Product
{
  private final AnimalPartService animalPartService;
  private final TrayService trayService;

  @Autowired
  public GrpcProductData_To_Product(AnimalPartService animalPartService, TrayService trayService) {
    this.animalPartService = animalPartService;
    this.trayService = trayService;
  }

  /** <p>Converts gRPC compatible ProductData information into an application compatible Product entity</p> */
  public Product convertToProduct(ProductData productData) throws NotFoundException, DataIntegrityViolationException, PersistenceException {

    if (productData == null)
      return null;

    // Convert the gRPC data fields, excluding any lists of other entities. These are queried from the repository based on the provided ids:
    long id = GrpcId_To_LongId.ConvertToLongId(productData.getProductId());
    List<AnimalPart> animalPartList = new ArrayList<>();
    List<TrayToProductTransfer> transferList = new ArrayList<>();
    List<Tray> trayList = new ArrayList<>();

    // Query database for the referenced AnimalPart entities, for proper Object Relational Model (ORM) behavior:
    for (AnimalPartId animalPartId : productData.getAnimalPartIdsList())
      animalPartList.add(animalPartService.readAnimalPart(GrpcId_To_LongId.ConvertToLongId(animalPartId)));

    // Query database for the referenced Tray and Transfer entities, for proper Object Relational Model (ORM) behavior:
    for (TrayId trayId : productData.getTrayIdsList()){
      Tray tray = trayService.readTray(GrpcId_To_LongId.ConvertToLongId(trayId));
      trayList.add(tray);
      transferList.addAll(tray.getTransferList());
    }

    // Construct new Product entity:
    Product product = new Product(id, animalPartList, trayList);
    product.addAllTransfersToTraySupplyJoinList(transferList);

    return product;
  }

  public List<Product> convertToProductList(ProductsData data) {
    // Return an empty list, if received list is null or empty.
    if(data == null || data.getProductsList().isEmpty())
      return new ArrayList<>();

    // Convert List of ProductsData to a java compatible list by iterating through each entry and running the method previously declared:
    List<Product> productList = new ArrayList<>();
    for (ProductData productData : data.getProductsList())
      productList.add(convertToProduct(productData));

    // return a new List of Product entities:
    return productList;
  }
}
