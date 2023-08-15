package gnu.kawa.functions;

import gnu.expr.Language;
import gnu.mapping.HasSetter;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1;
import java.util.List;

/* loaded from: classes.dex */
public class Setter extends Procedure1 implements HasSetter {
    public static final Setter setter = new Setter();

    static {
        setter.setName("setter");
        setter.setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompilationHelpers:validateSetter");
    }

    public static Object setter(Procedure arg) {
        return arg.getSetter();
    }

    @Override // gnu.mapping.Procedure1, gnu.mapping.Procedure
    public Object apply1(Object arg) {
        if (!(arg instanceof Procedure)) {
            if (arg instanceof List) {
                return new SetList((List) arg);
            }
            Class cl = arg.getClass();
            if (cl.isArray()) {
                return new SetArray(arg, Language.getDefaultLanguage());
            }
        }
        return ((Procedure) arg).getSetter();
    }

    @Override // gnu.mapping.Procedure
    public void set1(Object arg1, Object value) throws Throwable {
        ((Procedure) arg1).setSetter((Procedure) value);
    }
}
