package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.BluetoothReflection;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3Constants;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

@SimpleObject
/* loaded from: classes.dex */
public abstract class BluetoothConnectionBase extends AndroidNonvisibleComponent implements Component, OnDestroyListener, Deleteable {
    private final List<BluetoothConnectionListener> bluetoothConnectionListeners;
    private ByteOrder byteOrder;
    private Object connectedBluetoothSocket;
    private byte delimiter;
    protected boolean disconnectOnError;
    private String encoding;
    private InputStream inputStream;
    protected final String logTag;
    private OutputStream outputStream;
    private final int sdkLevel;
    protected boolean secure;

    /* JADX INFO: Access modifiers changed from: protected */
    public BluetoothConnectionBase(ComponentContainer container, String logTag) {
        this(container.$form(), logTag, SdkLevel.getLevel());
        this.form.registerForOnDestroy(this);
    }

    private BluetoothConnectionBase(Form form, String logTag, int sdkLevel) {
        super(form);
        this.bluetoothConnectionListeners = new ArrayList();
        this.logTag = logTag;
        this.sdkLevel = sdkLevel;
        this.disconnectOnError = false;
        HighByteFirst(false);
        CharacterEncoding("UTF-8");
        DelimiterByte(0);
        Secure(true);
    }

    protected BluetoothConnectionBase(OutputStream outputStream, InputStream inputStream) {
        this(null, null, 7);
        this.connectedBluetoothSocket = "Not Null";
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addBluetoothConnectionListener(BluetoothConnectionListener listener) {
        this.bluetoothConnectionListeners.add(listener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeBluetoothConnectionListener(BluetoothConnectionListener listener) {
        this.bluetoothConnectionListeners.remove(listener);
    }

    private void fireAfterConnectEvent() {
        for (BluetoothConnectionListener listener : this.bluetoothConnectionListeners) {
            listener.afterConnect(this);
        }
    }

    private void fireBeforeDisconnectEvent() {
        for (BluetoothConnectionListener listener : this.bluetoothConnectionListeners) {
            listener.beforeDisconnect(this);
        }
    }

    public final void Initialize() {
    }

    @SimpleEvent(description = "The BluetoothError event is no longer used. Please use the Screen.ErrorOccurred event instead.", userVisible = JSONzip.probe)
    public void BluetoothError(String functionName, String message) {
    }

    protected void bluetoothError(String functionName, int errorNumber, Object... messageArgs) {
        this.form.dispatchErrorOccurredEvent(this, functionName, errorNumber, messageArgs);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether Bluetooth is available on the device")
    public boolean Available() {
        Object bluetoothAdapter = BluetoothReflection.getBluetoothAdapter();
        return bluetoothAdapter != null;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether Bluetooth is enabled")
    public boolean Enabled() {
        Object bluetoothAdapter = BluetoothReflection.getBluetoothAdapter();
        return bluetoothAdapter != null && BluetoothReflection.isBluetoothEnabled(bluetoothAdapter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setConnection(Object bluetoothSocket) throws IOException {
        this.connectedBluetoothSocket = bluetoothSocket;
        this.inputStream = new BufferedInputStream(BluetoothReflection.getInputStream(this.connectedBluetoothSocket));
        this.outputStream = new BufferedOutputStream(BluetoothReflection.getOutputStream(this.connectedBluetoothSocket));
        fireAfterConnectEvent();
    }

    @SimpleFunction(description = "Disconnect from the connected Bluetooth device.")
    public final void Disconnect() {
        if (this.connectedBluetoothSocket != null) {
            fireBeforeDisconnectEvent();
            try {
                BluetoothReflection.closeBluetoothSocket(this.connectedBluetoothSocket);
                Log.i(this.logTag, "Disconnected from Bluetooth device.");
            } catch (IOException e) {
                Log.w(this.logTag, "Error while disconnecting: " + e.getMessage());
            }
            this.connectedBluetoothSocket = null;
        }
        this.inputStream = null;
        this.outputStream = null;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "On devices with API level 14 (LEVEL_ICE_CREAM_SANDWICH) or higher, this property returned is accurate. But on old devices with API level lower than 14, it may not return the current state of connection(e.g., it might be disconnected but you may not know until you attempt to read/write the socket.")
    public final boolean IsConnected() {
        return this.sdkLevel >= 14 ? this.connectedBluetoothSocket != null && BluetoothReflection.isBluetoothSocketConnected(this.connectedBluetoothSocket) : this.connectedBluetoothSocket != null;
    }

    protected boolean DisconnectOnError() {
        return this.disconnectOnError;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether to invoke SSP (Simple Secure Pairing), which is supported on devices with Bluetooth v2.1 or higher. When working with embedded Bluetooth devices, this property may need to be set to False. For Android 2.0-2.2, this property setting will be ignored.")
    public boolean Secure() {
        return this.secure;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Secure(boolean secure) {
        this.secure = secure;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean HighByteFirst() {
        return this.byteOrder == ByteOrder.BIG_ENDIAN;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void HighByteFirst(boolean highByteFirst) {
        this.byteOrder = highByteFirst ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "UTF-8", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void CharacterEncoding(String encoding) {
        try {
            "check".getBytes(encoding);
            this.encoding = encoding;
        } catch (UnsupportedEncodingException e) {
            bluetoothError("CharacterEncoding", ErrorMessages.ERROR_BLUETOOTH_UNSUPPORTED_ENCODING, encoding);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String CharacterEncoding() {
        return this.encoding;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_DEFAULT, editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void DelimiterByte(int number) {
        byte b = (byte) number;
        int n = number >> 8;
        if (n != 0 && n != -1) {
            bluetoothError("DelimiterByte", 511, Integer.valueOf(number));
        } else {
            this.delimiter = b;
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public int DelimiterByte() {
        return this.delimiter;
    }

    @SimpleFunction(description = "Send text to the connected Bluetooth device.")
    public void SendText(String text) {
        byte[] bytes;
        try {
            bytes = text.getBytes(this.encoding);
        } catch (UnsupportedEncodingException e) {
            Log.w(this.logTag, "UnsupportedEncodingException: " + e.getMessage());
            bytes = text.getBytes();
        }
        write("SendText", bytes);
    }

    @SimpleFunction(description = "Send a 1-byte number to the connected Bluetooth device.")
    public void Send1ByteNumber(String number) {
        try {
            int n = Integer.decode(number).intValue();
            byte b = (byte) n;
            int n2 = n >> 8;
            if (n2 != 0 && n2 != -1) {
                bluetoothError("Send1ByteNumber", 511, number);
            } else {
                write("Send1ByteNumber", b);
            }
        } catch (NumberFormatException e) {
            bluetoothError("Send1ByteNumber", ErrorMessages.ERROR_BLUETOOTH_COULD_NOT_DECODE, number);
        }
    }

    @SimpleFunction(description = "Send a 2-byte number to the connected Bluetooth device.")
    public void Send2ByteNumber(String number) {
        int n;
        try {
            int n2 = Integer.decode(number).intValue();
            byte[] bytes = new byte[2];
            if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
                bytes[1] = (byte) (n2 & 255);
                n = n2 >> 8;
                bytes[0] = (byte) (n & 255);
            } else {
                bytes[0] = (byte) (n2 & 255);
                n = n2 >> 8;
                bytes[1] = (byte) (n & 255);
            }
            int n3 = n >> 8;
            if (n3 != 0 && n3 != -1) {
                bluetoothError("Send2ByteNumber", 512, number, 2);
            } else {
                write("Send2ByteNumber", bytes);
            }
        } catch (NumberFormatException e) {
            bluetoothError("Send2ByteNumber", ErrorMessages.ERROR_BLUETOOTH_COULD_NOT_DECODE, number);
        }
    }

    @SimpleFunction(description = "Send a 4-byte number to the connected Bluetooth device.")
    public void Send4ByteNumber(String number) {
        long n;
        try {
            long n2 = Long.decode(number).longValue();
            byte[] bytes = new byte[4];
            if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
                bytes[3] = (byte) (n2 & 255);
                long n3 = n2 >> 8;
                bytes[2] = (byte) (n3 & 255);
                long n4 = n3 >> 8;
                bytes[1] = (byte) (n4 & 255);
                n = n4 >> 8;
                bytes[0] = (byte) (n & 255);
            } else {
                bytes[0] = (byte) (n2 & 255);
                long n5 = n2 >> 8;
                bytes[1] = (byte) (n5 & 255);
                long n6 = n5 >> 8;
                bytes[2] = (byte) (n6 & 255);
                n = n6 >> 8;
                bytes[3] = (byte) (n & 255);
            }
            long n7 = n >> 8;
            if (n7 != 0 && n7 != -1) {
                bluetoothError("Send4ByteNumber", 512, number, 4);
            } else {
                write("Send4ByteNumber", bytes);
            }
        } catch (NumberFormatException e) {
            bluetoothError("Send4ByteNumber", ErrorMessages.ERROR_BLUETOOTH_COULD_NOT_DECODE, number);
        }
    }

    @SimpleFunction(description = "Send a list of byte values to the connected Bluetooth device.")
    public void SendBytes(YailList list) {
        Object[] array = list.toArray();
        byte[] bytes = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            Object element = array[i];
            String s = element.toString();
            try {
                int n = Integer.decode(s).intValue();
                bytes[i] = (byte) (n & 255);
                int n2 = n >> 8;
                if (n2 != 0 && n2 != -1) {
                    bluetoothError("SendBytes", ErrorMessages.ERROR_BLUETOOTH_COULD_NOT_FIT_ELEMENT_IN_BYTE, Integer.valueOf(i + 1));
                    return;
                }
            } catch (NumberFormatException e) {
                bluetoothError("SendBytes", 513, Integer.valueOf(i + 1));
                return;
            }
        }
        write("SendBytes", bytes);
    }

    protected void write(String functionName, byte b) {
        if (!IsConnected()) {
            bluetoothError(functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_CONNECTED_TO_DEVICE, new Object[0]);
            return;
        }
        try {
            this.outputStream.write(b);
            this.outputStream.flush();
        } catch (IOException e) {
            Log.e(this.logTag, "IO Exception during Writing" + e.getMessage());
            if (this.disconnectOnError) {
                Disconnect();
            }
            bluetoothError(functionName, ErrorMessages.ERROR_BLUETOOTH_UNABLE_TO_WRITE, e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void write(String functionName, byte[] bytes) {
        if (!IsConnected()) {
            bluetoothError(functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_CONNECTED_TO_DEVICE, new Object[0]);
            return;
        }
        try {
            this.outputStream.write(bytes);
            this.outputStream.flush();
        } catch (IOException e) {
            Log.e(this.logTag, "IO Exception during Writing" + e.getMessage());
            if (this.disconnectOnError) {
                Disconnect();
            }
            bluetoothError(functionName, ErrorMessages.ERROR_BLUETOOTH_UNABLE_TO_WRITE, e.getMessage());
        }
    }

    @SimpleFunction(description = "Returns an estimate of the number of bytes that can be received without blocking")
    public int BytesAvailableToReceive() {
        if (!IsConnected()) {
            bluetoothError("BytesAvailableToReceive", ErrorMessages.ERROR_BLUETOOTH_NOT_CONNECTED_TO_DEVICE, new Object[0]);
            return 0;
        }
        try {
            return this.inputStream.available();
        } catch (IOException e) {
            Log.e(this.logTag, "IO Exception during Getting Receive Availability " + e.getMessage());
            if (this.disconnectOnError) {
                Disconnect();
            }
            bluetoothError("BytesAvailableToReceive", ErrorMessages.ERROR_BLUETOOTH_UNABLE_TO_READ, e.getMessage());
            return 0;
        }
    }

    @SimpleFunction(description = "Receive text from the connected Bluetooth device. If numberOfBytes is less than 0, read until a delimiter byte value is received.")
    public String ReceiveText(int numberOfBytes) {
        String str;
        byte[] bytes = read("ReceiveText", numberOfBytes);
        try {
            if (numberOfBytes < 0) {
                str = new String(bytes, 0, bytes.length - 1, this.encoding);
            } else {
                str = new String(bytes, this.encoding);
            }
            return str;
        } catch (UnsupportedEncodingException e) {
            Log.w(this.logTag, "UnsupportedEncodingException: " + e.getMessage());
            return new String(bytes);
        }
    }

    @SimpleFunction(description = "Receive a signed 1-byte number from the connected Bluetooth device.")
    public int ReceiveSigned1ByteNumber() {
        byte[] bytes = read("ReceiveSigned1ByteNumber", 1);
        if (bytes.length != 1) {
            return 0;
        }
        return bytes[0];
    }

    @SimpleFunction(description = "Receive an unsigned 1-byte number from the connected Bluetooth device.")
    public int ReceiveUnsigned1ByteNumber() {
        byte[] bytes = read("ReceiveUnsigned1ByteNumber", 1);
        if (bytes.length != 1) {
            return 0;
        }
        return bytes[0] & Ev3Constants.Opcode.TST;
    }

    @SimpleFunction(description = "Receive a signed 2-byte number from the connected Bluetooth device.")
    public int ReceiveSigned2ByteNumber() {
        byte[] bytes = read("ReceiveSigned2ByteNumber", 2);
        if (bytes.length != 2) {
            return 0;
        }
        if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
            return (bytes[0] << 8) | (bytes[1] & Ev3Constants.Opcode.TST);
        }
        return (bytes[0] & Ev3Constants.Opcode.TST) | (bytes[1] << 8);
    }

    @SimpleFunction(description = "Receive a unsigned 2-byte number from the connected Bluetooth device.")
    public int ReceiveUnsigned2ByteNumber() {
        byte[] bytes = read("ReceiveUnsigned2ByteNumber", 2);
        if (bytes.length != 2) {
            return 0;
        }
        if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
            return ((bytes[0] & Ev3Constants.Opcode.TST) << 8) | (bytes[1] & Ev3Constants.Opcode.TST);
        }
        return (bytes[0] & Ev3Constants.Opcode.TST) | ((bytes[1] & Ev3Constants.Opcode.TST) << 8);
    }

    @SimpleFunction(description = "Receive a signed 4-byte number from the connected Bluetooth device.")
    public long ReceiveSigned4ByteNumber() {
        byte[] bytes = read("ReceiveSigned4ByteNumber", 4);
        if (bytes.length != 4) {
            return 0L;
        }
        if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
            return (bytes[3] & Ev3Constants.Opcode.TST) | ((bytes[2] & Ev3Constants.Opcode.TST) << 8) | ((bytes[1] & Ev3Constants.Opcode.TST) << 16) | (bytes[0] << 24);
        }
        return (bytes[0] & Ev3Constants.Opcode.TST) | ((bytes[1] & Ev3Constants.Opcode.TST) << 8) | ((bytes[2] & Ev3Constants.Opcode.TST) << 16) | (bytes[3] << 24);
    }

    @SimpleFunction(description = "Receive a unsigned 4-byte number from the connected Bluetooth device.")
    public long ReceiveUnsigned4ByteNumber() {
        byte[] bytes = read("ReceiveUnsigned4ByteNumber", 4);
        if (bytes.length != 4) {
            return 0L;
        }
        if (this.byteOrder == ByteOrder.BIG_ENDIAN) {
            return (bytes[3] & 255) | ((bytes[2] & 255) << 8) | ((bytes[1] & 255) << 16) | ((bytes[0] & 255) << 24);
        }
        return (bytes[0] & 255) | ((bytes[1] & 255) << 8) | ((bytes[2] & 255) << 16) | ((bytes[3] & 255) << 24);
    }

    @SimpleFunction(description = "Receive multiple signed byte values from the connected Bluetooth device. If numberOfBytes is less than 0, read until a delimiter byte value is received.")
    public List<Integer> ReceiveSignedBytes(int numberOfBytes) {
        byte[] bytes = read("ReceiveSignedBytes", numberOfBytes);
        List<Integer> list = new ArrayList<>();
        for (int n : bytes) {
            list.add(Integer.valueOf(n));
        }
        return list;
    }

    @SimpleFunction(description = "Receive multiple unsigned byte values from the connected Bluetooth device. If numberOfBytes is less than 0, read until a delimiter byte value is received.")
    public List<Integer> ReceiveUnsignedBytes(int numberOfBytes) {
        byte[] bytes = read("ReceiveUnsignedBytes", numberOfBytes);
        List<Integer> list = new ArrayList<>();
        for (byte b : bytes) {
            int n = b & Ev3Constants.Opcode.TST;
            list.add(Integer.valueOf(n));
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x002b, code lost:
        bluetoothError(r13, com.google.appinventor.components.runtime.util.ErrorMessages.ERROR_BLUETOOTH_END_OF_STREAM, new java.lang.Object[0]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0075, code lost:
        bluetoothError(r13, com.google.appinventor.components.runtime.util.ErrorMessages.ERROR_BLUETOOTH_END_OF_STREAM, new java.lang.Object[0]);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] read(java.lang.String r13, int r14) {
        /*
            r12 = this;
            r11 = 517(0x205, float:7.24E-43)
            r10 = 1
            r8 = -1
            r9 = 0
            boolean r6 = r12.IsConnected()
            if (r6 != 0) goto L15
            r6 = 515(0x203, float:7.22E-43)
            java.lang.Object[] r7 = new java.lang.Object[r9]
            r12.bluetoothError(r13, r6, r7)
            byte[] r6 = new byte[r9]
        L14:
            return r6
        L15:
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            if (r14 < 0) goto L6d
            byte[] r1 = new byte[r14]
            r4 = 0
        L1f:
            if (r4 >= r14) goto L33
            java.io.InputStream r6 = r12.inputStream     // Catch: java.io.IOException -> L3d
            int r7 = r1.length     // Catch: java.io.IOException -> L3d
            int r7 = r7 - r4
            int r3 = r6.read(r1, r4, r7)     // Catch: java.io.IOException -> L3d
            if (r3 != r8) goto L3b
            r6 = 518(0x206, float:7.26E-43)
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch: java.io.IOException -> L3d
            r12.bluetoothError(r13, r6, r7)     // Catch: java.io.IOException -> L3d
        L33:
            r0.write(r1, r9, r4)
        L36:
            byte[] r6 = r0.toByteArray()
            goto L14
        L3b:
            int r4 = r4 + r3
            goto L1f
        L3d:
            r2 = move-exception
            java.lang.String r6 = r12.logTag
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "IO Exception during Reading "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = r2.getMessage()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Log.e(r6, r7)
            boolean r6 = r12.disconnectOnError
            if (r6 == 0) goto L61
            r12.Disconnect()
        L61:
            java.lang.Object[] r6 = new java.lang.Object[r10]
            java.lang.String r7 = r2.getMessage()
            r6[r9] = r7
            r12.bluetoothError(r13, r11, r6)
            goto L33
        L6d:
            java.io.InputStream r6 = r12.inputStream     // Catch: java.io.IOException -> L7e
            int r5 = r6.read()     // Catch: java.io.IOException -> L7e
            if (r5 != r8) goto Lae
            r6 = 518(0x206, float:7.26E-43)
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch: java.io.IOException -> L7e
            r12.bluetoothError(r13, r6, r7)     // Catch: java.io.IOException -> L7e
            goto L36
        L7e:
            r2 = move-exception
            java.lang.String r6 = r12.logTag
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "IO Exception during Reading "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = r2.getMessage()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Log.e(r6, r7)
            boolean r6 = r12.disconnectOnError
            if (r6 == 0) goto La2
            r12.Disconnect()
        La2:
            java.lang.Object[] r6 = new java.lang.Object[r10]
            java.lang.String r7 = r2.getMessage()
            r6[r9] = r7
            r12.bluetoothError(r13, r11, r6)
            goto L36
        Lae:
            r0.write(r5)     // Catch: java.io.IOException -> L7e
            byte r6 = r12.delimiter     // Catch: java.io.IOException -> L7e
            if (r5 != r6) goto L6d
            goto L36
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.BluetoothConnectionBase.read(java.lang.String, int):byte[]");
    }

    @Override // com.google.appinventor.components.runtime.OnDestroyListener
    public void onDestroy() {
        prepareToDie();
    }

    @Override // com.google.appinventor.components.runtime.Deleteable
    public void onDelete() {
        prepareToDie();
    }

    private void prepareToDie() {
        if (this.connectedBluetoothSocket != null) {
            Disconnect();
        }
    }
}
