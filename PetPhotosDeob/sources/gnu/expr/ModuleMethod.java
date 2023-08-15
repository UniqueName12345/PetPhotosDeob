package gnu.expr;

import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.bytecode.Type;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import gnu.mapping.WrongArguments;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class ModuleMethod extends MethodProc {
    public ModuleBody module;
    protected int numArgs;
    public int selector;

    public ModuleMethod(ModuleBody module, int selector, Object name, int numArgs) {
        init(module, selector, name, numArgs);
    }

    public ModuleMethod(ModuleBody module, int selector, Object name, int numArgs, Object argTypes) {
        init(module, selector, name, numArgs);
        this.argTypes = argTypes;
    }

    public ModuleMethod init(ModuleBody module, int selector, Object name, int numArgs) {
        this.module = module;
        this.selector = selector;
        this.numArgs = numArgs;
        if (name != null) {
            setSymbol(name);
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.mapping.MethodProc
    public void resolveParameterTypes() {
        Language lang;
        Method method = null;
        String name = getName();
        if (name != null) {
            try {
                Class moduleClass = this.module.getClass();
                Method[] methods = moduleClass.getDeclaredMethods();
                String mangledName = Compilation.mangleNameIfNeeded(name);
                int i = methods.length;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    } else if (methods[i].getName().equals(mangledName)) {
                        if (method != null) {
                            method = null;
                            break;
                        }
                        method = methods[i];
                    }
                }
                if (method != null && (lang = Language.getDefaultLanguage()) != null) {
                    Class[] parameterClasses = method.getParameterTypes();
                    int numParamTypes = parameterClasses.length;
                    Type[] atypes = new Type[numParamTypes];
                    int i2 = numParamTypes;
                    while (true) {
                        i2--;
                        if (i2 < 0) {
                            break;
                        }
                        atypes[i2] = lang.getTypeFor(parameterClasses[i2]);
                    }
                    this.argTypes = atypes;
                }
            } catch (Throwable th) {
            }
        }
        if (this.argTypes == null) {
            super.resolveParameterTypes();
        }
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return this.numArgs;
    }

    @Override // gnu.mapping.Procedure
    public int match0(CallContext ctx) {
        ctx.count = 0;
        ctx.where = 0;
        return this.module.match0(this, ctx);
    }

    @Override // gnu.mapping.Procedure
    public int match1(Object arg1, CallContext ctx) {
        ctx.count = 1;
        ctx.where = 1;
        return this.module.match1(this, arg1, ctx);
    }

    @Override // gnu.mapping.Procedure
    public int match2(Object arg1, Object arg2, CallContext ctx) {
        ctx.count = 2;
        ctx.where = 33;
        return this.module.match2(this, arg1, arg2, ctx);
    }

    @Override // gnu.mapping.Procedure
    public int match3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        ctx.count = 3;
        ctx.where = ErrorMessages.ERROR_SOUND_RECORDER;
        return this.module.match3(this, arg1, arg2, arg3, ctx);
    }

    @Override // gnu.mapping.Procedure
    public int match4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        ctx.count = 4;
        ctx.where = 17185;
        return this.module.match4(this, arg1, arg2, arg3, arg4, ctx);
    }

    @Override // gnu.mapping.Procedure
    public int matchN(Object[] args, CallContext ctx) {
        ctx.count = args.length;
        ctx.where = 0;
        return this.module.matchN(this, args, ctx);
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        Object result;
        switch (ctx.pc) {
            case 0:
                result = apply0();
                break;
            case 1:
                result = apply1(ctx.value1);
                break;
            case 2:
                result = apply2(ctx.value1, ctx.value2);
                break;
            case 3:
                result = apply3(ctx.value1, ctx.value2, ctx.value3);
                break;
            case 4:
                result = apply4(ctx.value1, ctx.value2, ctx.value3, ctx.value4);
                break;
            case 5:
                result = applyN(ctx.values);
                break;
            default:
                throw new Error("internal error - apply " + this);
        }
        ctx.writeValue(result);
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object apply0() throws Throwable {
        return this.module.apply0(this);
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object apply1(Object arg1) throws Throwable {
        return this.module.apply1(this, arg1);
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object apply2(Object arg1, Object arg2) throws Throwable {
        return this.module.apply2(this, arg1, arg2);
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object apply3(Object arg1, Object arg2, Object arg3) throws Throwable {
        return this.module.apply3(this, arg1, arg2, arg3);
    }

    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object apply4(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {
        return this.module.apply4(this, arg1, arg2, arg3, arg4);
    }

    @Override // gnu.mapping.MethodProc, gnu.mapping.ProcedureN, gnu.mapping.Procedure
    public Object applyN(Object[] args) throws Throwable {
        return this.module.applyN(this, args);
    }

    public static Object apply0Default(ModuleMethod method) throws Throwable {
        return method.module.applyN(method, Values.noArgs);
    }

    public static Object apply1Default(ModuleMethod method, Object arg1) throws Throwable {
        Object[] args = {arg1};
        return method.module.applyN(method, args);
    }

    public static Object apply2Default(ModuleMethod method, Object arg1, Object arg2) throws Throwable {
        Object[] args = {arg1, arg2};
        return method.module.applyN(method, args);
    }

    public static Object apply3Default(ModuleMethod method, Object arg1, Object arg2, Object arg3) throws Throwable {
        Object[] args = {arg1, arg2, arg3};
        return method.module.applyN(method, args);
    }

    public static Object apply4Default(ModuleMethod method, Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {
        Object[] args = {arg1, arg2, arg3, arg4};
        return method.module.applyN(method, args);
    }

    public static Object applyNDefault(ModuleMethod method, Object[] args) throws Throwable {
        int count = args.length;
        int num = method.numArgs();
        ModuleBody module = method.module;
        if (count >= (num & 4095) && (num < 0 || count <= (num >> 12))) {
            switch (count) {
                case 0:
                    return module.apply0(method);
                case 1:
                    return module.apply1(method, args[0]);
                case 2:
                    return module.apply2(method, args[0], args[1]);
                case 3:
                    return module.apply3(method, args[0], args[1], args[2]);
                case 4:
                    return module.apply4(method, args[0], args[1], args[2], args[3]);
            }
        }
        throw new WrongArguments(method, count);
    }

    public static void applyError() {
        throw new Error("internal error - bad selector");
    }
}
