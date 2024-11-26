package integrationTests.client.ui.model.service;

import client.interfaces.*;
import client.ui.Model.service.*;
import integrationTests.TestDataSourceConfig;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import server.ServerApplication;
import shared.model.dto.*;
import shared.model.exceptions.persistance.NotFoundException;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
        ServerApplication.class,
        TestDataSourceConfig.class})
@ComponentScan(basePackages = "client")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AnimalPartRegistrationSystemTest
{
  private static ManagedChannel channel;
  private static ProductRegistrationSystem productRegistrationSystem;
  private static AnimalPartRegistrationSystem animalPartRegistrationSystem;
  private static AnimalRegistrationSystem animalRegistrationSystem;
  private static TrayRegistrationSystem trayRegistrationSystem;
  private static PartTypeRegistrationSystem partTypeRegistrationSystem;
  private AutoCloseable closeable;

  // Registered data in test DB, prior to any AnimalParts being added:
  private AnimalDto animal1 = null;
  private AnimalDto animal2 = null;
  private PartTypeDto partType1 = null;
  private PartTypeDto partType2 = null;
  private TrayDto tray1 = null;
  private TrayDto tray2 = null;

  @BeforeAll
  public static void initialize(){
    // Start up a gRPC client channel:
    channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build();

    animalPartRegistrationSystem = new AnimalPartRegistrationSystemImpl("localhost", 9090);
    productRegistrationSystem = new ProductRegistrationSystemImpl("localhost", 9090);
    animalRegistrationSystem = new AnimalRegistrationSystemImpl("localhost", 9090);
    trayRegistrationSystem = new TrayRegistrationSystemImpl("localhost", 9090);
    partTypeRegistrationSystem = new PartTypeRegistrationSystemImpl("localhost", 9090);
  }

  @BeforeEach
  public void setUp() {
    // Initialize all the @Mock and @InjectMock fields, allowing Spring Boot time to perform its Dependency Injection.
    closeable = MockitoAnnotations.openMocks(this);

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
    BigDecimal maxCapacityTray2 = new BigDecimal("25.00");

    // Register the data:
    try {
      animal1 = animalRegistrationSystem.registerNewAnimal(weight1, origin1, arrivalDate1);
      animal2 = animalRegistrationSystem.registerNewAnimal(weight2, origin2, arrivalDate2);
      partType1 = partTypeRegistrationSystem.registerNewPartType(typeDesc1);
      partType2 = partTypeRegistrationSystem.registerNewPartType(typeDesc2);
      tray1 = trayRegistrationSystem.registerNewTray(maxCapacityTray1);
      tray2 = trayRegistrationSystem.registerNewTray(maxCapacityTray2);

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

  }

  @AfterEach
  public void tearDown() {
    animal1 = null;
    animal2 = null;
    partType1 = null;
    partType2 = null;
    tray1 = null;
    tray2 = null;

    // Close the Mockito Injections:
    try {
      closeable.close();
    } catch (Exception ignored) {}
  }

  @AfterAll
  public static void shutDown(){
    // Tear down the gRPC client channel:
    channel.shutdownNow();
    channel = null;

    productRegistrationSystem = null;
    animalPartRegistrationSystem = null;
    animalRegistrationSystem = null;
  }

  @Test
  public void whenRegisterNewAnimalPart_WithValidAnimalData_ReturnsRegisteredAnimalPart() {
    // Arrange:
    AnimalDto parentAnimal = animal1.copy();
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("7.42");
    AnimalPartDto createdAnimalPart = null;

    // Act:
    try {
      createdAnimalPart = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal, parentPartType, parentTray, weight);

      // Update the parent Animal, PartType and Tray now that the animalPart has been assigned:
      parentAnimal = animalRegistrationSystem.readAnimal(parentAnimal.getAnimalId());
      parentTray = trayRegistrationSystem.readTray(parentTray.getTrayId());
      parentPartType = partTypeRegistrationSystem.readPartType(parentPartType.getTypeId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. Exception: " + e.getCause() + ", Reason: " + e.getMessage());
    }

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
  public void whenRegisterNewAnimalPart_WithValidAnimalPartDataAndValidProduct_ReturnsRegisteredAnimalPart() {
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
      createdAnimalPart = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal, parentPartType, parentTray, weight);

      // Assign to a product:
      ArrayList<AnimalPartDto> listAnimalPart = new ArrayList<>();
      listAnimalPart.add(createdAnimalPart);
      ArrayList<TrayDto> listTray = new ArrayList<>();
      listTray.add(parentTray);

      // Register AnimalPart with Product
      product = productRegistrationSystem.registerNewProduct(listAnimalPart, listTray);

      // Read the most recent Animal, Tray, PartType and AnimalPart, since Product was now assigned.
      createdAnimalPart = animalPartRegistrationSystem.readAnimalPart(createdAnimalPart.getPartId());
      parentAnimal = animalRegistrationSystem.readAnimal(parentAnimal.getAnimalId());
      parentTray = trayRegistrationSystem.readTray(parentTray.getTrayId());
      parentPartType = partTypeRegistrationSystem.readPartType(parentPartType.getTypeId());

    } catch (Exception e) {
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


  @Test
  public void whenReadAnimalPart_WithValidAnimalPartId_ReturnsReadAnimalPart() {
    // Arrange:
    AnimalDto parentAnimal = animal1.copy();
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("7.42");
    ProductDto product = null;
    AnimalPartDto createdAnimalPart = null;
    AnimalPartDto readAnimalPart = null;


    try {
      // Register without product:
      createdAnimalPart = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal, parentPartType, parentTray, weight);

      // Assign to a product:
      ArrayList<AnimalPartDto> listAnimalPart = new ArrayList<>();
      listAnimalPart.add(createdAnimalPart);
      ArrayList<TrayDto> listTray = new ArrayList<>();
      listTray.add(parentTray);

      // Register AnimalPart with Product
      product = productRegistrationSystem.registerNewProduct(listAnimalPart, listTray);

      // Read the most recent Animal, Tray and PartType, since Product was now assigned.
      parentAnimal = animalRegistrationSystem.readAnimal(parentAnimal.getAnimalId());
      parentTray = trayRegistrationSystem.readTray(parentTray.getTrayId());
      parentPartType = partTypeRegistrationSystem.readPartType(parentPartType.getTypeId());

      // Act:
      readAnimalPart = animalPartRegistrationSystem.readAnimalPart(1);

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    // Validate the created AnimalPart:

    // Assert AnimalPart:
    assertNotNull(createdAnimalPart);
    assertEquals(1L, createdAnimalPart.getPartId());
    assertEquals(weight, readAnimalPart.getWeight_kilogram());

    // Assert embedded/associated Animal:
    assertEquals(parentAnimal.getAnimalId(), readAnimalPart.getAnimalId());

    // Assert embedded/associated PartType:
    assertEquals(parentPartType.getTypeId(), readAnimalPart.getTypeId());

    // Assert embedded/associated Tray:
    assertEquals(parentTray.getTrayId(), readAnimalPart.getTrayId());

    // Assert embedded/associated Product:
    assertEquals(product.getProductId(), readAnimalPart.getProductId());
  }


  @Test
  public void whenReadAnimalPartByAnimalId_WithValidAnimalId_ReturnsFoundAnimalPart() {
    // Arrange:
    AnimalDto parentAnimal1 = animal1.copy();
    AnimalDto parentAnimal2 = animalRegistrationSystem.registerNewAnimal(new BigDecimal("163.22"), "Test Farmstead 2", Timestamp.from(Instant.now()));
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("5.42");
    ProductDto product = null;
    AnimalPartDto createdAnimalPart1 = null;
    AnimalPartDto createdAnimalPart2 = null;
    AnimalPartDto createdAnimalPart3 = null;
    List<AnimalPartDto> readAnimalParts = new ArrayList<>();

    try {
      // Register without product:
      createdAnimalPart1 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal1, parentPartType, parentTray, weight);
      createdAnimalPart2 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal2, parentPartType, parentTray, weight);
      createdAnimalPart3 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal2, parentPartType, parentTray, weight);

      // Assign to a product:
      ArrayList<AnimalPartDto> listAnimalPart = new ArrayList<>();
      listAnimalPart.add(createdAnimalPart1);
      listAnimalPart.add(createdAnimalPart2);
      listAnimalPart.add(createdAnimalPart3);
      ArrayList<TrayDto> listTray = new ArrayList<>();
      listTray.add(parentTray);

      // Register AnimalPart with Product
      product = productRegistrationSystem.registerNewProduct(listAnimalPart, listTray);

      // Read the most recent Animal, Tray and PartType, since Product was now assigned.
      parentAnimal1 = animalRegistrationSystem.readAnimal(parentAnimal1.getAnimalId());
      parentAnimal2 = animalRegistrationSystem.readAnimal(parentAnimal2.getAnimalId());
      parentTray = trayRegistrationSystem.readTray(parentTray.getTrayId());
      parentPartType = partTypeRegistrationSystem.readPartType(parentPartType.getTypeId());

      // Act:
      readAnimalParts = animalPartRegistrationSystem.readAnimalPartsByAnimalId(parentAnimal2.getAnimalId());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    // Assert AnimalPart:
    assertNotNull(readAnimalParts);
    assertEquals(2, readAnimalParts.size());

    // Compare the id's of each found AnimalPart:
    assertEquals(createdAnimalPart2.getPartId(), readAnimalParts.get(0).getPartId());
    assertEquals(createdAnimalPart3.getPartId(), readAnimalParts.get(1).getPartId());
  }


  @Test
  public void whenReadAnimalPartByPartType_WithValidPartTypeId_ReturnsFoundAnimalPart() {
    // Arrange:
    AnimalDto parentAnimal1 = animal1.copy();
    AnimalDto parentAnimal2 = animalRegistrationSystem.registerNewAnimal(new BigDecimal("163.22"), "Test Farmstead 2", Timestamp.from(Instant.now()));
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("5.42");
    ProductDto product = null;
    AnimalPartDto createdAnimalPart1 = null;
    AnimalPartDto createdAnimalPart2 = null;
    AnimalPartDto createdAnimalPart3 = null;
    List<AnimalPartDto> readAnimalParts = new ArrayList<>();

    try {
      // Register without product:
      createdAnimalPart1 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal1, parentPartType, parentTray, weight);
      createdAnimalPart2 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal2, parentPartType, parentTray, weight);
      createdAnimalPart3 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal2, parentPartType, parentTray, weight);

      // Assign to a product:
      ArrayList<AnimalPartDto> listAnimalPart = new ArrayList<>();
      listAnimalPart.add(createdAnimalPart1);
      listAnimalPart.add(createdAnimalPart2);
      listAnimalPart.add(createdAnimalPart3);
      ArrayList<TrayDto> listTray = new ArrayList<>();
      listTray.add(parentTray);

      // Register AnimalPart with Product
      product = productRegistrationSystem.registerNewProduct(listAnimalPart, listTray);

      // Read the most recent Animal, Tray and PartType, since Product was now assigned.
      parentAnimal1 = animalRegistrationSystem.readAnimal(parentAnimal1.getAnimalId());
      parentAnimal2 = animalRegistrationSystem.readAnimal(parentAnimal2.getAnimalId());
      parentTray = trayRegistrationSystem.readTray(parentTray.getTrayId());
      parentPartType = partTypeRegistrationSystem.readPartType(parentPartType.getTypeId());

      // Act:
      readAnimalParts = animalPartRegistrationSystem.readAnimalPartsByPartTypeId(parentPartType.getTypeId());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    // Assert AnimalPart:
    assertNotNull(readAnimalParts);
    assertEquals(3, readAnimalParts.size());

    // Compare the id's of each found AnimalPart:
    assertEquals(createdAnimalPart1.getPartId(), readAnimalParts.get(0).getPartId());
    assertEquals(createdAnimalPart2.getPartId(), readAnimalParts.get(1).getPartId());
    assertEquals(createdAnimalPart3.getPartId(), readAnimalParts.get(2).getPartId());
  }


  @Test
  public void whenReadAnimalPartByProductId_WithValidProductId_ReturnsFoundAnimalPart() {
    // Arrange:
    AnimalDto parentAnimal1 = animal1.copy();
    AnimalDto parentAnimal2 = animalRegistrationSystem.registerNewAnimal(new BigDecimal("163.22"), "Test Farmstead 2", Timestamp.from(Instant.now()));
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("5.42");
    ProductDto product = null;
    AnimalPartDto createdAnimalPart1 = null;
    AnimalPartDto createdAnimalPart2 = null;
    AnimalPartDto createdAnimalPart3 = null;
    List<AnimalPartDto> readAnimalParts = new ArrayList<>();

    try {
      // Register without product:
      createdAnimalPart1 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal1, parentPartType, parentTray, weight);
      createdAnimalPart2 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal2, parentPartType, parentTray, weight);
      createdAnimalPart3 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal2, parentPartType, parentTray, weight);

      // Assign to a product:
      ArrayList<AnimalPartDto> listAnimalPart = new ArrayList<>();
      listAnimalPart.add(createdAnimalPart1);
      listAnimalPart.add(createdAnimalPart2);
      listAnimalPart.add(createdAnimalPart3);
      ArrayList<TrayDto> listTray = new ArrayList<>();
      listTray.add(parentTray);

      // Register AnimalPart with Product
      product = productRegistrationSystem.registerNewProduct(listAnimalPart, listTray);

      // Read the most recent Animal, Tray and PartType, since Product was now assigned.
      parentAnimal1 = animalRegistrationSystem.readAnimal(parentAnimal1.getAnimalId());
      parentAnimal2 = animalRegistrationSystem.readAnimal(parentAnimal2.getAnimalId());
      parentTray = trayRegistrationSystem.readTray(parentTray.getTrayId());
      parentPartType = partTypeRegistrationSystem.readPartType(parentPartType.getTypeId());

      // Act:
      readAnimalParts = animalPartRegistrationSystem.readAnimalPartsByProductId(product.getProductId());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    // Assert AnimalPart:
    assertNotNull(readAnimalParts);
    assertEquals(3, readAnimalParts.size());

    // Compare the id's of each found AnimalPart:
    assertEquals(createdAnimalPart1.getPartId(), readAnimalParts.get(0).getPartId());
    assertEquals(createdAnimalPart2.getPartId(), readAnimalParts.get(1).getPartId());
    assertEquals(createdAnimalPart3.getPartId(), readAnimalParts.get(2).getPartId());
  }


  @Test
  public void whenReadAnimalPartByTrayId_WithValidTrayId_ReturnsFoundAnimalPart() {
    // Arrange:
    AnimalDto parentAnimal1 = animal1.copy();
    AnimalDto parentAnimal2 = animalRegistrationSystem.registerNewAnimal(new BigDecimal("163.22"), "Test Farmstead 2", Timestamp.from(Instant.now()));
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("5.42");
    ProductDto product = null;
    AnimalPartDto createdAnimalPart1 = null;
    AnimalPartDto createdAnimalPart2 = null;
    AnimalPartDto createdAnimalPart3 = null;
    List<AnimalPartDto> readAnimalParts = new ArrayList<>();

    try {
      // Register without product:
      createdAnimalPart1 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal1, parentPartType, parentTray, weight);
      createdAnimalPart2 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal2, parentPartType, parentTray, weight);
      createdAnimalPart3 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal2, parentPartType, parentTray, weight);

      // Assign to a product:
      ArrayList<AnimalPartDto> listAnimalPart = new ArrayList<>();
      listAnimalPart.add(createdAnimalPart1);
      listAnimalPart.add(createdAnimalPart2);
      listAnimalPart.add(createdAnimalPart3);
      ArrayList<TrayDto> listTray = new ArrayList<>();
      listTray.add(parentTray);

      // Register AnimalPart with Product
      product = productRegistrationSystem.registerNewProduct(listAnimalPart, listTray);

      // Read the most recent Animal, Tray and PartType, since Product was now assigned.
      parentAnimal1 = animalRegistrationSystem.readAnimal(parentAnimal1.getAnimalId());
      parentAnimal2 = animalRegistrationSystem.readAnimal(parentAnimal2.getAnimalId());
      parentTray = trayRegistrationSystem.readTray(parentTray.getTrayId());
      parentPartType = partTypeRegistrationSystem.readPartType(parentPartType.getTypeId());

      // Act:
      readAnimalParts = animalPartRegistrationSystem.readAnimalPartsByTrayId(parentTray.getTrayId());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    // Assert AnimalPart:
    assertNotNull(readAnimalParts);
    assertEquals(3, readAnimalParts.size());

    // Compare the id's of each found AnimalPart:
    assertEquals(createdAnimalPart1.getPartId(), readAnimalParts.get(0).getPartId());
    assertEquals(createdAnimalPart2.getPartId(), readAnimalParts.get(1).getPartId());
    assertEquals(createdAnimalPart3.getPartId(), readAnimalParts.get(2).getPartId());
  }


  @Test
  public void whenUpdateAnimalPart_WithValidAnimalPartDataAndValidProduct_ReturnsVoid() {
    // Arrange:
    AnimalDto parentAnimal = animal1.copy();
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("7.42");
    ProductDto product = null;
    AnimalPartDto createdAnimalPart = null;
    AnimalPartDto updatedAnimalPart = null;
    AnimalDto newAnimal = null;
    PartTypeDto newPartType = null;
    TrayDto newTray = null;

    // Register without product:
    createdAnimalPart = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal, parentPartType, parentTray, weight);

    // Assign to a product:
    ArrayList<AnimalPartDto> listAnimalPart = new ArrayList<>();
    listAnimalPart.add(createdAnimalPart);
    ArrayList<TrayDto> listTray = new ArrayList<>();
    listTray.add(parentTray);

    try {
      // Register AnimalPart with Product
      product = productRegistrationSystem.registerNewProduct(listAnimalPart, listTray);

      // Read the most recent Animal, Tray, PartType and AnimalPart, since Product was now assigned.
      createdAnimalPart = animalPartRegistrationSystem.readAnimalPart(createdAnimalPart.getPartId());
      parentAnimal = animalRegistrationSystem.readAnimal(parentAnimal.getAnimalId());
      parentTray = trayRegistrationSystem.readTray(parentTray.getTrayId());
      parentPartType = partTypeRegistrationSystem.readPartType(parentPartType.getTypeId());

      // Define updated values:
      newAnimal = animalRegistrationSystem.registerNewAnimal(new BigDecimal("212.25"), "Test Farmstead", Timestamp.from(Instant.now()));
      newPartType = partTypeRegistrationSystem.registerNewPartType("Cow Bladder");
      newTray = trayRegistrationSystem.registerNewTray(new BigDecimal("25.00"));
      BigDecimal newWeight = new BigDecimal("2.50");


      // Act:
      createdAnimalPart.setAnimalId(newAnimal.getAnimalId());
      createdAnimalPart.setTypeId(newPartType.getTypeId());
      createdAnimalPart.setTrayId(newTray.getTrayId());
      createdAnimalPart.setWeight_kilogram(newWeight);
      animalPartRegistrationSystem.updateAnimalPart(createdAnimalPart);

      // Read updated entities for proper assertion:
      updatedAnimalPart = animalPartRegistrationSystem.readAnimalPart(createdAnimalPart.getPartId());
      newAnimal = animalRegistrationSystem.readAnimal(newAnimal.getAnimalId());
      newPartType = partTypeRegistrationSystem.readPartType(newPartType.getTypeId());
      newTray = trayRegistrationSystem.readTray(newTray.getTrayId());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    // Validate the created AnimalPart:

    // Assert AnimalPart:
    assertNotNull(updatedAnimalPart);
    assertEquals(createdAnimalPart.getPartId(), updatedAnimalPart.getPartId());
    assertEquals(createdAnimalPart.getWeight_kilogram(), updatedAnimalPart.getWeight_kilogram());

    // Assert embedded/associated Animal:
    assertEquals(newAnimal.getAnimalId(), updatedAnimalPart.getAnimalId());

    // Assert embedded/associated PartType:
    assertEquals(newPartType.getTypeId(), updatedAnimalPart.getTypeId());

    // Assert embedded/associated Tray:
    assertEquals(newTray.getTrayId(), updatedAnimalPart.getTrayId());

    // Assert embedded/associated Product:
    assertEquals(product.getProductId(), updatedAnimalPart.getProductId());
  }


  @Test
  public void whenRemoveAnimalPart_WithValidAnimalPartData_ReturnsTrue() {
    // Arrange:
    AnimalDto parentAnimal = animal1.copy();
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("5.42");
    ProductDto product = null;
    AnimalPartDto createdAnimalPart = null;
    boolean removalSuccessful = false;

    try {
      // Register without product:
      createdAnimalPart = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal, parentPartType, parentTray, weight);

      // Assign to a product:
      ArrayList<AnimalPartDto> listAnimalPart = new ArrayList<>();
      listAnimalPart.add(createdAnimalPart);
      ArrayList<TrayDto> listTray = new ArrayList<>();
      listTray.add(parentTray);

      // Register AnimalPart with Product
      product = productRegistrationSystem.registerNewProduct(listAnimalPart, listTray);

      // Read the most recent Animal, Tray and PartType, since Product was now assigned.
      parentAnimal = animalRegistrationSystem.readAnimal(parentAnimal.getAnimalId());
      parentTray = trayRegistrationSystem.readTray(parentTray.getTrayId());
      parentPartType = partTypeRegistrationSystem.readPartType(parentPartType.getTypeId());
      createdAnimalPart = animalPartRegistrationSystem.readAnimalPart(createdAnimalPart.getPartId());


      // Act:
      removalSuccessful = animalPartRegistrationSystem.removeAnimalPart(createdAnimalPart);

      // Check that AnimalPart was actually removed:
      try {
        createdAnimalPart = animalPartRegistrationSystem.readAnimalPart(createdAnimalPart.getPartId());

        // If we reach this line, removal was unsuccessful. We expect the NotFoundException to be cast.
        removalSuccessful = false;
      } catch (NotFoundException ignored) {}

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertTrue(removalSuccessful);
  }


  @Test
  public void whenGetAllAnimalParts_ReturnsAllAnimalParts() {
    // Arrange:
    AnimalDto parentAnimal1 = animal1.copy();
    AnimalDto parentAnimal2 = animalRegistrationSystem.registerNewAnimal(new BigDecimal("163.22"), "Test Farmstead 2", Timestamp.from(Instant.now()));
    PartTypeDto parentPartType = partType1.copy();
    TrayDto parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("5.42");
    ProductDto product = null;
    AnimalPartDto createdAnimalPart1 = null;
    AnimalPartDto createdAnimalPart2 = null;
    AnimalPartDto createdAnimalPart3 = null;
    List<AnimalPartDto> readAnimalParts = new ArrayList<>();

    try {
      // Register without product:
      createdAnimalPart1 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal1, parentPartType, parentTray, weight);
      createdAnimalPart2 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal2, parentPartType, parentTray, weight);
      createdAnimalPart3 = animalPartRegistrationSystem.registerNewAnimalPart(parentAnimal2, parentPartType, parentTray, weight);

      // Assign to a product:
      ArrayList<AnimalPartDto> listAnimalPart = new ArrayList<>();
      listAnimalPart.add(createdAnimalPart1);
      listAnimalPart.add(createdAnimalPart2);
      listAnimalPart.add(createdAnimalPart3);
      ArrayList<TrayDto> listTray = new ArrayList<>();
      listTray.add(parentTray);

      // Register AnimalPart with Product
      product = productRegistrationSystem.registerNewProduct(listAnimalPart, listTray);

      // Read the most recent Animal, Tray and PartType, since Product was now assigned.
      parentAnimal1 = animalRegistrationSystem.readAnimal(parentAnimal1.getAnimalId());
      parentAnimal2 = animalRegistrationSystem.readAnimal(parentAnimal2.getAnimalId());
      parentTray = trayRegistrationSystem.readTray(parentTray.getTrayId());
      parentPartType = partTypeRegistrationSystem.readPartType(parentPartType.getTypeId());

      // Act:
      readAnimalParts = animalPartRegistrationSystem.getAllAnimalParts();

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    // Assert AnimalPart:
    assertNotNull(readAnimalParts);
    assertEquals(3, readAnimalParts.size());

    // Compare the id's of each found AnimalPart:
    assertEquals(createdAnimalPart1.getPartId(), readAnimalParts.get(0).getPartId());
    assertEquals(createdAnimalPart2.getPartId(), readAnimalParts.get(1).getPartId());
    assertEquals(createdAnimalPart3.getPartId(), readAnimalParts.get(2).getPartId());
  }
}
