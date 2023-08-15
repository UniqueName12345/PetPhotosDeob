package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.QuoteExp;
import gnu.kawa.reflect.Invoke;

/* compiled from: CompilationHelpers.java */
/* loaded from: classes.dex */
class SetListExp extends ApplyExp {
    public SetListExp(Expression func, Expression[] args) {
        super(func, args);
    }

    @Override // gnu.expr.Expression
    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 2) {
            Expression[] xargs = {getArgs()[0], QuoteExp.getInstance("set"), Compilation.makeCoercion(args[0], Type.intType), args[1]};
            Expression set = visitor.visitApplyOnly(new ApplyExp(Invoke.invoke, xargs), required);
            return Compilation.makeCoercion(set, Type.voidType);
        }
        return exp;
    }
}
