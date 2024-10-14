package server.controller.grpc.java_to_gRPC;

import grpc.TrayToProductTransferData;
import grpc.TrayToProductTransfersData;
import shared.model.entities.TrayToProductTransfer;

import java.util.List;

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
        .setProductId(LongId_ToGrpc_Id.convertToProductId(transfer.getProduct_id()))
        .setTrayId(LongId_ToGrpc_Id.convertToTrayId(transfer.getTray_id()));

    return builder.build();
  }


  /** <p>Converts a List of TrayToProductTransfer into the gRPC compatible TrayToProductTransfersData format</p>
   * @param transfers A list containing all the TrayToProductTransfer entities to convert.
   * @return A gRPC compatible TrayToProductTransfersData data type, containing all the converted entities.*/
  public static TrayToProductTransfersData convertToTrayToProductTransferDataList(List<TrayToProductTransfer> transfers) {
    // Convert List of TrayToProductTransfer to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<TrayToProductTransferData> list = transfers.stream().map(server.controller.grpc.java_to_gRPC.TrayToProductTransfer_ToGrpc_TrayToProductTransferData::convertToTrayToProductTransferData).toList();

    // Construct and return a new List of AnimalData entities:
    return TrayToProductTransfersData.newBuilder().addAllTransferDataList(list).build();
  }
}
