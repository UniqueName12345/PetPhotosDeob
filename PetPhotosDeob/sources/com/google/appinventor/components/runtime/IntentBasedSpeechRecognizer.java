package com.google.appinventor.components.runtime;

import android.content.Intent;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class IntentBasedSpeechRecognizer extends SpeechRecognizerController implements ActivityResultListener {
    private ComponentContainer container;
    private Intent recognizerIntent;
    private int requestCode;
    private String result;

    public IntentBasedSpeechRecognizer(ComponentContainer container, Intent recognizerIntent) {
        this.container = container;
        this.recognizerIntent = recognizerIntent;
    }

    @Override // com.google.appinventor.components.runtime.SpeechRecognizerController
    public void start() {
        if (this.requestCode == 0) {
            this.requestCode = this.container.$form().registerForActivityResult(this);
        }
        this.container.$context().startActivityForResult(this.recognizerIntent, this.requestCode);
    }

    @Override // com.google.appinventor.components.runtime.SpeechRecognizerController
    public void stop() {
    }

    @Override // com.google.appinventor.components.runtime.ActivityResultListener
    public void resultReturned(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == -1) {
            if (data.hasExtra("android.speech.extra.RESULTS")) {
                ArrayList<String> results = data.getExtras().getStringArrayList("android.speech.extra.RESULTS");
                this.result = results.get(0);
            } else {
                this.result = "";
            }
            this.speechListener.onResult(this.result);
        }
    }
}
