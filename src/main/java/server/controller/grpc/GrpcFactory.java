package server.controller.grpc;

import grpc.*;
import server.controller.grpc.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData;
import server.controller.grpc.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.util.List;

/** Provides a host of static methods for building gRPC compatible application entities from given method/entity arguments/parameters */
public class GrpcFactory
{
  public static AnimalData buildGrpcAnimal (BigDecimal weight) {
    return AnimalData.newBuilder()
        .setAnimalWeight(weight.toString())
        .setAnimalId(0)
        .build();
  }


  public static EmptyMessage buildGrpcEmptyMessage () {
    return EmptyMessage.newBuilder().build();
  }


  public static AnimalPartData buildGrpcAnimalPartData (Animal animal, PartType type, Tray tray, BigDecimal weightInKilogram) {
    return AnimalPartData.newBuilder()
        .setAnimalId(animal.getId())
        .setPartTypeId(type.getTypeId())
        .setTrayId(tray.getTray_id())
        .setPartWeight(weightInKilogram.toString())
        .build();
  }


  public static PartTypeData buildGrpcPartTypeData (String desc) {
    return PartTypeData.newBuilder()
        .setPartDesc(desc)
        .build();
  }


  public static TrayData buildGrpcTrayData (BigDecimal maxWeight_kilogram, BigDecimal weight_kilogram) {
    return TrayData.newBuilder()
        .setMaxWeightKilogram(maxWeight_kilogram.toString())
        .setWeightKilogram(weight_kilogram.toString())
        .build();
  }


  public static ProductData buildGrpcProductData (long id, List<AnimalPart> animalPartContentList, List<TrayToProductTransfer> receivedPartsFromTrayList){
    return ProductData.newBuilder()
        .addAllAnimalPartList(animalPartContentList.stream().map(server.controller.grpc.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData::convertToAnimalPartData).toList())
        .addAllTrayToProductTransfersList(receivedPartsFromTrayList.stream().map(server.controller.grpc.java_to_gRPC.TrayToProductTransfer_ToGrpc_TrayToProductTransferData::convertToTrayToProductTransferData).toList())
        .setProductId(id)
        .build();
  }


  public static TrayToProductTransferData buildGrpcTrayToProductTransferData (long productId, long trayId){
    return TrayToProductTransferData.newBuilder()
        .setProductId(LongId_ToGrpc_Id.convertToProductId(productId))
        .setTrayId(LongId_ToGrpc_Id.convertToTrayId(trayId))
        .build();
  }


  //TODO MISSING IMPLEMENTATION
}
