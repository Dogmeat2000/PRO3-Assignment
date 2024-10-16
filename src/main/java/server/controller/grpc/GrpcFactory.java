package server.controller.grpc;

import grpc.*;
import server.controller.grpc.java_to_gRPC.*;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.util.List;

/** Provides a host of static methods for building gRPC compatible application entities from given method/entity arguments/parameters */
public class GrpcFactory
{
  public static AnimalData buildGrpcAnimal (BigDecimal weight, List<AnimalPart> animalPartContentList) {
    return AnimalData.newBuilder()
        .setAnimalWeight(weight.toString())
        .setAnimalId(1)
        .addAllAnimalPartList(animalPartContentList.stream().map(server.controller.grpc.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData::convertToAnimalPartData).toList())
        .build();
  }


  public static EmptyMessage buildGrpcEmptyMessage () {
    return EmptyMessage.newBuilder().build();
  }


  public static AnimalPartData buildGrpcAnimalPartData (long animalPartId, Animal animal, PartType type, Tray tray, BigDecimal weightInKilogram) {
    return AnimalPartData.newBuilder()
        .setAnimalPartId(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId, animal.getId(), type.getTypeId(), tray.getTray_id()))
        .setAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal))
        .setPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(type))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(tray))
        .setPartWeight(weightInKilogram.toString())
        .build();
  }


  public static PartTypeData buildGrpcPartTypeData (String desc, List<AnimalPart> animalPartOfThisTypeList) {
    return PartTypeData.newBuilder()
        .setPartDesc(desc)
        .addAllAnimalPartsOfThisTypeList(animalPartOfThisTypeList.stream().map(server.controller.grpc.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData::convertToAnimalPartData).toList())
        .build();
  }


  public static TrayData buildGrpcTrayData (BigDecimal maxWeight_kilogram, BigDecimal weight_kilogram, List<AnimalPart> animalPartContentList, List<TrayToProductTransfer> deliveredPartsToProductList) {
    return TrayData.newBuilder()
        .setMaxWeightKilogram(maxWeight_kilogram.toString())
        .setWeightKilogram(weight_kilogram.toString())
        .addAllAnimalParts(animalPartContentList.stream().map(server.controller.grpc.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData::convertToAnimalPartData).toList())
        .addAllTrayToProducts(deliveredPartsToProductList.stream().map(server.controller.grpc.java_to_gRPC.TrayToProductTransfer_ToGrpc_TrayToProductTransferData::convertToTrayToProductTransferData).toList())
        .build();
  }


  public static ProductData buildGrpcProductData (long id, List<AnimalPart> animalPartContentList, List<TrayToProductTransfer> receivedPartsFromTrayList){
    return ProductData.newBuilder()
        .addAllAnimalPartList(animalPartContentList.stream().map(server.controller.grpc.java_to_gRPC.AnimalPart_ToGrpc_AnimalPartData::convertToAnimalPartData).toList())
        .addAllTrayToProductTransfersList(receivedPartsFromTrayList.stream().map(server.controller.grpc.java_to_gRPC.TrayToProductTransfer_ToGrpc_TrayToProductTransferData::convertToTrayToProductTransferData).toList())
        .setProductId(id)
        .build();
  }


  public static TrayToProductTransferData buildGrpcTrayToProductTransferData (Product product, Tray tray){
    return TrayToProductTransferData.newBuilder()
        .setProduct(Product_ToGrpc_ProductData.convertToProductData(product))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(tray))
        .build();
  }
}
