package client.ui.Model.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Client
{
  protected final String host;
  protected final int port;

  public Client (String host, int port) {
    this.host = host;
    this.port = port;
  }

  protected ManagedChannel channel() {
    return ManagedChannelBuilder
        .forAddress(host, port)
        .usePlaintext()
        .build();
  }
}
