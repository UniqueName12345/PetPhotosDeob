package kawa.lang;

import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import gnu.mapping.Environment;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class AutoloadSyntax extends Syntax implements Externalizable {
    String className;
    Environment env;
    Syntax loaded;

    public AutoloadSyntax() {
    }

    public AutoloadSyntax(String name, String className) {
        super(name);
        this.className = className;
    }

    public AutoloadSyntax(String name, String className, Environment env) {
        super(name);
        this.className = className;
        this.env = env;
    }

    public void print(PrintWriter ps) {
        ps.print(toString());
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer(100);
        sbuf.append("#<syntax ");
        if (getName() != null) {
            sbuf.append(getName());
            sbuf.append(' ');
        }
        if (this.loaded != null) {
            sbuf.append("autoloaded>");
        } else {
            sbuf.append("autoload ");
            sbuf.append(this.className);
            sbuf.append(">");
        }
        return sbuf.toString();
    }

    private void throw_error(String prefix) {
        throw new GenericError(prefix + this.className + " while autoloading " + (getName() == null ? "" : getName().toString()));
    }

    void load() {
        String name = getName();
        try {
            Object value = Class.forName(this.className).newInstance();
            if (value instanceof Syntax) {
                this.loaded = (Syntax) value;
                if (name != null && this.loaded.getName() == null) {
                    this.loaded.setName(name);
                }
            } else {
                throw_error("failed to autoload valid syntax object ");
            }
        } catch (UnboundLocationException e) {
            throw_error("missing symbol '" + e.getMessage() + "' ");
        } catch (WrongArguments e2) {
            throw_error("type error");
        } catch (ClassNotFoundException e3) {
            throw_error("failed to find class ");
        } catch (IllegalAccessException e4) {
            throw_error("illegal access in class ");
        } catch (InstantiationException e5) {
            throw_error("failed to instantiate class ");
        }
    }

    @Override // kawa.lang.Syntax
    public void scanForm(Pair st, ScopeExp defs, Translator tr) {
        if (this.loaded == null) {
            try {
                load();
            } catch (RuntimeException e) {
                tr.syntaxError(e.getMessage());
                return;
            }
        }
        this.loaded.scanForm(st, defs, tr);
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        if (this.loaded == null) {
            try {
                load();
            } catch (WrongType e) {
                return tr.syntaxError(e.getMessage());
            } catch (GenericError e2) {
                return tr.syntaxError(e2.getMessage());
            }
        }
        Syntax saveSyntax = tr.currentSyntax;
        tr.currentSyntax = this.loaded;
        try {
            return this.loaded.rewriteForm(form, tr);
        } finally {
            tr.currentSyntax = saveSyntax;
        }
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
        out.writeObject(this.className);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setName((String) in.readObject());
        this.className = (String) in.readObject();
    }
}
