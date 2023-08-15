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

@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a sound sensor on a LEGO MINDSTORMS NXT robot.", iconName = "images/legoMindstormsNxt.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class NxtSoundSensor extends LegoMindstormsNxtSensor implements Deleteable {
    private static final int DEFAULT_BOTTOM_OF_RANGE = 256;
    private static final String DEFAULT_SENSOR_PORT = "2";
    private static final int DEFAULT_TOP_OF_RANGE = 767;
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

    public NxtSoundSensor(ComponentContainer container) {
        super(container, "NxtSoundSensor");
        this.handler = new Handler();
        this.previousState = State.UNKNOWN;
        this.sensorReader = new Runnable() { // from class: com.google.appinventor.components.runtime.NxtSoundSensor.1
            @Override // java.lang.Runnable
            public void run() {
                State currentState;
                if (NxtSoundSensor.this.bluetooth != null && NxtSoundSensor.this.bluetooth.IsConnected()) {
                    LegoMindstormsNxtSensor.SensorValue<Integer> sensorValue = NxtSoundSensor.this.getSoundValue("");
                    if (sensorValue.valid) {
                        if (((Integer) sensorValue.value).intValue() >= NxtSoundSensor.this.bottomOfRange) {
                            if (((Integer) sensorValue.value).intValue() > NxtSoundSensor.this.topOfRange) {
                                currentState = State.ABOVE_RANGE;
                            } else {
                                currentState = State.WITHIN_RANGE;
                            }
                        } else {
                            currentState = State.BELOW_RANGE;
                        }
                        if (currentState != NxtSoundSensor.this.previousState) {
                            if (currentState == State.BELOW_RANGE && NxtSoundSensor.this.belowRangeEventEnabled) {
                                NxtSoundSensor.this.BelowRange();
                            }
                            if (currentState == State.WITHIN_RANGE && NxtSoundSensor.this.withinRangeEventEnabled) {
                                NxtSoundSensor.this.WithinRange();
                            }
                            if (currentState == State.ABOVE_RANGE && NxtSoundSensor.this.aboveRangeEventEnabled) {
                                NxtSoundSensor.this.AboveRange();
                            }
                        }
                        NxtSoundSensor.this.previousState = currentState;
                    }
                }
                if (NxtSoundSensor.this.isHandlerNeeded()) {
                    NxtSoundSensor.this.handler.post(NxtSoundSensor.this.sensorReader);
                }
            }
        };
        SensorPort("2");
        BottomOfRange(256);
        TopOfRange(DEFAULT_TOP_OF_RANGE);
        BelowRangeEventEnabled(false);
        WithinRangeEventEnabled(false);
        AboveRangeEventEnabled(false);
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtSensor
    protected void initializeSensor(String functionName) {
        setInputMode(functionName, this.port, NxtSensorType.SoundDB, NxtSensorMode.Raw);
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsNxtSensor
    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "2", editorType = PropertyTypeConstants.PROPERTY_TYPE_LEGO_NXT_SENSOR_PORT)
    public void SensorPort(String sensorPortLetter) {
        setSensorPort(sensorPortLetter);
    }

    @SimpleFunction(description = "Returns the current sound level as a value between 0 and 1023, or -1 if the sound level can not be read.")
    public int GetSoundLevel() {
        if (checkBluetooth("GetSoundLevel")) {
            LegoMindstormsNxtSensor.SensorValue<Integer> sensorValue = getSoundValue("GetSoundLevel");
            if (sensorValue.valid) {
                return sensorValue.value.intValue();
            }
            return -1;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LegoMindstormsNxtSensor.SensorValue<Integer> getSoundValue(String functionName) {
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

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the BelowRange event should fire when the sound level goes below the BottomOfRange.")
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

    @SimpleEvent(description = "Sound level has gone below the range.")
    public void BelowRange() {
        EventDispatcher.dispatchEvent(this, "BelowRange", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the WithinRange event should fire when the sound level goes between the BottomOfRange and the TopOfRange.")
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

    @SimpleEvent(description = "Sound level has gone within the range.")
    public void WithinRange() {
        EventDispatcher.dispatchEvent(this, "WithinRange", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the AboveRange event should fire when the sound level goes above the TopOfRange.")
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

    @SimpleEvent(description = "Sound level has gone above the range.")
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
