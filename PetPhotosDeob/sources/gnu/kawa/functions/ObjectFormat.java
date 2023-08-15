package gnu.kawa.functions;

import gnu.lists.AbstractFormat;
import gnu.lists.Consumer;
import gnu.mapping.OutPort;
import gnu.text.ReportFormat;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.ParsePosition;
import kawa.standard.Scheme;

/* loaded from: classes.dex */
public class ObjectFormat extends ReportFormat {
    private static ObjectFormat plainFormat;
    private static ObjectFormat readableFormat;
    int maxChars;
    boolean readable;

    public static ObjectFormat getInstance(boolean readable) {
        if (readable) {
            if (readableFormat == null) {
                readableFormat = new ObjectFormat(true);
            }
            return readableFormat;
        }
        if (plainFormat == null) {
            plainFormat = new ObjectFormat(false);
        }
        return plainFormat;
    }

    public ObjectFormat(boolean readable) {
        this.readable = readable;
        this.maxChars = -1073741824;
    }

    public ObjectFormat(boolean readable, int maxChars) {
        this.readable = readable;
        this.maxChars = maxChars;
    }

    @Override // gnu.text.ReportFormat
    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        int maxChars = getParam(this.maxChars, -1, args, start);
        if (this.maxChars == -1610612736) {
            start++;
        }
        return format(args, start, dst, maxChars, this.readable);
    }

    private static void print(Object obj, OutPort out, boolean readable) {
        boolean saveReadable = out.printReadable;
        AbstractFormat saveFormat = out.objectFormat;
        try {
            out.printReadable = readable;
            AbstractFormat format = readable ? Scheme.writeFormat : Scheme.displayFormat;
            out.objectFormat = format;
            format.writeObject(obj, (Consumer) out);
        } finally {
            out.printReadable = saveReadable;
            out.objectFormat = saveFormat;
        }
    }

    public static boolean format(Object arg, Writer dst, int maxChars, boolean readable) throws IOException {
        if (maxChars < 0 && (dst instanceof OutPort)) {
            print(arg, (OutPort) dst, readable);
            return true;
        } else if (maxChars < 0 && (dst instanceof CharArrayWriter)) {
            OutPort oport = new OutPort(dst);
            print(arg, oport, readable);
            oport.close();
            return true;
        } else {
            CharArrayWriter wr = new CharArrayWriter();
            OutPort oport2 = new OutPort(wr);
            print(arg, oport2, readable);
            oport2.close();
            int len = wr.size();
            if (maxChars < 0 || len <= maxChars) {
                wr.writeTo(dst);
                return true;
            }
            dst.write(wr.toCharArray(), 0, maxChars);
            return false;
        }
    }

    public static int format(Object[] args, int start, Writer dst, int maxChars, boolean readable) throws IOException {
        Object obj;
        if (start >= args.length) {
            obj = "#<missing format argument>";
            start--;
            readable = false;
            maxChars = -1;
        } else {
            obj = args[start];
        }
        format(obj, dst, maxChars, readable);
        return start + 1;
    }

    @Override // gnu.text.ReportFormat, java.text.Format
    public Object parseObject(String text, ParsePosition status) {
        throw new RuntimeException("ObjectFormat.parseObject - not implemented");
    }
}
