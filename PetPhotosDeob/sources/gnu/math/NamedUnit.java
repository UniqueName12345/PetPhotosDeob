package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

/* loaded from: classes.dex */
public class NamedUnit extends Unit implements Externalizable {
    Unit base;
    NamedUnit chain;
    String name;
    double scale;

    public NamedUnit() {
    }

    public NamedUnit(String name, DQuantity value) {
        this.name = name.intern();
        this.scale = value.factor;
        this.base = value.unt;
        init();
    }

    public NamedUnit(String name, double factor, Unit base) {
        this.name = name;
        this.base = base;
        this.scale = factor;
        init();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void init() {
        this.factor = this.scale * this.base.factor;
        this.dims = this.base.dims;
        this.name = this.name.intern();
        int hash = this.name.hashCode();
        int index = (Integer.MAX_VALUE & hash) % table.length;
        this.chain = table[index];
        table[index] = this;
    }

    @Override // gnu.math.Unit
    public String getName() {
        return this.name;
    }

    public static NamedUnit lookup(String name) {
        String name2 = name.intern();
        int hash = name2.hashCode();
        int index = (Integer.MAX_VALUE & hash) % table.length;
        for (NamedUnit unit = table[index]; unit != null; unit = unit.chain) {
            if (unit.name == name2) {
                return unit;
            }
        }
        return null;
    }

    public static NamedUnit lookup(String name, double scale, Unit base) {
        String name2 = name.intern();
        int hash = name2.hashCode();
        int index = (Integer.MAX_VALUE & hash) % table.length;
        for (NamedUnit unit = table[index]; unit != null; unit = unit.chain) {
            if (unit.name == name2 && unit.scale == scale && unit.base == base) {
                return unit;
            }
        }
        return null;
    }

    public static NamedUnit make(String name, double scale, Unit base) {
        NamedUnit old = lookup(name, scale, base);
        return old == null ? new NamedUnit(name, scale, base) : old;
    }

    public static NamedUnit make(String name, Quantity value) {
        double scale;
        if (value instanceof DQuantity) {
            scale = ((DQuantity) value).factor;
        } else if (value.imValue() != 0.0d) {
            throw new ArithmeticException("defining " + name + " using complex value");
        } else {
            scale = value.re().doubleValue();
        }
        Unit base = value.unit();
        NamedUnit old = lookup(name, scale, base);
        return old == null ? new NamedUnit(name, scale, base) : old;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.name);
        out.writeDouble(this.scale);
        out.writeObject(this.base);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
        this.scale = in.readDouble();
        this.base = (Unit) in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        NamedUnit unit = lookup(this.name, this.scale, this.base);
        if (unit == null) {
            init();
            return this;
        }
        return unit;
    }
}
