package server.controller.grpc.java_to_gRPC;

import grpc.AnimalId;
import grpc.PartTypeId;
import grpc.ProductId;
import grpc.TrayId;

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


  /** <p>Converts a long id into a database/gRPC compatible TrayId format</p>
   * @param trayId The id (primary key) for an Tray entity
   * @return a gRPC compatible format of this id */
  public static TrayId convertToTrayId(long trayId) {
    if (trayId == 0)
      return null;

    TrayId.Builder idBuilder = TrayId.newBuilder()
        .setTrayId(trayId);

    return idBuilder.build();
  }


  /** <p>Converts a long id into a database/gRPC compatible ProductId format</p>
   * @param productId The id (primary key) for a Product entity
   * @return a gRPC compatible format of this id */
  public static ProductId convertToProductId(long productId) {
    if (productId == 0)
      return null;

    ProductId.Builder idBuilder = ProductId.newBuilder()
        .setProductId(productId);

    return idBuilder.build();
  }
}
