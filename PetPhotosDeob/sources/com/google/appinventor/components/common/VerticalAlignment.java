package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum VerticalAlignment implements OptionList<Integer> {
    Top(1),
    Center(2),
    Bottom(3);
    
    private static final Map<Integer, VerticalAlignment> lookup = new HashMap();
    private final int value;

    static {
        VerticalAlignment[] values;
        for (VerticalAlignment alignment : values()) {
            lookup.put(alignment.toUnderlyingValue(), alignment);
        }
    }

    VerticalAlignment(int value) {
        this.value = value;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static VerticalAlignment fromUnderlyingValue(Integer alignment) {
        return lookup.get(alignment);
    }
}
