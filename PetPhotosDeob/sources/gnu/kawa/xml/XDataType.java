package gnu.kawa.xml;

import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.Component;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.reflect.InstanceOf;
import gnu.lists.Consumer;
import gnu.lists.SeqPosition;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.Duration;
import gnu.math.IntNum;
import gnu.math.Unit;
import gnu.text.Path;
import gnu.text.Printable;
import gnu.text.URIPath;
import gnu.xml.TextUtils;
import java.math.BigDecimal;

/* loaded from: classes.dex */
public class XDataType extends Type implements TypeValue {
    public static final int ANY_ATOMIC_TYPE_CODE = 3;
    public static final int ANY_SIMPLE_TYPE_CODE = 2;
    public static final int ANY_URI_TYPE_CODE = 33;
    public static final int BASE64_BINARY_TYPE_CODE = 34;
    public static final int BOOLEAN_TYPE_CODE = 31;
    public static final int BYTE_TYPE_CODE = 11;
    public static final int DATE_TIME_TYPE_CODE = 20;
    public static final int DATE_TYPE_CODE = 21;
    public static final int DAY_TIME_DURATION_TYPE_CODE = 30;
    public static final int DECIMAL_TYPE_CODE = 4;
    public static final int DOUBLE_TYPE_CODE = 19;
    public static final int DURATION_TYPE_CODE = 28;
    public static final int ENTITY_TYPE_CODE = 47;
    public static final int FLOAT_TYPE_CODE = 18;
    public static final int G_DAY_TYPE_CODE = 26;
    public static final int G_MONTH_DAY_TYPE_CODE = 25;
    public static final int G_MONTH_TYPE_CODE = 27;
    public static final int G_YEAR_MONTH_TYPE_CODE = 23;
    public static final int G_YEAR_TYPE_CODE = 24;
    public static final int HEX_BINARY_TYPE_CODE = 35;
    public static final int IDREF_TYPE_CODE = 46;
    public static final int ID_TYPE_CODE = 45;
    public static final int INTEGER_TYPE_CODE = 5;
    public static final int INT_TYPE_CODE = 9;
    public static final int LANGUAGE_TYPE_CODE = 41;
    public static final int LONG_TYPE_CODE = 8;
    public static final int NAME_TYPE_CODE = 43;
    public static final int NCNAME_TYPE_CODE = 44;
    public static final int NEGATIVE_INTEGER_TYPE_CODE = 7;
    public static final int NMTOKEN_TYPE_CODE = 42;
    public static final int NONNEGATIVE_INTEGER_TYPE_CODE = 12;
    public static final int NON_POSITIVE_INTEGER_TYPE_CODE = 6;
    public static final int NORMALIZED_STRING_TYPE_CODE = 39;
    public static final int NOTATION_TYPE_CODE = 36;
    public static final int POSITIVE_INTEGER_TYPE_CODE = 17;
    public static final int QNAME_TYPE_CODE = 32;
    public static final int SHORT_TYPE_CODE = 10;
    public static final int STRING_TYPE_CODE = 38;
    public static final int TIME_TYPE_CODE = 22;
    public static final int TOKEN_TYPE_CODE = 40;
    public static final int UNSIGNED_BYTE_TYPE_CODE = 16;
    public static final int UNSIGNED_INT_TYPE_CODE = 14;
    public static final int UNSIGNED_LONG_TYPE_CODE = 13;
    public static final int UNSIGNED_SHORT_TYPE_CODE = 15;
    public static final int UNTYPED_ATOMIC_TYPE_CODE = 37;
    public static final int UNTYPED_TYPE_CODE = 48;
    public static final int YEAR_MONTH_DURATION_TYPE_CODE = 29;
    XDataType baseType;
    Type implementationType;
    Object name;
    int typeCode;
    public static final XDataType anySimpleType = new XDataType("anySimpleType", Type.objectType, 2);
    public static final XDataType anyAtomicType = new XDataType("anyAtomicType", Type.objectType, 3);
    public static final XDataType stringType = new XDataType(PropertyTypeConstants.PROPERTY_TYPE_STRING, ClassType.make("java.lang.CharSequence"), 38);
    public static final XDataType stringStringType = new XDataType("String", ClassType.make("java.lang.String"), 38);
    public static final XDataType untypedAtomicType = new XDataType(PropertyTypeConstants.PROPERTY_TYPE_STRING, ClassType.make("gnu.kawa.xml.UntypedAtomic"), 37);
    public static final XDataType base64BinaryType = new XDataType("base64Binary", ClassType.make("gnu.kawa.xml.Base64Binary"), 34);
    public static final XDataType hexBinaryType = new XDataType("hexBinary", ClassType.make("gnu.kawa.xml.HexBinary"), 35);
    public static final XDataType booleanType = new XDataType(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, Type.booleanType, 31);
    public static final XDataType anyURIType = new XDataType("anyURI", ClassType.make("gnu.text.Path"), 33);
    public static final XDataType NotationType = new XDataType("NOTATION", ClassType.make("gnu.kawa.xml.Notation"), 36);
    public static final XDataType decimalType = new XDataType("decimal", ClassType.make("java.lang.Number"), 4);
    public static final XDataType floatType = new XDataType(PropertyTypeConstants.PROPERTY_TYPE_FLOAT, ClassType.make("java.lang.Float"), 18);
    public static final XDataType doubleType = new XDataType("double", ClassType.make("java.lang.Double"), 19);
    public static final XDataType durationType = new XDataType("duration", ClassType.make("gnu.math.Duration"), 28);
    public static final XDataType yearMonthDurationType = new XDataType("yearMonthDuration", ClassType.make("gnu.math.Duration"), 29);
    public static final XDataType dayTimeDurationType = new XDataType("dayTimeDuration", ClassType.make("gnu.math.Duration"), 30);
    public static final XDataType untypedType = new XDataType("untyped", Type.objectType, 48);
    public static final Double DOUBLE_ZERO = makeDouble(0.0d);
    public static final Double DOUBLE_ONE = makeDouble(1.0d);
    public static final Float FLOAT_ZERO = makeFloat(0.0f);
    public static final Float FLOAT_ONE = makeFloat(1.0f);
    public static final BigDecimal DECIMAL_ONE = BigDecimal.valueOf(1L);

    public XDataType(Object name, Type implementationType, int typeCode) {
        super(implementationType);
        this.name = name;
        if (name != null) {
            setName(name.toString());
        }
        this.implementationType = implementationType;
        this.typeCode = typeCode;
    }

    @Override // gnu.bytecode.Type
    public Class getReflectClass() {
        return this.implementationType.getReflectClass();
    }

    @Override // gnu.bytecode.Type
    public Type getImplementationType() {
        return this.implementationType;
    }

    @Override // gnu.bytecode.Type
    public void emitCoerceFromObject(CodeAttr code) {
        Compilation comp = Compilation.getCurrent();
        comp.compileConstant(this, Target.pushObject);
        Method meth = ClassType.make("gnu.kawa.xml.XDataType").getDeclaredMethod("coerceFromObject", 1);
        code.emitSwap();
        code.emitInvokeVirtual(meth);
        this.implementationType.emitCoerceFromObject(code);
    }

    @Override // gnu.bytecode.Type
    public void emitCoerceToObject(CodeAttr code) {
        if (this.typeCode == 31) {
            this.implementationType.emitCoerceToObject(code);
        } else {
            super.emitCoerceToObject(code);
        }
    }

    @Override // gnu.expr.TypeValue
    public void emitTestIf(Variable incoming, Declaration decl, Compilation comp) {
        CodeAttr code = comp.getCode();
        if (this.typeCode == 31) {
            if (incoming != null) {
                code.emitLoad(incoming);
            }
            Type.javalangBooleanType.emitIsInstance(code);
            code.emitIfIntNotZero();
            if (decl != null) {
                code.emitLoad(incoming);
                Type.booleanType.emitCoerceFromObject(code);
                decl.compileStore(comp);
                return;
            }
            return;
        }
        comp.compileConstant(this, Target.pushObject);
        if (incoming == null) {
            code.emitSwap();
        } else {
            code.emitLoad(incoming);
        }
        code.emitInvokeVirtual(Compilation.typeType.getDeclaredMethod("isInstance", 1));
        code.emitIfIntNotZero();
        if (decl != null) {
            code.emitLoad(incoming);
            emitCoerceFromObject(code);
            decl.compileStore(comp);
        }
    }

    @Override // gnu.expr.TypeValue
    public Expression convertValue(Expression value) {
        return null;
    }

    @Override // gnu.bytecode.Type
    public boolean isInstance(Object obj) {
        boolean z = false;
        switch (this.typeCode) {
            case 2:
                return ((obj instanceof SeqPosition) || (obj instanceof Nodes)) ? false : true;
            case 3:
                return ((obj instanceof Values) || (obj instanceof SeqPosition)) ? false : true;
            case 4:
                if ((obj instanceof BigDecimal) || (obj instanceof IntNum)) {
                    z = true;
                }
                return z;
            case 18:
                return obj instanceof Float;
            case 19:
                return obj instanceof Double;
            case 28:
                return obj instanceof Duration;
            case 29:
                return (obj instanceof Duration) && ((Duration) obj).unit() == Unit.month;
            case 30:
                return (obj instanceof Duration) && ((Duration) obj).unit() == Unit.second;
            case 31:
                return obj instanceof Boolean;
            case 33:
                return obj instanceof Path;
            case 37:
                return obj instanceof UntypedAtomic;
            case 38:
                return obj instanceof CharSequence;
            case 48:
                return true;
            default:
                return super.isInstance(obj);
        }
    }

    @Override // gnu.expr.TypeValue
    public void emitIsInstance(Variable incoming, Compilation comp, Target target) {
        InstanceOf.emitIsInstance(this, incoming, comp, target);
    }

    public String toString(Object value) {
        return value.toString();
    }

    public void print(Object value, Consumer out) {
        if (value instanceof Printable) {
            ((Printable) value).print(out);
        } else {
            out.write(toString(value));
        }
    }

    public boolean castable(Object value) {
        try {
            cast(value);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:94:0x016b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object cast(java.lang.Object r9) {
        /*
            Method dump skipped, instructions count: 452
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.xml.XDataType.cast(java.lang.Object):java.lang.Object");
    }

    Duration castToDuration(Object value, Unit unit) {
        if (value instanceof Duration) {
            Duration dur = (Duration) value;
            if (dur.unit() != unit) {
                int months = dur.getTotalMonths();
                long seconds = dur.getTotalSeconds();
                int nanos = dur.getNanoSecondsOnly();
                if (unit == Unit.second) {
                    months = 0;
                }
                if (unit == Unit.month) {
                    seconds = 0;
                    nanos = 0;
                }
                return Duration.make(months, seconds, nanos, unit);
            }
            return dur;
        }
        return (Duration) coerceFromObject(value);
    }

    @Override // gnu.bytecode.Type
    public Object coerceFromObject(Object obj) {
        if (!isInstance(obj)) {
            throw new ClassCastException("cannot cast " + obj + " to " + this.name);
        }
        return obj;
    }

    @Override // gnu.bytecode.Type
    public int compare(Type other) {
        if (this == other || ((this == stringStringType && other == stringType) || (this == stringType && other == stringStringType))) {
            return 0;
        }
        return this.implementationType.compare(other);
    }

    public Object valueOf(String value) {
        char ch;
        switch (this.typeCode) {
            case 4:
                String value2 = value.trim();
                int i = value2.length();
                do {
                    i--;
                    if (i >= 0) {
                        ch = value2.charAt(i);
                        if (ch != 'e') {
                        }
                        throw new IllegalArgumentException("not a valid decimal: '" + value2 + "'");
                    }
                    return new BigDecimal(value2);
                } while (ch != 'E');
                throw new IllegalArgumentException("not a valid decimal: '" + value2 + "'");
            case 18:
            case 19:
                String value3 = value.trim();
                if ("INF".equals(value3)) {
                    value3 = "Infinity";
                } else if ("-INF".equals(value3)) {
                    value3 = "-Infinity";
                }
                return this.typeCode == 18 ? Float.valueOf(value3) : Double.valueOf(value3);
            case 28:
                return Duration.parseDuration(value);
            case 29:
                return Duration.parseYearMonthDuration(value);
            case 30:
                return Duration.parseDayTimeDuration(value);
            case 31:
                String value4 = value.trim();
                if (value4.equals("true") || value4.equals(Component.TYPEFACE_SANSSERIF)) {
                    return Boolean.TRUE;
                }
                if (value4.equals("false") || value4.equals(Component.TYPEFACE_DEFAULT)) {
                    return Boolean.FALSE;
                }
                throw new IllegalArgumentException("not a valid boolean: '" + value4 + "'");
            case 33:
                return URIPath.makeURI(TextUtils.replaceWhitespace(value, true));
            case 34:
                return Base64Binary.valueOf(value);
            case 35:
                return HexBinary.valueOf(value);
            case 37:
                return new UntypedAtomic(value);
            case 38:
                return value;
            default:
                throw new RuntimeException("valueOf not implemented for " + this.name);
        }
    }

    public static Float makeFloat(float value) {
        return Float.valueOf(value);
    }

    public static Double makeDouble(double value) {
        return Double.valueOf(value);
    }

    @Override // gnu.expr.TypeValue
    public Procedure getConstructor() {
        return null;
    }
}
