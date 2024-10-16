package server.controller.grpc.java_to_gRPC;

import grpc.TrayToProductTransferData;
import shared.model.entities.TrayToProductTransfer;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class TrayToProductTransfer_ToGrpc_TrayToProductTransferData
{
  /** <p>Converts a TrayToProductTransfer entity into a database/gRPC compatible TrayToProductTransfer format</p>
   * @param transfer The TrayToProductTransfer entity to convert
   * @return a gRPC compatible TrayData data type.
   * */
  public static TrayToProductTransferData convertToTrayToProductTransferData(TrayToProductTransfer transfer) {
    if (transfer == null)
      return null;

    TrayToProductTransferData.Builder builder = TrayToProductTransferData.newBuilder()
        .setProduct(Product_ToGrpc_ProductData.convertToProductData(transfer.getProduct()))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(transfer.getTray()));

    return builder.build();
  }
}
