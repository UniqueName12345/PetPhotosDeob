package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.annotations.UsesQueries;
import com.google.appinventor.components.annotations.androidmanifest.ActionElement;
import com.google.appinventor.components.annotations.androidmanifest.IntentFilterElement;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import kawa.lang.SyntaxForms;

@UsesPermissions({"android.permission.RECORD_AUDIO", "android.permission.INTERNET"})
@DesignerComponent(category = ComponentCategory.MEDIA, description = "Component for using Voice Recognition to convert from speech to text", iconName = "images/speechRecognizer.png", nonVisible = SyntaxForms.DEBUGGING, version = 3)
@SimpleObject
/* loaded from: classes.dex */
public class SpeechRecognizer extends AndroidNonvisibleComponent implements Component, OnClearListener, SpeechListener {
    private final ComponentContainer container;
    private boolean havePermission;
    private String language;
    private Intent recognizerIntent;
    private String result;
    private SpeechRecognizerController speechRecognizerController;
    private boolean useLegacy;

    public SpeechRecognizer(ComponentContainer container) {
        super(container.$form());
        this.havePermission = false;
        this.useLegacy = true;
        this.language = "";
        container.$form().registerForOnClear(this);
        this.container = container;
        this.result = "";
        this.recognizerIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.recognizerIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        this.recognizerIntent.putExtra("android.speech.extra.PARTIAL_RESULTS", true);
        UseLegacy(this.useLegacy);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Language() {
        return this.language;
    }

    @SimpleProperty
    public void Language(String language) {
        this.language = language;
        if (TextUtils.isEmpty(language)) {
            this.recognizerIntent.removeExtra("android.speech.extra.LANGUAGE");
        } else {
            this.recognizerIntent.putExtra("android.speech.extra.LANGUAGE", language);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Result() {
        return this.result;
    }

    @SimpleFunction
    public void GetText() {
        if (!this.havePermission) {
            this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.SpeechRecognizer.1
                @Override // java.lang.Runnable
                public void run() {
                    SpeechRecognizer.this.form.askPermission("android.permission.RECORD_AUDIO", new PermissionResultHandler() { // from class: com.google.appinventor.components.runtime.SpeechRecognizer.1.1
                        @Override // com.google.appinventor.components.runtime.PermissionResultHandler
                        public void HandlePermissionResponse(String permission, boolean granted) {
                            if (granted) {
                                me.havePermission = true;
                                me.GetText();
                                return;
                            }
                            SpeechRecognizer.this.form.dispatchPermissionDeniedEvent(me, "GetText", "android.permission.RECORD_AUDIO");
                        }
                    });
                }
            });
            return;
        }
        BeforeGettingText();
        this.speechRecognizerController.addListener(this);
        this.speechRecognizerController.start();
    }

    @SimpleFunction
    public void Stop() {
        if (this.speechRecognizerController != null) {
            this.speechRecognizerController.stop();
        }
    }

    @SimpleEvent
    public void BeforeGettingText() {
        EventDispatcher.dispatchEvent(this, "BeforeGettingText", new Object[0]);
    }

    @SimpleEvent
    public void AfterGettingText(String result, boolean partial) {
        EventDispatcher.dispatchEvent(this, "AfterGettingText", result, Boolean.valueOf(partial));
    }

    @Override // com.google.appinventor.components.runtime.SpeechListener
    public void onPartialResult(String text) {
        this.result = text;
        AfterGettingText(this.result, true);
    }

    @Override // com.google.appinventor.components.runtime.SpeechListener
    public void onResult(String text) {
        this.result = text;
        AfterGettingText(this.result, false);
    }

    @Override // com.google.appinventor.components.runtime.SpeechListener
    public void onError(int errorNumber) {
        this.form.dispatchErrorOccurredEvent(this, "GetText", errorNumber, new Object[0]);
    }

    @Override // com.google.appinventor.components.runtime.OnClearListener
    public void onClear() {
        Stop();
        this.speechRecognizerController = null;
        this.recognizerIntent = null;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "If true, an app can retain their older behaviour.")
    public boolean UseLegacy() {
        return this.useLegacy;
    }

    @SimpleProperty(description = "If true, a separate dialog is used to recognize speech (the default). If false, speech is recognized in the background and partial results are also provided.")
    @UsesQueries(intents = {@IntentFilterElement(actionElements = {@ActionElement(name = "android.speech.RecognitionService")})})
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void UseLegacy(boolean useLegacy) {
        this.useLegacy = useLegacy;
        Stop();
        if (useLegacy || Build.VERSION.SDK_INT < 8) {
            this.speechRecognizerController = new IntentBasedSpeechRecognizer(this.container, this.recognizerIntent);
        } else {
            this.speechRecognizerController = new ServiceBasedSpeechRecognizer(this.container, this.recognizerIntent);
        }
    }
}
