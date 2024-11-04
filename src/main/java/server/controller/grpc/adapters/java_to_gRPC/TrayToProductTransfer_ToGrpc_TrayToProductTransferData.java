package server.controller.grpc.adapters.java_to_gRPC;

import grpc.*;
import shared.model.entities.TrayToProductTransfer;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class TrayToProductTransfer_ToGrpc_TrayToProductTransferData
{
  /** <p>Converts a TrayToProductTransfer entity into a database/gRPC compatible TrayToProductTransfer format</p>
   * @param transfer The TrayToProductTransfer entity to convert
   * @return a gRPC compatible TrayData data type.
   * */
  public static TrayToProductTransferData convertToTrayToProductTransferData(TrayToProductTransfer transfer, int maxNestingDepth) {

    if (transfer == null)
      return null;

    if(maxNestingDepth < 0)
      return TrayToProductTransferData.newBuilder().build();

    int currentNestingDepth = maxNestingDepth-1;

    return TrayToProductTransferData.newBuilder()
        .setTransferId(transfer.getTransferId())
        .setProduct(Product_ToGrpc_ProductData.convertToProductData(transfer.getProduct(), currentNestingDepth))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(transfer.getTray(), currentNestingDepth))
        .build();
  }
}
