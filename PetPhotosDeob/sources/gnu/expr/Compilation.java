package gnu.expr;

import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.PrimType;
import gnu.bytecode.SwitchState;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.functions.Convert;
import gnu.kawa.lispexpr.LispReader;
import gnu.mapping.Environment;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.text.Lexer;
import gnu.text.Options;
import gnu.text.Path;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kawa.Shell;

/* loaded from: classes.dex */
public class Compilation implements SourceLocator {
    public static final int BODY_PARSED = 4;
    public static final int CALL_WITH_CONSUMER = 2;
    public static final int CALL_WITH_CONTINUATIONS = 4;
    public static final int CALL_WITH_RETURN = 1;
    public static final int CALL_WITH_TAILCALLS = 3;
    public static final int CALL_WITH_UNSPECIFIED = 0;
    public static final int CLASS_WRITTEN = 14;
    public static final int COMPILED = 12;
    public static final int COMPILE_SETUP = 10;
    public static final int ERROR_SEEN = 100;
    public static final int MODULE_NONSTATIC = -1;
    public static final int MODULE_STATIC = 1;
    public static final int MODULE_STATIC_DEFAULT = 0;
    public static final int MODULE_STATIC_RUN = 2;
    public static final int PROLOG_PARSED = 2;
    public static final int PROLOG_PARSING = 1;
    public static final int RESOLVED = 6;
    public static final int WALKED = 8;
    public static Type[] apply0args;
    public static Method apply0method;
    public static Type[] apply1args;
    public static Method apply1method;
    public static Type[] apply2args;
    public static Method apply2method;
    public static Method apply3method;
    public static Method apply4method;
    private static Type[] applyCpsArgs;
    public static Method applyCpsMethod;
    public static Type[] applyNargs;
    public static Method applyNmethod;
    public static Method[] applymethods;
    public static Field argsCallContextField;
    private static Compilation chainUninitialized;
    static Method checkArgCountMethod;
    public static String classPrefixDefault;
    private static final ThreadLocal<Compilation> current;
    public static boolean debugPrintFinalExpr;
    public static int defaultCallConvention;
    public static boolean emitSourceDebugExtAttr;
    public static boolean generateMainDefault;
    public static Method getCallContextInstanceMethod;
    public static Method getCurrentEnvironmentMethod;
    public static final Method getLocation2EnvironmentMethod;
    public static boolean inlineOk;
    static Method makeListMethod;
    public static Field noArgsField;
    public static Field pcCallContextField;
    public static Field procCallContextField;
    public static ClassType typeApplet;
    public static ClassType typeCallContext;
    public static final ClassType typeConsumer;
    public static ClassType typeMethodProc;
    public static ClassType typeModuleBody;
    public static ClassType typeModuleMethod;
    public static ClassType typeModuleWithContext;
    public static ClassType typeProcedure0;
    public static ClassType typeProcedure1;
    public static ClassType typeProcedure2;
    public static ClassType typeProcedure3;
    public static ClassType typeProcedure4;
    public static ClassType[] typeProcedureArray;
    public static ClassType typeProcedureN;
    public static ClassType typeServlet;
    public static ClassType typeValues;
    Variable callContextVar;
    Variable callContextVarForInit;
    ClassType[] classes;
    Initializer clinitChain;
    Method clinitMethod;
    public ClassType curClass;
    public LambdaExp curLambda;
    protected ScopeExp current_scope;
    public boolean explicit;
    public Stack<Expression> exprStack;
    Method forNameHelper;
    SwitchState fswitch;
    Field fswitchIndex;
    public boolean immediate;
    private int keyUninitialized;
    int langOptions;
    protected Language language;
    public Lexer lexer;
    public NameLookup lexical;
    LitTable litTable;
    ArrayClassLoader loader;
    int localFieldIndex;
    public ClassType mainClass;
    public ModuleExp mainLambda;
    int maxSelectorValue;
    protected SourceMessages messages;
    public Method method;
    int method_counter;
    public ModuleInfo minfo;
    public ClassType moduleClass;
    Field moduleInstanceMainField;
    Variable moduleInstanceVar;
    private Compilation nextUninitialized;
    int numClasses;
    boolean pedantic;
    public Stack<Object> pendingImports;
    private int state;
    public Variable thisDecl;
    public static boolean debugPrintExpr = false;
    public static Options options = new Options();
    public static Options.OptionInfo warnUndefinedVariable = options.add("warn-undefined-variable", 1, Boolean.TRUE, "warn if no compiler-visible binding for a variable");
    public static Options.OptionInfo warnUnknownMember = options.add("warn-unknown-member", 1, Boolean.TRUE, "warn if referencing an unknown method or field");
    public static Options.OptionInfo warnInvokeUnknownMethod = options.add("warn-invoke-unknown-method", 1, warnUnknownMember, "warn if invoke calls an unknown method (subsumed by warn-unknown-member)");
    public static Options.OptionInfo warnAsError = options.add("warn-as-error", 1, Boolean.FALSE, "Make all warnings into errors");
    public static int defaultClassFileVersion = ClassType.JDK_1_5_VERSION;
    public static int moduleStatic = 0;
    public static ClassType typeObject = Type.objectType;
    public static ClassType scmBooleanType = ClassType.make("java.lang.Boolean");
    public static ClassType typeString = ClassType.make("java.lang.String");
    public static ClassType javaStringType = typeString;
    public static ClassType scmKeywordType = ClassType.make("gnu.expr.Keyword");
    public static ClassType scmSequenceType = ClassType.make("gnu.lists.Sequence");
    public static ClassType scmListType = ClassType.make("gnu.lists.LList");
    public static ClassType typePair = ClassType.make("gnu.lists.Pair");
    public static final ArrayType objArrayType = ArrayType.make(typeObject);
    public static ClassType typeRunnable = ClassType.make("java.lang.Runnable");
    public static ClassType typeType = ClassType.make("gnu.bytecode.Type");
    public static ClassType typeObjectType = ClassType.make("gnu.bytecode.ObjectType");
    public static ClassType typeClass = Type.javalangClassType;
    public static ClassType typeClassType = ClassType.make("gnu.bytecode.ClassType");
    public static ClassType typeProcedure = ClassType.make("gnu.mapping.Procedure");
    public static ClassType typeLanguage = ClassType.make("gnu.expr.Language");
    public static ClassType typeEnvironment = ClassType.make("gnu.mapping.Environment");
    public static ClassType typeLocation = ClassType.make("gnu.mapping.Location");
    public static ClassType typeSymbol = ClassType.make("gnu.mapping.Symbol");
    public static final Method getSymbolValueMethod = typeLanguage.getDeclaredMethod("getSymbolValue", 1);
    public static final Method getSymbolProcedureMethod = typeLanguage.getDeclaredMethod("getSymbolProcedure", 1);
    public static final Method getLocationMethod = typeLocation.addMethod("get", Type.typeArray0, Type.objectType, 1);
    public static final Method getProcedureBindingMethod = typeSymbol.addMethod("getProcedure", Type.typeArray0, typeProcedure, 1);
    public static final Field trueConstant = scmBooleanType.getDeclaredField("TRUE");
    public static final Field falseConstant = scmBooleanType.getDeclaredField("FALSE");
    static final Method setNameMethod = typeProcedure.getDeclaredMethod("setName", 1);
    public static final Type[] int1Args = {Type.intType};
    public static final Type[] string1Arg = {javaStringType};
    public static final Type[] sym1Arg = string1Arg;
    public static final Method getLocation1EnvironmentMethod = typeEnvironment.getDeclaredMethod("getLocation", 1);
    public boolean mustCompile = ModuleExp.alwaysCompile;
    public Options currentOptions = new Options(options);
    public boolean generateMain = generateMainDefault;
    public String classPrefix = classPrefixDefault;

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isPedantic() {
        return this.pedantic;
    }

    public void pushPendingImport(ModuleInfo info, ScopeExp defs, int formSize) {
        if (this.pendingImports == null) {
            this.pendingImports = new Stack<>();
        }
        this.pendingImports.push(info);
        this.pendingImports.push(defs);
        Expression posExp = new ReferenceExp((Object) null);
        posExp.setLine(this);
        this.pendingImports.push(posExp);
        this.pendingImports.push(Integer.valueOf(formSize));
    }

    static {
        Type[] args = {typeSymbol, Type.objectType};
        getLocation2EnvironmentMethod = typeEnvironment.addMethod("getLocation", args, typeLocation, 17);
        Type[] makeListArgs = {objArrayType, Type.intType};
        makeListMethod = scmListType.addMethod("makeList", makeListArgs, scmListType, 9);
        getCurrentEnvironmentMethod = typeEnvironment.addMethod("getCurrent", Type.typeArray0, typeEnvironment, 9);
        apply0args = Type.typeArray0;
        apply1args = new Type[]{typeObject};
        apply2args = new Type[]{typeObject, typeObject};
        applyNargs = new Type[]{objArrayType};
        apply0method = typeProcedure.addMethod("apply0", apply0args, typeObject, 17);
        apply1method = typeProcedure.addMethod("apply1", apply1args, typeObject, 1);
        apply2method = typeProcedure.addMethod("apply2", apply2args, typeObject, 1);
        Type[] apply3args = {typeObject, typeObject, typeObject};
        apply3method = typeProcedure.addMethod("apply3", apply3args, typeObject, 1);
        Type[] apply4args = {typeObject, typeObject, typeObject, typeObject};
        apply4method = typeProcedure.addMethod("apply4", apply4args, typeObject, 1);
        applyNmethod = typeProcedure.addMethod("applyN", applyNargs, typeObject, 1);
        Type[] args2 = {typeProcedure, Type.intType};
        checkArgCountMethod = typeProcedure.addMethod("checkArgCount", args2, Type.voidType, 9);
        applymethods = new Method[]{apply0method, apply1method, apply2method, apply3method, apply4method, applyNmethod};
        typeProcedure0 = ClassType.make("gnu.mapping.Procedure0");
        typeProcedure1 = ClassType.make("gnu.mapping.Procedure1");
        typeProcedure2 = ClassType.make("gnu.mapping.Procedure2");
        typeProcedure3 = ClassType.make("gnu.mapping.Procedure3");
        typeProcedure4 = ClassType.make("gnu.mapping.Procedure4");
        typeProcedureN = ClassType.make("gnu.mapping.ProcedureN");
        typeModuleBody = ClassType.make("gnu.expr.ModuleBody");
        typeModuleWithContext = ClassType.make("gnu.expr.ModuleWithContext");
        typeApplet = ClassType.make("java.applet.Applet");
        typeServlet = ClassType.make("gnu.kawa.servlet.KawaServlet");
        typeCallContext = ClassType.make("gnu.mapping.CallContext");
        typeConsumer = ClassType.make("gnu.lists.Consumer");
        getCallContextInstanceMethod = typeCallContext.getDeclaredMethod("getInstance", 0);
        typeValues = ClassType.make("gnu.mapping.Values");
        noArgsField = typeValues.getDeclaredField("noArgs");
        pcCallContextField = typeCallContext.getDeclaredField("pc");
        typeMethodProc = ClassType.make("gnu.mapping.MethodProc");
        typeModuleMethod = ClassType.make("gnu.expr.ModuleMethod");
        argsCallContextField = typeCallContext.getDeclaredField("values");
        procCallContextField = typeCallContext.getDeclaredField("proc");
        applyCpsArgs = new Type[]{typeCallContext};
        applyCpsMethod = typeProcedure.addMethod("apply", applyCpsArgs, Type.voidType, 1);
        typeProcedureArray = new ClassType[]{typeProcedure0, typeProcedure1, typeProcedure2, typeProcedure3, typeProcedure4};
        generateMainDefault = false;
        inlineOk = true;
        classPrefixDefault = "";
        emitSourceDebugExtAttr = true;
        current = new InheritableThreadLocal();
    }

    public boolean warnUndefinedVariable() {
        return this.currentOptions.getBoolean(warnUndefinedVariable);
    }

    public boolean warnUnknownMember() {
        return this.currentOptions.getBoolean(warnUnknownMember);
    }

    public boolean warnInvokeUnknownMethod() {
        return this.currentOptions.getBoolean(warnInvokeUnknownMethod);
    }

    public boolean warnAsError() {
        return this.currentOptions.getBoolean(warnAsError);
    }

    public final boolean getBooleanOption(String key, boolean defaultValue) {
        return this.currentOptions.getBoolean(key, defaultValue);
    }

    public final boolean getBooleanOption(String key) {
        return this.currentOptions.getBoolean(key);
    }

    public boolean usingCPStyle() {
        return defaultCallConvention == 4;
    }

    public boolean usingTailCalls() {
        return defaultCallConvention >= 3;
    }

    public final CodeAttr getCode() {
        return this.method.getCode();
    }

    public boolean generatingApplet() {
        return (this.langOptions & 16) != 0;
    }

    public boolean generatingServlet() {
        return (this.langOptions & 32) != 0;
    }

    public boolean sharedModuleDefs() {
        return (this.langOptions & 2) != 0;
    }

    public void setSharedModuleDefs(boolean shared) {
        if (shared) {
            this.langOptions |= 2;
        } else {
            this.langOptions &= -3;
        }
    }

    public final ClassType getModuleType() {
        return defaultCallConvention >= 2 ? typeModuleWithContext : typeModuleBody;
    }

    public void compileConstant(Object value) {
        CodeAttr code = getCode();
        if (value == null) {
            code.emitPushNull();
        } else if ((value instanceof String) && !this.immediate) {
            code.emitPushString((String) value);
        } else {
            code.emitGetStatic(compileConstantToField(value));
        }
    }

    public Field compileConstantToField(Object value) {
        Literal literal = this.litTable.findLiteral(value);
        if (literal.field == null) {
            literal.assign(this.litTable);
        }
        return literal.field;
    }

    public boolean inlineOk(Expression proc) {
        if (proc instanceof LambdaExp) {
            LambdaExp lproc = (LambdaExp) proc;
            Declaration nameDecl = lproc.nameDecl;
            if (nameDecl == null || nameDecl.getSymbol() == null || !(nameDecl.context instanceof ModuleExp)) {
                return true;
            }
            if (this.immediate && nameDecl.isPublic() && !lproc.getFlag(2048) && (this.curLambda == null || lproc.topLevel() != this.curLambda.topLevel())) {
                return false;
            }
        }
        return inlineOk;
    }

    public boolean inlineOk(Procedure proc) {
        if (this.immediate && (proc instanceof ModuleMethod) && (((ModuleMethod) proc).module.getClass().getClassLoader() instanceof ArrayClassLoader)) {
            return false;
        }
        return inlineOk;
    }

    public void compileConstant(Object value, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            if (value instanceof Values) {
                Object[] values = ((Values) value).getValues();
                if (target instanceof ConsumerTarget) {
                    for (Object obj : values) {
                        compileConstant(obj, target);
                    }
                    return;
                }
            }
            if (target instanceof ConditionalTarget) {
                ConditionalTarget ctarg = (ConditionalTarget) target;
                getCode().emitGoto(getLanguage().isTrue(value) ? ctarg.ifTrue : ctarg.ifFalse);
                return;
            }
            if (target instanceof StackTarget) {
                Type type = ((StackTarget) target).getType();
                if (type instanceof PrimType) {
                    try {
                        String signature = type.getSignature();
                        CodeAttr code = getCode();
                        char sig1 = (signature == null || signature.length() != 1) ? ' ' : signature.charAt(0);
                        if (value instanceof Number) {
                            Number num = (Number) value;
                            switch (sig1) {
                                case 'B':
                                    code.emitPushInt(num.byteValue());
                                    return;
                                case 'D':
                                    code.emitPushDouble(num.doubleValue());
                                    return;
                                case 'F':
                                    code.emitPushFloat(num.floatValue());
                                    return;
                                case 'I':
                                    code.emitPushInt(num.intValue());
                                    return;
                                case 'J':
                                    code.emitPushLong(num.longValue());
                                    return;
                                case 'S':
                                    code.emitPushInt(num.shortValue());
                                    return;
                            }
                        }
                        if (sig1 == 'C') {
                            code.emitPushInt(((PrimType) type).charValue(value));
                            return;
                        } else if (sig1 == 'Z') {
                            boolean val = PrimType.booleanValue(value);
                            code.emitPushInt(val ? 1 : 0);
                            return;
                        }
                    } catch (ClassCastException e) {
                    }
                }
                if (type == typeClass && (value instanceof ClassType)) {
                    loadClassRef((ClassType) value);
                    return;
                }
                try {
                    value = type.coerceFromObject(value);
                } catch (Exception e2) {
                    StringBuffer sbuf = new StringBuffer();
                    if (value == Values.empty) {
                        sbuf.append("cannot convert void to ");
                    } else {
                        sbuf.append("cannot convert literal (of type ");
                        if (value == null) {
                            sbuf.append("<null>");
                        } else {
                            sbuf.append(value.getClass().getName());
                        }
                        sbuf.append(") to ");
                    }
                    sbuf.append(type.getName());
                    error('w', sbuf.toString());
                }
            }
            compileConstant(value);
            target.compileFromStack(this, value == null ? target.getType() : Type.make(value.getClass()));
        }
    }

    private void dumpInitializers(Initializer inits) {
        for (Initializer init = Initializer.reverse(inits); init != null; init = init.next) {
            init.emit(this);
        }
    }

    public ClassType findNamedClass(String name) {
        for (int i = 0; i < this.numClasses; i++) {
            if (name.equals(this.classes[i].getName())) {
                return this.classes[i];
            }
        }
        return null;
    }

    private static void putURLWords(String name, StringBuffer sbuf) {
        int dot = name.indexOf(46);
        if (dot > 0) {
            putURLWords(name.substring(dot + 1), sbuf);
            sbuf.append('.');
            name = name.substring(0, dot);
        }
        sbuf.append(name);
    }

    public static String mangleURI(String name) {
        int dot;
        int extLen;
        boolean hasSlash = name.indexOf(47) >= 0;
        int len = name.length();
        if (len > 6 && name.startsWith("class:")) {
            return name.substring(6);
        }
        if (len > 5 && name.charAt(4) == ':' && name.substring(0, 4).equalsIgnoreCase("http")) {
            name = name.substring(5);
            len -= 5;
            hasSlash = true;
        } else if (len > 4 && name.charAt(3) == ':' && name.substring(0, 3).equalsIgnoreCase("uri")) {
            name = name.substring(4);
            len -= 4;
        }
        int start = 0;
        StringBuffer sbuf = new StringBuffer();
        while (true) {
            int slash = name.indexOf(47, start);
            int end = slash < 0 ? len : slash;
            boolean first = sbuf.length() == 0;
            if (first && hasSlash) {
                String host = name.substring(start, end);
                if (end - start > 4 && host.startsWith("www.")) {
                    host = host.substring(4);
                }
                putURLWords(host, sbuf);
            } else if (start != end) {
                if (!first) {
                    sbuf.append('.');
                }
                if (end == len && (dot = name.lastIndexOf(46, len)) > start + 1 && !first && ((extLen = len - dot) <= 4 || (extLen == 5 && name.endsWith("html")))) {
                    len -= extLen;
                    end = len;
                    name = name.substring(0, len);
                }
                sbuf.append(name.substring(start, end));
            }
            if (slash >= 0) {
                start = slash + 1;
            } else {
                return sbuf.toString();
            }
        }
    }

    public static String mangleName(String name) {
        return mangleName(name, -1);
    }

    public static String mangleNameIfNeeded(String name) {
        return (name == null || isValidJavaName(name)) ? name : mangleName(name, 0);
    }

    public static boolean isValidJavaName(String name) {
        int len = name.length();
        if (len == 0 || !Character.isJavaIdentifierStart(name.charAt(0))) {
            return false;
        }
        int i = len;
        do {
            i--;
            if (i <= 0) {
                return true;
            }
        } while (Character.isJavaIdentifierPart(name.charAt(i)));
        return false;
    }

    public static String mangleName(String name, boolean reversible) {
        return mangleName(name, reversible ? 1 : -1);
    }

    public static String mangleName(String name, int kind) {
        boolean reversible = kind >= 0;
        int len = name.length();
        if (len == 6 && name.equals("*init*")) {
            return "<init>";
        }
        StringBuffer mangled = new StringBuffer(len);
        boolean upcaseNext = false;
        int i = 0;
        while (i < len) {
            char ch = name.charAt(i);
            if (upcaseNext) {
                ch = Character.toTitleCase(ch);
                upcaseNext = false;
            }
            if (Character.isDigit(ch)) {
                if (i == 0) {
                    mangled.append("$N");
                }
                mangled.append(ch);
            } else if (Character.isLetter(ch) || ch == '_') {
                mangled.append(ch);
            } else if (ch == '$') {
                mangled.append(kind > 1 ? "$$" : "$");
            } else {
                switch (ch) {
                    case '!':
                        mangled.append("$Ex");
                        break;
                    case '\"':
                        mangled.append("$Dq");
                        break;
                    case '#':
                        mangled.append("$Nm");
                        break;
                    case '%':
                        mangled.append("$Pc");
                        break;
                    case '&':
                        mangled.append("$Am");
                        break;
                    case '\'':
                        mangled.append("$Sq");
                        break;
                    case '(':
                        mangled.append("$LP");
                        break;
                    case ')':
                        mangled.append("$RP");
                        break;
                    case '*':
                        mangled.append("$St");
                        break;
                    case '+':
                        mangled.append("$Pl");
                        break;
                    case ',':
                        mangled.append("$Cm");
                        break;
                    case '-':
                        if (reversible) {
                            mangled.append("$Mn");
                            break;
                        } else {
                            char next = i + 1 < len ? name.charAt(i + 1) : (char) 0;
                            if (next == '>') {
                                mangled.append("$To$");
                                i++;
                                break;
                            } else if (!Character.isLowerCase(next)) {
                                mangled.append("$Mn");
                                break;
                            }
                        }
                        break;
                    case '.':
                        mangled.append("$Dt");
                        break;
                    case '/':
                        mangled.append("$Sl");
                        break;
                    case ':':
                        mangled.append("$Cl");
                        break;
                    case ';':
                        mangled.append("$SC");
                        break;
                    case '<':
                        mangled.append("$Ls");
                        break;
                    case '=':
                        mangled.append("$Eq");
                        break;
                    case '>':
                        mangled.append("$Gr");
                        break;
                    case '?':
                        char first = mangled.length() > 0 ? mangled.charAt(0) : (char) 0;
                        if (!reversible && i + 1 == len && Character.isLowerCase(first)) {
                            mangled.setCharAt(0, Character.toTitleCase(first));
                            mangled.insert(0, "is");
                            break;
                        } else {
                            mangled.append("$Qu");
                            break;
                        }
                    case '@':
                        mangled.append("$At");
                        break;
                    case '[':
                        mangled.append("$LB");
                        break;
                    case ']':
                        mangled.append("$RB");
                        break;
                    case '^':
                        mangled.append("$Up");
                        break;
                    case '{':
                        mangled.append("$LC");
                        break;
                    case '|':
                        mangled.append("$VB");
                        break;
                    case '}':
                        mangled.append("$RC");
                        break;
                    case '~':
                        mangled.append("$Tl");
                        break;
                    default:
                        mangled.append('$');
                        mangled.append(Character.forDigit((ch >> '\f') & 15, 16));
                        mangled.append(Character.forDigit((ch >> '\b') & 15, 16));
                        mangled.append(Character.forDigit((ch >> 4) & 15, 16));
                        mangled.append(Character.forDigit(ch & 15, 16));
                        break;
                }
                if (!reversible) {
                    upcaseNext = true;
                }
            }
            i++;
        }
        String mname = mangled.toString();
        return !mname.equals(name) ? mname : name;
    }

    public static char demangle2(char char1, char char2) {
        switch ((char1 << 16) | char2) {
            case 4259949:
                return '&';
            case 4259956:
                return '@';
            case 4391020:
                return ':';
            case 4391021:
                return ',';
            case 4456561:
                return '\"';
            case 4456564:
                return '.';
            case 4522097:
                return '=';
            case 4522104:
                return '!';
            case 4653170:
                return '>';
            case 4980802:
                return '[';
            case 4980803:
                return '{';
            case 4980816:
                return '(';
            case 4980851:
                return '<';
            case 5046371:
            case 5242979:
                return '%';
            case 5046382:
                return '-';
            case 5111917:
                return '#';
            case 5242988:
                return '+';
            case 5308533:
                return '?';
            case 5374018:
                return ']';
            case 5374019:
                return '}';
            case 5374032:
                return ')';
            case 5439555:
                return ';';
            case 5439596:
                return '/';
            case 5439601:
                return '\\';
            case 5439604:
                return '*';
            case 5505132:
                return '~';
            case 5570672:
                return '^';
            case 5636162:
                return '|';
            default:
                return LispReader.TOKEN_ESCAPE_CHAR;
        }
    }

    public static String demangleName(String name) {
        return demangleName(name, false);
    }

    public static String demangleName(String name, boolean reversible) {
        StringBuffer sbuf = new StringBuffer();
        int len = name.length();
        boolean mangled = false;
        boolean predicate = false;
        boolean downCaseNext = false;
        int i = 0;
        while (i < len) {
            char ch = name.charAt(i);
            if (downCaseNext && !reversible) {
                ch = Character.toLowerCase(ch);
                downCaseNext = false;
            }
            if (!reversible && ch == 'i' && i == 0 && len > 2 && name.charAt(i + 1) == 's') {
                char d = name.charAt(i + 2);
                if (!Character.isLowerCase(d)) {
                    mangled = true;
                    predicate = true;
                    i++;
                    if (Character.isUpperCase(d) || Character.isTitleCase(d)) {
                        sbuf.append(Character.toLowerCase(d));
                        i++;
                    }
                    i++;
                }
            }
            if (ch == '$' && i + 2 < len) {
                char c1 = name.charAt(i + 1);
                char c2 = name.charAt(i + 2);
                char d2 = demangle2(c1, c2);
                if (d2 != 65535) {
                    sbuf.append(d2);
                    i += 2;
                    mangled = true;
                    downCaseNext = true;
                } else if (c1 == 'T' && c2 == 'o' && i + 3 < len && name.charAt(i + 3) == '$') {
                    sbuf.append("->");
                    i += 3;
                    mangled = true;
                    downCaseNext = true;
                }
                i++;
            } else if (!reversible && i > 1 && ((Character.isUpperCase(ch) || Character.isTitleCase(ch)) && Character.isLowerCase(name.charAt(i - 1)))) {
                sbuf.append('-');
                mangled = true;
                ch = Character.toLowerCase(ch);
            }
            sbuf.append(ch);
            i++;
        }
        if (predicate) {
            sbuf.append('?');
        }
        return mangled ? sbuf.toString() : name;
    }

    public String generateClassName(String hint) {
        String hint2 = mangleName(hint, true);
        if (this.mainClass != null) {
            hint2 = this.mainClass.getName() + '$' + hint2;
        } else if (this.classPrefix != null) {
            hint2 = this.classPrefix + hint2;
        }
        if (findNamedClass(hint2) != null) {
            int i = 0;
            while (true) {
                String new_hint = hint2 + i;
                if (findNamedClass(new_hint) == null) {
                    return new_hint;
                }
                i++;
            }
        } else {
            return hint2;
        }
    }

    public Compilation(Language language, SourceMessages messages, NameLookup lexical) {
        this.language = language;
        this.messages = messages;
        this.lexical = lexical;
    }

    public void walkModule(ModuleExp mexp) {
        if (debugPrintExpr) {
            OutPort dout = OutPort.errDefault();
            dout.println("[Module:" + mexp.getName());
            mexp.print(dout);
            dout.println(']');
            dout.flush();
        }
        InlineCalls.inlineCalls(mexp, this);
        PushApply.pushApply(mexp);
        ChainLambdas.chainLambdas(mexp, this);
        FindTailCalls.findTailCalls(mexp, this);
    }

    public void outputClass(String directory) throws IOException {
        char dirSep = File.separatorChar;
        for (int iClass = 0; iClass < this.numClasses; iClass++) {
            ClassType clas = this.classes[iClass];
            String out_name = directory + clas.getName().replace('.', dirSep) + ".class";
            String parent = new File(out_name).getParent();
            if (parent != null) {
                new File(parent).mkdirs();
            }
            clas.writeToFile(out_name);
        }
        this.minfo.cleanupAfterCompilation();
    }

    public void cleanupAfterCompilation() {
        for (int iClass = 0; iClass < this.numClasses; iClass++) {
            this.classes[iClass].cleanupAfterCompilation();
        }
        this.classes = null;
        this.minfo.comp = null;
        if (this.minfo.exp != null) {
            this.minfo.exp.body = null;
        }
        this.mainLambda.body = null;
        this.mainLambda = null;
        if (!this.immediate) {
            this.litTable = null;
        }
    }

    public void compileToArchive(ModuleExp mexp, String fname) throws IOException {
        boolean makeJar;
        ZipOutputStream zout;
        if (fname.endsWith(".zip")) {
            makeJar = false;
        } else if (fname.endsWith(".jar")) {
            makeJar = true;
        } else {
            fname = fname + ".zip";
            makeJar = false;
        }
        process(12);
        File zar_file = new File(fname);
        if (zar_file.exists()) {
            zar_file.delete();
        }
        if (makeJar) {
            zout = new JarOutputStream(new FileOutputStream(zar_file));
        } else {
            zout = new ZipOutputStream(new FileOutputStream(zar_file));
        }
        byte[][] classBytes = new byte[this.numClasses];
        CRC32 zcrc = new CRC32();
        for (int iClass = 0; iClass < this.numClasses; iClass++) {
            ClassType clas = this.classes[iClass];
            classBytes[iClass] = clas.writeToArray();
            ZipEntry zent = new ZipEntry(clas.getName().replace('.', '/') + ".class");
            zent.setSize(classBytes[iClass].length);
            zcrc.reset();
            zcrc.update(classBytes[iClass], 0, classBytes[iClass].length);
            zent.setCrc(zcrc.getValue());
            zout.putNextEntry(zent);
            zout.write(classBytes[iClass]);
        }
        zout.close();
    }

    private void registerClass(ClassType new_class) {
        if (this.classes == null) {
            this.classes = new ClassType[20];
        } else if (this.numClasses >= this.classes.length) {
            ClassType[] new_classes = new ClassType[this.classes.length * 2];
            System.arraycopy(this.classes, 0, new_classes, 0, this.numClasses);
            this.classes = new_classes;
        }
        new_class.addModifiers(new_class.isInterface() ? 1 : 33);
        if (new_class == this.mainClass && this.numClasses > 0) {
            new_class = this.classes[0];
            this.classes[0] = this.mainClass;
        }
        ClassType[] classTypeArr = this.classes;
        int i = this.numClasses;
        this.numClasses = i + 1;
        classTypeArr[i] = new_class;
    }

    public void addClass(ClassType new_class) {
        if (this.mainLambda.filename != null) {
            if (emitSourceDebugExtAttr) {
                new_class.setStratum(getLanguage().getName());
            }
            new_class.setSourceFile(this.mainLambda.filename);
        }
        registerClass(new_class);
        new_class.setClassfileVersion(defaultClassFileVersion);
    }

    public boolean makeRunnable() {
        return (generatingServlet() || generatingApplet() || getModule().staticInitRun()) ? false : true;
    }

    public void addMainClass(ModuleExp module) {
        this.mainClass = module.classFor(this);
        ClassType type = this.mainClass;
        ClassType[] interfaces = module.getInterfaces();
        if (interfaces != null) {
            type.setInterfaces(interfaces);
        }
        ClassType sup = module.getSuperType();
        if (sup == null) {
            if (generatingApplet()) {
                sup = typeApplet;
            } else if (generatingServlet()) {
                sup = typeServlet;
            } else {
                sup = getModuleType();
            }
        }
        if (makeRunnable()) {
            type.addInterface(typeRunnable);
        }
        type.setSuper(sup);
        module.type = type;
        addClass(type);
        getConstructor(this.mainClass, module);
    }

    public final Method getConstructor(LambdaExp lexp) {
        return getConstructor(lexp.getHeapFrameType(), lexp);
    }

    public static final Method getConstructor(ClassType clas, LambdaExp lexp) {
        Method meth = clas.getDeclaredMethod("<init>", 0);
        if (meth == null) {
            Type[] args = (!(lexp instanceof ClassExp) || lexp.staticLinkField == null) ? apply0args : new Type[]{lexp.staticLinkField.getType()};
            return clas.addMethod("<init>", 1, args, Type.voidType);
        }
        return meth;
    }

    public final void generateConstructor(LambdaExp lexp) {
        generateConstructor(lexp.getHeapFrameType(), lexp);
    }

    public final void generateConstructor(ClassType clas, LambdaExp lexp) {
        Method save_method = this.method;
        Variable callContextSave = this.callContextVar;
        this.callContextVar = null;
        ClassType save_class = this.curClass;
        this.curClass = clas;
        Method constructor_method = getConstructor(clas, lexp);
        clas.constructor = constructor_method;
        this.method = constructor_method;
        CodeAttr code = constructor_method.startCode();
        if ((lexp instanceof ClassExp) && lexp.staticLinkField != null) {
            code.emitPushThis();
            code.emitLoad(code.getCurrentScope().getVariable(1));
            code.emitPutField(lexp.staticLinkField);
        }
        ClassType superClass = clas.getSuperclass();
        ClassExp.invokeDefaultSuperConstructor(superClass, this, lexp);
        if (this.curClass == this.mainClass && this.minfo != null && this.minfo.sourcePath != null) {
            code.emitPushThis();
            code.emitInvokeStatic(ClassType.make("gnu.expr.ModuleInfo").getDeclaredMethod("register", 1));
        }
        if (lexp != null && lexp.initChain != null) {
            LambdaExp save = this.curLambda;
            this.curLambda = new LambdaExp();
            this.curLambda.closureEnv = code.getArg(0);
            this.curLambda.outer = save;
            while (true) {
                Initializer init = lexp.initChain;
                if (init == null) {
                    break;
                }
                lexp.initChain = null;
                dumpInitializers(init);
            }
            this.curLambda = save;
        }
        if (lexp instanceof ClassExp) {
            ClassExp cexp = (ClassExp) lexp;
            callInitMethods(cexp.getCompiledClassType(this), new Vector<>(10));
        }
        code.emitReturn();
        this.method = save_method;
        this.curClass = save_class;
        this.callContextVar = callContextSave;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void callInitMethods(ClassType clas, Vector<ClassType> seen) {
        if (clas != null) {
            String name = clas.getName();
            if (!"java.lang.Object".equals(name)) {
                int i = seen.size();
                do {
                    i--;
                    if (i < 0) {
                        seen.addElement(clas);
                        ClassType[] interfaces = clas.getInterfaces();
                        if (interfaces != null) {
                            for (ClassType classType : interfaces) {
                                callInitMethods(classType, seen);
                            }
                        }
                        int clEnvArgs = 1;
                        if (clas.isInterface()) {
                            if (clas instanceof PairClassType) {
                                clas = ((PairClassType) clas).instanceType;
                            } else {
                                try {
                                    clas = (ClassType) Type.make(Class.forName(clas.getName() + "$class"));
                                } catch (Throwable th) {
                                    return;
                                }
                            }
                        } else {
                            clEnvArgs = 0;
                        }
                        Method meth = clas.getDeclaredMethod("$finit$", clEnvArgs);
                        if (meth != null) {
                            CodeAttr code = getCode();
                            code.emitPushThis();
                            code.emitInvoke(meth);
                            return;
                        }
                        return;
                    }
                } while (seen.elementAt(i).getName() != name);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x025a  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x02a5  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02c7  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x02d5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void generateMatchMethods(gnu.expr.LambdaExp r33) {
        /*
            Method dump skipped, instructions count: 804
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.Compilation.generateMatchMethods(gnu.expr.LambdaExp):void");
    }

    public void generateApplyMethodsWithContext(LambdaExp lexp) {
        int numApplyMethods = lexp.applyMethods == null ? 0 : lexp.applyMethods.size();
        if (numApplyMethods != 0) {
            ClassType save_class = this.curClass;
            this.curClass = lexp.getHeapFrameType();
            if (!this.curClass.getSuperclass().isSubtype(typeModuleWithContext)) {
                this.curClass = this.moduleClass;
            }
            ClassType classType = typeModuleMethod;
            Method save_method = this.method;
            Type[] applyArgs = {typeCallContext};
            this.method = this.curClass.addMethod("apply", applyArgs, Type.voidType, 1);
            CodeAttr code = this.method.startCode();
            Variable ctxVar = code.getArg(1);
            code.emitLoad(ctxVar);
            code.emitGetField(pcCallContextField);
            SwitchState aswitch = code.startSwitch();
            for (int j = 0; j < numApplyMethods; j++) {
                LambdaExp source = (LambdaExp) lexp.applyMethods.elementAt(j);
                Method[] primMethods = source.primMethods;
                int numMethods = primMethods.length;
                int i = 0;
                while (i < numMethods) {
                    boolean varArgs = i == numMethods + (-1) && (source.max_args < 0 || source.max_args >= source.min_args + numMethods);
                    int methodIndex = i;
                    aswitch.addCase(source.getSelectorValue(this) + i, code);
                    SourceLocator saveLoc1 = this.messages.swapSourceLocator(source);
                    int line = source.getLineNumber();
                    if (line > 0) {
                        code.putLineNumber(source.getFileName(), line);
                    }
                    Method primMethod = primMethods[methodIndex];
                    Type[] primArgTypes = primMethod.getParameterTypes();
                    int singleArgs = source.min_args + methodIndex;
                    Variable counter = null;
                    int pendingIfEnds = 0;
                    if (i > 4 && numMethods > 1) {
                        counter = code.addLocal(Type.intType);
                        code.emitLoad(ctxVar);
                        code.emitInvoke(typeCallContext.getDeclaredMethod("getArgCount", 0));
                        if (source.min_args != 0) {
                            code.emitPushInt(source.min_args);
                            code.emitSub(Type.intType);
                        }
                        code.emitStore(counter);
                    }
                    int needsThis = primMethod.getStaticFlag() ? 0 : 1;
                    int explicitFrameArg = (varArgs ? 2 : 1) + singleArgs < primArgTypes.length ? 1 : 0;
                    if (needsThis + explicitFrameArg > 0) {
                        code.emitPushThis();
                        if (this.curClass == this.moduleClass && this.mainClass != this.moduleClass) {
                            code.emitGetField(this.moduleInstanceMainField);
                        }
                    }
                    Declaration var = source.firstDecl();
                    if (var != null && var.isThisParameter()) {
                        var = var.nextDecl();
                    }
                    for (int k = 0; k < singleArgs; k++) {
                        if (counter != null && k >= source.min_args) {
                            code.emitLoad(counter);
                            code.emitIfIntLEqZero();
                            code.emitLoad(ctxVar);
                            code.emitInvoke(primMethods[k - source.min_args]);
                            code.emitElse();
                            pendingIfEnds++;
                            code.emitInc(counter, (short) -1);
                        }
                        code.emitLoad(ctxVar);
                        if (k <= 4 && !varArgs && source.max_args <= 4) {
                            code.emitGetField(typeCallContext.getDeclaredField("value" + (k + 1)));
                        } else {
                            code.emitGetField(typeCallContext.getDeclaredField("values"));
                            code.emitPushInt(k);
                            code.emitArrayLoad(Type.objectType);
                        }
                        Type ptype = var.getType();
                        if (ptype != Type.objectType) {
                            SourceLocator saveLoc2 = this.messages.swapSourceLocator(var);
                            CheckedTarget.emitCheckedCoerce(this, source, k + 1, ptype);
                            this.messages.swapSourceLocator(saveLoc2);
                        }
                        var = var.nextDecl();
                    }
                    if (varArgs) {
                        Type lastArgType = primArgTypes[explicitFrameArg + singleArgs];
                        if (lastArgType instanceof ArrayType) {
                            varArgsToArray(source, singleArgs, counter, lastArgType, ctxVar);
                        } else if ("gnu.lists.LList".equals(lastArgType.getName())) {
                            code.emitLoad(ctxVar);
                            code.emitPushInt(singleArgs);
                            code.emitInvokeVirtual(typeCallContext.getDeclaredMethod("getRestArgsList", 1));
                        } else if (lastArgType == typeCallContext) {
                            code.emitLoad(ctxVar);
                        } else {
                            throw new RuntimeException("unsupported #!rest type:" + lastArgType);
                        }
                    }
                    code.emitLoad(ctxVar);
                    code.emitInvoke(primMethod);
                    while (true) {
                        pendingIfEnds--;
                        if (pendingIfEnds < 0) {
                            break;
                        }
                        code.emitFi();
                    }
                    if (defaultCallConvention < 2) {
                        Target.pushObject.compileFromStack(this, source.getReturnType());
                    }
                    this.messages.swapSourceLocator(saveLoc1);
                    code.emitReturn();
                    i++;
                }
            }
            aswitch.addDefault(code);
            Method errMethod = typeModuleMethod.getDeclaredMethod("applyError", 0);
            code.emitInvokeStatic(errMethod);
            code.emitReturn();
            aswitch.finish(code);
            this.method = save_method;
            this.curClass = save_class;
        }
    }

    public void generateApplyMethodsWithoutContext(LambdaExp lexp) {
        int methodIndex;
        int numApplyMethods = lexp.applyMethods == null ? 0 : lexp.applyMethods.size();
        if (numApplyMethods != 0) {
            ClassType save_class = this.curClass;
            this.curClass = lexp.getHeapFrameType();
            ClassType procType = typeModuleMethod;
            if (!this.curClass.getSuperclass().isSubtype(typeModuleBody)) {
                this.curClass = this.moduleClass;
            }
            Method save_method = this.method;
            CodeAttr code = null;
            int i = defaultCallConvention >= 2 ? 5 : 0;
            while (i < 6) {
                boolean needThisApply = false;
                SwitchState aswitch = null;
                String mname = null;
                Type[] applyArgs = null;
                for (int j = 0; j < numApplyMethods; j++) {
                    LambdaExp source = (LambdaExp) lexp.applyMethods.elementAt(j);
                    Method[] primMethods = source.primMethods;
                    int numMethods = primMethods.length;
                    boolean varArgs = source.max_args < 0 || source.max_args >= source.min_args + numMethods;
                    boolean skipThisProc = false;
                    if (i < 5) {
                        methodIndex = i - source.min_args;
                        if (methodIndex < 0 || methodIndex >= numMethods || (methodIndex == numMethods - 1 && varArgs)) {
                            skipThisProc = true;
                        }
                        numMethods = 1;
                        varArgs = false;
                    } else {
                        int methodIndex2 = 5 - source.min_args;
                        if (methodIndex2 > 0 && numMethods <= methodIndex2 && !varArgs) {
                            skipThisProc = true;
                        }
                        methodIndex = numMethods - 1;
                    }
                    if (!skipThisProc) {
                        if (!needThisApply) {
                            if (i < 5) {
                                mname = "apply" + i;
                                applyArgs = new Type[i + 1];
                                for (int k = i; k > 0; k--) {
                                    applyArgs[k] = typeObject;
                                }
                            } else {
                                mname = "applyN";
                                applyArgs = new Type[2];
                                applyArgs[1] = objArrayType;
                            }
                            applyArgs[0] = procType;
                            this.method = this.curClass.addMethod(mname, applyArgs, defaultCallConvention >= 2 ? Type.voidType : Type.objectType, 1);
                            code = this.method.startCode();
                            code.emitLoad(code.getArg(1));
                            code.emitGetField(procType.getField("selector"));
                            aswitch = code.startSwitch();
                            needThisApply = true;
                        }
                        aswitch.addCase(source.getSelectorValue(this), code);
                        SourceLocator saveLoc1 = this.messages.swapSourceLocator(source);
                        int line = source.getLineNumber();
                        if (line > 0) {
                            code.putLineNumber(source.getFileName(), line);
                        }
                        Method primMethod = primMethods[methodIndex];
                        Type[] primArgTypes = primMethod.getParameterTypes();
                        int singleArgs = source.min_args + methodIndex;
                        Variable counter = null;
                        int pendingIfEnds = 0;
                        if (i > 4 && numMethods > 1) {
                            counter = code.addLocal(Type.intType);
                            code.emitLoad(code.getArg(2));
                            code.emitArrayLength();
                            if (source.min_args != 0) {
                                code.emitPushInt(source.min_args);
                                code.emitSub(Type.intType);
                            }
                            code.emitStore(counter);
                        }
                        int needsThis = primMethod.getStaticFlag() ? 0 : 1;
                        int explicitFrameArg = (varArgs ? 1 : 0) + singleArgs < primArgTypes.length ? 1 : 0;
                        if (needsThis + explicitFrameArg > 0) {
                            code.emitPushThis();
                            if (this.curClass == this.moduleClass && this.mainClass != this.moduleClass) {
                                code.emitGetField(this.moduleInstanceMainField);
                            }
                        }
                        Declaration var = source.firstDecl();
                        if (var != null && var.isThisParameter()) {
                            var = var.nextDecl();
                        }
                        for (int k2 = 0; k2 < singleArgs; k2++) {
                            if (counter != null && k2 >= source.min_args) {
                                code.emitLoad(counter);
                                code.emitIfIntLEqZero();
                                code.emitInvoke(primMethods[k2 - source.min_args]);
                                code.emitElse();
                                pendingIfEnds++;
                                code.emitInc(counter, (short) -1);
                            }
                            Variable pvar = null;
                            if (i <= 4) {
                                pvar = code.getArg(k2 + 2);
                                code.emitLoad(pvar);
                            } else {
                                code.emitLoad(code.getArg(2));
                                code.emitPushInt(k2);
                                code.emitArrayLoad(Type.objectType);
                            }
                            Type ptype = var.getType();
                            if (ptype != Type.objectType) {
                                SourceLocator saveLoc2 = this.messages.swapSourceLocator(var);
                                CheckedTarget.emitCheckedCoerce(this, source, k2 + 1, ptype, pvar);
                                this.messages.swapSourceLocator(saveLoc2);
                            }
                            var = var.nextDecl();
                        }
                        if (varArgs) {
                            Type lastArgType = primArgTypes[explicitFrameArg + singleArgs];
                            if (lastArgType instanceof ArrayType) {
                                varArgsToArray(source, singleArgs, counter, lastArgType, null);
                            } else if ("gnu.lists.LList".equals(lastArgType.getName())) {
                                code.emitLoad(code.getArg(2));
                                code.emitPushInt(singleArgs);
                                code.emitInvokeStatic(makeListMethod);
                            } else if (lastArgType == typeCallContext) {
                                code.emitLoad(code.getArg(2));
                            } else {
                                throw new RuntimeException("unsupported #!rest type:" + lastArgType);
                            }
                        }
                        code.emitInvoke(primMethod);
                        while (true) {
                            pendingIfEnds--;
                            if (pendingIfEnds < 0) {
                                break;
                            }
                            code.emitFi();
                        }
                        if (defaultCallConvention < 2) {
                            Target.pushObject.compileFromStack(this, source.getReturnType());
                        }
                        this.messages.swapSourceLocator(saveLoc1);
                        code.emitReturn();
                    }
                }
                if (needThisApply) {
                    aswitch.addDefault(code);
                    if (defaultCallConvention >= 2) {
                        Method errMethod = typeModuleMethod.getDeclaredMethod("applyError", 0);
                        code.emitInvokeStatic(errMethod);
                    } else {
                        int nargs = i > 4 ? 2 : i + 1;
                        int nargs2 = nargs + 1;
                        for (int k3 = 0; k3 < nargs2; k3++) {
                            code.emitLoad(code.getArg(k3));
                        }
                        code.emitInvokeSpecial(typeModuleBody.getDeclaredMethod(mname, applyArgs));
                    }
                    code.emitReturn();
                    aswitch.finish(code);
                }
                i++;
            }
            this.method = save_method;
            this.curClass = save_class;
        }
    }

    private void varArgsToArray(LambdaExp source, int singleArgs, Variable counter, Type lastArgType, Variable ctxVar) {
        CodeAttr code = getCode();
        Type elType = ((ArrayType) lastArgType).getComponentType();
        boolean mustConvert = !"java.lang.Object".equals(elType.getName());
        if (ctxVar != null && !mustConvert) {
            code.emitLoad(ctxVar);
            code.emitPushInt(singleArgs);
            code.emitInvokeVirtual(typeCallContext.getDeclaredMethod("getRestArgsArray", 1));
        } else if (singleArgs == 0 && !mustConvert) {
            code.emitLoad(code.getArg(2));
        } else {
            code.pushScope();
            if (counter == null) {
                counter = code.addLocal(Type.intType);
                if (ctxVar != null) {
                    code.emitLoad(ctxVar);
                    code.emitInvoke(typeCallContext.getDeclaredMethod("getArgCount", 0));
                } else {
                    code.emitLoad(code.getArg(2));
                    code.emitArrayLength();
                }
                if (singleArgs != 0) {
                    code.emitPushInt(singleArgs);
                    code.emitSub(Type.intType);
                }
                code.emitStore(counter);
            }
            code.emitLoad(counter);
            code.emitNewArray(elType.getImplementationType());
            Label testLabel = new Label(code);
            Label loopTopLabel = new Label(code);
            loopTopLabel.setTypes(code);
            code.emitGoto(testLabel);
            loopTopLabel.define(code);
            code.emitDup(1);
            code.emitLoad(counter);
            if (ctxVar != null) {
                code.emitLoad(ctxVar);
            } else {
                code.emitLoad(code.getArg(2));
            }
            code.emitLoad(counter);
            if (singleArgs != 0) {
                code.emitPushInt(singleArgs);
                code.emitAdd(Type.intType);
            }
            if (ctxVar != null) {
                code.emitInvokeVirtual(typeCallContext.getDeclaredMethod("getArgAsObject", 1));
            } else {
                code.emitArrayLoad(Type.objectType);
            }
            if (mustConvert) {
                CheckedTarget.emitCheckedCoerce(this, source, source.getName(), 0, elType, null);
            }
            code.emitArrayStore(elType);
            testLabel.define(code);
            code.emitInc(counter, (short) -1);
            code.emitLoad(counter);
            code.emitGotoIfIntGeZero(loopTopLabel);
            code.popScope();
        }
    }

    private Method startClassInit() {
        this.method = this.curClass.addMethod("<clinit>", apply0args, Type.voidType, 9);
        CodeAttr code = this.method.startCode();
        if (this.generateMain || generatingApplet() || generatingServlet()) {
            ClassType languageType = (ClassType) Type.make(getLanguage().getClass());
            Method registerMethod = languageType.getDeclaredMethod("registerEnvironment", 0);
            if (registerMethod != null) {
                code.emitInvokeStatic(registerMethod);
            }
        }
        return this.method;
    }

    public void process(int wantedState) {
        Compilation saveCompilation = setSaveCurrent(this);
        try {
            ModuleExp mexp = getModule();
            if (wantedState >= 4 && getState() < 3) {
                setState(3);
                this.language.parse(this, 0);
                this.lexer.close();
                this.lexer = null;
                setState(this.messages.seenErrors() ? 100 : 4);
                if (this.pendingImports != null) {
                    return;
                }
            }
            if (wantedState >= 6 && getState() < 6) {
                addMainClass(mexp);
                this.language.resolve(this);
                setState(this.messages.seenErrors() ? 100 : 6);
            }
            if (!this.explicit && !this.immediate && this.minfo.checkCurrent(ModuleManager.getInstance(), System.currentTimeMillis())) {
                this.minfo.cleanupAfterCompilation();
                setState(14);
            }
            if (wantedState >= 8 && getState() < 8) {
                walkModule(mexp);
                setState(this.messages.seenErrors() ? 100 : 8);
            }
            if (wantedState >= 10 && getState() < 10) {
                this.litTable = new LitTable(this);
                mexp.setCanRead(true);
                FindCapturedVars.findCapturedVars(mexp, this);
                mexp.allocFields(this);
                mexp.allocChildMethods(this);
                setState(this.messages.seenErrors() ? 100 : 10);
            }
            if (wantedState >= 12 && getState() < 12) {
                if (this.immediate) {
                    ClassLoader parentLoader = ObjectType.getContextClassLoader();
                    this.loader = new ArrayClassLoader(parentLoader);
                }
                generateBytecode();
                setState(this.messages.seenErrors() ? 100 : 12);
            }
            if (wantedState >= 14 && getState() < 14) {
                ModuleManager manager = ModuleManager.getInstance();
                outputClass(manager.getCompilationDirectory());
                setState(14);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            error('f', "caught " + ex);
            setState(100);
        } catch (SyntaxException ex2) {
            setState(100);
            if (ex2.getMessages() != getMessages()) {
                throw new RuntimeException("confussing syntax error: " + ex2);
            }
        } finally {
            restoreCurrent(saveCompilation);
        }
    }

    void generateBytecode() {
        Type[] arg_types;
        String mainPrefix;
        ModuleExp module = getModule();
        if (debugPrintFinalExpr) {
            OutPort dout = OutPort.errDefault();
            dout.println("[Compiling final " + module.getName() + " to " + this.mainClass.getName() + ":");
            module.print(dout);
            dout.println(']');
            dout.flush();
        }
        ClassType neededSuper = getModuleType();
        if (this.mainClass.getSuperclass().isSubtype(neededSuper)) {
            this.moduleClass = this.mainClass;
        } else {
            this.moduleClass = new ClassType(generateClassName("frame"));
            this.moduleClass.setSuper(neededSuper);
            addClass(this.moduleClass);
            generateConstructor(this.moduleClass, null);
        }
        this.curClass = module.type;
        LambdaExp saveLambda = this.curLambda;
        this.curLambda = module;
        if (!module.isHandlingTailCalls()) {
            if (module.min_args == module.max_args && module.min_args <= 4) {
                int arg_count = module.min_args;
                arg_types = new Type[arg_count];
                int i = arg_count;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    arg_types[i] = typeObject;
                }
            } else {
                arg_types = new Type[]{new ArrayType(typeObject)};
            }
        } else {
            arg_types = new Type[]{typeCallContext};
        }
        Variable heapFrame = module.heapFrame;
        boolean staticModule = module.isStatic();
        Method apply_method = this.curClass.addMethod("run", arg_types, Type.voidType, 17);
        this.method = apply_method;
        this.method.initCode();
        CodeAttr code = getCode();
        this.thisDecl = this.method.getStaticFlag() ? null : module.declareThis(module.type);
        module.closureEnv = module.thisVariable;
        module.heapFrame = module.isStatic() ? null : module.thisVariable;
        module.allocChildClasses(this);
        if (module.isHandlingTailCalls() || usingCPStyle()) {
            this.callContextVar = new Variable("$ctx", typeCallContext);
            module.getVarScope().addVariableAfter(this.thisDecl, this.callContextVar);
            this.callContextVar.setParameter(true);
        }
        int line = module.getLineNumber();
        if (line > 0) {
            code.putLineNumber(module.getFileName(), line);
        }
        module.allocParameters(this);
        module.enterFunction(this);
        if (usingCPStyle()) {
            loadCallContext();
            code.emitGetField(pcCallContextField);
            this.fswitch = code.startSwitch();
            this.fswitch.addCase(0, code);
        }
        module.compileBody(this);
        module.compileEnd(this);
        Label startLiterals = null;
        Label afterLiterals = null;
        Method initMethod = null;
        if (this.curClass == this.mainClass) {
            Method save_method = this.method;
            Variable callContextSave = this.callContextVar;
            this.callContextVar = null;
            initMethod = startClassInit();
            this.clinitMethod = initMethod;
            CodeAttr code2 = getCode();
            startLiterals = new Label(code2);
            afterLiterals = new Label(code2);
            code2.fixupChain(afterLiterals, startLiterals);
            if (staticModule) {
                generateConstructor(module);
                code2.emitNew(this.moduleClass);
                code2.emitDup(this.moduleClass);
                code2.emitInvokeSpecial(this.moduleClass.constructor);
                this.moduleInstanceMainField = this.moduleClass.addField("$instance", this.moduleClass, 25);
                code2.emitPutStatic(this.moduleInstanceMainField);
            }
            while (true) {
                Initializer init = this.clinitChain;
                if (init == null) {
                    break;
                }
                this.clinitChain = null;
                dumpInitializers(init);
            }
            if (module.staticInitRun()) {
                code2.emitGetStatic(this.moduleInstanceMainField);
                code2.emitInvoke(typeModuleBody.getDeclaredMethod("run", 0));
            }
            code2.emitReturn();
            if (this.moduleClass != this.mainClass && !staticModule && !this.generateMain && !this.immediate) {
                this.method = this.curClass.addMethod("run", 1, Type.typeArray0, Type.voidType);
                CodeAttr code3 = this.method.startCode();
                Variable ctxVar = code3.addLocal(typeCallContext);
                Variable saveVar = code3.addLocal(typeConsumer);
                Variable exceptionVar = code3.addLocal(Type.javalangThrowableType);
                code3.emitInvokeStatic(getCallContextInstanceMethod);
                code3.emitStore(ctxVar);
                Field consumerFld = typeCallContext.getDeclaredField("consumer");
                code3.emitLoad(ctxVar);
                code3.emitGetField(consumerFld);
                code3.emitStore(saveVar);
                code3.emitLoad(ctxVar);
                code3.emitGetStatic(ClassType.make("gnu.lists.VoidConsumer").getDeclaredField("instance"));
                code3.emitPutField(consumerFld);
                code3.emitTryStart(false, Type.voidType);
                code3.emitPushThis();
                code3.emitLoad(ctxVar);
                code3.emitInvokeVirtual(save_method);
                code3.emitPushNull();
                code3.emitStore(exceptionVar);
                code3.emitTryEnd();
                code3.emitCatchStart(exceptionVar);
                code3.emitCatchEnd();
                code3.emitTryCatchEnd();
                code3.emitLoad(ctxVar);
                code3.emitLoad(exceptionVar);
                code3.emitLoad(saveVar);
                code3.emitInvokeStatic(typeModuleBody.getDeclaredMethod("runCleanup", 3));
                code3.emitReturn();
            }
            this.method = save_method;
            this.callContextVar = callContextSave;
        }
        module.generateApplyMethods(this);
        this.curLambda = saveLambda;
        module.heapFrame = heapFrame;
        if (usingCPStyle()) {
            this.fswitch.finish(getCode());
        }
        if (startLiterals != null || this.callContextVar != null) {
            this.method = initMethod;
            CodeAttr code4 = getCode();
            Label endLiterals = new Label(code4);
            code4.fixupChain(startLiterals, endLiterals);
            if (this.callContextVarForInit != null) {
                code4.emitInvokeStatic(getCallContextInstanceMethod);
                code4.emitStore(this.callContextVarForInit);
            }
            try {
                if (this.immediate) {
                    code4.emitPushInt(registerForImmediateLiterals(this));
                    code4.emitInvokeStatic(ClassType.make("gnu.expr.Compilation").getDeclaredMethod("setupLiterals", 1));
                } else {
                    this.litTable.emit();
                }
            } catch (Throwable ex) {
                error('e', "Literals: Internal error:" + ex);
            }
            code4.fixupChain(endLiterals, afterLiterals);
        }
        if (this.generateMain && this.curClass == this.mainClass) {
            Type[] args = {new ArrayType(javaStringType)};
            this.method = this.curClass.addMethod("main", 9, args, Type.voidType);
            CodeAttr code5 = this.method.startCode();
            if (Shell.defaultFormatName != null) {
                code5.emitPushString(Shell.defaultFormatName);
                code5.emitInvokeStatic(ClassType.make("kawa.Shell").getDeclaredMethod("setDefaultFormat", 1));
            }
            code5.emitLoad(code5.getArg(0));
            code5.emitInvokeStatic(ClassType.make("gnu.expr.ApplicationMainSupport").getDeclaredMethod("processArgs", 1));
            if (this.moduleInstanceMainField != null) {
                code5.emitGetStatic(this.moduleInstanceMainField);
            } else {
                code5.emitNew(this.curClass);
                code5.emitDup(this.curClass);
                code5.emitInvokeSpecial(this.curClass.constructor);
            }
            code5.emitInvokeVirtual(typeModuleBody.getDeclaredMethod("runAsMain", 0));
            code5.emitReturn();
        }
        if (this.minfo == null) {
            return;
        }
        String uri = this.minfo.getNamespaceUri();
        if (uri != null) {
            ModuleManager manager = ModuleManager.getInstance();
            String mainPrefix2 = this.mainClass.getName();
            int dot = mainPrefix2.lastIndexOf(46);
            if (dot < 0) {
                mainPrefix = "";
            } else {
                String mainPackage = mainPrefix2.substring(0, dot);
                try {
                    manager.loadPackageInfo(mainPackage);
                } catch (ClassNotFoundException e) {
                } catch (Throwable ex2) {
                    error('e', "error loading map for " + mainPackage + " - " + ex2);
                }
                mainPrefix = mainPrefix2.substring(0, dot + 1);
            }
            ClassType mapClass = new ClassType(mainPrefix + ModuleSet.MODULES_MAP);
            ClassType typeModuleSet = ClassType.make("gnu.expr.ModuleSet");
            mapClass.setSuper(typeModuleSet);
            registerClass(mapClass);
            this.method = mapClass.addMethod("<init>", 1, apply0args, Type.voidType);
            Method superConstructor = typeModuleSet.addMethod("<init>", 1, apply0args, Type.voidType);
            CodeAttr code6 = this.method.startCode();
            code6.emitPushThis();
            code6.emitInvokeSpecial(superConstructor);
            code6.emitReturn();
            ClassType typeModuleManager = ClassType.make("gnu.expr.ModuleManager");
            Type[] margs = {typeModuleManager};
            this.method = mapClass.addMethod("register", margs, Type.voidType, 1);
            CodeAttr code7 = this.method.startCode();
            Method reg = typeModuleManager.getDeclaredMethod("register", 3);
            int i2 = manager.numModules;
            while (true) {
                i2--;
                if (i2 >= 0) {
                    ModuleInfo mi = manager.modules[i2];
                    String miClassName = mi.getClassName();
                    if (miClassName != null && miClassName.startsWith(mainPrefix)) {
                        String moduleSource = mi.sourcePath;
                        String moduleUri = mi.getNamespaceUri();
                        code7.emitLoad(code7.getArg(1));
                        compileConstant(miClassName);
                        if (!Path.valueOf(moduleSource).isAbsolute()) {
                            try {
                                char sep = File.separatorChar;
                                String path = Path.toURL(manager.getCompilationDirectory() + mainPrefix.replace('.', sep)).toString();
                                int plen = path.length();
                                if (plen > 0 && path.charAt(plen - 1) != sep) {
                                    path = path + sep;
                                }
                                moduleSource = Path.relativize(mi.getSourceAbsPathname(), path);
                            } catch (Throwable ex3) {
                                throw new WrappedException("exception while fixing up '" + moduleSource + '\'', ex3);
                            }
                        }
                        compileConstant(moduleSource);
                        compileConstant(moduleUri);
                        code7.emitInvokeVirtual(reg);
                    }
                } else {
                    code7.emitReturn();
                    return;
                }
            }
        }
    }

    public Field allocLocalField(Type type, String name) {
        if (name == null) {
            StringBuilder append = new StringBuilder().append("tmp_");
            int i = this.localFieldIndex + 1;
            this.localFieldIndex = i;
            name = append.append(i).toString();
        }
        Field field = this.curClass.addField(name, type, 0);
        return field;
    }

    public final void loadCallContext() {
        CodeAttr code = getCode();
        if (this.callContextVar != null && !this.callContextVar.dead()) {
            code.emitLoad(this.callContextVar);
        } else if (this.method == this.clinitMethod) {
            this.callContextVar = new Variable("$ctx", typeCallContext);
            this.callContextVar.reserveLocal(code.getMaxLocals(), code);
            code.emitLoad(this.callContextVar);
            this.callContextVarForInit = this.callContextVar;
        } else {
            code.emitInvokeStatic(getCallContextInstanceMethod);
            code.emitDup();
            this.callContextVar = new Variable("$ctx", typeCallContext);
            code.getCurrentScope().addVariable(code, this.callContextVar);
            code.emitStore(this.callContextVar);
        }
    }

    public void freeLocalField(Field field) {
    }

    public Expression parse(Object input) {
        throw new Error("unimeplemented parse");
    }

    public Language getLanguage() {
        return this.language;
    }

    public LambdaExp currentLambda() {
        return this.current_scope.currentLambda();
    }

    public final ModuleExp getModule() {
        return this.mainLambda;
    }

    public void setModule(ModuleExp mexp) {
        this.mainLambda = mexp;
    }

    public boolean isStatic() {
        return this.mainLambda.isStatic();
    }

    public ModuleExp currentModule() {
        return this.current_scope.currentModule();
    }

    public void mustCompileHere() {
        if (!this.mustCompile && !ModuleExp.compilerAvailable) {
            error('w', "this expression claimed that it must be compiled, but compiler is unavailable");
        } else {
            this.mustCompile = true;
        }
    }

    public ScopeExp currentScope() {
        return this.current_scope;
    }

    public void setCurrentScope(ScopeExp scope) {
        int scope_nesting = ScopeExp.nesting(scope);
        int current_nesting = ScopeExp.nesting(this.current_scope);
        while (current_nesting > scope_nesting) {
            pop(this.current_scope);
            current_nesting--;
        }
        ScopeExp sc = scope;
        while (scope_nesting > current_nesting) {
            sc = sc.outer;
            scope_nesting--;
        }
        while (sc != this.current_scope) {
            pop(this.current_scope);
            sc = sc.outer;
        }
        pushChain(scope, sc);
    }

    void pushChain(ScopeExp scope, ScopeExp limit) {
        if (scope != limit) {
            pushChain(scope.outer, limit);
            pushScope(scope);
            this.lexical.push(scope);
        }
    }

    public ModuleExp pushNewModule(Lexer lexer) {
        this.lexer = lexer;
        return pushNewModule(lexer.getName());
    }

    public ModuleExp pushNewModule(String filename) {
        ModuleExp module = new ModuleExp();
        if (filename != null) {
            module.setFile(filename);
        }
        if (generatingApplet() || generatingServlet()) {
            module.setFlag(131072);
        }
        if (this.immediate) {
            module.setFlag(1048576);
            new ModuleInfo().setCompilation(this);
        }
        this.mainLambda = module;
        push(module);
        return module;
    }

    public void push(ScopeExp scope) {
        pushScope(scope);
        this.lexical.push(scope);
    }

    public final void pushScope(ScopeExp scope) {
        if (!this.mustCompile && (scope.mustCompile() || (ModuleExp.compilerAvailable && (scope instanceof LambdaExp) && !(scope instanceof ModuleExp)))) {
            mustCompileHere();
        }
        scope.outer = this.current_scope;
        this.current_scope = scope;
    }

    public void pop(ScopeExp scope) {
        this.lexical.pop(scope);
        this.current_scope = scope.outer;
    }

    public final void pop() {
        pop(this.current_scope);
    }

    public void push(Declaration decl) {
        this.lexical.push(decl);
    }

    public Declaration lookup(Object name, int namespace) {
        return this.lexical.lookup(name, namespace);
    }

    public void usedClass(Type type) {
        while (type instanceof ArrayType) {
            type = ((ArrayType) type).getComponentType();
        }
        if (this.immediate && (type instanceof ClassType)) {
            this.loader.addClass((ClassType) type);
        }
    }

    public SourceMessages getMessages() {
        return this.messages;
    }

    public void setMessages(SourceMessages messages) {
        this.messages = messages;
    }

    public void error(char severity, String message, SourceLocator location) {
        String file = location.getFileName();
        int line = location.getLineNumber();
        int column = location.getColumnNumber();
        if (file == null || line <= 0) {
            file = getFileName();
            line = getLineNumber();
            column = getColumnNumber();
        }
        if (severity == 'w' && warnAsError()) {
            severity = 'e';
        }
        this.messages.error(severity, file, line, column, message);
    }

    public void error(char severity, String message) {
        if (severity == 'w' && warnAsError()) {
            severity = 'e';
        }
        this.messages.error(severity, this, message);
    }

    public void error(char severity, Declaration decl, String msg1, String msg2) {
        error(severity, msg1 + decl.getName() + msg2, (String) null, decl);
    }

    public void error(char severity, String message, String code, Declaration decl) {
        if (severity == 'w' && warnAsError()) {
            severity = 'e';
        }
        String filename = getFileName();
        int line = getLineNumber();
        int column = getColumnNumber();
        int decl_line = decl.getLineNumber();
        if (decl_line > 0) {
            filename = decl.getFileName();
            line = decl_line;
            column = decl.getColumnNumber();
        }
        this.messages.error(severity, filename, line, column, message, code);
    }

    public Expression syntaxError(String message) {
        error('e', message);
        return new ErrorExp(message);
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public final int getLineNumber() {
        return this.messages.getLineNumber();
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public final int getColumnNumber() {
        return this.messages.getColumnNumber();
    }

    @Override // gnu.text.SourceLocator
    public final String getFileName() {
        return this.messages.getFileName();
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public String getPublicId() {
        return this.messages.getPublicId();
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public String getSystemId() {
        return this.messages.getSystemId();
    }

    @Override // gnu.text.SourceLocator
    public boolean isStableSourceLocation() {
        return false;
    }

    public void setFile(String filename) {
        this.messages.setFile(filename);
    }

    public void setLine(int line) {
        this.messages.setLine(line);
    }

    public void setColumn(int column) {
        this.messages.setColumn(column);
    }

    public final void setLine(Expression position) {
        this.messages.setLocation(position);
    }

    public void setLine(Object location) {
        if (location instanceof SourceLocator) {
            this.messages.setLocation((SourceLocator) location);
        }
    }

    public final void setLocation(SourceLocator position) {
        this.messages.setLocation(position);
    }

    public void setLine(String filename, int line, int column) {
        this.messages.setLine(filename, line, column);
    }

    public void letStart() {
        pushScope(new LetExp(null));
    }

    public Declaration letVariable(Object name, Type type, Expression init) {
        LetExp let = (LetExp) this.current_scope;
        Declaration decl = let.addDeclaration(name, type);
        decl.noteValue(init);
        return decl;
    }

    public void letEnter() {
        LetExp let = (LetExp) this.current_scope;
        int ndecls = let.countDecls();
        Expression[] inits = new Expression[ndecls];
        Declaration decl = let.firstDecl();
        int i = 0;
        while (decl != null) {
            inits[i] = decl.getValue();
            decl = decl.nextDecl();
            i++;
        }
        let.inits = inits;
        this.lexical.push(let);
    }

    public LetExp letDone(Expression body) {
        LetExp let = (LetExp) this.current_scope;
        let.body = body;
        pop(let);
        return let;
    }

    private void checkLoop() {
        if (((LambdaExp) this.current_scope).getName() != "%do%loop") {
            throw new Error("internal error - bad loop state");
        }
    }

    public void loopStart() {
        LambdaExp loopLambda = new LambdaExp();
        Expression[] inits = {loopLambda};
        LetExp let = new LetExp(inits);
        Declaration fdecl = let.addDeclaration("%do%loop");
        fdecl.noteValue(loopLambda);
        loopLambda.setName("%do%loop");
        let.outer = this.current_scope;
        loopLambda.outer = let;
        this.current_scope = loopLambda;
    }

    public Declaration loopVariable(Object name, Type type, Expression init) {
        checkLoop();
        LambdaExp loopLambda = (LambdaExp) this.current_scope;
        Declaration decl = loopLambda.addDeclaration(name, type);
        if (this.exprStack == null) {
            this.exprStack = new Stack<>();
        }
        this.exprStack.push(init);
        loopLambda.min_args++;
        return decl;
    }

    public void loopEnter() {
        checkLoop();
        LambdaExp loopLambda = (LambdaExp) this.current_scope;
        int ninits = loopLambda.min_args;
        loopLambda.max_args = ninits;
        Expression[] inits = new Expression[ninits];
        int i = ninits;
        while (true) {
            i--;
            if (i >= 0) {
                inits[i] = this.exprStack.pop();
            } else {
                LetExp let = (LetExp) loopLambda.outer;
                Declaration fdecl = let.firstDecl();
                let.setBody(new ApplyExp((Expression) new ReferenceExp(fdecl), inits));
                this.lexical.push(loopLambda);
                return;
            }
        }
    }

    public void loopCond(Expression cond) {
        checkLoop();
        this.exprStack.push(cond);
    }

    public void loopBody(Expression body) {
        LambdaExp loopLambda = (LambdaExp) this.current_scope;
        loopLambda.body = body;
    }

    public Expression loopRepeat(Expression[] exps) {
        LambdaExp loopLambda = (LambdaExp) this.current_scope;
        ScopeExp let = loopLambda.outer;
        Declaration fdecl = let.firstDecl();
        Expression cond = this.exprStack.pop();
        Expression recurse = new ApplyExp((Expression) new ReferenceExp(fdecl), exps);
        loopLambda.body = new IfExp(cond, new BeginExp(loopLambda.body, recurse), QuoteExp.voidExp);
        this.lexical.pop(loopLambda);
        this.current_scope = let.outer;
        return let;
    }

    public Expression loopRepeat() {
        return loopRepeat(Expression.noExpressions);
    }

    public Expression loopRepeat(Expression exp) {
        Expression[] args = {exp};
        return loopRepeat(args);
    }

    public static ApplyExp makeCoercion(Expression value, Expression type) {
        Expression[] exps = {type, value};
        QuoteExp c = new QuoteExp(Convert.getInstance());
        return new ApplyExp((Expression) c, exps);
    }

    public static Expression makeCoercion(Expression value, Type type) {
        return makeCoercion(value, new QuoteExp(type));
    }

    public void loadClassRef(ObjectType clas) {
        CodeAttr code = getCode();
        if (this.curClass.getClassfileVersion() >= 3211264) {
            code.emitPushClass(clas);
        } else if (clas == this.mainClass && this.mainLambda.isStatic() && this.moduleInstanceMainField != null) {
            code.emitGetStatic(this.moduleInstanceMainField);
            code.emitInvokeVirtual(Type.objectType.getDeclaredMethod("getClass", 0));
        } else {
            String name = clas instanceof ClassType ? clas.getName() : clas.getInternalName().replace('/', '.');
            code.emitPushString(name);
            code.emitInvokeStatic(getForNameHelper());
        }
    }

    public Method getForNameHelper() {
        if (this.forNameHelper == null) {
            Method save_method = this.method;
            this.method = this.curClass.addMethod("class$", 9, string1Arg, typeClass);
            this.forNameHelper = this.method;
            CodeAttr code = this.method.startCode();
            code.emitLoad(code.getArg(0));
            code.emitPushInt(0);
            code.emitPushString(this.mainClass.getName());
            code.emitInvokeStatic(typeClass.getDeclaredMethod("forName", 1));
            code.emitInvokeVirtual(typeClass.getDeclaredMethod("getClassLoader", 0));
            code.emitInvokeStatic(typeClass.getDeclaredMethod("forName", 3));
            code.emitReturn();
            this.method = save_method;
        }
        return this.forNameHelper;
    }

    public Object resolve(Object name, boolean function) {
        Symbol symbol;
        Environment env = Environment.getCurrent();
        if (name instanceof String) {
            symbol = env.defaultNamespace().lookup((String) name);
        } else {
            symbol = (Symbol) name;
        }
        if (symbol == null) {
            return null;
        }
        if (function && getLanguage().hasSeparateFunctionNamespace()) {
            return env.getFunction(symbol, null);
        }
        return env.get(symbol, (Object) null);
    }

    public static void setupLiterals(int key) {
        Compilation comp = findForImmediateLiterals(key);
        try {
            Class clas = comp.loader.loadClass(comp.mainClass.getName());
            for (Literal init = comp.litTable.literalsChain; init != null; init = init.next) {
                clas.getDeclaredField(init.field.getName()).set(null, init.value);
            }
            comp.litTable = null;
        } catch (Throwable ex) {
            throw new WrappedException("internal error", ex);
        }
    }

    public static synchronized int registerForImmediateLiterals(Compilation comp) {
        int i;
        synchronized (Compilation.class) {
            i = 0;
            for (Compilation c = chainUninitialized; c != null; c = c.nextUninitialized) {
                if (i <= c.keyUninitialized) {
                    i = c.keyUninitialized + 1;
                }
            }
            comp.keyUninitialized = i;
            comp.nextUninitialized = chainUninitialized;
            chainUninitialized = comp;
        }
        return i;
    }

    public static synchronized Compilation findForImmediateLiterals(int key) {
        Compilation comp;
        Compilation next;
        synchronized (Compilation.class) {
            Compilation prev = null;
            comp = chainUninitialized;
            while (true) {
                next = comp.nextUninitialized;
                if (comp.keyUninitialized == key) {
                    break;
                }
                prev = comp;
                comp = next;
            }
            if (prev == null) {
                chainUninitialized = next;
            } else {
                prev.nextUninitialized = next;
            }
            comp.nextUninitialized = null;
        }
        return comp;
    }

    public static Compilation getCurrent() {
        return current.get();
    }

    public static void setCurrent(Compilation comp) {
        current.set(comp);
    }

    public static Compilation setSaveCurrent(Compilation comp) {
        Compilation save = current.get();
        current.set(comp);
        return save;
    }

    public static void restoreCurrent(Compilation saved) {
        current.set(saved);
    }

    public String toString() {
        return "<compilation " + this.mainLambda + ">";
    }
}
