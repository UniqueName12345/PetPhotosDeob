package kawa.standard;

import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Lambda;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class define extends Syntax {
    public static final define defineRaw = new define(SchemeCompilation.lambda);
    Lambda lambda;

    String getName(int options) {
        if ((options & 4) != 0) {
            return "define-private";
        }
        if ((options & 8) != 0) {
            return "define-constant";
        }
        return "define";
    }

    public define(Lambda lambda) {
        this.lambda = lambda;
    }

    @Override // kawa.lang.Syntax
    public void scanForm(Pair st, ScopeExp defs, Translator tr) {
        Pair p1 = (Pair) st.getCdr();
        Pair p2 = (Pair) p1.getCdr();
        Pair p3 = (Pair) p2.getCdr();
        Pair p4 = (Pair) p3.getCdr();
        SyntaxForm nameSyntax = null;
        Object name = p1.getCar();
        while (name instanceof SyntaxForm) {
            nameSyntax = (SyntaxForm) name;
            name = nameSyntax.getDatum();
        }
        int options = ((Number) Translator.stripSyntax(p2.getCar())).intValue();
        boolean makePrivate = (options & 4) != 0;
        boolean makeConstant = (options & 8) != 0;
        tr.currentScope();
        Object name2 = tr.namespaceResolve(name);
        if (!(name2 instanceof Symbol)) {
            tr.error('e', "'" + name2 + "' is not a valid identifier");
            name2 = null;
        }
        Object savePos = tr.pushPositionOf(p1);
        Declaration decl = tr.define(name2, nameSyntax, defs);
        tr.popPositionOf(savePos);
        Object name3 = decl.getSymbol();
        if (makePrivate) {
            decl.setFlag(16777216L);
            decl.setPrivate(true);
        }
        if (makeConstant) {
            decl.setFlag(JSONzip.int14);
        }
        decl.setFlag(262144L);
        if ((options & 2) != 0) {
            LambdaExp lexp = new LambdaExp();
            lexp.setSymbol(name3);
            if (Compilation.inlineOk) {
                decl.setProcedureDecl(true);
                decl.setType(Compilation.typeProcedure);
                lexp.nameDecl = decl;
            }
            Object formals = p4.getCar();
            Object body = p4.getCdr();
            Translator.setLine(lexp, p1);
            this.lambda.rewriteFormals(lexp, formals, tr, null);
            Object realBody = this.lambda.rewriteAttrs(lexp, body, tr);
            if (realBody != body) {
                p2 = new Pair(p2.getCar(), new Pair(p3.getCar(), new Pair(formals, realBody)));
            }
            decl.noteValue(lexp);
        }
        if ((defs instanceof ModuleExp) && !makePrivate && (!Compilation.inlineOk || tr.sharedModuleDefs())) {
            decl.setCanWrite(true);
        }
        if ((options & 1) != 0) {
            decl.setTypeExp(new LangExp(p3));
            decl.setFlag(8192L);
        }
        Pair st2 = Translator.makePair(st, this, Translator.makePair(p1, decl, p2));
        Translator.setLine(decl, p1);
        tr.formStack.addElement(st2);
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        Expression value;
        Pair p1 = (Pair) form.getCdr();
        Pair p2 = (Pair) p1.getCdr();
        Pair p3 = (Pair) p2.getCdr();
        Pair p4 = (Pair) p3.getCdr();
        Object name = Translator.stripSyntax(p1.getCar());
        int options = ((Number) Translator.stripSyntax(p2.getCar())).intValue();
        boolean makePrivate = (options & 4) != 0;
        if (!(name instanceof Declaration)) {
            return tr.syntaxError(getName(options) + " is only allowed in a <body>");
        }
        Declaration decl = (Declaration) name;
        if (decl.getFlag(8192L)) {
            Expression texp = decl.getTypeExp();
            if (texp instanceof LangExp) {
                Pair typeSpecPair = (Pair) ((LangExp) texp).getLangValue();
                decl.setType(tr.exp2Type(typeSpecPair));
            }
        }
        if ((options & 2) != 0) {
            LambdaExp lexp = (LambdaExp) decl.getValue();
            Object body = p4.getCdr();
            this.lambda.rewriteBody(lexp, body, tr);
            value = lexp;
            if (!Compilation.inlineOk) {
                decl.noteValue(null);
            }
        } else {
            value = tr.rewrite(p4.getCar());
            decl.noteValue(((decl.context instanceof ModuleExp) && !makePrivate && decl.getCanWrite()) ? null : value);
        }
        SetExp sexp = new SetExp(decl, value);
        sexp.setDefining(true);
        if (makePrivate && !(tr.currentScope() instanceof ModuleExp)) {
            tr.error('w', "define-private not at top level " + tr.currentScope());
            return sexp;
        }
        return sexp;
    }
}
