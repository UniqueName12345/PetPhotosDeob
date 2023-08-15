package gnu.lists;

/* loaded from: classes.dex */
public class ExtPosition extends SeqPosition {
    int position = -1;

    @Override // gnu.lists.SeqPosition
    public int getPos() {
        if (this.position < 0) {
            this.position = PositionManager.manager.register(this);
        }
        return this.position;
    }

    @Override // gnu.lists.SeqPosition
    public void setPos(AbstractSequence seq, int ipos) {
        throw seq.unsupported("setPos");
    }

    @Override // gnu.lists.SeqPosition
    public final boolean isAfter() {
        return (this.ipos & 1) != 0;
    }

    @Override // gnu.lists.SeqPosition
    public void release() {
        if (this.position >= 0) {
            PositionManager.manager.release(this.position);
        }
        this.sequence = null;
    }
}
