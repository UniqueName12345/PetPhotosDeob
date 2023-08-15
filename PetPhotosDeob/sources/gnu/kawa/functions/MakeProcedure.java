package gnu.kawa.functions;

import gnu.expr.GenericProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;

/* loaded from: classes.dex */
public class MakeProcedure extends ProcedureN {
    public static final MakeProcedure makeProcedure = new MakeProcedure("make-procedure");

    public MakeProcedure(String name) {
        super(name);
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyMakeProcedure");
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) {
        return GenericProc.make(args);
    }
}
