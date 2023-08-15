package gnu.xquery.util;

import gnu.bytecode.Access;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Arithmetic;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XDataType;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1or2;
import gnu.mapping.Values;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.xml.TextUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/* loaded from: classes.dex */
public class ArithOp extends Procedure1or2 implements Inlineable {
    char op;
    static final BigInteger TEN = BigInteger.valueOf(10);
    public static final ArithOp add = new ArithOp("+", '+', 2);
    public static final ArithOp sub = new ArithOp("-", '-', 2);
    public static final ArithOp mul = new ArithOp("*", '*', 2);
    public static final ArithOp div = new ArithOp("div", 'd', 2);
    public static final ArithOp idiv = new ArithOp("idiv", 'i', 2);
    public static final ArithOp mod = new ArithOp("mod", 'm', 2);
    public static final ArithOp plus = new ArithOp("+", 'P', 1);
    public static final ArithOp minus = new ArithOp("-", Access.METHOD_CONTEXT, 1);

    ArithOp(String name, char op, int nargs) {
        super(name);
        setProperty(Procedure.validateApplyKey, "gnu.xquery.util.CompileMisc:validateArithOp");
        this.op = op;
    }

    @Override // gnu.mapping.Procedure1or2, gnu.mapping.Procedure
    public Object apply1(Object arg1) throws Throwable {
        if (arg1 == Values.empty || arg1 == null) {
            return arg1;
        }
        if ((arg1 instanceof KNode) || (arg1 instanceof UntypedAtomic)) {
            arg1 = XDataType.doubleType.valueOf(TextUtils.stringValue(arg1));
        }
        switch (this.op) {
            case 'M':
                int code1 = Arithmetic.classifyValue(arg1);
                switch (code1) {
                    case 7:
                        return XDataType.makeFloat(-Arithmetic.asFloat(arg1));
                    case 8:
                        return XDataType.makeDouble(-Arithmetic.asDouble(arg1));
                    default:
                        if (arg1 instanceof Numeric) {
                            return ((Numeric) arg1).neg();
                        }
                        return AddOp.apply2(-1, IntNum.zero(), arg1);
                }
            case 'N':
            case 'O':
            default:
                throw new UnsupportedOperationException(getName());
            case 'P':
                return AddOp.apply2(1, IntNum.zero(), arg1);
        }
    }

    public static BigDecimal div(BigDecimal d1, BigDecimal d2) {
        return d1.divide(d2, MathContext.DECIMAL128);
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01cc  */
    @Override // gnu.mapping.Procedure1or2, gnu.mapping.Procedure
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object apply2(java.lang.Object r37, java.lang.Object r38) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 722
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.ArithOp.apply2(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ApplyExp.compile(exp, comp, target);
    }

    @Override // gnu.mapping.Procedure
    public Type getReturnType(Expression[] args) {
        return Type.pointer_type;
    }
}
