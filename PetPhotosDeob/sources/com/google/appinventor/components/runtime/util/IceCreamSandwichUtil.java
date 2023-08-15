package com.google.appinventor.components.runtime.util;

import android.os.Build;
import android.widget.TextView;

/* loaded from: classes.dex */
public final class IceCreamSandwichUtil {
    private IceCreamSandwichUtil() {
    }

    public static void setAllCaps(TextView view, boolean allCaps) {
        if (Build.VERSION.SDK_INT >= 14) {
            view.setAllCaps(allCaps);
        }
    }
}
