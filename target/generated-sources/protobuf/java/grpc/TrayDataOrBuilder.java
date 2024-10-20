// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: SlaughterHouseSim.proto
// Protobuf Java Version: 4.28.2

package grpc;

public interface TrayDataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.TrayData)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 trayId = 1;</code>
   * @return The trayId.
   */
  long getTrayId();

  /**
   * <code>string maxWeight_kilogram = 2;</code>
   * @return The maxWeightKilogram.
   */
  java.lang.String getMaxWeightKilogram();
  /**
   * <code>string maxWeight_kilogram = 2;</code>
   * @return The bytes for maxWeightKilogram.
   */
  com.google.protobuf.ByteString
      getMaxWeightKilogramBytes();

  /**
   * <code>string weight_kilogram = 3;</code>
   * @return The weightKilogram.
   */
  java.lang.String getWeightKilogram();
  /**
   * <code>string weight_kilogram = 3;</code>
   * @return The bytes for weightKilogram.
   */
  com.google.protobuf.ByteString
      getWeightKilogramBytes();

  /**
   * <code>repeated int64 animalPartIds = 4;</code>
   * @return A list containing the animalPartIds.
   */
  java.util.List<java.lang.Long> getAnimalPartIdsList();
  /**
   * <code>repeated int64 animalPartIds = 4;</code>
   * @return The count of animalPartIds.
   */
  int getAnimalPartIdsCount();
  /**
   * <code>repeated int64 animalPartIds = 4;</code>
   * @param index The index of the element to return.
   * @return The animalPartIds at the given index.
   */
  long getAnimalPartIds(int index);

  /**
   * <code>repeated int64 transferIds = 5;</code>
   * @return A list containing the transferIds.
   */
  java.util.List<java.lang.Long> getTransferIdsList();
  /**
   * <code>repeated int64 transferIds = 5;</code>
   * @return The count of transferIds.
   */
  int getTransferIdsCount();
  /**
   * <code>repeated int64 transferIds = 5;</code>
   * @param index The index of the element to return.
   * @return The transferIds at the given index.
   */
  long getTransferIds(int index);

  /**
   * <code>repeated .grpc.TrayToProductTransferData transfersData = 6;</code>
   */
  java.util.List<grpc.TrayToProductTransferData> 
      getTransfersDataList();
  /**
   * <code>repeated .grpc.TrayToProductTransferData transfersData = 6;</code>
   */
  grpc.TrayToProductTransferData getTransfersData(int index);
  /**
   * <code>repeated .grpc.TrayToProductTransferData transfersData = 6;</code>
   */
  int getTransfersDataCount();
  /**
   * <code>repeated .grpc.TrayToProductTransferData transfersData = 6;</code>
   */
  java.util.List<? extends grpc.TrayToProductTransferDataOrBuilder> 
      getTransfersDataOrBuilderList();
  /**
   * <code>repeated .grpc.TrayToProductTransferData transfersData = 6;</code>
   */
  grpc.TrayToProductTransferDataOrBuilder getTransfersDataOrBuilder(
      int index);
}
