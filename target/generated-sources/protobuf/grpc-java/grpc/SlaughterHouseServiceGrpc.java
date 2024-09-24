package grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: SlaughterHouseSim.proto")
public class SlaughterHouseServiceGrpc {

  protected SlaughterHouseServiceGrpc() {}

  public static final String SERVICE_NAME = "grpc.SlaughterHouseService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.AnimalData,
      grpc.AnimalData> METHOD_REGISTER_ANIMAL =
      io.grpc.MethodDescriptor.<grpc.AnimalData, grpc.AnimalData>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "registerAnimal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalData.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalData.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.AnimalPartData,
      grpc.AnimalPartData> METHOD_REGISTER_ANIMAL_PART =
      io.grpc.MethodDescriptor.<grpc.AnimalPartData, grpc.AnimalPartData>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "registerAnimalPart"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalPartData.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalPartData.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.ProductData,
      grpc.ProductData> METHOD_REGISTER_PRODUCT =
      io.grpc.MethodDescriptor.<grpc.ProductData, grpc.ProductData>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "registerProduct"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.ProductData.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.ProductData.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.AnimalId,
      grpc.AnimalData> METHOD_READ_ANIMAL =
      io.grpc.MethodDescriptor.<grpc.AnimalId, grpc.AnimalData>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "readAnimal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalId.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalData.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.AnimalPartId,
      grpc.AnimalPartData> METHOD_READ_ANIMAL_PART =
      io.grpc.MethodDescriptor.<grpc.AnimalPartId, grpc.AnimalPartData>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "readAnimalPart"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalPartId.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalPartData.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.ProductId,
      grpc.ProductData> METHOD_READ_PRODUCT =
      io.grpc.MethodDescriptor.<grpc.ProductId, grpc.ProductData>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "readProduct"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.ProductId.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.ProductData.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.AnimalData,
      grpc.EmptyMessage> METHOD_UPDATE_ANIMAL =
      io.grpc.MethodDescriptor.<grpc.AnimalData, grpc.EmptyMessage>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "updateAnimal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalData.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.EmptyMessage.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.AnimalPartData,
      grpc.EmptyMessage> METHOD_UPDATE_ANIMAL_PART =
      io.grpc.MethodDescriptor.<grpc.AnimalPartData, grpc.EmptyMessage>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "updateAnimalPart"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalPartData.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.EmptyMessage.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.ProductData,
      grpc.EmptyMessage> METHOD_UPDATE_PRODUCT =
      io.grpc.MethodDescriptor.<grpc.ProductData, grpc.EmptyMessage>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "updateProduct"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.ProductData.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.EmptyMessage.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.AnimalData,
      grpc.EmptyMessage> METHOD_REMOVE_ANIMAL =
      io.grpc.MethodDescriptor.<grpc.AnimalData, grpc.EmptyMessage>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "removeAnimal"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalData.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.EmptyMessage.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.AnimalPartData,
      grpc.EmptyMessage> METHOD_REMOVE_ANIMAL_PART =
      io.grpc.MethodDescriptor.<grpc.AnimalPartData, grpc.EmptyMessage>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "removeAnimalPart"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalPartData.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.EmptyMessage.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.ProductData,
      grpc.EmptyMessage> METHOD_REMOVE_PRODUCT =
      io.grpc.MethodDescriptor.<grpc.ProductData, grpc.EmptyMessage>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "removeProduct"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.ProductData.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.EmptyMessage.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.AnimalData> METHOD_GET_ALL_ANIMALS =
      io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.AnimalData>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "getAllAnimals"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.EmptyMessage.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalData.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.AnimalPartData> METHOD_GET_ANIMAL_PARTS =
      io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.AnimalPartData>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "getAnimalParts"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.EmptyMessage.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.AnimalPartData.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.ProductData> METHOD_GET_ALL_PRODUCTS =
      io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.ProductData>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "grpc.SlaughterHouseService", "getAllProducts"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.EmptyMessage.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              grpc.ProductData.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SlaughterHouseServiceStub newStub(io.grpc.Channel channel) {
    return new SlaughterHouseServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SlaughterHouseServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SlaughterHouseServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SlaughterHouseServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SlaughterHouseServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SlaughterHouseServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public void registerAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REGISTER_ANIMAL, responseObserver);
    }

    /**
     */
    public void registerAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REGISTER_ANIMAL_PART, responseObserver);
    }

    /**
     */
    public void registerProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REGISTER_PRODUCT, responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public void readAnimal(grpc.AnimalId request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_READ_ANIMAL, responseObserver);
    }

    /**
     */
    public void readAnimalPart(grpc.AnimalPartId request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_READ_ANIMAL_PART, responseObserver);
    }

    /**
     */
    public void readProduct(grpc.ProductId request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_READ_PRODUCT, responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public void updateAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_ANIMAL, responseObserver);
    }

    /**
     */
    public void updateAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_ANIMAL_PART, responseObserver);
    }

    /**
     */
    public void updateProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_UPDATE_PRODUCT, responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public void removeAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REMOVE_ANIMAL, responseObserver);
    }

    /**
     */
    public void removeAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REMOVE_ANIMAL_PART, responseObserver);
    }

    /**
     */
    public void removeProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REMOVE_PRODUCT, responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public void getAllAnimals(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ALL_ANIMALS, responseObserver);
    }

    /**
     */
    public void getAnimalParts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ANIMAL_PARTS, responseObserver);
    }

    /**
     */
    public void getAllProducts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_ALL_PRODUCTS, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_REGISTER_ANIMAL,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.AnimalData,
                grpc.AnimalData>(
                  this, METHODID_REGISTER_ANIMAL)))
          .addMethod(
            METHOD_REGISTER_ANIMAL_PART,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.AnimalPartData,
                grpc.AnimalPartData>(
                  this, METHODID_REGISTER_ANIMAL_PART)))
          .addMethod(
            METHOD_REGISTER_PRODUCT,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.ProductData,
                grpc.ProductData>(
                  this, METHODID_REGISTER_PRODUCT)))
          .addMethod(
            METHOD_READ_ANIMAL,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.AnimalId,
                grpc.AnimalData>(
                  this, METHODID_READ_ANIMAL)))
          .addMethod(
            METHOD_READ_ANIMAL_PART,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.AnimalPartId,
                grpc.AnimalPartData>(
                  this, METHODID_READ_ANIMAL_PART)))
          .addMethod(
            METHOD_READ_PRODUCT,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.ProductId,
                grpc.ProductData>(
                  this, METHODID_READ_PRODUCT)))
          .addMethod(
            METHOD_UPDATE_ANIMAL,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.AnimalData,
                grpc.EmptyMessage>(
                  this, METHODID_UPDATE_ANIMAL)))
          .addMethod(
            METHOD_UPDATE_ANIMAL_PART,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.AnimalPartData,
                grpc.EmptyMessage>(
                  this, METHODID_UPDATE_ANIMAL_PART)))
          .addMethod(
            METHOD_UPDATE_PRODUCT,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.ProductData,
                grpc.EmptyMessage>(
                  this, METHODID_UPDATE_PRODUCT)))
          .addMethod(
            METHOD_REMOVE_ANIMAL,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.AnimalData,
                grpc.EmptyMessage>(
                  this, METHODID_REMOVE_ANIMAL)))
          .addMethod(
            METHOD_REMOVE_ANIMAL_PART,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.AnimalPartData,
                grpc.EmptyMessage>(
                  this, METHODID_REMOVE_ANIMAL_PART)))
          .addMethod(
            METHOD_REMOVE_PRODUCT,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.ProductData,
                grpc.EmptyMessage>(
                  this, METHODID_REMOVE_PRODUCT)))
          .addMethod(
            METHOD_GET_ALL_ANIMALS,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.EmptyMessage,
                grpc.AnimalData>(
                  this, METHODID_GET_ALL_ANIMALS)))
          .addMethod(
            METHOD_GET_ANIMAL_PARTS,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.EmptyMessage,
                grpc.AnimalPartData>(
                  this, METHODID_GET_ANIMAL_PARTS)))
          .addMethod(
            METHOD_GET_ALL_PRODUCTS,
            asyncUnaryCall(
              new MethodHandlers<
                grpc.EmptyMessage,
                grpc.ProductData>(
                  this, METHODID_GET_ALL_PRODUCTS)))
          .build();
    }
  }

  /**
   */
  public static final class SlaughterHouseServiceStub extends io.grpc.stub.AbstractStub<SlaughterHouseServiceStub> {
    private SlaughterHouseServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SlaughterHouseServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SlaughterHouseServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SlaughterHouseServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public void registerAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REGISTER_ANIMAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REGISTER_ANIMAL_PART, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REGISTER_PRODUCT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public void readAnimal(grpc.AnimalId request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_READ_ANIMAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readAnimalPart(grpc.AnimalPartId request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_READ_ANIMAL_PART, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readProduct(grpc.ProductId request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_READ_PRODUCT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public void updateAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_ANIMAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_ANIMAL_PART, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_UPDATE_PRODUCT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public void removeAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REMOVE_ANIMAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void removeAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REMOVE_ANIMAL_PART, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void removeProduct(grpc.ProductData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_REMOVE_PRODUCT, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public void getAllAnimals(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ALL_ANIMALS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAnimalParts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ANIMAL_PARTS, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllProducts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_ALL_PRODUCTS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SlaughterHouseServiceBlockingStub extends io.grpc.stub.AbstractStub<SlaughterHouseServiceBlockingStub> {
    private SlaughterHouseServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SlaughterHouseServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SlaughterHouseServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SlaughterHouseServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public grpc.AnimalData registerAnimal(grpc.AnimalData request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REGISTER_ANIMAL, getCallOptions(), request);
    }

    /**
     */
    public grpc.AnimalPartData registerAnimalPart(grpc.AnimalPartData request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REGISTER_ANIMAL_PART, getCallOptions(), request);
    }

    /**
     */
    public grpc.ProductData registerProduct(grpc.ProductData request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REGISTER_PRODUCT, getCallOptions(), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public grpc.AnimalData readAnimal(grpc.AnimalId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_READ_ANIMAL, getCallOptions(), request);
    }

    /**
     */
    public grpc.AnimalPartData readAnimalPart(grpc.AnimalPartId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_READ_ANIMAL_PART, getCallOptions(), request);
    }

    /**
     */
    public grpc.ProductData readProduct(grpc.ProductId request) {
      return blockingUnaryCall(
          getChannel(), METHOD_READ_PRODUCT, getCallOptions(), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public grpc.EmptyMessage updateAnimal(grpc.AnimalData request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_ANIMAL, getCallOptions(), request);
    }

    /**
     */
    public grpc.EmptyMessage updateAnimalPart(grpc.AnimalPartData request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_ANIMAL_PART, getCallOptions(), request);
    }

    /**
     */
    public grpc.EmptyMessage updateProduct(grpc.ProductData request) {
      return blockingUnaryCall(
          getChannel(), METHOD_UPDATE_PRODUCT, getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public grpc.EmptyMessage removeAnimal(grpc.AnimalData request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REMOVE_ANIMAL, getCallOptions(), request);
    }

    /**
     */
    public grpc.EmptyMessage removeAnimalPart(grpc.AnimalPartData request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REMOVE_ANIMAL_PART, getCallOptions(), request);
    }

    /**
     */
    public grpc.EmptyMessage removeProduct(grpc.ProductData request) {
      return blockingUnaryCall(
          getChannel(), METHOD_REMOVE_PRODUCT, getCallOptions(), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public grpc.AnimalData getAllAnimals(grpc.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ALL_ANIMALS, getCallOptions(), request);
    }

    /**
     */
    public grpc.AnimalPartData getAnimalParts(grpc.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ANIMAL_PARTS, getCallOptions(), request);
    }

    /**
     */
    public grpc.ProductData getAllProducts(grpc.EmptyMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_ALL_PRODUCTS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SlaughterHouseServiceFutureStub extends io.grpc.stub.AbstractStub<SlaughterHouseServiceFutureStub> {
    private SlaughterHouseServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SlaughterHouseServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SlaughterHouseServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SlaughterHouseServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Create:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalData> registerAnimal(
        grpc.AnimalData request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REGISTER_ANIMAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalPartData> registerAnimalPart(
        grpc.AnimalPartData request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REGISTER_ANIMAL_PART, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.ProductData> registerProduct(
        grpc.ProductData request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REGISTER_PRODUCT, getCallOptions()), request);
    }

    /**
     * <pre>
     * Read:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalData> readAnimal(
        grpc.AnimalId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_READ_ANIMAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalPartData> readAnimalPart(
        grpc.AnimalPartId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_READ_ANIMAL_PART, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.ProductData> readProduct(
        grpc.ProductId request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_READ_PRODUCT, getCallOptions()), request);
    }

    /**
     * <pre>
     * Update:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> updateAnimal(
        grpc.AnimalData request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_ANIMAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> updateAnimalPart(
        grpc.AnimalPartData request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_ANIMAL_PART, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> updateProduct(
        grpc.ProductData request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_UPDATE_PRODUCT, getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> removeAnimal(
        grpc.AnimalData request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REMOVE_ANIMAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> removeAnimalPart(
        grpc.AnimalPartData request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REMOVE_ANIMAL_PART, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> removeProduct(
        grpc.ProductData request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_REMOVE_PRODUCT, getCallOptions()), request);
    }

    /**
     * <pre>
     * Get All:
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalData> getAllAnimals(
        grpc.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ALL_ANIMALS, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalPartData> getAnimalParts(
        grpc.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ANIMAL_PARTS, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.ProductData> getAllProducts(
        grpc.EmptyMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_ALL_PRODUCTS, getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_ANIMAL = 0;
  private static final int METHODID_REGISTER_ANIMAL_PART = 1;
  private static final int METHODID_REGISTER_PRODUCT = 2;
  private static final int METHODID_READ_ANIMAL = 3;
  private static final int METHODID_READ_ANIMAL_PART = 4;
  private static final int METHODID_READ_PRODUCT = 5;
  private static final int METHODID_UPDATE_ANIMAL = 6;
  private static final int METHODID_UPDATE_ANIMAL_PART = 7;
  private static final int METHODID_UPDATE_PRODUCT = 8;
  private static final int METHODID_REMOVE_ANIMAL = 9;
  private static final int METHODID_REMOVE_ANIMAL_PART = 10;
  private static final int METHODID_REMOVE_PRODUCT = 11;
  private static final int METHODID_GET_ALL_ANIMALS = 12;
  private static final int METHODID_GET_ANIMAL_PARTS = 13;
  private static final int METHODID_GET_ALL_PRODUCTS = 14;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SlaughterHouseServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SlaughterHouseServiceImplBase serviceImpl, int methodId) {
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
        case METHODID_REGISTER_ANIMAL_PART:
          serviceImpl.registerAnimalPart((grpc.AnimalPartData) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalPartData>) responseObserver);
          break;
        case METHODID_REGISTER_PRODUCT:
          serviceImpl.registerProduct((grpc.ProductData) request,
              (io.grpc.stub.StreamObserver<grpc.ProductData>) responseObserver);
          break;
        case METHODID_READ_ANIMAL:
          serviceImpl.readAnimal((grpc.AnimalId) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalData>) responseObserver);
          break;
        case METHODID_READ_ANIMAL_PART:
          serviceImpl.readAnimalPart((grpc.AnimalPartId) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalPartData>) responseObserver);
          break;
        case METHODID_READ_PRODUCT:
          serviceImpl.readProduct((grpc.ProductId) request,
              (io.grpc.stub.StreamObserver<grpc.ProductData>) responseObserver);
          break;
        case METHODID_UPDATE_ANIMAL:
          serviceImpl.updateAnimal((grpc.AnimalData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_UPDATE_ANIMAL_PART:
          serviceImpl.updateAnimalPart((grpc.AnimalPartData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_UPDATE_PRODUCT:
          serviceImpl.updateProduct((grpc.ProductData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_REMOVE_ANIMAL:
          serviceImpl.removeAnimal((grpc.AnimalData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_REMOVE_ANIMAL_PART:
          serviceImpl.removeAnimalPart((grpc.AnimalPartData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_REMOVE_PRODUCT:
          serviceImpl.removeProduct((grpc.ProductData) request,
              (io.grpc.stub.StreamObserver<grpc.EmptyMessage>) responseObserver);
          break;
        case METHODID_GET_ALL_ANIMALS:
          serviceImpl.getAllAnimals((grpc.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalData>) responseObserver);
          break;
        case METHODID_GET_ANIMAL_PARTS:
          serviceImpl.getAnimalParts((grpc.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<grpc.AnimalPartData>) responseObserver);
          break;
        case METHODID_GET_ALL_PRODUCTS:
          serviceImpl.getAllProducts((grpc.EmptyMessage) request,
              (io.grpc.stub.StreamObserver<grpc.ProductData>) responseObserver);
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

  private static final class SlaughterHouseServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.SlaughterHouseSim.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SlaughterHouseServiceDescriptorSupplier())
              .addMethod(METHOD_REGISTER_ANIMAL)
              .addMethod(METHOD_REGISTER_ANIMAL_PART)
              .addMethod(METHOD_REGISTER_PRODUCT)
              .addMethod(METHOD_READ_ANIMAL)
              .addMethod(METHOD_READ_ANIMAL_PART)
              .addMethod(METHOD_READ_PRODUCT)
              .addMethod(METHOD_UPDATE_ANIMAL)
              .addMethod(METHOD_UPDATE_ANIMAL_PART)
              .addMethod(METHOD_UPDATE_PRODUCT)
              .addMethod(METHOD_REMOVE_ANIMAL)
              .addMethod(METHOD_REMOVE_ANIMAL_PART)
              .addMethod(METHOD_REMOVE_PRODUCT)
              .addMethod(METHOD_GET_ALL_ANIMALS)
              .addMethod(METHOD_GET_ANIMAL_PARTS)
              .addMethod(METHOD_GET_ALL_PRODUCTS)
              .build();
        }
      }
    }
    return result;
  }
}
