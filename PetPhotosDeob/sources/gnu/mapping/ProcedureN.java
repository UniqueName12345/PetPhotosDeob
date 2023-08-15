package gnu.mapping;

/* loaded from: classes.dex */
public abstract class ProcedureN extends Procedure {
    public static final Object[] noArgs = new Object[0];

    @Override // gnu.mapping.Procedure
    public abstract Object applyN(Object[] objArr) throws Throwable;

    public ProcedureN() {
    }

    public ProcedureN(String n) {
        super(n);
    }

    @Override // gnu.mapping.Procedure
    public Object apply0() throws Throwable {
        return applyN(noArgs);
    }

    @Override // gnu.mapping.Procedure
    public Object apply1(Object arg1) throws Throwable {
        Object[] args = {arg1};
        return applyN(args);
    }

    @Override // gnu.mapping.Procedure
    public Object apply2(Object arg1, Object arg2) throws Throwable {
        Object[] args = {arg1, arg2};
        return applyN(args);
    }

    @Override // gnu.mapping.Procedure
    public Object apply3(Object arg1, Object arg2, Object arg3) throws Throwable {
        Object[] args = {arg1, arg2, arg3};
        return applyN(args);
    }

    @Override // gnu.mapping.Procedure
    public Object apply4(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {
        Object[] args = {arg1, arg2, arg3, arg4};
        return applyN(args);
    }
}
