package kawa.lang;

import gnu.mapping.CallContext;

/* loaded from: classes.dex */
public class CalledContinuation extends RuntimeException {
    public Continuation continuation;
    public CallContext ctx;
    public Object[] values;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CalledContinuation(Object[] values, Continuation continuation, CallContext ctx) {
        super("call/cc called");
        this.values = values;
        this.continuation = continuation;
        this.ctx = ctx;
    }
}
