package kawa.lang;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Special;
import gnu.kawa.functions.AppendValues;
import gnu.kawa.functions.CompileNamedPart;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.xml.MakeAttribute;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import gnu.xml.NamespaceBinding;
import java.util.Stack;
import java.util.Vector;
import kawa.standard.begin;
import kawa.standard.require;

/* loaded from: classes.dex */
public class Translator extends Compilation {
    private static Expression errorExp;
    public static final Declaration getNamedPartDecl = Declaration.getDeclarationFromStatic("gnu.kawa.functions.GetNamedPart", "getNamedPart");
    public LambdaExp curMethodLambda;
    public Macro currentMacroDefinition;
    Syntax currentSyntax;
    private Environment env;
    public int firstForm;
    public Stack formStack;
    Declaration macroContext;
    public Declaration matchArray;
    Vector notedAccess;
    public PatternScope patternScope;
    public Object pendingForm;
    PairWithPosition positionPair;
    Stack renamedAliasStack;
    public Declaration templateScopeDecl;
    public NamespaceBinding xmlElementNamespaces;

    static {
        LispLanguage.getNamedPartLocation.setDeclaration(getNamedPartDecl);
        errorExp = new ErrorExp("unknown syntax error");
    }

    public Translator(Language language, SourceMessages messages, NameLookup lexical) {
        super(language, messages, lexical);
        this.formStack = new Stack();
        this.xmlElementNamespaces = NamespaceBinding.predefinedXML;
        this.env = Environment.getCurrent();
    }

    public final Environment getGlobalEnvironment() {
        return this.env;
    }

    @Override // gnu.expr.Compilation
    public Expression parse(Object input) {
        return rewrite(input);
    }

    public final Expression rewrite_car(Pair pair, SyntaxForm syntax) {
        if (syntax == null || syntax.getScope() == this.current_scope || (pair.getCar() instanceof SyntaxForm)) {
            return rewrite_car(pair, false);
        }
        ScopeExp save_scope = this.current_scope;
        try {
            setCurrentScope(syntax.getScope());
            return rewrite_car(pair, false);
        } finally {
            setCurrentScope(save_scope);
        }
    }

    public final Expression rewrite_car(Pair pair, boolean function) {
        Object car = pair.getCar();
        return pair instanceof PairWithPosition ? rewrite_with_position(car, function, (PairWithPosition) pair) : rewrite(car, function);
    }

    public Syntax getCurrentSyntax() {
        return this.currentSyntax;
    }

    Expression apply_rewrite(Syntax syntax, Pair form) {
        Expression expression = errorExp;
        Syntax saveSyntax = this.currentSyntax;
        this.currentSyntax = syntax;
        try {
            Expression exp = syntax.rewriteForm(form, this);
            return exp;
        } finally {
            this.currentSyntax = saveSyntax;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ReferenceExp getOriginalRef(Declaration decl) {
        if (decl != null && decl.isAlias() && !decl.isIndirectBinding()) {
            Expression value = decl.getValue();
            if (value instanceof ReferenceExp) {
                return (ReferenceExp) value;
            }
        }
        return null;
    }

    public final boolean selfEvaluatingSymbol(Object obj) {
        return ((LispLanguage) getLanguage()).selfEvaluatingSymbol(obj);
    }

    public final boolean matches(Object form, String literal) {
        return matches(form, (SyntaxForm) null, literal);
    }

    public boolean matches(Object form, SyntaxForm syntax, String literal) {
        ReferenceExp rexp;
        if (syntax != null) {
        }
        if (form instanceof SyntaxForm) {
            form = ((SyntaxForm) form).getDatum();
        }
        if ((form instanceof SimpleSymbol) && !selfEvaluatingSymbol(form) && (rexp = getOriginalRef(this.lexical.lookup(form, -1))) != null) {
            form = rexp.getSymbol();
        }
        return (form instanceof SimpleSymbol) && ((Symbol) form).getLocalPart() == literal;
    }

    public boolean matches(Object form, SyntaxForm syntax, Symbol literal) {
        ReferenceExp rexp;
        if (syntax != null) {
        }
        if (form instanceof SyntaxForm) {
            form = ((SyntaxForm) form).getDatum();
        }
        if ((form instanceof SimpleSymbol) && !selfEvaluatingSymbol(form) && (rexp = getOriginalRef(this.lexical.lookup(form, -1))) != null) {
            form = rexp.getSymbol();
        }
        return form == literal;
    }

    public Object matchQuoted(Pair pair) {
        if (matches(pair.getCar(), LispLanguage.quote_sym) && (pair.getCdr() instanceof Pair)) {
            Pair pair2 = (Pair) pair.getCdr();
            if (pair2.getCdr() == LList.Empty) {
                return pair2.getCar();
            }
        }
        return null;
    }

    @Override // gnu.expr.Compilation
    public Declaration lookup(Object name, int namespace) {
        Declaration decl = this.lexical.lookup(name, namespace);
        return (decl == null || !getLanguage().hasNamespace(decl, namespace)) ? currentModule().lookup(name, getLanguage(), namespace) : decl;
    }

    public Declaration lookupGlobal(Object name) {
        return lookupGlobal(name, -1);
    }

    public Declaration lookupGlobal(Object name, int namespace) {
        ModuleExp module = currentModule();
        Declaration decl = module.lookup(name, getLanguage(), namespace);
        if (decl == null) {
            Declaration decl2 = module.getNoDefine(name);
            decl2.setIndirectBinding(true);
            return decl2;
        }
        return decl;
    }

    Syntax check_if_Syntax(Declaration decl) {
        Declaration d = Declaration.followAliases(decl);
        Object obj = null;
        Expression dval = d.getValue();
        if (dval != null && d.getFlag(32768L)) {
            try {
                if (decl.getValue() instanceof ReferenceExp) {
                    Declaration context = ((ReferenceExp) decl.getValue()).contextDecl();
                    if (context != null) {
                        this.macroContext = context;
                    } else if (this.current_scope instanceof TemplateScope) {
                        this.macroContext = ((TemplateScope) this.current_scope).macroContext;
                    }
                } else if (this.current_scope instanceof TemplateScope) {
                    this.macroContext = ((TemplateScope) this.current_scope).macroContext;
                }
                obj = dval.eval(this.env);
            } catch (Throwable ex) {
                ex.printStackTrace();
                error('e', "unable to evaluate macro for " + decl.getSymbol());
            }
        } else if (decl.getFlag(32768L) && !decl.needsContext()) {
            StaticFieldLocation loc = StaticFieldLocation.make(decl);
            obj = loc.get(null);
        }
        if (obj instanceof Syntax) {
            return (Syntax) obj;
        }
        return null;
    }

    public Expression rewrite_pair(Pair p, boolean function) {
        Symbol symbol;
        Expression func = rewrite_car(p, true);
        if (func instanceof QuoteExp) {
            Object proc = func.valueIfConstant();
            if (proc instanceof Syntax) {
                return apply_rewrite((Syntax) proc, p);
            }
        }
        if (func instanceof ReferenceExp) {
            ReferenceExp ref = (ReferenceExp) func;
            Declaration decl = ref.getBinding();
            if (decl == null) {
                Object sym = ref.getSymbol();
                if ((sym instanceof Symbol) && !selfEvaluatingSymbol(sym)) {
                    symbol = (Symbol) sym;
                    symbol.getName();
                } else {
                    String name = sym.toString();
                    symbol = this.env.getSymbol(name);
                }
                Object proc2 = this.env.get(symbol, getLanguage().hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null, null);
                if (proc2 instanceof Syntax) {
                    return apply_rewrite((Syntax) proc2, p);
                }
                if (proc2 instanceof AutoloadProcedure) {
                    try {
                        ((AutoloadProcedure) proc2).getLoaded();
                    } catch (RuntimeException e) {
                    }
                }
            } else {
                Declaration saveContext = this.macroContext;
                Syntax syntax = check_if_Syntax(decl);
                if (syntax != null) {
                    Expression apply_rewrite = apply_rewrite(syntax, p);
                    this.macroContext = saveContext;
                    return apply_rewrite;
                }
            }
            ref.setProcedureName(true);
            if (getLanguage().hasSeparateFunctionNamespace()) {
                func.setFlag(8);
            }
        }
        Object cdr = p.getCdr();
        int cdr_length = listLength(cdr);
        if (cdr_length == -1) {
            return syntaxError("circular list is not allowed after " + p.getCar());
        }
        if (cdr_length < 0) {
            return syntaxError("dotted list [" + cdr + "] is not allowed after " + p.getCar());
        }
        boolean mapKeywordsToAttributes = false;
        Stack vec = new Stack();
        ScopeExp save_scope = this.current_scope;
        int i = 0;
        while (i < cdr_length) {
            if (cdr instanceof SyntaxForm) {
                SyntaxForm sf = (SyntaxForm) cdr;
                cdr = sf.getDatum();
                setCurrentScope(sf.getScope());
            }
            Pair cdr_pair = (Pair) cdr;
            Expression arg = rewrite_car(cdr_pair, false);
            i++;
            if (mapKeywordsToAttributes) {
                if ((i & 1) == 0) {
                    Expression[] aargs = {(Expression) vec.pop(), arg};
                    arg = new ApplyExp(MakeAttribute.makeAttribute, aargs);
                } else {
                    if (arg instanceof QuoteExp) {
                        Object value = ((QuoteExp) arg).getValue();
                        if ((value instanceof Keyword) && i < cdr_length) {
                            arg = new QuoteExp(((Keyword) value).asSymbol());
                        }
                    }
                    mapKeywordsToAttributes = false;
                }
            }
            vec.addElement(arg);
            cdr = cdr_pair.getCdr();
        }
        Expression[] args = new Expression[vec.size()];
        vec.copyInto(args);
        if (save_scope != this.current_scope) {
            setCurrentScope(save_scope);
        }
        if ((func instanceof ReferenceExp) && ((ReferenceExp) func).getBinding() == getNamedPartDecl) {
            Expression part1 = args[0];
            Expression part2 = args[1];
            Symbol sym2 = namespaceResolve(part1, part2);
            if (sym2 != null) {
                return rewrite(sym2, function);
            }
            return CompileNamedPart.makeExp(part1, part2);
        }
        return ((LispLanguage) getLanguage()).makeApply(func, args);
    }

    public Namespace namespaceResolvePrefix(Expression context) {
        Object val;
        if (context instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) context;
            Declaration decl = rexp.getBinding();
            if (decl == null || decl.getFlag(65536L)) {
                Object rsym = rexp.getSymbol();
                Symbol sym = rsym instanceof Symbol ? (Symbol) rsym : this.env.getSymbol(rsym.toString());
                val = this.env.get(sym, (Object) null);
            } else if (decl.isNamespaceDecl()) {
                val = decl.getConstantValue();
            } else {
                val = null;
            }
            if (val instanceof Namespace) {
                Namespace ns = (Namespace) val;
                String uri = ns.getName();
                if (uri == null || !uri.startsWith("class:")) {
                    return ns;
                }
                return null;
            }
        }
        return null;
    }

    public Symbol namespaceResolve(Namespace ns, Expression member) {
        if (ns == null || !(member instanceof QuoteExp)) {
            return null;
        }
        String mem = ((QuoteExp) member).getValue().toString().intern();
        return ns.getSymbol(mem);
    }

    public Symbol namespaceResolve(Expression context, Expression member) {
        return namespaceResolve(namespaceResolvePrefix(context), member);
    }

    public static Object stripSyntax(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        return obj;
    }

    public static Object safeCar(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        if (obj instanceof Pair) {
            return stripSyntax(((Pair) obj).getCar());
        }
        return null;
    }

    public static Object safeCdr(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        if (obj instanceof Pair) {
            return stripSyntax(((Pair) obj).getCdr());
        }
        return null;
    }

    public static int listLength(Object obj) {
        int n = 0;
        Object slow = obj;
        Object fast = obj;
        while (true) {
            if (fast instanceof SyntaxForm) {
                fast = ((SyntaxForm) fast).getDatum();
            } else {
                while (slow instanceof SyntaxForm) {
                    slow = ((SyntaxForm) slow).getDatum();
                }
                if (fast == LList.Empty) {
                    return n;
                }
                if (!(fast instanceof Pair)) {
                    return (-1) - n;
                }
                int n2 = n + 1;
                Object next = ((Pair) fast).getCdr();
                while (next instanceof SyntaxForm) {
                    next = ((SyntaxForm) next).getDatum();
                }
                if (next == LList.Empty) {
                    return n2;
                }
                if (!(next instanceof Pair)) {
                    return (-1) - n2;
                }
                slow = ((Pair) slow).getCdr();
                fast = ((Pair) next).getCdr();
                n = n2 + 1;
                if (fast == slow) {
                    return Integer.MIN_VALUE;
                }
            }
        }
    }

    public void rewriteInBody(Object exp) {
        if (exp instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) exp;
            ScopeExp save_scope = this.current_scope;
            try {
                setCurrentScope(sf.getScope());
                rewriteInBody(sf.getDatum());
            } finally {
                setCurrentScope(save_scope);
            }
        } else if (exp instanceof Values) {
            Object[] vals = ((Values) exp).getValues();
            for (Object obj : vals) {
                rewriteInBody(obj);
            }
        } else {
            this.formStack.add(rewrite(exp, false));
        }
    }

    public Expression rewrite(Object exp) {
        return rewrite(exp, false);
    }

    public Object namespaceResolve(Object name) {
        if (!(name instanceof SimpleSymbol) && (name instanceof Pair)) {
            Pair p = (Pair) name;
            if (safeCar(p) == LispLanguage.lookup_sym && (p.getCdr() instanceof Pair)) {
                Pair p2 = (Pair) p.getCdr();
                if (p2.getCdr() instanceof Pair) {
                    Expression part1 = rewrite(p2.getCar());
                    Expression part2 = rewrite(((Pair) p2.getCdr()).getCar());
                    Symbol sym = namespaceResolve(part1, part2);
                    if (sym == null) {
                        String combinedName = CompileNamedPart.combineName(part1, part2);
                        if (combinedName != null) {
                            return Namespace.EmptyNamespace.getSymbol(combinedName);
                        }
                    } else {
                        return sym;
                    }
                }
            }
        }
        return name;
    }

    /* JADX WARN: Code restructure failed: missing block: B:125:0x02c6, code lost:
        if ((r16 instanceof gnu.bytecode.ArrayClassLoader) == false) goto L81;
     */
    /* JADX WARN: Removed duplicated region for block: B:53:0x011e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public gnu.expr.Expression rewrite(java.lang.Object r36, boolean r37) {
        /*
            Method dump skipped, instructions count: 971
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.rewrite(java.lang.Object, boolean):gnu.expr.Expression");
    }

    public static void setLine(Expression exp, Object location) {
        if (location instanceof SourceLocator) {
            exp.setLocation((SourceLocator) location);
        }
    }

    public static void setLine(Declaration decl, Object location) {
        if (location instanceof SourceLocator) {
            decl.setLocation((SourceLocator) location);
        }
    }

    public Object pushPositionOf(Object pair) {
        PairWithPosition saved;
        if (pair instanceof SyntaxForm) {
            pair = ((SyntaxForm) pair).getDatum();
        }
        if (!(pair instanceof PairWithPosition)) {
            return null;
        }
        PairWithPosition ppair = (PairWithPosition) pair;
        if (this.positionPair == null || this.positionPair.getFileName() != getFileName() || this.positionPair.getLineNumber() != getLineNumber() || this.positionPair.getColumnNumber() != getColumnNumber()) {
            saved = new PairWithPosition(this, Special.eof, this.positionPair);
        } else {
            saved = this.positionPair;
        }
        setLine(pair);
        this.positionPair = ppair;
        return saved;
    }

    public void popPositionOf(Object saved) {
        if (saved != null) {
            setLine(saved);
            this.positionPair = (PairWithPosition) saved;
            if (this.positionPair.getCar() == Special.eof) {
                this.positionPair = (PairWithPosition) this.positionPair.getCdr();
            }
        }
    }

    public void setLineOf(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            exp.setLocation(this);
        }
    }

    public Type exp2Type(Pair typeSpecPair) {
        Object saved = pushPositionOf(typeSpecPair);
        try {
            Expression texp = InlineCalls.inlineCalls(rewrite_car(typeSpecPair, false), this);
            if (!(texp instanceof ErrorExp)) {
                Type type = getLanguage().getTypeFor(texp);
                if (type == null) {
                    try {
                        Object t = texp.eval(this.env);
                        if (t instanceof Class) {
                            type = Type.make((Class) t);
                        } else if (t instanceof Type) {
                            type = (Type) t;
                        }
                    } catch (Throwable th) {
                    }
                }
                if (type == null) {
                    if (texp instanceof ReferenceExp) {
                        error('e', "unknown type name '" + ((ReferenceExp) texp).getName() + '\'');
                    } else {
                        error('e', "invalid type spec (must be \"type\" or 'type or <type>)");
                    }
                    return Type.pointer_type;
                }
                return type;
            }
            return null;
        } finally {
            popPositionOf(saved);
        }
    }

    public Expression rewrite_with_position(Object exp, boolean function, PairWithPosition pair) {
        Expression result;
        Object saved = pushPositionOf(pair);
        try {
            if (exp == pair) {
                result = rewrite_pair(pair, function);
            } else {
                result = rewrite(exp, function);
            }
            setLineOf(result);
            return result;
        } finally {
            popPositionOf(saved);
        }
    }

    public static Object wrapSyntax(Object form, SyntaxForm syntax) {
        return (syntax == null || (form instanceof Expression)) ? form : SyntaxForms.fromDatumIfNeeded(form, syntax);
    }

    public Object popForms(int first) {
        Object obj;
        int last = this.formStack.size();
        if (last == first) {
            return Values.empty;
        }
        if (last == first + 1) {
            obj = this.formStack.elementAt(first);
        } else {
            Values vals = new Values();
            for (int i = first; i < last; i++) {
                vals.writeObject(this.formStack.elementAt(i));
            }
            obj = vals;
        }
        this.formStack.setSize(first);
        return obj;
    }

    public void scanForm(Object st, ScopeExp defs) {
        if (st instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) st;
            ScopeExp save_scope = currentScope();
            try {
                setCurrentScope(sf.getScope());
                int first = this.formStack.size();
                scanForm(sf.getDatum(), defs);
                this.formStack.add(wrapSyntax(popForms(first), sf));
                return;
            } finally {
                setCurrentScope(save_scope);
            }
        }
        if (st instanceof Values) {
            if (st == Values.empty) {
                st = QuoteExp.voidExp;
            } else {
                Object[] vals = ((Values) st).getValues();
                for (Object obj : vals) {
                    scanForm(obj, defs);
                }
                return;
            }
        }
        if (st instanceof Pair) {
            Pair st_pair = (Pair) st;
            Declaration saveContext = this.macroContext;
            Syntax syntax = null;
            ScopeExp savedScope = this.current_scope;
            Object savedPosition = pushPositionOf(st);
            if ((st instanceof SourceLocator) && defs.getLineNumber() < 0) {
                defs.setLocation((SourceLocator) st);
            }
            try {
                Object obj2 = st_pair.getCar();
                if (obj2 instanceof SyntaxForm) {
                    SyntaxForm sf2 = (SyntaxForm) st_pair.getCar();
                    setCurrentScope(sf2.getScope());
                    obj2 = sf2.getDatum();
                }
                if (obj2 instanceof Pair) {
                    Pair p = (Pair) obj2;
                    if (p.getCar() == LispLanguage.lookup_sym && (p.getCdr() instanceof Pair)) {
                        Pair p2 = (Pair) p.getCdr();
                        if (p2.getCdr() instanceof Pair) {
                            Expression part1 = rewrite(p2.getCar());
                            Expression part2 = rewrite(((Pair) p2.getCdr()).getCar());
                            Object value1 = part1.valueIfConstant();
                            Object value2 = part2.valueIfConstant();
                            if ((value1 instanceof Class) && (value2 instanceof Symbol)) {
                                try {
                                    obj2 = GetNamedPart.getNamedPart(value1, (Symbol) value2);
                                    if (obj2 instanceof Syntax) {
                                        syntax = (Syntax) obj2;
                                    }
                                } catch (Throwable th) {
                                    obj2 = null;
                                }
                            } else {
                                obj2 = namespaceResolve(part1, part2);
                            }
                        }
                    }
                }
                if ((obj2 instanceof Symbol) && !selfEvaluatingSymbol(obj2)) {
                    Expression func = rewrite(obj2, true);
                    if (func instanceof ReferenceExp) {
                        Declaration decl = ((ReferenceExp) func).getBinding();
                        if (decl != null) {
                            syntax = check_if_Syntax(decl);
                        } else {
                            Object obj3 = resolve(obj2, true);
                            if (obj3 instanceof Syntax) {
                                syntax = (Syntax) obj3;
                            }
                        }
                    }
                } else if (obj2 == begin.begin) {
                    syntax = (Syntax) obj2;
                }
                if (syntax != null) {
                    String save_filename = getFileName();
                    int save_line = getLineNumber();
                    int save_column = getColumnNumber();
                    try {
                        setLine(st_pair);
                        syntax.scanForm(st_pair, defs, this);
                        return;
                    } finally {
                        this.macroContext = saveContext;
                        setLine(save_filename, save_line, save_column);
                    }
                }
            } finally {
                if (savedScope != this.current_scope) {
                    setCurrentScope(savedScope);
                }
                popPositionOf(savedPosition);
            }
        }
        this.formStack.add(st);
    }

    public LList scanBody(Object body, ScopeExp defs, boolean makeList) {
        LList list = makeList ? LList.Empty : null;
        Pair lastPair = null;
        while (body != LList.Empty) {
            if (body instanceof SyntaxForm) {
                SyntaxForm sf = (SyntaxForm) body;
                ScopeExp save_scope = this.current_scope;
                try {
                    setCurrentScope(sf.getScope());
                    int first = this.formStack.size();
                    LList f = scanBody(sf.getDatum(), defs, makeList);
                    if (makeList) {
                        LList f2 = (LList) SyntaxForms.fromDatumIfNeeded(f, sf);
                        if (lastPair != null) {
                            lastPair.setCdrBackdoor(f2);
                        } else {
                            setCurrentScope(save_scope);
                            list = f2;
                        }
                    } else {
                        this.formStack.add(wrapSyntax(popForms(first), sf));
                        list = null;
                        setCurrentScope(save_scope);
                    }
                    return list;
                } finally {
                    setCurrentScope(save_scope);
                }
            } else if (body instanceof Pair) {
                Pair pair = (Pair) body;
                int first2 = this.formStack.size();
                scanForm(pair.getCar(), defs);
                if (getState() == 2) {
                    if (pair.getCar() != this.pendingForm) {
                        pair = makePair(pair, this.pendingForm, pair.getCdr());
                    }
                    this.pendingForm = new Pair(begin.begin, pair);
                    LList list2 = LList.Empty;
                    return list2;
                }
                int fsize = this.formStack.size();
                if (makeList) {
                    for (int i = first2; i < fsize; i++) {
                        LList npair = makePair(pair, this.formStack.elementAt(i), LList.Empty);
                        if (lastPair == null) {
                            list = npair;
                        } else {
                            lastPair.setCdrBackdoor(npair);
                        }
                        lastPair = npair;
                    }
                    this.formStack.setSize(first2);
                }
                body = pair.getCdr();
            } else {
                this.formStack.add(syntaxError("body is not a proper list"));
                return list;
            }
        }
        return list;
    }

    public static Pair makePair(Pair pair, Object car, Object cdr) {
        return pair instanceof PairWithPosition ? new PairWithPosition((PairWithPosition) pair, car, cdr) : new Pair(car, cdr);
    }

    public Expression rewrite_body(Object exp) {
        Object saved = pushPositionOf(exp);
        LetExp defs = new LetExp(null);
        int first = this.formStack.size();
        defs.outer = this.current_scope;
        this.current_scope = defs;
        try {
            LList list = scanBody(exp, defs, true);
            if (list.isEmpty()) {
                this.formStack.add(syntaxError("body with no expressions"));
            }
            int ndecls = defs.countNonDynamicDecls();
            if (ndecls != 0) {
                Expression[] inits = new Expression[ndecls];
                int i = ndecls;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    inits[i] = QuoteExp.undefined_exp;
                }
                defs.inits = inits;
            }
            rewriteBody(list);
            Expression body = makeBody(first, null);
            setLineOf(body);
            if (ndecls == 0) {
                return body;
            }
            defs.body = body;
            setLineOf(defs);
            return defs;
        } finally {
            pop(defs);
            popPositionOf(saved);
        }
    }

    private void rewriteBody(LList forms) {
        while (forms != LList.Empty) {
            Pair pair = (Pair) forms;
            Object saved = pushPositionOf(pair);
            try {
                rewriteInBody(pair.getCar());
                popPositionOf(saved);
                forms = (LList) pair.getCdr();
            } catch (Throwable th) {
                popPositionOf(saved);
                throw th;
            }
        }
    }

    private Expression makeBody(int first, ScopeExp scope) {
        int nforms = this.formStack.size() - first;
        if (nforms == 0) {
            return QuoteExp.voidExp;
        }
        if (nforms == 1) {
            return (Expression) this.formStack.pop();
        }
        Expression[] exps = new Expression[nforms];
        for (int i = 0; i < nforms; i++) {
            exps[i] = (Expression) this.formStack.elementAt(first + i);
        }
        this.formStack.setSize(first);
        if (scope instanceof ModuleExp) {
            return new ApplyExp(AppendValues.appendValues, exps);
        }
        return ((LispLanguage) getLanguage()).makeBody(exps);
    }

    public void noteAccess(Object name, ScopeExp scope) {
        if (this.notedAccess == null) {
            this.notedAccess = new Vector();
        }
        this.notedAccess.addElement(name);
        this.notedAccess.addElement(scope);
    }

    public void processAccesses() {
        if (this.notedAccess != null) {
            int sz = this.notedAccess.size();
            ScopeExp saveScope = this.current_scope;
            for (int i = 0; i < sz; i += 2) {
                Object name = this.notedAccess.elementAt(i);
                ScopeExp scope = (ScopeExp) this.notedAccess.elementAt(i + 1);
                if (this.current_scope != scope) {
                    setCurrentScope(scope);
                }
                Declaration decl = this.lexical.lookup(name, -1);
                if (decl != null && !decl.getFlag(65536L)) {
                    decl.getContext().currentLambda().capture(decl);
                    decl.setCanRead(true);
                    decl.setSimple(false);
                    decl.setFlag(524288L);
                }
            }
            if (this.current_scope != saveScope) {
                setCurrentScope(saveScope);
            }
        }
    }

    public void finishModule(ModuleExp mexp) {
        String msg2;
        boolean moduleStatic = mexp.isStatic();
        for (Declaration decl = mexp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.getFlag(512L)) {
                if (decl.getFlag(1024L)) {
                    msg2 = "' exported but never defined";
                } else {
                    msg2 = decl.getFlag(2048L) ? "' declared static but never defined" : "' declared but never defined";
                }
                error('e', decl, "'", msg2);
            }
            if (mexp.getFlag(16384) || (this.generateMain && !this.immediate)) {
                if (decl.getFlag(1024L)) {
                    if (decl.isPrivate()) {
                        if (decl.getFlag(16777216L)) {
                            error('e', decl, "'", "' is declared both private and exported");
                        }
                        decl.setPrivate(false);
                    }
                } else {
                    decl.setPrivate(true);
                }
            }
            if (moduleStatic) {
                decl.setFlag(2048L);
            } else if ((mexp.getFlag(65536) && !decl.getFlag(2048L)) || Compilation.moduleStatic < 0 || mexp.getFlag(131072)) {
                decl.setFlag(4096L);
            }
        }
    }

    static void vectorReverse(Vector vec, int start, int count) {
        int j = count / 2;
        int last = (start + count) - 1;
        for (int i = 0; i < j; i++) {
            Object tmp = vec.elementAt(start + i);
            vec.setElementAt(vec.elementAt(last - i), start + i);
            vec.setElementAt(tmp, last - i);
        }
    }

    public void resolveModule(ModuleExp mexp) {
        int numPending = this.pendingImports == null ? 0 : this.pendingImports.size();
        int i = 0;
        while (i < numPending) {
            int i2 = i + 1;
            ModuleInfo info = (ModuleInfo) this.pendingImports.elementAt(i);
            int i3 = i2 + 1;
            ScopeExp defs = (ScopeExp) this.pendingImports.elementAt(i2);
            int i4 = i3 + 1;
            Expression posExp = (Expression) this.pendingImports.elementAt(i3);
            i = i4 + 1;
            Integer savedSize = (Integer) this.pendingImports.elementAt(i4);
            if (mexp == defs) {
                Expression savePos = new ReferenceExp((Object) null);
                savePos.setLine(this);
                setLine(posExp);
                int beforeSize = this.formStack.size();
                require.importDefinitions(null, info, null, this.formStack, defs, this);
                int desiredPosition = savedSize.intValue();
                if (savedSize.intValue() != beforeSize) {
                    int curSize = this.formStack.size();
                    int count = curSize - desiredPosition;
                    vectorReverse(this.formStack, desiredPosition, beforeSize - desiredPosition);
                    vectorReverse(this.formStack, beforeSize, curSize - beforeSize);
                    vectorReverse(this.formStack, desiredPosition, count);
                }
                setLine(savePos);
            }
        }
        this.pendingImports = null;
        processAccesses();
        setModule(mexp);
        Compilation save_comp = Compilation.setSaveCurrent(this);
        try {
            rewriteInBody(popForms(this.firstForm));
            mexp.body = makeBody(this.firstForm, mexp);
            if (!this.immediate) {
                this.lexical.pop(mexp);
            }
        } finally {
            Compilation.restoreCurrent(save_comp);
        }
    }

    public Declaration makeRenamedAlias(Declaration decl, ScopeExp templateScope) {
        return templateScope == null ? decl : makeRenamedAlias(decl.getSymbol(), decl, templateScope);
    }

    public Declaration makeRenamedAlias(Object name, Declaration decl, ScopeExp templateScope) {
        Declaration alias = new Declaration(name);
        alias.setAlias(true);
        alias.setPrivate(true);
        alias.context = templateScope;
        ReferenceExp ref = new ReferenceExp(decl);
        ref.setDontDereference(true);
        alias.noteValue(ref);
        return alias;
    }

    public void pushRenamedAlias(Declaration alias) {
        Declaration decl = getOriginalRef(alias).getBinding();
        ScopeExp templateScope = alias.context;
        decl.setSymbol(null);
        Declaration old = templateScope.lookup(decl.getSymbol());
        if (old != null) {
            templateScope.remove(old);
        }
        templateScope.addDeclaration(alias);
        if (this.renamedAliasStack == null) {
            this.renamedAliasStack = new Stack();
        }
        this.renamedAliasStack.push(old);
        this.renamedAliasStack.push(alias);
        this.renamedAliasStack.push(templateScope);
    }

    public void popRenamedAlias(int count) {
        while (true) {
            count--;
            if (count >= 0) {
                ScopeExp templateScope = (ScopeExp) this.renamedAliasStack.pop();
                Declaration alias = (Declaration) this.renamedAliasStack.pop();
                Declaration decl = getOriginalRef(alias).getBinding();
                decl.setSymbol(alias.getSymbol());
                templateScope.remove(alias);
                Object old = this.renamedAliasStack.pop();
                if (old != null) {
                    templateScope.addDeclaration((Declaration) old);
                }
            } else {
                return;
            }
        }
    }

    public Declaration define(Object name, SyntaxForm nameSyntax, ScopeExp defs) {
        boolean aliasNeeded = (nameSyntax == null || nameSyntax.getScope() == currentScope()) ? false : true;
        Object declName = aliasNeeded ? new String(name.toString()) : name;
        Declaration decl = defs.getDefine(declName, 'w', this);
        if (aliasNeeded) {
            Declaration alias = makeRenamedAlias(name, decl, nameSyntax.getScope());
            nameSyntax.getScope().addDeclaration(alias);
        }
        push(decl);
        return decl;
    }
}
