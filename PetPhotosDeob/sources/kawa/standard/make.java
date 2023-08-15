package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.Keyword;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;
import kawa.lang.Record;

/* loaded from: classes.dex */
public class make extends ProcedureN {
    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return -4095;
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) {
        Class clas;
        int nargs = args.length;
        if (nargs == 0) {
            throw new WrongArguments(this, nargs);
        }
        Object arg_0 = args[0];
        if (arg_0 instanceof Class) {
            clas = (Class) arg_0;
        } else if (arg_0 instanceof ClassType) {
            clas = ((ClassType) arg_0).getReflectClass();
        } else {
            clas = null;
        }
        if (clas == null) {
            throw new WrongType(this, 1, arg_0, "class");
        }
        try {
            Object result = clas.newInstance();
            int i = 1;
            while (i < nargs) {
                int i2 = i + 1;
                Keyword key = (Keyword) args[i];
                i = i2 + 1;
                Object arg = args[i2];
                Record.set1(arg, key.getName(), result);
            }
            return result;
        } catch (Exception ex) {
            throw new WrappedException(ex);
        }
    }
}
