package gnu.kawa.functions;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.Access;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Member;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.kawa.reflect.ClassMethods;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.mapping.Environment;
import gnu.mapping.HasNamedParts;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class CompileNamedPart {
    static final ClassType typeHasNamedParts = ClassType.make("gnu.mapping.HasNamedParts");

    public static Expression validateGetNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        ClassType caller;
        Object val;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 2 && (args[1] instanceof QuoteExp) && (exp instanceof GetNamedExp)) {
            Expression context = args[0];
            Declaration decl = null;
            if (context instanceof ReferenceExp) {
                ReferenceExp rexp = (ReferenceExp) context;
                if ("*".equals(rexp.getName())) {
                    return makeGetNamedInstancePartExp(args[1]);
                }
                decl = rexp.getBinding();
            }
            String mname = ((QuoteExp) args[1]).getValue().toString();
            Type type = context.getType();
            if (context == QuoteExp.nullExp) {
            }
            Compilation comp = visitor.getCompilation();
            Language language = comp.getLanguage();
            Type typeval = language.getTypeFor(context, false);
            if (comp == null) {
                caller = null;
            } else {
                caller = comp.curClass != null ? comp.curClass : comp.mainClass;
            }
            GetNamedExp nexp = (GetNamedExp) exp;
            if (typeval != null) {
                if (mname.equals(GetNamedPart.CLASSTYPE_FOR)) {
                    return new QuoteExp(typeval);
                }
                if (typeval instanceof ObjectType) {
                    if (mname.equals("new")) {
                        return nexp.setProcedureKind('N');
                    }
                    if (mname.equals(GetNamedPart.INSTANCEOF_METHOD_NAME)) {
                        return nexp.setProcedureKind(Access.INNERCLASS_CONTEXT);
                    }
                    if (mname.equals(GetNamedPart.CAST_METHOD_NAME)) {
                        return nexp.setProcedureKind(Access.CLASS_CONTEXT);
                    }
                }
            }
            if (typeval instanceof ObjectType) {
                if (mname.length() > 1 && mname.charAt(0) == '.') {
                    return new QuoteExp(new NamedPart(typeval, mname, 'D'));
                }
                if (CompileReflect.checkKnownClass(typeval, comp) >= 0) {
                    PrimProcedure[] methods = ClassMethods.getMethods((ObjectType) typeval, Compilation.mangleName(mname), (char) 0, caller, language);
                    if (methods != null && methods.length > 0) {
                        nexp.methods = methods;
                        return nexp.setProcedureKind('S');
                    }
                    ApplyExp aexp = new ApplyExp(SlotGet.staticField, args);
                    aexp.setLine(exp);
                    return visitor.visitApplyOnly(aexp, required);
                }
                return exp;
            }
            if (typeval != null) {
            }
            if (!type.isSubtype(Compilation.typeClassType) && !type.isSubtype(Type.javalangClassType)) {
                if (type instanceof ObjectType) {
                    ObjectType otype = (ObjectType) type;
                    PrimProcedure[] methods2 = ClassMethods.getMethods(otype, Compilation.mangleName(mname), 'V', caller, language);
                    if (methods2 != null && methods2.length > 0) {
                        nexp.methods = methods2;
                        return nexp.setProcedureKind(Access.METHOD_CONTEXT);
                    } else if (type.isSubtype(typeHasNamedParts)) {
                        if (decl != null && (val = Declaration.followAliases(decl).getConstantValue()) != null) {
                            HasNamedParts value = (HasNamedParts) val;
                            if (value.isConstant(mname)) {
                                return QuoteExp.getInstance(value.get(mname));
                            }
                        }
                        return new ApplyExp(typeHasNamedParts.getDeclaredMethod("get", 1), args[0], QuoteExp.getInstance(mname)).setLine(exp);
                    } else {
                        Member part = SlotGet.lookupMember(otype, mname, caller);
                        if (part != null || (mname.equals(PropertyTypeConstants.PROPERTY_TYPE_LENGTH) && (type instanceof ArrayType))) {
                            ApplyExp aexp2 = new ApplyExp(SlotGet.field, args);
                            aexp2.setLine(exp);
                            return visitor.visitApplyOnly(aexp2, required);
                        }
                    }
                }
                if (comp.warnUnknownMember()) {
                    comp.error('w', "no known slot '" + mname + "' in " + type.getName());
                    return exp;
                }
                return exp;
            }
            return exp;
        }
        return exp;
    }

    public static Expression validateSetNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        ClassType caller;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 3 && (args[1] instanceof QuoteExp)) {
            Expression context = args[0];
            String mname = ((QuoteExp) args[1]).getValue().toString();
            Type type = context.getType();
            Compilation comp = visitor.getCompilation();
            Language language = comp.getLanguage();
            Type typeval = language.getTypeFor(context);
            if (comp == null) {
                caller = null;
            } else {
                caller = comp.curClass != null ? comp.curClass : comp.mainClass;
            }
            if (typeval instanceof ClassType) {
                exp = new ApplyExp(SlotSet.set$Mnstatic$Mnfield$Ex, args);
            } else if (type instanceof ClassType) {
                Member part = SlotSet.lookupMember((ClassType) type, mname, caller);
                if (part != null) {
                    exp = new ApplyExp(SlotSet.set$Mnfield$Ex, args);
                }
            }
            if (exp != exp) {
                exp.setLine(exp);
            }
            exp.setType(Type.voidType);
        }
        return exp;
    }

    public static Expression makeExp(Expression clas, Expression member) {
        String combinedName = combineName(clas, member);
        Environment env = Environment.getCurrent();
        if (combinedName != null) {
            Translator tr = (Translator) Compilation.getCurrent();
            Symbol symbol = Namespace.EmptyNamespace.getSymbol(combinedName);
            Declaration decl = tr.lexical.lookup((Object) symbol, false);
            if (!Declaration.isUnknown(decl)) {
                return new ReferenceExp(decl);
            }
            if (symbol != null && env.isBound(symbol, null)) {
                return new ReferenceExp(combinedName);
            }
        }
        if (clas instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) clas;
            if (rexp.isUnknown()) {
                Object rsym = rexp.getSymbol();
                Symbol sym = rsym instanceof Symbol ? (Symbol) rsym : env.getSymbol(rsym.toString());
                if (env.get(sym, (Object) null) == null) {
                    String name = rexp.getName();
                    try {
                        Class cl = ClassType.getContextClass(name);
                        clas = QuoteExp.getInstance(Type.make(cl));
                    } catch (Throwable th) {
                    }
                }
            }
        }
        Expression[] args = {clas, member};
        GetNamedExp exp = new GetNamedExp(args);
        exp.combinedName = combinedName;
        return exp;
    }

    public static String combineName(Expression part1, Expression part2) {
        String name1;
        Object name2 = part2.valueIfConstant();
        if (!(name2 instanceof SimpleSymbol) || ((!(part1 instanceof ReferenceExp) || (name1 = ((ReferenceExp) part1).getSimpleName()) == null) && (!(part1 instanceof GetNamedExp) || (name1 = ((GetNamedExp) part1).combinedName) == null))) {
            return null;
        }
        return (name1 + ':' + name2).intern();
    }

    public static Expression makeExp(Expression clas, String member) {
        return makeExp(clas, new QuoteExp(member));
    }

    public static Expression makeExp(Type type, String member) {
        return makeExp(new QuoteExp(type), new QuoteExp(member));
    }

    public static Expression validateNamedPart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        SlotGet slotProc;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        NamedPart namedPart = (NamedPart) proc;
        switch (namedPart.kind) {
            case 'D':
                String fname = namedPart.member.toString().substring(1);
                Expression[] xargs = new Expression[2];
                xargs[1] = QuoteExp.getInstance(fname);
                if (args.length > 0) {
                    xargs[0] = Compilation.makeCoercion(args[0], new QuoteExp(namedPart.container));
                    slotProc = SlotGet.field;
                } else {
                    xargs[0] = QuoteExp.getInstance(namedPart.container);
                    slotProc = SlotGet.staticField;
                }
                ApplyExp aexp = new ApplyExp(slotProc, xargs);
                aexp.setLine(exp);
                return visitor.visitApplyOnly(aexp, required);
            default:
                return exp;
        }
    }

    public static Expression validateNamedPartSetter(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        SlotSet slotProc;
        exp.visitArgs(visitor);
        NamedPart get = (NamedPart) ((NamedPartSetter) proc).getGetter();
        if (get.kind == 'D') {
            Expression[] xargs = new Expression[3];
            xargs[1] = QuoteExp.getInstance(get.member.toString().substring(1));
            xargs[2] = exp.getArgs()[0];
            if (exp.getArgCount() == 1) {
                xargs[0] = QuoteExp.getInstance(get.container);
                slotProc = SlotSet.set$Mnstatic$Mnfield$Ex;
            } else if (exp.getArgCount() == 2) {
                xargs[0] = Compilation.makeCoercion(exp.getArgs()[0], new QuoteExp(get.container));
                slotProc = SlotSet.set$Mnfield$Ex;
            } else {
                return exp;
            }
            ApplyExp aexp = new ApplyExp(slotProc, xargs);
            aexp.setLine(exp);
            return visitor.visitApplyOnly(aexp, required);
        }
        return exp;
    }

    public static Expression makeGetNamedInstancePartExp(Expression member) {
        if (member instanceof QuoteExp) {
            Object val = ((QuoteExp) member).getValue();
            if (val instanceof SimpleSymbol) {
                return QuoteExp.getInstance(new GetNamedInstancePart(val.toString()));
            }
        }
        Expression[] args = {new QuoteExp(ClassType.make("gnu.kawa.functions.GetNamedInstancePart")), member};
        return new ApplyExp(Invoke.make, args);
    }

    public static Expression validateGetNamedInstancePart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Expression[] xargs;
        Procedure property;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        GetNamedInstancePart gproc = (GetNamedInstancePart) proc;
        if (gproc.isField) {
            xargs = new Expression[]{args[0], new QuoteExp(gproc.pname)};
            property = SlotGet.field;
        } else {
            int nargs = args.length;
            xargs = new Expression[nargs + 1];
            xargs[0] = args[0];
            xargs[1] = new QuoteExp(gproc.pname);
            System.arraycopy(args, 1, xargs, 2, nargs - 1);
            property = Invoke.invoke;
        }
        return visitor.visitApplyOnly(new ApplyExp(property, xargs), required);
    }

    public static Expression validateSetNamedInstancePart(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        String pname = ((SetNamedInstancePart) proc).pname;
        Expression[] xargs = {args[0], new QuoteExp(pname), args[1]};
        return visitor.visitApplyOnly(new ApplyExp(SlotSet.set$Mnfield$Ex, xargs), required);
    }
}
