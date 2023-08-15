package gnu.kawa.reflect;

import gnu.bytecode.Type;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.PropertySet;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class ArrayGet extends Procedure2 implements Externalizable {
    Type element_type;

    public ArrayGet(Type element_type) {
        this.element_type = element_type;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileArrays:validateArrayGet");
        Procedure.compilerKey.set((PropertySet) this, "*gnu.kawa.reflect.CompileArrays:getForArrayGet");
    }

    @Override // gnu.mapping.Procedure2, gnu.mapping.Procedure
    public Object apply2(Object array, Object index) {
        Object value = Array.get(array, ((Number) index).intValue());
        return this.element_type.coerceToObject(value);
    }

    @Override // gnu.mapping.Procedure
    public boolean isSideEffectFree() {
        return true;
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.element_type);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.element_type = (Type) in.readObject();
    }
}
