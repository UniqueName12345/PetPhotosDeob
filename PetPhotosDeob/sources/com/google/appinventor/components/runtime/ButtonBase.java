package com.google.appinventor.components.runtime;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.google.appinventor.components.annotations.Asset;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.IceCreamSandwichUtil;
import com.google.appinventor.components.runtime.util.KitkatUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.TextViewUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.io.IOException;

@UsesPermissions(permissionNames = "android.permission.INTERNET")
@SimpleObject
/* loaded from: classes.dex */
public abstract class ButtonBase extends AndroidViewComponent implements View.OnClickListener, View.OnFocusChangeListener, View.OnLongClickListener, View.OnTouchListener, AccessibleComponent {
    private static final String LOG_TAG = "ButtonBase";
    private static final float ROUNDED_CORNERS_RADIUS = 10.0f;
    private static final int SHAPED_DEFAULT_BACKGROUND_COLOR = -3355444;
    private Bitmap backgroundBitmap;
    private int backgroundColor;
    private Drawable backgroundImageDrawable;
    private boolean bold;
    private Drawable defaultButtonDrawable;
    private ColorStateList defaultColorStateList;
    private String fontTypeface;
    private String imagePath;
    private boolean isBigText;
    private boolean isHighContrast;
    private boolean italic;
    private Drawable myBackgroundDrawable;
    private int shape;
    private boolean showFeedback;
    private int textAlignment;
    private int textColor;
    private final android.widget.Button view;
    private static final float[] ROUNDED_CORNERS_ARRAY = {10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f};
    private static int defaultButtonMinWidth = 0;
    private static int defaultButtonMinHeight = 0;

    public abstract void click();

    public ButtonBase(ComponentContainer container) {
        super(container);
        this.showFeedback = true;
        this.imagePath = "";
        this.myBackgroundDrawable = null;
        this.isHighContrast = false;
        this.isBigText = false;
        this.view = new android.widget.Button(container.$context());
        this.defaultButtonDrawable = this.view.getBackground();
        this.defaultColorStateList = this.view.getTextColors();
        defaultButtonMinWidth = KitkatUtil.getMinWidth(this.view);
        defaultButtonMinHeight = KitkatUtil.getMinHeight(this.view);
        container.$add(this);
        this.view.setOnClickListener(this);
        this.view.setOnFocusChangeListener(this);
        this.view.setOnLongClickListener(this);
        this.view.setOnTouchListener(this);
        IceCreamSandwichUtil.setAllCaps(this.view, false);
        TextAlignment(1);
        BackgroundColor(0);
        Image("");
        Enabled(true);
        this.fontTypeface = Component.TYPEFACE_DEFAULT;
        TextViewUtil.setFontTypeface(container.$form(), this.view, this.fontTypeface, this.bold, this.italic);
        FontSize(14.0f);
        Text("");
        TextColor(0);
        Shape(0);
    }

    public void Initialize() {
        updateAppearance();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent me) {
        if (me.getAction() == 0) {
            if (ShowFeedback() && (AppInventorCompatActivity.isClassicMode() || Build.VERSION.SDK_INT < 21)) {
                view.getBackground().setAlpha(70);
                view.invalidate();
            }
            TouchDown();
            return false;
        } else if (me.getAction() == 1 || me.getAction() == 3) {
            if (ShowFeedback() && (AppInventorCompatActivity.isClassicMode() || Build.VERSION.SDK_INT < 21)) {
                view.getBackground().setAlpha(255);
                view.invalidate();
            }
            TouchUp();
            return false;
        } else {
            return false;
        }
    }

    @Override // com.google.appinventor.components.runtime.AndroidViewComponent
    public View getView() {
        return this.view;
    }

    @SimpleEvent(description = "Indicates that the %type% was pressed down.")
    public void TouchDown() {
        EventDispatcher.dispatchEvent(this, "TouchDown", new Object[0]);
    }

    @SimpleEvent(description = "Indicates that the %type% has been released.")
    public void TouchUp() {
        EventDispatcher.dispatchEvent(this, "TouchUp", new Object[0]);
    }

    @SimpleEvent(description = "Indicates the cursor moved over the %type% so it is now possible to click it.")
    public void GotFocus() {
        EventDispatcher.dispatchEvent(this, "GotFocus", new Object[0]);
    }

    @SimpleEvent(description = "Indicates the cursor moved away from the %type% so it is now no longer possible to click it.")
    public void LostFocus() {
        EventDispatcher.dispatchEvent(this, "LostFocus", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Left, center, or right.", userVisible = JSONzip.probe)
    public int TextAlignment() {
        return this.textAlignment;
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = Component.TYPEFACE_SANSSERIF, editorType = PropertyTypeConstants.PROPERTY_TYPE_TEXTALIGNMENT)
    public void TextAlignment(int alignment) {
        this.textAlignment = alignment;
        TextViewUtil.setAlignment(this.view, alignment, true);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = JSONzip.probe)
    public int Shape() {
        return this.shape;
    }

    @SimpleProperty(description = "Specifies the shape of the %type% (default, rounded, rectangular, oval). The shape will not be visible if an Image is being displayed.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_BUTTON_SHAPE)
    public void Shape(int shape) {
        this.shape = shape;
        updateAppearance();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Image to display on button.")
    public String Image() {
        return this.imagePath;
    }

    @SimpleProperty(description = "Specifies the path of the image of the %type%.  If there is both an Image and a BackgroundColor, only the Image will be visible.")
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void Image(@Asset String path) {
        if (!path.equals(this.imagePath) || this.backgroundImageDrawable == null) {
            if (path == null) {
                path = "";
            }
            this.imagePath = path;
            this.backgroundImageDrawable = null;
            if (this.imagePath.length() > 0) {
                try {
                    this.backgroundImageDrawable = MediaUtil.getBitmapDrawable(this.container.$form(), this.imagePath);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Unable to load " + this.imagePath);
                }
            }
            updateAppearance();
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns the button's background color")
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @SimpleProperty(description = "Specifies the background color of the %type%. The background color will not be visible if an Image is being displayed.")
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
        updateAppearance();
    }

    private void updateAppearance() {
        if (this.backgroundImageDrawable == null) {
            if (this.shape == 0) {
                if (this.backgroundColor == 0) {
                    if (this.isHighContrast || this.container.$form().HighContrast()) {
                        ViewUtil.setBackgroundDrawable(this.view, null);
                        ViewUtil.setBackgroundDrawable(this.view, getSafeBackgroundDrawable());
                        this.view.getBackground().setColorFilter(-16777216, PorterDuff.Mode.SRC_ATOP);
                    } else {
                        ViewUtil.setBackgroundDrawable(this.view, this.defaultButtonDrawable);
                    }
                } else if (this.backgroundColor == 16777215) {
                    ViewUtil.setBackgroundDrawable(this.view, null);
                    ViewUtil.setBackgroundDrawable(this.view, getSafeBackgroundDrawable());
                    this.view.getBackground().setColorFilter(this.backgroundColor, PorterDuff.Mode.CLEAR);
                } else {
                    ViewUtil.setBackgroundDrawable(this.view, null);
                    ViewUtil.setBackgroundDrawable(this.view, getSafeBackgroundDrawable());
                    this.view.getBackground().setColorFilter(this.backgroundColor, PorterDuff.Mode.SRC_ATOP);
                }
            } else {
                setShape();
            }
            TextViewUtil.setMinSize(this.view, defaultButtonMinWidth, defaultButtonMinHeight);
        } else if (this.shape == 0) {
            ViewUtil.setBackgroundImage(this.view, this.backgroundImageDrawable);
        } else {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            this.backgroundBitmap = ((BitmapDrawable) this.backgroundImageDrawable).getBitmap();
            float displayDensity = this.view.getContext().getResources().getDisplayMetrics().density;
            int shapeHeight = Math.round(this.backgroundImageDrawable.getIntrinsicHeight() * displayDensity);
            int shapeWidth = Math.round(this.backgroundImageDrawable.getIntrinsicWidth() * displayDensity);
            Bitmap result = Bitmap.createBitmap(shapeWidth, shapeHeight, Bitmap.Config.ARGB_8888);
            android.graphics.Canvas canvas = new android.graphics.Canvas(result);
            switch (this.shape) {
                case 1:
                    canvas.drawRoundRect(new RectF(0.0f, 0.0f, shapeWidth, shapeHeight), 100.0f, 100.0f, paint);
                    break;
                case 2:
                    canvas.drawRect(new RectF(0.0f, 0.0f, shapeWidth, shapeHeight), paint);
                    break;
                case 3:
                    canvas.drawOval(new RectF(0.0f, 0.0f, shapeWidth, shapeHeight), paint);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(this.backgroundBitmap, (Rect) null, new Rect(0, 0, shapeWidth, shapeHeight), paint);
            ViewUtil.setBackgroundImage(this.view, new BitmapDrawable(result));
        }
    }

    private Drawable getSafeBackgroundDrawable() {
        if (this.myBackgroundDrawable == null) {
            Drawable.ConstantState state = this.defaultButtonDrawable.getConstantState();
            if (state != null && Build.VERSION.SDK_INT >= 10) {
                try {
                    this.myBackgroundDrawable = state.newDrawable().mutate();
                } catch (NullPointerException e) {
                    Log.e(LOG_TAG, "Unable to clone button drawable", e);
                    this.myBackgroundDrawable = this.defaultButtonDrawable;
                }
            } else {
                this.myBackgroundDrawable = this.defaultButtonDrawable;
            }
        }
        return this.myBackgroundDrawable;
    }

    private ColorStateList createRippleState() {
        int[][] states = {new int[]{16842910}};
        int enabled_color = this.defaultColorStateList.getColorForState(this.view.getDrawableState(), 16842910);
        int[] colors = {Color.argb(70, Color.red(enabled_color), Color.green(enabled_color), Color.blue(enabled_color))};
        return new ColorStateList(states, colors);
    }

    private void setShape() {
        ShapeDrawable drawable = new ShapeDrawable();
        switch (this.shape) {
            case 1:
                drawable.setShape(new RoundRectShape(ROUNDED_CORNERS_ARRAY, null, null));
                break;
            case 2:
                drawable.setShape(new RectShape());
                break;
            case 3:
                drawable.setShape(new OvalShape());
                break;
            default:
                throw new IllegalArgumentException();
        }
        if (!AppInventorCompatActivity.isClassicMode() && Build.VERSION.SDK_INT >= 21) {
            ViewUtil.setBackgroundDrawable(this.view, new RippleDrawable(createRippleState(), drawable, drawable));
        } else {
            ViewUtil.setBackgroundDrawable(this.view, drawable);
        }
        if (this.backgroundColor == 16777215) {
            this.view.getBackground().setColorFilter(this.backgroundColor, PorterDuff.Mode.CLEAR);
        } else if (this.backgroundColor == 0) {
            if (this.isHighContrast || this.container.$form().HighContrast()) {
                this.view.getBackground().setColorFilter(-16777216, PorterDuff.Mode.SRC_ATOP);
            } else {
                this.view.getBackground().setColorFilter(-3355444, PorterDuff.Mode.SRC_ATOP);
            }
        } else {
            this.view.getBackground().setColorFilter(this.backgroundColor, PorterDuff.Mode.SRC_ATOP);
        }
        this.view.invalidate();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "If set, user can tap %type% to cause action.")
    public boolean Enabled() {
        return TextViewUtil.isEnabled(this.view);
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Enabled(boolean enabled) {
        TextViewUtil.setEnabled(this.view, enabled);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If set, %type% text is displayed in bold.")
    public boolean FontBold() {
        return this.bold;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void FontBold(boolean bold) {
        this.bold = bold;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, bold, this.italic);
    }

    @SimpleProperty(description = "Specifies if a visual feedback should be shown  for a %type% that has an image as background.")
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void ShowFeedback(boolean showFeedback) {
        this.showFeedback = showFeedback;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns the visual feedback state of the %type%")
    public boolean ShowFeedback() {
        return this.showFeedback;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If set, %type% text is displayed in italics.")
    public boolean FontItalic() {
        return this.italic;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void FontItalic(boolean italic) {
        this.italic = italic;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, this.bold, italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Point size for %type% text.")
    public float FontSize() {
        return TextViewUtil.getFontSize(this.view, this.container.$context());
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    @DesignerProperty(defaultValue = "14.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_FLOAT)
    public void FontSize(float size) {
        if (size == 14.0f && this.container.$form().BigDefaultText()) {
            TextViewUtil.setFontSize(this.view, 24.0f);
        } else {
            TextViewUtil.setFontSize(this.view, size);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Font family for %type% text.", userVisible = JSONzip.probe)
    public String FontTypeface() {
        return this.fontTypeface;
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_TYPEFACE)
    public void FontTypeface(String typeface) {
        this.fontTypeface = typeface;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, this.bold, this.italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Text to display on %type%.")
    public String Text() {
        return TextViewUtil.getText(this.view);
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void Text(String text) {
        TextViewUtil.setText(this.view, text);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Color for button text.")
    public int TextColor() {
        return this.textColor;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void TextColor(int argb) {
        this.textColor = argb;
        if (argb != 0) {
            TextViewUtil.setTextColor(this.view, argb);
        } else if (this.isHighContrast || this.container.$form().HighContrast()) {
            TextViewUtil.setTextColor(this.view, -1);
        } else {
            TextViewUtil.setTextColors(this.view, this.defaultColorStateList);
        }
    }

    public boolean longClick() {
        return false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        click();
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View previouslyFocused, boolean gainFocus) {
        if (gainFocus) {
            GotFocus();
        } else {
            LostFocus();
        }
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        return longClick();
    }

    @Override // com.google.appinventor.components.runtime.AccessibleComponent
    public void setHighContrast(boolean isHighContrast) {
        if (this.backgroundImageDrawable == null && this.shape == 0 && this.backgroundColor == 0) {
            if (isHighContrast) {
                ViewUtil.setBackgroundDrawable(this.view, null);
                ViewUtil.setBackgroundDrawable(this.view, getSafeBackgroundDrawable());
                this.view.getBackground().setColorFilter(-16777216, PorterDuff.Mode.SRC_ATOP);
            } else {
                ViewUtil.setBackgroundDrawable(this.view, this.defaultButtonDrawable);
            }
        }
        if (this.textColor == 0) {
            if (isHighContrast) {
                TextViewUtil.setTextColor(this.view, -1);
            } else {
                TextViewUtil.setTextColors(this.view, this.defaultColorStateList);
            }
        }
    }

    @Override // com.google.appinventor.components.runtime.AccessibleComponent
    public boolean getHighContrast() {
        return this.isHighContrast;
    }

    @Override // com.google.appinventor.components.runtime.AccessibleComponent
    public void setLargeFont(boolean isLargeFont) {
        if (TextViewUtil.getFontSize(this.view, this.container.$context()) == 24.0d || TextViewUtil.getFontSize(this.view, this.container.$context()) == 14.0f) {
            if (isLargeFont) {
                TextViewUtil.setFontSize(this.view, 24.0f);
            } else {
                TextViewUtil.setFontSize(this.view, 14.0f);
            }
        }
    }

    @Override // com.google.appinventor.components.runtime.AccessibleComponent
    public boolean getLargeFont() {
        return this.isBigText;
    }
}
