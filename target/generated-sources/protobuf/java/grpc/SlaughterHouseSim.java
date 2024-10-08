// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SlaughterHouseSim.proto

package grpc;

public final class SlaughterHouseSim {
  private SlaughterHouseSim() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_EmptyMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_EmptyMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_AnimalData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_AnimalId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalsData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_AnimalsData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalPartData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_AnimalPartData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalPartId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_AnimalPartId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalPartsData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_AnimalPartsData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_TrayData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_TrayData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_PartTypeData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_PartTypeData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_PartTypeId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_PartTypeId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_PartTypesData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_PartTypesData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_ProductData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_ProductData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_ProductId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_ProductId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_ProductsData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_ProductsData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\027SlaughterHouseSim.proto\022\004grpc\"\016\n\014Empty" +
      "Message\"4\n\nAnimalData\022\020\n\010animalId\030\001 \001(\003\022" +
      "\024\n\014animalWeight\030\002 \001(\t\"\034\n\010AnimalId\022\020\n\010ani" +
      "malId\030\001 \001(\003\"0\n\013AnimalsData\022!\n\007animals\030\001 " +
      "\003(\0132\020.grpc.AnimalData\"p\n\016AnimalPartData\022" +
      "\024\n\014animalPartId\030\001 \001(\003\022\022\n\npartTypeId\030\002 \001(" +
      "\003\022\022\n\npartWeight\030\003 \001(\t\022\020\n\010animalId\030\004 \001(\003\022" +
      "\016\n\006trayId\030\005 \001(\003\"$\n\014AnimalPartId\022\024\n\014anima" +
      "lPartId\030\001 \001(\003\"<\n\017AnimalPartsData\022)\n\013anim" +
      "alParts\030\001 \003(\0132\024.grpc.AnimalPartData\"C\n\010T" +
      "rayData\022\016\n\006trayId\030\001 \001(\003\022\'\n\013animalParts\030\002" +
      " \003(\0132\022.grpc.AnimalPartId\"4\n\014PartTypeData" +
      "\022\022\n\npartTypeId\030\001 \001(\003\022\020\n\010partDesc\030\002 \001(\t\" " +
      "\n\nPartTypeId\022\022\n\npartTypeId\030\001 \001(\003\"6\n\rPart" +
      "TypesData\022%\n\tpartTypes\030\001 \003(\0132\022.grpc.Part" +
      "TypeData\"l\n\013ProductData\022\021\n\tproductId\030\001 \001" +
      "(\003\022\037\n\007trayIds\030\002 \003(\0132\016.grpc.TrayData\022)\n\ra" +
      "nimalPartIds\030\003 \003(\0132\022.grpc.AnimalPartId\"\036" +
      "\n\tProductId\022\021\n\tproductId\030\001 \001(\003\"3\n\014Produc" +
      "tsData\022#\n\010products\030\001 \003(\0132\021.grpc.ProductD" +
      "ata2\220\t\n\025SlaughterHouseService\0224\n\016registe" +
      "rAnimal\022\020.grpc.AnimalData\032\020.grpc.AnimalD" +
      "ata\022@\n\022registerAnimalPart\022\024.grpc.AnimalP" +
      "artData\032\024.grpc.AnimalPartData\0227\n\017registe" +
      "rProduct\022\021.grpc.ProductData\032\021.grpc.Produ" +
      "ctData\022:\n\020registerPartType\022\022.grpc.PartTy" +
      "peData\032\022.grpc.PartTypeData\022.\n\nreadAnimal" +
      "\022\016.grpc.AnimalId\032\020.grpc.AnimalData\022:\n\016re" +
      "adAnimalPart\022\022.grpc.AnimalPartId\032\024.grpc." +
      "AnimalPartData\0221\n\013readProduct\022\017.grpc.Pro" +
      "ductId\032\021.grpc.ProductData\0224\n\014readPartTyp" +
      "e\022\020.grpc.PartTypeId\032\022.grpc.PartTypeData\022" +
      "4\n\014updateAnimal\022\020.grpc.AnimalData\032\022.grpc" +
      ".EmptyMessage\022<\n\020updateAnimalPart\022\024.grpc" +
      ".AnimalPartData\032\022.grpc.EmptyMessage\0226\n\ru" +
      "pdateProduct\022\021.grpc.ProductData\032\022.grpc.E" +
      "mptyMessage\0228\n\016updatePartType\022\022.grpc.Par" +
      "tTypeData\032\022.grpc.EmptyMessage\0224\n\014removeA" +
      "nimal\022\020.grpc.AnimalData\032\022.grpc.EmptyMess" +
      "age\022<\n\020removeAnimalPart\022\024.grpc.AnimalPar" +
      "tData\032\022.grpc.EmptyMessage\0226\n\rremoveProdu" +
      "ct\022\021.grpc.ProductData\032\022.grpc.EmptyMessag" +
      "e\0228\n\016removePartType\022\022.grpc.PartTypeData\032" +
      "\022.grpc.EmptyMessage\0226\n\rgetAllAnimals\022\022.g" +
      "rpc.EmptyMessage\032\021.grpc.AnimalsData\022;\n\016g" +
      "etAnimalParts\022\022.grpc.EmptyMessage\032\025.grpc" +
      ".AnimalPartsData\0228\n\016getAllProducts\022\022.grp" +
      "c.EmptyMessage\032\022.grpc.ProductsData\022:\n\017ge" +
      "tAllPartTypes\022\022.grpc.EmptyMessage\032\023.grpc" +
      ".PartTypesDataB\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_grpc_EmptyMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_grpc_EmptyMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_EmptyMessage_descriptor,
        new java.lang.String[] { });
    internal_static_grpc_AnimalData_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_grpc_AnimalData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_AnimalData_descriptor,
        new java.lang.String[] { "AnimalId", "AnimalWeight", });
    internal_static_grpc_AnimalId_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_grpc_AnimalId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_AnimalId_descriptor,
        new java.lang.String[] { "AnimalId", });
    internal_static_grpc_AnimalsData_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_grpc_AnimalsData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_AnimalsData_descriptor,
        new java.lang.String[] { "Animals", });
    internal_static_grpc_AnimalPartData_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_grpc_AnimalPartData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_AnimalPartData_descriptor,
        new java.lang.String[] { "AnimalPartId", "PartTypeId", "PartWeight", "AnimalId", "TrayId", });
    internal_static_grpc_AnimalPartId_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_grpc_AnimalPartId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_AnimalPartId_descriptor,
        new java.lang.String[] { "AnimalPartId", });
    internal_static_grpc_AnimalPartsData_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_grpc_AnimalPartsData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_AnimalPartsData_descriptor,
        new java.lang.String[] { "AnimalParts", });
    internal_static_grpc_TrayData_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_grpc_TrayData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_TrayData_descriptor,
        new java.lang.String[] { "TrayId", "AnimalParts", });
    internal_static_grpc_PartTypeData_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_grpc_PartTypeData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_PartTypeData_descriptor,
        new java.lang.String[] { "PartTypeId", "PartDesc", });
    internal_static_grpc_PartTypeId_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_grpc_PartTypeId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_PartTypeId_descriptor,
        new java.lang.String[] { "PartTypeId", });
    internal_static_grpc_PartTypesData_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_grpc_PartTypesData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_PartTypesData_descriptor,
        new java.lang.String[] { "PartTypes", });
    internal_static_grpc_ProductData_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_grpc_ProductData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_ProductData_descriptor,
        new java.lang.String[] { "ProductId", "TrayIds", "AnimalPartIds", });
    internal_static_grpc_ProductId_descriptor =
      getDescriptor().getMessageTypes().get(12);
    internal_static_grpc_ProductId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_ProductId_descriptor,
        new java.lang.String[] { "ProductId", });
    internal_static_grpc_ProductsData_descriptor =
      getDescriptor().getMessageTypes().get(13);
    internal_static_grpc_ProductsData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_ProductsData_descriptor,
        new java.lang.String[] { "Products", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
