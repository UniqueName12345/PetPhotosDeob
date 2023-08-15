package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import java.util.Iterator;

/* loaded from: classes.dex */
public class IteratorItems extends MethodProc {
    public static IteratorItems iteratorItems = new IteratorItems();

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) {
        Consumer out = ctx.consumer;
        Object arg = ctx.getNextArg();
        ctx.lastArg();
        Iterator iter = (Iterator) arg;
        while (iter.hasNext()) {
            Object val = iter.next();
            Values.writeValues(val, out);
        }
    }
}
