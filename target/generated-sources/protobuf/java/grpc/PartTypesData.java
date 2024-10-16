// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: SlaughterHouseSim.proto
// Protobuf Java Version: 4.28.2

package grpc;

/**
 * Protobuf type {@code grpc.PartTypesData}
 */
public final class PartTypesData extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:grpc.PartTypesData)
    PartTypesDataOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 2,
      /* suffix= */ "",
      PartTypesData.class.getName());
  }
  // Use PartTypesData.newBuilder() to construct.
  private PartTypesData(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private PartTypesData() {
    partTypes_ = java.util.Collections.emptyList();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return grpc.SlaughterHouseSim.internal_static_grpc_PartTypesData_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return grpc.SlaughterHouseSim.internal_static_grpc_PartTypesData_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            grpc.PartTypesData.class, grpc.PartTypesData.Builder.class);
  }

  public static final int PARTTYPES_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private java.util.List<grpc.PartTypeData> partTypes_;
  /**
   * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
   */
  @java.lang.Override
  public java.util.List<grpc.PartTypeData> getPartTypesList() {
    return partTypes_;
  }
  /**
   * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends grpc.PartTypeDataOrBuilder> 
      getPartTypesOrBuilderList() {
    return partTypes_;
  }
  /**
   * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
   */
  @java.lang.Override
  public int getPartTypesCount() {
    return partTypes_.size();
  }
  /**
   * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
   */
  @java.lang.Override
  public grpc.PartTypeData getPartTypes(int index) {
    return partTypes_.get(index);
  }
  /**
   * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
   */
  @java.lang.Override
  public grpc.PartTypeDataOrBuilder getPartTypesOrBuilder(
      int index) {
    return partTypes_.get(index);
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
    for (int i = 0; i < partTypes_.size(); i++) {
      output.writeMessage(1, partTypes_.get(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < partTypes_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, partTypes_.get(i));
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
    if (!(obj instanceof grpc.PartTypesData)) {
      return super.equals(obj);
    }
    grpc.PartTypesData other = (grpc.PartTypesData) obj;

    if (!getPartTypesList()
        .equals(other.getPartTypesList())) return false;
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
    if (getPartTypesCount() > 0) {
      hash = (37 * hash) + PARTTYPES_FIELD_NUMBER;
      hash = (53 * hash) + getPartTypesList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static grpc.PartTypesData parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.PartTypesData parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.PartTypesData parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.PartTypesData parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.PartTypesData parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static grpc.PartTypesData parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static grpc.PartTypesData parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static grpc.PartTypesData parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static grpc.PartTypesData parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static grpc.PartTypesData parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static grpc.PartTypesData parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static grpc.PartTypesData parseFrom(
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
  public static Builder newBuilder(grpc.PartTypesData prototype) {
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
   * Protobuf type {@code grpc.PartTypesData}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:grpc.PartTypesData)
      grpc.PartTypesDataOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return grpc.SlaughterHouseSim.internal_static_grpc_PartTypesData_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return grpc.SlaughterHouseSim.internal_static_grpc_PartTypesData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              grpc.PartTypesData.class, grpc.PartTypesData.Builder.class);
    }

    // Construct using grpc.PartTypesData.newBuilder()
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
      if (partTypesBuilder_ == null) {
        partTypes_ = java.util.Collections.emptyList();
      } else {
        partTypes_ = null;
        partTypesBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return grpc.SlaughterHouseSim.internal_static_grpc_PartTypesData_descriptor;
    }

    @java.lang.Override
    public grpc.PartTypesData getDefaultInstanceForType() {
      return grpc.PartTypesData.getDefaultInstance();
    }

    @java.lang.Override
    public grpc.PartTypesData build() {
      grpc.PartTypesData result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public grpc.PartTypesData buildPartial() {
      grpc.PartTypesData result = new grpc.PartTypesData(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(grpc.PartTypesData result) {
      if (partTypesBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          partTypes_ = java.util.Collections.unmodifiableList(partTypes_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.partTypes_ = partTypes_;
      } else {
        result.partTypes_ = partTypesBuilder_.build();
      }
    }

    private void buildPartial0(grpc.PartTypesData result) {
      int from_bitField0_ = bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof grpc.PartTypesData) {
        return mergeFrom((grpc.PartTypesData)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(grpc.PartTypesData other) {
      if (other == grpc.PartTypesData.getDefaultInstance()) return this;
      if (partTypesBuilder_ == null) {
        if (!other.partTypes_.isEmpty()) {
          if (partTypes_.isEmpty()) {
            partTypes_ = other.partTypes_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensurePartTypesIsMutable();
            partTypes_.addAll(other.partTypes_);
          }
          onChanged();
        }
      } else {
        if (!other.partTypes_.isEmpty()) {
          if (partTypesBuilder_.isEmpty()) {
            partTypesBuilder_.dispose();
            partTypesBuilder_ = null;
            partTypes_ = other.partTypes_;
            bitField0_ = (bitField0_ & ~0x00000001);
            partTypesBuilder_ = 
              com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
                 getPartTypesFieldBuilder() : null;
          } else {
            partTypesBuilder_.addAllMessages(other.partTypes_);
          }
        }
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
              grpc.PartTypeData m =
                  input.readMessage(
                      grpc.PartTypeData.parser(),
                      extensionRegistry);
              if (partTypesBuilder_ == null) {
                ensurePartTypesIsMutable();
                partTypes_.add(m);
              } else {
                partTypesBuilder_.addMessage(m);
              }
              break;
            } // case 10
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

    private java.util.List<grpc.PartTypeData> partTypes_ =
      java.util.Collections.emptyList();
    private void ensurePartTypesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        partTypes_ = new java.util.ArrayList<grpc.PartTypeData>(partTypes_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilder<
        grpc.PartTypeData, grpc.PartTypeData.Builder, grpc.PartTypeDataOrBuilder> partTypesBuilder_;

    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public java.util.List<grpc.PartTypeData> getPartTypesList() {
      if (partTypesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(partTypes_);
      } else {
        return partTypesBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public int getPartTypesCount() {
      if (partTypesBuilder_ == null) {
        return partTypes_.size();
      } else {
        return partTypesBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public grpc.PartTypeData getPartTypes(int index) {
      if (partTypesBuilder_ == null) {
        return partTypes_.get(index);
      } else {
        return partTypesBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public Builder setPartTypes(
        int index, grpc.PartTypeData value) {
      if (partTypesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePartTypesIsMutable();
        partTypes_.set(index, value);
        onChanged();
      } else {
        partTypesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public Builder setPartTypes(
        int index, grpc.PartTypeData.Builder builderForValue) {
      if (partTypesBuilder_ == null) {
        ensurePartTypesIsMutable();
        partTypes_.set(index, builderForValue.build());
        onChanged();
      } else {
        partTypesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public Builder addPartTypes(grpc.PartTypeData value) {
      if (partTypesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePartTypesIsMutable();
        partTypes_.add(value);
        onChanged();
      } else {
        partTypesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public Builder addPartTypes(
        int index, grpc.PartTypeData value) {
      if (partTypesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensurePartTypesIsMutable();
        partTypes_.add(index, value);
        onChanged();
      } else {
        partTypesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public Builder addPartTypes(
        grpc.PartTypeData.Builder builderForValue) {
      if (partTypesBuilder_ == null) {
        ensurePartTypesIsMutable();
        partTypes_.add(builderForValue.build());
        onChanged();
      } else {
        partTypesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public Builder addPartTypes(
        int index, grpc.PartTypeData.Builder builderForValue) {
      if (partTypesBuilder_ == null) {
        ensurePartTypesIsMutable();
        partTypes_.add(index, builderForValue.build());
        onChanged();
      } else {
        partTypesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public Builder addAllPartTypes(
        java.lang.Iterable<? extends grpc.PartTypeData> values) {
      if (partTypesBuilder_ == null) {
        ensurePartTypesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, partTypes_);
        onChanged();
      } else {
        partTypesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public Builder clearPartTypes() {
      if (partTypesBuilder_ == null) {
        partTypes_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        partTypesBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public Builder removePartTypes(int index) {
      if (partTypesBuilder_ == null) {
        ensurePartTypesIsMutable();
        partTypes_.remove(index);
        onChanged();
      } else {
        partTypesBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public grpc.PartTypeData.Builder getPartTypesBuilder(
        int index) {
      return getPartTypesFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public grpc.PartTypeDataOrBuilder getPartTypesOrBuilder(
        int index) {
      if (partTypesBuilder_ == null) {
        return partTypes_.get(index);  } else {
        return partTypesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public java.util.List<? extends grpc.PartTypeDataOrBuilder> 
         getPartTypesOrBuilderList() {
      if (partTypesBuilder_ != null) {
        return partTypesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(partTypes_);
      }
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public grpc.PartTypeData.Builder addPartTypesBuilder() {
      return getPartTypesFieldBuilder().addBuilder(
          grpc.PartTypeData.getDefaultInstance());
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public grpc.PartTypeData.Builder addPartTypesBuilder(
        int index) {
      return getPartTypesFieldBuilder().addBuilder(
          index, grpc.PartTypeData.getDefaultInstance());
    }
    /**
     * <code>repeated .grpc.PartTypeData partTypes = 1;</code>
     */
    public java.util.List<grpc.PartTypeData.Builder> 
         getPartTypesBuilderList() {
      return getPartTypesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilder<
        grpc.PartTypeData, grpc.PartTypeData.Builder, grpc.PartTypeDataOrBuilder> 
        getPartTypesFieldBuilder() {
      if (partTypesBuilder_ == null) {
        partTypesBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
            grpc.PartTypeData, grpc.PartTypeData.Builder, grpc.PartTypeDataOrBuilder>(
                partTypes_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        partTypes_ = null;
      }
      return partTypesBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:grpc.PartTypesData)
  }

  // @@protoc_insertion_point(class_scope:grpc.PartTypesData)
  private static final grpc.PartTypesData DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new grpc.PartTypesData();
  }

  public static grpc.PartTypesData getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PartTypesData>
      PARSER = new com.google.protobuf.AbstractParser<PartTypesData>() {
    @java.lang.Override
    public PartTypesData parsePartialFrom(
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

  public static com.google.protobuf.Parser<PartTypesData> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PartTypesData> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public grpc.PartTypesData getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

