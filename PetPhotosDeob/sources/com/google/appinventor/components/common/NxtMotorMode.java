package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum NxtMotorMode implements OptionList<Integer> {
    On(1),
    Brake(2),
    Regulated(4),
    Coast(0);
    
    private static final Map<Integer, NxtMotorMode> lookup = new HashMap();
    private final int value;

    static {
        NxtMotorMode[] values;
        for (NxtMotorMode mode : values()) {
            lookup.put(mode.toUnderlyingValue(), mode);
        }
    }

    NxtMotorMode(int mode) {
        this.value = mode;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static NxtMotorMode fromUnderlyingValue(Integer mode) {
        return lookup.get(mode);
    }
}
