package gnu.kawa.reflect;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.lists.FString;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;

/* loaded from: classes.dex */
public class CompileReflect {
    public static int checkKnownClass(Type type, Compilation comp) {
        if ((type instanceof ClassType) && type.isExisting()) {
            try {
                type.getReflectClass();
                return 1;
            } catch (Exception e) {
                comp.error('e', "unknown class: " + type.getName());
                return -1;
            }
        }
        return 0;
    }

    public static ApplyExp inlineClassName(ApplyExp exp, int carg, InlineCalls walker) {
        Compilation comp = walker.getCompilation();
        Language language = comp.getLanguage();
        Expression[] args = exp.getArgs();
        if (args.length > carg) {
            Type type = language.getTypeFor(args[carg]);
            if (type instanceof Type) {
                checkKnownClass(type, comp);
                Expression[] nargs = new Expression[args.length];
                System.arraycopy(args, 0, nargs, 0, args.length);
                nargs[carg] = new QuoteExp(type);
                ApplyExp nexp = new ApplyExp(exp.getFunction(), nargs);
                nexp.setLine(exp);
                return nexp;
            }
            return exp;
        }
        return exp;
    }

    public static Expression validateApplyInstanceOf(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        ApplyExp exp2 = inlineClassName(exp, 1, visitor);
        Expression[] args = exp2.getArgs();
        if (args.length == 2) {
            Expression value = args[0];
            Expression texp = args[1];
            if (texp instanceof QuoteExp) {
                Object t = ((QuoteExp) texp).getValue();
                if (t instanceof Type) {
                    Type type = (Type) t;
                    if (type instanceof PrimType) {
                        type = ((PrimType) type).boxedType();
                    }
                    if (value instanceof QuoteExp) {
                        return type.isInstance(((QuoteExp) value).getValue()) ? QuoteExp.trueExp : QuoteExp.falseExp;
                    } else if (!value.side_effects()) {
                        int comp = type.compare(value.getType());
                        if (comp == 1 || comp == 0) {
                            return QuoteExp.trueExp;
                        }
                        if (comp == -3) {
                            return QuoteExp.falseExp;
                        }
                    }
                }
            }
        }
        return exp2;
    }

    public static Expression validateApplySlotGet(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Type type;
        exp.visitArgs(visitor);
        Compilation comp = visitor.getCompilation();
        Language language = comp.getLanguage();
        SlotGet gproc = (SlotGet) proc;
        boolean isStatic = gproc.isStatic;
        Expression[] args = exp.getArgs();
        Expression arg0 = args[0];
        Expression arg1 = args[1];
        Object val1 = arg1.valueIfConstant();
        if ((val1 instanceof String) || (val1 instanceof FString) || (val1 instanceof Symbol)) {
            String name = val1.toString();
            if (isStatic) {
                type = language.getTypeFor(arg0);
                int known = checkKnownClass(type, comp);
                if (known >= 0) {
                    if ("class".equals(name)) {
                        if (known > 0) {
                            return QuoteExp.getInstance(type.getReflectClass());
                        }
                        return new ApplyExp(Compilation.typeType.getDeclaredMethod("getReflectClass", 0), arg0);
                    } else if (type != null) {
                        Expression[] nargs = {new QuoteExp(type), arg1};
                        ApplyExp nexp = new ApplyExp(exp.getFunction(), nargs);
                        nexp.setLine(exp);
                        exp = nexp;
                    }
                } else {
                    return exp;
                }
            } else {
                type = arg0.getType();
            }
            if (!(type instanceof ArrayType)) {
                if (type instanceof ObjectType) {
                    ObjectType ctype = (ObjectType) type;
                    ClassType caller = comp.curClass != null ? comp.curClass : comp.mainClass;
                    Member part = SlotGet.lookupMember(ctype, name, caller);
                    if (part instanceof Field) {
                        Field field = (Field) part;
                        int modifiers = field.getModifiers();
                        boolean isStaticField = (modifiers & 8) != 0;
                        if (isStatic && !isStaticField) {
                            return new ErrorExp("cannot access non-static field `" + name + "' using `" + proc.getName() + '\'', comp);
                        }
                        if (caller != null && !caller.isAccessible(field, ctype)) {
                            return new ErrorExp("field " + field.getDeclaringClass().getName() + '.' + name + " is not accessible here", comp);
                        }
                    } else if (part instanceof Method) {
                        Method method = (Method) part;
                        ClassType dtype = method.getDeclaringClass();
                        int modifiers2 = method.getModifiers();
                        boolean isStaticMethod = method.getStaticFlag();
                        if (isStatic && !isStaticMethod) {
                            return new ErrorExp("cannot call non-static getter method `" + name + "' using `" + proc.getName() + '\'', comp);
                        }
                        if (caller != null && !caller.isAccessible(dtype, ctype, modifiers2)) {
                            return new ErrorExp("method " + method + " is not accessible here", comp);
                        }
                    }
                    if (part != null) {
                        Expression[] nargs2 = {arg0, new QuoteExp(part)};
                        ApplyExp nexp2 = new ApplyExp(exp.getFunction(), nargs2);
                        nexp2.setLine(exp);
                        return nexp2;
                    } else if (type != Type.pointer_type && comp.warnUnknownMember()) {
                        comp.error('e', "no slot `" + name + "' in " + ctype.getName());
                    }
                }
                String fname = Compilation.mangleNameIfNeeded(name);
                String fname2 = fname.intern();
                String getName = ClassExp.slotToMethodName("get", name);
                String isName = ClassExp.slotToMethodName("is", name);
                Invoke invoke = Invoke.invokeStatic;
                Expression[] expressionArr = new Expression[9];
                expressionArr[0] = QuoteExp.getInstance("gnu.kawa.reflect.SlotGet");
                expressionArr[1] = QuoteExp.getInstance("getSlotValue");
                expressionArr[2] = isStatic ? QuoteExp.trueExp : QuoteExp.falseExp;
                expressionArr[3] = args[0];
                expressionArr[4] = QuoteExp.getInstance(name);
                expressionArr[5] = QuoteExp.getInstance(fname2);
                expressionArr[6] = QuoteExp.getInstance(getName);
                expressionArr[7] = QuoteExp.getInstance(isName);
                expressionArr[8] = QuoteExp.getInstance(language);
                ApplyExp nexp3 = new ApplyExp(invoke, expressionArr);
                nexp3.setLine(exp);
                return visitor.visitApplyOnly(nexp3, null);
            }
            return exp;
        }
        return exp;
    }

    public static Expression validateApplySlotSet(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        SlotSet sproc = (SlotSet) proc;
        boolean isStatic = sproc.isStatic;
        if (isStatic && visitor.getCompilation().mustCompile) {
            exp = inlineClassName(exp, 0, visitor);
        }
        exp.setType((sproc.returnSelf && exp.getArgCount() == 3) ? exp.getArg(0).getType() : Type.voidType);
        return exp;
    }

    public static Expression validateApplyTypeSwitch(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        for (int i = 1; i < args.length; i++) {
            if (args[i] instanceof LambdaExp) {
                LambdaExp lexp = (LambdaExp) args[i];
                lexp.setInlineOnly(true);
                lexp.returnContinuation = exp;
                lexp.inlineHome = visitor.getCurrentLambda();
            }
        }
        return exp;
    }
}
