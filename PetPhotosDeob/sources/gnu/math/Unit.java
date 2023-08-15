package gnu.math;

/* loaded from: classes.dex */
public abstract class Unit extends Quantity {
    public static double NON_COMBINABLE;
    public static final Unit cm;
    public static final NamedUnit date;
    public static final BaseUnit duration;
    public static final BaseUnit gram;
    public static final Unit hour;
    public static final Unit in;
    public static final BaseUnit meter;
    public static final Unit minute;
    public static final Unit mm;
    public static final NamedUnit month;
    public static final Unit pica;
    public static final Unit pt;
    public static final Unit radian;
    public static final NamedUnit second;
    Unit base;
    Dimensions dims;
    double factor = 1.0d;
    MulUnit products;
    static NamedUnit[] table = new NamedUnit[100];
    public static BaseUnit Empty = new BaseUnit();

    static {
        Dimensions.Empty.bases[0] = Empty;
        NON_COMBINABLE = 0.0d;
        meter = new BaseUnit("m", "Length");
        duration = new BaseUnit("duration", "Time");
        gram = new BaseUnit("g", "Mass");
        cm = define("cm", 0.01d, meter);
        mm = define("mm", 0.1d, cm);
        in = define("in", 0.0254d, meter);
        pt = define("pt", 3.527778E-4d, meter);
        pica = define("pica", 0.004233333d, meter);
        radian = define("rad", 1.0d, Empty);
        date = new NamedUnit("date", NON_COMBINABLE, duration);
        second = new NamedUnit("s", NON_COMBINABLE, duration);
        month = new NamedUnit("month", NON_COMBINABLE, duration);
        minute = define("min", 60.0d, second);
        hour = define("hour", 60.0d, minute);
    }

    @Override // gnu.math.Quantity
    public final Dimensions dimensions() {
        return this.dims;
    }

    @Override // gnu.math.Quantity, java.lang.Number
    public final double doubleValue() {
        return this.factor;
    }

    public int hashCode() {
        return this.dims.hashCode();
    }

    public String getName() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Unit times(Unit unit1, int power1, Unit unit2, int power2) {
        if (unit1 == unit2) {
            power1 += power2;
            unit2 = Empty;
            power2 = 0;
        }
        if (power1 == 0 || unit1 == Empty) {
            unit1 = unit2;
            power1 = power2;
            unit2 = Empty;
            power2 = 0;
        }
        if (power2 == 0 || unit2 == Empty) {
            if (power1 != 1) {
                if (power1 == 0) {
                    return Empty;
                }
            } else {
                return unit1;
            }
        }
        if (unit1 instanceof MulUnit) {
            MulUnit munit1 = (MulUnit) unit1;
            if (munit1.unit1 == unit2) {
                return times(unit2, (munit1.power1 * power1) + power2, munit1.unit2, munit1.power2 * power1);
            }
            if (munit1.unit2 == unit2) {
                return times(munit1.unit1, munit1.power1 * power1, unit2, (munit1.power2 * power1) + power2);
            }
            if (unit2 instanceof MulUnit) {
                MulUnit munit2 = (MulUnit) unit2;
                if (munit1.unit1 == munit2.unit1 && munit1.unit2 == munit2.unit2) {
                    return times(munit1.unit1, (munit1.power1 * power1) + (munit2.power1 * power2), munit1.unit2, (munit1.power2 * power1) + (munit2.power2 * power2));
                }
                if (munit1.unit1 == munit2.unit2 && munit1.unit2 == munit2.unit1) {
                    return times(munit1.unit1, (munit1.power1 * power1) + (munit2.power2 * power2), munit1.unit2, (munit1.power2 * power1) + (munit2.power1 * power2));
                }
            }
        }
        if (unit2 instanceof MulUnit) {
            MulUnit munit22 = (MulUnit) unit2;
            if (munit22.unit1 == unit1) {
                return times(unit1, (munit22.power1 * power2) + power1, munit22.unit2, munit22.power2 * power2);
            }
            if (munit22.unit2 == unit1) {
                return times(munit22.unit1, munit22.power1 * power2, unit1, (munit22.power2 * power2) + power1);
            }
        }
        return MulUnit.make(unit1, power1, unit2, power2);
    }

    public static Unit times(Unit unit1, Unit unit2) {
        return times(unit1, 1, unit2, 1);
    }

    public static Unit divide(Unit unit1, Unit unit2) {
        return times(unit1, 1, unit2, -1);
    }

    public static Unit pow(Unit unit, int power) {
        return times(unit, power, Empty, 0);
    }

    public static NamedUnit make(String name, Quantity value) {
        return NamedUnit.make(name, value);
    }

    public static Unit define(String name, DQuantity value) {
        return new NamedUnit(name, value);
    }

    public static Unit define(String name, double factor, Unit base) {
        return new NamedUnit(name, factor, base);
    }

    @Override // gnu.math.Quantity
    public Complex number() {
        return DFloNum.one();
    }

    @Override // gnu.math.Numeric
    public boolean isExact() {
        return false;
    }

    @Override // gnu.math.Numeric
    public final boolean isZero() {
        return false;
    }

    @Override // gnu.math.Numeric
    public Numeric power(IntNum y) {
        if (y.words != null) {
            throw new ArithmeticException("Unit raised to bignum power");
        }
        return pow(this, y.ival);
    }

    public Unit sqrt() {
        if (this == Empty) {
            return this;
        }
        throw new RuntimeException("unimplemented Unit.sqrt");
    }

    public static NamedUnit lookup(String name) {
        return NamedUnit.lookup(name);
    }

    public String toString(double val) {
        String str = Double.toString(val);
        return this == Empty ? str : str + toString();
    }

    public String toString(RealNum val) {
        return toString(val.doubleValue());
    }

    @Override // gnu.math.Numeric
    public String toString() {
        String name = getName();
        if (name == null) {
            if (this == Empty) {
                return "unit";
            }
            return Double.toString(this.factor) + "<unnamed unit>";
        }
        return name;
    }

    @Override // gnu.math.Quantity
    public Unit unit() {
        return this;
    }
}
