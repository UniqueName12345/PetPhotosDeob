package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum HorizontalAlignment implements OptionList<Integer> {
    Left(1),
    Center(3),
    Right(2);
    
    private static final Map<Integer, HorizontalAlignment> lookup = new HashMap();
    private final int value;

    static {
        HorizontalAlignment[] values;
        for (HorizontalAlignment alignment : values()) {
            lookup.put(alignment.toUnderlyingValue(), alignment);
        }
    }

    HorizontalAlignment(int value) {
        this.value = value;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return Integer.valueOf(this.value);
    }

    public static HorizontalAlignment fromUnderlyingValue(Integer alignment) {
        return lookup.get(alignment);
    }
}
