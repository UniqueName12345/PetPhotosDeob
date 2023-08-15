package kawa.lang;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Procedure;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* loaded from: classes.dex */
public class Macro extends Syntax implements Printable, Externalizable {
    private ScopeExp capturedScope;
    public Object expander;
    private boolean hygienic;
    Object instance;

    public ScopeExp getCapturedScope() {
        if (this.capturedScope == null) {
            if (this.instance instanceof ModuleExp) {
                this.capturedScope = (ModuleExp) this.instance;
            } else if (this.instance != null) {
                this.capturedScope = ModuleInfo.findFromInstance(this.instance).getModuleExp();
            }
        }
        return this.capturedScope;
    }

    public void setCapturedScope(ScopeExp scope) {
        this.capturedScope = scope;
    }

    public static Macro make(Declaration decl) {
        Macro mac = new Macro(decl.getSymbol());
        decl.setSyntax();
        mac.capturedScope = decl.context;
        return mac;
    }

    public static Macro makeNonHygienic(Object name, Procedure expander) {
        Macro mac = new Macro(name, expander);
        mac.hygienic = false;
        return mac;
    }

    public static Macro makeNonHygienic(Object name, Procedure expander, Object instance) {
        Macro mac = new Macro(name, expander);
        mac.hygienic = false;
        mac.instance = instance;
        return mac;
    }

    public static Macro make(Object name, Procedure expander) {
        Macro mac = new Macro(name, expander);
        return mac;
    }

    public static Macro make(Object name, Procedure expander, Object instance) {
        Macro mac = new Macro(name, expander);
        mac.instance = instance;
        return mac;
    }

    public final boolean isHygienic() {
        return this.hygienic;
    }

    public final void setHygienic(boolean hygienic) {
        this.hygienic = hygienic;
    }

    public Macro() {
        this.hygienic = true;
    }

    public Macro(Macro old) {
        this.hygienic = true;
        this.name = old.name;
        this.expander = old.expander;
        this.hygienic = old.hygienic;
    }

    public Macro(Object name, Procedure expander) {
        super(name);
        this.hygienic = true;
        this.expander = new QuoteExp(expander);
    }

    public Macro(Object name) {
        super(name);
        this.hygienic = true;
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        return tr.rewrite(expand(form, tr));
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Object form, Translator tr) {
        return tr.rewrite(expand(form, tr));
    }

    public String toString() {
        return "#<macro " + getName() + '>';
    }

    @Override // kawa.lang.Syntax, gnu.text.Printable
    public void print(Consumer out) {
        out.write("#<macro ");
        out.write(getName());
        out.write(62);
    }

    public Object expand(Object form, Translator tr) {
        Procedure pr;
        Object result;
        try {
            Object exp = this.expander;
            if ((exp instanceof Procedure) && !(exp instanceof Expression)) {
                pr = (Procedure) exp;
            } else {
                if (!(exp instanceof Expression)) {
                    Macro savedMacro = tr.currentMacroDefinition;
                    tr.currentMacroDefinition = this;
                    exp = tr.rewrite(exp);
                    this.expander = exp;
                    tr.currentMacroDefinition = savedMacro;
                }
                pr = (Procedure) ((Expression) exp).eval(tr.getGlobalEnvironment());
            }
            if (!this.hygienic) {
                form = Quote.quote(form, tr);
                int nargs = Translator.listLength(form);
                if (nargs <= 0) {
                    return tr.syntaxError("invalid macro argument list to " + this);
                }
                Object[] args = new Object[nargs - 1];
                for (int i = 0; i < nargs; i++) {
                    Pair pair = (Pair) form;
                    if (i > 0) {
                        args[i - 1] = pair.getCar();
                    }
                    form = pair.getCdr();
                }
                result = pr.applyN(args);
            } else {
                result = pr.apply1(form);
            }
            if ((form instanceof PairWithPosition) && (result instanceof Pair) && !(result instanceof PairWithPosition)) {
                Pair p = (Pair) result;
                return new PairWithPosition((PairWithPosition) form, p.getCar(), p.getCdr());
            }
            return result;
        } catch (Throwable ex) {
            return tr.syntaxError("evaluating syntax transformer '" + getName() + "' threw " + ex);
        }
    }

    @Override // kawa.lang.Syntax
    public void scanForm(Pair st, ScopeExp defs, Translator tr) {
        String save_filename = tr.getFileName();
        int save_line = tr.getLineNumber();
        int save_column = tr.getColumnNumber();
        Syntax saveSyntax = tr.currentSyntax;
        try {
            tr.setLine(st);
            tr.currentSyntax = this;
            Object x = expand(st, tr);
            tr.scanForm(x, defs);
        } finally {
            tr.setLine(save_filename, save_line, save_column);
            tr.currentSyntax = saveSyntax;
        }
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
        out.writeObject(((QuoteExp) this.expander).getValue());
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName((String) in.readObject());
        this.expander = new QuoteExp(in.readObject());
    }
}
