package com.google.appinventor.components.runtime;

import android.view.View;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.MapFactory;
import com.google.appinventor.components.runtime.util.YailList;
import org.json.JSONException;

@DesignerComponent(category = ComponentCategory.MAPS, description = "A FeatureCollection contains one or more map features as a group. Any events fired on a feature in the collection will also trigger the corresponding event on the collection object. FeatureCollections can be loaded from external resources as a means of populating a Map with content.", version = 2)
@SimpleObject
/* loaded from: classes.dex */
public class FeatureCollection extends MapFeatureContainerBase implements MapFactory.MapFeatureCollection {
    private Map map;
    private String source;

    public FeatureCollection(MapFactory.MapFeatureContainer container) {
        super(container);
        this.source = "";
        this.map = container.getMap();
    }

    @SimpleProperty(description = "Loads a collection of features from the given string. If the string is not valid GeoJSON, the ErrorLoadingFeatureCollection error will be run with url = <string>.")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_TEXTAREA)
    public void FeaturesFromGeoJSON(String geojson) {
        try {
            processGeoJSON("<string>", geojson);
        } catch (JSONException e) {
            $form().dispatchErrorOccurredEvent(this, "FeaturesFromGeoJSON", ErrorMessages.ERROR_INVALID_GEOJSON, e.getMessage());
        }
    }

    @Override // com.google.appinventor.components.runtime.MapFeatureContainerBase, com.google.appinventor.components.runtime.util.MapFactory.MapFeatureCollection
    @SimpleEvent(description = "A GeoJSON document was successfully read from url. The features specified in the document are provided as a list in features.")
    public void GotFeatures(String url, YailList features) {
        this.source = url;
        super.GotFeatures(url, features);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureCollection
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_GEOJSON_TYPE)
    public void Source(String source) {
        this.source = source;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureCollection
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Specifies the source URL used to populate the feature collection. If the feature collection was not loaded from a URL, this will be the empty string.")
    public String Source() {
        return this.source;
    }

    @Override // com.google.appinventor.components.runtime.AndroidViewComponent, com.google.appinventor.components.runtime.util.MapFactory.MapFeatureCollection
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean Visible() {
        return getMap().getController().isFeatureCollectionVisible(this);
    }

    @Override // com.google.appinventor.components.runtime.AndroidViewComponent
    @SimpleProperty(description = "Specifies whether the component should be visible on the screen. Value is true if the component is showing and false if hidden.")
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_VISIBILITY)
    public void Visible(boolean visibility) {
        getMap().getController().setFeatureCollectionVisible(this, visibility);
    }

    @Override // com.google.appinventor.components.runtime.AndroidViewComponent
    public View getView() {
        return null;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureContainer
    public Map getMap() {
        return this.map;
    }
}
