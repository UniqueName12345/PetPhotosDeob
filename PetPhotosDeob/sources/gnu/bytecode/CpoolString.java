package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class CpoolString extends CpoolEntry {
    CpoolUtf8 str;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CpoolString() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CpoolString(ConstantPool cpool, int hash, CpoolUtf8 str) {
        super(cpool, hash);
        this.str = str;
    }

    @Override // gnu.bytecode.CpoolEntry
    public int getTag() {
        return 8;
    }

    public final CpoolUtf8 getString() {
        return this.str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final int hashCode(CpoolUtf8 str) {
        return str.hashCode() ^ 62223;
    }

    @Override // gnu.bytecode.CpoolEntry
    public int hashCode() {
        if (this.hash == 0) {
            this.hash = hashCode(this.str);
        }
        return this.hash;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // gnu.bytecode.CpoolEntry
    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeByte(8);
        dstr.writeShort(this.str.index);
    }

    @Override // gnu.bytecode.CpoolEntry
    public void print(ClassTypeWriter dst, int verbosity) {
        if (verbosity > 0) {
            dst.print("String ");
            if (verbosity == 2) {
                dst.printOptionalIndex(this.str);
            }
        }
        dst.printConstantTersely(this.str.index, 1);
    }
}
