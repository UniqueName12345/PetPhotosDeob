package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import androidx.core.os.EnvironmentCompat;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesNativeLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.AppInvHTTPD;
import com.google.appinventor.components.runtime.util.EclairUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.WebRTCNativeMgr;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Formatter;
import kawa.lang.SyntaxForms;
import org.acra.util.Installation;

@SimpleObject
@UsesPermissions({"android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_WIFI_STATE"})
@DesignerComponent(category = ComponentCategory.INTERNAL, description = "Component that returns information about the phone.", iconName = "images/phoneip.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@UsesLibraries(libraries = "webrtc.jar,google-http-client.jar,google-http-client-android2-beta.jar,google-http-client-android3-beta.jar")
@UsesNativeLibraries(v7aLibraries = "libjingle_peerconnection_so.so", v8aLibraries = "libjingle_peerconnection_so.so", x86_64Libraries = "libjingle_peerconnection_so.so")
/* loaded from: classes.dex */
public class PhoneStatus extends AndroidNonvisibleComponent implements Component {
    private static final String LOG_TAG = "PhoneStatus";
    private static Activity activity;
    private String firstHmacSeed;
    private String firstSeed;
    private final Form form;
    private static PhoneStatus mainInstance = null;
    private static boolean useWebRTC = false;
    private static String popup = "No Page Provided!";

    public PhoneStatus(ComponentContainer container) {
        super(container.$form());
        this.firstSeed = null;
        this.firstHmacSeed = null;
        this.form = container.$form();
        activity = container.$context();
        if (mainInstance == null) {
            mainInstance = this;
        }
    }

    @SimpleFunction(description = "Returns the IP address of the phone in the form of a String")
    public static String GetWifiIpAddress() {
        Object wifiManager = activity.getSystemService("wifi");
        DhcpInfo ip = ((WifiManager) wifiManager).getDhcpInfo();
        int s_ipAddress = ip.ipAddress;
        if (isConnected()) {
            String ipAddress = intToIp(s_ipAddress);
            return ipAddress;
        }
        return "Error: No Wifi Connection";
    }

    @SimpleFunction(description = "Returns TRUE if the phone is on Wifi, FALSE otherwise")
    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService("connectivity");
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getNetworkInfo(1);
        }
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

    @SimpleFunction(description = "Establish the secret seed for HOTP generation. Return the SHA1 of the provided seed, this will be used to contact the rendezvous server. Note: This code also starts the connection negotiation process if we are using WebRTC. This is a bit of a kludge...")
    public String setHmacSeedReturnCode(String seed, String rendezvousServer) {
        if (seed.equals("")) {
            return "";
        }
        if (this.firstSeed != null) {
            if (!this.firstSeed.equals(seed)) {
                Notifier.oneButtonAlert(this.form, "You cannot use two codes with one start up of the Companion. You should restart the Companion and try again.", "Warning", "OK", new Runnable() { // from class: com.google.appinventor.components.runtime.PhoneStatus.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PhoneStatus.this.form.finish();
                        System.exit(0);
                    }
                });
            }
            return this.firstHmacSeed;
        }
        this.firstSeed = seed;
        if (!useWebRTC) {
            AppInvHTTPD.setHmacKey(seed);
        }
        try {
            MessageDigest Sha1 = MessageDigest.getInstance("SHA1");
            Sha1.update(seed.getBytes());
            byte[] result = Sha1.digest();
            StringBuffer sb = new StringBuffer(result.length * 2);
            Formatter formatter = new Formatter(sb);
            for (byte b : result) {
                formatter.format("%02x", Byte.valueOf(b));
            }
            Log.d(LOG_TAG, "Seed = " + seed);
            Log.d(LOG_TAG, "Code = " + sb.toString());
            this.firstHmacSeed = sb.toString();
            return this.firstHmacSeed;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception getting SHA1 Instance", e);
            return "";
        }
    }

    @SimpleFunction(description = "Returns true if we are running in the emulator or USB Connection")
    public boolean isDirect() {
        Log.d(LOG_TAG, "android.os.Build.VERSION.RELEASE = " + Build.VERSION.RELEASE);
        Log.d(LOG_TAG, "android.os.Build.PRODUCT = " + Build.PRODUCT);
        if (ReplForm.isEmulator()) {
            return true;
        }
        if (this.form instanceof ReplForm) {
            return ((ReplForm) this.form).isDirect();
        }
        return false;
    }

    @SimpleFunction(description = "Start the WebRTC engine")
    public void startWebRTC(String rendezvousServer, String iceServers) {
        if (useWebRTC) {
            WebRTCNativeMgr webRTCNativeMgr = new WebRTCNativeMgr(rendezvousServer, iceServers);
            webRTCNativeMgr.initiate((ReplForm) this.form, activity, this.firstSeed);
            ((ReplForm) this.form).setWebRTCMgr(webRTCNativeMgr);
        }
    }

    @SimpleFunction(description = "Start the internal AppInvHTTPD to listen for incoming forms. FOR REPL USE ONLY!")
    public void startHTTPD(boolean secure) {
        if (this.form.isRepl()) {
            ((ReplForm) this.form).startHTTPD(secure);
        }
    }

    @SimpleFunction(description = "Declare that we have loaded our initial assets and other assets should come from the sdcard")
    public void setAssetsLoaded() {
        if (this.form instanceof ReplForm) {
            ((ReplForm) this.form).setAssetsLoaded();
        }
    }

    @SimpleFunction(description = "Causes an Exception, used to debug exception processing.")
    public static void doFault() throws Exception {
        throw new Exception("doFault called!");
    }

    @SimpleFunction(description = "Downloads the URL and installs it as an Android Package via the installed browser")
    public void installURL(String url) {
        try {
            Class<?> clazz = Class.forName("edu.mit.appinventor.companionextras.CompanionExtras");
            Object o = clazz.getConstructor(Form.class).newInstance(this.form);
            Method m = clazz.getMethod("Extra1", String.class);
            m.invoke(o, url);
        } catch (Exception e) {
            Uri uri = Uri.parse(url + "?store=1");
            Intent intent = new Intent("android.intent.action.VIEW").setData(uri);
            this.form.startActivity(intent);
        }
    }

    @SimpleFunction(description = "Really Exit the Application")
    public void shutdown() {
        this.form.finish();
        System.exit(0);
    }

    @SimpleEvent
    public void OnSettings() {
        EventDispatcher.dispatchEvent(this, "OnSettings", new Object[0]);
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void WebRTC(boolean useWebRTC2) {
        useWebRTC = useWebRTC2;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "If True we are using WebRTC to talk to the server.")
    public boolean WebRTC() {
        return useWebRTC;
    }

    @SimpleFunction(description = "Get the current Android SDK Level")
    public int SdkLevel() {
        return SdkLevel.getLevel();
    }

    @SimpleFunction(description = "Return the our VersionName property")
    public String GetVersionName() {
        try {
            String packageName = this.form.getPackageName();
            return this.form.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(LOG_TAG, "Unable to get VersionName", e);
            return "UNKNOWN";
        }
    }

    @SimpleFunction(description = "Return the app that installed us")
    public String GetInstaller() {
        if (SdkLevel.getLevel() >= 5) {
            String installer = EclairUtil.getInstallerPackageName(YaVersion.ACCEPTABLE_COMPANION_PACKAGE, this.form);
            if (installer == null) {
                return "sideloaded";
            }
            return installer;
        }
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }

    @SimpleFunction(description = "Return the ACRA Installation ID")
    public String InstallationId() {
        return Installation.id(this.form);
    }

    @SimpleFunction(description = "Set the content of the Pop-Up page used for the new legacy connection")
    public void SetPopup(String page) {
        popup = page;
    }

    public static String getPopup() {
        return popup;
    }

    public static boolean getUseWebRTC() {
        return useWebRTC;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void doSettings() {
        Log.d(LOG_TAG, "doSettings called.");
        if (mainInstance != null) {
            mainInstance.OnSettings();
        } else {
            Log.d(LOG_TAG, "mainStance is null on doSettings");
        }
    }

    public static String intToIp(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }
}
