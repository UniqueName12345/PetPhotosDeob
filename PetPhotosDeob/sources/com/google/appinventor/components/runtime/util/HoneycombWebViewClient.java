package com.google.appinventor.components.runtime.util;

import android.os.Build;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import androidx.annotation.RequiresApi;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.WebViewer;
import gnu.kawa.servlet.HttpRequestContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = 11)
/* loaded from: classes.dex */
public class HoneycombWebViewClient extends FroyoWebViewClient<WebViewer> {
    private static final String ASSET_PREFIX = "file:///appinventor_asset/";
    private static final String TAG = HoneycombWebViewClient.class.getSimpleName();

    public HoneycombWebViewClient(boolean followLinks, boolean ignoreErrors, Form form, WebViewer component) {
        super(followLinks, ignoreErrors, form, component);
    }

    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return (url.startsWith("http://localhost/") || url.startsWith(ASSET_PREFIX)) ? handleAppRequest(url) : super.shouldInterceptRequest(view, url);
    }

    @Override // android.webkit.WebViewClient
    @RequiresApi(api = 21)
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Log.d(TAG, "scheme = " + request.getUrl().getScheme());
        return ("localhost".equals(request.getUrl().getAuthority()) || request.getUrl().toString().startsWith(ASSET_PREFIX)) ? handleAppRequest(request.getUrl().toString()) : super.shouldInterceptRequest(view, request);
    }

    protected WebResourceResponse handleAppRequest(String url) {
        String path;
        if (url.startsWith(ASSET_PREFIX)) {
            path = url.substring(ASSET_PREFIX.length());
        } else {
            path = url.substring(url.indexOf("//localhost/") + 12);
        }
        try {
            Log.i(TAG, "webviewer requested path = " + path);
            InputStream stream = getForm().openAsset(path);
            Map<String, String> headers = new HashMap<>();
            headers.put("Access-Control-Allow-Origin", "localhost");
            String mimeType = URLConnection.getFileNameMap().getContentTypeFor(path);
            String encoding = "utf-8";
            Log.i(TAG, "Mime type = " + mimeType);
            if (mimeType == null || (!mimeType.startsWith("text/") && !mimeType.equals("application/javascript"))) {
                encoding = null;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                return new WebResourceResponse(mimeType, encoding, HttpRequestContext.HTTP_OK, "OK", headers, stream);
            }
            return new WebResourceResponse(mimeType, encoding, stream);
        } catch (IOException e) {
            ByteArrayInputStream error = new ByteArrayInputStream(NanoHTTPD.HTTP_NOTFOUND.getBytes());
            if (Build.VERSION.SDK_INT >= 21) {
                return new WebResourceResponse(NanoHTTPD.MIME_PLAINTEXT, "utf-8", 404, "Not Found", null, error);
            }
            return new WebResourceResponse(NanoHTTPD.MIME_PLAINTEXT, "utf-8", error);
        }
    }
}
