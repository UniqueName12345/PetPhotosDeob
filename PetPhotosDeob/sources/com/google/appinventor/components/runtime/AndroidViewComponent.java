package com.google.appinventor.components.runtime;

import android.view.View;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.SimplePropertyCopier;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.ErrorMessages;

@SimpleObject
/* loaded from: classes.dex */
public abstract class AndroidViewComponent extends VisibleComponent {
    protected final ComponentContainer container;
    private int percentWidthHolder = -3;
    private int percentHeightHolder = -3;
    private int lastSetWidth = -3;
    private int lastSetHeight = -3;
    private int column = -1;
    private int row = -1;

    public abstract View getView();

    /* JADX INFO: Access modifiers changed from: protected */
    public AndroidViewComponent(ComponentContainer container) {
        this.container = container;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Specifies whether the %type% should be visible on the screen. Value is true if the %type% is showing and false if hidden.")
    public boolean Visible() {
        return getView().getVisibility() == 0;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_VISIBILITY)
    public void Visible(boolean visibility) {
        getView().setVisibility(visibility ? 0 : 8);
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty
    public int Width() {
        int zWidth = (int) (getView().getWidth() / this.container.$form().deviceDensity());
        return zWidth;
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty(description = "Specifies the horizontal width of the %type%, measured in pixels.")
    public void Width(int width) {
        this.container.setChildWidth(this, width);
        this.lastSetWidth = width;
        if (width <= -1000) {
            this.container.$form().registerPercentLength(this, width, Form.PercentStorageRecord.Dim.WIDTH);
        } else {
            this.container.$form().unregisterPercentLength(this, Form.PercentStorageRecord.Dim.WIDTH);
        }
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty(description = "Specifies the horizontal width of the %type% as a percentage of the width of the Screen.")
    public void WidthPercent(int pCent) {
        if (pCent < 0 || pCent > 100) {
            this.container.$form().dispatchErrorOccurredEvent(this, "WidthPercent", ErrorMessages.ERROR_BAD_PERCENT, Integer.valueOf(pCent));
            return;
        }
        int v = (-pCent) - 1000;
        Width(v);
    }

    public void setLastWidth(int width) {
        this.percentWidthHolder = width;
    }

    public int getSetWidth() {
        return this.percentWidthHolder == -3 ? Width() : this.percentWidthHolder;
    }

    public void setLastHeight(int height) {
        this.percentHeightHolder = height;
    }

    public int getSetHeight() {
        return this.percentHeightHolder == -3 ? Height() : this.percentHeightHolder;
    }

    @SimplePropertyCopier
    public void CopyWidth(AndroidViewComponent sourceComponent) {
        Width(sourceComponent.lastSetWidth);
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty
    public int Height() {
        return (int) (getView().getHeight() / this.container.$form().deviceDensity());
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty(description = "Specifies the vertical height of the %type%, measured in pixels.")
    public void Height(int height) {
        this.container.setChildHeight(this, height);
        this.lastSetHeight = height;
        if (height <= -1000) {
            this.container.$form().registerPercentLength(this, height, Form.PercentStorageRecord.Dim.HEIGHT);
        } else {
            this.container.$form().unregisterPercentLength(this, Form.PercentStorageRecord.Dim.HEIGHT);
        }
    }

    @Override // com.google.appinventor.components.runtime.VisibleComponent
    @SimpleProperty(description = "Specifies the vertical height of the %type% as a percentage of the height of the Screen.")
    public void HeightPercent(int pCent) {
        if (pCent < 0 || pCent > 100) {
            this.container.$form().dispatchErrorOccurredEvent(this, "HeightPercent", ErrorMessages.ERROR_BAD_PERCENT, Integer.valueOf(pCent));
            return;
        }
        int v = (-pCent) - 1000;
        Height(v);
    }

    @SimplePropertyCopier
    public void CopyHeight(AndroidViewComponent sourceComponent) {
        Height(sourceComponent.lastSetHeight);
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    public int Column() {
        return this.column;
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    public void Column(int column) {
        this.column = column;
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    public int Row() {
        return this.row;
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    public void Row(int row) {
        this.row = row;
    }

    @Override // com.google.appinventor.components.runtime.Component
    public HandlesEventDispatching getDispatchDelegate() {
        return this.container.$form();
    }
}
