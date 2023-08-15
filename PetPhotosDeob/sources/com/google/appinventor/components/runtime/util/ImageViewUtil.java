package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.widget.ImageViewCompat;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes.dex */
public final class ImageViewUtil {
    private ImageViewUtil() {
    }

    public static void setMenuButtonColor(Activity activity, int color) {
        ColorStateList stateList = new ColorStateList(new int[][]{new int[0]}, new int[]{color});
        ImageView view = findOverflowMenuView(activity);
        if (view != null) {
            ImageViewCompat.setImageTintMode(view, PorterDuff.Mode.MULTIPLY);
            ImageViewCompat.setImageTintList(view, stateList);
        }
    }

    private static ImageView findOverflowMenuView(Activity activity) {
        ViewGroup vg = (ViewGroup) activity.getWindow().getDecorView();
        Queue<ViewGroup> children = new LinkedList<>();
        children.add(vg);
        while (children.size() > 0) {
            ViewGroup vg2 = children.poll();
            ViewGroup vg3 = vg2;
            for (int i = 0; i < vg3.getChildCount(); i++) {
                View child = vg3.getChildAt(i);
                if (child instanceof ImageView) {
                    return (ImageView) child;
                }
                if (child instanceof ViewGroup) {
                    children.add((ViewGroup) child);
                }
            }
        }
        return null;
    }
}
