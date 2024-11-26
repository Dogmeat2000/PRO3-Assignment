package server.controller.grpc.adapters.java_to_gRPC;

import grpc.*;
import server.model.persistence.entities.TrayToProductTransfer;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;

/** <p>Responsible for converting a application entities into a gRPC compatible formats</p> */
public class TrayToProductTransfer_ToGrpc_TrayToProductTransferData
{
  /** <p>Converts a TrayToProductTransfer entity into a gRPC compatible TrayToProductTransfer format</p>
   * @param transfer The TrayToProductTransfer entity to convert
   * @return a gRPC compatible TrayData data type.
   * */
  public TrayToProductTransferData convertToTrayToProductTransferData(TrayToProductTransfer transfer) {

    if (transfer == null)
      return null;

    TrayToProductTransferData.Builder builder = TrayToProductTransferData.newBuilder()
        .setTransferId(LongId_ToGrpc_Id.convertToTrayToProductTransferId(transfer.getTransferId()))
        .setProductId(LongId_ToGrpc_Id.convertToProductId(transfer.getProduct().getProductId()))
        .setTrayId(LongId_ToGrpc_Id.convertToTrayId(transfer.getTray().getTrayId()));

    return builder.build();
  }
}
