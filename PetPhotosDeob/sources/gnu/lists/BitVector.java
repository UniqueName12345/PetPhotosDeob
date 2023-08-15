package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class BitVector extends SimpleVector implements Externalizable {
    protected static boolean[] empty = new boolean[0];
    boolean[] data;

    public BitVector() {
        this.data = empty;
    }

    public BitVector(int size, boolean value) {
        boolean[] array = new boolean[size];
        this.data = array;
        this.size = size;
        if (!value) {
            return;
        }
        while (true) {
            size--;
            if (size >= 0) {
                array[size] = true;
            } else {
                return;
            }
        }
    }

    public BitVector(int size) {
        this.data = new boolean[size];
        this.size = size;
    }

    public BitVector(boolean[] data) {
        this.data = data;
        this.size = data.length;
    }

    public BitVector(Sequence seq) {
        this.data = new boolean[seq.size()];
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
            boolean[] tmp = new boolean[length];
            boolean[] zArr = this.data;
            if (oldLength >= length) {
                oldLength = length;
            }
            System.arraycopy(zArr, 0, tmp, 0, oldLength);
            this.data = tmp;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.SimpleVector
    public Object getBuffer() {
        return this.data;
    }

    public final boolean booleanAt(int index) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        return this.data[index];
    }

    public final boolean booleanAtBuffer(int index) {
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
        boolean old = this.data[index];
        this.data[index] = Convert.toBoolean(value);
        return Convert.toObject(old);
    }

    public final void setBooleanAt(int index, boolean value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.data[index] = value;
    }

    public final void setBooleanAtBuffer(int index, boolean value) {
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
                this.data[start2] = false;
            } else {
                return;
            }
        }
    }

    @Override // gnu.lists.SimpleVector
    public int getElementKind() {
        return 27;
    }

    @Override // gnu.lists.SimpleVector
    public String getTag() {
        return "b";
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeBoolean(this.data[index]);
        return true;
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int end = iposEnd >>> 1;
            for (int i = iposStart >>> 1; i < end; i++) {
                out.writeBoolean(this.data[i]);
            }
        }
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeBoolean(this.data[i]);
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        boolean[] data = new boolean[size];
        for (int i = 0; i < size; i++) {
            data[i] = in.readBoolean();
        }
        this.data = data;
        this.size = size;
    }
}
