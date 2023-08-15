package com.google.appinventor.components.runtime;

import android.view.View;
import android.widget.CompoundButton;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.TextViewUtil;

@SimpleObject
/* loaded from: classes.dex */
public abstract class ToggleBase<T extends CompoundButton> extends AndroidViewComponent implements CompoundButton.OnCheckedChangeListener, View.OnFocusChangeListener, AccessibleComponent {
    private int backgroundColor;
    private boolean bold;
    private String fontTypeface;
    private boolean isBigText;
    private boolean italic;
    private int textColor;
    protected T view;

    public ToggleBase(ComponentContainer container) {
        super(container);
        this.isBigText = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initToggle() {
        this.view.setOnFocusChangeListener(this);
        this.view.setOnCheckedChangeListener(this);
        this.container.$add(this);
        BackgroundColor(16777215);
        Enabled(true);
        this.fontTypeface = Component.TYPEFACE_DEFAULT;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, this.bold, this.italic);
        FontSize(14.0f);
        Text("");
        TextColor(0);
    }

    @Override // com.google.appinventor.components.runtime.AndroidViewComponent
    public View getView() {
        return this.view;
    }

    @Override // com.google.appinventor.components.runtime.AccessibleComponent
    public void setHighContrast(boolean isHighContrast) {
    }

    @Override // com.google.appinventor.components.runtime.AccessibleComponent
    public boolean getHighContrast() {
        return false;
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

    @SimpleEvent(description = "User tapped and released the %type%.")
    public void Changed() {
        EventDispatcher.dispatchEvent(this, "Changed", new Object[0]);
    }

    @SimpleEvent(description = "%type% became the focused component.")
    public void GotFocus() {
        EventDispatcher.dispatchEvent(this, "GotFocus", new Object[0]);
    }

    @SimpleEvent(description = "%type% stopped being the focused component.")
    public void LostFocus() {
        EventDispatcher.dispatchEvent(this, "LostFocus", new Object[0]);
    }

    @SimpleProperty(description = "The background color of the %type% as an alpha-red-green-blue integer.")
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_NONE, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
        if (argb != 0) {
            TextViewUtil.setBackgroundColor(this.view, argb);
        } else {
            TextViewUtil.setBackgroundColor(this.view, 16777215);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @SimpleProperty(description = "True if the %type% is active and clickable.")
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Enabled(boolean enabled) {
        TextViewUtil.setEnabled(this.view, enabled);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Enabled() {
        return this.view.isEnabled();
    }

    @SimpleProperty(description = "Set to true if the text of the %type% should be bold.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void FontBold(boolean bold) {
        this.bold = bold;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, bold, this.italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = JSONzip.probe)
    public boolean FontBold() {
        return this.bold;
    }

    @SimpleProperty(description = "Set to true if the text of the %type% should be italic.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void FontItalic(boolean italic) {
        this.italic = italic;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, this.bold, italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = JSONzip.probe)
    public boolean FontItalic() {
        return this.italic;
    }

    @SimpleProperty(description = "Specifies the text font size of the %type% in scale-independent pixels.")
    @DesignerProperty(defaultValue = "14.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_FLOAT)
    public void FontSize(float size) {
        if (Math.abs(size - 14.0f) < 0.01d || Math.abs(size - 24.0f) < 0.01d) {
            if (this.isBigText || this.container.$form().BigDefaultText()) {
                TextViewUtil.setFontSize(this.view, 24.0f);
                return;
            } else {
                TextViewUtil.setFontSize(this.view, 14.0f);
                return;
            }
        }
        TextViewUtil.setFontSize(this.view, size);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public float FontSize() {
        return TextViewUtil.getFontSize(this.view, this.container.$context());
    }

    @SimpleProperty(description = "Specifies the text font face of the %type%.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_TYPEFACE)
    public void FontTypeface(String typeface) {
        this.fontTypeface = typeface;
        TextViewUtil.setFontTypeface(this.container.$form(), this.view, this.fontTypeface, this.bold, this.italic);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = JSONzip.probe)
    public String FontTypeface() {
        return this.fontTypeface;
    }

    @SimpleProperty(description = "Specifies the text displayed by the %type%.")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void Text(String text) {
        TextViewUtil.setText(this.view, text);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String Text() {
        return TextViewUtil.getText(this.view);
    }

    @SimpleProperty(description = "Specifies the text color of the %type% as an alpha-red-green-blue integer.")
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_BLACK, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void TextColor(int argb) {
        this.textColor = argb;
        if (argb != 0) {
            TextViewUtil.setTextColor(this.view, argb);
        } else {
            TextViewUtil.setTextColor(this.view, this.container.$form().isDarkTheme() ? -1 : -16777216);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int TextColor() {
        return this.textColor;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Changed();
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View previouslyFocused, boolean gainFocus) {
        if (gainFocus) {
            GotFocus();
        } else {
            LostFocus();
        }
    }
}
