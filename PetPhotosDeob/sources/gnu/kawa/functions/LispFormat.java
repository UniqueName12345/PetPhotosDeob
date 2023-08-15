package gnu.kawa.functions;

import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.text.CompoundFormat;
import gnu.text.ReportFormat;
import java.text.ParseException;
import java.util.Vector;

/* loaded from: classes.dex */
public class LispFormat extends CompoundFormat {
    public static final String paramFromCount = "<from count>";
    public static final String paramFromList = "<from list>";
    public static final String paramUnspecified = "<unspecified>";

    /* JADX WARN: Code restructure failed: missing block: B:131:0x0344, code lost:
        throw new java.text.ParseException("saw ~) without matching ~(", r29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x03d2, code lost:
        throw new java.text.ParseException("saw ~} without matching ~{", r29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0466, code lost:
        throw new java.text.ParseException("saw ~> without matching ~<", r29);
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x0618, code lost:
        throw new java.text.ParseException("saw ~] without matching ~[", r29);
     */
    /* JADX WARN: Removed duplicated region for block: B:242:0x074e  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x075e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public LispFormat(char[] r49, int r50, int r51) throws java.text.ParseException {
        /*
            Method dump skipped, instructions count: 2138
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.functions.LispFormat.<init>(char[], int, int):void");
    }

    static java.text.Format[] getFormats(Vector vector, int start, int end) {
        java.text.Format[] f = new java.text.Format[end - start];
        for (int i = start; i < end; i++) {
            f[i - start] = (java.text.Format) vector.elementAt(i);
        }
        return f;
    }

    static java.text.Format popFormats(Vector vector, int start, int end) {
        java.text.Format f;
        if (end == start + 1) {
            f = (java.text.Format) vector.elementAt(start);
        } else {
            f = new CompoundFormat(getFormats(vector, start, end));
        }
        vector.setSize(start);
        return f;
    }

    public LispFormat(String str) throws ParseException {
        this(str.toCharArray());
    }

    public LispFormat(char[] format) throws ParseException {
        this(format, 0, format.length);
    }

    public static int getParam(Vector vec, int index) {
        if (index >= vec.size()) {
            return -1073741824;
        }
        Object arg = vec.elementAt(index);
        if (arg == paramFromList) {
            return -1610612736;
        }
        if (arg == paramFromCount) {
            return ReportFormat.PARAM_FROM_COUNT;
        }
        if (arg != paramUnspecified) {
            return getParam(arg, -1073741824);
        }
        return -1073741824;
    }

    public static Object[] asArray(Object arg) {
        if (arg instanceof Object[]) {
            return (Object[]) arg;
        }
        if (arg instanceof Sequence) {
            int count = ((Sequence) arg).size();
            Object[] arr = new Object[count];
            int i = 0;
            while (arg instanceof Pair) {
                Pair pair = (Pair) arg;
                arr[i] = pair.getCar();
                arg = pair.getCdr();
                i++;
            }
            if (i < count) {
                if (!(arg instanceof Sequence)) {
                    return null;
                }
                int npairs = i;
                Sequence seq = (Sequence) arg;
                while (i < count) {
                    arr[i] = seq.get(npairs + i);
                    i++;
                }
            }
            return arr;
        }
        return null;
    }
}
