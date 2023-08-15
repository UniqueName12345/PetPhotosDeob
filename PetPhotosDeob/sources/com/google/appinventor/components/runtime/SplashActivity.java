package com.google.appinventor.components.runtime;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.appinventor.components.runtime.util.SdkLevel;

/* loaded from: classes.dex */
public final class SplashActivity extends AppInventorCompatActivity {
    Handler handler;
    WebView webview;

    /* loaded from: classes.dex */
    public class JavaInterface {
        Context mContext;

        public JavaInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public boolean hasPermission(String permission) {
            return SdkLevel.getLevel() < 23 || ContextCompat.checkSelfPermission(this.mContext, permission) == 0;
        }

        @JavascriptInterface
        public void askPermission(String permission) {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{permission}, 1);
        }

        @JavascriptInterface
        public String getVersion() {
            try {
                String packageName = this.mContext.getPackageName();
                PackageInfo pInfo = this.mContext.getPackageManager().getPackageInfo(packageName, 0);
                return pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                return "Unknown";
            }
        }

        @JavascriptInterface
        public void finished() {
            SplashActivity.this.handler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.SplashActivity.JavaInterface.1
                @Override // java.lang.Runnable
                public void run() {
                    SplashActivity.this.webview.destroy();
                    SplashActivity.this.finish();
                }
            });
        }
    }

    @Override // com.google.appinventor.components.runtime.AppInventorCompatActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JavaInterface android2 = new JavaInterface(this);
        this.handler = new Handler();
        this.webview = new WebView(this);
        WebSettings webSettings = this.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        String databasePath = getApplicationContext().getDir("database", 0).getPath();
        webSettings.setDatabasePath(databasePath);
        this.webview.setWebChromeClient(new WebChromeClient() { // from class: com.google.appinventor.components.runtime.SplashActivity.1
            @Override // android.webkit.WebChromeClient
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
                quotaUpdater.updateQuota(5242880L);
            }
        });
        setContentView(this.webview);
        this.webview.addJavascriptInterface(android2, "Android");
        this.webview.loadUrl("file:///android_asset/splash.html");
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int code, String[] permissions, int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int grantResult = grantResults[i];
            boolean granted = false;
            if (grantResult == 0) {
                granted = true;
            }
            this.webview.loadUrl("javascript:permresult('" + permission + "'," + granted + ")");
        }
    }
}
