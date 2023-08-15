package com.google.appinventor.components.runtime;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;

@SimpleObject
/* loaded from: classes.dex */
public abstract class SingleValueSensor extends AndroidNonvisibleComponent implements OnPauseListener, OnResumeListener, SensorComponent, SensorEventListener, Deleteable {
    private static final int DEFAULT_REFRESH_TIME = 1000;
    protected boolean enabled;
    protected int refreshTime;
    private Sensor sensor;
    protected final SensorManager sensorManager;
    protected int sensorType;
    protected float value;

    protected abstract void onValueChanged(float f);

    public SingleValueSensor(ComponentContainer container, int sensorType) {
        super(container.$form());
        this.sensorType = sensorType;
        this.form.registerForOnResume(this);
        this.form.registerForOnPause(this);
        this.refreshTime = 1000;
        this.enabled = true;
        this.sensorManager = (SensorManager) container.$context().getSystemService("sensor");
        this.sensor = this.sensorManager.getDefaultSensor(sensorType);
        startListening();
    }

    protected void startListening() {
        if (Build.VERSION.SDK_INT >= 9) {
            int timeInMicroseconds = this.refreshTime * 1000;
            this.sensorManager.registerListener(this, this.sensor, timeInMicroseconds);
            return;
        }
        this.sensorManager.registerListener(this, this.sensor, 2);
    }

    protected void stopListening() {
        this.sensorManager.unregisterListener(this);
    }

    @SimpleProperty(description = "Specifies whether or not the device has the hardware to support the %type% component.")
    public boolean Available() {
        return isAvailable();
    }

    @SimpleProperty(description = "If enabled, then device will listen for changes.")
    public boolean Enabled() {
        return this.enabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Enabled(boolean enabled) {
        setEnabled(enabled);
    }

    @SimpleProperty(description = "The requested minimum time in milliseconds between changes in readings being reported. Android is not guaranteed to honor the request. Setting this property has no effect on pre-Gingerbread devices.")
    public int RefreshTime() {
        return this.refreshTime;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "1000", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void RefreshTime(int time) {
        this.refreshTime = time;
        if (this.enabled) {
            stopListening();
            startListening();
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.enabled && sensorEvent.sensor.getType() == this.sensorType) {
            float[] values = sensorEvent.values;
            this.value = values[0];
            onValueChanged(this.value);
        }
    }

    protected boolean isAvailable() {
        return this.sensorManager.getSensorList(this.sensorType).size() > 0;
    }

    protected void setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) {
                startListening();
            } else {
                stopListening();
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override // com.google.appinventor.components.runtime.OnPauseListener
    public void onPause() {
        if (this.enabled) {
            stopListening();
        }
    }

    @Override // com.google.appinventor.components.runtime.OnResumeListener
    public void onResume() {
        if (this.enabled) {
            startListening();
        }
    }

    @Override // com.google.appinventor.components.runtime.Deleteable
    public void onDelete() {
        if (this.enabled) {
            stopListening();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getValue() {
        return this.value;
    }
}
