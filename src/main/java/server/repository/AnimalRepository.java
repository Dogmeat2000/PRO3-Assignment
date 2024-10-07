package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shared.model.entities.Animal;

/** <p>A @Repository (Spring Boot) for Java Persistance API (JPA).<br>
 * This interface replaces the old AnimalRepository implementation, and instead utilizes the flexibility of
 * JPA (Java Persistence API) with Spring Boot, instead of the old manual implementation through JDBC.<br>
 * This @Repository interface extends JpaRepository and provides access to paging, sorting and CRUD operations on the database.
 * It is automatically populated by Spring Boot JPA, so no implementation class is needed for the proper Database access operations.<br>
 * JPA specification states that each Entity in the database must have a java annotated @Entity object in the Java code.
 * JPA also states that for each @Entity there must be a corresponding @Repository similar to this.
 * </p>
 * <p>More info can be found here:<br>https://www.geeksforgeeks.org/spring-boot-difference-between-crudrepository-and-jparepository<br>
 * and here:<br> https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d</p>*/
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> // <-- Primary key of Entity must be provided as the Type to JpaRepository!
{
  // The extended JpaRepository adds CRUD and Paging/Sorting operations to the Animal entity.
  // If additional functionality is required, it can be added below.
}
