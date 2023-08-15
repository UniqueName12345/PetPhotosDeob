package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum StartedStatus implements OptionList<Integer> {
    Incoming(1),
    Outgoing(2);
    
    private static final Map<Integer, StartedStatus> lookup = new HashMap();
    private final int value;

    static {
        StartedStatus[] values;
        for (StartedStatus status : values()) {
            lookup.put(status.toUnderlyingValue(), status);
        }
    }

    StartedStatus(int status) {
        this.value = status;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static StartedStatus fromUnderlyingValue(Integer status) {
        return lookup.get(status);
    }
}
