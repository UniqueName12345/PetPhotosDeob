package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class CpoolUtf8 extends CpoolEntry {
    String string;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CpoolUtf8() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CpoolUtf8(ConstantPool cpool, int h, String s) {
        super(cpool, h);
        this.string = s;
    }

    @Override // gnu.bytecode.CpoolEntry
    public int hashCode() {
        if (this.hash == 0) {
            this.hash = this.string.hashCode();
        }
        return this.hash;
    }

    public final void intern() {
        this.string = this.string.intern();
    }

    @Override // gnu.bytecode.CpoolEntry
    public int getTag() {
        return 1;
    }

    public final String getString() {
        return this.string;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // gnu.bytecode.CpoolEntry
    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeByte(1);
        dstr.writeUTF(this.string);
    }

    @Override // gnu.bytecode.CpoolEntry
    public void print(ClassTypeWriter dst, int verbosity) {
        if (verbosity > 0) {
            dst.print("Utf8: ");
        }
        dst.printQuotedString(this.string);
    }
}
