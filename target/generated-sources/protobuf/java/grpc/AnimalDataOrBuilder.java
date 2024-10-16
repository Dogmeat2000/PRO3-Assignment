// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: SlaughterHouseSim.proto
// Protobuf Java Version: 4.28.2

package grpc;

public interface AnimalDataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.AnimalData)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 animalId = 1;</code>
   * @return The animalId.
   */
  long getAnimalId();

  /**
   * <code>string animalWeight = 2;</code>
   * @return The animalWeight.
   */
  java.lang.String getAnimalWeight();
  /**
   * <code>string animalWeight = 2;</code>
   * @return The bytes for animalWeight.
   */
  com.google.protobuf.ByteString
      getAnimalWeightBytes();

  /**
   * <code>repeated .grpc.AnimalPartData animalPartList = 3;</code>
   */
  java.util.List<grpc.AnimalPartData> 
      getAnimalPartListList();
  /**
   * <code>repeated .grpc.AnimalPartData animalPartList = 3;</code>
   */
  grpc.AnimalPartData getAnimalPartList(int index);
  /**
   * <code>repeated .grpc.AnimalPartData animalPartList = 3;</code>
   */
  int getAnimalPartListCount();
  /**
   * <code>repeated .grpc.AnimalPartData animalPartList = 3;</code>
   */
  java.util.List<? extends grpc.AnimalPartDataOrBuilder> 
      getAnimalPartListOrBuilderList();
  /**
   * <code>repeated .grpc.AnimalPartData animalPartList = 3;</code>
   */
  grpc.AnimalPartDataOrBuilder getAnimalPartListOrBuilder(
      int index);
}
