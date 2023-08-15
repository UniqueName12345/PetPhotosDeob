package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class CQuantity extends Quantity implements Externalizable {
    Complex num;
    Unit unt;

    public CQuantity(Complex num, Unit unit) {
        this.num = num;
        this.unt = unit;
    }

    public CQuantity(RealNum real, RealNum imag, Unit unit) {
        this.num = new CComplex(real, imag);
        this.unt = unit;
    }

    @Override // gnu.math.Quantity
    public Complex number() {
        return this.num;
    }

    @Override // gnu.math.Quantity
    public Unit unit() {
        return this.unt;
    }

    @Override // gnu.math.Numeric
    public boolean isExact() {
        return this.num.isExact();
    }

    @Override // gnu.math.Numeric
    public boolean isZero() {
        return this.num.isZero();
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.num);
        out.writeObject(this.unt);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.num = (Complex) in.readObject();
        this.unt = (Unit) in.readObject();
    }
}
