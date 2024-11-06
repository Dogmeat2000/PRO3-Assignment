package integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import server.ServerApplication;
import server.model.persistence.service.AnimalRegistryInterface;
import server.model.persistence.service.AnimalService;
import shared.model.dto.AnimalDto;
import shared.model.entities.Animal;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest(
    classes = {
        ServerApplication.class,
        TestDataSourceConfig.class},
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RestfulWebServicesEndPointsTest
{
  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private AnimalService animalRegistryService;

  @Autowired
  private ObjectMapper objectMapper; // JSON serializer

  @LocalServerPort
  private int port;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


  @BeforeEach
  public void setUp() {

    // Arrange some generic data in the in-memory database to test against:
    Date date1;
    Date date2;
    Date date3;
    try {
      date1 = dateFormat.parse("2024-11-01");
      date2 = dateFormat.parse("2024-10-02");
      date3 = dateFormat.parse("2024-12-03");

    } catch (ParseException e) {
      throw new RuntimeException(e);
    }

    Animal animal1 = new Animal(1, new BigDecimal(100), "Farmstead1", date1);
    Animal animal2 = new Animal(1, new BigDecimal(120), "Farmstead1", date2);
    Animal animal3 = new Animal(1, new BigDecimal(140), "Farmstead1", date3);
    Animal animal4 = new Animal(1, new BigDecimal(160), "Farmstead23", date1);
    Animal animal5 = new Animal(1, new BigDecimal(120), "Farmstead23", date2);
    Animal animal6 = new Animal(1, new BigDecimal(140), "Farmstead23", date3);

    // Register the testing data:
    animalRegistryService.registerAnimal(animal1);
    animalRegistryService.registerAnimal(animal2);
    animalRegistryService.registerAnimal(animal3);
    animalRegistryService.registerAnimal(animal4);
    animalRegistryService.registerAnimal(animal5);
    animalRegistryService.registerAnimal(animal6);
  }


  @AfterEach
  public void tearDown() {
    // Empty
  }


  @Test
  public void test_Rest_PostNewAnimalToEndpoint_mainSuccess() {
    try {
      // Arrange:
      Date date = dateFormat.parse("2024-11-04");

      AnimalDto animalDto = new AnimalDto(1, new BigDecimal(150), "Farmstead3", date, new ArrayList<>());

      // Serialize as JSON to simulate the REST state it is transferred in:
      String requestBody = objectMapper.writeValueAsString(animalDto);

      // Pack the request body inside a HttpEntity, to simulate the transferred HTTP message:
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);


      // Act: Send the HTTP message:
      String postUrl = "http://localhost:" + port + "/animals";
      ResponseEntity<AnimalDto> response = restTemplate.postForEntity(postUrl, entity, AnimalDto.class);


      // Assert: Evaluate the response:
      assertNotNull(response);
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertEquals(7, response.getBody().getAnimalId());
      assertEquals("Farmstead3", response.getBody().getOrigin());
      assertEquals(date, response.getBody().getArrival_date());
      assertEquals(0, response.getBody().getWeight_kilogram().compareTo(new BigDecimal(150)));


    } catch (Exception e) {
     fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
  }


  @Test
  public void test_Rest_GetSingleAnimalAtEndpoint_mainSuccess() {
    try {
      // Arrange:
      Date expectedResultDate = dateFormat.parse("2024-11-01");

      // Act: Send the HTTP message:
      String getUrl = "http://localhost:" + port + "/animals/1";
      ResponseEntity<AnimalDto> response = restTemplate.getForEntity(getUrl, AnimalDto.class);


      // Assert: Evaluate the response:
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(1, response.getBody().getAnimalId());
      assertEquals("Farmstead1", response.getBody().getOrigin());
      assertEquals(expectedResultDate, response.getBody().getArrival_date());
      assertEquals(0, response.getBody().getWeight_kilogram().compareTo(new BigDecimal(100)));


    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
  }


  @Test
  public void test_Rest_GetAllAnimalsAtEndpoint_mainSuccess() {
    try {
      // Arrange: All required data prepared in the @BeforeEach method.

      // Act: Send the HTTP message:
      String getUrl = "http://localhost:" + port + "/animals";
      ResponseEntity<List<AnimalDto>> response = restTemplate.exchange(
          getUrl,
          HttpMethod.GET,
          null,
          new ParameterizedTypeReference<>() {}
      );


      // Assert: Evaluate the response:
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(6, response.getBody().size());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
  }


  @Test
  public void test_Rest_GetAllAnimalsAddedOnSpecificDateAtEndpoint_mainSuccess() {
    try {
      // Arrange: All required data prepared in the @BeforeEach method.

      // Act: Send the HTTP message:
      String getUrl = "http://localhost:" + port + "/animals?arrival_date=2024-11-01";
      ResponseEntity<List<AnimalDto>> response = restTemplate.exchange(
          getUrl,
          HttpMethod.GET,
          null,
          new ParameterizedTypeReference<>() {}
      );


      // Assert: Evaluate the response:
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(2, response.getBody().size());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
  }


  @Test
  public void test_Rest_GetAllAnimalsWithSpecificOriginAtEndpoint_mainSuccess() {
    try {
      // Arrange: All required data prepared in the @BeforeEach method.

      // Act: Send the HTTP message:
      String getUrl = "http://localhost:" + port + "/animals?origin=Farmstead1";
      ResponseEntity<List<AnimalDto>> response = restTemplate.exchange(
          getUrl,
          HttpMethod.GET,
          null,
          new ParameterizedTypeReference<>() {}
      );


      // Assert: Evaluate the response:
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(3, response.getBody().size());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
  }


  @Test
  public void test_Rest_GetAllAnimalsAddedOnSpecificDateAndWithSpecificOriginAtEndpoint_mainSuccess() {
    try {
      // Arrange: All required data prepared in the @BeforeEach method.

      // Act: Send the HTTP message:
      String getUrl = "http://localhost:" + port + "/animals?origin=Farmstead1&arrival_date=2024-11-01";
      ResponseEntity<List<AnimalDto>> response = restTemplate.exchange(
          getUrl,
          HttpMethod.GET,
          null,
          new ParameterizedTypeReference<>() {}
      );


      // Assert: Evaluate the response:
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(1, response.getBody().size());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
  }


  @Test
  public void test_Rest_PutUpdatedAnimalToEndpoint_mainSuccess() {
    try {
      // Arrange:
      Date date = dateFormat.parse("2024-05-04");

      AnimalDto animalDto = new AnimalDto(1, new BigDecimal(175), "Farmstead44", date, new ArrayList<>());

      // Serialize as JSON to simulate the REST state it is transferred in:
      String requestBody = objectMapper.writeValueAsString(animalDto);

      // Pack the request body inside a HttpEntity, to simulate the transferred HTTP message:
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);


      // Act: Send the HTTP message:
      String putUrl = "http://localhost:" + port + "/animals/1";
      ResponseEntity<AnimalDto> updateResponse = restTemplate.exchange(
          putUrl,
          HttpMethod.PUT,
          entity,
          AnimalDto.class
      );

      // Read the updated entity with index = 1:
      String getUrl = "http://localhost:" + port + "/animals/1";
      ResponseEntity<AnimalDto> response = restTemplate.getForEntity(getUrl, AnimalDto.class);


      // Assert: Evaluate the response:
      assertNotNull(response);
      assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(1, response.getBody().getAnimalId());
      assertEquals("Farmstead44", response.getBody().getOrigin());
      assertEquals(date, response.getBody().getArrival_date());
      assertEquals(0, response.getBody().getWeight_kilogram().compareTo(new BigDecimal(175)));

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
  }


  @Test
  public void test_Rest_DeleteAnimalFromEndpoint_mainSuccess() {
    try {
      // Arrange:
      Date date = dateFormat.parse("2024-11-01");
      AnimalDto animalDto = new AnimalDto(1, new BigDecimal(100), "Farmstead1", date, new ArrayList<>());

      // Serialize as JSON to simulate the REST state it is transferred in:
      String requestBody = objectMapper.writeValueAsString(animalDto);

      // Pack the request body inside a HttpEntity, to simulate the transferred HTTP message:
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);


      // Act: Send the HTTP message:
      String deleteUrl = "http://localhost:" + port + "/animals/1";
      ResponseEntity<AnimalDto> deleteResponse = restTemplate.exchange(
          deleteUrl,
          HttpMethod.DELETE,
          entity,
          AnimalDto.class
      );

      // Attempt to read the now deleted entity at index 1:
      String getUrl = "http://localhost:" + port + "/animals/1";
      ResponseEntity<AnimalDto> response = restTemplate.getForEntity(getUrl, AnimalDto.class);


      // Assert: Evaluate the response:
      assertNotNull(response);
      assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    } catch (Exception e) {
      fail("Unexpected exception thrown while testing. " + e.getMessage());
    }
  }
}
