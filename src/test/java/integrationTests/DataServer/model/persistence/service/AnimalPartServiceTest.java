package integrationTests.DataServer.model.persistence.service;

import integrationTests.TestDataSourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import DataServer.DataServerApplication;
import DataServer.model.persistence.entities.*;
import DataServer.model.persistence.service.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

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
public class AnimalPartServiceTest
{
  private final AnimalPartRegistryInterface animalPartService;
  private final AnimalRegistryInterface animalService;
  private final PartTypeRegistryInterface partTypeService;
  private final TrayRegistryInterface trayService;
  private final ProductRegistryInterface productService;
  private AutoCloseable closeable;

  // Registered data in test DB, prior to any AnimalParts being added:
  private Animal animal1 = null;
  private Animal animal2 = null;
  private PartType partType1 = null;
  private PartType partType2 = null;
  private Tray tray1 = null;
  private Tray tray2 = null;

  @Autowired
  public AnimalPartServiceTest(AnimalPartRegistryInterface animalPartService,
      AnimalRegistryInterface animalService,
      PartTypeRegistryInterface partTypeService,
      TrayRegistryInterface trayService,
      ProductRegistryInterface productService) {

    this.animalPartService = animalPartService;
    this.animalService = animalService;
    this.partTypeService = partTypeService;
    this.trayService = trayService;
    this.productService = productService;
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
      animal1 = animalService.registerAnimal(new Animal(1, weight1, origin1, arrivalDate1));
      animal2 = animalService.registerAnimal(new Animal(1, weight2, origin2, arrivalDate2));
      partType1 = partTypeService.registerPartType(new PartType(1L, typeDesc1));
      partType2 = partTypeService.registerPartType(new PartType(1L, typeDesc2));
      tray1 = trayService.registerTray(new Tray(1, maxCapacityTray1));
      tray2 = trayService.registerTray(new Tray(1, maxCapacityTray2));

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


  @Test
  public void whenRegisterNewAnimalPart_WithValidAnimalPartDataAndNullProduct_ReturnsRegisteredAnimal() {
    // Arrange:
    Animal parentAnimal = animal1.copy();
    PartType parentPartType = partType1.copy();
    Tray parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("7.42");
    AnimalPart createdAnimalPart = null;

    // Act:
    try {
      createdAnimalPart = animalPartService.registerAnimalPart(new AnimalPart(1, weight, parentPartType, parentAnimal, parentTray, null));

      // Read the most recent Animal, PartType and Tray, since AnimalPart was now assigned.
      createdAnimalPart = animalPartService.readAnimalPart(createdAnimalPart.getPartId());
      parentAnimal = animalService.readAnimal(parentAnimal.getId());
      parentTray = trayService.readTray(parentTray.getTrayId());
      parentPartType = partTypeService.readPartType(parentPartType.getTypeId());
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
    assertEquals(parentAnimal.getId(), createdAnimalPart.getAnimal().getId());
    assertEquals(parentAnimal.getAnimalPartIdList(), createdAnimalPart.getAnimal().getAnimalPartIdList());
    assertEquals(parentAnimal.getOrigin(), createdAnimalPart.getAnimal().getOrigin());
    assertEquals(Timestamp.from(parentAnimal.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)), Timestamp.from(createdAnimalPart.getAnimal().getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(parentAnimal.getWeight_kilogram(), createdAnimalPart.getAnimal().getWeight_kilogram());

    // Assert embedded/associated PartType:
    assertEquals(parentPartType.getTypeId(), createdAnimalPart.getType().getTypeId());
    assertEquals(parentPartType.getTypeDesc(), createdAnimalPart.getType().getTypeDesc());
    assertEquals(parentPartType.getAnimalPartIdList(), createdAnimalPart.getType().getAnimalPartIdList());

    // Assert embedded/associated Tray:
    assertEquals(parentTray.getTrayId(), createdAnimalPart.getTray().getTrayId());
    assertEquals(parentTray.getMaxWeight_kilogram(), createdAnimalPart.getTray().getMaxWeight_kilogram());
    assertEquals(parentTray.getWeight_kilogram(), createdAnimalPart.getTray().getWeight_kilogram());
    assertEquals(parentTray.getAnimalPartIdList(), createdAnimalPart.getTray().getAnimalPartIdList());
    assertEquals(parentTray.getTransferIdList(), createdAnimalPart.getTray().getTransferIdList());
    assertEquals(parentTray.getTrayType().getTypeId(), createdAnimalPart.getTray().getTrayType().getTypeId());
    assertEquals(parentTray.getTrayType().getTypeDesc(), createdAnimalPart.getTray().getTrayType().getTypeDesc());
    assertEquals(parentTray.getTrayType().getAnimalPartIdList(), createdAnimalPart.getTray().getTrayType().getAnimalPartIdList());
  }


  @Test
  public void whenRegisterNewAnimalPart_WithValidAnimalPartDataAndValidProduct_ReturnsRegisteredAnimal() {
    // Arrange:
    Animal parentAnimal = animal1.copy();
    PartType parentPartType = partType1.copy();
    Tray parentTray = tray1.copy();
    BigDecimal weight = new BigDecimal("7.42");
    AnimalPart createdAnimalPart = null;

    Product product = null;

    // Act:
    try {
      // Register without product:
      createdAnimalPart = animalPartService.registerAnimalPart(new AnimalPart(1, weight, parentPartType, parentAnimal, parentTray, null));

      // Assign to a product:
      ArrayList<AnimalPart> listAnimalPart = new ArrayList<>();
      listAnimalPart.add(createdAnimalPart);
      ArrayList<Tray> listTray = new ArrayList<>();
      listTray.add(parentTray);

      // Register AnimalPart with Product
      product = productService.registerProduct(new Product(1, listAnimalPart, listTray));

      // Read the most recent Animal, Tray and AnimalParts, since product was now assigned.
      createdAnimalPart = animalPartService.readAnimalPart(createdAnimalPart.getPartId());
      parentAnimal = animalService.readAnimal(parentAnimal.getId());
      parentTray = trayService.readTray(parentTray.getTrayId());
      parentPartType = partTypeService.readPartType(parentPartType.getTypeId());
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
    assertEquals(parentAnimal.getId(), createdAnimalPart.getAnimal().getId());
    assertEquals(parentAnimal.getAnimalPartIdList(), createdAnimalPart.getAnimal().getAnimalPartIdList());
    assertEquals(parentAnimal.getOrigin(), createdAnimalPart.getAnimal().getOrigin());
    assertEquals(Timestamp.from(parentAnimal.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)), Timestamp.from(createdAnimalPart.getAnimal().getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(parentAnimal.getWeight_kilogram(), createdAnimalPart.getAnimal().getWeight_kilogram());

    // Assert embedded/associated PartType:
    assertEquals(parentPartType.getTypeId(), createdAnimalPart.getType().getTypeId());
    assertEquals(parentPartType.getTypeDesc(), createdAnimalPart.getType().getTypeDesc());
    assertEquals(parentPartType.getAnimalPartIdList(), createdAnimalPart.getType().getAnimalPartIdList());

    // Assert embedded/associated Tray:
    assertEquals(parentTray.getTrayId(), createdAnimalPart.getTray().getTrayId());
    assertEquals(parentTray.getMaxWeight_kilogram(), createdAnimalPart.getTray().getMaxWeight_kilogram());
    assertEquals(parentTray.getWeight_kilogram(), createdAnimalPart.getTray().getWeight_kilogram());
    assertEquals(parentTray.getAnimalPartIdList(), createdAnimalPart.getTray().getAnimalPartIdList());
    assertEquals(parentTray.getTransferIdList(), createdAnimalPart.getTray().getTransferIdList());
    assertEquals(parentTray.getTrayType().getTypeId(), createdAnimalPart.getTray().getTrayType().getTypeId());
    assertEquals(parentTray.getTrayType().getTypeDesc(), createdAnimalPart.getTray().getTrayType().getTypeDesc());
    assertEquals(parentTray.getTrayType().getAnimalPartIdList(), createdAnimalPart.getTray().getTrayType().getAnimalPartIdList());

    // Assert embedded/associated Product:
    assertEquals(product.getProductId(), createdAnimalPart.getProduct().getProductId());
    assertEquals(product.getTransferIdList(), createdAnimalPart.getProduct().getTransferIdList());
    assertEquals(product.getAnimalPartIdList(), createdAnimalPart.getProduct().getAnimalPartIdList());
  }
}
