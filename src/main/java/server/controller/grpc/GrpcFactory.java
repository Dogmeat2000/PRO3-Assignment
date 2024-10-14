package server.controller.grpc;

import grpc.*;
import shared.model.entities.Animal;
import shared.model.entities.PartType;
import shared.model.entities.Tray;

import java.math.BigDecimal;

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


  //TODO MISSING IMPLEMENTATION
}
