package com.google.appinventor.components.runtime.util;

import android.annotation.SuppressLint;

@SuppressLint({"InlinedApi"})
/* loaded from: classes.dex */
public enum FileAccessMode {
    READ("android.permission.READ_EXTERNAL_STORAGE"),
    WRITE("android.permission.WRITE_EXTERNAL_STORAGE"),
    APPEND("android.permission.WRITE_EXTERNAL_STORAGE");
    
    private final String permission;

    FileAccessMode(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }
}
