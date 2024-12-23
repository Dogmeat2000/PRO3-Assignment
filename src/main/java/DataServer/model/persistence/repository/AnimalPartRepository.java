package DataServer.model.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import DataServer.model.persistence.entities.AnimalPart;

import java.util.List;
import java.util.Optional;

/** <p>A @Repository (Spring Boot) for Java Persistance API (JPA).<br>
 * This @Repository interface extends JpaRepository and provides access to paging, sorting and CRUD operations on the database.
 * It is automatically populated by Spring Boot JPA, so no implementation class is needed for the proper Database access operations.<br>
 * JPA specification states that each Entity in the database must have a java annotated @Entity object in the Java code.
 * JPA also states that for each @Entity there must be a corresponding @Repository similar to this.
 * </p>
 * <p>More info can be found here:<br><a href="https://www.geeksforgeeks.org/spring-boot-difference-between-crudrepository-and-jparepository">https://www.geeksforgeeks.org/spring-boot-difference-between-crudrepository-and-jparepository</a><br>
 * and here:<br> <a href="https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d">https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d</a></p>*/
@Repository
public interface AnimalPartRepository extends JpaRepository<AnimalPart, Long>
{
  // The extended JpaRepository adds CRUD and Paging/Sorting operations to the Animal entity.
  // If additional functionality is required, it can be added below.
  Optional<List<AnimalPart>> findAnimalPartsByAnimal_animalId(Long animal_id);
  Optional<List<AnimalPart>> findAnimalPartsByProduct_productId(Long product_id);
  Optional<List<AnimalPart>> findAnimalPartsByTray_trayId(Long trayId);
  Optional<List<AnimalPart>> findAnimalPartsByType_typeId(Long partType_id);
  void deleteAnimalPartByPartId(Long part_id);
}
