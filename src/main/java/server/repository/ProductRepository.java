package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shared.model.entities.Product;

/** <p>A @Repository (Spring Boot) for Java Persistance API (JPA).<br>
 * This @Repository interface extends JpaRepository and provides access to paging, sorting and CRUD operations on the database.
 * It is automatically populated by Spring Boot JPA, so no implementation class is needed for the proper Database access operations.<br>
 * JPA specification states that each Entity in the database must have a java annotated @Entity object in the Java code.
 * JPA also states that for each @Entity there must be a corresponding @Repository similar to this.
 * </p>
 * <p>More info can be found here:<br><a href="https://www.geeksforgeeks.org/spring-boot-difference-between-crudrepository-and-jparepository">https://www.geeksforgeeks.org/spring-boot-difference-between-crudrepository-and-jparepository</a><br>
 * and here:<br> <a href="https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d">https://medium.com/@bubu.tripathy/best-practices-entity-class-design-with-jpa-and-spring-boot-6f703339ab3d</a></p>*/
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> // <-- Primary key of Entity must be provided as the Type to JpaRepository!
{
  // The extended JpaRepository adds CRUD and Paging/Sorting operations to the Animal entity.
  // If additional functionality is required, it can be added below.
}
