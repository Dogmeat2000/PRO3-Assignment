package server.controller.grpc.grpc_to_java;

import grpc.AnimalId;

/** <p>Responsible for converting gRPC compatible id's into application/java compatible id formats</p> */
public class GrpcId_To_LongId
{
  /** <p>Convert angRPC data type's id into a java compatible id format</p> */
  public static long ConvertToLongId(AnimalId animal_id) {
    return animal_id.getAnimalId();
  }
}
