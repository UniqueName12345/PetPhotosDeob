package gnu.xquery.util;

import gnu.kawa.functions.AddOp;
import gnu.lists.Sequence;
import gnu.lists.TreeList;
import gnu.mapping.Values;
import gnu.math.IntNum;

/* loaded from: classes.dex */
public class Reduce {
    public static Object sum(Object arg) throws Throwable {
        return sum(arg, IntNum.zero());
    }

    public static Object sum(Object arg, Object zero) throws Throwable {
        if (arg instanceof Values) {
            TreeList tlist = (TreeList) arg;
            int pos = 0;
            Object next = tlist.getPosNext(0);
            if (next != Sequence.eofValue) {
                Object result = MinMax.convert(next);
                while (true) {
                    pos = tlist.nextPos(pos);
                    Object next2 = tlist.getPosNext(pos);
                    if (next2 != Sequence.eofValue) {
                        result = AddOp.apply2(1, result, MinMax.convert(next2));
                    } else {
                        Object zero2 = result;
                        return zero2;
                    }
                }
            } else {
                return zero;
            }
        } else {
            Object zero3 = (Number) MinMax.convert(arg);
            return zero3;
        }
    }
}
