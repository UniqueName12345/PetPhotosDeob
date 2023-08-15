package gnu.text;

import gnu.lists.CharSeq;
import java.io.Reader;

/* loaded from: classes.dex */
public class QueueReader extends Reader implements Appendable {
    boolean EOFseen;
    char[] buffer;
    int limit;
    int mark;
    int pos;
    int readAheadLimit;

    @Override // java.io.Reader
    public boolean markSupported() {
        return true;
    }

    @Override // java.io.Reader
    public synchronized void mark(int readAheadLimit) {
        this.readAheadLimit = readAheadLimit;
        this.mark = this.pos;
    }

    @Override // java.io.Reader
    public synchronized void reset() {
        if (this.readAheadLimit > 0) {
            this.pos = this.mark;
        }
    }

    void resize(int len) {
        int cur_size = this.limit - this.pos;
        if (this.readAheadLimit > 0 && this.pos - this.mark <= this.readAheadLimit) {
            cur_size = this.limit - this.mark;
        } else {
            this.mark = this.pos;
        }
        char[] new_buffer = this.buffer.length < cur_size + len ? new char[(cur_size * 2) + len] : this.buffer;
        System.arraycopy(this.buffer, this.mark, new_buffer, 0, cur_size);
        this.buffer = new_buffer;
        this.pos -= this.mark;
        this.mark = 0;
        this.limit = cur_size;
    }

    @Override // java.lang.Appendable
    public QueueReader append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        return append(csq, 0, csq.length());
    }

    @Override // java.lang.Appendable
    public synchronized QueueReader append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        int len = end - start;
        reserveSpace(len);
        int sz = this.limit;
        char[] d = this.buffer;
        if (csq instanceof String) {
            ((String) csq).getChars(start, end, d, sz);
        } else if (csq instanceof CharSeq) {
            ((CharSeq) csq).getChars(start, end, d, sz);
        } else {
            int i = start;
            int j = sz;
            while (i < end) {
                int j2 = j + 1;
                d[j] = csq.charAt(i);
                i++;
                j = j2;
            }
        }
        this.limit = sz + len;
        notifyAll();
        return this;
    }

    public void append(char[] chars) {
        append(chars, 0, chars.length);
    }

    public synchronized void append(char[] chars, int off, int len) {
        reserveSpace(len);
        System.arraycopy(chars, off, this.buffer, this.limit, len);
        this.limit += len;
        notifyAll();
    }

    @Override // java.lang.Appendable
    public synchronized QueueReader append(char ch) {
        reserveSpace(1);
        char[] cArr = this.buffer;
        int i = this.limit;
        this.limit = i + 1;
        cArr[i] = ch;
        notifyAll();
        return this;
    }

    public synchronized void appendEOF() {
        this.EOFseen = true;
    }

    protected void reserveSpace(int len) {
        if (this.buffer == null) {
            this.buffer = new char[len + 100];
        } else if (this.buffer.length < this.limit + len) {
            resize(len);
        }
    }

    @Override // java.io.Reader
    public synchronized boolean ready() {
        boolean z;
        if (this.pos >= this.limit) {
            z = this.EOFseen;
        }
        return z;
    }

    public void checkAvailable() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0017, code lost:
        r1 = r4.buffer;
        r2 = r4.pos;
        r4.pos = r2 + 1;
        r0 = r1[r2];
     */
    @Override // java.io.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized int read() {
        /*
            r4 = this;
            monitor-enter(r4)
        L1:
            int r1 = r4.pos     // Catch: java.lang.Throwable -> L22
            int r2 = r4.limit     // Catch: java.lang.Throwable -> L22
            if (r1 < r2) goto L17
            boolean r1 = r4.EOFseen     // Catch: java.lang.Throwable -> L22
            if (r1 == 0) goto Le
            r0 = -1
        Lc:
            monitor-exit(r4)
            return r0
        Le:
            r4.checkAvailable()     // Catch: java.lang.Throwable -> L22
            r4.wait()     // Catch: java.lang.InterruptedException -> L15 java.lang.Throwable -> L22
            goto L1
        L15:
            r1 = move-exception
            goto L1
        L17:
            char[] r1 = r4.buffer     // Catch: java.lang.Throwable -> L22
            int r2 = r4.pos     // Catch: java.lang.Throwable -> L22
            int r3 = r2 + 1
            r4.pos = r3     // Catch: java.lang.Throwable -> L22
            char r0 = r1[r2]     // Catch: java.lang.Throwable -> L22
            goto Lc
        L22:
            r1 = move-exception
            monitor-exit(r4)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.QueueReader.read():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0018, code lost:
        r0 = r3.limit - r3.pos;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x001e, code lost:
        if (r6 <= r0) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0020, code lost:
        r6 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0021, code lost:
        java.lang.System.arraycopy(r3.buffer, r3.pos, r4, r5, r6);
        r3.pos += r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002d, code lost:
        r1 = r6;
     */
    @Override // java.io.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized int read(char[] r4, int r5, int r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r6 != 0) goto Lc
            r1 = 0
        L4:
            monitor-exit(r3)
            return r1
        L6:
            r3.checkAvailable()     // Catch: java.lang.Throwable -> L31
            r3.wait()     // Catch: java.lang.InterruptedException -> L2f java.lang.Throwable -> L31
        Lc:
            int r1 = r3.pos     // Catch: java.lang.Throwable -> L31
            int r2 = r3.limit     // Catch: java.lang.Throwable -> L31
            if (r1 < r2) goto L18
            boolean r1 = r3.EOFseen     // Catch: java.lang.Throwable -> L31
            if (r1 == 0) goto L6
            r1 = -1
            goto L4
        L18:
            int r1 = r3.limit     // Catch: java.lang.Throwable -> L31
            int r2 = r3.pos     // Catch: java.lang.Throwable -> L31
            int r0 = r1 - r2
            if (r6 <= r0) goto L21
            r6 = r0
        L21:
            char[] r1 = r3.buffer     // Catch: java.lang.Throwable -> L31
            int r2 = r3.pos     // Catch: java.lang.Throwable -> L31
            java.lang.System.arraycopy(r1, r2, r4, r5, r6)     // Catch: java.lang.Throwable -> L31
            int r1 = r3.pos     // Catch: java.lang.Throwable -> L31
            int r1 = r1 + r6
            r3.pos = r1     // Catch: java.lang.Throwable -> L31
            r1 = r6
            goto L4
        L2f:
            r1 = move-exception
            goto Lc
        L31:
            r1 = move-exception
            monitor-exit(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.QueueReader.read(char[], int, int):int");
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        this.pos = 0;
        this.limit = 0;
        this.mark = 0;
        this.EOFseen = true;
        this.buffer = null;
    }
}
