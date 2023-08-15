package gnu.lists;

import com.google.appinventor.components.runtime.util.Ev3Constants;

/* loaded from: classes.dex */
public class U8Vector extends ByteVector {
    public U8Vector() {
        this.data = ByteVector.empty;
    }

    public U8Vector(int size, byte value) {
        byte[] array = new byte[size];
        this.data = array;
        this.size = size;
        while (true) {
            size--;
            if (size >= 0) {
                array[size] = value;
            } else {
                return;
            }
        }
    }

    public U8Vector(int size) {
        this.data = new byte[size];
        this.size = size;
    }

    public U8Vector(byte[] data) {
        this.data = data;
        this.size = data.length;
    }

    public U8Vector(Sequence seq) {
        this.data = new byte[seq.size()];
        addAll(seq);
    }

    @Override // gnu.lists.SimpleVector
    public final int intAtBuffer(int index) {
        return this.data[index] & Ev3Constants.Opcode.TST;
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public final Object get(int index) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        return Convert.toObjectUnsigned(this.data[index]);
    }

    @Override // gnu.lists.SimpleVector
    public final Object getBuffer(int index) {
        return Convert.toObjectUnsigned(this.data[index]);
    }

    @Override // gnu.lists.SimpleVector
    public Object setBuffer(int index, Object value) {
        byte old = this.data[index];
        this.data[index] = Convert.toByteUnsigned(value);
        return Convert.toObjectUnsigned(old);
    }

    @Override // gnu.lists.SimpleVector
    public int getElementKind() {
        return 17;
    }

    @Override // gnu.lists.SimpleVector
    public String getTag() {
        return "u8";
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareToInt(this, (U8Vector) obj);
    }
}
