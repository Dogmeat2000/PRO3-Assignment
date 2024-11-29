package Client.common.model.adapters;

import grpc.*;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/** Provides a host of static methods for building gRPC compatible application entities from given method/entity arguments/parameters */
public class GrpcFactory
{
  public static AnimalData buildGrpcAnimalData(long animalId,
      BigDecimal weight,
      String origin,
      Date arrival_date,
      List<AnimalPartDto> animalPartDtoList) {

    AnimalData.Builder builder = AnimalData.newBuilder()
        .setAnimalId(animalId)
        .setAnimalWeight(weight.toString())
        .setOrigin(origin)
        .setArrivalDate(arrival_date.toString());

    for (AnimalPartDto animalDtoPart : animalPartDtoList)
      builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(animalDtoPart.getPartId()));

    return builder.build();
  }

  public static EmptyMessage buildGrpcEmptyMessage () {
    return EmptyMessage.newBuilder().build();
  }

  public static AnimalPartData buildGrpcAnimalPartData (long animalPartId,
      AnimalDto animalDto,
      PartTypeDto typeDto,
      TrayDto trayDto,
      BigDecimal weightInKilogram) {

    return AnimalPartData.newBuilder()
        .setAnimalPartId(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId))
        .setPartWeight(weightInKilogram.toString())
        .setAnimalId(LongId_ToGrpc_Id.convertToAnimalId(animalDto.getAnimalId()))
        .setPartTypeId(LongId_ToGrpc_Id.convertToPartTypeId(typeDto.getTypeId()))
        .setTrayId(LongId_ToGrpc_Id.convertToTrayId(trayDto.getTrayId()))
        .build();
  }

  public static PartTypeData buildGrpcPartTypeData (Long typeId,
      String desc,
      List<AnimalPartDto> animalPartDtoList) {

    PartTypeData.Builder builder = PartTypeData.newBuilder()
        .setPartTypeId(LongId_ToGrpc_Id.convertToPartTypeId(typeId))
        .setPartDesc(desc);

    for (AnimalPartDto animalPartDto : animalPartDtoList)
      builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartDto.getPartId()));

    return builder.build();
  }

  public static TrayData buildGrpcTrayData (long trayId,
      BigDecimal maxWeight_kilogram,
      BigDecimal weight_kilogram,
      List<AnimalPartDto> animalPartDtoList,
      List<ProductDto> productDtoList,
      PartTypeDto trayTypeDto) {

    TrayData.Builder builder = TrayData.newBuilder()
        .setTrayId(LongId_ToGrpc_Id.convertToTrayId(trayId))
        .setMaxWeightKilogram(maxWeight_kilogram.toString())
        .setWeightKilogram(weight_kilogram.toString());

    if(trayTypeDto != null)
      builder.setTrayTypeId(LongId_ToGrpc_Id.convertToPartTypeId(trayTypeDto.getTypeId()));

    for (AnimalPartDto animalPartDto : animalPartDtoList)
      builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartDto.getPartId()));

    for (ProductDto productDto : productDtoList)
      builder.addProductids(LongId_ToGrpc_Id.convertToProductId(productDto.getProductId()));

    return builder.build();
  }

  public static ProductData buildGrpcProductData (List<AnimalPartDto> animalPartDtoList,
      List<TrayDto> trayDtoList){

    ProductData.Builder builder = ProductData.newBuilder();

    for (AnimalPartDto animalPartDto : animalPartDtoList)
      builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartDto.getPartId()));

    for (TrayDto trayDto : trayDtoList)
      builder.addTrayIds(LongId_ToGrpc_Id.convertToTrayId(trayDto.getTrayId()));

    return builder.build();
  }
}
