package server.controller.grpc.java_to_gRPC;

import grpc.AnimalId;
import grpc.PartTypeId;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class LongId_ToGrpc_Id
{

  /** <p>Converts a long id into a database/gRPC compatible AnimalId format</p>
   * @param animal_id The id (primary key) for an Animal entity
   * @return a gRPC compatible format of this id */
  public static AnimalId convertToAnimalId(long animal_id) {
    if (animal_id == 0)
      return null;

    AnimalId.Builder idBuilder = AnimalId.newBuilder()
        .setAnimalId(animal_id);

    return idBuilder.build();
  }


  /** <p>Converts a long id into a database/gRPC compatible PartTypeId format</p>
   * @param partTypeId The id (primary key) for an PartType entity
   * @return a gRPC compatible format of this id */
  public static PartTypeId convertToPartTypeId(long partTypeId) {
    if (partTypeId == 0)
      return null;

    PartTypeId.Builder idBuilder = PartTypeId.newBuilder()
        .setPartTypeId(partTypeId);

    return idBuilder.build();
  }
}
