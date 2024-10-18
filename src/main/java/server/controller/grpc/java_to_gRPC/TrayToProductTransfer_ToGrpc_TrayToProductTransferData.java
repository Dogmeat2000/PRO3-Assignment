package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.TrayToProductTransfer;

import java.util.Map;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class TrayToProductTransfer_ToGrpc_TrayToProductTransferData
{
  /** <p>Converts a TrayToProductTransfer entity into a database/gRPC compatible TrayToProductTransfer format</p>
   * @param transfer The TrayToProductTransfer entity to convert
   * @return a gRPC compatible TrayData data type.
   * */
  public static TrayToProductTransferData convertToTrayToProductTransferData(TrayToProductTransfer transfer,
      Map<String, AnimalPartData> animalPartDataCache,
      Map<String, AnimalData> animalDataCache,
      Map<String, PartTypeData> partTypeDataCache,
      Map<String, ProductData> productDataCache,
      Map<String, TrayData> trayDataCache) {

    if (transfer == null)
      return null;

    TrayToProductTransferData.Builder builder = TrayToProductTransferData.newBuilder()
        .setProduct(Product_ToGrpc_ProductData.convertToProductData(transfer.getProduct(), animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache))
        .setTray(Tray_ToGrpc_TrayData.convertToTrayData(transfer.getTray(), animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache));

    return builder.build();
  }
}
