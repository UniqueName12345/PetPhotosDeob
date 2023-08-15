package com.google.appinventor.components.common;

import com.google.appinventor.components.runtime.util.MapFactory;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public enum MapFeature implements OptionList<String> {
    Circle(MapFactory.MapFeatureType.TYPE_CIRCLE),
    LineString(MapFactory.MapFeatureType.TYPE_LINESTRING),
    Marker(MapFactory.MapFeatureType.TYPE_MARKER),
    Polygon(MapFactory.MapFeatureType.TYPE_POLYGON),
    Rectangle(MapFactory.MapFeatureType.TYPE_RECTANGLE);
    
    private static final Map<String, MapFeature> lookup = new HashMap();
    private final String value;

    static {
        MapFeature[] values;
        for (MapFeature feat : values()) {
            lookup.put(feat.toUnderlyingValue(), feat);
        }
    }

    MapFeature(String feat) {
        this.value = feat;
    }

    @Override // com.google.appinventor.components.common.OptionList
    public String toUnderlyingValue() {
        return this.value;
    }

    public static MapFeature fromUnderlyingValue(String feat) {
        return lookup.get(feat);
    }
}
