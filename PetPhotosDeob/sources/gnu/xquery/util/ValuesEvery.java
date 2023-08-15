package gnu.xquery.util;

import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Values;

/* loaded from: classes.dex */
public class ValuesEvery extends MethodProc {
    public static final ValuesEvery every = new ValuesEvery(true);
    public static final ValuesEvery some = new ValuesEvery(false);
    boolean matchAll;

    public ValuesEvery(boolean matchAll) {
        this.matchAll = matchAll;
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return 8194;
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        Procedure proc = (Procedure) ctx.getNextArg();
        Object val = ctx.getNextArg();
        boolean ok = this.matchAll;
        Procedure.checkArgCount(proc, 1);
        if (val instanceof Values) {
            int ipos = 0;
            Values values = (Values) val;
            do {
                ipos = values.nextPos(ipos);
                if (ipos == 0) {
                    break;
                }
                proc.check1(values.getPosPrevious(ipos), ctx);
                ok = BooleanValue.booleanValue(ctx.runUntilValue());
            } while (ok == this.matchAll);
        } else {
            proc.check1(val, ctx);
            ok = BooleanValue.booleanValue(ctx.runUntilValue());
        }
        ctx.consumer.writeBoolean(ok);
    }
}
