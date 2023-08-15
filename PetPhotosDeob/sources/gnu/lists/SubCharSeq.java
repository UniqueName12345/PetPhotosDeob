package gnu.lists;

import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public class SubCharSeq extends SubSequence implements CharSeq {
    public SubCharSeq(AbstractSequence base, int startPos, int endPos) {
        super(base, startPos, endPos);
    }

    @Override // gnu.lists.CharSeq, java.lang.CharSequence
    public int length() {
        return size();
    }

    @Override // gnu.lists.CharSeq, java.lang.CharSequence
    public char charAt(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int start = this.base.nextIndex(this.ipos0);
        return ((CharSeq) this.base).charAt(start + index);
    }

    @Override // gnu.lists.CharSeq
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        int i = srcBegin;
        int dstBegin2 = dstBegin;
        while (i < srcEnd) {
            dst[dstBegin2] = charAt(i);
            i++;
            dstBegin2++;
        }
    }

    @Override // gnu.lists.CharSeq
    public void setCharAt(int index, char ch) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int start = this.base.nextIndex(this.ipos0);
        ((CharSeq) this.base).setCharAt(start + index, ch);
    }

    @Override // gnu.lists.CharSeq
    public void fill(char value) {
        int index0 = this.base.nextIndex(this.ipos0);
        int index1 = this.base.nextIndex(this.ipos0);
        ((CharSeq) this.base).fill(index0, index1, value);
    }

    @Override // gnu.lists.CharSeq
    public void fill(int fromIndex, int toIndex, char value) {
        int index0 = this.base.nextIndex(this.ipos0);
        int index1 = this.base.nextIndex(this.ipos0);
        if (fromIndex < 0 || toIndex < fromIndex || index0 + toIndex > index1) {
            throw new IndexOutOfBoundsException();
        }
        ((CharSeq) this.base).fill(index0 + fromIndex, index0 + toIndex, value);
    }

    @Override // gnu.lists.CharSeq
    public void writeTo(int start, int count, Appendable dest) throws IOException {
        int index0 = this.base.nextIndex(this.ipos0);
        int index1 = this.base.nextIndex(this.ipos0);
        if (start < 0 || count < 0 || index0 + start + count > index1) {
            throw new IndexOutOfBoundsException();
        }
        ((CharSeq) this.base).writeTo(index0 + start, count, dest);
    }

    @Override // gnu.lists.CharSeq
    public void writeTo(Appendable dest) throws IOException {
        int index0 = this.base.nextIndex(this.ipos0);
        ((CharSeq) this.base).writeTo(index0, size(), dest);
    }

    @Override // gnu.lists.CharSeq
    public void consume(int start, int count, Consumer out) {
        int index0 = this.base.nextIndex(this.ipos0);
        int index1 = this.base.nextIndex(this.ipos0);
        if (start < 0 || count < 0 || index0 + start + count > index1) {
            throw new IndexOutOfBoundsException();
        }
        ((CharSeq) this.base).consume(index0 + start, count, out);
    }

    @Override // gnu.lists.AbstractSequence
    public String toString() {
        int sz = size();
        StringBuffer sbuf = new StringBuffer(sz);
        for (int i = 0; i < sz; i++) {
            sbuf.append(charAt(i));
        }
        return sbuf.toString();
    }

    private SubCharSeq subCharSeq(int start, int end) {
        int sz = size();
        if (start < 0 || end < start || end > sz) {
            throw new IndexOutOfBoundsException();
        }
        return new SubCharSeq(this.base, this.base.createRelativePos(this.ipos0, start, false), this.base.createRelativePos(this.ipos0, end, true));
    }

    @Override // gnu.lists.AbstractSequence, java.util.List
    public List subList(int fromIx, int toIx) {
        return subCharSeq(fromIx, toIx);
    }

    @Override // gnu.lists.CharSeq, java.lang.CharSequence
    public CharSequence subSequence(int start, int end) {
        return subCharSeq(start, end);
    }
}
