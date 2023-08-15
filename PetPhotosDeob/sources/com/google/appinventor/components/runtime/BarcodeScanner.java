package com.google.appinventor.components.runtime;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesActivities;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.annotations.UsesQueries;
import com.google.appinventor.components.annotations.androidmanifest.ActionElement;
import com.google.appinventor.components.annotations.androidmanifest.ActivityElement;
import com.google.appinventor.components.annotations.androidmanifest.IntentFilterElement;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SdkLevel;
import kawa.lang.SyntaxForms;

@SimpleObject
@UsesActivities(activities = {@ActivityElement(configChanges = "orientation|keyboardHidden", name = BarcodeScanner.LOCAL_SCAN, screenOrientation = "landscape", stateNotNeeded = "true", theme = "@android:style/Theme.NoTitleBar.Fullscreen", windowSoftInputMode = "stateAlwaysHidden")})
@UsesPermissions(permissionNames = "android.permission.CAMERA")
@UsesQueries(intents = {@IntentFilterElement(actionElements = {@ActionElement(name = BarcodeScanner.SCAN_INTENT)})})
@DesignerComponent(category = ComponentCategory.SENSORS, description = "Component for using the Barcode Scanner to read a barcode", iconName = "images/barcodeScanner.png", nonVisible = SyntaxForms.DEBUGGING, version = 2)
@UsesLibraries(libraries = "Barcode.jar,QRGenerator.jar")
/* loaded from: classes.dex */
public class BarcodeScanner extends AndroidNonvisibleComponent implements ActivityResultListener, Component {
    private static final String LOCAL_SCAN = "com.google.zxing.client.android.AppInvCaptureActivity";
    private static final String SCANNER_RESULT_NAME = "SCAN_RESULT";
    private static final String SCAN_INTENT = "com.google.zxing.client.android.SCAN";
    private final ComponentContainer container;
    private boolean havePermission;
    private int requestCode;
    private String result;
    private boolean useExternalScanner;

    public BarcodeScanner(ComponentContainer container) {
        super(container.$form());
        this.result = "";
        this.useExternalScanner = true;
        this.havePermission = false;
        this.container = container;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Text result of the previous scan.")
    public String Result() {
        return this.result;
    }

    @SimpleFunction(description = "Begins a barcode scan, using the camera. When the scan is complete, the AfterScan event will be raised.")
    public void DoScan() {
        Intent intent = new Intent(SCAN_INTENT);
        if (!this.useExternalScanner && SdkLevel.getLevel() >= 5) {
            if (!this.havePermission) {
                this.container.$form().askPermission("android.permission.CAMERA", new PermissionResultHandler() { // from class: com.google.appinventor.components.runtime.BarcodeScanner.1
                    @Override // com.google.appinventor.components.runtime.PermissionResultHandler
                    public void HandlePermissionResponse(String permission, boolean granted) {
                        if (granted) {
                            BarcodeScanner.this.havePermission = true;
                            BarcodeScanner.this.DoScan();
                            return;
                        }
                        BarcodeScanner.this.form.dispatchPermissionDeniedEvent(BarcodeScanner.this, "DoScan", "android.permission.CAMERA");
                    }
                });
                return;
            } else {
                String packageName = this.container.$form().getPackageName();
                intent.setComponent(new ComponentName(packageName, LOCAL_SCAN));
            }
        }
        if (this.requestCode == 0) {
            this.requestCode = this.form.registerForActivityResult(this);
        }
        try {
            this.container.$context().startActivityForResult(intent, this.requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            this.container.$form().dispatchErrorOccurredEvent(this, "BarcodeScanner", ErrorMessages.ERROR_NO_SCANNER_FOUND, "");
        }
    }

    @Override // com.google.appinventor.components.runtime.ActivityResultListener
    public void resultReturned(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == -1) {
            if (data.hasExtra(SCANNER_RESULT_NAME)) {
                this.result = data.getStringExtra(SCANNER_RESULT_NAME);
            } else {
                this.result = "";
            }
            AfterScan(this.result);
        }
    }

    @SimpleEvent
    public void AfterScan(String result) {
        EventDispatcher.dispatchEvent(this, "AfterScan", result);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "If true App Inventor will look for and use an external scanning program such as \"Bar Code Scanner.\"")
    public boolean UseExternalScanner() {
        return this.useExternalScanner;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void UseExternalScanner(boolean useExternalScanner) {
        this.useExternalScanner = useExternalScanner;
    }
}
