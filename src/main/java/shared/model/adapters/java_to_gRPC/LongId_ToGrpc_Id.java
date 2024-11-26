package shared.model.adapters.java_to_gRPC;

import grpc.*;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class LongId_ToGrpc_Id
{

  /** <p>Converts a long id into a database/gRPC compatible AnimalId format</p>
   * @param animal_id The id (primary key) for an Animal entity
   * @return a gRPC compatible format of this id
   * @throws NullPointerException If any of the arguments were null (0, or less).*/
  public static AnimalId convertToAnimalId(long animal_id) throws NullPointerException {
    if (animal_id <= 0)
      throw new NullPointerException("An argument was 0. All arguments must be positive integers.");

    AnimalId.Builder idBuilder = AnimalId.newBuilder()
        .setAnimalId(animal_id);
    return idBuilder.build();
  }


  /** <p>Converts a long id into a database/gRPC compatible PartTypeId format</p>
   * @param partTypeId The id (primary key) for an PartType entity
   * @return a gRPC compatible format of this id
   * @throws NullPointerException If any of the arguments were null (0, or less).*/
  public static PartTypeId convertToPartTypeId(long partTypeId) throws NullPointerException {
    if (partTypeId <= 0)
      throw new NullPointerException("An argument was 0. All arguments must be positive integers.");

    PartTypeId.Builder idBuilder = PartTypeId.newBuilder()
        .setPartTypeId(partTypeId);
    return idBuilder.build();
  }


  /** <p>Converts a long id into a database/gRPC compatible TrayId format</p>
   * @param trayId The id (primary key) for a Tray entity
   * @return a gRPC compatible format of this id
   * @throws NullPointerException If any of the arguments were null (0, or less).*/
  public static TrayId convertToTrayId(long trayId) throws NullPointerException {
    if (trayId <= 0)
      throw new NullPointerException("An argument was 0. All arguments must be positive integers.");

    TrayId.Builder idBuilder = TrayId.newBuilder()
        .setTrayId(trayId);
    return idBuilder.build();
  }


  /** <p>Converts a long id into a database/gRPC compatible ProductId format</p>
   * @param productId The id (primary key) for a Product entity
   * @return a gRPC compatible format of this id
   * @throws NullPointerException If any of the arguments were null (0, or less).*/
  public static ProductId convertToProductId(long productId) throws NullPointerException {
    if (productId < 0)
      throw new NullPointerException("An argument was negative. All arguments must be 0 or positive value.");

    ProductId.Builder idBuilder = ProductId.newBuilder()
        .setProductId(productId);
    return idBuilder.build();
  }


  /** <p>Converts a long id into a database/gRPC compatible AnimalPartId format</p>
   * @param animalPartId The id (primary key) for a AnimalPart entity
   * @param animalId the id (primary key) for the Animal entity associated with this AnimalPart
   * @param typeId the id (primary key) for the PartType entity associated with this AnimalPart
   * @param trayId the id (primary key) for the Tray entity associated with this AnimalPart
   * @return a gRPC compatible format of this id
   * @throws NullPointerException If any of the arguments were null (0, or less).*/
  public static AnimalPartId convertToAnimalPartId(long animalPartId) throws NullPointerException {
    if (animalPartId <= 0)
      throw new NullPointerException("Id was 0. Must be positive value.");

    AnimalPartId.Builder idBuilder = AnimalPartId.newBuilder()
        .setAnimalPartId(animalPartId);
    return idBuilder.build();
  }


  /** <p>Converts a long id into a gRPC compatible TransferId format</p>
   * @param transferId The id (primary key) for a TrayToProductTransfer entity
   * @return a gRPC compatible format of this id
   * @throws NullPointerException If any of the arguments were null (0, or less).*/
  public static TrayToProductTransferId convertToTrayToProductTransferId(long transferId) throws NullPointerException {
    if (transferId <= 0)
      throw new NullPointerException("Id was 0. Must be positive value.");

    TrayToProductTransferId.Builder idBuilder = TrayToProductTransferId.newBuilder()
        .setTransferId(transferId);
    return idBuilder.build();
  }
}
