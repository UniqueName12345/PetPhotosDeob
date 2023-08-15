package com.google.appinventor.components.runtime;

import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;

@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "<p>A box for entering passwords.  This is the same as the ordinary <code>TextBox</code> component except this does not display the characters typed by the user.</p><p>The value of the text in the box can be found or set through the <code>Text</code> property. If blank, the <code>Hint</code> property, which appears as faint text in the box, can provide the user with guidance as to what to type.</p> <p>Text boxes are usually used with the <code>Button</code> component, with the user clicking on the button when text entry is complete.</p>", version = 5)
@SimpleObject
/* loaded from: classes.dex */
public final class PasswordTextBox extends TextBoxBase {
    private boolean acceptsNumbersOnly;
    private boolean passwordVisible;

    public PasswordTextBox(ComponentContainer container) {
        super(container, new EditText(container.$context()));
        this.view.setSingleLine(true);
        this.view.setTransformationMethod(new PasswordTransformationMethod());
        this.view.setImeOptions(6);
        PasswordVisible(false);
        NumbersOnly(false);
    }

    @SimpleProperty(description = "Visibility of password.")
    public void PasswordVisible(boolean visible) {
        this.passwordVisible = visible;
        setPasswordInputType(this.acceptsNumbersOnly, visible);
    }

    @SimpleProperty(description = "Visibility of password.")
    public boolean PasswordVisible() {
        return this.passwordVisible;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "If true, then this password text box accepts only numbers as keyboard input.  Numbers can include a decimal point and an optional leading minus sign.  This applies to keyboard input only.  Even if NumbersOnly is true, you can use [set Text to] to enter any text at all.")
    public boolean NumbersOnly() {
        return this.acceptsNumbersOnly;
    }

    @SimpleProperty(description = "If true, then this password text box accepts only numbers as keyboard input.  Numbers can include a decimal point and an optional leading minus sign.  This applies to keyboard input only.  Even if NumbersOnly is true, you can use [set Text to] to enter any text at all.")
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void NumbersOnly(boolean acceptsNumbersOnly) {
        this.acceptsNumbersOnly = acceptsNumbersOnly;
        setPasswordInputType(acceptsNumbersOnly, this.passwordVisible);
    }

    private void setPasswordInputType(boolean acceptsNumbersOnly, boolean passwordVisible) {
        if (passwordVisible && acceptsNumbersOnly) {
            this.view.setInputType(2);
        } else if (passwordVisible && !acceptsNumbersOnly) {
            this.view.setInputType(145);
        } else if (acceptsNumbersOnly && !passwordVisible) {
            this.view.setInputType(18);
        } else if (!acceptsNumbersOnly && !passwordVisible) {
            this.view.setInputType(129);
        }
    }
}
