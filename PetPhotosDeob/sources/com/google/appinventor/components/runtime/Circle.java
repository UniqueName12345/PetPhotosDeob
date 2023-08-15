package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.MapFeature;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.MapFactory;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.util.GeoPoint;

@DesignerComponent(category = ComponentCategory.MAPS, description = MapFactory.MapFeatureType.TYPE_CIRCLE, version = 2)
@SimpleObject
/* loaded from: classes.dex */
public class Circle extends PolygonBase implements MapFactory.MapCircle {
    private static final MapFactory.MapFeatureVisitor<Double> distanceComputation = new MapFactory.MapFeatureVisitor<Double>() { // from class: com.google.appinventor.components.runtime.Circle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapMarker marker, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(marker, (Circle) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(marker, (Circle) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapLineString lineString, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(lineString, (Circle) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(lineString, (Circle) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapPolygon polygon, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(polygon, (Circle) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(polygon, (Circle) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapCircle circle, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(circle, (Circle) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(circle, (Circle) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapRectangle rectangle, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids((Circle) arguments[0], rectangle)) : Double.valueOf(GeometryUtil.distanceBetweenEdges((Circle) arguments[0], rectangle));
        }
    };
    private GeoPoint center;
    private double latitude;
    private double longitude;
    private double radius;

    public Circle(MapFactory.MapFeatureContainer container) {
        super(container, distanceComputation);
        this.center = new GeoPoint(0.0d, 0.0d);
        container.addFeature(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty(description = "Returns the type of the feature. For Circles, this returns MapFeature.Circle (\"Circle\").")
    @Options(MapFeature.class)
    public String Type() {
        return TypeAbstract().toUnderlyingValue();
    }

    public MapFeature TypeAbstract() {
        return MapFeature.Circle;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapCircle
    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_FLOAT)
    public void Radius(double radius) {
        this.radius = radius;
        clearGeometry();
        this.map.getController().updateFeaturePosition(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapCircle
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The radius of the circle in meters.")
    public double Radius() {
        return this.radius;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapCircle
    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_LATITUDE)
    public void Latitude(double latitude) {
        if (GeometryUtil.isValidLatitude(latitude)) {
            this.latitude = latitude;
            this.center.setLatitude(latitude);
            clearGeometry();
            this.map.getController().updateFeaturePosition(this);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "Latitude", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapCircle
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The latitude of the center of the circle.")
    public double Latitude() {
        return this.latitude;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapCircle
    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_LONGITUDE)
    public void Longitude(double longitude) {
        if (GeometryUtil.isValidLongitude(longitude)) {
            this.longitude = longitude;
            this.center.setLongitude(longitude);
            clearGeometry();
            this.map.getController().updateFeaturePosition(this);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "Longitude", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapCircle
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The longitude of the center of the circle.")
    public double Longitude() {
        return this.longitude;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapCircle
    @SimpleFunction(description = "Set the center of the Circle.")
    public void SetLocation(double latitude, double longitude) {
        if (!GeometryUtil.isValidLatitude(latitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetLocation", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
        } else if (!GeometryUtil.isValidLongitude(longitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetLocation", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
        } else {
            this.latitude = latitude;
            this.longitude = longitude;
            this.center.setLatitude(latitude);
            this.center.setLongitude(longitude);
            clearGeometry();
            this.map.getController().updateFeaturePosition(this);
        }
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    public <T> T accept(MapFactory.MapFeatureVisitor<T> visitor, Object... arguments) {
        return visitor.visit(this, arguments);
    }

    @Override // com.google.appinventor.components.runtime.MapFeatureBase
    protected Geometry computeGeometry() {
        return GeometryUtil.createGeometry(this.center);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapCircle
    public void updateCenter(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        clearGeometry();
    }
}
