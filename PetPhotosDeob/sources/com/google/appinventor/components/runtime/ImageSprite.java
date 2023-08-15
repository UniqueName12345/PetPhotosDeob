package com.google.appinventor.components.runtime;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import com.google.appinventor.components.annotations.Asset;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.MediaUtil;
import java.io.IOException;

@UsesPermissions(permissionNames = "android.permission.INTERNET")
@DesignerComponent(category = ComponentCategory.ANIMATION, description = "<p>A 'sprite' that can be placed on a <code>Canvas</code>, where it can react to touches and drags, interact with other sprites (<code>Ball</code>s and other <code>ImageSprite</code>s) and the edge of the Canvas, and move according to its property values.  Its appearance is that of the image specified in its <code>Picture</code> property (unless its <code>Visible</code> property is <code>False</code>).</p> <p>To have an <code>ImageSprite</code> move 10 pixels to the left every 1000 milliseconds (one second), for example, you would set the <code>Speed</code> property to 10 [pixels], the <code>Interval</code> property to 1000 [milliseconds], the <code>Heading</code> property to 180 [degrees], and the <code>Enabled</code> property to <code>True</code>.  A sprite whose <code>Rotates</code> property is <code>True</code> will rotate its image as the sprite's <code>Heading</code> changes.  Checking for collisions with a rotated sprite currently checks the sprite's unrotated position so that collision checking will be inaccurate for tall narrow or short wide sprites that are rotated.  Any of the sprite properties can be changed at any time under program control.</p> ", version = 8)
@SimpleObject
/* loaded from: classes.dex */
public class ImageSprite extends Sprite {
    private BitmapDrawable drawable;
    private final Form form;
    private int heightHint;
    private String picturePath;
    private boolean rotates;
    private int widthHint;

    public ImageSprite(ComponentContainer container) {
        super(container);
        this.widthHint = -1;
        this.heightHint = -1;
        this.picturePath = "";
        this.form = container.$form();
        this.rotates = true;
    }

    @Override // com.google.appinventor.components.runtime.Sprite
    public void onDraw(android.graphics.Canvas canvas) {
        if (this.drawable != null && this.visible) {
            int xinit = (int) (((float) Math.round(this.xLeft)) * this.form.deviceDensity());
            int yinit = (int) (((float) Math.round(this.yTop)) * this.form.deviceDensity());
            int w = (int) (Width() * this.form.deviceDensity());
            int h = (int) (Height() * this.form.deviceDensity());
            this.drawable.setBounds(xinit, yinit, xinit + w, yinit + h);
            if (!this.rotates) {
                this.drawable.draw(canvas);
                return;
            }
            canvas.save();
            canvas.rotate((float) (-Heading()), (w / 2) + xinit, (h / 2) + yinit);
            this.drawable.draw(canvas);
            canvas.restore();
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The picture that determines the ImageSprite's appearance.")
    public String Picture() {
        return this.picturePath;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void Picture(@Asset String path) {
        if (path == null) {
            path = "";
        }
        this.picturePath = path;
        try {
            this.drawable = MediaUtil.getBitmapDrawable(this.form, this.picturePath);
        } catch (IOException e) {
            Log.e("ImageSprite", "Unable to load " + this.picturePath);
            this.drawable = null;
        }
        registerChange();
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty(description = "The height of the ImageSprite in pixels.")
    public int Height() {
        if (this.heightHint == -1 || this.heightHint == -2 || this.heightHint <= -1000) {
            if (this.drawable == null) {
                return 0;
            }
            return (int) (this.drawable.getBitmap().getHeight() / this.form.deviceDensity());
        }
        return this.heightHint;
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty
    public void Height(int height) {
        this.heightHint = height;
        registerChange();
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    public void HeightPercent(int pCent) {
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty(description = "The width of the ImageSprite in pixels.")
    public int Width() {
        if (this.widthHint == -1 || this.widthHint == -2 || this.widthHint <= -1000) {
            if (this.drawable == null) {
                return 0;
            }
            return (int) (this.drawable.getBitmap().getWidth() / this.form.deviceDensity());
        }
        return this.widthHint;
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty
    public void Width(int width) {
        this.widthHint = width;
        registerChange();
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    public void WidthPercent(int pCent) {
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the image should rotate to match the ImageSprite's heading. The sprite rotates around its centerpoint.")
    public boolean Rotates() {
        return this.rotates;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Rotates(boolean rotates) {
        this.rotates = rotates;
        registerChange();
    }

    @Override // com.google.appinventor.components.runtime.Sprite
    @SimpleProperty(description = "The horizontal coordinate of the left edge of the ImageSprite, increasing as the ImageSprite moves right.")
    public double X() {
        return super.X();
    }

    @Override // com.google.appinventor.components.runtime.Sprite
    @SimpleProperty(description = "The vertical coordinate of the top edge of the ImageSprite, increasing as the ImageSprite moves down.")
    public double Y() {
        return super.Y();
    }

    @Override // com.google.appinventor.components.runtime.Sprite
    @SimpleFunction(description = "Moves the ImageSprite so that its left top corner is at the specified x and y coordinates.")
    public void MoveTo(double x, double y) {
        super.MoveTo(x, y);
    }
}
