package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import java.io.File;

/* loaded from: classes.dex */
public class QUtil {
    public static String getExternalStoragePath(Context context, boolean forcePrivate, boolean useLegacy) {
        return getExternalStorageDir(context, forcePrivate, useLegacy).getAbsolutePath();
    }

    public static String getExternalStoragePath(Context context, boolean forcePrivate) {
        return getExternalStoragePath(context, forcePrivate, false);
    }

    public static String getExternalStoragePath(Context context) {
        return getExternalStoragePath(context, false, false);
    }

    public static File getExternalStorageDir(Context context) {
        return getExternalStorageDir(context, false);
    }

    public static File getExternalStorageDir(Context context, boolean forcePrivate) {
        return getExternalStorageDir(context, forcePrivate, false);
    }

    public static File getExternalStorageDir(Context context, boolean forcePrivate, boolean legacy) {
        if (Build.VERSION.SDK_INT < 8) {
            return Environment.getExternalStorageDirectory();
        }
        if ((!legacy && Build.VERSION.SDK_INT >= 29) || forcePrivate) {
            return context.getExternalFilesDir(null);
        }
        return Environment.getExternalStorageDirectory();
    }

    public static String getReplAssetPath(Context context, boolean forcePrivate) {
        return Build.VERSION.SDK_INT >= 29 ? getExternalStoragePath(context, forcePrivate) + "/assets/" : getExternalStoragePath(context, forcePrivate) + "/AppInventor/assets/";
    }

    public static String getReplDatabasePath(Context context, boolean forcePrivate) {
        return Build.VERSION.SDK_INT >= 29 ? getExternalStoragePath(context, forcePrivate) + "/databases/" : getExternalStoragePath(context, forcePrivate) + "/AppInventor/databases/";
    }

    public static String getReplAssetPath(Context context) {
        return getReplAssetPath(context, false);
    }

    public static String getReplDataPath(Context context, boolean forcePrivate) {
        return Build.VERSION.SDK_INT >= 29 ? getExternalStoragePath(context, forcePrivate) + "/data/" : getExternalStoragePath(context, forcePrivate) + "/AppInventor/data/";
    }

    public static String getReplDataPath(Context context) {
        return getReplDataPath(context, false);
    }
}
