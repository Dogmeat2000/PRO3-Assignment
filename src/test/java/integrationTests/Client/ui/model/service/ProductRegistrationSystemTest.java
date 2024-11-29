package integrationTests.Client.ui.model.service;

import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationService;
import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationServiceImpl;
import Client.Station2_Dissection.network.services.gRPC.AnimalPartRegistrationService;
import Client.Station2_Dissection.network.services.gRPC.AnimalPartRegistrationServiceImpl;
import Client.Station3_Packing.network.services.gRPC.ProductRegistrationSystem;
import Client.Station3_Packing.network.services.gRPC.ProductRegistrationSystemImpl;
import Client.common.services.gRPC.PartTypeRegistrationSystem;
import Client.common.services.gRPC.PartTypeRegistrationSystemImpl;
import Client.common.services.gRPC.TrayRegistrationSystem;
import Client.common.services.gRPC.TrayRegistrationSystemImpl;
import integrationTests.TestDataSourceConfig;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import DataServer.DataServerApplication;
import shared.model.dto.*;
import shared.model.exceptions.persistance.NotFoundException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(
    classes = {
        DataServerApplication.class,
        TestDataSourceConfig.class})
@ComponentScan(basePackages = "Client")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProductRegistrationSystemTest
{

  private static ManagedChannel channel;
  private static ProductRegistrationSystem productRegistrationSystem;
  private static AnimalPartRegistrationService animalPartRegistrationService;
  private static AnimalRegistrationService animalRegistrationService;
  private static TrayRegistrationSystem trayRegistrationSystem;
  private static PartTypeRegistrationSystem partTypeRegistrationSystem;
  private AutoCloseable closeable;

  @Value("${grpc.server.address:localhost}")
  private String grpcServerAddress;

  @Value("${grpc.server.port:9090}")
  private int grpcServerPort;

  // Registered data in test DB, prior to any AnimalParts being added:
  private AnimalDto animal1 = null;
  private AnimalDto animal2 = null;
  private PartTypeDto partType1 = null;
  private PartTypeDto partType2 = null;
  private TrayDto tray1 = null;
  private TrayDto tray2 = null;
  private AnimalPartDto animalPart1 = null;
  private AnimalPartDto animalPart2 = null;
  private AnimalPartDto animalPart3 = null;

  @BeforeEach
  public void setUp() {
    // Start up a gRPC client channel:
    channel = ManagedChannelBuilder.forAddress(grpcServerAddress, grpcServerPort)
        .usePlaintext()
        .build();

    animalPartRegistrationService = new AnimalPartRegistrationServiceImpl(grpcServerAddress, grpcServerPort);
    productRegistrationSystem = new ProductRegistrationSystemImpl(grpcServerAddress, grpcServerPort);
    animalRegistrationService = new AnimalRegistrationServiceImpl(grpcServerAddress, grpcServerPort);
    trayRegistrationSystem = new TrayRegistrationSystemImpl(grpcServerAddress, grpcServerPort);
    partTypeRegistrationSystem = new PartTypeRegistrationSystemImpl(grpcServerAddress, grpcServerPort);

    // Initialize all the @Mock and @InjectMock fields, allowing Spring Boot time to perform its Dependency Injection.
    closeable = MockitoAnnotations.openMocks(this);

    // Create some basic entities in the database, to test Products against:
    // Animals
    String origin1 = "Test Farmstead";
    Date arrivalDate1 = Date.from(Instant.now());
    BigDecimal weight1 = new BigDecimal("542.41");

    String origin2 = "Test Animal Coop";
    Date arrivalDate2 = Date.from(Instant.now());
    BigDecimal weight2 = new BigDecimal("123.45");

    // Part Types
    String typeDesc1 = "Cows Foot";
    String typeDesc2 = "Pig Tail";

    // Trays
    BigDecimal maxCapacityTray1 = new BigDecimal("25.00");
    BigDecimal maxCapacityTray2 = new BigDecimal("25.00");

    // AnimalParts
    BigDecimal animalPartWeight1 = new BigDecimal("7.25");
    BigDecimal animalPartWeight2 = new BigDecimal("4.18");
    BigDecimal animalPartWeight3 = new BigDecimal("5.42");


    // Register the data:
    try {
      animal1 = animalRegistrationService.registerNewAnimal(weight1, origin1, arrivalDate1);
      animal2 = animalRegistrationService.registerNewAnimal(weight2, origin2, arrivalDate2);
      partType1 = partTypeRegistrationSystem.registerNewPartType(typeDesc1);
      partType2 = partTypeRegistrationSystem.registerNewPartType(typeDesc2);
      tray1 = trayRegistrationSystem.registerNewTray(maxCapacityTray1);
      tray2 = trayRegistrationSystem.registerNewTray(maxCapacityTray2);
      animalPart1 = animalPartRegistrationService.registerNewAnimalPart(animal1, partType1, tray1, animalPartWeight1);
      System.out.println("REACHED HERE 1");
      animalPart2 = animalPartRegistrationService.registerNewAnimalPart(animal2, partType2, tray2, animalPartWeight2);
      System.out.println("REACHED HERE 2");
      animalPart3 = animalPartRegistrationService.registerNewAnimalPart(animal1, partType2, tray2, animalPartWeight3);
      System.out.println("REACHED HERE 3");

      // Read updated Animal, PartType and Tray after registering the AnimalPart:
      animal1 = animalRegistrationService.readAnimal(animal1.getAnimalId());
      animal2 = animalRegistrationService.readAnimal(animal2.getAnimalId());
      partType1 = partTypeRegistrationSystem.readPartType(partType1.getTypeId());
      partType2 = partTypeRegistrationSystem.readPartType(partType2.getTypeId());
      tray1 = trayRegistrationSystem.readTray(tray1.getTrayId());
      tray2 = trayRegistrationSystem.readTray(tray2.getTrayId());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
  }

  @AfterEach
  public void tearDown() {
    // Tear down the gRPC client channel:
    channel.shutdownNow();
    channel = null;

    // Close the Mockito Injections:
    try {
      closeable.close();
    } catch (Exception ignored) {}
  }

  @Test
  public void whenRegisterNewProduct_WithValidProductData_ReturnsRegisteredProduct() {
    // Arrange:
    List<AnimalPartDto> animalParts = new ArrayList<>();
    animalParts.add(animalPart1);

    List<TrayDto> trays = new ArrayList<>();
    trays.add(tray1);
    ProductDto createdProduct = null;

    // Act:
    try {
      createdProduct = productRegistrationSystem.registerNewProduct(animalParts, trays);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(createdProduct);
    assertEquals(1L, createdProduct.getProductId());
    assertEquals(1, createdProduct.getAnimalPartIdList().size());
    assertEquals(animalPart1.getPartId(), createdProduct.getAnimalPartIdList().get(0));
    assertEquals(1, createdProduct.getTransferIdList().size());
    assertEquals(1L, createdProduct.getTransferIdList().get(0));
    assertEquals(1, createdProduct.getTrayIdList().size());
    assertEquals(tray1.getTrayId(), createdProduct.getTrayIdList().get(0));
  }


  @Test
  public void whenReadProduct_WithValidProductData_ReturnsReadProduct() {
    // Arrange:
    List<AnimalPartDto> animalParts = new ArrayList<>();
    animalParts.add(animalPart1);

    List<TrayDto> trays = new ArrayList<>();
    trays.add(tray1);
    ProductDto createdProduct = null;

    try {
      createdProduct = productRegistrationSystem.registerNewProduct(animalParts, trays);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    ProductDto readProduct = null;

    // Act:
    try {
      readProduct = productRegistrationSystem.readProduct(createdProduct.getProductId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(readProduct);
    assertEquals(createdProduct.getProductId(), readProduct.getProductId());
    assertEquals(createdProduct.getTrayIdList(), readProduct.getTrayIdList());
    assertEquals(createdProduct.getTransferIdList(), readProduct.getTransferIdList());
    assertEquals(createdProduct.getAnimalPartIdList(), readProduct.getAnimalPartIdList());
  }


  @Test
  public void whenUpdateProduct_WithValidProductData_ReturnsUpdatedProduct() {
    // Arrange:
    // Original Product:
    List<AnimalPartDto> animalParts = new ArrayList<>();
    animalParts.add(animalPart1);

    List<TrayDto> trays = new ArrayList<>();
    trays.add(tray1);
    ProductDto createdProduct = null;

    try {
      createdProduct = productRegistrationSystem.registerNewProduct(animalParts, trays);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Update Information:
    animalParts.add(animalPart2);
    trays.add(tray2);
    createdProduct.addAnimalPartId(animalPart2.getPartId());
    createdProduct.addTrayId(tray2.getTrayId());
    ProductDto updatedProduct = null;

    // Act:
    try {
      productRegistrationSystem.updateProduct(createdProduct);
      Thread.sleep(10); // Give it time to update
      updatedProduct = productRegistrationSystem.readProduct(createdProduct.getProductId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    System.out.println("Updated product is: " + updatedProduct);

    // Assert:
    assertNotNull(updatedProduct);
    assertEquals(1L, updatedProduct.getProductId());
    assertEquals(2, updatedProduct.getTransferIdList().size());
    assertEquals(createdProduct.getAnimalPartIdList(), updatedProduct.getAnimalPartIdList());
    assertEquals(2, updatedProduct.getTrayIdList().size());
    assertEquals(createdProduct.getTrayIdList(), updatedProduct.getTrayIdList());
  }


  @Test
  public void whenRemoveTray_WithValidTrayData_ReturnsTrue() {
    // Arrange:
    // Original Product:
    List<AnimalPartDto> animalParts = new ArrayList<>();
    animalParts.add(animalPart1);

    List<TrayDto> trays = new ArrayList<>();
    trays.add(tray1);
    ProductDto createdProduct = null;

    try {
      createdProduct = productRegistrationSystem.registerNewProduct(animalParts, trays);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Delete Information:
    boolean deletedProduct = false;

    // Act:
    try {
      deletedProduct = productRegistrationSystem.removeProduct(createdProduct.getProductId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Check that the Product cannot be found in db:
    try {
      productRegistrationSystem.readProduct(createdProduct.getProductId());
    } catch (NotFoundException e) {
      // If we reach this line, the deletion succeeded, since we expect NotFoundException to be thrown, meaning PartType was deleted!
      deletedProduct = true;
    }

    // Assert:
    assertTrue(deletedProduct);
  }


  @Test
  public void whenGetAllProducts_With2ValidProductsInDB_Returns2Products() {
    // Arrange:
    // Product1:
    List<AnimalPartDto> animalParts1 = new ArrayList<>();
    animalParts1.add(animalPart1);

    List<TrayDto> trays1 = new ArrayList<>();
    trays1.add(tray1);
    ProductDto createdProduct1 = null;

    // Product2:
    List<AnimalPartDto> animalParts2 = new ArrayList<>();
    animalParts2.add(animalPart2);
    animalParts2.add(animalPart3);

    List<TrayDto> trays2 = new ArrayList<>();
    trays2.add(tray2);
    ProductDto createdProduct2 = null;

    try {
      createdProduct1 = productRegistrationSystem.registerNewProduct(animalParts1, trays1);
      createdProduct2 = productRegistrationSystem.registerNewProduct(animalParts2, trays2);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Act:
    List<ProductDto> products = new ArrayList<>();
    try {
      products = productRegistrationSystem.getAllProducts();
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(products);
    assertEquals(2, products.size());

    // Check PartType with id1:
    assertEquals(1L, products.get(0).getProductId());
    assertEquals(createdProduct1, products.get(0));

    // Check PartType with id2:
    assertEquals(2L, products.get(1).getProductId());
    assertEquals(createdProduct2, products.get(1));
  }
}
