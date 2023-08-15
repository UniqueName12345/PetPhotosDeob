package gnu.text;

import gnu.kawa.util.AbstractWeakHashTable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Char.java */
/* loaded from: classes.dex */
public class CharMap extends AbstractWeakHashTable<Char, Char> {
    public Char get(int key) {
        cleanup();
        int index = hashToIndex(key);
        for (AbstractWeakHashTable.WEntry<Char, Char> node = ((AbstractWeakHashTable.WEntry[]) this.table)[index]; node != null; node = node.next) {
            Char val = node.getValue();
            if (val != null && val.intValue() == key) {
                return val;
            }
        }
        Char val2 = new Char(key);
        super.put(val2, val2);
        return val2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.util.AbstractWeakHashTable
    public Char getKeyFromValue(Char ch) {
        return ch;
    }

    protected boolean matches(Char oldValue, Char newValue) {
        return oldValue.intValue() == newValue.intValue();
    }
}
