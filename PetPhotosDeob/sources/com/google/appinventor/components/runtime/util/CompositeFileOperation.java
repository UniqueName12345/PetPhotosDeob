package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class CompositeFileOperation extends FileOperation implements Iterable<FileOperand> {
    private final List<FileOperand> files;
    private boolean needsExternalStorage;
    private final Set<String> permissions;

    @Override // com.google.appinventor.components.runtime.util.FileOperation
    protected abstract void performOperation();

    /* loaded from: classes.dex */
    public static class FileOperand {
        private final String file;
        private final FileAccessMode mode;

        FileOperand(String file, FileAccessMode mode) {
            this.file = file;
            this.mode = mode;
        }

        public String getFile() {
            return this.file;
        }

        public FileAccessMode getMode() {
            return this.mode;
        }
    }

    public CompositeFileOperation(Form form, Component component, String method, boolean async) {
        super(form, component, method, async);
        this.files = new ArrayList();
        this.permissions = new HashSet();
        this.needsExternalStorage = false;
    }

    public void addFile(FileScope scope, String fileName, FileAccessMode mode) {
        FileOperand operand = new FileOperand(FileUtil.resolveFileName(this.form, fileName, scope), mode);
        this.files.add(operand);
        this.permissions.add(FileUtil.getNeededPermission(this.form, fileName, mode));
        this.needsExternalStorage |= FileUtil.isExternalStorageUri(this.form, operand.file);
    }

    @Override // com.google.appinventor.components.runtime.util.FileOperation
    public List<String> getPermissions() {
        return new ArrayList(this.permissions);
    }

    @Override // com.google.appinventor.components.runtime.util.FileOperation
    protected boolean needsExternalStorage() {
        return this.needsExternalStorage;
    }

    @Override // java.lang.Iterable
    public Iterator<FileOperand> iterator() {
        return this.files.iterator();
    }
}
