package kawa.standard;

import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

/* loaded from: classes.dex */
public class export extends Syntax {
    public static final export export;
    public static final export module_export = new export();

    static {
        module_export.setName("module-export");
        export = new export();
        module_export.setName("export");
    }

    @Override // kawa.lang.Syntax
    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        Object list = st.getCdr();
        Object savePos = tr.pushPositionOf(st);
        try {
            if (defs instanceof ModuleExp) {
                ((ModuleExp) defs).setFlag(16384);
                SyntaxForm restSyntax = null;
                while (list != LList.Empty) {
                    tr.pushPositionOf(list);
                    while (list instanceof SyntaxForm) {
                        restSyntax = (SyntaxForm) list;
                        list = restSyntax.getDatum();
                    }
                    SyntaxForm nameSyntax = restSyntax;
                    if (list instanceof Pair) {
                        Pair st2 = (Pair) list;
                        Object symbol = st2.getCar();
                        while (symbol instanceof SyntaxForm) {
                            nameSyntax = (SyntaxForm) symbol;
                            symbol = nameSyntax.getDatum();
                        }
                        if (symbol instanceof String) {
                            String str = (String) symbol;
                            if (str.startsWith("namespace:")) {
                                tr.error('w', "'namespace:' prefix ignored");
                                symbol = str.substring(10).intern();
                            }
                        }
                        if ((symbol instanceof String) || (symbol instanceof Symbol)) {
                            if (nameSyntax != null) {
                            }
                            Declaration decl = defs.getNoDefine(symbol);
                            if (decl.getFlag(512L)) {
                                Translator.setLine(decl, st2);
                            }
                            decl.setFlag(1024L);
                            list = st2.getCdr();
                        }
                    }
                    tr.error('e', "invalid syntax in '" + getName() + '\'');
                    return false;
                }
                return true;
            }
            tr.error('e', "'" + getName() + "' not at module level");
            return true;
        } finally {
            tr.popPositionOf(savePos);
        }
    }

    @Override // kawa.lang.Syntax
    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}
