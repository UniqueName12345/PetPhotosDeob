package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class S64Vector extends SimpleVector implements Externalizable, Comparable {
    protected static long[] empty = new long[0];
    long[] data;

    public S64Vector() {
        this.data = empty;
    }

    public S64Vector(int size, long value) {
        long[] array = new long[size];
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

    public S64Vector(int size) {
        this.data = new long[size];
        this.size = size;
    }

    public S64Vector(long[] data) {
        this.data = data;
        this.size = data.length;
    }

    public S64Vector(Sequence seq) {
        this.data = new long[seq.size()];
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
            long[] tmp = new long[length];
            long[] jArr = this.data;
            if (oldLength >= length) {
                oldLength = length;
            }
            System.arraycopy(jArr, 0, tmp, 0, oldLength);
            this.data = tmp;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.SimpleVector
    public Object getBuffer() {
        return this.data;
    }

    @Override // gnu.lists.SimpleVector
    public final long longAt(int index) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        return this.data[index];
    }

    @Override // gnu.lists.SimpleVector
    public final long longAtBuffer(int index) {
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
    public final int intAtBuffer(int index) {
        return (int) this.data[index];
    }

    @Override // gnu.lists.SimpleVector
    public Object setBuffer(int index, Object value) {
        long old = this.data[index];
        this.data[index] = Convert.toLong(value);
        return Convert.toObject(old);
    }

    public final void setLongAt(int index, long value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.data[index] = value;
    }

    public final void setLongAtBuffer(int index, long value) {
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
        return 24;
    }

    @Override // gnu.lists.SimpleVector
    public String getTag() {
        return "s64";
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeLong(this.data[index]);
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
                out.writeLong(this.data[i]);
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return compareToLong(this, (S64Vector) obj);
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeLong(this.data[i]);
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        long[] data = new long[size];
        for (int i = 0; i < size; i++) {
            data[i] = in.readLong();
        }
        this.data = data;
        this.size = size;
    }
}
