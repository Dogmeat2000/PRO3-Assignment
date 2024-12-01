package Client.common.model;

public interface BaseModel {
  Object readEntityFromReceivedEntityList(long Id);
  void addEntityToReceivedEntityList(Object obj);
}
