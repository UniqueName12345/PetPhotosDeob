package gnu.lists;

/* loaded from: classes.dex */
public class GapVector extends AbstractSequence implements Sequence {
    public SimpleVector base;
    public int gapEnd;
    public int gapStart;

    public GapVector(SimpleVector base) {
        this.base = base;
        this.gapStart = 0;
        this.gapEnd = base.size;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public GapVector() {
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List, java.util.Collection, com.google.appinventor.components.runtime.util.YailObject
    public int size() {
        return this.base.size - (this.gapEnd - this.gapStart);
    }

    @Override // gnu.lists.AbstractSequence
    public boolean hasNext(int ipos) {
        int index = ipos >>> 1;
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        return index < this.base.size;
    }

    @Override // gnu.lists.AbstractSequence
    public int getNextKind(int ipos) {
        if (hasNext(ipos)) {
            return this.base.getElementKind();
        }
        return 0;
    }

    @Override // gnu.lists.AbstractSequence
    public Object get(int index) {
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        return this.base.get(index);
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List
    public Object set(int index, Object value) {
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        return this.base.set(index, value);
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence
    public void fill(Object value) {
        this.base.fill(this.gapEnd, this.base.size, value);
        this.base.fill(0, this.gapStart, value);
    }

    @Override // gnu.lists.AbstractSequence
    public void fillPosRange(int fromPos, int toPos, Object value) {
        int from = fromPos == -1 ? this.base.size : fromPos >>> 1;
        int to = toPos == -1 ? this.base.size : toPos >>> 1;
        int limit = this.gapStart < to ? this.gapStart : to;
        for (int i = from; i < limit; i++) {
            this.base.setBuffer(i, value);
        }
        for (int i2 = this.gapEnd; i2 < to; i2++) {
            this.base.setBuffer(i2, value);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void shiftGap(int newGapStart) {
        int delta = newGapStart - this.gapStart;
        if (delta > 0) {
            this.base.shift(this.gapEnd, this.gapStart, delta);
        } else if (delta < 0) {
            this.base.shift(newGapStart, this.gapEnd + delta, -delta);
        }
        this.gapEnd += delta;
        this.gapStart = newGapStart;
    }

    protected final void gapReserve(int size) {
        gapReserve(this.gapStart, size);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void gapReserve(int where, int needed) {
        if (needed > this.gapEnd - this.gapStart) {
            int oldLength = this.base.size;
            int newLength = oldLength >= 16 ? oldLength * 2 : 16;
            int size = oldLength - (this.gapEnd - this.gapStart);
            int minLength = size + needed;
            if (newLength < minLength) {
                newLength = minLength;
            }
            int newGapEnd = (newLength - size) + where;
            this.base.resizeShift(this.gapStart, this.gapEnd, where, newGapEnd);
            this.gapStart = where;
            this.gapEnd = newGapEnd;
        } else if (where != this.gapStart) {
            shiftGap(where);
        }
    }

    public int getSegment(int where, int len) {
        int length = size();
        if (where < 0 || where > length) {
            return -1;
        }
        if (len < 0) {
            len = 0;
        } else if (where + len > length) {
            len = length - where;
        }
        if (where + len > this.gapStart) {
            if (where >= this.gapStart) {
                return where + (this.gapEnd - this.gapStart);
            }
            if (this.gapStart - where > (len >> 1)) {
                shiftGap(where + len);
                return where;
            }
            shiftGap(where);
            return where + (this.gapEnd - this.gapStart);
        }
        return where;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public int addPos(int ipos, Object value) {
        int index = ipos >>> 1;
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        add(index, value);
        return ((index + 1) << 1) | 1;
    }

    @Override // gnu.lists.AbstractSequence, java.util.List
    public void add(int index, Object o) {
        gapReserve(index, 1);
        this.base.set(index, o);
        this.gapStart++;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public void removePosRange(int ipos0, int ipos1) {
        int ipos02 = ipos0 >>> 1;
        int ipos12 = ipos1 >>> 1;
        if (ipos02 > this.gapEnd) {
            shiftGap((ipos02 - this.gapEnd) + this.gapStart);
        } else if (ipos12 < this.gapStart) {
            shiftGap(ipos12);
        }
        if (ipos02 < this.gapStart) {
            this.base.clearBuffer(ipos02, this.gapStart - ipos02);
            this.gapStart = ipos02;
        }
        if (ipos12 > this.gapEnd) {
            this.base.clearBuffer(this.gapEnd, ipos12 - this.gapEnd);
            this.gapEnd = ipos12;
        }
    }

    @Override // gnu.lists.AbstractSequence
    public int createPos(int index, boolean isAfter) {
        if (index > this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        return (isAfter ? 1 : 0) | (index << 1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public boolean isAfterPos(int ipos) {
        return (ipos & 1) != 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public int nextIndex(int ipos) {
        int index = ipos == -1 ? this.base.size : ipos >>> 1;
        if (index > this.gapStart) {
            return index - (this.gapEnd - this.gapStart);
        }
        return index;
    }

    @Override // gnu.lists.AbstractSequence
    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int i = iposStart >>> 1;
            int end = iposEnd >>> 1;
            if (i < this.gapStart) {
                int lim = end > this.gapStart ? end : this.gapStart;
                consumePosRange(iposStart, lim << 1, out);
            }
            if (end > this.gapEnd) {
                if (i < this.gapEnd) {
                    i = this.gapEnd;
                }
                consumePosRange(i << 1, iposEnd, out);
            }
        }
    }
}
