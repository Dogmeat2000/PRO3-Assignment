package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.repository.AnimalRepository;
import shared.model.entities.Animal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnimalService implements AnimalRegistryInterface
{
  private final AnimalRepository animalRepository;
  private final Map<Long, Animal> animalCache = new HashMap<>();

  @Autowired
  public AnimalService(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }

  // @Transactional is appended, to ensure that database actions are executed within a single transaction - and can be rolled back, if they fail!
  @Transactional
  @Override public Animal registerAnimal(Animal data) {
    // if (weightInKilogram.compareTo(BigDecimal.ZERO) < 0) throw new ValidationException("price must be greater than zero");
    // TODO: More data validation?

    Animal newAnimal = animalRepository.addNewAnimalToDatabase(data);
    animalCache.put(newAnimal.getId(), newAnimal);
    return newAnimal;
  }

  @Override public Animal readAnimal(String animalId) {
    //TODO Not implemented yet
    return null;
  }

  @Override public int updateAnimal(Animal data) {
    //TODO Not implemented yet
    return -1;
  }

  @Override public int removeAnimal(Animal data) {
    //TODO Not implemented yet
    return -1;
  }

  @Override public List<Animal> getAllAnimals() {
    //TODO Not implemented yet
    return List.of();
  }
}
