package gnu.kawa.functions;

import androidx.fragment.app.FragmentTransaction;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import kawa.lang.Continuation;

/* loaded from: classes.dex */
public class CallCC extends MethodProc implements Inlineable {
    public static final CallCC callcc = new CallCC();

    CallCC() {
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyCallCC");
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    @Override // gnu.mapping.Procedure
    public int match1(Object proc, CallContext ctx) {
        return !(proc instanceof Procedure) ? MethodProc.NO_MATCH_BAD_TYPE : super.match1(proc, ctx);
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        Procedure proc = (Procedure) ctx.value1;
        Continuation cont = new Continuation(ctx);
        proc.check1(cont, ctx);
        Procedure proc2 = ctx.proc;
        ctx.proc = null;
        try {
            proc2.apply(ctx);
            ctx.runUntilDone();
            cont.invoked = true;
        } catch (Throwable ex) {
            Continuation.handleException$X(ex, cont, ctx);
        }
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        CompileMisc.compileCallCC(exp, comp, target, this);
    }

    @Override // gnu.mapping.Procedure
    public Type getReturnType(Expression[] args) {
        return Type.pointer_type;
    }
}
