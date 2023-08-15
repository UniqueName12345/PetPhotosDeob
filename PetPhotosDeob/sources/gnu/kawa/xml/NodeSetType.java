package gnu.kawa.xml;

import gnu.bytecode.Type;
import gnu.kawa.reflect.OccurrenceType;

/* loaded from: classes.dex */
public class NodeSetType extends OccurrenceType {
    public NodeSetType(Type itemType) {
        super(itemType, 0, -1);
    }

    public static Type getInstance(Type base) {
        return new NodeSetType(base);
    }

    @Override // gnu.kawa.reflect.OccurrenceType, gnu.bytecode.Type
    public String toString() {
        return super.toString() + "node-set";
    }
}
