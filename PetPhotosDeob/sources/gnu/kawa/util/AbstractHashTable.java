package gnu.kawa.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class AbstractHashTable<Entry extends Map.Entry<K, V>, K, V> extends AbstractMap<K, V> {
    public static final int DEFAULT_INITIAL_SIZE = 64;
    protected int mask;
    protected int num_bindings;
    protected Entry[] table;

    protected abstract Entry[] allocEntries(int i);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract int getEntryHashCode(Entry entry);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract Entry getEntryNext(Entry entry);

    protected abstract Entry makeEntry(K k, int i, V v);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void setEntryNext(Entry entry, Entry entry2);

    public AbstractHashTable() {
        this(64);
    }

    public AbstractHashTable(int capacity) {
        int log2Size = 4;
        while (capacity > (1 << log2Size)) {
            log2Size++;
        }
        int capacity2 = 1 << log2Size;
        this.table = allocEntries(capacity2);
        this.mask = capacity2 - 1;
    }

    public int hash(Object key) {
        if (key == null) {
            return 0;
        }
        return key.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int hashToIndex(int hash) {
        return this.mask & (hash ^ (hash >>> 15));
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected boolean matches(Object key, int hash, Entry node) {
        return getEntryHashCode(node) == hash && matches(node.getKey(), key);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean matches(K key1, Object key2) {
        return key1 == key2 || (key1 != null && key1.equals(key2));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object key) {
        return get(key, null);
    }

    public Entry getNode(Object key) {
        int hash = hash(key);
        int index = hashToIndex(hash);
        Entry node = this.table[index];
        while (node != null) {
            if (matches(key, hash, node)) {
                return node;
            }
            node = getEntryNext(node);
        }
        return null;
    }

    public V get(Object key, V defaultValue) {
        Entry node = getNode(key);
        if (node == null) {
            return defaultValue;
        }
        V defaultValue2 = (V) node.getValue();
        return defaultValue2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r15v0, types: [gnu.kawa.util.AbstractHashTable<Entry extends java.util.Map$Entry<K, V>, K, V>, gnu.kawa.util.AbstractHashTable] */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.util.Map$Entry] */
    public void rehash() {
        Map.Entry[] entryArr = this.table;
        int oldCapacity = entryArr.length;
        int newCapacity = oldCapacity * 2;
        Entry[] newTable = (Entry[]) allocEntries(newCapacity);
        int newMask = newCapacity - 1;
        this.table = newTable;
        this.mask = newMask;
        int i = oldCapacity;
        while (true) {
            i--;
            if (i >= 0) {
                Map.Entry entry = entryArr[i];
                if (entry != null && getEntryNext(entry) != null) {
                    Map.Entry entry2 = null;
                    do {
                        Map.Entry entry3 = entry;
                        entry = getEntryNext(entry3);
                        setEntryNext(entry3, entry2);
                        entry2 = entry3;
                    } while (entry != null);
                    entry = entry2;
                }
                Entry element = entry;
                while (element != null) {
                    ?? entryNext = getEntryNext(element);
                    int hash = getEntryHashCode(element);
                    int j = hashToIndex(hash);
                    Entry head = newTable[j];
                    setEntryNext(element, head);
                    newTable[j] = element;
                    element = entryNext;
                }
            } else {
                return;
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K key, V value) {
        return put(key, hash(key), value);
    }

    public V put(K key, int hash, V value) {
        int index = hashToIndex(hash);
        Entry first = this.table[index];
        Entry node = first;
        while (node != null) {
            if (matches(key, hash, node)) {
                V v = (V) node.getValue();
                node.setValue(value);
                return v;
            }
            node = getEntryNext(node);
        }
        int i = this.num_bindings + 1;
        this.num_bindings = i;
        if (i >= this.table.length) {
            rehash();
            index = hashToIndex(hash);
            first = this.table[index];
        }
        Entry node2 = makeEntry(key, hash, value);
        setEntryNext(node2, first);
        this.table[index] = node2;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object key) {
        int hash = hash(key);
        int index = hashToIndex(hash);
        Entry prev = null;
        Entry node = this.table[index];
        while (node != null) {
            Entry next = getEntryNext(node);
            if (matches(key, hash, node)) {
                if (prev == null) {
                    this.table[index] = next;
                } else {
                    setEntryNext(prev, next);
                }
                this.num_bindings--;
                return (V) node.getValue();
            }
            prev = node;
            node = next;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Entry[] t = this.table;
        int i = t.length;
        while (true) {
            i--;
            if (i >= 0) {
                Entry e = t[i];
                while (e != null) {
                    Entry next = getEntryNext(e);
                    setEntryNext(e, null);
                    e = next;
                }
                t[i] = null;
            } else {
                this.num_bindings = 0;
                return;
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.num_bindings;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return new AbstractEntrySet(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class AbstractEntrySet<Entry extends Map.Entry<K, V>, K, V> extends AbstractSet<Entry> {
        AbstractHashTable<Entry, K, V> htable;

        public AbstractEntrySet(AbstractHashTable<Entry, K, V> htable) {
            this.htable = htable;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.htable.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Entry> iterator() {
            return (Iterator<Entry>) new Iterator<Entry>() { // from class: gnu.kawa.util.AbstractHashTable.AbstractEntrySet.1
                int curIndex = -1;
                Entry currentEntry;
                Entry nextEntry;
                int nextIndex;
                Entry previousEntry;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    if (this.curIndex < 0) {
                        this.nextIndex = AbstractEntrySet.this.htable.table.length;
                        this.curIndex = this.nextIndex;
                        advance();
                    }
                    return this.nextEntry != null;
                }

                private void advance() {
                    while (this.nextEntry == null) {
                        int i = this.nextIndex - 1;
                        this.nextIndex = i;
                        if (i >= 0) {
                            this.nextEntry = AbstractEntrySet.this.htable.table[this.nextIndex];
                        } else {
                            return;
                        }
                    }
                }

                @Override // java.util.Iterator
                public Entry next() {
                    if (this.nextEntry == null) {
                        throw new NoSuchElementException();
                    }
                    this.previousEntry = this.currentEntry;
                    this.currentEntry = this.nextEntry;
                    this.curIndex = this.nextIndex;
                    this.nextEntry = AbstractEntrySet.this.htable.getEntryNext(this.currentEntry);
                    advance();
                    return this.currentEntry;
                }

                @Override // java.util.Iterator
                public void remove() {
                    if (this.previousEntry == this.currentEntry) {
                        throw new IllegalStateException();
                    }
                    if (this.previousEntry == null) {
                        AbstractEntrySet.this.htable.table[this.curIndex] = this.nextEntry;
                    } else {
                        AbstractEntrySet.this.htable.setEntryNext(this.previousEntry, this.nextEntry);
                    }
                    AbstractHashTable<Entry, K, V> abstractHashTable = AbstractEntrySet.this.htable;
                    abstractHashTable.num_bindings--;
                    this.previousEntry = this.currentEntry;
                }
            };
        }
    }
}
