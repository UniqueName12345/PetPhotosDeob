package gnu.kawa.functions;

import gnu.mapping.Procedure;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* compiled from: GetNamedPart.java */
/* loaded from: classes.dex */
class NamedPartSetter extends gnu.mapping.Setter implements Externalizable {
    public NamedPartSetter(NamedPart getter) {
        super(getter);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileNamedPart:validateNamedPartSetter");
    }

    @Override // gnu.mapping.Setter, gnu.mapping.Procedure
    public int numArgs() {
        return ((NamedPart) this.getter).kind == 'D' ? 8193 : -4096;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Procedure getGetter() {
        return this.getter;
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.getter);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.getter = (Procedure) in.readObject();
    }
}
