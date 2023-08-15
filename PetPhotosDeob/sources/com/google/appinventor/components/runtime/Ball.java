package com.google.appinventor.components.runtime;

import android.graphics.Paint;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.PaintUtil;

@DesignerComponent(category = ComponentCategory.ANIMATION, description = "<p>A round 'sprite' that can be placed on a <code>Canvas</code>, where it can react to touches and drags, interact with other sprites (<code>ImageSprite</code>s and other <code>Ball</code>s) and the edge of the Canvas, and move according to its property values.</p><p>For example, to have a <code>Ball</code> move 4 pixels toward the top of a <code>Canvas</code> every 500 milliseconds (half second), you would set the <code>Speed</code> property to 4 [pixels], the <code>Interval</code> property to 500 [milliseconds], the <code>Heading</code> property to 90 [degrees], and the <code>Enabled</code> property to <code>True</code>.</p><p>The difference between a <code>Ball</code> and an <code>ImageSprite</code> is that the latter can get its appearance from an image file, while a <code>Ball</code>'s appearance can be changed only by varying its <code>PaintColor</code> and <code>Radius</code> properties.</p>", version = 8)
@SimpleObject
/* loaded from: classes.dex */
public final class Ball extends Sprite {
    static final int DEFAULT_RADIUS = 5;
    private Paint paint;
    private int paintColor;
    private int radius;

    public Ball(ComponentContainer container) {
        super(container);
        this.paint = new Paint();
        PaintColor(-16777216);
        Radius(5);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.Sprite
    public void onDraw(android.graphics.Canvas canvas) {
        if (this.visible) {
            float correctedXLeft = (float) (this.xLeft * this.form.deviceDensity());
            float correctedYTop = (float) (this.yTop * this.form.deviceDensity());
            float correctedRadius = this.radius * this.form.deviceDensity();
            canvas.drawCircle(correctedXLeft + correctedRadius, correctedYTop + correctedRadius, correctedRadius, this.paint);
        }
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    public int Height() {
        return this.radius * 2;
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    public void Height(int height) {
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    public void HeightPercent(int pCent) {
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    public int Width() {
        return this.radius * 2;
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    public void Width(int width) {
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    public void WidthPercent(int pCent) {
    }

    @Override // com.google.appinventor.components.runtime.Sprite
    public boolean containsPoint(double qx, double qy) {
        return ((qx - this.xCenter) * (qx - this.xCenter)) + ((qy - this.yCenter) * (qy - this.yCenter)) <= ((double) (this.radius * this.radius));
    }

    @SimpleProperty(description = "The distance from the edge of the Ball to its center.")
    @DesignerProperty(defaultValue = "5", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void Radius(int radius) {
        int dr = radius - this.radius;
        if (this.originAtCenter) {
            this.xLeft -= dr;
            this.yTop -= dr;
        }
        this.radius = radius;
        registerChange();
    }

    @SimpleProperty
    public int Radius() {
        return this.radius;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The color of the Ball.")
    public int PaintColor() {
        return this.paintColor;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_BLACK, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void PaintColor(int argb) {
        this.paintColor = argb;
        if (argb != 0) {
            PaintUtil.changePaint(this.paint, argb);
        } else {
            PaintUtil.changePaint(this.paint, -16777216);
        }
        registerChange();
    }

    @Override // com.google.appinventor.components.runtime.Sprite
    @SimpleProperty(description = "Whether the x- and y-coordinates should represent the center of the Ball (true) or its left and top edges (false).", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void OriginAtCenter(boolean b) {
        super.OriginAtCenter(b);
    }

    @Override // com.google.appinventor.components.runtime.Sprite
    @SimpleProperty(description = "The horizontal coordinate of the Ball, increasing as the Ball moves right. If the property OriginAtCenter is true, the coordinate is for the center of the Ball; otherwise, it is for the leftmost point of the Ball.")
    public double X() {
        return super.X();
    }

    @Override // com.google.appinventor.components.runtime.Sprite
    @SimpleProperty(description = "The vertical coordinate of the Ball, increasing as the Ball moves down. If the property OriginAtCenter is true, the coordinate is for the center of the Ball; otherwise, it is for the uppermost point of the Ball.")
    public double Y() {
        return super.Y();
    }

    @Override // com.google.appinventor.components.runtime.Sprite
    @SimpleFunction(description = "Sets the x and y coordinates of the Ball. If CenterAtOrigin is true, the center of the Ball will be placed here. Otherwise, the top left edge of the Ball will be placed at the specified coordinates.")
    public void MoveTo(double x, double y) {
        super.MoveTo(x, y);
    }
}
