package shared.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnimalDto implements Serializable
{
  @JsonProperty("animalId")
  private long animalId;

  @JsonProperty("weight_kilogram")
  private BigDecimal weight_kilogram;

  @JsonProperty("origin")
  private String origin;

  @JsonProperty("arrival_date")
  private Timestamp arrival_date;

  @JsonProperty("animalPartIdList")
  private List<Long> animalPartIdList = new ArrayList<>();

  public AnimalDto(long animalId, BigDecimal weight_kilogram, String origin, Timestamp arrival_date, List<Long> animalPartIdList) {
    this.animalId = animalId;
    this.weight_kilogram = weight_kilogram;
    this.origin = origin;
    this.arrival_date = arrival_date;
    this.animalPartIdList = animalPartIdList;
  }

  public long getAnimalId() {
    return animalId;
  }

  public void setAnimalId(long animalId) {
    this.animalId = animalId;
  }

  public BigDecimal getWeight_kilogram() {
    return weight_kilogram;
  }

  public void setWeight_kilogram(BigDecimal weight_kilogram) {
    this.weight_kilogram = weight_kilogram;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public Timestamp getArrival_date() {
    return arrival_date;
  }

  public void setArrival_date(Timestamp arrival_date) {
    this.arrival_date = arrival_date;
  }

  public List<Long> getAnimalPartIdList() {
    return animalPartIdList;
  }

  public void setAnimalPartIdList(List<Long> animalPartIdList) {
    this.animalPartIdList = animalPartIdList;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    AnimalDto animalDto = (AnimalDto) o;
    return getAnimalId() == animalDto.getAnimalId() && Objects.equals(getWeight_kilogram(), animalDto.getWeight_kilogram()) && Objects.equals(getOrigin(), animalDto.getOrigin()) && Objects.equals(
        getArrival_date(), animalDto.getArrival_date()) && Objects.equals(getAnimalPartIdList(), animalDto.getAnimalPartIdList());
  }

  @Override public int hashCode() {
    return Objects.hash(getAnimalId(), getWeight_kilogram(), getOrigin(), getArrival_date(), getAnimalPartIdList());
  }

  @Override public String toString() {
    return "AnimalDto{" + "animalId=" + animalId + ", weight_kilogram=" + weight_kilogram + ", origin='" + origin + '\'' + ", arrival_date=" + arrival_date + ", animalPartIdList=" + animalPartIdList
        + '}';
  }
}
