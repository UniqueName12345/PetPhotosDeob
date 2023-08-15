package gnu.lists;

import gnu.mapping.CharArrayOutPort;
import gnu.mapping.OutPort;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/* loaded from: classes.dex */
public abstract class AbstractFormat extends Format {
    public abstract void writeObject(Object obj, Consumer consumer);

    /* JADX INFO: Access modifiers changed from: protected */
    public void write(String str, Consumer out) {
        out.write(str);
    }

    public void write(int v, Consumer out) {
        out.write(v);
    }

    public void writeLong(long v, Consumer out) {
        out.writeLong(v);
    }

    public void writeInt(int i, Consumer out) {
        writeLong(i, out);
    }

    public void writeBoolean(boolean v, Consumer out) {
        out.writeBoolean(v);
    }

    public void startElement(Object type, Consumer out) {
        write("(", out);
        write(type.toString(), out);
        write(" ", out);
    }

    public void endElement(Consumer out) {
        write(")", out);
    }

    public void startAttribute(Object attrType, Consumer out) {
        write(attrType.toString(), out);
        write(": ", out);
    }

    public void endAttribute(Consumer out) {
        write(" ", out);
    }

    public void format(Object value, Consumer out) {
        if (out instanceof OutPort) {
            OutPort pout = (OutPort) out;
            AbstractFormat saveFormat = pout.objectFormat;
            try {
                pout.objectFormat = this;
                out.writeObject(value);
                return;
            } finally {
                pout.objectFormat = saveFormat;
            }
        }
        out.writeObject(value);
    }

    public final void writeObject(Object obj, PrintConsumer out) {
        writeObject(obj, (Consumer) out);
    }

    public final void writeObject(Object obj, Writer out) {
        if (out instanceof Consumer) {
            writeObject(obj, (Consumer) out);
            return;
        }
        OutPort port = new OutPort(out, false, true);
        writeObject(obj, (Consumer) out);
        port.close();
    }

    @Override // java.text.Format
    public StringBuffer format(Object val, StringBuffer sbuf, FieldPosition fpos) {
        CharArrayOutPort out = new CharArrayOutPort();
        writeObject(val, (PrintConsumer) out);
        sbuf.append(out.toCharArray());
        out.close();
        return sbuf;
    }

    @Override // java.text.Format
    public Object parseObject(String text, ParsePosition status) {
        throw new Error(getClass().getName() + ".parseObject - not implemented");
    }
}
