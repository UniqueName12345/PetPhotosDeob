package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class IntFraction extends RatNum implements Externalizable {
    IntNum den;
    IntNum num;

    IntFraction() {
    }

    public IntFraction(IntNum num, IntNum den) {
        this.num = num;
        this.den = den;
    }

    @Override // gnu.math.RatNum
    public final IntNum numerator() {
        return this.num;
    }

    @Override // gnu.math.RatNum
    public final IntNum denominator() {
        return this.den;
    }

    @Override // gnu.math.RealNum
    public final boolean isNegative() {
        return this.num.isNegative();
    }

    @Override // gnu.math.RealNum
    public final int sign() {
        return this.num.sign();
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public final int compare(Object obj) {
        return obj instanceof RatNum ? RatNum.compare((RatNum) this, (RatNum) obj) : ((RealNum) obj).compareReversed(this);
    }

    @Override // gnu.math.Quantity, gnu.math.Numeric
    public int compareReversed(Numeric x) {
        return RatNum.compare((RatNum) x, (RatNum) this);
    }

    @Override // gnu.math.RealNum, gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric add(Object y, int k) {
        if (y instanceof RatNum) {
            return RatNum.add((RatNum) this, (RatNum) y, k);
        }
        if (!(y instanceof Numeric)) {
            throw new IllegalArgumentException();
        }
        return ((Numeric) y).addReversed(this, k);
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric addReversed(Numeric x, int k) {
        if (!(x instanceof RatNum)) {
            throw new IllegalArgumentException();
        }
        return RatNum.add((RatNum) x, (RatNum) this, k);
    }

    @Override // gnu.math.RealNum, gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric mul(Object y) {
        if (y instanceof RatNum) {
            return RatNum.times((RatNum) this, (RatNum) y);
        }
        if (!(y instanceof Numeric)) {
            throw new IllegalArgumentException();
        }
        return ((Numeric) y).mulReversed(this);
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric mulReversed(Numeric x) {
        if (!(x instanceof RatNum)) {
            throw new IllegalArgumentException();
        }
        return RatNum.times((RatNum) x, (RatNum) this);
    }

    @Override // gnu.math.RealNum, gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric div(Object y) {
        if (y instanceof RatNum) {
            return RatNum.divide((RatNum) this, (RatNum) y);
        }
        if (!(y instanceof Numeric)) {
            throw new IllegalArgumentException();
        }
        return ((Numeric) y).divReversed(this);
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric divReversed(Numeric x) {
        if (!(x instanceof RatNum)) {
            throw new IllegalArgumentException();
        }
        return RatNum.divide((RatNum) x, (RatNum) this);
    }

    public static IntFraction neg(IntFraction x) {
        return new IntFraction(IntNum.neg(x.numerator()), x.denominator());
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric neg() {
        return neg(this);
    }

    @Override // gnu.math.Complex, gnu.math.Numeric, java.lang.Number
    public long longValue() {
        return toExactInt(4).longValue();
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, java.lang.Number
    public double doubleValue() {
        boolean neg = this.num.isNegative();
        if (this.den.isZero()) {
            if (neg) {
                return Double.NEGATIVE_INFINITY;
            }
            return this.num.isZero() ? Double.NaN : Double.POSITIVE_INFINITY;
        }
        IntNum n = this.num;
        if (neg) {
            n = IntNum.neg(n);
        }
        int num_len = n.intLength();
        int den_len = this.den.intLength();
        int exp = 0;
        if (num_len < den_len + 54) {
            int exp2 = (den_len + 54) - num_len;
            n = IntNum.shift(n, exp2);
            exp = -exp2;
        }
        IntNum quot = new IntNum();
        IntNum remainder = new IntNum();
        IntNum.divide(n, this.den, quot, remainder, 3);
        return quot.canonicalize().roundToDouble(exp, neg, !remainder.canonicalize().isZero());
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public String toString(int radix) {
        return this.num.toString(radix) + '/' + this.den.toString(radix);
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.num);
        out.writeObject(this.den);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.num = (IntNum) in.readObject();
        this.den = (IntNum) in.readObject();
    }
}
