package gnu.expr;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.lists.FString;
import gnu.mapping.Symbol;
import gnu.mapping.Table2D;
import gnu.mapping.Values;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.IdentityHashMap;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class LitTable implements ObjectOutput {
    static Table2D staticTable = new Table2D(100);
    Compilation comp;
    Literal literalsChain;
    int literalsCount;
    ClassType mainClass;
    int stackPointer;
    IdentityHashMap literalTable = new IdentityHashMap(100);
    Object[] valueStack = new Object[20];
    Type[] typeStack = new Type[20];

    public LitTable(Compilation comp) {
        this.comp = comp;
        this.mainClass = comp.mainClass;
    }

    public void emit() throws IOException {
        for (Literal init = this.literalsChain; init != null; init = init.next) {
            writeObject(init.value);
        }
        for (Literal init2 = this.literalsChain; init2 != null; init2 = init2.next) {
            emit(init2, true);
        }
        this.literalTable = null;
        this.literalsCount = 0;
    }

    void push(Object value, Type type) {
        if (this.stackPointer >= this.valueStack.length) {
            Object[] newValues = new Object[this.valueStack.length * 2];
            Type[] newTypes = new Type[this.typeStack.length * 2];
            System.arraycopy(this.valueStack, 0, newValues, 0, this.stackPointer);
            System.arraycopy(this.typeStack, 0, newTypes, 0, this.stackPointer);
            this.valueStack = newValues;
            this.typeStack = newTypes;
        }
        this.valueStack[this.stackPointer] = value;
        this.typeStack[this.stackPointer] = type;
        this.stackPointer++;
    }

    void error(String msg) {
        throw new Error(msg);
    }

    @Override // java.io.ObjectOutput
    public void flush() {
    }

    @Override // java.io.ObjectOutput, java.lang.AutoCloseable
    public void close() {
    }

    @Override // java.io.ObjectOutput, java.io.DataOutput
    public void write(int b) throws IOException {
        error("cannot handle call to write(int) when externalizing literal");
    }

    @Override // java.io.DataOutput
    public void writeBytes(String s) throws IOException {
        error("cannot handle call to writeBytes(String) when externalizing literal");
    }

    @Override // java.io.ObjectOutput, java.io.DataOutput
    public void write(byte[] b) throws IOException {
        error("cannot handle call to write(byte[]) when externalizing literal");
    }

    @Override // java.io.ObjectOutput, java.io.DataOutput
    public void write(byte[] b, int off, int len) throws IOException {
        error("cannot handle call to write(byte[],int,int) when externalizing literal");
    }

    @Override // java.io.DataOutput
    public void writeBoolean(boolean v) {
        push(new Boolean(v), Type.booleanType);
    }

    @Override // java.io.DataOutput
    public void writeChar(int v) {
        push(new Character((char) v), Type.charType);
    }

    @Override // java.io.DataOutput
    public void writeByte(int v) {
        push(new Byte((byte) v), Type.byteType);
    }

    @Override // java.io.DataOutput
    public void writeShort(int v) {
        push(new Short((short) v), Type.shortType);
    }

    @Override // java.io.DataOutput
    public void writeInt(int v) {
        push(new Integer(v), Type.intType);
    }

    @Override // java.io.DataOutput
    public void writeLong(long v) {
        push(new Long(v), Type.longType);
    }

    @Override // java.io.DataOutput
    public void writeFloat(float v) {
        push(new Float(v), Type.floatType);
    }

    @Override // java.io.DataOutput
    public void writeDouble(double v) {
        push(new Double(v), Type.doubleType);
    }

    @Override // java.io.DataOutput
    public void writeUTF(String v) {
        push(v, Type.string_type);
    }

    @Override // java.io.DataOutput
    public void writeChars(String v) {
        push(v, Type.string_type);
    }

    @Override // java.io.ObjectOutput
    public void writeObject(Object obj) throws IOException {
        Literal lit = findLiteral(obj);
        if ((lit.flags & 3) != 0) {
            if (lit.field == null && obj != null && !(obj instanceof String)) {
                lit.assign(this);
            }
            if ((lit.flags & 2) == 0) {
                lit.flags |= 4;
            }
        } else {
            lit.flags |= 1;
            int oldStack = this.stackPointer;
            if ((obj instanceof FString) && ((FString) obj).size() < 65535) {
                push(obj.toString(), Type.string_type);
            } else if (obj instanceof Externalizable) {
                ((Externalizable) obj).writeExternal(this);
            } else if (obj instanceof Object[]) {
                Object[] arr = (Object[]) obj;
                for (Object obj2 : arr) {
                    writeObject(obj2);
                }
            } else if (obj != null && !(obj instanceof String) && !(lit.type instanceof ArrayType)) {
                if (obj instanceof BigInteger) {
                    writeChars(obj.toString());
                } else if (obj instanceof BigDecimal) {
                    BigDecimal dec = (BigDecimal) obj;
                    writeObject(dec.unscaledValue());
                    writeInt(dec.scale());
                } else if (obj instanceof Integer) {
                    push(obj, Type.intType);
                } else if (obj instanceof Short) {
                    push(obj, Type.shortType);
                } else if (obj instanceof Byte) {
                    push(obj, Type.byteType);
                } else if (obj instanceof Long) {
                    push(obj, Type.longType);
                } else if (obj instanceof Double) {
                    push(obj, Type.doubleType);
                } else if (obj instanceof Float) {
                    push(obj, Type.floatType);
                } else if (obj instanceof Character) {
                    push(obj, Type.charType);
                } else if (obj instanceof Class) {
                    push(obj, Type.java_lang_Class_type);
                } else if (obj instanceof Pattern) {
                    Pattern pat = (Pattern) obj;
                    push(pat.pattern(), Type.string_type);
                    push(Integer.valueOf(pat.flags()), Type.intType);
                } else {
                    error(obj.getClass().getName() + " does not implement Externalizable");
                }
            }
            int nargs = this.stackPointer - oldStack;
            if (nargs == 0) {
                lit.argValues = Values.noArgs;
                lit.argTypes = Type.typeArray0;
            } else {
                lit.argValues = new Object[nargs];
                lit.argTypes = new Type[nargs];
                System.arraycopy(this.valueStack, oldStack, lit.argValues, 0, nargs);
                System.arraycopy(this.typeStack, oldStack, lit.argTypes, 0, nargs);
                this.stackPointer = oldStack;
            }
            lit.flags |= 2;
        }
        push(lit, lit.type);
    }

    public Literal findLiteral(Object value) {
        Literal literal;
        if (value == null) {
            return Literal.nullLiteral;
        }
        Literal literal2 = (Literal) this.literalTable.get(value);
        if (literal2 == null) {
            if (this.comp.immediate) {
                return new Literal(value, this);
            }
            Class valueClass = value.getClass();
            Type valueType = Type.make(valueClass);
            synchronized (staticTable) {
                literal = (Literal) staticTable.get(value, null, null);
                if ((literal == null || literal.value != value) && (valueType instanceof ClassType)) {
                    Class fldClass = valueClass;
                    ClassType fldType = (ClassType) valueType;
                    while (staticTable.get(fldClass, Boolean.TRUE, null) == null) {
                        staticTable.put(fldClass, Boolean.TRUE, fldClass);
                        for (Field fld = fldType.getFields(); fld != null; fld = fld.getNext()) {
                            if ((fld.getModifiers() & 25) == 25) {
                                java.lang.reflect.Field rfld = fld.getReflectField();
                                Object litValue = rfld.get(null);
                                if (litValue != null && fldClass.isInstance(litValue)) {
                                    Literal lit = new Literal(litValue, fld, this);
                                    staticTable.put(litValue, null, lit);
                                    if (value == litValue) {
                                        literal = lit;
                                    }
                                }
                            }
                        }
                        fldClass = fldClass.getSuperclass();
                        if (fldClass == null) {
                            break;
                        }
                        fldType = (ClassType) Type.make(fldClass);
                    }
                }
            }
            if (literal != null) {
                this.literalTable.put(value, literal);
                return literal;
            }
            return new Literal(value, valueType, this);
        }
        return literal2;
    }

    Method getMethod(ClassType type, String name, Literal literal, boolean isStatic) {
        Type[] argTypes = literal.argTypes;
        int argLength = argTypes.length;
        Method best = null;
        long bestArrayArgs = 0;
        boolean ambiguous = false;
        Type[] bParameters = null;
        for (Method method = type.getDeclaredMethods(); method != null; method = method.getNext()) {
            if (name.equals(method.getName())) {
                boolean mstatic = method.getStaticFlag();
                if (isStatic == mstatic) {
                    long arrayArgs = 0;
                    Type[] mParameters = method.getParameterTypes();
                    int iarg = 0;
                    int iparam = 0;
                    while (true) {
                        if (iarg == argLength && iparam == mParameters.length) {
                            if (best == null || (bestArrayArgs != 0 && arrayArgs == 0)) {
                                best = method;
                                bParameters = mParameters;
                                bestArrayArgs = arrayArgs;
                            } else if (arrayArgs == 0) {
                                boolean not1 = false;
                                boolean not2 = false;
                                int j = argLength;
                                while (true) {
                                    j--;
                                    if (j < 0) {
                                        break;
                                    }
                                    int c = bParameters[j].compare(mParameters[j]);
                                    if (c != 1) {
                                        not2 = true;
                                        if (not1) {
                                            break;
                                        }
                                    }
                                    if (c != -1) {
                                        not1 = true;
                                        if (not2) {
                                            break;
                                        }
                                    }
                                }
                                if (not1) {
                                    best = method;
                                    bParameters = mParameters;
                                }
                                ambiguous = not1 && not2;
                            }
                        } else if (iarg != argLength && iparam != mParameters.length) {
                            Type aType = argTypes[iarg];
                            Type pType = mParameters[iparam];
                            if (!aType.isSubtype(pType)) {
                                if ((pType instanceof ArrayType) && iparam < 64 && (aType == Type.intType || aType == Type.shortType)) {
                                    int count = ((Number) literal.argValues[iarg]).intValue();
                                    if (count < 0 && type.getName().equals("gnu.math.IntNum")) {
                                        count -= Integer.MIN_VALUE;
                                    }
                                    Type elementType = ((ArrayType) pType).getComponentType();
                                    if (count >= 0 && iarg + count < argLength) {
                                        int j2 = count;
                                        while (true) {
                                            j2--;
                                            if (j2 >= 0) {
                                                Type t = argTypes[iarg + j2 + 1];
                                                if (elementType instanceof PrimType) {
                                                    if (elementType.getSignature() != t.getSignature()) {
                                                        break;
                                                    }
                                                } else if (!t.isSubtype(elementType)) {
                                                    break;
                                                }
                                            } else {
                                                iarg += count;
                                                arrayArgs |= 1 << iparam;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            iarg++;
                            iparam++;
                        }
                    }
                }
            }
        }
        if (ambiguous) {
            return null;
        }
        if (bestArrayArgs != 0) {
            Object[] args = new Object[bParameters.length];
            Type[] types = new Type[bParameters.length];
            int iarg2 = 0;
            int iparam2 = 0;
            while (iarg2 != argLength) {
                Type pType2 = bParameters[iparam2];
                if (((1 << iparam2) & bestArrayArgs) == 0) {
                    args[iparam2] = literal.argValues[iarg2];
                    types[iparam2] = literal.argTypes[iarg2];
                } else {
                    int count2 = ((Number) literal.argValues[iarg2]).intValue();
                    boolean isIntNum = type.getName().equals("gnu.math.IntNum");
                    if (isIntNum) {
                        count2 -= Integer.MIN_VALUE;
                    }
                    Type elementType2 = ((ArrayType) pType2).getComponentType();
                    types[iparam2] = pType2;
                    args[iparam2] = Array.newInstance(elementType2.getReflectClass(), count2);
                    Object[] argValues = literal.argValues;
                    if (isIntNum) {
                        int[] arr = (int[]) args[iparam2];
                        for (int j3 = count2; j3 > 0; j3--) {
                            arr[count2 - j3] = ((Integer) argValues[iarg2 + j3]).intValue();
                        }
                    } else {
                        int j4 = count2;
                        while (true) {
                            j4--;
                            if (j4 < 0) {
                                break;
                            }
                            Array.set(args[iparam2], j4, argValues[iarg2 + 1 + j4]);
                        }
                    }
                    Literal arrayLiteral = new Literal(args[iparam2], pType2);
                    if (elementType2 instanceof ObjectType) {
                        arrayLiteral.argValues = (Object[]) args[iparam2];
                    }
                    args[iparam2] = arrayLiteral;
                    iarg2 += count2;
                }
                iarg2++;
                iparam2++;
            }
            literal.argValues = args;
            literal.argTypes = types;
            return best;
        }
        return best;
    }

    void putArgs(Literal literal, CodeAttr code) {
        Type[] argTypes = literal.argTypes;
        int len = argTypes.length;
        for (int i = 0; i < len; i++) {
            Object value = literal.argValues[i];
            if (value instanceof Literal) {
                emit((Literal) value, false);
            } else {
                this.comp.compileConstant(value, new StackTarget(argTypes[i]));
            }
        }
    }

    private void store(Literal literal, boolean ignore, CodeAttr code) {
        if (literal.field != null) {
            if (!ignore) {
                code.emitDup(literal.type);
            }
            code.emitPutStatic(literal.field);
        }
        literal.flags |= 8;
    }

    void emit(Literal literal, boolean ignore) {
        CodeAttr code = this.comp.getCode();
        if (literal.value == null) {
            if (!ignore) {
                code.emitPushNull();
            }
        } else if (literal.value instanceof String) {
            if (!ignore) {
                code.emitPushString(literal.value.toString());
            }
        } else if ((literal.flags & 8) != 0) {
            if (!ignore) {
                code.emitGetStatic(literal.field);
            }
        } else if (literal.value instanceof Object[]) {
            int len = literal.argValues.length;
            Type elementType = ((ArrayType) literal.type).getComponentType();
            code.emitPushInt(len);
            code.emitNewArray(elementType);
            store(literal, ignore, code);
            for (int i = 0; i < len; i++) {
                Literal el = (Literal) literal.argValues[i];
                if (el.value != null) {
                    code.emitDup(elementType);
                    code.emitPushInt(i);
                    emit(el, false);
                    code.emitArrayStore(elementType);
                }
            }
        } else if (literal.type instanceof ArrayType) {
            code.emitPushPrimArray(literal.value, (ArrayType) literal.type);
            store(literal, ignore, code);
        } else if (literal.value instanceof Class) {
            Class clas = (Class) literal.value;
            if (clas.isPrimitive()) {
                String cname = clas.getName();
                if (cname.equals("int")) {
                    cname = PropertyTypeConstants.PROPERTY_TYPE_INTEGER;
                }
                code.emitGetStatic(ClassType.make("java.lang." + Character.toUpperCase(cname.charAt(0)) + cname.substring(1)).getDeclaredField("TYPE"));
            } else {
                this.comp.loadClassRef((ObjectType) Type.make(clas));
            }
            store(literal, ignore, code);
        } else if ((literal.value instanceof ClassType) && !((ClassType) literal.value).isExisting()) {
            this.comp.loadClassRef((ClassType) literal.value);
            Method meth = Compilation.typeType.getDeclaredMethod("valueOf", 1);
            if (meth == null) {
                meth = Compilation.typeType.getDeclaredMethod("make", 1);
            }
            code.emitInvokeStatic(meth);
            code.emitCheckcast(Compilation.typeClassType);
            store(literal, ignore, code);
        } else {
            ClassType type = (ClassType) literal.type;
            boolean useDefaultInit = (literal.flags & 4) != 0;
            Method method = null;
            boolean makeStatic = false;
            if (!useDefaultInit) {
                if (!(literal.value instanceof Symbol)) {
                    method = getMethod(type, "valueOf", literal, true);
                }
                if (method == null && !(literal.value instanceof Values)) {
                    String mname = "make";
                    if (literal.value instanceof Pattern) {
                        mname = "compile";
                    }
                    method = getMethod(type, mname, literal, true);
                }
                if (method != null) {
                    makeStatic = true;
                } else if (literal.argTypes.length > 0) {
                    method = getMethod(type, "<init>", literal, false);
                }
                if (method == null) {
                    useDefaultInit = true;
                }
            }
            if (useDefaultInit) {
                method = getMethod(type, "set", literal, false);
            }
            if (method == null && literal.argTypes.length > 0) {
                error("no method to construct " + literal.type);
            }
            if (makeStatic) {
                putArgs(literal, code);
                code.emitInvokeStatic(method);
            } else if (useDefaultInit) {
                code.emitNew(type);
                code.emitDup(type);
                Method init0 = type.getDeclaredMethod("<init>", 0);
                code.emitInvokeSpecial(init0);
            } else {
                code.emitNew(type);
                code.emitDup(type);
                putArgs(literal, code);
                code.emitInvokeSpecial(method);
            }
            Method resolveMethod = (makeStatic || (literal.value instanceof Values)) ? null : type.getDeclaredMethod("readResolve", 0);
            if (resolveMethod != null) {
                code.emitInvokeVirtual(resolveMethod);
                type.emitCoerceFromObject(code);
            }
            store(literal, ignore && (!useDefaultInit || method == null), code);
            if (useDefaultInit && method != null) {
                if (!ignore) {
                    code.emitDup(type);
                }
                putArgs(literal, code);
                code.emitInvokeVirtual(method);
            }
        }
    }
}
