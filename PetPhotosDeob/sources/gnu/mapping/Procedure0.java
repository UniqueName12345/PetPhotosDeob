package gnu.mapping;

/* loaded from: classes.dex */
public abstract class Procedure0 extends Procedure {
    @Override // gnu.mapping.Procedure
    public abstract Object apply0() throws Throwable;

    public Procedure0() {
    }

    public Procedure0(String n) {
        super(n);
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return 0;
    }

    @Override // gnu.mapping.Procedure
    public Object apply1(Object arg1) {
        throw new WrongArguments(this, 1);
    }

    @Override // gnu.mapping.Procedure
    public Object apply2(Object arg1, Object arg2) {
        throw new WrongArguments(this, 2);
    }

    @Override // gnu.mapping.Procedure
    public Object apply3(Object arg1, Object arg2, Object arg3) {
        throw new WrongArguments(this, 3);
    }

    @Override // gnu.mapping.Procedure
    public Object apply4(Object arg1, Object arg2, Object arg3, Object arg4) {
        throw new WrongArguments(this, 4);
    }

    @Override // gnu.mapping.Procedure
    public Object applyN(Object[] args) throws Throwable {
        if (args.length != 0) {
            throw new WrongArguments(this, args.length);
        }
        return apply0();
    }
}
