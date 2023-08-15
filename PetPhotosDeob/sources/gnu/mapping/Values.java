package gnu.mapping;

import gnu.lists.Consumer;
import gnu.lists.TreeList;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.List;

/* loaded from: classes.dex */
public class Values extends TreeList implements Printable, Externalizable {
    public static final Object[] noArgs = new Object[0];
    public static final Values empty = new Values(noArgs);

    public Values() {
    }

    public Values(Object[] values) {
        for (Object obj : values) {
            writeObject(obj);
        }
    }

    public Object[] getValues() {
        return isEmpty() ? noArgs : toArray();
    }

    public static Object values(Object... vals) {
        return make(vals);
    }

    public static Values make() {
        return new Values();
    }

    public static Object make(Object[] vals) {
        if (vals.length == 1) {
            return vals[0];
        }
        if (vals.length == 0) {
            return empty;
        }
        return new Values(vals);
    }

    public static Object make(List seq) {
        int count = seq == null ? 0 : seq.size();
        if (count == 0) {
            return empty;
        }
        if (count == 1) {
            return seq.get(0);
        }
        Values vals = new Values();
        for (Object obj : seq) {
            vals.writeObject(obj);
        }
        return vals;
    }

    public static Object make(TreeList list) {
        return make(list, 0, list.data.length);
    }

    public static Object make(TreeList list, int startPosition, int endPosition) {
        int next;
        if (startPosition == endPosition || (next = list.nextDataIndex(startPosition)) <= 0) {
            return empty;
        }
        if (next == endPosition || list.nextDataIndex(next) < 0) {
            return list.getPosNext(startPosition << 1);
        }
        Values vals = new Values();
        list.consumeIRange(startPosition, endPosition, vals);
        return vals;
    }

    public final Object canonicalize() {
        if (this.gapEnd == this.data.length) {
            if (this.gapStart == 0) {
                return empty;
            }
            if (nextDataIndex(0) == this.gapStart) {
                return getPosNext(0);
            }
            return this;
        }
        return this;
    }

    public Object call_with(Procedure proc) throws Throwable {
        return proc.applyN(toArray());
    }

    @Override // gnu.text.Printable
    public void print(Consumer out) {
        if (this == empty) {
            out.write("#!void");
            return;
        }
        Object[] vals = toArray();
        int length = vals.length;
        if (1 != 0) {
            out.write("#<values");
        }
        int i = 0;
        while (true) {
            int next = nextDataIndex(i);
            if (next < 0) {
                break;
            }
            out.write(32);
            if (i >= this.gapEnd) {
                i -= this.gapEnd - this.gapStart;
            }
            Object val = getPosNext(i << 1);
            if (val instanceof Printable) {
                ((Printable) val).print(out);
            } else {
                out.writeObject(val);
            }
            i = next;
        }
        if (1 != 0) {
            out.write(62);
        }
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        Object[] vals = toArray();
        int len = vals.length;
        out.writeInt(len);
        for (Object obj : vals) {
            out.writeObject(obj);
        }
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int len = in.readInt();
        for (int i = 0; i < len; i++) {
            writeObject(in.readObject());
        }
    }

    public Object readResolve() throws ObjectStreamException {
        return isEmpty() ? empty : this;
    }

    public static int nextIndex(Object values, int curIndex) {
        if (values instanceof Values) {
            return ((Values) values).nextDataIndex(curIndex);
        }
        return curIndex == 0 ? 1 : -1;
    }

    public static Object nextValue(Object values, int curIndex) {
        if (values instanceof Values) {
            Values v = (Values) values;
            if (curIndex >= v.gapEnd) {
                curIndex -= v.gapEnd - v.gapStart;
            }
            return ((Values) values).getPosNext(curIndex << 1);
        }
        return values;
    }

    public static void writeValues(Object value, Consumer out) {
        if (value instanceof Values) {
            ((Values) value).consume(out);
        } else {
            out.writeObject(value);
        }
    }

    public static int countValues(Object value) {
        if (value instanceof Values) {
            return ((Values) value).size();
        }
        return 1;
    }
}
