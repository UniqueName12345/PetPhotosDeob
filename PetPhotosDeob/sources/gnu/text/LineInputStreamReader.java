package gnu.text;

import com.google.appinventor.components.runtime.util.Ev3Constants;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/* loaded from: classes.dex */
public class LineInputStreamReader extends LineBufferedReader {
    byte[] barr;
    ByteBuffer bbuf;
    char[] carr;
    CharBuffer cbuf;
    Charset cset;
    CharsetDecoder decoder;
    InputStream istrm;

    public void setCharset(Charset cset) {
        this.cset = cset;
        this.decoder = cset.newDecoder();
    }

    public void setCharset(String name) {
        Charset cset = Charset.forName(name);
        if (this.cset == null) {
            setCharset(cset);
        } else if (!cset.equals(this.cset)) {
            throw new RuntimeException("encoding " + name + " does not match previous " + this.cset);
        }
    }

    public LineInputStreamReader(InputStream in) {
        super((Reader) null);
        this.barr = new byte[8192];
        this.cbuf = null;
        this.bbuf = ByteBuffer.wrap(this.barr);
        this.bbuf.position(this.barr.length);
        this.istrm = in;
    }

    @Override // gnu.text.LineBufferedReader, java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.in != null) {
            this.in.close();
        }
        this.istrm.close();
    }

    private int fillBytes(int remaining) throws IOException {
        int n = this.istrm.read(this.barr, remaining, this.barr.length - remaining);
        this.bbuf.position(0);
        this.bbuf.limit((n >= 0 ? n : 0) + remaining);
        return n;
    }

    public void markStart() throws IOException {
    }

    public void resetStart(int pos) throws IOException {
        this.bbuf.position(pos);
    }

    public int getByte() throws IOException {
        if (!this.bbuf.hasRemaining()) {
            int n = fillBytes(0);
            if (n <= 0) {
                return -1;
            }
        }
        return this.bbuf.get() & Ev3Constants.Opcode.TST;
    }

    @Override // gnu.text.LineBufferedReader
    public int fill(int len) throws IOException {
        int count;
        if (this.cset == null) {
            setCharset("UTF-8");
        }
        if (this.buffer != this.carr) {
            this.cbuf = CharBuffer.wrap(this.buffer);
            this.carr = this.buffer;
        }
        this.cbuf.limit(this.pos + len);
        this.cbuf.position(this.pos);
        boolean eof = false;
        while (true) {
            CoderResult cres = this.decoder.decode(this.bbuf, this.cbuf, false);
            count = this.cbuf.position() - this.pos;
            if (count > 0 || !cres.isUnderflow()) {
                break;
            }
            int rem = this.bbuf.remaining();
            if (rem > 0) {
                this.bbuf.compact();
            }
            int n = fillBytes(rem);
            if (n < 0) {
                eof = true;
                break;
            }
        }
        if (count == 0 && eof) {
            return -1;
        }
        return count;
    }

    @Override // gnu.text.LineBufferedReader, java.io.Reader
    public boolean ready() throws IOException {
        return this.pos < this.limit || this.bbuf.hasRemaining() || this.istrm.available() > 0;
    }
}
