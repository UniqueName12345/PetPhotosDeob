package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum NxtRunState implements OptionList<Integer> {
    Disabled(0),
    Running(32),
    RampUp(16),
    RampDown(64);
    
    private static final Map<Integer, NxtRunState> lookup = new HashMap();
    private final int value;

    static {
        NxtRunState[] values;
        for (NxtRunState state : values()) {
            lookup.put(state.toUnderlyingValue(), state);
        }
    }

    NxtRunState(int state) {
        this.value = state;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static NxtRunState fromUnderlyingValue(Integer state) {
        return lookup.get(state);
    }
}
