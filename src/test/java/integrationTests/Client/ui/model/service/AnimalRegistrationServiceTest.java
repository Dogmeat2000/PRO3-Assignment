package integrationTests.Client.ui.model.service;

import Client.Station1_AnimalRegistration.network.services.gRPC.AnimalRegistrationService;
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
import shared.model.exceptions.persistance.NotFoundException;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(
    classes = {
        DataServerApplication.class,
        TestDataSourceConfig.class})
@ComponentScan(basePackages = "Client")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AnimalRegistrationServiceTest
{
  private ManagedChannel channel;
  private AnimalRegistrationService animalRegistrationService;
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

    animalRegistrationService = new AnimalRegistrationServiceImpl(grpcServerAddress, grpcServerPort);

    // Initialize all the @Mock and @InjectMock fields, allowing Spring Boot time to perform its Dependency Injection.
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void tearDown() {
    // Tear down the gRPC client channel:
    channel.shutdownNow();
    channel = null;
    animalRegistrationService = null;

    // Close the Mockito Injections:
    try {
      closeable.close();
    } catch (Exception ignored) {}
  }

  @Test
  public void whenRegisterNewAnimal_WithValidAnimalData_ReturnsRegisteredAnimal() {
    // Arrange:
    String origin = "Test Farmstead";
    Date arrivalDate = Date.from(Instant.now());
    BigDecimal weight = new BigDecimal("542.41");
    AnimalDto createdAnimal = null;

    // Act:
    try {
      createdAnimal = animalRegistrationService.registerNewAnimal(weight, origin, arrivalDate);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(createdAnimal);
    assertEquals(1L, createdAnimal.getAnimalId());
    assertEquals(origin, createdAnimal.getOrigin());
    assertEquals(Date.from(arrivalDate.toInstant().truncatedTo(ChronoUnit.DAYS)), Date.from(createdAnimal.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(weight, createdAnimal.getWeight_kilogram());
  }


  @Test
  public void whenReadAnimal_WithValidAnimalData_ReturnsReadAnimal() {
    // Arrange:
    String origin = "Test Farmstead";
    Date arrivalDate = Date.from(Instant.now());
    BigDecimal weight = new BigDecimal("542.41");
    AnimalDto createdAnimal = null;
    try {
      createdAnimal = animalRegistrationService.registerNewAnimal(weight, origin, arrivalDate);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
    AnimalDto readAnimal = null;

    // Act:
    try {
      readAnimal = animalRegistrationService.readAnimal(createdAnimal.getAnimalId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(readAnimal);
    assertEquals(1L, readAnimal.getAnimalId());
    assertEquals(origin, readAnimal.getOrigin());
    assertEquals(Date.from(arrivalDate.toInstant().truncatedTo(ChronoUnit.DAYS)), Date.from(readAnimal.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(weight, readAnimal.getWeight_kilogram());
  }


  @Test
  public void whenUpdateAnimal_WithValidAnimalData_ReturnsUpdatedAnimal() {
    // Arrange:
    // Original Animal:
    String origin = "Test Farmstead";
    Date arrivalDate = Date.from(Instant.now());
    BigDecimal weight = new BigDecimal("542.41");
    AnimalDto createdAnimal = null;
    try {
      createdAnimal = animalRegistrationService.registerNewAnimal(weight, origin, arrivalDate);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Update Information:
    String newOrigin = "Test SlaughterHouse";
    Date newArrivalDate = Date.from(Instant.now());
    BigDecimal newWeight = new BigDecimal("100.75");
    createdAnimal.setOrigin(newOrigin);
    createdAnimal.setWeight_kilogram(newWeight);
    createdAnimal.setArrivalDate(newArrivalDate);

    AnimalDto updatedAnimal = null;

    // Act:
    try {
      animalRegistrationService.updateAnimal(createdAnimal);
      Thread.sleep(50); // Give it time to update
      updatedAnimal = animalRegistrationService.readAnimal(createdAnimal.getAnimalId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(updatedAnimal);
    assertEquals(1L, updatedAnimal.getAnimalId());
    assertEquals(newOrigin, updatedAnimal.getOrigin());
    assertEquals(Date.from(newArrivalDate.toInstant().truncatedTo(ChronoUnit.DAYS)), Date.from(updatedAnimal.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(newWeight, updatedAnimal.getWeight_kilogram());
  }


  @Test
  public void whenRemoveAnimal_WithValidAnimalData_ReturnsTrue() {
    // Arrange:
    // Original Animal:
    String origin = "Test Farmstead";
    Date arrivalDate = Date.from(Instant.now());
    BigDecimal weight = new BigDecimal("542.41");
    AnimalDto createdAnimal = null;
    try {
      createdAnimal = animalRegistrationService.registerNewAnimal(weight, origin, arrivalDate);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Delete Information:
    boolean deletedAnimal = false;

    // Act:
    try {
      deletedAnimal = animalRegistrationService.removeAnimal(createdAnimal.getAnimalId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Check that the Animal cannot be found in db:
    try {
      animalRegistrationService.readAnimal(createdAnimal.getAnimalId());
    } catch (NotFoundException e) {
      // If we reach this line, the deletion succeeded, since we expect NotFoundException to be thrown, meaning Animal was deleted!
      deletedAnimal = true;
    }

    // Assert:
    assertTrue(deletedAnimal);
  }


  @Test
  public void whenGetAllAnimalS_With3ValidAnimalsInDB_Returns3Animals() {
    // Arrange:
    // Animal1:
    String origin1 = "Test Farmstead 1";
    Date arrivalDate1 = Date.from(Instant.now());
    BigDecimal weight1 = new BigDecimal("542.41");
    AnimalDto createdAnimal1 = null;

    // Animal2:
    String origin2 = "Test Farmstead 2";
    Date arrivalDate2 = Date.from(Instant.now());
    BigDecimal weight2 = new BigDecimal("123.45");
    AnimalDto createdAnimal2 = null;

    // Animal3:
    String origin3 = "Test Farmstead 3";
    Date arrivalDate3 = Date.from(Instant.now());
    BigDecimal weight3 = new BigDecimal("234.56");
    AnimalDto createdAnimal3 = null;


    try {
      createdAnimal1 = animalRegistrationService.registerNewAnimal(weight1, origin1, arrivalDate1);
      createdAnimal2 = animalRegistrationService.registerNewAnimal(weight2, origin2, arrivalDate2);
      createdAnimal3 = animalRegistrationService.registerNewAnimal(weight3, origin3, arrivalDate3);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Act:
    List<AnimalDto> animals = new ArrayList<>();
    try {
      animals = animalRegistrationService.getAllAnimals();
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(animals);
    assertEquals(3, animals.size());

    // Check Animal with id1:
    assertEquals(1L, createdAnimal1.getAnimalId());
    assertEquals(origin1, createdAnimal1.getOrigin());
    assertEquals(Date.from(arrivalDate1.toInstant().truncatedTo(ChronoUnit.DAYS)), Date.from(createdAnimal1.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(weight1, createdAnimal1.getWeight_kilogram());

    // Check Animal with id2:
    assertEquals(2L, createdAnimal2.getAnimalId());
    assertEquals(origin2, createdAnimal2.getOrigin());
    assertEquals(Date.from(arrivalDate2.toInstant().truncatedTo(ChronoUnit.DAYS)), Date.from(createdAnimal2.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(weight2, createdAnimal2.getWeight_kilogram());

    // Check Animal with id3:
    assertEquals(3L, createdAnimal3.getAnimalId());
    assertEquals(origin3, createdAnimal3.getOrigin());
    assertEquals(Date.from(arrivalDate3.toInstant().truncatedTo(ChronoUnit.DAYS)), Date.from(createdAnimal3.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(weight3, createdAnimal3.getWeight_kilogram());
  }
}
