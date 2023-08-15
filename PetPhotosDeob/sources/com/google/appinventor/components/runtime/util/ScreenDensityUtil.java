package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class ScreenDensityUtil {
    public static final int DEFAULT_NORMAL_SHORT_DIMENSION = 320;
    private static final String LOG_TAG = "ScreenDensityUtil";
    public static final float MAXIMUM_ASPECT_RATIO = 1.7791667f;

    private ScreenDensityUtil() {
    }

    public static float computeCompatibleScaling(Context context) {
        int shortSize;
        int longSize;
        int newWidth;
        int newHeight;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        Point rawDims = new Point();
        getRawScreenDim(context, rawDims);
        int width = rawDims.x;
        int height = rawDims.y;
        if (width < height) {
            shortSize = width;
            longSize = height;
        } else {
            shortSize = height;
            longSize = width;
        }
        int newShortSize = (int) ((320.0f * dm.density) + 0.5f);
        float aspect = longSize / shortSize;
        if (aspect > 1.7791667f) {
            aspect = 1.7791667f;
        }
        int newLongSize = (int) ((newShortSize * aspect) + 0.5f);
        if (width < height) {
            newWidth = newShortSize;
            newHeight = newLongSize;
        } else {
            newWidth = newLongSize;
            newHeight = newShortSize;
        }
        float sw = width / newWidth;
        float sh = height / newHeight;
        return Math.max(1.0f, Math.min(Math.min(sw, sh), 1.7791667f));
    }

    private static void getRawScreenDim(Context context, Point outSize) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService("window");
        Display display = wm.getDefaultDisplay();
        int sdkLevel = SdkLevel.getLevel();
        if (sdkLevel >= 24) {
            display.getMetrics(metrics);
            outSize.x = metrics.widthPixels;
            outSize.y = metrics.heightPixels;
        } else if (sdkLevel >= 17) {
            JellybeanUtil.getRealSize(display, outSize);
        } else if (sdkLevel > 10) {
            try {
                Method getRawH = Display.class.getMethod("getRawHeight", new Class[0]);
                Method getRawW = Display.class.getMethod("getRawWidth", new Class[0]);
                try {
                    try {
                        outSize.x = ((Integer) getRawW.invoke(display, new Object[0])).intValue();
                        outSize.y = ((Integer) getRawH.invoke(display, new Object[0])).intValue();
                    } catch (IllegalAccessException e) {
                        Log.e(LOG_TAG, "Error reading raw screen size", e);
                    } catch (InvocationTargetException e2) {
                        Log.e(LOG_TAG, "Error reading raw screen size", e2);
                    }
                } catch (IllegalArgumentException e3) {
                    Log.e(LOG_TAG, "Error reading raw screen size", e3);
                }
            } catch (NoSuchMethodException e4) {
                Log.e(LOG_TAG, "Error reading raw screen size", e4);
            }
        } else {
            outSize.x = display.getWidth();
            outSize.y = display.getHeight();
        }
    }
}
