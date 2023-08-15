package com.google.appinventor.components.runtime.repackaged.org.json.zip;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class BitOutputStream implements BitWriter {
    private OutputStream out;
    private int unwritten;
    private long nrBits = 0;
    private int vacant = 8;

    public BitOutputStream(OutputStream out) {
        this.out = out;
    }

    @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.BitWriter
    public long nrBits() {
        return this.nrBits;
    }

    @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.BitWriter
    public void one() throws IOException {
        write(1, 1);
    }

    @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.BitWriter
    public void pad(int factor) throws IOException {
        int padding = factor - ((int) (this.nrBits % factor));
        int excess = padding & 7;
        if (excess > 0) {
            write(0, excess);
            padding -= excess;
        }
        while (padding > 0) {
            write(0, 8);
            padding -= 8;
        }
        this.out.flush();
    }

    @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.BitWriter
    public void write(int bits, int width) throws IOException {
        if (bits != 0 || width != 0) {
            if (width <= 0 || width > 32) {
                throw new IOException("Bad write width.");
            }
            while (width > 0) {
                int actual = width;
                if (actual > this.vacant) {
                    actual = this.vacant;
                }
                this.unwritten |= ((bits >>> (width - actual)) & BitInputStream.mask[actual]) << (this.vacant - actual);
                width -= actual;
                this.nrBits += actual;
                this.vacant -= actual;
                if (this.vacant == 0) {
                    this.out.write(this.unwritten);
                    this.unwritten = 0;
                    this.vacant = 8;
                }
            }
        }
    }

    @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.BitWriter
    public void zero() throws IOException {
        write(0, 1);
    }
}
