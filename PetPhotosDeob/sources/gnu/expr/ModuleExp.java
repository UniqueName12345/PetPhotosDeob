package gnu.expr;

import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.kawa.reflect.ClassMemberLocation;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.Externalizable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* loaded from: classes.dex */
public class ModuleExp extends LambdaExp implements Externalizable {
    public static final int EXPORT_SPECIFIED = 16384;
    public static final int IMMEDIATE = 1048576;
    public static final int LAZY_DECLARATIONS = 524288;
    public static final int NONSTATIC_SPECIFIED = 65536;
    public static final int STATIC_RUN_SPECIFIED = 262144;
    public static final int STATIC_SPECIFIED = 32768;
    public static final int SUPERTYPE_SPECIFIED = 131072;
    public static String dumpZipPrefix;
    public static int interactiveCounter;
    static int lastZipCounter;
    ModuleInfo info;
    ClassType[] interfaces;
    ClassType superType;
    public static boolean compilerAvailable = true;
    public static boolean alwaysCompile = compilerAvailable;
    public static boolean neverCompile = false;

    public static Class evalToClass(Compilation comp, URL url) throws SyntaxException {
        comp.getModule();
        SourceMessages messages = comp.getMessages();
        try {
            comp.minfo.loadByStages(12);
            if (messages.seenErrors()) {
                return null;
            }
            ArrayClassLoader loader = comp.loader;
            if (url == null) {
                url = Path.currentPath().toURL();
            }
            loader.setResourceContext(url);
            ZipOutputStream zout = null;
            if (dumpZipPrefix != null) {
                StringBuffer zipname = new StringBuffer(dumpZipPrefix);
                lastZipCounter++;
                if (interactiveCounter > lastZipCounter) {
                    lastZipCounter = interactiveCounter;
                }
                zipname.append(lastZipCounter);
                zipname.append(".zip");
                FileOutputStream zfout = new FileOutputStream(zipname.toString());
                zout = new ZipOutputStream(zfout);
            }
            for (int iClass = 0; iClass < comp.numClasses; iClass++) {
                ClassType clas = comp.classes[iClass];
                String className = clas.getName();
                byte[] classBytes = clas.writeToArray();
                loader.addClass(className, classBytes);
                if (zout != null) {
                    String clname = className.replace('.', '/') + ".class";
                    ZipEntry zent = new ZipEntry(clname);
                    zent.setSize(classBytes.length);
                    CRC32 crc = new CRC32();
                    crc.update(classBytes);
                    zent.setCrc(crc.getValue());
                    zent.setMethod(0);
                    zout.putNextEntry(zent);
                    zout.write(classBytes);
                }
            }
            if (zout != null) {
                zout.close();
            }
            Class clas2 = null;
            ArrayClassLoader context = loader;
            while (context.getParent() instanceof ArrayClassLoader) {
                context = (ArrayClassLoader) context.getParent();
            }
            for (int iClass2 = 0; iClass2 < comp.numClasses; iClass2++) {
                ClassType ctype = comp.classes[iClass2];
                Class cclass = loader.loadClass(ctype.getName());
                ctype.setReflectClass(cclass);
                ctype.setExisting(true);
                if (iClass2 == 0) {
                    clas2 = cclass;
                } else if (context != loader) {
                    context.addClass(cclass);
                }
            }
            ModuleInfo minfo = comp.minfo;
            minfo.setModuleClass(clas2);
            comp.cleanupAfterCompilation();
            int ndeps = minfo.numDependencies;
            for (int idep = 0; idep < ndeps; idep++) {
                ModuleInfo dep = minfo.dependencies[idep];
                Class dclass = dep.getModuleClassRaw();
                if (dclass == null) {
                    dclass = evalToClass(dep.comp, null);
                }
                comp.loader.addClass(dclass);
            }
            return clas2;
        } catch (IOException ex) {
            throw new WrappedException("I/O error in lambda eval", ex);
        } catch (ClassNotFoundException ex2) {
            throw new WrappedException("class not found in lambda eval", ex2);
        } catch (Throwable ex3) {
            comp.getMessages().error('f', "internal compile error - caught " + ex3, ex3);
            throw new SyntaxException(messages);
        }
    }

    public static void mustNeverCompile() {
        alwaysCompile = false;
        neverCompile = true;
        compilerAvailable = false;
    }

    public static void mustAlwaysCompile() {
        alwaysCompile = true;
        neverCompile = false;
    }

    public static final boolean evalModule(Environment env, CallContext ctx, Compilation comp, URL url, OutPort msg) throws Throwable {
        ModuleExp mexp = comp.getModule();
        Language language = comp.getLanguage();
        Object inst = evalModule1(env, comp, url, msg);
        if (inst == null) {
            return false;
        }
        evalModule2(env, ctx, language, mexp, inst);
        return true;
    }

    public static final Object evalModule1(Environment env, Compilation comp, URL url, OutPort msg) throws SyntaxException {
        Thread thread;
        ModuleExp mexp = comp.getModule();
        mexp.info = comp.minfo;
        Environment orig_env = Environment.setSaveCurrent(env);
        Compilation orig_comp = Compilation.setSaveCurrent(comp);
        SourceMessages messages = comp.getMessages();
        ClassLoader savedLoader = null;
        Thread thread2 = null;
        if (alwaysCompile && neverCompile) {
            throw new RuntimeException("alwaysCompile and neverCompile are both true!");
        }
        if (neverCompile) {
            comp.mustCompile = false;
        }
        try {
            comp.process(6);
            comp.minfo.loadByStages(8);
            if (msg == null ? messages.seenErrors() : messages.checkErrors(msg, 20)) {
                return null;
            }
            if (!comp.mustCompile) {
                if (Compilation.debugPrintFinalExpr && msg != null) {
                    msg.println("[Evaluating final module \"" + mexp.getName() + "\":");
                    mexp.print(msg);
                    msg.println(']');
                    msg.flush();
                }
                Boolean bool = Boolean.TRUE;
                Environment.restoreCurrent(orig_env);
                Compilation.restoreCurrent(orig_comp);
                if (0 != 0) {
                    thread2.setContextClassLoader(null);
                    return bool;
                }
                return bool;
            }
            Class clas = evalToClass(comp, url);
            if (clas == null) {
                Environment.restoreCurrent(orig_env);
                Compilation.restoreCurrent(orig_comp);
                if (0 != 0) {
                    thread2.setContextClassLoader(null);
                }
                return null;
            }
            try {
                thread = Thread.currentThread();
                savedLoader = thread.getContextClassLoader();
                thread.setContextClassLoader(clas.getClassLoader());
            } catch (Throwable th) {
                thread = null;
            }
            mexp.body = null;
            mexp.thisVariable = null;
            if (msg == null ? messages.seenErrors() : messages.checkErrors(msg, 20)) {
                Environment.restoreCurrent(orig_env);
                Compilation.restoreCurrent(orig_comp);
                if (thread != null) {
                    thread.setContextClassLoader(savedLoader);
                    return false;
                }
                return false;
            }
            Environment.restoreCurrent(orig_env);
            Compilation.restoreCurrent(orig_comp);
            if (thread != null) {
                thread.setContextClassLoader(savedLoader);
                return clas;
            }
            return clas;
        } finally {
            Environment.restoreCurrent(orig_env);
            Compilation.restoreCurrent(orig_comp);
            if (0 != 0) {
                thread2.setContextClassLoader(null);
            }
        }
    }

    public static final void evalModule2(Environment env, CallContext ctx, Language language, ModuleExp mexp, Object inst) throws Throwable {
        Object value;
        Environment orig_env = Environment.setSaveCurrent(env);
        Thread thread = null;
        try {
            if (inst == Boolean.TRUE) {
                mexp.body.apply(ctx);
            } else {
                if (inst instanceof Class) {
                    inst = ModuleContext.getContext().findInstance((Class) inst);
                }
                if (inst instanceof Runnable) {
                    if (inst instanceof ModuleBody) {
                        ModuleBody mb = (ModuleBody) inst;
                        if (!mb.runDone) {
                            mb.runDone = true;
                            mb.run(ctx);
                        }
                    } else {
                        ((Runnable) inst).run();
                    }
                }
                if (mexp == null) {
                    ClassMemberLocation.defineAll(inst, language, env);
                } else {
                    for (Declaration decl = mexp.firstDecl(); decl != null; decl = decl.nextDecl()) {
                        Object dname = decl.getSymbol();
                        if (!decl.isPrivate() && dname != null) {
                            Field fld = decl.field;
                            Symbol sym = dname instanceof Symbol ? (Symbol) dname : Symbol.make("", dname.toString().intern());
                            Object property = language.getEnvPropertyFor(decl);
                            Expression dvalue = decl.getValue();
                            if ((decl.field.getModifiers() & 16) != 0) {
                                if ((dvalue instanceof QuoteExp) && dvalue != QuoteExp.undefined_exp) {
                                    value = ((QuoteExp) dvalue).getValue();
                                } else {
                                    value = decl.field.getReflectField().get(null);
                                    if (!decl.isIndirectBinding()) {
                                        decl.setValue(QuoteExp.getInstance(value));
                                    } else if (!decl.isAlias() || !(dvalue instanceof ReferenceExp)) {
                                        decl.setValue(null);
                                    }
                                }
                                if (decl.isIndirectBinding()) {
                                    env.addLocation(sym, property, (Location) value);
                                } else {
                                    env.define(sym, property, value);
                                }
                            } else {
                                StaticFieldLocation loc = new StaticFieldLocation(fld.getDeclaringClass(), fld.getName());
                                loc.setDeclaration(decl);
                                env.addLocation(sym, property, loc);
                                decl.setValue(null);
                            }
                        }
                    }
                }
            }
            ctx.runUntilDone();
        } finally {
            Environment.restoreCurrent(orig_env);
            if (0 != 0) {
                thread.setContextClassLoader(null);
            }
        }
    }

    public String getNamespaceUri() {
        return this.info.uri;
    }

    public final ClassType getSuperType() {
        return this.superType;
    }

    public final void setSuperType(ClassType s) {
        this.superType = s;
    }

    public final ClassType[] getInterfaces() {
        return this.interfaces;
    }

    public final void setInterfaces(ClassType[] s) {
        this.interfaces = s;
    }

    public final boolean isStatic() {
        return getFlag(32768) || !((Compilation.moduleStatic < 0 && !getFlag(1048576)) || getFlag(131072) || getFlag(65536));
    }

    public boolean staticInitRun() {
        return isStatic() && (getFlag(262144) || Compilation.moduleStatic == 2);
    }

    @Override // gnu.expr.LambdaExp
    public void allocChildClasses(Compilation comp) {
        declareClosureEnv();
        if (comp.usingCPStyle()) {
            allocFrame(comp);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void allocFields(Compilation comp) {
        for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
            if ((!decl.isSimple() || decl.isPublic()) && decl.field == null && decl.getFlag(65536L) && decl.getFlag(6L)) {
                decl.makeField(comp, null);
            }
        }
        for (Declaration decl2 = firstDecl(); decl2 != null; decl2 = decl2.nextDecl()) {
            if (decl2.field == null) {
                Expression value = decl2.getValue();
                if ((!decl2.isSimple() || decl2.isPublic() || decl2.isNamespaceDecl() || (decl2.getFlag(JSONzip.int14) && decl2.getFlag(6L))) && !decl2.getFlag(65536L)) {
                    if ((value instanceof LambdaExp) && !(value instanceof ModuleExp) && !(value instanceof ClassExp)) {
                        ((LambdaExp) value).allocFieldFor(comp);
                    } else {
                        if (!decl2.shouldEarlyInit() && !decl2.isAlias()) {
                            value = null;
                        }
                        decl2.makeField(comp, value);
                    }
                }
            }
        }
    }

    @Override // gnu.expr.LambdaExp, gnu.expr.ScopeExp, gnu.expr.Expression
    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitModuleExp(this, d);
    }

    @Override // gnu.expr.LambdaExp, gnu.expr.Expression
    public void print(OutPort out) {
        out.startLogicalBlock("(Module/", ")", 2);
        Object sym = getSymbol();
        if (sym != null) {
            out.print(sym);
            out.print('/');
        }
        out.print(this.id);
        out.print('/');
        out.writeSpaceFill();
        out.startLogicalBlock("(", false, ")");
        Declaration decl = firstDecl();
        if (decl != null) {
            out.print("Declarations:");
            while (decl != null) {
                out.writeSpaceFill();
                decl.printInfo(out);
                decl = decl.nextDecl();
            }
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

    @Override // gnu.expr.ScopeExp
    public Declaration firstDecl() {
        synchronized (this) {
            if (getFlag(524288)) {
                this.info.setupModuleExp();
            }
        }
        return this.decls;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public gnu.bytecode.ClassType classFor(gnu.expr.Compilation r12) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ModuleExp.classFor(gnu.expr.Compilation):gnu.bytecode.ClassType");
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput out) throws IOException {
        String name = null;
        if (this.type != null && this.type != Compilation.typeProcedure && !this.type.isExisting()) {
            out.writeObject(this.type);
            return;
        }
        if (0 == 0) {
            name = getName();
        }
        if (name == null) {
            name = getFileName();
        }
        out.writeObject(name);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        Object name = in.readObject();
        if (name instanceof ClassType) {
            this.type = (ClassType) name;
            setName(this.type.getName());
        } else {
            setName((String) name);
        }
        this.flags |= 524288;
    }
}
