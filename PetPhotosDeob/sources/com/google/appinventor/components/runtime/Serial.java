package com.google.appinventor.components.runtime;

import android.content.Context;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.physicaloid.lib.Physicaloid;
import java.io.UnsupportedEncodingException;
import kawa.lang.SyntaxForms;

@DesignerComponent(androidMinSdk = 12, category = ComponentCategory.CONNECTIVITY, description = "Serial component which can be used to connect to devices like Arduino", iconName = "images/arduino.png", nonVisible = SyntaxForms.DEBUGGING, version = 1)
@UsesLibraries(libraries = "physicaloid.jar")
@SimpleObject
/* loaded from: classes.dex */
public class Serial extends AndroidNonvisibleComponent implements Component {
    private static final String LOG_TAG = "Serial Component";
    private int baudRate;
    private int bytes;
    private Context context;
    private Physicaloid mPhysicaloid;

    public Serial(ComponentContainer container) {
        super(container.$form());
        this.baudRate = 9600;
        this.bytes = 256;
        this.context = container.$context();
        Log.d(LOG_TAG, "Created");
    }

    @SimpleFunction(description = "Initializes serial connection.")
    public void InitializeSerial() {
        this.mPhysicaloid = new Physicaloid(this.context);
        BaudRate(this.baudRate);
        Log.d(LOG_TAG, "Initialized");
    }

    @SimpleFunction(description = "Opens serial connection. Returns true when opened.")
    public boolean OpenSerial() {
        Log.d(LOG_TAG, "Opening connection");
        if (this.mPhysicaloid == null) {
            this.form.dispatchErrorOccurredEvent(this, "OpenSerial", ErrorMessages.ERROR_SERIAL_NOT_INITIALIZED, new Object[0]);
            return false;
        }
        return this.mPhysicaloid.open();
    }

    @SimpleFunction(description = "Closes serial connection. Returns true when closed.")
    public boolean CloseSerial() {
        Log.d(LOG_TAG, "Closing connection");
        if (this.mPhysicaloid == null) {
            this.form.dispatchErrorOccurredEvent(this, "CloseSerial", ErrorMessages.ERROR_SERIAL_NOT_INITIALIZED, new Object[0]);
            return false;
        }
        return this.mPhysicaloid.close();
    }

    @SimpleFunction(description = "Reads data from serial.")
    public String ReadSerial() {
        if (this.mPhysicaloid == null) {
            this.form.dispatchErrorOccurredEvent(this, "ReadSerial", ErrorMessages.ERROR_SERIAL_NOT_INITIALIZED, new Object[0]);
            return "";
        }
        byte[] buf = new byte[this.bytes];
        if (this.mPhysicaloid.read(buf) <= 0) {
            return "";
        }
        try {
            String data = new String(buf, "UTF-8");
            return data;
        } catch (UnsupportedEncodingException mEr) {
            Log.e(LOG_TAG, mEr.getMessage());
            return "";
        }
    }

    @SimpleFunction(description = "Writes given data to serial.")
    public void WriteSerial(String data) {
        if (!data.isEmpty() && this.mPhysicaloid != null) {
            byte[] buf = data.getBytes();
            int result = this.mPhysicaloid.write(buf);
            if (result == -1) {
                this.form.dispatchErrorOccurredEvent(this, "WriteSerial", ErrorMessages.ERROR_SERIAL_WRITING, new Object[0]);
            }
        } else if (this.mPhysicaloid == null) {
            this.form.dispatchErrorOccurredEvent(this, "WriteSerial", ErrorMessages.ERROR_SERIAL_NOT_INITIALIZED, new Object[0]);
        }
    }

    @SimpleFunction(description = "Writes given data to serial, and appends a new line at the end.")
    public void PrintSerial(String data) {
        if (!data.isEmpty()) {
            WriteSerial(data + "\n");
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns true when the Serial connection is open.")
    public boolean IsOpen() {
        if (this.mPhysicaloid == null) {
            this.form.dispatchErrorOccurredEvent(this, "IsOpen", ErrorMessages.ERROR_SERIAL_NOT_INITIALIZED, new Object[0]);
            return false;
        }
        return this.mPhysicaloid.isOpened();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns true when the Serial has been initialized.")
    public boolean IsInitialized() {
        return this.mPhysicaloid != null;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the current baud rate")
    public int BaudRate() {
        return this.baudRate;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "9600", editorType = PropertyTypeConstants.PROPERTY_TYPE_INTEGER)
    public void BaudRate(int baudRate) {
        this.baudRate = baudRate;
        Log.d(LOG_TAG, "Baud Rate: " + baudRate);
        if (this.mPhysicaloid != null) {
            this.mPhysicaloid.setBaudrate(baudRate);
        } else {
            Log.w(LOG_TAG, "Could not set Serial Baud Rate to " + baudRate + ". Just saved, not applied to serial! Maybe you forgot to initialize it?");
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the buffer size in bytes")
    public int BufferSize() {
        return this.bytes;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "256", editorType = PropertyTypeConstants.PROPERTY_TYPE_INTEGER)
    public void BufferSize(int bytes) {
        this.bytes = bytes;
        Log.d(LOG_TAG, "Buffer Size: " + bytes);
    }
}
