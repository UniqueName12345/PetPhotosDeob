package com.google.appinventor.components.runtime.util;

import android.util.Log;
import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.PermissionResultHandler;
import com.google.appinventor.components.runtime.errors.StopBlocksExecution;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class FileOperation implements Runnable, PermissionResultHandler {
    private static final String LOG_TAG = FileOperation.class.getSimpleName();
    protected final boolean async;
    protected final Component component;
    protected final Form form;
    protected final String method;
    protected volatile boolean askedForPermission = false;
    protected volatile boolean hasPermission = false;

    /* loaded from: classes.dex */
    public interface FileInvocation {
        void call(ScopedFile[] scopedFileArr) throws IOException;
    }

    public abstract List<String> getPermissions();

    protected abstract boolean needsExternalStorage();

    protected abstract boolean needsPermission();

    protected abstract void performOperation();

    /* JADX INFO: Access modifiers changed from: package-private */
    public FileOperation(Form form, Component component, String method, boolean async) {
        this.form = form;
        this.component = component;
        this.method = method;
        this.async = async;
    }

    @Override // java.lang.Runnable
    public final void run() {
        List<String> neededPermissions = getNeededPermissions();
        if (AsynchUtil.isUiThread()) {
            if (needsExternalStorage()) {
                FileUtil.checkExternalStorageWriteable();
            }
            if (!neededPermissions.isEmpty()) {
                if (!this.hasPermission) {
                    if (this.askedForPermission) {
                        this.form.dispatchPermissionDeniedEvent(this.component, this.method, neededPermissions.get(0));
                    } else {
                        this.askedForPermission = true;
                        this.form.askPermission(new BulkPermissionRequest(this.component, this.method, (String[]) neededPermissions.toArray(new String[0])) { // from class: com.google.appinventor.components.runtime.util.FileOperation.1
                            @Override // com.google.appinventor.components.runtime.util.BulkPermissionRequest
                            public void onGranted() {
                                FileOperation.this.hasPermission = true;
                                FileOperation.this.run();
                            }
                        });
                    }
                    throw new StopBlocksExecution();
                } else if (this.async) {
                    AsynchUtil.runAsynchronously(this);
                    return;
                } else {
                    performOperation();
                    return;
                }
            }
            this.hasPermission = true;
            if (this.async) {
                AsynchUtil.runAsynchronously(this);
            } else {
                performOperation();
            }
        } else if (!neededPermissions.isEmpty()) {
            this.hasPermission = false;
            this.askedForPermission = false;
            this.form.runOnUiThread(this);
        } else {
            performOperation();
        }
    }

    @Override // com.google.appinventor.components.runtime.PermissionResultHandler
    public void HandlePermissionResponse(String permission, boolean granted) {
        this.askedForPermission = true;
        this.hasPermission = granted;
        run();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void reportError(final int errorNumber, final Object... messageArgs) {
        this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.util.FileOperation.2
            @Override // java.lang.Runnable
            public void run() {
                FileOperation.this.form.dispatchErrorOccurredEvent(FileOperation.this.component, FileOperation.this.method, errorNumber, messageArgs);
            }
        });
    }

    private List<String> getNeededPermissions() {
        if (this.hasPermission) {
            return Collections.emptyList();
        }
        List<String> permissions = getPermissions();
        Set<String> result = new HashSet<>();
        for (String permission : permissions) {
            if (this.form.isDeniedPermission(permission)) {
                result.add(permission);
            }
        }
        return new ArrayList(result);
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private Component component;
        private Form form;
        private String method;
        private final LinkedHashMap<ScopedFile, FileAccessMode> scopedFiles = new LinkedHashMap<>();
        private final Set<String> neededPermissions = new HashSet();
        private final List<FileInvocation> commands = new ArrayList();
        private boolean async = true;
        private boolean needsExternalStorage = false;
        private boolean askPermission = true;

        public Builder() {
        }

        public Builder(Form form, Component component, String method) {
            this.form = form;
            this.component = component;
            this.method = method;
        }

        public Builder setForm(Form form) {
            this.form = form;
            return this;
        }

        public Builder setComponent(Component component) {
            this.component = component;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder addFile(FileScope scope, String fileName, FileAccessMode accessMode) {
            ScopedFile file = new ScopedFile(scope, fileName);
            if (file.getScope() == FileScope.Asset && accessMode != FileAccessMode.READ) {
                this.form.dispatchErrorOccurredEvent(this.component, this.method, ErrorMessages.ERROR_CANNOT_WRITE_ASSET, file.getFileName());
                throw new StopBlocksExecution();
            }
            this.scopedFiles.put(file, accessMode);
            String resolvedFileName = FileUtil.resolveFileName(this.form, fileName, scope);
            Log.d(FileOperation.LOG_TAG, this.method + " resolved " + resolvedFileName);
            this.needsExternalStorage |= FileUtil.needsPermission(this.form, resolvedFileName);
            String permission = FileUtil.getNeededPermission(this.form, resolvedFileName, accessMode);
            if (permission != null) {
                this.neededPermissions.add(permission);
            }
            return this;
        }

        public Builder addCommand(FileInvocation command) {
            this.commands.add(command);
            return this;
        }

        public Builder setAsynchronous(boolean async) {
            this.async = async;
            return this;
        }

        public Builder setAskPermission(boolean askPermission) {
            this.askPermission = askPermission;
            return this;
        }

        public FileOperation build() {
            return new FileOperation(this.form, this.component, this.method, this.async) { // from class: com.google.appinventor.components.runtime.util.FileOperation.Builder.1
                @Override // com.google.appinventor.components.runtime.util.FileOperation
                public List<String> getPermissions() {
                    return new ArrayList(Builder.this.neededPermissions);
                }

                @Override // com.google.appinventor.components.runtime.util.FileOperation
                protected void performOperation() {
                    if (Builder.this.askPermission && Builder.this.neededPermissions.size() > 0) {
                        Iterator<String> i = Builder.this.neededPermissions.iterator();
                        while (i.hasNext()) {
                            String perm = i.next();
                            if (!this.form.isDeniedPermission(perm)) {
                                i.remove();
                            }
                        }
                        if (needsPermission()) {
                            Log.d(FileOperation.LOG_TAG, this.method + " need permissions: " + Builder.this.neededPermissions);
                            this.form.askPermission(new BulkPermissionRequest(this.component, this.method, (String[]) Builder.this.neededPermissions.toArray(new String[0])) { // from class: com.google.appinventor.components.runtime.util.FileOperation.Builder.1.1
                                @Override // com.google.appinventor.components.runtime.util.BulkPermissionRequest
                                public void onGranted() {
                                }
                            });
                            throw new StopBlocksExecution();
                        }
                    }
                    ScopedFile[] filesArray = (ScopedFile[]) Builder.this.scopedFiles.keySet().toArray(new ScopedFile[0]);
                    for (FileInvocation command : Builder.this.commands) {
                        try {
                            command.call(filesArray);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override // com.google.appinventor.components.runtime.util.FileOperation
                protected boolean needsPermission() {
                    return !Builder.this.neededPermissions.isEmpty();
                }

                @Override // com.google.appinventor.components.runtime.util.FileOperation
                protected boolean needsExternalStorage() {
                    return Builder.this.needsExternalStorage;
                }
            };
        }
    }
}
