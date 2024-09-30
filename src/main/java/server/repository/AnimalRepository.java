package server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import shared.model.entities.Animal;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AnimalRepository
{
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public AnimalRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  // RowMapper is responsible for mapping each resultSet into Java Objects!
  private RowMapper<Animal> animalRowMapper = (resultSet, rowNum) -> {
    Animal animal = new Animal();
    animal.setId(resultSet.getLong("animal_id"));
    animal.setWeight(resultSet.getBigDecimal("weight_kilogram"));
    return animal;
  };

  public List<Animal> getAllAnimalsFromDatabase() {
    String sql = "SELECT id, name FROM animal";
    return jdbcTemplate.query(sql, animalRowMapper);
  }

  public Animal getAnimalByIdFromDatabase(Long id) {
    String sql = "SELECT id, name FROM animal WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, animalRowMapper, id);
  }

  public Animal addNewAnimalToDatabase(Animal animal) {
    try {
      // The SQL statement to be executed on the database:
      String sql = "INSERT INTO animal (weight_kilogram) VALUES (?) RETURNING animal_id";

      // A container for the primary key, which is assigned to the newly added Animal
      KeyHolder keyHolder = new GeneratedKeyHolder();

      // Execution of the SQL statement on the database, using jdbcTemplate - a spring boot method! Also ensuring to return the generated key.
      jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setBigDecimal(1, animal.getWeight());
        return ps;
      }, keyHolder);

      // Create an Animal, to return with the proper id:
      Animal createdAnimal = animal.copy();

      // Retrieve the id from the generated key
      Number key = keyHolder.getKey();

      if (key != null) {
        // Assign the created key id to the Animal, if such a key is not null.
        createdAnimal.setId(key.longValue());
        return createdAnimal;

      } else {
        throw new RuntimeException("Failed to retrieve generated animal_id.");
      }

    } catch (Exception e) {
      //TODO Implement better exception handling
      throw new RuntimeException("Error inserting animal into database", e);
    }
  }

  //TODO MISSING IMPLEMENTATION

  /*public int updateExistingAnimalInDatabase(Animal animal) {
    String sql = "UPDATE animal SET name = ? WHERE id = ?";
    return jdbcTemplate.update(sql, animal.getName(), animal.getId());
  }*/

  /*public int deleteExistingAnimalFromDatabase(Long id) {
    String sql = "DELETE FROM animal WHERE id = ?";
    return jdbcTemplate.update(sql, id);
  }*/
}
