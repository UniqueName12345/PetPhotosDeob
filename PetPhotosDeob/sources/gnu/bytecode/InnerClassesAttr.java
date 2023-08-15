package gnu.bytecode;

import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class InnerClassesAttr extends Attribute {
    int count;
    short[] data;

    public InnerClassesAttr(ClassType cl) {
        super("InnerClasses");
        addToFrontOf(cl);
    }

    public InnerClassesAttr(short[] data, ClassType cl) {
        this(cl);
        this.count = (short) (data.length >> 2);
        this.data = data;
    }

    public static InnerClassesAttr getFirstInnerClasses(Attribute attr) {
        while (attr != null && !(attr instanceof InnerClassesAttr)) {
            attr = attr.next;
        }
        return (InnerClassesAttr) attr;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addClass(CpoolClass centry, ClassType owner) {
        int i = this.count;
        this.count = i + 1;
        int i2 = i * 4;
        if (this.data == null) {
            this.data = new short[16];
        } else if (i2 >= this.data.length) {
            short[] tmp = new short[i2 * 2];
            System.arraycopy(this.data, 0, tmp, 0, i2);
            this.data = tmp;
        }
        ConstantPool constants = owner.constants;
        ClassType clas = (ClassType) centry.getClassType();
        String name = clas.getSimpleName();
        int name_index = (name == null || name.length() == 0) ? 0 : constants.addUtf8(name).index;
        this.data[i2] = (short) centry.index;
        ClassType outer = clas.getDeclaringClass();
        this.data[i2 + 1] = outer != null ? (short) constants.addClass(outer).index : (short) 0;
        this.data[i2 + 2] = (short) name_index;
        int flags = clas.getModifiers();
        this.data[i2 + 3] = (short) (flags & (-33));
    }

    @Override // gnu.bytecode.Attribute
    public void assignConstants(ClassType cl) {
        super.assignConstants(cl);
    }

    @Override // gnu.bytecode.Attribute
    public int getLength() {
        return (this.count * 8) + 2;
    }

    @Override // gnu.bytecode.Attribute
    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeShort(this.count);
        for (int i = 0; i < this.count; i++) {
            dstr.writeShort(this.data[i * 4]);
            dstr.writeShort(this.data[(i * 4) + 1]);
            dstr.writeShort(this.data[(i * 4) + 2]);
            dstr.writeShort(this.data[(i * 4) + 3]);
        }
    }

    @Override // gnu.bytecode.Attribute
    public void print(ClassTypeWriter dst) {
        String name;
        String name2;
        char ch;
        ClassType ctype = (ClassType) this.container;
        ConstantPool constants = this.data == null ? null : ctype.getConstants();
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", count: ");
        dst.println(this.count);
        for (int i = 0; i < this.count; i++) {
            int inner_index = constants == null ? 0 : this.data[i * 4] & 65535;
            CpoolClass centry = (constants == null || inner_index == 0) ? null : constants.getForcedClass(inner_index);
            ClassType clas = (centry == null || !(centry.clas instanceof ClassType)) ? null : (ClassType) centry.clas;
            dst.print(' ');
            int access = (inner_index != 0 || clas == null) ? this.data[(i * 4) + 3] & 65535 : clas.getModifiers();
            dst.print(Access.toString(access, Access.INNERCLASS_CONTEXT));
            dst.print(' ');
            if (inner_index == 0 && clas != null) {
                name = clas.getSimpleName();
            } else {
                int index = this.data[(i * 4) + 2] & 65535;
                if (constants == null || index == 0) {
                    name = "(Anonymous)";
                } else {
                    dst.printOptionalIndex(index);
                    name = ((CpoolUtf8) constants.getForced(index, 1)).string;
                }
            }
            dst.print(name);
            dst.print(" = ");
            if (centry != null) {
                name2 = centry.getClassName();
            } else {
                name2 = "(Unknown)";
            }
            dst.print(name2);
            dst.print("; ");
            if (inner_index == 0 && clas != null) {
                String iname = clas.getName();
                int dot = iname.lastIndexOf(46);
                if (dot > 0) {
                    iname = iname.substring(dot + 1);
                }
                int start = iname.lastIndexOf(36) + 1;
                if (start < iname.length() && (ch = iname.charAt(start)) >= '0' && ch <= '9') {
                    dst.print("not a member");
                } else {
                    dst.print("member of ");
                    dst.print(ctype.getName());
                }
            } else {
                int index2 = this.data[(i * 4) + 1] & 65535;
                if (index2 == 0) {
                    dst.print("not a member");
                } else {
                    dst.print("member of ");
                    CpoolEntry oentry = constants.getForced(index2, 7);
                    dst.print(((CpoolClass) oentry).getStringName());
                }
            }
            dst.println();
        }
    }
}
