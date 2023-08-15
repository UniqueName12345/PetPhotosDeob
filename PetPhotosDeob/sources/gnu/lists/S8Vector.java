package gnu.lists;

/* loaded from: classes.dex */
public class S8Vector extends ByteVector {
    public S8Vector() {
        this.data = ByteVector.empty;
    }

    public S8Vector(int size, byte value) {
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

    public S8Vector(int size) {
        this.data = new byte[size];
        this.size = size;
    }

    public S8Vector(byte[] data) {
        this.data = data;
        this.size = data.length;
    }

    public S8Vector(Sequence seq) {
        this.data = new byte[seq.size()];
        addAll(seq);
    }

    @Override // gnu.lists.SimpleVector
    public final int intAtBuffer(int index) {
        return this.data[index];
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public final Object get(int index) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        return Convert.toObject(this.data[index]);
    }

    @Override // gnu.lists.SimpleVector
    public final Object getBuffer(int index) {
        return Convert.toObject(this.data[index]);
    }

    @Override // gnu.lists.SimpleVector
    public Object setBuffer(int index, Object value) {
        byte old = this.data[index];
        this.data[index] = Convert.toByte(value);
        return Convert.toObject(old);
    }

    @Override // gnu.lists.SimpleVector
    public int getElementKind() {
        return 18;
    }

    @Override // gnu.lists.SimpleVector
    public String getTag() {
        return "s8";
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareToInt(this, (S8Vector) obj);
    }
}
