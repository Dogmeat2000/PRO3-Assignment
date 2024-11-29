package DataServer.controller.grpc.adapters.grpc_to_java;

import grpc.TrayToProductTransferData;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import DataServer.model.persistence.entities.Product;
import DataServer.model.persistence.entities.Tray;
import DataServer.model.persistence.entities.TrayToProductTransfer;
import DataServer.model.persistence.service.ProductService;
import DataServer.model.persistence.service.TrayService;
import shared.model.adapters.gRPC_to_java.GrpcId_To_LongId;
import shared.model.exceptions.persistance.NotFoundException;

/** <p>Responsible for converting a gRPC connection data entries into application compatible entities</p> */
@Component
public class GrpcTrayToProductTransferData_To_TrayToProductTransfer
{
  private final TrayService trayService;
  private final ProductService productService;

  @Autowired
  public GrpcTrayToProductTransferData_To_TrayToProductTransfer(TrayService trayService, ProductService productService) {
    this.trayService = trayService;
    this.productService = productService;
  }

  /** <p>Converts gRPC compatible TrayToProductTransferData information into an application compatible TrayToProductTransfer entity</p> */
  public TrayToProductTransfer convertToTrayToProductTransfer(TrayToProductTransferData trayToProductTransferData) throws NotFoundException, DataIntegrityViolationException, PersistenceException {

    if (trayToProductTransferData == null)
      return null;

    // Convert the gRPC data fields, excluding any lists of other entities. These are queried from the repository based on the provided ids:
    long id = GrpcId_To_LongId.ConvertToLongId(trayToProductTransferData.getTransferId());

    // Query database for the referenced Tray entity, for proper Object Relational Model (ORM) behavior:
    Tray tray = trayService.readTray(GrpcId_To_LongId.ConvertToLongId(trayToProductTransferData.getTrayId()));

    // Query database for the referenced Product entity, for proper Object Relational Model (ORM) behavior:
    Product product = productService.readProduct(GrpcId_To_LongId.ConvertToLongId(trayToProductTransferData.getProductId()));

    // Construct and return a new Tray entity with the above read attributes set:
    return new TrayToProductTransfer(id, tray, product);
  }
}
