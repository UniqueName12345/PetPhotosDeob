package gnu.xquery.util;

import gnu.kawa.functions.NumberCompare;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.lists.FilterConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;

/* loaded from: classes.dex */
public class OrderedTuples extends FilterConsumer {
    Procedure body;
    Object[] comps;
    int first;
    int n;
    int[] next;
    Object[] tuples;

    @Override // gnu.lists.FilterConsumer, gnu.lists.Consumer
    public void writeObject(Object v) {
        if (this.n >= this.tuples.length) {
            Object[] tmp = new Object[this.n * 2];
            System.arraycopy(this.tuples, 0, tmp, 0, this.n);
            this.tuples = tmp;
        }
        Object[] objArr = this.tuples;
        int i = this.n;
        this.n = i + 1;
        objArr[i] = v;
    }

    OrderedTuples() {
        super(null);
        this.tuples = new Object[10];
    }

    public static OrderedTuples make$V(Procedure body, Object[] comps) {
        OrderedTuples tuples = new OrderedTuples();
        tuples.comps = comps;
        tuples.body = body;
        return tuples;
    }

    public void run$X(CallContext ctx) throws Throwable {
        this.first = listsort(0);
        emit(ctx);
    }

    void emit(CallContext ctx) throws Throwable {
        int p = this.first;
        while (p >= 0) {
            emit(p, ctx);
            p = this.next[p];
        }
    }

    void emit(int index, CallContext ctx) throws Throwable {
        Object[] args = (Object[]) this.tuples[index];
        this.body.checkN(args, ctx);
        ctx.runUntilDone();
    }

    int cmp(int a, int b) throws Throwable {
        int c;
        for (int i = 0; i < this.comps.length; i += 3) {
            Procedure comparator = (Procedure) this.comps[i];
            String flags = (String) this.comps[i + 1];
            NamedCollator collator = (NamedCollator) this.comps[i + 2];
            if (collator == null) {
                collator = NamedCollator.codepointCollation;
            }
            Object val1 = comparator.applyN((Object[]) this.tuples[a]);
            Object val2 = comparator.applyN((Object[]) this.tuples[b]);
            Object val12 = KNode.atomicValue(val1);
            Object val22 = KNode.atomicValue(val2);
            if (val12 instanceof UntypedAtomic) {
                val12 = val12.toString();
            }
            if (val22 instanceof UntypedAtomic) {
                val22 = val22.toString();
            }
            boolean empty1 = SequenceUtils.isEmptySequence(val12);
            boolean empty2 = SequenceUtils.isEmptySequence(val22);
            if (!empty1 || !empty2) {
                if (empty1 || empty2) {
                    char emptyOrder = flags.charAt(1);
                    c = empty1 == (emptyOrder == 'L') ? -1 : 1;
                } else {
                    boolean isNaN1 = (val12 instanceof Number) && Double.isNaN(((Number) val12).doubleValue());
                    boolean isNaN2 = (val22 instanceof Number) && Double.isNaN(((Number) val22).doubleValue());
                    if (!isNaN1 || !isNaN2) {
                        if (isNaN1 || isNaN2) {
                            char emptyOrder2 = flags.charAt(1);
                            c = isNaN1 == (emptyOrder2 == 'L') ? -1 : 1;
                        } else if ((val12 instanceof Number) && (val22 instanceof Number)) {
                            c = NumberCompare.compare(val12, val22, false);
                        } else {
                            c = collator.compare(val12.toString(), val22.toString());
                        }
                    }
                }
                if (c != 0) {
                    if (flags.charAt(0) != 'A') {
                        return -c;
                    }
                    return c;
                }
            }
        }
        return 0;
    }

    int listsort(int list) throws Throwable {
        int e;
        if (this.n == 0) {
            return -1;
        }
        this.next = new int[this.n];
        int i = 1;
        while (i != this.n) {
            this.next[i - 1] = i;
            i++;
        }
        this.next[i - 1] = -1;
        int insize = 1;
        while (true) {
            int p = list;
            list = -1;
            int tail = -1;
            int nmerges = 0;
            while (p >= 0) {
                nmerges++;
                int q = p;
                int psize = 0;
                for (int i2 = 0; i2 < insize; i2++) {
                    psize++;
                    q = this.next[q];
                    if (q < 0) {
                        break;
                    }
                }
                int qsize = insize;
                while (true) {
                    if (psize > 0 || (qsize > 0 && q >= 0)) {
                        if (psize == 0) {
                            e = q;
                            q = this.next[q];
                            qsize--;
                        } else if (qsize == 0 || q < 0) {
                            e = p;
                            p = this.next[p];
                            psize--;
                        } else if (cmp(p, q) <= 0) {
                            e = p;
                            p = this.next[p];
                            psize--;
                        } else {
                            e = q;
                            q = this.next[q];
                            qsize--;
                        }
                        if (tail >= 0) {
                            this.next[tail] = e;
                        } else {
                            list = e;
                        }
                        tail = e;
                    }
                }
                p = q;
            }
            this.next[tail] = -1;
            if (nmerges <= 1) {
                return list;
            }
            insize *= 2;
        }
    }
}
