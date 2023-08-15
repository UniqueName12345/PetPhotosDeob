package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.List;

/* loaded from: classes.dex */
public class LList extends ExtSequence implements Sequence, Externalizable, Comparable {
    public static final LList Empty = new LList();

    public static int listLength(Object obj, boolean allowOtherSequence) {
        int n = 0;
        Object slow = obj;
        Object fast = obj;
        while (fast != Empty) {
            if (!(fast instanceof Pair)) {
                if ((fast instanceof Sequence) && allowOtherSequence) {
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
            if (fast == slow && n > 0) {
                return -1;
            }
            if (!(fast_pair.cdr instanceof Pair)) {
                n++;
                fast = fast_pair.cdr;
            } else if (!(slow instanceof Pair)) {
                return -2;
            } else {
                slow = ((Pair) slow).cdr;
                fast = ((Pair) fast_pair.cdr).cdr;
                n += 2;
            }
        }
        return n;
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return obj == Empty ? 0 : -1;
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List, java.util.Collection, com.google.appinventor.components.runtime.util.YailObject
    public int size() {
        return 0;
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List, java.util.Collection
    public boolean isEmpty() {
        return true;
    }

    @Override // gnu.lists.AbstractSequence
    public SeqPosition getIterator(int index) {
        return new LListPosition(this, index, false);
    }

    @Override // gnu.lists.AbstractSequence
    public int createPos(int index, boolean isAfter) {
        ExtPosition pos = new LListPosition(this, index, isAfter);
        return PositionManager.manager.register(pos);
    }

    @Override // gnu.lists.AbstractSequence
    public int createRelativePos(int pos, int delta, boolean isAfter) {
        boolean old_after = isAfterPos(pos);
        if (delta < 0 || pos == 0) {
            return super.createRelativePos(pos, delta, isAfter);
        }
        if (delta == 0) {
            if (isAfter == old_after) {
                return copyPos(pos);
            }
            if (isAfter && !old_after) {
                return super.createRelativePos(pos, delta, isAfter);
            }
        }
        if (pos < 0) {
            throw new IndexOutOfBoundsException();
        }
        LListPosition old = (LListPosition) PositionManager.getPositionObject(pos);
        if (old.xpos == null) {
            return super.createRelativePos(pos, delta, isAfter);
        }
        LListPosition it = new LListPosition(old);
        Object it_xpos = it.xpos;
        int it_ipos = it.ipos;
        if (isAfter && !old_after) {
            delta--;
            it_ipos += 3;
        }
        if (!isAfter && old_after) {
            delta++;
            it_ipos -= 3;
        }
        while (it_xpos instanceof Pair) {
            delta--;
            if (delta >= 0) {
                Pair p = (Pair) it_xpos;
                it_ipos += 2;
                it_xpos = p.cdr;
            } else {
                it.ipos = it_ipos;
                it.xpos = it_xpos;
                return PositionManager.manager.register(it);
            }
        }
        throw new IndexOutOfBoundsException();
    }

    @Override // gnu.lists.AbstractSequence
    public boolean hasNext(int ipos) {
        return false;
    }

    @Override // gnu.lists.AbstractSequence
    public int nextPos(int ipos) {
        return 0;
    }

    @Override // gnu.lists.AbstractSequence
    public Object getPosNext(int ipos) {
        return eofValue;
    }

    @Override // gnu.lists.AbstractSequence
    public Object getPosPrevious(int ipos) {
        return eofValue;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public void setPosNext(int ipos, Object value) {
        if (ipos <= 0) {
            if (ipos == -1 || !(this instanceof Pair)) {
                throw new IndexOutOfBoundsException();
            }
            ((Pair) this).car = value;
            return;
        }
        PositionManager.getPositionObject(ipos).setNext(value);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public void setPosPrevious(int ipos, Object value) {
        if (ipos <= 0) {
            if (ipos == 0 || !(this instanceof Pair)) {
                throw new IndexOutOfBoundsException();
            }
            ((Pair) this).lastPair().car = value;
            return;
        }
        PositionManager.getPositionObject(ipos).setPrevious(value);
    }

    @Override // gnu.lists.AbstractSequence
    public Object get(int index) {
        throw new IndexOutOfBoundsException();
    }

    public static final int length(Object arg) {
        int count = 0;
        while (arg instanceof Pair) {
            count++;
            arg = ((Pair) arg).cdr;
        }
        return count;
    }

    public static LList makeList(List vals) {
        LList result = Empty;
        Pair last = null;
        for (Object obj : vals) {
            Pair pair = new Pair(obj, Empty);
            if (last == null) {
                result = pair;
            } else {
                last.cdr = pair;
            }
            last = pair;
        }
        return result;
    }

    public static LList makeList(Object[] vals, int offset, int length) {
        LList result = Empty;
        int i = length;
        while (true) {
            LList result2 = result;
            i--;
            if (i >= 0) {
                result = new Pair(vals[offset + i], result2);
            } else {
                return result2;
            }
        }
    }

    public static LList makeList(Object[] vals, int offset) {
        LList result = Empty;
        int i = vals.length - offset;
        while (true) {
            LList result2 = result;
            i--;
            if (i >= 0) {
                result = new Pair(vals[offset + i], result2);
            } else {
                return result2;
            }
        }
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Consumable
    public void consume(Consumer out) {
        Object obj = this;
        out.startElement("list");
        while (obj instanceof Pair) {
            if (obj != this) {
                out.write(32);
            }
            Pair pair = (Pair) obj;
            out.writeObject(pair.car);
            obj = pair.cdr;
        }
        if (obj != Empty) {
            out.write(32);
            out.write(". ");
            out.writeObject(checkNonList(obj));
        }
        out.endElement();
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    public Object readResolve() throws ObjectStreamException {
        return Empty;
    }

    public static Pair list1(Object arg1) {
        return new Pair(arg1, Empty);
    }

    public static Pair list2(Object arg1, Object arg2) {
        return new Pair(arg1, new Pair(arg2, Empty));
    }

    public static Pair list3(Object arg1, Object arg2, Object arg3) {
        return new Pair(arg1, new Pair(arg2, new Pair(arg3, Empty)));
    }

    public static Pair list4(Object arg1, Object arg2, Object arg3, Object arg4) {
        return new Pair(arg1, new Pair(arg2, new Pair(arg3, new Pair(arg4, Empty))));
    }

    public static Pair chain1(Pair old, Object arg1) {
        Pair p1 = new Pair(arg1, Empty);
        old.cdr = p1;
        return p1;
    }

    public static Pair chain4(Pair old, Object arg1, Object arg2, Object arg3, Object arg4) {
        Pair p4 = new Pair(arg4, Empty);
        old.cdr = new Pair(arg1, new Pair(arg2, new Pair(arg3, p4)));
        return p4;
    }

    public static LList reverseInPlace(Object list) {
        LList prev = Empty;
        while (list != Empty) {
            Pair pair = (Pair) list;
            list = pair.cdr;
            pair.cdr = prev;
            prev = pair;
        }
        return prev;
    }

    public static Object listTail(Object list, int count) {
        while (true) {
            count--;
            if (count >= 0) {
                if (!(list instanceof Pair)) {
                    throw new IndexOutOfBoundsException("List is too short.");
                }
                Pair pair = (Pair) list;
                list = pair.cdr;
            } else {
                return list;
            }
        }
    }

    public static Object consX(Object[] args) {
        Object first = args[0];
        int n = args.length - 1;
        if (n > 0) {
            Pair result = new Pair(first, null);
            Pair prev = result;
            for (int i = 1; i < n; i++) {
                Pair next = new Pair(args[i], null);
                prev.cdr = next;
                prev = next;
            }
            prev.cdr = args[n];
            return result;
        }
        return first;
    }

    @Override // gnu.lists.AbstractSequence
    public String toString() {
        Object obj = this;
        int i = 0;
        StringBuffer sbuf = new StringBuffer(100);
        sbuf.append('(');
        while (true) {
            if (obj == Empty) {
                break;
            }
            if (i > 0) {
                sbuf.append(' ');
            }
            if (i >= 10) {
                sbuf.append("...");
                break;
            } else if (obj instanceof Pair) {
                Pair pair = (Pair) obj;
                sbuf.append(pair.car);
                obj = pair.cdr;
                i++;
            } else {
                sbuf.append(". ");
                sbuf.append(checkNonList(obj));
                break;
            }
        }
        sbuf.append(')');
        return sbuf.toString();
    }

    public static Object checkNonList(Object rest) {
        return rest instanceof LList ? "#<not a pair>" : rest;
    }
}
