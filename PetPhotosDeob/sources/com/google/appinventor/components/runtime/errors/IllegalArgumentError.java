package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
/* loaded from: classes.dex */
public class IllegalArgumentError extends RuntimeError {
    public IllegalArgumentError() {
    }

    public IllegalArgumentError(String msg) {
        super(msg);
    }
}
