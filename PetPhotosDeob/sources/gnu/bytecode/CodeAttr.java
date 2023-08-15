package gnu.bytecode;

import com.google.appinventor.components.runtime.util.Ev3Constants;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class CodeAttr extends Attribute implements AttrContainer {
    public static final int DONT_USE_JSR = 2;
    static final int FIXUP_CASE = 3;
    static final int FIXUP_DEFINE = 1;
    static final int FIXUP_DELETE3 = 8;
    static final int FIXUP_GOTO = 4;
    static final int FIXUP_JSR = 5;
    static final int FIXUP_LINE_NUMBER = 15;
    static final int FIXUP_LINE_PC = 14;
    static final int FIXUP_MOVE = 9;
    static final int FIXUP_MOVE_TO_END = 10;
    static final int FIXUP_NONE = 0;
    static final int FIXUP_SWITCH = 2;
    static final int FIXUP_TRANSFER = 6;
    static final int FIXUP_TRANSFER2 = 7;
    static final int FIXUP_TRY = 11;
    static final int FIXUP_TRY_END = 12;
    static final int FIXUP_TRY_HANDLER = 13;
    public static final int GENERATE_STACK_MAP_TABLE = 1;
    public static boolean instructionLineMode = false;
    int PC;
    int SP;
    Attribute attributes;
    byte[] code;
    ExitableBlock currentExitableBlock;
    short[] exception_table;
    int exception_table_length;
    int exitableBlockLevel;
    int fixup_count;
    Label[] fixup_labels;
    int[] fixup_offsets;
    int flags;
    IfState if_stack;
    LineNumbersAttr lines;
    Type[] local_types;
    public LocalVarsAttr locals;
    private int max_locals;
    private int max_stack;
    Label previousLabel;
    SourceDebugExtAttr sourceDbgExt;
    public StackMapTableAttr stackMap;
    public Type[] stack_types;
    TryState try_stack;
    private boolean unreachable_here;
    boolean[] varsSetInCurrentBlock;

    @Override // gnu.bytecode.AttrContainer
    public final Attribute getAttributes() {
        return this.attributes;
    }

    @Override // gnu.bytecode.AttrContainer
    public final void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean useJsr() {
        return (this.flags & 2) == 0;
    }

    public final void fixupChain(Label here, Label target) {
        fixupAdd(9, 0, target);
        here.defineRaw(this);
    }

    public final void fixupAdd(int kind, Label label) {
        fixupAdd(kind, this.PC, label);
    }

    final void fixupAdd(int kind, int offset, Label label) {
        if (label != null && kind != 1 && kind != 0 && kind != 2 && kind != 11) {
            label.needsStackMapEntry = true;
        }
        int count = this.fixup_count;
        if (count == 0) {
            this.fixup_offsets = new int[30];
            this.fixup_labels = new Label[30];
        } else if (this.fixup_count == this.fixup_offsets.length) {
            int new_length = count * 2;
            Label[] new_labels = new Label[new_length];
            System.arraycopy(this.fixup_labels, 0, new_labels, 0, count);
            this.fixup_labels = new_labels;
            int[] new_offsets = new int[new_length];
            System.arraycopy(this.fixup_offsets, 0, new_offsets, 0, count);
            this.fixup_offsets = new_offsets;
        }
        this.fixup_offsets[count] = (offset << 4) | kind;
        this.fixup_labels[count] = label;
        this.fixup_count = count + 1;
    }

    private final int fixupOffset(int index) {
        return this.fixup_offsets[index] >> 4;
    }

    private final int fixupKind(int index) {
        return this.fixup_offsets[index] & 15;
    }

    public final Method getMethod() {
        return (Method) getContainer();
    }

    public final int getPC() {
        return this.PC;
    }

    public final int getSP() {
        return this.SP;
    }

    public final ConstantPool getConstants() {
        return getMethod().classfile.constants;
    }

    public final boolean reachableHere() {
        return !this.unreachable_here;
    }

    public final void setReachable(boolean val) {
        this.unreachable_here = !val;
    }

    public final void setUnreachable() {
        this.unreachable_here = true;
    }

    public int getMaxStack() {
        return this.max_stack;
    }

    public int getMaxLocals() {
        return this.max_locals;
    }

    public void setMaxStack(int n) {
        this.max_stack = n;
    }

    public void setMaxLocals(int n) {
        this.max_locals = n;
    }

    public byte[] getCode() {
        return this.code;
    }

    public void setCode(byte[] code) {
        this.code = code;
        this.PC = code.length;
    }

    public void setCodeLength(int len) {
        this.PC = len;
    }

    public int getCodeLength() {
        return this.PC;
    }

    public CodeAttr(Method meth) {
        super("Code");
        addToFrontOf(meth);
        meth.code = this;
        if (meth.getDeclaringClass().getClassfileMajorVersion() >= 50) {
            this.flags |= 3;
        }
    }

    public final void reserve(int bytes) {
        if (this.code == null) {
            this.code = new byte[bytes + 100];
        } else if (this.PC + bytes > this.code.length) {
            byte[] new_code = new byte[(this.code.length * 2) + bytes];
            System.arraycopy(this.code, 0, new_code, 0, this.PC);
            this.code = new_code;
        }
    }

    byte invert_opcode(byte opcode) {
        int iopcode = opcode & Ev3Constants.Opcode.TST;
        if ((iopcode >= 153 && iopcode <= 166) || (iopcode >= 198 && iopcode <= 199)) {
            return (byte) (iopcode ^ 1);
        }
        throw new Error("unknown opcode to invert_opcode");
    }

    public final void put1(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) i;
        this.unreachable_here = false;
    }

    public final void put2(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) (i >> 8);
        byte[] bArr2 = this.code;
        int i3 = this.PC;
        this.PC = i3 + 1;
        bArr2[i3] = (byte) i;
        this.unreachable_here = false;
    }

    public final void put4(int i) {
        byte[] bArr = this.code;
        int i2 = this.PC;
        this.PC = i2 + 1;
        bArr[i2] = (byte) (i >> 24);
        byte[] bArr2 = this.code;
        int i3 = this.PC;
        this.PC = i3 + 1;
        bArr2[i3] = (byte) (i >> 16);
        byte[] bArr3 = this.code;
        int i4 = this.PC;
        this.PC = i4 + 1;
        bArr3[i4] = (byte) (i >> 8);
        byte[] bArr4 = this.code;
        int i5 = this.PC;
        this.PC = i5 + 1;
        bArr4[i5] = (byte) i;
        this.unreachable_here = false;
    }

    public final void putIndex2(CpoolEntry cnst) {
        put2(cnst.index);
    }

    public final void putLineNumber(String filename, int linenumber) {
        if (filename != null) {
            getMethod().classfile.setSourceFile(filename);
        }
        putLineNumber(linenumber);
    }

    public final void putLineNumber(int linenumber) {
        if (this.sourceDbgExt != null) {
            linenumber = this.sourceDbgExt.fixLine(linenumber);
        }
        fixupAdd(14, null);
        fixupAdd(15, linenumber, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void noteParamTypes() {
        Method method = getMethod();
        int offset = 0;
        if ((method.access_flags & 8) == 0) {
            Type type = method.classfile;
            if ("<init>".equals(method.getName()) && !"java.lang.Object".equals(type.getName())) {
                type = UninitializedType.uninitializedThis((ClassType) type);
            }
            int offset2 = 0 + 1;
            noteVarType(0, type);
            offset = offset2;
        }
        int arg_count = method.arg_types.length;
        int i = 0;
        int offset3 = offset;
        while (i < arg_count) {
            Type type2 = method.arg_types[i];
            int offset4 = offset3 + 1;
            noteVarType(offset3, type2);
            int size = type2.getSizeInWords();
            while (true) {
                size--;
                if (size > 0) {
                    offset4++;
                }
            }
            i++;
            offset3 = offset4;
        }
        if ((this.flags & 1) != 0) {
            this.stackMap = new StackMapTableAttr();
            int[] encodedLocals = new int[offset3 + 20];
            int i2 = 0;
            int count = 0;
            while (i2 < offset3) {
                int encoded = this.stackMap.encodeVerificationType(this.local_types[i2], this);
                int count2 = count + 1;
                encodedLocals[count] = encoded;
                int tag = encoded & 255;
                if (tag == 3 || tag == 4) {
                    i2++;
                }
                i2++;
                count = count2;
            }
            this.stackMap.encodedLocals = encodedLocals;
            this.stackMap.countLocals = count;
            this.stackMap.encodedStack = new int[10];
            this.stackMap.countStack = 0;
        }
    }

    public void noteVarType(int offset, Type type) {
        Type prev;
        int size = type.getSizeInWords();
        if (this.local_types == null) {
            this.local_types = new Type[offset + size + 20];
        } else if (offset + size > this.local_types.length) {
            Type[] new_array = new Type[(offset + size) * 2];
            System.arraycopy(this.local_types, 0, new_array, 0, this.local_types.length);
            this.local_types = new_array;
        }
        this.local_types[offset] = type;
        if (this.varsSetInCurrentBlock == null) {
            this.varsSetInCurrentBlock = new boolean[this.local_types.length];
        } else if (this.varsSetInCurrentBlock.length <= offset) {
            boolean[] tmp = new boolean[this.local_types.length];
            System.arraycopy(this.varsSetInCurrentBlock, 0, tmp, 0, this.varsSetInCurrentBlock.length);
            this.varsSetInCurrentBlock = tmp;
        }
        this.varsSetInCurrentBlock[offset] = true;
        if (offset > 0 && (prev = this.local_types[offset - 1]) != null && prev.getSizeInWords() == 2) {
            this.local_types[offset - 1] = null;
        }
        while (true) {
            size--;
            if (size > 0) {
                offset++;
                this.local_types[offset] = null;
            } else {
                return;
            }
        }
    }

    public final void setTypes(Label label) {
        setTypes(label.localTypes, label.stackTypes);
    }

    public final void setTypes(Type[] labelLocals, Type[] labelStack) {
        int usedStack = labelStack.length;
        int usedLocals = labelLocals.length;
        if (this.local_types != null) {
            if (usedLocals > 0) {
                System.arraycopy(labelLocals, 0, this.local_types, 0, usedLocals);
            }
            for (int i = usedLocals; i < this.local_types.length; i++) {
                this.local_types[i] = null;
            }
        }
        if (this.stack_types == null || usedStack > this.stack_types.length) {
            this.stack_types = new Type[usedStack];
        } else {
            for (int i2 = usedStack; i2 < this.stack_types.length; i2++) {
                this.stack_types[i2] = null;
            }
        }
        System.arraycopy(labelStack, 0, this.stack_types, 0, usedStack);
        this.SP = usedStack;
    }

    public final void pushType(Type type) {
        if (type.size == 0) {
            throw new Error("pushing void type onto stack");
        }
        if (this.stack_types == null || this.stack_types.length == 0) {
            this.stack_types = new Type[20];
        } else if (this.SP + 1 >= this.stack_types.length) {
            Type[] new_array = new Type[this.stack_types.length * 2];
            System.arraycopy(this.stack_types, 0, new_array, 0, this.SP);
            this.stack_types = new_array;
        }
        if (type.size == 8) {
            Type[] typeArr = this.stack_types;
            int i = this.SP;
            this.SP = i + 1;
            typeArr[i] = Type.voidType;
        }
        Type[] typeArr2 = this.stack_types;
        int i2 = this.SP;
        this.SP = i2 + 1;
        typeArr2[i2] = type;
        if (this.SP > this.max_stack) {
            this.max_stack = this.SP;
        }
    }

    public final Type popType() {
        if (this.SP <= 0) {
            throw new Error("popType called with empty stack " + getMethod());
        }
        Type[] typeArr = this.stack_types;
        int i = this.SP - 1;
        this.SP = i;
        Type type = typeArr[i];
        if (type.size == 8 && !popType().isVoid()) {
            throw new Error("missing void type on stack");
        }
        return type;
    }

    public final Type topType() {
        return this.stack_types[this.SP - 1];
    }

    public void emitPop(int nvalues) {
        while (nvalues > 0) {
            reserve(1);
            Type type = popType();
            if (type.size > 4) {
                put1(88);
            } else if (nvalues > 1) {
                Type type2 = popType();
                if (type2.size > 4) {
                    put1(87);
                    reserve(1);
                }
                put1(88);
                nvalues--;
            } else {
                put1(87);
            }
            nvalues--;
        }
    }

    public Label getLabel() {
        Label label = new Label();
        label.defineRaw(this);
        return label;
    }

    public void emitSwap() {
        reserve(1);
        Type type1 = popType();
        Type type2 = popType();
        if (type1.size > 4 || type2.size > 4) {
            pushType(type2);
            pushType(type1);
            emitDupX();
            emitPop(1);
            return;
        }
        pushType(type1);
        put1(95);
        pushType(type2);
    }

    public void emitDup() {
        reserve(1);
        Type type = topType();
        put1(type.size <= 4 ? 89 : 92);
        pushType(type);
    }

    public void emitDupX() {
        reserve(1);
        Type type = popType();
        Type skipedType = popType();
        if (skipedType.size <= 4) {
            put1(type.size <= 4 ? 90 : 93);
        } else {
            put1(type.size <= 4 ? 91 : 94);
        }
        pushType(type);
        pushType(skipedType);
        pushType(type);
    }

    public void emitDup(int size, int offset) {
        int kind;
        if (size != 0) {
            reserve(1);
            Type copied1 = popType();
            Type copied2 = null;
            if (size == 1) {
                if (copied1.size > 4) {
                    throw new Error("using dup for 2-word type");
                }
            } else if (size != 2) {
                throw new Error("invalid size to emitDup");
            } else {
                if (copied1.size <= 4) {
                    copied2 = popType();
                    if (copied2.size > 4) {
                        throw new Error("dup will cause invalid types on stack");
                    }
                }
            }
            Type skipped1 = null;
            Type skipped2 = null;
            if (offset == 0) {
                kind = size == 1 ? 89 : 92;
            } else if (offset == 1) {
                kind = size == 1 ? 90 : 93;
                skipped1 = popType();
                if (skipped1.size > 4) {
                    throw new Error("dup will cause invalid types on stack");
                }
            } else if (offset == 2) {
                kind = size == 1 ? 91 : 94;
                skipped1 = popType();
                if (skipped1.size <= 4) {
                    skipped2 = popType();
                    if (skipped2.size > 4) {
                        throw new Error("dup will cause invalid types on stack");
                    }
                }
            } else {
                throw new Error("emitDup:  invalid offset");
            }
            put1(kind);
            if (copied2 != null) {
                pushType(copied2);
            }
            pushType(copied1);
            if (skipped2 != null) {
                pushType(skipped2);
            }
            if (skipped1 != null) {
                pushType(skipped1);
            }
            if (copied2 != null) {
                pushType(copied2);
            }
            pushType(copied1);
        }
    }

    public void emitDup(int size) {
        emitDup(size, 0);
    }

    public void emitDup(Type type) {
        emitDup(type.size > 4 ? 2 : 1, 0);
    }

    public void enterScope(Scope scope) {
        scope.setStartPC(this);
        this.locals.enterScope(scope);
    }

    public Scope pushScope() {
        Scope scope = new Scope();
        if (this.locals == null) {
            this.locals = new LocalVarsAttr(getMethod());
        }
        enterScope(scope);
        if (this.locals.parameter_scope == null) {
            this.locals.parameter_scope = scope;
        }
        return scope;
    }

    public Scope getCurrentScope() {
        return this.locals.current_scope;
    }

    public Scope popScope() {
        Scope scope = this.locals.current_scope;
        this.locals.current_scope = scope.parent;
        scope.freeLocals(this);
        scope.end = getLabel();
        return scope;
    }

    public Variable getArg(int index) {
        return this.locals.parameter_scope.getVariable(index);
    }

    public Variable lookup(String name) {
        for (Scope scope = this.locals.current_scope; scope != null; scope = scope.parent) {
            Variable var = scope.lookup(name);
            if (var != null) {
                return var;
            }
        }
        return null;
    }

    public Variable addLocal(Type type) {
        return this.locals.current_scope.addVariable(this, type, null);
    }

    public Variable addLocal(Type type, String name) {
        return this.locals.current_scope.addVariable(this, type, name);
    }

    public void addParamLocals() {
        Method method = getMethod();
        if ((method.access_flags & 8) == 0) {
            addLocal(method.classfile).setParameter(true);
        }
        int arg_count = method.arg_types.length;
        for (int i = 0; i < arg_count; i++) {
            addLocal(method.arg_types[i]).setParameter(true);
        }
    }

    public final void emitPushConstant(int val, Type type) {
        switch (type.getSignature().charAt(0)) {
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z':
                emitPushInt(val);
                return;
            case 'D':
                emitPushDouble(val);
                return;
            case 'F':
                emitPushFloat(val);
                return;
            case 'J':
                emitPushLong(val);
                return;
            default:
                throw new Error("bad type to emitPushConstant");
        }
    }

    public final void emitPushConstant(CpoolEntry cnst) {
        reserve(3);
        int index = cnst.index;
        if (cnst instanceof CpoolValue2) {
            put1(20);
            put2(index);
        } else if (index < 256) {
            put1(18);
            put1(index);
        } else {
            put1(19);
            put2(index);
        }
    }

    public final void emitPushInt(int i) {
        reserve(3);
        if (i >= -1 && i <= 5) {
            put1(i + 3);
        } else if (i >= -128 && i < 128) {
            put1(16);
            put1(i);
        } else if (i >= -32768 && i < 32768) {
            put1(17);
            put2(i);
        } else {
            emitPushConstant(getConstants().addInt(i));
        }
        pushType(Type.intType);
    }

    public void emitPushLong(long i) {
        if (i == 0 || i == 1) {
            reserve(1);
            put1(((int) i) + 9);
        } else if (((int) i) == i) {
            emitPushInt((int) i);
            reserve(1);
            popType();
            put1(133);
        } else {
            emitPushConstant(getConstants().addLong(i));
        }
        pushType(Type.longType);
    }

    public void emitPushFloat(float x) {
        int xi = (int) x;
        if (xi == x && xi >= -128 && xi < 128) {
            if (xi >= 0 && xi <= 2) {
                reserve(1);
                put1(xi + 11);
                if (xi == 0 && Float.floatToIntBits(x) != 0) {
                    reserve(1);
                    put1(118);
                }
            } else {
                emitPushInt(xi);
                reserve(1);
                popType();
                put1(134);
            }
        } else {
            emitPushConstant(getConstants().addFloat(x));
        }
        pushType(Type.floatType);
    }

    public void emitPushDouble(double x) {
        int xi = (int) x;
        if (xi == x && xi >= -128 && xi < 128) {
            if (xi == 0 || xi == 1) {
                reserve(1);
                put1(xi + 14);
                if (xi == 0 && Double.doubleToLongBits(x) != 0) {
                    reserve(1);
                    put1(119);
                }
            } else {
                emitPushInt(xi);
                reserve(1);
                popType();
                put1(135);
            }
        } else {
            emitPushConstant(getConstants().addDouble(x));
        }
        pushType(Type.doubleType);
    }

    public static final String calculateSplit(String str) {
        int bytes;
        int strLength = str.length();
        StringBuffer sbuf = new StringBuffer(20);
        int segmentStart = 0;
        int byteLength = 0;
        for (int i = 0; i < strLength; i++) {
            char ch = str.charAt(i);
            if (ch >= 2048) {
                bytes = 3;
            } else {
                bytes = (ch >= 128 || ch == 0) ? 2 : 1;
            }
            if (byteLength + bytes > 65535) {
                sbuf.append((char) (i - segmentStart));
                segmentStart = i;
                byteLength = 0;
            }
            byteLength += bytes;
        }
        sbuf.append((char) (strLength - segmentStart));
        return sbuf.toString();
    }

    public final void emitPushString(String str) {
        if (str == null) {
            emitPushNull();
            return;
        }
        int length = str.length();
        String segments = calculateSplit(str);
        int numSegments = segments.length();
        if (numSegments <= 1) {
            emitPushConstant(getConstants().addString(str));
            pushType(Type.javalangStringType);
            return;
        }
        if (numSegments == 2) {
            int firstSegment = segments.charAt(0);
            emitPushString(str.substring(0, firstSegment));
            emitPushString(str.substring(firstSegment));
            Method concatMethod = Type.javalangStringType.getDeclaredMethod("concat", 1);
            emitInvokeVirtual(concatMethod);
        } else {
            ClassType sbufType = ClassType.make("java.lang.StringBuffer");
            emitNew(sbufType);
            emitDup(sbufType);
            emitPushInt(length);
            Type[] args1 = {Type.intType};
            emitInvokeSpecial(sbufType.getDeclaredMethod("<init>", args1));
            Type[] args2 = {Type.javalangStringType};
            Method appendMethod = sbufType.getDeclaredMethod("append", args2);
            int segStart = 0;
            for (int seg = 0; seg < numSegments; seg++) {
                emitDup(sbufType);
                int segEnd = segStart + segments.charAt(seg);
                emitPushString(str.substring(segStart, segEnd));
                emitInvokeVirtual(appendMethod);
                segStart = segEnd;
            }
            emitInvokeVirtual(Type.toString_method);
        }
        if (str == str.intern()) {
            emitInvokeVirtual(Type.javalangStringType.getDeclaredMethod("intern", 0));
        }
    }

    public final void emitPushClass(ObjectType ctype) {
        emitPushConstant(getConstants().addClass(ctype));
        pushType(Type.javalangClassType);
    }

    public void emitPushNull() {
        reserve(1);
        put1(1);
        pushType(Type.nullType);
    }

    public void emitPushDefaultValue(Type type) {
        Type type2 = type.getImplementationType();
        if (type2 instanceof PrimType) {
            emitPushConstant(0, type2);
        } else {
            emitPushNull();
        }
    }

    public void emitStoreDefaultValue(Variable var) {
        emitPushDefaultValue(var.getType());
        emitStore(var);
    }

    public final void emitPushThis() {
        emitLoad(this.locals.used[0]);
    }

    public final void emitPushPrimArray(Object value, ArrayType arrayType) {
        Type elementType = arrayType.getComponentType();
        int len = Array.getLength(value);
        emitPushInt(len);
        emitNewArray(elementType);
        char sig = elementType.getSignature().charAt(0);
        for (int i = 0; i < len; i++) {
            long ival = 0;
            float fval = 0.0f;
            double dval = 0.0d;
            switch (sig) {
                case 'B':
                    ival = ((byte[]) value)[i];
                    if (ival == 0) {
                        continue;
                    }
                    break;
                case 'C':
                    ival = ((char[]) value)[i];
                    if (ival == 0) {
                        continue;
                    }
                    break;
                case 'D':
                    dval = ((double[]) value)[i];
                    if (dval == 0.0d) {
                        continue;
                    }
                    break;
                case 'F':
                    fval = ((float[]) value)[i];
                    if (fval == 0.0d) {
                        continue;
                    }
                    break;
                case 'I':
                    ival = ((int[]) value)[i];
                    if (ival == 0) {
                        continue;
                    }
                    break;
                case 'J':
                    ival = ((long[]) value)[i];
                    if (ival == 0) {
                        continue;
                    }
                    break;
                case 'S':
                    ival = ((short[]) value)[i];
                    if (ival == 0) {
                        continue;
                    }
                    break;
                case 'Z':
                    ival = ((boolean[]) value)[i] ? 1L : 0L;
                    if (ival == 0) {
                        continue;
                    }
                    break;
            }
            emitDup(arrayType);
            emitPushInt(i);
            switch (sig) {
                case 'B':
                case 'C':
                case 'I':
                case 'S':
                case 'Z':
                    emitPushInt((int) ival);
                    break;
                case 'D':
                    emitPushDouble(dval);
                    break;
                case 'F':
                    emitPushFloat(fval);
                    break;
                case 'J':
                    emitPushLong(ival);
                    break;
            }
            emitArrayStore(elementType);
        }
    }

    void emitNewArray(int type_code) {
        reserve(2);
        put1(188);
        put1(type_code);
    }

    public final void emitArrayLength() {
        if (!(popType() instanceof ArrayType)) {
            throw new Error("non-array type in emitArrayLength");
        }
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SEEK);
        pushType(Type.intType);
    }

    private int adjustTypedOp(char sig) {
        switch (sig) {
            case 'B':
            case 'Z':
                return 5;
            case 'C':
                return 6;
            case 'D':
                return 3;
            case 'F':
                return 2;
            case 'I':
                return 0;
            case 'J':
                return 1;
            case 'S':
                return 7;
            default:
                return 4;
        }
    }

    private int adjustTypedOp(Type type) {
        return adjustTypedOp(type.getSignature().charAt(0));
    }

    private void emitTypedOp(int op, Type type) {
        reserve(1);
        put1(adjustTypedOp(type) + op);
    }

    private void emitTypedOp(int op, char sig) {
        reserve(1);
        put1(adjustTypedOp(sig) + op);
    }

    public void emitArrayStore(Type element_type) {
        popType();
        popType();
        popType();
        emitTypedOp(79, element_type);
    }

    public void emitArrayStore() {
        popType();
        popType();
        Type arrayType = popType().getImplementationType();
        Type elementType = ((ArrayType) arrayType).getComponentType();
        emitTypedOp(79, elementType);
    }

    public void emitArrayLoad(Type element_type) {
        popType();
        popType();
        emitTypedOp(46, element_type);
        pushType(element_type);
    }

    public void emitArrayLoad() {
        popType();
        Type arrayType = popType().getImplementationType();
        Type elementType = ((ArrayType) arrayType).getComponentType();
        emitTypedOp(46, elementType);
        pushType(elementType);
    }

    public void emitNew(ClassType type) {
        reserve(3);
        Label label = new Label(this);
        label.defineRaw(this);
        put1(187);
        putIndex2(getConstants().addClass(type));
        pushType(new UninitializedType(type, label));
    }

    public void emitNewArray(Type element_type, int dims) {
        int code;
        if (popType().promote() != Type.intType) {
            throw new Error("non-int dim. spec. in emitNewArray");
        }
        if (element_type instanceof PrimType) {
            switch (element_type.getSignature().charAt(0)) {
                case 'B':
                    code = 8;
                    break;
                case 'C':
                    code = 5;
                    break;
                case 'D':
                    code = 7;
                    break;
                case 'F':
                    code = 6;
                    break;
                case 'I':
                    code = 10;
                    break;
                case 'J':
                    code = 11;
                    break;
                case 'S':
                    code = 9;
                    break;
                case 'Z':
                    code = 4;
                    break;
                default:
                    throw new Error("bad PrimType in emitNewArray");
            }
            emitNewArray(code);
        } else if (element_type instanceof ObjectType) {
            reserve(3);
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG);
            putIndex2(getConstants().addClass((ObjectType) element_type));
        } else if (element_type instanceof ArrayType) {
            reserve(4);
            put1(197);
            putIndex2(getConstants().addClass(new ArrayType(element_type)));
            if (dims < 1 || dims > 255) {
                throw new Error("dims out of range in emitNewArray");
            }
            put1(dims);
            do {
                dims--;
                if (dims > 0) {
                }
            } while (popType().promote() == Type.intType);
            throw new Error("non-int dim. spec. in emitNewArray");
        } else {
            throw new Error("unimplemented type in emitNewArray");
        }
        pushType(new ArrayType(element_type));
    }

    public void emitNewArray(Type element_type) {
        emitNewArray(element_type, 1);
    }

    private void emitBinop(int base_code) {
        Type type2 = popType().promote();
        Type type1_raw = popType();
        Type type1 = type1_raw.promote();
        if (type1 != type2 || !(type1 instanceof PrimType)) {
            throw new Error("non-matching or bad types in binary operation");
        }
        emitTypedOp(base_code, type1);
        pushType(type1_raw);
    }

    private void emitBinop(int base_code, char sig) {
        popType();
        popType();
        emitTypedOp(base_code, sig);
        pushType(Type.signatureToPrimitive(sig));
    }

    public void emitBinop(int base_code, Type type) {
        popType();
        popType();
        emitTypedOp(base_code, type);
        pushType(type);
    }

    public final void emitAdd(char sig) {
        emitBinop(96, sig);
    }

    public final void emitAdd(PrimType type) {
        emitBinop(96, type);
    }

    public final void emitAdd() {
        emitBinop(96);
    }

    public final void emitSub(char sig) {
        emitBinop(100, sig);
    }

    public final void emitSub(PrimType type) {
        emitBinop(100, type);
    }

    public final void emitSub() {
        emitBinop(100);
    }

    public final void emitMul() {
        emitBinop(104);
    }

    public final void emitDiv() {
        emitBinop(108);
    }

    public final void emitRem() {
        emitBinop(112);
    }

    public final void emitAnd() {
        emitBinop(126);
    }

    public final void emitIOr() {
        emitBinop(128);
    }

    public final void emitXOr() {
        emitBinop(130);
    }

    public final void emitShl() {
        emitShift(120);
    }

    public final void emitShr() {
        emitShift(122);
    }

    public final void emitUshr() {
        emitShift(124);
    }

    private void emitShift(int base_code) {
        Type type2 = popType().promote();
        Type type1_raw = popType();
        Type type1 = type1_raw.promote();
        if (type1 != Type.intType && type1 != Type.longType) {
            throw new Error("the value shifted must be an int or a long");
        }
        if (type2 != Type.intType) {
            throw new Error("the amount of shift must be an int");
        }
        emitTypedOp(base_code, type1);
        pushType(type1_raw);
    }

    public final void emitNot(Type type) {
        emitPushConstant(1, type);
        emitAdd();
        emitPushConstant(1, type);
        emitAnd();
    }

    public void emitPrimop(int opcode, int arg_count, Type retType) {
        reserve(1);
        while (true) {
            arg_count--;
            if (arg_count >= 0) {
                popType();
            } else {
                put1(opcode);
                pushType(retType);
                return;
            }
        }
    }

    void emitMaybeWide(int opcode, int index) {
        if (index >= 256) {
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
            put1(opcode);
            put2(index);
            return;
        }
        put1(opcode);
        put1(index);
    }

    public final void emitLoad(Variable var) {
        if (var.dead()) {
            throw new Error("attempting to push dead variable");
        }
        int offset = var.offset;
        if (offset < 0 || !var.isSimple()) {
            throw new Error("attempting to load from unassigned variable " + var + " simple:" + var.isSimple() + ", offset: " + offset);
        }
        Type type = var.getType().promote();
        reserve(4);
        int kind = adjustTypedOp(type);
        if (offset <= 3) {
            put1((kind * 4) + 26 + offset);
        } else {
            emitMaybeWide(kind + 21, offset);
        }
        pushType(var.getType());
    }

    public void emitStore(Variable var) {
        int offset = var.offset;
        if (offset < 0 || !var.isSimple()) {
            throw new Error("attempting to store in unassigned " + var + " simple:" + var.isSimple() + ", offset: " + offset);
        }
        Type type = var.getType().promote();
        noteVarType(offset, type);
        reserve(4);
        popType();
        int kind = adjustTypedOp(type);
        if (offset <= 3) {
            put1((kind * 4) + 59 + offset);
        } else {
            emitMaybeWide(kind + 54, offset);
        }
    }

    public void emitInc(Variable var, short inc) {
        if (var.dead()) {
            throw new Error("attempting to increment dead variable");
        }
        int offset = var.offset;
        if (offset < 0 || !var.isSimple()) {
            throw new Error("attempting to increment unassigned variable" + var.getName() + " simple:" + var.isSimple() + ", offset: " + offset);
        }
        reserve(6);
        if (var.getType().getImplementationType().promote() != Type.intType) {
            throw new Error("attempting to increment non-int variable");
        }
        boolean wide = offset > 255 || inc > 255 || inc < -256;
        if (wide) {
            put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
            put1(132);
            put2(offset);
            put2(inc);
            return;
        }
        put1(132);
        put1(offset);
        put1(inc);
    }

    private final void emitFieldop(Field field, int opcode) {
        reserve(3);
        put1(opcode);
        putIndex2(getConstants().addFieldRef(field));
    }

    public final void emitGetStatic(Field field) {
        pushType(field.type);
        emitFieldop(field, 178);
    }

    public final void emitGetField(Field field) {
        popType();
        pushType(field.type);
        emitFieldop(field, 180);
    }

    public final void emitPutStatic(Field field) {
        popType();
        emitFieldop(field, 179);
    }

    public final void emitPutField(Field field) {
        popType();
        popType();
        emitFieldop(field, 181);
    }

    private int words(Type[] types) {
        int res = 0;
        int i = types.length;
        while (true) {
            i--;
            if (i >= 0) {
                if (types[i].size > 4) {
                    res += 2;
                } else {
                    res++;
                }
            } else {
                return res;
            }
        }
    }

    public void emitInvokeMethod(Method method, int opcode) {
        Type t;
        reserve(opcode == 185 ? 5 : 3);
        int arg_count = method.arg_types.length;
        boolean is_invokestatic = opcode == 184;
        boolean is_init = opcode == 183 && "<init>".equals(method.getName());
        if (is_invokestatic != ((method.access_flags & 8) != 0)) {
            throw new Error("emitInvokeXxx static flag mis-match method.flags=" + method.access_flags);
        }
        if (!is_invokestatic && !is_init) {
            arg_count++;
        }
        put1(opcode);
        putIndex2(getConstants().addMethodRef(method));
        if (opcode == 185) {
            put1(words(method.arg_types) + 1);
            put1(0);
        }
        do {
            arg_count--;
            if (arg_count >= 0) {
                t = popType();
            } else {
                if (is_init) {
                    Type t2 = popType();
                    if (!(t2 instanceof UninitializedType)) {
                        throw new Error("calling <init> on already-initialized object");
                    }
                    ClassType ctype = ((UninitializedType) t2).ctype;
                    for (int i = 0; i < this.SP; i++) {
                        if (this.stack_types[i] == t2) {
                            this.stack_types[i] = ctype;
                        }
                    }
                    Variable[] used = this.locals.used;
                    int i2 = used == null ? 0 : used.length;
                    while (true) {
                        i2--;
                        if (i2 < 0) {
                            break;
                        }
                        Variable var = used[i2];
                        if (var != null && var.type == t2) {
                            var.type = ctype;
                        }
                    }
                    int i3 = this.local_types == null ? 0 : this.local_types.length;
                    while (true) {
                        i3--;
                        if (i3 < 0) {
                            break;
                        } else if (this.local_types[i3] == t2) {
                            this.local_types[i3] = ctype;
                        }
                    }
                }
                if (method.return_type.size != 0) {
                    pushType(method.return_type);
                    return;
                }
                return;
            }
        } while (!(t instanceof UninitializedType));
        throw new Error("passing " + t + " as parameter");
    }

    public void emitInvoke(Method method) {
        int opcode;
        if ((method.access_flags & 8) != 0) {
            opcode = 184;
        } else if (method.classfile.isInterface()) {
            opcode = 185;
        } else if ("<init>".equals(method.getName())) {
            opcode = 183;
        } else {
            opcode = 182;
        }
        emitInvokeMethod(method, opcode);
    }

    public void emitInvokeVirtual(Method method) {
        emitInvokeMethod(method, 182);
    }

    public void emitInvokeSpecial(Method method) {
        emitInvokeMethod(method, 183);
    }

    public void emitInvokeStatic(Method method) {
        emitInvokeMethod(method, 184);
    }

    public void emitInvokeInterface(Method method) {
        emitInvokeMethod(method, 185);
    }

    final void emitTransfer(Label label, int opcode) {
        label.setTypes(this);
        fixupAdd(6, label);
        put1(opcode);
        this.PC += 2;
    }

    public final void emitGoto(Label label) {
        label.setTypes(this);
        fixupAdd(4, label);
        reserve(3);
        put1(167);
        this.PC += 2;
        setUnreachable();
    }

    public final void emitJsr(Label label) {
        fixupAdd(5, label);
        reserve(3);
        put1(168);
        this.PC += 2;
    }

    public ExitableBlock startExitableBlock(Type resultType, boolean runFinallyBlocks) {
        ExitableBlock bl = new ExitableBlock(resultType, this, runFinallyBlocks);
        bl.outer = this.currentExitableBlock;
        this.currentExitableBlock = bl;
        return bl;
    }

    public void endExitableBlock() {
        ExitableBlock bl = this.currentExitableBlock;
        bl.finish();
        this.currentExitableBlock = bl.outer;
    }

    public final void emitGotoIfCompare1(Label label, int opcode) {
        popType();
        reserve(3);
        emitTransfer(label, opcode);
    }

    public final void emitGotoIfIntEqZero(Label label) {
        emitGotoIfCompare1(label, 153);
    }

    public final void emitGotoIfIntNeZero(Label label) {
        emitGotoIfCompare1(label, 154);
    }

    public final void emitGotoIfIntLtZero(Label label) {
        emitGotoIfCompare1(label, 155);
    }

    public final void emitGotoIfIntGeZero(Label label) {
        emitGotoIfCompare1(label, 156);
    }

    public final void emitGotoIfIntGtZero(Label label) {
        emitGotoIfCompare1(label, 157);
    }

    public final void emitGotoIfIntLeZero(Label label) {
        emitGotoIfCompare1(label, 158);
    }

    public final void emitGotoIfCompare2(Label label, int logop) {
        boolean cmpg = false;
        if (logop < 153 || logop > 158) {
            throw new Error("emitGotoIfCompare2: logop must be one of ifeq...ifle");
        }
        Type type2 = popType().promote();
        Type type1 = popType().promote();
        reserve(4);
        char sig1 = type1.getSignature().charAt(0);
        char sig2 = type2.getSignature().charAt(0);
        cmpg = (logop == 155 || logop == 158) ? true : true;
        if (sig1 == 'I' && sig2 == 'I') {
            logop += 6;
        } else if (sig1 == 'J' && sig2 == 'J') {
            put1(148);
        } else if (sig1 == 'F' && sig2 == 'F') {
            put1(cmpg ? 149 : 150);
        } else if (sig1 == 'D' && sig2 == 'D') {
            put1(cmpg ? 151 : 152);
        } else if ((sig1 == 'L' || sig1 == '[') && ((sig2 == 'L' || sig2 == '[') && logop <= 154)) {
            logop += 12;
        } else {
            throw new Error("invalid types to emitGotoIfCompare2");
        }
        emitTransfer(label, logop);
    }

    public final void emitGotoIfEq(Label label, boolean invert) {
        emitGotoIfCompare2(label, invert ? 154 : 153);
    }

    public final void emitGotoIfEq(Label label) {
        emitGotoIfCompare2(label, 153);
    }

    public final void emitGotoIfNE(Label label) {
        emitGotoIfCompare2(label, 154);
    }

    public final void emitGotoIfLt(Label label) {
        emitGotoIfCompare2(label, 155);
    }

    public final void emitGotoIfGe(Label label) {
        emitGotoIfCompare2(label, 156);
    }

    public final void emitGotoIfGt(Label label) {
        emitGotoIfCompare2(label, 157);
    }

    public final void emitGotoIfLe(Label label) {
        emitGotoIfCompare2(label, 158);
    }

    public final void emitIfCompare1(int opcode) {
        IfState new_if = new IfState(this);
        if (popType().promote() != Type.intType) {
            throw new Error("non-int type to emitIfCompare1");
        }
        reserve(3);
        emitTransfer(new_if.end_label, opcode);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfIntNotZero() {
        emitIfCompare1(153);
    }

    public final void emitIfIntEqZero() {
        emitIfCompare1(154);
    }

    public final void emitIfIntLEqZero() {
        emitIfCompare1(157);
    }

    public final void emitIfRefCompare1(int opcode) {
        IfState new_if = new IfState(this);
        if (!(popType() instanceof ObjectType)) {
            throw new Error("non-ref type to emitIfRefCompare1");
        }
        reserve(3);
        emitTransfer(new_if.end_label, opcode);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfNotNull() {
        emitIfRefCompare1(198);
    }

    public final void emitIfNull() {
        emitIfRefCompare1(199);
    }

    public final void emitIfIntCompare(int opcode) {
        IfState new_if = new IfState(this);
        popType();
        popType();
        reserve(3);
        emitTransfer(new_if.end_label, opcode);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfIntLt() {
        emitIfIntCompare(162);
    }

    public final void emitIfNEq() {
        IfState new_if = new IfState(this);
        emitGotoIfEq(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfEq() {
        IfState new_if = new IfState(this);
        emitGotoIfNE(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfLt() {
        IfState new_if = new IfState(this);
        emitGotoIfGe(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfGe() {
        IfState new_if = new IfState(this);
        emitGotoIfLt(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfGt() {
        IfState new_if = new IfState(this);
        emitGotoIfLe(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public final void emitIfLe() {
        IfState new_if = new IfState(this);
        emitGotoIfGt(new_if.end_label);
        new_if.start_stack_size = this.SP;
    }

    public void emitRet(Variable var) {
        int offset = var.offset;
        if (offset < 256) {
            reserve(2);
            put1(169);
            put1(offset);
            return;
        }
        reserve(4);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION);
        put1(169);
        put2(offset);
    }

    public final void emitThen() {
        this.if_stack.start_stack_size = this.SP;
    }

    public final void emitIfThen() {
        new IfState(this, null);
    }

    public final void emitElse() {
        Label else_label = this.if_stack.end_label;
        if (reachableHere()) {
            Label end_label = new Label(this);
            this.if_stack.end_label = end_label;
            int growth = this.SP - this.if_stack.start_stack_size;
            this.if_stack.stack_growth = growth;
            if (growth > 0) {
                this.if_stack.then_stacked_types = new Type[growth];
                System.arraycopy(this.stack_types, this.if_stack.start_stack_size, this.if_stack.then_stacked_types, 0, growth);
            } else {
                this.if_stack.then_stacked_types = new Type[0];
            }
            emitGoto(end_label);
        } else {
            this.if_stack.end_label = null;
        }
        while (this.SP > this.if_stack.start_stack_size) {
            popType();
        }
        this.SP = this.if_stack.start_stack_size;
        if (else_label != null) {
            else_label.define(this);
        }
        this.if_stack.doing_else = true;
    }

    public final void emitFi() {
        boolean make_unreachable = false;
        if (!this.if_stack.doing_else) {
            if (reachableHere() && this.SP != this.if_stack.start_stack_size) {
                throw new Error("at PC " + this.PC + " then clause grows stack with no else clause");
            }
        } else if (this.if_stack.then_stacked_types != null) {
            int then_clause_stack_size = this.if_stack.start_stack_size + this.if_stack.stack_growth;
            if (!reachableHere()) {
                if (this.if_stack.stack_growth > 0) {
                    System.arraycopy(this.if_stack.then_stacked_types, 0, this.stack_types, this.if_stack.start_stack_size, this.if_stack.stack_growth);
                }
                this.SP = then_clause_stack_size;
            } else if (this.SP != then_clause_stack_size) {
                throw new Error("at PC " + this.PC + ": SP at end of 'then' was " + then_clause_stack_size + " while SP at end of 'else' was " + this.SP);
            }
        } else if (this.unreachable_here) {
            make_unreachable = true;
        }
        if (this.if_stack.end_label != null) {
            this.if_stack.end_label.define(this);
        }
        if (make_unreachable) {
            setUnreachable();
        }
        this.if_stack = this.if_stack.previous;
    }

    public final void emitConvert(Type from, Type to) {
        String to_sig = to.getSignature();
        String from_sig = from.getSignature();
        int op = -1;
        if (to_sig.length() == 1 || from_sig.length() == 1) {
            char to_sig0 = to_sig.charAt(0);
            char from_sig0 = from_sig.charAt(0);
            if (from_sig0 != to_sig0) {
                if (from.size < 4) {
                    from_sig0 = Access.INNERCLASS_CONTEXT;
                }
                if (to.size < 4) {
                    emitConvert(from, Type.intType);
                    from_sig0 = Access.INNERCLASS_CONTEXT;
                }
                if (from_sig0 != to_sig0) {
                    switch (from_sig0) {
                        case 'D':
                            switch (to_sig0) {
                                case 'F':
                                    op = 144;
                                    break;
                                case 'I':
                                    op = 142;
                                    break;
                                case 'J':
                                    op = 143;
                                    break;
                            }
                        case 'F':
                            switch (to_sig0) {
                                case 'D':
                                    op = 141;
                                    break;
                                case 'I':
                                    op = 139;
                                    break;
                                case 'J':
                                    op = 140;
                                    break;
                            }
                        case 'I':
                            switch (to_sig0) {
                                case 'B':
                                    op = 145;
                                    break;
                                case 'C':
                                    op = 146;
                                    break;
                                case 'D':
                                    op = 135;
                                    break;
                                case 'F':
                                    op = 134;
                                    break;
                                case 'J':
                                    op = 133;
                                    break;
                                case 'S':
                                    op = 147;
                                    break;
                            }
                        case 'J':
                            switch (to_sig0) {
                                case 'D':
                                    op = 138;
                                    break;
                                case 'F':
                                    op = 137;
                                    break;
                                case 'I':
                                    op = 136;
                                    break;
                            }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        if (op < 0) {
            throw new Error("unsupported CodeAttr.emitConvert");
        }
        reserve(1);
        popType();
        put1(op);
        pushType(to);
    }

    private void emitCheckcast(Type type, int opcode) {
        reserve(3);
        popType();
        put1(opcode);
        if (type instanceof ObjectType) {
            putIndex2(getConstants().addClass((ObjectType) type));
            return;
        }
        throw new Error("unimplemented type " + type + " in emitCheckcast/emitInstanceof");
    }

    public static boolean castNeeded(Type top, Type required) {
        if (top instanceof UninitializedType) {
            top = ((UninitializedType) top).getImplementationType();
        }
        while (top != required) {
            if ((required instanceof ClassType) && (top instanceof ClassType) && ((ClassType) top).isSubclass((ClassType) required)) {
                return false;
            }
            if ((required instanceof ArrayType) && (top instanceof ArrayType)) {
                required = ((ArrayType) required).getComponentType();
                top = ((ArrayType) top).getComponentType();
            } else {
                return true;
            }
        }
        return false;
    }

    public void emitCheckcast(Type type) {
        if (castNeeded(topType(), type)) {
            emitCheckcast(type, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE);
            pushType(type);
        }
    }

    public void emitInstanceof(Type type) {
        emitCheckcast(type, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_STOP);
        pushType(Type.booleanType);
    }

    public final void emitThrow() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PLAY);
        setUnreachable();
    }

    public final void emitMonitorEnter() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SOURCE);
    }

    public final void emitMonitorExit() {
        popType();
        reserve(1);
        put1(FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_FULLSCREEN);
    }

    public final void emitReturn() {
        if (this.try_stack != null) {
            new Error();
        }
        emitRawReturn();
    }

    final void emitRawReturn() {
        if (getMethod().getReturnType().size == 0) {
            reserve(1);
            put1(177);
        } else {
            emitTypedOp(172, popType().promote());
        }
        setUnreachable();
    }

    public void addHandler(int start_pc, int end_pc, int handler_pc, int catch_type) {
        int index = this.exception_table_length * 4;
        if (this.exception_table == null) {
            this.exception_table = new short[20];
        } else if (this.exception_table.length <= index) {
            short[] new_table = new short[this.exception_table.length * 2];
            System.arraycopy(this.exception_table, 0, new_table, 0, index);
            this.exception_table = new_table;
        }
        int index2 = index + 1;
        this.exception_table[index] = (short) start_pc;
        int index3 = index2 + 1;
        this.exception_table[index2] = (short) end_pc;
        int index4 = index3 + 1;
        this.exception_table[index3] = (short) handler_pc;
        int i = index4 + 1;
        this.exception_table[index4] = (short) catch_type;
        this.exception_table_length++;
    }

    public void addHandler(Label start_try, Label end_try, ClassType catch_type) {
        int catch_type_index;
        ConstantPool constants = getConstants();
        if (catch_type == null) {
            catch_type_index = 0;
        } else {
            catch_type_index = constants.addClass(catch_type).index;
        }
        fixupAdd(11, start_try);
        fixupAdd(12, catch_type_index, end_try);
        Label handler = new Label();
        handler.localTypes = start_try.localTypes;
        handler.stackTypes = new Type[1];
        Type handler_class = catch_type == null ? Type.javalangThrowableType : catch_type;
        handler.stackTypes[0] = handler_class;
        setTypes(handler);
        fixupAdd(13, 0, handler);
    }

    public void emitWithCleanupStart() {
        int savedSP = this.SP;
        this.SP = 0;
        emitTryStart(false, null);
        this.SP = savedSP;
    }

    public void emitWithCleanupCatch(Variable catchVar) {
        Type[] savedTypes;
        emitTryEnd();
        if (this.SP > 0) {
            savedTypes = new Type[this.SP];
            System.arraycopy(this.stack_types, 0, savedTypes, 0, this.SP);
            this.SP = 0;
        } else {
            savedTypes = null;
        }
        this.try_stack.savedTypes = savedTypes;
        this.try_stack.saved_result = catchVar;
        int i = this.SP;
        emitCatchStart(catchVar);
    }

    public void emitWithCleanupDone() {
        Variable catchVar = this.try_stack.saved_result;
        this.try_stack.saved_result = null;
        if (catchVar != null) {
            emitLoad(catchVar);
        }
        emitThrow();
        emitCatchEnd();
        Type[] savedTypes = this.try_stack.savedTypes;
        emitTryCatchEnd();
        if (savedTypes != null) {
            this.SP = savedTypes.length;
            if (this.SP >= this.stack_types.length) {
                this.stack_types = savedTypes;
                return;
            } else {
                System.arraycopy(savedTypes, 0, this.stack_types, 0, this.SP);
                return;
            }
        }
        this.SP = 0;
    }

    public void emitTryStart(boolean has_finally, Type result_type) {
        Type[] startLocals;
        if (result_type != null && result_type.isVoid()) {
            result_type = null;
        }
        Variable[] savedStack = null;
        if (result_type != null || this.SP > 0) {
            pushScope();
        }
        if (this.SP > 0) {
            savedStack = new Variable[this.SP];
            int i = 0;
            while (this.SP > 0) {
                Variable var = addLocal(topType());
                emitStore(var);
                savedStack[i] = var;
                i++;
            }
        }
        TryState try_state = new TryState(this);
        try_state.savedStack = savedStack;
        int usedLocals = this.local_types == null ? 0 : this.local_types.length;
        while (usedLocals > 0) {
            Type last = this.local_types[usedLocals - 1];
            if (last != null) {
                break;
            }
            usedLocals--;
        }
        if (usedLocals == 0) {
            startLocals = Type.typeArray0;
        } else {
            startLocals = new Type[usedLocals];
            System.arraycopy(this.local_types, 0, startLocals, 0, usedLocals);
        }
        try_state.start_try.localTypes = startLocals;
        if (result_type != null) {
            try_state.saved_result = addLocal(result_type);
        }
        if (has_finally) {
            try_state.finally_subr = new Label();
        }
    }

    public void emitTryEnd() {
        emitTryEnd(false);
    }

    private void emitTryEnd(boolean fromFinally) {
        if (!this.try_stack.tryClauseDone) {
            this.try_stack.tryClauseDone = true;
            if (this.try_stack.finally_subr != null) {
                this.try_stack.exception = addLocal(Type.javalangThrowableType);
            }
            gotoFinallyOrEnd(fromFinally);
            this.try_stack.end_try = getLabel();
        }
    }

    public void emitCatchStart(Variable var) {
        emitTryEnd(false);
        setTypes(this.try_stack.start_try.localTypes, Type.typeArray0);
        if (this.try_stack.try_type != null) {
            emitCatchEnd();
        }
        ClassType type = var == null ? null : (ClassType) var.getType();
        this.try_stack.try_type = type;
        addHandler(this.try_stack.start_try, this.try_stack.end_try, type);
        if (var != null) {
            emitStore(var);
        }
    }

    public void emitCatchEnd() {
        gotoFinallyOrEnd(false);
        this.try_stack.try_type = null;
    }

    private void gotoFinallyOrEnd(boolean fromFinally) {
        if (reachableHere()) {
            if (this.try_stack.saved_result != null) {
                emitStore(this.try_stack.saved_result);
            }
            if (this.try_stack.end_label == null) {
                this.try_stack.end_label = new Label();
            }
            if (this.try_stack.finally_subr == null || useJsr()) {
                if (this.try_stack.finally_subr != null) {
                    emitJsr(this.try_stack.finally_subr);
                }
                emitGoto(this.try_stack.end_label);
                return;
            }
            if (this.try_stack.exitCases != null) {
                emitPushInt(0);
            }
            emitPushNull();
            if (!fromFinally) {
                emitGoto(this.try_stack.finally_subr);
            }
        }
    }

    public void emitFinallyStart() {
        emitTryEnd(true);
        if (this.try_stack.try_type != null) {
            emitCatchEnd();
        }
        this.try_stack.end_try = getLabel();
        pushScope();
        if (useJsr()) {
            this.SP = 0;
            emitCatchStart(null);
            emitStore(this.try_stack.exception);
            emitJsr(this.try_stack.finally_subr);
            emitLoad(this.try_stack.exception);
            emitThrow();
        } else {
            setUnreachable();
            Label endLabel = new Label(this);
            int fragment_cookie = beginFragment(endLabel);
            addHandler(this.try_stack.start_try, this.try_stack.end_try, Type.javalangThrowableType);
            if (this.try_stack.saved_result != null) {
                emitStoreDefaultValue(this.try_stack.saved_result);
            }
            if (this.try_stack.exitCases != null) {
                emitPushInt(-1);
                emitSwap();
            }
            emitGoto(this.try_stack.finally_subr);
            endFragment(fragment_cookie);
        }
        this.try_stack.finally_subr.define(this);
        if (useJsr()) {
            Type ret_addr_type = Type.objectType;
            this.try_stack.finally_ret_addr = addLocal(ret_addr_type);
            pushType(ret_addr_type);
            emitStore(this.try_stack.finally_ret_addr);
        }
    }

    public void emitFinallyEnd() {
        if (useJsr()) {
            emitRet(this.try_stack.finally_ret_addr);
        } else if (this.try_stack.end_label == null && this.try_stack.exitCases == null) {
            emitThrow();
        } else {
            emitStore(this.try_stack.exception);
            emitLoad(this.try_stack.exception);
            emitIfNotNull();
            emitLoad(this.try_stack.exception);
            emitThrow();
            emitElse();
            ExitableBlock exit = this.try_stack.exitCases;
            if (exit != null) {
                SwitchState sw = startSwitch();
                while (exit != null) {
                    ExitableBlock next = exit.nextCase;
                    exit.nextCase = null;
                    exit.currentTryState = null;
                    TryState nextTry = TryState.outerHandler(this.try_stack.previous, exit.initialTryState);
                    if (nextTry == exit.initialTryState) {
                        sw.addCaseGoto(exit.switchCase, this, exit.endLabel);
                    } else {
                        sw.addCase(exit.switchCase, this);
                        exit.exit(nextTry);
                    }
                    exit = next;
                }
                this.try_stack.exitCases = null;
                sw.addDefault(this);
                sw.finish(this);
            }
            emitFi();
            setUnreachable();
        }
        popScope();
        this.try_stack.finally_subr = null;
    }

    public void emitTryCatchEnd() {
        if (this.try_stack.finally_subr != null) {
            emitFinallyEnd();
        }
        Variable[] vars = this.try_stack.savedStack;
        if (this.try_stack.end_label == null) {
            setUnreachable();
        } else {
            setTypes(this.try_stack.start_try.localTypes, Type.typeArray0);
            this.try_stack.end_label.define(this);
            if (vars != null) {
                int i = vars.length;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    Variable v = vars[i];
                    if (v != null) {
                        emitLoad(v);
                    }
                }
            }
            if (this.try_stack.saved_result != null) {
                emitLoad(this.try_stack.saved_result);
            }
        }
        if (this.try_stack.saved_result != null || vars != null) {
            popScope();
        }
        this.try_stack = this.try_stack.previous;
    }

    public final TryState getCurrentTry() {
        return this.try_stack;
    }

    public final boolean isInTry() {
        return this.try_stack != null;
    }

    public SwitchState startSwitch() {
        SwitchState sw = new SwitchState(this);
        sw.switchValuePushed(this);
        return sw;
    }

    public void emitTailCall(boolean pop_args, Scope scope) {
        if (pop_args) {
            Method meth = getMethod();
            int arg_slots = (meth.access_flags & 8) != 0 ? 0 : 1;
            int i = meth.arg_types.length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                arg_slots += meth.arg_types[i].size > 4 ? 2 : 1;
            }
            int i2 = meth.arg_types.length;
            while (true) {
                i2--;
                if (i2 < 0) {
                    break;
                }
                arg_slots -= meth.arg_types[i2].size > 4 ? 2 : 1;
                emitStore(this.locals.used[arg_slots]);
            }
        }
        emitGoto(scope.start);
    }

    /* JADX WARN: Removed duplicated region for block: B:150:0x00e4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0265 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0245 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0142  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void processFixups() {
        /*
            Method dump skipped, instructions count: 1426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.CodeAttr.processFixups():void");
    }

    private Label mergeLabels(Label oldLabel, Label newLabel) {
        if (oldLabel != null) {
            newLabel.setTypes(oldLabel);
        }
        return newLabel;
    }

    @Override // gnu.bytecode.Attribute
    public void assignConstants(ClassType cl) {
        if (this.locals != null && this.locals.container == null && !this.locals.isEmpty()) {
            this.locals.addToFrontOf(this);
        }
        processFixups();
        if (this.stackMap != null && this.stackMap.numEntries > 0) {
            this.stackMap.addToFrontOf(this);
        }
        if (instructionLineMode) {
            if (this.lines == null) {
                this.lines = new LineNumbersAttr(this);
            }
            this.lines.linenumber_count = 0;
            int codeLen = getCodeLength();
            for (int i = 0; i < codeLen; i++) {
                this.lines.put(i, i);
            }
        }
        super.assignConstants(cl);
        Attribute.assignConstants(this, cl);
    }

    @Override // gnu.bytecode.Attribute
    public final int getLength() {
        return getCodeLength() + 12 + (this.exception_table_length * 8) + Attribute.getLengthAll(this);
    }

    @Override // gnu.bytecode.Attribute
    public void write(DataOutputStream dstr) throws IOException {
        dstr.writeShort(this.max_stack);
        dstr.writeShort(this.max_locals);
        dstr.writeInt(this.PC);
        dstr.write(this.code, 0, this.PC);
        dstr.writeShort(this.exception_table_length);
        int count = this.exception_table_length;
        int i = 0;
        while (true) {
            count--;
            if (count >= 0) {
                dstr.writeShort(this.exception_table[i]);
                dstr.writeShort(this.exception_table[i + 1]);
                dstr.writeShort(this.exception_table[i + 2]);
                dstr.writeShort(this.exception_table[i + 3]);
                i += 4;
            } else {
                Attribute.writeAll(this, dstr);
                return;
            }
        }
    }

    @Override // gnu.bytecode.Attribute
    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", max_stack:");
        dst.print(this.max_stack);
        dst.print(", max_locals:");
        dst.print(this.max_locals);
        dst.print(", code_length:");
        int length = getCodeLength();
        dst.println(length);
        disAssemble(dst, 0, length);
        if (this.exception_table_length > 0) {
            dst.print("Exceptions (count: ");
            dst.print(this.exception_table_length);
            dst.println("):");
            int count = this.exception_table_length;
            int i = 0;
            while (true) {
                count--;
                if (count < 0) {
                    break;
                }
                dst.print("  start: ");
                dst.print(this.exception_table[i] & 65535);
                dst.print(", end: ");
                dst.print(this.exception_table[i + 1] & 65535);
                dst.print(", handler: ");
                dst.print(this.exception_table[i + 2] & 65535);
                dst.print(", type: ");
                int catch_type_index = this.exception_table[i + 3] & 65535;
                if (catch_type_index == 0) {
                    dst.print("0 /* finally */");
                } else {
                    dst.printOptionalIndex(catch_type_index);
                    dst.printConstantTersely(catch_type_index, 7);
                }
                dst.println();
                i += 4;
            }
        }
        dst.printAttributes(this);
    }

    public void disAssemble(ClassTypeWriter dst, int start, int limit) {
        int i;
        int i2;
        int index;
        int var_index;
        int constant;
        int i3;
        int index2;
        String load_or_store;
        int index3;
        int op;
        int index4;
        int constant2;
        int i4;
        char typ;
        int op2;
        boolean wide = false;
        int i5 = start;
        while (true) {
            int i6 = i5;
            if (i6 < limit) {
                int i7 = i6 + 1;
                int op3 = this.code[i6] & Ev3Constants.Opcode.TST;
                String str = Integer.toString(i6);
                int printConstant = 0;
                int j = str.length();
                while (true) {
                    j++;
                    if (j > 3) {
                        break;
                    }
                    dst.print(' ');
                }
                dst.print(str);
                dst.print(": ");
                if (op3 < 120) {
                    if (op3 < 87) {
                        if (op3 < 3) {
                            print("nop;aconst_null;iconst_m1;", op3, dst);
                            i = i7;
                        } else if (op3 < 9) {
                            dst.print("iconst_");
                            dst.print(op3 - 3);
                            i = i7;
                        } else if (op3 < 16) {
                            if (op3 < 11) {
                                typ = 'l';
                                op2 = op3 - 9;
                            } else if (op3 < 14) {
                                typ = 'f';
                                op2 = op3 - 11;
                            } else {
                                typ = 'd';
                                op2 = op3 - 14;
                            }
                            dst.print(typ);
                            dst.print("const_");
                            dst.print(op2);
                            i = i7;
                        } else if (op3 < 21) {
                            if (op3 < 18) {
                                print("bipush ;sipush ;", op3 - 16, dst);
                                if (op3 == 16) {
                                    constant2 = this.code[i7];
                                    i4 = i7 + 1;
                                } else {
                                    constant2 = (short) readUnsignedShort(i7);
                                    i4 = i7 + 2;
                                }
                                dst.print(constant2);
                                i = i4;
                            } else {
                                printConstant = op3 == 18 ? 1 : 2;
                                print("ldc;ldc_w;ldc2_w;", op3 - 18, dst);
                                i = i7;
                            }
                        } else {
                            if (op3 < 54) {
                                load_or_store = "load";
                            } else {
                                load_or_store = "store";
                                op3 -= 33;
                            }
                            if (op3 < 26) {
                                index3 = -1;
                                op = op3 - 21;
                            } else if (op3 < 46) {
                                int op4 = op3 - 26;
                                index3 = op4 % 4;
                                op = op4 >> 2;
                            } else {
                                index3 = -2;
                                op = op3 - 46;
                            }
                            dst.print("ilfdabcs".charAt(op));
                            if (index3 == -2) {
                                dst.write(97);
                            }
                            dst.print(load_or_store);
                            if (index3 >= 0) {
                                dst.write(95);
                                dst.print(index3);
                            } else if (index3 == -1) {
                                if (wide) {
                                    index4 = readUnsignedShort(i7);
                                    i7 += 2;
                                } else {
                                    index4 = this.code[i7] & Ev3Constants.Opcode.TST;
                                    i7++;
                                }
                                wide = false;
                                dst.print(' ');
                                dst.print(index4);
                            }
                            i = i7;
                        }
                    } else if (op3 < 96) {
                        print("pop;pop2;dup;dup_x1;dup_x2;dup2;dup2_x1;dup2_x2;swap;", op3 - 87, dst);
                        i = i7;
                    } else {
                        dst.print("ilfda".charAt((op3 - 96) % 4));
                        print("add;sub;mul;div;rem;neg;", (op3 - 96) >> 2, dst);
                        i = i7;
                    }
                } else if (op3 < 170) {
                    if (op3 < 132) {
                        dst.print((op3 & 1) == 0 ? 'i' : 'l');
                        print("shl;shr;ushr;and;or;xor;", (op3 - 120) >> 1, dst);
                        i = i7;
                    } else if (op3 == 132) {
                        dst.print("iinc");
                        if (!wide) {
                            int i8 = i7 + 1;
                            var_index = this.code[i7] & Ev3Constants.Opcode.TST;
                            i3 = i8 + 1;
                            constant = this.code[i8];
                        } else {
                            var_index = readUnsignedShort(i7);
                            int i9 = i7 + 2;
                            constant = (short) readUnsignedShort(i9);
                            i3 = i9 + 2;
                            wide = false;
                        }
                        dst.print(' ');
                        dst.print(var_index);
                        dst.print(' ');
                        dst.print(constant);
                        i = i3;
                    } else if (op3 < 148) {
                        dst.print("ilfdi".charAt((op3 - 133) / 3));
                        dst.print('2');
                        dst.print("lfdifdildilfbcs".charAt(op3 - 133));
                        i = i7;
                    } else if (op3 < 153) {
                        print("lcmp;fcmpl;fcmpg;dcmpl;dcmpg;", op3 - 148, dst);
                        i = i7;
                    } else if (op3 < 169) {
                        if (op3 < 159) {
                            dst.print("if");
                            print("eq;ne;lt;ge;gt;le;", op3 - 153, dst);
                        } else if (op3 < 167) {
                            if (op3 < 165) {
                                dst.print("if_icmp");
                            } else {
                                dst.print("if_acmp");
                                op3 -= 6;
                            }
                            print("eq;ne;lt;ge;gt;le;", op3 - 159, dst);
                        } else {
                            print("goto;jsr;", op3 - 167, dst);
                        }
                        int delta = (short) readUnsignedShort(i7);
                        dst.print(' ');
                        dst.print(i6 + delta);
                        i = i7 + 2;
                    } else {
                        dst.print("ret ");
                        if (wide) {
                            int index5 = readUnsignedShort(i7);
                            index = index5 + 2;
                        } else {
                            index = this.code[i7] & Ev3Constants.Opcode.TST;
                            i7++;
                        }
                        wide = false;
                        dst.print(index);
                        i = i7;
                    }
                } else if (op3 < 172) {
                    if (this.fixup_count <= 0) {
                        i7 = (i7 + 3) & (-4);
                    }
                    int code_offset = readInt(i7);
                    int i10 = i7 + 4;
                    if (op3 == 170) {
                        dst.print("tableswitch");
                        int low = readInt(i10);
                        int i11 = i10 + 4;
                        int high = readInt(i11);
                        i2 = i11 + 4;
                        dst.print(" low: ");
                        dst.print(low);
                        dst.print(" high: ");
                        dst.print(high);
                        dst.print(" default: ");
                        dst.print(i6 + code_offset);
                        while (low <= high) {
                            int code_offset2 = readInt(i2);
                            i2 += 4;
                            dst.println();
                            dst.print("  ");
                            dst.print(low);
                            dst.print(": ");
                            dst.print(i6 + code_offset2);
                            low++;
                        }
                    } else {
                        dst.print("lookupswitch");
                        int npairs = readInt(i10);
                        i2 = i10 + 4;
                        dst.print(" npairs: ");
                        dst.print(npairs);
                        dst.print(" default: ");
                        dst.print(i6 + code_offset);
                        while (true) {
                            npairs--;
                            if (npairs < 0) {
                                break;
                            }
                            int match = readInt(i2);
                            int i12 = i2 + 4;
                            int code_offset3 = readInt(i12);
                            i2 = i12 + 4;
                            dst.println();
                            dst.print("  ");
                            dst.print(match);
                            dst.print(": ");
                            dst.print(i6 + code_offset3);
                        }
                    }
                    i = i2;
                } else if (op3 < 178) {
                    if (op3 < 177) {
                        dst.print("ilfda".charAt(op3 - 172));
                    }
                    dst.print("return");
                    i = i7;
                } else if (op3 < 182) {
                    print("getstatic;putstatic;getfield;putfield;", op3 - 178, dst);
                    printConstant = 2;
                    i = i7;
                } else if (op3 < 185) {
                    dst.print("invoke");
                    print("virtual;special;static;", op3 - 182, dst);
                    printConstant = 2;
                    i = i7;
                } else if (op3 == 185) {
                    dst.print("invokeinterface (");
                    int index6 = readUnsignedShort(i7);
                    int i13 = i7 + 2;
                    int args = this.code[i13] & Ev3Constants.Opcode.TST;
                    dst.print(args + " args)");
                    dst.printConstantOperand(index6);
                    i = i13 + 2;
                } else if (op3 < 196) {
                    print("186;new;newarray;anewarray;arraylength;athrow;checkcast;instanceof;monitorenter;monitorexit;", op3 - 186, dst);
                    if (op3 == 187 || op3 == 189 || op3 == 192 || op3 == 193) {
                        printConstant = 2;
                        i = i7;
                    } else {
                        if (op3 == 188) {
                            i = i7 + 1;
                            int type = this.code[i7];
                            dst.print(' ');
                            if (type >= 4 && type <= 11) {
                                print("boolean;char;float;double;byte;short;int;long;", type - 4, dst);
                            } else {
                                dst.print(type);
                            }
                        }
                        i = i7;
                    }
                } else if (op3 == 196) {
                    dst.print("wide");
                    wide = true;
                    i = i7;
                } else if (op3 == 197) {
                    dst.print("multianewarray");
                    int index7 = readUnsignedShort(i7);
                    int i14 = i7 + 2;
                    dst.printConstantOperand(index7);
                    i = i14 + 1;
                    int dims = this.code[i14] & Ev3Constants.Opcode.TST;
                    dst.print(' ');
                    dst.print(dims);
                } else if (op3 < 200) {
                    print("ifnull;ifnonnull;", op3 - 198, dst);
                    int delta2 = (short) readUnsignedShort(i7);
                    dst.print(' ');
                    dst.print(i6 + delta2);
                    i = i7 + 2;
                } else if (op3 < 202) {
                    print("goto_w;jsr_w;", op3 - 200, dst);
                    int delta3 = readInt(i7);
                    dst.print(' ');
                    dst.print(i6 + delta3);
                    i = i7 + 4;
                } else {
                    dst.print(op3);
                    i = i7;
                }
                if (printConstant > 0) {
                    if (printConstant == 1) {
                        i5 = i + 1;
                        index2 = this.code[i] & Ev3Constants.Opcode.TST;
                    } else {
                        index2 = readUnsignedShort(i);
                        i5 = i + 2;
                    }
                    dst.printConstantOperand(index2);
                } else {
                    i5 = i;
                }
                dst.println();
            } else {
                return;
            }
        }
    }

    private int readUnsignedShort(int offset) {
        return ((this.code[offset] & Ev3Constants.Opcode.TST) << 8) | (this.code[offset + 1] & Ev3Constants.Opcode.TST);
    }

    private int readInt(int offset) {
        return (readUnsignedShort(offset) << 16) | readUnsignedShort(offset + 2);
    }

    private void print(String str, int i, PrintWriter dst) {
        int last = 0;
        int pos = -1;
        while (i >= 0) {
            last = pos + 1;
            pos = str.indexOf(59, last);
            i--;
        }
        dst.write(str, last, pos - last);
    }

    public int beginFragment(Label after) {
        return beginFragment(new Label(), after);
    }

    public int beginFragment(Label start, Label after) {
        int i = this.fixup_count;
        fixupAdd(10, after);
        start.define(this);
        return i;
    }

    public void endFragment(int cookie) {
        this.fixup_offsets[cookie] = (this.fixup_count << 4) | 10;
        Label after = this.fixup_labels[cookie];
        fixupAdd(9, 0, null);
        after.define(this);
    }
}
