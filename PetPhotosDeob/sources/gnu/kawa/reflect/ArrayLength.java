package gnu.kawa.reflect;

import gnu.bytecode.Type;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;
import gnu.mapping.PropertySet;
import gnu.math.IntNum;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class ArrayLength extends Procedure1 implements Externalizable {
    Type element_type;

    public ArrayLength(Type element_type) {
        this.element_type = element_type;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileArrays:validateArrayLength");
        Procedure.compilerKey.set((PropertySet) this, "*gnu.kawa.reflect.CompileArrays:getForArrayLength");
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public Object apply1(Object array) {
        return IntNum.make(Array.getLength(array));
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
