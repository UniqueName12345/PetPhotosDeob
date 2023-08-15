package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.text.Printable;

/* loaded from: classes.dex */
public abstract class Pattern implements Printable {
    public static ClassType typePattern = ClassType.make("kawa.lang.Pattern");
    private static Type[] matchArgs = {Type.pointer_type, Compilation.objArrayType, Type.intType};
    public static final Method matchPatternMethod = typePattern.addMethod("match", matchArgs, Type.booleanType, 1);

    public abstract boolean match(Object obj, Object[] objArr, int i);

    public abstract int varCount();

    public Object[] match(Object obj) {
        Object[] vars = new Object[varCount()];
        if (match(obj, vars, 0)) {
            return vars;
        }
        return null;
    }
}
