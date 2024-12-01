package Client.common.model;

import java.util.List;

public interface QueueManager extends Runnable
{
  boolean addLast(Object obj) throws IllegalArgumentException;
  List<Object> copyQueue();
  boolean findAndConsume(Object obj);
}
