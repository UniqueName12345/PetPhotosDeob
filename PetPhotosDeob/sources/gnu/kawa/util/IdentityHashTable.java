package gnu.kawa.util;

/* loaded from: classes.dex */
public class IdentityHashTable<K, V> extends GeneralHashTable<K, V> {
    public IdentityHashTable() {
    }

    public IdentityHashTable(int capacity) {
        super(capacity);
    }

    @Override // gnu.kawa.util.AbstractHashTable
    public int hash(Object key) {
        return System.identityHashCode(key);
    }

    @Override // gnu.kawa.util.AbstractHashTable
    public boolean matches(K value1, Object value2) {
        return value1 == value2;
    }
}
