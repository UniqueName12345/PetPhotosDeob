package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;

@SimpleObject
/* loaded from: classes.dex */
public class LegoMindstormsEv3Sensor extends LegoMindstormsEv3Base {
    protected static final String DEFAULT_SENSOR_PORT = "1";
    protected int sensorPortNumber;

    /* JADX INFO: Access modifiers changed from: protected */
    public LegoMindstormsEv3Sensor(ComponentContainer container, String logTag) {
        super(container, logTag);
        SensorPort("1");
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The sensor port that the sensor is connected to.", userVisible = JSONzip.probe)
    public String SensorPort() {
        return portNumberToSensorPortLetter(this.sensorPortNumber);
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "1", editorType = PropertyTypeConstants.PROPERTY_TYPE_LEGO_EV3_SENSOR_PORT)
    public void SensorPort(String sensorPortLetter) {
        setSensorPort("SensorPort", sensorPortLetter);
    }

    protected final void setSensorPort(String functionName, String sensorPortLetter) {
        try {
            this.sensorPortNumber = sensorPortLetterToPortNumber(sensorPortLetter);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_SENSOR_PORT, sensorPortLetter);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int readInputPercentage(String functionName, int layer, int no, int type, int mode) {
        if (layer < 0 || layer > 3 || no < 0 || no > 3 || mode < -1 || mode > 7) {
            throw new IllegalArgumentException();
        }
        byte[] command = Ev3BinaryParser.encodeDirectCommand((byte) -103, true, 1, 0, "ccccccg", (byte) 27, Byte.valueOf((byte) layer), Byte.valueOf((byte) no), Byte.valueOf((byte) type), Byte.valueOf((byte) mode), (byte) 1, (byte) 0);
        byte[] reply = sendCommand(functionName, command, true);
        if (reply != null && reply.length == 2 && reply[0] == 2) {
            return reply[1];
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final double readInputSI(String functionName, int layer, int no, int type, int mode) {
        if (layer < 0 || layer > 3 || no < 0 || no > 3 || mode < -1 || mode > 7) {
            throw new IllegalArgumentException();
        }
        byte[] command = Ev3BinaryParser.encodeDirectCommand((byte) -103, true, 4, 0, "ccccccg", (byte) 29, Byte.valueOf((byte) layer), Byte.valueOf((byte) no), Byte.valueOf((byte) type), Byte.valueOf((byte) mode), (byte) 1, (byte) 0);
        byte[] reply = sendCommand(functionName, command, true);
        if (reply != null && reply.length == 5 && reply[0] == 2) {
            Object[] values = Ev3BinaryParser.unpack("xf", reply);
            return ((Float) values[0]).floatValue();
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_INVALID_REPLY, new Object[0]);
        return -1.0d;
    }
}
