package com.google.appinventor.components.runtime;

/* loaded from: classes.dex */
public abstract class SpeechRecognizerController {
    SpeechListener speechListener;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addListener(SpeechListener speechListener) {
        this.speechListener = speechListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void start() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void stop() {
    }
}
