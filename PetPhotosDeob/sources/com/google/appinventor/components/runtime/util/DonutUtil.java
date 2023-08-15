package com.google.appinventor.components.runtime.util;

import android.graphics.Bitmap;
import android.view.View;

/* loaded from: classes.dex */
public class DonutUtil {
    private DonutUtil() {
    }

    public static void buildDrawingCache(View view, boolean autoScale) {
        view.buildDrawingCache(autoScale);
    }

    public static Bitmap getDrawingCache(View view, boolean autoScale) {
        return view.getDrawingCache(autoScale);
    }
}
