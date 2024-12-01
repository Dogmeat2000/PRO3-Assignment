package Client.Station3_Packing.model;

import Client.common.model.QueueManager;

import java.util.List;

public class ProducedProductsQueueManager implements QueueManager
{
  @Override public boolean addLast(Object obj) throws IllegalArgumentException {
    // TODO: Not implemented
    return false;
  }

  @Override public List<Object> copyQueue() {
    // TODO: Not implemented
    return List.of();
  }

  @Override public boolean findAndConsume(Object obj) {
    // TODO: Not implemented
    return false;
  }

  @Override public void run() {
    // TODO: Not implemented
  }
}
