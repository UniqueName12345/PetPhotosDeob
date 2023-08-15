package gnu.lists;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

/* loaded from: classes.dex */
public class PrintConsumer extends PrintWriter implements Appendable, Consumer {
    public PrintConsumer(Consumer out, boolean autoFlush) {
        super(out instanceof Writer ? (Writer) out : new ConsumerWriter(out), autoFlush);
    }

    public PrintConsumer(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public PrintConsumer(Writer out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public PrintConsumer(Writer out) {
        super(out);
    }

    protected void startNumber() {
    }

    protected void endNumber() {
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.lang.Appendable
    public PrintConsumer append(char c) {
        print(c);
        return this;
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.lang.Appendable
    public PrintConsumer append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        append(csq, 0, csq.length());
        return this;
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.lang.Appendable
    public PrintConsumer append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        for (int i = start; i < end; i++) {
            append(csq.charAt(i));
        }
        return this;
    }

    @Override // gnu.lists.Consumer
    public void write(CharSequence csq, int start, int end) {
        if (csq instanceof String) {
            write((String) csq, start, end);
            return;
        }
        for (int i = start; i < end; i++) {
            write(csq.charAt(i));
        }
    }

    @Override // gnu.lists.Consumer
    public void writeBoolean(boolean v) {
        print(v);
    }

    @Override // gnu.lists.Consumer
    public void writeFloat(float v) {
        startNumber();
        print(v);
        endNumber();
    }

    @Override // gnu.lists.Consumer
    public void writeDouble(double v) {
        startNumber();
        print(v);
        endNumber();
    }

    @Override // gnu.lists.Consumer
    public void writeInt(int v) {
        startNumber();
        print(v);
        endNumber();
    }

    @Override // gnu.lists.Consumer
    public void writeLong(long v) {
        startNumber();
        print(v);
        endNumber();
    }

    @Override // gnu.lists.Consumer
    public void startDocument() {
    }

    @Override // gnu.lists.Consumer
    public void endDocument() {
    }

    @Override // gnu.lists.Consumer
    public void startElement(Object type) {
    }

    @Override // gnu.lists.Consumer
    public void endElement() {
    }

    @Override // gnu.lists.Consumer
    public void startAttribute(Object attrType) {
    }

    @Override // gnu.lists.Consumer
    public void endAttribute() {
    }

    @Override // gnu.lists.Consumer
    public void writeObject(Object v) {
        print(v);
    }

    @Override // gnu.lists.Consumer
    public boolean ignoring() {
        return false;
    }
}
