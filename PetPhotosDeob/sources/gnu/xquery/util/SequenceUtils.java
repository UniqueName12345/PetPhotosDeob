package gnu.xquery.util;

import gnu.kawa.xml.KAttr;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.NodeType;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.xml.NodeTree;

/* loaded from: classes.dex */
public class SequenceUtils {
    public static final NodeType textOrElement = new NodeType("element-or-text", 3);

    public static boolean isZeroOrOne(Object arg) {
        return !(arg instanceof Values) || ((Values) arg).isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object coerceToZeroOrOne(Object arg, String functionName, int iarg) {
        if (isZeroOrOne(arg)) {
            return arg;
        }
        throw new WrongType(functionName, iarg, arg, "xs:item()?");
    }

    public static Object zeroOrOne(Object arg) {
        return coerceToZeroOrOne(arg, "zero-or-one", 1);
    }

    public static Object oneOrMore(Object arg) {
        if ((arg instanceof Values) && ((Values) arg).isEmpty()) {
            throw new IllegalArgumentException();
        }
        return arg;
    }

    public static Object exactlyOne(Object arg) {
        if (arg instanceof Values) {
            throw new IllegalArgumentException();
        }
        return arg;
    }

    public static boolean isEmptySequence(Object arg) {
        return (arg instanceof Values) && ((Values) arg).isEmpty();
    }

    public static boolean exists(Object arg) {
        return ((arg instanceof Values) && ((Values) arg).isEmpty()) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0022, code lost:
        if (r0 == r12) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void insertBefore$X(java.lang.Object r11, long r12, java.lang.Object r14, gnu.mapping.CallContext r15) {
        /*
            gnu.lists.Consumer r4 = r15.consumer
            r6 = 0
            r8 = 0
            int r7 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
            if (r7 > 0) goto Lb
            r12 = 1
        Lb:
            boolean r7 = r11 instanceof gnu.mapping.Values
            if (r7 == 0) goto L30
            r5 = r11
            gnu.mapping.Values r5 = (gnu.mapping.Values) r5
            r2 = 0
            r0 = 0
        L15:
            int r3 = r5.nextPos(r2)
            if (r3 != 0) goto L1d
            if (r6 == 0) goto L24
        L1d:
            r8 = 1
            long r0 = r0 + r8
            int r7 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1))
            if (r7 != 0) goto L28
        L24:
            gnu.mapping.Values.writeValues(r14, r4)
            r6 = 1
        L28:
            if (r3 != 0) goto L2b
        L2a:
            return
        L2b:
            r5.consumePosRange(r2, r3, r4)
            r2 = r3
            goto L15
        L30:
            r8 = 1
            int r7 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
            if (r7 > 0) goto L39
            gnu.mapping.Values.writeValues(r14, r4)
        L39:
            r4.writeObject(r11)
            r8 = 1
            int r7 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
            if (r7 <= 0) goto L2a
            gnu.mapping.Values.writeValues(r14, r4)
            goto L2a
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.SequenceUtils.insertBefore$X(java.lang.Object, long, java.lang.Object, gnu.mapping.CallContext):void");
    }

    public static void remove$X(Object arg, long position, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (arg instanceof Values) {
            Values values = (Values) arg;
            int ipos = 0;
            long i = 0;
            while (true) {
                int next = values.nextPos(ipos);
                if (next != 0) {
                    i++;
                    if (i != position) {
                        values.consumePosRange(ipos, next, out);
                    }
                    ipos = next;
                } else {
                    return;
                }
            }
        } else if (position != 1) {
            out.writeObject(arg);
        }
    }

    public static void reverse$X(Object arg, CallContext ctx) {
        int n;
        Consumer out = ctx.consumer;
        if (!(arg instanceof Values)) {
            out.writeObject(arg);
            return;
        }
        Values vals = (Values) arg;
        int ipos = 0;
        int[] poses = new int[100];
        int n2 = 0;
        while (true) {
            if (n2 >= poses.length) {
                int[] t = new int[n2 * 2];
                System.arraycopy(poses, 0, t, 0, n2);
                poses = t;
            }
            n = n2 + 1;
            poses[n2] = ipos;
            ipos = vals.nextPos(ipos);
            if (ipos == 0) {
                break;
            }
            n2 = n;
        }
        int i = n - 1;
        while (true) {
            i--;
            if (i >= 0) {
                vals.consumePosRange(poses[i], poses[i + 1], out);
            } else {
                return;
            }
        }
    }

    public static void indexOf$X(Object seqParam, Object srchParam, NamedCollator collator, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (seqParam instanceof Values) {
            Values vals = (Values) seqParam;
            int ipos = vals.startPos();
            int i = 1;
            while (true) {
                ipos = vals.nextPos(ipos);
                if (ipos != 0) {
                    if (Compare.apply(72, vals.getPosPrevious(ipos), srchParam, collator)) {
                        out.writeInt(i);
                    }
                    i++;
                } else {
                    return;
                }
            }
        } else if (Compare.apply(72, seqParam, srchParam, collator)) {
            out.writeInt(1);
        }
    }

    public static boolean deepEqualChildren(NodeTree seq1, int ipos1, NodeTree seq2, int ipos2, NamedCollator collator) {
        NodeType filter = textOrElement;
        int child1 = seq1.firstChildPos(ipos1, filter);
        int child2 = seq2.firstChildPos(ipos2, filter);
        while (child1 != 0 && child2 != 0) {
            if (!deepEqual(seq1, child1, seq2, child2, collator)) {
                return false;
            }
            child1 = seq1.nextMatching(child1, filter, -1, false);
            child2 = seq2.nextMatching(child2, filter, -1, false);
        }
        return child1 == child2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean deepEqual(NodeTree seq1, int ipos1, NodeTree seq2, int ipos2, NamedCollator collator) {
        int kind1 = seq1.getNextKind(ipos1);
        int kind2 = seq2.getNextKind(ipos2);
        switch (kind1) {
            case 33:
                if (kind1 != kind2) {
                    return false;
                }
                String loc1 = seq1.posLocalName(ipos1);
                String loc2 = seq2.posLocalName(ipos2);
                if (loc1 != loc2) {
                    return false;
                }
                String ns1 = seq1.posNamespaceURI(ipos1);
                String ns2 = seq2.posNamespaceURI(ipos2);
                if (ns1 != ns2) {
                    return false;
                }
                int attr1 = seq1.firstAttributePos(ipos1);
                int nattr1 = 0;
                while (attr1 != 0 && seq1.getNextKind(attr1) == 35) {
                    nattr1++;
                    String local = seq1.posLocalName(attr1);
                    String ns = seq1.posNamespaceURI(attr1);
                    int attr2 = seq2.getAttributeI(ipos2, ns, local);
                    if (attr2 == 0) {
                        return false;
                    }
                    String aval1 = KNode.getNodeValue(seq1, attr1);
                    String aval2 = KNode.getNodeValue(seq2, attr2);
                    if (!deepEqualItems(aval1, aval2, collator)) {
                        return false;
                    }
                    attr1 = seq1.nextPos(attr1);
                }
                int nattr2 = seq2.getAttributeCount(ipos2);
                if (nattr1 != nattr2) {
                    return false;
                }
                break;
            case 34:
                break;
            case 35:
                if (seq1.posLocalName(ipos1) != seq2.posLocalName(ipos2) || seq1.posNamespaceURI(ipos1) != seq2.posNamespaceURI(ipos2)) {
                    return false;
                }
                return deepEqualItems(KAttr.getObjectValue(seq1, ipos1), KAttr.getObjectValue(seq2, ipos2), collator);
            case 36:
            default:
                if (kind1 != kind2) {
                    return false;
                }
                return KNode.getNodeValue(seq1, ipos1).equals(KNode.getNodeValue(seq2, ipos2));
            case 37:
                if (!seq1.posTarget(ipos1).equals(seq2.posTarget(ipos2))) {
                    return false;
                }
                return KNode.getNodeValue(seq1, ipos1).equals(KNode.getNodeValue(seq2, ipos2));
        }
        return deepEqualChildren(seq1, ipos1, seq2, ipos2, collator);
    }

    public static boolean deepEqualItems(Object arg1, Object arg2, NamedCollator collator) {
        if (NumberValue.isNaN(arg1) && NumberValue.isNaN(arg2)) {
            return true;
        }
        return Compare.atomicCompare(8, arg1, arg2, collator);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0069, code lost:
        if (r7 != r8) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006b, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0073, code lost:
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean deepEqual(java.lang.Object r21, java.lang.Object r22, gnu.xquery.util.NamedCollator r23) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.SequenceUtils.deepEqual(java.lang.Object, java.lang.Object, gnu.xquery.util.NamedCollator):boolean");
    }
}
