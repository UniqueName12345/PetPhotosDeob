package kawa.standard;

import gnu.expr.Special;
import kawa.lang.Lambda;
import kawa.repl;

/* loaded from: classes.dex */
public class SchemeCompilation {
    public static final Lambda lambda = new Lambda();
    public static final repl repl = new repl(Scheme.instance);

    static {
        lambda.setKeywords(Special.optional, Special.rest, Special.key);
    }
}
