package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.kawa.reflect.Invoke;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class define_member_alias extends Syntax {
    public static final define_member_alias define_member_alias = new define_member_alias();

    static {
        define_member_alias.setName("define-member-alias");
    }

    @Override // kawa.lang.Syntax
    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if ((st.getCdr() instanceof Pair) && !(tr.currentScope() instanceof ModuleExp)) {
            Pair p = (Pair) st.getCdr();
            if (p.getCar() instanceof String) {
                Object name = p.getCar();
                Declaration decl = defs.addDeclaration((String) name, Compilation.typeSymbol);
                decl.setIndirectBinding(true);
                forms.addElement(Translator.makePair(st, this, Translator.makePair(p, decl, p.getCdr())));
                return true;
            }
        }
        return super.scanForDefinitions(st, forms, defs, tr);
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        String name;
        Object obj = form.getCdr();
        if (obj instanceof Pair) {
            Pair p1 = (Pair) obj;
            if ((p1.getCar() instanceof String) || (p1.getCar() instanceof Declaration)) {
                if (p1.getCdr() instanceof Pair) {
                    Object p1_car = p1.getCar();
                    if (p1_car instanceof Declaration) {
                        Declaration decl = (Declaration) p1_car;
                        name = decl.getName();
                    } else {
                        name = (String) p1_car;
                    }
                    Pair p2 = (Pair) p1.getCdr();
                    Expression fname = null;
                    Expression arg = tr.rewrite(p2.getCar());
                    Object p2_cdr = p2.getCdr();
                    if (p2_cdr == LList.Empty) {
                        fname = new QuoteExp(Compilation.mangleName(name));
                    } else if (p2_cdr instanceof Pair) {
                        Pair p3 = (Pair) p2_cdr;
                        if (p3.getCdr() == LList.Empty) {
                            fname = tr.rewrite(p3.getCar());
                        }
                    }
                    if (fname != null) {
                        ClassType t = ClassType.make("gnu.kawa.reflect.ClassMemberConstraint");
                        Expression[] args = {new QuoteExp(name), arg, fname};
                        return Invoke.makeInvokeStatic(t, "define", args);
                    }
                }
                return tr.syntaxError("invalid syntax for " + getName());
            }
        }
        return tr.syntaxError("missing name in " + getName());
    }
}
