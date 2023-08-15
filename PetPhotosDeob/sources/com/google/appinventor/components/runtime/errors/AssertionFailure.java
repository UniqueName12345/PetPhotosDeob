package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
/* loaded from: classes.dex */
public class AssertionFailure extends RuntimeError {
    public AssertionFailure() {
    }

    public AssertionFailure(String msg) {
        super(msg);
    }
}
