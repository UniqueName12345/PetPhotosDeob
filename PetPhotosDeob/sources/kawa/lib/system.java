package kawa.lib;

import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.expr.ApplicationMainSupport;
import gnu.expr.Compilation;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.IsEqual;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.InputStream;
import java.util.StringTokenizer;
import kawa.lang.CompileFile;
import kawa.lang.NamedException;
import kawa.standard.Scheme;

/* compiled from: system.scm */
/* loaded from: classes.dex */
public class system extends ModuleBody {

    /* renamed from: catch  reason: not valid java name */
    public static final ModuleMethod f9catch;
    public static Procedure command$Mnparse;
    public static final ModuleMethod compile$Mnfile;
    public static final ModuleMethod convert$Mnlist$Mnto$Mnstring$Mnarray;
    public static final ModuleMethod convert$Mnvector$Mnto$Mnstring$Mnarray;
    public static final ModuleMethod make$Mnprocess;
    public static final ModuleMethod open$Mninput$Mnpipe;
    public static final ModuleMethod process$Mncommand$Mnline$Mnassignments;
    public static final ModuleMethod system;
    public static final ModuleMethod tokenize$Mnstring$Mnto$Mnstring$Mnarray;
    public static final ModuleMethod tokenize$Mnstring$Mnusing$Mnshell;
    static final SimpleSymbol Lit11 = (SimpleSymbol) new SimpleSymbol("process-command-line-assignments").readResolve();
    static final SimpleSymbol Lit10 = (SimpleSymbol) new SimpleSymbol("catch").readResolve();
    static final SimpleSymbol Lit9 = (SimpleSymbol) new SimpleSymbol("compile-file").readResolve();
    static final SimpleSymbol Lit8 = (SimpleSymbol) new SimpleSymbol("tokenize-string-using-shell").readResolve();
    static final SimpleSymbol Lit7 = (SimpleSymbol) new SimpleSymbol("tokenize-string-to-string-array").readResolve();
    static final SimpleSymbol Lit6 = (SimpleSymbol) new SimpleSymbol("convert-list-to-string-array").readResolve();
    static final SimpleSymbol Lit5 = (SimpleSymbol) new SimpleSymbol("convert-vector-to-string-array").readResolve();
    static final SimpleSymbol Lit4 = (SimpleSymbol) new SimpleSymbol("system").readResolve();
    static final SimpleSymbol Lit3 = (SimpleSymbol) new SimpleSymbol("open-input-pipe").readResolve();
    static final SimpleSymbol Lit2 = (SimpleSymbol) new SimpleSymbol("make-process").readResolve();
    static final IntNum Lit1 = IntNum.make(1);
    static final IntNum Lit0 = IntNum.make(0);
    public static final system $instance = new system();

    static {
        system systemVar = $instance;
        make$Mnprocess = new ModuleMethod(systemVar, 1, Lit2, 8194);
        open$Mninput$Mnpipe = new ModuleMethod(systemVar, 2, Lit3, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        system = new ModuleMethod(systemVar, 3, Lit4, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        convert$Mnvector$Mnto$Mnstring$Mnarray = new ModuleMethod(systemVar, 4, Lit5, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        convert$Mnlist$Mnto$Mnstring$Mnarray = new ModuleMethod(systemVar, 5, Lit6, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tokenize$Mnstring$Mnto$Mnstring$Mnarray = new ModuleMethod(systemVar, 6, Lit7, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tokenize$Mnstring$Mnusing$Mnshell = new ModuleMethod(systemVar, 7, Lit8, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        compile$Mnfile = new ModuleMethod(systemVar, 8, Lit9, 8194);
        f9catch = new ModuleMethod(systemVar, 9, Lit10, 12291);
        process$Mncommand$Mnline$Mnassignments = new ModuleMethod(systemVar, 10, Lit11, 0);
        $instance.run();
    }

    public system() {
        ModuleInfo.register(this);
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
        command$Mnparse = IsEqual.apply(System.getProperty("file.separator"), "/") ? tokenize$Mnstring$Mnusing$Mnshell : tokenize$Mnstring$Mnto$Mnstring$Mnarray;
    }

    public static Process makeProcess(Object args, Object env) {
        Object arargs;
        if (vectors.isVector(args)) {
            arargs = convertVectorToStringArray(args);
        } else if (lists.isList(args)) {
            arargs = convertListToStringArray(args);
        } else if (strings.isString(args)) {
            arargs = command$Mnparse.apply1(args);
        } else {
            arargs = args instanceof String[] ? args : misc.error$V("invalid arguments to make-process", new Object[0]);
        }
        Runtime runtime = Runtime.getRuntime();
        try {
            try {
                return runtime.exec((String[]) arargs, (String[]) env);
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Runtime.exec(java.lang.String[],java.lang.String[])", 3, env);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "java.lang.Runtime.exec(java.lang.String[],java.lang.String[])", 2, arargs);
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 8:
                if (obj instanceof CharSequence) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static InputStream openInputPipe(Object command) {
        return makeProcess(command, null).getInputStream();
    }

    @Override // gnu.expr.ModuleBody
    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 3:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 4:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 6:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 7:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static int system(Object command) {
        return makeProcess(command, null).waitFor();
    }

    public static Object convertVectorToStringArray(Object vec) {
        try {
            int count = vectors.vectorLength((FVector) vec);
            String[] arr = new String[count];
            for (Object obj = Lit0; Scheme.numEqu.apply2(obj, Integer.valueOf(count)) == Boolean.FALSE; obj = AddOp.$Pl.apply2(obj, Lit1)) {
                int intValue = ((Number) obj).intValue();
                try {
                    try {
                        Object vectorRef = vectors.vectorRef((FVector) vec, ((Number) obj).intValue());
                        arr[intValue] = vectorRef == null ? null : vectorRef.toString();
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "vector-ref", 2, obj);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "vector-ref", 1, vec);
                }
            }
            return arr;
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "vector-length", 1, vec);
        }
    }

    public static Object convertListToStringArray(Object lst) {
        try {
            int count = lists.length((LList) lst);
            String[] arr = new String[count];
            int i = 0;
            Object p = lst;
            while (!lists.isNull(p)) {
                try {
                    Pair pp = (Pair) p;
                    Object car = pp.getCar();
                    arr[i] = car == null ? null : car.toString();
                    p = pp.getCdr();
                    i++;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "pp", -2, p);
                }
            }
            return arr;
        } catch (ClassCastException e2) {
            throw new WrongType(e2, PropertyTypeConstants.PROPERTY_TYPE_LENGTH, 1, lst);
        }
    }

    public static Object tokenizeStringToStringArray(String string) {
        StringTokenizer toks = new StringTokenizer(string);
        LList lList = LList.Empty;
        while (toks.hasMoreTokens()) {
            lList = lists.cons(toks.nextToken(), lList);
        }
        try {
            int count = lists.length(lList);
            String[] arr = new String[count];
            int i = count - 1;
            Object p = lList;
            while (!lists.isNull(p)) {
                try {
                    Pair pp = (Pair) p;
                    Object car = pp.getCar();
                    arr[i] = car == null ? null : car.toString();
                    p = pp.getCdr();
                    i--;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "pp", -2, p);
                }
            }
            return arr;
        } catch (ClassCastException e2) {
            throw new WrongType(e2, PropertyTypeConstants.PROPERTY_TYPE_LENGTH, 1, lList);
        }
    }

    public static String[] tokenizeStringUsingShell(Object string) {
        String[] arr = new String[3];
        arr[0] = "/bin/sh";
        arr[1] = "-c";
        arr[2] = string == null ? null : string.toString();
        return arr;
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 2:
                return openInputPipe(obj);
            case 3:
                return Integer.valueOf(system(obj));
            case 4:
                return convertVectorToStringArray(obj);
            case 5:
                return convertListToStringArray(obj);
            case 6:
                return tokenizeStringToStringArray(obj == null ? null : obj.toString());
            case 7:
                return tokenizeStringUsingShell(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static void compileFile(CharSequence source, String output) {
        SourceMessages messages = new SourceMessages();
        Compilation comp = CompileFile.read(source.toString(), messages);
        comp.explicit = true;
        if (messages.seenErrors()) {
            throw new SyntaxException(messages);
        }
        comp.compileToArchive(comp.getModule(), output);
        if (messages.seenErrors()) {
            throw new SyntaxException(messages);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 1:
                return makeProcess(obj, obj2);
            case 8:
                try {
                    compileFile((CharSequence) obj, obj2 == null ? null : obj2.toString());
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "compile-file", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    /* renamed from: catch  reason: not valid java name */
    public static Object m560catch(Object key, Procedure thunk, Procedure handler) {
        try {
            return thunk.apply0();
        } catch (NamedException ex) {
            return ex.applyHandler(key, handler);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        if (moduleMethod.selector == 9) {
            try {
                try {
                    return m560catch(obj, (Procedure) obj2, (Procedure) obj3);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "catch", 3, obj3);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "catch", 2, obj2);
            }
        }
        return super.apply3(moduleMethod, obj, obj2, obj3);
    }

    @Override // gnu.expr.ModuleBody
    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        if (moduleMethod.selector == 9) {
            callContext.value1 = obj;
            if (obj2 instanceof Procedure) {
                callContext.value2 = obj2;
                if (obj3 instanceof Procedure) {
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                }
                return -786429;
            }
            return -786430;
        }
        return super.match3(moduleMethod, obj, obj2, obj3, callContext);
    }

    public static void processCommandLineAssignments() {
        ApplicationMainSupport.processSetProperties();
    }

    @Override // gnu.expr.ModuleBody
    public Object apply0(ModuleMethod moduleMethod) {
        if (moduleMethod.selector == 10) {
            processCommandLineAssignments();
            return Values.empty;
        }
        return super.apply0(moduleMethod);
    }

    @Override // gnu.expr.ModuleBody
    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        if (moduleMethod.selector == 10) {
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }
        return super.match0(moduleMethod, callContext);
    }
}
