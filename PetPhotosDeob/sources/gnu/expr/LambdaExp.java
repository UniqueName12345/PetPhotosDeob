package gnu.expr;

import gnu.bytecode.Access;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.ExceptionsAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Filter;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import java.util.Set;
import java.util.Vector;

/* loaded from: classes.dex */
public class LambdaExp extends ScopeExp {
    public static final int ATTEMPT_INLINE = 4096;
    static final int CANNOT_INLINE = 32;
    static final int CAN_CALL = 4;
    static final int CAN_READ = 2;
    static final int CLASS_METHOD = 64;
    static final int DEFAULT_CAPTURES_ARG = 512;
    static final int IMPORTS_LEX_VARS = 8;
    static final int INLINE_ONLY = 8192;
    static final int METHODS_COMPILED = 128;
    static final int NEEDS_STATIC_LINK = 16;
    protected static final int NEXT_AVAIL_FLAG = 16384;
    public static final int NO_FIELD = 256;
    public static final int OVERLOADABLE_FIELD = 2048;
    public static final int SEQUENCE_RESULT = 1024;
    static Method searchForKeywordMethod3;
    static Method searchForKeywordMethod4;
    static final ApplyExp unknownContinuation = new ApplyExp((Expression) null, (Expression[]) null);
    Vector applyMethods;
    Variable argsArray;
    public Expression body;
    Declaration capturedVars;
    Variable closureEnv;
    public Field closureEnvField;
    public Expression[] defaultArgs;
    private Declaration firstArgsArrayArg;
    public LambdaExp firstChild;
    Variable heapFrame;
    Initializer initChain;
    public LambdaExp inlineHome;
    public Keyword[] keywords;
    public int max_args;
    public int min_args;
    public Declaration nameDecl;
    public LambdaExp nextSibling;
    Method[] primBodyMethods;
    Method[] primMethods;
    Object[] properties;
    public Expression returnContinuation;
    public Type returnType;
    int selectorValue;
    public Field staticLinkField;
    Set<LambdaExp> tailCallers;
    Procedure thisValue;
    Variable thisVariable;
    Expression[] throwsSpecification;
    ClassType type = Compilation.typeProcedure;

    public void capture(Declaration decl) {
        if (decl.isSimple()) {
            if (this.capturedVars == null && !decl.isStatic() && !(this instanceof ModuleExp) && !(this instanceof ClassExp)) {
                this.heapFrame = new Variable("heapFrame");
            }
            decl.setSimple(false);
            if (!decl.isPublic()) {
                decl.nextCapturedVar = this.capturedVars;
                this.capturedVars = decl;
            }
        }
    }

    public void setExceptions(Expression[] exceptions) {
        this.throwsSpecification = exceptions;
    }

    public final boolean getInlineOnly() {
        return (this.flags & 8192) != 0;
    }

    public final void setInlineOnly(boolean inlineOnly) {
        setFlag(inlineOnly, 8192);
    }

    public final boolean getNeedsClosureEnv() {
        return (this.flags & 24) != 0;
    }

    public final boolean getNeedsStaticLink() {
        return (this.flags & 16) != 0;
    }

    public final void setNeedsStaticLink(boolean needsStaticLink) {
        if (!needsStaticLink) {
            this.flags &= -17;
        } else {
            this.flags |= 16;
        }
    }

    public final boolean getImportsLexVars() {
        return (this.flags & 8) != 0;
    }

    public final void setImportsLexVars(boolean importsLexVars) {
        if (!importsLexVars) {
            this.flags &= -9;
        } else {
            this.flags |= 8;
        }
    }

    public final void setImportsLexVars() {
        int old = this.flags;
        this.flags |= 8;
        if ((old & 8) == 0 && this.nameDecl != null) {
            setCallersNeedStaticLink();
        }
    }

    public final void setNeedsStaticLink() {
        int old = this.flags;
        this.flags |= 16;
        if ((old & 16) == 0 && this.nameDecl != null) {
            setCallersNeedStaticLink();
        }
    }

    void setCallersNeedStaticLink() {
        LambdaExp outer = outerLambda();
        for (ApplyExp app = this.nameDecl.firstCall; app != null; app = app.nextCall) {
            for (LambdaExp caller = app.context; caller != outer && !(caller instanceof ModuleExp); caller = caller.outerLambda()) {
                caller.setNeedsStaticLink();
            }
        }
    }

    public final boolean getCanRead() {
        return (this.flags & 2) != 0;
    }

    public final void setCanRead(boolean read) {
        if (!read) {
            this.flags &= -3;
        } else {
            this.flags |= 2;
        }
    }

    public final boolean getCanCall() {
        return (this.flags & 4) != 0;
    }

    public final void setCanCall(boolean called) {
        if (!called) {
            this.flags &= -5;
        } else {
            this.flags |= 4;
        }
    }

    public final boolean isClassMethod() {
        return (this.flags & 64) != 0;
    }

    public final void setClassMethod(boolean isMethod) {
        if (!isMethod) {
            this.flags &= -65;
        } else {
            this.flags |= 64;
        }
    }

    public final boolean isModuleBody() {
        return this instanceof ModuleExp;
    }

    public final boolean isClassGenerated() {
        return isModuleBody() || (this instanceof ClassExp);
    }

    public boolean isAbstract() {
        return this.body == QuoteExp.abstractExp;
    }

    public int getCallConvention() {
        if (isModuleBody()) {
            if (Compilation.defaultCallConvention >= 2) {
                return Compilation.defaultCallConvention;
            }
            return 2;
        }
        if (!isClassMethod() && Compilation.defaultCallConvention != 0) {
            return Compilation.defaultCallConvention;
        }
        return 1;
    }

    public final boolean isHandlingTailCalls() {
        return isModuleBody() || (Compilation.defaultCallConvention >= 3 && !isClassMethod());
    }

    public final boolean variable_args() {
        return this.max_args < 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public ClassType getCompiledClassType(Compilation comp) {
        if (this.type == Compilation.typeProcedure) {
            throw new Error("internal error: getCompiledClassType");
        }
        return this.type;
    }

    @Override // gnu.expr.Expression
    public Type getType() {
        return this.type;
    }

    public ClassType getClassType() {
        return this.type;
    }

    public void setType(ClassType type) {
        this.type = type;
    }

    public int incomingArgs() {
        if (this.min_args != this.max_args || this.max_args > 4 || this.max_args <= 0) {
            return 1;
        }
        return this.max_args;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getSelectorValue(Compilation comp) {
        int s = this.selectorValue;
        if (s == 0) {
            int s2 = comp.maxSelectorValue;
            comp.maxSelectorValue = this.primMethods.length + s2;
            int s3 = s2 + 1;
            this.selectorValue = s3;
            return s3;
        }
        return s;
    }

    public final Method getMethod(int argCount) {
        int index;
        if (this.primMethods != null) {
            if ((this.max_args < 0 || argCount <= this.max_args) && (index = argCount - this.min_args) >= 0) {
                int length = this.primMethods.length;
                Method[] methodArr = this.primMethods;
                if (index >= length) {
                    index = length - 1;
                }
                return methodArr[index];
            }
            return null;
        }
        return null;
    }

    public final Method getMainMethod() {
        Method[] methods = this.primBodyMethods;
        if (methods == null) {
            return null;
        }
        return methods[methods.length - 1];
    }

    public final Type restArgType() {
        if (this.min_args == this.max_args) {
            return null;
        }
        if (this.primMethods == null) {
            throw new Error("internal error - restArgType");
        }
        Method[] methods = this.primMethods;
        if (this.max_args < 0 || methods.length <= this.max_args - this.min_args) {
            Method method = methods[methods.length - 1];
            Type[] types = method.getParameterTypes();
            int ilast = types.length - 1;
            if (method.getName().endsWith("$X")) {
                ilast--;
            }
            return types[ilast];
        }
        return null;
    }

    public LambdaExp outerLambda() {
        if (this.outer == null) {
            return null;
        }
        return this.outer.currentLambda();
    }

    public LambdaExp outerLambdaNotInline() {
        ScopeExp exp = this;
        while (true) {
            exp = exp.outer;
            if (exp == null) {
                return null;
            }
            if (exp instanceof LambdaExp) {
                LambdaExp result = (LambdaExp) exp;
                if (!result.getInlineOnly()) {
                    return result;
                }
            }
        }
    }

    boolean inlinedIn(LambdaExp outer) {
        for (LambdaExp exp = this; exp.getInlineOnly(); exp = exp.getCaller()) {
            if (exp == outer) {
                return true;
            }
        }
        return false;
    }

    public LambdaExp getCaller() {
        return this.inlineHome;
    }

    public Variable declareThis(ClassType clas) {
        if (this.thisVariable == null) {
            this.thisVariable = new Variable("this");
            getVarScope().addVariableAfter(null, this.thisVariable);
            this.thisVariable.setParameter(true);
        }
        if (this.thisVariable.getType() == null) {
            this.thisVariable.setType(clas);
        }
        if (this.decls != null && this.decls.isThisParameter()) {
            this.decls.var = this.thisVariable;
        }
        return this.thisVariable;
    }

    public Variable declareClosureEnv() {
        Variable prev;
        if (this.closureEnv == null && getNeedsClosureEnv()) {
            LambdaExp parent = outerLambda();
            if (parent instanceof ClassExp) {
                parent = parent.outerLambda();
            }
            Variable parentFrame = parent.heapFrame != null ? parent.heapFrame : parent.closureEnv;
            if (isClassMethod() && !"*init*".equals(getName())) {
                this.closureEnv = declareThis(this.type);
            } else if (parent.heapFrame == null && !parent.getNeedsStaticLink() && !(parent instanceof ModuleExp)) {
                this.closureEnv = null;
            } else if (!isClassGenerated() && !getInlineOnly()) {
                Method primMethod = getMainMethod();
                boolean isInit = "*init*".equals(getName());
                if (!primMethod.getStaticFlag() && !isInit) {
                    this.closureEnv = declareThis(primMethod.getDeclaringClass());
                } else {
                    Type envType = primMethod.getParameterTypes()[0];
                    this.closureEnv = new Variable("closureEnv", envType);
                    if (isInit) {
                        prev = declareThis(primMethod.getDeclaringClass());
                    } else {
                        prev = null;
                    }
                    getVarScope().addVariableAfter(prev, this.closureEnv);
                    this.closureEnv.setParameter(true);
                }
            } else if (inlinedIn(parent)) {
                this.closureEnv = parentFrame;
            } else {
                this.closureEnv = new Variable("closureEnv", parentFrame.getType());
                getVarScope().addVariable(this.closureEnv);
            }
        }
        return this.closureEnv;
    }

    public LambdaExp() {
    }

    public LambdaExp(int args) {
        this.min_args = args;
        this.max_args = args;
    }

    public LambdaExp(Expression body) {
        this.body = body;
    }

    public void loadHeapFrame(Compilation comp) {
        ClassType curType;
        LambdaExp curLambda = comp.curLambda;
        while (curLambda != this && curLambda.getInlineOnly()) {
            curLambda = curLambda.getCaller();
        }
        CodeAttr code = comp.getCode();
        if (curLambda.heapFrame != null && this == curLambda) {
            code.emitLoad(curLambda.heapFrame);
            return;
        }
        if (curLambda.closureEnv != null) {
            code.emitLoad(curLambda.closureEnv);
            curType = (ClassType) curLambda.closureEnv.getType();
        } else {
            code.emitPushThis();
            curType = comp.curClass;
        }
        while (curLambda != this) {
            Field link = curLambda.staticLinkField;
            if (link != null && link.getDeclaringClass() == curType) {
                code.emitGetField(link);
                curType = (ClassType) link.getType();
            }
            curLambda = curLambda.outerLambda();
        }
    }

    Declaration getArg(int i) {
        for (Declaration var = firstDecl(); var != null; var = var.nextDecl()) {
            if (i == 0) {
                return var;
            }
            i--;
        }
        throw new Error("internal error - getArg");
    }

    public void compileEnd(Compilation comp) {
        CodeAttr code = comp.getCode();
        if (!getInlineOnly()) {
            if (comp.method.reachableHere() && (Compilation.defaultCallConvention < 3 || isModuleBody() || isClassMethod() || isHandlingTailCalls())) {
                code.emitReturn();
            }
            popScope(code);
            code.popScope();
        }
        for (LambdaExp child = this.firstChild; child != null; child = child.nextSibling) {
            if (!child.getCanRead() && !child.getInlineOnly()) {
                child.compileAsMethod(comp);
            }
        }
        if (this.heapFrame != null) {
            comp.generateConstructor(this);
        }
    }

    public void generateApplyMethods(Compilation comp) {
        comp.generateMatchMethods(this);
        if (Compilation.defaultCallConvention >= 2) {
            comp.generateApplyMethodsWithContext(this);
        } else {
            comp.generateApplyMethodsWithoutContext(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Field allocFieldFor(Compilation comp) {
        if (this.nameDecl != null && this.nameDecl.field != null) {
            return this.nameDecl.field;
        }
        boolean needsClosure = getNeedsClosureEnv();
        ClassType frameType = needsClosure ? getOwningLambda().getHeapFrameType() : comp.mainClass;
        String name = getName();
        String fname = name == null ? "lambda" : Compilation.mangleNameIfNeeded(name);
        int fflags = 16;
        if (this.nameDecl != null && (this.nameDecl.context instanceof ModuleExp)) {
            boolean external_access = this.nameDecl.needsExternalAccess();
            if (external_access) {
                fname = Declaration.PRIVATE_PREFIX + fname;
            }
            if (this.nameDecl.getFlag(2048L)) {
                fflags = 16 | 8;
                if (!((ModuleExp) this.nameDecl.context).isStatic()) {
                    fflags &= -17;
                }
            }
            if (!this.nameDecl.isPrivate() || external_access || comp.immediate) {
                fflags |= 1;
            }
            if ((this.flags & 2048) != 0) {
                String fname0 = fname;
                int suffix = this.min_args == this.max_args ? this.min_args : 1;
                while (true) {
                    int suffix2 = suffix + 1;
                    fname = fname0 + '$' + suffix;
                    if (frameType.getDeclaredField(fname) == null) {
                        break;
                    }
                    suffix = suffix2;
                }
            }
        } else {
            StringBuilder append = new StringBuilder().append(fname).append("$Fn");
            int i = comp.localFieldIndex + 1;
            comp.localFieldIndex = i;
            fname = append.append(i).toString();
            if (!needsClosure) {
                fflags = 16 | 8;
            }
        }
        Type rtype = Compilation.typeModuleMethod;
        Field field = frameType.addField(fname, rtype, fflags);
        if (this.nameDecl != null) {
            this.nameDecl.field = field;
            return field;
        }
        return field;
    }

    final void addApplyMethod(Compilation comp, Field field) {
        LambdaExp owner = this;
        if (field != null && field.getStaticFlag()) {
            owner = comp.getModule();
        } else {
            do {
                owner = owner.outerLambda();
                if (owner instanceof ModuleExp) {
                    break;
                }
            } while (owner.heapFrame == null);
            ClassType frameType = owner.getHeapFrameType();
            if (!frameType.getSuperclass().isSubtype(Compilation.typeModuleBody)) {
                owner = comp.getModule();
            }
        }
        if (owner.applyMethods == null) {
            owner.applyMethods = new Vector();
        }
        owner.applyMethods.addElement(this);
    }

    public Field compileSetField(Compilation comp) {
        if (this.primMethods == null) {
            allocMethod(outerLambda(), comp);
        }
        Field field = allocFieldFor(comp);
        if (comp.usingCPStyle()) {
            compile(comp, Type.objectType);
        } else {
            compileAsMethod(comp);
            addApplyMethod(comp, field);
        }
        return new ProcInitializer(this, comp, field).field;
    }

    @Override // gnu.expr.Expression
    public void compile(Compilation comp, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            CodeAttr code = comp.getCode();
            LambdaExp outer = outerLambda();
            Type rtype = Compilation.typeModuleMethod;
            if ((this.flags & 256) != 0 || (comp.immediate && (outer instanceof ModuleExp))) {
                if (this.primMethods == null) {
                    allocMethod(outerLambda(), comp);
                }
                compileAsMethod(comp);
                addApplyMethod(comp, null);
                ProcInitializer.emitLoadModuleMethod(this, comp);
            } else {
                Field field = compileSetField(comp);
                if (field.getStaticFlag()) {
                    code.emitGetStatic(field);
                } else {
                    LambdaExp parent = comp.curLambda;
                    Variable frame = parent.heapFrame != null ? parent.heapFrame : parent.closureEnv;
                    code.emitLoad(frame);
                    code.emitGetField(field);
                }
            }
            target.compileFromStack(comp, rtype);
        }
    }

    public ClassType getHeapFrameType() {
        return ((this instanceof ModuleExp) || (this instanceof ClassExp)) ? (ClassType) getType() : (ClassType) this.heapFrame.getType();
    }

    public LambdaExp getOwningLambda() {
        for (ScopeExp exp = this.outer; exp != null; exp = exp.outer) {
            if ((exp instanceof ModuleExp) || (((exp instanceof ClassExp) && getNeedsClosureEnv()) || ((exp instanceof LambdaExp) && ((LambdaExp) exp).heapFrame != null))) {
                return (LambdaExp) exp;
            }
        }
        return null;
    }

    void addMethodFor(Compilation comp, ObjectType closureEnvType) {
        ClassType ctype;
        ScopeExp sc = this;
        while (sc != null && !(sc instanceof ClassExp)) {
            sc = sc.outer;
        }
        if (sc != null) {
            ctype = ((ClassExp) sc).instanceType;
        } else {
            ctype = getOwningLambda().getHeapFrameType();
        }
        addMethodFor(ctype, comp, closureEnvType);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addMethodFor(ClassType ctype, Compilation comp, ObjectType closureEnvType) {
        boolean isStatic;
        String name = getName();
        LambdaExp outer = outerLambda();
        int key_args = this.keywords == null ? 0 : this.keywords.length;
        int opt_args = this.defaultArgs == null ? 0 : this.defaultArgs.length - key_args;
        int numStubs = (this.flags & 512) != 0 ? 0 : opt_args;
        boolean varArgs = this.max_args < 0 || this.min_args + numStubs < this.max_args;
        Method[] methods = new Method[numStubs + 1];
        this.primBodyMethods = methods;
        if (this.primMethods == null) {
            this.primMethods = methods;
        }
        char isInitMethod = 0;
        if (this.nameDecl != null && this.nameDecl.getFlag(4096L)) {
            isStatic = false;
        } else if (this.nameDecl != null && this.nameDecl.getFlag(2048L)) {
            isStatic = true;
        } else if (isClassMethod()) {
            if (outer instanceof ClassExp) {
                ClassExp cl = (ClassExp) outer;
                isStatic = cl.isMakingClassPair() && closureEnvType != null;
                if (this == cl.initMethod) {
                    isInitMethod = Access.INNERCLASS_CONTEXT;
                } else if (this == cl.clinitMethod) {
                    isInitMethod = Access.CLASS_CONTEXT;
                    isStatic = true;
                }
            } else {
                isStatic = false;
            }
        } else if (this.thisVariable != null || closureEnvType == ctype) {
            isStatic = false;
        } else if (this.nameDecl != null && (this.nameDecl.context instanceof ModuleExp)) {
            ModuleExp mexp = (ModuleExp) this.nameDecl.context;
            isStatic = mexp.getSuperType() == null && mexp.getInterfaces() == null;
        } else {
            isStatic = true;
        }
        StringBuffer nameBuf = new StringBuffer(60);
        int mflags = isStatic ? 8 : 0;
        if (this.nameDecl != null) {
            if (this.nameDecl.needsExternalAccess()) {
                mflags |= 1;
            } else {
                short defaultFlag = this.nameDecl.isPrivate() ? (short) 0 : (short) 1;
                if (isClassMethod()) {
                    defaultFlag = this.nameDecl.getAccessFlags(defaultFlag);
                }
                mflags |= defaultFlag;
            }
        }
        if ((!outer.isModuleBody() && !(outer instanceof ClassExp)) || name == null) {
            nameBuf.append("lambda");
            int i = comp.method_counter + 1;
            comp.method_counter = i;
            nameBuf.append(i);
        }
        if (isInitMethod == 'C') {
            nameBuf.append("<clinit>");
        } else if (getSymbol() != null) {
            nameBuf.append(Compilation.mangleName(name));
        }
        if (getFlag(1024)) {
            nameBuf.append("$C");
        }
        boolean withContext = getCallConvention() >= 2 && isInitMethod == 0;
        if (isInitMethod != 0) {
            if (isStatic) {
                mflags = (mflags & (-3)) + 1;
            } else {
                mflags = (mflags & 2) + 2;
            }
        }
        if (ctype.isInterface() || isAbstract()) {
            mflags |= 1024;
        }
        if (isClassMethod() && (outer instanceof ClassExp) && this.min_args == this.max_args) {
            Method[] inherited = null;
            int iarg = 0;
            Declaration param = firstDecl();
            while (true) {
                if (param == null) {
                    if (this.returnType != null) {
                        break;
                    }
                } else {
                    if (param.isThisParameter()) {
                        iarg--;
                    } else if (param.getFlag(8192L)) {
                        continue;
                    }
                    param = param.nextDecl();
                    iarg++;
                }
                if (inherited == null) {
                    final String mangled = nameBuf.toString();
                    Filter filter = new Filter() { // from class: gnu.expr.LambdaExp.1
                        @Override // gnu.bytecode.Filter
                        public boolean select(Object value) {
                            Method method = (Method) value;
                            if (method.getName().equals(mangled)) {
                                Type[] ptypes = method.getParameterTypes();
                                return ptypes.length == LambdaExp.this.min_args;
                            }
                            return false;
                        }
                    };
                    inherited = ctype.getMethods(filter, 2);
                }
                Type type = null;
                int i2 = inherited.length;
                while (true) {
                    i2--;
                    if (i2 >= 0) {
                        Method method = inherited[i2];
                        Type ptype = param == null ? method.getReturnType() : method.getParameterTypes()[iarg];
                        if (type == null) {
                            type = ptype;
                        } else if (ptype != type) {
                            if (param == null) {
                                break;
                            }
                        }
                    } else {
                        if (type != null) {
                            if (param != null) {
                                param.setType(type);
                            } else {
                                setCoercedReturnType(type);
                            }
                        }
                        if (param == null) {
                            break;
                        }
                    }
                }
            }
        }
        Type rtype = (getFlag(1024) || getCallConvention() >= 2) ? Type.voidType : getReturnType().getImplementationType();
        int extraArg = (closureEnvType == null || closureEnvType == ctype) ? 0 : 1;
        int ctxArg = 0;
        if (getCallConvention() >= 2 && isInitMethod == 0) {
            ctxArg = 1;
        }
        int nameBaseLength = nameBuf.length();
        for (int i3 = 0; i3 <= numStubs; i3++) {
            nameBuf.setLength(nameBaseLength);
            int plainArgs = this.min_args + i3;
            int numArgs = plainArgs;
            if (i3 == numStubs && varArgs) {
                numArgs++;
            }
            Type[] atypes = new Type[extraArg + numArgs + ctxArg];
            if (extraArg > 0) {
                atypes[0] = closureEnvType;
            }
            Declaration var = firstDecl();
            if (var != null && var.isThisParameter()) {
                var = var.nextDecl();
            }
            int itype = 0;
            while (true) {
                int itype2 = itype;
                if (itype2 >= plainArgs) {
                    break;
                }
                itype = itype2 + 1;
                atypes[extraArg + itype2] = var.getType().getImplementationType();
                var = var.nextDecl();
            }
            if (ctxArg != 0) {
                atypes[atypes.length - 1] = Compilation.typeCallContext;
            }
            if (plainArgs < numArgs) {
                Type lastType = var.getType();
                String lastTypeName = lastType.getName();
                if (ctype.getClassfileVersion() >= 3211264 && (lastType instanceof ArrayType)) {
                    mflags |= 128;
                } else {
                    nameBuf.append("$V");
                }
                if (key_args > 0 || numStubs < opt_args || (!"gnu.lists.LList".equals(lastTypeName) && !(lastType instanceof ArrayType))) {
                    lastType = Compilation.objArrayType;
                    this.argsArray = new Variable("argsArray", Compilation.objArrayType);
                    this.argsArray.setParameter(true);
                }
                this.firstArgsArrayArg = var;
                atypes[atypes.length - (withContext ? 2 : 1)] = lastType;
            }
            if (withContext) {
                nameBuf.append("$X");
            }
            boolean classSpecified = (outer instanceof ClassExp) || ((outer instanceof ModuleExp) && ((ModuleExp) outer).getFlag(131072));
            String name2 = nameBuf.toString();
            int renameCount = 0;
            int len = nameBuf.length();
            while (true) {
                for (ClassType t = ctype; t != null; t = t.getSuperclass()) {
                    if (t.getDeclaredMethod(name2, atypes) != null) {
                        break;
                    } else if (classSpecified) {
                        break;
                    }
                }
                nameBuf.setLength(len);
                nameBuf.append('$');
                renameCount++;
                nameBuf.append(renameCount);
                name2 = nameBuf.toString();
            }
            Method method2 = ctype.addMethod(name2, atypes, rtype, mflags);
            methods[i3] = method2;
            if (this.throwsSpecification != null && this.throwsSpecification.length > 0) {
                int n = this.throwsSpecification.length;
                ClassType[] exceptions = new ClassType[n];
                for (int j = 0; j < n; j++) {
                    ClassType exception = null;
                    Expression throwsExpr = this.throwsSpecification[j];
                    String msg = null;
                    if (throwsExpr instanceof ReferenceExp) {
                        ReferenceExp throwsRef = (ReferenceExp) throwsExpr;
                        Declaration decl = throwsRef.getBinding();
                        if (decl != null) {
                            Expression declValue = decl.getValue();
                            if (declValue instanceof ClassExp) {
                                exception = ((ClassExp) declValue).getCompiledClassType(comp);
                            } else {
                                msg = "throws specification " + decl.getName() + " has non-class lexical binding";
                            }
                        } else {
                            msg = "unknown class " + throwsRef.getName();
                        }
                    } else if (throwsExpr instanceof QuoteExp) {
                        Object value = ((QuoteExp) throwsExpr).getValue();
                        if (value instanceof Class) {
                            value = Type.make((Class) value);
                        }
                        if (value instanceof ClassType) {
                            exception = value;
                        }
                        if (exception != null && !exception.isSubtype(Type.javalangThrowableType)) {
                            msg = exception.getName() + " does not extend Throwable";
                        }
                    }
                    if (exception == null && msg == null) {
                        msg = "invalid throws specification";
                    }
                    if (msg != null) {
                        comp.error('e', msg, throwsExpr);
                        exception = Type.javalangThrowableType;
                    }
                    exceptions[j] = exception;
                }
                ExceptionsAttr attr = new ExceptionsAttr(method2);
                attr.setExceptions(exceptions);
            }
        }
    }

    public void allocChildClasses(Compilation comp) {
        Method main = getMainMethod();
        if (main != null && !main.getStaticFlag()) {
            declareThis(main.getDeclaringClass());
        }
        Declaration decl = firstDecl();
        while (true) {
            if (decl == this.firstArgsArrayArg && this.argsArray != null) {
                getVarScope().addVariable(this.argsArray);
            }
            if (!getInlineOnly() && getCallConvention() >= 2 && (this.firstArgsArrayArg != null ? !(this.argsArray == null ? decl != this.firstArgsArrayArg.nextDecl() : decl != this.firstArgsArrayArg) : decl == null)) {
                getVarScope().addVariable(null, Compilation.typeCallContext, "$ctx").setParameter(true);
            }
            if (decl != null) {
                if (decl.var == null && (!getInlineOnly() || !decl.ignorable())) {
                    if (decl.isSimple() && !decl.isIndirectBinding()) {
                        decl.allocateVariable(null);
                    } else {
                        String vname = Compilation.mangleName(decl.getName()).intern();
                        Type vtype = decl.getType().getImplementationType();
                        Variable var = getVarScope().addVariable(null, vtype, vname);
                        decl.var = var;
                        var.setParameter(true);
                    }
                }
                decl = decl.nextDecl();
            } else {
                declareClosureEnv();
                allocFrame(comp);
                allocChildMethods(comp);
                return;
            }
        }
    }

    void allocMethod(LambdaExp outer, Compilation comp) {
        ObjectType closureEnvType;
        if (!getNeedsClosureEnv()) {
            closureEnvType = null;
        } else if ((outer instanceof ClassExp) || (outer instanceof ModuleExp)) {
            closureEnvType = outer.getCompiledClassType(comp);
        } else {
            LambdaExp owner = outer;
            while (owner.heapFrame == null) {
                owner = owner.outerLambda();
            }
            closureEnvType = (ClassType) owner.heapFrame.getType();
        }
        addMethodFor(comp, closureEnvType);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void allocChildMethods(Compilation comp) {
        ClassType parentFrameType;
        for (LambdaExp child = this.firstChild; child != null; child = child.nextSibling) {
            if (!child.isClassGenerated() && !child.getInlineOnly() && child.nameDecl != null) {
                child.allocMethod(this, comp);
            }
            if (child instanceof ClassExp) {
                ClassExp cl = (ClassExp) child;
                if (cl.getNeedsClosureEnv()) {
                    if ((this instanceof ModuleExp) || (this instanceof ClassExp)) {
                        parentFrameType = (ClassType) getType();
                    } else {
                        Variable parentFrame = this.heapFrame != null ? this.heapFrame : this.closureEnv;
                        parentFrameType = (ClassType) parentFrame.getType();
                    }
                    Field outerLink = cl.instanceType.setOuterLink(parentFrameType);
                    cl.staticLinkField = outerLink;
                    cl.closureEnvField = outerLink;
                }
            }
        }
    }

    public void allocFrame(Compilation comp) {
        ClassType frameType;
        if (this.heapFrame != null) {
            if ((this instanceof ModuleExp) || (this instanceof ClassExp)) {
                frameType = getCompiledClassType(comp);
            } else {
                frameType = new ClassType(comp.generateClassName("frame"));
                frameType.setSuper(comp.getModuleType());
                comp.addClass(frameType);
            }
            this.heapFrame.setType(frameType);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void allocParameters(Compilation comp) {
        CodeAttr code = comp.getCode();
        code.locals.enterScope(getVarScope());
        int line = getLineNumber();
        if (line > 0) {
            code.putLineNumber(getFileName(), line);
        }
        if (this.heapFrame != null) {
            this.heapFrame.allocateLocal(code);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void enterFunction(Compilation comp) {
        int key_i;
        int opt_i;
        CodeAttr code = comp.getCode();
        getVarScope().noteStartFunction(code);
        if (this.closureEnv != null && !this.closureEnv.isParameter() && !comp.usingCPStyle()) {
            if (!getInlineOnly()) {
                code.emitPushThis();
                Field field = this.closureEnvField;
                if (field == null) {
                    field = outerLambda().closureEnvField;
                }
                code.emitGetField(field);
                code.emitStore(this.closureEnv);
            } else if (!inlinedIn(outerLambda())) {
                outerLambda().loadHeapFrame(comp);
                code.emitStore(this.closureEnv);
            }
        }
        if (!comp.usingCPStyle()) {
            ClassType frameType = this.heapFrame == null ? currentModule().getCompiledClassType(comp) : (ClassType) this.heapFrame.getType();
            for (Declaration decl = this.capturedVars; decl != null; decl = decl.nextCapturedVar) {
                if (decl.field == null) {
                    decl.makeField(frameType, comp, null);
                }
            }
        }
        if (this.heapFrame != null && !comp.usingCPStyle()) {
            ClassType frameType2 = (ClassType) this.heapFrame.getType();
            if (this.closureEnv != null && !(this instanceof ModuleExp)) {
                this.staticLinkField = frameType2.addField("staticLink", this.closureEnv.getType());
            }
            if (!(this instanceof ModuleExp) && !(this instanceof ClassExp)) {
                frameType2.setEnclosingMember(comp.method);
                code.emitNew(frameType2);
                code.emitDup(frameType2);
                Method constructor = Compilation.getConstructor(frameType2, this);
                code.emitInvokeSpecial(constructor);
                if (this.staticLinkField != null) {
                    code.emitDup(frameType2);
                    code.emitLoad(this.closureEnv);
                    code.emitPutField(this.staticLinkField);
                }
                code.emitStore(this.heapFrame);
            }
        }
        Variable argsArray = this.argsArray;
        if (this.min_args == this.max_args && this.primMethods == null && getCallConvention() < 2) {
            argsArray = null;
        }
        int i = 0;
        int key_args = this.keywords == null ? 0 : this.keywords.length;
        int opt_args = this.defaultArgs == null ? 0 : this.defaultArgs.length - key_args;
        if (!(this instanceof ModuleExp)) {
            int plainArgs = -1;
            int defaultStart = 0;
            getMainMethod();
            Variable callContextSave = comp.callContextVar;
            Declaration param = firstDecl();
            int key_i2 = 0;
            int opt_i2 = 0;
            while (param != null) {
                comp.callContextVar = getCallConvention() < 2 ? null : getVarScope().lookup("$ctx");
                if (param == this.firstArgsArrayArg && argsArray != null) {
                    if (this.primMethods != null) {
                        plainArgs = i;
                        defaultStart = plainArgs - this.min_args;
                    } else {
                        plainArgs = 0;
                        defaultStart = 0;
                    }
                }
                if (plainArgs >= 0 || !param.isSimple() || param.isIndirectBinding()) {
                    Type paramType = param.getType();
                    Type stackType = plainArgs >= 0 ? Type.objectType : paramType;
                    if (!param.isSimple()) {
                        param.loadOwningObject(null, comp);
                    }
                    if (plainArgs < 0) {
                        code.emitLoad(param.getVariable());
                        key_i = key_i2;
                        opt_i = opt_i2;
                    } else if (i < this.min_args) {
                        code.emitLoad(argsArray);
                        code.emitPushInt(i);
                        code.emitArrayLoad(Type.objectType);
                        key_i = key_i2;
                        opt_i = opt_i2;
                    } else if (i < this.min_args + opt_args) {
                        code.emitPushInt(i - plainArgs);
                        code.emitLoad(argsArray);
                        code.emitArrayLength();
                        code.emitIfIntLt();
                        code.emitLoad(argsArray);
                        code.emitPushInt(i - plainArgs);
                        code.emitArrayLoad();
                        code.emitElse();
                        opt_i = opt_i2 + 1;
                        this.defaultArgs[defaultStart + opt_i2].compile(comp, paramType);
                        code.emitFi();
                        key_i = key_i2;
                    } else if (this.max_args < 0 && i == this.min_args + opt_args) {
                        code.emitLoad(argsArray);
                        code.emitPushInt(i - plainArgs);
                        code.emitInvokeStatic(Compilation.makeListMethod);
                        stackType = Compilation.scmListType;
                        key_i = key_i2;
                        opt_i = opt_i2;
                    } else {
                        code.emitLoad(argsArray);
                        code.emitPushInt((this.min_args + opt_args) - plainArgs);
                        key_i = key_i2 + 1;
                        comp.compileConstant(this.keywords[key_i2]);
                        opt_i = opt_i2 + 1;
                        Expression defaultArg = this.defaultArgs[defaultStart + opt_i2];
                        if (defaultArg instanceof QuoteExp) {
                            if (searchForKeywordMethod4 == null) {
                                Type[] argts = {Compilation.objArrayType, Type.intType, Type.objectType, Type.objectType};
                                searchForKeywordMethod4 = Compilation.scmKeywordType.addMethod("searchForKeyword", argts, Type.objectType, 9);
                            }
                            defaultArg.compile(comp, paramType);
                            code.emitInvokeStatic(searchForKeywordMethod4);
                        } else {
                            if (searchForKeywordMethod3 == null) {
                                Type[] argts2 = {Compilation.objArrayType, Type.intType, Type.objectType};
                                searchForKeywordMethod3 = Compilation.scmKeywordType.addMethod("searchForKeyword", argts2, Type.objectType, 9);
                            }
                            code.emitInvokeStatic(searchForKeywordMethod3);
                            code.emitDup(1);
                            comp.compileConstant(Special.dfault);
                            code.emitIfEq();
                            code.emitPop(1);
                            defaultArg.compile(comp, paramType);
                            code.emitFi();
                        }
                    }
                    if (paramType != stackType) {
                        CheckedTarget.emitCheckedCoerce(comp, this, i + 1, paramType);
                    }
                    if (param.isIndirectBinding()) {
                        param.pushIndirectBinding(comp);
                    }
                    if (param.isSimple()) {
                        Variable var = param.getVariable();
                        if (param.isIndirectBinding()) {
                            var.setType(Compilation.typeLocation);
                        }
                        code.emitStore(var);
                    } else {
                        code.emitPutField(param.field);
                    }
                } else {
                    key_i = key_i2;
                    opt_i = opt_i2;
                }
                i++;
                param = param.nextDecl();
                key_i2 = key_i;
                opt_i2 = opt_i;
            }
            comp.callContextVar = callContextSave;
        }
    }

    void compileAsMethod(Compilation comp) {
        Expression arg;
        if ((this.flags & 128) == 0 && !isAbstract()) {
            this.flags |= 128;
            if (this.primMethods != null) {
                Method save_method = comp.method;
                LambdaExp save_lambda = comp.curLambda;
                comp.curLambda = this;
                Method method = this.primMethods[0];
                boolean isStatic = method.getStaticFlag();
                int numStubs = this.primMethods.length - 1;
                Type restArgType = restArgType();
                long[] saveDeclFlags = null;
                if (numStubs > 0) {
                    saveDeclFlags = new long[this.min_args + numStubs];
                    Declaration decl = firstDecl();
                    for (int k = 0; k < this.min_args + numStubs; k++) {
                        saveDeclFlags[k] = decl.flags;
                        decl = decl.nextDecl();
                    }
                }
                boolean ctxArg = getCallConvention() >= 2;
                for (int i = 0; i <= numStubs; i++) {
                    comp.method = this.primMethods[i];
                    if (i < numStubs) {
                        CodeAttr code = comp.method.startCode();
                        int toCall = i + 1;
                        while (toCall < numStubs && (this.defaultArgs[toCall] instanceof QuoteExp)) {
                            toCall++;
                        }
                        boolean varArgs = toCall == numStubs && restArgType != null;
                        Variable callContextSave = comp.callContextVar;
                        Variable var = code.getArg(0);
                        if (!isStatic) {
                            code.emitPushThis();
                            if (getNeedsClosureEnv()) {
                                this.closureEnv = var;
                            }
                            var = code.getArg(1);
                        }
                        Declaration decl2 = firstDecl();
                        int j = 0;
                        while (j < this.min_args + i) {
                            decl2.flags |= 64;
                            decl2.var = var;
                            code.emitLoad(var);
                            var = var.nextVar();
                            j++;
                            decl2 = decl2.nextDecl();
                        }
                        comp.callContextVar = ctxArg ? var : null;
                        int j2 = i;
                        while (j2 < toCall) {
                            Target paramTarget = StackTarget.getInstance(decl2.getType());
                            this.defaultArgs[j2].compile(comp, paramTarget);
                            j2++;
                            decl2 = decl2.nextDecl();
                        }
                        if (varArgs) {
                            String lastTypeName = restArgType.getName();
                            if ("gnu.lists.LList".equals(lastTypeName)) {
                                arg = new QuoteExp(LList.Empty);
                            } else if ("java.lang.Object[]".equals(lastTypeName)) {
                                arg = new QuoteExp(Values.noArgs);
                            } else {
                                throw new Error("unimplemented #!rest type " + lastTypeName);
                            }
                            arg.compile(comp, restArgType);
                        }
                        if (ctxArg) {
                            code.emitLoad(var);
                        }
                        if (isStatic) {
                            code.emitInvokeStatic(this.primMethods[toCall]);
                        } else {
                            code.emitInvokeVirtual(this.primMethods[toCall]);
                        }
                        code.emitReturn();
                        this.closureEnv = null;
                        comp.callContextVar = callContextSave;
                    } else {
                        if (saveDeclFlags != null) {
                            Declaration decl3 = firstDecl();
                            for (int k2 = 0; k2 < this.min_args + numStubs; k2++) {
                                decl3.flags = saveDeclFlags[k2];
                                decl3.var = null;
                                decl3 = decl3.nextDecl();
                            }
                        }
                        comp.method.initCode();
                        allocChildClasses(comp);
                        allocParameters(comp);
                        enterFunction(comp);
                        compileBody(comp);
                        compileEnd(comp);
                        generateApplyMethods(comp);
                    }
                }
                comp.method = save_method;
                comp.curLambda = save_lambda;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v3, types: [gnu.expr.Expression] */
    /* JADX WARN: Type inference failed for: r5v1, types: [gnu.expr.Expression] */
    /* JADX WARN: Type inference failed for: r5v3 */
    public void compileBody(Compilation comp) {
        Target target;
        Variable callContextSave = comp.callContextVar;
        comp.callContextVar = null;
        if (getCallConvention() >= 2) {
            Variable var = getVarScope().lookup("$ctx");
            if (var != null && var.getType() == Compilation.typeCallContext) {
                comp.callContextVar = var;
            }
            target = ConsumerTarget.makeContextTarget(comp);
        } else {
            target = Target.pushValue(getReturnType());
        }
        ?? r3 = this.body;
        int lineNumber = this.body.getLineNumber();
        ?? r5 = this;
        if (lineNumber > 0) {
            r5 = this.body;
        }
        r3.compileWithPosition(comp, target, r5);
        comp.callContextVar = callContextSave;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ScopeExp, gnu.expr.Expression
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        LambdaExp saveLambda;
        Compilation comp = visitor.getCompilation();
        if (comp == null) {
            saveLambda = null;
        } else {
            saveLambda = comp.curLambda;
            comp.curLambda = this;
        }
        try {
            return visitor.visitLambdaExp(this, d);
        } finally {
            if (comp != null) {
                comp.curLambda = saveLambda;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        visitChildrenOnly(visitor, d);
        visitProperties(visitor, d);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final <R, D> void visitChildrenOnly(ExpVisitor<R, D> visitor, D d) {
        LambdaExp save = visitor.currentLambda;
        visitor.currentLambda = this;
        try {
            this.throwsSpecification = visitor.visitExps(this.throwsSpecification, d);
            visitor.visitDefaultArgs(this, d);
            if (visitor.exitValue == null && this.body != null) {
                this.body = visitor.update(this.body, visitor.visit(this.body, d));
            }
        } finally {
            visitor.currentLambda = save;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final <R, D> void visitProperties(ExpVisitor<R, D> visitor, D d) {
        if (this.properties != null) {
            int len = this.properties.length;
            for (int i = 1; i < len; i += 2) {
                Object val = this.properties[i];
                if (val instanceof Expression) {
                    this.properties[i] = visitor.visitAndUpdate((Expression) val, d);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public boolean mustCompile() {
        if (this.keywords == null || this.keywords.length <= 0) {
            if (this.defaultArgs != null) {
                int i = this.defaultArgs.length;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    Expression def = this.defaultArgs[i];
                    if (def != null && !(def instanceof QuoteExp)) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override // gnu.expr.Expression, gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        setIndexes();
        ctx.writeValue(new Closure(this, ctx));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Object evalDefaultArg(int index, CallContext ctx) {
        try {
            return this.defaultArgs[index].eval(ctx);
        } catch (Throwable ex) {
            throw new WrappedException("error evaluating default argument", ex);
        }
    }

    @Override // gnu.expr.Expression
    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        Method method;
        Expression[] margs;
        Expression inlined;
        Expression[] args = exp.getArgs();
        if ((this.flags & 4096) != 0 && (inlined = InlineCalls.inlineCall(this, args, true)) != null) {
            return visitor.visit(inlined, required);
        }
        exp.visitArgs(visitor);
        int args_length = exp.args.length;
        String msg = WrongArguments.checkArgCount(getName(), this.min_args, this.max_args, args_length);
        if (msg != null) {
            return visitor.noteError(msg);
        }
        int conv = getCallConvention();
        Compilation comp = visitor.getCompilation();
        if (comp.inlineOk((Expression) this) && isClassMethod()) {
            if ((conv <= 2 || conv == 3) && (method = getMethod(args_length)) != null) {
                boolean isStatic = this.nameDecl.isStatic();
                if (!isStatic && (this.outer instanceof ClassExp)) {
                    ClassExp cl = (ClassExp) this.outer;
                    if (cl.isMakingClassPair()) {
                    }
                }
                PrimProcedure mproc = new PrimProcedure(method, this);
                if (isStatic) {
                    margs = exp.args;
                } else {
                    for (LambdaExp curLambda = visitor.getCurrentLambda(); curLambda != null; curLambda = curLambda.outerLambda()) {
                        if (curLambda.outer == this.outer) {
                            Declaration d = curLambda.firstDecl();
                            if (d == null || !d.isThisParameter()) {
                                return visitor.noteError("calling non-static method " + getName() + " from static method " + curLambda.getName());
                            }
                            int nargs = exp.getArgCount();
                            margs = new Expression[nargs + 1];
                            System.arraycopy(exp.getArgs(), 0, margs, 1, nargs);
                            margs[0] = new ThisExp(d);
                        }
                    }
                    return visitor.noteError("internal error: missing " + this);
                }
                ApplyExp nexp = new ApplyExp(mproc, margs);
                return nexp.setLine(exp);
            }
            return exp;
        }
        return exp;
    }

    @Override // gnu.expr.Expression
    public void print(OutPort out) {
        int opt_i;
        Special mode;
        int opt_i2;
        out.startLogicalBlock("(Lambda/", ")", 2);
        Object sym = getSymbol();
        if (sym != null) {
            out.print(sym);
            out.print('/');
        }
        out.print(this.id);
        out.print('/');
        out.print("fl:");
        out.print(Integer.toHexString(this.flags));
        out.writeSpaceFill();
        printLineColumn(out);
        out.startLogicalBlock("(", false, ")");
        Special prevMode = null;
        int i = 0;
        int key_args = this.keywords == null ? 0 : this.keywords.length;
        int opt_args = this.defaultArgs == null ? 0 : this.defaultArgs.length - key_args;
        Declaration decl = firstDecl();
        if (decl == null || !decl.isThisParameter()) {
            opt_i = 0;
        } else {
            i = -1;
            opt_i = 0;
        }
        while (decl != null) {
            if (i < this.min_args) {
                mode = null;
            } else if (i < this.min_args + opt_args) {
                mode = Special.optional;
            } else if (this.max_args < 0 && i == this.min_args + opt_args) {
                mode = Special.rest;
            } else {
                mode = Special.key;
            }
            if (decl != firstDecl()) {
                out.writeSpaceFill();
            }
            if (mode != prevMode) {
                out.print(mode);
                out.writeSpaceFill();
            }
            Expression defaultArg = null;
            if (mode == Special.optional || mode == Special.key) {
                opt_i2 = opt_i + 1;
                defaultArg = this.defaultArgs[opt_i];
            } else {
                opt_i2 = opt_i;
            }
            if (defaultArg != null) {
                out.print('(');
            }
            decl.printInfo(out);
            if (defaultArg != null && defaultArg != QuoteExp.falseExp) {
                out.print(' ');
                defaultArg.print(out);
                out.print(')');
            }
            i++;
            prevMode = mode;
            decl = decl.nextDecl();
            opt_i = opt_i2;
        }
        out.endLogicalBlock(")");
        out.writeSpaceLinear();
        if (this.body == null) {
            out.print("<null body>");
        } else {
            this.body.print(out);
        }
        out.endLogicalBlock(")");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String getExpClassName() {
        String cname = getClass().getName();
        int index = cname.lastIndexOf(46);
        if (index >= 0) {
            return cname.substring(index + 1);
        }
        return cname;
    }

    @Override // gnu.expr.Expression
    public boolean side_effects() {
        return false;
    }

    @Override // gnu.expr.ScopeExp, gnu.expr.Expression, gnu.mapping.Procedure
    public String toString() {
        String str = getExpClassName() + ':' + getSymbol() + '/' + this.id + '/';
        int l = getLineNumber();
        if (l <= 0 && this.body != null) {
            l = this.body.getLineNumber();
        }
        if (l > 0) {
            return str + "l:" + l;
        }
        return str;
    }

    @Override // gnu.mapping.PropertySet
    public Object getProperty(Object key, Object defaultValue) {
        if (this.properties != null) {
            int i = this.properties.length;
            do {
                i -= 2;
                if (i < 0) {
                    return defaultValue;
                }
            } while (this.properties[i] != key);
            return this.properties[i + 1];
        }
        return defaultValue;
    }

    @Override // gnu.mapping.PropertySet
    public synchronized void setProperty(Object key, Object value) {
        this.properties = PropertySet.setProperty(this.properties, key, value);
    }

    public final Type getReturnType() {
        if (this.returnType == null) {
            this.returnType = Type.objectType;
            if (this.body != null && !isAbstract()) {
                this.returnType = this.body.getType();
            }
        }
        return this.returnType;
    }

    public final void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public final void setCoercedReturnType(Type returnType) {
        this.returnType = returnType;
        if (returnType != null && returnType != Type.objectType && returnType != Type.voidType && this.body != QuoteExp.abstractExp) {
            Expression value = this.body;
            this.body = Compilation.makeCoercion(value, returnType);
            this.body.setLine(value);
        }
    }

    public final void setCoercedReturnValue(Expression type, Language language) {
        if (!isAbstract()) {
            Expression value = this.body;
            this.body = Compilation.makeCoercion(value, type);
            this.body.setLine(value);
        }
        Type rtype = language.getTypeFor(type);
        if (rtype != null) {
            setReturnType(rtype);
        }
    }
}
