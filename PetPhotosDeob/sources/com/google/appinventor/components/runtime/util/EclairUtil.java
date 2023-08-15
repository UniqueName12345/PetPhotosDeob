package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import com.google.appinventor.components.runtime.WebViewer;

/* loaded from: classes.dex */
public class EclairUtil {
    private EclairUtil() {
    }

    public static void overridePendingTransitions(Activity activity, int enterAnim, int exitAnim) {
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    public static void setupWebViewGeoLoc(final WebViewer caller, WebView webview, final Activity activity) {
        webview.getSettings().setGeolocationDatabasePath(activity.getFilesDir().getAbsolutePath());
        webview.getSettings().setDatabaseEnabled(true);
        webview.setWebChromeClient(new WebChromeClient() { // from class: com.google.appinventor.components.runtime.util.EclairUtil.1
            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
                if (!WebViewer.this.PromptforPermission()) {
                    callback.invoke(origin, true, true);
                    return;
                }
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle("Permission Request");
                if (origin.equals("file://")) {
                    origin = "This Application";
                }
                alertDialog.setMessage(origin + " would like to access your location.");
                alertDialog.setButton(-1, "Allow", new DialogInterface.OnClickListener() { // from class: com.google.appinventor.components.runtime.util.EclairUtil.1.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        callback.invoke(origin, true, true);
                    }
                });
                alertDialog.setButton(-2, "Refuse", new DialogInterface.OnClickListener() { // from class: com.google.appinventor.components.runtime.util.EclairUtil.1.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                        callback.invoke(origin, false, true);
                    }
                });
                alertDialog.show();
            }
        });
    }

    public static void clearWebViewGeoLoc() {
        GeolocationPermissions permissions = GeolocationPermissions.getInstance();
        permissions.clearAll();
    }

    public static String getInstallerPackageName(String pname, Activity form) {
        return form.getPackageManager().getInstallerPackageName(pname);
    }

    public static void disableSuggestions(EditText textview) {
        textview.setInputType(textview.getInputType() | 524288);
    }
}
