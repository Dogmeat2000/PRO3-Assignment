package client.ui.Model.adapters.gRPC_to_java;

import grpc.*;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a gRPC connection data entries into a client compatible dto</p> */
public class GrpcProductData_To_ProductDto
{
  /** <p>Converts gRPC compatible ProductData information into the client compatible ProductDto entity</p> */
  public ProductDto convertToProductDto(ProductData grpcData) {

    if (grpcData == null)
      return null;

    // Convert the gRPC data fields:
    long id = GrpcId_To_LongId.ConvertToLongId(grpcData.getProductId());
    List<Long> animalPartIds = new ArrayList<>();
    List<Long> transferIds = new ArrayList<>();
    List<Long> trayIds = new ArrayList<>();

    // Convert AnimalPartIds:
    for (AnimalPartId animalPartId : grpcData.getAnimalPartIdsList())
      animalPartIds.add(GrpcId_To_LongId.ConvertToLongId(animalPartId));

    // Convert TrayToProductTransferIds:
    for (TrayToProductTransferId transferId : grpcData.getTransferIdsList())
      transferIds.add(GrpcId_To_LongId.ConvertToLongId(transferId));

    // Convert TrayIds:
    for (TrayId trayId : grpcData.getTrayIdsList())
      trayIds.add(GrpcId_To_LongId.ConvertToLongId(trayId));

    // Construct and return new ProductDto entity:
    return new ProductDto(id, animalPartIds, trayIds, transferIds);
  }


  public List<ProductDto> convertToProductDtoList(ProductsData grpcData) {
    // Return an empty list, if received list is null or empty.
    if(grpcData == null || grpcData.getProductsList().isEmpty())
      return new ArrayList<>();

    // Convert List of ProductsData to a java compatible list by iterating through each entry and running the method previously declared:
    List<ProductDto> productList = new ArrayList<>();
    for (ProductData productData : grpcData.getProductsList())
      productList.add(convertToProductDto(productData));

    // return a new List of ProductDto entities:
    return productList;
  }
}
