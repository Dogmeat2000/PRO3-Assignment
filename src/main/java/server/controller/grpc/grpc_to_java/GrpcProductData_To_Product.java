package server.controller.grpc.grpc_to_java;

import grpc.*;
import shared.model.entities.*;

import java.util.ArrayList;
import java.util.List;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcProductData_To_Product
{
    /** Converts database/gRPC compatible ProductData information into an application compatible Product entity */
    public static Product convertToProduct(ProductData productData) {

      if (productData == null)
        return null;

      // Convert the gRPC data fields, excluding any lists of other entities. These need to be queried separately by the calling gRPC service layer:
      long id = productData.getProductId();
      List<Long> animalPartIdList = new ArrayList<>(productData.getAnimalPartIdsList());
      List<Long> transferIdList = new ArrayList<>(productData.getTransferIdsList());

      // Construct new Product entity:
      Product product = new Product(id, animalPartIdList, transferIdList);

      // Add remaining values:
      for (TrayToProductTransferData transferData : productData.getTransfersDataList())
        product.getTraySupplyJoinList().add(GrpcTrayToProductTransferData_To_TrayToProductTransfer.convertToTrayToProductTransfer(transferData));

      return product;
    }


    public static List<Product> convertToProductList(ProductsData data) {
      // Return an empty list, if received list is null or empty.
      if(data == null || data.getProductsList().isEmpty())
        return new ArrayList<>();

      // Convert List of ProductsData to a java compatible list by iteration through each entry and running the method previously declared:
      List<Product> productList = new ArrayList<>();
      for (ProductData productData : data.getProductsList())
        productList.add(convertToProduct(productData));

      // return a new List of Product entities:
      return productList;
    }
}
