package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

/* loaded from: classes.dex */
public class ParentAxis extends TreeScanner {
    public static ParentAxis make(NodePredicate type) {
        ParentAxis axis = new ParentAxis();
        axis.type = type;
        return axis;
    }

    @Override // gnu.kawa.xml.TreeScanner
    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        int ipos2 = seq.parentPos(ipos);
        int end = seq.endPos();
        if (ipos2 != end && this.type.isInstancePos(seq, ipos2)) {
            out.writePosition(seq, ipos2);
        }
    }
}
