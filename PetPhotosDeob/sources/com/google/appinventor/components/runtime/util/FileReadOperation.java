package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class FileReadOperation extends FileStreamOperation<InputStream> {
    public FileReadOperation(Form form, Component component, String method, String fileName, FileScope scope, boolean async) {
        super(form, component, method, fileName, scope, FileAccessMode.READ, async);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.util.FileStreamOperation
    public boolean process(InputStream stream) throws IOException {
        return process(IOUtils.readStream(stream));
    }

    public boolean process(byte[] contents) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.util.FileStreamOperation
    public InputStream openFile() throws IOException {
        return FileUtil.openForReading(this.form, this.scopedFile);
    }
}
