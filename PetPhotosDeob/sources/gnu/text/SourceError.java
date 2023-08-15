package gnu.text;

import java.io.PrintStream;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class SourceError implements SourceLocator {
    public String code;
    public int column;
    public Throwable fakeException;
    public String filename;
    public int line;
    public String message;
    public SourceError next;
    public char severity;

    public SourceError(char severity, String filename, int line, int column, String message) {
        this.severity = severity;
        this.filename = filename;
        this.line = line;
        this.column = column;
        this.message = message;
    }

    public SourceError(char severity, SourceLocator location, String message) {
        this(severity, location.getFileName(), location.getLineNumber(), location.getColumnNumber(), message);
    }

    public SourceError(LineBufferedReader port, char severity, String message) {
        this(severity, port.getName(), port.getLineNumber() + 1, port.getColumnNumber(), message);
        if (this.column >= 0) {
            this.column++;
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.filename == null ? "<unknown>" : this.filename);
        if (this.line > 0 || this.column > 0) {
            buffer.append(':');
            buffer.append(this.line);
            if (this.column > 0) {
                buffer.append(':');
                buffer.append(this.column);
            }
        }
        buffer.append(": ");
        if (this.severity == 'w') {
            buffer.append("warning - ");
        }
        buffer.append(this.message);
        if (this.code != null) {
            buffer.append(" [");
            buffer.append(this.code);
            buffer.append("]");
        }
        if (this.fakeException != null) {
            StackTraceElement[] stackTrace = this.fakeException.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                buffer.append("\n");
                buffer.append("    ");
                buffer.append(stackTraceElement.toString());
            }
        }
        return buffer.toString();
    }

    public void print(PrintWriter out) {
        out.print(this);
    }

    public void println(PrintWriter out) {
        String line = toString();
        while (true) {
            int nl = line.indexOf(10);
            if (nl >= 0) {
                out.println(line.substring(0, nl));
                line = line.substring(nl + 1);
            } else {
                out.println(line);
                return;
            }
        }
    }

    public void println(PrintStream out) {
        String line = toString();
        while (true) {
            int nl = line.indexOf(10);
            if (nl >= 0) {
                out.println(line.substring(0, nl));
                line = line.substring(nl + 1);
            } else {
                out.println(line);
                return;
            }
        }
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public int getLineNumber() {
        if (this.line == 0) {
            return -1;
        }
        return this.line;
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public int getColumnNumber() {
        if (this.column == 0) {
            return -1;
        }
        return this.column;
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public String getPublicId() {
        return null;
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public String getSystemId() {
        return this.filename;
    }

    @Override // gnu.text.SourceLocator
    public String getFileName() {
        return this.filename;
    }

    @Override // gnu.text.SourceLocator
    public boolean isStableSourceLocation() {
        return true;
    }
}
