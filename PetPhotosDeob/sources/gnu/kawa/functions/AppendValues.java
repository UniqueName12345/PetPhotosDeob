package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.Special;
import gnu.expr.Target;
import gnu.lists.Consumable;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;

/* loaded from: classes.dex */
public class AppendValues extends MethodProc implements Inlineable {
    public static final AppendValues appendValues = new AppendValues();

    public AppendValues() {
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyAppendValues");
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) {
        Special endMarker = Special.dfault;
        while (true) {
            Object arg = ctx.getNextArg(endMarker);
            if (arg != endMarker) {
                if (arg instanceof Consumable) {
                    ((Consumable) arg).consume(ctx.consumer);
                } else {
                    ctx.writeValue(arg);
                }
            } else {
                return;
            }
        }
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        if ((target instanceof ConsumerTarget) || (target instanceof IgnoreTarget)) {
            for (Expression expression : args) {
                expression.compileWithPosition(comp, target);
            }
            return;
        }
        ConsumerTarget.compileUsingConsumer(exp, comp, target);
    }

    @Override // gnu.mapping.Procedure
    public Type getReturnType(Expression[] args) {
        return Compilation.typeObject;
    }
}
