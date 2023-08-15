package gnu.kawa.reflect;

import gnu.bytecode.Type;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;
import gnu.mapping.PropertySet;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class ArrayNew extends Procedure1 implements Externalizable {
    Type element_type;

    public ArrayNew(Type element_type) {
        this.element_type = element_type;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileArrays:validateArrayNew");
        Procedure.compilerKey.set((PropertySet) this, "*gnu.kawa.reflect.CompileArrays:getForArrayNew");
    }

    @Override // gnu.mapping.Procedure
    public boolean isSideEffectFree() {
        return true;
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public Object apply1(Object count) {
        Class clas = this.element_type.getImplementationType().getReflectClass();
        return Array.newInstance(clas, ((Number) count).intValue());
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
