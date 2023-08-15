package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum TransportMethod implements OptionList<String> {
    Foot("foot-walking"),
    Car("driving-car"),
    Bicycle("cycling-regular"),
    Wheelchair("wheelchair");
    
    private static final Map<String, TransportMethod> lookup = new HashMap();
    private final String value;

    static {
        TransportMethod[] values;
        for (TransportMethod method : values()) {
            lookup.put(method.toUnderlyingValue(), method);
        }
    }

    TransportMethod(String value) {
        this.value = value;
    }

    @Override // com.google.appinventor.components.common.OptionList
    public String toUnderlyingValue() {
        return this.value;
    }

    public static TransportMethod fromUnderlyingValue(String method) {
        return lookup.get(method);
    }
}
