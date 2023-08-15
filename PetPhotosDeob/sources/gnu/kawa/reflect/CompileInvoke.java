package gnu.kawa.reflect;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Keyword;
import gnu.expr.LetExp;
import gnu.expr.PairClassType;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.TypeValue;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;

/* loaded from: classes.dex */
public class CompileInvoke {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r44v0, types: [gnu.expr.LetExp] */
    public static Expression validateApplyInvoke(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        ObjectType type;
        int margsLength;
        int argsStartIndex;
        int objIndex;
        ClassType caller;
        int okCount;
        int maybeCount;
        ApplyExp ae;
        Type stype;
        Procedure constructor;
        Invoke iproc = (Invoke) proc;
        char kind = iproc.kind;
        Compilation comp = visitor.getCompilation();
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        if (!comp.mustCompile || nargs == 0 || ((kind == 'V' || kind == '*') && nargs == 1)) {
            exp.visitArgs(visitor);
            return exp;
        }
        Expression arg0 = visitor.visit(args[0], (Type) null);
        args[0] = arg0;
        Type type0 = (kind == 'V' || kind == '*') ? arg0.getType() : iproc.language.getTypeFor(arg0);
        if ((type0 instanceof PairClassType) && kind == 'N') {
            type = ((PairClassType) type0).instanceType;
        } else if (type0 instanceof ObjectType) {
            type = (ObjectType) type0;
        } else {
            type = null;
        }
        String name = getMethodName(args, kind);
        if (kind == 'V' || kind == '*') {
            margsLength = nargs - 1;
            argsStartIndex = 2;
            objIndex = 0;
        } else if (kind == 'N') {
            margsLength = nargs;
            argsStartIndex = 0;
            objIndex = -1;
        } else if (kind == 'S' || kind == 's') {
            margsLength = nargs - 2;
            argsStartIndex = 2;
            objIndex = -1;
        } else if (kind == 'P') {
            margsLength = nargs - 2;
            argsStartIndex = 3;
            objIndex = 1;
        } else {
            exp.visitArgs(visitor);
            return exp;
        }
        if (kind == 'N' && (type instanceof ArrayType)) {
            ArrayType atype = (ArrayType) type;
            Type elementType = atype.getComponentType();
            Expression sizeArg = null;
            boolean lengthSpecified = false;
            if (args.length >= 3 && (args[1] instanceof QuoteExp)) {
                Object arg1 = ((QuoteExp) args[1]).getValue();
                if (arg1 instanceof Keyword) {
                    String name2 = ((Keyword) arg1).getName();
                    if (PropertyTypeConstants.PROPERTY_TYPE_LENGTH.equals(name2) || "size".equals(name2)) {
                        sizeArg = args[2];
                        lengthSpecified = true;
                    }
                }
            }
            if (sizeArg == null) {
                sizeArg = QuoteExp.getInstance(new Integer(args.length - 1));
            }
            ApplyExp alloc = new ApplyExp(new ArrayNew(elementType), visitor.visit(sizeArg, Type.intType));
            alloc.setType(atype);
            if (lengthSpecified && args.length == 3) {
                return alloc;
            }
            LetExp let = new LetExp(new Expression[]{alloc});
            Declaration adecl = let.addDeclaration(null, atype);
            adecl.noteValue(alloc);
            BeginExp begin = new BeginExp();
            int index = 0;
            int i = lengthSpecified ? 3 : 1;
            while (i < args.length) {
                Expression arg = args[i];
                if (lengthSpecified && i + 1 < args.length && (arg instanceof QuoteExp)) {
                    Object key = ((QuoteExp) arg).getValue();
                    if (key instanceof Keyword) {
                        String kname = ((Keyword) key).getName();
                        try {
                            index = Integer.parseInt(kname);
                            i++;
                            arg = args[i];
                        } catch (Throwable th) {
                            comp.error('e', "non-integer keyword '" + kname + "' in array constructor");
                            return exp;
                        }
                    } else {
                        continue;
                    }
                }
                begin.add(new ApplyExp(new ArraySet(elementType), new ReferenceExp(adecl), QuoteExp.getInstance(new Integer(index)), visitor.visit(arg, elementType)));
                index++;
                i++;
            }
            begin.add(new ReferenceExp(adecl));
            let.body = begin;
            return let;
        }
        if (type != null && name != null) {
            if ((type instanceof TypeValue) && kind == 'N' && (constructor = ((TypeValue) type).getConstructor()) != null) {
                Expression[] xargs = new Expression[nargs - 1];
                System.arraycopy(args, 1, xargs, 0, nargs - 1);
                return visitor.visit(new ApplyExp(constructor, xargs), required);
            }
            if (comp == null) {
                caller = null;
            } else {
                caller = comp.curClass != null ? comp.curClass : comp.mainClass;
            }
            ObjectType ctype = type;
            try {
                PrimProcedure[] methods = getMethods(ctype, name, caller, iproc);
                int numCode = ClassMethods.selectApplicable(methods, margsLength);
                int index2 = -1;
                if (kind == 'N') {
                    int keywordStart = hasKeywordArgument(1, args);
                    if (keywordStart < args.length || (numCode <= 0 && (ClassMethods.selectApplicable(methods, new Type[]{Compilation.typeClassType}) >> 32) == 1)) {
                        Object[] slots = checkKeywords(ctype, args, keywordStart, caller);
                        if (slots.length * 2 == args.length - keywordStart || ClassMethods.selectApplicable(ClassMethods.getMethods(ctype, "add", 'V', null, iproc.language), 2) > 0) {
                            StringBuffer errbuf = null;
                            for (int i2 = 0; i2 < slots.length; i2++) {
                                if (slots[i2] instanceof String) {
                                    if (errbuf == null) {
                                        errbuf = new StringBuffer();
                                        errbuf.append("no field or setter ");
                                    } else {
                                        errbuf.append(", ");
                                    }
                                    errbuf.append('`');
                                    errbuf.append(slots[i2]);
                                    errbuf.append('\'');
                                }
                            }
                            if (errbuf != null) {
                                errbuf.append(" in class ");
                                errbuf.append(type.getName());
                                comp.error('w', errbuf.toString());
                                return exp;
                            }
                            if (keywordStart < args.length) {
                                Expression[] xargs2 = new Expression[keywordStart];
                                System.arraycopy(args, 0, xargs2, 0, keywordStart);
                                ae = (ApplyExp) visitor.visit(new ApplyExp(exp.getFunction(), xargs2), (Type) ctype);
                            } else {
                                ae = new ApplyExp(methods[0], arg0);
                            }
                            ae.setType(ctype);
                            ApplyExp applyExp = ae;
                            if (args.length > 0) {
                                for (int i3 = 0; i3 < slots.length; i3++) {
                                    Object slot = slots[i3];
                                    if (slot instanceof Method) {
                                        stype = ((Method) slot).getParameterTypes()[0];
                                    } else if (slot instanceof Field) {
                                        stype = ((Field) slot).getType();
                                    } else {
                                        stype = null;
                                    }
                                    if (stype != null) {
                                        stype = iproc.language.getLangTypeFor(stype);
                                    }
                                    ae = new ApplyExp(SlotSet.setFieldReturnObject, ae, new QuoteExp(slot), visitor.visit(args[(i3 * 2) + keywordStart + 1], stype));
                                    ae.setType(ctype);
                                }
                                int sargs = keywordStart == args.length ? 1 : (slots.length * 2) + keywordStart;
                                applyExp = ae;
                                if (sargs < args.length) {
                                    ?? letExp = new LetExp(new Expression[]{applyExp});
                                    Declaration adecl2 = letExp.addDeclaration(null, ctype);
                                    adecl2.noteValue(applyExp);
                                    BeginExp begin2 = new BeginExp();
                                    for (int i4 = sargs; i4 < args.length; i4++) {
                                        Expression[] iargs = {new ReferenceExp(adecl2), QuoteExp.getInstance("add"), args[i4]};
                                        begin2.add(visitor.visit(new ApplyExp(Invoke.invoke, iargs), (Type) null));
                                    }
                                    begin2.add(new ReferenceExp(adecl2));
                                    letExp.body = begin2;
                                    applyExp = letExp;
                                }
                            }
                            return visitor.checkType(applyExp.setLine(exp), required);
                        }
                    }
                }
                if (numCode >= 0) {
                    int i5 = 1;
                    while (i5 < nargs) {
                        Type atype2 = null;
                        boolean last = i5 == nargs + (-1);
                        if ((kind == 'P' && i5 == 2) || (kind != 'N' && i5 == 1)) {
                            atype2 = null;
                        } else if (kind == 'P' && i5 == 1) {
                            atype2 = ctype;
                        } else if (numCode > 0) {
                            int pi = i5 - (kind == 'N' ? 1 : argsStartIndex);
                            int j = 0;
                            while (j < numCode) {
                                PrimProcedure pproc = methods[j];
                                int pii = pi + ((kind == 'S' || !pproc.takesTarget()) ? 0 : 1);
                                if (last && pproc.takesVarArgs() && pii == pproc.minArgs()) {
                                    atype2 = null;
                                } else {
                                    Type ptype = pproc.getParameterType(pii);
                                    if (j == 0) {
                                        atype2 = ptype;
                                    } else if ((ptype instanceof PrimType) != (atype2 instanceof PrimType)) {
                                        atype2 = null;
                                    } else {
                                        atype2 = Type.lowestCommonSuperType(atype2, ptype);
                                    }
                                }
                                if (atype2 != null) {
                                    j++;
                                    atype2 = atype2;
                                }
                            }
                        }
                        args[i5] = visitor.visit(args[i5], atype2);
                        i5++;
                    }
                    long num = selectApplicable(methods, ctype, args, margsLength, argsStartIndex, objIndex);
                    okCount = (int) (num >> 32);
                    maybeCount = (int) num;
                } else {
                    okCount = 0;
                    maybeCount = 0;
                }
                int nmethods = methods.length;
                if (okCount + maybeCount == 0 && kind == 'N') {
                    methods = getMethods(ctype, "valueOf", caller, Invoke.invokeStatic);
                    argsStartIndex = 1;
                    margsLength = nargs - 1;
                    long num2 = selectApplicable(methods, ctype, args, margsLength, 1, -1);
                    okCount = (int) (num2 >> 32);
                    maybeCount = (int) num2;
                }
                if (okCount + maybeCount == 0) {
                    if (kind == 'P' || comp.warnInvokeUnknownMethod()) {
                        if (kind == 'N') {
                            name = name + "/valueOf";
                        }
                        StringBuilder sbuf = new StringBuilder();
                        if (methods.length + nmethods == 0) {
                            sbuf.append("no accessible method '");
                        } else if (numCode == -983040) {
                            sbuf.append("too few arguments for method '");
                        } else if (numCode == -917504) {
                            sbuf.append("too many arguments for method '");
                        } else {
                            sbuf.append("no possibly applicable method '");
                        }
                        sbuf.append(name);
                        sbuf.append("' in ");
                        sbuf.append(type.getName());
                        comp.error(kind == 'P' ? 'e' : 'w', sbuf.toString());
                    }
                } else if (okCount == 1 || (okCount == 0 && maybeCount == 1)) {
                    index2 = 0;
                } else if (okCount > 0) {
                    index2 = MethodProc.mostSpecific(methods, okCount);
                    if (index2 < 0 && kind == 'S') {
                        int i6 = 0;
                        while (true) {
                            if (i6 >= okCount) {
                                break;
                            }
                            if (methods[i6].getStaticFlag()) {
                                if (index2 >= 0) {
                                    index2 = -1;
                                    break;
                                }
                                index2 = i6;
                            }
                            i6++;
                        }
                    }
                    if (index2 < 0 && (kind == 'P' || comp.warnInvokeUnknownMethod())) {
                        StringBuffer sbuf2 = new StringBuffer();
                        sbuf2.append("more than one definitely applicable method `");
                        sbuf2.append(name);
                        sbuf2.append("' in ");
                        sbuf2.append(type.getName());
                        append(methods, okCount, sbuf2);
                        comp.error(kind == 'P' ? 'e' : 'w', sbuf2.toString());
                    }
                } else if (kind == 'P' || comp.warnInvokeUnknownMethod()) {
                    StringBuffer sbuf3 = new StringBuffer();
                    sbuf3.append("more than one possibly applicable method '");
                    sbuf3.append(name);
                    sbuf3.append("' in ");
                    sbuf3.append(type.getName());
                    append(methods, maybeCount, sbuf3);
                    comp.error(kind == 'P' ? 'e' : 'w', sbuf3.toString());
                }
                if (index2 >= 0) {
                    Expression[] margs = new Expression[margsLength];
                    PrimProcedure method = methods[index2];
                    method.takesVarArgs();
                    int dst = 0;
                    if (objIndex >= 0) {
                        int dst2 = 0 + 1;
                        margs[0] = args[objIndex];
                        dst = dst2;
                    }
                    int src = argsStartIndex;
                    while (src < args.length && dst < margs.length) {
                        margs[dst] = args[src];
                        src++;
                        dst++;
                    }
                    ApplyExp e = new ApplyExp(method, margs);
                    e.setLine(exp);
                    return visitor.visitApplyOnly(e, required);
                }
            } catch (Exception e2) {
                comp.error('w', "unknown class: " + type.getName());
                return exp;
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    static Object[] checkKeywords(ObjectType type, Expression[] args, int start, ClassType caller) {
        int len = args.length;
        int npairs = 0;
        while ((npairs * 2) + start + 1 < len && (args[(npairs * 2) + start].valueIfConstant() instanceof Keyword)) {
            npairs++;
        }
        Object[] fields = new Object[npairs];
        for (int i = 0; i < npairs; i++) {
            Object value = args[(i * 2) + start].valueIfConstant();
            String name = ((Keyword) value).getName();
            String lookupMember = SlotSet.lookupMember(type, name, caller);
            if (lookupMember == null) {
                lookupMember = type.getMethod(ClassExp.slotToMethodName("add", name), SlotSet.type1Array);
            }
            if (lookupMember == null) {
                lookupMember = name;
            }
            fields[i] = lookupMember;
        }
        return fields;
    }

    private static String getMethodName(Expression[] args, char kind) {
        if (kind == 'N') {
            return "<init>";
        }
        int nameIndex = kind == 'P' ? 2 : 1;
        if (args.length >= nameIndex + 1) {
            return ClassMethods.checkName(args[nameIndex], false);
        }
        return null;
    }

    private static void append(PrimProcedure[] methods, int mcount, StringBuffer sbuf) {
        for (int i = 0; i < mcount; i++) {
            sbuf.append("\n  candidate: ");
            sbuf.append(methods[i]);
        }
    }

    protected static PrimProcedure[] getMethods(ObjectType ctype, String mname, ClassType caller, Invoke iproc) {
        int kind = iproc.kind;
        return ClassMethods.getMethods(ctype, mname, kind != 80 ? (kind == 42 || kind == 86) ? 'V' : (char) 0 : 'P', caller, iproc.language);
    }

    static int hasKeywordArgument(int argsStartIndex, Expression[] args) {
        for (int i = argsStartIndex; i < args.length; i++) {
            if (args[i].valueIfConstant() instanceof Keyword) {
                return i;
            }
        }
        int i2 = args.length;
        return i2;
    }

    private static long selectApplicable(PrimProcedure[] methods, ObjectType ctype, Expression[] args, int margsLength, int argsStartIndex, int objIndex) {
        Type[] atypes = new Type[margsLength];
        int dst = 0;
        if (objIndex >= 0) {
            int dst2 = 0 + 1;
            atypes[0] = ctype;
            dst = dst2;
        }
        int src = argsStartIndex;
        while (src < args.length && dst < atypes.length) {
            Expression arg = args[src];
            Type atype = null;
            if (InlineCalls.checkIntValue(arg) != null) {
                atype = Type.intType;
            } else if (InlineCalls.checkLongValue(arg) != null) {
                atype = Type.longType;
            } else if (0 == 0) {
                atype = arg.getType();
            }
            atypes[dst] = atype;
            src++;
            dst++;
        }
        return ClassMethods.selectApplicable(methods, atypes);
    }

    public static synchronized PrimProcedure getStaticMethod(ClassType type, String name, Expression[] args) {
        int index;
        PrimProcedure primProcedure;
        synchronized (CompileInvoke.class) {
            PrimProcedure[] methods = getMethods(type, name, null, Invoke.invokeStatic);
            long num = selectApplicable(methods, type, args, args.length, 0, -1);
            int okCount = (int) (num >> 32);
            int maybeCount = (int) num;
            if (methods == null) {
                index = -1;
            } else if (okCount > 0) {
                index = MethodProc.mostSpecific(methods, okCount);
            } else if (maybeCount == 1) {
                index = 0;
            } else {
                index = -1;
            }
            primProcedure = index < 0 ? null : methods[index];
        }
        return primProcedure;
    }
}
