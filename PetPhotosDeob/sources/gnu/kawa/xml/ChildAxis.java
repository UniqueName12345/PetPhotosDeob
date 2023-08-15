package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

/* loaded from: classes.dex */
public class ChildAxis extends TreeScanner {
    public static ChildAxis make(NodePredicate type) {
        ChildAxis axis = new ChildAxis();
        axis.type = type;
        return axis;
    }

    @Override // gnu.kawa.xml.TreeScanner
    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        int child = seq.firstChildPos(ipos, this.type);
        while (child != 0) {
            out.writePosition(seq, child);
            child = seq.nextMatching(child, this.type, -1, false);
        }
    }
}
