package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;

/* loaded from: classes.dex */
public abstract class ScopeExp extends Expression {
    static int counter;
    Declaration decls;
    protected int frameSize;
    public int id;
    Declaration last;
    public ScopeExp outer;
    private Scope scope;

    public Declaration firstDecl() {
        return this.decls;
    }

    public Scope getVarScope() {
        Scope sc = this.scope;
        if (sc == null) {
            Scope sc2 = new Scope();
            this.scope = sc2;
            return sc2;
        }
        return sc;
    }

    public void popScope(CodeAttr code) {
        for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
            decl.var = null;
        }
        code.popScope();
        this.scope = null;
    }

    public void add(Declaration decl) {
        if (this.last == null) {
            this.decls = decl;
        } else {
            this.last.next = decl;
        }
        this.last = decl;
        decl.context = this;
    }

    public void add(Declaration prev, Declaration decl) {
        if (prev == null) {
            decl.next = this.decls;
            this.decls = decl;
        } else {
            decl.next = prev.next;
            prev.next = decl;
        }
        if (this.last == prev) {
            this.last = decl;
        }
        decl.context = this;
    }

    public void replaceFollowing(Declaration prev, Declaration newDecl) {
        Declaration oldDecl;
        if (prev == null) {
            oldDecl = this.decls;
            this.decls = newDecl;
        } else {
            oldDecl = prev.next;
            prev.next = newDecl;
        }
        newDecl.next = oldDecl.next;
        if (this.last == oldDecl) {
            this.last = newDecl;
        }
        oldDecl.next = null;
        newDecl.context = this;
    }

    public void remove(Declaration decl) {
        Declaration prev = null;
        for (Declaration cur = firstDecl(); cur != null; cur = cur.nextDecl()) {
            if (cur == decl) {
                remove(prev, decl);
                return;
            }
            prev = cur;
        }
    }

    public void remove(Declaration prev, Declaration decl) {
        if (prev == null) {
            this.decls = decl.next;
        } else {
            prev.next = decl.next;
        }
        if (this.last == decl) {
            this.last = prev;
        }
    }

    public ScopeExp() {
        int i = counter + 1;
        counter = i;
        this.id = i;
    }

    public LambdaExp currentLambda() {
        for (ScopeExp exp = this; exp != null; exp = exp.outer) {
            if (exp instanceof LambdaExp) {
                return (LambdaExp) exp;
            }
        }
        return null;
    }

    public ScopeExp topLevel() {
        ScopeExp exp = this;
        while (true) {
            ScopeExp outer = exp.outer;
            if (outer == null || (outer instanceof ModuleExp)) {
                break;
            }
            exp = outer;
        }
        return exp;
    }

    public ModuleExp currentModule() {
        for (ScopeExp exp = this; exp != null; exp = exp.outer) {
            if (exp instanceof ModuleExp) {
                return (ModuleExp) exp;
            }
        }
        return null;
    }

    public Declaration lookup(Object sym) {
        if (sym != null) {
            for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
                if (sym.equals(decl.symbol)) {
                    return decl;
                }
            }
        }
        return null;
    }

    public Declaration lookup(Object sym, Language language, int namespace) {
        for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (sym.equals(decl.symbol) && language.hasNamespace(decl, namespace)) {
                return decl;
            }
        }
        return null;
    }

    public Declaration getNoDefine(Object name) {
        Declaration decl = lookup(name);
        if (decl == null) {
            Declaration decl2 = addDeclaration(name);
            decl2.flags |= 66048;
            return decl2;
        }
        return decl;
    }

    public Declaration getDefine(Object name, char severity, Compilation parser) {
        Declaration decl = lookup(name);
        if (decl == null) {
            return addDeclaration(name);
        }
        if ((decl.flags & 66048) != 0) {
            decl.flags &= -66049;
            return decl;
        }
        Declaration newDecl = addDeclaration(name);
        duplicateDeclarationError(decl, newDecl, parser);
        return newDecl;
    }

    public static void duplicateDeclarationError(Declaration oldDecl, Declaration newDecl, Compilation comp) {
        comp.error('e', newDecl, "duplicate declaration of '", "'");
        comp.error('e', oldDecl, "(this is the previous declaration of '", "')");
    }

    public final Declaration addDeclaration(Object name) {
        Declaration decl = new Declaration(name);
        add(decl);
        return decl;
    }

    public final Declaration addDeclaration(Object name, Type type) {
        Declaration decl = new Declaration(name, type);
        add(decl);
        return decl;
    }

    public final void addDeclaration(Declaration decl) {
        add(decl);
    }

    public int countDecls() {
        int n = 0;
        for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
            n++;
        }
        return n;
    }

    public int countNonDynamicDecls() {
        int n = 0;
        for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (!decl.getFlag(268435456L)) {
                n++;
            }
        }
        return n;
    }

    public static int nesting(ScopeExp sc) {
        int n = 0;
        while (sc != null) {
            sc = sc.outer;
            n++;
        }
        return n;
    }

    public boolean nestedIn(ScopeExp outer) {
        for (ScopeExp sc = this; sc != null; sc = sc.outer) {
            if (sc == outer) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setIndexes() {
        Declaration decl = firstDecl();
        int i = 0;
        while (decl != null) {
            decl.evalIndex = i;
            decl = decl.nextDecl();
            i++;
        }
        this.frameSize = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitScopeExp(this, d);
    }

    @Override // gnu.expr.Expression, gnu.mapping.Procedure
    public String toString() {
        return getClass().getName() + "#" + this.id;
    }
}
