// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: SlaughterHouseSim.proto
// Protobuf Java Version: 4.28.2

package grpc;

/**
 * Protobuf type {@code grpc.UpdatedAnimalPartData}
 */
public final class UpdatedAnimalPartData extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:grpc.UpdatedAnimalPartData)
    UpdatedAnimalPartDataOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      UpdatedAnimalPartData.class.getName());
  }
  // Use UpdatedAnimalPartData.newBuilder() to construct.
  private UpdatedAnimalPartData(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private UpdatedAnimalPartData() {
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return grpc.SlaughterHouseSim.internal_static_grpc_UpdatedAnimalPartData_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return grpc.SlaughterHouseSim.internal_static_grpc_UpdatedAnimalPartData_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            grpc.UpdatedAnimalPartData.class, grpc.UpdatedAnimalPartData.Builder.class);
  }

  private int bitField0_;
  public static final int OLDDATA_FIELD_NUMBER = 1;
  private grpc.AnimalPartData oldData_;
  /**
   * <code>.grpc.AnimalPartData oldData = 1;</code>
   * @return Whether the oldData field is set.
   */
  @java.lang.Override
  public boolean hasOldData() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>.grpc.AnimalPartData oldData = 1;</code>
   * @return The oldData.
   */
  @java.lang.Override
  public grpc.AnimalPartData getOldData() {
    return oldData_ == null ? grpc.AnimalPartData.getDefaultInstance() : oldData_;
  }
  /**
   * <code>.grpc.AnimalPartData oldData = 1;</code>
   */
  @java.lang.Override
  public grpc.AnimalPartDataOrBuilder getOldDataOrBuilder() {
    return oldData_ == null ? grpc.AnimalPartData.getDefaultInstance() : oldData_;
  }

  public static final int NEWDATA_FIELD_NUMBER = 2;
  private grpc.AnimalPartData newData_;
  /**
   * <code>.grpc.AnimalPartData newData = 2;</code>
   * @return Whether the newData field is set.
   */
  @java.lang.Override
  public boolean hasNewData() {
    return ((bitField0_ & 0x00000002) != 0);
  }
  /**
   * <code>.grpc.AnimalPartData newData = 2;</code>
   * @return The newData.
   */
  @java.lang.Override
  public grpc.AnimalPartData getNewData() {
    return newData_ == null ? grpc.AnimalPartData.getDefaultInstance() : newData_;
  }
  /**
   * <code>.grpc.AnimalPartData newData = 2;</code>
   */
  @java.lang.Override
  public grpc.AnimalPartDataOrBuilder getNewDataOrBuilder() {
    return newData_ == null ? grpc.AnimalPartData.getDefaultInstance() : newData_;
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
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(1, getOldData());
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      output.writeMessage(2, getNewData());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getOldData());
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getNewData());
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
    if (!(obj instanceof grpc.UpdatedAnimalPartData)) {
      return super.equals(obj);
    }
    grpc.UpdatedAnimalPartData other = (grpc.UpdatedAnimalPartData) obj;

    if (hasOldData() != other.hasOldData()) return false;
    if (hasOldData()) {
      if (!getOldData()
          .equals(other.getOldData())) return false;
    }
    if (hasNewData() != other.hasNewData()) return false;
    if (hasNewData()) {
      if (!getNewData()
          .equals(other.getNewData())) return false;
    }
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
    if (hasOldData()) {
      hash = (37 * hash) + OLDDATA_FIELD_NUMBER;
      hash = (53 * hash) + getOldData().hashCode();
    }
    if (hasNewData()) {
      hash = (37 * hash) + NEWDATA_FIELD_NUMBER;
      hash = (53 * hash) + getNewData().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static grpc.UpdatedAnimalPartData parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.UpdatedAnimalPartData parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.UpdatedAnimalPartData parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.UpdatedAnimalPartData parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.UpdatedAnimalPartData parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.UpdatedAnimalPartData parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.UpdatedAnimalPartData parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static grpc.UpdatedAnimalPartData parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static grpc.UpdatedAnimalPartData parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static grpc.UpdatedAnimalPartData parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static grpc.UpdatedAnimalPartData parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static grpc.UpdatedAnimalPartData parseFrom(
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
  public static Builder newBuilder(grpc.UpdatedAnimalPartData prototype) {
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
   * Protobuf type {@code grpc.UpdatedAnimalPartData}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:grpc.UpdatedAnimalPartData)
      grpc.UpdatedAnimalPartDataOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return grpc.SlaughterHouseSim.internal_static_grpc_UpdatedAnimalPartData_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return grpc.SlaughterHouseSim.internal_static_grpc_UpdatedAnimalPartData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              grpc.UpdatedAnimalPartData.class, grpc.UpdatedAnimalPartData.Builder.class);
    }

    // Construct using grpc.UpdatedAnimalPartData.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage
              .alwaysUseFieldBuilders) {
        getOldDataFieldBuilder();
        getNewDataFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      oldData_ = null;
      if (oldDataBuilder_ != null) {
        oldDataBuilder_.dispose();
        oldDataBuilder_ = null;
      }
      newData_ = null;
      if (newDataBuilder_ != null) {
        newDataBuilder_.dispose();
        newDataBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return grpc.SlaughterHouseSim.internal_static_grpc_UpdatedAnimalPartData_descriptor;
    }

    @java.lang.Override
    public grpc.UpdatedAnimalPartData getDefaultInstanceForType() {
      return grpc.UpdatedAnimalPartData.getDefaultInstance();
    }

    @java.lang.Override
    public grpc.UpdatedAnimalPartData build() {
      grpc.UpdatedAnimalPartData result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public grpc.UpdatedAnimalPartData buildPartial() {
      grpc.UpdatedAnimalPartData result = new grpc.UpdatedAnimalPartData(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(grpc.UpdatedAnimalPartData result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.oldData_ = oldDataBuilder_ == null
            ? oldData_
            : oldDataBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.newData_ = newDataBuilder_ == null
            ? newData_
            : newDataBuilder_.build();
        to_bitField0_ |= 0x00000002;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof grpc.UpdatedAnimalPartData) {
        return mergeFrom((grpc.UpdatedAnimalPartData)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(grpc.UpdatedAnimalPartData other) {
      if (other == grpc.UpdatedAnimalPartData.getDefaultInstance()) return this;
      if (other.hasOldData()) {
        mergeOldData(other.getOldData());
      }
      if (other.hasNewData()) {
        mergeNewData(other.getNewData());
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
            case 10: {
              input.readMessage(
                  getOldDataFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              input.readMessage(
                  getNewDataFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000002;
              break;
            } // case 18
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

    private grpc.AnimalPartData oldData_;
    private com.google.protobuf.SingleFieldBuilder<
        grpc.AnimalPartData, grpc.AnimalPartData.Builder, grpc.AnimalPartDataOrBuilder> oldDataBuilder_;
    /**
     * <code>.grpc.AnimalPartData oldData = 1;</code>
     * @return Whether the oldData field is set.
     */
    public boolean hasOldData() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>.grpc.AnimalPartData oldData = 1;</code>
     * @return The oldData.
     */
    public grpc.AnimalPartData getOldData() {
      if (oldDataBuilder_ == null) {
        return oldData_ == null ? grpc.AnimalPartData.getDefaultInstance() : oldData_;
      } else {
        return oldDataBuilder_.getMessage();
      }
    }
    /**
     * <code>.grpc.AnimalPartData oldData = 1;</code>
     */
    public Builder setOldData(grpc.AnimalPartData value) {
      if (oldDataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        oldData_ = value;
      } else {
        oldDataBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.grpc.AnimalPartData oldData = 1;</code>
     */
    public Builder setOldData(
        grpc.AnimalPartData.Builder builderForValue) {
      if (oldDataBuilder_ == null) {
        oldData_ = builderForValue.build();
      } else {
        oldDataBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.grpc.AnimalPartData oldData = 1;</code>
     */
    public Builder mergeOldData(grpc.AnimalPartData value) {
      if (oldDataBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          oldData_ != null &&
          oldData_ != grpc.AnimalPartData.getDefaultInstance()) {
          getOldDataBuilder().mergeFrom(value);
        } else {
          oldData_ = value;
        }
      } else {
        oldDataBuilder_.mergeFrom(value);
      }
      if (oldData_ != null) {
        bitField0_ |= 0x00000001;
        onChanged();
      }
      return this;
    }
    /**
     * <code>.grpc.AnimalPartData oldData = 1;</code>
     */
    public Builder clearOldData() {
      bitField0_ = (bitField0_ & ~0x00000001);
      oldData_ = null;
      if (oldDataBuilder_ != null) {
        oldDataBuilder_.dispose();
        oldDataBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.grpc.AnimalPartData oldData = 1;</code>
     */
    public grpc.AnimalPartData.Builder getOldDataBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getOldDataFieldBuilder().getBuilder();
    }
    /**
     * <code>.grpc.AnimalPartData oldData = 1;</code>
     */
    public grpc.AnimalPartDataOrBuilder getOldDataOrBuilder() {
      if (oldDataBuilder_ != null) {
        return oldDataBuilder_.getMessageOrBuilder();
      } else {
        return oldData_ == null ?
            grpc.AnimalPartData.getDefaultInstance() : oldData_;
      }
    }
    /**
     * <code>.grpc.AnimalPartData oldData = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        grpc.AnimalPartData, grpc.AnimalPartData.Builder, grpc.AnimalPartDataOrBuilder> 
        getOldDataFieldBuilder() {
      if (oldDataBuilder_ == null) {
        oldDataBuilder_ = new com.google.protobuf.SingleFieldBuilder<
            grpc.AnimalPartData, grpc.AnimalPartData.Builder, grpc.AnimalPartDataOrBuilder>(
                getOldData(),
                getParentForChildren(),
                isClean());
        oldData_ = null;
      }
      return oldDataBuilder_;
    }

    private grpc.AnimalPartData newData_;
    private com.google.protobuf.SingleFieldBuilder<
        grpc.AnimalPartData, grpc.AnimalPartData.Builder, grpc.AnimalPartDataOrBuilder> newDataBuilder_;
    /**
     * <code>.grpc.AnimalPartData newData = 2;</code>
     * @return Whether the newData field is set.
     */
    public boolean hasNewData() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>.grpc.AnimalPartData newData = 2;</code>
     * @return The newData.
     */
    public grpc.AnimalPartData getNewData() {
      if (newDataBuilder_ == null) {
        return newData_ == null ? grpc.AnimalPartData.getDefaultInstance() : newData_;
      } else {
        return newDataBuilder_.getMessage();
      }
    }
    /**
     * <code>.grpc.AnimalPartData newData = 2;</code>
     */
    public Builder setNewData(grpc.AnimalPartData value) {
      if (newDataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        newData_ = value;
      } else {
        newDataBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>.grpc.AnimalPartData newData = 2;</code>
     */
    public Builder setNewData(
        grpc.AnimalPartData.Builder builderForValue) {
      if (newDataBuilder_ == null) {
        newData_ = builderForValue.build();
      } else {
        newDataBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>.grpc.AnimalPartData newData = 2;</code>
     */
    public Builder mergeNewData(grpc.AnimalPartData value) {
      if (newDataBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0) &&
          newData_ != null &&
          newData_ != grpc.AnimalPartData.getDefaultInstance()) {
          getNewDataBuilder().mergeFrom(value);
        } else {
          newData_ = value;
        }
      } else {
        newDataBuilder_.mergeFrom(value);
      }
      if (newData_ != null) {
        bitField0_ |= 0x00000002;
        onChanged();
      }
      return this;
    }
    /**
     * <code>.grpc.AnimalPartData newData = 2;</code>
     */
    public Builder clearNewData() {
      bitField0_ = (bitField0_ & ~0x00000002);
      newData_ = null;
      if (newDataBuilder_ != null) {
        newDataBuilder_.dispose();
        newDataBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.grpc.AnimalPartData newData = 2;</code>
     */
    public grpc.AnimalPartData.Builder getNewDataBuilder() {
      bitField0_ |= 0x00000002;
      onChanged();
      return getNewDataFieldBuilder().getBuilder();
    }
    /**
     * <code>.grpc.AnimalPartData newData = 2;</code>
     */
    public grpc.AnimalPartDataOrBuilder getNewDataOrBuilder() {
      if (newDataBuilder_ != null) {
        return newDataBuilder_.getMessageOrBuilder();
      } else {
        return newData_ == null ?
            grpc.AnimalPartData.getDefaultInstance() : newData_;
      }
    }
    /**
     * <code>.grpc.AnimalPartData newData = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
        grpc.AnimalPartData, grpc.AnimalPartData.Builder, grpc.AnimalPartDataOrBuilder> 
        getNewDataFieldBuilder() {
      if (newDataBuilder_ == null) {
        newDataBuilder_ = new com.google.protobuf.SingleFieldBuilder<
            grpc.AnimalPartData, grpc.AnimalPartData.Builder, grpc.AnimalPartDataOrBuilder>(
                getNewData(),
                getParentForChildren(),
                isClean());
        newData_ = null;
      }
      return newDataBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:grpc.UpdatedAnimalPartData)
  }

  // @@protoc_insertion_point(class_scope:grpc.UpdatedAnimalPartData)
  private static final grpc.UpdatedAnimalPartData DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new grpc.UpdatedAnimalPartData();
  }

  public static grpc.UpdatedAnimalPartData getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UpdatedAnimalPartData>
      PARSER = new com.google.protobuf.AbstractParser<UpdatedAnimalPartData>() {
    @java.lang.Override
    public UpdatedAnimalPartData parsePartialFrom(
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

  public static com.google.protobuf.Parser<UpdatedAnimalPartData> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<UpdatedAnimalPartData> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public grpc.UpdatedAnimalPartData getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

