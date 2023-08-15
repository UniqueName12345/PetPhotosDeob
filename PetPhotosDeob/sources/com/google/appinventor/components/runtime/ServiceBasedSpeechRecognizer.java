package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ServiceBasedSpeechRecognizer extends SpeechRecognizerController implements RecognitionListener {
    private ComponentContainer container;
    private Intent recognizerIntent;
    private String result;
    private android.speech.SpeechRecognizer speech = null;

    public ServiceBasedSpeechRecognizer(ComponentContainer container, Intent recognizerIntent) {
        this.container = container;
        this.recognizerIntent = recognizerIntent;
    }

    @Override // com.google.appinventor.components.runtime.SpeechRecognizerController
    public void start() {
        this.speech = android.speech.SpeechRecognizer.createSpeechRecognizer(this.container.$context());
        this.speech.setRecognitionListener(this);
        this.speech.startListening(this.recognizerIntent);
    }

    @Override // com.google.appinventor.components.runtime.SpeechRecognizerController
    public void stop() {
        if (this.speech != null) {
            this.speech.stopListening();
        }
    }

    @Override // android.speech.RecognitionListener
    public void onReadyForSpeech(Bundle bundle) {
    }

    @Override // android.speech.RecognitionListener
    public void onBeginningOfSpeech() {
    }

    @Override // android.speech.RecognitionListener
    public void onRmsChanged(float v) {
    }

    @Override // android.speech.RecognitionListener
    public void onBufferReceived(byte[] bytes) {
    }

    @Override // android.speech.RecognitionListener
    public void onEndOfSpeech() {
    }

    @Override // android.speech.RecognitionListener
    public void onError(int i) {
        int errorNumber = getErrorMessage(i);
        this.speechListener.onError(errorNumber);
    }

    @Override // android.speech.RecognitionListener
    public void onResults(Bundle bundle) {
        if (bundle.isEmpty()) {
            this.result = "";
        } else {
            ArrayList<String> results = bundle.getStringArrayList("results_recognition");
            this.result = results.get(0);
        }
        this.speechListener.onResult(this.result);
    }

    @Override // android.speech.RecognitionListener
    public void onPartialResults(Bundle bundle) {
        if (bundle.isEmpty()) {
            this.result = "";
        } else {
            ArrayList<String> results = bundle.getStringArrayList("results_recognition");
            this.result = results.get(0);
        }
        this.speechListener.onPartialResult(this.result);
    }

    @Override // android.speech.RecognitionListener
    public void onEvent(int i, Bundle bundle) {
    }

    private int getErrorMessage(int errorCode) {
        switch (errorCode) {
            case 1:
                return ErrorMessages.ERROR_NETWORK_TIMEOUT;
            case 2:
                return ErrorMessages.ERROR_NETWORK;
            case 3:
                return ErrorMessages.ERROR_AUDIO;
            case 4:
                return ErrorMessages.ERROR_SERVER;
            case 5:
                return ErrorMessages.ERROR_CLIENT;
            case 6:
                return ErrorMessages.ERROR_SPEECH_TIMEOUT;
            case 7:
                return ErrorMessages.ERROR_NO_MATCH;
            case 8:
                return ErrorMessages.ERROR_RECOGNIZER_BUSY;
            case 9:
                return ErrorMessages.ERROR_INSUFFICIENT_PERMISSIONS;
            default:
                return 0;
        }
    }
}
