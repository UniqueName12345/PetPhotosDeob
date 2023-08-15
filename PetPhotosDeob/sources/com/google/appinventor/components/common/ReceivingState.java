package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum ReceivingState implements OptionList<Integer> {
    Off(1),
    Foreground(2),
    Always(3);
    
    private static final Map<Integer, ReceivingState> lookup = new HashMap();
    private final int value;

    static {
        ReceivingState[] values;
        for (ReceivingState status : values()) {
            lookup.put(status.toUnderlyingValue(), status);
        }
    }

    ReceivingState(int status) {
        this.value = status;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static ReceivingState fromUnderlyingValue(Integer status) {
        return lookup.get(status);
    }
}
