package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.util.IdentityHashMap;

/* loaded from: classes.dex */
public class Quote extends Syntax {
    protected static final int QUOTE_DEPTH = -1;
    protected boolean isQuasi;
    public static final Quote plainQuote = new Quote(LispLanguage.quote_sym, false);
    public static final Quote quasiQuote = new Quote(LispLanguage.quasiquote_sym, true);
    private static final Object WORKING = new String("(working)");
    private static final Object CYCLE = new String("(cycle)");
    static final Method vectorAppendMethod = ClassType.make("kawa.standard.vector_append").getDeclaredMethod("apply$V", 1);
    static final ClassType quoteType = ClassType.make("kawa.lang.Quote");
    static final Method consXMethod = quoteType.getDeclaredMethod("consX$V", 1);
    static final Method appendMethod = quoteType.getDeclaredMethod("append$V", 1);
    static final Method makePairMethod = Compilation.typePair.getDeclaredMethod("make", 2);
    static final Method makeVectorMethod = ClassType.make("gnu.lists.FVector").getDeclaredMethod("make", 1);

    public Quote(String name, boolean isQuasi) {
        super(name);
        this.isQuasi = isQuasi;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object expand(Object template, int depth, Translator tr) {
        IdentityHashMap seen = new IdentityHashMap();
        return expand(template, depth, null, seen, tr);
    }

    public static Object quote(Object obj, Translator tr) {
        return plainQuote.expand(obj, -1, tr);
    }

    public static Object quote(Object obj) {
        return plainQuote.expand(obj, -1, (Translator) Compilation.getCurrent());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Expression coerceExpression(Object val, Translator tr) {
        return val instanceof Expression ? (Expression) val : leaf(val, tr);
    }

    protected Expression leaf(Object val, Translator tr) {
        return new QuoteExp(val);
    }

    protected boolean expandColonForms() {
        return true;
    }

    public static Symbol makeSymbol(Namespace ns, Object local) {
        String name;
        if (local instanceof CharSequence) {
            name = ((CharSequence) local).toString();
        } else {
            name = (String) local;
        }
        return ns.getSymbol(name.intern());
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0336, code lost:
        if (r18 < r27.length) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0338, code lost:
        r0 = new gnu.lists.Pair[r18 * 2];
        java.lang.System.arraycopy(r27, 0, r0, 0, r18);
        r27 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x034b, code lost:
        r19 = r18 + 1;
        r27[r18] = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0353, code lost:
        if (r22.getCdr() != r4) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x0357, code lost:
        if ((r3 instanceof gnu.expr.Expression) == false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0359, code lost:
        r31 = gnu.lists.LList.Empty;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x035b, code lost:
        r18 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x035d, code lost:
        r18 = r18 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x035f, code lost:
        if (r18 < 0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0361, code lost:
        r22 = r27[r18];
        r31 = kawa.lang.Translator.makePair(r22, r22.getCar(), r31);
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0370, code lost:
        r22 = (gnu.lists.Pair) r22.getCdr();
        r18 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0379, code lost:
        r31 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x037e, code lost:
        if ((r3 instanceof gnu.expr.Expression) == false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0380, code lost:
        r11 = new gnu.expr.Expression[2];
        r11[1] = (gnu.expr.Expression) r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x038b, code lost:
        if (r18 != 1) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x038d, code lost:
        r11[0] = leaf(r39.getCar(), r43);
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x03a5, code lost:
        r11[0] = leaf(r31, r43);
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:?, code lost:
        return new gnu.expr.ApplyExp(kawa.lang.Quote.makePairMethod, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:?, code lost:
        return new gnu.expr.ApplyExp(kawa.lang.Quote.appendMethod, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:?, code lost:
        return r31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0075, code lost:
        if (r39 != r4) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0077, code lost:
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0327, code lost:
        r22 = r39;
        r27 = new gnu.lists.Pair[20];
        r18 = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.lang.Object expand_pair(gnu.lists.Pair r39, int r40, kawa.lang.SyntaxForm r41, java.lang.Object r42, kawa.lang.Translator r43) {
        /*
            Method dump skipped, instructions count: 959
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Quote.expand_pair(gnu.lists.Pair, int, kawa.lang.SyntaxForm, java.lang.Object, kawa.lang.Translator):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x010b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.lang.Object expand(java.lang.Object r23, int r24, kawa.lang.SyntaxForm r25, java.lang.Object r26, kawa.lang.Translator r27) {
        /*
            Method dump skipped, instructions count: 430
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Quote.expand(java.lang.Object, int, kawa.lang.SyntaxForm, java.lang.Object, kawa.lang.Translator):java.lang.Object");
    }

    private static ApplyExp makeInvokeMakeVector(Expression[] args) {
        return new ApplyExp(makeVectorMethod, args);
    }

    @Override // kawa.lang.Syntax
    public Expression rewrite(Object obj, Translator tr) {
        if (obj instanceof Pair) {
            Pair pair = (Pair) obj;
            if (pair.getCdr() == LList.Empty) {
                return coerceExpression(expand(pair.getCar(), this.isQuasi ? 1 : -1, tr), tr);
            }
        }
        return tr.syntaxError("wrong number of arguments to quote");
    }

    public static Object append$V(Object[] args) {
        Pair pair;
        int count = args.length;
        if (count == 0) {
            return LList.Empty;
        }
        Pair result = args[count - 1];
        int i = count - 1;
        while (true) {
            Object obj = result;
            i--;
            if (i >= 0) {
                Object list = args[i];
                Pair last = null;
                SyntaxForm syntax = null;
                result = null;
                while (true) {
                    if (list instanceof SyntaxForm) {
                        syntax = (SyntaxForm) list;
                        list = syntax.getDatum();
                    } else if (list == LList.Empty) {
                        break;
                    } else {
                        Pair list_pair = (Pair) list;
                        Object car = list_pair.getCar();
                        if (syntax != null && !(car instanceof SyntaxForm)) {
                            car = SyntaxForms.makeForm(car, syntax.getScope());
                        }
                        Pair new_pair = new Pair(car, null);
                        if (last == null) {
                            pair = new_pair;
                        } else {
                            last.setCdr(new_pair);
                            pair = result;
                        }
                        last = new_pair;
                        list = list_pair.getCdr();
                        result = pair;
                    }
                }
                if (last != null) {
                    last.setCdr(obj);
                } else {
                    result = obj;
                }
            } else {
                return obj;
            }
        }
    }
}
