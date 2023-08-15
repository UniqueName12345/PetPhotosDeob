package gnu.bytecode;

import com.google.appinventor.components.runtime.util.Ev3Constants;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class MiscAttr extends Attribute {
    byte[] data;
    int dataLength;
    int offset;

    public MiscAttr(String name, byte[] data, int offset, int length) {
        super(name);
        this.data = data;
        this.offset = offset;
        this.dataLength = length;
    }

    public MiscAttr(String name, byte[] data) {
        this(name, data, 0, data.length);
    }

    @Override // gnu.bytecode.Attribute
    public int getLength() {
        return this.dataLength;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int u1(int offset) {
        return this.data[offset] & Ev3Constants.Opcode.TST;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int u2(int offset) {
        return ((this.data[offset] & Ev3Constants.Opcode.TST) << 8) + (this.data[offset + 1] & Ev3Constants.Opcode.TST);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int u1() {
        int i = this.offset;
        this.offset = i + 1;
        return u1(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int u2() {
        int v = u2(this.offset);
        this.offset += 2;
        return v;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void put1(int val) {
        if (this.data == null) {
            this.data = new byte[20];
        } else if (this.dataLength >= this.data.length) {
            byte[] tmp = new byte[this.data.length * 2];
            System.arraycopy(this.data, 0, tmp, 0, this.dataLength);
            this.data = tmp;
        }
        byte[] bArr = this.data;
        int i = this.dataLength;
        this.dataLength = i + 1;
        bArr[i] = (byte) val;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void put2(int val) {
        put1((byte) (val >> 8));
        put1((byte) val);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void put2(int offset, int val) {
        this.data[offset] = (byte) (val >> 8);
        this.data[offset + 1] = (byte) val;
    }

    @Override // gnu.bytecode.Attribute
    public void write(DataOutputStream dstr) throws IOException {
        dstr.write(this.data, this.offset, this.dataLength);
    }

    @Override // gnu.bytecode.Attribute
    public void print(ClassTypeWriter dst) {
        super.print(dst);
        int len = getLength();
        int i = 0;
        while (i < len) {
            int b = this.data[i];
            if (i % 20 == 0) {
                dst.print(' ');
            }
            dst.print(' ');
            dst.print(Character.forDigit((b >> 4) & 15, 16));
            dst.print(Character.forDigit(b & 15, 16));
            i++;
            if (i % 20 == 0 || i == len) {
                dst.println();
            }
        }
    }
}
