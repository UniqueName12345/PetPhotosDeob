package kawa.lang;

import gnu.lists.Consumer;
import gnu.lists.Pair;

/* loaded from: classes.dex */
public class VarListPat extends Pattern {
    int min_length;

    public VarListPat(int min) {
        this.min_length = min;
    }

    @Override // kawa.lang.Pattern
    public boolean match(Object obj, Object[] vars, int start_vars) {
        int i = 0;
        while (i < this.min_length) {
            if (obj instanceof Pair) {
                Pair p = (Pair) obj;
                vars[start_vars + i] = p.getCar();
                obj = p.getCdr();
                i++;
            } else {
                return false;
            }
        }
        vars[start_vars + i] = obj;
        return true;
    }

    @Override // kawa.lang.Pattern
    public int varCount() {
        return this.min_length + 1;
    }

    @Override // gnu.text.Printable
    public void print(Consumer out) {
        out.write("#<varlist-pattern min:");
        out.writeInt(this.min_length);
        out.write(62);
    }
}
