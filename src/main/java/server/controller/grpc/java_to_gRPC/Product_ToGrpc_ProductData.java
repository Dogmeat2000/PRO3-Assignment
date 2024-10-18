package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.Product;

import java.util.ArrayList;
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

    // Convert the java data fields, excluding any lists of other entities. These need to be queried separately by the receiving service layer:
    return ProductData.newBuilder()
        .setProductId(product.getProductId())
        .addAllAnimalPartIds(product.getAnimalPartIdList())
        .addAllTransferIds(product.getTransferIdList())
        .build();
  }


  /** <p>Converts a List of Products into the gRPC compatible ProductsData format</p>
   * @param products A list containing all the Product entities to convert.
   * @return A gRPC compatible ProductsData data type, containing all the converted entities.*/
  public static ProductsData convertToProductsDataList(List<Product> products) {
    List<ProductData> productDataList = new ArrayList<>();

    // Convert List of Products to a gRPC compatible list by iteration through each entry and running the method previously declared:
    for (Product product : products)
      productDataList.add(convertToProductData(product));

    // Construct and return a new List of ProductData entities:
    return ProductsData.newBuilder().addAllProducts(productDataList).build();
  }
}
