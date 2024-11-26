package integrationTests.client.ui.model.service;

import client.interfaces.AnimalRegistrationSystem;
import client.ui.Model.service.AnimalRegistrationSystemImpl;
import integrationTests.TestDataSourceConfig;
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
import shared.model.dto.AnimalDto;
import shared.model.exceptions.persistance.NotFoundException;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
        ServerApplication.class,
        TestDataSourceConfig.class})
@ComponentScan(basePackages = "client")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AnimalRegistrationSystemTest
{
  private ManagedChannel channel;
  private AnimalRegistrationSystem animalRegistrationSystem;
  private AutoCloseable closeable;

  @BeforeEach
  public void setUp() {
    // Start up a gRPC client channel:
    channel = ManagedChannelBuilder.forAddress("localhost", 9090)
        .usePlaintext()
        .build();

    animalRegistrationSystem = new AnimalRegistrationSystemImpl("localhost", 9090);

    // Initialize all the @Mock and @InjectMock fields, allowing Spring Boot time to perform its Dependency Injection.
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void tearDown() {
    // Tear down the gRPC client channel:
    channel.shutdownNow();
    channel = null;
    animalRegistrationSystem = null;

    // Close the Mockito Injections:
    try {
      closeable.close();
    } catch (Exception ignored) {}
  }

  @Test
  public void whenRegisterNewAnimal_WithValidAnimalData_ReturnsRegisteredAnimal() {
    // Arrange:
    String origin = "Test Farmstead";
    Date arrivalDate = Timestamp.from(Instant.now());
    BigDecimal weight = new BigDecimal("542.41");
    AnimalDto createdAnimal = null;

    // Act:
    try {
      createdAnimal = animalRegistrationSystem.registerNewAnimal(weight, origin, arrivalDate);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(createdAnimal);
    assertEquals(1L, createdAnimal.getAnimalId());
    assertEquals(origin, createdAnimal.getOrigin());
    assertEquals(Timestamp.from(arrivalDate.toInstant().truncatedTo(ChronoUnit.DAYS)), Timestamp.from(createdAnimal.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(weight, createdAnimal.getWeight_kilogram());
  }


  @Test
  public void whenReadAnimal_WithValidAnimalData_ReturnsReadAnimal() {
    // Arrange:
    String origin = "Test Farmstead";
    Date arrivalDate = Timestamp.from(Instant.now());
    BigDecimal weight = new BigDecimal("542.41");
    AnimalDto createdAnimal = null;
    try {
      createdAnimal = animalRegistrationSystem.registerNewAnimal(weight, origin, arrivalDate);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
    AnimalDto readAnimal = null;

    // Act:
    try {
      readAnimal = animalRegistrationSystem.readAnimal(createdAnimal.getAnimalId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(readAnimal);
    assertEquals(1L, readAnimal.getAnimalId());
    assertEquals(origin, readAnimal.getOrigin());
    assertEquals(Timestamp.from(arrivalDate.toInstant().truncatedTo(ChronoUnit.DAYS)), Timestamp.from(readAnimal.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(weight, readAnimal.getWeight_kilogram());
  }


  @Test
  public void whenUpdateAnimal_WithValidAnimalData_ReturnsUpdatedAnimal() {
    // Arrange:
    // Original Animal:
    String origin = "Test Farmstead";
    Date arrivalDate = Timestamp.from(Instant.now());
    BigDecimal weight = new BigDecimal("542.41");
    AnimalDto createdAnimal = null;
    try {
      createdAnimal = animalRegistrationSystem.registerNewAnimal(weight, origin, arrivalDate);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Update Information:
    String newOrigin = "Test SlaughterHouse";
    Date newArrivalDate = Timestamp.from(Instant.now());
    BigDecimal newWeight = new BigDecimal("100.75");
    createdAnimal.setOrigin(newOrigin);
    createdAnimal.setWeight_kilogram(newWeight);
    createdAnimal.setArrivalDate(newArrivalDate);

    AnimalDto updatedAnimal = null;

    // Act:
    try {
      animalRegistrationSystem.updateAnimal(createdAnimal);
      Thread.sleep(50); // Give it time to update
      updatedAnimal = animalRegistrationSystem.readAnimal(createdAnimal.getAnimalId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(updatedAnimal);
    assertEquals(1L, updatedAnimal.getAnimalId());
    assertEquals(newOrigin, updatedAnimal.getOrigin());
    assertEquals(Timestamp.from(newArrivalDate.toInstant().truncatedTo(ChronoUnit.DAYS)), Timestamp.from(updatedAnimal.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(newWeight, updatedAnimal.getWeight_kilogram());
  }


  @Test
  public void whenRemoveAnimal_WithValidAnimalData_ReturnsTrue() {
    // Arrange:
    // Original Animal:
    String origin = "Test Farmstead";
    Date arrivalDate = Timestamp.from(Instant.now());
    BigDecimal weight = new BigDecimal("542.41");
    AnimalDto createdAnimal = null;
    try {
      createdAnimal = animalRegistrationSystem.registerNewAnimal(weight, origin, arrivalDate);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Delete Information:
    boolean deletedAnimal = false;

    // Act:
    try {
      deletedAnimal = animalRegistrationSystem.removeAnimal(createdAnimal.getAnimalId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Check that the Animal cannot be found in db:
    try {
      animalRegistrationSystem.readAnimal(createdAnimal.getAnimalId());
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
    Date arrivalDate1 = Timestamp.from(Instant.now());
    BigDecimal weight1 = new BigDecimal("542.41");
    AnimalDto createdAnimal1 = null;

    // Animal2:
    String origin2 = "Test Farmstead 2";
    Date arrivalDate2 = Timestamp.from(Instant.now());
    BigDecimal weight2 = new BigDecimal("123.45");
    AnimalDto createdAnimal2 = null;

    // Animal3:
    String origin3 = "Test Farmstead 3";
    Date arrivalDate3 = Timestamp.from(Instant.now());
    BigDecimal weight3 = new BigDecimal("234.56");
    AnimalDto createdAnimal3 = null;


    try {
      createdAnimal1 = animalRegistrationSystem.registerNewAnimal(weight1, origin1, arrivalDate1);
      createdAnimal2 = animalRegistrationSystem.registerNewAnimal(weight2, origin2, arrivalDate2);
      createdAnimal3 = animalRegistrationSystem.registerNewAnimal(weight3, origin3, arrivalDate3);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Act:
    List<AnimalDto> animals = new ArrayList<>();
    try {
      animals = animalRegistrationSystem.getAllAnimals();
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(animals);
    assertEquals(3, animals.size());

    // Check Animal with id1:
    assertEquals(1L, createdAnimal1.getAnimalId());
    assertEquals(origin1, createdAnimal1.getOrigin());
    assertEquals(Timestamp.from(arrivalDate1.toInstant().truncatedTo(ChronoUnit.DAYS)), Timestamp.from(createdAnimal1.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(weight1, createdAnimal1.getWeight_kilogram());

    // Check Animal with id2:
    assertEquals(2L, createdAnimal2.getAnimalId());
    assertEquals(origin2, createdAnimal2.getOrigin());
    assertEquals(Timestamp.from(arrivalDate2.toInstant().truncatedTo(ChronoUnit.DAYS)), Timestamp.from(createdAnimal2.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(weight2, createdAnimal2.getWeight_kilogram());

    // Check Animal with id3:
    assertEquals(3L, createdAnimal3.getAnimalId());
    assertEquals(origin3, createdAnimal3.getOrigin());
    assertEquals(Timestamp.from(arrivalDate3.toInstant().truncatedTo(ChronoUnit.DAYS)), Timestamp.from(createdAnimal3.getArrivalDate().toInstant().truncatedTo(ChronoUnit.DAYS)));
    assertEquals(weight3, createdAnimal3.getWeight_kilogram());
  }
}
