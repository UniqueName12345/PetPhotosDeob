package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.NxtSensorPort;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.ErrorMessages;

@SimpleObject
/* loaded from: classes.dex */
public abstract class LegoMindstormsNxtSensor extends LegoMindstormsNxtBase {
    protected NxtSensorPort port;

    public abstract void SensorPort(String str);

    protected abstract void initializeSensor(String str);

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class SensorValue<T> {
        final boolean valid;
        final T value;

        /* JADX INFO: Access modifiers changed from: package-private */
        public SensorValue(boolean valid, T value) {
            this.valid = valid;
            this.value = value;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public LegoMindstormsNxtSensor(ComponentContainer container, String logTag) {
        super(container, logTag);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The sensor port that the sensor is connected to.", userVisible = JSONzip.probe)
    public String SensorPort() {
        return this.port.toUnderlyingValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setSensorPort(String sensorPortLetter) {
        NxtSensorPort port = NxtSensorPort.fromUnderlyingValue(sensorPortLetter);
        if (port == null) {
            this.form.dispatchErrorOccurredEvent(this, "SensorPort", ErrorMessages.ERROR_NXT_INVALID_SENSOR_PORT, sensorPortLetter);
            return;
        }
        this.port = port;
        if (this.bluetooth != null && this.bluetooth.IsConnected()) {
            initializeSensor("SensorPort");
        }
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtBase, com.google.appinventor.components.runtime.BluetoothConnectionListener
    public void afterConnect(BluetoothConnectionBase bluetoothConnection) {
        initializeSensor("Connect");
    }
}
