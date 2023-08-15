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
import com.google.appinventor.components.runtime.util.YailList;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

@DesignerComponent(category = ComponentCategory.MAPS, description = MapFactory.MapFeatureType.TYPE_RECTANGLE, version = 2)
@SimpleObject
/* loaded from: classes.dex */
public class Rectangle extends PolygonBase implements MapFactory.MapRectangle {
    private static final MapFactory.MapFeatureVisitor<Double> distanceComputation = new MapFactory.MapFeatureVisitor<Double>() { // from class: com.google.appinventor.components.runtime.Rectangle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapMarker marker, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(marker, (Rectangle) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(marker, (Rectangle) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapLineString lineString, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(lineString, (Rectangle) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(lineString, (Rectangle) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapPolygon polygon, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(polygon, (Rectangle) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(polygon, (Rectangle) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapCircle circle, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(circle, (Rectangle) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(circle, (Rectangle) arguments[0]));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Double visit(MapFactory.MapRectangle rectangle, Object... arguments) {
            return ((Boolean) arguments[1]).booleanValue() ? Double.valueOf(GeometryUtil.distanceBetweenCentroids(rectangle, (Rectangle) arguments[0])) : Double.valueOf(GeometryUtil.distanceBetweenEdges(rectangle, (Rectangle) arguments[0]));
        }
    };
    private double east;
    private double north;
    private double south;
    private double west;

    public Rectangle(MapFactory.MapFeatureContainer container) {
        super(container, distanceComputation);
        this.east = 0.0d;
        this.west = 0.0d;
        this.north = 0.0d;
        this.south = 0.0d;
        container.addFeature(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeature
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the type of the feature. For rectangles, this returns MapFeature.Rectangle (\"Rectangle\").")
    @Options(MapFeature.class)
    public String Type() {
        return TypeAbstract().toUnderlyingValue();
    }

    public MapFeature TypeAbstract() {
        return MapFeature.Rectangle;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The east edge of the rectangle, in decimal degrees east of the prime meridian.")
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_FLOAT)
    public void EastLongitude(double east) {
        this.east = east;
        clearGeometry();
        this.map.getController().updateFeaturePosition(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleProperty
    public double EastLongitude() {
        return this.east;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The north edge of the rectangle, in decimal degrees north of the equator.")
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_FLOAT)
    public void NorthLatitude(double north) {
        this.north = north;
        clearGeometry();
        this.map.getController().updateFeaturePosition(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleProperty
    public double NorthLatitude() {
        return this.north;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The south edge of the rectangle, in decimal degrees north of the equator.")
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_FLOAT)
    public void SouthLatitude(double south) {
        this.south = south;
        clearGeometry();
        this.map.getController().updateFeaturePosition(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleProperty
    public double SouthLatitude() {
        return this.south;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The west edge of the rectangle, in decimal degrees east of the equator.")
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_FLOAT)
    public void WestLongitude(double west) {
        this.west = west;
        clearGeometry();
        this.map.getController().updateFeaturePosition(this);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleProperty
    public double WestLongitude() {
        return this.west;
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleFunction(description = "Returns the center of the Rectangle as a list of the form (Latitude Longitude).")
    public YailList Center() {
        return GeometryUtil.asYailList(getCentroid());
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleFunction(description = "Returns the bounding box of the Rectangle in the format ((North West) (South East)).")
    public YailList Bounds() {
        YailList nw = YailList.makeList(new Double[]{Double.valueOf(this.north), Double.valueOf(this.west)});
        YailList se = YailList.makeList(new Double[]{Double.valueOf(this.south), Double.valueOf(this.east)});
        return YailList.makeList(new YailList[]{nw, se});
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    @SimpleFunction(description = "Moves the Rectangle so that it is centered on the given latitude and longitude while attempting to maintain the width and height of the Rectangle as measured from the center to the edges.")
    public void SetCenter(double latitude, double longitude) {
        if (latitude < -90.0d || latitude > 90.0d) {
            this.container.$form().dispatchErrorOccurredEvent(this, "SetCenter", ErrorMessages.ERROR_INVALID_POINT, Double.valueOf(latitude), Double.valueOf(longitude));
        } else if (longitude < -180.0d || longitude > 180.0d) {
            this.container.$form().dispatchErrorOccurredEvent(this, "SetCenter", ErrorMessages.ERROR_INVALID_POINT, Double.valueOf(latitude), Double.valueOf(longitude));
        } else {
            GeoPoint currentCenter = getCentroid();
            GeoPoint northPoint = new GeoPoint(this.north, currentCenter.getLongitude());
            GeoPoint southPoint = new GeoPoint(this.south, currentCenter.getLongitude());
            GeoPoint eastPoint = new GeoPoint(currentCenter.getLatitude(), this.east);
            GeoPoint westPoint = new GeoPoint(currentCenter.getLatitude(), this.west);
            double latExtent2 = GeometryUtil.distanceBetween((IGeoPoint) northPoint, (IGeoPoint) southPoint) / 2.0d;
            double longExtent2 = GeometryUtil.distanceBetween((IGeoPoint) eastPoint, (IGeoPoint) westPoint) / 2.0d;
            currentCenter.setCoords(latitude, longitude);
            this.north = currentCenter.destinationPoint(latExtent2, 0.0f).getLatitude();
            this.south = currentCenter.destinationPoint(latExtent2, 180.0f).getLatitude();
            this.east = currentCenter.destinationPoint(longExtent2, 90.0f).getLongitude();
            this.west = currentCenter.destinationPoint(longExtent2, 270.0f).getLongitude();
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
        return GeometryUtil.createGeometry(this.north, this.east, this.south, this.west);
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapRectangle
    public void updateBounds(double north, double west, double south, double east) {
        this.north = north;
        this.west = west;
        this.south = south;
        this.east = east;
        clearGeometry();
    }
}
