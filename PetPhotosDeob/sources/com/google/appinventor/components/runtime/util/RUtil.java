package com.google.appinventor.components.runtime.util;

import android.os.Build;
import android.os.Environment;
import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.runtime.Form;

/* loaded from: classes.dex */
public class RUtil {
    public static boolean needsFilePermission(Form form, String path, FileScope fileScope) {
        if (fileScope != null) {
            switch (fileScope) {
                case App:
                    return Build.VERSION.SDK_INT < 19;
                case Asset:
                    return form.isRepl() && Build.VERSION.SDK_INT < 19;
                case Shared:
                    return true;
                default:
                    return false;
            }
        } else if (path.startsWith("//")) {
            return false;
        } else {
            if (path.startsWith("/") || path.startsWith("file:")) {
                if (Build.VERSION.SDK_INT >= 8) {
                    return FileUtil.isExternalStorageUri(form, path) && !FileUtil.isAppSpecificExternalUri(form, path);
                }
                String fpath = path;
                if (path.startsWith("file:")) {
                    fpath = path.substring(5);
                }
                return fpath.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath());
            }
            return false;
        }
    }
}
