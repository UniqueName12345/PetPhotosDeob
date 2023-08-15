package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.PrimProcedure;
import gnu.kawa.lispexpr.ClassNamespace;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;

/* loaded from: classes.dex */
public class Invoke extends ProcedureN {
    char kind;
    Language language;
    public static final Invoke invoke = new Invoke("invoke", '*');
    public static final Invoke invokeStatic = new Invoke("invoke-static", 'S');
    public static final Invoke invokeSpecial = new Invoke("invoke-special", 'P');
    public static final Invoke make = new Invoke("make", 'N');

    public Invoke(String name, char kind) {
        this(name, kind, Language.getDefaultLanguage());
    }

    public Invoke(String name, char kind, Language language) {
        super(name);
        this.kind = kind;
        this.language = language;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileInvoke:validateApplyInvoke");
    }

    private static ObjectType typeFrom(Object arg, Invoke thisProc) {
        if (arg instanceof Class) {
            arg = Type.make((Class) arg);
        }
        if (arg instanceof ObjectType) {
            return (ObjectType) arg;
        }
        if ((arg instanceof String) || (arg instanceof FString)) {
            return ClassType.make(arg.toString());
        }
        if (arg instanceof Symbol) {
            return ClassType.make(((Symbol) arg).getName());
        }
        if (arg instanceof ClassNamespace) {
            return ((ClassNamespace) arg).getClassType();
        }
        throw new WrongType(thisProc, 0, arg, "class-specifier");
    }

    @Override // gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        Object[] args = ctx.getArgs();
        if (this.kind == 'S' || this.kind == 'V' || this.kind == 's' || this.kind == '*') {
            int nargs = args.length;
            Procedure.checkArgCount(this, nargs);
            Object arg0 = args[0];
            ObjectType dtype = (ObjectType) ((this.kind == 'S' || this.kind == 's') ? typeFrom(arg0, this) : Type.make(arg0.getClass()));
            Procedure proc = lookupMethods(dtype, args[1]);
            Object[] margs = new Object[nargs - (this.kind == 'S' ? 2 : 1)];
            int i = 0;
            if (this.kind == 'V' || this.kind == '*') {
                int i2 = 0 + 1;
                margs[0] = args[0];
                i = i2;
            }
            System.arraycopy(args, 2, margs, i, nargs - 2);
            proc.checkN(margs, ctx);
            return;
        }
        ctx.writeValue(applyN(args));
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0122  */
    @Override // gnu.mapping.ProcedureN, gnu.mapping.Procedure
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object applyN(java.lang.Object[] r39) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 796
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.Invoke.applyN(java.lang.Object[]):java.lang.Object");
    }

    @Override // gnu.mapping.Procedure
    public int numArgs() {
        return (this.kind == 'N' ? 1 : 2) | (-4096);
    }

    protected MethodProc lookupMethods(ObjectType dtype, Object name) {
        String mname;
        String mname2;
        char c = 'P';
        if (this.kind == 'N') {
            mname2 = "<init>";
        } else {
            if ((name instanceof String) || (name instanceof FString)) {
                mname = name.toString();
            } else if (name instanceof Symbol) {
                mname = ((Symbol) name).getName();
            } else {
                throw new WrongType((Procedure) this, 1, (ClassCastException) null);
            }
            mname2 = Compilation.mangleName(mname);
        }
        if (this.kind != 'P') {
            c = (this.kind == '*' || this.kind == 'V') ? 'V' : (char) 0;
        }
        MethodProc proc = ClassMethods.apply(dtype, mname2, c, this.language);
        if (proc == null) {
            throw new RuntimeException(getName() + ": no method named `" + mname2 + "' in class " + dtype.getName());
        }
        return proc;
    }

    public static synchronized ApplyExp makeInvokeStatic(ClassType type, String name, Expression[] args) {
        ApplyExp applyExp;
        synchronized (Invoke.class) {
            PrimProcedure method = getStaticMethod(type, name, args);
            if (method == null) {
                throw new RuntimeException("missing or ambiguous method `" + name + "' in " + type.getName());
            }
            applyExp = new ApplyExp(method, args);
        }
        return applyExp;
    }

    public static synchronized PrimProcedure getStaticMethod(ClassType type, String name, Expression[] args) {
        PrimProcedure staticMethod;
        synchronized (Invoke.class) {
            staticMethod = CompileInvoke.getStaticMethod(type, name, args);
        }
        return staticMethod;
    }
}
