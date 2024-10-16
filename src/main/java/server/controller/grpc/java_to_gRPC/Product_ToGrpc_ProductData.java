package server.controller.grpc.java_to_gRPC;

import grpc.ProductData;
import grpc.ProductsData;
import shared.model.entities.Product;

import java.util.List;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class Product_ToGrpc_ProductData
{
  /** <p>Converts a Product entity into a database/gRPC compatible ProductData format</p>
   * @param product The Product entity to convert
   * @return a gRPC compatible ProductData data type.
   * */
  public static ProductData convertToProductData(Product product) {
    if (product == null)
      return null;

    ProductData.Builder builder = ProductData.newBuilder()
        .setProductId(product.getProduct_id())
        .addAllAnimalPartList(product.getContentList().stream().map(AnimalPart_ToGrpc_AnimalPartData::convertToAnimalPartData).toList())
        .addAllTrayToProductTransfersList(product.getTraySupplyJoinList().stream().map(server.controller.grpc.java_to_gRPC.TrayToProductTransfer_ToGrpc_TrayToProductTransferData::convertToTrayToProductTransferData).toList());

    return builder.build();
  }


  /** <p>Converts a List of Products into the gRPC compatible ProductsData format</p>
   * @param products A list containing all the Product entities to convert.
   * @return A gRPC compatible ProductsData data type, containing all the converted entities.*/
  public static ProductsData convertToProductsDataList(List<Product> products) {
    // Convert List of Products to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<ProductData> list = products.stream().map(Product_ToGrpc_ProductData::convertToProductData).toList();

    // Construct and return a new List of AnimalData entities:
    return ProductsData.newBuilder().addAllProducts(list).build();
  }
}
