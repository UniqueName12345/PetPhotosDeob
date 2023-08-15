package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleContext;
import gnu.mapping.SimpleSymbol;
import kawa.lang.SyntaxForm;

/* loaded from: classes.dex */
public class IfFeature {
    public static boolean testFeature(Object form) {
        if (form instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) form;
            form = sf.getDatum();
        }
        if ((form instanceof String) || (form instanceof SimpleSymbol)) {
            return hasFeature(form.toString());
        }
        return false;
    }

    public static boolean hasFeature(String name) {
        if (name == "kawa" || name == "srfi-0" || name == "srfi-4" || name == "srfi-6" || name == "srfi-8" || name == "srfi-9" || name == "srfi-11" || name == "srfi-16" || name == "srfi-17" || name == "srfi-23" || name == "srfi-25" || name == "srfi-26" || name == "srfi-28" || name == "srfi-30" || name == "srfi-39") {
            return true;
        }
        if (name == "in-http-server" || name == "in-servlet") {
            int mflags = ModuleContext.getContext().getFlags();
            if (name == "in-http-server") {
                return (ModuleContext.IN_HTTP_SERVER & mflags) != 0;
            } else if (name == "in-servlet") {
                return (ModuleContext.IN_SERVLET & mflags) != 0;
            }
        }
        String provide_name = ("%provide%" + name).intern();
        Compilation comp = Compilation.getCurrent();
        Declaration decl = comp.lookup(provide_name, -1);
        return (decl == null || decl.getFlag(65536L)) ? false : true;
    }
}
