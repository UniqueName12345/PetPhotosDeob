package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.PatternScope;
import kawa.lang.Quote;
import kawa.lang.SyntaxTemplate;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class syntax extends Quote {
    public static final syntax syntax = new syntax("syntax", false);
    public static final syntax quasiSyntax = new syntax("quasisyntax", true);
    static final ClassType typeTemplateScope = ClassType.make("kawa.lang.TemplateScope");
    static final Method makeTemplateScopeMethod = typeTemplateScope.getDeclaredMethod("make", 0);

    public syntax(String name, boolean isQuasi) {
        super(name, isQuasi);
    }

    @Override // kawa.lang.Quote
    protected boolean expandColonForms() {
        return false;
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        if (form.getCdr() instanceof Pair) {
            Pair form2 = (Pair) form.getCdr();
            if (form2.getCdr() == LList.Empty) {
                Declaration saveTemplateScopeDecl = tr.templateScopeDecl;
                if (saveTemplateScopeDecl == null) {
                    tr.letStart();
                    Expression init = new ApplyExp(makeTemplateScopeMethod, Expression.noExpressions);
                    Declaration templateScopeDecl = tr.letVariable(null, typeTemplateScope, init);
                    templateScopeDecl.setCanRead();
                    tr.templateScopeDecl = templateScopeDecl;
                    tr.letEnter();
                }
                try {
                    Expression body = coerceExpression(expand(form2.getCar(), this.isQuasi ? 1 : -1, tr), tr);
                    if (saveTemplateScopeDecl == null) {
                        body = tr.letDone(body);
                    }
                    return body;
                } finally {
                    tr.templateScopeDecl = saveTemplateScopeDecl;
                }
            }
        }
        return tr.syntaxError("syntax forms requires a single form");
    }

    @Override // kawa.lang.Quote
    protected Expression leaf(Object val, Translator tr) {
        return makeSyntax(val, tr);
    }

    static Expression makeSyntax(Object form, Translator tr) {
        SyntaxTemplate template = new SyntaxTemplate(form, null, tr);
        Expression matchArray = QuoteExp.nullExp;
        PatternScope patternScope = tr.patternScope;
        if (patternScope != null && patternScope.matchArray != null) {
            matchArray = new ReferenceExp(patternScope.matchArray);
        }
        Expression[] args = {new QuoteExp(template), matchArray, new ReferenceExp(tr.templateScopeDecl)};
        return new ApplyExp(ClassType.make("kawa.lang.SyntaxTemplate").getDeclaredMethod("execute", 2), args);
    }
}
