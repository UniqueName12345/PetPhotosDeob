package gnu.kawa.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class AbstractWeakHashTable<K, V> extends AbstractHashTable<WEntry<K, V>, K, V> {
    ReferenceQueue<V> rqueue;

    protected abstract K getKeyFromValue(V v);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.util.AbstractHashTable
    public /* bridge */ /* synthetic */ int getEntryHashCode(Map.Entry x0) {
        return getEntryHashCode((WEntry) ((WEntry) x0));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.util.AbstractHashTable
    public /* bridge */ /* synthetic */ Map.Entry getEntryNext(Map.Entry x0) {
        return getEntryNext((WEntry) ((WEntry) x0));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // gnu.kawa.util.AbstractHashTable
    protected /* bridge */ /* synthetic */ Map.Entry makeEntry(Object x0, int x1, Object x2) {
        return makeEntry((AbstractWeakHashTable<K, V>) x0, x1, (int) x2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.util.AbstractHashTable
    public /* bridge */ /* synthetic */ void setEntryNext(Map.Entry x0, Map.Entry x1) {
        setEntryNext((WEntry) ((WEntry) x0), (WEntry) ((WEntry) x1));
    }

    public AbstractWeakHashTable() {
        super(64);
        this.rqueue = new ReferenceQueue<>();
    }

    public AbstractWeakHashTable(int capacity) {
        super(capacity);
        this.rqueue = new ReferenceQueue<>();
    }

    protected int getEntryHashCode(WEntry<K, V> entry) {
        return entry.hash;
    }

    protected WEntry<K, V> getEntryNext(WEntry<K, V> entry) {
        return entry.next;
    }

    protected void setEntryNext(WEntry<K, V> entry, WEntry<K, V> next) {
        entry.next = next;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.util.AbstractHashTable
    public WEntry<K, V>[] allocEntries(int n) {
        return new WEntry[n];
    }

    protected V getValueIfMatching(WEntry<K, V> node, Object key) {
        V val = node.getValue();
        if (val == null || !matches(getKeyFromValue(val), key)) {
            return null;
        }
        return val;
    }

    @Override // gnu.kawa.util.AbstractHashTable
    public V get(Object key, V defaultValue) {
        cleanup();
        return (V) super.get(key, defaultValue);
    }

    @Override // gnu.kawa.util.AbstractHashTable
    public int hash(Object key) {
        return System.identityHashCode(key);
    }

    protected boolean valuesEqual(V oldValue, V newValue) {
        return oldValue == newValue;
    }

    @Override // gnu.kawa.util.AbstractHashTable
    protected WEntry<K, V> makeEntry(K key, int hash, V value) {
        return new WEntry<>(value, this, hash);
    }

    @Override // gnu.kawa.util.AbstractHashTable, java.util.AbstractMap, java.util.Map
    public V put(K key, V value) {
        cleanup();
        int hash = hash(key);
        int index = hashToIndex(hash);
        WEntry<K, V> first = ((WEntry[]) this.table)[index];
        WEntry<K, V> node = first;
        WEntry<K, V> prev = null;
        V v = null;
        while (node != null) {
            V curValue = node.getValue();
            if (curValue == value) {
                return curValue;
            }
            WEntry<K, V> next = node.next;
            if (curValue != null && valuesEqual(curValue, value)) {
                if (prev == null) {
                    ((WEntry[]) this.table)[index] = next;
                } else {
                    prev.next = next;
                }
                v = curValue;
            } else {
                prev = node;
            }
            node = next;
        }
        int i = this.num_bindings + 1;
        this.num_bindings = i;
        if (i >= ((WEntry[]) this.table).length) {
            rehash();
            index = hashToIndex(hash);
            first = ((WEntry[]) this.table)[index];
        }
        WEntry<K, V> node2 = makeEntry((AbstractWeakHashTable<K, V>) null, hash, (int) value);
        node2.next = first;
        ((WEntry[]) this.table)[index] = node2;
        return v;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void cleanup() {
        cleanup(this, this.rqueue);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static <Entry extends Map.Entry<K, V>, K, V> void cleanup(AbstractHashTable<Entry, ?, ?> map, ReferenceQueue<?> rqueue) {
        while (true) {
            Map.Entry entry = (Map.Entry) rqueue.poll();
            if (entry != null) {
                int index = map.hashToIndex(map.getEntryHashCode(entry));
                Map.Entry entry2 = null;
                Entry node = map.table[index];
                while (true) {
                    if (node != null) {
                        Entry next = map.getEntryNext(node);
                        if (node == entry) {
                            if (entry2 == null) {
                                map.table[index] = next;
                            } else {
                                map.setEntryNext(entry2, next);
                            }
                        } else {
                            entry2 = node;
                            node = next;
                        }
                    }
                }
                map.num_bindings--;
            } else {
                return;
            }
        }
    }

    /* loaded from: classes.dex */
    public static class WEntry<K, V> extends WeakReference<V> implements Map.Entry<K, V> {
        public int hash;
        AbstractWeakHashTable<K, V> htable;
        public WEntry next;

        public WEntry(V value, AbstractWeakHashTable<K, V> htable, int hash) {
            super(value, htable.rqueue);
            this.htable = htable;
            this.hash = hash;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Map.Entry
        public K getKey() {
            Object obj = get();
            if (obj == null) {
                return null;
            }
            return (K) this.htable.getKeyFromValue(obj);
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return (V) get();
        }

        @Override // java.util.Map.Entry
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
    }
}
