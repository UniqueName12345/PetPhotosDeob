package gnu.bytecode;

import androidx.appcompat.widget.ActivityChooserView;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public abstract class CpoolEntry {
    int hash;
    public int index;
    CpoolEntry next;

    public abstract int getTag();

    public abstract void print(ClassTypeWriter classTypeWriter, int i);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void write(DataOutputStream dataOutputStream) throws IOException;

    public int getIndex() {
        return this.index;
    }

    public int hashCode() {
        return this.hash;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void add_hashed(ConstantPool cpool) {
        CpoolEntry[] hashTab = cpool.hashTab;
        int index = (this.hash & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) % hashTab.length;
        this.next = hashTab[index];
        hashTab[index] = this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public CpoolEntry() {
    }

    public CpoolEntry(ConstantPool cpool, int h) {
        this.hash = h;
        if (cpool.locked) {
            throw new Error("adding new entry to locked contant pool");
        }
        int i = cpool.count + 1;
        cpool.count = i;
        this.index = i;
        if (cpool.pool == null) {
            cpool.pool = new CpoolEntry[60];
        } else if (this.index >= cpool.pool.length) {
            int old_size = cpool.pool.length;
            int new_size = cpool.pool.length * 2;
            CpoolEntry[] new_pool = new CpoolEntry[new_size];
            for (int i2 = 0; i2 < old_size; i2++) {
                new_pool[i2] = cpool.pool[i2];
            }
            cpool.pool = new_pool;
        }
        if (cpool.hashTab == null || this.index >= 0.6d * cpool.hashTab.length) {
            cpool.rehash();
        }
        cpool.pool[this.index] = this;
        add_hashed(cpool);
    }
}
