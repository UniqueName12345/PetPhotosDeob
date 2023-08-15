package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class InlineCalls extends ExpExpVisitor<Type> {
    private static Class[] inlinerMethodArgTypes;

    public static Expression inlineCalls(Expression exp, Compilation comp) {
        InlineCalls visitor = new InlineCalls(comp);
        return visitor.visit(exp, (Type) null);
    }

    public InlineCalls(Compilation comp) {
        setContext(comp);
    }

    @Override // gnu.expr.ExpVisitor
    public Expression visit(Expression exp, Type required) {
        if (!exp.getFlag(1)) {
            exp.setFlag(1);
            exp = (Expression) super.visit(exp, (Expression) required);
            exp.setFlag(1);
        }
        return checkType(exp, required);
    }

    public Expression checkType(Expression exp, Type required) {
        Expression converted;
        Method amethod;
        boolean incompatible = true;
        Type expType = exp.getType();
        if ((required instanceof ClassType) && ((ClassType) required).isInterface() && expType.isSubtype(Compilation.typeProcedure) && !expType.isSubtype(required)) {
            if ((exp instanceof LambdaExp) && (amethod = ((ClassType) required).checkSingleAbstractMethod()) != null) {
                LambdaExp lexp = (LambdaExp) exp;
                ObjectExp oexp = new ObjectExp();
                oexp.setLocation(exp);
                oexp.supers = new Expression[]{new QuoteExp(required)};
                oexp.setTypes(getCompilation());
                String mname = amethod.getName();
                oexp.addMethod(lexp, mname);
                oexp.addDeclaration(mname, Compilation.typeProcedure);
                oexp.firstChild = lexp;
                oexp.declareParts(this.comp);
                return visit((Expression) oexp, required);
            }
            incompatible = true;
        } else {
            if (expType == Type.toStringType) {
                expType = Type.javalangStringType;
            }
            if (required == null || required.compare(expType) != -3) {
                incompatible = false;
            }
            if (incompatible && (required instanceof TypeValue) && (converted = ((TypeValue) required).convertValue(exp)) != null) {
                return converted;
            }
        }
        if (incompatible) {
            Language language = this.comp.getLanguage();
            this.comp.error('w', "type " + language.formatType(expType) + " is incompatible with required type " + language.formatType(required), exp);
        }
        return exp;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitApplyExp(ApplyExp exp, Type required) {
        Expression func = exp.func;
        if (func instanceof LambdaExp) {
            LambdaExp lambdaExp = (LambdaExp) func;
            Expression inlined = inlineCall((LambdaExp) func, exp.args, false);
            if (inlined != null) {
                return visit(inlined, required);
            }
        }
        Expression func2 = visit(func, (Type) null);
        exp.func = func2;
        return func2.validateApply(exp, this, required, null);
    }

    public final Expression visitApplyOnly(ApplyExp exp, Type required) {
        return exp.func.validateApply(exp, this, required, null);
    }

    public static Integer checkIntValue(Expression exp) {
        if (exp instanceof QuoteExp) {
            QuoteExp qarg = (QuoteExp) exp;
            Object value = qarg.getValue();
            if (!qarg.isExplicitlyTyped() && (value instanceof IntNum)) {
                IntNum ivalue = (IntNum) value;
                if (ivalue.inIntRange()) {
                    return Integer.valueOf(ivalue.intValue());
                }
            }
        }
        return null;
    }

    public static Long checkLongValue(Expression exp) {
        if (exp instanceof QuoteExp) {
            QuoteExp qarg = (QuoteExp) exp;
            Object value = qarg.getValue();
            if (!qarg.isExplicitlyTyped() && (value instanceof IntNum)) {
                IntNum ivalue = (IntNum) value;
                if (ivalue.inLongRange()) {
                    return Long.valueOf(ivalue.longValue());
                }
            }
        }
        return null;
    }

    public QuoteExp fixIntValue(Expression exp) {
        Integer ival = checkIntValue(exp);
        if (ival != null) {
            return new QuoteExp(ival, this.comp.getLanguage().getTypeFor(Integer.TYPE));
        }
        return null;
    }

    public QuoteExp fixLongValue(Expression exp) {
        Long ival = checkLongValue(exp);
        if (ival != null) {
            return new QuoteExp(ival, this.comp.getLanguage().getTypeFor(Long.TYPE));
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitQuoteExp(QuoteExp exp, Type required) {
        Object value;
        QuoteExp ret;
        if (exp.getRawType() == null && !exp.isSharedConstant() && (value = exp.getValue()) != null) {
            Language language = this.comp.getLanguage();
            Type vtype = language.getTypeFor(value.getClass());
            if (vtype == Type.toStringType) {
                vtype = Type.javalangStringType;
            }
            exp.type = vtype;
            if (required instanceof PrimType) {
                char sig1 = required.getSignature().charAt(0);
                if (sig1 == 'I') {
                    ret = fixIntValue(exp);
                } else {
                    ret = sig1 == 'J' ? fixLongValue(exp) : null;
                }
                if (ret != null) {
                    return ret;
                }
                return exp;
            }
            return exp;
        }
        return exp;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitReferenceExp(ReferenceExp exp, Type required) {
        Declaration decl = exp.getBinding();
        if (decl != null && decl.field == null && !decl.getCanWrite()) {
            Expression dval = decl.getValue();
            if ((dval instanceof QuoteExp) && dval != QuoteExp.undefined_exp) {
                return visitQuoteExp((QuoteExp) dval, required);
            }
            if ((dval instanceof ReferenceExp) && !decl.isAlias()) {
                ReferenceExp rval = (ReferenceExp) dval;
                Declaration rdecl = rval.getBinding();
                Type dtype = decl.getType();
                if (rdecl != null && !rdecl.getCanWrite() && ((dtype == null || dtype == Type.pointer_type || dtype == rdecl.getType()) && !rval.getDontDereference())) {
                    return visitReferenceExp(rval, required);
                }
            }
            if (!exp.isProcedureName() && (decl.flags & 1048704) == 1048704) {
                this.comp.error('e', "unimplemented: reference to method " + decl.getName() + " as variable");
                this.comp.error('e', decl, "here is the definition of ", "");
            }
        }
        return (Expression) super.visitReferenceExp(exp, (ReferenceExp) required);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitIfExp(IfExp exp, Type required) {
        Declaration decl;
        Expression test = (Expression) exp.test.visit(this, null);
        if ((test instanceof ReferenceExp) && (decl = ((ReferenceExp) test).getBinding()) != null) {
            Expression value = decl.getValue();
            if ((value instanceof QuoteExp) && value != QuoteExp.undefined_exp) {
                test = value;
            }
        }
        exp.test = test;
        if (this.exitValue == null) {
            exp.then_clause = visit(exp.then_clause, required);
        }
        if (this.exitValue == null && exp.else_clause != null) {
            exp.else_clause = visit(exp.else_clause, required);
        }
        if (test instanceof QuoteExp) {
            return exp.select(this.comp.getLanguage().isTrue(((QuoteExp) test).getValue()));
        }
        if (test.getType().isVoid()) {
            boolean truth = this.comp.getLanguage().isTrue(Values.empty);
            this.comp.error('w', "void-valued condition is always " + truth);
            return new BeginExp(test, exp.select(truth));
        }
        return exp;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitBeginExp(BeginExp exp, Type required) {
        int last = exp.length - 1;
        int i = 0;
        while (i <= last) {
            exp.exps[i] = visit(exp.exps[i], i < last ? null : required);
            i++;
        }
        return exp;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitScopeExp(ScopeExp exp, Type required) {
        exp.visitChildren(this, null);
        visitDeclarationTypes(exp);
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.type == null) {
                Expression val = decl.getValue();
                decl.type = Type.objectType;
                decl.setType((val == null || val == QuoteExp.undefined_exp) ? Type.objectType : val.getType());
            }
        }
        return exp;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitLetExp(LetExp exp, Type required) {
        ReferenceExp ref;
        Declaration d;
        Declaration decl = exp.firstDecl();
        int n = exp.inits.length;
        int i = 0;
        while (i < n) {
            Expression init0 = exp.inits[i];
            boolean typeSpecified = decl.getFlag(8192L);
            Type dtype = (!typeSpecified || init0 == QuoteExp.undefined_exp) ? null : decl.getType();
            Expression init = visit(init0, dtype);
            exp.inits[i] = init;
            Expression dvalue = decl.value;
            if (dvalue == init0) {
                decl.value = init;
                if (!typeSpecified) {
                    decl.setType(init.getType());
                }
            }
            i++;
            decl = decl.nextDecl();
        }
        if (this.exitValue == null) {
            exp.body = visit(exp.body, required);
        }
        if ((exp.body instanceof ReferenceExp) && (d = (ref = (ReferenceExp) exp.body).getBinding()) != null && d.context == exp && !ref.getDontDereference() && n == 1) {
            Expression init2 = exp.inits[0];
            Expression texp = d.getTypeExp();
            if (texp != QuoteExp.classObjectExp) {
                return visitApplyOnly(Compilation.makeCoercion(init2, texp), null);
            }
            return init2;
        }
        return exp;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitLambdaExp(LambdaExp exp, Type required) {
        Declaration firstDecl = exp.firstDecl();
        if (firstDecl != null && firstDecl.isThisParameter() && !exp.isClassMethod() && firstDecl.type == null) {
            firstDecl.setType(this.comp.mainClass);
        }
        return visitScopeExp((ScopeExp) exp, required);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitTryExp(TryExp exp, Type required) {
        return (exp.getCatchClauses() == null && exp.getFinallyClause() == null) ? visit(exp.try_clause, required) : (Expression) super.visitTryExp(exp, (TryExp) required);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitSetExpValue(Expression new_value, Type required, Declaration decl) {
        return visit(new_value, (decl == null || decl.isAlias()) ? null : decl.type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitSetExp(SetExp exp, Type required) {
        Declaration decl = exp.getBinding();
        super.visitSetExp(exp, (SetExp) required);
        if (!exp.isDefining() && decl != null && (decl.flags & 1048704) == 1048704) {
            this.comp.error('e', "can't assign to method " + decl.getName(), exp);
        }
        if (decl != null && decl.getFlag(8192L) && CompileReflect.checkKnownClass(decl.getType(), this.comp) < 0) {
            decl.setType(Type.errorType);
        }
        return exp;
    }

    private static synchronized Class[] getInlinerMethodArgTypes() throws Exception {
        Class[] t;
        synchronized (InlineCalls.class) {
            t = inlinerMethodArgTypes;
            if (t == null) {
                t = new Class[]{Class.forName("gnu.expr.ApplyExp"), Class.forName("gnu.expr.InlineCalls"), Class.forName("gnu.bytecode.Type"), Class.forName("gnu.mapping.Procedure")};
                inlinerMethodArgTypes = t;
            }
        }
        return t;
    }

    public Expression maybeInline(ApplyExp exp, Type required, Procedure proc) {
        try {
        } catch (Throwable th) {
            ex = th;
            if (ex instanceof InvocationTargetException) {
                ex = ((InvocationTargetException) ex).getTargetException();
            }
            this.messages.error('e', "caught exception in inliner for " + proc + " - " + ex, ex);
        }
        synchronized (proc) {
            Object inliner = proc.getProperty(Procedure.validateApplyKey, null);
            if (inliner instanceof String) {
                String inliners = (String) inliner;
                int colon = inliners.indexOf(58);
                java.lang.reflect.Method method = null;
                if (colon > 0) {
                    String cname = inliners.substring(0, colon);
                    String mname = inliners.substring(colon + 1);
                    Class clas = Class.forName(cname, true, proc.getClass().getClassLoader());
                    method = clas.getDeclaredMethod(mname, getInlinerMethodArgTypes());
                }
                if (method == null) {
                    error('e', "inliner property string for " + proc + " is not of the form CLASS:METHOD");
                    return null;
                }
                inliner = method;
            }
            if (inliner != null) {
                Object[] vargs = {exp, this, required, proc};
                if (inliner instanceof Procedure) {
                    return (Expression) ((Procedure) inliner).applyN(vargs);
                }
                if (inliner instanceof java.lang.reflect.Method) {
                    return (Expression) ((java.lang.reflect.Method) inliner).invoke(null, vargs);
                }
            }
            return null;
        }
    }

    public static Expression inlineCall(LambdaExp lexp, Expression[] args, boolean makeCopy) {
        IdentityHashTable mapper;
        Expression[] cargs;
        if (lexp.keywords != null || (lexp.nameDecl != null && !makeCopy)) {
            return null;
        }
        boolean varArgs = lexp.max_args < 0;
        if ((lexp.min_args == lexp.max_args && lexp.min_args == args.length) || (varArgs && lexp.min_args == 0)) {
            Declaration prev = null;
            int i = 0;
            if (makeCopy) {
                mapper = new IdentityHashTable();
                cargs = Expression.deepCopy(args, mapper);
                if (cargs == null && args != null) {
                    return null;
                }
            } else {
                mapper = null;
                cargs = args;
            }
            if (varArgs) {
                Expression[] xargs = new Expression[args.length + 1];
                xargs[0] = QuoteExp.getInstance(lexp.firstDecl().type);
                System.arraycopy(args, 0, xargs, 1, args.length);
                cargs = new Expression[]{new ApplyExp(Invoke.make, xargs)};
            }
            LetExp let = new LetExp(cargs);
            Declaration param = lexp.firstDecl();
            while (param != null) {
                Declaration next = param.nextDecl();
                if (makeCopy) {
                    Declaration ldecl = let.addDeclaration(param.symbol, param.type);
                    if (param.typeExp != null) {
                        ldecl.typeExp = Expression.deepCopy(param.typeExp);
                        if (ldecl.typeExp == null) {
                            return null;
                        }
                    }
                    mapper.put(param, ldecl);
                } else {
                    lexp.remove(prev, param);
                    let.add(prev, param);
                }
                if (!varArgs && !param.getCanWrite()) {
                    param.setValue(cargs[i]);
                }
                prev = param;
                param = next;
                i++;
            }
            Expression body = lexp.body;
            if (makeCopy && (body = Expression.deepCopy(body, mapper)) == null && lexp.body != null) {
                return null;
            }
            let.body = body;
            return let;
        }
        return null;
    }
}
