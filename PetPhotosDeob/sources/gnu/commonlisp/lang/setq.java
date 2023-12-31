package gnu.commonlisp.lang;

import gnu.expr.BeginExp;
import gnu.expr.Expression;
import gnu.expr.SetExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class setq extends Syntax {
    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        Object obj;
        Object obj2 = form.getCdr();
        Vector results = null;
        while (obj2 != LList.Empty) {
            if (!(obj2 instanceof Pair)) {
                return tr.syntaxError("invalid syntax for setq");
            }
            Pair pair = (Pair) obj2;
            Object sym = pair.getCar();
            if ((sym instanceof Symbol) || (sym instanceof String)) {
                obj = sym;
            } else if (sym == CommonLisp.FALSE) {
                obj = "nil";
            } else {
                return tr.syntaxError("invalid variable name in setq");
            }
            Object obj3 = pair.getCdr();
            if (!(obj3 instanceof Pair)) {
                return tr.syntaxError("wrong number of arguments for setq");
            }
            Pair pair2 = (Pair) obj3;
            Expression value = tr.rewrite(pair2.getCar());
            obj2 = pair2.getCdr();
            SetExp sexp = new SetExp(obj, value);
            sexp.setFlag(8);
            if (obj2 == LList.Empty) {
                sexp.setHasValue(true);
                if (results == null) {
                    return sexp;
                }
            }
            if (results == null) {
                results = new Vector(10);
            }
            results.addElement(sexp);
        }
        if (results == null) {
            return CommonLisp.nilExpr;
        }
        Expression[] stmts = new Expression[results.size()];
        results.copyInto(stmts);
        return new BeginExp(stmts);
    }
}
