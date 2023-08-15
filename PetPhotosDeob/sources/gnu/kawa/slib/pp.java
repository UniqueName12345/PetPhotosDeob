package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.math.IntNum;
import kawa.lib.ports;

/* compiled from: pp.scm */
/* loaded from: classes.dex */
public class pp extends ModuleBody {
    static final SimpleSymbol Lit1 = (SimpleSymbol) new SimpleSymbol("pretty-print").readResolve();
    static final IntNum Lit0 = IntNum.make(79);
    public static final pp $instance = new pp();
    public static final ModuleMethod pretty$Mnprint = new ModuleMethod($instance, 2, Lit1, 8193);

    static {
        $instance.run();
    }

    public pp() {
        ModuleInfo.register(this);
    }

    public static Object prettyPrint(Object obj) {
        return prettyPrint(obj, ports.current$Mnoutput$Mnport.apply0());
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    /* compiled from: pp.scm */
    /* loaded from: classes.dex */
    public class frame extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        Object port;

        public frame() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pp.scm:9");
            this.lambda$Fn1 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 1 ? lambda1(obj) : super.apply1(moduleMethod, obj);
        }

        Boolean lambda1(Object s) {
            ports.display(s, this.port);
            return Boolean.TRUE;
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

    public static Object prettyPrint(Object obj, Object port) {
        frame frameVar = new frame();
        frameVar.port = port;
        return genwrite.genericWrite(obj, Boolean.FALSE, Lit0, frameVar.lambda$Fn1);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        return moduleMethod.selector == 2 ? prettyPrint(obj) : super.apply1(moduleMethod, obj);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        return moduleMethod.selector == 2 ? prettyPrint(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
    }

    @Override // gnu.expr.ModuleBody
    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        if (moduleMethod.selector == 2) {
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
        return super.match1(moduleMethod, obj, callContext);
    }

    @Override // gnu.expr.ModuleBody
    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector == 2) {
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
        return super.match2(moduleMethod, obj, obj2, callContext);
    }
}
