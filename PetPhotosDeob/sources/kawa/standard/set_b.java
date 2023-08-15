package kawa.standard;

import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.kawa.functions.CompilationHelpers;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class set_b extends Syntax {
    public static final set_b set = new set_b();

    static {
        set.setName("set!");
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        Object o1 = form.getCdr();
        SyntaxForm syntax = null;
        while (o1 instanceof SyntaxForm) {
            syntax = (SyntaxForm) o1;
            o1 = syntax.getDatum();
        }
        if (!(o1 instanceof Pair)) {
            return tr.syntaxError("missing name");
        }
        Pair p1 = (Pair) o1;
        Expression name = tr.rewrite_car(p1, syntax);
        Object o2 = p1.getCdr();
        while (o2 instanceof SyntaxForm) {
            syntax = (SyntaxForm) o2;
            o2 = syntax.getDatum();
        }
        if (o2 instanceof Pair) {
            Pair p2 = (Pair) o2;
            if (p2.getCdr() == LList.Empty) {
                Expression value = tr.rewrite_car(p2, syntax);
                if (name instanceof ApplyExp) {
                    ApplyExp aexp = (ApplyExp) name;
                    Expression[] args = aexp.getArgs();
                    int nargs = args.length;
                    int skip = 0;
                    Expression func = aexp.getFunction();
                    if (args.length > 0 && (func instanceof ReferenceExp) && ((ReferenceExp) func).getBinding() == Scheme.applyFieldDecl) {
                        skip = 1;
                        nargs--;
                        func = args[0];
                    }
                    Expression[] setterArgs = {func};
                    Expression[] xargs = new Expression[nargs + 1];
                    System.arraycopy(args, skip, xargs, 0, nargs);
                    xargs[nargs] = value;
                    Declaration setter = CompilationHelpers.setterDecl;
                    return new ApplyExp((Expression) new ApplyExp((Expression) new ReferenceExp(setter), setterArgs), xargs);
                } else if (!(name instanceof ReferenceExp)) {
                    return tr.syntaxError("first set! argument is not a variable name");
                } else {
                    ReferenceExp ref = (ReferenceExp) name;
                    Declaration decl = ref.getBinding();
                    SetExp sexp = new SetExp(ref.getSymbol(), value);
                    sexp.setContextDecl(ref.contextDecl());
                    if (decl != null) {
                        decl.setCanWrite(true);
                        sexp.setBinding(decl);
                        Declaration decl2 = Declaration.followAliases(decl);
                        if (decl2 != null) {
                            decl2.noteValue(value);
                        }
                        if (decl2.getFlag(JSONzip.int14)) {
                            return tr.syntaxError("constant variable " + decl2.getName() + " is set!");
                        }
                        if (decl2.context != tr.mainLambda && (decl2.context instanceof ModuleExp) && !decl2.getFlag(268435456L) && !decl2.context.getFlag(1048576)) {
                            tr.error('w', decl2, "imported variable ", " is set!");
                            return sexp;
                        }
                        return sexp;
                    }
                    return sexp;
                }
            }
        }
        return tr.syntaxError("missing or extra arguments to set!");
    }
}
