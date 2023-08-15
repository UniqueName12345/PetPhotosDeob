package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

/* loaded from: classes.dex */
public class FVector extends SimpleVector implements Externalizable, Consumable, Comparable {
    protected static Object[] empty = new Object[0];
    public Object[] data;

    public FVector() {
        this.data = empty;
    }

    public FVector(int num) {
        this.size = num;
        this.data = new Object[num];
    }

    public FVector(int num, Object o) {
        Object[] data = new Object[num];
        if (o != null) {
            for (int i = 0; i < num; i++) {
                data[i] = o;
            }
        }
        this.data = data;
        this.size = num;
    }

    public FVector(Object[] data) {
        this.size = data.length;
        this.data = data;
    }

    public FVector(List seq) {
        this.data = new Object[seq.size()];
        addAll(seq);
    }

    public static FVector make(Object... data) {
        return new FVector(data);
    }

    @Override // gnu.lists.SimpleVector
    public int getBufferLength() {
        return this.data.length;
    }

    @Override // gnu.lists.SimpleVector
    public void setBufferLength(int length) {
        int oldLength = this.data.length;
        if (oldLength != length) {
            Object[] tmp = new Object[length];
            Object[] objArr = this.data;
            if (oldLength >= length) {
                oldLength = length;
            }
            System.arraycopy(objArr, 0, tmp, 0, oldLength);
            this.data = tmp;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.SimpleVector
    public Object getBuffer() {
        return this.data;
    }

    @Override // gnu.lists.SimpleVector
    public void shift(int srcStart, int dstStart, int count) {
        System.arraycopy(this.data, srcStart, this.data, dstStart, count);
    }

    @Override // gnu.lists.SimpleVector
    public final Object getBuffer(int index) {
        return this.data[index];
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public final Object get(int index) {
        if (index >= this.size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.data[index];
    }

    @Override // gnu.lists.SimpleVector
    public final Object setBuffer(int index, Object value) {
        Object old = this.data[index];
        this.data[index] = value;
        return old;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.SimpleVector
    public void clearBuffer(int start, int count) {
        Object[] d = this.data;
        while (true) {
            int start2 = start;
            count--;
            if (count >= 0) {
                start = start2 + 1;
                d[start2] = null;
            } else {
                return;
            }
        }
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof FVector)) {
            return false;
        }
        FVector obj_vec = (FVector) obj;
        int n = this.size;
        if (obj_vec.data == null || obj_vec.size != n) {
            return false;
        }
        Object[] this_data = this.data;
        Object[] obj_data = obj_vec.data;
        for (int i = 0; i < n; i++) {
            if (!this_data[i].equals(obj_data[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        FVector vec2 = (FVector) obj;
        Object[] d1 = this.data;
        Object[] d2 = vec2.data;
        int n1 = this.size;
        int n2 = vec2.size;
        int n = n1 > n2 ? n2 : n1;
        for (int i = 0; i < n; i++) {
            Comparable v1 = (Comparable) d1[i];
            Comparable v2 = (Comparable) d2[i];
            int d = v1.compareTo(v2);
            if (d != 0) {
                return d;
            }
        }
        return n1 - n2;
    }

    public final void setAll(Object new_value) {
        Object[] d = this.data;
        int i = this.size;
        while (true) {
            i--;
            if (i >= 0) {
                d[i] = new_value;
            } else {
                return;
            }
        }
    }

    @Override // gnu.lists.SimpleVector, gnu.lists.AbstractSequence
    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeObject(this.data[index]);
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
                out.writeObject(this.data[i]);
            }
        }
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Consumable
    public void consume(Consumer out) {
        out.startElement("#vector");
        int len = this.size;
        for (int i = 0; i < len; i++) {
            out.writeObject(this.data[i]);
        }
        out.endElement();
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        int n = this.size;
        out.writeInt(n);
        for (int i = 0; i < n; i++) {
            out.writeObject(this.data[i]);
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int n = in.readInt();
        Object[] data = new Object[n];
        for (int i = 0; i < n; i++) {
            data[i] = in.readObject();
        }
        this.size = n;
        this.data = data;
    }
}
