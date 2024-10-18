package server.controller.grpc.grpc_to_java;

import grpc.TrayToProductTransferData;
import shared.model.entities.*;

import java.util.Map;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcTrayToProductTransferData_To_TrayToProductTransfer
{
    /** Converts database/gRPC compatible TrayToProductTransferData information into an application compatible TrayToProductTransfer entity */
    public static TrayToProductTransfer convertToTrayToProductTransfer(TrayToProductTransferData TrayToProductTransferData,
        Map<String, AnimalPart> animalPartCache,
        Map<String, Animal> animalCache,
        Map<String, PartType> partTypeCache,
        Map<String, Product> productCache,
        Map<String, Tray> trayCache) {

      if (TrayToProductTransferData == null)
        return null;

      // Read TrayToProductTransfer information from the gRPC data:
      Product product = GrpcProductData_To_Product.convertToProduct(TrayToProductTransferData.getProduct(), animalPartCache, animalCache, partTypeCache, productCache, trayCache);
      Tray tray = GrpcTrayData_To_Tray.convertToTray(TrayToProductTransferData.getTray(), animalPartCache, animalCache, partTypeCache, productCache, trayCache);

      // Construct and return a new Tray entity with the above read attributes set:
      return new TrayToProductTransfer(tray, product);
    }
}
