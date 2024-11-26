package integrationTests.server.controller.grpc.service;

import client.ui.Model.adapters.gRPC_to_java.*;
import client.ui.Model.adapters.java_to_gRPC.*;
import grpc.*;
import integrationTests.TestDataSourceConfig;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import server.ServerApplication;
import client.ui.Model.adapters.GrpcFactory;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(
    classes = {
        ServerApplication.class,
        TestDataSourceConfig.class})
@ComponentScan(basePackages = "client")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class GrpcAnimalPartServiceImplTest
{
  private ManagedChannel channel;
  private AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub;
  private AnimalServiceGrpc.AnimalServiceBlockingStub animalStub;
  private PartTypeServiceGrpc.PartTypeServiceBlockingStub partTypeStub;
  private ProductServiceGrpc.ProductServiceBlockingStub productStub;
  private TrayServiceGrpc.TrayServiceBlockingStub trayStub;
  /*private ProductRegistrationSystem productRegistrationSystem;
  private AnimalPartRegistrationSystem animalPartRegistrationSystem;
  private AnimalRegistrationSystem animalRegistrationSystem;
  private TrayRegistrationSystem trayRegistrationSystem;
  private PartTypeRegistrationSystem partTypeRegistrationSystem;*/
  private final GrpcAnimalData_To_AnimalDto grpcAnimalData_To_AnimalDto = new GrpcAnimalData_To_AnimalDto();
  private final GrpcAnimalPartData_To_AnimalPartDto grpcAnimalPartData_To_AnimalPartDto = new GrpcAnimalPartData_To_AnimalPartDto();
  private final GrpcProductData_To_ProductDto grpcProductData_To_ProductDto = new GrpcProductData_To_ProductDto();
  private final GrpcPartTypeData_To_PartTypeDto grpcPartTypeData_To_PartTypeDto = new GrpcPartTypeData_To_PartTypeDto();
  private final GrpcTrayData_To_TrayDto grpcTrayData_To_TrayDto = new GrpcTrayData_To_TrayDto();
  /*private final AnimalDto_ToGrpc_AnimalData animal_To_AnimalData = new AnimalDto_ToGrpc_AnimalData();
  private final AnimalPartDto_ToGrpc_AnimalPartData animalPart_To_AnimalPartData = new AnimalPartDto_ToGrpc_AnimalPartData();*/
  private final ProductDto_ToGrpc_ProductData product_To_ProductData = new ProductDto_ToGrpc_ProductData();
  private final PartTypeDto_ToGrpc_PartTypeData partType_To_PartTypeData = new PartTypeDto_ToGrpc_PartTypeData();
  //private final TrayDto_ToGrpc_TrayData tray_To_TrayData = new TrayDto_ToGrpc_TrayData();

  //@Value("${maxNestingDepth}") private int maxNestingDepth;

  // Registered data in test DB, prior to any AnimalParts being added:
  private AnimalDto animal1 = null;
  private AnimalDto animal2 = null;
  private PartTypeDto partType1 = null;
  private PartTypeDto partType2 = null;
  private TrayDto tray1 = null;
  private TrayDto tray2 = null;

  @BeforeEach
  public void setUp() {
    // Start up a gRPC client channel:
    channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build();

    /*animalPartRegistrationSystem = new AnimalPartRegistrationSystemImpl("localhost", 9090);
    productRegistrationSystem = new ProductRegistrationSystemImpl("localhost", 9090);
    animalRegistrationSystem = new AnimalRegistrationSystemImpl("localhost", 9090);
    trayRegistrationSystem = new TrayRegistrationSystemImpl("localhost", 9090);
    partTypeRegistrationSystem = new PartTypeRegistrationSystemImpl("localhost", 9090);*/

    animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);
    animalStub = AnimalServiceGrpc.newBlockingStub(channel);
    partTypeStub = PartTypeServiceGrpc.newBlockingStub(channel);
    productStub = ProductServiceGrpc.newBlockingStub(channel);
    trayStub = TrayServiceGrpc.newBlockingStub(channel);

    // Initialize all the @Mock and @InjectMock fields, allowing Spring Boot time to perform its Dependency Injection.
    MockitoAnnotations.openMocks(this);

    // In the DB, register some Animals, PartTypes, Products and Trays to use in the AnimalPart tests:
    // Animals
    String origin1 = "Test Farmstead";
    Date arrivalDate1 = Timestamp.from(Instant.now());
    BigDecimal weight1 = new BigDecimal("542.41");

    String origin2 = "Test Animal Coop";
    Date arrivalDate2 = Timestamp.from(Instant.now());
    BigDecimal weight2 = new BigDecimal("123.45");

    // Part Types
    String typeDesc1 = "Cows Foot";
    String typeDesc2 = "Pig Tail";

    // Trays
    BigDecimal maxCapacityTray1 = new BigDecimal("25.00");
    BigDecimal curCapacityTray1 = new BigDecimal("0.00");

    BigDecimal maxCapacityTray2 = new BigDecimal("25.00");
    BigDecimal curCapacityTray2 = new BigDecimal("0.00");

    // Register the data:
    try {
      animal1 = grpcAnimalData_To_AnimalDto.convertToAnimalDto(animalStub.registerAnimal(GrpcFactory.buildGrpcAnimalData(1, weight1, origin1, arrivalDate1, new ArrayList<>())));
      animal2 = grpcAnimalData_To_AnimalDto.convertToAnimalDto(animalStub.registerAnimal(GrpcFactory.buildGrpcAnimalData(1, weight2, origin2, arrivalDate2, new ArrayList<>())));
      partType1 = grpcPartTypeData_To_PartTypeDto.convertToPartTypeDto(partTypeStub.registerPartType(GrpcFactory.buildGrpcPartTypeData(1L, typeDesc1, new ArrayList<>())));
      partType2 = grpcPartTypeData_To_PartTypeDto.convertToPartTypeDto(partTypeStub.registerPartType(GrpcFactory.buildGrpcPartTypeData(1L, typeDesc2, new ArrayList<>())));
      tray1 = grpcTrayData_To_TrayDto.convertToTrayDto(trayStub.registerTray(GrpcFactory.buildGrpcTrayData(1, maxCapacityTray1, curCapacityTray1, new ArrayList<>(), new ArrayList<>(), partType1)));
      tray2 = grpcTrayData_To_TrayDto.convertToTrayDto(trayStub.registerTray(GrpcFactory.buildGrpcTrayData(1, maxCapacityTray2, curCapacityTray2, new ArrayList<>(), new ArrayList<>(), partType2)));

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

  }

  @AfterEach
  public void tearDown() {
    // Tear down the gRPC client channel:
    channel.shutdownNow();
    channel = null;

    animalPartStub = null;
    animalStub = null;
    partTypeStub = null;
    productStub = null;
    trayStub = null;
    /*productRegistrationSystem = null;
    animalPartRegistrationSystem = null;
    animalRegistrationSystem = null;*/

    animal1 = null;
    animal2 = null;
    partType1 = null;
    partType2 = null;
    tray1 = null;
    tray2 = null;
  }


  @Test
  public void whenRegisterNewAnimalPart_WithValidAnimalData_ReturnsRegisteredAnimal() {
    // Arrange:
    AnimalDto parentAnimal = animal1.copy();
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("7.42");
    AnimalPartDto createdAnimalPart = null;

    // Act:
    try {
      AnimalPartData animalPartData = animalPartStub.registerAnimalPart(GrpcFactory.buildGrpcAnimalPartData(1, parentAnimal, parentPartType, parentTray, weight));
      createdAnimalPart = grpcAnimalPartData_To_AnimalPartDto.convertToAnimalPartDto(animalPartData);
    } catch (Exception e) {
      e.printStackTrace(); // TODO: DELETE LINE
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Read the most recent Animal, Tray and PartType, since AnimalPart was now assigned.
    parentAnimal = grpcAnimalData_To_AnimalDto.convertToAnimalDto(animalStub.readAnimal(LongId_ToGrpc_Id.convertToAnimalId(parentAnimal.getAnimalId())));
    parentTray = grpcTrayData_To_TrayDto.convertToTrayDto(trayStub.readTray(LongId_ToGrpc_Id.convertToTrayId(parentTray.getTrayId())));
    parentPartType = grpcPartTypeData_To_PartTypeDto.convertToPartTypeDto(partType_To_PartTypeData.convertToPartTypeData(parentPartType));

    // Assert:
    // Validate the created AnimalPart:
    assertNotNull(createdAnimalPart);
    assertEquals(1L, createdAnimalPart.getPartId());
    assertEquals(weight, createdAnimalPart.getWeight_kilogram());
    assertEquals(parentAnimal.getAnimalId(), createdAnimalPart.getAnimalId());
    assertEquals(parentPartType.getTypeId(), createdAnimalPart.getTypeId());
    assertEquals(parentTray.getTrayId(), createdAnimalPart.getTrayId());
  }

  @Test
  public void whenRegisterNewAnimalPart_WithValidAnimalPartDataAndValidProduct_ReturnsRegisteredAnimal() {
    // Arrange:
    AnimalDto parentAnimal = animal1.copy();
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("7.42");
    ProductDto product = null;
    AnimalPartDto createdAnimalPart = null;

    // Act:
    try {
      // Register without product:
      AnimalPartData animalPartData = animalPartStub.registerAnimalPart(GrpcFactory.buildGrpcAnimalPartData(1, parentAnimal, parentPartType, parentTray, weight));
      createdAnimalPart = grpcAnimalPartData_To_AnimalPartDto.convertToAnimalPartDto(animalPartData);

      // Assign to a product:
      ArrayList<Long> listAnimalPartId = new ArrayList<>();
      listAnimalPartId.add(createdAnimalPart.getPartId());
      ArrayList<Long> listTrayId = new ArrayList<>();
      listTrayId.add(parentTray.getTrayId());

      // Register AnimalPart with Product
      ProductData productData = productStub.registerProduct(product_To_ProductData.convertToProductData(new ProductDto(1, listAnimalPartId, listTrayId, null)));
      product = grpcProductData_To_ProductDto.convertToProductDto(productData);

      // Read the most recent Animal, Tray, PartType and AnimalPart, since Product was now assigned.
      createdAnimalPart = grpcAnimalPartData_To_AnimalPartDto.convertToAnimalPartDto(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(createdAnimalPart.getPartId())));
      parentAnimal = grpcAnimalData_To_AnimalDto.convertToAnimalDto(animalStub.readAnimal(LongId_ToGrpc_Id.convertToAnimalId(parentAnimal.getAnimalId())));
      parentTray = grpcTrayData_To_TrayDto.convertToTrayDto(trayStub.readTray(LongId_ToGrpc_Id.convertToTrayId(parentTray.getTrayId())));
      parentPartType = grpcPartTypeData_To_PartTypeDto.convertToPartTypeDto(partTypeStub.readPartType(LongId_ToGrpc_Id.convertToPartTypeId(parentPartType.getTypeId())));

    } catch (Exception e) {
      e.printStackTrace(); // TODO: DELETE LINE
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    // Validate the created AnimalPart:

    // Assert AnimalPart:
    assertNotNull(createdAnimalPart);
    assertEquals(1L, createdAnimalPart.getPartId());
    assertEquals(weight, createdAnimalPart.getWeight_kilogram());

    // Assert embedded/associated Animal:
    assertEquals(parentAnimal.getAnimalId(), createdAnimalPart.getAnimalId());

    // Assert embedded/associated PartType:
    assertEquals(parentPartType.getTypeId(), createdAnimalPart.getTypeId());

    // Assert embedded/associated Tray:
    assertEquals(parentTray.getTrayId(), createdAnimalPart.getTrayId());

    // Assert embedded/associated Product:
    assertEquals(product.getProductId(), createdAnimalPart.getProductId());
  }
}
