package gnu.kawa.functions;

import gnu.expr.Language;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;
import gnu.mapping.PropertySet;

/* loaded from: classes.dex */
public class Not extends Procedure1 {
    Language language;

    public Not(Language language) {
        this.language = language;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyNot");
        Procedure.compilerKey.set((PropertySet) this, "*gnu.kawa.functions.CompileMisc:forNot");
    }

    public Not(Language language, String name) {
        this(language);
        setName(name);
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public Object apply1(Object arg1) {
        return this.language.booleanObject(!this.language.isTrue(arg1));
    }
}
