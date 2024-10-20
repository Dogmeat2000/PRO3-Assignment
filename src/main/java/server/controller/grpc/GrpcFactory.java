package server.controller.grpc;

import grpc.*;
import server.controller.grpc.java_to_gRPC.*;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.util.List;

/** Provides a host of static methods for building gRPC compatible application entities from given method/entity arguments/parameters */
public class GrpcFactory
{
  public static AnimalData buildGrpcAnimal (BigDecimal weight, List<Long> animalPartIdList) {
    return AnimalData.newBuilder()
        .setAnimalId(1)
        .setAnimalWeight(weight.toString())
        .addAllAnimalPartIds(animalPartIdList)
        .build();
  }


  public static EmptyMessage buildGrpcEmptyMessage () {
    return EmptyMessage.newBuilder().build();
  }


  public static AnimalPartData buildGrpcAnimalPartData (long animalPartId,
      Animal animal,
      PartType type,
      Tray tray,
      BigDecimal weightInKilogram) {

    return AnimalPartData.newBuilder()
        .setAnimalPartId(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId))
        .setAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal))
        .setPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(type))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(tray, 3))
        .setPartWeight(weightInKilogram.toString())
        .build();
  }


  public static PartTypeData buildGrpcPartTypeData (String desc, List<Long> animalPartIdList) {
    return PartTypeData.newBuilder()
        .setPartDesc(desc)
        .addAllAnimalPartIds(animalPartIdList)
        .build();
  }


  public static TrayData buildGrpcTrayData (BigDecimal maxWeight_kilogram, BigDecimal weight_kilogram, List<Long> animalPartIdList, List<Long> transferIdList) {
    return TrayData.newBuilder()
        .setMaxWeightKilogram(maxWeight_kilogram.toString())
        .setWeightKilogram(weight_kilogram.toString())
        .addAllAnimalPartIds(animalPartIdList)
        .addAllTransferIds(transferIdList)
        .build();
  }


  public static ProductData buildGrpcProductData (List<Long> animalPartIdList, List<Long> transferIdList){
    return ProductData.newBuilder()
        .setProductId(1)
        .addAllAnimalPartIds(animalPartIdList)
        .addAllTransferIds(transferIdList)
        .build();
  }


  public static TrayToProductTransferData buildGrpcTrayToProductTransferData (Product product, Tray tray){
    return TrayToProductTransferData.newBuilder()
        .setTransferId(1)
        .setProduct(Product_ToGrpc_ProductData.convertToProductData(product, 3))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(tray,3))
        .build();
  }
}
