package gnu.kawa.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;

/* loaded from: classes.dex */
public class WeakHashNode<K, V> extends WeakReference<K> implements Map.Entry<K, V> {
    public int hash;
    public WeakHashNode<K, V> next;
    public V value;

    public WeakHashNode(K key, ReferenceQueue<K> q, int hash) {
        super(key, q);
        this.hash = hash;
    }

    @Override // java.util.Map.Entry
    public K getKey() {
        return (K) get();
    }

    @Override // java.util.Map.Entry
    public V getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}
