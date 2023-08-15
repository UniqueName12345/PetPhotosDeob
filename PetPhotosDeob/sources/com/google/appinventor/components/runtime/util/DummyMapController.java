package com.google.appinventor.components.runtime.util;

import android.view.View;
import com.google.appinventor.components.common.MapType;
import com.google.appinventor.components.common.ScaleUnits;
import com.google.appinventor.components.runtime.LocationSensor;
import com.google.appinventor.components.runtime.util.MapFactory;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class DummyMapController implements MapFactory.MapController {
    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public View getView() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public double getLatitude() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public double getLongitude() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setCenter(double latitude, double longitude) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setZoom(int zoom) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setRotation(float rotation) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public float getRotation() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public int getZoom() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setMapType(MapFactory.MapType type) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public MapFactory.MapType getMapType() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setMapTypeAbstract(MapType type) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public MapType getMapTypeAbstract() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setCompassEnabled(boolean enabled) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public boolean isCompassEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setZoomEnabled(boolean enabled) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public boolean isZoomEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setZoomControlEnabled(boolean enabled) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public boolean isZoomControlEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setShowUserEnabled(boolean enable) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public boolean isShowUserEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setRotationEnabled(boolean enable) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public boolean isRotationEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setPanEnabled(boolean enable) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public boolean isPanEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void panTo(double latitude, double longitude, int zoom, double seconds) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void addEventListener(MapFactory.MapEventListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void addFeature(MapFactory.MapMarker marker) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeaturePosition(MapFactory.MapMarker marker) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeatureFill(MapFactory.HasFill marker) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeatureImage(MapFactory.MapMarker marker) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeatureText(MapFactory.MapFeature marker) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeatureDraggable(MapFactory.MapFeature marker) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public org.osmdroid.util.BoundingBox getBoundingBox() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setBoundingBox(org.osmdroid.util.BoundingBox bbox) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void addFeature(MapFactory.MapLineString polyline) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void addFeature(MapFactory.MapPolygon polygon) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void addFeature(MapFactory.MapCircle circle) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void addFeature(MapFactory.MapRectangle circle) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void removeFeature(MapFactory.MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void showFeature(MapFactory.MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void hideFeature(MapFactory.MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public boolean isFeatureVisible(MapFactory.MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public boolean isFeatureCollectionVisible(MapFactory.MapFeatureCollection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setFeatureCollectionVisible(MapFactory.MapFeatureCollection collection, boolean visible) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void showInfobox(MapFactory.MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void hideInfobox(MapFactory.MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public boolean isInfoboxVisible(MapFactory.MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeaturePosition(MapFactory.MapLineString polyline) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeaturePosition(MapFactory.MapPolygon polygon) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeatureHoles(MapFactory.MapPolygon polygon) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeaturePosition(MapFactory.MapCircle circle) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeaturePosition(MapFactory.MapRectangle rectangle) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeatureStroke(MapFactory.HasStroke marker) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void updateFeatureSize(MapFactory.MapMarker marker) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public LocationSensor.LocationSensorListener getLocationListener() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public int getOverlayCount() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setScaleVisible(boolean show) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public boolean isScaleVisible() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setScaleUnits(MapFactory.MapScaleUnits units) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public MapFactory.MapScaleUnits getScaleUnits() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public void setScaleUnitsAbstract(ScaleUnits units) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.util.MapFactory.MapController
    public ScaleUnits getScaleUnitsAbstract() {
        throw new UnsupportedOperationException();
    }
}
