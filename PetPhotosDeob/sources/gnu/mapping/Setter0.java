package gnu.mapping;

import androidx.fragment.app.FragmentTransaction;

/* loaded from: classes.dex */
public class Setter0 extends Setter {
    public Setter0(Procedure getter) {
        super(getter);
    }

    @Override // gnu.mapping.Setter, gnu.mapping.Procedure
    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object apply1(Object result) throws Throwable {
        this.getter.set0(result);
        return Values.empty;
    }

    @Override // gnu.mapping.Setter, gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) throws Throwable {
        int nargs = args.length;
        if (nargs != 1) {
            throw new WrongArguments(this, nargs);
        }
        this.getter.set0(args[0]);
        return Values.empty;
    }
}
