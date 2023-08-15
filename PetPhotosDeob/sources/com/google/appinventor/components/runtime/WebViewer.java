package com.google.appinventor.components.runtime;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.RequiresApi;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.EclairUtil;
import com.google.appinventor.components.runtime.util.FroyoWebViewClient;
import com.google.appinventor.components.runtime.util.HoneycombWebViewClient;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import kawa.lang.SyntaxForms;

@UsesPermissions(permissionNames = "android.permission.INTERNET")
@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "Component for viewing Web pages.  The Home URL can be specified in the Designer or in the Blocks Editor.  The view can be set to follow links when they are tapped, and users can fill in Web forms. Warning: This is not a full browser.  For example, pressing the phone's hardware Back key will exit the app, rather than move back in the browser history.<p />You can use the WebViewer.WebViewString property to communicate between your app and Javascript code running in the Webviewer page. In the app, you get and set WebViewString.  In the WebViewer, you include Javascript that references the window.AppInventor object, using the methoods </em getWebViewString()</em> and <em>setWebViewString(text)</em>.  <p />For example, if the WebViewer opens to a page that contains the Javascript command <br /> <em>document.write(\"The answer is\" + window.AppInventor.getWebViewString());</em> <br />and if you set WebView.WebVewString to \"hello\", then the web page will show </br ><em>The answer is hello</em>.  <br />And if the Web page contains Javascript that executes the command <br /><em>window.AppInventor.setWebViewString(\"hello from Javascript\")</em>, <br />then the value of the WebViewString property will be <br /><em>hello from Javascript</em>. ", version = 10)
@SimpleObject
/* loaded from: classes.dex */
public final class WebViewer extends AndroidViewComponent {
    private boolean followLinks;
    private boolean havePermission;
    private String homeUrl;
    private boolean ignoreSslErrors;
    private boolean prompt;
    private final WebView webview;
    WebViewInterface wvInterface;

    @RequiresApi(api = 3)
    public WebViewer(ComponentContainer container) {
        super(container);
        this.followLinks = true;
        this.prompt = true;
        this.ignoreSslErrors = false;
        this.havePermission = false;
        this.webview = new WebView(container.$context());
        resetWebViewClient();
        WebSettings settings = this.webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        this.webview.setFocusable(true);
        this.wvInterface = new WebViewInterface();
        this.webview.addJavascriptInterface(this.wvInterface, "AppInventor");
        settings.setBuiltInZoomControls(true);
        if (SdkLevel.getLevel() >= 5) {
            EclairUtil.setupWebViewGeoLoc(this, this.webview, container.$context());
        }
        container.$add(this);
        this.webview.setOnTouchListener(new View.OnTouchListener() { // from class: com.google.appinventor.components.runtime.WebViewer.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                    case 1:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                            return false;
                        }
                        return false;
                    default:
                        return false;
                }
            }
        });
        HomeUrl("");
        Width(-2);
        Height(-2);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Gets the WebView's String, which is viewable through Javascript in the WebView as the window.AppInventor object")
    public String WebViewString() {
        return this.wvInterface.getWebViewString();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void WebViewString(String newString) {
        this.wvInterface.setWebViewStringFromBlocks(newString);
    }

    @Override // com.google.appinventor.components.runtime.AndroidViewComponent
    public View getView() {
        return this.webview;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class WebViewerClient extends WebViewClient {
        private WebViewerClient() {
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return !WebViewer.this.followLinks;
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            WebViewer.this.BeforePageLoad(url);
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            WebViewer.this.PageLoaded(url);
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, final int errorCode, final String description, final String failingUrl) {
            WebViewer.this.container.$form().runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.WebViewer.WebViewerClient.1
                @Override // java.lang.Runnable
                public void run() {
                    WebViewer.this.ErrorOccurred(errorCode, description, failingUrl);
                }
            });
        }
    }

    @Override // com.google.appinventor.components.runtime.AndroidViewComponent, com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty
    public void Width(int width) {
        if (width == -1) {
            width = -2;
        }
        super.Width(width);
    }

    @Override // com.google.appinventor.components.runtime.AndroidViewComponent, com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty
    public void Height(int height) {
        if (height == -1) {
            height = -2;
        }
        super.Height(height);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "URL of the page the WebViewer should initially open to.  Setting this will load the page.")
    public String HomeUrl() {
        return this.homeUrl;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void HomeUrl(String url) {
        this.homeUrl = url;
        this.webview.clearHistory();
        loadUrl("HomeUrl", this.homeUrl);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "URL of the page currently viewed.   This could be different from the Home URL if new pages were visited by following links.")
    public String CurrentUrl() {
        return this.webview.getUrl() == null ? "" : this.webview.getUrl();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Title of the page currently viewed")
    public String CurrentPageTitle() {
        return this.webview.getTitle() == null ? "" : this.webview.getTitle();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Determines whether to follow links when they are tapped in the WebViewer.  If you follow links, you can use GoBack and GoForward to navigate the browser history. ")
    public boolean FollowLinks() {
        return this.followLinks;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void FollowLinks(boolean follow) {
        this.followLinks = follow;
        resetWebViewClient();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Determine whether or not to ignore SSL errors. Set to true to ignore errors. Use this to accept self signed certificates from websites.")
    public boolean IgnoreSslErrors() {
        return this.ignoreSslErrors;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void IgnoreSslErrors(boolean ignoreSslErrors) {
        this.ignoreSslErrors = ignoreSslErrors;
        resetWebViewClient();
    }

    @SimpleFunction(description = "Loads the home URL page.  This happens automatically when the home URL is changed.")
    public void GoHome() {
        loadUrl("GoHome", this.homeUrl);
    }

    @SimpleFunction(description = "Go back to the previous page in the history list.  Does nothing if there is no previous page.")
    public void GoBack() {
        if (this.webview.canGoBack()) {
            this.webview.goBack();
        }
    }

    @SimpleFunction(description = "Go forward to the next page in the history list.   Does nothing if there is no next page.")
    public void GoForward() {
        if (this.webview.canGoForward()) {
            this.webview.goForward();
        }
    }

    @SimpleFunction(description = "Returns true if the WebViewer can go forward in the history list.")
    public boolean CanGoForward() {
        return this.webview.canGoForward();
    }

    @SimpleFunction(description = "Returns true if the WebViewer can go back in the history list.")
    public boolean CanGoBack() {
        return this.webview.canGoBack();
    }

    @SimpleFunction(description = "Load the page at the given URL.")
    public void GoToUrl(String url) {
        loadUrl("GoToUrl", url);
    }

    @SimpleFunction(description = "Stop loading a page.")
    public void StopLoading() {
        this.webview.stopLoading();
    }

    @SimpleFunction(description = "Reload the current page.")
    public void Reload() {
        this.webview.reload();
    }

    @SimpleProperty(description = "Whether or not to give the application permission to use the Javascript geolocation API. This property is available only in the designer.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void UsesLocation(boolean uses) {
    }

    @SimpleProperty(description = "If True, then prompt the user of the WebView to give permission to access the geolocation API. If False, then assume permission is granted.")
    public boolean PromptforPermission() {
        return this.prompt;
    }

    @SimpleProperty(userVisible = SyntaxForms.DEBUGGING)
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void PromptforPermission(boolean prompt) {
        this.prompt = prompt;
    }

    @SimpleFunction(description = "Clear stored location permissions.")
    public void ClearLocations() {
        if (SdkLevel.getLevel() >= 5) {
            EclairUtil.clearWebViewGeoLoc();
        }
    }

    private void resetWebViewClient() {
        if (SdkLevel.getLevel() >= 11) {
            this.webview.setWebViewClient(new HoneycombWebViewClient(this.followLinks, this.ignoreSslErrors, this.container.$form(), this));
        } else if (SdkLevel.getLevel() >= 8) {
            this.webview.setWebViewClient(new FroyoWebViewClient(this.followLinks, this.ignoreSslErrors, this.container.$form(), this));
        } else {
            this.webview.setWebViewClient(new WebViewerClient());
        }
    }

    @SimpleFunction(description = "Clear WebView caches.")
    public void ClearCaches() {
        this.webview.clearCache(true);
    }

    @SimpleFunction(description = "Clear WebView cookies.")
    public void ClearCookies() {
        CookieManager cookieManager = CookieManager.getInstance();
        if (SdkLevel.getLevel() >= 21) {
            cookieManager.removeAllCookies(null);
        } else {
            cookieManager.removeAllCookie();
        }
    }

    @SimpleFunction(description = "Run JavaScript in the current page.")
    public void RunJavaScript(String js) {
        this.webview.loadUrl("javascript:(function(){" + js + "})()");
    }

    @SimpleEvent(description = "When the JavaScript calls AppInventor.setWebViewString this event is run.")
    public void WebViewStringChange(String value) {
        EventDispatcher.dispatchEvent(this, "WebViewStringChange", value);
    }

    @SimpleEvent(description = "When a page is about to load this event is run.")
    public void BeforePageLoad(String url) {
        EventDispatcher.dispatchEvent(this, "BeforePageLoad", url);
    }

    @SimpleEvent(description = "When a page is finished loading this event is run.")
    public void PageLoaded(String url) {
        EventDispatcher.dispatchEvent(this, "PageLoaded", url);
    }

    @SimpleEvent(description = "When an error occurs this event is run.")
    public void ErrorOccurred(int errorCode, String description, String failingUrl) {
        EventDispatcher.dispatchEvent(this, "ErrorOccurred", Integer.valueOf(errorCode), description, failingUrl);
    }

    private void loadUrl(final String caller, final String url) {
        if (!this.havePermission && MediaUtil.isExternalFileUrl(this.container.$form(), url)) {
            this.container.$form().askPermission("android.permission.READ_EXTERNAL_STORAGE", new PermissionResultHandler() { // from class: com.google.appinventor.components.runtime.WebViewer.2
                @Override // com.google.appinventor.components.runtime.PermissionResultHandler
                public void HandlePermissionResponse(String permission, boolean granted) {
                    if (granted) {
                        WebViewer.this.havePermission = true;
                        WebViewer.this.webview.loadUrl(url);
                        return;
                    }
                    WebViewer.this.container.$form().dispatchPermissionDeniedEvent(WebViewer.this, caller, "android.permission.READ_EXTERNAL_STORAGE");
                }
            });
        } else {
            this.webview.loadUrl(url);
        }
    }

    /* loaded from: classes.dex */
    public class WebViewInterface {
        String webViewString = " ";

        WebViewInterface() {
        }

        @JavascriptInterface
        public String getWebViewString() {
            return this.webViewString;
        }

        @JavascriptInterface
        public void setWebViewString(final String newString) {
            this.webViewString = newString;
            WebViewer.this.container.$form().runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.WebViewer.WebViewInterface.1
                @Override // java.lang.Runnable
                public void run() {
                    WebViewer.this.WebViewStringChange(newString);
                }
            });
        }

        public void setWebViewStringFromBlocks(String newString) {
            this.webViewString = newString;
        }
    }
}
