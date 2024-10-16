package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: SlaughterHouseSim.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AnimalServiceGrpc {

  private AnimalServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "grpc.AnimalService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.AnimalData,
      grpc.AnimalData> getRegisterAnimalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerAnimal",
      requestType = grpc.AnimalData.class,
      responseType = grpc.AnimalData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.AnimalData,
      grpc.AnimalData> getRegisterAnimalMethod() {
    io.grpc.MethodDescriptor<grpc.AnimalData, grpc.AnimalData> getRegisterAnimalMethod;
    if ((getRegisterAnimalMethod = AnimalServiceGrpc.getRegisterAnimalMethod) == null) {
      synchronized (AnimalServiceGrpc.class) {
        if ((getRegisterAnimalMethod = AnimalServiceGrpc.getRegisterAnimalMethod) == null) {
          AnimalServiceGrpc.getRegisterAnimalMethod = getRegisterAnimalMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalData, grpc.AnimalData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerAnimal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setSchemaDescriptor(new AnimalServiceMethodDescriptorSupplier("registerAnimal"))
              .build();
        }
      }
    }
    return getRegisterAnimalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.AnimalId,
      grpc.AnimalData> getReadAnimalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readAnimal",
      requestType = grpc.AnimalId.class,
      responseType = grpc.AnimalData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.AnimalId,
      grpc.AnimalData> getReadAnimalMethod() {
    io.grpc.MethodDescriptor<grpc.AnimalId, grpc.AnimalData> getReadAnimalMethod;
    if ((getReadAnimalMethod = AnimalServiceGrpc.getReadAnimalMethod) == null) {
      synchronized (AnimalServiceGrpc.class) {
        if ((getReadAnimalMethod = AnimalServiceGrpc.getReadAnimalMethod) == null) {
          AnimalServiceGrpc.getReadAnimalMethod = getReadAnimalMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalId, grpc.AnimalData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readAnimal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setSchemaDescriptor(new AnimalServiceMethodDescriptorSupplier("readAnimal"))
              .build();
        }
      }
    }
    return getReadAnimalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.AnimalData,
      grpc.EmptyMessage> getUpdateAnimalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateAnimal",
      requestType = grpc.AnimalData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.AnimalData,
      grpc.EmptyMessage> getUpdateAnimalMethod() {
    io.grpc.MethodDescriptor<grpc.AnimalData, grpc.EmptyMessage> getUpdateAnimalMethod;
    if ((getUpdateAnimalMethod = AnimalServiceGrpc.getUpdateAnimalMethod) == null) {
      synchronized (AnimalServiceGrpc.class) {
        if ((getUpdateAnimalMethod = AnimalServiceGrpc.getUpdateAnimalMethod) == null) {
          AnimalServiceGrpc.getUpdateAnimalMethod = getUpdateAnimalMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateAnimal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new AnimalServiceMethodDescriptorSupplier("updateAnimal"))
              .build();
        }
      }
    }
    return getUpdateAnimalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.AnimalData,
      grpc.EmptyMessage> getRemoveAnimalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "removeAnimal",
      requestType = grpc.AnimalData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.AnimalData,
      grpc.EmptyMessage> getRemoveAnimalMethod() {
    io.grpc.MethodDescriptor<grpc.AnimalData, grpc.EmptyMessage> getRemoveAnimalMethod;
    if ((getRemoveAnimalMethod = AnimalServiceGrpc.getRemoveAnimalMethod) == null) {
      synchronized (AnimalServiceGrpc.class) {
        if ((getRemoveAnimalMethod = AnimalServiceGrpc.getRemoveAnimalMethod) == null) {
          AnimalServiceGrpc.getRemoveAnimalMethod = getRemoveAnimalMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "removeAnimal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new AnimalServiceMethodDescriptorSupplier("removeAnimal"))
              .build();
        }
      }
    }
    return getRemoveAnimalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.AnimalsData> getGetAllAnimalsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllAnimals",
      requestType = grpc.EmptyMessage.class,
      responseType = grpc.AnimalsData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.AnimalsData> getGetAllAnimalsMethod() {
    io.grpc.MethodDescriptor<grpc.EmptyMessage, grpc.AnimalsData> getGetAllAnimalsMethod;
    if ((getGetAllAnimalsMethod = AnimalServiceGrpc.getGetAllAnimalsMethod) == null) {
      synchronized (AnimalServiceGrpc.class) {
        if ((getGetAllAnimalsMethod = AnimalServiceGrpc.getGetAllAnimalsMethod) == null) {
          AnimalServiceGrpc.getGetAllAnimalsMethod = getGetAllAnimalsMethod =
              io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.AnimalsData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllAnimals"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalsData.getDefaultInstance()))
              .setSchemaDescriptor(new AnimalServiceMethodDescriptorSupplier("getAllAnimals"))
              .build();
        }
      }
    }
    return getGetAllAnimalsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AnimalServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AnimalServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AnimalServiceStub>() {
        @java.lang.Override
        public AnimalServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AnimalServiceStub(channel, callOptions);
        }
      };
    return AnimalServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AnimalServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AnimalServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AnimalServiceBlockingStub>() {
        @java.lang.Override
        public AnimalServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AnimalServiceBlockingStub(channel, callOptions);
        }
      };
    return AnimalServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AnimalServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AnimalServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AnimalServiceFutureStub>() {
        @java.lang.Override
        public AnimalServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AnimalServiceFutureStub(channel, callOptions);
        }
      };
    return AnimalServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Create:
     * </pre>
     */
    default void registerAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterAnimalMethod(), responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    default void readAnimal(grpc.AnimalId request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadAnimalMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    default void updateAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateAnimalMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    default void removeAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveAnimalMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    default void getAllAnimals(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalsData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllAnimalsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AnimalService.
   */
  public static abstract class AnimalServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AnimalServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AnimalService.
   */
  public static final class AnimalServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AnimalServiceStub> {
    private AnimalServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimalServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AnimalServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public void registerAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterAnimalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public void readAnimal(grpc.AnimalId request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadAnimalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public void updateAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateAnimalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public void removeAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveAnimalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public void getAllAnimals(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalsData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllAnimalsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AnimalService.
   */
  public static final class AnimalServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AnimalServiceBlockingStub> {
    private AnimalServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimalServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AnimalServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public grpc.AnimalData registerAnimal(grpc.AnimalData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterAnimalMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public grpc.AnimalData readAnimal(grpc.AnimalId request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadAnimalMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public grpc.EmptyMessage updateAnimal(grpc.AnimalData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateAnimalMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public grpc.EmptyMessage removeAnimal(grpc.AnimalData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveAnimalMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public grpc.AnimalsData getAllAnimals(grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllAnimalsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AnimalService.
   */
  public static final class AnimalServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AnimalServiceFutureStub> {
    private AnimalServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AnimalServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AnimalServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalData> registerAnimal(
        grpc.AnimalData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterAnimalMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalData> readAnimal(
        grpc.AnimalId request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadAnimalMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> updateAnimal(
        grpc.AnimalData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateAnimalMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> removeAnimal(
        grpc.AnimalData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveAnimalMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalsData> getAllAnimals(
        grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllAnimalsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_ANIMAL = 0;
  private static final int METHODID_READ_ANIMAL = 1;
  private static final int METHODID_UPDATE_ANIMAL = 2;
  private static final int METHODID_REMOVE_ANIMAL = 3;
  private static final int METHODID_GET_ALL_ANIMALS = 4;

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
        case METHODID_REGISTER_ANIMAL:
          serviceImpl.registerAnimal((grpc.AnimalData) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalData>) responseObserver);
          break;
        case METHODID_READ_ANIMAL:
          serviceImpl.readAnimal((grpc.AnimalId) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalData>) responseObserver);
          break;
        case METHODID_UPDATE_ANIMAL:
          serviceImpl.updateAnimal((grpc.AnimalData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_REMOVE_ANIMAL:
          serviceImpl.removeAnimal((grpc.AnimalData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_GET_ALL_ANIMALS:
          serviceImpl.getAllAnimals((grpc.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalsData>) responseObserver);
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
          getRegisterAnimalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalData,
              grpc.AnimalData>(
                service, METHODID_REGISTER_ANIMAL)))
        .addMethod(
          getReadAnimalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalId,
              grpc.AnimalData>(
                service, METHODID_READ_ANIMAL)))
        .addMethod(
          getUpdateAnimalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalData,
              grpc.EmptyMessage>(
                service, METHODID_UPDATE_ANIMAL)))
        .addMethod(
          getRemoveAnimalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalData,
              grpc.EmptyMessage>(
                service, METHODID_REMOVE_ANIMAL)))
        .addMethod(
          getGetAllAnimalsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.EmptyMessage,
              grpc.AnimalsData>(
                service, METHODID_GET_ALL_ANIMALS)))
        .build();
  }

  private static abstract class AnimalServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AnimalServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.SlaughterHouseSim.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AnimalService");
    }
  }

  private static final class AnimalServiceFileDescriptorSupplier
      extends AnimalServiceBaseDescriptorSupplier {
    AnimalServiceFileDescriptorSupplier() {}
  }

  private static final class AnimalServiceMethodDescriptorSupplier
      extends AnimalServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AnimalServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AnimalServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AnimalServiceFileDescriptorSupplier())
              .addMethod(getRegisterAnimalMethod())
              .addMethod(getReadAnimalMethod())
              .addMethod(getUpdateAnimalMethod())
              .addMethod(getRemoveAnimalMethod())
              .addMethod(getGetAllAnimalsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
