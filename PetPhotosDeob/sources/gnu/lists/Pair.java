package gnu.lists;

import androidx.appcompat.widget.ActivityChooserView;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

/* loaded from: classes.dex */
public class Pair extends LList implements Externalizable {
    protected Object car;
    protected Object cdr;

    public Pair(Object carval, Object cdrval) {
        this.car = carval;
        this.cdr = cdrval;
    }

    public Pair() {
    }

    public Object getCar() {
        return this.car;
    }

    public Object getCdr() {
        return this.cdr;
    }

    public void setCar(Object car) {
        this.car = car;
    }

    public void setCdr(Object cdr) {
        this.cdr = cdr;
    }

    public void setCdrBackdoor(Object cdr) {
        this.cdr = cdr;
    }

    @Override // gnu.lists.LList, gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List, java.util.Collection, com.google.appinventor.components.runtime.util.YailObject
    public int size() {
        int n = listLength(this, true);
        if (n < 0) {
            if (n == -1) {
                return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            }
            throw new RuntimeException("not a true list");
        }
        return n;
    }

    @Override // gnu.lists.LList, gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List, java.util.Collection
    public boolean isEmpty() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int length() {
        int n = 0;
        Object fast = this;
        Object obj = this;
        while (fast != Empty) {
            if (!(fast instanceof Pair)) {
                if (fast instanceof Sequence) {
                    int j = ((Sequence) fast).size();
                    if (j >= 0) {
                        j += n;
                    }
                    int n2 = j;
                    return n2;
                }
                return -2;
            }
            Pair fast_pair = (Pair) fast;
            if (fast_pair.cdr == Empty) {
                return n + 1;
            }
            if (fast == obj && n > 0) {
                return -1;
            }
            if (!(fast_pair.cdr instanceof Pair)) {
                n++;
                fast = fast_pair.cdr;
            } else if (!(obj instanceof Pair)) {
                return -2;
            } else {
                Object slow = ((Pair) obj).cdr;
                fast = ((Pair) fast_pair.cdr).cdr;
                n += 2;
                obj = slow;
            }
        }
        return n;
    }

    @Override // gnu.lists.LList, gnu.lists.AbstractSequence
    public boolean hasNext(int ipos) {
        if (ipos <= 0) {
            return ipos == 0;
        }
        return PositionManager.getPositionObject(ipos).hasNext();
    }

    @Override // gnu.lists.LList, gnu.lists.AbstractSequence
    public int nextPos(int ipos) {
        if (ipos <= 0) {
            if (ipos < 0) {
                return 0;
            }
            return PositionManager.manager.register(new LListPosition(this, 1, true));
        }
        LListPosition it = (LListPosition) PositionManager.getPositionObject(ipos);
        if (!it.gotoNext()) {
            ipos = 0;
        }
        return ipos;
    }

    @Override // gnu.lists.LList, gnu.lists.AbstractSequence
    public Object getPosNext(int ipos) {
        if (ipos <= 0) {
            return ipos == 0 ? this.car : eofValue;
        }
        return PositionManager.getPositionObject(ipos).getNext();
    }

    @Override // gnu.lists.LList, gnu.lists.AbstractSequence
    public Object getPosPrevious(int ipos) {
        if (ipos <= 0) {
            return ipos == 0 ? eofValue : lastPair().car;
        }
        return PositionManager.getPositionObject(ipos).getPrevious();
    }

    public final Pair lastPair() {
        Pair pair = this;
        while (true) {
            Object next = pair.cdr;
            if (next instanceof Pair) {
                pair = (Pair) next;
            } else {
                return pair;
            }
        }
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public int hashCode() {
        int hash = 1;
        Object obj = this;
        while (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            Object obj2 = pair.car;
            hash = (hash * 31) + (obj2 == null ? 0 : obj2.hashCode());
            obj = pair.cdr;
        }
        if (obj != LList.Empty && obj != null) {
            return hash ^ obj.hashCode();
        }
        return hash;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:?, code lost:
        return r0.equals(r1);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean equals(gnu.lists.Pair r5, gnu.lists.Pair r6) {
        /*
            r2 = 1
            r3 = 0
            if (r5 != r6) goto L5
        L4:
            return r2
        L5:
            if (r5 == 0) goto L9
            if (r6 != 0) goto L11
        L9:
            r2 = r3
            goto L4
        Lb:
            r5 = r0
            gnu.lists.Pair r5 = (gnu.lists.Pair) r5
            r6 = r1
            gnu.lists.Pair r6 = (gnu.lists.Pair) r6
        L11:
            java.lang.Object r0 = r5.car
            java.lang.Object r1 = r6.car
            if (r0 == r1) goto L21
            if (r0 == 0) goto L1f
            boolean r4 = r0.equals(r1)
            if (r4 != 0) goto L21
        L1f:
            r2 = r3
            goto L4
        L21:
            java.lang.Object r0 = r5.cdr
            java.lang.Object r1 = r6.cdr
            if (r0 == r1) goto L4
            if (r0 == 0) goto L2b
            if (r1 != 0) goto L2d
        L2b:
            r2 = r3
            goto L4
        L2d:
            boolean r4 = r0 instanceof gnu.lists.Pair
            if (r4 == 0) goto L35
            boolean r4 = r1 instanceof gnu.lists.Pair
            if (r4 != 0) goto Lb
        L35:
            boolean r2 = r0.equals(r1)
            goto L4
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.Pair.equals(gnu.lists.Pair, gnu.lists.Pair):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:?, code lost:
        return ((java.lang.Comparable) r1).compareTo((java.lang.Comparable) r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int compareTo(gnu.lists.Pair r7, gnu.lists.Pair r8) {
        /*
            r5 = 1
            r3 = 0
            r4 = -1
            if (r7 != r8) goto L7
            r0 = r3
        L6:
            return r0
        L7:
            if (r7 != 0) goto Lb
            r0 = r4
            goto L6
        Lb:
            if (r8 != 0) goto L15
            r0 = r5
            goto L6
        Lf:
            r7 = r1
            gnu.lists.Pair r7 = (gnu.lists.Pair) r7
            r8 = r2
            gnu.lists.Pair r8 = (gnu.lists.Pair) r8
        L15:
            java.lang.Object r1 = r7.car
            java.lang.Object r2 = r8.car
            java.lang.Comparable r1 = (java.lang.Comparable) r1
            java.lang.Comparable r2 = (java.lang.Comparable) r2
            int r0 = r1.compareTo(r2)
            if (r0 != 0) goto L6
            java.lang.Object r1 = r7.cdr
            java.lang.Object r2 = r8.cdr
            if (r1 != r2) goto L2b
            r0 = r3
            goto L6
        L2b:
            if (r1 != 0) goto L2f
            r0 = r4
            goto L6
        L2f:
            if (r2 != 0) goto L33
            r0 = r5
            goto L6
        L33:
            boolean r6 = r1 instanceof gnu.lists.Pair
            if (r6 == 0) goto L3b
            boolean r6 = r2 instanceof gnu.lists.Pair
            if (r6 != 0) goto Lf
        L3b:
            java.lang.Comparable r1 = (java.lang.Comparable) r1
            java.lang.Comparable r2 = (java.lang.Comparable) r2
            int r0 = r1.compareTo(r2)
            goto L6
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.Pair.compareTo(gnu.lists.Pair, gnu.lists.Pair):int");
    }

    @Override // gnu.lists.LList, java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj == Empty) {
            return 1;
        }
        return compareTo(this, (Pair) obj);
    }

    @Override // gnu.lists.LList, gnu.lists.AbstractSequence
    public Object get(int index) {
        Pair pair = this;
        int i = index;
        while (true) {
            if (i <= 0) {
                break;
            }
            i--;
            if (pair.cdr instanceof Pair) {
                pair = (Pair) pair.cdr;
            } else if (pair.cdr instanceof Sequence) {
                return ((Sequence) pair.cdr).get(i);
            }
        }
        if (i == 0) {
            return pair.car;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // gnu.lists.LList, gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Pair)) {
            return false;
        }
        return equals(this, (Pair) obj);
    }

    public static Pair make(Object car, Object cdr) {
        return new Pair(car, cdr);
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public Object[] toArray() {
        int len = size();
        Object[] arr = new Object[len];
        int i = 0;
        Sequence rest = this;
        while (i < len && (rest instanceof Pair)) {
            Pair pair = (Pair) rest;
            arr[i] = pair.car;
            rest = (Sequence) pair.cdr;
            i++;
        }
        int prefix = i;
        while (i < len) {
            arr[i] = rest.get(i - prefix);
            i++;
        }
        return arr;
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public Object[] toArray(Object[] arr) {
        int alen = arr.length;
        int len = length();
        if (len > alen) {
            arr = new Object[len];
            alen = len;
        }
        int i = 0;
        Sequence rest = this;
        while (i < len && (rest instanceof Pair)) {
            Pair pair = (Pair) rest;
            arr[i] = pair.car;
            rest = (Sequence) pair.cdr;
            i++;
        }
        int prefix = i;
        while (i < len) {
            arr[i] = rest.get(i - prefix);
            i++;
        }
        if (len < alen) {
            arr[len] = null;
        }
        return arr;
    }

    @Override // gnu.lists.LList, java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.car);
        out.writeObject(this.cdr);
    }

    @Override // gnu.lists.LList, java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.car = in.readObject();
        this.cdr = in.readObject();
    }

    @Override // gnu.lists.LList
    public Object readResolve() throws ObjectStreamException {
        return this;
    }
}
