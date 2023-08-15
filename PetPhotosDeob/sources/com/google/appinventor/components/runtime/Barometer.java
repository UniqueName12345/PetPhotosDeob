package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.SENSORS, description = "A sensor component that can measure the ambient air pressure.", iconName = "images/barometer.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@SimpleObject
/* loaded from: classes.dex */
public class Barometer extends SingleValueSensor {
    public Barometer(ComponentContainer container) {
        super(container.$form(), 6);
    }

    @Override // com.google.appinventor.components.runtime.SingleValueSensor
    protected void onValueChanged(float value) {
        AirPressureChanged(value);
    }

    @SimpleEvent
    public void AirPressureChanged(float pressure) {
        EventDispatcher.dispatchEvent(this, "AirPressureChanged", Float.valueOf(pressure));
    }

    @SimpleProperty(description = "The air pressure in hPa (millibar), if the sensor is available and enabled.")
    public float AirPressure() {
        return getValue();
    }
}
