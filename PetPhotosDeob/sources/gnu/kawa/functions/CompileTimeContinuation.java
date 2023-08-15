package gnu.kawa.functions;

import gnu.bytecode.ExitableBlock;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.ProcedureN;

/* compiled from: CallCC.java */
/* loaded from: classes.dex */
class CompileTimeContinuation extends ProcedureN implements Inlineable {
    Target blockTarget;
    ExitableBlock exitableBlock;

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) throws Throwable {
        throw new Error("internal error");
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        comp.getCode();
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        boolean noStack = (this.blockTarget instanceof IgnoreTarget) || (this.blockTarget instanceof ConsumerTarget);
        if (!noStack) {
            target.getType();
        }
        if (noStack || nargs == 1) {
            for (Expression expression : args) {
                expression.compileWithPosition(comp, this.blockTarget);
            }
        } else {
            AppendValues app = AppendValues.appendValues;
            app.compile(new ApplyExp(app, args), comp, this.blockTarget);
        }
        this.exitableBlock.exit();
    }

    @Override // gnu.mapping.Procedure
    public Type getReturnType(Expression[] args) {
        return Type.neverReturnsType;
    }
}
