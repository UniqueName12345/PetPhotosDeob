package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.mapping.Values;

/* loaded from: classes.dex */
public class SingletonType extends ObjectType {
    static final SingletonType instance = new SingletonType("singleton");

    public SingletonType(String name) {
        super(name);
    }

    public static final SingletonType getInstance() {
        return instance;
    }

    @Override // gnu.bytecode.ObjectType, gnu.bytecode.Type
    public Class getReflectClass() {
        return getImplementationType().getReflectClass();
    }

    @Override // gnu.bytecode.ObjectType, gnu.bytecode.Type
    public Type getImplementationType() {
        return Type.pointer_type;
    }

    @Override // gnu.bytecode.ObjectType, gnu.bytecode.Type
    public int compare(Type other) {
        int otherRange = OccurrenceType.itemCountRange(other);
        int otherMin = otherRange & 4095;
        int otherMax = otherRange >> 12;
        if (otherMax == 0 || otherMin > 1) {
            return -3;
        }
        if (otherMin == 1 && otherMax == 1) {
            return Type.pointer_type.compare(other);
        }
        int cmp = Type.pointer_type.compare(other);
        return (cmp == 0 || cmp == -1) ? -1 : -2;
    }

    public static Object coerceToSingleton(Object obj) {
        if (obj instanceof Values) {
            obj = ((Values) obj).canonicalize();
        }
        if (obj == null || (obj instanceof Values)) {
            throw new ClassCastException("value is not a singleton");
        }
        return obj;
    }

    @Override // gnu.bytecode.ObjectType, gnu.bytecode.Type
    public Object coerceFromObject(Object obj) {
        return coerceToSingleton(obj);
    }

    @Override // gnu.bytecode.ObjectType, gnu.bytecode.Type
    public void emitCoerceFromObject(CodeAttr code) {
        code.emitInvokeStatic(ClassType.make("gnu.kawa.reflect.SingletonType").getDeclaredMethod("coerceToSingleton", 1));
    }

    @Override // gnu.bytecode.ObjectType, gnu.bytecode.Type
    public boolean isInstance(Object obj) {
        return (obj == null || (obj instanceof Values)) ? false : true;
    }
}
