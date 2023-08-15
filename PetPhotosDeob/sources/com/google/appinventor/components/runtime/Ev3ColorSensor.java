package com.google.appinventor.components.runtime;

import android.os.Handler;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ColorSensorMode;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a color sensor on a LEGO MINDSTORMS EV3 robot.", iconName = "images/legoMindstormsEv3.png", nonVisible = SyntaxForms.DEBUGGING, version = 2)
@SimpleObject
/* loaded from: classes.dex */
public class Ev3ColorSensor extends LegoMindstormsEv3Sensor implements Deleteable {
    private static final int DEFAULT_BOTTOM_OF_RANGE = 30;
    private static final int DEFAULT_TOP_OF_RANGE = 60;
    private static final int DELAY_MILLISECONDS = 50;
    private static final int SENSOR_TYPE = 29;
    private boolean aboveRangeEventEnabled;
    private boolean belowRangeEventEnabled;
    private int bottomOfRange;
    private boolean colorChangedEventEnabled;
    private Handler eventHandler;
    private ColorSensorMode mode;
    private int previousColor;
    private int previousLightLevel;
    private final Runnable sensorValueChecker;
    private int topOfRange;
    private boolean withinRangeEventEnabled;

    public Ev3ColorSensor(ComponentContainer container) {
        super(container, "Ev3ColorSensor");
        this.mode = ColorSensorMode.Reflected;
        this.previousColor = -1;
        this.previousLightLevel = 0;
        this.eventHandler = new Handler();
        this.sensorValueChecker = new Runnable() { // from class: com.google.appinventor.components.runtime.Ev3ColorSensor.1
            @Override // java.lang.Runnable
            public void run() {
                if (Ev3ColorSensor.this.bluetooth != null && Ev3ColorSensor.this.bluetooth.IsConnected()) {
                    if (Ev3ColorSensor.this.mode == ColorSensorMode.Color) {
                        int currentColor = Ev3ColorSensor.this.getSensorValue("");
                        if (Ev3ColorSensor.this.previousColor < 0) {
                            Ev3ColorSensor.this.previousColor = currentColor;
                            Ev3ColorSensor.this.eventHandler.postDelayed(this, 50L);
                            return;
                        }
                        if (currentColor != Ev3ColorSensor.this.previousColor && Ev3ColorSensor.this.colorChangedEventEnabled) {
                            Ev3ColorSensor.this.ColorChanged(currentColor, Ev3ColorSensor.this.toColorName(currentColor));
                        }
                        Ev3ColorSensor.this.previousColor = currentColor;
                    } else {
                        int currentLightLevel = Ev3ColorSensor.this.getSensorValue("");
                        if (Ev3ColorSensor.this.previousLightLevel < 0) {
                            Ev3ColorSensor.this.previousLightLevel = currentLightLevel;
                            Ev3ColorSensor.this.eventHandler.postDelayed(this, 50L);
                            return;
                        }
                        if (currentLightLevel < Ev3ColorSensor.this.bottomOfRange) {
                            if (Ev3ColorSensor.this.belowRangeEventEnabled && Ev3ColorSensor.this.previousLightLevel >= Ev3ColorSensor.this.bottomOfRange) {
                                Ev3ColorSensor.this.BelowRange();
                            }
                        } else if (currentLightLevel > Ev3ColorSensor.this.topOfRange) {
                            if (Ev3ColorSensor.this.aboveRangeEventEnabled && Ev3ColorSensor.this.previousLightLevel <= Ev3ColorSensor.this.topOfRange) {
                                Ev3ColorSensor.this.AboveRange();
                            }
                        } else if (Ev3ColorSensor.this.withinRangeEventEnabled && (Ev3ColorSensor.this.previousLightLevel < Ev3ColorSensor.this.bottomOfRange || Ev3ColorSensor.this.previousLightLevel > Ev3ColorSensor.this.topOfRange)) {
                            Ev3ColorSensor.this.WithinRange();
                        }
                        Ev3ColorSensor.this.previousLightLevel = currentLightLevel;
                    }
                }
                Ev3ColorSensor.this.eventHandler.postDelayed(this, 50L);
            }
        };
        this.eventHandler.post(this.sensorValueChecker);
        TopOfRange(60);
        BottomOfRange(30);
        BelowRangeEventEnabled(false);
        AboveRangeEventEnabled(false);
        WithinRangeEventEnabled(false);
        ColorChangedEventEnabled(false);
        ModeAbstract(ColorSensorMode.Reflected);
    }

    @SimpleFunction(description = "It returns the light level in percentage, or -1 when the light level cannot be read.")
    public int GetLightLevel() {
        if (this.mode == ColorSensorMode.Color) {
            return -1;
        }
        return getSensorValue("GetLightLevel");
    }

    @SimpleFunction(description = "It returns the color code from 0 to 7 corresponding to no color, black, blue, green, yellow, red, white and brown.")
    public int GetColorCode() {
        if (this.mode != ColorSensorMode.Color) {
            return 0;
        }
        return getSensorValue("GetColorCode");
    }

    @SimpleFunction(description = "Return the color name in one of \"No Color\", \"Black\", \"Blue\", \"Green\", \"Yellow\", \"Red\", \"White\", \"Brown\".")
    public String GetColorName() {
        return this.mode != ColorSensorMode.Color ? "No Color" : toColorName(getSensorValue("GetColorName"));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The bottom of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int BottomOfRange() {
        return this.bottomOfRange;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "30", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void BottomOfRange(int bottomOfRange) {
        this.bottomOfRange = bottomOfRange;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The top of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int TopOfRange() {
        return this.topOfRange;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "60", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void TopOfRange(int topOfRange) {
        this.topOfRange = topOfRange;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the BelowRange event should fire when the light level goes below the BottomOfRange.")
    public boolean BelowRangeEventEnabled() {
        return this.belowRangeEventEnabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void BelowRangeEventEnabled(boolean enabled) {
        this.belowRangeEventEnabled = enabled;
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
        this.withinRangeEventEnabled = enabled;
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
        this.aboveRangeEventEnabled = enabled;
    }

    @SimpleEvent(description = "Light level has gone above the range.")
    public void AboveRange() {
        EventDispatcher.dispatchEvent(this, "AboveRange", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the ColorChanged event should fire when the Mode property is set to \"color\" and the detected color changes.")
    public boolean ColorChangedEventEnabled() {
        return this.colorChangedEventEnabled;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void ColorChangedEventEnabled(boolean enabled) {
        this.colorChangedEventEnabled = enabled;
    }

    @SimpleEvent(description = "Called when the detected color has changed. The ColorChanged event will occur if the Mode property is set to \"color\" and the ColorChangedEventEnabled property is set to True.")
    public void ColorChanged(int colorCode, String colorName) {
        EventDispatcher.dispatchEvent(this, "ColorChanged", Integer.valueOf(colorCode), colorName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSensorValue(String functionName) {
        int level = readInputPercentage(functionName, 0, this.sensorPortNumber, 29, this.mode.toInt().intValue());
        if (this.mode != ColorSensorMode.Color) {
            return level;
        }
        switch (level) {
            case 0:
            default:
                return 0;
            case 12:
                return 1;
            case 25:
                return 2;
            case 37:
                return 3;
            case 50:
                return 4;
            case 62:
                return 5;
            case 75:
                return 6;
            case 87:
                return 7;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String toColorName(int colorCode) {
        if (this.mode != ColorSensorMode.Color) {
            return "No Color";
        }
        switch (colorCode) {
            case 0:
                return "No Color";
            case 1:
                return "Black";
            case 2:
                return "Blue";
            case 3:
                return "Green";
            case 4:
                return "Yellow";
            case 5:
                return "Red";
            case 6:
                return "White";
            case 7:
                return "Brown";
            default:
                return "No Color";
        }
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "reflected", editorType = PropertyTypeConstants.PROPERTY_TYPE_LEGO_EV3_COLOR_SENSOR_MODE)
    public void Mode(@Options(ColorSensorMode.class) String modeName) {
        ColorSensorMode mode = ColorSensorMode.fromUnderlyingValue(modeName);
        if (mode == null) {
            this.form.dispatchErrorOccurredEvent(this, "Mode", ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, modeName);
        } else {
            setMode(mode);
        }
    }

    public void ModeAbstract(ColorSensorMode mode) {
        setMode(mode);
    }

    public ColorSensorMode ModeAbstract() {
        return this.mode;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Get the current sensor mode.")
    @Options(ColorSensorMode.class)
    public String Mode() {
        return this.mode.toUnderlyingValue();
    }

    @SimpleFunction(description = "Enter the color detection mode.")
    @Deprecated
    public void SetColorMode() {
        setMode(ColorSensorMode.Color);
    }

    @SimpleFunction(description = "Make the sensor read the light level with reflected light.")
    @Deprecated
    public void SetReflectedMode() {
        setMode(ColorSensorMode.Reflected);
    }

    @SimpleFunction(description = "Make the sensor read the light level without reflected light.")
    @Deprecated
    public void SetAmbientMode() {
        setMode(ColorSensorMode.Ambient);
    }

    private void setMode(ColorSensorMode newMode) {
        this.previousColor = -1;
        this.previousLightLevel = -1;
        this.mode = newMode;
    }

    @Override // com.google.appinventor.components.runtime.LegoMindstormsEv3Base, com.google.appinventor.components.runtime.Deleteable
    public void onDelete() {
        this.eventHandler.removeCallbacks(this.sensorValueChecker);
        super.onDelete();
    }
}
