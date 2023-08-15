package com.google.appinventor.components.runtime.util;

import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.LocationEnumeration;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ComponentUtil {
    private ComponentUtil() {
    }

    public static List<Object> filterComponentsOfType(Environment env, String type) {
        List<Object> components = new ArrayList<>();
        LocationEnumeration iterator = env.enumerateAllLocations();
        while (iterator.hasNext()) {
            Location loc = iterator.next();
            Object maybeComponent = loc.get();
            if (maybeComponent.getClass().getName().equals(type)) {
                components.add(maybeComponent);
            }
        }
        return components;
    }
}
