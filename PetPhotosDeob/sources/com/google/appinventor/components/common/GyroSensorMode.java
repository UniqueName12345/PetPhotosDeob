package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum GyroSensorMode implements OptionList<String> {
    Angle("angle", 0),
    Rate("rate", 1);
    
    private static final Map<String, GyroSensorMode> lookup = new HashMap();
    private final int intValue;
    private final String value;

    static {
        GyroSensorMode[] values;
        for (GyroSensorMode mode : values()) {
            lookup.put(mode.toUnderlyingValue(), mode);
        }
    }

    GyroSensorMode(String mode, int intMode) {
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

    public static GyroSensorMode fromUnderlyingValue(String mode) {
        return lookup.get(mode);
    }
}
