package gnu.expr;

import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.kawa.lispexpr.ClassNamespace;
import gnu.kawa.reflect.ClassMemberLocation;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.lists.AbstractFormat;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.FString;
import gnu.lists.PrintConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.InPort;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.NamedLocation;
import gnu.mapping.Namespace;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kawa.repl;

/* loaded from: classes.dex */
public abstract class Language {
    public static final int FUNCTION_NAMESPACE = 2;
    public static final int NAMESPACE_PREFIX_NAMESPACE = 4;
    public static final int PARSE_CURRENT_NAMES = 2;
    public static final int PARSE_EXPLICIT = 64;
    public static final int PARSE_FOR_APPLET = 16;
    public static final int PARSE_FOR_EVAL = 3;
    public static final int PARSE_FOR_SERVLET = 32;
    public static final int PARSE_IMMEDIATE = 1;
    public static final int PARSE_ONE_LINE = 4;
    public static final int PARSE_PROLOG = 8;
    public static final int VALUE_NAMESPACE = 1;
    protected static final InheritableThreadLocal<Language> current = new InheritableThreadLocal<>();
    static int envCounter;
    protected static int env_counter;
    protected static Language global;
    static String[][] languages;
    public static boolean requirePedantic;
    protected Environment environ;
    protected Environment userEnv;

    public abstract Lexer getLexer(InPort inPort, SourceMessages sourceMessages);

    public abstract boolean parse(Compilation compilation, int i) throws IOException, SyntaxException;

    static {
        Environment.setGlobal(BuiltinEnvironment.getInstance());
        languages = new String[][]{new String[]{"scheme", ".scm", ".sc", "kawa.standard.Scheme"}, new String[]{"krl", ".krl", "gnu.kawa.brl.BRL"}, new String[]{"brl", ".brl", "gnu.kawa.brl.BRL"}, new String[]{"emacs", "elisp", "emacs-lisp", ".el", "gnu.jemacs.lang.ELisp"}, new String[]{"xquery", ".xquery", ".xq", ".xql", "gnu.xquery.lang.XQuery"}, new String[]{"q2", ".q2", "gnu.q2.lang.Q2"}, new String[]{"xslt", "xsl", ".xsl", "gnu.kawa.xslt.XSLT"}, new String[]{"commonlisp", "common-lisp", "clisp", "lisp", ".lisp", ".lsp", ".cl", "gnu.commonlisp.lang.CommonLisp"}};
        env_counter = 0;
    }

    public static Language getDefaultLanguage() {
        Language lang = current.get();
        return lang != null ? lang : global;
    }

    public static void setCurrentLanguage(Language language) {
        current.set(language);
    }

    public static Language setSaveCurrent(Language language) {
        Language save = current.get();
        current.set(language);
        return save;
    }

    public static void restoreCurrent(Language saved) {
        current.set(saved);
    }

    public static String[][] getLanguages() {
        return languages;
    }

    public static void registerLanguage(String[] langMapping) {
        String[][] newLangs = new String[languages.length + 1];
        System.arraycopy(languages, 0, newLangs, 0, languages.length);
        newLangs[newLangs.length - 1] = langMapping;
        languages = newLangs;
    }

    public static Language detect(InputStream in) throws IOException {
        int c;
        if (!in.markSupported()) {
            return null;
        }
        StringBuffer sbuf = new StringBuffer();
        in.mark(HttpRequestContext.HTTP_OK);
        while (sbuf.length() < 200 && (c = in.read()) >= 0 && c != 10 && c != 13) {
            sbuf.append((char) c);
        }
        in.reset();
        return detect(sbuf.toString());
    }

    public static Language detect(InPort port) throws IOException {
        StringBuffer sbuf = new StringBuffer();
        port.mark(300);
        port.readLine(sbuf, 'P');
        port.reset();
        return detect(sbuf.toString());
    }

    public static Language detect(String line) {
        String str = line.trim();
        int k = str.indexOf("kawa:");
        if (k >= 0) {
            int i = k + 5;
            int j = i;
            while (j < str.length() && Character.isJavaIdentifierPart(str.charAt(j))) {
                j++;
            }
            if (j > i) {
                String w = str.substring(i, j);
                Language lang = getInstance(w);
                if (lang != null) {
                    return lang;
                }
            }
        }
        if (str.indexOf("-*- scheme -*-") >= 0) {
            Language lang2 = getInstance("scheme");
            return lang2;
        } else if (str.indexOf("-*- xquery -*-") >= 0) {
            Language lang3 = getInstance("xquery");
            return lang3;
        } else if (str.indexOf("-*- emacs-lisp -*-") >= 0) {
            Language lang4 = getInstance("elisp");
            return lang4;
        } else if (str.indexOf("-*- common-lisp -*-") >= 0 || str.indexOf("-*- lisp -*-") >= 0) {
            Language lang5 = getInstance("common-lisp");
            return lang5;
        } else if ((str.charAt(0) == '(' && str.charAt(1) == ':') || (str.length() >= 7 && str.substring(0, 7).equals("xquery "))) {
            Language lang6 = getInstance("xquery");
            return lang6;
        } else if (str.charAt(0) == ';' && str.charAt(1) == ';') {
            Language lang7 = getInstance("scheme");
            return lang7;
        } else {
            return null;
        }
    }

    public static Language getInstanceFromFilenameExtension(String filename) {
        Language lang;
        int dot = filename.lastIndexOf(46);
        if (dot <= 0 || (lang = getInstance(filename.substring(dot))) == null) {
            return null;
        }
        return lang;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static gnu.expr.Language getInstance(java.lang.String r8) {
        /*
            java.lang.String[][] r7 = gnu.expr.Language.languages
            int r4 = r7.length
            r1 = 0
        L4:
            if (r1 >= r4) goto L2e
            java.lang.String[][] r7 = gnu.expr.Language.languages
            r6 = r7[r1]
            int r7 = r6.length
            int r5 = r7 + (-1)
            r2 = r5
        Le:
            int r2 = r2 + (-1)
            if (r2 < 0) goto L2b
            if (r8 == 0) goto L1c
            r7 = r6[r2]
            boolean r7 = r7.equalsIgnoreCase(r8)
            if (r7 == 0) goto Le
        L1c:
            r7 = r6[r5]     // Catch: java.lang.ClassNotFoundException -> L2a
            java.lang.Class r3 = java.lang.Class.forName(r7)     // Catch: java.lang.ClassNotFoundException -> L2a
            r7 = 0
            r7 = r6[r7]
            gnu.expr.Language r7 = getInstance(r7, r3)
        L29:
            return r7
        L2a:
            r0 = move-exception
        L2b:
            int r1 = r1 + 1
            goto L4
        L2e:
            r7 = 0
            goto L29
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.Language.getInstance(java.lang.String):gnu.expr.Language");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Language() {
        Convert.setInstance(KawaConvert.getInstance());
    }

    public static Language getInstance(String langName, Class langClass) {
        Throwable th;
        Method method;
        try {
            Class[] args = new Class[0];
            try {
                String capitalizedName = Character.toTitleCase(langName.charAt(0)) + langName.substring(1).toLowerCase();
                String methodName = "get" + capitalizedName + "Instance";
                method = langClass.getDeclaredMethod(methodName, args);
            } catch (Exception e) {
                method = langClass.getDeclaredMethod("getInstance", args);
            }
            return (Language) method.invoke(null, Values.noArgs);
        } catch (Exception ex) {
            String langName2 = langClass.getName();
            if (ex instanceof InvocationTargetException) {
                th = ((InvocationTargetException) ex).getTargetException();
            } else {
                th = ex;
            }
            throw new WrappedException("getInstance for '" + langName2 + "' failed", th);
        }
    }

    public boolean isTrue(Object value) {
        return value != Boolean.FALSE;
    }

    public Object booleanObject(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    public Object noValue() {
        return Values.empty;
    }

    public boolean hasSeparateFunctionNamespace() {
        return false;
    }

    public final Environment getEnvironment() {
        return this.userEnv != null ? this.userEnv : Environment.getCurrent();
    }

    public final Environment getNewEnvironment() {
        StringBuilder append = new StringBuilder().append("environment-");
        int i = envCounter + 1;
        envCounter = i;
        return Environment.make(append.append(i).toString(), this.environ);
    }

    public Environment getLangEnvironment() {
        return this.environ;
    }

    public NamedLocation lookupBuiltin(Symbol name, Object property, int hash) {
        if (this.environ == null) {
            return null;
        }
        return this.environ.lookup(name, property, hash);
    }

    public void define(String sym, Object p) {
        Symbol s = getSymbol(sym);
        this.environ.define(s, null, p);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void defAliasStFld(String name, String cname, String fname) {
        StaticFieldLocation.define(this.environ, getSymbol(name), (Object) null, cname, fname);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void defProcStFld(String name, String cname, String fname) {
        Object property = hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null;
        Symbol sym = getSymbol(name);
        StaticFieldLocation loc = StaticFieldLocation.define(this.environ, sym, property, cname, fname);
        loc.setProcedure();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void defProcStFld(String name, String cname) {
        defProcStFld(name, cname, Compilation.mangleNameIfNeeded(name));
    }

    public final void defineFunction(Named proc) {
        Object name = proc.getSymbol();
        Symbol sym = name instanceof Symbol ? (Symbol) name : getSymbol(name.toString());
        Object property = hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null;
        this.environ.define(sym, property, proc);
    }

    public void defineFunction(String name, Object proc) {
        Object property = hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null;
        this.environ.define(getSymbol(name), property, proc);
    }

    public Object getEnvPropertyFor(Field fld, Object value) {
        if (hasSeparateFunctionNamespace() && Compilation.typeProcedure.getReflectClass().isAssignableFrom(fld.getType())) {
            return EnvironmentKey.FUNCTION;
        }
        return null;
    }

    public Object getEnvPropertyFor(Declaration decl) {
        if (hasSeparateFunctionNamespace() && decl.isProcedureDecl()) {
            return EnvironmentKey.FUNCTION;
        }
        return null;
    }

    public void loadClass(String name) throws ClassNotFoundException {
        try {
            Class clas = Class.forName(name);
            try {
                Object inst = clas.newInstance();
                ClassMemberLocation.defineAll(inst, this, Environment.getCurrent());
                if (inst instanceof ModuleBody) {
                    ((ModuleBody) inst).run();
                }
            } catch (Exception ex) {
                throw new WrappedException("cannot load " + name, ex);
            }
        } catch (ClassNotFoundException ex2) {
            throw ex2;
        }
    }

    public Symbol getSymbol(String name) {
        return this.environ.getSymbol(name);
    }

    public Object lookup(String name) {
        return this.environ.get(name);
    }

    public AbstractFormat getFormat(boolean readable) {
        return null;
    }

    public Consumer getOutputConsumer(Writer out) {
        OutPort oport = out instanceof OutPort ? (OutPort) out : new OutPort(out);
        oport.objectFormat = getFormat(false);
        return oport;
    }

    public String getName() {
        String name = getClass().getName();
        int dot = name.lastIndexOf(46);
        if (dot >= 0) {
            return name.substring(dot + 1);
        }
        return name;
    }

    public Compilation getCompilation(Lexer lexer, SourceMessages messages, NameLookup lexical) {
        return new Compilation(this, messages, lexical);
    }

    public final Compilation parse(InPort port, SourceMessages messages, int options) throws IOException, SyntaxException {
        return parse(getLexer(port, messages), options, (ModuleInfo) null);
    }

    public final Compilation parse(InPort port, SourceMessages messages, ModuleInfo info) throws IOException, SyntaxException {
        return parse(getLexer(port, messages), 8, info);
    }

    public final Compilation parse(InPort port, SourceMessages messages, int options, ModuleInfo info) throws IOException, SyntaxException {
        return parse(getLexer(port, messages), options, info);
    }

    public final Compilation parse(Lexer lexer, int options, ModuleInfo info) throws IOException, SyntaxException {
        SourceMessages messages = lexer.getMessages();
        NameLookup lexical = (options & 2) != 0 ? NameLookup.getInstance(getEnvironment(), this) : new NameLookup(this);
        boolean immediate = (options & 1) != 0;
        Compilation tr = getCompilation(lexer, messages, lexical);
        if (requirePedantic) {
            tr.pedantic = true;
        }
        if (!immediate) {
            tr.mustCompile = true;
        }
        tr.immediate = immediate;
        tr.langOptions = options;
        if ((options & 64) != 0) {
            tr.explicit = true;
        }
        if ((options & 8) != 0) {
            tr.setState(1);
        }
        tr.pushNewModule(lexer);
        if (info != null) {
            info.setCompilation(tr);
        }
        if (!parse(tr, options)) {
            return null;
        }
        if (tr.getState() == 1) {
            tr.setState(2);
            return tr;
        }
        return tr;
    }

    public void resolve(Compilation comp) {
    }

    public Type getTypeFor(Class clas) {
        return Type.make(clas);
    }

    public final Type getLangTypeFor(Type type) {
        Class clas;
        if (type.isExisting() && (clas = type.getReflectClass()) != null) {
            return getTypeFor(clas);
        }
        return type;
    }

    public String formatType(Type type) {
        return type.getName();
    }

    public static Type string2Type(String name) {
        if (name.endsWith("[]")) {
            Type t = string2Type(name.substring(0, name.length() - 2));
            if (t == null) {
                return null;
            }
            return ArrayType.make(t);
        } else if (Type.isValidJavaTypeName(name)) {
            return Type.getType(name);
        } else {
            return null;
        }
    }

    public Type getTypeFor(String name) {
        return string2Type(name);
    }

    public final Type getTypeFor(Object spec, boolean lenient) {
        String uri;
        if (spec instanceof Type) {
            return (Type) spec;
        }
        if (spec instanceof Class) {
            return getTypeFor((Class) spec);
        }
        if (lenient && ((spec instanceof FString) || (spec instanceof String) || (((spec instanceof Symbol) && ((Symbol) spec).hasEmptyNamespace()) || (spec instanceof CharSeq)))) {
            return getTypeFor(spec.toString());
        }
        if ((spec instanceof Namespace) && (uri = ((Namespace) spec).getName()) != null && uri.startsWith("class:")) {
            return getLangTypeFor(string2Type(uri.substring(6)));
        }
        return null;
    }

    public final Type asType(Object spec) {
        Type type = getTypeFor(spec, true);
        return type == null ? (Type) spec : type;
    }

    public final Type getTypeFor(Expression exp) {
        return getTypeFor(exp, true);
    }

    public Type getTypeFor(Expression exp, boolean lenient) {
        if (exp instanceof QuoteExp) {
            Object value = ((QuoteExp) exp).getValue();
            if (value instanceof Type) {
                return (Type) value;
            }
            if (value instanceof Class) {
                return Type.make((Class) value);
            }
            return getTypeFor(value, lenient);
        } else if (exp instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) exp;
            Declaration decl = Declaration.followAliases(rexp.getBinding());
            String name = rexp.getName();
            if (decl != null) {
                Expression exp2 = decl.getValue();
                if ((exp2 instanceof QuoteExp) && decl.getFlag(JSONzip.int14) && !decl.isIndirectBinding()) {
                    return getTypeFor(((QuoteExp) exp2).getValue(), lenient);
                }
                if ((exp2 instanceof ClassExp) || (exp2 instanceof ModuleExp)) {
                    decl.setCanRead(true);
                    return ((LambdaExp) exp2).getClassType();
                } else if (decl.isAlias() && (exp2 instanceof QuoteExp)) {
                    Object val = ((QuoteExp) exp2).getValue();
                    if (val instanceof Location) {
                        Location loc = (Location) val;
                        if (loc.isBound()) {
                            return getTypeFor(loc.get(), lenient);
                        }
                        if (!(loc instanceof Named)) {
                            return null;
                        }
                        name = ((Named) loc).getName();
                    }
                } else if (!decl.getFlag(65536L)) {
                    return getTypeFor(exp2, lenient);
                }
            }
            Object val2 = getEnvironment().get(name);
            if (val2 instanceof Type) {
                return (Type) val2;
            }
            if (val2 instanceof ClassNamespace) {
                return ((ClassNamespace) val2).getClassType();
            }
            int len = name.length();
            if (len > 2 && name.charAt(0) == '<' && name.charAt(len - 1) == '>') {
                return getTypeFor(name.substring(1, len - 1));
            }
            return null;
        } else if ((exp instanceof ClassExp) || (exp instanceof ModuleExp)) {
            return ((LambdaExp) exp).getClassType();
        } else {
            return null;
        }
    }

    public static Type unionType(Type t1, Type t2) {
        if (t1 == Type.toStringType) {
            t1 = Type.javalangStringType;
        }
        if (t2 == Type.toStringType) {
            t2 = Type.javalangStringType;
        }
        if (t1 != t2) {
            if ((t1 instanceof PrimType) && (t2 instanceof PrimType)) {
                char sig1 = t1.getSignature().charAt(0);
                char sig2 = t2.getSignature().charAt(0);
                if (sig1 != sig2) {
                    if ((sig1 == 'B' || sig1 == 'S' || sig1 == 'I') && (sig2 == 'I' || sig2 == 'J')) {
                        return t2;
                    }
                    if ((sig2 != 'B' && sig2 != 'S' && sig2 != 'I') || (sig1 != 'I' && sig1 != 'J')) {
                        if (sig1 == 'F' && sig2 == 'D') {
                            return t2;
                        }
                        if (sig2 != 'F' || sig1 != 'D') {
                            return Type.objectType;
                        }
                        return t1;
                    }
                    return t1;
                }
                return t1;
            }
            return Type.objectType;
        }
        return t1;
    }

    public Declaration declFromField(ModuleExp mod, Object fvalue, gnu.bytecode.Field fld) {
        Object intern;
        String fname = fld.getName();
        Type ftype = fld.getType();
        boolean isAlias = ftype.isSubtype(Compilation.typeLocation);
        boolean externalAccess = false;
        boolean isFinal = (fld.getModifiers() & 16) != 0;
        boolean isImportedInstance = fname.endsWith("$instance");
        if (isImportedInstance) {
            intern = fname;
        } else if (isFinal && (fvalue instanceof Named)) {
            intern = ((Named) fvalue).getSymbol();
        } else {
            if (fname.startsWith(Declaration.PRIVATE_PREFIX)) {
                externalAccess = true;
                fname = fname.substring(Declaration.PRIVATE_PREFIX.length());
            }
            intern = Compilation.demangleName(fname, true).intern();
        }
        if (intern instanceof String) {
            String uri = mod.getNamespaceUri();
            String sname = (String) intern;
            if (uri == null) {
                intern = SimpleSymbol.valueOf(sname);
            } else {
                intern = Symbol.make(uri, sname);
            }
        }
        Type dtype = isAlias ? Type.objectType : getTypeFor(ftype.getReflectClass());
        Declaration fdecl = mod.addDeclaration(intern, dtype);
        boolean isStatic = (fld.getModifiers() & 8) != 0;
        if (isAlias) {
            fdecl.setIndirectBinding(true);
            if ((ftype instanceof ClassType) && ((ClassType) ftype).isSubclass("gnu.mapping.ThreadLocation")) {
                fdecl.setFlag(268435456L);
            }
        } else if (isFinal && (ftype instanceof ClassType)) {
            if (ftype.isSubtype(Compilation.typeProcedure)) {
                fdecl.setProcedureDecl(true);
            } else if (((ClassType) ftype).isSubclass("gnu.mapping.Namespace")) {
                fdecl.setFlag(2097152L);
            }
        }
        if (isStatic) {
            fdecl.setFlag(2048L);
        }
        fdecl.field = fld;
        if (isFinal && !isAlias) {
            fdecl.setFlag(JSONzip.int14);
        }
        if (isImportedInstance) {
            fdecl.setFlag(1073741824L);
        }
        fdecl.setSimple(false);
        if (externalAccess) {
            fdecl.setFlag(524320L);
        }
        return fdecl;
    }

    public int getNamespaceOf(Declaration decl) {
        return 1;
    }

    public boolean hasNamespace(Declaration decl, int namespace) {
        return (getNamespaceOf(decl) & namespace) != 0;
    }

    public void emitPushBoolean(boolean value, CodeAttr code) {
        code.emitGetStatic(value ? Compilation.trueConstant : Compilation.falseConstant);
    }

    public void emitCoerceToBoolean(CodeAttr code) {
        emitPushBoolean(false, code);
        code.emitIfNEq();
        code.emitPushInt(1);
        code.emitElse();
        code.emitPushInt(0);
        code.emitFi();
    }

    public Object coerceFromObject(Class clas, Object obj) {
        return getTypeFor(clas).coerceFromObject(obj);
    }

    public Object coerceToObject(Class clas, Object obj) {
        return getTypeFor(clas).coerceToObject(obj);
    }

    public static synchronized void setDefaults(Language lang) {
        synchronized (Language.class) {
            setCurrentLanguage(lang);
            global = lang;
            if (Environment.getGlobal() == BuiltinEnvironment.getInstance()) {
                Environment.setGlobal(Environment.getCurrent());
            }
        }
    }

    public Procedure getPrompter() {
        Object property = null;
        if (hasSeparateFunctionNamespace()) {
            property = EnvironmentKey.FUNCTION;
        }
        Procedure prompter = (Procedure) getEnvironment().get(getSymbol("default-prompter"), property, null);
        return prompter != null ? prompter : new SimplePrompter();
    }

    public final Object eval(String string) throws Throwable {
        return eval((InPort) new CharArrayInPort(string));
    }

    public final Object eval(Reader in) throws Throwable {
        return eval(in instanceof InPort ? (InPort) in : new InPort(in));
    }

    public final Object eval(InPort port) throws Throwable {
        CallContext ctx = CallContext.getInstance();
        int oldIndex = ctx.startFromContext();
        try {
            eval(port, ctx);
            return ctx.getFromContext(oldIndex);
        } catch (Throwable ex) {
            ctx.cleanupFromContext(oldIndex);
            throw ex;
        }
    }

    public final void eval(String string, Writer out) throws Throwable {
        eval(new CharArrayInPort(string), out);
    }

    public final void eval(String string, PrintConsumer out) throws Throwable {
        eval(string, getOutputConsumer(out));
    }

    public final void eval(String string, Consumer out) throws Throwable {
        eval(new CharArrayInPort(string), out);
    }

    public final void eval(Reader in, Writer out) throws Throwable {
        eval(in, getOutputConsumer(out));
    }

    public void eval(Reader in, Consumer out) throws Throwable {
        InPort port = in instanceof InPort ? (InPort) in : new InPort(in);
        CallContext ctx = CallContext.getInstance();
        Consumer save = ctx.consumer;
        try {
            ctx.consumer = out;
            eval(port, ctx);
        } finally {
            ctx.consumer = save;
        }
    }

    public void eval(InPort port, CallContext ctx) throws Throwable {
        SourceMessages messages = new SourceMessages();
        Language saveLang = setSaveCurrent(this);
        try {
            Compilation comp = parse(port, messages, 3);
            ModuleExp.evalModule(getEnvironment(), ctx, comp, null, null);
            restoreCurrent(saveLang);
            if (messages.seenErrors()) {
                throw new RuntimeException("invalid syntax in eval form:\n" + messages.toString(20));
            }
        } catch (Throwable th) {
            restoreCurrent(saveLang);
            throw th;
        }
    }

    public void runAsApplication(String[] args) {
        setDefaults(this);
        repl.main(args);
    }
}
