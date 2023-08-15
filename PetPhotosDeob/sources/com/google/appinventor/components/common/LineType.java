package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum LineType implements OptionList<Integer> {
    Linear(0),
    Curved(1),
    Stepped(2);
    
    private static final Map<Integer, LineType> LOOKUP = new HashMap();
    private final int value;

    static {
        LineType[] values;
        for (LineType type : values()) {
            LOOKUP.put(type.toUnderlyingValue(), type);
        }
    }

    LineType(int value) {
        this.value = value;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static LineType fromUnderlyingValue(Integer value) {
        return LOOKUP.get(value);
    }

    public static LineType fromUnderlyingValue(String value) {
        return fromUnderlyingValue(Integer.valueOf(Integer.parseInt(value)));
    }
}
