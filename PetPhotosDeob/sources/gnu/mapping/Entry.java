package gnu.mapping;

import java.lang.ref.WeakReference;

/* compiled from: Table2D.java */
/* loaded from: classes.dex */
class Entry {
    Entry chain;
    Object key1;
    Object key2;
    Object value;

    public Object getKey1() {
        return this.key1 instanceof WeakReference ? ((WeakReference) this.key1).get() : this.key1;
    }

    public Object getKey2() {
        return this.key2 instanceof WeakReference ? ((WeakReference) this.key2).get() : this.key2;
    }

    public boolean matches(Object key1, Object key2) {
        return key1 == getKey1() && key2 == getKey2();
    }

    public Object getValue() {
        if (this.value == this) {
            return null;
        }
        return this.value;
    }
}
