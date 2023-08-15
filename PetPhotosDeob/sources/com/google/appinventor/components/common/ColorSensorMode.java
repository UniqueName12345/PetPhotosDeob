package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum ColorSensorMode implements OptionList<String> {
    Reflected("reflected", 0),
    Ambient("ambient", 1),
    Color(PropertyTypeConstants.PROPERTY_TYPE_COLOR, 2);
    
    private static final Map<String, ColorSensorMode> lookup = new HashMap();
    private final int intValue;
    private final String value;

    static {
        ColorSensorMode[] values;
        for (ColorSensorMode mode : values()) {
            lookup.put(mode.toUnderlyingValue(), mode);
        }
    }

    ColorSensorMode(String mode, int intMode) {
        this.value = mode;
        this.intValue = intMode;
    }

    @Override // com.google.appinventor.components.common.OptionList
    public String toUnderlyingValue() {
        return this.value;
    }

    public Integer toInt() {
        return Integer.valueOf(this.intValue);
    }

    public static ColorSensorMode fromUnderlyingValue(String mode) {
        return lookup.get(mode);
    }
}
