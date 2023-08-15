package gnu.xquery.lang;

import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ResolveNames;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.functions.GetModuleClass;
import gnu.kawa.reflect.SingletonType;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.xml.MakeAttribute;
import gnu.kawa.xml.MakeElement;
import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.XDataType;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.mapping.WrongArguments;
import gnu.xml.NamespaceBinding;
import gnu.xml.XMLFilter;
import gnu.xquery.util.NamedCollator;
import gnu.xquery.util.QNameUtils;

/* loaded from: classes.dex */
public class XQResolveNames extends ResolveNames {
    public static final int BASE_URI_BUILTIN = -11;
    public static final int CASTABLE_AS_BUILTIN = -34;
    public static final int CAST_AS_BUILTIN = -33;
    public static final int COLLECTION_BUILTIN = -8;
    public static final int COMPARE_BUILTIN = -4;
    public static final int DEEP_EQUAL_BUILTIN = -25;
    public static final int DEFAULT_COLLATION_BUILTIN = -29;
    public static final int DISTINCT_VALUES_BUILTIN = -5;
    public static final int DOC_AVAILABLE_BUILTIN = -10;
    public static final int DOC_BUILTIN = -9;
    public static final int HANDLE_EXTENSION_BUILTIN = -3;
    public static final int IDREF_BUILTIN = -31;
    public static final int ID_BUILTIN = -30;
    public static final int INDEX_OF_BUILTIN = -15;
    public static final int LANG_BUILTIN = -23;
    public static final int LAST_BUILTIN = -1;
    public static final int LOCAL_NAME_BUILTIN = -6;
    public static final int MAX_BUILTIN = -27;
    public static final int MIN_BUILTIN = -26;
    public static final int NAMESPACE_URI_BUILTIN = -7;
    public static final int NAME_BUILTIN = -24;
    public static final int NORMALIZE_SPACE_BUILTIN = -17;
    public static final int NUMBER_BUILTIN = -28;
    public static final int POSITION_BUILTIN = -2;
    public static final int RESOLVE_PREFIX_BUILTIN = -13;
    public static final int RESOLVE_URI_BUILTIN = -12;
    public static final int ROOT_BUILTIN = -32;
    public static final int STATIC_BASE_URI_BUILTIN = -14;
    public static final int STRING_BUILTIN = -16;
    public static final int UNORDERED_BUILTIN = -18;
    public static final int XS_QNAME_BUILTIN = -35;
    public static final int XS_QNAME_IGNORE_DEFAULT_BUILTIN = -36;
    public Namespace[] functionNamespacePath;
    private Declaration moduleDecl;
    public XQParser parser;
    public static final Declaration handleExtensionDecl = makeBuiltin("(extension)", -3);
    public static final Declaration castAsDecl = makeBuiltin("(cast as)", -33);
    public static final Declaration castableAsDecl = makeBuiltin("(castable as)", -34);
    public static final Declaration lastDecl = makeBuiltin("last", -1);
    public static final Declaration xsQNameDecl = makeBuiltin(Symbol.make(XQuery.SCHEMA_NAMESPACE, "QName"), -35);
    public static final Declaration xsQNameIgnoreDefaultDecl = makeBuiltin(Symbol.make(XQuery.SCHEMA_NAMESPACE, "(QName-ignore-default)"), -36);
    public static final Declaration staticBaseUriDecl = makeBuiltin("static-base-uri", -14);
    public static final Declaration resolvePrefixDecl = makeBuiltin(Symbol.make(XQuery.SCHEMA_NAMESPACE, "(resolve-prefix)"), -13);

    public static Declaration makeBuiltin(String name, int code) {
        return makeBuiltin(Symbol.make(XQuery.XQUERY_FUNCTION_NAMESPACE, name, "fn"), code);
    }

    public static Declaration makeBuiltin(Symbol name, int code) {
        Declaration decl = new Declaration(name);
        decl.setProcedureDecl(true);
        decl.setCode(code);
        return decl;
    }

    public XQResolveNames() {
        this(null);
    }

    void pushBuiltin(String name, int code) {
        this.lookup.push(makeBuiltin(name, code));
    }

    public XQResolveNames(Compilation comp) {
        super(comp);
        this.functionNamespacePath = XQuery.defaultFunctionNamespacePath;
        this.lookup.push(lastDecl);
        this.lookup.push(xsQNameDecl);
        this.lookup.push(staticBaseUriDecl);
        pushBuiltin("position", -2);
        pushBuiltin("compare", -4);
        pushBuiltin("distinct-values", -5);
        pushBuiltin("local-name", -6);
        pushBuiltin("name", -24);
        pushBuiltin("namespace-uri", -7);
        pushBuiltin("root", -32);
        pushBuiltin("base-uri", -11);
        pushBuiltin("lang", -23);
        pushBuiltin("resolve-uri", -12);
        pushBuiltin("collection", -8);
        pushBuiltin("doc", -9);
        pushBuiltin("document", -9);
        pushBuiltin("doc-available", -10);
        pushBuiltin("index-of", -15);
        pushBuiltin(PropertyTypeConstants.PROPERTY_TYPE_STRING, -16);
        pushBuiltin("normalize-space", -17);
        pushBuiltin("unordered", -18);
        pushBuiltin("deep-equal", -25);
        pushBuiltin("min", -26);
        pushBuiltin("max", -27);
        pushBuiltin("number", -28);
        pushBuiltin("default-collation", -29);
        pushBuiltin("id", -30);
        pushBuiltin("idref", -31);
    }

    @Override // gnu.expr.ResolveNames
    protected void push(ScopeExp exp) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            push(decl);
        }
    }

    void push(Declaration decl) {
        Compilation comp = getCompilation();
        Symbol name = decl.getSymbol();
        boolean function = decl.isProcedureDecl();
        if (name instanceof String) {
            int line = decl.getLineNumber();
            if (line > 0 && comp != null) {
                String saveFilename = comp.getFileName();
                int saveLine = comp.getLineNumber();
                int saveColumn = comp.getColumnNumber();
                comp.setLocation(decl);
                name = this.parser.namespaceResolve((String) name, function);
                comp.setLine(saveFilename, saveLine, saveColumn);
            } else {
                name = this.parser.namespaceResolve((String) name, function);
            }
            if (name != null) {
                decl.setName(name);
            } else {
                return;
            }
        }
        Declaration old = this.lookup.lookup(name, XQuery.instance.getNamespaceOf(decl));
        if (old != null) {
            if (decl.context == old.context) {
                ScopeExp.duplicateDeclarationError(old, decl, comp);
            } else if (XQParser.warnHidePreviousDeclaration && (!(name instanceof Symbol) || ((Symbol) name).getNamespace() != null)) {
                comp.error('w', decl, "declaration ", " hides previous declaration");
            }
        }
        this.lookup.push(decl);
    }

    Declaration flookup(Symbol sym) {
        Declaration decl;
        Environment env = XQuery.xqEnvironment;
        Location loc = env.lookup(sym, EnvironmentKey.FUNCTION);
        if (loc == null) {
            return null;
        }
        Location loc2 = loc.getBase();
        if (!(loc2 instanceof StaticFieldLocation) || (decl = ((StaticFieldLocation) loc2).getDeclaration()) == null) {
            Object val = loc2.get(null);
            if (val != null) {
                return procToDecl(sym, val);
            }
            return null;
        }
        return decl;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ResolveNames, gnu.expr.ExpVisitor
    public Expression visitReferenceExp(ReferenceExp exp, Void ignored) {
        return visitReferenceExp(exp, (ApplyExp) null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00e0, code lost:
        r14 = r22.parser.namespaceResolve(r10, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ec, code lost:
        if (r14 == null) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ee, code lost:
        r6 = r22.lookup.lookup(r14, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00fa, code lost:
        if (r6 != null) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00fc, code lost:
        if (r12 != false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00fe, code lost:
        if (r13 == false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0100, code lost:
        r17 = r14.getNamespaceURI();
        r16 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0110, code lost:
        if (gnu.xquery.lang.XQuery.SCHEMA_NAMESPACE.equals(r17) == false) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0112, code lost:
        r16 = gnu.xquery.lang.XQuery.getStandardType(r14.getName());
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x011a, code lost:
        if (r16 == null) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x013b, code lost:
        if (r13 == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0143, code lost:
        if (r17 != "") goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x014d, code lost:
        if (getCompilation().isPedantic() != false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x014f, code lost:
        r16 = kawa.standard.Scheme.string2Type(r14.getName());
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0158, code lost:
        if (r17 == null) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0164, code lost:
        if (r17.length() <= 6) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x016c, code lost:
        if (r17.startsWith("class:") == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x016e, code lost:
        r5 = gnu.bytecode.ClassType.make(r17.substring(6));
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0184, code lost:
        r6 = flookup(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:?, code lost:
        return new gnu.expr.QuoteExp(r16).setLine(r23);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:?, code lost:
        return gnu.kawa.functions.CompileNamedPart.makeExp(r5, r14.getName());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected gnu.expr.Expression visitReferenceExp(gnu.expr.ReferenceExp r23, gnu.expr.ApplyExp r24) {
        /*
            Method dump skipped, instructions count: 526
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.lang.XQResolveNames.visitReferenceExp(gnu.expr.ReferenceExp, gnu.expr.ApplyExp):gnu.expr.Expression");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ResolveNames, gnu.expr.ExpVisitor
    public Expression visitSetExp(SetExp exp, Void ignored) {
        Expression result = super.visitSetExp(exp, ignored);
        Declaration decl = exp.getBinding();
        if (decl != null && !getCompilation().immediate) {
            Object name = decl.getSymbol();
            if ((name instanceof Symbol) && XQuery.LOCAL_NAMESPACE.equals(((Symbol) name).getNamespaceURI())) {
                Expression new_value = exp.getNewValue();
                if (!(new_value instanceof ApplyExp) || ((ApplyExp) new_value).getFunction() != XQParser.getExternalFunction) {
                    decl.setFlag(16777216L);
                    decl.setPrivate(true);
                }
            }
        }
        return result;
    }

    private Expression visitStatements(Expression exp) {
        if (exp instanceof BeginExp) {
            BeginExp bbody = (BeginExp) exp;
            Expression[] exps = bbody.getExpressions();
            int nexps = bbody.getExpressionCount();
            for (int i = 0; i < nexps; i++) {
                exps[i] = visitStatements(exps[i]);
            }
            return exp;
        } else if (exp instanceof SetExp) {
            Declaration decl = this.moduleDecl;
            SetExp sexp = (SetExp) exp;
            Expression exp2 = visitSetExp(sexp, (Void) null);
            if (sexp.isDefining() && sexp.getBinding() == decl) {
                if (!decl.isProcedureDecl()) {
                    push(decl);
                }
                decl = decl.nextDecl();
            }
            this.moduleDecl = decl;
            return exp2;
        } else {
            return visit(exp, null);
        }
    }

    @Override // gnu.expr.ResolveNames
    public void resolveModule(ModuleExp exp) {
        this.currentLambda = exp;
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.isProcedureDecl()) {
                push(decl);
            }
        }
        this.moduleDecl = exp.firstDecl();
        exp.body = visitStatements(exp.body);
        for (Declaration decl2 = exp.firstDecl(); decl2 != null; decl2 = decl2.nextDecl()) {
            if (decl2.getSymbol() != null) {
                this.lookup.removeSubsumed(decl2);
            }
        }
    }

    Expression getCollator(Expression[] args, int argno) {
        if (args != null && args.length > argno) {
            return new ApplyExp(ClassType.make("gnu.xquery.util.NamedCollator").getDeclaredMethod("find", 1), args[argno]);
        }
        NamedCollator coll = this.parser.defaultCollator;
        return coll == null ? QuoteExp.nullExp : new QuoteExp(coll);
    }

    Expression withCollator(Method method, Expression[] args, String name, int minArgs) {
        return withCollator(new QuoteExp(new PrimProcedure(method)), args, name, minArgs);
    }

    Expression withCollator(Expression function, Expression[] args, String name, int minArgs) {
        String err = WrongArguments.checkArgCount(name, minArgs, minArgs + 1, args.length);
        if (err != null) {
            return getCompilation().syntaxError(err);
        }
        Expression[] xargs = new Expression[minArgs + 1];
        System.arraycopy(args, 0, xargs, 0, minArgs);
        xargs[minArgs] = getCollator(args, minArgs);
        return new ApplyExp(function, xargs);
    }

    Expression withContext(Method method, Expression[] args, String name, int minArgs) {
        String err = WrongArguments.checkArgCount(name, minArgs, minArgs + 1, args.length);
        if (err != null) {
            return getCompilation().syntaxError(err);
        }
        if (args.length == minArgs) {
            Expression[] xargs = new Expression[minArgs + 1];
            System.arraycopy(args, 0, xargs, 0, minArgs);
            Declaration dot = this.lookup.lookup((Object) XQParser.DOT_VARNAME, false);
            if (dot == null) {
                String message = "undefined context for " + name;
                this.messages.error('e', message, "XPDY0002");
                return new ErrorExp(message);
            }
            xargs[minArgs] = new ReferenceExp(dot);
            args = xargs;
        }
        return new ApplyExp(method, args);
    }

    private Expression checkArgCount(Expression[] args, Declaration decl, int min, int max) {
        String err = WrongArguments.checkArgCount("fn:" + decl.getName(), min, max, args.length);
        if (err == null) {
            return null;
        }
        return getCompilation().syntaxError(err);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitApplyExp(ApplyExp exp, Void ignored) {
        Expression func;
        Symbol sym;
        Declaration decl;
        int code;
        String mname;
        Expression func2;
        Expression func3 = exp.getFunction();
        NamespaceBinding namespaceSave = this.parser.constructorNamespaces;
        Object proc = exp.getFunctionValue();
        if (proc instanceof MakeElement) {
            MakeElement mk = (MakeElement) proc;
            NamespaceBinding nschain = NamespaceBinding.nconc(mk.getNamespaceNodes(), namespaceSave);
            mk.setNamespaceNodes(nschain);
            this.parser.constructorNamespaces = nschain;
        }
        if (func3 instanceof ReferenceExp) {
            func = visitReferenceExp((ReferenceExp) func3, exp);
        } else {
            func = visit(func3, ignored);
        }
        exp.setFunction(func);
        visitExps(exp.getArgs(), ignored);
        this.parser.constructorNamespaces = namespaceSave;
        Expression func4 = exp.getFunction();
        if ((func4 instanceof ReferenceExp) && (decl = ((ReferenceExp) func4).getBinding()) != null && (code = decl.getCode()) < 0) {
            switch (code) {
                case XS_QNAME_IGNORE_DEFAULT_BUILTIN /* -36 */:
                case XS_QNAME_BUILTIN /* -35 */:
                    Expression[] args = exp.getArgs();
                    Expression err = checkArgCount(args, decl, 1, 1);
                    if (err == null) {
                        NamespaceBinding constructorNamespaces = this.parser.constructorNamespaces;
                        if (code == -36) {
                            constructorNamespaces = new NamespaceBinding(null, "", constructorNamespaces);
                        }
                        if (args[0] instanceof QuoteExp) {
                            try {
                                return new QuoteExp(QNameUtils.resolveQName(((QuoteExp) args[0]).getValue(), constructorNamespaces, this.parser.prologNamespaces));
                            } catch (RuntimeException ex) {
                                return getCompilation().syntaxError(ex.getMessage());
                            }
                        }
                        Expression[] xargs = {args[0], new QuoteExp(constructorNamespaces), new QuoteExp(this.parser.prologNamespaces)};
                        Method meth = ClassType.make("gnu.xquery.util.QNameUtils").getDeclaredMethod("resolveQName", 3);
                        ApplyExp app = new ApplyExp(meth, xargs);
                        app.setFlag(4);
                        return app;
                    }
                    return err;
                case CASTABLE_AS_BUILTIN /* -34 */:
                case CAST_AS_BUILTIN /* -33 */:
                    Expression[] args2 = exp.getArgs();
                    Expression texp = args2[code == -33 ? (char) 0 : (char) 1];
                    Expression qexp = texp;
                    if (texp instanceof ApplyExp) {
                        ApplyExp taexp = (ApplyExp) texp;
                        if (taexp.getFunction().valueIfConstant() == XQParser.proc_OccurrenceType_getInstance) {
                            qexp = taexp.getArg(0);
                        }
                    }
                    Object value = qexp.valueIfConstant();
                    String msg = null;
                    if (value == SingletonType.getInstance()) {
                        msg = "type to 'cast as' or 'castable as' must be atomic";
                    } else if (value == XDataType.anyAtomicType) {
                        msg = "type to 'cast as' or 'castable as' cannot be anyAtomicType";
                    } else if (value == XDataType.anySimpleType) {
                        msg = "type to 'cast as' or 'castable as' cannot be anySimpleType";
                    } else if (value == XDataType.untypedType) {
                        msg = "type to 'cast as' or 'castable as' cannot be untyped";
                    } else if (value == XDataType.NotationType) {
                        msg = "type to 'cast as' or 'castable as' cannot be NOTATION";
                    }
                    if (msg != null) {
                        this.messages.error('e', texp, msg, "XPST0080");
                    }
                    boolean toQName = value == Compilation.typeSymbol && !(texp instanceof ApplyExp);
                    if (code == -33) {
                        if (toQName) {
                            return visitApplyExp(XQParser.castQName(args2[1], true), ignored);
                        }
                        func2 = XQParser.makeFunctionExp("gnu.xquery.util.CastAs", "castAs");
                    } else if (toQName && (args2[0] instanceof QuoteExp)) {
                        try {
                            QNameUtils.resolveQName(((QuoteExp) args2[0]).getValue(), this.parser.constructorNamespaces, this.parser.prologNamespaces);
                            return XQuery.trueExp;
                        } catch (RuntimeException e) {
                            return XQuery.falseExp;
                        }
                    } else {
                        func2 = XQParser.makeFunctionExp("gnu.xquery.lang.XQParser", "castableAs");
                    }
                    return new ApplyExp(func2, args2).setLine(exp);
                case ROOT_BUILTIN /* -32 */:
                    Method meth2 = ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("root", 1);
                    return withContext(meth2, exp.getArgs(), "fn:root", 0);
                case IDREF_BUILTIN /* -31 */:
                    Method meth3 = ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("idref", 2);
                    return withContext(meth3, exp.getArgs(), "fn:idref", 1);
                case ID_BUILTIN /* -30 */:
                    Method meth4 = ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("id$X", 3);
                    return withContext(meth4, exp.getArgs(), "fn:id", 1);
                case DEFAULT_COLLATION_BUILTIN /* -29 */:
                    Expression err2 = checkArgCount(exp.getArgs(), decl, 0, 0);
                    if (err2 == null) {
                        NamedCollator coll = this.parser.defaultCollator;
                        return QuoteExp.getInstance(coll != null ? coll.getName() : NamedCollator.UNICODE_CODEPOINT_COLLATION);
                    }
                    return err2;
                case NUMBER_BUILTIN /* -28 */:
                    Method meth5 = ClassType.make("gnu.xquery.util.NumberValue").getDeclaredMethod("numberValue", 1);
                    return withContext(meth5, exp.getArgs(), "fn:number", 0);
                case MAX_BUILTIN /* -27 */:
                    Method meth6 = ClassType.make("gnu.xquery.util.MinMax").getDeclaredMethod("max", 2);
                    return withCollator(meth6, exp.getArgs(), "fn:max", 1);
                case MIN_BUILTIN /* -26 */:
                    Method meth7 = ClassType.make("gnu.xquery.util.MinMax").getDeclaredMethod("min", 2);
                    return withCollator(meth7, exp.getArgs(), "fn:min", 1);
                case DEEP_EQUAL_BUILTIN /* -25 */:
                    Method meth8 = ClassType.make("gnu.xquery.util.SequenceUtils").getDeclaredMethod("deepEqual", 3);
                    return withCollator(meth8, exp.getArgs(), "fn:deep-equal", 2);
                case NAME_BUILTIN /* -24 */:
                    Method meth9 = ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("name", 1);
                    return withContext(meth9, exp.getArgs(), "fn:name", 0);
                case LANG_BUILTIN /* -23 */:
                    Method meth10 = ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("lang", 2);
                    return withContext(meth10, exp.getArgs(), "fn:lang", 1);
                case UNORDERED_BUILTIN /* -18 */:
                    Expression[] args3 = exp.getArgs();
                    Expression err3 = checkArgCount(args3, decl, 1, 1);
                    if (err3 == null) {
                        return args3[0];
                    }
                    return err3;
                case NORMALIZE_SPACE_BUILTIN /* -17 */:
                    Method meth11 = ClassType.make("gnu.xquery.util.StringUtils").getDeclaredMethod("normalizeSpace", 1);
                    return withContext(meth11, exp.getArgs(), "fn:normalize-space", 0);
                case STRING_BUILTIN /* -16 */:
                    return withContext(ClassType.make("gnu.xml.TextUtils").getDeclaredMethod("asString", 1), exp.getArgs(), "fn:string", 0);
                case INDEX_OF_BUILTIN /* -15 */:
                    Method meth12 = ClassType.make("gnu.xquery.util.SequenceUtils").getDeclaredMethod("indexOf$X", 4);
                    return withCollator(meth12, exp.getArgs(), "fn:index-of", 2);
                case STATIC_BASE_URI_BUILTIN /* -14 */:
                    Expression err4 = checkArgCount(exp.getArgs(), decl, 0, 0);
                    if (err4 == null) {
                        return getBaseUriExpr();
                    }
                    return err4;
                case RESOLVE_PREFIX_BUILTIN /* -13 */:
                    Expression[] args4 = exp.getArgs();
                    Expression err5 = checkArgCount(args4, decl, 1, 1);
                    if (err5 == null) {
                        if (args4[0] instanceof QuoteExp) {
                            Object val = ((QuoteExp) args4[0]).getValue();
                            String prefix = val == null ? null : val.toString();
                            Object val2 = QNameUtils.lookupPrefix(prefix, this.parser.constructorNamespaces, this.parser.prologNamespaces);
                            if (val2 == null) {
                                return getCompilation().syntaxError("unknown namespace prefix '" + prefix + "'");
                            }
                            return new QuoteExp(val2);
                        }
                        Expression[] xargs2 = {args4[0], new QuoteExp(this.parser.constructorNamespaces), new QuoteExp(this.parser.prologNamespaces)};
                        PrimProcedure pproc = new PrimProcedure(ClassType.make("gnu.xquery.util.QNameUtils").getDeclaredMethod("resolvePrefix", 3));
                        ApplyExp app2 = new ApplyExp(pproc, xargs2);
                        app2.setFlag(4);
                        return app2;
                    }
                    return err5;
                case RESOLVE_URI_BUILTIN /* -12 */:
                    Expression[] args5 = exp.getArgs();
                    Expression err6 = checkArgCount(args5, decl, 1, 2);
                    if (err6 == null) {
                        Expression[] margs = new Expression[2];
                        margs[0] = args5[0];
                        if (args5.length == 1) {
                            margs[1] = getBaseUriExpr();
                        } else {
                            margs[1] = args5[1];
                        }
                        Method meth13 = ClassType.make("gnu.xquery.util.QNameUtils").getDeclaredMethod("resolveURI", 2);
                        return new ApplyExp(meth13, margs);
                    }
                    return err6;
                case BASE_URI_BUILTIN /* -11 */:
                    Method meth14 = ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("baseUri", 1);
                    return withContext(meth14, exp.getArgs(), "fn:base-uri", 0);
                case DOC_AVAILABLE_BUILTIN /* -10 */:
                case DOC_BUILTIN /* -9 */:
                    Expression[] args6 = exp.getArgs();
                    ClassType cl = ClassType.make("gnu.xquery.util.NodeUtils");
                    if (code == -9) {
                        mname = "docCached";
                        if (XQParser.warnOldVersion && "document".equals(decl.getName())) {
                            getCompilation().error('w', "replace 'document' by 'doc'");
                        }
                    } else {
                        mname = "availableCached";
                    }
                    Method meth15 = cl.getDeclaredMethod(mname, 2);
                    Expression err7 = checkArgCount(args6, decl, 1, 1);
                    if (err7 == null) {
                        Expression base = getBaseUriExpr();
                        ApplyExp aexp = new ApplyExp(meth15, args6[0], base);
                        if (code == -9) {
                            aexp.setType(NodeType.documentNodeTest);
                        } else {
                            aexp.setType(XDataType.booleanType);
                        }
                        return aexp;
                    }
                    return err7;
                case COLLECTION_BUILTIN /* -8 */:
                    Expression[] args7 = exp.getArgs();
                    ClassType cl2 = ClassType.make("gnu.xquery.util.NodeUtils");
                    Method meth16 = cl2.getDeclaredMethod("collection", 2);
                    Expression err8 = checkArgCount(args7, decl, 0, 1);
                    if (err8 == null) {
                        Expression base2 = getBaseUriExpr();
                        Expression uri = args7.length > 0 ? args7[0] : QuoteExp.voidExp;
                        ApplyExp aexp2 = new ApplyExp(meth16, uri, base2);
                        aexp2.setType(NodeType.documentNodeTest);
                        return aexp2;
                    }
                    return err8;
                case NAMESPACE_URI_BUILTIN /* -7 */:
                    Method meth17 = ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("namespaceURI", 1);
                    return withContext(meth17, exp.getArgs(), "fn:namespace-uri", 0);
                case LOCAL_NAME_BUILTIN /* -6 */:
                    Method meth18 = ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("localName", 1);
                    return withContext(meth18, exp.getArgs(), "fn:local-name", 0);
                case DISTINCT_VALUES_BUILTIN /* -5 */:
                    Method meth19 = ClassType.make("gnu.xquery.util.DistinctValues").getDeclaredMethod("distinctValues$X", 3);
                    return withCollator(meth19, exp.getArgs(), "fn:distinct-values", 1);
                case -4:
                    Method meth20 = ClassType.make("gnu.xquery.util.StringUtils").getDeclaredMethod("compare", 3);
                    return withCollator(meth20, exp.getArgs(), "fn:compare", 2);
                case -3:
                    Compilation comp = getCompilation();
                    Expression[] args8 = exp.getArgs();
                    int i = 0;
                    while (i < args8.length - 1) {
                        Expression pname = args8[i];
                        String qname = (String) ((QuoteExp) pname).getValue();
                        Symbol psymbol = this.parser.namespaceResolve(qname, false);
                        if (psymbol != null) {
                            if (psymbol.getNamespaceURI().length() == 0) {
                                comp.error('e', "pragma name cannot be in the empty namespace");
                            } else {
                                Expression replacement = checkPragma(psymbol, args8[i + 1]);
                                if (replacement != null) {
                                    return replacement;
                                }
                            }
                        }
                        i += 2;
                    }
                    if (i < args8.length) {
                        return args8[args8.length - 1];
                    }
                    getMessages().error('e', "no recognized pragma or default in extension expression", "XQST0079");
                    return new ErrorExp("no recognized pragma or default in extension expression");
                case -2:
                case -1:
                    Symbol sym2 = code == -1 ? XQParser.LAST_VARNAME : XQParser.POSITION_VARNAME;
                    Declaration decl2 = this.lookup.lookup((Object) sym2, false);
                    if (decl2 == null) {
                        error('e', "undefined context for " + sym2.getName());
                    } else {
                        decl2.setCanRead(true);
                    }
                    return new ReferenceExp(sym2, decl2);
            }
        }
        Object proc2 = exp.getFunctionValue();
        if (proc2 instanceof Type) {
            Expression[] args9 = exp.getArgs();
            if (args9.length != 1) {
                this.messages.error('e', "type constructor requires a single argument");
                return exp;
            }
            return new ApplyExp(XQParser.makeFunctionExp("gnu.xquery.util.CastAs", "castAs"), exp.getFunction(), args9[0]);
        }
        if (proc2 instanceof MakeElement) {
            MakeElement make = (MakeElement) proc2;
            NamespaceBinding nsBindings = make.getNamespaceNodes();
            Symbol tag = make.tag;
            if (tag == null) {
                tag = MakeElement.getTagName(exp);
            }
            NamespaceBinding nsBindings2 = maybeAddNamespace(tag, false, nsBindings);
            Expression[] args10 = exp.getArgs();
            Symbol[] attrSyms = new Symbol[args10.length];
            int nattrSyms = 0;
            for (Expression arg : args10) {
                if (arg instanceof ApplyExp) {
                    ApplyExp app3 = (ApplyExp) arg;
                    if (app3.getFunction() == MakeAttribute.makeAttributeExp && (sym = MakeElement.getTagName(app3)) != null) {
                        for (int j = 0; j != nattrSyms; j++) {
                            if (sym.equals(attrSyms[j])) {
                                getCompilation().setLine((Expression) app3);
                                Symbol elementSym = MakeElement.getTagName(exp);
                                String elementName = elementSym == null ? null : elementSym.toString();
                                this.messages.error('e', XMLFilter.duplicateAttributeMessage(sym, elementName), "XQST0040");
                            }
                        }
                        attrSyms[nattrSyms] = sym;
                        nsBindings2 = maybeAddNamespace(sym, true, nsBindings2);
                        nattrSyms++;
                    }
                }
            }
            if (nsBindings2 != null) {
                make.setNamespaceNodes(nsBindings2);
            }
        }
        return exp;
    }

    public Expression checkPragma(Symbol name, Expression contents) {
        return null;
    }

    Expression getBaseUriExpr() {
        Compilation comp = getCompilation();
        String staticBaseUri = this.parser.getStaticBaseUri();
        return staticBaseUri != null ? QuoteExp.getInstance(staticBaseUri) : GetModuleClass.getModuleClassURI(comp);
    }

    static NamespaceBinding maybeAddNamespace(Symbol qname, boolean isAttribute, NamespaceBinding bindings) {
        if (qname != null) {
            String prefix = qname.getPrefix();
            String uri = qname.getNamespaceURI();
            if (prefix == "") {
                prefix = null;
            }
            if (uri == "") {
                uri = null;
            }
            return (isAttribute && prefix == null && uri == null) ? bindings : NamespaceBinding.maybeAdd(prefix, uri, bindings);
        }
        return bindings;
    }

    static Declaration procToDecl(Object symbol, Object val) {
        Declaration decl = new Declaration(symbol);
        decl.setProcedureDecl(true);
        decl.noteValue(new QuoteExp(val));
        decl.setFlag(JSONzip.int14);
        return decl;
    }
}
