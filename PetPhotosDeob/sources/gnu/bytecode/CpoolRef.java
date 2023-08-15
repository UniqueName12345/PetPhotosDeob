package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class CpoolRef extends CpoolEntry {
    CpoolClass clas;
    CpoolNameAndType nameAndType;
    int tag;

    @Override // gnu.bytecode.CpoolEntry
    public int getTag() {
        return this.tag;
    }

    public final CpoolClass getCpoolClass() {
        return this.clas;
    }

    public final CpoolNameAndType getNameAndType() {
        return this.nameAndType;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CpoolRef(int tag) {
        this.tag = tag;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CpoolRef(ConstantPool cpool, int hash, int tag, CpoolClass clas, CpoolNameAndType nameAndType) {
        super(cpool, hash);
        this.tag = tag;
        this.clas = clas;
        this.nameAndType = nameAndType;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static final int hashCode(CpoolClass clas, CpoolNameAndType nameAndType) {
        return clas.hashCode() ^ nameAndType.hashCode();
    }

    @Override // gnu.bytecode.CpoolEntry
    public int hashCode() {
        if (this.hash == 0) {
            this.hash = hashCode(this.clas, this.nameAndType);
        }
        return this.hash;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // gnu.bytecode.CpoolEntry
    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeByte(this.tag);
        dstr.writeShort(this.clas.index);
        dstr.writeShort(this.nameAndType.index);
    }

    @Override // gnu.bytecode.CpoolEntry
    public void print(ClassTypeWriter dst, int verbosity) {
        String str;
        switch (this.tag) {
            case 9:
                str = "Field";
                break;
            case 10:
                str = "Method";
                break;
            case 11:
                str = "InterfaceMethod";
                break;
            default:
                str = "<Unknown>Ref";
                break;
        }
        if (verbosity > 0) {
            dst.print(str);
            if (verbosity == 2) {
                dst.print(" class: ");
                dst.printOptionalIndex(this.clas);
            } else {
                dst.print(' ');
            }
        }
        this.clas.print(dst, 0);
        if (verbosity < 2) {
            dst.print('.');
        } else {
            dst.print(" name_and_type: ");
            dst.printOptionalIndex(this.nameAndType);
            dst.print('<');
        }
        this.nameAndType.print(dst, 0);
        if (verbosity == 2) {
            dst.print('>');
        }
    }
}
