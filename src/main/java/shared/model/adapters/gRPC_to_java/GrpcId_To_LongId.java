package shared.model.adapters.gRPC_to_java;

import grpc.*;

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


  /** <p>Convert a gRPC data type's id into a java compatible id format</p> */
  public static long ConvertToLongId(ProductId productId) {
    return productId.getProductId();
  }

  /** <p>Convert a gRPC data type's id into a java compatible id format</p> */
  public static long ConvertToLongId(TrayToProductTransferId transferId) {
    return transferId.getTransferId();
  }
}
