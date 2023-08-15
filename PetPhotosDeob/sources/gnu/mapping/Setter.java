package gnu.mapping;

import androidx.fragment.app.FragmentTransaction;

/* loaded from: classes.dex */
public class Setter extends ProcedureN {
    protected Procedure getter;

    public Setter(Procedure getter) {
        this.getter = getter;
        String name = getter.getName();
        if (name != null) {
            setName("(setter " + name + ")");
        }
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        int get_args = this.getter.numArgs();
        return get_args < 0 ? get_args + 1 : get_args + FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) throws Throwable {
        this.getter.setN(args);
        return Values.empty;
    }
}
