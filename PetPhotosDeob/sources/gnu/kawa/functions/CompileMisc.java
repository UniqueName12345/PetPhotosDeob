package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.ExitableBlock;
import gnu.bytecode.Method;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.IgnoreTarget;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.expr.TryExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.lists.LList;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.WrongArguments;
import kawa.standard.Scheme;

/* loaded from: classes.dex */
public class CompileMisc implements Inlineable {
    static final int CONVERT = 2;
    static final int NOT = 3;
    static Method coerceMethod;
    public static final ClassType typeContinuation = ClassType.make("kawa.lang.Continuation");
    static ClassType typeType;
    int code;
    Procedure proc;

    public CompileMisc(Procedure proc, int code) {
        this.proc = proc;
        this.code = code;
    }

    public static CompileMisc forConvert(Object proc) {
        return new CompileMisc((Procedure) proc, 2);
    }

    public static CompileMisc forNot(Object proc) {
        return new CompileMisc((Procedure) proc, 3);
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        switch (this.code) {
            case 2:
                compileConvert((Convert) this.proc, exp, comp, target);
                return;
            case 3:
                compileNot((Not) this.proc, exp, comp, target);
                return;
            default:
                throw new Error();
        }
    }

    public static Expression validateApplyConstantFunction0(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        int nargs = exp.getArgCount();
        if (nargs == 0 || visitor == null) {
            return ((ConstantFunction0) proc).constant;
        }
        String message = WrongArguments.checkArgCount(proc, nargs);
        return visitor.noteError(message);
    }

    public static Expression validateApplyConvert(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Compilation comp = visitor.getCompilation();
        Language language = comp.getLanguage();
        Expression[] args = exp.getArgs();
        if (args.length == 2) {
            args[0] = visitor.visit(args[0], (Type) null);
            Type type = language.getTypeFor(args[0]);
            if (type instanceof Type) {
                args[0] = new QuoteExp(type);
                args[1] = visitor.visit(args[1], type);
                CompileReflect.checkKnownClass(type, comp);
                exp.setType(type);
                return exp;
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public static Expression validateApplyNot(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        exp.setType(visitor.getCompilation().getLanguage().getTypeFor(Boolean.TYPE));
        return exp.inlineIfConstant(proc, visitor);
    }

    public static Expression validateApplyFormat(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Type retType = Type.objectType;
        Expression[] args = exp.getArgs();
        if (args.length > 0) {
            ClassType typeFormat = ClassType.make("gnu.kawa.functions.Format");
            Object f = args[0].valueIfConstant();
            Type ftype = args[0].getType();
            if (f == Boolean.FALSE || ftype.isSubtype(LangObjType.stringType)) {
                int skip = f == Boolean.FALSE ? 1 : 0;
                Expression[] xargs = new Expression[(args.length + 1) - skip];
                xargs[0] = new QuoteExp(0, Type.intType);
                System.arraycopy(args, skip, xargs, 1, xargs.length - 1);
                ApplyExp ae = new ApplyExp(typeFormat.getDeclaredMethod("formatToString", 2), xargs);
                ae.setType(Type.javalangStringType);
                return ae;
            } else if (f == Boolean.TRUE || ftype.isSubtype(ClassType.make("java.io.Writer"))) {
                if (f == Boolean.TRUE) {
                    Expression[] xargs2 = new Expression[args.length];
                    xargs2[0] = QuoteExp.nullExp;
                    System.arraycopy(args, 1, xargs2, 1, args.length - 1);
                    args = xargs2;
                }
                ApplyExp ae2 = new ApplyExp(typeFormat.getDeclaredMethod("formatToWriter", 3), args);
                ae2.setType(Type.voidType);
                return ae2;
            } else if (ftype.isSubtype(ClassType.make("java.io.OutputStream"))) {
                retType = Type.voidType;
            }
        }
        exp.setType(retType);
        return null;
    }

    public static Expression validateApplyAppendValues(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            return args[0];
        }
        if (args.length == 0) {
            return QuoteExp.voidExp;
        }
        Expression folded = exp.inlineIfConstant(proc, visitor);
        return folded == exp ? exp : folded;
    }

    public static Expression validateApplyMakeProcedure(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        int alen = args.length;
        LambdaExp method = null;
        int countMethods = 0;
        String name = null;
        int i = 0;
        while (i < alen) {
            Expression arg = args[i];
            if (arg instanceof QuoteExp) {
                Object key = ((QuoteExp) arg).getValue();
                if (key instanceof Keyword) {
                    String keyword = ((Keyword) key).getName();
                    i++;
                    Expression next = args[i];
                    if (keyword == "name") {
                        if (next instanceof QuoteExp) {
                            name = ((QuoteExp) next).getValue().toString();
                        }
                    } else if (keyword == "method") {
                        countMethods++;
                        method = next;
                    }
                    i++;
                }
            }
            countMethods++;
            method = arg;
            i++;
        }
        if (countMethods == 1 && (method instanceof LambdaExp)) {
            LambdaExp lexp = method;
            int i2 = 0;
            while (i2 < alen) {
                Expression arg2 = args[i2];
                if (arg2 instanceof QuoteExp) {
                    Object key2 = ((QuoteExp) arg2).getValue();
                    if (key2 instanceof Keyword) {
                        String keyword2 = ((Keyword) key2).getName();
                        i2++;
                        Expression next2 = args[i2];
                        if (keyword2 == "name") {
                            lexp.setName(name);
                        } else if (keyword2 != "method") {
                            lexp.setProperty(Namespace.EmptyNamespace.getSymbol(keyword2), next2);
                        }
                    }
                }
                i2++;
            }
            return method;
        }
        return exp;
    }

    public static Expression validateApplyValuesMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        LambdaExp lexp = ValuesMap.canInline(exp, (ValuesMap) proc);
        if (lexp != null) {
            lexp.setInlineOnly(true);
            lexp.returnContinuation = exp;
            lexp.inlineHome = visitor.getCurrentLambda();
        }
        return exp;
    }

    public static void compileConvert(Convert proc, ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        if (args.length != 2) {
            throw new Error("wrong number of arguments to " + proc.getName());
        }
        CodeAttr code = comp.getCode();
        Type type = Scheme.getTypeValue(args[0]);
        if (type != null) {
            args[1].compile(comp, Target.pushValue(type));
            if (code.reachableHere()) {
                target.compileFromStack(comp, type);
                return;
            }
            return;
        }
        if (typeType == null) {
            typeType = ClassType.make("gnu.bytecode.Type");
        }
        if (coerceMethod == null) {
            coerceMethod = typeType.addMethod("coerceFromObject", Compilation.apply1args, Type.pointer_type, 1);
        }
        args[0].compile(comp, LangObjType.typeClassType);
        args[1].compile(comp, Target.pushObject);
        code.emitInvokeVirtual(coerceMethod);
        target.compileFromStack(comp, Type.pointer_type);
    }

    public void compileNot(Not proc, ApplyExp exp, Compilation comp, Target target) {
        Expression arg = exp.getArgs()[0];
        Language language = proc.language;
        if (target instanceof ConditionalTarget) {
            ConditionalTarget ctarget = (ConditionalTarget) target;
            ConditionalTarget sub_target = new ConditionalTarget(ctarget.ifFalse, ctarget.ifTrue, language);
            sub_target.trueBranchComesFirst = !ctarget.trueBranchComesFirst;
            arg.compile(comp, sub_target);
            return;
        }
        CodeAttr code = comp.getCode();
        Type type = target.getType();
        if ((target instanceof StackTarget) && type.getSignature().charAt(0) == 'Z') {
            arg.compile(comp, target);
            code.emitNot(target.getType());
            return;
        }
        QuoteExp trueExp = QuoteExp.getInstance(language.booleanObject(true));
        QuoteExp falseExp = QuoteExp.getInstance(language.booleanObject(false));
        IfExp.compile(arg, falseExp, trueExp, comp, target);
    }

    public static Expression validateApplyCallCC(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        LambdaExp lexp = canInlineCallCC(exp);
        if (lexp != null) {
            lexp.setInlineOnly(true);
            lexp.returnContinuation = exp;
            lexp.inlineHome = visitor.getCurrentLambda();
            Declaration contDecl = lexp.firstDecl();
            if (!contDecl.getFlag(8192L)) {
                contDecl.setType(typeContinuation);
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public static void compileCallCC(ApplyExp exp, Compilation comp, Target target, Procedure proc) {
        LambdaExp lambda = canInlineCallCC(exp);
        if (lambda == null) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        CodeAttr code = comp.getCode();
        Declaration param = lambda.firstDecl();
        if (param.isSimple() && !param.getCanRead() && !param.getCanWrite()) {
            param.setCanCall(false);
            CompileTimeContinuation contProxy = new CompileTimeContinuation();
            Type rtype = target instanceof StackTarget ? target.getType() : null;
            boolean runFinallyBlocks = ExitThroughFinallyChecker.check(param, lambda.body);
            ExitableBlock bl = code.startExitableBlock(rtype, runFinallyBlocks);
            contProxy.exitableBlock = bl;
            contProxy.blockTarget = target;
            param.setValue(new QuoteExp(contProxy));
            new ApplyExp((Expression) lambda, QuoteExp.nullExp).compile(comp, target);
            code.endExitableBlock();
            return;
        }
        Scope sc = code.pushScope();
        Variable contVar = sc.addVariable(code, typeContinuation, null);
        Declaration contDecl = new Declaration(contVar);
        code.emitNew(typeContinuation);
        code.emitDup(typeContinuation);
        comp.loadCallContext();
        code.emitInvokeSpecial(typeContinuation.getDeclaredMethod("<init>", 1));
        code.emitStore(contVar);
        code.emitTryStart(false, ((target instanceof IgnoreTarget) || (target instanceof ConsumerTarget)) ? null : Type.objectType);
        ApplyExp app = new ApplyExp((Expression) lambda, new ReferenceExp(contDecl));
        app.compile(comp, target);
        if (code.reachableHere()) {
            code.emitLoad(contVar);
            code.emitPushInt(1);
            code.emitPutField(typeContinuation.getField("invoked"));
        }
        code.emitTryEnd();
        code.emitCatchStart(null);
        code.emitLoad(contVar);
        if (target instanceof ConsumerTarget) {
            comp.loadCallContext();
            Method handleMethod = typeContinuation.getDeclaredMethod("handleException$X", 3);
            code.emitInvokeStatic(handleMethod);
        } else {
            Method handleMethod2 = typeContinuation.getDeclaredMethod("handleException", 2);
            code.emitInvokeStatic(handleMethod2);
            target.compileFromStack(comp, Type.objectType);
        }
        code.emitCatchEnd();
        code.emitTryCatchEnd();
        code.popScope();
    }

    private static LambdaExp canInlineCallCC(ApplyExp exp) {
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            Expression arg0 = args[0];
            if (arg0 instanceof LambdaExp) {
                LambdaExp lexp = (LambdaExp) arg0;
                if (lexp.min_args == 1 && lexp.max_args == 1 && !lexp.firstDecl().getCanWrite()) {
                    return lexp;
                }
            }
        }
        return null;
    }

    /* loaded from: classes.dex */
    static class ExitThroughFinallyChecker extends ExpVisitor<Expression, TryExp> {
        Declaration decl;

        ExitThroughFinallyChecker() {
        }

        public static boolean check(Declaration decl, Expression body) {
            ExitThroughFinallyChecker visitor = new ExitThroughFinallyChecker();
            visitor.decl = decl;
            visitor.visit(body, null);
            return visitor.exitValue != null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // gnu.expr.ExpVisitor
        public Expression defaultValue(Expression r, TryExp d) {
            return r;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // gnu.expr.ExpVisitor
        public Expression visitReferenceExp(ReferenceExp exp, TryExp currentTry) {
            if (this.decl == exp.getBinding() && currentTry != null) {
                this.exitValue = Boolean.TRUE;
            }
            return exp;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // gnu.expr.ExpVisitor
        public Expression visitTryExp(TryExp exp, TryExp currentTry) {
            if (exp.getFinallyClause() != null) {
                currentTry = exp;
            }
            visitExpression(exp, currentTry);
            return exp;
        }
    }

    public static Expression validateApplyMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure xproc) {
        Map mproc = (Map) xproc;
        boolean collect = mproc.collect;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        if (nargs >= 2) {
            int nargs2 = nargs - 1;
            Expression proc = args[0];
            boolean procSafeForMultipleEvaluation = !proc.side_effects();
            Expression[] inits1 = {proc};
            LetExp let1 = new LetExp(inits1);
            Declaration procDecl = let1.addDeclaration("%proc", Compilation.typeProcedure);
            procDecl.noteValue(args[0]);
            Expression[] inits2 = new Expression[1];
            LetExp let2 = new LetExp(inits2);
            let1.setBody(let2);
            LambdaExp lexp = new LambdaExp(collect ? nargs2 + 1 : nargs2);
            inits2[0] = lexp;
            Declaration loopDecl = let2.addDeclaration("%loop");
            loopDecl.noteValue(lexp);
            Expression[] inits3 = new Expression[nargs2];
            LetExp let3 = new LetExp(inits3);
            Declaration[] largs = new Declaration[nargs2];
            Declaration[] pargs = new Declaration[nargs2];
            for (int i = 0; i < nargs2; i++) {
                String argName = "arg" + i;
                largs[i] = lexp.addDeclaration(argName);
                pargs[i] = let3.addDeclaration(argName, Compilation.typePair);
                inits3[i] = new ReferenceExp(largs[i]);
                pargs[i].noteValue(inits3[i]);
            }
            Declaration resultDecl = collect ? lexp.addDeclaration("result") : null;
            Expression[] doArgs = new Expression[nargs2 + 1];
            Expression[] recArgs = new Expression[collect ? nargs2 + 1 : nargs2];
            for (int i2 = 0; i2 < nargs2; i2++) {
                doArgs[i2 + 1] = visitor.visitApplyOnly(SlotGet.makeGetField(new ReferenceExp(pargs[i2]), "car"), null);
                recArgs[i2] = visitor.visitApplyOnly(SlotGet.makeGetField(new ReferenceExp(pargs[i2]), "cdr"), null);
            }
            if (!procSafeForMultipleEvaluation) {
                proc = new ReferenceExp(procDecl);
            }
            doArgs[0] = proc;
            Expression doit = visitor.visitApplyOnly(new ApplyExp((Expression) new ReferenceExp(mproc.applyFieldDecl), doArgs), null);
            if (collect) {
                Expression[] consArgs = {doit, new ReferenceExp(resultDecl)};
                recArgs[nargs2] = Invoke.makeInvokeStatic(Compilation.typePair, "make", consArgs);
            }
            Expression rec = visitor.visitApplyOnly(new ApplyExp((Expression) new ReferenceExp(loopDecl), recArgs), null);
            if (!collect) {
                rec = new BeginExp(doit, rec);
            }
            lexp.body = rec;
            let3.setBody(lexp.body);
            lexp.body = let3;
            Expression[] initArgs = new Expression[collect ? nargs2 + 1 : nargs2];
            QuoteExp empty = new QuoteExp(LList.Empty);
            int i3 = nargs2;
            while (true) {
                i3--;
                if (i3 < 0) {
                    break;
                }
                Expression[] compArgs = {new ReferenceExp(largs[i3]), empty};
                Expression result = collect ? new ReferenceExp(resultDecl) : QuoteExp.voidExp;
                lexp.body = new IfExp(visitor.visitApplyOnly(new ApplyExp(mproc.isEq, compArgs), null), result, lexp.body);
                initArgs[i3] = args[i3 + 1];
            }
            if (collect) {
                initArgs[nargs2] = empty;
            }
            Expression body = visitor.visitApplyOnly(new ApplyExp((Expression) new ReferenceExp(loopDecl), initArgs), null);
            if (collect) {
                Expression[] reverseArgs = {body};
                body = Invoke.makeInvokeStatic(Compilation.scmListType, "reverseInPlace", reverseArgs);
            }
            let2.setBody(body);
            return procSafeForMultipleEvaluation ? let2 : let1;
        }
        return exp;
    }
}
