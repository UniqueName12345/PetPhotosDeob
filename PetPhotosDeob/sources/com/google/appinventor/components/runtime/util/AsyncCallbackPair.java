package com.google.appinventor.components.runtime.util;

/* loaded from: classes.dex */
public interface AsyncCallbackPair<T> {
    void onFailure(String str);

    void onSuccess(T t);
}
