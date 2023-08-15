package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class CComplex extends Complex implements Externalizable {
    RealNum imag;
    RealNum real;

    public CComplex() {
    }

    public CComplex(RealNum real, RealNum imag) {
        this.real = real;
        this.imag = imag;
    }

    @Override // gnu.math.Quantity
    public RealNum re() {
        return this.real;
    }

    @Override // gnu.math.Quantity
    public RealNum im() {
        return this.imag;
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.real);
        out.writeObject(this.imag);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.real = (RealNum) in.readObject();
        this.imag = (RealNum) in.readObject();
    }
}
