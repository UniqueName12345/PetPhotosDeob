package kawa.lang;

import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;

/* loaded from: classes.dex */
public class Continuation extends MethodProc {
    static int counter;
    int id;
    public boolean invoked;

    public Continuation(CallContext ctx) {
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) {
        if (this.invoked) {
            throw new GenericError("implementation restriction: continuation can only be used once");
        }
        throw new CalledContinuation(ctx.values, this, ctx);
    }

    public static void handleException$X(Throwable ex, Continuation cont, CallContext ctx) throws Throwable {
        if (ex instanceof CalledContinuation) {
            CalledContinuation cex = (CalledContinuation) ex;
            if (cex.continuation == cont) {
                cont.invoked = true;
                Object[] values = cex.values;
                for (Object obj : values) {
                    ctx.consumer.writeObject(obj);
                }
                return;
            }
        }
        throw ex;
    }

    public static Object handleException(Throwable ex, Continuation cont) throws Throwable {
        if (ex instanceof CalledContinuation) {
            CalledContinuation cex = (CalledContinuation) ex;
            if (cex.continuation == cont) {
                cont.invoked = true;
                return Values.make(cex.values);
            }
        }
        throw ex;
    }

    @Override // gnu.mapping.Procedure
    public final String toString() {
        return "#<continuation " + this.id + (this.invoked ? " (invoked)>" : ">");
    }
}
