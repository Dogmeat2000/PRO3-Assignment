package server.controller.grpc.adapters.grpc_to_java;

import grpc.TrayToProductTransferData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shared.model.entities.*;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
@Component
@Scope("singleton")
public class GrpcTrayToProductTransferData_To_TrayToProductTransfer
{
  private GrpcTrayData_To_Tray trayConverter = null;
  private GrpcProductData_To_Product productConverter = null;

    /** Converts database/gRPC compatible TrayToProductTransferData information into an application compatible TrayToProductTransfer entity */
    public TrayToProductTransfer convertToTrayToProductTransfer(TrayToProductTransferData trayToProductTransferData, int maxNestingDepth) {

      if (trayToProductTransferData == null || maxNestingDepth < 0)
        return null;

      int currentNestingDepth = maxNestingDepth-1;

      // Lazy instantiate the required converters as needed:
      if(trayConverter == null)
        trayConverter = new GrpcTrayData_To_Tray();
      if(productConverter == null)
        productConverter = new GrpcProductData_To_Product();

      // Read TrayToProductTransfer information from the gRPC data:
      long id = trayToProductTransferData.getTransferId();
      Product product = productConverter.convertToProduct(trayToProductTransferData.getProduct(), currentNestingDepth);
      Tray tray = trayConverter.convertToTray(trayToProductTransferData.getTray(), currentNestingDepth);

      // Construct and return a new Tray entity with the above read attributes set:
      return new TrayToProductTransfer(id, tray, product);
    }
}
