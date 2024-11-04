package integrationTests;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import server.GrpcServer;
import server.controller.grpc.grpc_to_java.*;
import server.controller.grpc.java_to_gRPC.*;
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
        GrpcServer.class,
        TestDataSourceConfig.class}) // Signals to Spring Boot that this is a Spring Boot Test and defines which spring configs to use!
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Ensures that Mocks are reset after each test, to avoid tests modifying data in shared mocks, that could cause tests to influence each other.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Establishes an InMemory database instead of using the actual Postgresql database, so tests do not disrupt the production database.
public class RecallMachineTest
{
  // Signals that these are "fake" Spring Boot beans.
  /*private AnimalPartRepository animalPartRepository;
  private AnimalRepository animalRepository; // Signals that this is a "fake" Spring Boot bean.
  private PartTypeRepository partTypeRepository; // Signals that this is a "fake" Spring Boot bean.
  private ProductRepository productRepository; // Signals that this is a "fake" Spring Boot bean.
  private TrayRepository trayRepository; // Signals that this is a "fake" Spring Boot bean.
  private TrayToProductTransferRepository trayToProductTransferRepository; // Signals that this is a "fake" Spring Boot bean.*/

  private ManagedChannel channel;
  private AnimalPartServiceGrpc.AnimalPartServiceBlockingStub animalPartStub;
  private AnimalServiceGrpc.AnimalServiceBlockingStub animalStub;
  private PartTypeServiceGrpc.PartTypeServiceBlockingStub partTypeStub;
  private ProductServiceGrpc.ProductServiceBlockingStub productStub;
  private TrayServiceGrpc.TrayServiceBlockingStub trayStub;
  private ProductRegistrationSystemImpl productRegistrationSystem;
  private AnimalPartRegistrationSystemImpl animalPartRegistrationSystem;
  private AnimalRegistrationSystemImpl animalRegistrationSystem;

  @BeforeEach
  public void setUp() {
    // Start up a gRPC client channel:
    channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build();

    animalPartStub = AnimalPartServiceGrpc.newBlockingStub(channel);
    animalStub = AnimalServiceGrpc.newBlockingStub(channel);
    partTypeStub = PartTypeServiceGrpc.newBlockingStub(channel);
    productStub = ProductServiceGrpc.newBlockingStub(channel);
    trayStub = TrayServiceGrpc.newBlockingStub(channel);
    productRegistrationSystem = new ProductRegistrationSystemImpl("localhost", 9090);
    animalPartRegistrationSystem = new AnimalPartRegistrationSystemImpl("localhost", 9090);
    animalRegistrationSystem = new AnimalRegistrationSystemImpl("localhost", 9090);


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

    animal1 = GrpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal1)));
    //System.out.println(animal1);
    animal2 = GrpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal2)));
    animal3 = GrpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal3)));

    // Create PartTypes:
    PartType partType1 = new PartType(1L, "type1");
    PartType partType2 = new PartType(1L, "type2");

    partType1 = GrpcPartTypeData_To_PartType.convertToPartType(partTypeStub.registerPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType1)));
    partType2 = GrpcPartTypeData_To_PartType.convertToPartType(partTypeStub.registerPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType2)));

    // Create Trays:
    Tray tray1 = new Tray(1L, BigDecimal.valueOf(25), BigDecimal.valueOf(0), new ArrayList<>(), new ArrayList<>());
    Tray tray2 = new Tray(1L, BigDecimal.valueOf(25), BigDecimal.valueOf(0), new ArrayList<>(), new ArrayList<>());

    tray1 = GrpcTrayData_To_Tray.convertToTray(trayStub.registerTray(Tray_ToGrpc_TrayData.convertToTrayData(tray1, 3)),3);
    tray2 = GrpcTrayData_To_Tray.convertToTray(trayStub.registerTray(Tray_ToGrpc_TrayData.convertToTrayData(tray2, 3)),3);

    // Create AnimalParts:
    AnimalPart animalPart1 = new AnimalPart(1L, BigDecimal.valueOf(3.4), partType1, animal1, tray1, null);
    AnimalPart animalPart2 = new AnimalPart(1L, BigDecimal.valueOf(2.7), partType2, animal2, tray2, null);
    AnimalPart animalPart3 = new AnimalPart(1L, BigDecimal.valueOf(5.12), partType1, animal1, tray1, null);

    animalPart1 = GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart1)));
    animalPart2 = GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart2)));
    animalPart3 = GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart3)));

    // Create Products:
    List<Long> contentIdList = new ArrayList<>();
    contentIdList.add(animalPart1.getPart_id());
    List<Long> trayIdList = new ArrayList<>();
    trayIdList.add(tray1.getTrayId());
    Product product1 = new Product(1L, contentIdList, trayIdList);
    product1 = GrpcProductData_To_Product.convertToProduct(productStub.registerProduct(Product_ToGrpc_ProductData.convertToProductData(product1, 3)),3);

    for (Long animalPartId : product1.getAnimalPartIdList())
      product1.addAnimalPart(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId))));

    contentIdList.clear();
    contentIdList.add(animalPart2.getPart_id());
    trayIdList.clear();
    trayIdList.add(tray2.getTrayId());
    Product product2 = new Product(1L, contentIdList, trayIdList);
    product2 = GrpcProductData_To_Product.convertToProduct(productStub.registerProduct(Product_ToGrpc_ProductData.convertToProductData(product2, 3)),3);

    for (Long animalPartId : product2.getAnimalPartIdList())
      product2.addAnimalPart(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId))));


    // Act: Simulate the process that is performed when this command is selected from main:
    // Simulate user prompt to select a Product_id to get info for:
    long productId = product1.getProductId();

    Product productReceived = null;
    productReceived = productRegistrationSystem.readProduct(productId);

    for (Long animalPartId : productReceived.getAnimalPartIdList())
      productReceived.addAnimalPart(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId))));

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

    animal1 = GrpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal1)));
    System.out.println(animal1);
    animal2 = GrpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal2)));
    animal3 = GrpcAnimalData_To_Animal.convertToAnimal(animalStub.registerAnimal(Animal_ToGrpc_AnimalData.convertToAnimalData(animal3)));

    // Create PartTypes:
    PartType partType1 = new PartType(1L, "type1");
    PartType partType2 = new PartType(1L, "type2");

    partType1 = GrpcPartTypeData_To_PartType.convertToPartType(partTypeStub.registerPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType1)));
    partType2 = GrpcPartTypeData_To_PartType.convertToPartType(partTypeStub.registerPartType(PartType_ToGrpc_PartTypeData.convertToPartTypeData(partType2)));

    // Create Trays:
    Tray tray1 = new Tray(1L, BigDecimal.valueOf(25), BigDecimal.valueOf(0), new ArrayList<>(), new ArrayList<>());
    Tray tray2 = new Tray(1L, BigDecimal.valueOf(25), BigDecimal.valueOf(0), new ArrayList<>(), new ArrayList<>());

    tray1 = GrpcTrayData_To_Tray.convertToTray(trayStub.registerTray(Tray_ToGrpc_TrayData.convertToTrayData(tray1, 3)),3);
    tray2 = GrpcTrayData_To_Tray.convertToTray(trayStub.registerTray(Tray_ToGrpc_TrayData.convertToTrayData(tray2, 3)),3);

    // Create AnimalParts:
    AnimalPart animalPart1 = new AnimalPart(1L, BigDecimal.valueOf(3.4), partType1, animal1, tray1, null);
    AnimalPart animalPart2 = new AnimalPart(1L, BigDecimal.valueOf(2.7), partType2, animal2, tray2, null);
    AnimalPart animalPart3 = new AnimalPart(1L, BigDecimal.valueOf(5.12), partType1, animal1, tray1, null);

    animalPart1 = GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart1)));
    animalPart2 = GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart2)));
    animalPart3 = GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.registerAnimalPart(AnimalPart_ToGrpc_AnimalPartData.convertToAnimalPartData(animalPart3)));

    // Create Products:
    List<Long> contentIdList = new ArrayList<>();
    contentIdList.add(animalPart1.getPart_id());
    List<Long> trayIdList = new ArrayList<>();
    trayIdList.add(tray1.getTrayId());
    Product product1 = new Product(1L, contentIdList, trayIdList);
    product1 = GrpcProductData_To_Product.convertToProduct(productStub.registerProduct(Product_ToGrpc_ProductData.convertToProductData(product1, 3)),3);

    for (Long animalPartId : product1.getAnimalPartIdList())
      product1.addAnimalPart(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId))));

    contentIdList.clear();
    contentIdList.add(animalPart2.getPart_id());
    trayIdList.clear();
    trayIdList.add(tray2.getTrayId());
    Product product2 = new Product(1L, contentIdList, trayIdList);
    product2 = GrpcProductData_To_Product.convertToProduct(productStub.registerProduct(Product_ToGrpc_ProductData.convertToProductData(product2, 3)),3);

    for (Long animalPartId : product2.getAnimalPartIdList())
      product2.addAnimalPart(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId))));


    // Act: Simulate the process that is performed when this command is selected from main:
    // Simulate user prompt to select a Product_id to get info for:
    long animalId = animal1.getId();

    Animal animalReceived = null;
    animalReceived = animalRegistrationSystem.readAnimal(animalId);

    for (Long animalPartId : animalReceived.getAnimalPartIdList())
      animalReceived.addAnimalPart(GrpcAnimalPartData_To_AnimalPart.convertToAnimalPart(animalPartStub.readAnimalPart(LongId_ToGrpc_Id.convertToAnimalPartId(animalPartId))));

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
