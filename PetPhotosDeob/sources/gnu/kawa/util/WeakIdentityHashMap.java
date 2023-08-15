package gnu.kawa.util;

import java.lang.ref.ReferenceQueue;
import java.util.Map;

/* loaded from: classes.dex */
public class WeakIdentityHashMap<K, V> extends AbstractHashTable<WeakHashNode<K, V>, K, V> {
    ReferenceQueue<K> rqueue;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.util.AbstractHashTable
    public /* bridge */ /* synthetic */ int getEntryHashCode(Map.Entry x0) {
        return getEntryHashCode((WeakHashNode) ((WeakHashNode) x0));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.util.AbstractHashTable
    public /* bridge */ /* synthetic */ Map.Entry getEntryNext(Map.Entry x0) {
        return getEntryNext((WeakHashNode) ((WeakHashNode) x0));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // gnu.kawa.util.AbstractHashTable
    protected /* bridge */ /* synthetic */ Map.Entry makeEntry(Object x0, int x1, Object x2) {
        return makeEntry((WeakIdentityHashMap<K, V>) x0, x1, (int) x2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.util.AbstractHashTable
    public /* bridge */ /* synthetic */ void setEntryNext(Map.Entry x0, Map.Entry x1) {
        setEntryNext((WeakHashNode) ((WeakHashNode) x0), (WeakHashNode) ((WeakHashNode) x1));
    }

    public WeakIdentityHashMap() {
        super(64);
        this.rqueue = new ReferenceQueue<>();
    }

    public WeakIdentityHashMap(int capacity) {
        super(capacity);
        this.rqueue = new ReferenceQueue<>();
    }

    protected int getEntryHashCode(WeakHashNode<K, V> entry) {
        return entry.hash;
    }

    protected WeakHashNode<K, V> getEntryNext(WeakHashNode<K, V> entry) {
        return entry.next;
    }

    protected void setEntryNext(WeakHashNode<K, V> entry, WeakHashNode<K, V> next) {
        entry.next = next;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.util.AbstractHashTable
    public WeakHashNode<K, V>[] allocEntries(int n) {
        return new WeakHashNode[n];
    }

    @Override // gnu.kawa.util.AbstractHashTable
    public int hash(Object key) {
        return System.identityHashCode(key);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.util.AbstractHashTable
    public boolean matches(K key1, Object key2) {
        return key1 == key2;
    }

    @Override // gnu.kawa.util.AbstractHashTable
    protected WeakHashNode<K, V> makeEntry(K key, int hash, V value) {
        WeakHashNode<K, V> node = new WeakHashNode<>(key, this.rqueue, hash);
        node.value = value;
        return node;
    }

    @Override // gnu.kawa.util.AbstractHashTable
    public V get(Object key, V defaultValue) {
        cleanup();
        return (V) super.get(key, defaultValue);
    }

    @Override // gnu.kawa.util.AbstractHashTable
    public V put(K key, int hash, V value) {
        cleanup();
        return (V) super.put(key, hash, value);
    }

    @Override // gnu.kawa.util.AbstractHashTable, java.util.AbstractMap, java.util.Map
    public V remove(Object key) {
        cleanup();
        return (V) super.remove(key);
    }

    void cleanup() {
        AbstractWeakHashTable.cleanup(this, this.rqueue);
    }
}
