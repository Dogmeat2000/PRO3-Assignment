package server.controller.grpc;

import grpc.AnimalData;
import grpc.AnimalId;
import grpc.EmptyMessage;

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

  public static AnimalId buildGrpcAnimalId (long id) {
    return AnimalId.newBuilder()
        .setAnimalId(id)
        .build();
  }

  public static EmptyMessage buildGrpcEmptyMessage () {
    return EmptyMessage.newBuilder().build();
  }

  //TODO MISSING IMPLEMENTATION
}
