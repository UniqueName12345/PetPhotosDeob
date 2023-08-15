package kawa.lang;

import gnu.lists.Consumer;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class AnyPat extends Pattern implements Printable, Externalizable {
    public static AnyPat make() {
        return new AnyPat();
    }

    @Override // gnu.text.Printable
    public void print(Consumer out) {
        out.write("#<match any>");
    }

    @Override // kawa.lang.Pattern
    public boolean match(Object obj, Object[] vars, int start_vars) {
        vars[start_vars] = obj;
        return true;
    }

    @Override // kawa.lang.Pattern
    public int varCount() {
        return 1;
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }
}
