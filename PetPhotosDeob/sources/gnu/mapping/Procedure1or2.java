package gnu.mapping;

/* loaded from: classes.dex */
public abstract class Procedure1or2 extends Procedure {
    @Override // gnu.mapping.Procedure
    public abstract Object apply1(Object obj) throws Throwable;

    @Override // gnu.mapping.Procedure
    public abstract Object apply2(Object obj, Object obj2) throws Throwable;

    public Procedure1or2() {
    }

    public Procedure1or2(String n) {
        super(n);
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return 8193;
    }

    @Override // gnu.mapping.Procedure
    public Object apply0() {
        throw new WrongArguments(this, 0);
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
        if (args.length == 1) {
            return apply1(args[0]);
        }
        if (args.length == 2) {
            return apply2(args[0], args[1]);
        }
        throw new WrongArguments(this, args.length);
    }
}
