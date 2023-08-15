package com.google.appinventor.components.runtime.errors;

/* loaded from: classes.dex */
public class PermissionException extends RuntimeException {
    private final String permissionNeeded;

    public PermissionException(String permissionNeeded) {
        this.permissionNeeded = permissionNeeded;
    }

    public String getPermissionNeeded() {
        return this.permissionNeeded;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return "Unable to complete the operation because the user denied permission: " + this.permissionNeeded;
    }
}
