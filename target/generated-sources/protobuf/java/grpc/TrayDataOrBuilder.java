// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SlaughterHouseSim.proto

package grpc;

public interface TrayDataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.TrayData)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string trayId = 1;</code>
   */
  java.lang.String getTrayId();
  /**
   * <code>string trayId = 1;</code>
   */
  com.google.protobuf.ByteString
      getTrayIdBytes();

  /**
   * <code>repeated .grpc.AnimalPartId animalParts = 2;</code>
   */
  java.util.List<grpc.AnimalPartId> 
      getAnimalPartsList();
  /**
   * <code>repeated .grpc.AnimalPartId animalParts = 2;</code>
   */
  grpc.AnimalPartId getAnimalParts(int index);
  /**
   * <code>repeated .grpc.AnimalPartId animalParts = 2;</code>
   */
  int getAnimalPartsCount();
  /**
   * <code>repeated .grpc.AnimalPartId animalParts = 2;</code>
   */
  java.util.List<? extends grpc.AnimalPartIdOrBuilder> 
      getAnimalPartsOrBuilderList();
  /**
   * <code>repeated .grpc.AnimalPartId animalParts = 2;</code>
   */
  grpc.AnimalPartIdOrBuilder getAnimalPartsOrBuilder(
      int index);
}