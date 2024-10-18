package server.controller.grpc.grpc_to_java;

import grpc.TrayToProductTransferData;
import shared.model.entities.*;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcTrayToProductTransferData_To_TrayToProductTransfer
{
    /** Converts database/gRPC compatible TrayToProductTransferData information into an application compatible TrayToProductTransfer entity */
    public static TrayToProductTransfer convertToTrayToProductTransfer(TrayToProductTransferData trayToProductTransferData) {

      if (trayToProductTransferData == null)
        return null;

      // Read TrayToProductTransfer information from the gRPC data:
      long id = trayToProductTransferData.getTransferId();
      Product product = GrpcProductData_To_Product.convertToProduct(trayToProductTransferData.getProduct());
      Tray tray = GrpcTrayData_To_Tray.convertToTray(trayToProductTransferData.getTray());

      // Construct and return a new Tray entity with the above read attributes set:
      return new TrayToProductTransfer(id, tray, product);
    }
}
