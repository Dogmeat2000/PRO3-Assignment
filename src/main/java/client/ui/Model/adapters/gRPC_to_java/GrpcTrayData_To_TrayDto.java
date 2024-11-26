package client.ui.Model.adapters.gRPC_to_java;

import grpc.*;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.dto.TrayDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a gRPC connection data entries into a client compatible dto</p> */
public class GrpcTrayData_To_TrayDto
{
  /** <p>Converts gRPC compatible TrayData information into the client compatible TrayDto entity</p> */
  public TrayDto convertToTrayDto(TrayData grpcData) {

    if (grpcData == null)
      return null;

    // Convert the gRPC data fields:
    long id = GrpcId_To_LongId.ConvertToLongId(grpcData.getTrayId());
    BigDecimal maxWeight_kilogram = grpcData.getMaxWeightKilogram().isEmpty() ? BigDecimal.ZERO : new BigDecimal(grpcData.getMaxWeightKilogram());
    BigDecimal weight_kilogram;

    if(grpcData.getWeightKilogram().isEmpty() || grpcData.getWeightKilogram().equals("0.00"))
      weight_kilogram = BigDecimal.ZERO;
    else
      weight_kilogram = new BigDecimal(grpcData.getWeightKilogram());

    long trayTypeId = GrpcId_To_LongId.ConvertToLongId(grpcData.getTrayTypeId());
    List<Long> animalPartIds = new ArrayList<>();
    List<Long> transferIds = new ArrayList<>();
    List<Long> productIds = new ArrayList<>();

    // Convert AnimalPartIds:
    for (AnimalPartId animalPartId : grpcData.getAnimalPartIdsList())
      animalPartIds.add(GrpcId_To_LongId.ConvertToLongId(animalPartId));

    // Construct new TrayDto entity:
    TrayDto trayDto = new TrayDto(id, maxWeight_kilogram, weight_kilogram, trayTypeId, animalPartIds);

    // Convert TransferIds:
    for (TrayToProductTransferId transferId : grpcData.getTransferIdsList())
      transferIds.add(GrpcId_To_LongId.ConvertToLongId(transferId));
    trayDto.addAllTransferIds(transferIds);

    // Convert ProductIds:
    for (ProductId productId : grpcData.getProductidsList())
      productIds.add(GrpcId_To_LongId.ConvertToLongId(productId));
    trayDto.addAllProductIds(productIds);

    return trayDto;
  }

  public List<TrayDto> convertToTrayDtoList(TraysData grpcData) {
    // Return an empty list, if received list is null or empty.
    if(grpcData == null || grpcData.getTraysList().isEmpty())
      return new ArrayList<>();

    // Convert List of TraysData to a java compatible list by iterating through each entry and running the method previously declared:
    List<TrayDto> trayDtoList = new ArrayList<>();
    for (TrayData trayData : grpcData.getTraysList())
      trayDtoList.add(convertToTrayDto(trayData));

    // return a new List of TrayDto entities:
    return trayDtoList;
  }
}
