package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

/* loaded from: classes.dex */
public class FollowingSiblingAxis extends TreeScanner {
    public static FollowingSiblingAxis make(NodePredicate type) {
        FollowingSiblingAxis axis = new FollowingSiblingAxis();
        axis.type = type;
        return axis;
    }

    @Override // gnu.kawa.xml.TreeScanner
    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        int limit = seq.endPos();
        while (true) {
            ipos = seq.nextMatching(ipos, this.type, limit, false);
            if (ipos != 0) {
                out.writePosition(seq, ipos);
            } else {
                return;
            }
        }
    }
}
