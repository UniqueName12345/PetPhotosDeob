package com.google.appinventor.components.runtime;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.SENSORS, description = "<p>Non-visible component that measures the ambient geomagnetic field for all three physical axes (x, y, z) in Tesla https://en.wikipedia.org/wiki/Tesla_(unit).</p>", iconName = "images/magneticSensor.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class MagneticFieldSensor extends AndroidNonvisibleComponent implements SensorEventListener, Deleteable, OnPauseListener, OnResumeListener, OnStopListener, SensorComponent {
    private double absoluteStrength;
    private boolean enabled;
    private boolean listening;
    private Sensor magneticSensor;
    private final SensorManager sensorManager;
    private float xStrength;
    private float yStrength;
    private float zStrength;

    public MagneticFieldSensor(ComponentContainer container) {
        super(container.$form());
        this.enabled = true;
        this.form.registerForOnResume(this);
        this.form.registerForOnStop(this);
        this.form.registerForOnPause(this);
        this.sensorManager = (SensorManager) container.$context().getSystemService("sensor");
        this.magneticSensor = this.sensorManager.getDefaultSensor(2);
        startListening();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Indicates that there is a magnetic field sensor in the device and it is available.")
    public boolean Available() {
        return this.sensorManager.getSensorList(2).size() > 0;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Indicates the maximum range the magnetic sensor can reach.")
    public float MaximumRange() {
        return this.magneticSensor.getMaximumRange();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Indicates whether or not the magnetic field sensor is enabled and working.")
    public boolean Enabled() {
        return this.enabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Enabled(boolean localEnabled) {
        if (this.enabled != localEnabled) {
            this.enabled = localEnabled;
        }
        if (this.enabled) {
            startListening();
        } else {
            stopListening();
        }
    }

    @SimpleEvent(description = "Triggers when magnetic field has changed, setting the new values in parameters.")
    public void MagneticChanged(float xStrength, float yStrength, float zStrength, double absoluteStrength) {
        EventDispatcher.dispatchEvent(this, "MagneticChanged", Float.valueOf(xStrength), Float.valueOf(yStrength), Float.valueOf(zStrength), Double.valueOf(absoluteStrength));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Indicates the absolute strength of the field.")
    public double AbsoluteStrength() {
        return this.absoluteStrength;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Indicates the field's strength in the X-axis.")
    public float XStrength() {
        return this.xStrength;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Indicates the field's strength in the Y-axis.")
    public float YStrength() {
        return this.yStrength;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Indicates the field's strength in the Z-axis.")
    public float ZStrength() {
        return this.zStrength;
    }

    private Sensor getMagneticSensor() {
        Sensor sensor = this.sensorManager.getDefaultSensor(2);
        return sensor != null ? sensor : this.sensorManager.getDefaultSensor(2);
    }

    @Override // com.google.appinventor.components.runtime.OnResumeListener
    public void onResume() {
        if (this.enabled) {
            startListening();
        }
    }

    @Override // com.google.appinventor.components.runtime.OnStopListener
    public void onStop() {
        if (this.enabled) {
            stopListening();
        }
    }

    @Override // com.google.appinventor.components.runtime.Deleteable
    public void onDelete() {
        if (this.enabled) {
            stopListening();
        }
    }

    @Override // com.google.appinventor.components.runtime.OnPauseListener
    public void onPause() {
        stopListening();
    }

    private void startListening() {
        if (!this.listening && this.sensorManager != null && this.magneticSensor != null) {
            this.sensorManager.registerListener(this, this.magneticSensor, 3);
            this.listening = true;
        }
    }

    private void stopListening() {
        if (this.listening && this.sensorManager != null) {
            this.sensorManager.unregisterListener(this);
            this.listening = false;
            this.xStrength = 0.0f;
            this.yStrength = 0.0f;
            this.zStrength = 0.0f;
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.enabled && sensorEvent.sensor.getType() == 2) {
            float[] fArr = (float[]) sensorEvent.values.clone();
            this.xStrength = sensorEvent.values[0];
            this.yStrength = sensorEvent.values[1];
            this.zStrength = sensorEvent.values[2];
            this.absoluteStrength = Math.sqrt((this.xStrength * this.xStrength) + (this.yStrength * this.yStrength) + (this.zStrength * this.zStrength));
            MagneticChanged(this.xStrength, this.yStrength, this.zStrength, this.absoluteStrength);
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
