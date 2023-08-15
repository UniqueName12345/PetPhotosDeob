package gnu.kawa.reflect;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.ClassExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.mapping.HasSetter;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure2;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;

/* loaded from: classes.dex */
public class SlotGet extends Procedure2 implements HasSetter, Inlineable {
    boolean isStatic;
    Procedure setter;
    static Class[] noClasses = new Class[0];
    public static final SlotGet field = new SlotGet("field", false, SlotSet.set$Mnfield$Ex);
    public static final SlotGet slotRef = new SlotGet("slot-ref", false, SlotSet.set$Mnfield$Ex);
    public static final SlotGet staticField = new SlotGet("static-field", true, SlotSet.set$Mnstatic$Mnfield$Ex);

    public SlotGet(String name, boolean isStatic) {
        super(name);
        this.isStatic = isStatic;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileReflect:validateApplySlotGet");
    }

    public SlotGet(String name, boolean isStatic, Procedure setter) {
        this(name, isStatic);
        this.setter = setter;
    }

    public static Object field(Object obj, String fname) {
        return field.apply2(obj, fname);
    }

    public static Object staticField(Object obj, String fname) {
        return staticField.apply2(obj, fname);
    }

    @Override // gnu.mapping.Procedure2, gnu.mapping.Procedure
    public Object apply2(Object arg1, Object arg2) {
        String name;
        String fname;
        String getName = null;
        String isName = null;
        if (arg2 instanceof Field) {
            fname = ((Field) arg2).getName();
            name = Compilation.demangleName(fname, true);
        } else if (arg2 instanceof Method) {
            String mname = ((Method) arg2).getName();
            name = Compilation.demangleName(mname, false);
            if (mname.startsWith("get")) {
                getName = mname;
            } else if (mname.startsWith("is")) {
                isName = mname;
            }
            fname = null;
        } else if ((arg2 instanceof SimpleSymbol) || (arg2 instanceof CharSequence)) {
            name = arg2.toString();
            fname = Compilation.mangleNameIfNeeded(name);
        } else {
            throw new WrongType(this, 2, arg2, PropertyTypeConstants.PROPERTY_TYPE_STRING);
        }
        if ("class".equals(fname)) {
            fname = "class";
        } else if (PropertyTypeConstants.PROPERTY_TYPE_LENGTH.equals(fname)) {
            fname = PropertyTypeConstants.PROPERTY_TYPE_LENGTH;
        }
        return getSlotValue(this.isStatic, arg1, name, fname, getName, isName, Language.getDefaultLanguage());
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0100  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object getSlotValue(boolean r13, java.lang.Object r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, java.lang.String r18, gnu.expr.Language r19) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.SlotGet.getSlotValue(boolean, java.lang.Object, java.lang.String, java.lang.String, java.lang.String, java.lang.String, gnu.expr.Language):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Class coerceToClass(Object obj) {
        if (obj instanceof Class) {
            return (Class) obj;
        }
        if (obj instanceof Type) {
            return ((Type) obj).getReflectClass();
        }
        throw new RuntimeException("argument is neither Class nor Type");
    }

    @Override // gnu.mapping.Procedure
    public void setN(Object[] args) {
        int nargs = args.length;
        if (nargs != 3) {
            throw new WrongArguments(getSetter(), nargs);
        }
        set2(args[0], args[1], args[2]);
    }

    public void set2(Object obj, Object name, Object value) {
        SlotSet.apply(this.isStatic, obj, (String) name, value);
    }

    public static Member lookupMember(ObjectType clas, String name, ClassType caller) {
        Field field2 = clas.getField(Compilation.mangleNameIfNeeded(name), -1);
        if (field2 != null) {
            if (caller == null) {
                caller = Type.pointer_type;
            }
            if (caller.isAccessible(field2, clas)) {
                return field2;
            }
        }
        String getname = ClassExp.slotToMethodName("get", name);
        Method method = clas.getMethod(getname, Type.typeArray0);
        return method != null ? method : field2;
    }

    @Override // gnu.expr.Inlineable
    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        Expression arg0 = args[0];
        Expression arg1 = args[1];
        Language language = comp.getLanguage();
        Type type = this.isStatic ? language.getTypeFor(arg0) : arg0.getType();
        CodeAttr code = comp.getCode();
        if ((type instanceof ObjectType) && (arg1 instanceof QuoteExp)) {
            ObjectType ctype = (ObjectType) type;
            Object part = ((QuoteExp) arg1).getValue();
            if (part instanceof Field) {
                Field field2 = (Field) part;
                int modifiers = field2.getModifiers();
                boolean isStaticField = (modifiers & 8) != 0;
                args[0].compile(comp, isStaticField ? Target.Ignore : Target.pushValue(ctype));
                if (isStaticField) {
                    if (0 == 0) {
                        code.emitGetStatic(field2);
                    }
                } else {
                    code.emitGetField(field2);
                }
                Type ftype = language.getLangTypeFor(field2.getType());
                target.compileFromStack(comp, ftype);
                return;
            } else if (part instanceof Method) {
                Method method = (Method) part;
                method.getModifiers();
                boolean isStaticMethod = method.getStaticFlag();
                args[0].compile(comp, isStaticMethod ? Target.Ignore : Target.pushValue(ctype));
                if (isStaticMethod) {
                    code.emitInvokeStatic(method);
                } else {
                    code.emitInvoke(method);
                }
                target.compileFromStack(comp, method.getReturnType());
                return;
            }
        }
        String name = ClassMethods.checkName(arg1);
        if ((type instanceof ArrayType) && PropertyTypeConstants.PROPERTY_TYPE_LENGTH.equals(name) && !this.isStatic) {
            args[0].compile(comp, Target.pushValue(type));
            code.emitArrayLength();
            target.compileFromStack(comp, LangPrimType.intType);
            return;
        }
        ApplyExp.compile(exp, comp, target);
    }

    @Override // gnu.mapping.Procedure
    public Type getReturnType(Expression[] args) {
        int nargs = args.length;
        if (nargs == 2) {
            Expression arg0 = args[0];
            Expression arg1 = args[1];
            if (arg1 instanceof QuoteExp) {
                Object part = ((QuoteExp) arg1).getValue();
                if (part instanceof Field) {
                    return ((Field) part).getType();
                }
                if (part instanceof Method) {
                    return ((Method) part).getReturnType();
                }
                if (!this.isStatic && (arg0.getType() instanceof ArrayType) && PropertyTypeConstants.PROPERTY_TYPE_LENGTH.equals(ClassMethods.checkName(arg1, true))) {
                    return LangPrimType.intType;
                }
            }
        }
        return Type.pointer_type;
    }

    @Override // gnu.mapping.Procedure, gnu.mapping.HasSetter
    public Procedure getSetter() {
        return this.setter == null ? super.getSetter() : this.setter;
    }

    public static ApplyExp makeGetField(Expression value, String fieldName) {
        Expression[] args = {value, new QuoteExp(fieldName)};
        return new ApplyExp(field, args);
    }
}
