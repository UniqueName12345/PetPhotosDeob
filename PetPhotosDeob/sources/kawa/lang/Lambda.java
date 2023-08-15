package kawa.lang;

import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.LangExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.SimpleSymbol;
import kawa.standard.object;

/* loaded from: classes.dex */
public class Lambda extends Syntax {
    public static final Keyword nameKeyword = Keyword.make("name");
    public Expression defaultDefault = QuoteExp.falseExp;
    public Object keyKeyword;
    public Object optionalKeyword;
    public Object restKeyword;

    public void setKeywords(Object optional, Object rest, Object key) {
        this.optionalKeyword = optional;
        this.restKeyword = rest;
        this.keyKeyword = key;
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        Expression exp = rewrite(form.getCdr(), tr);
        Translator.setLine(exp, form);
        return exp;
    }

    @Override // kawa.lang.Syntax
    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing formals in lambda");
        }
        int old_errors = tr.getMessages().getErrorCount();
        LambdaExp lexp = new LambdaExp();
        Pair pair = (Pair) obj;
        Translator.setLine(lexp, pair);
        rewrite(lexp, pair.getCar(), pair.getCdr(), tr, null);
        if (tr.getMessages().getErrorCount() > old_errors) {
            return new ErrorExp("bad lambda expression");
        }
        return lexp;
    }

    public void rewrite(LambdaExp lexp, Object formals, Object body, Translator tr, TemplateScope templateScopeRest) {
        rewriteFormals(lexp, formals, tr, templateScopeRest);
        if (body instanceof PairWithPosition) {
            lexp.setFile(((PairWithPosition) body).getFileName());
        }
        rewriteBody(lexp, rewriteAttrs(lexp, body, tr), tr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:198:?, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00cd, code lost:
        r29.syntaxError(r26.optionalKeyword.toString() + " after " + r26.restKeyword + " or " + r26.keyKeyword);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void rewriteFormals(gnu.expr.LambdaExp r27, java.lang.Object r28, kawa.lang.Translator r29, kawa.lang.TemplateScope r30) {
        /*
            Method dump skipped, instructions count: 1562
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Lambda.rewriteFormals(gnu.expr.LambdaExp, java.lang.Object, kawa.lang.Translator, kawa.lang.TemplateScope):void");
    }

    private static void addParam(Declaration decl, ScopeExp templateScope, LambdaExp lexp, Translator tr) {
        if (templateScope != null) {
            decl = tr.makeRenamedAlias(decl, templateScope);
        }
        lexp.addDeclaration(decl);
        if (templateScope != null) {
            decl.context = templateScope;
        }
    }

    public Object rewriteAttrs(LambdaExp lexp, Object body, Translator tr) {
        String accessFlagName = null;
        String allocationFlagName = null;
        int accessFlag = 0;
        int allocationFlag = 0;
        SyntaxForm syntax0 = null;
        while (true) {
            if (body instanceof SyntaxForm) {
                syntax0 = (SyntaxForm) body;
                body = syntax0.getDatum();
            } else if (!(body instanceof Pair)) {
                break;
            } else {
                Pair pair1 = (Pair) body;
                Object attrName = Translator.stripSyntax(pair1.getCar());
                if (tr.matches(attrName, "::")) {
                    attrName = null;
                } else if (!(attrName instanceof Keyword)) {
                    break;
                }
                SyntaxForm syntax1 = syntax0;
                Object pair1_cdr = pair1.getCdr();
                while (pair1_cdr instanceof SyntaxForm) {
                    SyntaxForm syntax12 = pair1_cdr;
                    syntax1 = syntax12;
                    pair1_cdr = syntax1.getDatum();
                }
                if (!(pair1_cdr instanceof Pair)) {
                    break;
                }
                Pair pair2 = pair1_cdr;
                if (attrName == null) {
                    if (lexp.isClassMethod() && "*init*".equals(lexp.getName())) {
                        tr.error('e', "explicit return type for '*init*' method");
                    } else {
                        lexp.body = new LangExp(new Object[]{pair2, syntax1});
                    }
                } else if (attrName == object.accessKeyword) {
                    Expression attrExpr = tr.rewrite_car(pair2, syntax1);
                    if (attrExpr instanceof QuoteExp) {
                        Object attrValue = ((QuoteExp) attrExpr).getValue();
                        if ((attrValue instanceof SimpleSymbol) || (attrValue instanceof CharSequence)) {
                            if (lexp.nameDecl == null) {
                                tr.error('e', "access: not allowed for anonymous function");
                            } else {
                                String value = attrValue.toString();
                                if ("private".equals(value)) {
                                    accessFlag = 16777216;
                                } else if ("protected".equals(value)) {
                                    accessFlag = Declaration.PROTECTED_ACCESS;
                                } else if ("public".equals(value)) {
                                    accessFlag = Declaration.PUBLIC_ACCESS;
                                } else if ("package".equals(value)) {
                                    accessFlag = Declaration.PACKAGE_ACCESS;
                                } else {
                                    tr.error('e', "unknown access specifier");
                                }
                                if (accessFlagName != null && value != null) {
                                    tr.error('e', "duplicate access specifiers - " + accessFlagName + " and " + value);
                                }
                                accessFlagName = value;
                            }
                        }
                    }
                    tr.error('e', "access: value not a constant symbol or string");
                } else if (attrName == object.allocationKeyword) {
                    Expression attrExpr2 = tr.rewrite_car(pair2, syntax1);
                    if (attrExpr2 instanceof QuoteExp) {
                        Object attrValue2 = ((QuoteExp) attrExpr2).getValue();
                        if ((attrValue2 instanceof SimpleSymbol) || (attrValue2 instanceof CharSequence)) {
                            if (lexp.nameDecl == null) {
                                tr.error('e', "allocation: not allowed for anonymous function");
                            } else {
                                String value2 = attrValue2.toString();
                                if ("class".equals(value2) || "static".equals(value2)) {
                                    allocationFlag = 2048;
                                } else if ("instance".equals(value2)) {
                                    allocationFlag = 4096;
                                } else {
                                    tr.error('e', "unknown allocation specifier");
                                }
                                if (allocationFlagName != null && value2 != null) {
                                    tr.error('e', "duplicate allocation specifiers - " + allocationFlagName + " and " + value2);
                                }
                                allocationFlagName = value2;
                            }
                        }
                    }
                    tr.error('e', "allocation: value not a constant symbol or string");
                } else if (attrName == object.throwsKeyword) {
                    Object attrValue3 = pair2.getCar();
                    int count = Translator.listLength(attrValue3);
                    if (count < 0) {
                        tr.error('e', "throws: not followed by a list");
                    } else {
                        Expression[] exps = new Expression[count];
                        SyntaxForm syntax2 = syntax1;
                        for (int i = 0; i < count; i++) {
                            while (attrValue3 instanceof SyntaxForm) {
                                syntax2 = (SyntaxForm) attrValue3;
                                attrValue3 = syntax2.getDatum();
                            }
                            Pair pair3 = (Pair) attrValue3;
                            exps[i] = tr.rewrite_car(pair3, syntax2);
                            Translator.setLine(exps[i], pair3);
                            attrValue3 = pair3.getCdr();
                        }
                        lexp.setExceptions(exps);
                    }
                } else if (attrName == nameKeyword) {
                    Expression attrExpr3 = tr.rewrite_car(pair2, syntax1);
                    if (attrExpr3 instanceof QuoteExp) {
                        lexp.setName(((QuoteExp) attrExpr3).getValue().toString());
                    }
                } else {
                    tr.error('w', "unknown procedure property " + attrName);
                }
                body = pair2.getCdr();
            }
        }
        int accessFlag2 = accessFlag | allocationFlag;
        if (accessFlag2 != 0) {
            lexp.nameDecl.setFlag(accessFlag2);
        }
        if (syntax0 != null) {
            return SyntaxForms.fromDatumIfNeeded(body, syntax0);
        }
        return body;
    }

    public Object skipAttrs(LambdaExp lexp, Object body, Translator tr) {
        while (body instanceof Pair) {
            Pair pair = (Pair) body;
            if (!(pair.getCdr() instanceof Pair)) {
                break;
            }
            Object attrName = pair.getCar();
            if (!tr.matches(attrName, "::") && !(attrName instanceof Keyword)) {
                break;
            }
            body = ((Pair) pair.getCdr()).getCdr();
        }
        return body;
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x01cc, code lost:
        if ((r22 instanceof java.lang.Class) == false) goto L65;
     */
    /* JADX WARN: Removed duplicated region for block: B:59:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void rewriteBody(gnu.expr.LambdaExp r27, java.lang.Object r28, kawa.lang.Translator r29) {
        /*
            Method dump skipped, instructions count: 568
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Lambda.rewriteBody(gnu.expr.LambdaExp, java.lang.Object, kawa.lang.Translator):void");
    }

    @Override // kawa.lang.Syntax, gnu.text.Printable
    public void print(Consumer out) {
        out.write("#<builtin lambda>");
    }
}
