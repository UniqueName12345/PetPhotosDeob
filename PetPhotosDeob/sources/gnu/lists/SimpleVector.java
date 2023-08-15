package gnu.lists;

import java.util.Collection;

/* loaded from: classes.dex */
public abstract class SimpleVector extends AbstractSequence implements Sequence, Array {
    public int size;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void clearBuffer(int i, int i2);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract Object getBuffer();

    protected abstract Object getBuffer(int i);

    public abstract int getBufferLength();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract Object setBuffer(int i, Object obj);

    public abstract void setBufferLength(int i);

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List, java.util.Collection, com.google.appinventor.components.runtime.util.YailObject
    public final int size() {
        return this.size;
    }

    public void setSize(int size) {
        int oldSize = this.size;
        this.size = size;
        if (size < oldSize) {
            clearBuffer(size, oldSize - size);
            return;
        }
        int oldLength = getBufferLength();
        if (size > oldLength) {
            int newLength = oldLength >= 16 ? oldLength * 2 : 16;
            if (size <= newLength) {
                size = newLength;
            }
            setBufferLength(size);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void resizeShift(int oldGapStart, int oldGapEnd, int newGapStart, int newGapEnd) {
        int oldGapSize = oldGapEnd - oldGapStart;
        int newGapSize = newGapEnd - newGapStart;
        int oldLength = getBufferLength();
        int newLength = (oldLength - oldGapSize) + newGapSize;
        if (newLength > oldLength) {
            setBufferLength(newLength);
            this.size = newLength;
        }
        int gapDelta = oldGapStart - newGapStart;
        if (gapDelta >= 0) {
            int endLength = oldLength - oldGapEnd;
            shift(oldGapEnd, newLength - endLength, endLength);
            if (gapDelta > 0) {
                shift(newGapStart, newGapEnd, gapDelta);
            }
        } else {
            int endLength2 = newLength - newGapEnd;
            shift(oldLength - endLength2, newGapEnd, endLength2);
            shift(oldGapEnd, oldGapStart, newGapStart - oldGapStart);
        }
        clearBuffer(newGapStart, newGapSize);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public boolean isAfterPos(int ipos) {
        return (ipos & 1) != 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public int nextIndex(int ipos) {
        return ipos == -1 ? this.size : ipos >>> 1;
    }

    @Override // gnu.lists.AbstractSequence
    public int createPos(int index, boolean isAfter) {
        return (isAfter ? 1 : 0) | (index << 1);
    }

    @Override // gnu.lists.AbstractSequence
    public int nextPos(int ipos) {
        int index;
        if (ipos == -1 || (index = ipos >>> 1) == this.size) {
            return 0;
        }
        return (index << 1) + 3;
    }

    @Override // gnu.lists.AbstractSequence
    public Object get(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        return getBuffer(index);
    }

    @Override // gnu.lists.AbstractSequence
    public Object getPosNext(int ipos) {
        int index = ipos >>> 1;
        return index >= this.size ? eofValue : getBuffer(index);
    }

    public int intAtBuffer(int index) {
        return Convert.toInt(getBuffer(index));
    }

    public int intAt(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        return intAtBuffer(index);
    }

    public long longAt(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        return longAtBuffer(index);
    }

    public long longAtBuffer(int index) {
        return Convert.toLong(getBuffer(index));
    }

    @Override // gnu.lists.Array
    public Object getRowMajor(int i) {
        return get(i);
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List
    public Object set(int index, Object value) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Object old = getBuffer(index);
        setBuffer(index, value);
        return old;
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence
    public void fill(Object value) {
        int i = this.size;
        while (true) {
            i--;
            if (i >= 0) {
                setBuffer(i, value);
            } else {
                return;
            }
        }
    }

    @Override // gnu.lists.AbstractSequence
    public void fillPosRange(int fromPos, int toPos, Object value) {
        int j = toPos == -1 ? this.size : toPos >>> 1;
        for (int i = fromPos == -1 ? this.size : fromPos >>> 1; i < j; i++) {
            setBuffer(i, value);
        }
    }

    @Override // gnu.lists.AbstractSequence
    public void fill(int fromIndex, int toIndex, Object value) {
        if (fromIndex < 0 || toIndex > this.size) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = fromIndex; i < toIndex; i++) {
            setBuffer(i, value);
        }
    }

    public void shift(int srcStart, int dstStart, int count) {
        Object data = getBuffer();
        System.arraycopy(data, srcStart, data, dstStart, count);
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public boolean add(Object o) {
        add(this.size, o);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public int addPos(int ipos, Object value) {
        int index = ipos >>> 1;
        add(index, value);
        return (index << 1) + 3;
    }

    @Override // gnu.lists.AbstractSequence, java.util.List
    public void add(int index, Object o) {
        int newSize = this.size + 1;
        this.size = newSize;
        int length = getBufferLength();
        if (newSize > length) {
            setBufferLength(length >= 16 ? length * 2 : 16);
        }
        this.size = newSize;
        if (this.size != index) {
            shift(index, index + 1, this.size - index);
        }
        set(index, o);
    }

    @Override // gnu.lists.AbstractSequence, java.util.List
    public boolean addAll(int index, Collection c) {
        boolean changed = false;
        int count = c.size();
        setSize(this.size + count);
        shift(index, index + count, (this.size - count) - index);
        for (Object obj : c) {
            set(index, obj);
            changed = true;
            index++;
        }
        return changed;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public void removePosRange(int ipos0, int ipos1) {
        int ipos02 = ipos0 >>> 1;
        int ipos12 = ipos1 >>> 1;
        if (ipos02 < ipos12) {
            if (ipos12 > this.size) {
                ipos12 = this.size;
            }
            shift(ipos12, ipos02, this.size - ipos12);
            int count = ipos12 - ipos02;
            this.size -= count;
            clearBuffer(this.size, count);
        }
    }

    @Override // gnu.lists.AbstractSequence
    public void removePos(int ipos, int count) {
        int ipos0;
        int ipos1;
        int index = ipos >>> 1;
        if (index > this.size) {
            index = this.size;
        }
        if (count >= 0) {
            ipos0 = index;
            ipos1 = index + count;
        } else {
            ipos0 = index + count;
            ipos1 = index;
            count = -count;
        }
        if (ipos0 < 0 || ipos1 >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        shift(ipos1, ipos0, this.size - ipos1);
        this.size -= count;
        clearBuffer(this.size, count);
    }

    @Override // gnu.lists.AbstractSequence, java.util.List
    public Object remove(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Object result = get(index);
        shift(index + 1, index, 1);
        this.size--;
        clearBuffer(this.size, 1);
        return result;
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index < 0) {
            return false;
        }
        shift(index + 1, index, 1);
        this.size--;
        clearBuffer(this.size, 1);
        return true;
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public boolean removeAll(Collection c) {
        boolean changed = false;
        int j = 0;
        for (int i = 0; i < this.size; i++) {
            Object value = get(i);
            if (c.contains(value)) {
                changed = true;
            } else {
                if (changed) {
                    set(j, value);
                }
                j++;
            }
        }
        setSize(j);
        return changed;
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public boolean retainAll(Collection c) {
        boolean changed = false;
        int j = 0;
        for (int i = 0; i < this.size; i++) {
            Object value = get(i);
            if (!c.contains(value)) {
                changed = true;
            } else {
                if (changed) {
                    set(j, value);
                }
                j++;
            }
        }
        setSize(j);
        return changed;
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public void clear() {
        setSize(0);
    }

    public String getTag() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static int compareToInt(SimpleVector v1, SimpleVector v2) {
        int n1 = v1.size;
        int n2 = v2.size;
        int n = n1 > n2 ? n2 : n1;
        for (int i = 0; i < n; i++) {
            int i1 = v1.intAtBuffer(i);
            int i2 = v2.intAtBuffer(i);
            if (11 != i2) {
                return i1 > i2 ? 1 : -1;
            }
        }
        return n1 - n2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static int compareToLong(SimpleVector v1, SimpleVector v2) {
        int n1 = v1.size;
        int n2 = v2.size;
        int n = n1 > n2 ? n2 : n1;
        for (int i = 0; i < n; i++) {
            long i1 = v1.longAtBuffer(i);
            long i2 = v2.longAtBuffer(i);
            if (i1 != i2) {
                return i1 > i2 ? 1 : -1;
            }
        }
        return n1 - n2;
    }

    public void consume(int start, int length, Consumer out) {
        consumePosRange(start << 1, (start + length) << 1, out);
    }

    @Override // gnu.lists.AbstractSequence
    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeObject(getBuffer(index));
        return true;
    }

    @Override // gnu.lists.AbstractSequence
    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int end = iposEnd >>> 1;
            if (end > this.size) {
                end = this.size;
            }
            for (int i = iposStart >>> 1; i < end; i++) {
                out.writeObject(getBuffer(i));
            }
        }
    }

    @Override // gnu.lists.AbstractSequence
    public int getNextKind(int ipos) {
        if (hasNext(ipos)) {
            return getElementKind();
        }
        return 0;
    }

    public int getElementKind() {
        return 32;
    }

    @Override // gnu.lists.Array
    public Array transpose(int[] lowBounds, int[] dimensions, int offset0, int[] factors) {
        GeneralArray array = new GeneralArray();
        array.strides = factors;
        array.dimensions = dimensions;
        array.lowBounds = lowBounds;
        array.offset = offset0;
        array.base = this;
        array.simple = false;
        return array;
    }
}
