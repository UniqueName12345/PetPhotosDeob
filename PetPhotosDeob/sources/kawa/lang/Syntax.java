package kawa.lang;

import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.Pair;
import gnu.mapping.Named;
import gnu.mapping.Symbol;
import gnu.text.Printable;
import java.util.Vector;

/* loaded from: classes.dex */
public abstract class Syntax implements Printable, Named {
    Object name;

    @Override // gnu.mapping.Named
    public final String getName() {
        if (this.name == null) {
            return null;
        }
        return this.name instanceof Symbol ? ((Symbol) this.name).getName() : this.name.toString();
    }

    @Override // gnu.mapping.Named
    public Object getSymbol() {
        return this.name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    @Override // gnu.mapping.Named
    public void setName(String name) {
        this.name = name;
    }

    public Syntax() {
    }

    public Syntax(Object name) {
        setName(name);
    }

    public Expression rewrite(Object obj, Translator tr) {
        throw new InternalError("rewrite method not defined");
    }

    public Expression rewriteForm(Object form, Translator tr) {
        return form instanceof Pair ? rewriteForm((Pair) form, tr) : tr.syntaxError("non-list form for " + this);
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return rewrite(form.getCdr(), tr);
    }

    public void scanForm(Pair st, ScopeExp defs, Translator tr) {
        boolean ok = scanForDefinitions(st, tr.formStack, defs, tr);
        if (!ok) {
            tr.formStack.add(new ErrorExp("syntax error expanding " + this));
        }
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        forms.addElement(st);
        return true;
    }

    @Override // gnu.text.Printable
    public void print(Consumer out) {
        out.write("#<syntax ");
        String name = getName();
        if (name == null) {
            name = "<unnamed>";
        }
        out.write(name);
        out.write(62);
    }
}
