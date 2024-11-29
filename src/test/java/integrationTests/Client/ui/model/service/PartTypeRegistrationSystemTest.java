package integrationTests.Client.ui.model.service;

import Client.network.services.gRPC.PartTypeRegistrationSystem;
import Client.network.services.gRPC.PartTypeRegistrationSystemImpl;
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
import shared.model.dto.PartTypeDto;
import shared.model.exceptions.persistance.NotFoundException;

import java.util.ArrayList;
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
public class PartTypeRegistrationSystemTest
{
  private ManagedChannel channel;
  private PartTypeRegistrationSystem partTypeRegistrationSystem;
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

    partTypeRegistrationSystem = new PartTypeRegistrationSystemImpl(grpcServerAddress, grpcServerPort);

    // Initialize all the @Mock and @InjectMock fields, allowing Spring Boot time to perform its Dependency Injection.
    closeable = MockitoAnnotations.openMocks(this);

  }

  @AfterEach
  public void tearDown() {
    // Tear down the gRPC client channel:
    channel.shutdownNow();
    channel = null;

    partTypeRegistrationSystem = null;

    // Close the Mockito Injections:
    try {
      closeable.close();
    } catch (Exception ignored) {}

  }

  @Test
  public void whenRegisterNewPartType_WithValidPartTypeData_ReturnsRegisteredPartType() {
    // Arrange:
    String desc = "Body part Test";
    PartTypeDto createdPartType = null;

    // Act:
    try {
      createdPartType = partTypeRegistrationSystem.registerNewPartType(desc);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(createdPartType);
    assertEquals(1L, createdPartType.getTypeId());
    assertEquals(desc, createdPartType.getTypeDesc());
    assertEquals(new ArrayList<Long>(), createdPartType.getAnimalPartIdList());
  }


  @Test
  public void whenReadPartType_WithValidPartTypeData_ReturnsReadPartType() {
    // Arrange:
    String desc = "Body part Test";
    PartTypeDto createdPartType = null;

    try {
      createdPartType = partTypeRegistrationSystem.registerNewPartType(desc);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
    PartTypeDto readPartType = null;

    // Act:
    try {
      readPartType = partTypeRegistrationSystem.readPartType(createdPartType.getTypeId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(readPartType);
    assertEquals(createdPartType.getTypeId(), readPartType.getTypeId());
    assertEquals(createdPartType.getTypeDesc(), readPartType.getTypeDesc());
    assertEquals(createdPartType.getAnimalPartIdList(), readPartType.getAnimalPartIdList());
  }


  @Test
  public void whenUpdatePartType_WithValidPartTypeData_ReturnsUpdatedPartType() {
    // Arrange:
    // Original PartType:
    String desc = "Body part Test";
    PartTypeDto createdPartType = null;

    try {
      createdPartType = partTypeRegistrationSystem.registerNewPartType(desc);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Update Information:
    String newDesc = "Another Body Part Test";
    createdPartType.setTypeDesc(newDesc);

    PartTypeDto updatedPartType = null;

    // Act:
    try {
      partTypeRegistrationSystem.updatePartType(createdPartType);
      Thread.sleep(10); // Give it time to update
      updatedPartType = partTypeRegistrationSystem.readPartType(createdPartType.getTypeId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(updatedPartType);
    assertEquals(1L, updatedPartType.getTypeId());
    assertEquals(newDesc, updatedPartType.getTypeDesc());
  }


  @Test
  public void whenRemovePartType_WithValidPartTypeData_ReturnsTrue() {
    // Arrange:
    // Original Animal:
    String desc = "Body part Test";
    PartTypeDto createdPartType = null;

    try {
      createdPartType = partTypeRegistrationSystem.registerNewPartType(desc);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Delete Information:
    boolean deletedAnimal = false;

    // Act:
    try {
      deletedAnimal = partTypeRegistrationSystem.removePartType(createdPartType.getTypeId());
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Check that the Animal cannot be found in db:
    try {
      partTypeRegistrationSystem.readPartType(createdPartType.getTypeId());
    } catch (NotFoundException e) {
      // If we reach this line, the deletion succeeded, since we expect NotFoundException to be thrown, meaning PartType was deleted!
      deletedAnimal = true;
    }

    // Assert:
    assertTrue(deletedAnimal);
  }


  @Test
  public void whenGetAllPartTypes_With3ValidPartTypesInDB_Returns3PartTypes() {
    // Arrange:
    // PartType1:
    String desc1 = "Test Body Part 1";
    PartTypeDto createdPartType1 = null;

    // PartType2:
    String desc2 = "Test Body Part 2";
    PartTypeDto createdPartType2 = null;

    // PartType3:
    String desc3 = "Test Body Part 3";
    PartTypeDto createdPartType3 = null;


    try {
      createdPartType1 = partTypeRegistrationSystem.registerNewPartType(desc1);
      createdPartType2 = partTypeRegistrationSystem.registerNewPartType(desc2);
      createdPartType3 = partTypeRegistrationSystem.registerNewPartType(desc3);
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Act:
    List<PartTypeDto> partTypes = new ArrayList<>();
    try {
      partTypes = partTypeRegistrationSystem.getAllPartTypes();
    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }

    // Assert:
    assertNotNull(partTypes);
    assertEquals(3, partTypes.size());

    // Check PartType with id1:
    assertEquals(1L, createdPartType1.getTypeId());
    assertEquals(desc1, createdPartType1.getTypeDesc());
    assertEquals(new ArrayList<Long>(), createdPartType1.getAnimalPartIdList());

    // Check PartType with id2:
    assertEquals(2L, createdPartType2.getTypeId());
    assertEquals(desc2, createdPartType2.getTypeDesc());
    assertEquals(new ArrayList<Long>(), createdPartType2.getAnimalPartIdList());

    // Check PartType with id3:
    assertEquals(3L, createdPartType3.getTypeId());
    assertEquals(desc3, createdPartType3.getTypeDesc());
    assertEquals(new ArrayList<Long>(), createdPartType3.getAnimalPartIdList());
  }
}
