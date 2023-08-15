package com.google.appinventor.components.runtime.util;

import android.text.TextUtils;
import android.util.Log;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.LineString;
import com.google.appinventor.components.runtime.Marker;
import com.google.appinventor.components.runtime.Polygon;
import com.google.appinventor.components.runtime.util.MapFactory;
import com.google.common.annotations.VisibleForTesting;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

/* loaded from: classes.dex */
public final class GeoJSONUtil {
    private static final int ERROR_CODE_MALFORMED_GEOJSON = -3;
    private static final String ERROR_MALFORMED_GEOJSON = "Malformed GeoJSON response. Expected FeatureCollection as root element.";
    private static final String ERROR_UNKNOWN_TYPE = "Unrecognized/invalid type in JSON object";
    private static final String GEOJSON_COORDINATES = "coordinates";
    private static final String GEOJSON_FEATURE = "Feature";
    private static final String GEOJSON_FEATURECOLLECTION = "FeatureCollection";
    private static final String GEOJSON_FEATURES = "features";
    private static final String GEOJSON_GEOMETRY = "geometry";
    private static final String GEOJSON_GEOMETRYCOLLECTION = "GeometryCollection";
    private static final String GEOJSON_PROPERTIES = "properties";
    private static final String GEOJSON_TYPE = "type";
    private static final int KEY = 1;
    private static final int LATITUDE = 2;
    private static final int LONGITUDE = 1;
    private static final String PROPERTY_ANCHOR_HORIZONTAL = "anchorHorizontal";
    private static final String PROPERTY_ANCHOR_VERTICAL = "anchorVertical";
    private static final String PROPERTY_DESCRIPTION = "description";
    private static final String PROPERTY_DRAGGABLE = "draggable";
    private static final String PROPERTY_FILL = "fill";
    private static final String PROPERTY_FILL_OPACITY = "fill-opacity";
    private static final String PROPERTY_HEIGHT = "height";
    private static final String PROPERTY_IMAGE = "image";
    private static final String PROPERTY_INFOBOX = "infobox";
    private static final String PROPERTY_STROKE = "stroke";
    private static final String PROPERTY_STROKE_OPACITY = "stroke-opacity";
    private static final String PROPERTY_STROKE_WIDTH = "stroke-width";
    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_VISIBLE = "visible";
    private static final String PROPERTY_WIDTH = "width";
    private static final Map<String, PropertyApplication> SUPPORTED_PROPERTIES;
    private static final int VALUE = 2;
    private static final Map<String, Integer> colors = new HashMap();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface PropertyApplication {
        void apply(MapFactory.MapFeature mapFeature, Object obj);
    }

    static {
        colors.put("black", -16777216);
        colors.put("blue", Integer.valueOf((int) Component.COLOR_BLUE));
        colors.put("cyan", Integer.valueOf((int) Component.COLOR_CYAN));
        colors.put("darkgray", Integer.valueOf((int) Component.COLOR_DKGRAY));
        colors.put("gray", Integer.valueOf((int) Component.COLOR_GRAY));
        colors.put("green", Integer.valueOf((int) Component.COLOR_GREEN));
        colors.put("lightgray", Integer.valueOf((int) Component.COLOR_LTGRAY));
        colors.put("magenta", Integer.valueOf((int) Component.COLOR_MAGENTA));
        colors.put("orange", Integer.valueOf((int) Component.COLOR_ORANGE));
        colors.put("pink", Integer.valueOf((int) Component.COLOR_PINK));
        colors.put("red", -65536);
        colors.put("white", -1);
        colors.put("yellow", -256);
        SUPPORTED_PROPERTIES = new HashMap();
        SUPPORTED_PROPERTIES.put(PROPERTY_ANCHOR_HORIZONTAL.toLowerCase(), new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.1
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                if (feature instanceof MapFactory.MapMarker) {
                    ((MapFactory.MapMarker) feature).AnchorHorizontal(GeoJSONUtil.parseIntegerOrString(value));
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_ANCHOR_VERTICAL.toLowerCase(), new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.2
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                if (feature instanceof MapFactory.MapMarker) {
                    ((MapFactory.MapMarker) feature).AnchorHorizontal();
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_DESCRIPTION, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.3
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                feature.Description(value.toString());
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_DRAGGABLE, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.4
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                feature.Draggable(GeoJSONUtil.parseBooleanOrString(value));
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_FILL, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.5
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                if (feature instanceof MapFactory.HasFill) {
                    ((MapFactory.HasFill) feature).FillColor(value instanceof Number ? ((Number) value).intValue() : GeoJSONUtil.parseColor(value.toString()));
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_FILL_OPACITY, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.6
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                if (feature instanceof MapFactory.HasFill) {
                    ((MapFactory.HasFill) feature).FillOpacity(GeoJSONUtil.parseFloatOrString(value));
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_HEIGHT, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.7
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                if (feature instanceof MapFactory.MapMarker) {
                    ((MapFactory.MapMarker) feature).Height(GeoJSONUtil.parseIntegerOrString(value));
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_IMAGE, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.8
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                if (feature instanceof MapFactory.MapMarker) {
                    ((MapFactory.MapMarker) feature).ImageAsset(value.toString());
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_INFOBOX, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.9
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                feature.EnableInfobox(GeoJSONUtil.parseBooleanOrString(value));
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_STROKE, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.10
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                if (feature instanceof MapFactory.HasStroke) {
                    ((MapFactory.HasStroke) feature).StrokeColor(value instanceof Number ? ((Number) value).intValue() : GeoJSONUtil.parseColor(value.toString()));
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_STROKE_OPACITY, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.11
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                if (feature instanceof MapFactory.HasStroke) {
                    ((MapFactory.HasStroke) feature).StrokeOpacity(GeoJSONUtil.parseFloatOrString(value));
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_STROKE_WIDTH, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.12
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                if (feature instanceof MapFactory.HasStroke) {
                    ((MapFactory.HasStroke) feature).StrokeWidth(GeoJSONUtil.parseIntegerOrString(value));
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_TITLE, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.13
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                feature.Title(value.toString());
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_WIDTH, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.14
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                if (feature instanceof MapFactory.MapMarker) {
                    ((MapFactory.MapMarker) feature).Width(GeoJSONUtil.parseIntegerOrString(value));
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_VISIBLE, new PropertyApplication() { // from class: com.google.appinventor.components.runtime.util.GeoJSONUtil.15
            @Override // com.google.appinventor.components.runtime.util.GeoJSONUtil.PropertyApplication
            public void apply(MapFactory.MapFeature feature, Object value) {
                feature.Visible(GeoJSONUtil.parseBooleanOrString(value));
            }
        });
    }

    private GeoJSONUtil() {
    }

    @VisibleForTesting
    static int parseColor(String value) {
        String lcValue = value.toLowerCase();
        Integer result = colors.get(lcValue);
        if (result != null) {
            return result.intValue();
        }
        if (lcValue.startsWith("#")) {
            return parseColorHex(lcValue.substring(1));
        }
        if (lcValue.startsWith("&h")) {
            return parseColorHex(lcValue.substring(2));
        }
        return -65536;
    }

    @VisibleForTesting
    static int parseColorHex(String value) {
        int argb = 0;
        if (value.length() == 3) {
            argb = -16777216;
            for (int i = 0; i < value.length(); i++) {
                int hex = charToHex(value.charAt(i));
                argb |= ((hex << 4) | hex) << ((2 - i) * 8);
            }
        } else if (value.length() == 6) {
            argb = -16777216;
            for (int i2 = 0; i2 < 3; i2++) {
                int hex2 = (charToHex(value.charAt(i2 * 2)) << 4) | charToHex(value.charAt((i2 * 2) + 1));
                argb |= hex2 << ((2 - i2) * 8);
            }
        } else if (value.length() == 8) {
            for (int i3 = 0; i3 < 4; i3++) {
                int hex3 = (charToHex(value.charAt(i3 * 2)) << 4) | charToHex(value.charAt((i3 * 2) + 1));
                argb |= hex3 << ((3 - i3) * 8);
            }
        } else {
            throw new IllegalArgumentException();
        }
        return argb;
    }

    @VisibleForTesting
    static int charToHex(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('a' <= c && c <= 'f') {
            return (c - 'a') + 10;
        }
        if ('A' <= c && c <= 'F') {
            return (c - 'A') + 10;
        }
        throw new IllegalArgumentException("Invalid hex character. Expected [0-9A-Fa-f].");
    }

    public static MapFactory.MapFeature processGeoJSONFeature(String logTag, MapFactory.MapFeatureContainer container, YailList descriptions) {
        String type = null;
        YailList geometry = null;
        YailList properties = null;
        Iterator it = ((LList) descriptions.getCdr()).iterator();
        while (it.hasNext()) {
            Object o = it.next();
            YailList keyvalue = (YailList) o;
            String key = keyvalue.getString(0);
            Object value = keyvalue.getObject(1);
            if (GEOJSON_TYPE.equals(key)) {
                type = (String) value;
            } else if (GEOJSON_GEOMETRY.equals(key)) {
                geometry = (YailList) value;
            } else if (GEOJSON_PROPERTIES.equals(key)) {
                properties = (YailList) value;
            } else {
                Log.w(logTag, String.format("Unsupported field \"%s\" in JSON format", key));
            }
        }
        if (!GEOJSON_FEATURE.equals(type)) {
            throw new IllegalArgumentException(String.format("Unknown type \"%s\"", type));
        }
        if (geometry == null) {
            return null;
        }
        MapFactory.MapFeature feature = processGeometry(logTag, container, geometry);
        if (properties != null) {
            processProperties(logTag, feature, properties);
            return feature;
        }
        return feature;
    }

    private static MapFactory.MapFeature processGeometry(String logTag, MapFactory.MapFeatureContainer container, YailList geometry) {
        String type = null;
        YailList coordinates = null;
        Iterator it = ((LList) geometry.getCdr()).iterator();
        while (it.hasNext()) {
            Object o = it.next();
            YailList keyvalue = (YailList) o;
            String key = keyvalue.getString(0);
            Object value = keyvalue.getObject(1);
            if (GEOJSON_TYPE.equals(key)) {
                type = (String) value;
            } else if (GEOJSON_COORDINATES.equals(key)) {
                coordinates = (YailList) value;
            } else {
                Log.w(logTag, String.format("Unsupported field \"%s\" in JSON format", key));
            }
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("No coordinates found in GeoJSON Feature");
        }
        return processCoordinates(container, type, coordinates);
    }

    private static MapFactory.MapFeature processCoordinates(MapFactory.MapFeatureContainer container, String type, YailList coordinates) {
        if (MapFactory.MapFeatureType.TYPE_POINT.equals(type)) {
            return markerFromGeoJSON(container, coordinates);
        }
        if (MapFactory.MapFeatureType.TYPE_LINESTRING.equals(type)) {
            return lineStringFromGeoJSON(container, coordinates);
        }
        if (MapFactory.MapFeatureType.TYPE_POLYGON.equals(type)) {
            return polygonFromGeoJSON(container, coordinates);
        }
        if (MapFactory.MapFeatureType.TYPE_MULTIPOLYGON.equals(type)) {
            return multipolygonFromGeoJSON(container, coordinates);
        }
        throw new IllegalArgumentException();
    }

    private static MapFactory.MapMarker markerFromGeoJSON(MapFactory.MapFeatureContainer container, YailList coordinates) {
        if (coordinates.length() != 3) {
            throw new IllegalArgumentException("Invalid coordinate supplied in GeoJSON");
        }
        Marker marker = new Marker(container);
        marker.Latitude(((Number) coordinates.get(2)).doubleValue());
        marker.Longitude(((Number) coordinates.get(1)).doubleValue());
        return marker;
    }

    private static MapFactory.MapLineString lineStringFromGeoJSON(MapFactory.MapFeatureContainer container, YailList coordinates) {
        if (coordinates.size() < 2) {
            throw new IllegalArgumentException("Too few coordinates supplied in GeoJSON");
        }
        LineString lineString = new LineString(container);
        lineString.Points(swapCoordinates(coordinates));
        return lineString;
    }

    private static MapFactory.MapPolygon polygonFromGeoJSON(MapFactory.MapFeatureContainer container, YailList coordinates) {
        Polygon polygon = new Polygon(container);
        Iterator i = coordinates.iterator();
        i.next();
        polygon.Points(swapCoordinates((YailList) i.next()));
        if (i.hasNext()) {
            polygon.HolePoints(YailList.makeList((List) swapNestedCoordinates((LList) ((Pair) coordinates.getCdr()).getCdr())));
        }
        polygon.Initialize();
        return polygon;
    }

    private static MapFactory.MapPolygon multipolygonFromGeoJSON(MapFactory.MapFeatureContainer container, YailList coordinates) {
        Polygon polygon = new Polygon(container);
        List<YailList> points = new ArrayList<>();
        List<YailList> holePoints = new ArrayList<>();
        Iterator i = coordinates.iterator();
        i.next();
        while (i.hasNext()) {
            YailList list = (YailList) i.next();
            points.add(swapCoordinates((YailList) list.get(1)));
            holePoints.add(YailList.makeList((List) swapNestedCoordinates((LList) ((Pair) list.getCdr()).getCdr())));
        }
        polygon.Points(YailList.makeList((List) points));
        polygon.HolePoints(YailList.makeList((List) holePoints));
        polygon.Initialize();
        return polygon;
    }

    private static void processProperties(String logTag, MapFactory.MapFeature feature, YailList properties) {
        Iterator it = properties.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof YailList) {
                YailList pair = (YailList) o;
                String key = pair.get(1).toString();
                PropertyApplication application = SUPPORTED_PROPERTIES.get(key.toLowerCase());
                if (application != null) {
                    application.apply(feature, pair.get(2));
                } else {
                    Log.i(logTag, String.format("Ignoring GeoJSON property \"%s\"", key));
                }
            }
        }
    }

    @VisibleForTesting
    static boolean parseBooleanOrString(Object value) {
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        if (value instanceof String) {
            return ("false".equalsIgnoreCase((String) value) || ((String) value).length() == 0) ? false : true;
        } else if (value instanceof FString) {
            return parseBooleanOrString(value.toString());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @VisibleForTesting
    static int parseIntegerOrString(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        if (value instanceof FString) {
            return Integer.parseInt(value.toString());
        }
        throw new IllegalArgumentException();
    }

    @VisibleForTesting
    static float parseFloatOrString(Object value) {
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        if (value instanceof String) {
            return Float.parseFloat((String) value);
        }
        if (value instanceof FString) {
            return Float.parseFloat(value.toString());
        }
        throw new IllegalArgumentException();
    }

    public static List<YailList> getGeoJSONFeatures(String logTag, String content) throws JSONException {
        JSONObject parsedData = new JSONObject(stripBOM(content));
        JSONArray features = parsedData.getJSONArray(GEOJSON_FEATURES);
        List<YailList> yailFeatures = new ArrayList<>();
        for (int i = 0; i < features.length(); i++) {
            yailFeatures.add(jsonObjectToYail(logTag, features.getJSONObject(i)));
        }
        return yailFeatures;
    }

    public static String getGeoJSONType(String content, String geojsonType) throws JSONException {
        JSONObject parsedData = new JSONObject(stripBOM(content));
        String type = parsedData.optString(geojsonType);
        return type;
    }

    private static YailList jsonObjectToYail(String logTag, JSONObject object) throws JSONException {
        List<YailList> pairs = new ArrayList<>();
        Iterator<String> j = object.keys();
        while (j.hasNext()) {
            String key = j.next();
            Object value = object.get(key);
            if ((value instanceof Boolean) || (value instanceof Integer) || (value instanceof Long) || (value instanceof Double) || (value instanceof String)) {
                pairs.add(YailList.makeList(new Object[]{key, value}));
            } else if (value instanceof JSONArray) {
                pairs.add(YailList.makeList(new Object[]{key, jsonArrayToYail(logTag, (JSONArray) value)}));
            } else if (value instanceof JSONObject) {
                pairs.add(YailList.makeList(new Object[]{key, jsonObjectToYail(logTag, (JSONObject) value)}));
            } else if (!JSONObject.NULL.equals(value)) {
                Log.wtf(logTag, "Unrecognized/invalid type in JSON object: " + value.getClass());
                throw new IllegalArgumentException(ERROR_UNKNOWN_TYPE);
            }
        }
        return YailList.makeList((List) pairs);
    }

    private static YailList jsonArrayToYail(String logTag, JSONArray array) throws JSONException {
        List<Object> items = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if ((value instanceof Boolean) || (value instanceof Integer) || (value instanceof Long) || (value instanceof Double) || (value instanceof String)) {
                items.add(value);
            } else if (value instanceof JSONArray) {
                items.add(jsonArrayToYail(logTag, (JSONArray) value));
            } else if (value instanceof JSONObject) {
                items.add(jsonObjectToYail(logTag, (JSONObject) value));
            } else if (!JSONObject.NULL.equals(value)) {
                Log.wtf(logTag, "Unrecognized/invalid type in JSON object: " + value.getClass());
                throw new IllegalArgumentException(ERROR_UNKNOWN_TYPE);
            }
        }
        return YailList.makeList((List) items);
    }

    private static String stripBOM(String content) {
        if (content.charAt(0) == 65279) {
            return content.substring(1);
        }
        return content;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class FeatureWriter implements MapFactory.MapFeatureVisitor<Void> {
        private final PrintStream out;

        private FeatureWriter(PrintStream out) {
            this.out = out;
        }

        private void writeType(String type) {
            this.out.print("\"type\":\"");
            this.out.print(type);
            this.out.print("\"");
        }

        private void writeProperty(String property, Object value) {
            try {
                String result = JsonUtil.getJsonRepresentation(value);
                this.out.print(",\"");
                this.out.print(property);
                this.out.print("\":");
                this.out.print(result);
            } catch (JSONException e) {
                Log.w("GeoJSONUtil", "Unable to serialize the value of \"" + property + "\" as JSON", e);
            }
        }

        private void writeProperty(String property, String value) {
            if (value != null && !TextUtils.isEmpty(value)) {
                writeProperty(property, (Object) value);
            }
        }

        private void writeColorProperty(String property, int color) {
            this.out.print(",\"");
            this.out.print(property);
            this.out.print("\":\"&H");
            String unpadded = Integer.toHexString(color);
            for (int i = 8; i > unpadded.length(); i--) {
                this.out.print(Component.TYPEFACE_DEFAULT);
            }
            this.out.print(unpadded);
            this.out.print("\"");
        }

        private void writePointGeometry(GeoPoint point) {
            this.out.print("\"geometry\":{\"type\":\"Point\",\"coordinates\":[");
            this.out.print(point.getLongitude());
            this.out.print(",");
            this.out.print(point.getLatitude());
            if (hasAltitude(point)) {
                this.out.print(",");
                this.out.print(point.getAltitude());
            }
            this.out.print("]}");
        }

        private void writePropertiesHeader(String runtimeType) {
            this.out.print(",\"properties\":{\"$Type\":\"" + runtimeType + "\"");
        }

        private void writeProperties(MapFactory.MapFeature feature) {
            writeProperty(GeoJSONUtil.PROPERTY_DESCRIPTION, feature.Description());
            writeProperty(GeoJSONUtil.PROPERTY_DRAGGABLE, Boolean.valueOf(feature.Draggable()));
            writeProperty(GeoJSONUtil.PROPERTY_INFOBOX, Boolean.valueOf(feature.EnableInfobox()));
            writeProperty(GeoJSONUtil.PROPERTY_TITLE, feature.Title());
            writeProperty(GeoJSONUtil.PROPERTY_VISIBLE, Boolean.valueOf(feature.Visible()));
        }

        private void writeProperties(MapFactory.HasStroke feature) {
            writeColorProperty(GeoJSONUtil.PROPERTY_STROKE, feature.StrokeColor());
            writeProperty(GeoJSONUtil.PROPERTY_STROKE_OPACITY, Float.valueOf(feature.StrokeOpacity()));
            writeProperty(GeoJSONUtil.PROPERTY_STROKE_WIDTH, Integer.valueOf(feature.StrokeWidth()));
        }

        private void writeProperties(MapFactory.HasFill feature) {
            writeColorProperty(GeoJSONUtil.PROPERTY_FILL, feature.FillColor());
            writeProperty(GeoJSONUtil.PROPERTY_FILL_OPACITY, Float.valueOf(feature.FillOpacity()));
        }

        private void writePoints(List<GeoPoint> points) {
            boolean first = true;
            for (GeoPoint p : points) {
                if (!first) {
                    this.out.print(',');
                }
                this.out.print("[");
                this.out.print(p.getLongitude());
                this.out.print(",");
                this.out.print(p.getLatitude());
                if (hasAltitude(p)) {
                    this.out.print(",");
                    this.out.print(p.getAltitude());
                }
                this.out.print("]");
                first = false;
            }
        }

        private void writeLineGeometry(MapFactory.MapLineString lineString) {
            this.out.print("\"geometry\":{\"type\":\"LineString\",\"coordinates\":[");
            writePoints(lineString.getPoints());
            this.out.print("]}");
        }

        private void writeMultipolygonGeometryNoHoles(MapFactory.MapPolygon polygon) {
            this.out.print("\"geometry\":{\"type\":\"MultiPolygon\",\"coordinates\":[");
            Iterator<List<List<GeoPoint>>> holePointIterator = polygon.getHolePoints().iterator();
            boolean first = true;
            for (List<GeoPoint> list : polygon.getPoints()) {
                if (!first) {
                    this.out.print(",");
                }
                this.out.print("[");
                writePoints(list);
                if (holePointIterator.hasNext()) {
                    for (List<GeoPoint> holePoints : holePointIterator.next()) {
                        this.out.print(",");
                        writePoints(holePoints);
                    }
                }
                this.out.print("]");
                first = false;
            }
            this.out.print("]}");
        }

        private void writePolygonGeometryNoHoles(MapFactory.MapPolygon polygon) {
            this.out.print("\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[");
            writePoints(polygon.getPoints().get(0));
            if (!polygon.getHolePoints().isEmpty()) {
                for (List<GeoPoint> points : polygon.getHolePoints().get(0)) {
                    this.out.print(",");
                    writePoints(points);
                }
            }
            this.out.print("]}");
        }

        private void writePolygonGeometry(MapFactory.MapPolygon polygon) {
            if (polygon.getPoints().size() > 1) {
                writeMultipolygonGeometryNoHoles(polygon);
            } else {
                writePolygonGeometryNoHoles(polygon);
            }
        }

        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Void visit(MapFactory.MapMarker marker, Object... arguments) {
            this.out.print("{");
            writeType(GeoJSONUtil.GEOJSON_FEATURE);
            this.out.print(',');
            writePointGeometry(marker.getCentroid());
            writePropertiesHeader(marker.getClass().getName());
            writeProperties((MapFactory.MapFeature) marker);
            writeProperties((MapFactory.HasStroke) marker);
            writeProperties((MapFactory.HasFill) marker);
            writeProperty(GeoJSONUtil.PROPERTY_ANCHOR_HORIZONTAL, Integer.valueOf(marker.AnchorHorizontal()));
            writeProperty(GeoJSONUtil.PROPERTY_ANCHOR_VERTICAL, Integer.valueOf(marker.AnchorVertical()));
            writeProperty(GeoJSONUtil.PROPERTY_HEIGHT, Integer.valueOf(marker.Height()));
            writeProperty(GeoJSONUtil.PROPERTY_IMAGE, marker.ImageAsset());
            writeProperty(GeoJSONUtil.PROPERTY_WIDTH, Integer.valueOf(marker.Width()));
            this.out.print("}}");
            return null;
        }

        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Void visit(MapFactory.MapLineString lineString, Object... arguments) {
            this.out.print("{");
            writeType(GeoJSONUtil.GEOJSON_FEATURE);
            this.out.print(',');
            writeLineGeometry(lineString);
            writePropertiesHeader(lineString.getClass().getName());
            writeProperties((MapFactory.MapFeature) lineString);
            writeProperties((MapFactory.HasStroke) lineString);
            this.out.print("}}");
            return null;
        }

        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Void visit(MapFactory.MapPolygon polygon, Object... arguments) {
            this.out.print("{");
            writeType(GeoJSONUtil.GEOJSON_FEATURE);
            this.out.print(',');
            writePolygonGeometry(polygon);
            writePropertiesHeader(polygon.getClass().getName());
            writeProperties((MapFactory.MapFeature) polygon);
            writeProperties((MapFactory.HasStroke) polygon);
            writeProperties((MapFactory.HasFill) polygon);
            this.out.print("}}");
            return null;
        }

        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Void visit(MapFactory.MapCircle circle, Object... arguments) {
            this.out.print("{");
            writeType(GeoJSONUtil.GEOJSON_FEATURE);
            this.out.print(',');
            writePointGeometry(circle.getCentroid());
            writePropertiesHeader(circle.getClass().getName());
            writeProperties((MapFactory.MapFeature) circle);
            writeProperties((MapFactory.HasStroke) circle);
            writeProperties((MapFactory.HasFill) circle);
            this.out.print("}}");
            return null;
        }

        @Override // com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor
        public Void visit(MapFactory.MapRectangle rectangle, Object... arguments) {
            this.out.print("{");
            writeType(GeoJSONUtil.GEOJSON_FEATURE);
            this.out.print(",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[");
            this.out.print("[" + rectangle.WestLongitude() + "," + rectangle.NorthLatitude() + "],");
            this.out.print("[" + rectangle.WestLongitude() + "," + rectangle.SouthLatitude() + "],");
            this.out.print("[" + rectangle.EastLongitude() + "," + rectangle.SouthLatitude() + "],");
            this.out.print("[" + rectangle.EastLongitude() + "," + rectangle.NorthLatitude() + "],");
            this.out.print("[" + rectangle.WestLongitude() + "," + rectangle.NorthLatitude() + "]]}");
            writePropertiesHeader(rectangle.getClass().getName());
            writeProperties((MapFactory.MapFeature) rectangle);
            writeProperties((MapFactory.HasStroke) rectangle);
            writeProperties((MapFactory.HasFill) rectangle);
            writeProperty("NorthLatitude", Double.valueOf(rectangle.NorthLatitude()));
            writeProperty("WestLongitude", Double.valueOf(rectangle.WestLongitude()));
            writeProperty("SouthLatitude", Double.valueOf(rectangle.SouthLatitude()));
            writeProperty("EastLongitude", Double.valueOf(rectangle.EastLongitude()));
            this.out.print("}}");
            return null;
        }

        private static boolean hasAltitude(GeoPoint point) {
            return Double.compare(0.0d, point.getAltitude()) != 0;
        }
    }

    public static void writeFeaturesAsGeoJSON(List<MapFactory.MapFeature> featuresToSave, String path) throws IOException {
        PrintStream out;
        PrintStream out2 = null;
        try {
            out = new PrintStream(new FileOutputStream(path));
        } catch (Throwable th) {
            th = th;
        }
        try {
            FeatureWriter writer = new FeatureWriter(out);
            out.print("{\"type\": \"FeatureCollection\", \"features\":[");
            Iterator<MapFactory.MapFeature> it = featuresToSave.iterator();
            if (it.hasNext()) {
                MapFactory.MapFeature feature = it.next();
                feature.accept(writer, new Object[0]);
                while (it.hasNext()) {
                    MapFactory.MapFeature feature2 = it.next();
                    out.print(',');
                    feature2.accept(writer, new Object[0]);
                }
            }
            out.print("]}");
            IOUtils.closeQuietly("GeoJSONUtil", out);
        } catch (Throwable th2) {
            th = th2;
            out2 = out;
            IOUtils.closeQuietly("GeoJSONUtil", out2);
            throw th;
        }
    }

    public static YailList swapCoordinates(YailList coordinates) {
        Iterator i = coordinates.iterator();
        i.next();
        while (i.hasNext()) {
            YailList coordinate = (YailList) i.next();
            Object temp = coordinate.get(1);
            Pair p = (Pair) coordinate.getCdr();
            p.setCar(coordinate.get(2));
            ((Pair) p.getCdr()).setCar(temp);
        }
        return coordinates;
    }

    public static <E> List<List<E>> swapCoordinates2(List<List<E>> coordinates) {
        for (List<E> point : coordinates) {
            E temp = point.get(0);
            point.set(0, point.get(1));
            point.set(1, temp);
        }
        return coordinates;
    }

    public static LList swapNestedCoordinates(LList coordinates) {
        for (LList it = coordinates; !it.isEmpty(); it = (LList) ((Pair) it).getCdr()) {
            swapCoordinates((YailList) it.get(0));
        }
        return coordinates;
    }
}
