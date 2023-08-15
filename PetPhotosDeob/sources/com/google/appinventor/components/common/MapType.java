package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum MapType implements OptionList<Integer> {
    Road(1),
    Aerial(2),
    Terrain(3);
    
    private static final Map<Integer, MapType> lookup = new HashMap();
    private final Integer value;

    static {
        MapType[] values;
        for (MapType type : values()) {
            lookup.put(type.toUnderlyingValue(), type);
        }
    }

    MapType(Integer value) {
        this.value = value;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.appinventor.components.common.OptionList
    public Integer toUnderlyingValue() {
        return this.value;
    }

    public static MapType fromUnderlyingValue(Integer type) {
        return lookup.get(type);
    }
}
