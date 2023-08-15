package kawa.standard;

import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class module_compile_options extends Syntax {
    public static final module_compile_options module_compile_options = new module_compile_options();

    static {
        module_compile_options.setName("module-compile-options");
    }

    @Override // kawa.lang.Syntax
    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        Object rest = with_compile_options.getOptions(st.getCdr(), null, this, tr);
        if (rest != LList.Empty) {
            tr.error('e', getName() + " key must be a keyword");
            return true;
        }
        return true;
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
