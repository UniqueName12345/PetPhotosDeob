package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public abstract class ByteVector extends SimpleVector implements Externalizable, Comparable {
    protected static byte[] empty = new byte[0];
    byte[] data;

    @Override // gnu.lists.SimpleVector
    public int getBufferLength() {
        return this.data.length;
    }

    @Override // gnu.lists.SimpleVector
    public void setBufferLength(int length) {
        int oldLength = this.data.length;
        if (oldLength != length) {
            byte[] tmp = new byte[length];
            byte[] bArr = this.data;
            if (oldLength >= length) {
                oldLength = length;
            }
            System.arraycopy(bArr, 0, tmp, 0, oldLength);
            this.data = tmp;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.SimpleVector
    public Object getBuffer() {
        return this.data;
    }

    public final byte byteAt(int index) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        return this.data[index];
    }

    public final byte byteAtBuffer(int index) {
        return this.data[index];
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeInt(intAtBuffer(index));
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
                out.writeInt(intAtBuffer(i));
            }
        }
    }

    public final void setByteAt(int index, byte value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.data[index] = value;
    }

    public final void setByteAtBuffer(int index, byte value) {
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

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeByte(this.data[i]);
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            data[i] = in.readByte();
        }
        this.data = data;
        this.size = size;
    }
}
