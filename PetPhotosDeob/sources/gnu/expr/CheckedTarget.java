package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;

/* loaded from: classes.dex */
public class CheckedTarget extends StackTarget {
    static Method initWrongTypeProcMethod;
    static Method initWrongTypeStringMethod;
    static ClassType typeClassCastException;
    static ClassType typeWrongType;
    int argno;
    LambdaExp proc;
    String procname;

    public CheckedTarget(Type type) {
        super(type);
        this.argno = -4;
    }

    public CheckedTarget(Type type, LambdaExp proc, int argno) {
        super(type);
        this.proc = proc;
        this.procname = proc.getName();
        this.argno = argno;
    }

    public CheckedTarget(Type type, String procname, int argno) {
        super(type);
        this.procname = procname;
        this.argno = argno;
    }

    public static Target getInstance(Type type, String procname, int argno) {
        return type == Type.objectType ? Target.pushObject : new CheckedTarget(type, procname, argno);
    }

    public static Target getInstance(Type type, LambdaExp proc, int argno) {
        return type == Type.objectType ? Target.pushObject : new CheckedTarget(type, proc, argno);
    }

    public static Target getInstance(Type type) {
        return type == Type.objectType ? Target.pushObject : new CheckedTarget(type);
    }

    public static Target getInstance(Declaration decl) {
        return getInstance(decl.getType(), decl.getName(), -2);
    }

    private static void initWrongType() {
        if (typeClassCastException == null) {
            typeClassCastException = ClassType.make("java.lang.ClassCastException");
        }
        if (typeWrongType == null) {
            typeWrongType = ClassType.make("gnu.mapping.WrongType");
            Type[] args = {typeClassCastException, Compilation.javaStringType, Type.intType, Type.objectType};
            initWrongTypeStringMethod = typeWrongType.addMethod("<init>", 1, args, Type.voidType);
            Type[] args2 = {typeClassCastException, Compilation.typeProcedure, Type.intType, Type.objectType};
            initWrongTypeProcMethod = typeWrongType.addMethod("<init>", 1, args2, Type.voidType);
        }
    }

    @Override // gnu.expr.StackTarget, gnu.expr.Target
    public void compileFromStack(Compilation comp, Type stackType) {
        if (!compileFromStack0(comp, stackType)) {
            emitCheckedCoerce(comp, this.proc, this.procname, this.argno, this.type, null);
        }
    }

    public static void emitCheckedCoerce(Compilation comp, String procname, int argno, Type type) {
        emitCheckedCoerce(comp, null, procname, argno, type, null);
    }

    public static void emitCheckedCoerce(Compilation comp, LambdaExp proc, int argno, Type type) {
        emitCheckedCoerce(comp, proc, proc.getName(), argno, type, null);
    }

    public static void emitCheckedCoerce(Compilation comp, LambdaExp proc, int argno, Type type, Variable argValue) {
        emitCheckedCoerce(comp, proc, proc.getName(), argno, type, argValue);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void emitCheckedCoerce(Compilation comp, LambdaExp proc, String procname, int argno, Type type, Variable argValue) {
        Scope tmpScope;
        CodeAttr code = comp.getCode();
        boolean isInTry = code.isInTry();
        initWrongType();
        Label startTry = new Label(code);
        if (argValue == null && type != Type.toStringType) {
            tmpScope = code.pushScope();
            argValue = code.addLocal(Type.objectType);
            code.emitDup(1);
            code.emitStore(argValue);
        } else {
            tmpScope = null;
        }
        int startPC = code.getPC();
        startTry.define(code);
        emitCoerceFromObject(type, comp);
        int endPC = code.getPC();
        if (endPC == startPC || type == Type.toStringType) {
            if (tmpScope != null) {
                code.popScope();
                return;
            }
            return;
        }
        Label endTry = new Label(code);
        endTry.define(code);
        Label endLabel = new Label(code);
        endLabel.setTypes(code);
        if (isInTry) {
            code.emitGoto(endLabel);
        }
        int fragment_cookie = 0;
        code.setUnreachable();
        if (!isInTry) {
            fragment_cookie = code.beginFragment(endLabel);
        }
        code.addHandler(startTry, endTry, typeClassCastException);
        boolean thisIsProc = false;
        if (proc != null && proc.isClassGenerated() && !comp.method.getStaticFlag() && comp.method.getDeclaringClass() == proc.getCompiledClassType(comp)) {
            thisIsProc = true;
        }
        int line = comp.getLineNumber();
        if (line > 0) {
            code.putLineNumber(line);
        }
        code.emitNew(typeWrongType);
        code.emitDupX();
        code.emitSwap();
        if (thisIsProc) {
            code.emitPushThis();
        } else {
            if (procname == null && argno != -4) {
                procname = "lambda";
            }
            code.emitPushString(procname);
        }
        code.emitPushInt(argno);
        code.emitLoad(argValue);
        code.emitInvokeSpecial(thisIsProc ? initWrongTypeProcMethod : initWrongTypeStringMethod);
        if (tmpScope != null) {
            code.popScope();
        }
        code.emitThrow();
        if (isInTry) {
            endLabel.define(code);
        } else {
            code.endFragment(fragment_cookie);
        }
    }
}
