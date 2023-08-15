package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class CpoolNameAndType extends CpoolEntry {
    CpoolUtf8 name;
    CpoolUtf8 type;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CpoolNameAndType() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CpoolNameAndType(ConstantPool cpool, int hash, CpoolUtf8 n, CpoolUtf8 t) {
        super(cpool, hash);
        this.name = n;
        this.type = t;
    }

    @Override // gnu.bytecode.CpoolEntry
    public int getTag() {
        return 12;
    }

    public final CpoolUtf8 getName() {
        return this.name;
    }

    public final CpoolUtf8 getType() {
        return this.type;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final int hashCode(CpoolUtf8 name, CpoolUtf8 type) {
        return name.hashCode() ^ type.hashCode();
    }

    @Override // gnu.bytecode.CpoolEntry
    public int hashCode() {
        if (this.hash == 0) {
            this.hash = hashCode(this.name, this.type);
        }
        return this.hash;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // gnu.bytecode.CpoolEntry
    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeByte(12);
        dstr.writeShort(this.name.index);
        dstr.writeShort(this.type.index);
    }

    @Override // gnu.bytecode.CpoolEntry
    public void print(ClassTypeWriter dst, int verbosity) {
        if (verbosity == 1) {
            dst.print("NameAndType ");
        } else if (verbosity > 1) {
            dst.print("NameAndType name: ");
            dst.printOptionalIndex(this.name);
        }
        dst.printName(this.name.string);
        if (verbosity <= 1) {
            dst.print(' ');
        } else {
            dst.print(", signature: ");
            dst.printOptionalIndex(this.type);
        }
        dst.printSignature(this.type.string);
    }
}
