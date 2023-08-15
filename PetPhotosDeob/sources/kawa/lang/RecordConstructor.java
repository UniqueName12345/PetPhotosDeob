package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Type;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;

/* loaded from: classes.dex */
public class RecordConstructor extends ProcedureN {
    Field[] fields;
    ClassType type;

    public RecordConstructor(ClassType type, Field[] fields) {
        this.type = type;
        this.fields = fields;
    }

    public RecordConstructor(Class clas, Field[] fields) {
        this((ClassType) Type.make(clas), fields);
    }

    public RecordConstructor(Class clas) {
        init((ClassType) Type.make(clas));
    }

    public RecordConstructor(ClassType type) {
        init(type);
    }

    private void init(ClassType type) {
        int i;
        this.type = type;
        Field list = type.getFields();
        int count = 0;
        for (Field fld = list; fld != null; fld = fld.getNext()) {
            if ((fld.getModifiers() & 9) == 1) {
                count++;
            }
        }
        this.fields = new Field[count];
        Field fld2 = list;
        int i2 = 0;
        while (fld2 != null) {
            if ((fld2.getModifiers() & 9) == 1) {
                i = i2 + 1;
                this.fields[i2] = fld2;
            } else {
                i = i2;
            }
            fld2 = fld2.getNext();
            i2 = i;
        }
    }

    public RecordConstructor(Class clas, Object fieldsList) {
        this((ClassType) Type.make(clas), fieldsList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0056, code lost:
        r9.fields[r2] = r0;
        r11 = r5.getCdr();
        r2 = r2 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public RecordConstructor(gnu.bytecode.ClassType r10, java.lang.Object r11) {
        /*
            r9 = this;
            r9.<init>()
            r9.type = r10
            if (r11 != 0) goto Lb
            r9.init(r10)
        La:
            return
        Lb:
            r6 = 0
            int r4 = gnu.lists.LList.listLength(r11, r6)
            gnu.bytecode.Field[] r6 = new gnu.bytecode.Field[r4]
            r9.fields = r6
            gnu.bytecode.Field r3 = r10.getFields()
            r2 = 0
        L19:
            if (r2 >= r4) goto La
            r5 = r11
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            java.lang.Object r6 = r5.getCar()
            java.lang.String r1 = r6.toString()
            r0 = r3
        L27:
            if (r0 != 0) goto L50
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "no such field "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r1)
            java.lang.String r8 = " in "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r8 = r10.getName()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        L50:
            java.lang.String r6 = r0.getSourceName()
            if (r6 != r1) goto L61
            gnu.bytecode.Field[] r6 = r9.fields
            r6[r2] = r0
            java.lang.Object r11 = r5.getCdr()
            int r2 = r2 + 1
            goto L19
        L61:
            gnu.bytecode.Field r0 = r0.getNext()
            goto L27
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.RecordConstructor.<init>(gnu.bytecode.ClassType, java.lang.Object):void");
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        int nargs = this.fields.length;
        return (nargs << 12) | nargs;
    }

    @Override // gnu.mapping.PropertySet, gnu.mapping.Named
    public String getName() {
        return this.type.getName() + " constructor";
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) {
        try {
            Object obj = this.type.getReflectClass().newInstance();
            if (args.length != this.fields.length) {
                throw new WrongArguments(this, args.length);
            }
            for (int i = 0; i < args.length; i++) {
                Field fld = this.fields[i];
                try {
                    fld.getReflectField().set(obj, args[i]);
                } catch (Exception ex) {
                    throw new WrappedException("illegal access for field " + fld.getName(), ex);
                }
            }
            return obj;
        } catch (IllegalAccessException ex2) {
            throw new GenericError(ex2.toString());
        } catch (InstantiationException ex3) {
            throw new GenericError(ex3.toString());
        }
    }
}
