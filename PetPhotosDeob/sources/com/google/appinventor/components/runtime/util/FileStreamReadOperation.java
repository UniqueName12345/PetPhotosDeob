package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* loaded from: classes.dex */
public abstract class FileStreamReadOperation extends FileReadOperation {
    private static final String LOG_TAG = FileStreamReadOperation.class.getSimpleName();

    protected abstract boolean process(String str);

    public FileStreamReadOperation(Form form, Component component, String method, String fileName, FileScope scope, boolean async) {
        super(form, component, method, fileName, scope, async);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.util.FileReadOperation, com.google.appinventor.components.runtime.util.FileStreamOperation
    public boolean process(InputStream in) throws IOException {
        InputStreamReader reader = null;
        try {
            InputStreamReader reader2 = new InputStreamReader(in);
            try {
                boolean close = process(reader2);
                if (close) {
                    IOUtils.closeQuietly(LOG_TAG, reader2);
                }
                return close;
            } catch (Throwable th) {
                th = th;
                reader = reader2;
                if (1 != 0) {
                    IOUtils.closeQuietly(LOG_TAG, reader);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    protected boolean process(InputStreamReader reader) throws IOException {
        return process(IOUtils.readReader(reader));
    }
}
