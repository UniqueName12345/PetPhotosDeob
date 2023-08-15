package com.google.appinventor.components.runtime.util;

import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class WebServiceUtil {
    private static final String LOG_TAG = "WebServiceUtil";
    private static final WebServiceUtil INSTANCE = new WebServiceUtil();
    private static HttpClient httpClient = null;
    private static Object httpClientSynchronizer = new Object();

    private WebServiceUtil() {
    }

    public static WebServiceUtil getInstance() {
        synchronized (httpClientSynchronizer) {
            if (httpClient == null) {
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                BasicHttpParams params = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params, 20000);
                HttpConnectionParams.setSoTimeout(params, 20000);
                ConnManagerParams.setMaxTotalConnections(params, 20);
                ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(params, schemeRegistry);
                httpClient = new DefaultHttpClient(manager, params);
            }
        }
        return INSTANCE;
    }

    public void postCommandReturningArray(String serviceURL, String commandName, List<NameValuePair> params, final AsyncCallbackPair<JSONArray> callback) {
        AsyncCallbackPair<String> thisCallback = new AsyncCallbackPair<String>() { // from class: com.google.appinventor.components.runtime.util.WebServiceUtil.1
            @Override // com.google.appinventor.components.runtime.util.AsyncCallbackPair
            public void onSuccess(String httpResponseString) {
                try {
                    callback.onSuccess(new JSONArray(httpResponseString));
                } catch (JSONException e) {
                    callback.onFailure(e.getMessage());
                }
            }

            @Override // com.google.appinventor.components.runtime.util.AsyncCallbackPair
            public void onFailure(String failureMessage) {
                callback.onFailure(failureMessage);
            }
        };
        postCommand(serviceURL, commandName, params, thisCallback);
    }

    public void postCommandReturningObject(String serviceURL, String commandName, List<NameValuePair> params, final AsyncCallbackPair<JSONObject> callback) {
        AsyncCallbackPair<String> thisCallback = new AsyncCallbackPair<String>() { // from class: com.google.appinventor.components.runtime.util.WebServiceUtil.2
            @Override // com.google.appinventor.components.runtime.util.AsyncCallbackPair
            public void onSuccess(String httpResponseString) {
                try {
                    callback.onSuccess(new JSONObject(httpResponseString));
                } catch (JSONException e) {
                    callback.onFailure(e.getMessage());
                }
            }

            @Override // com.google.appinventor.components.runtime.util.AsyncCallbackPair
            public void onFailure(String failureMessage) {
                callback.onFailure(failureMessage);
            }
        };
        postCommand(serviceURL, commandName, params, thisCallback);
    }

    public void postCommand(String serviceURL, String commandName, List<NameValuePair> params, AsyncCallbackPair<String> callback) {
        Log.d(LOG_TAG, "Posting " + commandName + " to " + serviceURL + " with arguments " + params);
        if (serviceURL == null || serviceURL.equals("")) {
            callback.onFailure("No service url to post command to.");
        }
        HttpPost httpPost = new HttpPost(serviceURL + "/" + commandName);
        if (params == null) {
            params = new ArrayList<>();
        }
        try {
            BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpPost.setHeader("Accept", "application/json");
            String httpResponseString = (String) httpClient.execute(httpPost, basicResponseHandler);
            callback.onSuccess(httpResponseString);
        } catch (ClientProtocolException e) {
            Log.w(LOG_TAG, (Throwable) e);
            callback.onFailure("Communication with the web service encountered a protocol exception.");
        } catch (UnsupportedEncodingException e2) {
            Log.w(LOG_TAG, e2);
            callback.onFailure("Failed to encode params for web service call.");
        } catch (IOException e3) {
            Log.w(LOG_TAG, e3);
            callback.onFailure("Communication with the web service timed out.");
        }
    }
}
