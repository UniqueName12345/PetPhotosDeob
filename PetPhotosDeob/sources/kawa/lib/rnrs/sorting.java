package kawa.lib.rnrs;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.lib.srfi95;
import kawa.standard.append;

/* compiled from: sorting.scm */
/* loaded from: classes.dex */
public class sorting extends ModuleBody {
    public static final ModuleMethod list$Mnsort;
    public static final ModuleMethod vector$Mnsort;
    public static final ModuleMethod vector$Mnsort$Ex;
    static final SimpleSymbol Lit2 = (SimpleSymbol) new SimpleSymbol("vector-sort!").readResolve();
    static final SimpleSymbol Lit1 = (SimpleSymbol) new SimpleSymbol("vector-sort").readResolve();
    static final SimpleSymbol Lit0 = (SimpleSymbol) new SimpleSymbol("list-sort").readResolve();
    public static final sorting $instance = new sorting();

    static {
        sorting sortingVar = $instance;
        list$Mnsort = new ModuleMethod(sortingVar, 1, Lit0, 8194);
        vector$Mnsort = new ModuleMethod(sortingVar, 2, Lit1, 8194);
        vector$Mnsort$Ex = new ModuleMethod(sortingVar, 3, Lit2, 8194);
        $instance.run();
    }

    public sorting() {
        ModuleInfo.register(this);
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    public static Object listSort(Object less$Qu, Object list) {
        return srfi95.$PcSortList(append.append$V(new Object[]{list, LList.Empty}), less$Qu, Boolean.FALSE);
    }

    @Override // gnu.expr.ModuleBody
    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 2:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 3:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static void vectorSort(Object less$Qu, Object seq) {
        try {
            srfi95.$PcSortVector((Sequence) seq, less$Qu, Boolean.FALSE);
        } catch (ClassCastException e) {
            throw new WrongType(e, "%sort-vector", 0, seq);
        }
    }

    public static void vectorSort$Ex(Object proc, Object vector) {
        try {
            srfi95.$PcVectorSort$Ex((Sequence) vector, proc, Boolean.FALSE);
        } catch (ClassCastException e) {
            throw new WrongType(e, "%vector-sort!", 0, vector);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 1:
                return listSort(obj, obj2);
            case 2:
                vectorSort(obj, obj2);
                return Values.empty;
            case 3:
                vectorSort$Ex(obj, obj2);
                return Values.empty;
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }
}
