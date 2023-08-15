package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.mapping.Procedure;
import gnu.math.IntNum;

/* loaded from: classes.dex */
public class CompileArith implements Inlineable {
    int op;
    Procedure proc;
    public static CompileArith $Pl = new CompileArith(AddOp.$Pl, 1);
    public static CompileArith $Mn = new CompileArith(AddOp.$Mn, 2);

    CompileArith(Object proc, int op) {
        this.proc = (Procedure) proc;
        this.op = op;
    }

    public static CompileArith forMul(Object proc) {
        return new CompileArith(proc, 3);
    }

    public static CompileArith forDiv(Object proc) {
        return new CompileArith(proc, ((DivideOp) proc).op);
    }

    public static CompileArith forBitwise(Object proc) {
        return new CompileArith(proc, ((BitwiseOp) proc).op);
    }

    public static boolean appropriateIntConstant(Expression[] args, int iarg, InlineCalls visitor) {
        Expression exp = visitor.fixIntValue(args[iarg]);
        if (exp != null) {
            args[iarg] = exp;
            return true;
        }
        return false;
    }

    public static boolean appropriateLongConstant(Expression[] args, int iarg, InlineCalls visitor) {
        Expression exp = visitor.fixLongValue(args[iarg]);
        if (exp != null) {
            args[iarg] = exp;
            return true;
        }
        return false;
    }

    public static Expression validateApplyArithOp(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        int rkind;
        ArithOp aproc = (ArithOp) proc;
        int op = aproc.op;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length > 2) {
            return pairwise(proc, exp.getFunction(), args, visitor);
        }
        Expression folded = exp.inlineIfConstant(proc, visitor);
        if (folded != exp) {
            return folded;
        }
        int rkind2 = 0;
        if (args.length == 2 || args.length == 1) {
            int kind1 = Arithmetic.classifyType(args[0].getType());
            if (args.length == 2 && (op < 9 || op > 12)) {
                int kind2 = Arithmetic.classifyType(args[1].getType());
                rkind = getReturnKind(kind1, kind2, op);
                if (rkind == 4) {
                    if (kind1 == 1 && appropriateIntConstant(args, 1, visitor)) {
                        rkind = 1;
                    } else if (kind2 == 1 && appropriateIntConstant(args, 0, visitor)) {
                        rkind = 1;
                    } else if (kind1 == 2 && appropriateLongConstant(args, 1, visitor)) {
                        rkind = 2;
                    } else if (kind2 == 2 && appropriateLongConstant(args, 0, visitor)) {
                        rkind = 2;
                    }
                }
            } else {
                rkind = kind1;
            }
            rkind2 = adjustReturnKind(rkind, op);
            exp.setType(Arithmetic.kindType(rkind2));
        }
        if (visitor.getCompilation().mustCompile) {
            switch (op) {
                case 1:
                case 2:
                    return validateApplyAdd((AddOp) proc, exp, visitor);
                case 3:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                default:
                    return exp;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    return validateApplyDiv((DivideOp) proc, exp, visitor);
                case 16:
                    if (rkind2 > 0) {
                        return validateApplyNot(exp, rkind2, visitor);
                    }
                    return exp;
            }
        }
        return exp;
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Type wtype;
        Method meth;
        Expression[] args = exp.getArgs();
        int len = args.length;
        if (len == 0) {
            comp.compileConstant(((ArithOp) this.proc).defaultResult(), target);
        } else if (len == 1 || (target instanceof IgnoreTarget)) {
            ApplyExp.compile(exp, comp, target);
        } else {
            int kind1 = Arithmetic.classifyType(args[0].getType());
            int kind2 = Arithmetic.classifyType(args[1].getType());
            int kind = getReturnKind(kind1, kind2, this.op);
            Type type = Arithmetic.kindType(kind);
            if (kind == 0 || len != 2) {
                ApplyExp.compile(exp, comp, target);
                return;
            }
            Type targetType = target.getType();
            int tkind = Arithmetic.classifyType(targetType);
            if ((tkind == 1 || tkind == 2) && kind >= 1 && kind <= 4) {
                kind = tkind;
                wtype = tkind == 1 ? LangPrimType.intType : LangPrimType.longType;
            } else if ((tkind == 8 || tkind == 7) && kind > 2 && kind <= 10) {
                kind = tkind;
                wtype = tkind == 7 ? LangPrimType.floatType : LangPrimType.doubleType;
            } else if (kind == 7) {
                wtype = LangPrimType.floatType;
            } else if (kind == 8 || kind == 9) {
                kind = 8;
                wtype = LangPrimType.doubleType;
            } else {
                wtype = type;
            }
            if (this.op >= 4 && this.op <= 8) {
                DivideOp dproc = (DivideOp) this.proc;
                if (dproc.op != 4 || (kind > 4 && kind < 6 && kind > 9)) {
                    if ((dproc.op == 5 && kind <= 10 && kind != 7) || (dproc.op == 4 && kind == 10)) {
                        kind = 8;
                    } else if (((dproc.op != 7 && (dproc.op != 6 || kind > 4)) || (dproc.getRoundingMode() != 3 && kind != 4 && kind != 7 && kind != 8)) && (dproc.op != 8 || (dproc.getRoundingMode() != 3 && kind != 4))) {
                        ApplyExp.compile(exp, comp, target);
                        return;
                    }
                }
            }
            if (this.op == 4 && kind <= 10 && kind != 8 && kind != 7) {
                if (kind == 6 || kind > 4) {
                    LangObjType ctype = kind == 6 ? Arithmetic.typeRatNum : Arithmetic.typeRealNum;
                    wtype = ctype;
                    meth = ctype.getDeclaredMethod("divide", 2);
                } else {
                    wtype = Arithmetic.typeIntNum;
                    meth = Arithmetic.typeRatNum.getDeclaredMethod("make", 2);
                }
                Target wtarget = StackTarget.getInstance(wtype);
                args[0].compile(comp, wtarget);
                args[1].compile(comp, wtarget);
                comp.getCode().emitInvokeStatic(meth);
            } else if (kind == 4 && (this.op == 1 || this.op == 3 || this.op == 2 || this.op == 13 || this.op == 14 || this.op == 15 || this.op == 7 || this.op == 8 || (this.op >= 9 && this.op <= 11))) {
                compileIntNum(args[0], args[1], kind1, kind2, comp);
            } else if (kind == 1 || kind == 2 || ((kind == 7 || kind == 8) && (this.op <= 8 || this.op >= 13))) {
                Target wtarget2 = StackTarget.getInstance(wtype);
                CodeAttr code = comp.getCode();
                for (int i = 0; i < len; i++) {
                    if (i == 1 && this.op >= 9 && this.op <= 12) {
                        wtarget2 = StackTarget.getInstance(Type.intType);
                    }
                    args[i].compile(comp, wtarget2);
                    if (i != 0) {
                        switch (kind) {
                            case 1:
                            case 2:
                            case 7:
                            case 8:
                                if (this.op == 9) {
                                    Type[] margs = {wtype, Type.intType};
                                    Method method = ClassType.make("gnu.math.IntNum").getDeclaredMethod("shift", margs);
                                    code.emitInvokeStatic(method);
                                    continue;
                                } else {
                                    code.emitBinop(primitiveOpcode(), (PrimType) wtype.getImplementationType());
                                    break;
                                }
                        }
                    }
                }
            } else {
                ApplyExp.compile(exp, comp, target);
                return;
            }
            target.compileFromStack(comp, wtype);
        }
    }

    public boolean compileIntNum(Expression arg1, Expression arg2, int kind1, int kind2, Compilation comp) {
        Type type2;
        Type type1;
        boolean swap;
        boolean negateOk;
        long lval;
        if (this.op == 2 && (arg2 instanceof QuoteExp)) {
            Object val = arg2.valueIfConstant();
            if (kind2 <= 2) {
                lval = ((Number) val).longValue();
                negateOk = lval > -2147483648L && lval <= 2147483647L;
            } else if (val instanceof IntNum) {
                IntNum ival = (IntNum) val;
                lval = ival.longValue();
                negateOk = ival.inRange(-2147483647L, 2147483647L);
            } else {
                negateOk = false;
                lval = 0;
            }
            if (negateOk) {
                return $Pl.compileIntNum(arg1, QuoteExp.getInstance(Integer.valueOf((int) (-lval))), kind1, 1, comp);
            }
        }
        boolean addOrMul = this.op == 1 || this.op == 3;
        if (addOrMul) {
            if (InlineCalls.checkIntValue(arg1) != null) {
                kind1 = 1;
            }
            if (InlineCalls.checkIntValue(arg2) != null) {
                kind2 = 1;
            }
            swap = kind1 == 1 && kind2 != 1;
            if (swap && (!arg1.side_effects() || !arg2.side_effects())) {
                return compileIntNum(arg2, arg1, kind2, kind1, comp);
            }
            type1 = kind1 == 1 ? Type.intType : Arithmetic.typeIntNum;
            type2 = kind2 == 1 ? Type.intType : Arithmetic.typeIntNum;
        } else if (this.op >= 9 && this.op <= 12) {
            type1 = Arithmetic.typeIntNum;
            type2 = Type.intType;
            swap = false;
        } else {
            type2 = Arithmetic.typeIntNum;
            type1 = type2;
            swap = false;
        }
        arg1.compile(comp, type1);
        arg2.compile(comp, type2);
        CodeAttr code = comp.getCode();
        if (swap) {
            code.emitSwap();
            type1 = Arithmetic.typeIntNum;
            type2 = LangPrimType.intType;
        }
        String mname = null;
        Type[] argTypes = null;
        ObjectType mclass = Arithmetic.typeIntNum;
        switch (this.op) {
            case 1:
                mname = "add";
                break;
            case 2:
                mname = "sub";
                break;
            case 3:
                mname = "times";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                mname = this.op == 8 ? "remainder" : "quotient";
                DivideOp dproc = (DivideOp) this.proc;
                if (this.op == 8 && dproc.rounding_mode == 1) {
                    mname = "modulo";
                    break;
                } else if (dproc.rounding_mode != 3) {
                    code.emitPushInt(dproc.rounding_mode);
                    argTypes = new Type[]{type1, type2, Type.intType};
                    break;
                }
                break;
            case 9:
                mname = "shift";
                break;
            case 10:
            case 11:
                mname = this.op == 10 ? "shiftLeft" : "shiftRight";
                mclass = ClassType.make("gnu.kawa.functions.BitwiseOp");
                break;
            case 12:
            default:
                throw new Error();
            case 13:
                mname = "and";
            case 14:
                if (mname == null) {
                    mname = "ior";
                }
            case 15:
                if (mname == null) {
                    mname = "xor";
                }
                mclass = ClassType.make("gnu.math.BitOps");
                break;
        }
        if (argTypes == null) {
            argTypes = new Type[]{type1, type2};
        }
        Method meth = mclass.getMethod(mname, argTypes);
        code.emitInvokeStatic(meth);
        return true;
    }

    public static int getReturnKind(int kind1, int kind2, int op) {
        if (op < 9 || op > 12) {
            if (kind1 <= 0 || (kind1 > kind2 && kind2 > 0)) {
                kind2 = kind1;
            }
            return kind2;
        }
        return kind1;
    }

    public int getReturnKind(Expression[] args) {
        int len = args.length;
        if (len == 0) {
            return 4;
        }
        ClassType classType = Type.pointer_type;
        int kindr = 0;
        for (int i = 0; i < len; i++) {
            Expression arg = args[i];
            int kind = Arithmetic.classifyType(arg.getType());
            if (i == 0 || kind == 0 || kind > kindr) {
                kindr = kind;
            }
        }
        return kindr;
    }

    public Type getReturnType(Expression[] args) {
        return Arithmetic.kindType(adjustReturnKind(getReturnKind(args), this.op));
    }

    static int adjustReturnKind(int rkind, int op) {
        if (op >= 4 && op <= 7 && rkind > 0) {
            switch (op) {
                case 4:
                    if (rkind <= 4) {
                        return 6;
                    }
                    return rkind;
                case 5:
                    if (rkind <= 10 && rkind != 7) {
                        return 8;
                    }
                    return rkind;
                case 6:
                default:
                    return rkind;
                case 7:
                    if (rkind <= 10) {
                        return 4;
                    }
                    return rkind;
            }
        }
        return rkind;
    }

    public static Expression validateApplyAdd(AddOp proc, ApplyExp exp, InlineCalls visitor) {
        Expression[] args = exp.getArgs();
        if (args.length == 1 && proc.plusOrMinus < 0) {
            Type type0 = args[0].getType();
            if (type0 instanceof PrimType) {
                char sig0 = type0.getSignature().charAt(0);
                Type type = null;
                int opcode = 0;
                if (sig0 != 'V' && sig0 != 'Z' && sig0 != 'C') {
                    if (sig0 == 'D') {
                        opcode = 119;
                        type = LangPrimType.doubleType;
                    } else if (sig0 == 'F') {
                        opcode = 118;
                        type = LangPrimType.floatType;
                    } else if (sig0 == 'J') {
                        opcode = 117;
                        type = LangPrimType.longType;
                    } else {
                        opcode = 116;
                        type = LangPrimType.intType;
                    }
                }
                if (type != null) {
                    PrimProcedure prim = PrimProcedure.makeBuiltinUnary(opcode, type);
                    return new ApplyExp(prim, args);
                }
                return exp;
            }
            return exp;
        }
        return exp;
    }

    public static Expression validateApplyDiv(DivideOp proc, ApplyExp exp, InlineCalls visitor) {
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            return new ApplyExp(exp.getFunction(), QuoteExp.getInstance(IntNum.one()), args[0]);
        }
        return exp;
    }

    public static Expression validateApplyNot(ApplyExp exp, int kind, InlineCalls visitor) {
        String cname;
        if (exp.getArgCount() == 1) {
            Expression arg = exp.getArg(0);
            if (kind == 1 || kind == 2) {
                Expression[] args = {arg, QuoteExp.getInstance(IntNum.minusOne())};
                return visitor.visitApplyOnly(new ApplyExp(BitwiseOp.xor, args), null);
            }
            if (kind == 4) {
                cname = "gnu.math.BitOps";
            } else if (kind == 3) {
                cname = "java.meth.BigInteger";
            } else {
                cname = null;
            }
            if (cname != null) {
                return new ApplyExp(ClassType.make(cname).getDeclaredMethod("not", 1), exp.getArgs());
            }
            return exp;
        }
        return exp;
    }

    public static Expression validateApplyNumberCompare(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression folded = exp.inlineIfConstant(proc, visitor);
        return folded != exp ? folded : exp;
    }

    public int primitiveOpcode() {
        switch (this.op) {
            case 1:
                return 96;
            case 2:
                return 100;
            case 3:
                return 104;
            case 4:
            case 5:
            case 6:
            case 7:
                return 108;
            case 8:
                return 112;
            case 9:
            default:
                return -1;
            case 10:
                return 120;
            case 11:
                return 122;
            case 12:
                return 124;
            case 13:
                return 126;
            case 14:
                return 128;
            case 15:
                return 130;
        }
    }

    public static Expression pairwise(Procedure proc, Expression rproc, Expression[] args, InlineCalls visitor) {
        int len = args.length;
        Expression prev = args[0];
        for (int i = 1; i < len; i++) {
            Expression[] args2 = {prev, args[i]};
            ApplyExp next = new ApplyExp(rproc, args2);
            Expression inlined = visitor.maybeInline(next, null, proc);
            prev = inlined != null ? inlined : next;
        }
        return prev;
    }

    public static Expression validateApplyNumberPredicate(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        NumberPredicate nproc = (NumberPredicate) proc;
        int i = nproc.op;
        Expression[] args = exp.getArgs();
        args[0] = visitor.visit(args[0], (Type) LangObjType.integerType);
        exp.setType(Type.booleanType);
        return exp;
    }
}
