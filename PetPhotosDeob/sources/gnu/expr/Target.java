package gnu.expr;

import gnu.bytecode.Type;

/* loaded from: classes.dex */
public abstract class Target {
    public static final Target Ignore = new IgnoreTarget();
    public static final Target pushObject = new StackTarget(Type.pointer_type);

    public abstract void compileFromStack(Compilation compilation, Type type);

    public abstract Type getType();

    public static Target pushValue(Type type) {
        return type.isVoid() ? Ignore : StackTarget.getInstance(type);
    }
}
