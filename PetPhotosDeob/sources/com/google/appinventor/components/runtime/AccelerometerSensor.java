package com.google.appinventor.components.runtime;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.Sensitivity;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

@DesignerComponent(category = ComponentCategory.SENSORS, description = "Non-visible component that can detect shaking and measure acceleration approximately in three dimensions using SI units (m/s<sup>2</sup>).  The components are: <ul>\n<li> <strong>xAccel</strong>: 0 when the phone is at rest on a flat      surface, positive when the phone is tilted to the right (i.e.,      its left side is raised), and negative when the phone is tilted      to the left (i.e., its right size is raised).</li>\n <li> <strong>yAccel</strong>: 0 when the phone is at rest on a flat      surface, positive when its bottom is raised, and negative when      its top is raised. </li>\n <li> <strong>zAccel</strong>: Equal to -9.8 (earth's gravity in meters per      second per second when the device is at rest parallel to the ground      with the display facing up,      0 when perpendicular to the ground, and +9.8 when facing down.       The value can also be affected by accelerating it with or against      gravity. </li></ul>", iconName = "images/accelerometersensor.png", nonVisible = true, version = 5)
@SimpleObject
/* loaded from: classes.dex */
public class AccelerometerSensor extends AndroidNonvisibleComponent implements OnPauseListener, OnResumeListener, SensorComponent, SensorEventListener, Deleteable, RealTimeDataSource<String, Float> {
    private static final boolean DEBUG = true;
    private static final String LOG_TAG = "AccelerometerSensor";
    private static final int SENSOR_CACHE_SIZE = 10;
    private static final double moderateShakeThreshold = 13.0d;
    private static final double strongShakeThreshold = 20.0d;
    private static final double weakShakeThreshold = 5.0d;
    private final Queue<Float> X_CACHE;
    private final Queue<Float> Y_CACHE;
    private final Queue<Float> Z_CACHE;
    private Sensor accelerometerSensor;
    private int accuracy;
    private final Handler androidUIHandler;
    private final Set<DataSourceChangeListener> dataSourceObservers;
    private volatile int deviceDefaultOrientation;
    private boolean enabled;
    private boolean legacyMode;
    private int minimumInterval;
    private final Resources resources;
    private Sensitivity sensitivity;
    private final SensorManager sensorManager;
    private long timeLastShook;
    private final WindowManager windowManager;
    private float xAccel;
    private float yAccel;
    private float zAccel;

    public AccelerometerSensor(ComponentContainer container) {
        super(container.$form());
        this.X_CACHE = new LinkedList();
        this.Y_CACHE = new LinkedList();
        this.Z_CACHE = new LinkedList();
        this.legacyMode = false;
        this.dataSourceObservers = new HashSet();
        this.form.registerForOnResume(this);
        this.form.registerForOnPause(this);
        this.enabled = true;
        this.resources = container.$context().getResources();
        this.windowManager = (WindowManager) container.$context().getSystemService("window");
        this.sensorManager = (SensorManager) container.$context().getSystemService("sensor");
        this.accelerometerSensor = this.sensorManager.getDefaultSensor(1);
        this.androidUIHandler = new Handler();
        startListening();
        MinimumInterval(400);
        SensitivityAbstract(Sensitivity.Moderate);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The minimum interval, in milliseconds, between phone shakes")
    public int MinimumInterval() {
        return this.minimumInterval;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "400", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void MinimumInterval(int interval) {
        this.minimumInterval = interval;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A number that encodes how sensitive the accelerometer is. The choices are: 1 = weak, 2 = moderate,  3 = strong.")
    @Options(Sensitivity.class)
    public int Sensitivity() {
        return this.sensitivity.toUnderlyingValue().intValue();
    }

    public Sensitivity SensitivityAbstract() {
        return this.sensitivity;
    }

    public void SensitivityAbstract(Sensitivity sensitivity) {
        this.sensitivity = sensitivity;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_SERIF, editorType = PropertyTypeConstants.PROPERTY_TYPE_ACCELEROMETER_SENSITIVITY)
    public void Sensitivity(@Options(Sensitivity.class) int sensitivity) {
        Sensitivity level = Sensitivity.fromUnderlyingValue(Integer.valueOf(sensitivity));
        if (level == null) {
            this.form.dispatchErrorOccurredEvent(this, "Sensitivity", ErrorMessages.ERROR_BAD_VALUE_FOR_ACCELEROMETER_SENSITIVITY, Integer.valueOf(sensitivity));
        } else {
            SensitivityAbstract(level);
        }
    }

    @SimpleEvent
    public void AccelerationChanged(float xAccel, float yAccel, float zAccel) {
        this.xAccel = xAccel;
        this.yAccel = yAccel;
        this.zAccel = zAccel;
        addToSensorCache(this.X_CACHE, xAccel);
        addToSensorCache(this.Y_CACHE, yAccel);
        addToSensorCache(this.Z_CACHE, zAccel);
        notifyDataObservers("X", (Object) Float.valueOf(xAccel));
        notifyDataObservers("Y", (Object) Float.valueOf(yAccel));
        notifyDataObservers("Z", (Object) Float.valueOf(zAccel));
        long currentTime = System.currentTimeMillis();
        if ((isShaking(this.X_CACHE, xAccel) || isShaking(this.Y_CACHE, yAccel) || isShaking(this.Z_CACHE, zAccel)) && (this.timeLastShook == 0 || currentTime >= this.timeLastShook + this.minimumInterval)) {
            this.timeLastShook = currentTime;
            Shaking();
        }
        EventDispatcher.dispatchEvent(this, "AccelerationChanged", Float.valueOf(xAccel), Float.valueOf(yAccel), Float.valueOf(zAccel));
    }

    public int getDeviceDefaultOrientation() {
        if (Build.VERSION.SDK_INT < 8) {
            return 1;
        }
        Configuration config = this.resources.getConfiguration();
        int rotation = this.windowManager.getDefaultDisplay().getRotation();
        Log.d(LOG_TAG, "rotation = " + rotation);
        Log.d(LOG_TAG, "config.orientation = " + config.orientation);
        return (((rotation == 0 || rotation == 2) && config.orientation == 2) || ((rotation == 1 || rotation == 3) && config.orientation == 1)) ? 2 : 1;
    }

    @SimpleEvent
    public void Shaking() {
        EventDispatcher.dispatchEvent(this, "Shaking", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns whether the accelerometer is available on the device.")
    public boolean Available() {
        List<Sensor> sensors = this.sensorManager.getSensorList(1);
        return sensors.size() > 0;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Enabled() {
        return this.enabled;
    }

    private void startListening() {
        this.androidUIHandler.postDelayed(new Runnable() { // from class: com.google.appinventor.components.runtime.AccelerometerSensor.1
            @Override // java.lang.Runnable
            public void run() {
                AccelerometerSensor.this.deviceDefaultOrientation = AccelerometerSensor.this.getDeviceDefaultOrientation();
                Log.d(AccelerometerSensor.LOG_TAG, "deviceDefaultOrientation = " + AccelerometerSensor.this.deviceDefaultOrientation);
                Log.d(AccelerometerSensor.LOG_TAG, "Configuration.ORIENTATION_LANDSCAPE = 2");
                Log.d(AccelerometerSensor.LOG_TAG, "Configuration.ORIENTATION_PORTRAIT = 1");
            }
        }, 32L);
        this.sensorManager.registerListener(this, this.accelerometerSensor, 1);
    }

    private void stopListening() {
        this.sensorManager.unregisterListener(this);
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
    public float XAccel() {
        return this.xAccel;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public float YAccel() {
        return this.yAccel;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public float ZAccel() {
        return this.zAccel;
    }

    private void addToSensorCache(Queue<Float> cache, float value) {
        if (cache.size() >= 10) {
            cache.remove();
        }
        cache.add(Float.valueOf(value));
    }

    private boolean isShaking(Queue<Float> cache, float currentValue) {
        float average = 0.0f;
        for (Float f : cache) {
            float value = f.floatValue();
            average += value;
        }
        float delta = Math.abs((average / cache.size()) - currentValue);
        switch (this.sensitivity) {
            case Weak:
                return ((double) delta) > strongShakeThreshold;
            case Moderate:
                return ((double) delta) > moderateShakeThreshold && ((double) delta) < strongShakeThreshold;
            case Strong:
                return ((double) delta) > weakShakeThreshold && ((double) delta) < moderateShakeThreshold;
            default:
                return false;
        }
    }

    @SimpleProperty(description = "Prior to the release that added this property the AccelerometerSensor component passed through sensor values directly as received from the Android system. However these values do not compensate for tablets that default to Landscape mode, requiring the MIT App Inventor programmer to compensate. However compensating would result in incorrect results in Portrait mode devices such as phones. We now detect Landscape mode tablets and perform the compensation. However if your project is already compensating for the change, you will now get incorrect results. Although our preferred solution is for you to update your project, you can also just set this property to “true” and our compensation code will be deactivated. Note: We recommend that you update your project as we may remove this property in a future release.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void LegacyMode(boolean legacyMode) {
        this.legacyMode = legacyMode;
    }

    public boolean LegacyMode() {
        return this.legacyMode;
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.enabled) {
            float[] values = sensorEvent.values;
            if (this.deviceDefaultOrientation == 2 && !this.legacyMode) {
                this.xAccel = values[1];
                this.yAccel = -values[0];
            } else {
                this.xAccel = values[0];
                this.yAccel = values[1];
            }
            this.zAccel = values[2];
            this.accuracy = sensorEvent.accuracy;
            AccelerationChanged(this.xAccel, this.yAccel, this.zAccel);
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override // com.google.appinventor.components.runtime.OnResumeListener
    public void onResume() {
        if (this.enabled) {
            startListening();
        }
    }

    @Override // com.google.appinventor.components.runtime.OnPauseListener
    public void onPause() {
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
            case 88:
                if (key.equals("X")) {
                    c = 0;
                    break;
                }
                break;
            case 89:
                if (key.equals("Y")) {
                    c = 1;
                    break;
                }
                break;
            case 90:
                if (key.equals("Z")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Float.valueOf(this.xAccel);
            case 1:
                return Float.valueOf(this.yAccel);
            case 2:
                return Float.valueOf(this.zAccel);
            default:
                return Float.valueOf(0.0f);
        }
    }
}
