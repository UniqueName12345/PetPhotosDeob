package com.google.appinventor.components.runtime;

/* loaded from: classes.dex */
public interface SpeechListener {
    void onError(int i);

    void onPartialResult(String str);

    void onResult(String str);
}
