package gnu.expr;

import gnu.lists.Consumer;
import gnu.lists.Sequence;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

/* loaded from: classes.dex */
public class Special implements Printable, Externalizable {
    private String name;
    public static final Special undefined = new Special("undefined");
    public static final Special optional = new Special("optional");
    public static final Special rest = new Special("rest");
    public static final Special key = new Special("key");
    public static final Special dfault = new Special("default");
    public static final Special abstractSpecial = new Special("abstract");
    public static final Object eof = Sequence.eofValue;

    public Special() {
    }

    private Special(String n) {
        this.name = new String(n);
    }

    public static Special make(String name) {
        return name == "optional" ? optional : name == "rest" ? rest : name == "key" ? key : name == "default" ? dfault : new Special(name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public final String toString() {
        return "#!" + this.name;
    }

    @Override // gnu.text.Printable
    public void print(Consumer out) {
        out.write("#!");
        out.write(this.name);
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.name);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
    }

    public Object readResolve() throws ObjectStreamException {
        return make(this.name);
    }
}
