package kawa.standard;

import gnu.math.Quantity;
import gnu.math.Unit;
import kawa.lang.GenericError;

/* loaded from: classes.dex */
public class sleep {
    public static void sleep(Quantity q) {
        Unit u = q.unit();
        if (u == Unit.Empty || u.dimensions() == Unit.second.dimensions()) {
            double seconds = q.doubleValue();
            long millis = (long) (1000.0d * seconds);
            int nanos = (int) ((1.0E9d * seconds) - (millis * 1000000.0d));
            try {
                Thread.sleep(millis, nanos);
                return;
            } catch (InterruptedException e) {
                throw new GenericError("sleep was interrupted");
            }
        }
        throw new GenericError("bad unit for sleep");
    }
}
