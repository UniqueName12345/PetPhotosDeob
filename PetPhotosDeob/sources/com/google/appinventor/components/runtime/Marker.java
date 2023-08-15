package com.google.appinventor.components.runtime;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.appinventor.components.annotations.Asset;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.HorizontalAlignment;
import com.google.appinventor.components.common.MapFeature;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.VerticalAlignment;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.MapFactory;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

@DesignerComponent(category = ComponentCategory.MAPS, description = "<p>An icon positioned at a point to indicate information on a map. Markers can be used to provide an info window, custom fill and stroke colors, and custom images to convey information to the user.</p>", version = 4)
@UsesLibraries(libraries = "osmdroid.aar, androidsvg.jar")
@SimpleObject
/* loaded from: classes.dex */
public class Marker extends MapFeatureBaseWithFill implements MapFactory.MapMarker {
    private HorizontalAlignment anchorHAlign;
    private VerticalAlignment anchorVAlign;
    private final Handler handler;
    private int height;
    private String imagePath;
    private GeoPoint location;
    private volatile boolean pendingUpdate;
    private int width;
    private static final String TAG = Marker.class.getSimpleName();
    private static final MapFactory.MapFeatureVisitor<Double> distanceComputation = new MapFactory.MapFeatureVisitor<Double>() { // from class: com.google.appinventor.components.runtime.Marker.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapMarker marker, Object... arguments) {
            return Double.valueOf(GeometryUtil.distanceBetween((Marker) arguments[0], marker));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapLineString lineString, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids((Marker) arguments[0], lineString)) : Double.valueOf(GeometryUtil.distanceBetweenEdges((Marker) arguments[0], lineString));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapPolygon polygon, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids((Marker) arguments[0], polygon)) : Double.valueOf(GeometryUtil.distanceBetweenEdges((Marker) arguments[0], polygon));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapCircle circle, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids((Marker) arguments[0], circle)) : Double.valueOf(GeometryUtil.distanceBetweenEdges((Marker) arguments[0], circle));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapRectangle rectangle, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids((Marker) arguments[0], rectangle)) : Double.valueOf(GeometryUtil.distanceBetweenEdges((Marker) arguments[0], rectangle));
        }
    };
    private static final MapFactory.MapFeatureVisitor<Double> bearingComputation = new MapFactory.MapFeatureVisitor<Double>() { // from class: com.google.appinventor.components.runtime.Marker.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapMarker marker, Object... arguments) {
            return Double.valueOf(GeometryUtil.bearingTo((Marker) arguments[0], marker));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapLineString lineString, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.bearingToCentroid((MapFactory.MapMarker) arguments[0], lineString)) : Double.valueOf(GeometryUtil.bearingToEdge((MapFactory.MapMarker) arguments[0], lineString));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapPolygon polygon, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.bearingToCentroid((MapFactory.MapMarker) arguments[0], polygon)) : Double.valueOf(GeometryUtil.bearingToEdge((MapFactory.MapMarker) arguments[0], polygon));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapCircle circle, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.bearingToCentroid((MapFactory.MapMarker) arguments[0], circle)) : Double.valueOf(GeometryUtil.bearingToEdge((MapFactory.MapMarker) arguments[0], circle));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapRectangle rectangle, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.bearingToCentroid((MapFactory.MapMarker) arguments[0], rectangle)) : Double.valueOf(GeometryUtil.bearingToEdge((MapFactory.MapMarker) arguments[0], rectangle));
        }
    };

    public Marker(MapFactory.MapFeatureContainer container) {
        super(container, distanceComputation);
        this.imagePath = "";
        this.anchorHAlign = HorizontalAlignment.Center;
        this.anchorVAlign = VerticalAlignment.Bottom;
        this.location = new GeoPoint(0.0d, 0.0d);
        this.width = -1;
        this.height = -1;
        this.pendingUpdate = false;
        this.handler = new Handler(Looper.getMainLooper());
        container.addFeature(this);
        ShowShadow(false);
        AnchorHorizontal(3);
        AnchorVertical(3);
        ImageAsset("");
        Width(-1);
        Height(-1);
        Latitude(0.0d);
        Longitude(0.0d);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty(description = "Returns the type of the feature. For Markers, this returns MapFeature.Marker (\"Marker\").")
    @Options(MapFeature.class)
    public String Type() {
        return TypeAbstract().toUnderlyingValue();
    }

    public MapFeature TypeAbstract() {
        return MapFeature.Marker;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_LATITUDE)
    public void Latitude(double latitude) {
        Log.d(TAG, "Latitude");
        if (latitude < -90.0d || latitude > 90.0d) {
            this.container.$form().dispatchErrorOccurredEvent(this, "Latitude", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
            return;
        }
        this.location.setLatitude(latitude);
        clearGeometry();
        this.map.getController().updateFeaturePosition(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    public double Latitude() {
        return this.location.getLatitude();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_LONGITUDE)
    public void Longitude(double longitude) {
        Log.d(TAG, "Longitude");
        if (longitude < -180.0d || longitude > 180.0d) {
            this.container.$form().dispatchErrorOccurredEvent(this, "Longitude", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
            return;
        }
        this.location.setLongitude(longitude);
        clearGeometry();
        this.map.getController().updateFeaturePosition(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    public double Longitude() {
        return this.location.getLongitude();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void ImageAsset(@Asset String path) {
        Log.d(TAG, "ImageAsset");
        this.imagePath = path;
        setNeedsUpdate();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty(description = "The ImageAsset property is used to provide an alternative image for the Marker.")
    public String ImageAsset() {
        return this.imagePath;
    }

    @Override // com.google.appinventor.components.runtime.MapFeatureBase, com.google.appinventor.components.runtime.util.MapFactory.HasStroke
    @SimpleProperty
    public void StrokeColor(int argb) {
        super.StrokeColor(argb);
        this.map.getController().updateFeatureStroke(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_MONOSPACE, editorType = PropertyTypeConstants.PROPERTY_TYPE_HORIZONTAL_ALIGNMENT)
    public void AnchorHorizontal(@Options(HorizontalAlignment.class) int horizontal) {
        HorizontalAlignment alignment = HorizontalAlignment.fromUnderlyingValue(Integer.valueOf(horizontal));
        if (alignment == null) {
            this.container.$form().dispatchErrorOccurredEvent(this, "AnchorHorizontal", ErrorMessages.ERROR_INVALID_ANCHOR_HORIZONTAL, Integer.valueOf(horizontal));
        } else {
            AnchorHorizontalAbstract(alignment);
        }
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty(description = "The horizontal alignment property controls where the Marker's anchor is located relative to its width. The choices are: 1 = left aligned, 3 = horizontally centered, 2 = right aligned.")
    @Options(HorizontalAlignment.class)
    public int AnchorHorizontal() {
        return AnchorHorizontalAbstract().toUnderlyingValue().intValue();
    }

    public HorizontalAlignment AnchorHorizontalAbstract() {
        return this.anchorHAlign;
    }

    public void AnchorHorizontalAbstract(HorizontalAlignment alignment) {
        if (alignment != this.anchorHAlign) {
            this.anchorHAlign = alignment;
            this.map.getController().updateFeaturePosition(this);
        }
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_MONOSPACE, editorType = PropertyTypeConstants.PROPERTY_TYPE_VERTICAL_ALIGNMENT)
    public void AnchorVertical(@Options(VerticalAlignment.class) int vertical) {
        VerticalAlignment alignment = VerticalAlignment.fromUnderlyingValue(Integer.valueOf(vertical));
        if (alignment == null) {
            this.container.$form().dispatchErrorOccurredEvent(this, "AnchorVertical", ErrorMessages.ERROR_INVALID_ANCHOR_VERTICAL, Integer.valueOf(vertical));
        } else {
            AnchorVerticalAbstract(alignment);
        }
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty(description = "The vertical alignment property controls where the Marker's anchor is located relative to its height. The choices are: 1 = aligned at the top, 2 = vertically centered, 3 = aligned at the bottom.")
    @Options(VerticalAlignment.class)
    public int AnchorVertical() {
        return AnchorVerticalAbstract().toUnderlyingValue().intValue();
    }

    public VerticalAlignment AnchorVerticalAbstract() {
        return this.anchorVAlign;
    }

    public void AnchorVerticalAbstract(VerticalAlignment alignment) {
        if (alignment != null) {
            this.anchorVAlign = alignment;
            this.map.getController().updateFeaturePosition(this);
        }
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty(userVisible = JSONzip.probe)
    public void ShowShadow(boolean show) {
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty(description = "Gets whether or not the shadow of the Marker is shown.")
    public boolean ShowShadow() {
        return false;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    public void Width(int width) {
        this.width = width;
        setNeedsUpdate();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    public int Width() {
        if (this.width == -2) {
            return this.map.getView().getWidth();
        }
        if (this.width < -1000) {
            return (int) ((((-this.width) - 1000.0d) / 100.0d) * this.map.getView().getWidth());
        }
        return this.width;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void WidthPercent(int pCent) {
        this.width = (-1000) - pCent;
        setNeedsUpdate();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    public void Height(int height) {
        this.height = height;
        setNeedsUpdate();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleProperty
    public int Height() {
        if (this.height == -2) {
            return this.map.getView().getHeight();
        }
        if (this.height < -1000) {
            return (int) ((((-this.height) - 1000.0d) / 100.0d) * this.map.getView().getHeight());
        }
        return this.height;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void HeightPercent(int pCent) {
        this.height = (-1000) - pCent;
        setNeedsUpdate();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    @SimpleFunction(description = "Set the location of the marker.")
    public void SetLocation(double latitude, double longitude) {
        Log.d(TAG, "SetLocation");
        this.location.setCoords(latitude, longitude);
        clearGeometry();
        this.map.getController().updateFeaturePosition(this);
    }

    @Override // com.google.appinventor.components.runtime.MapFeatureBase
    public double DistanceToPoint(double latitude, double longitude, boolean centroid) {
        return DistanceToPoint(latitude, longitude);
    }

    @SimpleFunction(description = "Compute the distance, in meters, between a Marker and a latitude, longitude point.")
    public double DistanceToPoint(double latitude, double longitude) {
        return GeometryUtil.distanceBetween(this, new GeoPoint(latitude, longitude));
    }

    @SimpleFunction(description = "Returns the bearing from the Marker to the given latitude and longitude, in degrees from due north.")
    public double BearingToPoint(double latitude, double longitude) {
        return this.location.bearingTo(new GeoPoint(latitude, longitude));
    }

    @SimpleFunction(description = "Returns the bearing from the Marker to the given map feature, in degrees from due north. If the centroids parameter is true, the bearing will be to the center of the map feature. Otherwise, the bearing will be computed to the point in the feature nearest the Marker.")
    public double BearingToFeature(MapFactory.MapFeature mapFeature, boolean centroids) {
        if (mapFeature == null) {
            return -1.0d;
        }
        return ((Double) mapFeature.accept(bearingComputation, this, Boolean.valueOf(centroids))).doubleValue();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    public IGeoPoint getLocation() {
        return this.location;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapMarker
    public void updateLocation(double latitude, double longitude) {
        this.location = new GeoPoint(latitude, longitude);
        clearGeometry();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    public <T> T accept(MapFactory.MapFeatureVisitor<T> visitor, Object... arguments) {
        return visitor.visit(this, arguments);
    }

    @Override // com.google.appinventor.components.runtime.MapFeatureBase
    protected Geometry computeGeometry() {
        return GeometryUtil.createGeometry(this.location);
    }

    private void setNeedsUpdate() {
        synchronized (this) {
            if (!this.pendingUpdate) {
                this.pendingUpdate = true;
                this.handler.postDelayed(new Runnable() { // from class: com.google.appinventor.components.runtime.Marker.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Marker.this.map.getController().updateFeatureImage(Marker.this);
                        synchronized (Marker.this) {
                            Marker.this.pendingUpdate = false;
                        }
                    }
                }, 1L);
            }
        }
    }
}
