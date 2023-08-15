package gnu.xquery.util;

import gnu.mapping.CallContext;
import gnu.mapping.Values;

/* loaded from: classes.dex */
public class DistinctValues {
    public static void distinctValues$X(Object values, NamedCollator coll, CallContext ctx) {
        DistinctValuesConsumer out = new DistinctValuesConsumer(coll, ctx.consumer);
        Values.writeValues(values, out);
    }
}
