// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SlaughterHouseSim.proto

package grpc;

public interface ProductDataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.ProductData)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string productId = 1;</code>
   */
  java.lang.String getProductId();
  /**
   * <code>string productId = 1;</code>
   */
  com.google.protobuf.ByteString
      getProductIdBytes();

  /**
   * <code>repeated .grpc.TrayData trayIds = 2;</code>
   */
  java.util.List<grpc.TrayData> 
      getTrayIdsList();
  /**
   * <code>repeated .grpc.TrayData trayIds = 2;</code>
   */
  grpc.TrayData getTrayIds(int index);
  /**
   * <code>repeated .grpc.TrayData trayIds = 2;</code>
   */
  int getTrayIdsCount();
  /**
   * <code>repeated .grpc.TrayData trayIds = 2;</code>
   */
  java.util.List<? extends grpc.TrayDataOrBuilder> 
      getTrayIdsOrBuilderList();
  /**
   * <code>repeated .grpc.TrayData trayIds = 2;</code>
   */
  grpc.TrayDataOrBuilder getTrayIdsOrBuilder(
      int index);

  /**
   * <code>repeated .grpc.AnimalPartId animalPartIds = 3;</code>
   */
  java.util.List<grpc.AnimalPartId> 
      getAnimalPartIdsList();
  /**
   * <code>repeated .grpc.AnimalPartId animalPartIds = 3;</code>
   */
  grpc.AnimalPartId getAnimalPartIds(int index);
  /**
   * <code>repeated .grpc.AnimalPartId animalPartIds = 3;</code>
   */
  int getAnimalPartIdsCount();
  /**
   * <code>repeated .grpc.AnimalPartId animalPartIds = 3;</code>
   */
  java.util.List<? extends grpc.AnimalPartIdOrBuilder> 
      getAnimalPartIdsOrBuilderList();
  /**
   * <code>repeated .grpc.AnimalPartId animalPartIds = 3;</code>
   */
  grpc.AnimalPartIdOrBuilder getAnimalPartIdsOrBuilder(
      int index);
}