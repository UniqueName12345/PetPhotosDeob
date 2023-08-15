package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class DFloNum extends RealNum implements Externalizable {
    private static final DFloNum one = new DFloNum(1.0d);
    double value;

    public DFloNum() {
    }

    public DFloNum(double value) {
        this.value = value;
    }

    public DFloNum(String s) throws NumberFormatException {
        Double d = Double.valueOf(s);
        this.value = d.doubleValue();
        if (this.value == 0.0d && s.charAt(0) == '-') {
            this.value = -0.0d;
        }
    }

    public static DFloNum make(double value) {
        return new DFloNum(value);
    }

    public static DFloNum asDFloNumOrNull(Object value) {
        if (value instanceof DFloNum) {
            return (DFloNum) value;
        }
        if ((value instanceof RealNum) || (value instanceof Number)) {
            return new DFloNum(((Number) value).doubleValue());
        }
        return null;
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, java.lang.Number
    public final double doubleValue() {
        return this.value;
    }

    @Override // gnu.math.Complex, gnu.math.Numeric, java.lang.Number
    public long longValue() {
        return (long) this.value;
    }

    public int hashCode() {
        return (int) this.value;
    }

    @Override // gnu.math.Complex, gnu.math.Numeric
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof DFloNum) && Double.doubleToLongBits(((DFloNum) obj).value) == Double.doubleToLongBits(this.value);
    }

    @Override // gnu.math.RealNum, gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric add(Object y, int k) {
        if (y instanceof RealNum) {
            return new DFloNum(this.value + (k * ((RealNum) y).doubleValue()));
        }
        if (!(y instanceof Numeric)) {
            throw new IllegalArgumentException();
        }
        return ((Numeric) y).addReversed(this, k);
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric addReversed(Numeric x, int k) {
        if (x instanceof RealNum) {
            return new DFloNum(((RealNum) x).doubleValue() + (k * this.value));
        }
        throw new IllegalArgumentException();
    }

    @Override // gnu.math.RealNum, gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric mul(Object y) {
        if (y instanceof RealNum) {
            return new DFloNum(this.value * ((RealNum) y).doubleValue());
        }
        if (!(y instanceof Numeric)) {
            throw new IllegalArgumentException();
        }
        return ((Numeric) y).mulReversed(this);
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric mulReversed(Numeric x) {
        if (x instanceof RealNum) {
            return new DFloNum(((RealNum) x).doubleValue() * this.value);
        }
        throw new IllegalArgumentException();
    }

    public static final DFloNum one() {
        return one;
    }

    @Override // gnu.math.RealNum, gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric div(Object y) {
        if (y instanceof RealNum) {
            return new DFloNum(this.value / ((RealNum) y).doubleValue());
        }
        if (!(y instanceof Numeric)) {
            throw new IllegalArgumentException();
        }
        return ((Numeric) y).divReversed(this);
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric divReversed(Numeric x) {
        if (x instanceof RealNum) {
            return new DFloNum(((RealNum) x).doubleValue() / this.value);
        }
        throw new IllegalArgumentException();
    }

    @Override // gnu.math.Numeric
    public Numeric power(IntNum y) {
        return new DFloNum(Math.pow(doubleValue(), y.doubleValue()));
    }

    @Override // gnu.math.RealNum
    public boolean isNegative() {
        return this.value < 0.0d;
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public Numeric neg() {
        return new DFloNum(-this.value);
    }

    @Override // gnu.math.RealNum
    public int sign() {
        if (this.value > 0.0d) {
            return 1;
        }
        if (this.value < 0.0d) {
            return -1;
        }
        return this.value == 0.0d ? 0 : -2;
    }

    public static int compare(double x, double y) {
        if (x > y) {
            return 1;
        }
        if (x < y) {
            return -1;
        }
        return x == y ? 0 : -2;
    }

    public static int compare(IntNum x_num, IntNum x_den, double y) {
        long bits;
        if (Double.isNaN(y)) {
            return -2;
        }
        if (Double.isInfinite(y)) {
            int result = y >= 0.0d ? -1 : 1;
            if (x_den.isZero()) {
                if (x_num.isZero()) {
                    return -2;
                }
                int result2 = result >> 1;
                return !x_num.isNegative() ? result2 ^ (-1) : result2;
            }
            return result;
        }
        long bits2 = Double.doubleToLongBits(y);
        boolean neg = bits2 < 0;
        int exp = ((int) (bits2 >> 52)) & 2047;
        long bits3 = bits2 & 4503599627370495L;
        if (exp == 0) {
            bits = bits3 << 1;
        } else {
            bits = bits3 | 4503599627370496L;
        }
        if (neg) {
            bits = -bits;
        }
        IntNum y_num = IntNum.make(bits);
        if (exp >= 1075) {
            y_num = IntNum.shift(y_num, exp - 1075);
        } else {
            x_num = IntNum.shift(x_num, 1075 - exp);
        }
        return IntNum.compare(x_num, IntNum.times(y_num, x_den));
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public int compare(Object obj) {
        if (obj instanceof RatNum) {
            RatNum y_rat = (RatNum) obj;
            int i = compare(y_rat.numerator(), y_rat.denominator(), this.value);
            if (i >= -1) {
                return -i;
            }
            return i;
        }
        return compare(this.value, ((RealNum) obj).doubleValue());
    }

    @Override // gnu.math.Quantity, gnu.math.Numeric
    public int compareReversed(Numeric x) {
        if (x instanceof RatNum) {
            RatNum x_rat = (RatNum) x;
            return compare(x_rat.numerator(), x_rat.denominator(), this.value);
        }
        return compare(((RealNum) x).doubleValue(), this.value);
    }

    @Override // gnu.math.Complex, gnu.math.Numeric
    public boolean isExact() {
        return false;
    }

    @Override // gnu.math.RealNum, gnu.math.Complex, gnu.math.Numeric
    public boolean isZero() {
        return this.value == 0.0d;
    }

    public static RatNum toExact(double value) {
        long bits;
        if (Double.isInfinite(value)) {
            return RatNum.infinity(value < 0.0d ? -1 : 1);
        } else if (Double.isNaN(value)) {
            throw new ArithmeticException("cannot convert NaN to exact rational");
        } else {
            long bits2 = Double.doubleToLongBits(value);
            boolean neg = bits2 < 0;
            int exp = ((int) (bits2 >> 52)) & 2047;
            long bits3 = bits2 & 4503599627370495L;
            if (exp == 0) {
                bits = bits3 << 1;
            } else {
                bits = bits3 | 4503599627370496L;
            }
            if (neg) {
                bits = -bits;
            }
            IntNum mant = IntNum.make(bits);
            if (exp >= 1075) {
                return IntNum.shift(mant, exp - 1075);
            }
            return RatNum.make(mant, IntNum.shift(IntNum.one(), 1075 - exp));
        }
    }

    @Override // gnu.math.Numeric
    public String toString() {
        return this.value == Double.POSITIVE_INFINITY ? "+inf.0" : this.value == Double.NEGATIVE_INFINITY ? "-inf.0" : Double.isNaN(this.value) ? "+nan.0" : Double.toString(this.value);
    }

    @Override // gnu.math.Complex, gnu.math.Quantity, gnu.math.Numeric
    public String toString(int radix) {
        return radix == 10 ? toString() : "#d" + toString();
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(this.value);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.value = in.readDouble();
    }
}
