package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class DQuantity extends Quantity implements Externalizable {
    double factor;
    Unit unt;

    @Override // gnu.math.Quantity
    public final Unit unit() {
        return this.unt;
    }

    @Override // gnu.math.Quantity
    public final Complex number() {
        return new DFloNum(this.factor);
    }

    @Override // gnu.math.Quantity
    public final RealNum re() {
        return new DFloNum(this.factor);
    }

    @Override // gnu.math.Quantity, java.lang.Number
    public final double doubleValue() {
        return this.factor * this.unt.factor;
    }

    public DQuantity(double factor, Unit unit) {
        this.factor = factor;
        this.unt = unit;
    }

    @Override // gnu.math.Numeric
    public boolean isExact() {
        return false;
    }

    @Override // gnu.math.Numeric
    public boolean isZero() {
        return this.factor == 0.0d;
    }

    public static DQuantity add(DQuantity x, DQuantity y, double k) {
        if (x.dimensions() != y.dimensions()) {
            throw new ArithmeticException("units mis-match");
        }
        double unit_ratio = y.unit().factor / x.unit().factor;
        return new DQuantity(x.factor + (k * unit_ratio * y.factor), x.unit());
    }

    public static DQuantity times(DQuantity x, DQuantity y) {
        double factor = x.factor * y.factor;
        Unit unit = Unit.times(x.unit(), y.unit());
        return new DQuantity(factor, unit);
    }

    public static DQuantity divide(DQuantity x, DQuantity y) {
        double factor = x.factor / y.factor;
        Unit unit = Unit.divide(x.unit(), y.unit());
        return new DQuantity(factor, unit);
    }

    @Override // gnu.math.Quantity, gnu.math.Numeric
    public Numeric add(Object y, int k) {
        if (y instanceof DQuantity) {
            return add(this, (DQuantity) y, k);
        }
        if (dimensions() == Dimensions.Empty && (y instanceof RealNum)) {
            return new DQuantity(this.factor + (k * ((RealNum) y).doubleValue()), unit());
        }
        if (!(y instanceof Numeric)) {
            throw new IllegalArgumentException();
        }
        return ((Numeric) y).addReversed(this, k);
    }

    @Override // gnu.math.Quantity, gnu.math.Numeric
    public Numeric addReversed(Numeric x, int k) {
        if (dimensions() == Dimensions.Empty && (x instanceof RealNum)) {
            return new DFloNum(((RealNum) x).doubleValue() + (k * this.factor));
        }
        throw new IllegalArgumentException();
    }

    @Override // gnu.math.Quantity, gnu.math.Numeric
    public Numeric mul(Object y) {
        if (y instanceof DQuantity) {
            return times(this, (DQuantity) y);
        }
        if (y instanceof RealNum) {
            return new DQuantity(this.factor * ((RealNum) y).doubleValue(), unit());
        }
        if (!(y instanceof Numeric)) {
            throw new IllegalArgumentException();
        }
        return ((Numeric) y).mulReversed(this);
    }

    @Override // gnu.math.Quantity, gnu.math.Numeric
    public Numeric mulReversed(Numeric x) {
        if (x instanceof RealNum) {
            return new DQuantity(((RealNum) x).doubleValue() * this.factor, unit());
        }
        throw new IllegalArgumentException();
    }

    @Override // gnu.math.Quantity, gnu.math.Numeric
    public Numeric div(Object y) {
        if (y instanceof DQuantity) {
            DQuantity qy = (DQuantity) y;
            if (dimensions() == qy.dimensions()) {
                return new DFloNum((this.factor * unit().doubleValue()) / (qy.factor * qy.unit().factor));
            }
            return divide(this, qy);
        } else if (y instanceof RealNum) {
            return new DQuantity(this.factor / ((RealNum) y).doubleValue(), unit());
        } else {
            if (!(y instanceof Numeric)) {
                throw new IllegalArgumentException();
            }
            return ((Numeric) y).divReversed(this);
        }
    }

    @Override // gnu.math.Quantity, gnu.math.Numeric
    public Numeric divReversed(Numeric x) {
        if (x instanceof RealNum) {
            return new DQuantity(((RealNum) x).doubleValue() / this.factor, Unit.divide((Unit) Unit.Empty, unit()));
        }
        throw new IllegalArgumentException();
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(this.factor);
        out.writeObject(this.unt);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.factor = in.readDouble();
        this.unt = (Unit) in.readObject();
    }
}
