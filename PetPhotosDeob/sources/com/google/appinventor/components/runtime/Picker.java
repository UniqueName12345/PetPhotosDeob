package com.google.appinventor.components.runtime;

import android.content.Intent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.runtime.util.AnimationUtil;

@SimpleObject
/* loaded from: classes.dex */
public abstract class Picker extends ButtonBase implements ActivityResultListener {
    protected final ComponentContainer container;
    protected int requestCode;

    protected abstract Intent getIntent();

    public Picker(ComponentContainer container) {
        super(container);
        this.container = container;
    }

    @Override // com.google.appinventor.components.runtime.ButtonBase
    public void click() {
        BeforePicking();
        if (this.requestCode == 0) {
            this.requestCode = this.container.$form().registerForActivityResult(this);
        }
        this.container.$context().startActivityForResult(getIntent(), this.requestCode);
        String openAnim = this.container.$form().OpenScreenAnimation();
        AnimationUtil.ApplyOpenScreenAnimation(this.container.$context(), openAnim);
    }

    @SimpleFunction(description = "Opens the %type%, as though the user clicked on it.")
    public void Open() {
        click();
    }

    @SimpleEvent
    public void BeforePicking() {
        EventDispatcher.dispatchEvent(this, "BeforePicking", new Object[0]);
    }

    @SimpleEvent
    public void AfterPicking() {
        EventDispatcher.dispatchEvent(this, "AfterPicking", new Object[0]);
    }
}
