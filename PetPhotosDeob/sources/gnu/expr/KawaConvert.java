package gnu.expr;

import com.google.appinventor.components.runtime.util.Ev3Constants;
import gnu.lists.Convert;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.text.Char;

/* loaded from: classes.dex */
public class KawaConvert extends Convert {
    public static Convert instance = new KawaConvert();

    public static Convert getInstance() {
        return instance;
    }

    public static void setInstance(Convert value) {
        instance = value;
    }

    @Override // gnu.lists.Convert
    public Object charToObject(char ch) {
        return Char.make(ch);
    }

    @Override // gnu.lists.Convert
    public char objectToChar(Object obj) {
        return ((Char) obj).charValue();
    }

    @Override // gnu.lists.Convert
    public Object byteToObject(byte value) {
        return IntNum.make((int) value);
    }

    @Override // gnu.lists.Convert
    public Object shortToObject(short value) {
        return IntNum.make((int) value);
    }

    @Override // gnu.lists.Convert
    public Object intToObject(int value) {
        return IntNum.make(value);
    }

    @Override // gnu.lists.Convert
    public Object longToObject(long value) {
        return IntNum.make(value);
    }

    @Override // gnu.lists.Convert
    public Object byteToObjectUnsigned(byte value) {
        return IntNum.make(value & Ev3Constants.Opcode.TST);
    }

    @Override // gnu.lists.Convert
    public Object shortToObjectUnsigned(short value) {
        return IntNum.make(65535 & value);
    }

    @Override // gnu.lists.Convert
    public Object intToObjectUnsigned(int value) {
        return IntNum.make(value & 4294967295L);
    }

    @Override // gnu.lists.Convert
    public Object longToObjectUnsigned(long value) {
        return IntNum.makeU(value);
    }

    @Override // gnu.lists.Convert
    public Object floatToObject(float value) {
        return DFloNum.make(value);
    }

    @Override // gnu.lists.Convert
    public Object doubleToObject(double value) {
        return DFloNum.make(value);
    }
}
