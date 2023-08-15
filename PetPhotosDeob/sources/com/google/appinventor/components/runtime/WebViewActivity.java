package com.google.appinventor.components.runtime;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* loaded from: classes.dex */
public final class WebViewActivity extends AppInventorCompatActivity {
    @Override // com.google.appinventor.components.runtime.AppInventorCompatActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() { // from class: com.google.appinventor.components.runtime.WebViewActivity.1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("WebView", "Handling url " + url);
                Uri uri = Uri.parse(url);
                String scheme = uri.getScheme();
                if (scheme.equals(Form.APPINVENTOR_URL_SCHEME)) {
                    Intent resultIntent = new Intent();
                    resultIntent.setData(uri);
                    WebViewActivity.this.setResult(-1, resultIntent);
                    WebViewActivity.this.finish();
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
        setContentView(webview);
        Intent uriIntent = getIntent();
        if (uriIntent != null && uriIntent.getData() != null) {
            Uri uri = uriIntent.getData();
            String scheme = uri.getScheme();
            String host = uri.getHost();
            Log.i("WebView", "Got intent with URI: " + uri + ", scheme=" + scheme + ", host=" + host);
            webview.loadUrl(uri.toString());
        }
    }
}
