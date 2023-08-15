package gnu.mapping;

import gnu.lists.Sequence;

/* loaded from: classes.dex */
public class ValueStack extends Values implements Sequence {
    @Override // gnu.lists.TreeList, gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public void clear() {
        this.oindex = 0;
        super.clear();
    }
}
