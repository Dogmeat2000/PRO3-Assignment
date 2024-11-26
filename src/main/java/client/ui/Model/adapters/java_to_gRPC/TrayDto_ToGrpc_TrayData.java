package client.ui.Model.adapters.java_to_gRPC;

import grpc.*;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.TrayDto;

import java.util.ArrayList;
import java.util.List;

/** <p>Responsible for converting a client dto into a database/gRPC compatible formats</p> */
public class TrayDto_ToGrpc_TrayData
{
  /** <p>Converts a TrayDto entity into a gRPC compatible TrayData format</p>
   * @param dto The TrayDto entity to convert
   * @return a gRPC compatible TrayData data type.
   * */
  public TrayData convertToTrayData(TrayDto dto) {

    if (dto == null)
      return null;

    // Convert the java data fields:
    TrayData.Builder builder = TrayData.newBuilder();
    builder.setTrayId(LongId_ToGrpc_Id.convertToTrayId(dto.getTrayId()));
    builder.setMaxWeightKilogram(dto.getMaxWeight_kilogram().toString());
    builder.setWeightKilogram(dto.getWeight_kilogram().toString());
    try {
      builder.setTrayTypeId(LongId_ToGrpc_Id.convertToPartTypeId(dto.getTrayTypeId()));
    } catch (NullPointerException ignored) {}


    // Convert AnimalPartIds:
    for (long id : dto.getAnimalPartIdList())
      builder.addAnimalPartIds(LongId_ToGrpc_Id.convertToAnimalPartId(id));

    // Convert TrayToProductTransferIds:
    for (long id : dto.getTransferIdList())
      builder.addTransferIds(LongId_ToGrpc_Id.convertToTrayToProductTransferId(id));

    // Convert ProductIds:
    for (long id : dto.getProductIdList())
      builder.addProductids(LongId_ToGrpc_Id.convertToProductId(id));

    return builder.build();
  }


  /** <p>Converts a List of TrayDtos into the gRPC compatible TraysData format</p>
   * @param trayDtos A list containing all the TrayDto entities to convert.
   * @return A gRPC compatible TraysData data type, containing all the converted entities.*/
  public TraysData convertToTraysDataList(List<TrayDto> trayDtos) {
    // Return an empty list, if received list is null or empty.
    if(trayDtos == null || trayDtos.isEmpty())
      return TraysData.newBuilder().addAllTrays(new ArrayList<>()).build();

    // Convert List of TrayDtos to a gRPC compatible list by iterating through each entry and running the method previously declared:
    List<TrayData> trayDataList = new ArrayList<>();
    for (TrayDto dto : trayDtos)
      trayDataList.add(convertToTrayData(dto));

    // Construct and return a new List of TrayData entities:
    return TraysData.newBuilder().addAllTrays(trayDataList).build();
  }
}
