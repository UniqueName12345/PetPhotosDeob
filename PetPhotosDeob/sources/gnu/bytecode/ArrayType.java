package gnu.bytecode;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.List;

/* loaded from: classes.dex */
public class ArrayType extends ObjectType implements Externalizable {
    public Type elements;

    public ArrayType(Type elements) {
        this(elements, elements.getName() + "[]");
    }

    ArrayType(Type elements, String name) {
        this.this_name = name;
        this.elements = elements;
    }

    @Override // gnu.bytecode.Type
    public String getSignature() {
        if (this.signature == null) {
            setSignature("[" + this.elements.getSignature());
        }
        return this.signature;
    }

    @Override // gnu.bytecode.ObjectType, gnu.bytecode.Type
    public Type getImplementationType() {
        Type eltype = this.elements.getImplementationType();
        return this.elements == eltype ? this : make(eltype);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ArrayType make(String name) {
        Type elements = Type.getType(name.substring(0, name.length() - 2));
        ArrayType array_type = elements.array_type;
        if (array_type == null) {
            ArrayType array_type2 = new ArrayType(elements, name);
            elements.array_type = array_type2;
            return array_type2;
        }
        return array_type;
    }

    public static ArrayType make(Type elements) {
        ArrayType array_type = elements.array_type;
        if (array_type == null) {
            ArrayType array_type2 = new ArrayType(elements, elements.getName() + "[]");
            elements.array_type = array_type2;
            return array_type2;
        }
        return array_type;
    }

    public Type getComponentType() {
        return this.elements;
    }

    @Override // gnu.bytecode.ObjectType
    public String getInternalName() {
        return getSignature();
    }

    @Override // gnu.bytecode.ObjectType, gnu.bytecode.Type
    public Class getReflectClass() {
        try {
            if (this.reflectClass == null) {
                String cname = getInternalName().replace('/', '.');
                Class elClass = this.elements.getReflectClass();
                this.reflectClass = Class.forName(cname, false, elClass.getClassLoader());
            }
            this.flags |= 16;
        } catch (ClassNotFoundException ex) {
            if ((this.flags & 16) != 0) {
                RuntimeException rex = new RuntimeException("no such array class: " + getName());
                rex.initCause(ex);
                throw rex;
            }
        }
        return this.reflectClass;
    }

    @Override // gnu.bytecode.ObjectType
    public int getMethods(Filter filter, int searchSupers, List<Method> result) {
        if (searchSupers > 0) {
            int count = Type.objectType.getMethods(filter, 0, result);
            if (searchSupers > 1 && filter.select(Type.clone_method)) {
                if (result != null) {
                    Method meth = Type.clone_method;
                    result.add(meth);
                }
                return count + 1;
            }
            return count;
        }
        return 0;
    }

    @Override // gnu.bytecode.ObjectType, gnu.bytecode.Type
    public int compare(Type other) {
        if (other == nullType) {
            return 1;
        }
        if (other instanceof ArrayType) {
            return this.elements.compare(((ArrayType) other).elements);
        }
        if (other.getName().equals("java.lang.Object") || other == toStringType) {
            return -1;
        }
        return -3;
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.elements);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.elements = (Type) in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        ArrayType array_type = this.elements.array_type;
        if (array_type == null) {
            this.elements.array_type = this;
            return this;
        }
        return array_type;
    }
}
