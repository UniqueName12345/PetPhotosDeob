package com.google.appinventor.components.runtime.repackaged.org.json;

import java.io.StringWriter;

/* loaded from: classes2.dex */
public class JSONStringer extends JSONWriter {
    public JSONStringer() {
        super(new StringWriter());
    }

    public String toString() {
        if (this.mode == 'd') {
            return this.writer.toString();
        }
        return null;
    }
}
