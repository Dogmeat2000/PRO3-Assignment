package server.controller.grpc.grpc_to_java;

import grpc.ProductData;
import grpc.ProductsData;
import shared.model.entities.AnimalPart;
import shared.model.entities.Product;
import shared.model.entities.TrayToProductTransfer;

import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcProductData_To_Product
{
    /** Converts database/gRPC compatible ProductData information into an application compatible Product entity */
    public static Product convertToProduct(ProductData productData) {
      if (productData == null)
        return null;

      // Read PartType information from the gRPC data:
      long id = productData.getProductId();
      List<AnimalPart> contentList = productData.getAnimalPartListList().stream().map(server.controller.grpc.grpc_to_java.GrpcAnimalPartData_To_AnimalPart::convertToAnimalPart).toList();
      List<TrayToProductTransfer> trayToProductTransfersList = productData.getTrayToProductTransfersListList().stream().map(server.controller.grpc.grpc_to_java.GrpcTrayToProductTransferData_To_TrayToProductTransfer::convertToTrayToProductTransfer).toList();

      // Construct and return a new PartType entity with the above read attributes set:
      return new Product(id, contentList, trayToProductTransfersList);
    }


    public static List<Product> convertToProductList(ProductsData data) {

      // Construct and return a new List of Product entities:
      return data.getProductsList().stream().map(server.controller.grpc.grpc_to_java.GrpcProductData_To_Product::convertToProduct).toList();
    }
}
