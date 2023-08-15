package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.google.appinventor.components.runtime.util.ITextToSpeech;
import java.util.HashMap;
import java.util.Locale;

/* loaded from: classes.dex */
public class InternalTextToSpeech implements ITextToSpeech {
    private static final String LOG_TAG = "InternalTTS";
    private final Activity activity;
    private final ITextToSpeech.TextToSpeechCallback callback;
    private volatile boolean isTtsInitialized;
    private TextToSpeech tts;
    private Handler mHandler = new Handler();
    private int nextUtteranceId = 1;
    private int ttsRetryDelay = 500;
    private int ttsMaxRetries = 20;

    public InternalTextToSpeech(Activity activity, ITextToSpeech.TextToSpeechCallback callback) {
        this.activity = activity;
        this.callback = callback;
        initializeTts();
    }

    private void initializeTts() {
        if (this.tts == null) {
            Log.d(LOG_TAG, "INTERNAL TTS is reinitializing");
            this.tts = new TextToSpeech(this.activity, new TextToSpeech.OnInitListener() { // from class: com.google.appinventor.components.runtime.util.InternalTextToSpeech.1
                @Override // android.speech.tts.TextToSpeech.OnInitListener
                public void onInit(int status) {
                    if (status == 0) {
                        InternalTextToSpeech.this.isTtsInitialized = true;
                    }
                }
            });
        }
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void speak(String message, Locale loc) {
        Log.d(LOG_TAG, "Internal TTS got speak");
        speak(message, loc, 0);
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public boolean isInitialized() {
        return this.isTtsInitialized;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void speak(final String message, final Locale loc, final int retries) {
        Log.d(LOG_TAG, "InternalTTS speak called, message = " + message);
        if (retries > this.ttsMaxRetries) {
            Log.d(LOG_TAG, "max number of speak retries exceeded: speak will fail");
            this.callback.onFailure();
        }
        if (this.isTtsInitialized) {
            Log.d(LOG_TAG, "TTS initialized after " + retries + " retries.");
            this.tts.setLanguage(loc);
            this.tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() { // from class: com.google.appinventor.components.runtime.util.InternalTextToSpeech.2
                @Override // android.speech.tts.TextToSpeech.OnUtteranceCompletedListener
                public void onUtteranceCompleted(String utteranceId) {
                    InternalTextToSpeech.this.activity.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.util.InternalTextToSpeech.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            InternalTextToSpeech.this.callback.onSuccess();
                        }
                    });
                }
            });
            HashMap<String, String> params = new HashMap<>();
            int i = this.nextUtteranceId;
            this.nextUtteranceId = i + 1;
            params.put("utteranceId", Integer.toString(i));
            TextToSpeech textToSpeech = this.tts;
            TextToSpeech textToSpeech2 = this.tts;
            int result = textToSpeech.speak(message, 0, params);
            if (result == -1) {
                Log.d(LOG_TAG, "speak called and tts.speak result was an error");
                this.callback.onFailure();
                return;
            }
            return;
        }
        Log.d(LOG_TAG, "speak called when TTS not initialized");
        this.mHandler.postDelayed(new Runnable() { // from class: com.google.appinventor.components.runtime.util.InternalTextToSpeech.3
            @Override // java.lang.Runnable
            public void run() {
                Log.d(InternalTextToSpeech.LOG_TAG, "delaying call to speak.  Retries is: " + retries + " Message is: " + message);
                InternalTextToSpeech.this.speak(message, loc, retries + 1);
            }
        }, this.ttsRetryDelay);
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void onStop() {
        Log.d(LOG_TAG, "Internal TTS got onStop");
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void onDestroy() {
        Log.d(LOG_TAG, "Internal TTS got onDestroy");
        if (this.tts != null) {
            this.tts.shutdown();
            this.isTtsInitialized = false;
            this.tts = null;
        }
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void onResume() {
        Log.d(LOG_TAG, "Internal TTS got onResume");
        initializeTts();
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void setPitch(float pitch) {
        this.tts.setPitch(pitch);
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public void setSpeechRate(float speechRate) {
        this.tts.setSpeechRate(speechRate);
    }

    @Override // com.google.appinventor.components.runtime.util.ITextToSpeech
    public int isLanguageAvailable(Locale loc) {
        return this.tts.isLanguageAvailable(loc);
    }
}
