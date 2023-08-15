package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class U32Vector extends SimpleVector implements Externalizable, Comparable {
    int[] data;

    public U32Vector() {
        this.data = S32Vector.empty;
    }

    public U32Vector(int size, int value) {
        int[] array = new int[size];
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

    public U32Vector(int size) {
        this.data = new int[size];
        this.size = size;
    }

    public U32Vector(int[] data) {
        this.data = data;
        this.size = data.length;
    }

    public U32Vector(Sequence seq) {
        this.data = new int[seq.size()];
        addAll(seq);
    }

    @Override // gnu.lists.SimpleVector
    public int getBufferLength() {
        return this.data.length;
    }

    @Override // gnu.lists.SimpleVector
    public void setBufferLength(int length) {
        int oldLength = this.data.length;
        if (oldLength != length) {
            int[] tmp = new int[length];
            int[] iArr = this.data;
            if (oldLength >= length) {
                oldLength = length;
            }
            System.arraycopy(iArr, 0, tmp, 0, oldLength);
            this.data = tmp;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.SimpleVector
    public Object getBuffer() {
        return this.data;
    }

    @Override // gnu.lists.SimpleVector
    public final int intAtBuffer(int index) {
        return this.data[index];
    }

    @Override // gnu.lists.SimpleVector
    public final long longAtBuffer(int index) {
        return this.data[index] & 4294967295L;
    }

    @Override // gnu.lists.SimpleVector
    public final long longAt(int index) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        return longAtBuffer(index);
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
        int old = this.data[index];
        this.data[index] = Convert.toIntUnsigned(value);
        return Convert.toObjectUnsigned(old);
    }

    public final void setIntAt(int index, int value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.data[index] = value;
    }

    public final void setIntAtBuffer(int index, int value) {
        this.data[index] = value;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.SimpleVector
    public void clearBuffer(int start, int count) {
        while (true) {
            int start2 = start;
            count--;
            if (count >= 0) {
                start = start2 + 1;
                this.data[start2] = 0;
            } else {
                return;
            }
        }
    }

    @Override // gnu.lists.SimpleVector
    public int getElementKind() {
        return 19;
    }

    @Override // gnu.lists.SimpleVector
    public String getTag() {
        return "u32";
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeInt(this.data[index]);
        return true;
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int end = iposEnd >>> 1;
            if (end > this.size) {
                end = this.size;
            }
            for (int i = iposStart >>> 1; i < end; i++) {
                out.writeInt(this.data[i]);
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareToLong(this, (U32Vector) obj);
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeInt(this.data[i]);
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = in.readInt();
        }
        this.data = data;
        this.size = size;
    }
}
