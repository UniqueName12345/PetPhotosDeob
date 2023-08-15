package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.MapFactory;

@SimpleObject
/* loaded from: classes.dex */
public abstract class MapFeatureBaseWithFill extends MapFeatureBase implements MapFactory.HasFill {
    private int fillColor;
    private float fillOpacity;

    public MapFeatureBaseWithFill(MapFactory.MapFeatureContainer container, MapFactory.MapFeatureVisitor<Double> distanceComputation) {
        super(container, distanceComputation);
        this.fillColor = -65536;
        this.fillOpacity = 1.0f;
        FillColor(-65536);
        FillOpacity(1.0f);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.HasFill
    @SimpleProperty
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_RED, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void FillColor(int argb) {
        this.fillColor = argb;
        this.map.getController().updateFeatureFill(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.HasFill
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The paint color used to fill in the %type%.")
    public int FillColor() {
        return this.fillColor;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.HasFill
    @SimpleProperty
    @DesignerProperty(defaultValue = "1.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_FLOAT)
    public void FillOpacity(float opacity) {
        this.fillOpacity = opacity;
        this.fillColor = (this.fillColor & 16777215) | (Math.round(255.0f * opacity) << 24);
        this.map.getController().updateFeatureFill(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.HasFill
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The opacity of the interior of the map feature.")
    public float FillOpacity() {
        return this.fillOpacity;
    }
}
