package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum NxtRegulationMode implements OptionList<Integer> {
    Disabled(0),
    Speed(1),
    Synchronization(2);
    
    private static final Map<Integer, NxtRegulationMode> lookup = new HashMap();
    private final int value;

    static {
        NxtRegulationMode[] values;
        for (NxtRegulationMode mode : values()) {
            lookup.put(mode.toUnderlyingValue(), mode);
        }
    }

    NxtRegulationMode(int mode) {
        this.value = mode;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static NxtRegulationMode fromUnderlyingValue(Integer mode) {
        return lookup.get(mode);
    }
}
