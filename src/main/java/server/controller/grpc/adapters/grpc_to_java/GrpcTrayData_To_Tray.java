package server.controller.grpc.adapters.grpc_to_java;

import grpc.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shared.model.entities.*;
import shared.model.exceptions.persistance.NotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
@Component
@Scope("singleton")
public class GrpcTrayData_To_Tray
{
  private GrpcAnimalPartData_To_AnimalPart animalPartConverter = null;
  private GrpcPartTypeData_To_PartType trayTypeConverter = null;
  private GrpcTrayToProductTransferData_To_TrayToProductTransfer trayToProductTransfer = null;
  private GrpcProductData_To_Product productConverter = null;

  /** Converts database/gRPC compatible TrayData information into an application compatible Tray entity */
  public Tray convertToTray(TrayData trayData, int maxNestingDepth) {

    if (trayData == null || maxNestingDepth < 0)
      return null;

    int currentNestingDepth = maxNestingDepth-1;

    // Lazy instantiate the required converters as needed:
    if(animalPartConverter == null)
      animalPartConverter = new GrpcAnimalPartData_To_AnimalPart();
    if(trayToProductTransfer == null)
      trayToProductTransfer = new GrpcTrayToProductTransferData_To_TrayToProductTransfer();
    if(trayTypeConverter == null)
      trayTypeConverter = new GrpcPartTypeData_To_PartType();
    if(productConverter == null)
      productConverter = new GrpcProductData_To_Product();

    // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
    long id = trayData.getTrayId();
    BigDecimal maxWeight_kilogram = trayData.getMaxWeightKilogram().isEmpty() ? BigDecimal.ZERO : new BigDecimal(trayData.getMaxWeightKilogram());
    BigDecimal weight_kilogram = trayData.getWeightKilogram().isEmpty() ? BigDecimal.ZERO : new BigDecimal(trayData.getWeightKilogram());
    List<Long> animalPartIdList = new ArrayList<>(trayData.getAnimalPartIdsList());
    List<Long> transferIdList = new ArrayList<>(trayData.getTransferIdsList());
    PartType trayType = trayTypeConverter.convertToPartType(trayData.getTrayType(), currentNestingDepth);

    // Construct new Tray entity:
    Tray tray = new Tray(id, maxWeight_kilogram, weight_kilogram, animalPartIdList, transferIdList);
    tray.setTrayType(trayType);

    // Add remaining values:
    for (TrayToProductTransferData transferData : trayData.getTransfersDataList())
      tray.getTransferList().add(trayToProductTransfer.convertToTrayToProductTransfer(transferData, currentNestingDepth));

    // Convert the attached AnimalParts, for proper Object Relational Model (ORM) behavior:
    try {
      for (AnimalPartData animalPartData : trayData.getAnimalPartListList()) {
        tray.addAnimalPart(animalPartConverter.convertToAnimalPart(animalPartData, currentNestingDepth));
      }
    } catch (NotFoundException e) {
      tray.clearAnimalPartContents();
    }

    // Convert the attached Products, for proper Object Relational Model (ORM) behavior:
    try {
      for (ProductData productData : trayData.getProductListList()) {
        tray.getProductList().add(productConverter.convertToProduct(productData, currentNestingDepth));
      }
    } catch (NotFoundException e) {
      tray.setProductList(new ArrayList<>());
    }

    return tray;
  }


  public List<Tray> convertToTrayList(TraysData data, int maxNestingDepth) {
    // Return an empty list, if received list is null or empty.
    if(data == null || data.getTraysList().isEmpty())
      return new ArrayList<>();

    // Convert List of TraysData to a java compatible list by iteration through each entry and running the method previously declared:
    List<Tray> trayList = new ArrayList<>();
    for (TrayData trayData : data.getTraysList())
      trayList.add(convertToTray(trayData, maxNestingDepth));

    // return a new List of Tray entities:
    return trayList;
  }
}
