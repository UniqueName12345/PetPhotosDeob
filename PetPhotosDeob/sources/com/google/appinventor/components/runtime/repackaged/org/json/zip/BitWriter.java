package com.google.appinventor.components.runtime.repackaged.org.json.zip;

import java.io.IOException;

/* loaded from: classes2.dex */
public interface BitWriter {
    long nrBits();

    void one() throws IOException;

    void pad(int i) throws IOException;

    void write(int i, int i2) throws IOException;

    void zero() throws IOException;
}
