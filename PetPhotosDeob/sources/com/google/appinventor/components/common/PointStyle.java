package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum PointStyle implements OptionList<Integer> {
    Circle(0),
    Square(1),
    Triangle(2),
    Cross(3),
    X(4);
    
    private static final Map<Integer, PointStyle> LOOKUP = new HashMap();
    private final int value;

    static {
        PointStyle[] values;
        for (PointStyle style : values()) {
            LOOKUP.put(style.toUnderlyingValue(), style);
        }
    }

    PointStyle(int value) {
        this.value = value;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static PointStyle fromUnderlyingValue(Integer value) {
        return LOOKUP.get(value);
    }

    public static PointStyle fromUnderlyingValue(String value) {
        return fromUnderlyingValue(Integer.valueOf(Integer.parseInt(value)));
    }
}
