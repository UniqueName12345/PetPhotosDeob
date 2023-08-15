package com.google.appinventor.components.runtime.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.util.ViewUtil;
import org.osmdroid.views.MapView;

/* loaded from: classes.dex */
public class ZoomControlView extends LinearLayout {
    private float density;
    private final MapView parent;
    private final Button zoomIn;
    private final Button zoomOut;

    public ZoomControlView(MapView parent) {
        super(parent.getContext());
        this.density = parent.getContext().getResources().getDisplayMetrics().density;
        this.parent = parent;
        setOrientation(1);
        this.zoomIn = new Button(parent.getContext());
        this.zoomOut = new Button(parent.getContext());
        initButton(this.zoomIn, "+");
        initButton(this.zoomOut, "−");
        this.zoomIn.setOnClickListener(new View.OnClickListener() { // from class: com.google.appinventor.components.runtime.view.ZoomControlView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ZoomControlView.this.parent.getController().zoomIn();
            }
        });
        this.zoomOut.setOnClickListener(new View.OnClickListener() { // from class: com.google.appinventor.components.runtime.view.ZoomControlView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ZoomControlView.this.parent.getController().zoomOut();
            }
        });
        ViewUtil.setBackgroundDrawable(this.zoomIn, getZoomInDrawable(this.density));
        ViewUtil.setBackgroundDrawable(this.zoomOut, getZoomOutDrawable(this.density));
        int[][] states = {new int[]{-16842910}, new int[]{16842910}};
        int[] colors = {Component.COLOR_LTGRAY, -16777216};
        this.zoomIn.setTextColor(new ColorStateList(states, colors));
        this.zoomOut.setTextColor(new ColorStateList(states, colors));
        addView(this.zoomIn);
        addView(this.zoomOut);
        setPadding((int) (this.density * 12.0f), (int) (this.density * 12.0f), 0, 0);
        updateButtons();
    }

    public final void updateButtons() {
        this.zoomIn.setEnabled(this.parent.canZoomIn());
        this.zoomOut.setEnabled(this.parent.canZoomOut());
    }

    private void initButton(Button button, String text) {
        button.setText(text);
        button.setTextSize(22.0f);
        button.setPadding(0, 0, 0, 0);
        button.setWidth((int) (this.density * 30.0f));
        button.setHeight((int) (this.density * 30.0f));
        button.setSingleLine();
        button.setGravity(17);
    }

    private static Drawable getZoomInDrawable(float density) {
        int R = (int) (4.0f * density);
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(new float[]{R, R, R, R, 0.0f, 0.0f, 0.0f, 0.0f}, null, null));
        drawable.getPaint().setColor(-1);
        return drawable;
    }

    private static Drawable getZoomOutDrawable(float density) {
        int R = (int) (4.0f * density);
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(new float[]{0.0f, 0.0f, 0.0f, 0.0f, R, R, R, R}, null, null));
        drawable.getPaint().setColor(-1);
        return drawable;
    }
}
