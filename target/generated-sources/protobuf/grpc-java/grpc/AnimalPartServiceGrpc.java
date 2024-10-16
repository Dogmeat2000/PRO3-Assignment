package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: SlaughterHouseSim.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AnimalPartServiceGrpc {

  private AnimalPartServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "grpc.AnimalPartService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.AnimalPartData,
      grpc.AnimalPartData> getRegisterAnimalPartMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerAnimalPart",
      requestType = grpc.AnimalPartData.class,
      responseType = grpc.AnimalPartData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.AnimalPartData,
      grpc.AnimalPartData> getRegisterAnimalPartMethod() {
    io.grpc.MethodDescriptor<grpc.AnimalPartData, grpc.AnimalPartData> getRegisterAnimalPartMethod;
    if ((getRegisterAnimalPartMethod = AnimalPartServiceGrpc.getRegisterAnimalPartMethod) == null) {
      synchronized (AnimalPartServiceGrpc.class) {
        if ((getRegisterAnimalPartMethod = AnimalPartServiceGrpc.getRegisterAnimalPartMethod) == null) {
          AnimalPartServiceGrpc.getRegisterAnimalPartMethod = getRegisterAnimalPartMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalPartData, grpc.AnimalPartData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerAnimalPart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartData.getDefaultInstance()))
              .setSchemaDescriptor(new AnimalPartServiceMethodDescriptorSupplier("registerAnimalPart"))
              .build();
        }
      }
    }
    return getRegisterAnimalPartMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.AnimalPartId,
      grpc.AnimalPartData> getReadAnimalPartMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readAnimalPart",
      requestType = grpc.AnimalPartId.class,
      responseType = grpc.AnimalPartData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.AnimalPartId,
      grpc.AnimalPartData> getReadAnimalPartMethod() {
    io.grpc.MethodDescriptor<grpc.AnimalPartId, grpc.AnimalPartData> getReadAnimalPartMethod;
    if ((getReadAnimalPartMethod = AnimalPartServiceGrpc.getReadAnimalPartMethod) == null) {
      synchronized (AnimalPartServiceGrpc.class) {
        if ((getReadAnimalPartMethod = AnimalPartServiceGrpc.getReadAnimalPartMethod) == null) {
          AnimalPartServiceGrpc.getReadAnimalPartMethod = getReadAnimalPartMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalPartId, grpc.AnimalPartData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readAnimalPart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartData.getDefaultInstance()))
              .setSchemaDescriptor(new AnimalPartServiceMethodDescriptorSupplier("readAnimalPart"))
              .build();
        }
      }
    }
    return getReadAnimalPartMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.UpdatedAnimalPartData,
      grpc.EmptyMessage> getUpdateAnimalPartMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateAnimalPart",
      requestType = grpc.UpdatedAnimalPartData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.UpdatedAnimalPartData,
      grpc.EmptyMessage> getUpdateAnimalPartMethod() {
    io.grpc.MethodDescriptor<grpc.UpdatedAnimalPartData, grpc.EmptyMessage> getUpdateAnimalPartMethod;
    if ((getUpdateAnimalPartMethod = AnimalPartServiceGrpc.getUpdateAnimalPartMethod) == null) {
      synchronized (AnimalPartServiceGrpc.class) {
        if ((getUpdateAnimalPartMethod = AnimalPartServiceGrpc.getUpdateAnimalPartMethod) == null) {
          AnimalPartServiceGrpc.getUpdateAnimalPartMethod = getUpdateAnimalPartMethod =
              io.grpc.MethodDescriptor.<grpc.UpdatedAnimalPartData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateAnimalPart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.UpdatedAnimalPartData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new AnimalPartServiceMethodDescriptorSupplier("updateAnimalPart"))
              .build();
        }
      }
    }
    return getUpdateAnimalPartMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.AnimalPartData,
      grpc.EmptyMessage> getRemoveAnimalPartMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "removeAnimalPart",
      requestType = grpc.AnimalPartData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.AnimalPartData,
      grpc.EmptyMessage> getRemoveAnimalPartMethod() {
    io.grpc.MethodDescriptor<grpc.AnimalPartData, grpc.EmptyMessage> getRemoveAnimalPartMethod;
    if ((getRemoveAnimalPartMethod = AnimalPartServiceGrpc.getRemoveAnimalPartMethod) == null) {
      synchronized (AnimalPartServiceGrpc.class) {
        if ((getRemoveAnimalPartMethod = AnimalPartServiceGrpc.getRemoveAnimalPartMethod) == null) {
          AnimalPartServiceGrpc.getRemoveAnimalPartMethod = getRemoveAnimalPartMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalPartData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "removeAnimalPart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new AnimalPartServiceMethodDescriptorSupplier("removeAnimalPart"))
              .build();
        }
      }
    }
    return getRemoveAnimalPartMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.AnimalPartsData> getGetAnimalPartsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAnimalParts",
      requestType = grpc.EmptyMessage.class,
      responseType = grpc.AnimalPartsData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.AnimalPartsData> getGetAnimalPartsMethod() {
    io.grpc.MethodDescriptor<grpc.EmptyMessage, grpc.AnimalPartsData> getGetAnimalPartsMethod;
    if ((getGetAnimalPartsMethod = AnimalPartServiceGrpc.getGetAnimalPartsMethod) == null) {
      synchronized (AnimalPartServiceGrpc.class) {
        if ((getGetAnimalPartsMethod = AnimalPartServiceGrpc.getGetAnimalPartsMethod) == null) {
          AnimalPartServiceGrpc.getGetAnimalPartsMethod = getGetAnimalPartsMethod =
              io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.AnimalPartsData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAnimalParts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartsData.getDefaultInstance()))
              .setSchemaDescriptor(new AnimalPartServiceMethodDescriptorSupplier("getAnimalParts"))
              .build();
        }
      }
    }
    return getGetAnimalPartsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AnimalPartServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AnimalPartServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AnimalPartServiceStub>() {
        @java.lang.Override
        public AnimalPartServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AnimalPartServiceStub(channel, callOptions);
        }
      };
    return AnimalPartServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AnimalPartServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AnimalPartServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AnimalPartServiceBlockingStub>() {
        @java.lang.Override
        public AnimalPartServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AnimalPartServiceBlockingStub(channel, callOptions);
        }
      };
    return AnimalPartServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AnimalPartServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AnimalPartServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AnimalPartServiceFutureStub>() {
        @java.lang.Override
        public AnimalPartServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AnimalPartServiceFutureStub(channel, callOptions);
        }
      };
    return AnimalPartServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Create:
     * </pre>
     */
    default void registerAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterAnimalPartMethod(), responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    default void readAnimalPart(grpc.AnimalPartId request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadAnimalPartMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    default void updateAnimalPart(grpc.UpdatedAnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateAnimalPartMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    default void removeAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveAnimalPartMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    default void getAnimalParts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartsData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAnimalPartsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AnimalPartService.
   */
  public static abstract class AnimalPartServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AnimalPartServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AnimalPartService.
   */
  public static final class AnimalPartServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AnimalPartServiceStub> {
    private AnimalPartServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimalPartServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AnimalPartServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public void registerAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterAnimalPartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public void readAnimalPart(grpc.AnimalPartId request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadAnimalPartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public void updateAnimalPart(grpc.UpdatedAnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateAnimalPartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public void removeAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveAnimalPartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public void getAnimalParts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartsData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAnimalPartsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AnimalPartService.
   */
  public static final class AnimalPartServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AnimalPartServiceBlockingStub> {
    private AnimalPartServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimalPartServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AnimalPartServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public grpc.AnimalPartData registerAnimalPart(grpc.AnimalPartData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterAnimalPartMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public grpc.AnimalPartData readAnimalPart(grpc.AnimalPartId request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadAnimalPartMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public grpc.EmptyMessage updateAnimalPart(grpc.UpdatedAnimalPartData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateAnimalPartMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public grpc.EmptyMessage removeAnimalPart(grpc.AnimalPartData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveAnimalPartMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public grpc.AnimalPartsData getAnimalParts(grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAnimalPartsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AnimalPartService.
   */
  public static final class AnimalPartServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AnimalPartServiceFutureStub> {
    private AnimalPartServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimalPartServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AnimalPartServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalPartData> registerAnimalPart(
        grpc.AnimalPartData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterAnimalPartMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalPartData> readAnimalPart(
        grpc.AnimalPartId request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadAnimalPartMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> updateAnimalPart(
        grpc.UpdatedAnimalPartData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateAnimalPartMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> removeAnimalPart(
        grpc.AnimalPartData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveAnimalPartMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalPartsData> getAnimalParts(
        grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAnimalPartsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_ANIMAL_PART = 0;
  private static final int METHODID_READ_ANIMAL_PART = 1;
  private static final int METHODID_UPDATE_ANIMAL_PART = 2;
  private static final int METHODID_REMOVE_ANIMAL_PART = 3;
  private static final int METHODID_GET_ANIMAL_PARTS = 4;

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
        case METHODID_REGISTER_ANIMAL_PART:
          serviceImpl.registerAnimalPart((grpc.AnimalPartData) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalPartData>) responseObserver);
          break;
        case METHODID_READ_ANIMAL_PART:
          serviceImpl.readAnimalPart((grpc.AnimalPartId) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalPartData>) responseObserver);
          break;
        case METHODID_UPDATE_ANIMAL_PART:
          serviceImpl.updateAnimalPart((grpc.UpdatedAnimalPartData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_REMOVE_ANIMAL_PART:
          serviceImpl.removeAnimalPart((grpc.AnimalPartData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_GET_ANIMAL_PARTS:
          serviceImpl.getAnimalParts((grpc.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalPartsData>) responseObserver);
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
          getRegisterAnimalPartMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalPartData,
              grpc.AnimalPartData>(
                service, METHODID_REGISTER_ANIMAL_PART)))
        .addMethod(
          getReadAnimalPartMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalPartId,
              grpc.AnimalPartData>(
                service, METHODID_READ_ANIMAL_PART)))
        .addMethod(
          getUpdateAnimalPartMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.UpdatedAnimalPartData,
              grpc.EmptyMessage>(
                service, METHODID_UPDATE_ANIMAL_PART)))
        .addMethod(
          getRemoveAnimalPartMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalPartData,
              grpc.EmptyMessage>(
                service, METHODID_REMOVE_ANIMAL_PART)))
        .addMethod(
          getGetAnimalPartsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.EmptyMessage,
              grpc.AnimalPartsData>(
                service, METHODID_GET_ANIMAL_PARTS)))
        .build();
  }

  private static abstract class AnimalPartServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AnimalPartServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.SlaughterHouseSim.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AnimalPartService");
    }
  }

  private static final class AnimalPartServiceFileDescriptorSupplier
      extends AnimalPartServiceBaseDescriptorSupplier {
    AnimalPartServiceFileDescriptorSupplier() {}
  }

  private static final class AnimalPartServiceMethodDescriptorSupplier
      extends AnimalPartServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AnimalPartServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AnimalPartServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AnimalPartServiceFileDescriptorSupplier())
              .addMethod(getRegisterAnimalPartMethod())
              .addMethod(getReadAnimalPartMethod())
              .addMethod(getUpdateAnimalPartMethod())
              .addMethod(getRemoveAnimalPartMethod())
              .addMethod(getGetAnimalPartsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
