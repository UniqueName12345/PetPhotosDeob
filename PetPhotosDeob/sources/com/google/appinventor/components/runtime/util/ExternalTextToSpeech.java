package com.google.appinventor.components.runtime.util;

import android.content.Intent;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.ITextToSpeech;
import gnu.expr.Declaration;
import java.util.Locale;

/* loaded from: classes.dex */
public class ExternalTextToSpeech implements ITextToSpeech, ActivityResultListener {
    private static final String TTS_INTENT = "com.google.tts.makeBagel";
    private final ITextToSpeech.TextToSpeechCallback callback;
    private final ComponentContainer container;
    private int requestCode;

    public ExternalTextToSpeech(ComponentContainer container, ITextToSpeech.TextToSpeechCallback callback) {
        this.container = container;
        this.callback = callback;
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void speak(String message, Locale loc) {
        Intent intent = new Intent(TTS_INTENT);
        intent.setFlags(131072);
        intent.setFlags(8388608);
        intent.setFlags(Declaration.MODULE_REFERENCE);
        intent.putExtra("message", message);
        intent.putExtra("language", loc.getISO3Language());
        intent.putExtra("country", loc.getISO3Country());
        if (this.requestCode == 0) {
            this.requestCode = this.container.$form().registerForActivityResult(this);
        }
        this.container.$context().startActivityForResult(intent, this.requestCode);
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void onDestroy() {
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void onStop() {
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void onResume() {
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void setPitch(float pitch) {
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void setSpeechRate(float speechRate) {
    }

    @Override // com.google.appinventor.components.runtime.ActivityResultListener
    public void resultReturned(int requestCode, int resultCode, Intent data) {
        boolean isSuccess = requestCode == this.requestCode && resultCode == -1;
        if (isSuccess) {
            this.callback.onSuccess();
        } else {
            this.callback.onFailure();
        }
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public int isLanguageAvailable(Locale loc) {
        return -1;
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public boolean isInitialized() {
        return true;
    }
}
