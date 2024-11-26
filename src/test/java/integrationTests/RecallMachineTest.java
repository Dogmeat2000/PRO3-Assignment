package integrationTests;

import client.interfaces.AnimalPartRegistrationSystem;
import client.interfaces.AnimalRegistrationSystem;
import client.interfaces.ProductRegistrationSystem;
import client.ui.Model.adapters.gRPC_to_java.*;
import client.ui.Model.adapters.java_to_gRPC.*;
import client.ui.Model.service.AnimalPartRegistrationSystemImpl;
import client.ui.Model.service.AnimalRegistrationSystemImpl;
import client.ui.Model.service.ProductRegistrationSystemImpl;
import grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import server.ServerApplication;
import server.model.persistence.entities.*;
import shared.model.adapters.java_to_gRPC.LongId_ToGrpc_Id;
import shared.model.dto.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(
    classes = {
        ServerApplication.class,
        TestDataSourceConfig.class})
@ComponentScan(basePackages = "client")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RecallMachineTest
{
  private ManagedChannel channel;
  private AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub;
  private AnimalServiceGrpc.AnimalServiceBlockingStub animalStub;
  private PartTypeServiceGrpc.PartTypeServiceBlockingStub partTypeStub;
  private ProductServiceGrpc.ProductServiceBlockingStub productStub;
  private TrayServiceGrpc.TrayServiceBlockingStub trayStub;
  private ProductRegistrationSystem productRegistrationSystem;
  private AnimalPartRegistrationSystem animalPartRegistrationSystem;
  private AnimalRegistrationSystem animalRegistrationSystem;
  private final GrpcAnimalData_To_AnimalDto grpcAnimalData_To_AnimalDto = new GrpcAnimalData_To_AnimalDto();
  private final GrpcAnimalPartData_To_AnimalPartDto grpcAnimalPartData_To_AnimalPartDto = new GrpcAnimalPartData_To_AnimalPartDto();
  private final GrpcProductData_To_ProductDto grpcProductData_To_ProductDto = new GrpcProductData_To_ProductDto();
  private final GrpcPartTypeData_To_PartTypeDto grpcPartTypeData_To_PartTypeDto = new GrpcPartTypeData_To_PartTypeDto();
  private final GrpcTrayData_To_TrayDto grpcTrayData_To_TrayDto = new GrpcTrayData_To_TrayDto();
  private final AnimalDto_ToGrpc_AnimalData animalDto_To_AnimalData = new AnimalDto_ToGrpc_AnimalData();
  private final AnimalPartDto_ToGrpc_AnimalPartData animalPartDto_To_AnimalPartData = new AnimalPartDto_ToGrpc_AnimalPartData();
  private final ProductDto_ToGrpc_ProductData productDto_To_ProductData = new ProductDto_ToGrpc_ProductData();
  private final PartTypeDto_ToGrpc_PartTypeData partTypeDto_To_PartTypeData = new PartTypeDto_ToGrpc_PartTypeData();
  private final TrayDto_ToGrpc_TrayData trayDto_To_TrayData = new TrayDto_ToGrpc_TrayData();

  @BeforeEach
  public void setUp() {
    // Start up a gRPC client channel:
    channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build();

    animalPartRegistrationSystem = new AnimalPartRegistrationSystemImpl("localhost", 9090);
    productRegistrationSystem = new ProductRegistrationSystemImpl("localhost", 9090);
    animalRegistrationSystem = new AnimalRegistrationSystemImpl("localhost", 9090);

    animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);
    animalStub = AnimalServiceGrpc.newBlockingStub(channel);
    partTypeStub = PartTypeServiceGrpc.newBlockingStub(channel);
    productStub = ProductServiceGrpc.newBlockingStub(channel);
    trayStub = TrayServiceGrpc.newBlockingStub(channel);

    // Initialize all the @Mock and @InjectMock fields, allowing Spring Boot time to perform its Dependency Injection.
    MockitoAnnotations.openMocks(this);
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
    productRegistrationSystem = null;
    animalPartRegistrationSystem = null;
    animalRegistrationSystem = null;
  }

  @Test
  public void testRetrieveRegistrationNumberForAllAnimalsInvolvedInAProduct() {
    // Arrange: Add some entities to the temporary database:
    // Create Animals:
    AnimalDto animal1 = new AnimalDto(1L, BigDecimal.valueOf(420), "Johnson Farmstead", Timestamp.from(Instant.now()), null);
    AnimalDto animal2 = new AnimalDto(1L, BigDecimal.valueOf(400), "Smith Farmstead", Timestamp.from(Instant.now()), null);
    AnimalDto animal3 = new AnimalDto(1L,BigDecimal.valueOf(435), "Corporate Slaughterers Inc.", Timestamp.from(Instant.now()), null);

    animal1 = grpcAnimalData_To_AnimalDto.convertToAnimalDto(animalStub.registerAnimal(animalDto_To_AnimalData.convertToAnimalData(animal1)));
    animal2 = grpcAnimalData_To_AnimalDto.convertToAnimalDto(animalStub.registerAnimal(animalDto_To_AnimalData.convertToAnimalData(animal2)));
    animal3 = grpcAnimalData_To_AnimalDto.convertToAnimalDto(animalStub.registerAnimal(animalDto_To_AnimalData.convertToAnimalData(animal3)));

    // Create PartTypes:
    PartTypeDto partType1 = new PartTypeDto(1L, "type1");
    PartTypeDto partType2 = new PartTypeDto(1L, "type2");

    partType1 = grpcPartTypeData_To_PartTypeDto.convertToPartTypeDto(partTypeStub.registerPartType(partTypeDto_To_PartTypeData.convertToPartTypeData(partType1)));
    partType2 = grpcPartTypeData_To_PartTypeDto.convertToPartTypeDto(partTypeStub.registerPartType(partTypeDto_To_PartTypeData.convertToPartTypeData(partType2)));

    // Create Trays:
    TrayDto tray1 = new TrayDto(1L, BigDecimal.valueOf(25), BigDecimal.ZERO, 0, null);
    TrayDto tray2 = new TrayDto(1L, BigDecimal.valueOf(25), BigDecimal.ZERO, 0, null);

    tray1 = grpcTrayData_To_TrayDto.convertToTrayDto(trayStub.registerTray(trayDto_To_TrayData.convertToTrayData(tray1)));
    tray2 = grpcTrayData_To_TrayDto.convertToTrayDto(trayStub.registerTray(trayDto_To_TrayData.convertToTrayData(tray2)));

    // Create AnimalParts:
    AnimalPartDto animalPart1 = new AnimalPartDto(1L, BigDecimal.valueOf(3.4), partType1.getTypeId(), animal1.getAnimalId(), tray1.getTrayId(), 0);
    AnimalPartDto animalPart2 = new AnimalPartDto(1L, BigDecimal.valueOf(2.7), partType2.getTypeId(), animal2.getAnimalId(), tray2.getTrayId(), 0);
    AnimalPartDto animalPart3 = new AnimalPartDto(1L, BigDecimal.valueOf(5.12), partType1.getTypeId(), animal1.getAnimalId(), tray1.getTrayId(), 0);

    animalPart1 = grpcAnimalPartData_To_AnimalPartDto.convertToAnimalPartDto(animalPartStub.registerAnimalPart(animalPartDto_To_AnimalPartData.convertToAnimalPartData(animalPart1)));
    animalPart2 = grpcAnimalPartData_To_AnimalPartDto.convertToAnimalPartDto(animalPartStub.registerAnimalPart(animalPartDto_To_AnimalPartData.convertToAnimalPartData(animalPart2)));
    animalPart3 = grpcAnimalPartData_To_AnimalPartDto.convertToAnimalPartDto(animalPartStub.registerAnimalPart(animalPartDto_To_AnimalPartData.convertToAnimalPartData(animalPart3)));

    // Create Products:
    List<Long> contentList = new ArrayList<>();
    contentList.add(animalPart1.getPartId());
    List<Long> trayList = new ArrayList<>();
    trayList.add(tray1.getTrayId());
    ProductDto product1 = new ProductDto(1L, contentList, trayList, null);
    product1 = grpcProductData_To_ProductDto.convertToProductDto(productStub.registerProduct(productDto_To_ProductData.convertToProductData(product1)));

    contentList = new ArrayList<>();
    contentList.add(animalPart2.getPartId());
    trayList = new ArrayList<>();
    trayList.add(tray2.getTrayId());
    ProductDto product2 = new ProductDto(1L, contentList, trayList, null);
    product2 = grpcProductData_To_ProductDto.convertToProductDto(productStub.registerProduct(productDto_To_ProductData.convertToProductData(product2)));

    // Act: Simulate the process that is performed when this command is selected from main:
    // Simulate user prompt to select a Product_id to get info for:
    long productId = product1.getProductId();

    ProductDto productReceived;
    productReceived = productRegistrationSystem.readProduct(productId);

    // Retrieve a List of all AnimalPart_ids involved in this Product:
    List<AnimalPartDto> animalParts = animalPartRegistrationSystem.readAnimalPartsByProductId(productId);

    // Retrieve a List of all Animals involved in each AnimalPart:
    List<Long> animalIdsInvolved = new ArrayList<>();
    for (AnimalPartDto animalPart : animalParts)
      if(animalPart.getAnimalId() > 0)
        animalIdsInvolved.add(animalPart.getAnimalId());


    // Assert:
    // Verify if gRPC response is correct and if the database is updated:
    assertNotNull(productReceived); // Ensure that the Product looked up, actually exists in DB
    assertNotNull(animalParts); // Ensure that the received AnimalPart list is not null
    assertEquals(1L, productReceived.getProductId());
    assertEquals(1L, animalIdsInvolved.get(0));
    assertEquals(1, animalIdsInvolved.size());
  }


  @Test
  public void testRetrieveAllProductsAGivenAnimalIsInvolvedIn() {
    // Arrange: Add some entities to the temporary database:
    // Create Animals:
    AnimalDto animal1 = new AnimalDto(1L, BigDecimal.valueOf(420), "Johnson Farmstead", Timestamp.from(Instant.now()), null);
    AnimalDto animal2 = new AnimalDto(1L, BigDecimal.valueOf(400), "Smith Farmstead", Timestamp.from(Instant.now()), null);
    AnimalDto animal3 = new AnimalDto(1L,BigDecimal.valueOf(435), "Corporate Slaughterers Inc.", Timestamp.from(Instant.now()), null);

    animal1 = grpcAnimalData_To_AnimalDto.convertToAnimalDto(animalStub.registerAnimal(animalDto_To_AnimalData.convertToAnimalData(animal1)));
    animal2 = grpcAnimalData_To_AnimalDto.convertToAnimalDto(animalStub.registerAnimal(animalDto_To_AnimalData.convertToAnimalData(animal2)));
    animal3 = grpcAnimalData_To_AnimalDto.convertToAnimalDto(animalStub.registerAnimal(animalDto_To_AnimalData.convertToAnimalData(animal3)));

    // Create PartTypes:
    PartTypeDto partType1 = new PartTypeDto(1L, "type1");
    PartTypeDto partType2 = new PartTypeDto(1L, "type2");

    partType1 = grpcPartTypeData_To_PartTypeDto.convertToPartTypeDto(partTypeStub.registerPartType(partTypeDto_To_PartTypeData.convertToPartTypeData(partType1)));
    partType2 = grpcPartTypeData_To_PartTypeDto.convertToPartTypeDto(partTypeStub.registerPartType(partTypeDto_To_PartTypeData.convertToPartTypeData(partType2)));

    // Create Trays:
    TrayDto tray1 = new TrayDto(1L, BigDecimal.valueOf(25), BigDecimal.ZERO, 0, null);
    TrayDto tray2 = new TrayDto(1L, BigDecimal.valueOf(25), BigDecimal.ZERO, 0, null);

    tray1 = grpcTrayData_To_TrayDto.convertToTrayDto(trayStub.registerTray(trayDto_To_TrayData.convertToTrayData(tray1)));
    tray2 = grpcTrayData_To_TrayDto.convertToTrayDto(trayStub.registerTray(trayDto_To_TrayData.convertToTrayData(tray2)));

    // Create AnimalParts:
    AnimalPartDto animalPart1 = new AnimalPartDto(1L, BigDecimal.valueOf(3.4), partType1.getTypeId(), animal1.getAnimalId(), tray1.getTrayId(), 0);
    AnimalPartDto animalPart2 = new AnimalPartDto(1L, BigDecimal.valueOf(2.7), partType2.getTypeId(), animal2.getAnimalId(), tray2.getTrayId(), 0);
    AnimalPartDto animalPart3 = new AnimalPartDto(1L, BigDecimal.valueOf(5.12), partType1.getTypeId(), animal1.getAnimalId(), tray1.getTrayId(), 0);

    animalPart1 = grpcAnimalPartData_To_AnimalPartDto.convertToAnimalPartDto(animalPartStub.registerAnimalPart(animalPartDto_To_AnimalPartData.convertToAnimalPartData(animalPart1)));
    animalPart2 = grpcAnimalPartData_To_AnimalPartDto.convertToAnimalPartDto(animalPartStub.registerAnimalPart(animalPartDto_To_AnimalPartData.convertToAnimalPartData(animalPart2)));
    animalPart3 = grpcAnimalPartData_To_AnimalPartDto.convertToAnimalPartDto(animalPartStub.registerAnimalPart(animalPartDto_To_AnimalPartData.convertToAnimalPartData(animalPart3)));

    // Create Products:
    List<Long> contentList = new ArrayList<>();
    contentList.add(animalPart1.getPartId());
    List<Long> trayList = new ArrayList<>();
    trayList.add(tray1.getTrayId());
    ProductDto product1 = new ProductDto(1L, contentList, trayList, null);
    product1 = grpcProductData_To_ProductDto.convertToProductDto(productStub.registerProduct(productDto_To_ProductData.convertToProductData(product1)));

    contentList = new ArrayList<>();
    contentList.add(animalPart2.getPartId());
    trayList = new ArrayList<>();
    trayList.add(tray2.getTrayId());
    ProductDto product2 = new ProductDto(1L, contentList, trayList, null);
    product2 = grpcProductData_To_ProductDto.convertToProductDto(productStub.registerProduct(productDto_To_ProductData.convertToProductData(product2)));


    // Act: Simulate the process that is performed when this command is selected from main:
    // Simulate user prompt to select a Product_id to get info for:
    long animalId = animal1.getAnimalId();

    AnimalDto animalReceived;
    animalReceived = animalRegistrationSystem.readAnimal(animalId);

    // Retrieve a List of all AnimalPart_ids involved in this Animal:
    List<AnimalPartDto> animalParts = animalPartRegistrationSystem.readAnimalPartsByAnimalId(animalId);

    // Retrieve a List of all Products involved in each AnimalPart:
    List<Long> productIdsInvolved = new ArrayList<>();
    for (AnimalPartDto animalPart : animalParts)
      if(animalPart.getProductId() > 0)
        productIdsInvolved.add(animalPart.getProductId());


    // Assert:
    // Verify if gRPC response is correct and if the database is updated:
    assertNotNull(animalReceived); // Ensure that the Product looked up, actually exists in DB
    assertNotNull(animalParts); // Ensure that the received AnimalPart list is not null
    assertEquals(1L, animalReceived.getAnimalId());
    assertEquals(1L, productIdsInvolved.get(0));
    assertEquals(1, productIdsInvolved.size());
  }

}
