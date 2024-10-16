package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: SlaughterHouseSim.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PartTypeServiceGrpc {

  private PartTypeServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "grpc.PartTypeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.PartTypeData,
      grpc.PartTypeData> getRegisterPartTypeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerPartType",
      requestType = grpc.PartTypeData.class,
      responseType = grpc.PartTypeData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.PartTypeData,
      grpc.PartTypeData> getRegisterPartTypeMethod() {
    io.grpc.MethodDescriptor<grpc.PartTypeData, grpc.PartTypeData> getRegisterPartTypeMethod;
    if ((getRegisterPartTypeMethod = PartTypeServiceGrpc.getRegisterPartTypeMethod) == null) {
      synchronized (PartTypeServiceGrpc.class) {
        if ((getRegisterPartTypeMethod = PartTypeServiceGrpc.getRegisterPartTypeMethod) == null) {
          PartTypeServiceGrpc.getRegisterPartTypeMethod = getRegisterPartTypeMethod =
              io.grpc.MethodDescriptor.<grpc.PartTypeData, grpc.PartTypeData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerPartType"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.PartTypeData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.PartTypeData.getDefaultInstance()))
              .setSchemaDescriptor(new PartTypeServiceMethodDescriptorSupplier("registerPartType"))
              .build();
        }
      }
    }
    return getRegisterPartTypeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.PartTypeId,
      grpc.PartTypeData> getReadPartTypeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readPartType",
      requestType = grpc.PartTypeId.class,
      responseType = grpc.PartTypeData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.PartTypeId,
      grpc.PartTypeData> getReadPartTypeMethod() {
    io.grpc.MethodDescriptor<grpc.PartTypeId, grpc.PartTypeData> getReadPartTypeMethod;
    if ((getReadPartTypeMethod = PartTypeServiceGrpc.getReadPartTypeMethod) == null) {
      synchronized (PartTypeServiceGrpc.class) {
        if ((getReadPartTypeMethod = PartTypeServiceGrpc.getReadPartTypeMethod) == null) {
          PartTypeServiceGrpc.getReadPartTypeMethod = getReadPartTypeMethod =
              io.grpc.MethodDescriptor.<grpc.PartTypeId, grpc.PartTypeData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readPartType"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.PartTypeId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.PartTypeData.getDefaultInstance()))
              .setSchemaDescriptor(new PartTypeServiceMethodDescriptorSupplier("readPartType"))
              .build();
        }
      }
    }
    return getReadPartTypeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.PartTypeData,
      grpc.EmptyMessage> getUpdatePartTypeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updatePartType",
      requestType = grpc.PartTypeData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.PartTypeData,
      grpc.EmptyMessage> getUpdatePartTypeMethod() {
    io.grpc.MethodDescriptor<grpc.PartTypeData, grpc.EmptyMessage> getUpdatePartTypeMethod;
    if ((getUpdatePartTypeMethod = PartTypeServiceGrpc.getUpdatePartTypeMethod) == null) {
      synchronized (PartTypeServiceGrpc.class) {
        if ((getUpdatePartTypeMethod = PartTypeServiceGrpc.getUpdatePartTypeMethod) == null) {
          PartTypeServiceGrpc.getUpdatePartTypeMethod = getUpdatePartTypeMethod =
              io.grpc.MethodDescriptor.<grpc.PartTypeData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updatePartType"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.PartTypeData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new PartTypeServiceMethodDescriptorSupplier("updatePartType"))
              .build();
        }
      }
    }
    return getUpdatePartTypeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.PartTypeData,
      grpc.EmptyMessage> getRemovePartTypeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "removePartType",
      requestType = grpc.PartTypeData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.PartTypeData,
      grpc.EmptyMessage> getRemovePartTypeMethod() {
    io.grpc.MethodDescriptor<grpc.PartTypeData, grpc.EmptyMessage> getRemovePartTypeMethod;
    if ((getRemovePartTypeMethod = PartTypeServiceGrpc.getRemovePartTypeMethod) == null) {
      synchronized (PartTypeServiceGrpc.class) {
        if ((getRemovePartTypeMethod = PartTypeServiceGrpc.getRemovePartTypeMethod) == null) {
          PartTypeServiceGrpc.getRemovePartTypeMethod = getRemovePartTypeMethod =
              io.grpc.MethodDescriptor.<grpc.PartTypeData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "removePartType"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.PartTypeData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new PartTypeServiceMethodDescriptorSupplier("removePartType"))
              .build();
        }
      }
    }
    return getRemovePartTypeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.PartTypesData> getGetAllPartTypesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllPartTypes",
      requestType = grpc.EmptyMessage.class,
      responseType = grpc.PartTypesData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.PartTypesData> getGetAllPartTypesMethod() {
    io.grpc.MethodDescriptor<grpc.EmptyMessage, grpc.PartTypesData> getGetAllPartTypesMethod;
    if ((getGetAllPartTypesMethod = PartTypeServiceGrpc.getGetAllPartTypesMethod) == null) {
      synchronized (PartTypeServiceGrpc.class) {
        if ((getGetAllPartTypesMethod = PartTypeServiceGrpc.getGetAllPartTypesMethod) == null) {
          PartTypeServiceGrpc.getGetAllPartTypesMethod = getGetAllPartTypesMethod =
              io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.PartTypesData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllPartTypes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.PartTypesData.getDefaultInstance()))
              .setSchemaDescriptor(new PartTypeServiceMethodDescriptorSupplier("getAllPartTypes"))
              .build();
        }
      }
    }
    return getGetAllPartTypesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PartTypeServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PartTypeServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PartTypeServiceStub>() {
        @java.lang.Override
        public PartTypeServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PartTypeServiceStub(channel, callOptions);
        }
      };
    return PartTypeServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PartTypeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PartTypeServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PartTypeServiceBlockingStub>() {
        @java.lang.Override
        public PartTypeServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PartTypeServiceBlockingStub(channel, callOptions);
        }
      };
    return PartTypeServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PartTypeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PartTypeServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PartTypeServiceFutureStub>() {
        @java.lang.Override
        public PartTypeServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PartTypeServiceFutureStub(channel, callOptions);
        }
      };
    return PartTypeServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Create:
     * </pre>
     */
    default void registerPartType(grpc.PartTypeData request,
        io.grpc.stub.StreamObserver<grpc.PartTypeData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterPartTypeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    default void readPartType(grpc.PartTypeId request,
        io.grpc.stub.StreamObserver<grpc.PartTypeData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadPartTypeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    default void updatePartType(grpc.PartTypeData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdatePartTypeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    default void removePartType(grpc.PartTypeData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemovePartTypeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    default void getAllPartTypes(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.PartTypesData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllPartTypesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PartTypeService.
   */
  public static abstract class PartTypeServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PartTypeServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PartTypeService.
   */
  public static final class PartTypeServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PartTypeServiceStub> {
    private PartTypeServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PartTypeServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PartTypeServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public void registerPartType(grpc.PartTypeData request,
        io.grpc.stub.StreamObserver<grpc.PartTypeData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterPartTypeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public void readPartType(grpc.PartTypeId request,
        io.grpc.stub.StreamObserver<grpc.PartTypeData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadPartTypeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public void updatePartType(grpc.PartTypeData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdatePartTypeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public void removePartType(grpc.PartTypeData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemovePartTypeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public void getAllPartTypes(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.PartTypesData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllPartTypesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PartTypeService.
   */
  public static final class PartTypeServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PartTypeServiceBlockingStub> {
    private PartTypeServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PartTypeServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PartTypeServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public grpc.PartTypeData registerPartType(grpc.PartTypeData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterPartTypeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public grpc.PartTypeData readPartType(grpc.PartTypeId request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadPartTypeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public grpc.EmptyMessage updatePartType(grpc.PartTypeData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdatePartTypeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public grpc.EmptyMessage removePartType(grpc.PartTypeData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemovePartTypeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public grpc.PartTypesData getAllPartTypes(grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllPartTypesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PartTypeService.
   */
  public static final class PartTypeServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PartTypeServiceFutureStub> {
    private PartTypeServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PartTypeServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PartTypeServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.PartTypeData> registerPartType(
        grpc.PartTypeData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterPartTypeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.PartTypeData> readPartType(
        grpc.PartTypeId request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadPartTypeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> updatePartType(
        grpc.PartTypeData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdatePartTypeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> removePartType(
        grpc.PartTypeData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemovePartTypeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.PartTypesData> getAllPartTypes(
        grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllPartTypesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_PART_TYPE = 0;
  private static final int METHODID_READ_PART_TYPE = 1;
  private static final int METHODID_UPDATE_PART_TYPE = 2;
  private static final int METHODID_REMOVE_PART_TYPE = 3;
  private static final int METHODID_GET_ALL_PART_TYPES = 4;

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
        case METHODID_REGISTER_PART_TYPE:
          serviceImpl.registerPartType((grpc.PartTypeData) request,
              (io.grpc.stub.StreamObserver<grpc.PartTypeData>) responseObserver);
          break;
        case METHODID_READ_PART_TYPE:
          serviceImpl.readPartType((grpc.PartTypeId) request,
              (io.grpc.stub.StreamObserver<grpc.PartTypeData>) responseObserver);
          break;
        case METHODID_UPDATE_PART_TYPE:
          serviceImpl.updatePartType((grpc.PartTypeData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_REMOVE_PART_TYPE:
          serviceImpl.removePartType((grpc.PartTypeData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_GET_ALL_PART_TYPES:
          serviceImpl.getAllPartTypes((grpc.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<grpc.PartTypesData>) responseObserver);
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
          getRegisterPartTypeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.PartTypeData,
              grpc.PartTypeData>(
                service, METHODID_REGISTER_PART_TYPE)))
        .addMethod(
          getReadPartTypeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.PartTypeId,
              grpc.PartTypeData>(
                service, METHODID_READ_PART_TYPE)))
        .addMethod(
          getUpdatePartTypeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.PartTypeData,
              grpc.EmptyMessage>(
                service, METHODID_UPDATE_PART_TYPE)))
        .addMethod(
          getRemovePartTypeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.PartTypeData,
              grpc.EmptyMessage>(
                service, METHODID_REMOVE_PART_TYPE)))
        .addMethod(
          getGetAllPartTypesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.EmptyMessage,
              grpc.PartTypesData>(
                service, METHODID_GET_ALL_PART_TYPES)))
        .build();
  }

  private static abstract class PartTypeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PartTypeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.SlaughterHouseSim.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PartTypeService");
    }
  }

  private static final class PartTypeServiceFileDescriptorSupplier
      extends PartTypeServiceBaseDescriptorSupplier {
    PartTypeServiceFileDescriptorSupplier() {}
  }

  private static final class PartTypeServiceMethodDescriptorSupplier
      extends PartTypeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PartTypeServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (PartTypeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PartTypeServiceFileDescriptorSupplier())
              .addMethod(getRegisterPartTypeMethod())
              .addMethod(getReadPartTypeMethod())
              .addMethod(getUpdatePartTypeMethod())
              .addMethod(getRemovePartTypeMethod())
              .addMethod(getGetAllPartTypesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
