// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: SlaughterHouseSim.proto
// Protobuf Java Version: 4.28.2

package grpc;

public interface TrayToProductTransferDataOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpc.TrayToProductTransferData)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int64 transferId = 1;</code>
   * @return The transferId.
   */
  long getTransferId();

  /**
   * <code>.grpc.TrayData tray = 2;</code>
   * @return Whether the tray field is set.
   */
  boolean hasTray();
  /**
   * <code>.grpc.TrayData tray = 2;</code>
   * @return The tray.
   */
  grpc.TrayData getTray();
  /**
   * <code>.grpc.TrayData tray = 2;</code>
   */
  grpc.TrayDataOrBuilder getTrayOrBuilder();

  /**
   * <code>.grpc.ProductData product = 3;</code>
   * @return Whether the product field is set.
   */
  boolean hasProduct();
  /**
   * <code>.grpc.ProductData product = 3;</code>
   * @return The product.
   */
  grpc.ProductData getProduct();
  /**
   * <code>.grpc.ProductData product = 3;</code>
   */
  grpc.ProductDataOrBuilder getProductOrBuilder();
}
