package com.google.appinventor.components.runtime.util;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;

/* loaded from: classes.dex */
public class FroyoWebViewClient<T extends Component> extends WebViewClient {
    private final T component;
    private final boolean followLinks;
    private final Form form;
    private final boolean ignoreErrors;

    public FroyoWebViewClient(boolean followLinks, boolean ignoreErrors, Form form, T component) {
        this.followLinks = followLinks;
        this.ignoreErrors = ignoreErrors;
        this.form = form;
        this.component = component;
    }

    public T getComponent() {
        return this.component;
    }

    public Form getForm() {
        return this.form;
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return !this.followLinks;
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (this.ignoreErrors) {
            handler.proceed();
            return;
        }
        handler.cancel();
        this.form.dispatchErrorOccurredEvent(this.component, "WebView", ErrorMessages.ERROR_WEBVIEW_SSL_ERROR, new Object[0]);
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        EventDispatcher.dispatchEvent(this.component, "BeforePageLoad", url);
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView view, String url) {
        EventDispatcher.dispatchEvent(this.component, "PageLoaded", url);
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView view, final int errorCode, final String description, final String failingUrl) {
        this.form.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.util.FroyoWebViewClient.1
            @Override // java.lang.Runnable
            public void run() {
                EventDispatcher.dispatchEvent(FroyoWebViewClient.this.component, "ErrorOccurred", Integer.valueOf(errorCode), description, failingUrl);
            }
        });
    }
}
