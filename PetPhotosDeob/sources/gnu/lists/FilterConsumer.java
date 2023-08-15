package gnu.lists;

/* loaded from: classes.dex */
public class FilterConsumer implements XConsumer {
    protected Object attributeType;
    protected Consumer base;
    protected boolean inAttribute;
    protected boolean skipping;

    public FilterConsumer(Consumer base) {
        this.base = base;
    }

    protected void beforeContent() {
    }

    protected void beforeNode() {
    }

    @Override // gnu.lists.Consumer
    public void write(int v) {
        beforeContent();
        if (!this.skipping) {
            this.base.write(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void writeBoolean(boolean v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeBoolean(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void writeFloat(float v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeFloat(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void writeDouble(double v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeDouble(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void writeInt(int v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeInt(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void writeLong(long v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeLong(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void startDocument() {
        if (!this.skipping) {
            this.base.startDocument();
        }
    }

    @Override // gnu.lists.Consumer
    public void endDocument() {
        if (!this.skipping) {
            this.base.endDocument();
        }
    }

    @Override // gnu.lists.Consumer
    public void startElement(Object type) {
        if (!this.skipping) {
            beforeNode();
            this.base.startElement(type);
        }
    }

    @Override // gnu.lists.Consumer
    public void endElement() {
        if (!this.skipping) {
            this.base.endElement();
        }
    }

    @Override // gnu.lists.Consumer
    public void startAttribute(Object attrType) {
        this.attributeType = attrType;
        this.inAttribute = true;
        if (!this.skipping) {
            beforeNode();
            this.base.startAttribute(attrType);
        }
    }

    @Override // gnu.lists.Consumer
    public void endAttribute() {
        if (!this.skipping) {
            this.base.endAttribute();
        }
        this.inAttribute = false;
    }

    @Override // gnu.lists.XConsumer
    public void writeComment(char[] chars, int offset, int length) {
        if (!this.skipping) {
            beforeNode();
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeComment(chars, offset, length);
            }
        }
    }

    @Override // gnu.lists.XConsumer
    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        if (!this.skipping) {
            beforeNode();
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeProcessingInstruction(target, content, offset, length);
            }
        }
    }

    @Override // gnu.lists.XConsumer
    public void writeCDATA(char[] chars, int offset, int length) {
        beforeContent();
        if (!this.skipping) {
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeCDATA(chars, offset, length);
            } else {
                this.base.write(chars, offset, length);
            }
        }
    }

    @Override // gnu.lists.XConsumer
    public void beginEntity(Object baseUri) {
        if (!this.skipping) {
            beforeNode();
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).beginEntity(baseUri);
            }
        }
    }

    @Override // gnu.lists.XConsumer
    public void endEntity() {
        if (!this.skipping && (this.base instanceof XConsumer)) {
            ((XConsumer) this.base).endEntity();
        }
    }

    @Override // gnu.lists.Consumer
    public void writeObject(Object v) {
        beforeContent();
        if (!this.skipping) {
            this.base.writeObject(v);
        }
    }

    @Override // gnu.lists.Consumer
    public boolean ignoring() {
        return this.base.ignoring();
    }

    @Override // gnu.lists.Consumer
    public void write(char[] buf, int off, int len) {
        beforeContent();
        if (!this.skipping) {
            this.base.write(buf, off, len);
        }
    }

    @Override // gnu.lists.Consumer
    public void write(String str) {
        write(str, 0, str.length());
    }

    @Override // gnu.lists.Consumer
    public void write(CharSequence str, int start, int length) {
        beforeContent();
        if (!this.skipping) {
            this.base.write(str, start, length);
        }
    }

    @Override // java.lang.Appendable
    public Consumer append(char c) {
        write(c);
        return this;
    }

    @Override // java.lang.Appendable
    public Consumer append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        write(csq, 0, csq.length());
        return this;
    }

    @Override // java.lang.Appendable
    public Consumer append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        write(csq, start, end - start);
        return this;
    }
}
