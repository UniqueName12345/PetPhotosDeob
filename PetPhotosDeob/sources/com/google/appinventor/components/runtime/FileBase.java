package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.errors.StopBlocksExecution;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileStreamReadOperation;
import com.google.appinventor.components.runtime.util.IOUtils;
import java.io.FileNotFoundException;
import java.io.IOException;

@SimpleObject
/* loaded from: classes.dex */
public abstract class FileBase extends AndroidNonvisibleComponent implements Component {
    protected static final String LOG_TAG = "FileComponent";
    protected FileScope scope;

    protected abstract void afterRead(String str);

    /* JADX INFO: Access modifiers changed from: protected */
    public FileBase(ComponentContainer container) {
        super(container.$form());
        this.scope = FileScope.App;
        DefaultScope(FileScope.App);
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "App", editorType = PropertyTypeConstants.PROPERTY_TYPE_FILESCOPE)
    public void DefaultScope(FileScope scope) {
        this.scope = scope;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @Deprecated
    public void LegacyMode(boolean legacy) {
        this.scope = legacy ? FileScope.Legacy : FileScope.App;
    }

    @SimpleProperty(description = "Allows app to access files from the root of the external storage directory (legacy mode).")
    @Deprecated
    public boolean LegacyMode() {
        return this.scope == FileScope.Legacy;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void readFromFile(String fileName) {
        try {
            new FileStreamReadOperation(this.form, this, "ReadFrom", fileName, this.scope, true) { // from class: com.google.appinventor.components.runtime.FileBase.1
                @Override // com.google.appinventor.components.runtime.util.FileStreamReadOperation
                public boolean process(String contents) {
                    String text = IOUtils.normalizeNewLines(contents);
                    FileBase.this.afterRead(text);
                    return true;
                }

                @Override // com.google.appinventor.components.runtime.util.FileStreamOperation
                public void onError(IOException e) {
                    if (e instanceof FileNotFoundException) {
                        Log.e(FileBase.LOG_TAG, "FileNotFoundException", e);
                        this.form.dispatchErrorOccurredEvent(FileBase.this, "ReadFrom", ErrorMessages.ERROR_CANNOT_FIND_FILE, this.fileName);
                        return;
                    }
                    Log.e(FileBase.LOG_TAG, "IOException", e);
                    this.form.dispatchErrorOccurredEvent(FileBase.this, "ReadFrom", ErrorMessages.ERROR_CANNOT_READ_FILE, this.fileName);
                }
            }.run();
        } catch (StopBlocksExecution e) {
        }
    }
}
