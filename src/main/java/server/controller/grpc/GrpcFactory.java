package server.controller.grpc;

import grpc.*;
import server.controller.grpc.java_to_gRPC.*;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Provides a host of static methods for building gRPC compatible application entities from given method/entity arguments/parameters */
public class GrpcFactory
{
  public static AnimalData buildGrpcAnimal (BigDecimal weight, List<AnimalPart> animalPartContentList) {

    if(animalPartContentList == null)
      animalPartContentList = new ArrayList<>();

    List<AnimalPartData> animalPartDataList = new ArrayList<>();
    for (AnimalPart animalPart : animalPartContentList)
      animalPartDataList.add(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    return AnimalData.newBuilder()
        .setAnimalWeight(weight.toString())
        .setAnimalId(1)
        .addAllAnimalPartList(animalPartDataList)
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
        .setAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()))
        .setPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(type, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(tray, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()))
        .setPartWeight(weightInKilogram.toString())
        .build();
  }


  public static PartTypeData buildGrpcPartTypeData (String desc, List<AnimalPart> animalPartOfThisTypeList) {

    if(animalPartOfThisTypeList == null)
      animalPartOfThisTypeList = new ArrayList<>();

    List<AnimalPartData> animalPartDataList = new ArrayList<>();
    for (AnimalPart animalPart : animalPartOfThisTypeList)
      animalPartDataList.add(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    return PartTypeData.newBuilder()
        .setPartDesc(desc)
        .addAllAnimalPartsOfThisTypeList(animalPartDataList)
        .build();
  }


  public static TrayData buildGrpcTrayData (BigDecimal maxWeight_kilogram, BigDecimal weight_kilogram, List<AnimalPart> animalPartContentList, List<TrayToProductTransfer> deliveredPartsToProductList) {

    if(animalPartContentList == null)
      animalPartContentList = new ArrayList<>();

    List<AnimalPartData> animalPartDataList = new ArrayList<>();
    for (AnimalPart animalPart : animalPartContentList)
      animalPartDataList.add(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    if(deliveredPartsToProductList == null)
      deliveredPartsToProductList = new ArrayList<>();

    List<TrayToProductTransferData> transferDataList = new ArrayList<>();
    for (TrayToProductTransfer transferData : deliveredPartsToProductList)
      transferDataList.add(TrayToProductTransfer_ToGrpc_TrayToProductTransferData.convertToTrayToProductTransferData(transferData, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    return TrayData.newBuilder()
        .setMaxWeightKilogram(maxWeight_kilogram.toString())
        .setWeightKilogram(weight_kilogram.toString())
        .addAllAnimalParts(animalPartDataList)
        .addAllTrayToProducts(transferDataList)
        .build();
  }


  public static ProductData buildGrpcProductData (long id, List<AnimalPart> animalPartContentList, List<TrayToProductTransfer> receivedPartsFromTrayList){

    if(animalPartContentList == null)
      animalPartContentList = new ArrayList<>();

    List<AnimalPartData> animalPartDataList = new ArrayList<>();
    for (AnimalPart animalPart : animalPartContentList)
      animalPartDataList.add(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    if(receivedPartsFromTrayList == null)
      receivedPartsFromTrayList = new ArrayList<>();

    List<TrayToProductTransferData> transferDataList = new ArrayList<>();
    for (TrayToProductTransfer transferData : receivedPartsFromTrayList)
      transferDataList.add(TrayToProductTransfer_ToGrpc_TrayToProductTransferData.convertToTrayToProductTransferData(transferData, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    return ProductData.newBuilder()
        .addAllAnimalPartList(animalPartDataList)
        .addAllTrayToProductTransfersList(transferDataList)
        .setProductId(id)
        .build();
  }


  public static TrayToProductTransferData buildGrpcTrayToProductTransferData (Product product, Tray tray){
    return TrayToProductTransferData.newBuilder()
        .setProduct(Product_ToGrpc_ProductData.convertToProductData(product, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(tray, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()))
        .build();
  }
}
