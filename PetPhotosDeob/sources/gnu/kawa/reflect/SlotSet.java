package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.CheckedTarget;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.lists.FString;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure3;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import kawa.standard.Scheme;

/* loaded from: classes.dex */
public class SlotSet extends Procedure3 implements Inlineable {
    public static final SlotSet set$Mnfield$Ex = new SlotSet("set-field!", false);
    public static final SlotSet set$Mnstatic$Mnfield$Ex = new SlotSet("set-static-field!", true);
    public static final SlotSet setFieldReturnObject = new SlotSet("set-field-return-object!", false);
    static final Type[] type1Array;
    boolean isStatic;
    boolean returnSelf;

    static {
        setFieldReturnObject.returnSelf = true;
        type1Array = new Type[1];
    }

    public SlotSet(String name, boolean isStatic) {
        super(name);
        this.isStatic = isStatic;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplySlotSet");
    }

    public static void setField(Object obj, String name, Object value) {
        apply(false, obj, name, value);
    }

    public static void setStaticField(Object obj, String name, Object value) {
        apply(true, obj, name, value);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0080 A[Catch: Exception -> 0x00f4, TRY_ENTER, TryCatch #4 {Exception -> 0x00f4, blocks: (B:31:0x0080, B:32:0x009d, B:41:0x00eb), top: B:60:0x007e, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00e2 A[Catch: InvocationTargetException -> 0x00d8, IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e, TRY_ENTER, TRY_LEAVE, TryCatch #4 {IllegalAccessException -> 0x0126, NoSuchMethodException -> 0x016e, InvocationTargetException -> 0x00d8, blocks: (B:23:0x0067, B:27:0x0071, B:34:0x00a7, B:45:0x00f7, B:46:0x0114, B:47:0x011d, B:39:0x00e2, B:31:0x0080, B:32:0x009d, B:41:0x00eb), top: B:64:0x0067, inners: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00eb A[Catch: Exception -> 0x00f4, TRY_ENTER, TRY_LEAVE, TryCatch #4 {Exception -> 0x00f4, blocks: (B:31:0x0080, B:32:0x009d, B:41:0x00eb), top: B:60:0x007e, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0145  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void apply(boolean r21, java.lang.Object r22, java.lang.Object r23, java.lang.Object r24) {
        /*
            Method dump skipped, instructions count: 371
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.SlotSet.apply(boolean, java.lang.Object, java.lang.Object, java.lang.Object):void");
    }

    @Override // gnu.mapping.Procedure3, gnu.mapping.Procedure
    public Object apply3(Object obj, Object fname, Object value) {
        apply(this.isStatic, obj, fname, value);
        return this.returnSelf ? obj : Values.empty;
    }

    public static Member lookupMember(ObjectType clas, String name, ClassType caller) {
        Field field = clas.getField(Compilation.mangleNameIfNeeded(name), -1);
        if (field != null) {
            if (caller == null) {
                caller = Type.pointer_type;
            }
            if (caller.isAccessible(field, clas)) {
                return field;
            }
        }
        String setName = ClassExp.slotToMethodName("set", name);
        Method method = clas.getMethod(setName, type1Array);
        return method != null ? method : field;
    }

    static void compileSet(Procedure thisProc, ObjectType ctype, Expression valArg, Object part, Compilation comp) {
        CodeAttr code = comp.getCode();
        Language language = comp.getLanguage();
        boolean isStatic = (thisProc instanceof SlotSet) && ((SlotSet) thisProc).isStatic;
        if (part instanceof Field) {
            Field field = (Field) part;
            boolean isStaticField = field.getStaticFlag();
            Type ftype = language.getLangTypeFor(field.getType());
            if (isStatic && !isStaticField) {
                comp.error('e', "cannot access non-static field `" + field.getName() + "' using `" + thisProc.getName() + '\'');
            }
            valArg.compile(comp, CheckedTarget.getInstance(ftype));
            if (isStaticField) {
                code.emitPutStatic(field);
            } else {
                code.emitPutField(field);
            }
        } else if (part instanceof Method) {
            Method method = (Method) part;
            boolean isStaticMethod = method.getStaticFlag();
            if (isStatic && !isStaticMethod) {
                comp.error('e', "cannot call non-static getter method `" + method.getName() + "' using `" + thisProc.getName() + '\'');
            }
            Type[] setArgTypes = method.getParameterTypes();
            valArg.compile(comp, CheckedTarget.getInstance(language.getLangTypeFor(setArgTypes[0])));
            if (isStaticMethod) {
                code.emitInvokeStatic(method);
            } else {
                code.emitInvoke(method);
            }
            if (!method.getReturnType().isVoid()) {
                code.emitPop(1);
            }
        }
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        String name;
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        if (nargs != 3) {
            String msg = nargs < 3 ? "too few" : "too many";
            comp.error('e', msg + " arguments to `" + getName() + '\'');
            comp.compileConstant(null, target);
            return;
        }
        Expression arg0 = args[0];
        Expression arg1 = args[1];
        Expression expression = args[2];
        Type type = this.isStatic ? Scheme.exp2Type(arg0) : arg0.getType();
        Member part = null;
        if ((type instanceof ObjectType) && (arg1 instanceof QuoteExp)) {
            Object val1 = ((QuoteExp) arg1).getValue();
            ObjectType ctype = (ObjectType) type;
            ClassType caller = comp.curClass != null ? comp.curClass : comp.mainClass;
            if ((val1 instanceof String) || (val1 instanceof FString) || (val1 instanceof Symbol)) {
                name = val1.toString();
                part = lookupMember(ctype, name, caller);
                if (part == null && type != Type.pointer_type && comp.warnUnknownMember()) {
                    comp.error('w', "no slot `" + name + "' in " + ctype.getName());
                }
            } else if (val1 instanceof Member) {
                part = (Member) val1;
                name = part.getName();
            } else {
                name = null;
            }
            if (part != null) {
                int modifiers = part.getModifiers();
                boolean isStaticField = (modifiers & 8) != 0;
                if (caller != null && !caller.isAccessible(part, ctype)) {
                    comp.error('e', "slot '" + name + "' in " + part.getDeclaringClass().getName() + " not accessible here");
                }
                args[0].compile(comp, isStaticField ? Target.Ignore : Target.pushValue(ctype));
                if (this.returnSelf) {
                    comp.getCode().emitDup(ctype.getImplementationType());
                }
                compileSet(this, ctype, args[2], part, comp);
                if (this.returnSelf) {
                    target.compileFromStack(comp, ctype);
                    return;
                } else {
                    comp.compileConstant(Values.empty, target);
                    return;
                }
            }
        }
        ApplyExp.compile(exp, comp, target);
    }
}
