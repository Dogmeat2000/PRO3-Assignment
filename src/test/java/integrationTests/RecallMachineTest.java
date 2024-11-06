package integrationTests;

import client.interfaces.AnimalPartRegistrationSystem;
import client.interfaces.AnimalRegistrationSystem;
import client.interfaces.ProductRegistrationSystem;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import server.ServerApplication;
import server.controller.grpc.adapters.grpc_to_java.*;
import server.controller.grpc.adapters.java_to_gRPC.*;
import shared.model.entities.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)  // Gives access to extended testing functionality
@SpringBootTest(
    classes = {
        ServerApplication.class, /*RecallMachineApplication.class,*/
        TestDataSourceConfig.class}) // Signals to Spring Boot that this is a Spring Boot Test and defines which spring configs to use!
@ComponentScan(basePackages = "client")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Ensures that Mocks are reset after each test, to avoid tests modifying data in shared mocks, that could cause tests to influence each other.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Establishes an InMemory database instead of using the actual Postgresql database, so tests do not disrupt the production database.
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
  private final GrpcAnimalData_To_Animal grpcAnimalData_To_Animal = new GrpcAnimalData_To_Animal();
  private final GrpcAnimalPartData_To_AnimalPart grpcAnimalPartData_To_Animal = new GrpcAnimalPartData_To_AnimalPart();
  private final GrpcProductData_To_Product grpcProductData_To_Product = new GrpcProductData_To_Product();
  private final GrpcPartTypeData_To_PartType grpcPartTypeData_To_PartType = new GrpcPartTypeData_To_PartType();
  private final GrpcTrayData_To_Tray grpcTrayData_To_Tray = new GrpcTrayData_To_Tray();
  @Value("${maxNestingDepth}") private int maxNestingDepth;


  @BeforeEach
  public void setUp() {
    // Start up a gRPC client channel:
    channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build();

    animalPartRegistrationSystem = new AnimalPartRegistrationSystemImpl("localhost", 9090, maxNestingDepth);
    productRegistrationSystem = new ProductRegistrationSystemImpl("localhost", 9090, maxNestingDepth);
    animalRegistrationSystem = new AnimalRegistrationSystemImpl("localhost", 9090, maxNestingDepth);

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
    Animal animal1 = new Animal(1L, BigDecimal.valueOf(420), "Johnson Farmstead", Timestamp.from(Instant.now()));

    Animal animal2 = new Animal(1L, BigDecimal.valueOf(400), "Smith Farmstead", Timestamp.from(Instant.now()));

    Animal animal3 = new Animal(1L,BigDecimal.valueOf(435), "Corporate Slaughterers Inc.", Timestamp.from(Instant.now()));

    animal1 = grpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal1)), maxNestingDepth);
    //System.out.println(animal1);
    animal2 = grpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal2)), maxNestingDepth);
    animal3 = grpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal3)), maxNestingDepth);

    // Create PartTypes:
    PartType partType1 = new PartType(1L, "type1");
    PartType partType2 = new PartType(1L, "type2");

    partType1 = grpcPartTypeData_To_PartType.convertToPartType(partTypeStub.registerPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType1)), maxNestingDepth);
    partType2 = grpcPartTypeData_To_PartType.convertToPartType(partTypeStub.registerPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType2)), maxNestingDepth);

    // Create Trays:
    Tray tray1 = new Tray(1L, BigDecimal.valueOf(25), BigDecimal.valueOf(0), new ArrayList<>(), new ArrayList<>());
    Tray tray2 = new Tray(1L, BigDecimal.valueOf(25), BigDecimal.valueOf(0), new ArrayList<>(), new ArrayList<>());

    tray1 = grpcTrayData_To_Tray.convertToTray(trayStub.registerTray(Tray_ToGrpc_TrayData.convertToTrayData(tray1, maxNestingDepth)),maxNestingDepth);
    tray2 = grpcTrayData_To_Tray.convertToTray(trayStub.registerTray(Tray_ToGrpc_TrayData.convertToTrayData(tray2, maxNestingDepth)),maxNestingDepth);

    // Create AnimalParts:
    AnimalPart animalPart1 = new AnimalPart(1L, BigDecimal.valueOf(3.4), partType1, animal1, tray1, null);
    AnimalPart animalPart2 = new AnimalPart(1L, BigDecimal.valueOf(2.7), partType2, animal2, tray2, null);
    AnimalPart animalPart3 = new AnimalPart(1L, BigDecimal.valueOf(5.12), partType1, animal1, tray1, null);

    animalPart1 = grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart1)), maxNestingDepth);
    animalPart2 = grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart2)), maxNestingDepth);
    animalPart3 = grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart3)), maxNestingDepth);

    // Create Products:
    List<Long> contentIdList = new ArrayList<>();
    contentIdList.add(animalPart1.getPart_id());
    List<Long> trayIdList = new ArrayList<>();
    trayIdList.add(tray1.getTrayId());
    Product product1 = new Product(1L, contentIdList, trayIdList);
    product1 = grpcProductData_To_Product.convertToProduct(productStub.registerProduct(Product_ToGrpc_ProductData.convertToProductData(product1, maxNestingDepth)),maxNestingDepth);

    for (Long animalPartId : product1.getAnimalPartIdList())
      product1.addAnimalPart(grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId)), maxNestingDepth));

    contentIdList.clear();
    contentIdList.add(animalPart2.getPart_id());
    trayIdList.clear();
    trayIdList.add(tray2.getTrayId());
    Product product2 = new Product(1L, contentIdList, trayIdList);
    product2 = grpcProductData_To_Product.convertToProduct(productStub.registerProduct(Product_ToGrpc_ProductData.convertToProductData(product2, maxNestingDepth)),maxNestingDepth);

    for (Long animalPartId : product2.getAnimalPartIdList())
      product2.addAnimalPart(grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId)), maxNestingDepth));


    // Act: Simulate the process that is performed when this command is selected from main:
    // Simulate user prompt to select a Product_id to get info for:
    long productId = product1.getProductId();

    Product productReceived;
    productReceived = productRegistrationSystem.readProduct(productId);

    for (Long animalPartId : productReceived.getAnimalPartIdList())
      productReceived.addAnimalPart(grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId)), maxNestingDepth));

    // Retrieve a List of all AnimalPart_ids involved in this Product:
    List<AnimalPart> animalParts = animalPartRegistrationSystem.readAnimalPartsByProductId(productId);

    // Retrieve a List of all Animals involved in each AnimalPart:
    List<Long> animalIdsInvolved = new ArrayList<>();
    for (AnimalPart animalPart : animalParts)
      if(animalPart.getAnimal().getId() > 0)
        animalIdsInvolved.add(animalPart.getAnimal().getId());


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
    Animal animal1 = new Animal(1L, BigDecimal.valueOf(420), "Johnson Farmstead", Timestamp.from(Instant.now()));

    Animal animal2 = new Animal(1L, BigDecimal.valueOf(400), "Smith Farmstead", Timestamp.from(Instant.now()));

    Animal animal3 = new Animal(1L,BigDecimal.valueOf(435), "Corporate Slaughterers Inc.", Timestamp.from(Instant.now()));

    animal1 = grpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal1)), maxNestingDepth);
    System.out.println(animal1);
    animal2 = grpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal2)), maxNestingDepth);
    animal3 = grpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal3)), maxNestingDepth);

    // Create PartTypes:
    PartType partType1 = new PartType(1L, "type1");
    PartType partType2 = new PartType(1L, "type2");

    partType1 = grpcPartTypeData_To_PartType.convertToPartType(partTypeStub.registerPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType1)), maxNestingDepth);
    partType2 = grpcPartTypeData_To_PartType.convertToPartType(partTypeStub.registerPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType2)), maxNestingDepth);

    // Create Trays:
    Tray tray1 = new Tray(1L, BigDecimal.valueOf(25), BigDecimal.valueOf(0), new ArrayList<>(), new ArrayList<>());
    Tray tray2 = new Tray(1L, BigDecimal.valueOf(25), BigDecimal.valueOf(0), new ArrayList<>(), new ArrayList<>());

    tray1 = grpcTrayData_To_Tray.convertToTray(trayStub.registerTray(Tray_ToGrpc_TrayData.convertToTrayData(tray1, maxNestingDepth)),maxNestingDepth);
    tray2 = grpcTrayData_To_Tray.convertToTray(trayStub.registerTray(Tray_ToGrpc_TrayData.convertToTrayData(tray2, maxNestingDepth)),maxNestingDepth);

    // Create AnimalParts:
    AnimalPart animalPart1 = new AnimalPart(1L, BigDecimal.valueOf(3.4), partType1, animal1, tray1, null);
    AnimalPart animalPart2 = new AnimalPart(1L, BigDecimal.valueOf(2.7), partType2, animal2, tray2, null);
    AnimalPart animalPart3 = new AnimalPart(1L, BigDecimal.valueOf(5.12), partType1, animal1, tray1, null);

    animalPart1 = grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart1)), maxNestingDepth);
    animalPart2 = grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart2)), maxNestingDepth);
    animalPart3 = grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart3)), maxNestingDepth);

    // Create Products:
    List<Long> contentIdList = new ArrayList<>();
    contentIdList.add(animalPart1.getPart_id());
    List<Long> trayIdList = new ArrayList<>();
    trayIdList.add(tray1.getTrayId());
    Product product1 = new Product(1L, contentIdList, trayIdList);
    product1 = grpcProductData_To_Product.convertToProduct(productStub.registerProduct(Product_ToGrpc_ProductData.convertToProductData(product1, maxNestingDepth)),maxNestingDepth);

    for (Long animalPartId : product1.getAnimalPartIdList())
      product1.addAnimalPart(grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId)), maxNestingDepth));

    contentIdList.clear();
    contentIdList.add(animalPart2.getPart_id());
    trayIdList.clear();
    trayIdList.add(tray2.getTrayId());
    Product product2 = new Product(1L, contentIdList, trayIdList);
    product2 = grpcProductData_To_Product.convertToProduct(productStub.registerProduct(Product_ToGrpc_ProductData.convertToProductData(product2, maxNestingDepth)),maxNestingDepth);

    for (Long animalPartId : product2.getAnimalPartIdList())
      product2.addAnimalPart(grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId)), maxNestingDepth));


    // Act: Simulate the process that is performed when this command is selected from main:
    // Simulate user prompt to select a Product_id to get info for:
    long animalId = animal1.getId();

    Animal animalReceived;
    animalReceived = animalRegistrationSystem.readAnimal(animalId);

    for (Long animalPartId : animalReceived.getAnimalPartIdList())
      animalReceived.addAnimalPart(grpcAnimalPartData_To_Animal.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId)), maxNestingDepth));

    // Retrieve a List of all AnimalPart_ids involved in this Animal:
    List<AnimalPart> animalParts = animalPartRegistrationSystem.readAnimalPartsByAnimalId(animalId);

    // Retrieve a List of all Products involved in each AnimalPart:
    List<Long> productIdsInvolved = new ArrayList<>();
    for (AnimalPart animalPart : animalParts)
      if(animalPart.getProduct().getProductId() > 0)
        productIdsInvolved.add(animalPart.getAnimal().getId());


    // Assert:
    // Verify if gRPC response is correct and if the database is updated:
    assertNotNull(animalReceived); // Ensure that the Product looked up, actually exists in DB
    assertNotNull(animalParts); // Ensure that the received AnimalPart list is not null
    assertEquals(1L, animalReceived.getId());
    assertEquals(1L, productIdsInvolved.get(0));
    assertEquals(1, productIdsInvolved.size());
  }

}
