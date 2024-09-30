package grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: SlaughterHouseSim.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SlaughterHouseServiceGrpc {

  private SlaughterHouseServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "grpc.SlaughterHouseService";

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
    if ((getRegisterAnimalMethod = SlaughterHouseServiceGrpc.getRegisterAnimalMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getRegisterAnimalMethod = SlaughterHouseServiceGrpc.getRegisterAnimalMethod) == null) {
          SlaughterHouseServiceGrpc.getRegisterAnimalMethod = getRegisterAnimalMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalData, grpc.AnimalData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerAnimal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("registerAnimal"))
              .build();
        }
      }
    }
    return getRegisterAnimalMethod;
  }

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
    if ((getRegisterAnimalPartMethod = SlaughterHouseServiceGrpc.getRegisterAnimalPartMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getRegisterAnimalPartMethod = SlaughterHouseServiceGrpc.getRegisterAnimalPartMethod) == null) {
          SlaughterHouseServiceGrpc.getRegisterAnimalPartMethod = getRegisterAnimalPartMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalPartData, grpc.AnimalPartData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerAnimalPart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartData.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("registerAnimalPart"))
              .build();
        }
      }
    }
    return getRegisterAnimalPartMethod;
  }

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
    if ((getRegisterProductMethod = SlaughterHouseServiceGrpc.getRegisterProductMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getRegisterProductMethod = SlaughterHouseServiceGrpc.getRegisterProductMethod) == null) {
          SlaughterHouseServiceGrpc.getRegisterProductMethod = getRegisterProductMethod =
              io.grpc.MethodDescriptor.<grpc.ProductData, grpc.ProductData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("registerProduct"))
              .build();
        }
      }
    }
    return getRegisterProductMethod;
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
    if ((getReadAnimalMethod = SlaughterHouseServiceGrpc.getReadAnimalMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getReadAnimalMethod = SlaughterHouseServiceGrpc.getReadAnimalMethod) == null) {
          SlaughterHouseServiceGrpc.getReadAnimalMethod = getReadAnimalMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalId, grpc.AnimalData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readAnimal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("readAnimal"))
              .build();
        }
      }
    }
    return getReadAnimalMethod;
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
    if ((getReadAnimalPartMethod = SlaughterHouseServiceGrpc.getReadAnimalPartMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getReadAnimalPartMethod = SlaughterHouseServiceGrpc.getReadAnimalPartMethod) == null) {
          SlaughterHouseServiceGrpc.getReadAnimalPartMethod = getReadAnimalPartMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalPartId, grpc.AnimalPartData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readAnimalPart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartData.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("readAnimalPart"))
              .build();
        }
      }
    }
    return getReadAnimalPartMethod;
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
    if ((getReadProductMethod = SlaughterHouseServiceGrpc.getReadProductMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getReadProductMethod = SlaughterHouseServiceGrpc.getReadProductMethod) == null) {
          SlaughterHouseServiceGrpc.getReadProductMethod = getReadProductMethod =
              io.grpc.MethodDescriptor.<grpc.ProductId, grpc.ProductData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "readProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("readProduct"))
              .build();
        }
      }
    }
    return getReadProductMethod;
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
    if ((getUpdateAnimalMethod = SlaughterHouseServiceGrpc.getUpdateAnimalMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getUpdateAnimalMethod = SlaughterHouseServiceGrpc.getUpdateAnimalMethod) == null) {
          SlaughterHouseServiceGrpc.getUpdateAnimalMethod = getUpdateAnimalMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateAnimal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("updateAnimal"))
              .build();
        }
      }
    }
    return getUpdateAnimalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.AnimalPartData,
      grpc.EmptyMessage> getUpdateAnimalPartMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateAnimalPart",
      requestType = grpc.AnimalPartData.class,
      responseType = grpc.EmptyMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.AnimalPartData,
      grpc.EmptyMessage> getUpdateAnimalPartMethod() {
    io.grpc.MethodDescriptor<grpc.AnimalPartData, grpc.EmptyMessage> getUpdateAnimalPartMethod;
    if ((getUpdateAnimalPartMethod = SlaughterHouseServiceGrpc.getUpdateAnimalPartMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getUpdateAnimalPartMethod = SlaughterHouseServiceGrpc.getUpdateAnimalPartMethod) == null) {
          SlaughterHouseServiceGrpc.getUpdateAnimalPartMethod = getUpdateAnimalPartMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalPartData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateAnimalPart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("updateAnimalPart"))
              .build();
        }
      }
    }
    return getUpdateAnimalPartMethod;
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
    if ((getUpdateProductMethod = SlaughterHouseServiceGrpc.getUpdateProductMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getUpdateProductMethod = SlaughterHouseServiceGrpc.getUpdateProductMethod) == null) {
          SlaughterHouseServiceGrpc.getUpdateProductMethod = getUpdateProductMethod =
              io.grpc.MethodDescriptor.<grpc.ProductData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("updateProduct"))
              .build();
        }
      }
    }
    return getUpdateProductMethod;
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
    if ((getRemoveAnimalMethod = SlaughterHouseServiceGrpc.getRemoveAnimalMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getRemoveAnimalMethod = SlaughterHouseServiceGrpc.getRemoveAnimalMethod) == null) {
          SlaughterHouseServiceGrpc.getRemoveAnimalMethod = getRemoveAnimalMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "removeAnimal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("removeAnimal"))
              .build();
        }
      }
    }
    return getRemoveAnimalMethod;
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
    if ((getRemoveAnimalPartMethod = SlaughterHouseServiceGrpc.getRemoveAnimalPartMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getRemoveAnimalPartMethod = SlaughterHouseServiceGrpc.getRemoveAnimalPartMethod) == null) {
          SlaughterHouseServiceGrpc.getRemoveAnimalPartMethod = getRemoveAnimalPartMethod =
              io.grpc.MethodDescriptor.<grpc.AnimalPartData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "removeAnimalPart"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("removeAnimalPart"))
              .build();
        }
      }
    }
    return getRemoveAnimalPartMethod;
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
    if ((getRemoveProductMethod = SlaughterHouseServiceGrpc.getRemoveProductMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getRemoveProductMethod = SlaughterHouseServiceGrpc.getRemoveProductMethod) == null) {
          SlaughterHouseServiceGrpc.getRemoveProductMethod = getRemoveProductMethod =
              io.grpc.MethodDescriptor.<grpc.ProductData, grpc.EmptyMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "removeProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("removeProduct"))
              .build();
        }
      }
    }
    return getRemoveProductMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.AnimalData> getGetAllAnimalsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllAnimals",
      requestType = grpc.EmptyMessage.class,
      responseType = grpc.AnimalData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.AnimalData> getGetAllAnimalsMethod() {
    io.grpc.MethodDescriptor<grpc.EmptyMessage, grpc.AnimalData> getGetAllAnimalsMethod;
    if ((getGetAllAnimalsMethod = SlaughterHouseServiceGrpc.getGetAllAnimalsMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getGetAllAnimalsMethod = SlaughterHouseServiceGrpc.getGetAllAnimalsMethod) == null) {
          SlaughterHouseServiceGrpc.getGetAllAnimalsMethod = getGetAllAnimalsMethod =
              io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.AnimalData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllAnimals"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalData.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("getAllAnimals"))
              .build();
        }
      }
    }
    return getGetAllAnimalsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.AnimalPartData> getGetAnimalPartsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAnimalParts",
      requestType = grpc.EmptyMessage.class,
      responseType = grpc.AnimalPartData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.AnimalPartData> getGetAnimalPartsMethod() {
    io.grpc.MethodDescriptor<grpc.EmptyMessage, grpc.AnimalPartData> getGetAnimalPartsMethod;
    if ((getGetAnimalPartsMethod = SlaughterHouseServiceGrpc.getGetAnimalPartsMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getGetAnimalPartsMethod = SlaughterHouseServiceGrpc.getGetAnimalPartsMethod) == null) {
          SlaughterHouseServiceGrpc.getGetAnimalPartsMethod = getGetAnimalPartsMethod =
              io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.AnimalPartData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAnimalParts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.AnimalPartData.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("getAnimalParts"))
              .build();
        }
      }
    }
    return getGetAnimalPartsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.ProductData> getGetAllProductsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllProducts",
      requestType = grpc.EmptyMessage.class,
      responseType = grpc.ProductData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.EmptyMessage,
      grpc.ProductData> getGetAllProductsMethod() {
    io.grpc.MethodDescriptor<grpc.EmptyMessage, grpc.ProductData> getGetAllProductsMethod;
    if ((getGetAllProductsMethod = SlaughterHouseServiceGrpc.getGetAllProductsMethod) == null) {
      synchronized (SlaughterHouseServiceGrpc.class) {
        if ((getGetAllProductsMethod = SlaughterHouseServiceGrpc.getGetAllProductsMethod) == null) {
          SlaughterHouseServiceGrpc.getGetAllProductsMethod = getGetAllProductsMethod =
              io.grpc.MethodDescriptor.<grpc.EmptyMessage, grpc.ProductData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllProducts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.EmptyMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.ProductData.getDefaultInstance()))
              .setSchemaDescriptor(new SlaughterHouseServiceMethodDescriptorSupplier("getAllProducts"))
              .build();
        }
      }
    }
    return getGetAllProductsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SlaughterHouseServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SlaughterHouseServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SlaughterHouseServiceStub>() {
        @java.lang.Override
        public SlaughterHouseServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SlaughterHouseServiceStub(channel, callOptions);
        }
      };
    return SlaughterHouseServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SlaughterHouseServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SlaughterHouseServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SlaughterHouseServiceBlockingStub>() {
        @java.lang.Override
        public SlaughterHouseServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SlaughterHouseServiceBlockingStub(channel, callOptions);
        }
      };
    return SlaughterHouseServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SlaughterHouseServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SlaughterHouseServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SlaughterHouseServiceFutureStub>() {
        @java.lang.Override
        public SlaughterHouseServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SlaughterHouseServiceFutureStub(channel, callOptions);
        }
      };
    return SlaughterHouseServiceFutureStub.newStub(factory, channel);
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
     */
    default void registerAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterAnimalPartMethod(), responseObserver);
    }

    /**
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
    default void readAnimal(grpc.AnimalId request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadAnimalMethod(), responseObserver);
    }

    /**
     */
    default void readAnimalPart(grpc.AnimalPartId request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReadAnimalPartMethod(), responseObserver);
    }

    /**
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
    default void updateAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateAnimalMethod(), responseObserver);
    }

    /**
     */
    default void updateAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateAnimalPartMethod(), responseObserver);
    }

    /**
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
    default void removeAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveAnimalMethod(), responseObserver);
    }

    /**
     */
    default void removeAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveAnimalPartMethod(), responseObserver);
    }

    /**
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
    default void getAllAnimals(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllAnimalsMethod(), responseObserver);
    }

    /**
     */
    default void getAnimalParts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAnimalPartsMethod(), responseObserver);
    }

    /**
     */
    default void getAllProducts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllProductsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SlaughterHouseService.
   */
  public static abstract class SlaughterHouseServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SlaughterHouseServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SlaughterHouseService.
   */
  public static final class SlaughterHouseServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SlaughterHouseServiceStub> {
    private SlaughterHouseServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SlaughterHouseServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SlaughterHouseServiceStub(channel, callOptions);
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
     */
    public void registerAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterAnimalPartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
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
    public void readAnimal(grpc.AnimalId request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadAnimalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void readAnimalPart(grpc.AnimalPartId request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReadAnimalPartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
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
    public void updateAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateAnimalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateAnimalPartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
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
    public void removeAnimal(grpc.AnimalData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveAnimalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void removeAnimalPart(grpc.AnimalPartData request,
        io.grpc.stub.StreamObserver<grpc.EmptyMessage> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveAnimalPartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
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
    public void getAllAnimals(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllAnimalsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAnimalParts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.AnimalPartData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAnimalPartsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllProducts(grpc.EmptyMessage request,
        io.grpc.stub.StreamObserver<grpc.ProductData> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllProductsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SlaughterHouseService.
   */
  public static final class SlaughterHouseServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SlaughterHouseServiceBlockingStub> {
    private SlaughterHouseServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SlaughterHouseServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SlaughterHouseServiceBlockingStub(channel, callOptions);
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
     */
    public grpc.AnimalPartData registerAnimalPart(grpc.AnimalPartData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterAnimalPartMethod(), getCallOptions(), request);
    }

    /**
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
    public grpc.AnimalData readAnimal(grpc.AnimalId request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadAnimalMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.AnimalPartData readAnimalPart(grpc.AnimalPartId request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReadAnimalPartMethod(), getCallOptions(), request);
    }

    /**
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
    public grpc.EmptyMessage updateAnimal(grpc.AnimalData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateAnimalMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.EmptyMessage updateAnimalPart(grpc.AnimalPartData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateAnimalPartMethod(), getCallOptions(), request);
    }

    /**
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
    public grpc.EmptyMessage removeAnimal(grpc.AnimalData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveAnimalMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.EmptyMessage removeAnimalPart(grpc.AnimalPartData request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveAnimalPartMethod(), getCallOptions(), request);
    }

    /**
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
    public grpc.AnimalData getAllAnimals(grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllAnimalsMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.AnimalPartData getAnimalParts(grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAnimalPartsMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.ProductData getAllProducts(grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllProductsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SlaughterHouseService.
   */
  public static final class SlaughterHouseServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SlaughterHouseServiceFutureStub> {
    private SlaughterHouseServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SlaughterHouseServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SlaughterHouseServiceFutureStub(channel, callOptions);
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
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalPartData> registerAnimalPart(
        grpc.AnimalPartData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterAnimalPartMethod(), getCallOptions()), request);
    }

    /**
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
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalData> readAnimal(
        grpc.AnimalId request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadAnimalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalPartData> readAnimalPart(
        grpc.AnimalPartId request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReadAnimalPartMethod(), getCallOptions()), request);
    }

    /**
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
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> updateAnimal(
        grpc.AnimalData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateAnimalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> updateAnimalPart(
        grpc.AnimalPartData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateAnimalPartMethod(), getCallOptions()), request);
    }

    /**
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
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> removeAnimal(
        grpc.AnimalData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveAnimalMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.EmptyMessage> removeAnimalPart(
        grpc.AnimalPartData request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveAnimalPartMethod(), getCallOptions()), request);
    }

    /**
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
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalData> getAllAnimals(
        grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllAnimalsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.AnimalPartData> getAnimalParts(
        grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAnimalPartsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.ProductData> getAllProducts(
        grpc.EmptyMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllProductsMethod(), getCallOptions()), request);
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
          getRegisterAnimalPartMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalPartData,
              grpc.AnimalPartData>(
                service, METHODID_REGISTER_ANIMAL_PART)))
        .addMethod(
          getRegisterProductMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.ProductData,
              grpc.ProductData>(
                service, METHODID_REGISTER_PRODUCT)))
        .addMethod(
          getReadAnimalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalId,
              grpc.AnimalData>(
                service, METHODID_READ_ANIMAL)))
        .addMethod(
          getReadAnimalPartMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalPartId,
              grpc.AnimalPartData>(
                service, METHODID_READ_ANIMAL_PART)))
        .addMethod(
          getReadProductMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.ProductId,
              grpc.ProductData>(
                service, METHODID_READ_PRODUCT)))
        .addMethod(
          getUpdateAnimalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalData,
              grpc.EmptyMessage>(
                service, METHODID_UPDATE_ANIMAL)))
        .addMethod(
          getUpdateAnimalPartMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalPartData,
              grpc.EmptyMessage>(
                service, METHODID_UPDATE_ANIMAL_PART)))
        .addMethod(
          getUpdateProductMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.ProductData,
              grpc.EmptyMessage>(
                service, METHODID_UPDATE_PRODUCT)))
        .addMethod(
          getRemoveAnimalMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalData,
              grpc.EmptyMessage>(
                service, METHODID_REMOVE_ANIMAL)))
        .addMethod(
          getRemoveAnimalPartMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.AnimalPartData,
              grpc.EmptyMessage>(
                service, METHODID_REMOVE_ANIMAL_PART)))
        .addMethod(
          getRemoveProductMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.ProductData,
              grpc.EmptyMessage>(
                service, METHODID_REMOVE_PRODUCT)))
        .addMethod(
          getGetAllAnimalsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.EmptyMessage,
              grpc.AnimalData>(
                service, METHODID_GET_ALL_ANIMALS)))
        .addMethod(
          getGetAnimalPartsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.EmptyMessage,
              grpc.AnimalPartData>(
                service, METHODID_GET_ANIMAL_PARTS)))
        .addMethod(
          getGetAllProductsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.EmptyMessage,
              grpc.ProductData>(
                service, METHODID_GET_ALL_PRODUCTS)))
        .build();
  }

  private static abstract class SlaughterHouseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SlaughterHouseServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.SlaughterHouseSim.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SlaughterHouseService");
    }
  }

  private static final class SlaughterHouseServiceFileDescriptorSupplier
      extends SlaughterHouseServiceBaseDescriptorSupplier {
    SlaughterHouseServiceFileDescriptorSupplier() {}
  }

  private static final class SlaughterHouseServiceMethodDescriptorSupplier
      extends SlaughterHouseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SlaughterHouseServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SlaughterHouseServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SlaughterHouseServiceFileDescriptorSupplier())
              .addMethod(getRegisterAnimalMethod())
              .addMethod(getRegisterAnimalPartMethod())
              .addMethod(getRegisterProductMethod())
              .addMethod(getReadAnimalMethod())
              .addMethod(getReadAnimalPartMethod())
              .addMethod(getReadProductMethod())
              .addMethod(getUpdateAnimalMethod())
              .addMethod(getUpdateAnimalPartMethod())
              .addMethod(getUpdateProductMethod())
              .addMethod(getRemoveAnimalMethod())
              .addMethod(getRemoveAnimalPartMethod())
              .addMethod(getRemoveProductMethod())
              .addMethod(getGetAllAnimalsMethod())
              .addMethod(getGetAnimalPartsMethod())
              .addMethod(getGetAllProductsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
