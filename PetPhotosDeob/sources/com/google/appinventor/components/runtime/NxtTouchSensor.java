package com.google.appinventor.components.runtime;

import android.os.Handler;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.NxtSensorMode;
import com.google.appinventor.components.common.NxtSensorType;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.LegoMindstormsNxtSensor;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a touch sensor on a LEGO MINDSTORMS NXT robot.", iconName = "images/legoMindstormsNxt.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class NxtTouchSensor extends LegoMindstormsNxtSensor implements Deleteable {
    private static final String DEFAULT_SENSOR_PORT = "1";
    private Handler handler;
    private boolean pressedEventEnabled;
    private State previousState;
    private boolean releasedEventEnabled;
    private final Runnable sensorReader;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum State {
        UNKNOWN,
        PRESSED,
        RELEASED
    }

    public NxtTouchSensor(ComponentContainer container) {
        super(container, "NxtTouchSensor");
        this.handler = new Handler();
        this.previousState = State.UNKNOWN;
        this.sensorReader = new Runnable() { // from class: com.google.appinventor.components.runtime.NxtTouchSensor.1
            @Override // java.lang.Runnable
            public void run() {
                if (NxtTouchSensor.this.bluetooth != null && NxtTouchSensor.this.bluetooth.IsConnected()) {
                    LegoMindstormsNxtSensor.SensorValue<Boolean> sensorValue = NxtTouchSensor.this.getPressedValue("");
                    if (sensorValue.valid) {
                        State currentState = ((Boolean) sensorValue.value).booleanValue() ? State.PRESSED : State.RELEASED;
                        if (currentState != NxtTouchSensor.this.previousState) {
                            if (currentState == State.PRESSED && NxtTouchSensor.this.pressedEventEnabled) {
                                NxtTouchSensor.this.Pressed();
                            }
                            if (currentState == State.RELEASED && NxtTouchSensor.this.releasedEventEnabled) {
                                NxtTouchSensor.this.Released();
                            }
                        }
                        NxtTouchSensor.this.previousState = currentState;
                    }
                }
                if (NxtTouchSensor.this.isHandlerNeeded()) {
                    NxtTouchSensor.this.handler.post(NxtTouchSensor.this.sensorReader);
                }
            }
        };
        SensorPort("1");
        PressedEventEnabled(false);
        ReleasedEventEnabled(false);
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtSensor
    protected void initializeSensor(String functionName) {
        setInputMode(functionName, this.port, NxtSensorType.Touch, NxtSensorMode.Boolean);
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtSensor
    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "1", editorType = PropertyTypeConstants.PROPERTY_TYPE_LEGO_NXT_SENSOR_PORT)
    public void SensorPort(String sensorPortLetter) {
        setSensorPort(sensorPortLetter);
    }

    @SimpleFunction(description = "Returns true if the touch sensor is pressed.")
    public boolean IsPressed() {
        if (checkBluetooth("IsPressed")) {
            LegoMindstormsNxtSensor.SensorValue<Boolean> sensorValue = getPressedValue("IsPressed");
            if (sensorValue.valid) {
                return sensorValue.value.booleanValue();
            }
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LegoMindstormsNxtSensor.SensorValue<Boolean> getPressedValue(String functionName) {
        byte[] returnPackage = getInputValues(functionName, this.port);
        if (returnPackage != null) {
            boolean valid = getBooleanValueFromBytes(returnPackage, 4);
            if (valid) {
                int scaledValue = getSWORDValueFromBytes(returnPackage, 12);
                return new LegoMindstormsNxtSensor.SensorValue<>(true, Boolean.valueOf(scaledValue != 0));
            }
        }
        return new LegoMindstormsNxtSensor.SensorValue<>(false, null);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the Pressed event should fire when the touch sensor is pressed.")
    public boolean PressedEventEnabled() {
        return this.pressedEventEnabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void PressedEventEnabled(boolean enabled) {
        boolean handlerWasNeeded = isHandlerNeeded();
        this.pressedEventEnabled = enabled;
        boolean handlerIsNeeded = isHandlerNeeded();
        if (handlerWasNeeded && !handlerIsNeeded) {
            this.handler.removeCallbacks(this.sensorReader);
        }
        if (!handlerWasNeeded && handlerIsNeeded) {
            this.previousState = State.UNKNOWN;
            this.handler.post(this.sensorReader);
        }
    }

    @SimpleEvent(description = "Touch sensor has been pressed.")
    public void Pressed() {
        EventDispatcher.dispatchEvent(this, "Pressed", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the Released event should fire when the touch sensor is released.")
    public boolean ReleasedEventEnabled() {
        return this.releasedEventEnabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void ReleasedEventEnabled(boolean enabled) {
        boolean handlerWasNeeded = isHandlerNeeded();
        this.releasedEventEnabled = enabled;
        boolean handlerIsNeeded = isHandlerNeeded();
        if (handlerWasNeeded && !handlerIsNeeded) {
            this.handler.removeCallbacks(this.sensorReader);
        }
        if (!handlerWasNeeded && handlerIsNeeded) {
            this.previousState = State.UNKNOWN;
            this.handler.post(this.sensorReader);
        }
    }

    @SimpleEvent(description = "Touch sensor has been released.")
    public void Released() {
        EventDispatcher.dispatchEvent(this, "Released", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHandlerNeeded() {
        return this.pressedEventEnabled || this.releasedEventEnabled;
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtBase, com.google.appinventor.components.runtime.Deleteable
    public void onDelete() {
        this.handler.removeCallbacks(this.sensorReader);
        super.onDelete();
    }
}
