// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: SlaughterHouseSim.proto
// Protobuf Java Version: 4.28.2

package grpc;

/**
 * Protobuf type {@code grpc.PartTypeId}
 */
public final class PartTypeId extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:grpc.PartTypeId)
    PartTypeIdOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      PartTypeId.class.getName());
  }
  // Use PartTypeId.newBuilder() to construct.
  private PartTypeId(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private PartTypeId() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return grpc.SlaughterHouseSim.internal_static_grpc_PartTypeId_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return grpc.SlaughterHouseSim.internal_static_grpc_PartTypeId_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            grpc.PartTypeId.class, grpc.PartTypeId.Builder.class);
  }

  public static final int PARTTYPEID_FIELD_NUMBER = 1;
  private long partTypeId_ = 0L;
  /**
   * <code>int64 partTypeId = 1;</code>
   * @return The partTypeId.
   */
  @java.lang.Override
  public long getPartTypeId() {
    return partTypeId_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (partTypeId_ != 0L) {
      output.writeInt64(1, partTypeId_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (partTypeId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(1, partTypeId_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof grpc.PartTypeId)) {
      return super.equals(obj);
    }
    grpc.PartTypeId other = (grpc.PartTypeId) obj;

    if (getPartTypeId()
        != other.getPartTypeId()) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + PARTTYPEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getPartTypeId());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static grpc.PartTypeId parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.PartTypeId parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.PartTypeId parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.PartTypeId parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.PartTypeId parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.PartTypeId parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.PartTypeId parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static grpc.PartTypeId parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static grpc.PartTypeId parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static grpc.PartTypeId parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static grpc.PartTypeId parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static grpc.PartTypeId parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(grpc.PartTypeId prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code grpc.PartTypeId}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:grpc.PartTypeId)
      grpc.PartTypeIdOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return grpc.SlaughterHouseSim.internal_static_grpc_PartTypeId_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return grpc.SlaughterHouseSim.internal_static_grpc_PartTypeId_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              grpc.PartTypeId.class, grpc.PartTypeId.Builder.class);
    }

    // Construct using grpc.PartTypeId.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      partTypeId_ = 0L;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return grpc.SlaughterHouseSim.internal_static_grpc_PartTypeId_descriptor;
    }

    @java.lang.Override
    public grpc.PartTypeId getDefaultInstanceForType() {
      return grpc.PartTypeId.getDefaultInstance();
    }

    @java.lang.Override
    public grpc.PartTypeId build() {
      grpc.PartTypeId result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public grpc.PartTypeId buildPartial() {
      grpc.PartTypeId result = new grpc.PartTypeId(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(grpc.PartTypeId result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.partTypeId_ = partTypeId_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof grpc.PartTypeId) {
        return mergeFrom((grpc.PartTypeId)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(grpc.PartTypeId other) {
      if (other == grpc.PartTypeId.getDefaultInstance()) return this;
      if (other.getPartTypeId() != 0L) {
        setPartTypeId(other.getPartTypeId());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              partTypeId_ = input.readInt64();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private long partTypeId_ ;
    /**
     * <code>int64 partTypeId = 1;</code>
     * @return The partTypeId.
     */
    @java.lang.Override
    public long getPartTypeId() {
      return partTypeId_;
    }
    /**
     * <code>int64 partTypeId = 1;</code>
     * @param value The partTypeId to set.
     * @return This builder for chaining.
     */
    public Builder setPartTypeId(long value) {

      partTypeId_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>int64 partTypeId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearPartTypeId() {
      bitField0_ = (bitField0_ & ~0x00000001);
      partTypeId_ = 0L;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:grpc.PartTypeId)
  }

  // @@protoc_insertion_point(class_scope:grpc.PartTypeId)
  private static final grpc.PartTypeId DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new grpc.PartTypeId();
  }

  public static grpc.PartTypeId getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PartTypeId>
      PARSER = new com.google.protobuf.AbstractParser<PartTypeId>() {
    @java.lang.Override
    public PartTypeId parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<PartTypeId> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PartTypeId> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public grpc.PartTypeId getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
