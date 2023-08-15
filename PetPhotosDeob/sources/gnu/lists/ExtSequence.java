package gnu.lists;

/* loaded from: classes.dex */
public abstract class ExtSequence extends AbstractSequence {
    @Override // gnu.lists.AbstractSequence
    public int copyPos(int ipos) {
        return ipos <= 0 ? ipos : PositionManager.manager.register(PositionManager.getPositionObject(ipos).copy());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public void releasePos(int ipos) {
        if (ipos > 0) {
            PositionManager.manager.release(ipos);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public boolean isAfterPos(int ipos) {
        return ipos <= 0 ? ipos < 0 : (PositionManager.getPositionObject(ipos).ipos & 1) != 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public int nextIndex(int ipos) {
        if (ipos == -1) {
            return size();
        }
        if (ipos == 0) {
            return 0;
        }
        return PositionManager.getPositionObject(ipos).nextIndex();
    }
}
