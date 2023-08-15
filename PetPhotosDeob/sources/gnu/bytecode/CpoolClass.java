package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class CpoolClass extends CpoolEntry {
    ObjectType clas;
    CpoolUtf8 name;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CpoolClass() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CpoolClass(ConstantPool cpool, int hash, CpoolUtf8 n) {
        super(cpool, hash);
        this.name = n;
    }

    @Override // gnu.bytecode.CpoolEntry
    public int getTag() {
        return 7;
    }

    public final CpoolUtf8 getName() {
        return this.name;
    }

    public final String getStringName() {
        return this.name.string;
    }

    public final String getClassName() {
        return this.name.string.replace('/', '.');
    }

    public final ObjectType getClassType() {
        ObjectType otype;
        ObjectType otype2 = this.clas;
        if (otype2 != null) {
            return otype2;
        }
        String name = this.name.string;
        if (name.charAt(0) == '[') {
            otype = (ObjectType) Type.signatureToType(name);
        } else {
            otype = ClassType.make(name.replace('/', '.'));
        }
        this.clas = otype;
        return otype;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final int hashCode(CpoolUtf8 name) {
        return name.hashCode() ^ 3855;
    }

    @Override // gnu.bytecode.CpoolEntry
    public int hashCode() {
        if (this.hash == 0) {
            this.hash = hashCode(this.name);
        }
        return this.hash;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // gnu.bytecode.CpoolEntry
    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeByte(7);
        dstr.writeShort(this.name.index);
    }

    @Override // gnu.bytecode.CpoolEntry
    public void print(ClassTypeWriter dst, int verbosity) {
        if (verbosity == 1) {
            dst.print("Class ");
        } else if (verbosity > 1) {
            dst.print("Class name: ");
            dst.printOptionalIndex(this.name);
        }
        String str = this.name.string;
        int nlen = str.length();
        if (nlen > 1 && str.charAt(0) == '[') {
            Type.printSignature(str, 0, nlen, dst);
        } else {
            dst.print(str.replace('/', '.'));
        }
    }
}
