package gnu.text;

import java.io.Reader;

/* loaded from: classes.dex */
public class NullReader extends Reader {
    public static final NullReader nullReader = new NullReader();

    @Override // java.io.Reader
    public int read(char[] buffer, int offset, int length) {
        return -1;
    }

    @Override // java.io.Reader
    public boolean ready() {
        return true;
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }
}
