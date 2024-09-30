package server.controller.grpc;

import grpc.AnimalId;
import shared.model.entities.Animal;

import java.math.BigDecimal;

/** Responsible for converting a application entities into a database/gRPC compatible formats */
public class LongId_ToAnimalGrpc_AnimalId
{

  /** Converts a Animal entity into a database/gRPC compatible AnimalData format */
  public static AnimalId ConvertToAnimalId(long animal_id) {
    if (animal_id == 0)
      return null;

    AnimalId.Builder idBuilder = AnimalId.newBuilder()
        .setAnimalId(animal_id);

    return idBuilder.build();
  }
}
