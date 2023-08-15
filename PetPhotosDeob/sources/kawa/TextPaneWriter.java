package kawa;

import java.io.Writer;
import javax.swing.text.AttributeSet;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ReplPaneOutPort.java */
/* loaded from: classes.dex */
public class TextPaneWriter extends Writer {
    ReplDocument document;
    String str = "";
    AttributeSet style;

    public TextPaneWriter(ReplDocument document, AttributeSet style) {
        this.document = document;
        this.style = style;
    }

    @Override // java.io.Writer
    public synchronized void write(int x) {
        this.str += ((char) x);
        if (x == 10) {
            flush();
        }
    }

    @Override // java.io.Writer
    public void write(String str) {
        this.document.write(str, this.style);
    }

    @Override // java.io.Writer
    public synchronized void write(char[] data, int off, int len) {
        flush();
        if (len != 0) {
            write(new String(data, off, len));
        }
    }

    @Override // java.io.Writer, java.io.Flushable
    public synchronized void flush() {
        String s = this.str;
        if (!s.equals("")) {
            this.str = "";
            write(s);
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        flush();
    }
}
