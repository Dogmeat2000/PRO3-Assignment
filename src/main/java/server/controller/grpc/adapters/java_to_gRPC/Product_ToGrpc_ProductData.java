package server.controller.grpc.adapters.java_to_gRPC;

import grpc.*;
import server.model.persistence.entities.Product;
import server.model.persistence.entities.Tray;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a application entities into a gRPC compatible formats</p> */
public class Product_ToGrpc_ProductData
{
  /** <p>Converts a Product entity into a gRPC compatible ProductData format</p>
   * @param product The Product entity to convert
   * @return a gRPC compatible ProductData data type.
   * */
  public ProductData convertToProductData(Product product) {

    if (product == null)
      return null;

    // Convert the java data fields:
    ProductData.Builder builder = ProductData.newBuilder();
    builder.setProductId(LongId_ToGrpc_Id.convertToProductId(product.getProductId()));

    // Convert AnimalPartIds:
    for (long id : product.getAnimalPartIdList())
      builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(id));

    // Convert TransferIds:
    for (long id : product.getTransferIdList())
      builder.addTransferIds(LongId_ToGrpc_Id.convertToTrayToProductTransferId(id));

    // Convert TrayIds:
    for (Tray tray : product.getTraySuppliersList())
      builder.addTrayIds(LongId_ToGrpc_Id.convertToTrayId(tray.getTrayId()));

    return builder.build();
  }

  /** <p>Converts a List of Products into the gRPC compatible ProductsData format</p>
   * @param products A list containing all the Product entities to convert.
   * @return A gRPC compatible ProductsData data type, containing all the converted entities.*/
  public ProductsData convertToProductsDataList(List<Product> products) {
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
