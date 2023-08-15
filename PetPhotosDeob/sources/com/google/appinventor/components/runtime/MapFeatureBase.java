package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.MapFactory;
import com.google.appinventor.components.runtime.util.YailList;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.util.GeoPoint;

@SimpleObject
/* loaded from: classes.dex */
public abstract class MapFeatureBase implements MapFactory.MapFeature, MapFactory.HasStroke {
    protected MapFactory.MapFeatureContainer container;
    private final MapFactory.MapFeatureVisitor<Double> distanceComputation;
    protected Map map;
    private boolean visible = true;
    private int strokeColor = -16777216;
    private float strokeOpacity = 1.0f;
    private int strokeWidth = 1;
    private String title = "";
    private String description = "";
    private boolean draggable = false;
    private boolean infobox = false;
    private GeoPoint centroid = null;
    private MapFactory.MapFeatureVisitor<Double> distanceToPoint = new MapFactory.MapFeatureVisitor<Double>() { // from class: com.google.appinventor.components.runtime.MapFeatureBase.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapMarker marker, Object... arguments) {
            return Double.valueOf(GeometryUtil.distanceBetween(marker, (GeoPoint) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapLineString lineString, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(lineString, (GeoPoint) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(lineString, (GeoPoint) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapPolygon polygon, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(polygon, (GeoPoint) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(polygon, (GeoPoint) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapCircle circle, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(circle, (GeoPoint) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(circle, (GeoPoint) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapRectangle rectangle, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(rectangle, (GeoPoint) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(rectangle, (GeoPoint) arguments[0]));
        }
    };
    private Geometry geometry = null;

    protected abstract Geometry computeGeometry();

    /* JADX INFO: Access modifiers changed from: protected */
    public MapFeatureBase(MapFactory.MapFeatureContainer container, MapFactory.MapFeatureVisitor<Double> distanceComputation) {
        this.container = null;
        this.map = null;
        this.container = container;
        this.map = container.getMap();
        this.distanceComputation = distanceComputation;
        Description("");
        Draggable(false);
        EnableInfobox(false);
        StrokeColor(-16777216);
        StrokeOpacity(1.0f);
        StrokeWidth(1);
        Title("");
        Visible(true);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    public void setMap(MapFactory.MapFeatureContainer container) {
        this.map = container.getMap();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    public void removeFromMap() {
        this.map.getController().removeFeature(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_VISIBILITY)
    public void Visible(boolean visibility) {
        if (this.visible != visibility) {
            this.visible = visibility;
            if (this.visible) {
                this.map.getController().showFeature(this);
            } else {
                this.map.getController().hideFeature(this);
            }
            this.map.getView().invalidate();
        }
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Specifies whether the %type% should be visible on the screen. Value is true if the component is showing and false if hidden.")
    public boolean Visible() {
        return this.visible;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.HasStroke
    @SimpleProperty
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_BLACK, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void StrokeColor(int argb) {
        this.strokeColor = argb;
        this.map.getController().updateFeatureStroke(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.HasStroke
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The paint color used to outline the %type%.")
    public int StrokeColor() {
        return this.strokeColor;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.HasStroke
    @SimpleProperty
    @DesignerProperty(defaultValue = "1.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_FLOAT)
    public void StrokeOpacity(float opacity) {
        this.strokeOpacity = opacity;
        this.strokeColor = (this.strokeColor & 16777215) | (Math.round(255.0f * opacity) << 24);
        this.map.getController().updateFeatureStroke(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.HasStroke
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The opacity of the stroke used to outline the map feature.")
    public float StrokeOpacity() {
        return this.strokeOpacity;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_SANSSERIF, editorType = PropertyTypeConstants.PROPERTY_TYPE_INTEGER)
    public void StrokeWidth(int width) {
        this.strokeWidth = width;
        this.map.getController().updateFeatureStroke(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.HasStroke
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The width of the stroke used to outline the %type%.")
    public int StrokeWidth() {
        return this.strokeWidth;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Draggable(boolean draggable) {
        this.draggable = draggable;
        this.map.getController().updateFeatureDraggable(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty(description = "The Draggable property is used to set whether or not the user can drag the %type% by long-pressing and then dragging the %type% to a new location.")
    public boolean Draggable() {
        return this.draggable;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty
    @DesignerProperty
    public void Title(String title) {
        this.title = title;
        this.map.getController().updateFeatureText(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The title displayed in the info window that appears when the user clicks on the %type%.")
    public String Title() {
        return this.title;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty
    @DesignerProperty
    public void Description(String description) {
        this.description = description;
        this.map.getController().updateFeatureText(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The description displayed in the info window that appears when the user clicks on the %type%.")
    public String Description() {
        return this.description;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void EnableInfobox(boolean enable) {
        this.infobox = enable;
        this.map.getController().updateFeatureText(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Enable or disable the infobox window display when the user taps the %type%.")
    public boolean EnableInfobox() {
        return this.infobox;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleFunction(description = "Show the infobox for the %type%. This will show the infobox even if EnableInfobox is set to false.")
    public void ShowInfobox() {
        this.map.getController().showInfobox(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleFunction(description = "Hide the infobox if it is shown. If the infobox is not visible this function has no effect.")
    public void HideInfobox() {
        this.map.getController().hideInfobox(this);
    }

    public YailList Centroid() {
        return GeometryUtil.asYailList(getCentroid());
    }

    @SimpleFunction(description = "Compute the distance, in meters, between a %type% and a latitude, longitude point.")
    public double DistanceToPoint(double latitude, double longitude, boolean centroid) {
        return ((Double) accept(this.distanceToPoint, new GeoPoint(latitude, longitude), Boolean.valueOf(centroid))).doubleValue();
    }

    @SimpleFunction(description = "Compute the distance, in meters, between two map features.")
    public double DistanceToFeature(MapFactory.MapFeature mapFeature, boolean centroids) {
        if (mapFeature == null) {
            return -1.0d;
        }
        return ((Double) mapFeature.accept(this.distanceComputation, this, Boolean.valueOf(centroids))).doubleValue();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleEvent(description = "The user clicked on the %type%.")
    public void Click() {
        EventDispatcher.dispatchEvent(this, "Click", new Object[0]);
        this.container.FeatureClick(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleEvent(description = "The user long-pressed on the %type%. This event will only trigger if Draggable is false.")
    public void LongClick() {
        EventDispatcher.dispatchEvent(this, "LongClick", new Object[0]);
        this.container.FeatureLongClick(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleEvent(description = "The user started a drag operation.")
    public void StartDrag() {
        EventDispatcher.dispatchEvent(this, "StartDrag", new Object[0]);
        this.container.FeatureStartDrag(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleEvent(description = "The user dragged the %type%.")
    public void Drag() {
        EventDispatcher.dispatchEvent(this, "Drag", new Object[0]);
        this.container.FeatureDrag(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleEvent(description = "The user stopped a drag operation.")
    public void StopDrag() {
        EventDispatcher.dispatchEvent(this, "StopDrag", new Object[0]);
        this.container.FeatureStopDrag(this);
    }

    @Override // com.google.appinventor.components.runtime.Component
    public HandlesEventDispatching getDispatchDelegate() {
        return this.map.getDispatchDelegate();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    public final synchronized GeoPoint getCentroid() {
        if (this.centroid == null) {
            this.centroid = GeometryUtil.jtsPointToGeoPoint(getGeometry().getCentroid());
        }
        return this.centroid;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    public final synchronized Geometry getGeometry() {
        if (this.geometry == null) {
            this.geometry = computeGeometry();
        }
        return this.geometry;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final synchronized void clearGeometry() {
        this.centroid = null;
        this.geometry = null;
    }
}
