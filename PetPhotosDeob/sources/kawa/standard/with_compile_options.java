package kawa.standard;

import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.util.Stack;
import kawa.lang.Syntax;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class with_compile_options extends Syntax {
    public static final with_compile_options with_compile_options = new with_compile_options();

    static {
        with_compile_options.setName("with-compile-options");
    }

    @Override // kawa.lang.Syntax
    public void scanForm(Pair form, ScopeExp defs, Translator tr) {
        Stack stack = new Stack();
        Object rest = getOptions(form.getCdr(), stack, this, tr);
        if (rest != LList.Empty) {
            if (rest == form.getCdr()) {
                tr.scanBody(rest, defs, false);
                return;
            }
            Pair rest2 = new Pair(stack, tr.scanBody(rest, defs, true));
            tr.currentOptions.popOptionValues(stack);
            tr.formStack.add(Translator.makePair(form, form.getCar(), rest2));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x003a, code lost:
        return kawa.lang.Translator.wrapSyntax(r13, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0016, code lost:
        if (r7 != false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0018, code lost:
        r16.error('e', "no option keyword in " + r15.getName());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object getOptions(java.lang.Object r13, java.util.Stack r14, kawa.lang.Syntax r15, kawa.lang.Translator r16) {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.with_compile_options.getOptions(java.lang.Object, java.util.Stack, kawa.lang.Syntax, kawa.lang.Translator):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0041 A[Catch: all -> 0x004d, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x004d, blocks: (B:7:0x0022, B:9:0x002a, B:10:0x002e, B:14:0x0041), top: B:19:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002a A[Catch: all -> 0x004d, TryCatch #0 {all -> 0x004d, blocks: (B:7:0x0022, B:9:0x002a, B:10:0x002e, B:14:0x0041), top: B:19:0x0022 }] */
    @Override // kawa.lang.Syntax
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public gnu.expr.Expression rewriteForm(gnu.lists.Pair r10, kawa.lang.Translator r11) {
        /*
            r9 = this;
            java.lang.Object r2 = r10.getCdr()
            boolean r7 = r2 instanceof gnu.lists.Pair
            if (r7 == 0) goto L37
            r3 = r2
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            java.lang.Object r7 = r3.getCar()
            boolean r7 = r7 instanceof java.util.Stack
            if (r7 == 0) goto L37
            java.lang.Object r6 = r3.getCar()
            java.util.Stack r6 = (java.util.Stack) r6
            java.lang.Object r4 = r3.getCdr()
            gnu.text.Options r7 = r11.currentOptions
            r7.pushOptionValues(r6)
        L22:
            gnu.expr.Expression r5 = r11.rewrite_body(r4)     // Catch: java.lang.Throwable -> L4d
            boolean r7 = r5 instanceof gnu.expr.BeginExp     // Catch: java.lang.Throwable -> L4d
            if (r7 == 0) goto L41
            r0 = r5
            gnu.expr.BeginExp r0 = (gnu.expr.BeginExp) r0     // Catch: java.lang.Throwable -> L4d
            r1 = r0
        L2e:
            r1.setCompileOptions(r6)     // Catch: java.lang.Throwable -> L4d
            gnu.text.Options r7 = r11.currentOptions
            r7.popOptionValues(r6)
            return r1
        L37:
            java.util.Stack r6 = new java.util.Stack
            r6.<init>()
            java.lang.Object r4 = getOptions(r2, r6, r9, r11)
            goto L22
        L41:
            gnu.expr.BeginExp r1 = new gnu.expr.BeginExp     // Catch: java.lang.Throwable -> L4d
            r7 = 1
            gnu.expr.Expression[] r7 = new gnu.expr.Expression[r7]     // Catch: java.lang.Throwable -> L4d
            r8 = 0
            r7[r8] = r5     // Catch: java.lang.Throwable -> L4d
            r1.<init>(r7)     // Catch: java.lang.Throwable -> L4d
            goto L2e
        L4d:
            r7 = move-exception
            gnu.text.Options r8 = r11.currentOptions
            r8.popOptionValues(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.with_compile_options.rewriteForm(gnu.lists.Pair, kawa.lang.Translator):gnu.expr.Expression");
    }
}
