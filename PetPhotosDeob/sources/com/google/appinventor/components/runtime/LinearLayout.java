package com.google.appinventor.components.runtime;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
/* loaded from: classes.dex */
public final class LinearLayout implements Layout {
    private final android.widget.LinearLayout layoutManager;

    /* JADX INFO: Access modifiers changed from: package-private */
    public LinearLayout(Context context, int orientation) {
        this(context, orientation, null, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LinearLayout(Context context, int orientation, final Integer preferredEmptyWidth, final Integer preferredEmptyHeight) {
        if ((preferredEmptyWidth == null && preferredEmptyHeight != null) || (preferredEmptyWidth != null && preferredEmptyHeight == null)) {
            throw new IllegalArgumentException("LinearLayout - preferredEmptyWidth and preferredEmptyHeight must be either both null or both not null");
        }
        this.layoutManager = new android.widget.LinearLayout(context) { // from class: com.google.appinventor.components.runtime.LinearLayout.1
            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                if (preferredEmptyWidth == null || preferredEmptyHeight == null) {
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                } else if (getChildCount() != 0) {
                    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                } else {
                    setMeasuredDimension(getSize(widthMeasureSpec, preferredEmptyWidth.intValue()), getSize(heightMeasureSpec, preferredEmptyHeight.intValue()));
                }
            }

            private int getSize(int measureSpec, int preferredSize) {
                int specMode = View.MeasureSpec.getMode(measureSpec);
                int specSize = View.MeasureSpec.getSize(measureSpec);
                if (specMode == 1073741824) {
                    return specSize;
                }
                if (specMode != Integer.MIN_VALUE) {
                    return preferredSize;
                }
                int result = Math.min(preferredSize, specSize);
                return result;
            }
        };
        this.layoutManager.setOrientation(orientation == 1 ? 0 : 1);
    }

    @Override // com.google.appinventor.components.runtime.Layout
    public ViewGroup getLayoutManager() {
        return this.layoutManager;
    }

    @Override // com.google.appinventor.components.runtime.Layout
    public void add(AndroidViewComponent component) {
        this.layoutManager.addView(component.getView(), new LinearLayout.LayoutParams(-2, -2, 0.0f));
    }

    public void setHorizontalGravity(int gravity) {
        this.layoutManager.setHorizontalGravity(gravity);
    }

    public void setVerticalGravity(int gravity) {
        this.layoutManager.setVerticalGravity(gravity);
    }

    public void setBaselineAligned(boolean baselineAligned) {
        this.layoutManager.setBaselineAligned(baselineAligned);
    }
}
