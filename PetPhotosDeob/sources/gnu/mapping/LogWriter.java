package gnu.mapping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/* loaded from: classes.dex */
public class LogWriter extends FilterWriter {
    private Writer log;

    public LogWriter(Writer out) {
        super(out);
    }

    public final Writer getLogFile() {
        return this.log;
    }

    public void setLogFile(Writer log) {
        this.log = log;
    }

    public void setLogFile(String name) throws IOException {
        this.log = new PrintWriter(new BufferedWriter(new FileWriter(name)));
    }

    public void closeLogFile() throws IOException {
        if (this.log != null) {
            this.log.close();
        }
        this.log = null;
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(int c) throws IOException {
        if (this.log != null) {
            this.log.write(c);
        }
        super.write(c);
    }

    public void echo(char[] buf, int off, int len) throws IOException {
        if (this.log != null) {
            this.log.write(buf, off, len);
        }
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(char[] buf, int off, int len) throws IOException {
        if (this.log != null) {
            this.log.write(buf, off, len);
        }
        super.write(buf, off, len);
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(String str, int off, int len) throws IOException {
        if (this.log != null) {
            this.log.write(str, off, len);
        }
        super.write(str, off, len);
    }

    @Override // java.io.FilterWriter, java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        if (this.log != null) {
            this.log.flush();
        }
        super.flush();
    }

    @Override // java.io.FilterWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.log != null) {
            this.log.close();
        }
        super.close();
    }
}
