package gnu.commonlisp.lang;

import gnu.expr.Expression;
import gnu.expr.TryExp;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class UnwindProtect extends Syntax {
    @Override // kawa.lang.Syntax
    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("invalid syntax for unwind-protect");
        }
        Pair pair = (Pair) obj;
        return new TryExp(tr.rewrite(pair.getCar()), tr.rewrite_body(pair.getCdr()));
    }
}
