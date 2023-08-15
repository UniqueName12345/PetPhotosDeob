package kawa.standard;

import gnu.bytecode.Type;
import gnu.expr.BeginExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.ObjectExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.expr.ThisExp;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class object extends Syntax {
    public static final Keyword accessKeyword;
    public static final Keyword allocationKeyword;
    public static final Keyword classNameKeyword;
    static final Symbol coloncolon;
    static final Keyword initKeyword;
    static final Keyword init_formKeyword;
    static final Keyword init_keywordKeyword;
    static final Keyword init_valueKeyword;
    static final Keyword initformKeyword;
    public static final Keyword interfaceKeyword;
    public static final object objectSyntax = new object(SchemeCompilation.lambda);
    public static final Keyword throwsKeyword;
    static final Keyword typeKeyword;
    Lambda lambda;

    static {
        objectSyntax.setName("object");
        accessKeyword = Keyword.make("access");
        classNameKeyword = Keyword.make("class-name");
        interfaceKeyword = Keyword.make("interface");
        throwsKeyword = Keyword.make("throws");
        typeKeyword = Keyword.make("type");
        allocationKeyword = Keyword.make("allocation");
        initKeyword = Keyword.make("init");
        initformKeyword = Keyword.make("initform");
        init_formKeyword = Keyword.make("init-form");
        init_valueKeyword = Keyword.make("init-value");
        init_keywordKeyword = Keyword.make("init-keyword");
        coloncolon = Namespace.EmptyNamespace.getSymbol("::");
    }

    public object(Lambda lambda) {
        this.lambda = lambda;
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        if (!(form.getCdr() instanceof Pair)) {
            return tr.syntaxError("missing superclass specification in object");
        }
        Pair pair = (Pair) form.getCdr();
        ObjectExp oexp = new ObjectExp();
        if (pair.getCar() instanceof FString) {
            if (!(pair.getCdr() instanceof Pair)) {
                return tr.syntaxError("missing superclass specification after object class name");
            }
            pair = (Pair) pair.getCdr();
        }
        Object[] saved = scanClassDef(pair, oexp, tr);
        if (saved != null) {
            rewriteClassDef(saved, tr);
            return oexp;
        }
        return oexp;
    }

    public Object[] scanClassDef(Pair pair, ClassExp oexp, Translator tr) {
        Declaration decl;
        Object args;
        Object obj;
        Object obj2;
        tr.mustCompileHere();
        Object superlist = pair.getCar();
        Object components = pair.getCdr();
        Object classNamePair = null;
        LambdaExp method_list = null;
        LambdaExp last_method = null;
        long classAccessFlag = 0;
        Vector inits = new Vector(20);
        Object obj3 = components;
        while (obj3 != LList.Empty) {
            while (obj3 instanceof SyntaxForm) {
                obj3 = ((SyntaxForm) obj3).getDatum();
            }
            if (!(obj3 instanceof Pair)) {
                tr.error('e', "object member not a list");
                return null;
            }
            Pair pair2 = (Pair) obj3;
            Object pair_car = pair2.getCar();
            while (pair_car instanceof SyntaxForm) {
                pair_car = ((SyntaxForm) pair_car).getDatum();
            }
            obj3 = pair2.getCdr();
            Object savedPos1 = tr.pushPositionOf(pair2);
            if (pair_car instanceof Keyword) {
                while (obj3 instanceof SyntaxForm) {
                    obj3 = ((SyntaxForm) obj3).getDatum();
                }
                if (obj3 instanceof Pair) {
                    if (pair_car == interfaceKeyword) {
                        Object val = ((Pair) obj3).getCar();
                        if (val == Boolean.FALSE) {
                            oexp.setFlag(65536);
                        } else {
                            oexp.setFlag(32768);
                        }
                        obj3 = ((Pair) obj3).getCdr();
                        tr.popPositionOf(savedPos1);
                    } else if (pair_car == classNameKeyword) {
                        if (classNamePair != null) {
                            tr.error('e', "duplicate class-name specifiers");
                        }
                        classNamePair = obj3;
                        obj3 = ((Pair) obj3).getCdr();
                        tr.popPositionOf(savedPos1);
                    } else if (pair_car == accessKeyword) {
                        Object savedPos2 = tr.pushPositionOf(obj3);
                        classAccessFlag = addAccessFlags(((Pair) obj3).getCar(), classAccessFlag, Declaration.CLASS_ACCESS_FLAGS, "class", tr);
                        if (oexp.nameDecl == null) {
                            tr.error('e', "access specifier for anonymous class");
                        }
                        tr.popPositionOf(savedPos2);
                        obj3 = ((Pair) obj3).getCdr();
                        tr.popPositionOf(savedPos1);
                    }
                }
            }
            if (!(pair_car instanceof Pair)) {
                tr.error('e', "object member not a list");
                return null;
            }
            Pair pair3 = (Pair) pair_car;
            Object pair_car2 = pair3.getCar();
            while (pair_car2 instanceof SyntaxForm) {
                pair_car2 = ((SyntaxForm) pair_car2).getDatum();
            }
            if ((pair_car2 instanceof String) || (pair_car2 instanceof Symbol) || (pair_car2 instanceof Keyword)) {
                Pair typePair = null;
                Object sname = pair_car2;
                int allocationFlag = 0;
                long accessFlag = 0;
                if (sname instanceof Keyword) {
                    decl = null;
                    args = pair3;
                } else {
                    decl = oexp.addDeclaration(sname);
                    decl.setSimple(false);
                    decl.setFlag(1048576L);
                    Translator.setLine(decl, pair3);
                    args = pair3.getCdr();
                }
                int nKeywords = 0;
                boolean seenInit = false;
                Pair initPair = null;
                while (args != LList.Empty) {
                    while (true) {
                        obj2 = args;
                        if (!(obj2 instanceof SyntaxForm)) {
                            break;
                        }
                        args = ((SyntaxForm) obj2).getDatum();
                    }
                    Pair pair4 = (Pair) obj2;
                    Object key = pair4.getCar();
                    while (key instanceof SyntaxForm) {
                        key = ((SyntaxForm) key).getDatum();
                    }
                    Object savedPos22 = tr.pushPositionOf(pair4);
                    args = pair4.getCdr();
                    if ((key == coloncolon || (key instanceof Keyword)) && (args instanceof Pair)) {
                        nKeywords++;
                        Pair pair5 = (Pair) args;
                        Object value = pair5.getCar();
                        args = pair5.getCdr();
                        if (key == coloncolon || key == typeKeyword) {
                            typePair = pair5;
                        } else if (key == allocationKeyword) {
                            if (allocationFlag != 0) {
                                tr.error('e', "duplicate allocation: specification");
                            }
                            if (matches(value, "class", tr) || matches(value, "static", tr)) {
                                allocationFlag = 2048;
                            } else if (matches(value, "instance", tr)) {
                                allocationFlag = 4096;
                            } else {
                                tr.error('e', "unknown allocation kind '" + value + "'");
                            }
                        } else if (key == initKeyword || key == initformKeyword || key == init_formKeyword || key == init_valueKeyword) {
                            if (seenInit) {
                                tr.error('e', "duplicate initialization");
                            }
                            seenInit = true;
                            if (key != initKeyword) {
                                initPair = pair5;
                            }
                        } else if (key == init_keywordKeyword) {
                            if (!(value instanceof Keyword)) {
                                tr.error('e', "invalid 'init-keyword' - not a keyword");
                            } else if (((Keyword) value).getName() != sname.toString()) {
                                tr.error('w', "init-keyword option ignored");
                            }
                        } else if (key == accessKeyword) {
                            Object savedPos3 = tr.pushPositionOf(pair5);
                            accessFlag = addAccessFlags(value, accessFlag, Declaration.FIELD_ACCESS_FLAGS, "field", tr);
                            tr.popPositionOf(savedPos3);
                        } else {
                            tr.error('w', "unknown slot keyword '" + key + "'");
                        }
                    } else if (args == LList.Empty && !seenInit) {
                        initPair = pair4;
                        seenInit = true;
                    } else {
                        if ((args instanceof Pair) && nKeywords == 0 && !seenInit && typePair == null) {
                            Pair pair6 = (Pair) args;
                            if (pair6.getCdr() == LList.Empty) {
                                typePair = pair4;
                                initPair = pair6;
                                args = pair6.getCdr();
                                seenInit = true;
                            }
                        }
                        args = null;
                        break;
                    }
                    tr.popPositionOf(savedPos22);
                }
                if (args != LList.Empty) {
                    tr.error('e', "invalid argument list for slot '" + sname + "' args:" + (args == null ? "null" : args.getClass().getName()));
                    return null;
                }
                if (seenInit) {
                    boolean isStatic = allocationFlag == 2048;
                    if (decl != null) {
                        obj = decl;
                    } else {
                        obj = isStatic ? Boolean.TRUE : Boolean.FALSE;
                    }
                    inits.addElement(obj);
                    inits.addElement(initPair);
                }
                if (decl == null) {
                    if (!seenInit) {
                        tr.error('e', "missing field name");
                        return null;
                    }
                } else {
                    if (typePair != null) {
                        decl.setType(tr.exp2Type(typePair));
                    }
                    if (allocationFlag != 0) {
                        decl.setFlag(allocationFlag);
                    }
                    if (accessFlag != 0) {
                        decl.setFlag(accessFlag);
                    }
                    decl.setCanRead(true);
                    decl.setCanWrite(true);
                }
            } else if (pair_car2 instanceof Pair) {
                Pair mpair = (Pair) pair_car2;
                Object mname = mpair.getCar();
                if (!(mname instanceof String) && !(mname instanceof Symbol)) {
                    tr.error('e', "missing method name");
                    return null;
                }
                LambdaExp lexp = new LambdaExp();
                Translator.setLine(oexp.addMethod(lexp, mname), mpair);
                if (last_method == null) {
                    method_list = lexp;
                } else {
                    last_method.nextSibling = lexp;
                }
                last_method = lexp;
            } else {
                tr.error('e', "invalid field/method definition");
            }
            tr.popPositionOf(savedPos1);
        }
        if (classAccessFlag != 0) {
            oexp.nameDecl.setFlag(classAccessFlag);
        }
        return new Object[]{oexp, components, inits, method_list, superlist, classNamePair};
    }

    public void rewriteClassDef(Object[] saved, Translator tr) {
        Declaration decl;
        ClassExp oexp = (ClassExp) saved[0];
        Object components = saved[1];
        Vector inits = (Vector) saved[2];
        LambdaExp method_list = (LambdaExp) saved[3];
        Object superlist = saved[4];
        Object classNamePair = saved[5];
        oexp.firstChild = method_list;
        int num_supers = Translator.listLength(superlist);
        if (num_supers < 0) {
            tr.error('e', "object superclass specification not a list");
            num_supers = 0;
        }
        Expression[] supers = new Expression[num_supers];
        for (int i = 0; i < num_supers; i++) {
            while (superlist instanceof SyntaxForm) {
                superlist = ((SyntaxForm) superlist).getDatum();
            }
            Pair superpair = (Pair) superlist;
            supers[i] = tr.rewrite_car(superpair, false);
            if ((supers[i] instanceof ReferenceExp) && (decl = Declaration.followAliases(((ReferenceExp) supers[i]).getBinding())) != null) {
                Expression svalue = decl.getValue();
                if (svalue instanceof ClassExp) {
                    ((ClassExp) svalue).setFlag(131072);
                }
            }
            superlist = superpair.getCdr();
        }
        if (classNamePair != null) {
            Expression classNameExp = tr.rewrite_car((Pair) classNamePair, false);
            Object classNameVal = classNameExp.valueIfConstant();
            boolean isString = classNameVal instanceof CharSequence;
            if (isString) {
                String classNameSpecifier = classNameVal.toString();
                if (classNameSpecifier.length() > 0) {
                    oexp.classNameSpecifier = classNameSpecifier;
                }
            }
            Object savedPos = tr.pushPositionOf(classNamePair);
            tr.error('e', "class-name specifier must be a non-empty string literal");
            tr.popPositionOf(savedPos);
        }
        oexp.supers = supers;
        oexp.setTypes(tr);
        int len = inits.size();
        for (int i2 = 0; i2 < len; i2 += 2) {
            Object init = inits.elementAt(i2 + 1);
            if (init != null) {
                rewriteInit(inits.elementAt(i2), oexp, (Pair) init, tr, null);
            }
        }
        tr.push(oexp);
        LambdaExp meth = method_list;
        int init_index = 0;
        SyntaxForm componentsSyntax = null;
        Object obj = components;
        while (obj != LList.Empty) {
            while (obj instanceof SyntaxForm) {
                componentsSyntax = (SyntaxForm) obj;
                obj = componentsSyntax.getDatum();
            }
            Pair pair = (Pair) obj;
            Object savedPos1 = tr.pushPositionOf(pair);
            Object pair_car = pair.getCar();
            SyntaxForm memberSyntax = componentsSyntax;
            while (pair_car instanceof SyntaxForm) {
                SyntaxForm memberSyntax2 = pair_car;
                memberSyntax = memberSyntax2;
                pair_car = memberSyntax.getDatum();
            }
            try {
                obj = pair.getCdr();
                if ((pair_car instanceof Keyword) && (obj instanceof Pair)) {
                    obj = ((Pair) obj).getCdr();
                    tr.popPositionOf(savedPos1);
                } else {
                    Pair pair2 = (Pair) pair_car;
                    Object pair_car2 = pair2.getCar();
                    SyntaxForm memberCarSyntax = memberSyntax;
                    while (pair_car2 instanceof SyntaxForm) {
                        memberCarSyntax = pair_car2;
                        pair_car2 = memberCarSyntax.getDatum();
                    }
                    if ((pair_car2 instanceof String) || (pair_car2 instanceof Symbol) || (pair_car2 instanceof Keyword)) {
                        Object type = null;
                        int nKeywords = 0;
                        Object args = pair_car2 instanceof Keyword ? pair2 : pair2.getCdr();
                        Pair initPair = null;
                        SyntaxForm initSyntax = null;
                        while (args != LList.Empty) {
                            while (args instanceof SyntaxForm) {
                                memberSyntax = args;
                                args = memberSyntax.getDatum();
                            }
                            Pair pair3 = (Pair) args;
                            Object key = pair3.getCar();
                            while (key instanceof SyntaxForm) {
                                key = ((SyntaxForm) key).getDatum();
                            }
                            Object savedPos2 = tr.pushPositionOf(pair3);
                            args = pair3.getCdr();
                            if ((key == coloncolon || (key instanceof Keyword)) && (args instanceof Pair)) {
                                nKeywords++;
                                Pair pair4 = (Pair) args;
                                Object value = pair4.getCar();
                                args = pair4.getCdr();
                                if (key == coloncolon || key == typeKeyword) {
                                    type = value;
                                } else if (key == initKeyword || key == initformKeyword || key == init_formKeyword || key == init_valueKeyword) {
                                    initPair = pair4;
                                    initSyntax = memberSyntax;
                                }
                            } else if (args != LList.Empty || initPair != null) {
                                if ((args instanceof Pair) && nKeywords == 0 && initPair == null && type == null) {
                                    Pair pair5 = (Pair) args;
                                    if (pair5.getCdr() == LList.Empty) {
                                        type = key;
                                        initPair = pair5;
                                        initSyntax = memberSyntax;
                                        args = pair5.getCdr();
                                    }
                                }
                                break;
                            } else {
                                initPair = pair3;
                                initSyntax = memberSyntax;
                            }
                            tr.popPositionOf(savedPos2);
                        }
                        if (initPair != null) {
                            int init_index2 = init_index + 1;
                            try {
                                Object d = inits.elementAt(init_index);
                                if (d instanceof Declaration) {
                                    ((Declaration) d).getFlag(2048L);
                                } else if (d == Boolean.TRUE) {
                                }
                                init_index = init_index2 + 1;
                                if (inits.elementAt(init_index2) == null) {
                                    rewriteInit(d, oexp, initPair, tr, initSyntax);
                                }
                            } catch (Throwable th) {
                                th = th;
                                tr.popPositionOf(savedPos1);
                                throw th;
                            }
                        }
                    } else if (pair_car2 instanceof Pair) {
                        ScopeExp save_scope = tr.currentScope();
                        if (memberSyntax != null) {
                            tr.setCurrentScope(memberSyntax.getScope());
                        }
                        if ("*init*".equals(meth.getName())) {
                            meth.setReturnType(Type.voidType);
                        }
                        Translator.setLine(meth, pair2);
                        LambdaExp saveLambda = tr.curMethodLambda;
                        tr.curMethodLambda = meth;
                        this.lambda.rewrite(meth, ((Pair) pair_car2).getCdr(), pair2.getCdr(), tr, (memberCarSyntax == null || (memberSyntax != null && memberCarSyntax.getScope() == memberSyntax.getScope())) ? null : memberCarSyntax.getScope());
                        tr.curMethodLambda = saveLambda;
                        if (memberSyntax != null) {
                            tr.setCurrentScope(save_scope);
                        }
                        meth = meth.nextSibling;
                    } else {
                        tr.syntaxError("invalid field/method definition");
                    }
                    tr.popPositionOf(savedPos1);
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        if (oexp.initMethod != null) {
            oexp.initMethod.outer = oexp;
        }
        if (oexp.clinitMethod != null) {
            oexp.clinitMethod.outer = oexp;
        }
        tr.pop(oexp);
        oexp.declareParts(tr);
    }

    private static void rewriteInit(Object d, ClassExp oexp, Pair initPair, Translator tr, SyntaxForm initSyntax) {
        boolean isStatic;
        Expression initValue;
        if (d instanceof Declaration) {
            isStatic = ((Declaration) d).getFlag(2048L);
        } else {
            isStatic = d == Boolean.TRUE;
        }
        LambdaExp initMethod = isStatic ? oexp.clinitMethod : oexp.initMethod;
        if (initMethod == null) {
            initMethod = new LambdaExp(new BeginExp());
            initMethod.setClassMethod(true);
            initMethod.setReturnType(Type.voidType);
            if (isStatic) {
                initMethod.setName("$clinit$");
                oexp.clinitMethod = initMethod;
            } else {
                initMethod.setName("$finit$");
                oexp.initMethod = initMethod;
                initMethod.add(null, new Declaration(ThisExp.THIS_NAME));
            }
            initMethod.nextSibling = oexp.firstChild;
            oexp.firstChild = initMethod;
        }
        tr.push(initMethod);
        LambdaExp saveLambda = tr.curMethodLambda;
        tr.curMethodLambda = initMethod;
        Expression initValue2 = tr.rewrite_car(initPair, initSyntax);
        if (d instanceof Declaration) {
            Declaration decl = (Declaration) d;
            SetExp sexp = new SetExp(decl, initValue2);
            sexp.setLocation(decl);
            decl.noteValue(null);
            initValue = sexp;
        } else {
            initValue = Compilation.makeCoercion(initValue2, new QuoteExp(Type.voidType));
        }
        ((BeginExp) initMethod.body).add(initValue);
        tr.curMethodLambda = saveLambda;
        tr.pop(initMethod);
    }

    static boolean matches(Object exp, String tag, Translator tr) {
        String value;
        if (exp instanceof Keyword) {
            value = ((Keyword) exp).getName();
        } else if (exp instanceof FString) {
            value = ((FString) exp).toString();
        } else if (!(exp instanceof Pair)) {
            return false;
        } else {
            Object qvalue = tr.matchQuoted((Pair) exp);
            if (!(qvalue instanceof SimpleSymbol)) {
                return false;
            }
            value = qvalue.toString();
        }
        return tag == null || tag.equals(value);
    }

    static long addAccessFlags(Object value, long previous, long allowed, String kind, Translator tr) {
        long flags = matchAccess(value, tr);
        if (flags == 0) {
            tr.error('e', "unknown access specifier " + value);
        } else if ((((-1) ^ allowed) & flags) != 0) {
            tr.error('e', "invalid " + kind + " access specifier " + value);
        } else if ((previous & flags) != 0) {
            tr.error('w', "duplicate " + kind + " access specifiers " + value);
        }
        return previous | flags;
    }

    static long matchAccess(Object value, Translator tr) {
        while (value instanceof SyntaxForm) {
            value = ((SyntaxForm) value).getDatum();
        }
        if (value instanceof Pair) {
            Pair pair = (Pair) value;
            value = tr.matchQuoted((Pair) value);
            if (value instanceof Pair) {
                return matchAccess2((Pair) value, tr);
            }
        }
        return matchAccess1(value, tr);
    }

    private static long matchAccess2(Pair pair, Translator tr) {
        long icar = matchAccess1(pair.getCar(), tr);
        Object cdr = pair.getCdr();
        if (cdr != LList.Empty && icar != 0) {
            if (cdr instanceof Pair) {
                long icdr = matchAccess2((Pair) cdr, tr);
                if (icdr != 0) {
                    return icar | icdr;
                }
            }
            return 0L;
        }
        return icar;
    }

    private static long matchAccess1(Object value, Translator tr) {
        if (value instanceof Keyword) {
            value = ((Keyword) value).getName();
        } else if (value instanceof FString) {
            value = ((FString) value).toString();
        } else if (value instanceof SimpleSymbol) {
            value = value.toString();
        }
        if ("private".equals(value)) {
            return 16777216L;
        }
        if ("protected".equals(value)) {
            return 33554432L;
        }
        if ("public".equals(value)) {
            return 67108864L;
        }
        if ("package".equals(value)) {
            return 134217728L;
        }
        if ("volatile".equals(value)) {
            return Declaration.VOLATILE_ACCESS;
        }
        if ("transient".equals(value)) {
            return Declaration.TRANSIENT_ACCESS;
        }
        if ("enum".equals(value)) {
            return Declaration.ENUM_ACCESS;
        }
        if ("final".equals(value)) {
            return Declaration.FINAL_ACCESS;
        }
        return 0L;
    }
}
