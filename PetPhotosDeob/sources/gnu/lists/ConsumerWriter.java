package gnu.lists;

import java.io.Writer;

/* loaded from: classes.dex */
public class ConsumerWriter extends Writer {
    protected Consumer out;

    public ConsumerWriter(Consumer out) {
        this.out = out;
    }

    @Override // java.io.Writer
    public void write(char[] buffer, int offset, int length) {
        this.out.write(buffer, offset, length);
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        flush();
    }

    public void finalize() {
        close();
    }
}
