package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Filter;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ClassMethods.java */
/* loaded from: classes.dex */
public class MethodFilter implements Filter {
    ClassType caller;
    int modifiers;
    int modmask;
    String name;
    int nlen;
    ObjectType receiver;

    public MethodFilter(String name, int modifiers, int modmask, ClassType caller, ObjectType receiver) {
        this.name = name;
        this.nlen = name.length();
        this.modifiers = modifiers;
        this.modmask = modmask;
        this.caller = caller;
        this.receiver = receiver;
    }

    @Override // gnu.bytecode.Filter
    public boolean select(Object value) {
        char c;
        Method method = (Method) value;
        String mname = method.getName();
        int mmods = method.getModifiers();
        if ((this.modmask & mmods) == this.modifiers && (mmods & 4096) == 0 && mname.startsWith(this.name)) {
            int mlen = mname.length();
            if (mlen == this.nlen || ((mlen == this.nlen + 2 && mname.charAt(this.nlen) == '$' && ((c = mname.charAt(this.nlen + 1)) == 'V' || c == 'X')) || (mlen == this.nlen + 4 && mname.endsWith("$V$X")))) {
                ClassType declaring = this.receiver instanceof ClassType ? (ClassType) this.receiver : method.getDeclaringClass();
                return this.caller == null || this.caller.isAccessible(declaring, this.receiver, method.getModifiers());
            }
            return false;
        }
        return false;
    }
}
