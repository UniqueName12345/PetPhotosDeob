package kawa.lang;

import gnu.lists.Consumer;
import gnu.lists.Pair;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class PairPat extends Pattern implements Printable, Externalizable {
    Pattern car;
    private int car_count;
    Pattern cdr;
    private int cdr_count;

    public PairPat() {
    }

    public PairPat(Pattern car, Pattern cdr) {
        this.car = car;
        this.cdr = cdr;
        this.car_count = car.varCount();
        this.cdr_count = cdr.varCount();
    }

    public static PairPat make(Pattern car, Pattern cdr) {
        return new PairPat(car, cdr);
    }

    @Override // kawa.lang.Pattern
    public boolean match(Object obj, Object[] vars, int start_vars) {
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            return this.car.match(pair.getCar(), vars, start_vars) && this.cdr.match(pair.getCdr(), vars, this.car_count + start_vars);
        }
        return false;
    }

    @Override // gnu.text.Printable
    public void print(Consumer out) {
        out.write("#<pair-pattern car: ");
        this.car.print(out);
        out.write(" cdr: ");
        this.cdr.print(out);
        out.write(62);
    }

    @Override // kawa.lang.Pattern
    public int varCount() {
        return this.car_count + this.cdr_count;
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.car);
        out.writeObject(this.cdr);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.car = (Pattern) in.readObject();
        this.cdr = (Pattern) in.readObject();
    }
}
