package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum NxtSensorType implements OptionList<Integer> {
    NoSensor(0),
    Touch(1),
    LightOn(5),
    LightOff(6),
    SoundDB(7),
    SoundDBA(8),
    ColorFull(13),
    ColorRed(14),
    ColorGreen(15),
    ColorBlue(16),
    ColorNone(17),
    Digital12C(10),
    Digital12C9V(11),
    RcxTemperature(2),
    RcxLight(3),
    RcxAngle(4);
    
    private static final Map<Integer, NxtSensorType> lookup = new HashMap();
    private final int value;

    static {
        NxtSensorType[] values;
        for (NxtSensorType type : values()) {
            lookup.put(type.toUnderlyingValue(), type);
        }
    }

    NxtSensorType(int type) {
        this.value = type;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static NxtSensorType fromUnderlyingValue(Integer type) {
        return lookup.get(type);
    }
}
