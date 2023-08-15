package com.google.appinventor.components.runtime;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.EclairUtil;
import com.google.appinventor.components.runtime.util.TextViewUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;

@SimpleObject
/* loaded from: classes.dex */
public abstract class TextBoxBase extends AndroidViewComponent implements View.OnFocusChangeListener, AccessibleComponent {
    private int backgroundColor;
    private boolean bold;
    private Drawable defaultTextBoxDrawable;
    private String fontTypeface;
    private String hint;
    private int hintColorDefault;
    private boolean isBigText;
    private boolean isHighContrast;
    private boolean italic;
    private int textAlignment;
    private int textColor;
    protected final EditText view;

    public TextBoxBase(ComponentContainer container, EditText textview) {
        super(container);
        this.isHighContrast = false;
        this.isBigText = false;
        this.view = textview;
        this.hintColorDefault = this.view.getCurrentHintTextColor();
        if (Build.VERSION.SDK_INT >= 24) {
            EclairUtil.disableSuggestions(textview);
        }
        this.view.setOnFocusChangeListener(this);
        this.defaultTextBoxDrawable = this.view.getBackground();
        container.$add(this);
        container.setChildWidth(this, ComponentConstants.TEXTBOX_PREFERRED_WIDTH);
        TextAlignment(0);
        Enabled(true);
        this.fontTypeface = Component.TYPEFACE_DEFAULT;
        TextViewUtil.setFontTypeface(container.$form(), this.view, this.fontTypeface, this.bold, this.italic);
        FontSize(14.0f);
        Hint("");
        if (this.isHighContrast || container.$form().HighContrast()) {
            this.view.setHintTextColor(-256);
        } else {
            this.view.setHintTextColor(this.hintColorDefault);
        }
        Text("");
        TextColor(0);
        BackgroundColor(0);
    }

    @Override // com.google.appinventor.components.runtime.AndroidViewComponent
    public View getView() {
        return this.view;
    }

    @SimpleEvent(description = "Event raised when the %type% is selected for input, such as by the user touching it.")
    public void GotFocus() {
        EventDispatcher.dispatchEvent(this, "GotFocus", new Object[0]);
    }

    @SimpleEvent(description = "Event raised when the %type% is no longer selected for input, such as if the user touches a different text box.")
    public void LostFocus() {
        EventDispatcher.dispatchEvent(this, "LostFocus", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Whether the text should be left justified, centered, or right justified.  By default, text is left justified.", userVisible = JSONzip.probe)
    public int TextAlignment() {
        return this.textAlignment;
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_TEXTALIGNMENT)
    public void TextAlignment(int alignment) {
        this.textAlignment = alignment;
        TextViewUtil.setAlignment(this.view, alignment, false);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The background color of the input box.  You can choose a color by name in the Designer or in the Blocks Editor.  The default background color is 'default' (shaded 3-D look).")
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
        if (argb != 0) {
            TextViewUtil.setBackgroundColor(this.view, argb);
        } else if (this.isHighContrast || this.container.$form().$form().HighContrast()) {
            TextViewUtil.setBackgroundColor(this.view, -16777216);
        } else {
            ViewUtil.setBackgroundDrawable(this.view, this.defaultTextBoxDrawable);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the user can enter text into the %type%.  By default, this is true.")
    public boolean Enabled() {
        return TextViewUtil.isEnabled(this.view);
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Enabled(boolean enabled) {
        TextViewUtil.setEnabled(this.view, enabled);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Whether the font for the text should be bold.  By default, it is not.", userVisible = JSONzip.probe)
    public boolean FontBold() {
        return this.bold;
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void FontBold(boolean bold) {
        this.bold = bold;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, bold, this.italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Whether the text should appear in italics.  By default, it does not.", userVisible = JSONzip.probe)
    public boolean FontItalic() {
        return this.italic;
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void FontItalic(boolean italic) {
        this.italic = italic;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, this.bold, italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The font size for the text.  By default, it is 14.0 points.")
    public float FontSize() {
        return TextViewUtil.getFontSize(this.view, this.container.$context());
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "14.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_FLOAT)
    public void FontSize(float size) {
        if (size == 14.0f && this.container.$form().BigDefaultText()) {
            TextViewUtil.setFontSize(this.view, 24.0f);
        } else {
            TextViewUtil.setFontSize(this.view, size);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The font for the text.  The value can be changed in the Designer.", userVisible = JSONzip.probe)
    public String FontTypeface() {
        return this.fontTypeface;
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_TYPEFACE)
    public void FontTypeface(String typeface) {
        this.fontTypeface = typeface;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, this.bold, this.italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Text that should appear faintly in the %type% to provide a hint as to what the user should enter.  This can only be seen if the Text property is empty.")
    public String Hint() {
        return this.hint;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void Hint(String hint) {
        this.hint = hint;
        this.view.setHint(hint);
        this.view.invalidate();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Text() {
        return TextViewUtil.getText(this.view);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The text in the %type%, which can be set by the programmer in the Designer or Blocks Editor, or it can be entered by the user (unless the <code>Enabled</code> property is false).")
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_TEXTAREA)
    public void Text(String text) {
        TextViewUtil.setText(this.view, text);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The color for the text.  You can choose a color by name in the Designer or in the Blocks Editor.  The default text color is black.")
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
            TextViewUtil.setTextColor(this.view, this.container.$form().isDarkTheme() ? -1 : -16777216);
        }
    }

    @SimpleFunction(description = "Sets the %type% active.")
    public void RequestFocus() {
        this.view.requestFocus();
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View previouslyFocused, boolean gainFocus) {
        if (gainFocus) {
            GotFocus();
        } else {
            LostFocus();
        }
    }

    @Override // com.google.appinventor.components.runtime.AccessibleComponent
    public void setHighContrast(boolean isHighContrast) {
        if (this.backgroundColor == 0) {
            if (isHighContrast) {
                TextViewUtil.setBackgroundColor(this.view, -16777216);
            } else {
                ViewUtil.setBackgroundDrawable(this.view, this.defaultTextBoxDrawable);
            }
        }
        if (this.textColor == 0) {
            if (isHighContrast) {
                TextViewUtil.setTextColor(this.view, -1);
                this.view.setHintTextColor(-256);
                return;
            }
            TextViewUtil.setTextColor(this.view, this.container.$form().isDarkTheme() ? -1 : -16777216);
            this.view.setHintTextColor(this.hintColorDefault);
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
