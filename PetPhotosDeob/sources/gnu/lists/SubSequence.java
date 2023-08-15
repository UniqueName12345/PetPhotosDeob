package gnu.lists;

/* loaded from: classes.dex */
public class SubSequence extends AbstractSequence implements Sequence {
    AbstractSequence base;
    int ipos0;
    int ipos1;

    public SubSequence() {
    }

    public SubSequence(AbstractSequence base, int startPos, int endPos) {
        this.base = base;
        this.ipos0 = startPos;
        this.ipos1 = endPos;
    }

    public SubSequence(AbstractSequence base) {
        this.base = base;
    }

    @Override // gnu.lists.AbstractSequence
    public Object get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int start = this.base.nextIndex(this.ipos0);
        return this.base.get(start + index);
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List, java.util.Collection, com.google.appinventor.components.runtime.util.YailObject
    public int size() {
        return this.base.getIndexDifference(this.ipos1, this.ipos0);
    }

    @Override // gnu.lists.AbstractSequence
    public void removePosRange(int istart, int iend) {
        AbstractSequence abstractSequence = this.base;
        if (istart == 0) {
            istart = this.ipos0;
        } else if (istart == -1) {
            istart = this.ipos1;
        }
        if (iend == -1) {
            iend = this.ipos1;
        } else if (iend == 0) {
            iend = this.ipos0;
        }
        abstractSequence.removePosRange(istart, iend);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public boolean isAfterPos(int ipos) {
        return this.base.isAfterPos(ipos);
    }

    @Override // gnu.lists.AbstractSequence
    public int createPos(int offset, boolean isAfter) {
        return this.base.createRelativePos(this.ipos0, offset, isAfter);
    }

    @Override // gnu.lists.AbstractSequence
    public int createRelativePos(int pos, int offset, boolean isAfter) {
        return this.base.createRelativePos(pos, offset, isAfter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public int getIndexDifference(int ipos1, int ipos0) {
        return this.base.getIndexDifference(ipos1, ipos0);
    }

    @Override // gnu.lists.AbstractSequence
    public void releasePos(int ipos) {
        this.base.releasePos(ipos);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public int nextIndex(int ipos) {
        return getIndexDifference(ipos, this.ipos0);
    }

    @Override // gnu.lists.AbstractSequence
    public int compare(int ipos1, int ipos2) {
        return this.base.compare(ipos1, ipos2);
    }

    @Override // gnu.lists.AbstractSequence
    public Object getPosNext(int ipos) {
        return this.base.compare(ipos, this.ipos1) >= 0 ? eofValue : this.base.getPosNext(ipos);
    }

    @Override // gnu.lists.AbstractSequence
    public int getNextKind(int ipos) {
        if (this.base.compare(ipos, this.ipos1) >= 0) {
            return 0;
        }
        return this.base.getNextKind(ipos);
    }

    @Override // gnu.lists.AbstractSequence
    public int startPos() {
        return this.ipos0;
    }

    @Override // gnu.lists.AbstractSequence
    public int endPos() {
        return this.ipos1;
    }

    @Override // gnu.lists.AbstractSequence
    public Object getPosPrevious(int ipos) {
        return this.base.compare(ipos, this.ipos0) <= 0 ? eofValue : this.base.getPosPrevious(ipos);
    }

    @Override // gnu.lists.AbstractSequence
    protected Sequence subSequencePos(int ipos0, int ipos1) {
        return new SubSequence(this.base, ipos0, ipos1);
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public void clear() {
        removePosRange(this.ipos0, this.ipos1);
    }

    public void finalize() {
        this.base.releasePos(this.ipos0);
        this.base.releasePos(this.ipos1);
    }
}
