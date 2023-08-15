package com.google.appinventor.components.runtime.repackaged.org.json.zip;

import java.io.IOException;

/* loaded from: classes2.dex */
public interface BitReader {
    boolean bit() throws IOException;

    long nrBits();

    boolean pad(int i) throws IOException;

    int read(int i) throws IOException;
}
