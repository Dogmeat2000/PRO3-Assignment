package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: SlaughterHouseSim.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TrayServiceGrpc {

  private TrayServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "grpc.TrayService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.TrayData,
      grpc.TrayData> getRegisterTrayMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerTray",
      requestType = grpc.TrayData.class,
      responseType = grpc.TrayData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.TrayData,
      grpc.TrayData> getRegisterTrayMethod() {
    io.grpc.MethodDescriptor<grpc.TrayData, grpc.TrayData> getRegisterTrayMethod;
    if ((getRegisterTrayMethod = TrayServiceGrpc.getRegisterTrayMethod) == null) {
      synchronized (TrayServiceGrpc.class) {
        if ((getRegisterTrayMethod = TrayServiceGrpc.getRegisterTrayMethod) == null) {
          TrayServiceGrpc.getRegisterTrayMethod = getRegisterTrayMethod =
              io.grpc.MethodDescriptor.<grpc.TrayData, grpc.TrayData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerTray"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TrayData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TrayData.getDefaultInstance()))
              .setSchemaDescriptor(new TrayServiceMethodDescriptorSupplier("registerTray"))
              .build();
        }
      }
    }
    return getRegisterTrayMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.TrayId,
      grpc.TrayData> getReadTrayMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readTray",
      requestType = grpc.TrayId.class,
      responseType = grpc.TrayData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.TrayId,
      grpc.TrayData> getReadTrayMethod() {
    io.grpc.MethodDescriptor<grpc.TrayId, grpc.TrayData> getReadTrayMethod;
    if ((getReadTrayMethod = TrayServiceGrpc.getReadTrayMethod) == null) {
      synchronized (TrayServiceGrpc.class) {
        if ((getReadTrayMethod = TrayServiceGrpc.getReadTrayMethod) == null) {
          TrayServiceGrpc.getReadTrayMethod = getReadTrayMethod =
              io.grpc.MethodDescriptor.<grpc.TrayId, grpc.TrayData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readTray"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TrayId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TrayData.getDefaultInstance()))
              .setSchemaDescriptor(new TrayServiceMethodDescriptorSupplier("readTray"))
              .build();
        }
      }
    }
    return getReadTrayMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.TrayData,
      grpc.EmptyMessage> getUpdateTrayMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateTray",
      requestType = grpc.TrayData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.TrayData,
      grpc.EmptyMessage> getUpdateTrayMethod() {
    io.grpc.MethodDescriptor<grpc.TrayData, grpc.EmptyMessage> getUpdateTrayMethod;
    if ((getUpdateTrayMethod = TrayServiceGrpc.getUpdateTrayMethod) == null) {
      synchronized (TrayServiceGrpc.class) {
        if ((getUpdateTrayMethod = TrayServiceGrpc.getUpdateTrayMethod) == null) {
          TrayServiceGrpc.getUpdateTrayMethod = getUpdateTrayMethod =
              io.grpc.MethodDescriptor.<grpc.TrayData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateTray"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TrayData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new TrayServiceMethodDescriptorSupplier("updateTray"))
              .build();
        }
      }
    }
    return getUpdateTrayMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.TrayData,
      grpc.EmptyMessage> getRemoveTrayMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "removeTray",
      requestType = grpc.TrayData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.TrayData,
      grpc.EmptyMessage> getRemoveTrayMethod() {
    io.grpc.MethodDescriptor<grpc.TrayData, grpc.EmptyMessage> getRemoveTrayMethod;
    if ((getRemoveTrayMethod = TrayServiceGrpc.getRemoveTrayMethod) == null) {
      synchronized (TrayServiceGrpc.class) {
        if ((getRemoveTrayMethod = TrayServiceGrpc.getRemoveTrayMethod) == null) {
          TrayServiceGrpc.getRemoveTrayMethod = getRemoveTrayMethod =
              io.grpc.MethodDescriptor.<grpc.TrayData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "removeTray"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TrayData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new TrayServiceMethodDescriptorSupplier("removeTray"))
              .build();
        }
      }
    }
    return getRemoveTrayMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.TraysData> getGetAllTraysMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllTrays",
      requestType = grpc.EmptyMessage.class,
      responseType = grpc.TraysData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.TraysData> getGetAllTraysMethod() {
    io.grpc.MethodDescriptor<grpc.EmptyMessage, grpc.TraysData> getGetAllTraysMethod;
    if ((getGetAllTraysMethod = TrayServiceGrpc.getGetAllTraysMethod) == null) {
      synchronized (TrayServiceGrpc.class) {
        if ((getGetAllTraysMethod = TrayServiceGrpc.getGetAllTraysMethod) == null) {
          TrayServiceGrpc.getGetAllTraysMethod = getGetAllTraysMethod =
              io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.TraysData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllTrays"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.TraysData.getDefaultInstance()))
              .setSchemaDescriptor(new TrayServiceMethodDescriptorSupplier("getAllTrays"))
              .build();
        }
      }
    }
    return getGetAllTraysMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TrayServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TrayServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TrayServiceStub>() {
        @java.lang.Override
        public TrayServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TrayServiceStub(channel, callOptions);
        }
      };
    return TrayServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TrayServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TrayServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TrayServiceBlockingStub>() {
        @java.lang.Override
        public TrayServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TrayServiceBlockingStub(channel, callOptions);
        }
      };
    return TrayServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TrayServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TrayServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TrayServiceFutureStub>() {
        @java.lang.Override
        public TrayServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TrayServiceFutureStub(channel, callOptions);
        }
      };
    return TrayServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Create:
     * </pre>
     */
    default void registerTray(grpc.TrayData request,
        io.grpc.stub.StreamObserver<grpc.TrayData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterTrayMethod(), responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    default void readTray(grpc.TrayId request,
        io.grpc.stub.StreamObserver<grpc.TrayData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadTrayMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    default void updateTray(grpc.TrayData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateTrayMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    default void removeTray(grpc.TrayData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveTrayMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    default void getAllTrays(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.TraysData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllTraysMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service TrayService.
   */
  public static abstract class TrayServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return TrayServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service TrayService.
   */
  public static final class TrayServiceStub
      extends io.grpc.stub.AbstractAsyncStub<TrayServiceStub> {
    private TrayServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrayServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TrayServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public void registerTray(grpc.TrayData request,
        io.grpc.stub.StreamObserver<grpc.TrayData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterTrayMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public void readTray(grpc.TrayId request,
        io.grpc.stub.StreamObserver<grpc.TrayData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadTrayMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public void updateTray(grpc.TrayData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateTrayMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public void removeTray(grpc.TrayData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveTrayMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public void getAllTrays(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.TraysData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllTraysMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service TrayService.
   */
  public static final class TrayServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<TrayServiceBlockingStub> {
    private TrayServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrayServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TrayServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public grpc.TrayData registerTray(grpc.TrayData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterTrayMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public grpc.TrayData readTray(grpc.TrayId request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadTrayMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public grpc.EmptyMessage updateTray(grpc.TrayData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateTrayMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public grpc.EmptyMessage removeTray(grpc.TrayData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveTrayMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public grpc.TraysData getAllTrays(grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllTraysMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service TrayService.
   */
  public static final class TrayServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<TrayServiceFutureStub> {
    private TrayServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TrayServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TrayServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.TrayData> registerTray(
        grpc.TrayData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterTrayMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.TrayData> readTray(
        grpc.TrayId request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadTrayMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> updateTray(
        grpc.TrayData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateTrayMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> removeTray(
        grpc.TrayData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveTrayMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.TraysData> getAllTrays(
        grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllTraysMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_TRAY = 0;
  private static final int METHODID_READ_TRAY = 1;
  private static final int METHODID_UPDATE_TRAY = 2;
  private static final int METHODID_REMOVE_TRAY = 3;
  private static final int METHODID_GET_ALL_TRAYS = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_TRAY:
          serviceImpl.registerTray((grpc.TrayData) request,
              (io.grpc.stub.StreamObserver<grpc.TrayData>) responseObserver);
          break;
        case METHODID_READ_TRAY:
          serviceImpl.readTray((grpc.TrayId) request,
              (io.grpc.stub.StreamObserver<grpc.TrayData>) responseObserver);
          break;
        case METHODID_UPDATE_TRAY:
          serviceImpl.updateTray((grpc.TrayData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_REMOVE_TRAY:
          serviceImpl.removeTray((grpc.TrayData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_GET_ALL_TRAYS:
          serviceImpl.getAllTrays((grpc.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<grpc.TraysData>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRegisterTrayMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.TrayData,
              grpc.TrayData>(
                service, METHODID_REGISTER_TRAY)))
        .addMethod(
          getReadTrayMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.TrayId,
              grpc.TrayData>(
                service, METHODID_READ_TRAY)))
        .addMethod(
          getUpdateTrayMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.TrayData,
              grpc.EmptyMessage>(
                service, METHODID_UPDATE_TRAY)))
        .addMethod(
          getRemoveTrayMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.TrayData,
              grpc.EmptyMessage>(
                service, METHODID_REMOVE_TRAY)))
        .addMethod(
          getGetAllTraysMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.EmptyMessage,
              grpc.TraysData>(
                service, METHODID_GET_ALL_TRAYS)))
        .build();
  }

  private static abstract class TrayServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TrayServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.SlaughterHouseSim.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TrayService");
    }
  }

  private static final class TrayServiceFileDescriptorSupplier
      extends TrayServiceBaseDescriptorSupplier {
    TrayServiceFileDescriptorSupplier() {}
  }

  private static final class TrayServiceMethodDescriptorSupplier
      extends TrayServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    TrayServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TrayServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TrayServiceFileDescriptorSupplier())
              .addMethod(getRegisterTrayMethod())
              .addMethod(getReadTrayMethod())
              .addMethod(getUpdateTrayMethod())
              .addMethod(getRemoveTrayMethod())
              .addMethod(getGetAllTraysMethod())
              .build();
        }
      }
    }
    return result;
  }
}
