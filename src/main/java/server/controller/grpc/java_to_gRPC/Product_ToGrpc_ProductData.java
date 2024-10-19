package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.Product;
import shared.model.entities.TrayToProductTransfer;

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
    ProductData.Builder builder = ProductData.newBuilder();
    builder.setProductId(product.getProductId());

    if(product.getAnimalPartIdList() != null && !product.getAnimalPartIdList().isEmpty())
      builder.addAllAnimalPartIds(product.getAnimalPartIdList());

    if(product.getTransferIdList() != null && !product.getTransferIdList().isEmpty())
      builder.addAllTransferIds(product.getTransferIdList());

    if(product.getTraySupplyJoinList() != null && !product.getTraySupplyJoinList().isEmpty()) {
      for (TrayToProductTransfer transfer : product.getTraySupplyJoinList())
        builder.addTransfersData(TrayToProductTransfer_ToGrpc_TrayToProductTransferData.convertToTrayToProductTransferData(transfer));
    }

    return builder.build();
  }


  /** <p>Converts a List of Products into the gRPC compatible ProductsData format</p>
   * @param products A list containing all the Product entities to convert.
   * @return A gRPC compatible ProductsData data type, containing all the converted entities.*/
  public static ProductsData convertToProductsDataList(List<Product> products) {
    // Return an empty list, if received list is null or empty.
    if(products == null || products.isEmpty())
      return ProductsData.newBuilder().addAllProducts(new ArrayList<>()).build();

    // Convert List of Products to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<ProductData> productDataList = new ArrayList<>();
    for (Product product : products)
      productDataList.add(convertToProductData(product));

    // Construct and return a new List of ProductData entities:
    return ProductsData.newBuilder().addAllProducts(productDataList).build();
  }
}
