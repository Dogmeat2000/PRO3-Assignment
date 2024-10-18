// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: SlaughterHouseSim.proto
// Protobuf Java Version: 4.28.2

package grpc;

public interface ProductDataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.ProductData)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 productId = 1;</code>
   * @return The productId.
   */
  long getProductId();

  /**
   * <code>repeated int64 animalPartIds = 2;</code>
   * @return A list containing the animalPartIds.
   */
  java.util.List<java.lang.Long> getAnimalPartIdsList();
  /**
   * <code>repeated int64 animalPartIds = 2;</code>
   * @return The count of animalPartIds.
   */
  int getAnimalPartIdsCount();
  /**
   * <code>repeated int64 animalPartIds = 2;</code>
   * @param index The index of the element to return.
   * @return The animalPartIds at the given index.
   */
  long getAnimalPartIds(int index);

  /**
   * <pre>
   * repeated TrayToProductTransferData trayToProductTransfersList = 2;
   * repeated AnimalPartData animalPartList = 3;
   * </pre>
   *
   * <code>repeated int64 transferIds = 3;</code>
   * @return A list containing the transferIds.
   */
  java.util.List<java.lang.Long> getTransferIdsList();
  /**
   * <pre>
   * repeated TrayToProductTransferData trayToProductTransfersList = 2;
   * repeated AnimalPartData animalPartList = 3;
   * </pre>
   *
   * <code>repeated int64 transferIds = 3;</code>
   * @return The count of transferIds.
   */
  int getTransferIdsCount();
  /**
   * <pre>
   * repeated TrayToProductTransferData trayToProductTransfersList = 2;
   * repeated AnimalPartData animalPartList = 3;
   * </pre>
   *
   * <code>repeated int64 transferIds = 3;</code>
   * @param index The index of the element to return.
   * @return The transferIds at the given index.
   */
  long getTransferIds(int index);
}
