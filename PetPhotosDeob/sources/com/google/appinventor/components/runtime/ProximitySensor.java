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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.SENSORS, description = "<p>Non-visible component that can measures the proximity of an object in cm relative to the view screen of a device. This sensor is typically used to determine whether a handset is being held up to a persons ear; i.e. lets you determine how far away an object is from a device. Many devices return the absolute distance, in cm, but some return only near and far values. In this case, the sensor usually reports its maximum range value in the far state and a lesser value in the near state.</p>", iconName = "images/proximitysensor.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class ProximitySensor extends AndroidNonvisibleComponent implements OnStopListener, OnResumeListener, SensorComponent, OnPauseListener, SensorEventListener, Deleteable, RealTimeDataSource<String, Float> {
    private final Set<DataSourceChangeListener> dataSourceObservers;
    private float distance;
    private boolean enabled;
    private boolean keepRunningWhenOnPause;
    private final Sensor proximitySensor;
    private final SensorManager sensorManager;

    public ProximitySensor(ComponentContainer container) {
        super(container.$form());
        this.distance = 0.0f;
        this.dataSourceObservers = new HashSet();
        this.form.registerForOnResume(this);
        this.form.registerForOnStop(this);
        this.form.registerForOnPause(this);
        this.enabled = true;
        this.sensorManager = (SensorManager) container.$context().getSystemService("sensor");
        this.proximitySensor = this.sensorManager.getDefaultSensor(8);
        startListening();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Reports whether or not the device has a proximity sensor.")
    public boolean Available() {
        List<Sensor> sensors = this.sensorManager.getSensorList(8);
        return sensors.size() > 0;
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
        if (this.enabled && !this.keepRunningWhenOnPause) {
            stopListening();
        }
    }

    private void startListening() {
        this.sensorManager.registerListener(this, this.proximitySensor, 3);
    }

    private void stopListening() {
        this.sensorManager.unregisterListener(this);
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.enabled) {
            float[] values = (float[]) sensorEvent.values.clone();
            this.distance = values[0];
            ProximityChanged(this.distance);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Reports the Maximum Range of the device's ProximitySensor")
    public float MaximumRange() {
        return this.proximitySensor.getMaximumRange();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Enabled() {
        return this.enabled;
    }

    @SimpleProperty(description = "If enabled, then device will listen for changes in proximity.")
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
    public boolean KeepRunningWhenOnPause() {
        return this.keepRunningWhenOnPause;
    }

    @SimpleProperty(description = "If set to true, it will keep sensing for proximity changes even when the app is not visible")
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void KeepRunningWhenOnPause(boolean enabled) {
        this.keepRunningWhenOnPause = enabled;
    }

    @SimpleEvent(description = "Triggered when distance (in cm) of the object to the device changes.")
    public void ProximityChanged(float distance) {
        this.distance = distance;
        notifyDataObservers("distance", (Object) Float.valueOf(distance));
        EventDispatcher.dispatchEvent(this, "ProximityChanged", Float.valueOf(this.distance));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the distance from the object to the device")
    public float Distance() {
        return this.distance;
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
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
        return key.equals("distance") ? Float.valueOf(this.distance) : Float.valueOf(0.0f);
    }
}
