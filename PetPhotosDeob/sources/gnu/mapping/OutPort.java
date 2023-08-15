package gnu.mapping;

import gnu.lists.AbstractFormat;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.PrintConsumer;
import gnu.text.Path;
import gnu.text.PrettyWriter;
import gnu.text.Printable;
import gnu.text.WriterManager;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.NumberFormat;

/* loaded from: classes.dex */
public class OutPort extends PrintConsumer implements Printable {
    public static final ThreadLocation errLocation;
    static Writer logFile;
    private Writer base;
    protected PrettyWriter bout;
    NumberFormat numberFormat;
    public AbstractFormat objectFormat;
    Path path;
    public boolean printReadable;
    protected Object unregisterRef;
    static OutPort outInitial = new OutPort(new LogWriter(new BufferedWriter(new OutputStreamWriter(System.out))), true, true, Path.valueOf("/dev/stdout"));
    private static OutPort errInitial = new OutPort(new LogWriter(new OutputStreamWriter(System.err)), true, true, Path.valueOf("/dev/stderr"));
    public static final ThreadLocation outLocation = new ThreadLocation("out-default");

    protected OutPort(Writer base, PrettyWriter out, boolean autoflush) {
        super(out, autoflush);
        this.bout = out;
        this.base = base;
        if (closeOnExit()) {
            this.unregisterRef = WriterManager.instance.register(out);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public OutPort(OutPort out, boolean autoflush) {
        this(out, out.bout, autoflush);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public OutPort(Writer out, boolean autoflush) {
        this(out, out instanceof OutPort ? ((OutPort) out).bout : new PrettyWriter(out, true), autoflush);
    }

    public OutPort(Writer base, boolean printPretty, boolean autoflush) {
        this(base, new PrettyWriter(base, printPretty), autoflush);
    }

    public OutPort(Writer base, boolean printPretty, boolean autoflush, Path path) {
        this(base, new PrettyWriter(base, printPretty), autoflush);
        this.path = path;
    }

    public OutPort(OutputStream out) {
        this(out, (Path) null);
    }

    public OutPort(OutputStream out, Path path) {
        this((Writer) new OutputStreamWriter(out), true, path);
    }

    public OutPort(Writer out) {
        this(out, out instanceof OutPort ? ((OutPort) out).bout : new PrettyWriter(out, false), false);
    }

    public OutPort(Writer base, Path path) {
        this(base, false, false);
        this.path = path;
    }

    public OutPort(Writer base, boolean autoflush, Path path) {
        this(base, false, autoflush);
        this.path = path;
    }

    static {
        outLocation.setGlobal(outInitial);
        errLocation = new ThreadLocation("err-default");
        errLocation.setGlobal(errInitial);
    }

    public static OutPort outDefault() {
        return (OutPort) outLocation.get();
    }

    public static void setOutDefault(OutPort o) {
        outLocation.set(o);
    }

    public static OutPort errDefault() {
        return (OutPort) errLocation.get();
    }

    public static void setErrDefault(OutPort e) {
        errLocation.set(e);
    }

    public static OutPort openFile(Object fname) throws IOException {
        Writer wr;
        Object conv = Environment.user().get("port-char-encoding");
        Path path = Path.valueOf(fname);
        OutputStream strm = new BufferedOutputStream(path.openOutputStream());
        if (conv == null || conv == Boolean.TRUE) {
            wr = new OutputStreamWriter(strm);
        } else {
            if (conv == Boolean.FALSE) {
                conv = "8859_1";
            }
            wr = new OutputStreamWriter(strm, conv.toString());
        }
        return new OutPort(wr, path);
    }

    public void echo(char[] buf, int off, int len) throws IOException {
        if (this.base instanceof LogWriter) {
            ((LogWriter) this.base).echo(buf, off, len);
        }
    }

    public static void closeLogFile() throws IOException {
        if (logFile != null) {
            logFile.close();
            logFile = null;
        }
        if (outInitial.base instanceof LogWriter) {
            ((LogWriter) outInitial.base).setLogFile((Writer) null);
        }
        if (errInitial.base instanceof LogWriter) {
            ((LogWriter) errInitial.base).setLogFile((Writer) null);
        }
    }

    public static void setLogFile(String name) throws IOException {
        if (logFile != null) {
            closeLogFile();
        }
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(name)));
        if (outInitial.base instanceof LogWriter) {
            ((LogWriter) outInitial.base).setLogFile(logFile);
        }
        if (errInitial.base instanceof LogWriter) {
            ((LogWriter) errInitial.base).setLogFile(logFile);
        }
    }

    protected static final boolean isWordChar(char ch) {
        return Character.isJavaIdentifierPart(ch) || ch == '-' || ch == '+';
    }

    @Override // java.io.PrintWriter
    public void print(int v) {
        if (this.numberFormat == null) {
            super.print(v);
        } else {
            print(this.numberFormat.format(v));
        }
    }

    @Override // java.io.PrintWriter
    public void print(long v) {
        if (this.numberFormat == null) {
            super.print(v);
        } else {
            print(this.numberFormat.format(v));
        }
    }

    @Override // java.io.PrintWriter
    public void print(double v) {
        if (this.numberFormat == null) {
            super.print(v);
        } else {
            print(this.numberFormat.format(v));
        }
    }

    @Override // java.io.PrintWriter
    public void print(float v) {
        if (this.numberFormat == null) {
            super.print(v);
        } else {
            print(this.numberFormat.format(v));
        }
    }

    @Override // java.io.PrintWriter
    public void print(boolean v) {
        if (this.objectFormat == null) {
            super.print(v);
        } else {
            this.objectFormat.writeBoolean(v, this);
        }
    }

    @Override // java.io.PrintWriter
    public void print(String v) {
        if (v == null) {
            v = "(null)";
        }
        write(v);
    }

    @Override // java.io.PrintWriter
    public void print(Object v) {
        if (this.objectFormat != null) {
            this.objectFormat.writeObject(v, (PrintConsumer) this);
        } else if (v instanceof Consumable) {
            ((Consumable) v).consume(this);
        } else {
            if (v == null) {
                v = "null";
            }
            super.print(v);
        }
    }

    @Override // gnu.text.Printable
    public void print(Consumer out) {
        out.write("#<output-port");
        if (this.path != null) {
            out.write(32);
            out.write(this.path.toString());
        }
        out.write(62);
    }

    @Override // gnu.lists.PrintConsumer, gnu.lists.Consumer
    public void startElement(Object type) {
        if (this.objectFormat != null) {
            this.objectFormat.startElement(type, this);
            return;
        }
        print('(');
        print(type);
    }

    @Override // gnu.lists.PrintConsumer, gnu.lists.Consumer
    public void endElement() {
        if (this.objectFormat != null) {
            this.objectFormat.endElement(this);
        } else {
            print(')');
        }
    }

    @Override // gnu.lists.PrintConsumer, gnu.lists.Consumer
    public void startAttribute(Object attrType) {
        if (this.objectFormat != null) {
            this.objectFormat.startAttribute(attrType, this);
            return;
        }
        print(' ');
        print(attrType);
        print(": ");
    }

    @Override // gnu.lists.PrintConsumer, gnu.lists.Consumer
    public void endAttribute() {
        if (this.objectFormat != null) {
            this.objectFormat.endAttribute(this);
        } else {
            print(' ');
        }
    }

    public void writeWordEnd() {
        this.bout.writeWordEnd();
    }

    public void writeWordStart() {
        this.bout.writeWordStart();
    }

    public void freshLine() {
        int col = this.bout.getColumnNumber();
        if (col != 0) {
            println();
        }
    }

    public int getColumnNumber() {
        return this.bout.getColumnNumber();
    }

    public void setColumnNumber(int column) {
        this.bout.setColumnNumber(column);
    }

    public void clearBuffer() {
        this.bout.clearBuffer();
    }

    public void closeThis() {
        try {
            if (!(this.base instanceof OutPort) || ((OutPort) this.base).bout != this.bout) {
                this.bout.closeThis();
            }
        } catch (IOException e) {
            setError();
        }
        WriterManager.instance.unregister(this.unregisterRef);
    }

    @Override // java.io.PrintWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            if ((this.base instanceof OutPort) && ((OutPort) this.base).bout == this.bout) {
                this.base.close();
            } else {
                this.out.close();
            }
        } catch (IOException e) {
            setError();
        }
        WriterManager.instance.unregister(this.unregisterRef);
    }

    protected boolean closeOnExit() {
        return true;
    }

    public static void runCleanups() {
        WriterManager.instance.run();
    }

    public void startLogicalBlock(String prefix, boolean perLine, String suffix) {
        this.bout.startLogicalBlock(prefix, perLine, suffix);
    }

    public void startLogicalBlock(String prefix, String suffix, int indent) {
        this.bout.startLogicalBlock(prefix, false, suffix);
        PrettyWriter prettyWriter = this.bout;
        if (prefix != null) {
            indent -= prefix.length();
        }
        prettyWriter.addIndentation(indent, false);
    }

    public void endLogicalBlock(String suffix) {
        this.bout.endLogicalBlock(suffix);
    }

    public void writeBreak(int kind) {
        this.bout.writeBreak(kind);
    }

    public void writeSpaceLinear() {
        write(32);
        writeBreak(78);
    }

    public void writeBreakLinear() {
        writeBreak(78);
    }

    public void writeSpaceFill() {
        write(32);
        writeBreak(70);
    }

    public void writeBreakFill() {
        writeBreak(70);
    }

    public void setIndentation(int amount, boolean current) {
        this.bout.addIndentation(amount, current);
    }
}
