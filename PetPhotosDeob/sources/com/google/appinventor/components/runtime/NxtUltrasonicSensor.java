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
import com.google.appinventor.components.runtime.util.Ev3Constants;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to an ultrasonic sensor on a LEGO MINDSTORMS NXT robot.", iconName = "images/legoMindstormsNxt.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class NxtUltrasonicSensor extends LegoMindstormsNxtSensor implements Deleteable {
    private static final int DEFAULT_BOTTOM_OF_RANGE = 30;
    private static final String DEFAULT_SENSOR_PORT = "4";
    private static final int DEFAULT_TOP_OF_RANGE = 90;
    private boolean aboveRangeEventEnabled;
    private boolean belowRangeEventEnabled;
    private int bottomOfRange;
    private Handler handler;
    private State previousState;
    private final Runnable sensorReader;
    private int topOfRange;
    private boolean withinRangeEventEnabled;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public enum State {
        UNKNOWN,
        BELOW_RANGE,
        WITHIN_RANGE,
        ABOVE_RANGE
    }

    public NxtUltrasonicSensor(ComponentContainer container) {
        super(container, "NxtUltrasonicSensor");
        this.handler = new Handler();
        this.previousState = State.UNKNOWN;
        this.sensorReader = new Runnable() { // from class: com.google.appinventor.components.runtime.NxtUltrasonicSensor.1
            @Override // java.lang.Runnable
            public void run() {
                State currentState;
                if (NxtUltrasonicSensor.this.bluetooth != null && NxtUltrasonicSensor.this.bluetooth.IsConnected()) {
                    LegoMindstormsNxtSensor.SensorValue<Integer> sensorValue = NxtUltrasonicSensor.this.getDistanceValue("");
                    if (sensorValue.valid) {
                        if (((Integer) sensorValue.value).intValue() >= NxtUltrasonicSensor.this.bottomOfRange) {
                            if (((Integer) sensorValue.value).intValue() > NxtUltrasonicSensor.this.topOfRange) {
                                currentState = State.ABOVE_RANGE;
                            } else {
                                currentState = State.WITHIN_RANGE;
                            }
                        } else {
                            currentState = State.BELOW_RANGE;
                        }
                        if (currentState != NxtUltrasonicSensor.this.previousState) {
                            if (currentState == State.BELOW_RANGE && NxtUltrasonicSensor.this.belowRangeEventEnabled) {
                                NxtUltrasonicSensor.this.BelowRange();
                            }
                            if (currentState == State.WITHIN_RANGE && NxtUltrasonicSensor.this.withinRangeEventEnabled) {
                                NxtUltrasonicSensor.this.WithinRange();
                            }
                            if (currentState == State.ABOVE_RANGE && NxtUltrasonicSensor.this.aboveRangeEventEnabled) {
                                NxtUltrasonicSensor.this.AboveRange();
                            }
                        }
                        NxtUltrasonicSensor.this.previousState = currentState;
                    }
                }
                if (NxtUltrasonicSensor.this.isHandlerNeeded()) {
                    NxtUltrasonicSensor.this.handler.post(NxtUltrasonicSensor.this.sensorReader);
                }
            }
        };
        SensorPort(DEFAULT_SENSOR_PORT);
        BottomOfRange(30);
        TopOfRange(90);
        BelowRangeEventEnabled(false);
        WithinRangeEventEnabled(false);
        AboveRangeEventEnabled(false);
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtSensor
    protected void initializeSensor(String functionName) {
        setInputMode(functionName, this.port, NxtSensorType.Digital12C9V, NxtSensorMode.Raw);
        configureUltrasonicSensor(functionName);
    }

    private void configureUltrasonicSensor(String functionName) {
        byte[] data = {2, Ev3Constants.Opcode.JR_FALSE, 2};
        lsWrite(functionName, this.port, data, 0);
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtSensor
    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = DEFAULT_SENSOR_PORT, editorType = PropertyTypeConstants.PROPERTY_TYPE_LEGO_NXT_SENSOR_PORT)
    public void SensorPort(String sensorPortLetter) {
        setSensorPort(sensorPortLetter);
    }

    @SimpleFunction(description = "Returns the current distance in centimeters as a value between 0 and 254, or -1 if the distance can not be read.")
    public int GetDistance() {
        if (checkBluetooth("GetDistance")) {
            LegoMindstormsNxtSensor.SensorValue<Integer> sensorValue = getDistanceValue("GetDistance");
            if (sensorValue.valid) {
                return sensorValue.value.intValue();
            }
            return -1;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LegoMindstormsNxtSensor.SensorValue<Integer> getDistanceValue(String functionName) {
        int value;
        byte[] data = {2, Ev3Constants.Opcode.JR_TRUE};
        lsWrite(functionName, this.port, data, 1);
        int i = 0;
        while (true) {
            if (i >= 3) {
                break;
            }
            int countAvailableBytes = lsGetStatus(functionName, this.port);
            if (countAvailableBytes <= 0) {
                i++;
            } else {
                byte[] returnPackage = lsRead(functionName, this.port);
                if (returnPackage != null && (value = getUBYTEValueFromBytes(returnPackage, 4)) >= 0 && value <= 254) {
                    return new LegoMindstormsNxtSensor.SensorValue<>(true, Integer.valueOf(value));
                }
            }
        }
        return new LegoMindstormsNxtSensor.SensorValue<>(false, null);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The bottom of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int BottomOfRange() {
        return this.bottomOfRange;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "30", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void BottomOfRange(int bottomOfRange) {
        this.bottomOfRange = bottomOfRange;
        this.previousState = State.UNKNOWN;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The top of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int TopOfRange() {
        return this.topOfRange;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "90", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void TopOfRange(int topOfRange) {
        this.topOfRange = topOfRange;
        this.previousState = State.UNKNOWN;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the BelowRange event should fire when the distance goes below the BottomOfRange.")
    public boolean BelowRangeEventEnabled() {
        return this.belowRangeEventEnabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void BelowRangeEventEnabled(boolean enabled) {
        boolean handlerWasNeeded = isHandlerNeeded();
        this.belowRangeEventEnabled = enabled;
        boolean handlerIsNeeded = isHandlerNeeded();
        if (handlerWasNeeded && !handlerIsNeeded) {
            this.handler.removeCallbacks(this.sensorReader);
        }
        if (!handlerWasNeeded && handlerIsNeeded) {
            this.previousState = State.UNKNOWN;
            this.handler.post(this.sensorReader);
        }
    }

    @SimpleEvent(description = "Distance has gone below the range.")
    public void BelowRange() {
        EventDispatcher.dispatchEvent(this, "BelowRange", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the WithinRange event should fire when the distance goes between the BottomOfRange and the TopOfRange.")
    public boolean WithinRangeEventEnabled() {
        return this.withinRangeEventEnabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void WithinRangeEventEnabled(boolean enabled) {
        boolean handlerWasNeeded = isHandlerNeeded();
        this.withinRangeEventEnabled = enabled;
        boolean handlerIsNeeded = isHandlerNeeded();
        if (handlerWasNeeded && !handlerIsNeeded) {
            this.handler.removeCallbacks(this.sensorReader);
        }
        if (!handlerWasNeeded && handlerIsNeeded) {
            this.previousState = State.UNKNOWN;
            this.handler.post(this.sensorReader);
        }
    }

    @SimpleEvent(description = "Distance has gone within the range.")
    public void WithinRange() {
        EventDispatcher.dispatchEvent(this, "WithinRange", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the AboveRange event should fire when the distance goes above the TopOfRange.")
    public boolean AboveRangeEventEnabled() {
        return this.aboveRangeEventEnabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void AboveRangeEventEnabled(boolean enabled) {
        boolean handlerWasNeeded = isHandlerNeeded();
        this.aboveRangeEventEnabled = enabled;
        boolean handlerIsNeeded = isHandlerNeeded();
        if (handlerWasNeeded && !handlerIsNeeded) {
            this.handler.removeCallbacks(this.sensorReader);
        }
        if (!handlerWasNeeded && handlerIsNeeded) {
            this.previousState = State.UNKNOWN;
            this.handler.post(this.sensorReader);
        }
    }

    @SimpleEvent(description = "Distance has gone above the range.")
    public void AboveRange() {
        EventDispatcher.dispatchEvent(this, "AboveRange", new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isHandlerNeeded() {
        return this.belowRangeEventEnabled || this.withinRangeEventEnabled || this.aboveRangeEventEnabled;
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtBase, com.google.appinventor.components.runtime.Deleteable
    public void onDelete() {
        this.handler.removeCallbacks(this.sensorReader);
        super.onDelete();
    }
}
