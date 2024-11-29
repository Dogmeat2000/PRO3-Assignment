package Client.common.model.adapters.java_to_gRPC;

import grpc.*;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a client dto into a database/gRPC compatible formats</p> */
public class ProductDto_ToGrpc_ProductData
{
  /** <p>Converts a ProductDto entity into a gRPC compatible ProductData format</p>
   * @param dto The ProductDto entity to convert
   * @return a gRPC compatible ProductData data type.
   * */
  public ProductData convertToProductData(ProductDto dto) {

    if (dto == null)
      return null;

    // Convert the java data fields:
    ProductData.Builder builder = ProductData.newBuilder();
    builder.setProductId(LongId_ToGrpc_Id.convertToProductId(dto.getProductId()));

    // Convert AnimalPartIds:
    for (long id : dto.getAnimalPartIdList())
      builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(id));

    // Convert TransferIds:
    for (long id : dto.getTransferIdList())
      builder.addTransferIds(LongId_ToGrpc_Id.convertToTrayToProductTransferId(id));

    // Convert TrayIds:
    for (long id : dto.getTrayIdList())
      builder.addTrayIds(LongId_ToGrpc_Id.convertToTrayId(id));

    return builder.build();
  }


  /** <p>Converts a List of ProductDtos into the gRPC compatible ProductsData format</p>
   * @param productDtos A list containing all the ProductDto entities to convert.
   * @return A gRPC compatible ProductsData data type, containing all the converted entities.*/
  public ProductsData convertToProductsDataList(List<ProductDto> productDtos) {
    // Return an empty list, if received list is null or empty.
    if(productDtos == null || productDtos.isEmpty())
      return ProductsData.newBuilder().addAllProducts(new ArrayList<>()).build();

    // Convert List of ProductsDto to a gRPC compatible list by iteration through each entry and running the method previously declared:
    List<ProductData> productDataList = new ArrayList<>();
    for (ProductDto product : productDtos)
      productDataList.add(convertToProductData(product));

    // Construct and return a new List of ProductData entities:
    return ProductsData.newBuilder().addAllProducts(productDataList).build();
  }
}
