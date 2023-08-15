package gnu.kawa.functions;

import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;
import gnu.math.IntNum;

/* loaded from: classes.dex */
public class CountValues extends Procedure1 {
    public static final CountValues countValues = new CountValues();

    public static int countValues(Object arg) {
        if (arg instanceof Values) {
            return ((Values) arg).size();
        }
        return 1;
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public Object apply1(Object arg) {
        return IntNum.make(countValues(arg));
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) {
        Consumer consumer = ctx.consumer;
        Object arg = ctx.getNextArg();
        ctx.lastArg();
        consumer.writeInt(countValues(arg));
    }
}
