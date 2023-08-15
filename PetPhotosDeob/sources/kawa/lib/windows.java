package kawa.lib;

import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.GuiConsole;
import kawa.standard.Scheme;

/* compiled from: windows.scm */
/* loaded from: classes.dex */
public class windows extends ModuleBody {
    static final SimpleSymbol Lit0 = (SimpleSymbol) new SimpleSymbol("scheme-window").readResolve();
    public static final windows $instance = new windows();
    public static final ModuleMethod scheme$Mnwindow = new ModuleMethod($instance, 1, Lit0, 4096);

    static {
        $instance.run();
    }

    public windows() {
        ModuleInfo.register(this);
    }

    public static void schemeWindow() {
        schemeWindow(Boolean.FALSE);
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    public static void schemeWindow(Object share) {
        Language language = Scheme.getInstance();
        Environment env = share != Boolean.FALSE ? misc.interactionEnvironment() : language.getNewEnvironment();
        try {
            new GuiConsole(language, env, share != Boolean.FALSE);
        } catch (ClassCastException e) {
            throw new WrongType(e, "kawa.GuiConsole.<init>(gnu.expr.Language,gnu.mapping.Environment,boolean)", 3, share);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply0(ModuleMethod moduleMethod) {
        if (moduleMethod.selector == 1) {
            schemeWindow();
            return Values.empty;
        }
        return super.apply0(moduleMethod);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        if (moduleMethod.selector == 1) {
            schemeWindow(obj);
            return Values.empty;
        }
        return super.apply1(moduleMethod, obj);
    }

    @Override // gnu.expr.ModuleBody
    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        if (moduleMethod.selector == 1) {
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }
        return super.match0(moduleMethod, callContext);
    }

    @Override // gnu.expr.ModuleBody
    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        if (moduleMethod.selector == 1) {
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
        return super.match1(moduleMethod, obj, callContext);
    }
}
