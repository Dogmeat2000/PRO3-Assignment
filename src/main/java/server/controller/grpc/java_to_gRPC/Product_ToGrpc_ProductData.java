package server.controller.grpc.java_to_gRPC;

import grpc.*;
import shared.model.entities.AnimalPart;
import shared.model.entities.Product;
import shared.model.entities.TrayToProductTransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** <p>Responsible for converting a application entities into a database/gRPC compatible formats</p> */
public class Product_ToGrpc_ProductData
{
  /** <p>Converts a Product entity into a database/gRPC compatible ProductData format</p>
   * @param product The Product entity to convert
   * @return a gRPC compatible ProductData data type.
   * */
  public static ProductData convertToProductData(Product product,
      Map<String, AnimalPartData> animalPartDataCache,
      Map<String, AnimalData> animalDataCache,
      Map<String, PartTypeData> partTypeDataCache,
      Map<String, ProductData> productDataCache,
      Map<String, TrayData> trayDataCache) {

    if (product == null)
      return null;

    // Utilize an embedded cache, to avoid both stale data and infinite recursion. In cases where other conversion algorithms
    // call this conversion, the value inside the cache will simply be used once converted,
    // instead of an infinite recursive attempt:
    String hashKey = "" + product.getProductId();
    if(productDataCache.containsKey(hashKey))
      return productDataCache.get(hashKey);

    // If not already cached, create an initial placeholder cached version, to avoid infinite recursion:
    ProductData.Builder productDataPlaceHolderBuilder = ProductData.newBuilder()
        .setProductId(product.getProductId());

    // Cache the placeholder:
    productDataCache.put(hashKey, productDataPlaceHolderBuilder.build());

    // Convert the remaining data fields:
    List<AnimalPart> partList = product.getContentList();
    List<TrayToProductTransfer> transferList = product.getTraySupplyJoinList();

    if(partList == null)
      partList = new ArrayList<>();

    List<AnimalPartData> animalPartDataList = new ArrayList<>();
    for (AnimalPart animalPart : partList)
      animalPartDataList.add(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart, animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache));


    if(transferList == null)
      transferList = new ArrayList<>();

    List<TrayToProductTransferData> transferDataList = new ArrayList<>();
    for (TrayToProductTransfer transfer : transferList)
      transferDataList.add(TrayToProductTransfer_ToGrpc_TrayToProductTransferData.convertToTrayToProductTransferData(transfer, animalPartDataCache, animalDataCache, partTypeDataCache, productDataCache, trayDataCache));

    ProductData productData = productDataPlaceHolderBuilder
        .addAllAnimalPartList(animalPartDataList)
        .addAllTrayToProductTransfersList(transferDataList)
        .build();

    // Replace the cached placeholder with the final version of this entity:
    productDataCache.put(hashKey, productData);
    return productData;
  }


  /** <p>Converts a List of Products into the gRPC compatible ProductsData format</p>
   * @param products A list containing all the Product entities to convert.
   * @return A gRPC compatible ProductsData data type, containing all the converted entities.*/
  public static ProductsData convertToProductsDataList(List<Product> products) {
    List<ProductData> productDataList = new ArrayList<>();

    // Convert List of Products to a gRPC compatible list by iteration through each entry and running the method previously declared:
    for (Product product : products)
      productDataList.add(convertToProductData(product, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

    // Construct and return a new List of ProductData entities:
    return ProductsData.newBuilder().addAllProducts(productDataList).build();
  }
}
