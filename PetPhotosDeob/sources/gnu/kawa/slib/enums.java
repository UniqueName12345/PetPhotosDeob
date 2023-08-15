package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Apply;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxForms;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.prim_syntax;
import kawa.lib.std_syntax;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.syntax_case;

/* compiled from: enums.scm */
/* loaded from: classes.dex */
public class enums extends ModuleBody {
    public static final Macro $Prvt$$Pcdefine$Mnenum;
    public static final enums $instance;
    static final PairWithPosition Lit0;
    static final PairWithPosition Lit1;
    static final PairWithPosition Lit10;
    static final SimpleSymbol Lit11;
    static final SyntaxPattern Lit12;
    static final SyntaxTemplate Lit13;
    static final SyntaxTemplate Lit14;
    static final SyntaxPattern Lit15;
    static final SyntaxTemplate Lit16;
    static final SyntaxPattern Lit17;
    static final SyntaxPattern Lit18;
    static final SyntaxPattern Lit19;
    static final PairWithPosition Lit2;
    static final SyntaxTemplate Lit20;
    static final PairWithPosition Lit3;
    static final PairWithPosition Lit4;
    static final PairWithPosition Lit5;
    static final PairWithPosition Lit6;
    static final PairWithPosition Lit7;
    static final PairWithPosition Lit8;
    static final PairWithPosition Lit9;
    public static final Macro define$Mnenum;
    static final SimpleSymbol Lit53 = (SimpleSymbol) new SimpleSymbol("final").readResolve();
    static final SimpleSymbol Lit52 = (SimpleSymbol) new SimpleSymbol("enum").readResolve();
    static final SimpleSymbol Lit51 = (SimpleSymbol) new SimpleSymbol("num").readResolve();
    static final SimpleSymbol Lit50 = (SimpleSymbol) new SimpleSymbol("str").readResolve();
    static final SimpleSymbol Lit49 = (SimpleSymbol) new SimpleSymbol("*init*").readResolve();
    static final Keyword Lit48 = Keyword.make("access");
    static final SimpleSymbol Lit47 = (SimpleSymbol) new SimpleSymbol("String").readResolve();
    static final Keyword Lit46 = Keyword.make("allocation");
    static final SimpleSymbol Lit45 = (SimpleSymbol) new SimpleSymbol("static").readResolve();
    static final SimpleSymbol Lit44 = (SimpleSymbol) new SimpleSymbol("java.lang.Enum").readResolve();
    static final SimpleSymbol Lit43 = (SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve();
    static final SimpleSymbol Lit42 = (SimpleSymbol) new SimpleSymbol("::").readResolve();
    static final SimpleSymbol Lit41 = (SimpleSymbol) new SimpleSymbol("s").readResolve();
    static final SimpleSymbol Lit40 = (SimpleSymbol) new SimpleSymbol("valueOf").readResolve();
    static final SyntaxTemplate Lit39 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0010", new Object[0], 0);
    static final SyntaxTemplate Lit38 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0010", new Object[0], 0);
    static final SyntaxTemplate Lit37 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit41, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 290882)}, 0);
    static final SyntaxTemplate Lit36 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve(), Pair.make(Lit44, Pair.make(Pair.make((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve(), Pair.make(Lit40, LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 290823)}, 0);
    static final SyntaxTemplate Lit35 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit43, PairWithPosition.make(Lit45, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 286739), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 286739)}, 0);
    static final SyntaxTemplate Lit34 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit46, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 286726)}, 0);
    static final SyntaxTemplate Lit33 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit42, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 282649)}, 0);
    static final SyntaxTemplate Lit32 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit40, PairWithPosition.make(Lit41, PairWithPosition.make(Lit42, PairWithPosition.make(Lit47, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 282642), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 282640), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 282639), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 282630)}, 0);
    static final SyntaxTemplate Lit31 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit43, PairWithPosition.make(PairWithPosition.make(Lit52, PairWithPosition.make(Lit53, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 266284), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 266278), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 266278), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 266278)}, 0);
    static final SyntaxTemplate Lit30 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit48, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 266269)}, 0);
    static final SyntaxTemplate Lit29 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit44, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 266252)}, 0);
    static final SyntaxTemplate Lit28 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol) new SimpleSymbol("define-simple-class").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 262154)}, 0);
    static final SyntaxTemplate Lit27 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\b%#", new Object[0], 1);
    static final SyntaxTemplate Lit26 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0013", new Object[0], 0);
    static final SyntaxTemplate Lit25 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\b\u001d\u001b", new Object[0], 1);
    static final SimpleSymbol Lit24 = (SimpleSymbol) new SimpleSymbol("[]").readResolve();
    static final SyntaxTemplate Lit23 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u000b", new Object[0], 0);
    static final SyntaxPattern Lit22 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017,\r\u001f\u0018\b\b\r' \b\b", new Object[0], 5);
    static final SimpleSymbol Lit21 = (SimpleSymbol) new SimpleSymbol("%define-enum").readResolve();

    public enums() {
        ModuleInfo.register(this);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return lambda1(obj);
            case 2:
                return lambda2(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 2:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("define-enum").readResolve();
        Lit11 = simpleSymbol;
        Lit20 = new SyntaxTemplate("\u0001\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\t\u000b\t\u0010\b\u0015\u0013", new Object[]{simpleSymbol, "findkeywords"}, 1);
        Lit19 = new SyntaxPattern("\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3);
        Lit18 = new SyntaxPattern("\f\u0007\f\u000f\b", new Object[0], 2);
        Lit17 = new SyntaxPattern("\f\u0007\b", new Object[0], 1);
        Lit16 = new SyntaxTemplate("\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004\t\u000b\u0019\b\u0015\u0013\b\u001d\u001b", new Object[]{Lit21}, 1);
        Lit15 = new SyntaxPattern("\f\u0007\f\u0002\f\u000f,\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{"findkeywords"}, 4);
        Lit14 = new SyntaxTemplate("\u0001\u0001\u0003\u0001\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\t\u000b9\t\u001b\t#\b\u0015\u0013\b-+", new Object[]{Lit11, "findkeywords"}, 1);
        Lit13 = new SyntaxTemplate("\u0001\u0001\u0003\u0001\u0001\u0003", "\u001b", new Object[0], 0);
        Lit12 = new SyntaxPattern("\f\u0007\f\u0002\f\u000f,\r\u0017\u0010\b\b\f\u001f\f'\r/(\b\b", new Object[]{"findkeywords"}, 6);
        Lit10 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit45, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 127013), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 127013);
        Lit9 = PairWithPosition.make(Lit46, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 127000);
        Lit8 = PairWithPosition.make(Lit42, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 126990);
        Lit7 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("values").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 126981);
        Lit6 = PairWithPosition.make(PairWithPosition.make(Lit49, PairWithPosition.make(PairWithPosition.make(Lit50, PairWithPosition.make(Lit42, PairWithPosition.make(Lit47, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 90133), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 90130), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 90125), PairWithPosition.make(PairWithPosition.make(Lit51, PairWithPosition.make(Lit42, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("int").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 90149), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 90146), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 90141), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 90141), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 90125), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 90117), PairWithPosition.make(Lit48, PairWithPosition.make(PairWithPosition.make(Lit43, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("private").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 94222), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 94222), PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("invoke-special").readResolve(), PairWithPosition.make(Lit44, PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("this").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 98340), PairWithPosition.make(PairWithPosition.make(Lit43, PairWithPosition.make(Lit49, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 98348), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 98348), PairWithPosition.make(Lit50, PairWithPosition.make(Lit51, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 98359), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 98355), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 98347), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 98340), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 98325), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 98309), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 98309), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 94221), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 94213), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 90116);
        Lit5 = PairWithPosition.make(Keyword.make("init"), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 73741);
        Lit4 = PairWithPosition.make(Lit43, PairWithPosition.make(PairWithPosition.make(Lit52, PairWithPosition.make(Lit53, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 69680), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 69674), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 69674), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 69674);
        Lit3 = PairWithPosition.make(Lit48, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 69665);
        Lit2 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit45, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 69658), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 69658);
        Lit1 = PairWithPosition.make(Lit46, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 69645);
        Lit0 = PairWithPosition.make(Lit42, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/enums.scm", 65549);
        $instance = new enums();
        SimpleSymbol simpleSymbol2 = Lit11;
        enums enumsVar = $instance;
        define$Mnenum = Macro.make(simpleSymbol2, new ModuleMethod(enumsVar, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        $Prvt$$Pcdefine$Mnenum = Macro.make(Lit21, new ModuleMethod(enumsVar, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        $instance.run();
    }

    static SimpleSymbol symbolAppend$V(Object[] argsArray) {
        LList syms = LList.makeList(argsArray, 0);
        Apply apply = Scheme.apply;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Object obj = LList.Empty;
        LList lList = syms;
        while (lList != LList.Empty) {
            try {
                Pair arg0 = (Pair) lList;
                Object arg02 = arg0.getCdr();
                Object car = arg0.getCar();
                try {
                    obj = Pair.make(((Symbol) car).toString(), obj);
                    lList = arg02;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "symbol->string", 1, car);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, lList);
            }
        }
        Object apply2 = apply.apply2(moduleMethod, LList.reverseInPlace(obj));
        try {
            return misc.string$To$Symbol((CharSequence) apply2);
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string->symbol", 1, apply2);
        }
    }

    static Object makeFieldDesc(Symbol t$Mnname, Symbol e$Mnname, int e$Mnval) {
        Object consX;
        Object consX2;
        Object consX3;
        Object consX4;
        Object consX5;
        PairWithPosition pairWithPosition = Lit2;
        PairWithPosition pairWithPosition2 = Lit4;
        consX = LList.consX(new Object[]{Integer.valueOf(e$Mnval), LList.Empty});
        consX2 = LList.consX(new Object[]{e$Mnname.toString(), consX});
        consX3 = LList.consX(new Object[]{t$Mnname, consX2});
        consX4 = LList.consX(new Object[]{t$Mnname, Quote.append$V(new Object[]{Lit1, Pair.make(pairWithPosition, Quote.append$V(new Object[]{Lit3, Pair.make(pairWithPosition2, Quote.append$V(new Object[]{Lit5, Pair.make(consX3, LList.Empty)}))}))})});
        consX5 = LList.consX(new Object[]{e$Mnname, Quote.append$V(new Object[]{Lit0, consX4})});
        return consX5;
    }

    static PairWithPosition makeInit() {
        return Lit6;
    }

    static Pair makeValues(Symbol t$Mnarr, LList e$Mnnames) {
        Object consX;
        Object consX2;
        PairWithPosition pairWithPosition = Lit7;
        PairWithPosition pairWithPosition2 = Lit10;
        consX = LList.consX(new Object[]{t$Mnarr, Quote.append$V(new Object[]{e$Mnnames, LList.Empty})});
        consX2 = LList.consX(new Object[]{t$Mnarr, Quote.append$V(new Object[]{Lit9, Pair.make(pairWithPosition2, Pair.make(consX, LList.Empty))})});
        return Pair.make(pairWithPosition, Quote.append$V(new Object[]{Lit8, consX2}));
    }

    static Object lambda1(Object form) {
        Object[] allocVars = SyntaxPattern.allocVars(6, null);
        if (Lit12.match(form, allocVars, 0) && std_syntax.isIdentifier(Lit13.execute(allocVars, TemplateScope.make()))) {
            return Lit14.execute(allocVars, TemplateScope.make());
        }
        if (Lit15.match(form, allocVars, 0)) {
            return Lit16.execute(allocVars, TemplateScope.make());
        }
        if (Lit17.match(form, allocVars, 0)) {
            return prim_syntax.syntaxError(form, "no enum type name given" instanceof Object[] ? "no enum type name given" : new Object[]{"no enum type name given"});
        } else if (Lit18.match(form, allocVars, 0)) {
            return prim_syntax.syntaxError(form, "no enum constants given" instanceof Object[] ? "no enum constants given" : new Object[]{"no enum constants given"});
        } else {
            return Lit19.match(form, allocVars, 0) ? Lit20.execute(allocVars, TemplateScope.make()) : syntax_case.error("syntax-case", form);
        }
    }

    static LList mapNames(Symbol t$Mnname, LList e$Mnnames, int i) {
        if (lists.isNull(e$Mnnames)) {
            return LList.Empty;
        }
        Object apply1 = lists.car.apply1(e$Mnnames);
        try {
            Object makeFieldDesc = makeFieldDesc(t$Mnname, (Symbol) apply1, i);
            Object apply12 = lists.cdr.apply1(e$Mnnames);
            try {
                return lists.cons(makeFieldDesc, mapNames(t$Mnname, (LList) apply12, i + 1));
            } catch (ClassCastException e) {
                throw new WrongType(e, "map-names", 1, apply12);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "make-field-desc", 1, apply1);
        }
    }

    static Object lambda2(Object form) {
        Object consX;
        Object consX2;
        Object consX3;
        Object consX4;
        Object consX5;
        Object[] allocVars = SyntaxPattern.allocVars(5, null);
        if (Lit22.match(form, allocVars, 0)) {
            Object execute = Lit23.execute(allocVars, TemplateScope.make());
            try {
                Symbol t$Mnname = (Symbol) execute;
                Symbol t$Mnarr = symbolAppend$V(new Object[]{t$Mnname, Lit24});
                Object execute2 = Lit25.execute(allocVars, TemplateScope.make());
                try {
                    LList e$Mnnames = (LList) execute2;
                    lists.length(e$Mnnames);
                    LList field$Mndescs = mapNames(t$Mnname, e$Mnnames, 0);
                    LList init = makeInit();
                    LList values$Mnmethod = makeValues(t$Mnarr, e$Mnnames);
                    Object execute3 = Lit26.execute(allocVars, TemplateScope.make());
                    try {
                        LList opts = (LList) execute3;
                        Object execute4 = Lit27.execute(allocVars, TemplateScope.make());
                        try {
                            LList other$Mndefs = (LList) execute4;
                            TemplateScope make = TemplateScope.make();
                            Object execute5 = Lit29.execute(allocVars, make);
                            Object execute6 = Lit31.execute(allocVars, make);
                            Object execute7 = Lit32.execute(allocVars, make);
                            Object execute8 = Lit35.execute(allocVars, make);
                            Object execute9 = Lit36.execute(allocVars, make);
                            consX = LList.consX(new Object[]{SyntaxForms.makeWithTemplate(form, t$Mnname), Lit37.execute(allocVars, make)});
                            consX2 = LList.consX(new Object[]{SyntaxForms.makeWithTemplate(form, t$Mnname), Quote.append$V(new Object[]{Lit34.execute(allocVars, make), Pair.make(execute8, Pair.make(Pair.make(execute9, consX), Lit38.execute(allocVars, make)))})});
                            consX3 = LList.consX(new Object[]{SyntaxForms.makeWithTemplate(form, values$Mnmethod), Pair.make(Pair.make(execute7, Quote.append$V(new Object[]{Lit33.execute(allocVars, make), consX2})), Quote.append$V(new Object[]{SyntaxForms.makeWithTemplate(form, field$Mndescs), Quote.append$V(new Object[]{SyntaxForms.makeWithTemplate(form, other$Mndefs), Lit39.execute(allocVars, make)})}))});
                            consX4 = LList.consX(new Object[]{SyntaxForms.makeWithTemplate(form, init), consX3});
                            consX5 = LList.consX(new Object[]{SyntaxForms.makeWithTemplate(form, t$Mnname), Pair.make(execute5, Quote.append$V(new Object[]{Lit30.execute(allocVars, make), Pair.make(execute6, Quote.append$V(new Object[]{SyntaxForms.makeWithTemplate(form, opts), consX4}))}))});
                            return Quote.append$V(new Object[]{Lit28.execute(allocVars, make), consX5});
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "other-defs", -2, execute4);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "opts", -2, execute3);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "e-names", -2, execute2);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "t-name", -2, execute);
            }
        }
        return syntax_case.error("syntax-case", form);
    }
}
