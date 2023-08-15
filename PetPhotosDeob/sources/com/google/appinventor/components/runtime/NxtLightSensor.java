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

@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a light sensor on a LEGO MINDSTORMS NXT robot.", iconName = "images/legoMindstormsNxt.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class NxtLightSensor extends LegoMindstormsNxtSensor implements Deleteable {
    private static final int DEFAULT_BOTTOM_OF_RANGE = 256;
    private static final String DEFAULT_SENSOR_PORT = "3";
    private static final int DEFAULT_TOP_OF_RANGE = 767;
    private boolean aboveRangeEventEnabled;
    private boolean belowRangeEventEnabled;
    private int bottomOfRange;
    private boolean generateLight;
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

    public NxtLightSensor(ComponentContainer container) {
        super(container, "NxtLightSensor");
        this.handler = new Handler();
        this.previousState = State.UNKNOWN;
        this.sensorReader = new Runnable() { // from class: com.google.appinventor.components.runtime.NxtLightSensor.1
            @Override // java.lang.Runnable
            public void run() {
                State currentState;
                if (NxtLightSensor.this.bluetooth != null && NxtLightSensor.this.bluetooth.IsConnected()) {
                    LegoMindstormsNxtSensor.SensorValue<Integer> sensorValue = NxtLightSensor.this.getLightValue("");
                    if (sensorValue.valid) {
                        if (((Integer) sensorValue.value).intValue() >= NxtLightSensor.this.bottomOfRange) {
                            if (((Integer) sensorValue.value).intValue() > NxtLightSensor.this.topOfRange) {
                                currentState = State.ABOVE_RANGE;
                            } else {
                                currentState = State.WITHIN_RANGE;
                            }
                        } else {
                            currentState = State.BELOW_RANGE;
                        }
                        if (currentState != NxtLightSensor.this.previousState) {
                            if (currentState == State.BELOW_RANGE && NxtLightSensor.this.belowRangeEventEnabled) {
                                NxtLightSensor.this.BelowRange();
                            }
                            if (currentState == State.WITHIN_RANGE && NxtLightSensor.this.withinRangeEventEnabled) {
                                NxtLightSensor.this.WithinRange();
                            }
                            if (currentState == State.ABOVE_RANGE && NxtLightSensor.this.aboveRangeEventEnabled) {
                                NxtLightSensor.this.AboveRange();
                            }
                        }
                        NxtLightSensor.this.previousState = currentState;
                    }
                }
                if (NxtLightSensor.this.isHandlerNeeded()) {
                    NxtLightSensor.this.handler.post(NxtLightSensor.this.sensorReader);
                }
            }
        };
        SensorPort("3");
        BottomOfRange(256);
        TopOfRange(DEFAULT_TOP_OF_RANGE);
        BelowRangeEventEnabled(false);
        WithinRangeEventEnabled(false);
        AboveRangeEventEnabled(false);
        GenerateLight(false);
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtSensor
    protected void initializeSensor(String functionName) {
        setInputMode(functionName, this.port, this.generateLight ? NxtSensorType.LightOn : NxtSensorType.LightOff, NxtSensorMode.Percentage);
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtSensor
    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "3", editorType = PropertyTypeConstants.PROPERTY_TYPE_LEGO_NXT_SENSOR_PORT)
    public void SensorPort(String sensorPortLetter) {
        setSensorPort(sensorPortLetter);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the light sensor should generate light.")
    public boolean GenerateLight() {
        return this.generateLight;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void GenerateLight(boolean generateLight) {
        this.generateLight = generateLight;
        if (this.bluetooth != null && this.bluetooth.IsConnected()) {
            initializeSensor("GenerateLight");
        }
    }

    @SimpleFunction(description = "Returns the current light level as a value between 0 and 1023, or -1 if the light level can not be read.")
    public int GetLightLevel() {
        if (checkBluetooth("GetLightLevel")) {
            LegoMindstormsNxtSensor.SensorValue<Integer> sensorValue = getLightValue("GetLightLevel");
            if (sensorValue.valid) {
                return sensorValue.value.intValue();
            }
            return -1;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LegoMindstormsNxtSensor.SensorValue<Integer> getLightValue(String functionName) {
        byte[] returnPackage = getInputValues(functionName, this.port);
        if (returnPackage != null) {
            boolean valid = getBooleanValueFromBytes(returnPackage, 4);
            if (valid) {
                int normalizedValue = getUWORDValueFromBytes(returnPackage, 10);
                return new LegoMindstormsNxtSensor.SensorValue<>(true, Integer.valueOf(normalizedValue));
            }
        }
        return new LegoMindstormsNxtSensor.SensorValue<>(false, null);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The bottom of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int BottomOfRange() {
        return this.bottomOfRange;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "256", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void BottomOfRange(int bottomOfRange) {
        this.bottomOfRange = bottomOfRange;
        this.previousState = State.UNKNOWN;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The top of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int TopOfRange() {
        return this.topOfRange;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "767", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void TopOfRange(int topOfRange) {
        this.topOfRange = topOfRange;
        this.previousState = State.UNKNOWN;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the BelowRange event should fire when the light level goes below the BottomOfRange.")
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

    @SimpleEvent(description = "Light level has gone below the range.")
    public void BelowRange() {
        EventDispatcher.dispatchEvent(this, "BelowRange", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the WithinRange event should fire when the light level goes between the BottomOfRange and the TopOfRange.")
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

    @SimpleEvent(description = "Light level has gone within the range.")
    public void WithinRange() {
        EventDispatcher.dispatchEvent(this, "WithinRange", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the AboveRange event should fire when the light level goes above the TopOfRange.")
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

    @SimpleEvent(description = "Light level has gone above the range.")
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
