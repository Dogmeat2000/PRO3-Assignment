// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SlaughterHouseSim.proto

package grpc;

/**
 * Protobuf type {@code grpc.AnimalPartData}
 */
public  final class AnimalPartData extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:grpc.AnimalPartData)
    AnimalPartDataOrBuilder {
  // Use AnimalPartData.newBuilder() to construct.
  private AnimalPartData(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AnimalPartData() {
    animalPartId_ = "";
    partTypeId_ = "";
    partWeight_ = "";
    animalId_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private AnimalPartData(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            animalPartId_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            partTypeId_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            partWeight_ = s;
            break;
          }
          case 34: {
            java.lang.String s = input.readStringRequireUtf8();

            animalId_ = s;
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return grpc.SlaughterHouseSim.internal_static_grpc_AnimalPartData_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return grpc.SlaughterHouseSim.internal_static_grpc_AnimalPartData_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            grpc.AnimalPartData.class, grpc.AnimalPartData.Builder.class);
  }

  public static final int ANIMALPARTID_FIELD_NUMBER = 1;
  private volatile java.lang.Object animalPartId_;
  /**
   * <code>string animalPartId = 1;</code>
   */
  public java.lang.String getAnimalPartId() {
    java.lang.Object ref = animalPartId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      animalPartId_ = s;
      return s;
    }
  }
  /**
   * <code>string animalPartId = 1;</code>
   */
  public com.google.protobuf.ByteString
      getAnimalPartIdBytes() {
    java.lang.Object ref = animalPartId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      animalPartId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PARTTYPEID_FIELD_NUMBER = 2;
  private volatile java.lang.Object partTypeId_;
  /**
   * <code>string partTypeId = 2;</code>
   */
  public java.lang.String getPartTypeId() {
    java.lang.Object ref = partTypeId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      partTypeId_ = s;
      return s;
    }
  }
  /**
   * <code>string partTypeId = 2;</code>
   */
  public com.google.protobuf.ByteString
      getPartTypeIdBytes() {
    java.lang.Object ref = partTypeId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      partTypeId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int PARTWEIGHT_FIELD_NUMBER = 3;
  private volatile java.lang.Object partWeight_;
  /**
   * <code>string partWeight = 3;</code>
   */
  public java.lang.String getPartWeight() {
    java.lang.Object ref = partWeight_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      partWeight_ = s;
      return s;
    }
  }
  /**
   * <code>string partWeight = 3;</code>
   */
  public com.google.protobuf.ByteString
      getPartWeightBytes() {
    java.lang.Object ref = partWeight_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      partWeight_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ANIMALID_FIELD_NUMBER = 4;
  private volatile java.lang.Object animalId_;
  /**
   * <code>string animalId = 4;</code>
   */
  public java.lang.String getAnimalId() {
    java.lang.Object ref = animalId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      animalId_ = s;
      return s;
    }
  }
  /**
   * <code>string animalId = 4;</code>
   */
  public com.google.protobuf.ByteString
      getAnimalIdBytes() {
    java.lang.Object ref = animalId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      animalId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getAnimalPartIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, animalPartId_);
    }
    if (!getPartTypeIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, partTypeId_);
    }
    if (!getPartWeightBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, partWeight_);
    }
    if (!getAnimalIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, animalId_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getAnimalPartIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, animalPartId_);
    }
    if (!getPartTypeIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, partTypeId_);
    }
    if (!getPartWeightBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, partWeight_);
    }
    if (!getAnimalIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, animalId_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof grpc.AnimalPartData)) {
      return super.equals(obj);
    }
    grpc.AnimalPartData other = (grpc.AnimalPartData) obj;

    boolean result = true;
    result = result && getAnimalPartId()
        .equals(other.getAnimalPartId());
    result = result && getPartTypeId()
        .equals(other.getPartTypeId());
    result = result && getPartWeight()
        .equals(other.getPartWeight());
    result = result && getAnimalId()
        .equals(other.getAnimalId());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + ANIMALPARTID_FIELD_NUMBER;
    hash = (53 * hash) + getAnimalPartId().hashCode();
    hash = (37 * hash) + PARTTYPEID_FIELD_NUMBER;
    hash = (53 * hash) + getPartTypeId().hashCode();
    hash = (37 * hash) + PARTWEIGHT_FIELD_NUMBER;
    hash = (53 * hash) + getPartWeight().hashCode();
    hash = (37 * hash) + ANIMALID_FIELD_NUMBER;
    hash = (53 * hash) + getAnimalId().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static grpc.AnimalPartData parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.AnimalPartData parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.AnimalPartData parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.AnimalPartData parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.AnimalPartData parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.AnimalPartData parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.AnimalPartData parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static grpc.AnimalPartData parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static grpc.AnimalPartData parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static grpc.AnimalPartData parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static grpc.AnimalPartData parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static grpc.AnimalPartData parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(grpc.AnimalPartData prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code grpc.AnimalPartData}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:grpc.AnimalPartData)
      grpc.AnimalPartDataOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return grpc.SlaughterHouseSim.internal_static_grpc_AnimalPartData_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return grpc.SlaughterHouseSim.internal_static_grpc_AnimalPartData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              grpc.AnimalPartData.class, grpc.AnimalPartData.Builder.class);
    }

    // Construct using grpc.AnimalPartData.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      animalPartId_ = "";

      partTypeId_ = "";

      partWeight_ = "";

      animalId_ = "";

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return grpc.SlaughterHouseSim.internal_static_grpc_AnimalPartData_descriptor;
    }

    public grpc.AnimalPartData getDefaultInstanceForType() {
      return grpc.AnimalPartData.getDefaultInstance();
    }

    public grpc.AnimalPartData build() {
      grpc.AnimalPartData result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public grpc.AnimalPartData buildPartial() {
      grpc.AnimalPartData result = new grpc.AnimalPartData(this);
      result.animalPartId_ = animalPartId_;
      result.partTypeId_ = partTypeId_;
      result.partWeight_ = partWeight_;
      result.animalId_ = animalId_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof grpc.AnimalPartData) {
        return mergeFrom((grpc.AnimalPartData)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(grpc.AnimalPartData other) {
      if (other == grpc.AnimalPartData.getDefaultInstance()) return this;
      if (!other.getAnimalPartId().isEmpty()) {
        animalPartId_ = other.animalPartId_;
        onChanged();
      }
      if (!other.getPartTypeId().isEmpty()) {
        partTypeId_ = other.partTypeId_;
        onChanged();
      }
      if (!other.getPartWeight().isEmpty()) {
        partWeight_ = other.partWeight_;
        onChanged();
      }
      if (!other.getAnimalId().isEmpty()) {
        animalId_ = other.animalId_;
        onChanged();
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      grpc.AnimalPartData parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (grpc.AnimalPartData) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object animalPartId_ = "";
    /**
     * <code>string animalPartId = 1;</code>
     */
    public java.lang.String getAnimalPartId() {
      java.lang.Object ref = animalPartId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        animalPartId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string animalPartId = 1;</code>
     */
    public com.google.protobuf.ByteString
        getAnimalPartIdBytes() {
      java.lang.Object ref = animalPartId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        animalPartId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string animalPartId = 1;</code>
     */
    public Builder setAnimalPartId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      animalPartId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string animalPartId = 1;</code>
     */
    public Builder clearAnimalPartId() {
      
      animalPartId_ = getDefaultInstance().getAnimalPartId();
      onChanged();
      return this;
    }
    /**
     * <code>string animalPartId = 1;</code>
     */
    public Builder setAnimalPartIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      animalPartId_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object partTypeId_ = "";
    /**
     * <code>string partTypeId = 2;</code>
     */
    public java.lang.String getPartTypeId() {
      java.lang.Object ref = partTypeId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        partTypeId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string partTypeId = 2;</code>
     */
    public com.google.protobuf.ByteString
        getPartTypeIdBytes() {
      java.lang.Object ref = partTypeId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        partTypeId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string partTypeId = 2;</code>
     */
    public Builder setPartTypeId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      partTypeId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string partTypeId = 2;</code>
     */
    public Builder clearPartTypeId() {
      
      partTypeId_ = getDefaultInstance().getPartTypeId();
      onChanged();
      return this;
    }
    /**
     * <code>string partTypeId = 2;</code>
     */
    public Builder setPartTypeIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      partTypeId_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object partWeight_ = "";
    /**
     * <code>string partWeight = 3;</code>
     */
    public java.lang.String getPartWeight() {
      java.lang.Object ref = partWeight_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        partWeight_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string partWeight = 3;</code>
     */
    public com.google.protobuf.ByteString
        getPartWeightBytes() {
      java.lang.Object ref = partWeight_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        partWeight_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string partWeight = 3;</code>
     */
    public Builder setPartWeight(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      partWeight_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string partWeight = 3;</code>
     */
    public Builder clearPartWeight() {
      
      partWeight_ = getDefaultInstance().getPartWeight();
      onChanged();
      return this;
    }
    /**
     * <code>string partWeight = 3;</code>
     */
    public Builder setPartWeightBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      partWeight_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object animalId_ = "";
    /**
     * <code>string animalId = 4;</code>
     */
    public java.lang.String getAnimalId() {
      java.lang.Object ref = animalId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        animalId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string animalId = 4;</code>
     */
    public com.google.protobuf.ByteString
        getAnimalIdBytes() {
      java.lang.Object ref = animalId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        animalId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string animalId = 4;</code>
     */
    public Builder setAnimalId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      animalId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string animalId = 4;</code>
     */
    public Builder clearAnimalId() {
      
      animalId_ = getDefaultInstance().getAnimalId();
      onChanged();
      return this;
    }
    /**
     * <code>string animalId = 4;</code>
     */
    public Builder setAnimalIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      animalId_ = value;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:grpc.AnimalPartData)
  }

  // @@protoc_insertion_point(class_scope:grpc.AnimalPartData)
  private static final grpc.AnimalPartData DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new grpc.AnimalPartData();
  }

  public static grpc.AnimalPartData getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AnimalPartData>
      PARSER = new com.google.protobuf.AbstractParser<AnimalPartData>() {
    public AnimalPartData parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new AnimalPartData(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AnimalPartData> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AnimalPartData> getParserForType() {
    return PARSER;
  }

  public grpc.AnimalPartData getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

