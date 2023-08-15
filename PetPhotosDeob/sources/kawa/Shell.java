package kawa;

import gnu.bytecode.ZipLoader;
import gnu.expr.Compilation;
import gnu.expr.CompiledModule;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.lists.AbstractFormat;
import gnu.lists.Consumer;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import gnu.text.FilePath;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;

/* loaded from: classes.dex */
public class Shell {
    public static Object[] defaultFormatInfo;
    public static Method defaultFormatMethod;
    public static String defaultFormatName;
    public static ThreadLocal currentLoadPath = new ThreadLocal();
    private static Class[] noClasses = new Class[0];
    private static Class[] boolClasses = {Boolean.TYPE};
    private static Class[] xmlPrinterClasses = {OutPort.class, Object.class};
    private static Class[] httpPrinterClasses = {OutPort.class};
    private static Object portArg = "(port)";
    static Object[][] formats = {new Object[]{"scheme", "gnu.kawa.functions.DisplayFormat", "getSchemeFormat", boolClasses, Boolean.FALSE}, new Object[]{"readable-scheme", "gnu.kawa.functions.DisplayFormat", "getSchemeFormat", boolClasses, Boolean.TRUE}, new Object[]{"elisp", "gnu.kawa.functions.DisplayFormat", "getEmacsLispFormat", boolClasses, Boolean.FALSE}, new Object[]{"readable-elisp", "gnu.kawa.functions.DisplayFormat", "getEmacsLispFormat", boolClasses, Boolean.TRUE}, new Object[]{"clisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", boolClasses, Boolean.FALSE}, new Object[]{"readable-clisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", boolClasses, Boolean.TRUE}, new Object[]{"commonlisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", boolClasses, Boolean.FALSE}, new Object[]{"readable-commonlisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", boolClasses, Boolean.TRUE}, new Object[]{"xml", "gnu.xml.XMLPrinter", "make", xmlPrinterClasses, portArg, null}, new Object[]{"html", "gnu.xml.XMLPrinter", "make", xmlPrinterClasses, portArg, "html"}, new Object[]{"xhtml", "gnu.xml.XMLPrinter", "make", xmlPrinterClasses, portArg, "xhtml"}, new Object[]{"cgi", "gnu.kawa.xml.HttpPrinter", "make", httpPrinterClasses, portArg}, new Object[]{"ignore", "gnu.lists.VoidConsumer", "getInstance", noClasses}, new Object[]{null}};

    public static void setDefaultFormat(String name) {
        Object[] info;
        String name2 = name.intern();
        defaultFormatName = name2;
        int i = 0;
        while (true) {
            info = formats[i];
            Object iname = info[0];
            if (iname == null) {
                System.err.println("kawa: unknown output format '" + name2 + "'");
                System.exit(-1);
            } else if (iname == name2) {
                break;
            }
            i++;
        }
        defaultFormatInfo = info;
        try {
            Class formatClass = Class.forName((String) info[1]);
            defaultFormatMethod = formatClass.getMethod((String) info[2], (Class[]) info[3]);
        } catch (Throwable ex) {
            System.err.println("kawa:  caught " + ex + " while looking for format '" + name2 + "'");
            System.exit(-1);
        }
        if (!defaultFormatInfo[1].equals("gnu.lists.VoidConsumer")) {
            ModuleBody.setMainPrintValues(true);
        }
    }

    public static Consumer getOutputConsumer(OutPort out) {
        Object[] info = defaultFormatInfo;
        if (out == null) {
            return VoidConsumer.getInstance();
        }
        if (info == null) {
            return Language.getDefaultLanguage().getOutputConsumer(out);
        }
        try {
            Object[] args = new Object[info.length - 4];
            System.arraycopy(info, 4, args, 0, args.length);
            int i = args.length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                } else if (args[i] == portArg) {
                    args[i] = out;
                }
            }
            Object format = defaultFormatMethod.invoke(null, args);
            if (format instanceof AbstractFormat) {
                out.objectFormat = (AbstractFormat) format;
                return out;
            }
            return (Consumer) format;
        } catch (Throwable ex) {
            throw new RuntimeException("cannot get output-format '" + defaultFormatName + "' - caught " + ex);
        }
    }

    public static boolean run(Language language, Environment env) {
        OutPort perr;
        InPort inp = InPort.inDefault();
        SourceMessages messages = new SourceMessages();
        if (inp instanceof TtyInPort) {
            Procedure prompter = language.getPrompter();
            if (prompter != null) {
                ((TtyInPort) inp).setPrompter(prompter);
            }
            perr = OutPort.errDefault();
        } else {
            perr = null;
        }
        Throwable ex = run(language, env, inp, OutPort.outDefault(), perr, messages);
        if (ex == null) {
            return true;
        }
        printError(ex, messages, OutPort.errDefault());
        return false;
    }

    public static Throwable run(Language language, Environment env, InPort inp, OutPort pout, OutPort perr, SourceMessages messages) {
        AbstractFormat saveFormat = null;
        if (pout != null) {
            saveFormat = pout.objectFormat;
        }
        Consumer out = getOutputConsumer(pout);
        try {
            return run(language, env, inp, out, perr, null, messages);
        } finally {
            if (pout != null) {
                pout.objectFormat = saveFormat;
            }
        }
    }

    public static boolean run(Language language, Environment env, InPort inp, Consumer out, OutPort perr, URL url) {
        SourceMessages messages = new SourceMessages();
        Throwable ex = run(language, env, inp, out, perr, url, messages);
        if (ex != null) {
            printError(ex, messages, perr);
        }
        return ex == null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x004b, code lost:
        if (r21 == null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004d, code lost:
        r5.consumer = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004f, code lost:
        gnu.expr.Language.restoreCurrent(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0053, code lost:
        return null;
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Throwable run(gnu.expr.Language r18, gnu.mapping.Environment r19, gnu.mapping.InPort r20, gnu.lists.Consumer r21, gnu.mapping.OutPort r22, java.net.URL r23, gnu.text.SourceMessages r24) {
        /*
            Method dump skipped, instructions count: 223
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.Shell.run(gnu.expr.Language, gnu.mapping.Environment, gnu.mapping.InPort, gnu.lists.Consumer, gnu.mapping.OutPort, java.net.URL, gnu.text.SourceMessages):java.lang.Throwable");
    }

    public static void printError(Throwable ex, SourceMessages messages, OutPort perr) {
        if (ex instanceof WrongArguments) {
            WrongArguments e = (WrongArguments) ex;
            messages.printAll(perr, 20);
            if (e.usage != null) {
                perr.println("usage: " + e.usage);
            }
            e.printStackTrace(perr);
        } else if (ex instanceof ClassCastException) {
            messages.printAll(perr, 20);
            perr.println("Invalid parameter, was: " + ex.getMessage());
            ex.printStackTrace(perr);
        } else {
            if (ex instanceof SyntaxException) {
                SyntaxException se = (SyntaxException) ex;
                if (se.getMessages() == messages) {
                    se.printAll(perr, 20);
                    se.clear();
                    return;
                }
            }
            messages.printAll(perr, 20);
            ex.printStackTrace(perr);
        }
    }

    public static final CompiledModule checkCompiledZip(InputStream fs, Path path, Environment env, Language language) throws IOException {
        CompiledModule compiledModule = null;
        try {
            fs.mark(5);
            boolean isZip = fs.read() == 80 && fs.read() == 75 && fs.read() == 3 && fs.read() == 4;
            fs.reset();
            if (isZip) {
                fs.close();
                Environment orig_env = Environment.getCurrent();
                String name = path.toString();
                try {
                    if (env != orig_env) {
                        try {
                            Environment.setCurrent(env);
                        } catch (IOException ex) {
                            throw new WrappedException("load: " + name + " - " + ex.toString(), ex);
                        }
                    }
                    if (!(path instanceof FilePath)) {
                        throw new RuntimeException("load: " + name + " - not a file path");
                    }
                    File zfile = ((FilePath) path).toFile();
                    if (!zfile.exists()) {
                        throw new RuntimeException("load: " + name + " - not found");
                    }
                    if (!zfile.canRead()) {
                        throw new RuntimeException("load: " + name + " - not readable");
                    }
                    ZipLoader loader = new ZipLoader(name);
                    Class clas = loader.loadAllClasses();
                    compiledModule = CompiledModule.make(clas, language);
                    if (env != orig_env) {
                        Environment.setCurrent(orig_env);
                    }
                } catch (Throwable th) {
                    if (env != orig_env) {
                        Environment.setCurrent(orig_env);
                    }
                    throw th;
                }
            }
        } catch (IOException e) {
        }
        return compiledModule;
    }

    public static boolean runFileOrClass(String fname, boolean lineByLine, int skipLines) {
        Path path;
        InputStream fs;
        Language language = Language.getDefaultLanguage();
        try {
            if (fname.equals("-")) {
                path = Path.valueOf("/dev/stdin");
                fs = System.in;
            } else {
                path = Path.valueOf(fname);
                fs = path.openInputStream();
            }
            Environment env = Environment.getCurrent();
            return runFile(fs, path, env, lineByLine, skipLines);
        } catch (Throwable e) {
            try {
                Class clas = Class.forName(fname);
                try {
                    CompiledModule cmodule = CompiledModule.make(clas, language);
                    cmodule.evalModule(Environment.getCurrent(), OutPort.outDefault());
                    return true;
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    return false;
                }
            } catch (Throwable th) {
                System.err.println("Cannot read file " + e.getMessage());
                return false;
            }
        }
    }

    public static final boolean runFile(InputStream fs, Path path, Environment env, boolean lineByLine, int skipLines) throws Throwable {
        if (!(fs instanceof BufferedInputStream)) {
            fs = new BufferedInputStream(fs);
        }
        Language language = Language.getDefaultLanguage();
        Path savePath = (Path) currentLoadPath.get();
        try {
            currentLoadPath.set(path);
            CompiledModule cmodule = checkCompiledZip(fs, path, env, language);
            if (cmodule == null) {
                InPort src = InPort.openFile(fs, path);
                while (true) {
                    skipLines--;
                    if (skipLines < 0) {
                        break;
                    }
                    src.skipRestOfLine();
                }
                SourceMessages messages = new SourceMessages();
                URL url = path.toURL();
                if (lineByLine) {
                    boolean print = ModuleBody.getMainPrintValues();
                    Consumer out = print ? getOutputConsumer(OutPort.outDefault()) : new VoidConsumer();
                    Throwable ex = run(language, env, src, out, null, url, messages);
                    if (ex != null) {
                        throw ex;
                    }
                } else {
                    cmodule = compileSource(src, env, url, language, messages);
                    messages.printAll(OutPort.errDefault(), 20);
                    if (cmodule == null) {
                        src.close();
                        return false;
                    }
                }
                src.close();
            }
            if (cmodule != null) {
                cmodule.evalModule(env, OutPort.outDefault());
            }
            currentLoadPath.set(savePath);
            return true;
        } finally {
            currentLoadPath.set(savePath);
        }
    }

    static CompiledModule compileSource(InPort port, Environment env, URL url, Language language, SourceMessages messages) throws SyntaxException, IOException {
        ModuleManager manager = ModuleManager.getInstance();
        ModuleInfo minfo = manager.findWithSourcePath(port.getName());
        Compilation comp = language.parse(port, messages, 1, minfo);
        CallContext ctx = CallContext.getInstance();
        ctx.values = Values.noArgs;
        Object inst = ModuleExp.evalModule1(env, comp, url, null);
        if (inst == null || messages.seenErrors()) {
            return null;
        }
        return new CompiledModule(comp.getModule(), inst, language);
    }
}
