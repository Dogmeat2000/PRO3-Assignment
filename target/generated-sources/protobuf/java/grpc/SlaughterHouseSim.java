// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: SlaughterHouseSim.proto
// Protobuf Java Version: 4.28.2

package grpc;

public final class SlaughterHouseSim {
  private SlaughterHouseSim() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      SlaughterHouseSim.class.getName());
  }
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
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_EmptyMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_AnimalData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_AnimalId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalsData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_AnimalsData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalPartData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_AnimalPartData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_UpdatedAnimalPartData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_UpdatedAnimalPartData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalPartId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_AnimalPartId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_AnimalPartsData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_AnimalPartsData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_TrayData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_TrayData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_TrayId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_TrayId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_TraysData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_TraysData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_PartTypeData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_PartTypeData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_PartTypeId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_PartTypeId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_PartTypesData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_PartTypesData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_ProductData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_ProductData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_ProductId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_ProductId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_ProductsData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_ProductsData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_TrayToProductTransferData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_TrayToProductTransferData_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_TrayToProductTransferId_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_TrayToProductTransferId_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_TrayToProductTransfersData_descriptor;
  static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_grpc_TrayToProductTransfersData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\027SlaughterHouseSim.proto\022\004grpc\"\016\n\014Empty" +
      "Message\"b\n\nAnimalData\022\020\n\010animalId\030\001 \001(\003\022" +
      "\024\n\014animalWeight\030\002 \001(\t\022,\n\016animalPartList\030" +
      "\003 \003(\0132\024.grpc.AnimalPartData\"\034\n\010AnimalId\022" +
      "\020\n\010animalId\030\001 \001(\003\"0\n\013AnimalsData\022!\n\007anim" +
      "als\030\001 \003(\0132\020.grpc.AnimalData\"\330\001\n\016AnimalPa" +
      "rtData\022(\n\014animalPartId\030\001 \001(\0132\022.grpc.Anim" +
      "alPartId\022$\n\010partType\030\002 \001(\0132\022.grpc.PartTy" +
      "peData\022\022\n\npartWeight\030\003 \001(\t\022 \n\006animal\030\004 \001" +
      "(\0132\020.grpc.AnimalData\022\034\n\004tray\030\005 \001(\0132\016.grp" +
      "c.TrayData\022\"\n\007product\030\006 \001(\0132\021.grpc.Produ" +
      "ctData\"e\n\025UpdatedAnimalPartData\022%\n\007oldDa" +
      "ta\030\001 \001(\0132\024.grpc.AnimalPartData\022%\n\007newDat" +
      "a\030\002 \001(\0132\024.grpc.AnimalPartData\"\206\001\n\014Animal" +
      "PartId\022\024\n\014animalPartId\030\001 \001(\003\022 \n\010animalId" +
      "\030\002 \001(\0132\016.grpc.AnimalId\022 \n\006typeId\030\003 \001(\0132\020" +
      ".grpc.PartTypeId\022\034\n\006trayId\030\004 \001(\0132\014.grpc." +
      "TrayId\"<\n\017AnimalPartsData\022)\n\013animalParts" +
      "\030\001 \003(\0132\024.grpc.AnimalPartData\"\263\001\n\010TrayDat" +
      "a\022\016\n\006trayId\030\001 \001(\003\022\032\n\022maxWeight_kilogram\030" +
      "\002 \001(\t\022\027\n\017weight_kilogram\030\003 \001(\t\022)\n\013animal" +
      "Parts\030\004 \003(\0132\024.grpc.AnimalPartData\0227\n\016tra" +
      "yToProducts\030\005 \003(\0132\037.grpc.TrayToProductTr" +
      "ansferData\"\030\n\006TrayId\022\016\n\006trayId\030\001 \001(\003\"*\n\t" +
      "TraysData\022\035\n\005trays\030\001 \003(\0132\016.grpc.TrayData" +
      "\"m\n\014PartTypeData\022\022\n\npartTypeId\030\001 \001(\003\022\020\n\010" +
      "partDesc\030\002 \001(\t\0227\n\031animalPartsOfThisTypeL" +
      "ist\030\003 \003(\0132\024.grpc.AnimalPartData\" \n\nPartT" +
      "ypeId\022\022\n\npartTypeId\030\001 \001(\003\"6\n\rPartTypesDa" +
      "ta\022%\n\tpartTypes\030\001 \003(\0132\022.grpc.PartTypeDat" +
      "a\"\223\001\n\013ProductData\022\021\n\tproductId\030\001 \001(\003\022C\n\032" +
      "trayToProductTransfersList\030\002 \003(\0132\037.grpc." +
      "TrayToProductTransferData\022,\n\016animalPartL" +
      "ist\030\003 \003(\0132\024.grpc.AnimalPartData\"\036\n\tProdu" +
      "ctId\022\021\n\tproductId\030\001 \001(\003\"3\n\014ProductsData\022" +
      "#\n\010products\030\001 \003(\0132\021.grpc.ProductData\"]\n\031" +
      "TrayToProductTransferData\022\034\n\004tray\030\001 \001(\0132" +
      "\016.grpc.TrayData\022\"\n\007product\030\002 \001(\0132\021.grpc." +
      "ProductData\"[\n\027TrayToProductTransferId\022\"" +
      "\n\tproductId\030\001 \001(\0132\017.grpc.ProductId\022\034\n\006tr" +
      "ayId\030\002 \001(\0132\014.grpc.TrayId\"W\n\032TrayToProduc" +
      "tTransfersData\0229\n\020transferDataList\030\001 \003(\013" +
      "2\037.grpc.TrayToProductTransferData2\231\002\n\rAn" +
      "imalService\0224\n\016registerAnimal\022\020.grpc.Ani" +
      "malData\032\020.grpc.AnimalData\022.\n\nreadAnimal\022" +
      "\016.grpc.AnimalId\032\020.grpc.AnimalData\0224\n\014upd" +
      "ateAnimal\022\020.grpc.AnimalData\032\022.grpc.Empty" +
      "Message\0224\n\014removeAnimal\022\020.grpc.AnimalDat" +
      "a\032\022.grpc.EmptyMessage\0226\n\rgetAllAnimals\022\022" +
      ".grpc.EmptyMessage\032\021.grpc.AnimalsData2\321\002" +
      "\n\021AnimalPartService\022@\n\022registerAnimalPar" +
      "t\022\024.grpc.AnimalPartData\032\024.grpc.AnimalPar" +
      "tData\022:\n\016readAnimalPart\022\022.grpc.AnimalPar" +
      "tId\032\024.grpc.AnimalPartData\022C\n\020updateAnima" +
      "lPart\022\033.grpc.UpdatedAnimalPartData\032\022.grp" +
      "c.EmptyMessage\022<\n\020removeAnimalPart\022\024.grp" +
      "c.AnimalPartData\032\022.grpc.EmptyMessage\022;\n\016" +
      "getAnimalParts\022\022.grpc.EmptyMessage\032\025.grp" +
      "c.AnimalPartsData2\246\002\n\016ProductService\0227\n\017" +
      "registerProduct\022\021.grpc.ProductData\032\021.grp" +
      "c.ProductData\0221\n\013readProduct\022\017.grpc.Prod" +
      "uctId\032\021.grpc.ProductData\0226\n\rupdateProduc" +
      "t\022\021.grpc.ProductData\032\022.grpc.EmptyMessage" +
      "\0226\n\rremoveProduct\022\021.grpc.ProductData\032\022.g" +
      "rpc.EmptyMessage\0228\n\016getAllProducts\022\022.grp" +
      "c.EmptyMessage\032\022.grpc.ProductsData2\377\001\n\013T" +
      "rayService\022.\n\014registerTray\022\016.grpc.TrayDa" +
      "ta\032\016.grpc.TrayData\022(\n\010readTray\022\014.grpc.Tr" +
      "ayId\032\016.grpc.TrayData\0220\n\nupdateTray\022\016.grp" +
      "c.TrayData\032\022.grpc.EmptyMessage\0220\n\nremove" +
      "Tray\022\016.grpc.TrayData\032\022.grpc.EmptyMessage" +
      "\0222\n\013getAllTrays\022\022.grpc.EmptyMessage\032\017.gr" +
      "pc.TraysData2\263\002\n\017PartTypeService\022:\n\020regi" +
      "sterPartType\022\022.grpc.PartTypeData\032\022.grpc." +
      "PartTypeData\0224\n\014readPartType\022\020.grpc.Part" +
      "TypeId\032\022.grpc.PartTypeData\0228\n\016updatePart" +
      "Type\022\022.grpc.PartTypeData\032\022.grpc.EmptyMes" +
      "sage\0228\n\016removePartType\022\022.grpc.PartTypeDa" +
      "ta\032\022.grpc.EmptyMessage\022:\n\017getAllPartType" +
      "s\022\022.grpc.EmptyMessage\032\023.grpc.PartTypesDa" +
      "taB\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_grpc_EmptyMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_grpc_EmptyMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_EmptyMessage_descriptor,
        new java.lang.String[] { });
    internal_static_grpc_AnimalData_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_grpc_AnimalData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_AnimalData_descriptor,
        new java.lang.String[] { "AnimalId", "AnimalWeight", "AnimalPartList", });
    internal_static_grpc_AnimalId_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_grpc_AnimalId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_AnimalId_descriptor,
        new java.lang.String[] { "AnimalId", });
    internal_static_grpc_AnimalsData_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_grpc_AnimalsData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_AnimalsData_descriptor,
        new java.lang.String[] { "Animals", });
    internal_static_grpc_AnimalPartData_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_grpc_AnimalPartData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_AnimalPartData_descriptor,
        new java.lang.String[] { "AnimalPartId", "PartType", "PartWeight", "Animal", "Tray", "Product", });
    internal_static_grpc_UpdatedAnimalPartData_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_grpc_UpdatedAnimalPartData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_UpdatedAnimalPartData_descriptor,
        new java.lang.String[] { "OldData", "NewData", });
    internal_static_grpc_AnimalPartId_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_grpc_AnimalPartId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_AnimalPartId_descriptor,
        new java.lang.String[] { "AnimalPartId", "AnimalId", "TypeId", "TrayId", });
    internal_static_grpc_AnimalPartsData_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_grpc_AnimalPartsData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_AnimalPartsData_descriptor,
        new java.lang.String[] { "AnimalParts", });
    internal_static_grpc_TrayData_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_grpc_TrayData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_TrayData_descriptor,
        new java.lang.String[] { "TrayId", "MaxWeightKilogram", "WeightKilogram", "AnimalParts", "TrayToProducts", });
    internal_static_grpc_TrayId_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_grpc_TrayId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_TrayId_descriptor,
        new java.lang.String[] { "TrayId", });
    internal_static_grpc_TraysData_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_grpc_TraysData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_TraysData_descriptor,
        new java.lang.String[] { "Trays", });
    internal_static_grpc_PartTypeData_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_grpc_PartTypeData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_PartTypeData_descriptor,
        new java.lang.String[] { "PartTypeId", "PartDesc", "AnimalPartsOfThisTypeList", });
    internal_static_grpc_PartTypeId_descriptor =
      getDescriptor().getMessageTypes().get(12);
    internal_static_grpc_PartTypeId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_PartTypeId_descriptor,
        new java.lang.String[] { "PartTypeId", });
    internal_static_grpc_PartTypesData_descriptor =
      getDescriptor().getMessageTypes().get(13);
    internal_static_grpc_PartTypesData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_PartTypesData_descriptor,
        new java.lang.String[] { "PartTypes", });
    internal_static_grpc_ProductData_descriptor =
      getDescriptor().getMessageTypes().get(14);
    internal_static_grpc_ProductData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_ProductData_descriptor,
        new java.lang.String[] { "ProductId", "TrayToProductTransfersList", "AnimalPartList", });
    internal_static_grpc_ProductId_descriptor =
      getDescriptor().getMessageTypes().get(15);
    internal_static_grpc_ProductId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_ProductId_descriptor,
        new java.lang.String[] { "ProductId", });
    internal_static_grpc_ProductsData_descriptor =
      getDescriptor().getMessageTypes().get(16);
    internal_static_grpc_ProductsData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_ProductsData_descriptor,
        new java.lang.String[] { "Products", });
    internal_static_grpc_TrayToProductTransferData_descriptor =
      getDescriptor().getMessageTypes().get(17);
    internal_static_grpc_TrayToProductTransferData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_TrayToProductTransferData_descriptor,
        new java.lang.String[] { "Tray", "Product", });
    internal_static_grpc_TrayToProductTransferId_descriptor =
      getDescriptor().getMessageTypes().get(18);
    internal_static_grpc_TrayToProductTransferId_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_TrayToProductTransferId_descriptor,
        new java.lang.String[] { "ProductId", "TrayId", });
    internal_static_grpc_TrayToProductTransfersData_descriptor =
      getDescriptor().getMessageTypes().get(19);
    internal_static_grpc_TrayToProductTransfersData_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_grpc_TrayToProductTransfersData_descriptor,
        new java.lang.String[] { "TransferDataList", });
    descriptor.resolveAllFeaturesImmutable();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
