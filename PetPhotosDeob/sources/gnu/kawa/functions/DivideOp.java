package gnu.kawa.functions;

import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import gnu.math.RealNum;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/* loaded from: classes.dex */
public class DivideOp extends ArithOp {
    int rounding_mode;
    public static final DivideOp $Sl = new DivideOp("/", 4);
    public static final DivideOp idiv = new DivideOp("idiv", 7);
    public static final DivideOp quotient = new DivideOp("quotient", 6);
    public static final DivideOp remainder = new DivideOp("remainder", 8);
    public static final DivideOp modulo = new DivideOp("modulo", 8);
    public static final DivideOp div = new DivideOp("div", 6);
    public static final DivideOp mod = new DivideOp("mod", 8);
    public static final DivideOp div0 = new DivideOp("div0", 6);
    public static final DivideOp mod0 = new DivideOp("mod0", 8);

    public int getRoundingMode() {
        return this.rounding_mode;
    }

    static {
        idiv.rounding_mode = 3;
        quotient.rounding_mode = 3;
        remainder.rounding_mode = 3;
        modulo.rounding_mode = 1;
        div.rounding_mode = 5;
        mod.rounding_mode = 5;
        div0.rounding_mode = 4;
        mod0.rounding_mode = 4;
    }

    public DivideOp(String name, int op) {
        super(name, op);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
        Procedure.compilerKey.set((PropertySet) this, "*gnu.kawa.functions.CompileArith:forDiv");
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) throws Throwable {
        RoundingMode mround;
        long l1;
        int i1;
        int len = args.length;
        if (len == 0) {
            return IntNum.one();
        }
        Number result = (Number) args[0];
        if (len == 1) {
            return apply2(IntNum.one(), result);
        }
        int code = Arithmetic.classifyValue(result);
        for (int i = 1; i < len; i++) {
            Object arg2 = args[i];
            int code2 = Arithmetic.classifyValue(arg2);
            if (code < code2) {
                code = code2;
            }
            int scode = code;
            if (code < 4) {
                switch (this.op) {
                    case 4:
                    case 5:
                        code = 4;
                        scode = 4;
                        break;
                    default:
                        if (this.rounding_mode != 3 || (code != 1 && code != 2)) {
                            scode = 4;
                            break;
                        }
                        break;
                }
            }
            if (this.op == 5 && code <= 10) {
                scode = 10;
                if (code != 8 && code != 7) {
                    code = 9;
                }
            } else if (scode == 8 || scode == 7) {
                scode = 9;
                if (this.op == 7) {
                    code = 9;
                }
            }
            switch (scode) {
                case 1:
                    int i12 = Arithmetic.asInt(result);
                    int i2 = Arithmetic.asInt(arg2);
                    switch (this.op) {
                        case 8:
                            i1 = i12 % i2;
                            break;
                        default:
                            i1 = i12 / i2;
                            break;
                    }
                    result = Integer.valueOf(i1);
                    break;
                case 2:
                    long l12 = Arithmetic.asLong(result);
                    long l2 = Arithmetic.asLong(arg2);
                    switch (this.op) {
                        case 8:
                            l1 = l12 % l2;
                            break;
                        default:
                            l1 = l12 / l2;
                            break;
                    }
                    result = Long.valueOf(l1);
                    break;
                case 3:
                case 6:
                case 7:
                case 8:
                default:
                    Numeric num1 = Arithmetic.asNumeric(result);
                    Numeric num2 = Arithmetic.asNumeric(arg2);
                    if (this.op != 8 || !num2.isZero()) {
                        Numeric numr = num1.div(num2);
                        if (this.op == 8) {
                            numr = num1.sub(((RealNum) numr).toInt(getRoundingMode()).mul(num2));
                        }
                        switch (this.op) {
                            case 5:
                                result = numr.toInexact();
                                break;
                            case 6:
                                result = ((RealNum) numr).toInt(this.rounding_mode);
                                break;
                            case 7:
                                result = ((RealNum) numr).toExactInt(this.rounding_mode);
                                code = 4;
                                scode = 4;
                                break;
                            default:
                                result = numr;
                                break;
                        }
                    } else {
                        return !num2.isExact() ? num1.toInexact() : num1;
                    }
                case 4:
                    switch (this.op) {
                        case 4:
                            result = RatNum.make(Arithmetic.asIntNum(result), Arithmetic.asIntNum(arg2));
                            code = result instanceof IntNum ? 4 : 6;
                            scode = code;
                            break;
                        case 6:
                        case 7:
                            result = IntNum.quotient(Arithmetic.asIntNum(result), Arithmetic.asIntNum(arg2), getRoundingMode());
                            break;
                        case 8:
                            result = IntNum.remainder(Arithmetic.asIntNum(result), Arithmetic.asIntNum(arg2), getRoundingMode());
                            break;
                    }
                case 5:
                    BigDecimal bd1 = Arithmetic.asBigDecimal(result);
                    BigDecimal bd2 = Arithmetic.asBigDecimal(arg2);
                    switch (getRoundingMode()) {
                        case 1:
                            mround = RoundingMode.FLOOR;
                            break;
                        case 2:
                            mround = RoundingMode.CEILING;
                            break;
                        case 3:
                            mround = RoundingMode.DOWN;
                            break;
                        case 5:
                            if (bd2.signum() < 0) {
                                RoundingMode roundingMode = RoundingMode.CEILING;
                            } else {
                                RoundingMode mround2 = RoundingMode.FLOOR;
                            }
                        case 4:
                        default:
                            mround = RoundingMode.HALF_EVEN;
                            break;
                    }
                    MathContext mcontext = new MathContext(0, mround);
                    switch (this.op) {
                        case 4:
                            result = bd1.divide(bd2);
                            break;
                        case 6:
                            result = bd1.divideToIntegralValue(bd2, mcontext);
                            break;
                        case 7:
                            result = bd1.divideToIntegralValue(bd2, mcontext).toBigInteger();
                            scode = 3;
                            code = 3;
                            break;
                        case 8:
                            result = bd1.remainder(bd2, mcontext);
                            break;
                    }
                case 9:
                    double d1 = Arithmetic.asDouble(result);
                    double d2 = Arithmetic.asDouble(arg2);
                    switch (this.op) {
                        case 4:
                        case 5:
                            result = DFloNum.make(d1 / d2);
                            break;
                        case 6:
                            result = Double.valueOf(RealNum.toInt(d1 / d2, getRoundingMode()));
                            break;
                        case 7:
                            result = RealNum.toExactInt(d1 / d2, getRoundingMode());
                            scode = 4;
                            code = 4;
                            break;
                        case 8:
                            if (d2 != 0.0d) {
                                d1 -= RealNum.toInt(d1 / d2, getRoundingMode()) * d2;
                            }
                            result = DFloNum.make(d1);
                            break;
                    }
            }
            if (code != scode) {
                switch (code) {
                    case 1:
                        result = Integer.valueOf(result.intValue());
                        continue;
                    case 2:
                        result = Long.valueOf(result.longValue());
                        continue;
                    case 3:
                        result = Arithmetic.asBigInteger(result);
                        continue;
                    case 7:
                        result = Float.valueOf(result.floatValue());
                        continue;
                    case 8:
                        result = Double.valueOf(result.doubleValue());
                        continue;
                }
            }
        }
        return result;
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return this.op == 4 ? -4095 : 8194;
    }
}
