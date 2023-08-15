package com.google.appinventor.components.runtime.repackaged.org.json.zip;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class BitInputStream implements BitReader {
    static final int[] mask = {0, 1, 3, 7, 15, 31, 63, 127, 255};
    private int available;
    private InputStream in;
    private long nrBits;
    private int unread;

    public BitInputStream(InputStream in) {
        this.available = 0;
        this.unread = 0;
        this.nrBits = 0L;
        this.in = in;
    }

    public BitInputStream(InputStream in, int firstByte) {
        this.available = 0;
        this.unread = 0;
        this.nrBits = 0L;
        this.in = in;
        this.unread = firstByte;
        this.available = 8;
    }

    @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.BitReader
    public boolean bit() throws IOException {
        return read(1) != 0;
    }

    @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.BitReader
    public long nrBits() {
        return this.nrBits;
    }

    @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.BitReader
    public boolean pad(int factor) throws IOException {
        int padding = factor - ((int) (this.nrBits % factor));
        boolean result = true;
        for (int i = 0; i < padding; i++) {
            if (bit()) {
                result = false;
            }
        }
        return result;
    }

    @Override // com.google.appinventor.components.runtime.repackaged.org.json.zip.BitReader
    public int read(int width) throws IOException {
        if (width == 0) {
            return 0;
        }
        if (width < 0 || width > 32) {
            throw new IOException("Bad read width.");
        }
        int result = 0;
        while (width > 0) {
            if (this.available == 0) {
                this.unread = this.in.read();
                if (this.unread < 0) {
                    throw new IOException("Attempt to read past end.");
                }
                this.available = 8;
            }
            int take = width;
            if (take > this.available) {
                take = this.available;
            }
            result |= ((this.unread >>> (this.available - take)) & mask[take]) << (width - take);
            this.nrBits += take;
            this.available -= take;
            width -= take;
        }
        return result;
    }
}
