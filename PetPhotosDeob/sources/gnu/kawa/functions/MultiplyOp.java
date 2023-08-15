package gnu.kawa.functions;

import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.RatNum;
import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes.dex */
public class MultiplyOp extends ArithOp {
    public static final MultiplyOp $St = new MultiplyOp("*");

    public MultiplyOp(String name) {
        super(name, 3);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyArithOp");
        Procedure.compilerKey.set((PropertySet) this, "*gnu.kawa.functions.CompileArith:forMul");
    }

    @Override // gnu.kawa.functions.ArithOp
    public Object defaultResult() {
        return IntNum.one();
    }

    public static Object apply(Object arg1, Object arg2) {
        return ((Numeric) arg1).mul(arg2);
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) {
        int len = args.length;
        if (len == 0) {
            return IntNum.one();
        }
        Number result = (Number) args[0];
        int code = Arithmetic.classifyValue(result);
        for (int i = 1; i < len; i++) {
            Object arg2 = args[i];
            int code2 = Arithmetic.classifyValue(arg2);
            if (code < code2) {
                code = code2;
            }
            switch (code) {
                case 1:
                    int i1 = Arithmetic.asInt(result);
                    int i2 = Arithmetic.asInt(arg2);
                    result = new Integer(i1 * i2);
                    break;
                case 2:
                    long l1 = Arithmetic.asLong(result);
                    long l2 = Arithmetic.asLong(arg2);
                    result = new Long(l1 * l2);
                    break;
                case 3:
                    BigInteger bi1 = Arithmetic.asBigInteger(result);
                    BigInteger bi2 = Arithmetic.asBigInteger(arg2);
                    result = bi1.multiply(bi2);
                    break;
                case 4:
                    result = IntNum.times(Arithmetic.asIntNum(result), Arithmetic.asIntNum(arg2));
                    break;
                case 5:
                    BigDecimal bd1 = Arithmetic.asBigDecimal(result);
                    BigDecimal bd2 = Arithmetic.asBigDecimal(arg2);
                    result = bd1.multiply(bd2);
                    break;
                case 6:
                    result = RatNum.times(Arithmetic.asRatNum(result), Arithmetic.asRatNum(arg2));
                    break;
                case 7:
                    float f1 = Arithmetic.asFloat(result);
                    float f2 = Arithmetic.asFloat(arg2);
                    result = new Float(f1 * f2);
                    break;
                case 8:
                    double d1 = Arithmetic.asDouble(result);
                    double d2 = Arithmetic.asDouble(arg2);
                    result = new Double(d1 * d2);
                    break;
                case 9:
                    double d12 = Arithmetic.asDouble(result);
                    double d22 = Arithmetic.asDouble(arg2);
                    result = new DFloNum(d12 * d22);
                    break;
                default:
                    result = Arithmetic.asNumeric(result).mul(Arithmetic.asNumeric(arg2));
                    break;
            }
        }
        return result;
    }
}
