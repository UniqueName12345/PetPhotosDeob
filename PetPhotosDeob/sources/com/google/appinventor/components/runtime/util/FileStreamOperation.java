package com.google.appinventor.components.runtime.util;

import android.util.Log;
import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes.dex */
public class FileStreamOperation<T extends Closeable> extends SingleFileOperation {
    private static final String LOG_TAG = FileStreamOperation.class.getSimpleName();

    /* JADX INFO: Access modifiers changed from: protected */
    public FileStreamOperation(Form form, Component component, String method, String fileName, FileScope scope, FileAccessMode accessMode, boolean async) {
        super(form, component, method, fileName, scope, accessMode, async);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public FileStreamOperation(Form form, Component component, String method, ScopedFile file, FileAccessMode accessMode, boolean async) {
        super(form, component, method, file, accessMode, async);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.util.SingleFileOperation
    public void processFile(ScopedFile file) {
        T stream = null;
        try {
            try {
                stream = openFile();
                boolean shouldClose = process(stream);
                if (shouldClose) {
                    IOUtils.closeQuietly(this.component.getClass().getSimpleName(), stream);
                }
            } catch (IOException e) {
                e.printStackTrace();
                onError(e);
                if (1 != 0) {
                    IOUtils.closeQuietly(this.component.getClass().getSimpleName(), stream);
                }
            }
        } catch (Throwable th) {
            if (1 != 0) {
                IOUtils.closeQuietly(this.component.getClass().getSimpleName(), stream);
            }
            throw th;
        }
    }

    public void onError(IOException e) {
        Log.e(LOG_TAG, "IO error in file operation", e);
    }

    protected boolean process(T stream) throws IOException {
        throw new UnsupportedOperationException("Subclasses must implement FileOperation#process.");
    }

    protected T openFile() throws IOException {
        throw new UnsupportedOperationException("Subclasses must implement FileOperation#openFile.");
    }
}
