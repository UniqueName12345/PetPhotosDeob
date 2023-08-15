package gnu.bytecode;

/* loaded from: classes.dex */
public class PrimType extends Type {
    private static final String numberHierarchy = "A:java.lang.Byte;B:java.lang.Short;C:java.lang.Integer;D:java.lang.Long;E:gnu.math.IntNum;E:java.gnu.math.BitInteger;G:gnu.math.RatNum;H:java.lang.Float;I:java.lang.Double;I:gnu.math.DFloNum;J:gnu.math.RealNum;K:gnu.math.Complex;L:gnu.math.Quantity;K:gnu.math.Numeric;N:java.lang.Number;";

    public PrimType(String nam, String sig, int siz, Class reflectClass) {
        super(nam, sig);
        this.size = siz;
        this.reflectClass = reflectClass;
        Type.registerTypeForClass(reflectClass, this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public PrimType(PrimType type) {
        super(type.this_name, type.signature);
        this.size = type.size;
        this.reflectClass = type.reflectClass;
    }

    @Override // gnu.bytecode.Type
    public Object coerceFromObject(Object obj) {
        if (obj.getClass() != this.reflectClass) {
            char sig1 = (this.signature == null || this.signature.length() != 1) ? ' ' : this.signature.charAt(0);
            switch (sig1) {
                case 'B':
                    return Byte.valueOf(((Number) obj).byteValue());
                case 'D':
                    return Double.valueOf(((Number) obj).doubleValue());
                case 'F':
                    return Float.valueOf(((Number) obj).floatValue());
                case 'I':
                    return Integer.valueOf(((Number) obj).intValue());
                case 'J':
                    return Long.valueOf(((Number) obj).longValue());
                case 'S':
                    return Short.valueOf(((Number) obj).shortValue());
                case 'Z':
                    return Boolean.valueOf(((Boolean) obj).booleanValue());
                default:
                    throw new ClassCastException("don't know how to coerce " + obj.getClass().getName() + " to " + getName());
            }
        }
        return obj;
    }

    public char charValue(Object value) {
        return ((Character) value).charValue();
    }

    public static boolean booleanValue(Object value) {
        return !(value instanceof Boolean) || ((Boolean) value).booleanValue();
    }

    public ClassType boxedType() {
        String cname;
        char sig1 = getSignature().charAt(0);
        switch (sig1) {
            case 'B':
                cname = "java.lang.Byte";
                break;
            case 'C':
                cname = "java.lang.Character";
                break;
            case 'D':
                cname = "java.lang.Double";
                break;
            case 'F':
                cname = "java.lang.Float";
                break;
            case 'I':
                cname = "java.lang.Integer";
                break;
            case 'J':
                cname = "java.lang.Long";
                break;
            case 'S':
                cname = "java.lang.Short";
                break;
            case 'Z':
                cname = "java.lang.Boolean";
                break;
            default:
                cname = null;
                break;
        }
        return ClassType.make(cname);
    }

    @Override // gnu.bytecode.Type
    public void emitCoerceToObject(CodeAttr code) {
        Method method;
        char sig1 = getSignature().charAt(0);
        ClassType clas = boxedType();
        if (sig1 == 'Z') {
            code.emitIfIntNotZero();
            code.emitGetStatic(clas.getDeclaredField("TRUE"));
            code.emitElse();
            code.emitGetStatic(clas.getDeclaredField("FALSE"));
            code.emitFi();
            return;
        }
        Type[] args = {this};
        if (code.getMethod().getDeclaringClass().classfileFormatVersion >= 3211264) {
            method = clas.getDeclaredMethod("valueOf", args);
        } else {
            method = clas.getDeclaredMethod("<init>", args);
            code.emitNew(clas);
            code.emitDupX();
            code.emitSwap();
        }
        code.emitInvoke(method);
    }

    @Override // gnu.bytecode.Type
    public void emitIsInstance(CodeAttr code) {
        char sig1 = (this.signature == null || this.signature.length() != 1) ? ' ' : this.signature.charAt(0);
        if (sig1 == 'Z') {
            javalangBooleanType.emitIsInstance(code);
        } else if (sig1 == 'V') {
            code.emitPop(1);
            code.emitPushInt(1);
        } else {
            javalangNumberType.emitIsInstance(code);
        }
    }

    @Override // gnu.bytecode.Type
    public void emitCoerceFromObject(CodeAttr code) {
        char sig1 = (this.signature == null || this.signature.length() != 1) ? ' ' : this.signature.charAt(0);
        if (sig1 == 'Z') {
            code.emitCheckcast(javalangBooleanType);
            code.emitInvokeVirtual(booleanValue_method);
        } else if (sig1 == 'V') {
            code.emitPop(1);
        } else {
            code.emitCheckcast(javalangNumberType);
            if (sig1 == 'I' || sig1 == 'S' || sig1 == 'B') {
                code.emitInvokeVirtual(intValue_method);
            } else if (sig1 == 'J') {
                code.emitInvokeVirtual(longValue_method);
            } else if (sig1 == 'D') {
                code.emitInvokeVirtual(doubleValue_method);
            } else if (sig1 == 'F') {
                code.emitInvokeVirtual(floatValue_method);
            } else {
                super.emitCoerceFromObject(code);
            }
        }
    }

    public static int compare(PrimType type1, PrimType type2) {
        char sig1 = type1.signature.charAt(0);
        char sig2 = type2.signature.charAt(0);
        if (sig1 == sig2) {
            return 0;
        }
        if (sig1 == 'V') {
            return 1;
        }
        if (sig2 != 'V') {
            if (sig1 == 'Z' || sig2 == 'Z') {
                return -3;
            }
            if (sig1 == 'C') {
                return type2.size <= 2 ? -3 : -1;
            } else if (sig2 == 'C') {
                return type1.size > 2 ? 1 : -3;
            } else if (sig1 == 'D') {
                return 1;
            } else {
                if (sig2 != 'D') {
                    if (sig1 == 'F') {
                        return 1;
                    }
                    if (sig2 != 'F') {
                        if (sig1 == 'J') {
                            return 1;
                        }
                        if (sig2 != 'J') {
                            if (sig1 == 'I') {
                                return 1;
                            }
                            if (sig2 != 'I') {
                                if (sig1 == 'S') {
                                    return 1;
                                }
                                return sig2 != 'S' ? -3 : -1;
                            }
                            return -1;
                        }
                        return -1;
                    }
                    return -1;
                }
                return -1;
            }
        }
        return -1;
    }

    public Type promotedType() {
        switch (this.signature.charAt(0)) {
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z':
                return Type.intType;
            default:
                return getImplementationType();
        }
    }

    private static char findInHierarchy(String cname) {
        int pos = numberHierarchy.indexOf(cname) - 2;
        if (pos < 0) {
            return (char) 0;
        }
        return numberHierarchy.charAt(pos);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
    @Override // gnu.bytecode.Type
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int compare(gnu.bytecode.Type r10) {
        /*
            r9 = this;
            r5 = 1
            r7 = -3
            r6 = -1
            r4 = 0
            boolean r8 = r10 instanceof gnu.bytecode.PrimType
            if (r8 == 0) goto L1e
            gnu.bytecode.Type r4 = r10.getImplementationType()
            if (r4 == r10) goto L17
            int r4 = r10.compare(r9)
            int r4 = swappedCompareResult(r4)
        L16:
            return r4
        L17:
            gnu.bytecode.PrimType r10 = (gnu.bytecode.PrimType) r10
            int r4 = compare(r9, r10)
            goto L16
        L1e:
            boolean r8 = r10 instanceof gnu.bytecode.ClassType
            if (r8 != 0) goto L31
            boolean r4 = r10 instanceof gnu.bytecode.ArrayType
            if (r4 == 0) goto L28
            r4 = r7
            goto L16
        L28:
            int r4 = r10.compare(r9)
            int r4 = swappedCompareResult(r4)
            goto L16
        L31:
            java.lang.String r8 = r9.signature
            char r2 = r8.charAt(r4)
            java.lang.String r0 = r10.getName()
            if (r0 != 0) goto L3f
            r4 = r6
            goto L16
        L3f:
            r3 = 0
            switch(r2) {
                case 66: goto L64;
                case 67: goto L5b;
                case 68: goto L73;
                case 70: goto L70;
                case 73: goto L6a;
                case 74: goto L6d;
                case 83: goto L67;
                case 86: goto L51;
                case 90: goto L53;
                default: goto L43;
            }
        L43:
            if (r3 == 0) goto L78
            char r1 = findInHierarchy(r0)
            if (r1 == 0) goto L78
            if (r1 == r3) goto L16
            if (r1 >= r3) goto L76
            r4 = r5
            goto L16
        L51:
            r4 = r5
            goto L16
        L53:
            java.lang.String r8 = "java.lang.Boolean"
            boolean r8 = r0.equals(r8)
            if (r8 != 0) goto L16
        L5b:
            java.lang.String r8 = "java.lang.Character"
            boolean r8 = r0.equals(r8)
            if (r8 == 0) goto L43
            goto L16
        L64:
            r3 = 65
            goto L43
        L67:
            r3 = 66
            goto L43
        L6a:
            r3 = 67
            goto L43
        L6d:
            r3 = 68
            goto L43
        L70:
            r3 = 72
            goto L43
        L73:
            r3 = 73
            goto L43
        L76:
            r4 = r6
            goto L16
        L78:
            java.lang.String r4 = "java.lang.Object"
            boolean r4 = r0.equals(r4)
            if (r4 != 0) goto L84
            gnu.bytecode.ClassType r4 = gnu.bytecode.PrimType.toStringType
            if (r10 != r4) goto L86
        L84:
            r4 = r6
            goto L16
        L86:
            r4 = r7
            goto L16
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.PrimType.compare(gnu.bytecode.Type):int");
    }
}
