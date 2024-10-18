package server.controller.grpc.grpc_to_java;

import grpc.*;
import shared.model.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Responsible for converting a gRPC connection data entries into application compatible entities */
public class GrpcProductData_To_Product
{
    /** Converts database/gRPC compatible ProductData information into an application compatible Product entity */
    public static Product convertToProduct(ProductData productData,
        Map<String, AnimalPart> animalPartCache,
        Map<String, Animal> animalCache,
        Map<String, PartType> partTypeCache,
        Map<String, Product> productCache,
        Map<String, Tray> trayCache) {

      if (productData == null)
        return null;

      // Utilize an embedded cache, to avoid both stale data and infinite recursion. In cases where other conversion algorithms
      // call this conversion, the value inside the cache will simply be used once converted,
      // instead of an infinite recursive attempt:
      String hashKey = "" + productData.getProductId();
      if(productCache.containsKey(hashKey))
        return productCache.get(hashKey);

      // If not already cached, create an initial placeholder cached version, to avoid infinite recursion:
      long id = productData.getProductId();
      Product productPlaceHolder = new Product(id, null, null);

      // Cache the placeholder:
      productCache.put(hashKey, productPlaceHolder);

      // Convert the remaining gRPC data fields:
      List<AnimalPart> animalPartList = new ArrayList<>();
      for (AnimalPartData animalDataPart : productData.getAnimalPartListList())
        animalPartList.add(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalDataPart, animalPartCache, animalCache, partTypeCache, productCache, trayCache));

      List<TrayToProductTransfer> transferList = new ArrayList<>();
      for (TrayToProductTransferData transfer : productData.getTrayToProductTransfersListList())
        transferList.add(GrpcTrayToProductTransferData_To_TrayToProductTransfer.convertToTrayToProductTransfer(transfer, animalPartCache, animalCache, partTypeCache, productCache, trayCache));

      // Construct and return a new Product entity with the above read attributes set:
      Product product = productPlaceHolder.copy();
      product.getContentList().addAll(animalPartList);
      product.getTraySupplyJoinList().addAll(transferList);

      // Put the final entity into the cache:
      productCache.put(hashKey, product);

      return product;
    }


    public static List<Product> convertToProductList(ProductsData data) {
      List<Product> productList = new ArrayList<>();

      // Convert List of ProductsData to a java compatible list by iteration through each entry and running the method previously declared:
      for (ProductData productData : data.getProductsList())
        productList.add(convertToProduct(productData, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>()));

      // return a new List of Product entities:
      return productList;
    }
}
