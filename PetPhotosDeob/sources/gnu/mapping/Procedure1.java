package gnu.mapping;

import androidx.fragment.app.FragmentTransaction;

/* loaded from: classes.dex */
public abstract class Procedure1 extends Procedure {
    @Override // gnu.mapping.Procedure
    public abstract Object apply1(Object obj) throws Throwable;

    public Procedure1() {
    }

    public Procedure1(String n) {
        super(n);
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    @Override // gnu.mapping.Procedure
    public Object apply0() throws Throwable {
        throw new WrongArguments(this, 0);
    }

    @Override // gnu.mapping.Procedure
    public Object apply2(Object arg1, Object arg2) throws Throwable {
        throw new WrongArguments(this, 2);
    }

    @Override // gnu.mapping.Procedure
    public Object apply3(Object arg1, Object arg2, Object arg3) throws Throwable {
        throw new WrongArguments(this, 3);
    }

    @Override // gnu.mapping.Procedure
    public Object apply4(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {
        throw new WrongArguments(this, 4);
    }

    @Override // gnu.mapping.Procedure
    public Object applyN(Object[] args) throws Throwable {
        if (args.length != 1) {
            throw new WrongArguments(this, args.length);
        }
        return apply1(args[0]);
    }
}
