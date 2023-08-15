package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.MediaController;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

/* loaded from: classes.dex */
public class CustomMediaController extends MediaController implements View.OnTouchListener {
    private View mAnchorView;
    private int mShowTime;

    public CustomMediaController(Context context) {
        super(context);
        this.mShowTime = PathInterpolatorCompat.MAX_NUM_POINTS;
    }

    @Override // android.widget.MediaController
    public void show(int timeout) {
        setVisibility(0);
        super.show(timeout);
    }

    @Override // android.widget.MediaController
    public void show() {
        setVisibility(0);
        super.show();
    }

    public boolean addTo(ViewGroup parent, ViewGroup.LayoutParams params) {
        ViewParent mParent = getParent();
        if (mParent != null && (mParent instanceof ViewGroup)) {
            ((ViewGroup) mParent).removeView(this);
            parent.addView(this, params);
            return true;
        }
        Log.e("CustomMediaController.addTo", "MediaController not available in fullscreen.");
        return false;
    }

    @Override // android.widget.MediaController
    public void setAnchorView(View anchorView) {
        this.mAnchorView = anchorView;
        this.mAnchorView.setOnTouchListener(this);
        super.setAnchorView(anchorView);
    }

    @Override // android.widget.MediaController
    public void hide() {
        super.hide();
        setVisibility(4);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        if (v == this.mAnchorView) {
            show(this.mShowTime);
            return false;
        }
        return false;
    }
}
