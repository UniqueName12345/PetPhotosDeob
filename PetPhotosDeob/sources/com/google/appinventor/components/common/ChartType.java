package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum ChartType implements OptionList<Integer> {
    Line(0),
    Scatter(1),
    Area(2),
    Bar(3),
    Pie(4);
    
    private static final Map<Integer, ChartType> LOOKUP = new HashMap();
    private final int value;

    static {
        ChartType[] values;
        for (ChartType type : values()) {
            LOOKUP.put(type.toUnderlyingValue(), type);
        }
    }

    ChartType(int value) {
        this.value = value;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static ChartType fromUnderlyingValue(Integer type) {
        return LOOKUP.get(type);
    }

    public static ChartType fromUnderlyingValue(String type) {
        return fromUnderlyingValue(Integer.valueOf(Integer.parseInt(type)));
    }
}
