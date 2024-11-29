package integrationTests.Client.ui.model.service;

import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationService;
import Client.Station2_Dissection.network.services.gRPC.AnimalPartRegistrationService;
import Client.Station2_Dissection.network.services.gRPC.AnimalPartRegistrationServiceImpl;
import Client.common.services.gRPC.PartTypeRegistrationSystem;
import Client.common.services.gRPC.PartTypeRegistrationSystemImpl;
import Client.common.services.gRPC.TrayRegistrationSystem;
import Client.common.services.gRPC.TrayRegistrationSystemImpl;
import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationServiceImpl;
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
import DataServer.DataServerApplication;
import shared.model.dto.AnimalDto;
import shared.model.dto.AnimalPartDto;
import shared.model.dto.PartTypeDto;
import shared.model.dto.TrayDto;
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
public class TrayRegistrationSystemTest
{
  private ManagedChannel channel;
  private TrayRegistrationSystem trayRegistrationSystem;
  private AnimalRegistrationService animalRegistrationService;
  private PartTypeRegistrationSystem partTypeRegistrationSystem;
  private AnimalPartRegistrationService animalPartRegistrationService;
  private AutoCloseable closeable;

  @Value("${grpc.server.address:localhost}")
  private String grpcServerAddress;

  @Value("${grpc.server.port:9090}")
  private int grpcServerPort;

  @BeforeEach
  public void setUp() {
    // Start up a gRPC client channel:
    channel = ManagedChannelBuilder.forAddress(grpcServerAddress, grpcServerPort)
        .usePlaintext()
        .build();

    trayRegistrationSystem = new TrayRegistrationSystemImpl(grpcServerAddress, grpcServerPort);
    animalRegistrationService = new AnimalRegistrationServiceImpl(grpcServerAddress, grpcServerPort);
    partTypeRegistrationSystem = new PartTypeRegistrationSystemImpl(grpcServerAddress, grpcServerPort);
    animalPartRegistrationService = new AnimalPartRegistrationServiceImpl(grpcServerAddress, grpcServerPort);

    // Initialize all the @Mock and @InjectMock fields, allowing Spring Boot time to perform its Dependency Injection.
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void tearDown() {
    // Tear down the gRPC client channel:
    channel.shutdownNow();
    channel = null;
    trayRegistrationSystem = null;
    animalRegistrationService = null;
    partTypeRegistrationSystem = null;
    animalPartRegistrationService = null;

    // Close the Mockito Injections:
    try {
      closeable.close();
    } catch (Exception ignored) {}
  }

  @Test
  public void whenRegisterNewTray_WithValidTrayData_ReturnsRegisteredTray() {
    // Arrange:
    BigDecimal maxWeight = new BigDecimal("25.00");
    BigDecimal curWeight = BigDecimal.ZERO;
    TrayDto createdTray = null;

    // Act:
    try {
      createdTray = trayRegistrationSystem.registerNewTray(maxWeight);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(createdTray);
    assertEquals(1L, createdTray.getTrayId());
    assertEquals(maxWeight, createdTray.getMaxWeight_kilogram());
    assertEquals(curWeight, createdTray.getWeight_kilogram());
  }


  @Test
  public void whenReadTray_WithValidTrayData_ReturnsReadTray() {
    // Arrange:
    BigDecimal maxWeight = new BigDecimal("25.00");
    TrayDto createdTray = null;

    try {
      createdTray = trayRegistrationSystem.registerNewTray(maxWeight);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
    TrayDto readTray = null;

    // Act:
    try {
      readTray = trayRegistrationSystem.readTray(createdTray.getTrayId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(readTray);
    assertEquals(createdTray.getTrayId(), readTray.getTrayId());
    assertEquals(createdTray.getMaxWeight_kilogram(), readTray.getMaxWeight_kilogram());
    assertEquals(createdTray.getWeight_kilogram(), readTray.getWeight_kilogram());
    assertEquals(createdTray.getTrayTypeId(), readTray.getTrayTypeId());
    assertEquals(createdTray.getAnimalPartIdList(), readTray.getAnimalPartIdList());
    assertEquals(createdTray.getProductIdList(), readTray.getProductIdList());
  }


  @Test
  public void whenUpdateTray_WithValidTrayData_ReturnsUpdatedTray() {
    // Arrange:
    // Original Tray:
    BigDecimal maxWeight = new BigDecimal("25.00");
    BigDecimal curWeight = BigDecimal.ZERO;
    TrayDto createdTray = null;

    try {
      createdTray = trayRegistrationSystem.registerNewTray(maxWeight);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Update Information:
    BigDecimal newMaxWeight = new BigDecimal("30.00");

    // Add an AnimalPart, so there is some weight to test against:
    AnimalDto animal = animalRegistrationService.registerNewAnimal(new BigDecimal("212.25"), "Test Farmstead", Date.from(Instant.now()));
    PartTypeDto partType = partTypeRegistrationSystem.registerNewPartType("Cow Bladder");
    AnimalPartDto animalPart1 = animalPartRegistrationService.registerNewAnimalPart(animal, partType, createdTray, new BigDecimal("7.12"));

    createdTray.addAnimalPartId(animalPart1.getPartId());
    curWeight = animalPart1.getWeight_kilogram();
    trayRegistrationSystem.updateTray(createdTray);
    createdTray.setMaxWeight_kilogram(newMaxWeight);

    TrayDto updatedTray = null;

    // Act:
    try {
      trayRegistrationSystem.updateTray(createdTray);
      Thread.sleep(10); // Give it time to update
      updatedTray = trayRegistrationSystem.readTray(createdTray.getTrayId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(updatedTray);
    assertEquals(1L, updatedTray.getTrayId());
    assertEquals(newMaxWeight, updatedTray.getMaxWeight_kilogram());
    assertEquals(curWeight, updatedTray.getWeight_kilogram());
  }


  @Test
  public void whenRemoveTray_WithValidTrayData_ReturnsTrue() {
    // Arrange:
    // Original Tray:
    BigDecimal maxWeight = new BigDecimal("25.00");
    TrayDto createdTray = null;

    try {
      createdTray = trayRegistrationSystem.registerNewTray(maxWeight);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Delete Information:
    boolean deletedTray = false;

    // Act:
    try {
      deletedTray = trayRegistrationSystem.removeTray(createdTray.getTrayId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Check that the Tray cannot be found in db:
    try {
      trayRegistrationSystem.readTray(createdTray.getTrayId());
    } catch (NotFoundException e) {
      // If we reach this line, the deletion succeeded, since we expect NotFoundException to be thrown, meaning PartType was deleted!
      deletedTray = true;
    }

    // Assert:
    assertTrue(deletedTray);
  }


  @Test
  public void whenGetAllTrays_With3ValidTraysInDB_Returns3Trays() {
    // Arrange:
    // Tray1:
    BigDecimal maxWeight1 = new BigDecimal("25.00");
    BigDecimal curWeight1 = BigDecimal.ZERO;
    TrayDto createdTray1 = null;

    // Tray:2
    BigDecimal maxWeight2 = new BigDecimal("30.15");
    BigDecimal curWeight2 = BigDecimal.ZERO;
    TrayDto createdTray2 = null;

    // Tray3:
    BigDecimal maxWeight3 = new BigDecimal("35.12");
    BigDecimal curWeight3 = BigDecimal.ZERO;
    TrayDto createdTray3 = null;

    try {
      createdTray1 = trayRegistrationSystem.registerNewTray(maxWeight1);
      createdTray2 = trayRegistrationSystem.registerNewTray(maxWeight2);
      createdTray3 = trayRegistrationSystem.registerNewTray(maxWeight3);

      // Add an AnimalPart, so there is some weight to test against:
      AnimalDto animal = animalRegistrationService.registerNewAnimal(new BigDecimal("212.25"), "Test Farmstead", Date.from(Instant.now()));
      PartTypeDto partType = partTypeRegistrationSystem.registerNewPartType("Cow Bladder");
      AnimalPartDto animalPart1 = animalPartRegistrationService.registerNewAnimalPart(animal, partType, createdTray2, new BigDecimal("3.25"));
      AnimalPartDto animalPart2 = animalPartRegistrationService.registerNewAnimalPart(animal, partType, createdTray3, new BigDecimal("5.10"));

      createdTray2.addAnimalPartId(animalPart1.getPartId());
      curWeight2 = animalPart1.getWeight_kilogram();
      trayRegistrationSystem.updateTray(createdTray2);

      createdTray3.addAnimalPartId(animalPart2.getPartId());
      curWeight3 = animalPart2.getWeight_kilogram();
      trayRegistrationSystem.updateTray(createdTray3);

      // Update the created trays:
      createdTray2 = trayRegistrationSystem.readTray(createdTray2.getTrayId());
      createdTray3 = trayRegistrationSystem.readTray(createdTray3.getTrayId());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Act:
    List<TrayDto> trays = new ArrayList<>();
    try {
      trays = trayRegistrationSystem.getAllTrays();
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(trays);
    assertEquals(3, trays.size());

    // Check PartType with id1:
    assertEquals(1L, createdTray1.getTrayId());
    assertEquals(maxWeight1, createdTray1.getMaxWeight_kilogram());
    assertEquals(curWeight1, createdTray1.getWeight_kilogram());

    // Check PartType with id2:
    assertEquals(2L, createdTray2.getTrayId());
    assertEquals(maxWeight2, createdTray2.getMaxWeight_kilogram());
    assertEquals(curWeight2, createdTray2.getWeight_kilogram());

    // Check PartType with id3:
    assertEquals(3L, createdTray3.getTrayId());
    assertEquals(maxWeight3, createdTray3.getMaxWeight_kilogram());
    assertEquals(curWeight3, createdTray3.getWeight_kilogram());
  }
}
