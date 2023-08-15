package com.google.appinventor.components.runtime;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.FroyoUtil;
import com.google.appinventor.components.runtime.util.OrientationSensorUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.util.HashSet;
import java.util.Set;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.SENSORS, description = "<p>Non-visible component providing information about the device's physical orientation in three dimensions: <ul> <li> <strong>Roll</strong>: 0 degrees when the device is level, increases to      90 degrees as the device is tilted up on its left side, and      decreases to -90 degrees when the device is tilted up on its right side.      </li> <li> <strong>Pitch</strong>: 0 degrees when the device is level, up to      90 degrees as the device is tilted so its top is pointing down,      up to 180 degrees as it gets turned over.  Similarly, as the device      is tilted so its bottom points down, pitch decreases to -90      degrees, then further decreases to -180 degrees as it gets turned all the way      over.</li> <li> <strong>Azimuth</strong>: 0 degrees when the top of the device is      pointing north, 90 degrees when it is pointing east, 180 degrees      when it is pointing south, 270 degrees when it is pointing west,      etc.</li></ul>     These measurements assume that the device itself is not moving.</p>", iconName = "images/orientationsensor.png", nonVisible = SyntaxForms.DEBUGGING, version = 2)
@SimpleObject
/* loaded from: classes.dex */
public class OrientationSensor extends AndroidNonvisibleComponent implements SensorEventListener, Deleteable, OnPauseListener, OnResumeListener, RealTimeDataSource<String, Float> {
    private static final int AZIMUTH = 0;
    private static final int DIMENSIONS = 3;
    private static final String LOG_TAG = "OrientationSensor";
    private static final int PITCH = 1;
    private static final int ROLL = 2;
    private final Sensor accelerometerSensor;
    private final float[] accels;
    private boolean accelsFilled;
    private int accuracy;
    private float azimuth;
    private Set<DataSourceChangeListener> dataSourceObservers;
    private boolean enabled;
    private final float[] inclinationMatrix;
    private boolean listening;
    private final Sensor magneticFieldSensor;
    private final float[] mags;
    private boolean magsFilled;
    private float pitch;
    private float roll;
    private final float[] rotationMatrix;
    private final SensorManager sensorManager;
    private final float[] values;

    public OrientationSensor(ComponentContainer container) {
        super(container.$form());
        this.accels = new float[3];
        this.mags = new float[3];
        this.rotationMatrix = new float[9];
        this.inclinationMatrix = new float[9];
        this.values = new float[3];
        this.dataSourceObservers = new HashSet();
        this.sensorManager = (SensorManager) container.$context().getSystemService("sensor");
        this.accelerometerSensor = this.sensorManager.getDefaultSensor(1);
        this.magneticFieldSensor = this.sensorManager.getDefaultSensor(2);
        this.form.registerForOnResume(this);
        this.form.registerForOnPause(this);
        Enabled(true);
    }

    private void startListening() {
        if (!this.listening) {
            this.sensorManager.registerListener(this, this.accelerometerSensor, 3);
            this.sensorManager.registerListener(this, this.magneticFieldSensor, 3);
            this.listening = true;
        }
    }

    private void stopListening() {
        if (this.listening) {
            this.sensorManager.unregisterListener(this);
            this.listening = false;
            this.accelsFilled = false;
            this.magsFilled = false;
        }
    }

    @SimpleEvent(description = "Called when the orientation has changed.")
    public void OrientationChanged(float azimuth, float pitch, float roll) {
        notifyDataObservers("azimuth", (Object) Float.valueOf(azimuth));
        notifyDataObservers("pitch", (Object) Float.valueOf(pitch));
        notifyDataObservers("roll", (Object) Float.valueOf(roll));
        EventDispatcher.dispatchEvent(this, "OrientationChanged", Float.valueOf(azimuth), Float.valueOf(pitch), Float.valueOf(roll));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Available() {
        return this.sensorManager.getSensorList(1).size() > 0 && this.sensorManager.getSensorList(2).size() > 0;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Enabled() {
        return this.enabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Enabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) {
                startListening();
            } else {
                stopListening();
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public float Pitch() {
        return this.pitch;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public float Roll() {
        return this.roll;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public float Azimuth() {
        return this.azimuth;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public float Angle() {
        return computeAngle(this.pitch, this.roll);
    }

    static float computeAngle(float pitch, float roll) {
        return (float) Math.toDegrees(Math.atan2(Math.toRadians(pitch), -Math.toRadians(roll)));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public float Magnitude() {
        double npitch = Math.toRadians(Math.min(90.0f, Math.abs(this.pitch)));
        double nroll = Math.toRadians(Math.min(90.0f, Math.abs(this.roll)));
        return (float) (1.0d - (Math.cos(npitch) * Math.cos(nroll)));
    }

    private int getScreenRotation() {
        Display display = ((WindowManager) this.form.getSystemService("window")).getDefaultDisplay();
        return SdkLevel.getLevel() >= 8 ? FroyoUtil.getRotation(display) : display.getOrientation();
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.enabled) {
            int eventType = sensorEvent.sensor.getType();
            switch (eventType) {
                case 1:
                    System.arraycopy(sensorEvent.values, 0, this.accels, 0, 3);
                    this.accelsFilled = true;
                    this.accuracy = sensorEvent.accuracy;
                    break;
                case 2:
                    System.arraycopy(sensorEvent.values, 0, this.mags, 0, 3);
                    this.magsFilled = true;
                    break;
                default:
                    Log.e(LOG_TAG, "Unexpected sensor type: " + eventType);
                    return;
            }
            if (this.accelsFilled && this.magsFilled) {
                SensorManager.getRotationMatrix(this.rotationMatrix, this.inclinationMatrix, this.accels, this.mags);
                SensorManager.getOrientation(this.rotationMatrix, this.values);
                this.azimuth = OrientationSensorUtil.normalizeAzimuth((float) Math.toDegrees(this.values[0]));
                this.pitch = OrientationSensorUtil.normalizePitch((float) Math.toDegrees(this.values[1]));
                this.roll = OrientationSensorUtil.normalizeRoll((float) (-Math.toDegrees(this.values[2])));
                int rotation = getScreenRotation();
                switch (rotation) {
                    case 0:
                        break;
                    case 1:
                        float temp = -this.pitch;
                        this.pitch = -this.roll;
                        this.roll = temp;
                        break;
                    case 2:
                        this.roll = -this.roll;
                        break;
                    case 3:
                        float temp2 = this.pitch;
                        this.pitch = this.roll;
                        this.roll = temp2;
                        break;
                    default:
                        Log.e(LOG_TAG, "Illegal value for getScreenRotation(): " + rotation);
                        break;
                }
                OrientationChanged(this.azimuth, this.pitch, this.roll);
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override // com.google.appinventor.components.runtime.Deleteable
    public void onDelete() {
        stopListening();
    }

    @Override // com.google.appinventor.components.runtime.OnPauseListener
    public void onPause() {
        stopListening();
    }

    @Override // com.google.appinventor.components.runtime.OnResumeListener
    public void onResume() {
        if (this.enabled) {
            startListening();
        }
    }

    @Override // com.google.appinventor.components.runtime.ObservableDataSource
    public void addDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.add(dataComponent);
    }

    @Override // com.google.appinventor.components.runtime.ObservableDataSource
    public void removeDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.remove(dataComponent);
    }

    @Override // com.google.appinventor.components.runtime.ObservableDataSource
    public void notifyDataObservers(String key, Object value) {
        for (DataSourceChangeListener dataComponent : this.dataSourceObservers) {
            dataComponent.onReceiveValue(this, key, value);
        }
    }

    @Override // com.google.appinventor.components.runtime.DataSource
    public Float getDataValue(String key) {
        char c = 65535;
        switch (key.hashCode()) {
            case -513366676:
                if (key.equals("azimuth")) {
                    c = 0;
                    break;
                }
                break;
            case 3506301:
                if (key.equals("roll")) {
                    c = 2;
                    break;
                }
                break;
            case 106677056:
                if (key.equals("pitch")) {
                    c = 1;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Float.valueOf(this.azimuth);
            case 1:
                return Float.valueOf(this.pitch);
            case 2:
                return Float.valueOf(this.roll);
            default:
                return Float.valueOf(0.0f);
        }
    }
}
