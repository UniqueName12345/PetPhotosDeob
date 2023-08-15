package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;

@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "Checkbox that raises an event when the user clicks on it. There are many properties affecting its appearance that can be set in the Designer or Blocks Editor.", version = 2)
@SimpleObject
/* loaded from: classes.dex */
public final class CheckBox extends ToggleBase<android.widget.CheckBox> {
    public CheckBox(ComponentContainer container) {
        super(container);
        this.view = new android.widget.CheckBox(container.$context());
        Checked(false);
        initToggle();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "True if the box is checked, false otherwise.")
    public boolean Checked() {
        return ((android.widget.CheckBox) this.view).isChecked();
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Checked(boolean value) {
        ((android.widget.CheckBox) this.view).setChecked(value);
        ((android.widget.CheckBox) this.view).invalidate();
    }
}
