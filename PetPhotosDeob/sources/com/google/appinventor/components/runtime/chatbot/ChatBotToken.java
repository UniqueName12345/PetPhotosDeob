package com.google.appinventor.components.runtime.chatbot;

import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class ChatBotToken {
    private static Descriptors.FileDescriptor descriptor;
    private static final Descriptors.Descriptor internal_static_request_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_request_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_response_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_response_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_token_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_token_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_unsigned_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_unsigned_fieldAccessorTable;

    /* loaded from: classes.dex */
    public interface requestOrBuilder extends MessageOrBuilder {
        String getApikey();

        ByteString getApikeyBytes();

        String getModel();

        ByteString getModelBytes();

        String getProvider();

        ByteString getProviderBytes();

        String getQuestion();

        ByteString getQuestionBytes();

        String getSystem();

        ByteString getSystemBytes();

        token getToken();

        tokenOrBuilder getTokenOrBuilder();

        String getUuid();

        ByteString getUuidBytes();

        long getVersion();

        boolean hasApikey();

        boolean hasModel();

        boolean hasProvider();

        boolean hasQuestion();

        boolean hasSystem();

        boolean hasToken();

        boolean hasUuid();

        boolean hasVersion();
    }

    /* loaded from: classes.dex */
    public interface responseOrBuilder extends MessageOrBuilder {
        String getAnswer();

        ByteString getAnswerBytes();

        long getStatus();

        String getUuid();

        ByteString getUuidBytes();

        long getVersion();

        boolean hasAnswer();

        boolean hasStatus();

        boolean hasUuid();

        boolean hasVersion();
    }

    /* loaded from: classes.dex */
    public interface tokenOrBuilder extends MessageOrBuilder {
        long getGeneration();

        long getKeyid();

        ByteString getSignature();

        ByteString getUnsigned();

        long getVersion();

        boolean hasGeneration();

        boolean hasKeyid();

        boolean hasSignature();

        boolean hasUnsigned();

        boolean hasVersion();
    }

    /* loaded from: classes.dex */
    public interface unsignedOrBuilder extends MessageOrBuilder {
        long getGeneration();

        String getHuuid();

        ByteString getHuuidBytes();

        long getVersion();

        boolean hasGeneration();

        boolean hasHuuid();

        boolean hasVersion();
    }

    private ChatBotToken() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite) registry);
    }

    /* loaded from: classes.dex */
    public static final class unsigned extends GeneratedMessageV3 implements unsignedOrBuilder {
        public static final int GENERATION_FIELD_NUMBER = 3;
        public static final int HUUID_FIELD_NUMBER = 1;
        public static final int VERSION_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private long generation_;
        private volatile Object huuid_;
        private byte memoizedIsInitialized;
        private long version_;
        private static final unsigned DEFAULT_INSTANCE = new unsigned();
        @Deprecated
        public static final Parser<unsigned> PARSER = new AbstractParser<unsigned>() { // from class: com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsigned.1
            /* renamed from: parsePartialFrom */
            public unsigned m151parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new unsigned(input, extensionRegistry);
            }
        };

        private unsigned(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        private unsigned() {
            this.memoizedIsInitialized = (byte) -1;
            this.huuid_ = "";
            this.version_ = 0L;
            this.generation_ = 0L;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private unsigned(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    try {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0:
                                done = true;
                                break;
                            case 10:
                                ByteString bs = input.readBytes();
                                this.bitField0_ |= 1;
                                this.huuid_ = bs;
                                break;
                            case 16:
                                this.bitField0_ |= 2;
                                this.version_ = input.readUInt64();
                                break;
                            case 24:
                                this.bitField0_ |= 4;
                                this.generation_ = input.readUInt64();
                                break;
                            default:
                                if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                    break;
                                } else {
                                    break;
                                }
                        }
                    } catch (IOException e) {
                        throw new InvalidProtocolBufferException(e).setUnfinishedMessage(this);
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    }
                } finally {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return ChatBotToken.internal_static_unsigned_descriptor;
        }

        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ChatBotToken.internal_static_unsigned_fieldAccessorTable.ensureFieldAccessorsInitialized(unsigned.class, Builder.class);
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
        public boolean hasHuuid() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
        public String getHuuid() {
            Object ref = this.huuid_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.huuid_ = s;
            }
            return s;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
        public ByteString getHuuidBytes() {
            Object ref = this.huuid_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.huuid_ = b;
                return b;
            }
            return (ByteString) ref;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
        public boolean hasVersion() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
        public long getVersion() {
            return this.version_;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
        public boolean hasGeneration() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
        public long getGeneration() {
            return this.generation_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                GeneratedMessageV3.writeString(output, 1, this.huuid_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeUInt64(2, this.version_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeUInt64(3, this.generation_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = (this.bitField0_ & 1) == 1 ? 0 + GeneratedMessageV3.computeStringSize(1, this.huuid_) : 0;
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeUInt64Size(2, this.version_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeUInt64Size(3, this.generation_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof unsigned)) {
                return super.equals(obj);
            }
            unsigned other = (unsigned) obj;
            boolean result = 1 != 0 && hasHuuid() == other.hasHuuid();
            if (hasHuuid()) {
                result = result && getHuuid().equals(other.getHuuid());
            }
            boolean result2 = result && hasVersion() == other.hasVersion();
            if (hasVersion()) {
                result2 = result2 && getVersion() == other.getVersion();
            }
            boolean result3 = result2 && hasGeneration() == other.hasGeneration();
            if (hasGeneration()) {
                result3 = result3 && getGeneration() == other.getGeneration();
            }
            return result3 && this.unknownFields.equals(other.unknownFields);
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasHuuid()) {
                hash = (((hash * 37) + 1) * 53) + getHuuid().hashCode();
            }
            if (hasVersion()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashLong(getVersion());
            }
            if (hasGeneration()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashLong(getGeneration());
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static unsigned parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (unsigned) PARSER.parseFrom(data);
        }

        public static unsigned parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (unsigned) PARSER.parseFrom(data, extensionRegistry);
        }

        public static unsigned parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (unsigned) PARSER.parseFrom(data);
        }

        public static unsigned parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (unsigned) PARSER.parseFrom(data, extensionRegistry);
        }

        public static unsigned parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static unsigned parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static unsigned parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static unsigned parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static unsigned parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static unsigned parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        /* renamed from: newBuilderForType */
        public Builder m148newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.m150toBuilder();
        }

        public static Builder newBuilder(unsigned prototype) {
            return DEFAULT_INSTANCE.m150toBuilder().mergeFrom(prototype);
        }

        /* renamed from: toBuilder */
        public Builder m150toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: newBuilderForType */
        public Builder m147newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /* loaded from: classes.dex */
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements unsignedOrBuilder {
            private int bitField0_;
            private long generation_;
            private Object huuid_;
            private long version_;

            public static final Descriptors.Descriptor getDescriptor() {
                return ChatBotToken.internal_static_unsigned_descriptor;
            }

            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return ChatBotToken.internal_static_unsigned_fieldAccessorTable.ensureFieldAccessorsInitialized(unsigned.class, Builder.class);
            }

            private Builder() {
                this.huuid_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.huuid_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (unsigned.alwaysUseFieldBuilders) {
                }
            }

            /* renamed from: clear */
            public Builder m161clear() {
                super.clear();
                this.huuid_ = "";
                this.bitField0_ &= -2;
                this.version_ = 0L;
                this.bitField0_ &= -3;
                this.generation_ = 0L;
                this.bitField0_ &= -5;
                return this;
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return ChatBotToken.internal_static_unsigned_descriptor;
            }

            /* renamed from: getDefaultInstanceForType */
            public unsigned m174getDefaultInstanceForType() {
                return unsigned.getDefaultInstance();
            }

            /* renamed from: build */
            public unsigned m155build() {
                unsigned result = m157buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            /* renamed from: buildPartial */
            public unsigned m157buildPartial() {
                unsigned result = new unsigned(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.huuid_ = this.huuid_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.version_ = this.version_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.generation_ = this.generation_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            /* renamed from: clone */
            public Builder m172clone() {
                return (Builder) super.clone();
            }

            /* renamed from: setField */
            public Builder m185setField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            /* renamed from: clearField */
            public Builder m163clearField(Descriptors.FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            /* renamed from: clearOneof */
            public Builder m166clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            /* renamed from: setRepeatedField */
            public Builder m187setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            /* renamed from: addRepeatedField */
            public Builder m153addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* renamed from: mergeFrom */
            public Builder m179mergeFrom(Message other) {
                if (other instanceof unsigned) {
                    return mergeFrom((unsigned) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(unsigned other) {
                if (other != unsigned.getDefaultInstance()) {
                    if (other.hasHuuid()) {
                        this.bitField0_ |= 1;
                        this.huuid_ = other.huuid_;
                        onChanged();
                    }
                    if (other.hasVersion()) {
                        setVersion(other.getVersion());
                    }
                    if (other.hasGeneration()) {
                        setGeneration(other.getGeneration());
                    }
                    m183mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            /* renamed from: mergeFrom */
            public Builder m180mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                unsigned parsedMessage = null;
                try {
                    try {
                        parsedMessage = (unsigned) unsigned.PARSER.parsePartialFrom(input, extensionRegistry);
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        parsedMessage = (unsigned) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    }
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
            public boolean hasHuuid() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
            public String getHuuid() {
                Object ref = this.huuid_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.huuid_ = s;
                    return s;
                }
                return s;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
            public ByteString getHuuidBytes() {
                Object ref = this.huuid_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.huuid_ = b;
                    return b;
                }
                return (ByteString) ref;
            }

            public Builder setHuuid(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.huuid_ = value;
                onChanged();
                return this;
            }

            public Builder clearHuuid() {
                this.bitField0_ &= -2;
                this.huuid_ = unsigned.getDefaultInstance().getHuuid();
                onChanged();
                return this;
            }

            public Builder setHuuidBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.huuid_ = value;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
            public boolean hasVersion() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
            public long getVersion() {
                return this.version_;
            }

            public Builder setVersion(long value) {
                this.bitField0_ |= 2;
                this.version_ = value;
                onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.bitField0_ &= -3;
                this.version_ = 0L;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
            public boolean hasGeneration() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.unsignedOrBuilder
            public long getGeneration() {
                return this.generation_;
            }

            public Builder setGeneration(long value) {
                this.bitField0_ |= 4;
                this.generation_ = value;
                onChanged();
                return this;
            }

            public Builder clearGeneration() {
                this.bitField0_ &= -5;
                this.generation_ = 0L;
                onChanged();
                return this;
            }

            /* renamed from: setUnknownFields */
            public final Builder m189setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            /* renamed from: mergeUnknownFields */
            public final Builder m183mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public static unsigned getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<unsigned> parser() {
            return PARSER;
        }

        public Parser<unsigned> getParserForType() {
            return PARSER;
        }

        /* renamed from: getDefaultInstanceForType */
        public unsigned m145getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    /* loaded from: classes.dex */
    public static final class token extends GeneratedMessageV3 implements tokenOrBuilder {
        public static final int GENERATION_FIELD_NUMBER = 3;
        public static final int KEYID_FIELD_NUMBER = 2;
        public static final int SIGNATURE_FIELD_NUMBER = 5;
        public static final int UNSIGNED_FIELD_NUMBER = 4;
        public static final int VERSION_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private long generation_;
        private long keyid_;
        private byte memoizedIsInitialized;
        private ByteString signature_;
        private ByteString unsigned_;
        private long version_;
        private static final token DEFAULT_INSTANCE = new token();
        @Deprecated
        public static final Parser<token> PARSER = new AbstractParser<token>() { // from class: com.google.appinventor.components.runtime.chatbot.ChatBotToken.token.1
            /* renamed from: parsePartialFrom */
            public token m105parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new token(input, extensionRegistry);
            }
        };

        private token(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        private token() {
            this.memoizedIsInitialized = (byte) -1;
            this.version_ = 1L;
            this.keyid_ = 1L;
            this.generation_ = 0L;
            this.unsigned_ = ByteString.EMPTY;
            this.signature_ = ByteString.EMPTY;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private token(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    try {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0:
                                done = true;
                                break;
                            case 8:
                                this.bitField0_ |= 1;
                                this.version_ = input.readUInt64();
                                break;
                            case 16:
                                this.bitField0_ |= 2;
                                this.keyid_ = input.readUInt64();
                                break;
                            case 24:
                                this.bitField0_ |= 4;
                                this.generation_ = input.readUInt64();
                                break;
                            case 34:
                                this.bitField0_ |= 8;
                                this.unsigned_ = input.readBytes();
                                break;
                            case 42:
                                this.bitField0_ |= 16;
                                this.signature_ = input.readBytes();
                                break;
                            default:
                                if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                    break;
                                } else {
                                    break;
                                }
                        }
                    } catch (IOException e) {
                        throw new InvalidProtocolBufferException(e).setUnfinishedMessage(this);
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    }
                } finally {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return ChatBotToken.internal_static_token_descriptor;
        }

        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ChatBotToken.internal_static_token_fieldAccessorTable.ensureFieldAccessorsInitialized(token.class, Builder.class);
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
        public boolean hasVersion() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
        public long getVersion() {
            return this.version_;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
        public boolean hasKeyid() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
        public long getKeyid() {
            return this.keyid_;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
        public boolean hasGeneration() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
        public long getGeneration() {
            return this.generation_;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
        public boolean hasUnsigned() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
        public ByteString getUnsigned() {
            return this.unsigned_;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
        public boolean hasSignature() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
        public ByteString getSignature() {
            return this.signature_;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeUInt64(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeUInt64(2, this.keyid_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeUInt64(3, this.generation_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBytes(4, this.unsigned_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBytes(5, this.signature_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = (this.bitField0_ & 1) == 1 ? 0 + CodedOutputStream.computeUInt64Size(1, this.version_) : 0;
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeUInt64Size(2, this.keyid_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.computeUInt64Size(3, this.generation_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.computeBytesSize(4, this.unsigned_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += CodedOutputStream.computeBytesSize(5, this.signature_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof token)) {
                return super.equals(obj);
            }
            token other = (token) obj;
            boolean result = 1 != 0 && hasVersion() == other.hasVersion();
            if (hasVersion()) {
                result = result && getVersion() == other.getVersion();
            }
            boolean result2 = result && hasKeyid() == other.hasKeyid();
            if (hasKeyid()) {
                result2 = result2 && getKeyid() == other.getKeyid();
            }
            boolean result3 = result2 && hasGeneration() == other.hasGeneration();
            if (hasGeneration()) {
                result3 = result3 && getGeneration() == other.getGeneration();
            }
            boolean result4 = result3 && hasUnsigned() == other.hasUnsigned();
            if (hasUnsigned()) {
                result4 = result4 && getUnsigned().equals(other.getUnsigned());
            }
            boolean result5 = result4 && hasSignature() == other.hasSignature();
            if (hasSignature()) {
                result5 = result5 && getSignature().equals(other.getSignature());
            }
            return result5 && this.unknownFields.equals(other.unknownFields);
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasVersion()) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashLong(getVersion());
            }
            if (hasKeyid()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashLong(getKeyid());
            }
            if (hasGeneration()) {
                hash = (((hash * 37) + 3) * 53) + Internal.hashLong(getGeneration());
            }
            if (hasUnsigned()) {
                hash = (((hash * 37) + 4) * 53) + getUnsigned().hashCode();
            }
            if (hasSignature()) {
                hash = (((hash * 37) + 5) * 53) + getSignature().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static token parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (token) PARSER.parseFrom(data);
        }

        public static token parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (token) PARSER.parseFrom(data, extensionRegistry);
        }

        public static token parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (token) PARSER.parseFrom(data);
        }

        public static token parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (token) PARSER.parseFrom(data, extensionRegistry);
        }

        public static token parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static token parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static token parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static token parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static token parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static token parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        /* renamed from: newBuilderForType */
        public Builder m102newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.m104toBuilder();
        }

        public static Builder newBuilder(token prototype) {
            return DEFAULT_INSTANCE.m104toBuilder().mergeFrom(prototype);
        }

        /* renamed from: toBuilder */
        public Builder m104toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: newBuilderForType */
        public Builder m101newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /* loaded from: classes.dex */
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements tokenOrBuilder {
            private int bitField0_;
            private long generation_;
            private long keyid_;
            private ByteString signature_;
            private ByteString unsigned_;
            private long version_;

            public static final Descriptors.Descriptor getDescriptor() {
                return ChatBotToken.internal_static_token_descriptor;
            }

            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return ChatBotToken.internal_static_token_fieldAccessorTable.ensureFieldAccessorsInitialized(token.class, Builder.class);
            }

            private Builder() {
                this.version_ = 1L;
                this.keyid_ = 1L;
                this.unsigned_ = ByteString.EMPTY;
                this.signature_ = ByteString.EMPTY;
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.version_ = 1L;
                this.keyid_ = 1L;
                this.unsigned_ = ByteString.EMPTY;
                this.signature_ = ByteString.EMPTY;
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (token.alwaysUseFieldBuilders) {
                }
            }

            /* renamed from: clear */
            public Builder m115clear() {
                super.clear();
                this.version_ = 1L;
                this.bitField0_ &= -2;
                this.keyid_ = 1L;
                this.bitField0_ &= -3;
                this.generation_ = 0L;
                this.bitField0_ &= -5;
                this.unsigned_ = ByteString.EMPTY;
                this.bitField0_ &= -9;
                this.signature_ = ByteString.EMPTY;
                this.bitField0_ &= -17;
                return this;
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return ChatBotToken.internal_static_token_descriptor;
            }

            /* renamed from: getDefaultInstanceForType */
            public token m128getDefaultInstanceForType() {
                return token.getDefaultInstance();
            }

            /* renamed from: build */
            public token m109build() {
                token result = m111buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            /* renamed from: buildPartial */
            public token m111buildPartial() {
                token result = new token(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.version_ = this.version_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.keyid_ = this.keyid_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.generation_ = this.generation_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.unsigned_ = this.unsigned_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.signature_ = this.signature_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            /* renamed from: clone */
            public Builder m126clone() {
                return (Builder) super.clone();
            }

            /* renamed from: setField */
            public Builder m139setField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            /* renamed from: clearField */
            public Builder m117clearField(Descriptors.FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            /* renamed from: clearOneof */
            public Builder m120clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            /* renamed from: setRepeatedField */
            public Builder m141setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            /* renamed from: addRepeatedField */
            public Builder m107addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* renamed from: mergeFrom */
            public Builder m133mergeFrom(Message other) {
                if (other instanceof token) {
                    return mergeFrom((token) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(token other) {
                if (other != token.getDefaultInstance()) {
                    if (other.hasVersion()) {
                        setVersion(other.getVersion());
                    }
                    if (other.hasKeyid()) {
                        setKeyid(other.getKeyid());
                    }
                    if (other.hasGeneration()) {
                        setGeneration(other.getGeneration());
                    }
                    if (other.hasUnsigned()) {
                        setUnsigned(other.getUnsigned());
                    }
                    if (other.hasSignature()) {
                        setSignature(other.getSignature());
                    }
                    m137mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            /* renamed from: mergeFrom */
            public Builder m134mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                token parsedMessage = null;
                try {
                    try {
                        parsedMessage = (token) token.PARSER.parsePartialFrom(input, extensionRegistry);
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        parsedMessage = (token) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    }
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
            public boolean hasVersion() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
            public long getVersion() {
                return this.version_;
            }

            public Builder setVersion(long value) {
                this.bitField0_ |= 1;
                this.version_ = value;
                onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.bitField0_ &= -2;
                this.version_ = 1L;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
            public boolean hasKeyid() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
            public long getKeyid() {
                return this.keyid_;
            }

            public Builder setKeyid(long value) {
                this.bitField0_ |= 2;
                this.keyid_ = value;
                onChanged();
                return this;
            }

            public Builder clearKeyid() {
                this.bitField0_ &= -3;
                this.keyid_ = 1L;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
            public boolean hasGeneration() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
            public long getGeneration() {
                return this.generation_;
            }

            public Builder setGeneration(long value) {
                this.bitField0_ |= 4;
                this.generation_ = value;
                onChanged();
                return this;
            }

            public Builder clearGeneration() {
                this.bitField0_ &= -5;
                this.generation_ = 0L;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
            public boolean hasUnsigned() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
            public ByteString getUnsigned() {
                return this.unsigned_;
            }

            public Builder setUnsigned(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.unsigned_ = value;
                onChanged();
                return this;
            }

            public Builder clearUnsigned() {
                this.bitField0_ &= -9;
                this.unsigned_ = token.getDefaultInstance().getUnsigned();
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
            public boolean hasSignature() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.tokenOrBuilder
            public ByteString getSignature() {
                return this.signature_;
            }

            public Builder setSignature(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.signature_ = value;
                onChanged();
                return this;
            }

            public Builder clearSignature() {
                this.bitField0_ &= -17;
                this.signature_ = token.getDefaultInstance().getSignature();
                onChanged();
                return this;
            }

            /* renamed from: setUnknownFields */
            public final Builder m143setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            /* renamed from: mergeUnknownFields */
            public final Builder m137mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public static token getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<token> parser() {
            return PARSER;
        }

        public Parser<token> getParserForType() {
            return PARSER;
        }

        /* renamed from: getDefaultInstanceForType */
        public token m99getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    /* loaded from: classes.dex */
    public static final class request extends GeneratedMessageV3 implements requestOrBuilder {
        public static final int APIKEY_FIELD_NUMBER = 6;
        public static final int MODEL_FIELD_NUMBER = 8;
        public static final int PROVIDER_FIELD_NUMBER = 7;
        public static final int QUESTION_FIELD_NUMBER = 4;
        public static final int SYSTEM_FIELD_NUMBER = 5;
        public static final int TOKEN_FIELD_NUMBER = 2;
        public static final int UUID_FIELD_NUMBER = 3;
        public static final int VERSION_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        private volatile Object apikey_;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private volatile Object model_;
        private volatile Object provider_;
        private volatile Object question_;
        private volatile Object system_;
        private token token_;
        private volatile Object uuid_;
        private long version_;
        private static final request DEFAULT_INSTANCE = new request();
        @Deprecated
        public static final Parser<request> PARSER = new AbstractParser<request>() { // from class: com.google.appinventor.components.runtime.chatbot.ChatBotToken.request.1
            /* renamed from: parsePartialFrom */
            public request m13parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new request(input, extensionRegistry);
            }
        };

        private request(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        private request() {
            this.memoizedIsInitialized = (byte) -1;
            this.version_ = 1L;
            this.uuid_ = "";
            this.question_ = "";
            this.system_ = "";
            this.apikey_ = "";
            this.provider_ = "chatgpt";
            this.model_ = "";
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private request(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    try {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0:
                                done = true;
                                break;
                            case 8:
                                this.bitField0_ |= 1;
                                this.version_ = input.readUInt64();
                                break;
                            case 18:
                                token.Builder subBuilder = (this.bitField0_ & 2) == 2 ? this.token_.m104toBuilder() : null;
                                this.token_ = input.readMessage(token.PARSER, extensionRegistry);
                                if (subBuilder != null) {
                                    subBuilder.mergeFrom(this.token_);
                                    this.token_ = subBuilder.m111buildPartial();
                                }
                                this.bitField0_ |= 2;
                                break;
                            case 26:
                                ByteString bs = input.readBytes();
                                this.bitField0_ |= 4;
                                this.uuid_ = bs;
                                break;
                            case 34:
                                ByteString bs2 = input.readBytes();
                                this.bitField0_ |= 8;
                                this.question_ = bs2;
                                break;
                            case 42:
                                ByteString bs3 = input.readBytes();
                                this.bitField0_ |= 16;
                                this.system_ = bs3;
                                break;
                            case 50:
                                ByteString bs4 = input.readBytes();
                                this.bitField0_ |= 32;
                                this.apikey_ = bs4;
                                break;
                            case 58:
                                ByteString bs5 = input.readBytes();
                                this.bitField0_ |= 64;
                                this.provider_ = bs5;
                                break;
                            case 66:
                                ByteString bs6 = input.readBytes();
                                this.bitField0_ |= 128;
                                this.model_ = bs6;
                                break;
                            default:
                                if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                    break;
                                } else {
                                    break;
                                }
                        }
                    } catch (IOException e) {
                        throw new InvalidProtocolBufferException(e).setUnfinishedMessage(this);
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    }
                } finally {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return ChatBotToken.internal_static_request_descriptor;
        }

        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ChatBotToken.internal_static_request_fieldAccessorTable.ensureFieldAccessorsInitialized(request.class, Builder.class);
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public boolean hasVersion() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public long getVersion() {
            return this.version_;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public boolean hasToken() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public token getToken() {
            return this.token_ == null ? token.getDefaultInstance() : this.token_;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public tokenOrBuilder getTokenOrBuilder() {
            return this.token_ == null ? token.getDefaultInstance() : this.token_;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public boolean hasUuid() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public String getUuid() {
            Object ref = this.uuid_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.uuid_ = s;
            }
            return s;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public ByteString getUuidBytes() {
            Object ref = this.uuid_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.uuid_ = b;
                return b;
            }
            return (ByteString) ref;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public boolean hasQuestion() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public String getQuestion() {
            Object ref = this.question_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.question_ = s;
            }
            return s;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public ByteString getQuestionBytes() {
            Object ref = this.question_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.question_ = b;
                return b;
            }
            return (ByteString) ref;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public boolean hasSystem() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public String getSystem() {
            Object ref = this.system_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.system_ = s;
            }
            return s;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public ByteString getSystemBytes() {
            Object ref = this.system_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.system_ = b;
                return b;
            }
            return (ByteString) ref;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public boolean hasApikey() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public String getApikey() {
            Object ref = this.apikey_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.apikey_ = s;
            }
            return s;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public ByteString getApikeyBytes() {
            Object ref = this.apikey_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.apikey_ = b;
                return b;
            }
            return (ByteString) ref;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public boolean hasProvider() {
            return (this.bitField0_ & 64) == 64;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public String getProvider() {
            Object ref = this.provider_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.provider_ = s;
            }
            return s;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public ByteString getProviderBytes() {
            Object ref = this.provider_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.provider_ = b;
                return b;
            }
            return (ByteString) ref;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public boolean hasModel() {
            return (this.bitField0_ & 128) == 128;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public String getModel() {
            Object ref = this.model_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.model_ = s;
            }
            return s;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
        public ByteString getModelBytes() {
            Object ref = this.model_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.model_ = b;
                return b;
            }
            return (ByteString) ref;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeUInt64(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, getToken());
            }
            if ((this.bitField0_ & 4) == 4) {
                GeneratedMessageV3.writeString(output, 3, this.uuid_);
            }
            if ((this.bitField0_ & 8) == 8) {
                GeneratedMessageV3.writeString(output, 4, this.question_);
            }
            if ((this.bitField0_ & 16) == 16) {
                GeneratedMessageV3.writeString(output, 5, this.system_);
            }
            if ((this.bitField0_ & 32) == 32) {
                GeneratedMessageV3.writeString(output, 6, this.apikey_);
            }
            if ((this.bitField0_ & 64) == 64) {
                GeneratedMessageV3.writeString(output, 7, this.provider_);
            }
            if ((this.bitField0_ & 128) == 128) {
                GeneratedMessageV3.writeString(output, 8, this.model_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = (this.bitField0_ & 1) == 1 ? 0 + CodedOutputStream.computeUInt64Size(1, this.version_) : 0;
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeMessageSize(2, getToken());
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += GeneratedMessageV3.computeStringSize(3, this.uuid_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += GeneratedMessageV3.computeStringSize(4, this.question_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size2 += GeneratedMessageV3.computeStringSize(5, this.system_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size2 += GeneratedMessageV3.computeStringSize(6, this.apikey_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size2 += GeneratedMessageV3.computeStringSize(7, this.provider_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size2 += GeneratedMessageV3.computeStringSize(8, this.model_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof request)) {
                return super.equals(obj);
            }
            request other = (request) obj;
            boolean result = 1 != 0 && hasVersion() == other.hasVersion();
            if (hasVersion()) {
                result = result && getVersion() == other.getVersion();
            }
            boolean result2 = result && hasToken() == other.hasToken();
            if (hasToken()) {
                result2 = result2 && getToken().equals(other.getToken());
            }
            boolean result3 = result2 && hasUuid() == other.hasUuid();
            if (hasUuid()) {
                result3 = result3 && getUuid().equals(other.getUuid());
            }
            boolean result4 = result3 && hasQuestion() == other.hasQuestion();
            if (hasQuestion()) {
                result4 = result4 && getQuestion().equals(other.getQuestion());
            }
            boolean result5 = result4 && hasSystem() == other.hasSystem();
            if (hasSystem()) {
                result5 = result5 && getSystem().equals(other.getSystem());
            }
            boolean result6 = result5 && hasApikey() == other.hasApikey();
            if (hasApikey()) {
                result6 = result6 && getApikey().equals(other.getApikey());
            }
            boolean result7 = result6 && hasProvider() == other.hasProvider();
            if (hasProvider()) {
                result7 = result7 && getProvider().equals(other.getProvider());
            }
            boolean result8 = result7 && hasModel() == other.hasModel();
            if (hasModel()) {
                result8 = result8 && getModel().equals(other.getModel());
            }
            return result8 && this.unknownFields.equals(other.unknownFields);
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasVersion()) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashLong(getVersion());
            }
            if (hasToken()) {
                hash = (((hash * 37) + 2) * 53) + getToken().hashCode();
            }
            if (hasUuid()) {
                hash = (((hash * 37) + 3) * 53) + getUuid().hashCode();
            }
            if (hasQuestion()) {
                hash = (((hash * 37) + 4) * 53) + getQuestion().hashCode();
            }
            if (hasSystem()) {
                hash = (((hash * 37) + 5) * 53) + getSystem().hashCode();
            }
            if (hasApikey()) {
                hash = (((hash * 37) + 6) * 53) + getApikey().hashCode();
            }
            if (hasProvider()) {
                hash = (((hash * 37) + 7) * 53) + getProvider().hashCode();
            }
            if (hasModel()) {
                hash = (((hash * 37) + 8) * 53) + getModel().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static request parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (request) PARSER.parseFrom(data);
        }

        public static request parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (request) PARSER.parseFrom(data, extensionRegistry);
        }

        public static request parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (request) PARSER.parseFrom(data);
        }

        public static request parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (request) PARSER.parseFrom(data, extensionRegistry);
        }

        public static request parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static request parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static request parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static request parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static request parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static request parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        /* renamed from: newBuilderForType */
        public Builder m10newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.m12toBuilder();
        }

        public static Builder newBuilder(request prototype) {
            return DEFAULT_INSTANCE.m12toBuilder().mergeFrom(prototype);
        }

        /* renamed from: toBuilder */
        public Builder m12toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: newBuilderForType */
        public Builder m9newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /* loaded from: classes.dex */
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements requestOrBuilder {
            private Object apikey_;
            private int bitField0_;
            private Object model_;
            private Object provider_;
            private Object question_;
            private Object system_;
            private SingleFieldBuilderV3<token, token.Builder, tokenOrBuilder> tokenBuilder_;
            private token token_;
            private Object uuid_;
            private long version_;

            public static final Descriptors.Descriptor getDescriptor() {
                return ChatBotToken.internal_static_request_descriptor;
            }

            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return ChatBotToken.internal_static_request_fieldAccessorTable.ensureFieldAccessorsInitialized(request.class, Builder.class);
            }

            private Builder() {
                this.version_ = 1L;
                this.token_ = null;
                this.uuid_ = "";
                this.question_ = "";
                this.system_ = "";
                this.apikey_ = "";
                this.provider_ = "chatgpt";
                this.model_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.version_ = 1L;
                this.token_ = null;
                this.uuid_ = "";
                this.question_ = "";
                this.system_ = "";
                this.apikey_ = "";
                this.provider_ = "chatgpt";
                this.model_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (request.alwaysUseFieldBuilders) {
                    getTokenFieldBuilder();
                }
            }

            /* renamed from: clear */
            public Builder m23clear() {
                super.clear();
                this.version_ = 1L;
                this.bitField0_ &= -2;
                if (this.tokenBuilder_ == null) {
                    this.token_ = null;
                } else {
                    this.tokenBuilder_.clear();
                }
                this.bitField0_ &= -3;
                this.uuid_ = "";
                this.bitField0_ &= -5;
                this.question_ = "";
                this.bitField0_ &= -9;
                this.system_ = "";
                this.bitField0_ &= -17;
                this.apikey_ = "";
                this.bitField0_ &= -33;
                this.provider_ = "chatgpt";
                this.bitField0_ &= -65;
                this.model_ = "";
                this.bitField0_ &= -129;
                return this;
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return ChatBotToken.internal_static_request_descriptor;
            }

            /* renamed from: getDefaultInstanceForType */
            public request m36getDefaultInstanceForType() {
                return request.getDefaultInstance();
            }

            /* renamed from: build */
            public request m17build() {
                request result = m19buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            /* renamed from: buildPartial */
            public request m19buildPartial() {
                request result = new request(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.version_ = this.version_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.tokenBuilder_ == null) {
                    result.token_ = this.token_;
                } else {
                    result.token_ = this.tokenBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.uuid_ = this.uuid_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.question_ = this.question_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.system_ = this.system_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.apikey_ = this.apikey_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.provider_ = this.provider_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                result.model_ = this.model_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            /* renamed from: clone */
            public Builder m34clone() {
                return (Builder) super.clone();
            }

            /* renamed from: setField */
            public Builder m47setField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            /* renamed from: clearField */
            public Builder m25clearField(Descriptors.FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            /* renamed from: clearOneof */
            public Builder m28clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            /* renamed from: setRepeatedField */
            public Builder m49setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            /* renamed from: addRepeatedField */
            public Builder m15addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* renamed from: mergeFrom */
            public Builder m41mergeFrom(Message other) {
                if (other instanceof request) {
                    return mergeFrom((request) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(request other) {
                if (other != request.getDefaultInstance()) {
                    if (other.hasVersion()) {
                        setVersion(other.getVersion());
                    }
                    if (other.hasToken()) {
                        mergeToken(other.getToken());
                    }
                    if (other.hasUuid()) {
                        this.bitField0_ |= 4;
                        this.uuid_ = other.uuid_;
                        onChanged();
                    }
                    if (other.hasQuestion()) {
                        this.bitField0_ |= 8;
                        this.question_ = other.question_;
                        onChanged();
                    }
                    if (other.hasSystem()) {
                        this.bitField0_ |= 16;
                        this.system_ = other.system_;
                        onChanged();
                    }
                    if (other.hasApikey()) {
                        this.bitField0_ |= 32;
                        this.apikey_ = other.apikey_;
                        onChanged();
                    }
                    if (other.hasProvider()) {
                        this.bitField0_ |= 64;
                        this.provider_ = other.provider_;
                        onChanged();
                    }
                    if (other.hasModel()) {
                        this.bitField0_ |= 128;
                        this.model_ = other.model_;
                        onChanged();
                    }
                    m45mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            /* renamed from: mergeFrom */
            public Builder m42mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                request parsedMessage = null;
                try {
                    try {
                        parsedMessage = (request) request.PARSER.parsePartialFrom(input, extensionRegistry);
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        parsedMessage = (request) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    }
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public boolean hasVersion() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public long getVersion() {
                return this.version_;
            }

            public Builder setVersion(long value) {
                this.bitField0_ |= 1;
                this.version_ = value;
                onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.bitField0_ &= -2;
                this.version_ = 1L;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public boolean hasToken() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public token getToken() {
                if (this.tokenBuilder_ == null) {
                    return this.token_ == null ? token.getDefaultInstance() : this.token_;
                }
                return this.tokenBuilder_.getMessage();
            }

            public Builder setToken(token value) {
                if (this.tokenBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.token_ = value;
                    onChanged();
                } else {
                    this.tokenBuilder_.setMessage(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setToken(token.Builder builderForValue) {
                if (this.tokenBuilder_ == null) {
                    this.token_ = builderForValue.m109build();
                    onChanged();
                } else {
                    this.tokenBuilder_.setMessage(builderForValue.m109build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeToken(token value) {
                if (this.tokenBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2 && this.token_ != null && this.token_ != token.getDefaultInstance()) {
                        this.token_ = token.newBuilder(this.token_).mergeFrom(value).m111buildPartial();
                    } else {
                        this.token_ = value;
                    }
                    onChanged();
                } else {
                    this.tokenBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearToken() {
                if (this.tokenBuilder_ == null) {
                    this.token_ = null;
                    onChanged();
                } else {
                    this.tokenBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public token.Builder getTokenBuilder() {
                this.bitField0_ |= 2;
                onChanged();
                return getTokenFieldBuilder().getBuilder();
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public tokenOrBuilder getTokenOrBuilder() {
                if (this.tokenBuilder_ != null) {
                    return (tokenOrBuilder) this.tokenBuilder_.getMessageOrBuilder();
                }
                return this.token_ == null ? token.getDefaultInstance() : this.token_;
            }

            private SingleFieldBuilderV3<token, token.Builder, tokenOrBuilder> getTokenFieldBuilder() {
                if (this.tokenBuilder_ == null) {
                    this.tokenBuilder_ = new SingleFieldBuilderV3<>(getToken(), getParentForChildren(), isClean());
                    this.token_ = null;
                }
                return this.tokenBuilder_;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public boolean hasUuid() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public String getUuid() {
                Object ref = this.uuid_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.uuid_ = s;
                    return s;
                }
                return s;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public ByteString getUuidBytes() {
                Object ref = this.uuid_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.uuid_ = b;
                    return b;
                }
                return (ByteString) ref;
            }

            public Builder setUuid(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.uuid_ = value;
                onChanged();
                return this;
            }

            public Builder clearUuid() {
                this.bitField0_ &= -5;
                this.uuid_ = request.getDefaultInstance().getUuid();
                onChanged();
                return this;
            }

            public Builder setUuidBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.uuid_ = value;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public boolean hasQuestion() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public String getQuestion() {
                Object ref = this.question_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.question_ = s;
                    return s;
                }
                return s;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public ByteString getQuestionBytes() {
                Object ref = this.question_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.question_ = b;
                    return b;
                }
                return (ByteString) ref;
            }

            public Builder setQuestion(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.question_ = value;
                onChanged();
                return this;
            }

            public Builder clearQuestion() {
                this.bitField0_ &= -9;
                this.question_ = request.getDefaultInstance().getQuestion();
                onChanged();
                return this;
            }

            public Builder setQuestionBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.question_ = value;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public boolean hasSystem() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public String getSystem() {
                Object ref = this.system_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.system_ = s;
                    return s;
                }
                return s;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public ByteString getSystemBytes() {
                Object ref = this.system_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.system_ = b;
                    return b;
                }
                return (ByteString) ref;
            }

            public Builder setSystem(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.system_ = value;
                onChanged();
                return this;
            }

            public Builder clearSystem() {
                this.bitField0_ &= -17;
                this.system_ = request.getDefaultInstance().getSystem();
                onChanged();
                return this;
            }

            public Builder setSystemBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.system_ = value;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public boolean hasApikey() {
                return (this.bitField0_ & 32) == 32;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public String getApikey() {
                Object ref = this.apikey_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.apikey_ = s;
                    return s;
                }
                return s;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public ByteString getApikeyBytes() {
                Object ref = this.apikey_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.apikey_ = b;
                    return b;
                }
                return (ByteString) ref;
            }

            public Builder setApikey(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.apikey_ = value;
                onChanged();
                return this;
            }

            public Builder clearApikey() {
                this.bitField0_ &= -33;
                this.apikey_ = request.getDefaultInstance().getApikey();
                onChanged();
                return this;
            }

            public Builder setApikeyBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.apikey_ = value;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public boolean hasProvider() {
                return (this.bitField0_ & 64) == 64;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public String getProvider() {
                Object ref = this.provider_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.provider_ = s;
                    return s;
                }
                return s;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public ByteString getProviderBytes() {
                Object ref = this.provider_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.provider_ = b;
                    return b;
                }
                return (ByteString) ref;
            }

            public Builder setProvider(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.provider_ = value;
                onChanged();
                return this;
            }

            public Builder clearProvider() {
                this.bitField0_ &= -65;
                this.provider_ = request.getDefaultInstance().getProvider();
                onChanged();
                return this;
            }

            public Builder setProviderBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.provider_ = value;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public boolean hasModel() {
                return (this.bitField0_ & 128) == 128;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public String getModel() {
                Object ref = this.model_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.model_ = s;
                    return s;
                }
                return s;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.requestOrBuilder
            public ByteString getModelBytes() {
                Object ref = this.model_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.model_ = b;
                    return b;
                }
                return (ByteString) ref;
            }

            public Builder setModel(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 128;
                this.model_ = value;
                onChanged();
                return this;
            }

            public Builder clearModel() {
                this.bitField0_ &= -129;
                this.model_ = request.getDefaultInstance().getModel();
                onChanged();
                return this;
            }

            public Builder setModelBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 128;
                this.model_ = value;
                onChanged();
                return this;
            }

            /* renamed from: setUnknownFields */
            public final Builder m51setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            /* renamed from: mergeUnknownFields */
            public final Builder m45mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public static request getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<request> parser() {
            return PARSER;
        }

        public Parser<request> getParserForType() {
            return PARSER;
        }

        /* renamed from: getDefaultInstanceForType */
        public request m7getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    /* loaded from: classes.dex */
    public static final class response extends GeneratedMessageV3 implements responseOrBuilder {
        public static final int ANSWER_FIELD_NUMBER = 4;
        private static final response DEFAULT_INSTANCE = new response();
        @Deprecated
        public static final Parser<response> PARSER = new AbstractParser<response>() { // from class: com.google.appinventor.components.runtime.chatbot.ChatBotToken.response.1
            /* renamed from: parsePartialFrom */
            public response m59parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new response(input, extensionRegistry);
            }
        };
        public static final int STATUS_FIELD_NUMBER = 2;
        public static final int UUID_FIELD_NUMBER = 3;
        public static final int VERSION_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        private volatile Object answer_;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private long status_;
        private volatile Object uuid_;
        private long version_;

        private response(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        private response() {
            this.memoizedIsInitialized = (byte) -1;
            this.version_ = 1L;
            this.status_ = 0L;
            this.uuid_ = "";
            this.answer_ = "";
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private response(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    try {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0:
                                done = true;
                                break;
                            case 8:
                                this.bitField0_ |= 1;
                                this.version_ = input.readUInt64();
                                break;
                            case 16:
                                this.bitField0_ |= 2;
                                this.status_ = input.readUInt64();
                                break;
                            case 26:
                                ByteString bs = input.readBytes();
                                this.bitField0_ |= 4;
                                this.uuid_ = bs;
                                break;
                            case 34:
                                ByteString bs2 = input.readBytes();
                                this.bitField0_ |= 8;
                                this.answer_ = bs2;
                                break;
                            default:
                                if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                    break;
                                } else {
                                    break;
                                }
                        }
                    } catch (IOException e) {
                        throw new InvalidProtocolBufferException(e).setUnfinishedMessage(this);
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    }
                } finally {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return ChatBotToken.internal_static_response_descriptor;
        }

        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return ChatBotToken.internal_static_response_fieldAccessorTable.ensureFieldAccessorsInitialized(response.class, Builder.class);
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
        public boolean hasVersion() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
        public long getVersion() {
            return this.version_;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
        public boolean hasStatus() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
        public long getStatus() {
            return this.status_;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
        public boolean hasUuid() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
        public String getUuid() {
            Object ref = this.uuid_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.uuid_ = s;
            }
            return s;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
        public ByteString getUuidBytes() {
            Object ref = this.uuid_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.uuid_ = b;
                return b;
            }
            return (ByteString) ref;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
        public boolean hasAnswer() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
        public String getAnswer() {
            Object ref = this.answer_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = (ByteString) ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.answer_ = s;
            }
            return s;
        }

        @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
        public ByteString getAnswerBytes() {
            Object ref = this.answer_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.answer_ = b;
                return b;
            }
            return (ByteString) ref;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                output.writeUInt64(1, this.version_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeUInt64(2, this.status_);
            }
            if ((this.bitField0_ & 4) == 4) {
                GeneratedMessageV3.writeString(output, 3, this.uuid_);
            }
            if ((this.bitField0_ & 8) == 8) {
                GeneratedMessageV3.writeString(output, 4, this.answer_);
            }
            this.unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            int size2 = (this.bitField0_ & 1) == 1 ? 0 + CodedOutputStream.computeUInt64Size(1, this.version_) : 0;
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.computeUInt64Size(2, this.status_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += GeneratedMessageV3.computeStringSize(3, this.uuid_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += GeneratedMessageV3.computeStringSize(4, this.answer_);
            }
            int size3 = size2 + this.unknownFields.getSerializedSize();
            this.memoizedSize = size3;
            return size3;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof response)) {
                return super.equals(obj);
            }
            response other = (response) obj;
            boolean result = 1 != 0 && hasVersion() == other.hasVersion();
            if (hasVersion()) {
                result = result && getVersion() == other.getVersion();
            }
            boolean result2 = result && hasStatus() == other.hasStatus();
            if (hasStatus()) {
                result2 = result2 && getStatus() == other.getStatus();
            }
            boolean result3 = result2 && hasUuid() == other.hasUuid();
            if (hasUuid()) {
                result3 = result3 && getUuid().equals(other.getUuid());
            }
            boolean result4 = result3 && hasAnswer() == other.hasAnswer();
            if (hasAnswer()) {
                result4 = result4 && getAnswer().equals(other.getAnswer());
            }
            return result4 && this.unknownFields.equals(other.unknownFields);
        }

        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = getDescriptorForType().hashCode() + 779;
            if (hasVersion()) {
                hash = (((hash * 37) + 1) * 53) + Internal.hashLong(getVersion());
            }
            if (hasStatus()) {
                hash = (((hash * 37) + 2) * 53) + Internal.hashLong(getStatus());
            }
            if (hasUuid()) {
                hash = (((hash * 37) + 3) * 53) + getUuid().hashCode();
            }
            if (hasAnswer()) {
                hash = (((hash * 37) + 4) * 53) + getAnswer().hashCode();
            }
            int hash2 = (hash * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = hash2;
            return hash2;
        }

        public static response parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (response) PARSER.parseFrom(data);
        }

        public static response parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (response) PARSER.parseFrom(data, extensionRegistry);
        }

        public static response parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (response) PARSER.parseFrom(data);
        }

        public static response parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (response) PARSER.parseFrom(data, extensionRegistry);
        }

        public static response parseFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static response parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        public static response parseDelimitedFrom(InputStream input) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static response parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }

        public static response parseFrom(CodedInputStream input) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static response parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return GeneratedMessageV3.parseWithIOException(PARSER, input, extensionRegistry);
        }

        /* renamed from: newBuilderForType */
        public Builder m56newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.m58toBuilder();
        }

        public static Builder newBuilder(response prototype) {
            return DEFAULT_INSTANCE.m58toBuilder().mergeFrom(prototype);
        }

        /* renamed from: toBuilder */
        public Builder m58toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: newBuilderForType */
        public Builder m55newBuilderForType(GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        /* loaded from: classes.dex */
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements responseOrBuilder {
            private Object answer_;
            private int bitField0_;
            private long status_;
            private Object uuid_;
            private long version_;

            public static final Descriptors.Descriptor getDescriptor() {
                return ChatBotToken.internal_static_response_descriptor;
            }

            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return ChatBotToken.internal_static_response_fieldAccessorTable.ensureFieldAccessorsInitialized(response.class, Builder.class);
            }

            private Builder() {
                this.version_ = 1L;
                this.uuid_ = "";
                this.answer_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.version_ = 1L;
                this.uuid_ = "";
                this.answer_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (response.alwaysUseFieldBuilders) {
                }
            }

            /* renamed from: clear */
            public Builder m69clear() {
                super.clear();
                this.version_ = 1L;
                this.bitField0_ &= -2;
                this.status_ = 0L;
                this.bitField0_ &= -3;
                this.uuid_ = "";
                this.bitField0_ &= -5;
                this.answer_ = "";
                this.bitField0_ &= -9;
                return this;
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return ChatBotToken.internal_static_response_descriptor;
            }

            /* renamed from: getDefaultInstanceForType */
            public response m82getDefaultInstanceForType() {
                return response.getDefaultInstance();
            }

            /* renamed from: build */
            public response m63build() {
                response result = m65buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            /* renamed from: buildPartial */
            public response m65buildPartial() {
                response result = new response(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.version_ = this.version_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.status_ = this.status_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.uuid_ = this.uuid_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.answer_ = this.answer_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            /* renamed from: clone */
            public Builder m80clone() {
                return (Builder) super.clone();
            }

            /* renamed from: setField */
            public Builder m93setField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder) super.setField(field, value);
            }

            /* renamed from: clearField */
            public Builder m71clearField(Descriptors.FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }

            /* renamed from: clearOneof */
            public Builder m74clearOneof(Descriptors.OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }

            /* renamed from: setRepeatedField */
            public Builder m95setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }

            /* renamed from: addRepeatedField */
            public Builder m61addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }

            /* renamed from: mergeFrom */
            public Builder m87mergeFrom(Message other) {
                if (other instanceof response) {
                    return mergeFrom((response) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(response other) {
                if (other != response.getDefaultInstance()) {
                    if (other.hasVersion()) {
                        setVersion(other.getVersion());
                    }
                    if (other.hasStatus()) {
                        setStatus(other.getStatus());
                    }
                    if (other.hasUuid()) {
                        this.bitField0_ |= 4;
                        this.uuid_ = other.uuid_;
                        onChanged();
                    }
                    if (other.hasAnswer()) {
                        this.bitField0_ |= 8;
                        this.answer_ = other.answer_;
                        onChanged();
                    }
                    m91mergeUnknownFields(other.unknownFields);
                    onChanged();
                }
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            /* renamed from: mergeFrom */
            public Builder m88mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                response parsedMessage = null;
                try {
                    try {
                        parsedMessage = (response) response.PARSER.parsePartialFrom(input, extensionRegistry);
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        parsedMessage = (response) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    }
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
            public boolean hasVersion() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
            public long getVersion() {
                return this.version_;
            }

            public Builder setVersion(long value) {
                this.bitField0_ |= 1;
                this.version_ = value;
                onChanged();
                return this;
            }

            public Builder clearVersion() {
                this.bitField0_ &= -2;
                this.version_ = 1L;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
            public boolean hasStatus() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
            public long getStatus() {
                return this.status_;
            }

            public Builder setStatus(long value) {
                this.bitField0_ |= 2;
                this.status_ = value;
                onChanged();
                return this;
            }

            public Builder clearStatus() {
                this.bitField0_ &= -3;
                this.status_ = 0L;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
            public boolean hasUuid() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
            public String getUuid() {
                Object ref = this.uuid_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.uuid_ = s;
                    return s;
                }
                return s;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
            public ByteString getUuidBytes() {
                Object ref = this.uuid_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.uuid_ = b;
                    return b;
                }
                return (ByteString) ref;
            }

            public Builder setUuid(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.uuid_ = value;
                onChanged();
                return this;
            }

            public Builder clearUuid() {
                this.bitField0_ &= -5;
                this.uuid_ = response.getDefaultInstance().getUuid();
                onChanged();
                return this;
            }

            public Builder setUuidBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.uuid_ = value;
                onChanged();
                return this;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
            public boolean hasAnswer() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
            public String getAnswer() {
                Object ref = this.answer_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = (ByteString) ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.answer_ = s;
                    return s;
                }
                return s;
            }

            @Override // com.google.appinventor.components.runtime.chatbot.ChatBotToken.responseOrBuilder
            public ByteString getAnswerBytes() {
                Object ref = this.answer_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.answer_ = b;
                    return b;
                }
                return (ByteString) ref;
            }

            public Builder setAnswer(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.answer_ = value;
                onChanged();
                return this;
            }

            public Builder clearAnswer() {
                this.bitField0_ &= -9;
                this.answer_ = response.getDefaultInstance().getAnswer();
                onChanged();
                return this;
            }

            public Builder setAnswerBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.answer_ = value;
                onChanged();
                return this;
            }

            /* renamed from: setUnknownFields */
            public final Builder m97setUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.setUnknownFields(unknownFields);
            }

            /* renamed from: mergeUnknownFields */
            public final Builder m91mergeUnknownFields(UnknownFieldSet unknownFields) {
                return (Builder) super.mergeUnknownFields(unknownFields);
            }
        }

        public static response getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<response> parser() {
            return PARSER;
        }

        public Parser<response> getParserForType() {
            return PARSER;
        }

        /* renamed from: getDefaultInstanceForType */
        public response m53getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = {"\n\nchat.proto\"D\n\bunsigned\u0012\r\n\u0005huuid\u0018\u0001 \u0001(\t\u0012\u0012\n\u0007version\u0018\u0002 \u0001(\u0004:\u00010\u0012\u0015\n\ngeneration\u0018\u0003 \u0001(\u0004:\u00010\"i\n\u0005token\u0012\u0012\n\u0007version\u0018\u0001 \u0001(\u0004:\u00011\u0012\u0010\n\u0005keyid\u0018\u0002 \u0001(\u0004:\u00011\u0012\u0015\n\ngeneration\u0018\u0003 \u0001(\u0004:\u00010\u0012\u0010\n\bunsigned\u0018\u0004 \u0001(\f\u0012\u0011\n\tsignature\u0018\u0005 \u0001(\f\"\u009e\u0001\n\u0007request\u0012\u0012\n\u0007version\u0018\u0001 \u0001(\u0004:\u00011\u0012\u0015\n\u0005token\u0018\u0002 \u0001(\u000b2\u0006.token\u0012\f\n\u0004uuid\u0018\u0003 \u0001(\t\u0012\u0010\n\bquestion\u0018\u0004 \u0001(\t\u0012\u000e\n\u0006system\u0018\u0005 \u0001(\t\u0012\u000e\n\u0006apikey\u0018\u0006 \u0001(\t\u0012\u0019\n\bprovider\u0018\u0007 \u0001(\t:\u0007chatgpt\u0012\r\n\u0005model\u0018\b \u0001(\t\"O\n\bresponse\u0012\u0012\n\u0007version\u0018\u0001 \u0001(\u0004:\u00011\u0012\u0011\n\u0006status\u0018\u0002 \u0001(\u0004:\u0001", "0\u0012\f\n\u0004uuid\u0018\u0003 \u0001(\t\u0012\u000e\n\u0006answer\u0018\u0004 \u0001(\tBA\n1com.google.appinventor.components.runtime.chatbotB\fChatBotToken"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner() { // from class: com.google.appinventor.components.runtime.chatbot.ChatBotToken.1
            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                Descriptors.FileDescriptor unused = ChatBotToken.descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[0], assigner);
        internal_static_unsigned_descriptor = (Descriptors.Descriptor) getDescriptor().getMessageTypes().get(0);
        internal_static_unsigned_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_unsigned_descriptor, new String[]{"Huuid", "Version", "Generation"});
        internal_static_token_descriptor = (Descriptors.Descriptor) getDescriptor().getMessageTypes().get(1);
        internal_static_token_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_token_descriptor, new String[]{"Version", "Keyid", "Generation", "Unsigned", "Signature"});
        internal_static_request_descriptor = (Descriptors.Descriptor) getDescriptor().getMessageTypes().get(2);
        internal_static_request_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_request_descriptor, new String[]{"Version", "Token", "Uuid", "Question", "System", "Apikey", "Provider", "Model"});
        internal_static_response_descriptor = (Descriptors.Descriptor) getDescriptor().getMessageTypes().get(3);
        internal_static_response_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(internal_static_response_descriptor, new String[]{"Version", "Status", "Uuid", "Answer"});
    }
}
