package kawa.lang;

import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class ListRepeatPat extends Pattern implements Printable, Externalizable {
    Pattern element_pattern;

    public ListRepeatPat() {
    }

    public ListRepeatPat(Pattern element_pattern) {
        this.element_pattern = element_pattern;
    }

    public static ListRepeatPat make(Pattern element_pattern) {
        return new ListRepeatPat(element_pattern);
    }

    @Override // gnu.text.Printable
    public void print(Consumer out) {
        out.write("#<list-repeat-pattern ");
        this.element_pattern.print(out);
        out.write(62);
    }

    @Override // kawa.lang.Pattern
    public boolean match(Object obj, Object[] vars, int start_vars) {
        int length = LList.listLength(obj, false);
        if (length < 0) {
            return false;
        }
        int var_count = this.element_pattern.varCount();
        int i = var_count;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            vars[start_vars + i] = new Object[length];
        }
        Object[] element_vars = new Object[var_count];
        for (int j = 0; j < length; j++) {
            Pair pair = (Pair) obj;
            if (!this.element_pattern.match(pair.getCar(), element_vars, 0)) {
                return false;
            }
            for (int i2 = 0; i2 < var_count; i2++) {
                ((Object[]) vars[start_vars + i2])[j] = element_vars[i2];
            }
            obj = pair.getCdr();
        }
        return true;
    }

    @Override // kawa.lang.Pattern
    public int varCount() {
        return this.element_pattern.varCount();
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.element_pattern);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.element_pattern = (Pattern) in.readObject();
    }
}
