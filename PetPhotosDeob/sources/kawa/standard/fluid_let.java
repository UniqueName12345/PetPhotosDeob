package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.FluidLetExp;
import gnu.expr.ReferenceExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class fluid_let extends Syntax {
    public static final fluid_let fluid_let = new fluid_let();
    Expression defaultInit;
    boolean star;

    static {
        fluid_let.setName("fluid-set");
    }

    public fluid_let(boolean star, Expression defaultInit) {
        this.star = star;
        this.defaultInit = defaultInit;
    }

    public fluid_let() {
        this.star = false;
    }

    @Override // kawa.lang.Syntax
    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing let arguments");
        }
        Pair pair = (Pair) obj;
        return rewrite(pair.getCar(), pair.getCdr(), tr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Expression rewrite(Object bindings, Object body, Translator tr) {
        Expression value;
        int decl_count = this.star ? 1 : LList.length(bindings);
        Expression[] inits = new Expression[decl_count];
        FluidLetExp let = new FluidLetExp(inits);
        for (int i = 0; i < decl_count; i++) {
            Pair bind_pair = (Pair) bindings;
            Object savePos = tr.pushPositionOf(bind_pair);
            try {
                Object name = bind_pair.getCar();
                if ((name instanceof String) || (name instanceof Symbol)) {
                    value = this.defaultInit;
                } else {
                    if (name instanceof Pair) {
                        Pair binding = (Pair) name;
                        if ((binding.getCar() instanceof String) || (binding.getCar() instanceof Symbol) || (binding.getCar() instanceof SyntaxForm)) {
                            name = binding.getCar();
                            if (name instanceof SyntaxForm) {
                                name = ((SyntaxForm) name).getDatum();
                            }
                            if (binding.getCdr() == LList.Empty) {
                                value = this.defaultInit;
                            } else {
                                if (binding.getCdr() instanceof Pair) {
                                    Pair binding2 = (Pair) binding.getCdr();
                                    if (binding2.getCdr() == LList.Empty) {
                                        value = tr.rewrite(binding2.getCar());
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
                Declaration decl = let.addDeclaration(name);
                Declaration found = tr.lexical.lookup(name, false);
                if (found != null) {
                    found.maybeIndirectBinding(tr);
                    decl.base = found;
                    found.setFluid(true);
                    found.setCanWrite(true);
                }
                decl.setCanWrite(true);
                decl.setFluid(true);
                decl.setIndirectBinding(true);
                if (value == null) {
                    value = new ReferenceExp(name);
                }
                inits[i] = value;
                decl.noteValue(null);
                bindings = bind_pair.getCdr();
                tr.popPositionOf(savePos);
            } finally {
                tr.popPositionOf(savePos);
            }
        }
        tr.push(let);
        if (this.star && bindings != LList.Empty) {
            let.body = rewrite(bindings, body, tr);
        } else {
            let.body = tr.rewrite_body(body);
        }
        tr.pop(let);
        Expression expression = let;
        return expression;
    }
}
