package kawa.standard;

import gnu.expr.Expression;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class module_static extends Syntax {
    public static final module_static module_static = new module_static();

    static {
        module_static.setName("module-static");
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:54:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    @Override // kawa.lang.Syntax
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean scanForDefinitions(gnu.lists.Pair r12, java.util.Vector r13, gnu.expr.ScopeExp r14, kawa.lang.Translator r15) {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.module_static.scanForDefinitions(gnu.lists.Pair, java.util.Vector, gnu.expr.ScopeExp, kawa.lang.Translator):boolean");
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
