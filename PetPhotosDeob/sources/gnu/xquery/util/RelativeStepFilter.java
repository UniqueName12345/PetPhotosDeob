package gnu.xquery.util;

import gnu.kawa.xml.SortedNodes;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumer;
import gnu.lists.FilterConsumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;

/* loaded from: classes.dex */
public class RelativeStepFilter extends FilterConsumer implements PositionConsumer {
    char seen;
    SortedNodes snodes;

    public RelativeStepFilter(Consumer base) {
        super(base);
    }

    @Override // gnu.lists.PositionConsumer
    public void consume(SeqPosition position) {
        writePosition(position.sequence, position.ipos);
    }

    @Override // gnu.lists.FilterConsumer, gnu.lists.Consumer
    public void writeObject(Object v) {
        if (v instanceof SeqPosition) {
            SeqPosition n = (SeqPosition) v;
            writePosition(n.sequence, n.ipos);
            return;
        }
        super.writeObject(v);
    }

    @Override // gnu.lists.FilterConsumer
    protected void beforeContent() {
        if (this.seen == 'N') {
            throw new Error("path returns mix of atoms and nodes");
        }
        this.seen = 'A';
    }

    @Override // gnu.lists.PositionConsumer
    public void writePosition(AbstractSequence seq, int ipos) {
        if (this.seen == 'A') {
            throw new Error("path returns mix of atoms and nodes");
        }
        this.seen = 'N';
        if (this.snodes == null) {
            this.snodes = new SortedNodes();
        }
        this.snodes.writePosition(seq, ipos);
    }

    public void finish() {
        if (this.snodes != null) {
            this.snodes.consume(this.base);
        }
        this.snodes = null;
    }
}
