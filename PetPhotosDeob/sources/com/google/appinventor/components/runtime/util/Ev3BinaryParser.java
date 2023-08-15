package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.util.Ev3Constants;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class Ev3BinaryParser {
    private static byte PRIMPAR_SHORT = 0;
    private static byte PRIMPAR_LONG = Byte.MIN_VALUE;
    private static byte PRIMPAR_CONST = 0;
    private static byte PRIMPAR_VARIABEL = Ev3Constants.Opcode.JR;
    private static byte PRIMPAR_LOCAL = 0;
    private static byte PRIMPAR_GLOBAL = 32;
    private static byte PRIMPAR_HANDLE = 16;
    private static byte PRIMPAR_ADDR = 8;
    private static byte PRIMPAR_INDEX = 31;
    private static byte PRIMPAR_CONST_SIGN = 32;
    private static byte PRIMPAR_VALUE = Ev3Constants.Opcode.MOVEF_F;
    private static byte PRIMPAR_BYTES = 7;
    private static byte PRIMPAR_STRING_OLD = 0;
    private static byte PRIMPAR_1_BYTE = 1;
    private static byte PRIMPAR_2_BYTES = 2;
    private static byte PRIMPAR_4_BYTES = 3;
    private static byte PRIMPAR_STRING = 4;

    /* loaded from: classes.dex */
    private static class FormatLiteral {
        public int size;
        public char symbol;

        public FormatLiteral(char symbol, int size) {
            this.symbol = symbol;
            this.size = size;
        }
    }

    public static byte[] pack(String format, Object... values) throws IllegalArgumentException {
        String[] formatTokens = format.split("(?<=\\D)");
        FormatLiteral[] literals = new FormatLiteral[formatTokens.length];
        int index = 0;
        int bufferCapacity = 0;
        for (int i = 0; i < formatTokens.length; i++) {
            String token = formatTokens[i];
            char symbol = token.charAt(token.length() - 1);
            int size = 1;
            boolean sizeSpecified = false;
            if (token.length() != 1) {
                size = Integer.parseInt(token.substring(0, token.length() - 1));
                sizeSpecified = true;
                if (size < 1) {
                    throw new IllegalArgumentException("Illegal format string");
                }
            }
            switch (symbol) {
                case 'B':
                    bufferCapacity += size;
                    index++;
                    break;
                case 'F':
                    bufferCapacity += size * 4;
                    index++;
                    break;
                case 'H':
                    bufferCapacity += size * 2;
                    index++;
                    break;
                case 'I':
                    bufferCapacity += size * 4;
                    index++;
                    break;
                case 'L':
                    bufferCapacity += size * 8;
                    index++;
                    break;
                case 'S':
                    if (sizeSpecified) {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                    bufferCapacity += ((String) values[index]).length() + 1;
                    index++;
                    break;
                case 'b':
                    bufferCapacity += size;
                    index += size;
                    break;
                case 'f':
                    bufferCapacity += size * 4;
                    index += size;
                    break;
                case 'h':
                    bufferCapacity += size * 2;
                    index += size;
                    break;
                case 'i':
                    bufferCapacity += size * 4;
                    index += size;
                    break;
                case 'l':
                    bufferCapacity += size * 8;
                    index += size;
                    break;
                case 's':
                    if (size != ((String) values[index]).length()) {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                    bufferCapacity += size;
                    index++;
                    break;
                case 'x':
                    bufferCapacity += size;
                    break;
                default:
                    throw new IllegalArgumentException("Illegal format string");
            }
            literals[i] = new FormatLiteral(symbol, size);
        }
        if (index != values.length) {
            throw new IllegalArgumentException("Illegal format string");
        }
        int index2 = 0;
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int length = literals.length;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < length) {
                FormatLiteral literal = literals[i3];
                switch (literal.symbol) {
                    case 'B':
                        buffer.put((byte[]) values[index2]);
                        index2++;
                        break;
                    case 'F':
                        for (int i4 = 0; i4 < literal.size; i4++) {
                            buffer.putFloat(((float[]) values[index2])[i4]);
                        }
                        index2++;
                        break;
                    case 'H':
                        for (int i5 = 0; i5 < literal.size; i5++) {
                            buffer.putShort(((short[]) values[index2])[i5]);
                        }
                        index2++;
                        break;
                    case 'I':
                        for (int i6 = 0; i6 < literal.size; i6++) {
                            buffer.putInt(((int[]) values[index2])[i6]);
                        }
                        index2++;
                        break;
                    case 'L':
                        for (int i7 = 0; i7 < literal.size; i7++) {
                            buffer.putLong(((long[]) values[index2])[i7]);
                        }
                        index2++;
                        break;
                    case 'S':
                        try {
                            buffer.put(((String) values[index2]).getBytes("US-ASCII"));
                            buffer.put((byte) 0);
                            index2++;
                            break;
                        } catch (UnsupportedEncodingException e) {
                            throw new IllegalArgumentException();
                        }
                    case 'b':
                        for (int i8 = 0; i8 < literal.size; i8++) {
                            buffer.put(((Byte) values[index2]).byteValue());
                            index2++;
                        }
                        break;
                    case 'f':
                        for (int i9 = 0; i9 < literal.size; i9++) {
                            buffer.putFloat(((Float) values[index2]).floatValue());
                            index2++;
                        }
                        break;
                    case 'h':
                        for (int i10 = 0; i10 < literal.size; i10++) {
                            buffer.putShort(((Short) values[index2]).shortValue());
                            index2++;
                        }
                        break;
                    case 'i':
                        for (int i11 = 0; i11 < literal.size; i11++) {
                            buffer.putInt(((Integer) values[index2]).intValue());
                            index2++;
                        }
                        break;
                    case 'l':
                        for (int i12 = 0; i12 < literal.size; i12++) {
                            buffer.putLong(((Long) values[index2]).longValue());
                            index2++;
                        }
                        break;
                    case 's':
                        try {
                            buffer.put(((String) values[index2]).getBytes("US-ASCII"));
                            index2++;
                            break;
                        } catch (UnsupportedEncodingException e2) {
                            throw new IllegalArgumentException();
                        }
                    case 'x':
                        for (int i13 = 0; i13 < literal.size; i13++) {
                            buffer.put((byte) 0);
                        }
                        break;
                }
                i2 = i3 + 1;
            } else {
                return buffer.array();
            }
        }
    }

    public static Object[] unpack(String format, byte[] bytes) throws IllegalArgumentException {
        String[] formatTokens = format.split("(?<=\\D)");
        ArrayList<Object> decodedObjects = new ArrayList<>();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        for (String token : formatTokens) {
            boolean sizeSpecified = false;
            int size = 1;
            char symbol = token.charAt(token.length() - 1);
            if (token.length() > 1) {
                sizeSpecified = true;
                size = Integer.parseInt(token.substring(0, token.length() - 1));
                if (size < 1) {
                    throw new IllegalArgumentException("Illegal format string");
                }
            }
            switch (symbol) {
                case '$':
                    if (sizeSpecified) {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                    if (buffer.hasRemaining()) {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                    throw new IllegalArgumentException("Illegal format string");
                case 'B':
                    byte[] byteArray = new byte[size];
                    buffer.get(byteArray, 0, size);
                    decodedObjects.add(byteArray);
                    break;
                case 'F':
                    float[] floats = new float[size];
                    for (int i = 0; i < size; i++) {
                        floats[i] = buffer.getFloat();
                    }
                    decodedObjects.add(floats);
                    break;
                case 'H':
                    short[] shorts = new short[size];
                    for (short i2 = 0; i2 < size; i2 = (short) (i2 + 1)) {
                        shorts[i2] = buffer.getShort();
                    }
                    decodedObjects.add(shorts);
                    break;
                case 'I':
                    int[] integers = new int[size];
                    for (int i3 = 0; i3 < size; i3++) {
                        integers[i3] = buffer.getInt();
                    }
                    decodedObjects.add(integers);
                    break;
                case 'L':
                    long[] longs = new long[size];
                    for (int i4 = 0; i4 < size; i4++) {
                        longs[i4] = buffer.getLong();
                    }
                    decodedObjects.add(longs);
                    break;
                case 'S':
                    if (sizeSpecified) {
                        throw new IllegalArgumentException("Illegal format string");
                    }
                    StringBuffer stringBuffer = new StringBuffer();
                    while (true) {
                        byte b = buffer.get();
                        if (b != 0) {
                            stringBuffer.append((char) b);
                        } else {
                            decodedObjects.add(stringBuffer.toString());
                            break;
                        }
                    }
                case 'b':
                    for (int i5 = 0; i5 < size; i5++) {
                        decodedObjects.add(Byte.valueOf(buffer.get()));
                    }
                    break;
                case 'f':
                    for (int i6 = 0; i6 < size; i6++) {
                        decodedObjects.add(Float.valueOf(buffer.getFloat()));
                    }
                    break;
                case 'h':
                    for (int i7 = 0; i7 < size; i7++) {
                        decodedObjects.add(Short.valueOf(buffer.getShort()));
                    }
                    break;
                case 'i':
                    for (int i8 = 0; i8 < size; i8++) {
                        decodedObjects.add(Integer.valueOf(buffer.getInt()));
                    }
                    break;
                case 'l':
                    for (int i9 = 0; i9 < size; i9++) {
                        decodedObjects.add(Long.valueOf(buffer.getLong()));
                    }
                    break;
                case 's':
                    byte[] byteString = new byte[size];
                    buffer.get(byteString, 0, size);
                    try {
                        decodedObjects.add(new String(byteString, "US-ASCII"));
                        break;
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalArgumentException();
                    }
                case 'x':
                    for (int i10 = 0; i10 < size; i10++) {
                        buffer.get();
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Illegal format string");
            }
        }
        return decodedObjects.toArray();
    }

    public static byte[] encodeLC0(byte v) {
        if (v < -31 || v > 31) {
            throw new IllegalArgumentException("Encoded value must be in range [0, 127]");
        }
        return new byte[]{(byte) (PRIMPAR_VALUE & v)};
    }

    public static byte[] encodeLC1(byte v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_1_BYTE), (byte) (v & Ev3Constants.Opcode.TST)};
    }

    public static byte[] encodeLC2(short v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_2_BYTES), (byte) (v & 255), (byte) ((v >>> 8) & 255)};
    }

    public static byte[] encodeLC4(int v) {
        return new byte[]{(byte) (((byte) (PRIMPAR_LONG | PRIMPAR_CONST)) | PRIMPAR_4_BYTES), (byte) (v & 255), (byte) ((v >>> 8) & 255), (byte) ((v >>> 16) & 255), (byte) ((v >>> 24) & 255)};
    }

    public static byte[] encodeLV0(int i) {
        return new byte[]{(byte) ((PRIMPAR_INDEX & i) | PRIMPAR_SHORT | PRIMPAR_VARIABEL | PRIMPAR_LOCAL)};
    }

    public static byte[] encodeLV1(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_LOCAL | PRIMPAR_1_BYTE), (byte) (i & 255)};
    }

    public static byte[] encodeLV2(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_LOCAL | PRIMPAR_2_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255)};
    }

    public static byte[] encodeLV4(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_LOCAL | PRIMPAR_4_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 24) & 255)};
    }

    public static byte[] encodeGV0(int i) {
        return new byte[]{(byte) ((PRIMPAR_INDEX & i) | PRIMPAR_SHORT | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL)};
    }

    public static byte[] encodeGV1(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL | PRIMPAR_1_BYTE), (byte) (i & 255)};
    }

    public static byte[] encodeGV2(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL | PRIMPAR_2_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255)};
    }

    public static byte[] encodeGV4(int i) {
        return new byte[]{(byte) (PRIMPAR_LONG | PRIMPAR_VARIABEL | PRIMPAR_GLOBAL | PRIMPAR_4_BYTES), (byte) (i & 255), (byte) ((i >>> 8) & 255), (byte) ((i >>> 16) & 255), (byte) ((i >>> 24) & 255)};
    }

    public static byte[] encodeSystemCommand(byte command, boolean needReply, Object... parameters) {
        int bufferCapacity = 2;
        for (Object obj : parameters) {
            if (obj instanceof Byte) {
                bufferCapacity++;
            } else if (obj instanceof Short) {
                bufferCapacity += 2;
            } else if (obj instanceof Integer) {
                bufferCapacity += 4;
            } else if (obj instanceof String) {
                bufferCapacity += ((String) obj).length() + 1;
            } else {
                throw new IllegalArgumentException("Parameters should be one of the class types: Byte, Short, Integer, String");
            }
        }
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(needReply ? (byte) 1 : (byte) -127);
        buffer.put(command);
        for (Object obj2 : parameters) {
            if (obj2 instanceof Byte) {
                buffer.put(((Byte) obj2).byteValue());
            } else if (obj2 instanceof Short) {
                buffer.putShort(((Short) obj2).shortValue());
            } else if (obj2 instanceof Integer) {
                buffer.putInt(((Integer) obj2).intValue());
            } else if (obj2 instanceof String) {
                try {
                    buffer.put(((String) obj2).getBytes("US-ASCII"));
                    buffer.put((byte) 0);
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalArgumentException("Non-ASCII string encoding is not supported");
                }
            } else {
                throw new IllegalArgumentException("Parameters should be one of the class types: Byte, Short, Integer, String");
            }
        }
        return buffer.array();
    }

    public static byte[] encodeDirectCommand(byte opcode, boolean needReply, int globalAllocation, int localAllocation, String paramFormat, Object... parameters) {
        if (globalAllocation < 0 || globalAllocation > 1023 || localAllocation < 0 || localAllocation > 63 || paramFormat.length() != parameters.length) {
            throw new IllegalArgumentException();
        }
        ArrayList<byte[]> payloads = new ArrayList<>();
        for (int i = 0; i < paramFormat.length(); i++) {
            char letter = paramFormat.charAt(i);
            Object obj = parameters[i];
            switch (letter) {
                case 'c':
                    if (obj instanceof Byte) {
                        if (((Byte) obj).byteValue() <= 31 && ((Byte) obj).byteValue() >= -31) {
                            payloads.add(encodeLC0(((Byte) obj).byteValue()));
                            break;
                        } else {
                            payloads.add(encodeLC1(((Byte) obj).byteValue()));
                            break;
                        }
                    } else if (obj instanceof Short) {
                        payloads.add(encodeLC2(((Short) obj).shortValue()));
                        break;
                    } else if (obj instanceof Integer) {
                        payloads.add(encodeLC4(((Integer) obj).intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 'g':
                    if (obj instanceof Byte) {
                        if (((Byte) obj).byteValue() <= 31 && ((Byte) obj).byteValue() >= -31) {
                            payloads.add(encodeGV0(((Byte) obj).byteValue()));
                            break;
                        } else {
                            payloads.add(encodeGV1(((Byte) obj).byteValue()));
                            break;
                        }
                    } else if (obj instanceof Short) {
                        payloads.add(encodeGV2(((Short) obj).shortValue()));
                        break;
                    } else if (obj instanceof Integer) {
                        payloads.add(encodeGV4(((Integer) obj).intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 'l':
                    if (obj instanceof Byte) {
                        if (((Byte) obj).byteValue() <= 31 && ((Byte) obj).byteValue() >= -31) {
                            payloads.add(encodeLV0(((Byte) obj).byteValue()));
                            break;
                        } else {
                            payloads.add(encodeLV1(((Byte) obj).byteValue()));
                            break;
                        }
                    } else if (obj instanceof Short) {
                        payloads.add(encodeLV2(((Short) obj).shortValue()));
                        break;
                    } else if (obj instanceof Integer) {
                        payloads.add(encodeLV4(((Integer) obj).intValue()));
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 's':
                    if (!(obj instanceof String)) {
                        throw new IllegalArgumentException();
                    }
                    try {
                        payloads.add((((String) obj) + (char) 0).getBytes("US-ASCII"));
                        break;
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalArgumentException();
                    }
                default:
                    throw new IllegalArgumentException("Illegal format string");
            }
        }
        int bufferCapacity = 4;
        Iterator<byte[]> it = payloads.iterator();
        while (it.hasNext()) {
            byte[] array = it.next();
            bufferCapacity += array.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(needReply ? (byte) 0 : Byte.MIN_VALUE);
        buffer.put(new byte[]{(byte) (globalAllocation & 255), (byte) (((globalAllocation >>> 8) & 3) | (localAllocation << 2))});
        buffer.put(opcode);
        Iterator<byte[]> it2 = payloads.iterator();
        while (it2.hasNext()) {
            byte[] array2 = it2.next();
            buffer.put(array2);
        }
        return buffer.array();
    }
}
