package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.TransportMethod;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.GeoJSONUtil;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.MapFactory;
import com.google.appinventor.components.runtime.util.YailDictionary;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import kawa.lang.SyntaxForms;
import org.json.JSONException;
import org.osmdroid.util.GeoPoint;

@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
@DesignerComponent(category = ComponentCategory.MAPS, description = Navigation.TAG, iconName = "images/navigation.png", nonVisible = SyntaxForms.DEBUGGING, version = 2)
@UsesLibraries({"osmdroid.jar"})
/* loaded from: classes.dex */
public class Navigation extends AndroidNonvisibleComponent implements Component {
    public static final String OPEN_ROUTE_SERVICE_URL = "https://api.openrouteservice.org/v2/directions/";
    private static final String TAG = "Navigation";
    private String apiKey;
    private GeoPoint endLocation;
    private String language;
    private YailDictionary lastResponse;
    private TransportMethod method;
    private String serviceUrl;
    private GeoPoint startLocation;

    public Navigation(ComponentContainer container) {
        super(container.$form());
        this.serviceUrl = OPEN_ROUTE_SERVICE_URL;
        this.language = "en";
        this.lastResponse = YailDictionary.makeDictionary();
        this.apiKey = "";
        this.startLocation = new GeoPoint(0.0d, 0.0d);
        this.endLocation = new GeoPoint(0.0d, 0.0d);
        this.method = TransportMethod.Foot;
    }

    @SimpleFunction(description = "Request directions from the routing service.")
    public void RequestDirections() {
        if (this.apiKey.equals("")) {
            this.form.dispatchErrorOccurredEvent(this, "Authorization", ErrorMessages.ERROR_INVALID_API_KEY, new Object[0]);
            return;
        }
        final GeoPoint startLocation = this.startLocation;
        final GeoPoint endLocation = this.endLocation;
        final TransportMethod method = this.method;
        AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.Navigation.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Navigation.this.performRequest(startLocation, endLocation, method);
                } catch (IOException e) {
                    Navigation.this.form.dispatchErrorOccurredEvent(Navigation.this, "RequestDirections", 0, new Object[0]);
                } catch (JSONException e2) {
                    Navigation.this.form.dispatchErrorOccurredEvent(Navigation.this, "RequestDirections", 0, new Object[0]);
                }
            }
        });
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    public void ServiceURL(String url) {
        this.serviceUrl = url;
    }

    @SimpleProperty(description = "API Key for Open Route Service.")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ApiKey(String key) {
        this.apiKey = key;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "0.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_LATITUDE)
    public void StartLatitude(double latitude) {
        if (GeometryUtil.isValidLatitude(latitude)) {
            this.startLocation.setLatitude(latitude);
        } else {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "StartLatitude", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The latitude of the start location.")
    public double StartLatitude() {
        return this.startLocation.getLatitude();
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "0.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_LONGITUDE)
    public void StartLongitude(double longitude) {
        if (GeometryUtil.isValidLongitude(longitude)) {
            this.startLocation.setLongitude(longitude);
        } else {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "StartLongitude", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The longitude of the start location.")
    public double StartLongitude() {
        return this.startLocation.getLongitude();
    }

    @SimpleProperty(description = "Set the start location.")
    public void StartLocation(MapFactory.MapFeature feature) {
        GeoPoint point = feature.getCentroid();
        double latitude = point.getLatitude();
        double longitude = point.getLongitude();
        if (!GeometryUtil.isValidLatitude(latitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetStartLocation", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
        } else if (!GeometryUtil.isValidLongitude(longitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetStartLocation", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
        } else {
            this.startLocation.setCoords(latitude, longitude);
        }
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "0.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_LATITUDE)
    public void EndLatitude(double latitude) {
        if (GeometryUtil.isValidLatitude(latitude)) {
            this.endLocation.setLatitude(latitude);
        } else {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "EndLatitude", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The latitude of the end location.")
    public double EndLatitude() {
        return this.endLocation.getLatitude();
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "0.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_LONGITUDE)
    public void EndLongitude(double longitude) {
        if (GeometryUtil.isValidLongitude(longitude)) {
            this.endLocation.setLongitude(longitude);
        } else {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "EndLongitude", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The longitude of the end location.")
    public double EndLongitude() {
        return this.endLocation.getLongitude();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    @Options(TransportMethod.class)
    public String TransportationMethod() {
        return TransportationMethodAbstract().toUnderlyingValue();
    }

    public TransportMethod TransportationMethodAbstract() {
        return this.method;
    }

    public void TransportationMethodAbstract(TransportMethod method) {
        this.method = method;
    }

    @SimpleProperty(description = "The transportation method used for determining the route.")
    @DesignerProperty(defaultValue = "foot-walking", editorType = PropertyTypeConstants.PROPERTY_TYPE_NAVIGATION_METHOD)
    public void TransportationMethod(@Options(TransportMethod.class) String method) {
        TransportMethod t = TransportMethod.fromUnderlyingValue(method);
        if (t != null) {
            TransportationMethodAbstract(t);
        }
    }

    @SimpleProperty(description = "Set the end location.")
    public void EndLocation(MapFactory.MapFeature feature) {
        GeoPoint point = feature.getCentroid();
        double latitude = point.getLatitude();
        double longitude = point.getLongitude();
        if (!GeometryUtil.isValidLatitude(latitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetEndLocation", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
        } else if (!GeometryUtil.isValidLongitude(longitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetEndLocation", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
        } else {
            this.endLocation.setCoords(latitude, longitude);
        }
    }

    @SimpleProperty(description = "The language to use for textual directions.")
    @DesignerProperty(defaultValue = "en")
    public void Language(String language) {
        this.language = language;
    }

    @SimpleProperty
    public String Language() {
        return this.language;
    }

    @SimpleProperty(description = "Content of the last response as a dictionary.")
    public YailDictionary ResponseContent() {
        return this.lastResponse;
    }

    @SimpleEvent(description = "Event triggered when the Openrouteservice returns the directions.")
    public void GotDirections(YailList directions, YailList points, double distance, double duration) {
        Log.d(TAG, "GotDirections");
        EventDispatcher.dispatchEvent(this, "GotDirections", directions, points, Double.valueOf(distance), Double.valueOf(duration));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performRequest(GeoPoint start, GeoPoint end, TransportMethod method) throws IOException, JSONException {
        String finalUrl = this.serviceUrl + method.toUnderlyingValue() + "/geojson/";
        URL url = new URL(finalUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", this.apiKey);
        try {
            String coords = "{\"coordinates\": " + JsonUtil.getJsonRepresentation(getCoordinates(start, end)) + ", \"language\": \"" + this.language + "\"}";
            byte[] postData = coords.getBytes("UTF-8");
            connection.setFixedLengthStreamingMode(postData.length);
            BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
            try {
                out.write(postData, 0, postData.length);
                out.flush();
                out.close();
                if (connection.getResponseCode() != 200) {
                    this.form.dispatchErrorOccurredEvent(this, "RequestDirections", ErrorMessages.ERROR_ROUTING_SERVICE_ERROR, Integer.valueOf(connection.getResponseCode()), connection.getResponseMessage());
                    return;
                }
                String geoJson = getResponseContent(connection);
                Log.d(TAG, geoJson);
                final YailDictionary response = (YailDictionary) JsonUtil.getObjectFromJson(geoJson, true);
                YailList features = (YailList) response.get("features");
                if (features.size() > 0) {
                    YailDictionary feature = (YailDictionary) features.getObject(0);
                    YailDictionary summary = (YailDictionary) feature.getObjectAtKeyPath(Arrays.asList("properties", "summary"));
                    final double distance = ((Double) summary.get("distance")).doubleValue();
                    final double duration = ((Double) summary.get("duration")).doubleValue();
                    final YailList directions = YailList.makeList((List) getDirections(feature));
                    final YailList coordinates = getLineStringCoords(feature);
                    this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.Navigation.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Navigation.this.lastResponse = response;
                            Navigation.this.GotDirections(directions, coordinates, distance, duration);
                        }
                    });
                } else {
                    this.form.dispatchErrorOccurredEvent(this, "RequestDirections", ErrorMessages.ERROR_NO_ROUTE_FOUND, new Object[0]);
                }
            } catch (Throwable th) {
                out.close();
                throw th;
            }
        } catch (Exception e) {
            this.form.dispatchErrorOccurredEvent(this, "RequestDirections", ErrorMessages.ERROR_UNABLE_TO_REQUEST_DIRECTIONS, e.getMessage());
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

    private static String getResponseContent(HttpURLConnection connection) throws IOException {
        String encoding = connection.getContentEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        Log.d(TAG, Integer.toString(connection.getResponseCode()));
        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), encoding);
        try {
            int contentLength = connection.getContentLength();
            StringBuilder sb = contentLength != -1 ? new StringBuilder(contentLength) : new StringBuilder();
            char[] buf = new char[1024];
            while (true) {
                int read = reader.read(buf);
                if (read != -1) {
                    sb.append(buf, 0, read);
                } else {
                    return sb.toString();
                }
            }
        } finally {
            reader.close();
        }
    }

    private Double[][] getCoordinates(GeoPoint startLocation, GeoPoint endLocation) {
        Double[][] coords = (Double[][]) Array.newInstance(Double.class, 2, 2);
        coords[0][0] = Double.valueOf(startLocation.getLongitude());
        coords[0][1] = Double.valueOf(startLocation.getLatitude());
        coords[1][0] = Double.valueOf(endLocation.getLongitude());
        coords[1][1] = Double.valueOf(endLocation.getLatitude());
        return coords;
    }

    private YailList getLineStringCoords(YailDictionary feature) {
        YailList coords = (YailList) feature.getObjectAtKeyPath(Arrays.asList("geometry", "coordinates"));
        return GeoJSONUtil.swapCoordinates(coords);
    }

    private List<?> getDirections(YailDictionary feature) {
        return YailDictionary.walkKeyPath(feature, Arrays.asList("properties", "segments", YailDictionary.ALL, "steps", YailDictionary.ALL, "instruction"));
    }
}
