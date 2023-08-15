package gnu.kawa.lispexpr;

import gnu.bytecode.ClassType;
import gnu.kawa.functions.GetNamedPart;
import gnu.mapping.Namespace;
import gnu.mapping.WrappedException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

/* loaded from: classes.dex */
public class ClassNamespace extends Namespace implements Externalizable {
    ClassType ctype;

    public ClassType getClassType() {
        return this.ctype;
    }

    public static ClassNamespace getInstance(String name, ClassType ctype) {
        synchronized (nsTable) {
            Object old = nsTable.get(name);
            if (old instanceof ClassNamespace) {
                return (ClassNamespace) old;
            }
            ClassNamespace ns = new ClassNamespace(ctype);
            nsTable.put(name, ns);
            return ns;
        }
    }

    public ClassNamespace() {
    }

    public ClassNamespace(ClassType ctype) {
        setName("class:" + ctype.getName());
        this.ctype = ctype;
    }

    @Override // gnu.mapping.Namespace, gnu.mapping.HasNamedParts
    public Object get(String name) {
        try {
            return GetNamedPart.getTypePart(this.ctype, name);
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    @Override // gnu.mapping.Namespace, java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.ctype);
    }

    @Override // gnu.mapping.Namespace, java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.ctype = (ClassType) in.readObject();
        setName("class:" + this.ctype.getName());
    }

    @Override // gnu.mapping.Namespace
    public Object readResolve() throws ObjectStreamException {
        String name = getName();
        if (name != null) {
            Namespace ns = (Namespace) nsTable.get(name);
            if (!(ns instanceof ClassNamespace)) {
                nsTable.put(name, this);
            } else {
                return ns;
            }
        }
        return this;
    }
}
