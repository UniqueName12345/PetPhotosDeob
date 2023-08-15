package gnu.kawa.functions;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.math.IntNum;

/* loaded from: classes.dex */
public class NumberCompare extends ProcedureN implements Inlineable {
    static final int RESULT_EQU = 0;
    static final int RESULT_GRT = 1;
    static final int RESULT_LSS = -1;
    static final int RESULT_NAN = -2;
    static final int RESULT_NEQ = -3;
    public static final int TRUE_IF_EQU = 8;
    public static final int TRUE_IF_GRT = 16;
    public static final int TRUE_IF_LSS = 4;
    public static final int TRUE_IF_NAN = 2;
    public static final int TRUE_IF_NEQ = 1;
    int flags;
    Language language;

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return -4094;
    }

    public static boolean $Eq(Object arg1, Object arg2) {
        return apply2(8, arg1, arg2);
    }

    public static boolean $Gr(Object arg1, Object arg2) {
        return apply2(16, arg1, arg2);
    }

    public static boolean $Gr$Eq(Object arg1, Object arg2) {
        return apply2(24, arg1, arg2);
    }

    public static boolean $Ls(Object arg1, Object arg2) {
        return apply2(4, arg1, arg2);
    }

    public static boolean $Ls$Eq(Object arg1, Object arg2) {
        return apply2(12, arg1, arg2);
    }

    public static boolean $Eq$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if ($Eq(arg1, arg2) && $Eq(arg2, arg3)) {
            return rest.length == 0 || ($Eq(arg3, rest[0]) && applyN(8, rest));
        }
        return false;
    }

    public static boolean $Gr$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if ($Gr(arg1, arg2) && $Gr(arg2, arg3)) {
            return rest.length == 0 || ($Gr(arg3, rest[0]) && applyN(16, rest));
        }
        return false;
    }

    public static boolean $Gr$Eq$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if ($Gr$Eq(arg1, arg2) && $Gr$Eq(arg2, arg3)) {
            return rest.length == 0 || ($Gr$Eq(arg3, rest[0]) && applyN(24, rest));
        }
        return false;
    }

    public static boolean $Ls$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if ($Ls(arg1, arg2) && $Ls(arg2, arg3)) {
            return rest.length == 0 || ($Ls(arg3, rest[0]) && applyN(4, rest));
        }
        return false;
    }

    public static boolean $Ls$Eq$V(Object arg1, Object arg2, Object arg3, Object[] rest) {
        if ($Ls$Eq(arg1, arg2) && $Ls$Eq(arg2, arg3)) {
            return rest.length == 0 || ($Ls$Eq(arg3, rest[0]) && applyN(12, rest));
        }
        return false;
    }

    public static NumberCompare make(Language language, String name, int flags) {
        NumberCompare proc = new NumberCompare();
        proc.language = language;
        proc.setName(name);
        proc.flags = flags;
        proc.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileArith:validateApplyNumberCompare");
        return proc;
    }

    protected final Language getLanguage() {
        return this.language;
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object apply2(Object arg1, Object arg2) {
        return getLanguage().booleanObject(apply2(this.flags, arg1, arg2));
    }

    public static boolean apply2(int flags, Object arg1, Object arg2) {
        return ((1 << (compare(arg1, arg2, true) + 3)) & flags) != 0;
    }

    public static boolean checkCompareCode(int code, int flags) {
        return ((1 << (code + 3)) & flags) != 0;
    }

    public static boolean applyWithPromotion(int flags, Object arg1, Object arg2) {
        return checkCompareCode(compare(arg1, arg2, false), flags);
    }

    public static int compare(Object arg1, Object arg2, boolean exact) {
        int code1 = Arithmetic.classifyValue(arg1);
        int code2 = Arithmetic.classifyValue(arg2);
        return compare(arg1, code1, arg2, code2, exact);
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x00ca A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int compare(java.lang.Object r24, int r25, java.lang.Object r26, int r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.NumberCompare.compare(java.lang.Object, int, java.lang.Object, int, boolean):int");
    }

    static boolean applyN(int flags, Object[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            Object arg1 = args[i];
            Object arg2 = args[i + 1];
            if (!apply2(flags, arg1, arg2)) {
                return false;
            }
        }
        return true;
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) {
        return getLanguage().booleanObject(applyN(this.flags, args));
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Type commonType;
        int opcode;
        Expression[] args = exp.getArgs();
        if (args.length == 2) {
            Expression arg0 = args[0];
            Expression arg1 = args[1];
            int kind0 = classify(arg0);
            int kind1 = classify(arg1);
            CodeAttr code = comp.getCode();
            if (kind0 > 0 && kind1 > 0 && kind0 <= 10 && kind1 <= 10 && (kind0 != 6 || kind1 != 6)) {
                if (!(target instanceof ConditionalTarget)) {
                    IfExp.compile(exp, QuoteExp.trueExp, QuoteExp.falseExp, comp, target);
                    return;
                }
                int mask = this.flags;
                if (mask == 1) {
                    mask = 20;
                }
                if (kind0 <= 4 && kind1 <= 4 && (kind0 > 2 || kind1 > 2)) {
                    Type[] ctypes = new Type[2];
                    ctypes[0] = Arithmetic.typeIntNum;
                    if (kind1 <= 2) {
                        ctypes[1] = Type.longType;
                    } else if (kind0 <= 2 && ((arg0 instanceof QuoteExp) || (arg1 instanceof QuoteExp) || (arg0 instanceof ReferenceExp) || (arg1 instanceof ReferenceExp))) {
                        ctypes[1] = Type.longType;
                        args = new Expression[]{arg1, arg0};
                        if (mask != 8 && mask != 20) {
                            mask ^= 20;
                        }
                    } else {
                        ctypes[1] = Arithmetic.typeIntNum;
                    }
                    Method cmeth = Arithmetic.typeIntNum.getMethod("compare", ctypes);
                    PrimProcedure compare = new PrimProcedure(cmeth);
                    arg0 = new ApplyExp(compare, args);
                    arg1 = new QuoteExp(IntNum.zero());
                    kind1 = 1;
                    kind0 = 1;
                }
                if (kind0 <= 1 && kind1 <= 1) {
                    commonType = Type.intType;
                } else if (kind0 <= 2 && kind1 <= 2) {
                    commonType = Type.longType;
                } else {
                    commonType = Type.doubleType;
                }
                StackTarget subTarget = new StackTarget(commonType);
                ConditionalTarget ctarget = (ConditionalTarget) target;
                if ((arg0 instanceof QuoteExp) && !(arg1 instanceof QuoteExp)) {
                    Expression tmp = arg1;
                    arg1 = arg0;
                    arg0 = tmp;
                    if (mask != 8 && mask != 20) {
                        mask ^= 20;
                    }
                }
                Label label1 = ctarget.trueBranchComesFirst ? ctarget.ifFalse : ctarget.ifTrue;
                if (ctarget.trueBranchComesFirst) {
                    mask ^= 28;
                }
                switch (mask) {
                    case 4:
                        opcode = 155;
                        break;
                    case 8:
                        opcode = 153;
                        break;
                    case 12:
                        opcode = 158;
                        break;
                    case 16:
                        opcode = 157;
                        break;
                    case 20:
                        opcode = 154;
                        break;
                    case 24:
                        opcode = 156;
                        break;
                    default:
                        opcode = 0;
                        break;
                }
                arg0.compile(comp, subTarget);
                if (kind0 <= 1 && kind1 <= 1 && (arg1 instanceof QuoteExp)) {
                    Object value = ((QuoteExp) arg1).getValue();
                    if ((value instanceof IntNum) && ((IntNum) value).isZero()) {
                        code.emitGotoIfCompare1(label1, opcode);
                        ctarget.emitGotoFirstBranch(code);
                        return;
                    }
                }
                arg1.compile(comp, subTarget);
                code.emitGotoIfCompare2(label1, opcode);
                ctarget.emitGotoFirstBranch(code);
                return;
            }
        }
        ApplyExp.compile(exp, comp, target);
    }

    static int classify(Expression exp) {
        Type type = exp.getType();
        int kind = Arithmetic.classifyType(type);
        if (kind == 4 && (exp instanceof QuoteExp)) {
            Object value = ((QuoteExp) exp).getValue();
            if (value instanceof IntNum) {
                int ilength = ((IntNum) value).intLength();
                if (ilength < 32) {
                    return 1;
                }
                if (ilength < 64) {
                    return 2;
                }
                return kind;
            }
            return kind;
        }
        return kind;
    }

    @Override // gnu.mapping.Procedure
    public Type getReturnType(Expression[] args) {
        return Type.booleanType;
    }
}
