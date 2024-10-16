package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: SlaughterHouseSim.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProductServiceGrpc {

  private ProductServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "grpc.ProductService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.ProductData,
      grpc.ProductData> getRegisterProductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerProduct",
      requestType = grpc.ProductData.class,
      responseType = grpc.ProductData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.ProductData,
      grpc.ProductData> getRegisterProductMethod() {
    io.grpc.MethodDescriptor<grpc.ProductData, grpc.ProductData> getRegisterProductMethod;
    if ((getRegisterProductMethod = ProductServiceGrpc.getRegisterProductMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getRegisterProductMethod = ProductServiceGrpc.getRegisterProductMethod) == null) {
          ProductServiceGrpc.getRegisterProductMethod = getRegisterProductMethod =
              io.grpc.MethodDescriptor.<grpc.ProductData, grpc.ProductData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("registerProduct"))
              .build();
        }
      }
    }
    return getRegisterProductMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.ProductId,
      grpc.ProductData> getReadProductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "readProduct",
      requestType = grpc.ProductId.class,
      responseType = grpc.ProductData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.ProductId,
      grpc.ProductData> getReadProductMethod() {
    io.grpc.MethodDescriptor<grpc.ProductId, grpc.ProductData> getReadProductMethod;
    if ((getReadProductMethod = ProductServiceGrpc.getReadProductMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getReadProductMethod = ProductServiceGrpc.getReadProductMethod) == null) {
          ProductServiceGrpc.getReadProductMethod = getReadProductMethod =
              io.grpc.MethodDescriptor.<grpc.ProductId, grpc.ProductData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("readProduct"))
              .build();
        }
      }
    }
    return getReadProductMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.ProductData,
      grpc.EmptyMessage> getUpdateProductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateProduct",
      requestType = grpc.ProductData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.ProductData,
      grpc.EmptyMessage> getUpdateProductMethod() {
    io.grpc.MethodDescriptor<grpc.ProductData, grpc.EmptyMessage> getUpdateProductMethod;
    if ((getUpdateProductMethod = ProductServiceGrpc.getUpdateProductMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getUpdateProductMethod = ProductServiceGrpc.getUpdateProductMethod) == null) {
          ProductServiceGrpc.getUpdateProductMethod = getUpdateProductMethod =
              io.grpc.MethodDescriptor.<grpc.ProductData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("updateProduct"))
              .build();
        }
      }
    }
    return getUpdateProductMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.ProductData,
      grpc.EmptyMessage> getRemoveProductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "removeProduct",
      requestType = grpc.ProductData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.ProductData,
      grpc.EmptyMessage> getRemoveProductMethod() {
    io.grpc.MethodDescriptor<grpc.ProductData, grpc.EmptyMessage> getRemoveProductMethod;
    if ((getRemoveProductMethod = ProductServiceGrpc.getRemoveProductMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getRemoveProductMethod = ProductServiceGrpc.getRemoveProductMethod) == null) {
          ProductServiceGrpc.getRemoveProductMethod = getRemoveProductMethod =
              io.grpc.MethodDescriptor.<grpc.ProductData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "removeProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("removeProduct"))
              .build();
        }
      }
    }
    return getRemoveProductMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.ProductsData> getGetAllProductsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllProducts",
      requestType = grpc.EmptyMessage.class,
      responseType = grpc.ProductsData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.ProductsData> getGetAllProductsMethod() {
    io.grpc.MethodDescriptor<grpc.EmptyMessage, grpc.ProductsData> getGetAllProductsMethod;
    if ((getGetAllProductsMethod = ProductServiceGrpc.getGetAllProductsMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getGetAllProductsMethod = ProductServiceGrpc.getGetAllProductsMethod) == null) {
          ProductServiceGrpc.getGetAllProductsMethod = getGetAllProductsMethod =
              io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.ProductsData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllProducts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductsData.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("getAllProducts"))
              .build();
        }
      }
    }
    return getGetAllProductsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductServiceStub>() {
        @java.lang.Override
        public ProductServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductServiceStub(channel, callOptions);
        }
      };
    return ProductServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductServiceBlockingStub>() {
        @java.lang.Override
        public ProductServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductServiceBlockingStub(channel, callOptions);
        }
      };
    return ProductServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductServiceFutureStub>() {
        @java.lang.Override
        public ProductServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductServiceFutureStub(channel, callOptions);
        }
      };
    return ProductServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     * Create:
     * </pre>
     */
    default void registerProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterProductMethod(), responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    default void readProduct(grpc.ProductId request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadProductMethod(), responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    default void updateProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateProductMethod(), responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    default void removeProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveProductMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    default void getAllProducts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.ProductsData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllProductsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ProductService.
   */
  public static abstract class ProductServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ProductServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ProductService.
   */
  public static final class ProductServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ProductServiceStub> {
    private ProductServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public void registerProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterProductMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public void readProduct(grpc.ProductId request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadProductMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public void updateProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateProductMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public void removeProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveProductMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public void getAllProducts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.ProductsData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllProductsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ProductService.
   */
  public static final class ProductServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ProductServiceBlockingStub> {
    private ProductServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public grpc.ProductData registerProduct(grpc.ProductData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterProductMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public grpc.ProductData readProduct(grpc.ProductId request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadProductMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public grpc.EmptyMessage updateProduct(grpc.ProductData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateProductMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public grpc.EmptyMessage removeProduct(grpc.ProductData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveProductMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public grpc.ProductsData getAllProducts(grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllProductsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ProductService.
   */
  public static final class ProductServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ProductServiceFutureStub> {
    private ProductServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.ProductData> registerProduct(
        grpc.ProductData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterProductMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.ProductData> readProduct(
        grpc.ProductId request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadProductMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> updateProduct(
        grpc.ProductData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateProductMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> removeProduct(
        grpc.ProductData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveProductMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.ProductsData> getAllProducts(
        grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllProductsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_PRODUCT = 0;
  private static final int METHODID_READ_PRODUCT = 1;
  private static final int METHODID_UPDATE_PRODUCT = 2;
  private static final int METHODID_REMOVE_PRODUCT = 3;
  private static final int METHODID_GET_ALL_PRODUCTS = 4;

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
        case METHODID_REGISTER_PRODUCT:
          serviceImpl.registerProduct((grpc.ProductData) request,
              (io.grpc.stub.StreamObserver<grpc.ProductData>) responseObserver);
          break;
        case METHODID_READ_PRODUCT:
          serviceImpl.readProduct((grpc.ProductId) request,
              (io.grpc.stub.StreamObserver<grpc.ProductData>) responseObserver);
          break;
        case METHODID_UPDATE_PRODUCT:
          serviceImpl.updateProduct((grpc.ProductData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_REMOVE_PRODUCT:
          serviceImpl.removeProduct((grpc.ProductData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_GET_ALL_PRODUCTS:
          serviceImpl.getAllProducts((grpc.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<grpc.ProductsData>) responseObserver);
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
          getRegisterProductMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.ProductData,
              grpc.ProductData>(
                service, METHODID_REGISTER_PRODUCT)))
        .addMethod(
          getReadProductMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.ProductId,
              grpc.ProductData>(
                service, METHODID_READ_PRODUCT)))
        .addMethod(
          getUpdateProductMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.ProductData,
              grpc.EmptyMessage>(
                service, METHODID_UPDATE_PRODUCT)))
        .addMethod(
          getRemoveProductMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.ProductData,
              grpc.EmptyMessage>(
                service, METHODID_REMOVE_PRODUCT)))
        .addMethod(
          getGetAllProductsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.EmptyMessage,
              grpc.ProductsData>(
                service, METHODID_GET_ALL_PRODUCTS)))
        .build();
  }

  private static abstract class ProductServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.SlaughterHouseSim.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProductService");
    }
  }

  private static final class ProductServiceFileDescriptorSupplier
      extends ProductServiceBaseDescriptorSupplier {
    ProductServiceFileDescriptorSupplier() {}
  }

  private static final class ProductServiceMethodDescriptorSupplier
      extends ProductServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ProductServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ProductServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductServiceFileDescriptorSupplier())
              .addMethod(getRegisterProductMethod())
              .addMethod(getReadProductMethod())
              .addMethod(getUpdateProductMethod())
              .addMethod(getRemoveProductMethod())
              .addMethod(getGetAllProductsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
