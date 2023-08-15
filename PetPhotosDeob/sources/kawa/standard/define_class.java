package kawa.standard;

import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class define_class extends Syntax {
    public static final define_class define_class = new define_class("define-class", false);
    public static final define_class define_simple_class = new define_class("define-simple-class", true);
    boolean isSimple;
    object objectSyntax;

    define_class(object objectSyntax, boolean isSimple) {
        this.objectSyntax = objectSyntax;
        this.isSimple = isSimple;
    }

    define_class(String name, boolean isSimple) {
        super(name);
        this.objectSyntax = object.objectSyntax;
        this.isSimple = isSimple;
    }

    @Override // kawa.lang.Syntax
    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        Object st_cdr = st.getCdr();
        SyntaxForm nameSyntax = null;
        while (st_cdr instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) st_cdr;
            st_cdr = nameSyntax.getDatum();
        }
        if (!(st_cdr instanceof Pair)) {
            return super.scanForDefinitions(st, forms, defs, tr);
        }
        Pair p = (Pair) st_cdr;
        Object name = p.getCar();
        while (name instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) name;
            name = nameSyntax.getDatum();
        }
        Object name2 = tr.namespaceResolve(name);
        if (!(name2 instanceof String) && !(name2 instanceof Symbol)) {
            tr.error('e', "missing class name");
            return false;
        }
        Declaration decl = tr.define(name2, nameSyntax, defs);
        if (p instanceof PairWithPosition) {
            decl.setLocation((PairWithPosition) p);
        }
        ClassExp oexp = new ClassExp(this.isSimple);
        decl.noteValue(oexp);
        decl.setFlag(536887296L);
        decl.setType(this.isSimple ? Compilation.typeClass : Compilation.typeClassType);
        tr.mustCompileHere();
        String cname = name2 instanceof Symbol ? ((Symbol) name2).getName() : name2.toString();
        int nlen = cname.length();
        if (nlen > 2 && cname.charAt(0) == '<' && cname.charAt(nlen - 1) == '>') {
            cname = cname.substring(1, nlen - 1);
        }
        oexp.setName(cname);
        Object members = p.getCdr();
        while (members instanceof SyntaxForm) {
            SyntaxForm nameSyntax2 = members;
            nameSyntax = nameSyntax2;
            members = nameSyntax.getDatum();
        }
        if (!(members instanceof Pair)) {
            tr.error('e', "missing class members");
            return false;
        }
        Pair p2 = (Pair) members;
        ScopeExp save_scope = tr.currentScope();
        if (nameSyntax != null) {
            tr.setCurrentScope(nameSyntax.getScope());
        }
        Object[] saved = this.objectSyntax.scanClassDef(p2, oexp, tr);
        if (nameSyntax != null) {
            tr.setCurrentScope(save_scope);
        }
        if (saved == null) {
            return false;
        }
        forms.addElement(Translator.makePair(st, this, Translator.makePair(p2, decl, saved)));
        return true;
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        Declaration decl = null;
        Object form_cdr = form.getCdr();
        if (form_cdr instanceof Pair) {
            form = (Pair) form_cdr;
            Object form_car = form.getCar();
            if (!(form_car instanceof Declaration)) {
                return tr.syntaxError(getName() + " can only be used in <body>");
            }
            decl = (Declaration) form_car;
        }
        ClassExp oexp = (ClassExp) decl.getValue();
        this.objectSyntax.rewriteClassDef((Object[]) form.getCdr(), tr);
        SetExp sexp = new SetExp(decl, (Expression) oexp);
        sexp.setDefining(true);
        return sexp;
    }
}
