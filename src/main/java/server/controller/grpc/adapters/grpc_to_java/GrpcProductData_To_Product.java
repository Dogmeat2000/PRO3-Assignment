package server.controller.grpc.adapters.grpc_to_java;

import grpc.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shared.model.entities.*;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.ArrayList;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
@Component
@Scope("singleton")
public class GrpcProductData_To_Product
{
  private GrpcAnimalPartData_To_AnimalPart animalPartConverter = null;
  private GrpcTrayToProductTransferData_To_TrayToProductTransfer trayToProductTransfer = null;
  private GrpcTrayData_To_Tray trayConverter = null;


  /** Converts database/gRPC compatible ProductData information into an application compatible Product entity */
  public Product convertToProduct(ProductData productData, int maxNestingDepth) {

    if (productData == null || maxNestingDepth < 0)
      return null;

    int currentNestingDepth = maxNestingDepth-1;

    // Lazy instantiate the required converters as needed:
    if(animalPartConverter == null)
      animalPartConverter = new GrpcAnimalPartData_To_AnimalPart();
    if(trayToProductTransfer == null)
      trayToProductTransfer = new GrpcTrayToProductTransferData_To_TrayToProductTransfer();
    if(trayConverter == null)
      trayConverter = new GrpcTrayData_To_Tray();

    // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
    long id = productData.getProductId();
    List<Long> animalPartIdList = new ArrayList<>(productData.getAnimalPartIdsList());
    List<Long> transferIdList = new ArrayList<>(productData.getTransferIdsList());

    // Construct new Product entity:
    Product product = new Product(id, animalPartIdList, transferIdList);

    // Add remaining values:
    for (TrayToProductTransferData transferData : productData.getTransfersDataList())
      product.getTraySupplyJoinList().add(trayToProductTransfer.convertToTrayToProductTransfer(transferData, currentNestingDepth));

    // Convert the attached AnimalParts, for proper Object Relational Model (ORM) behavior:
    try {
      for (AnimalPartData animalPartData : productData.getAnimalPartListList()) {
        product.addAnimalPart(animalPartConverter.convertToAnimalPart(animalPartData, currentNestingDepth));
      }
    } catch (NotFoundException e) {
      product.setAnimalParts(new ArrayList<>());
    }

    // Convert the attached Trays, for proper Object Relational Model (ORM) behavior:
    try {
      for (TrayData trayData : productData.getTrayDataListList()) {
        product.getTraySuppliersList().add((trayConverter.convertToTray(trayData, currentNestingDepth)));
      }
    } catch (NotFoundException e) {
      product.setTraySuppliersList(new ArrayList<>());
    }

    return product;
  }


  public List<Product> convertToProductList(ProductsData data, int maxNestingDepth) {
    // Return an empty list, if received list is null or empty.
    if(data == null || data.getProductsList().isEmpty())
      return new ArrayList<>();

    // Convert List of ProductsData to a java compatible list by iterating through each entry and running the method previously declared:
    List<Product> productList = new ArrayList<>();
    for (ProductData productData : data.getProductsList())
      productList.add(convertToProduct(productData, maxNestingDepth));

    // return a new List of Product entities:
    return productList;
  }
}
