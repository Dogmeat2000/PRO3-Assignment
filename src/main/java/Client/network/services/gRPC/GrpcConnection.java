package Client.network.services.gRPC;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcConnection
{
  protected final String host;
  protected final int port;

  public GrpcConnection(String host, int port) {
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
