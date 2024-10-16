package server.controller.grpc.grpc_to_java;

import grpc.TrayToProductTransferData;
import grpc.TrayToProductTransfersData;
import shared.model.entities.Product;
import shared.model.entities.Tray;
import shared.model.entities.TrayToProductTransfer;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcTrayToProductTransferData_To_TrayToProductTransfer
{
    /** Converts database/gRPC compatible TrayToProductTransferData information into an application compatible TrayToProductTransfer entity */
    public static TrayToProductTransfer convertToTrayToProductTransfer(TrayToProductTransferData TrayToProductTransferData) {
      if (TrayToProductTransferData == null)
        return null;

      // Read TrayToProductTransfer information from the gRPC data:
      Product product = GrpcProductData_To_Product.convertToProduct(TrayToProductTransferData.getProduct());
      Tray tray = GrpcTrayData_To_Tray.convertToTray(TrayToProductTransferData.getTray());

      // Construct and return a new Tray entity with the above read attributes set:
      return new TrayToProductTransfer(tray, product);
    }


    /*public static List<TrayToProductTransfer> convertToTrayToProductTransferList(TrayToProductTransfersData data) {

      // Construct and return a new List of Animal entities:
      return data.getTransferDataListList().stream().map(server.controller.grpc.grpc_to_java.GrpcTrayToProductTransferData_To_TrayToProductTransfer::convertToTrayToProductTransfer).toList();
    }*/
}
