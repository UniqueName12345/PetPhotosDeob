package gnu.mapping;

/* loaded from: classes.dex */
public abstract class Procedure0or1 extends Procedure {
    @Override // gnu.mapping.Procedure
    public abstract Object apply0() throws Throwable;

    @Override // gnu.mapping.Procedure
    public abstract Object apply1(Object obj) throws Throwable;

    public Procedure0or1() {
    }

    public Procedure0or1(String n) {
        super(n);
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return 4096;
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
        if (args.length == 0) {
            return apply0();
        }
        if (args.length == 1) {
            return apply1(args[0]);
        }
        throw new WrongArguments(this, args.length);
    }
}
