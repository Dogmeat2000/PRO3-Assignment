package server.controller.grpc.grpc_to_java;

import grpc.AnimalId;
import grpc.AnimalPartId;
import grpc.PartTypeId;
import grpc.TrayId;

/** <p>Responsible for converting gRPC compatible id's into application/java compatible id formats</p> */
public class GrpcId_To_LongId
{
  /** <p>Convert a gRPC data type's id into a java compatible id format</p> */
  public static long ConvertToLongId(AnimalId animal_id) {
    return animal_id.getAnimalId();
  }


  /** <p>Convert a gRPC data type's id into a java compatible id format</p> */
  public static long ConvertToLongId(AnimalPartId animalPartid) {
    return animalPartid.getAnimalPartId();
  }


  /** <p>Convert a gRPC data type's id into a java compatible id format</p> */
  public static long ConvertToLongId(PartTypeId partTypeid) {
    return partTypeid.getPartTypeId();
  }


  /** <p>Convert a gRPC data type's id into a java compatible id format</p> */
  public static long ConvertToLongId(TrayId trayId) {
    return trayId.getTrayId();
  }
}
