package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import kawa.lang.Eval;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.parameters;
import kawa.lib.ports;
import kawa.lib.std_syntax;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.readchar;
import kawa.standard.syntax_case;

/* compiled from: testing.scm */
/* loaded from: classes.dex */
public class testing extends ModuleBody {
    public static final ModuleMethod $Pctest$Mnbegin;
    static final ModuleMethod $Pctest$Mnnull$Mncallback;
    public static final ModuleMethod $Prvt$$Pctest$Mnapproximimate$Eq;
    public static final ModuleMethod $Prvt$$Pctest$Mnas$Mnspecifier;
    public static final Macro $Prvt$$Pctest$Mncomp1body;
    public static final Macro $Prvt$$Pctest$Mncomp2body;
    public static final ModuleMethod $Prvt$$Pctest$Mnend;
    public static final Macro $Prvt$$Pctest$Mnerror;
    public static final Macro $Prvt$$Pctest$Mnevaluate$Mnwith$Mncatch;
    public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnall;
    public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnany;
    public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnnth;
    public static final ModuleMethod $Prvt$$Pctest$Mnon$Mntest$Mnbegin;
    public static final ModuleMethod $Prvt$$Pctest$Mnon$Mntest$Mnend;
    public static final ModuleMethod $Prvt$$Pctest$Mnreport$Mnresult;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist$Ex;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist$Ex;
    public static final ModuleMethod $Prvt$$Pctest$Mnshould$Mnexecute;
    public static final Macro $Prvt$test$Mngroup;
    public static final testing $instance;
    static final IntNum Lit0;
    static final SimpleSymbol Lit1;
    static final PairWithPosition Lit10;
    static final SyntaxPattern Lit100;
    static final SyntaxTemplate Lit101;
    static final SyntaxPattern Lit102;
    static final SyntaxTemplate Lit103;
    static final SimpleSymbol Lit104;
    static final SyntaxTemplate Lit105;
    static final SimpleSymbol Lit106;
    static final SyntaxTemplate Lit107;
    static final SimpleSymbol Lit108;
    static final SyntaxTemplate Lit109;
    static final PairWithPosition Lit11;
    static final SimpleSymbol Lit110;
    static final SyntaxPattern Lit111;
    static final SyntaxTemplate Lit112;
    static final SyntaxPattern Lit113;
    static final SyntaxTemplate Lit114;
    static final SimpleSymbol Lit115;
    static final SyntaxRules Lit116;
    static final SimpleSymbol Lit117;
    static final SyntaxPattern Lit118;
    static final SyntaxTemplate Lit119;
    static final SimpleSymbol Lit12;
    static final SyntaxPattern Lit120;
    static final SyntaxTemplate Lit121;
    static final SyntaxPattern Lit122;
    static final SyntaxTemplate Lit123;
    static final SimpleSymbol Lit124;
    static final SimpleSymbol Lit125;
    static final SyntaxRules Lit126;
    static final SimpleSymbol Lit127;
    static final SimpleSymbol Lit128;
    static final SyntaxRules Lit129;
    static final IntNum Lit13;
    static final SimpleSymbol Lit130;
    static final SimpleSymbol Lit131;
    static final SyntaxRules Lit132;
    static final SimpleSymbol Lit133;
    static final SimpleSymbol Lit134;
    static final SyntaxRules Lit135;
    static final SimpleSymbol Lit136;
    static final SimpleSymbol Lit137;
    static final SyntaxRules Lit138;
    static final SimpleSymbol Lit139;
    static final SimpleSymbol Lit14;
    static final SyntaxRules Lit140;
    static final SimpleSymbol Lit15;
    static final SyntaxPattern Lit16;
    static final SyntaxTemplate Lit17;
    static final SyntaxPattern Lit18;
    static final SyntaxTemplate Lit19;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit20;
    static final SimpleSymbol Lit21;
    static final SimpleSymbol Lit22;
    static final SimpleSymbol Lit23;
    static final SimpleSymbol Lit24;
    static final SimpleSymbol Lit25;
    static final SimpleSymbol Lit26;
    static final SimpleSymbol Lit27;
    static final SimpleSymbol Lit28;
    static final SimpleSymbol Lit29;
    static final SimpleSymbol Lit3;
    static final SimpleSymbol Lit30;
    static final SimpleSymbol Lit31;
    static final SimpleSymbol Lit32;
    static final SimpleSymbol Lit33;
    static final SimpleSymbol Lit34;
    static final SimpleSymbol Lit35;
    static final SimpleSymbol Lit36;
    static final SimpleSymbol Lit37;
    static final SimpleSymbol Lit38;
    static final SimpleSymbol Lit39;
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit40;
    static final SimpleSymbol Lit41;
    static final SimpleSymbol Lit42;
    static final SimpleSymbol Lit43;
    static final SimpleSymbol Lit44;
    static final SimpleSymbol Lit45;
    static final SimpleSymbol Lit46;
    static final SimpleSymbol Lit47;
    static final SimpleSymbol Lit48;
    static final SimpleSymbol Lit49;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit50;
    static final SimpleSymbol Lit51;
    static final SimpleSymbol Lit52;
    static final SimpleSymbol Lit53;
    static final SimpleSymbol Lit54;
    static final SimpleSymbol Lit55;
    static final SimpleSymbol Lit56;
    static final SimpleSymbol Lit57;
    static final SimpleSymbol Lit58;
    static final SimpleSymbol Lit59;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit60;
    static final SimpleSymbol Lit61;
    static final SimpleSymbol Lit62;
    static final SimpleSymbol Lit63;
    static final SimpleSymbol Lit64;
    static final SimpleSymbol Lit65;
    static final SimpleSymbol Lit66;
    static final SimpleSymbol Lit67;
    static final SimpleSymbol Lit68;
    static final SimpleSymbol Lit69;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit70;
    static final SyntaxRules Lit71;
    static final SimpleSymbol Lit72;
    static final SyntaxRules Lit73;
    static final SimpleSymbol Lit74;
    static final SimpleSymbol Lit75;
    static final SyntaxRules Lit76;
    static final SimpleSymbol Lit77;
    static final SimpleSymbol Lit78;
    static final SimpleSymbol Lit79;
    static final PairWithPosition Lit8;
    static final SimpleSymbol Lit80;
    static final SimpleSymbol Lit81;
    static final SimpleSymbol Lit82;
    static final SimpleSymbol Lit83;
    static final SimpleSymbol Lit84;
    static final SyntaxRules Lit85;
    static final SimpleSymbol Lit86;
    static final SimpleSymbol Lit87;
    static final SimpleSymbol Lit88;
    static final SimpleSymbol Lit89;
    static final SimpleSymbol Lit9;
    static final SyntaxRules Lit90;
    static final SimpleSymbol Lit91;
    static final SimpleSymbol Lit92;
    static final SyntaxRules Lit93;
    static final SimpleSymbol Lit94;
    static final SyntaxPattern Lit95;
    static final SyntaxTemplate Lit96;
    static final SyntaxPattern Lit97;
    static final SyntaxTemplate Lit98;
    static final SimpleSymbol Lit99;
    static final ModuleMethod lambda$Fn1;
    static final ModuleMethod lambda$Fn2;
    static final ModuleMethod lambda$Fn3;
    public static final ModuleMethod test$Mnapply;
    public static final Macro test$Mnapproximate;
    public static final Macro test$Mnassert;
    public static final Macro test$Mnend;
    public static final Macro test$Mneq;
    public static final Macro test$Mnequal;
    public static final Macro test$Mneqv;
    public static final Macro test$Mnerror;
    public static final Macro test$Mnexpect$Mnfail;
    public static final Macro test$Mngroup$Mnwith$Mncleanup;
    public static Boolean test$Mnlog$Mnto$Mnfile;
    public static final Macro test$Mnmatch$Mnall;
    public static final Macro test$Mnmatch$Mnany;
    public static final ModuleMethod test$Mnmatch$Mnname;
    public static final Macro test$Mnmatch$Mnnth;
    public static final ModuleMethod test$Mnon$Mnbad$Mncount$Mnsimple;
    public static final ModuleMethod test$Mnon$Mnbad$Mnend$Mnname$Mnsimple;
    public static final ModuleMethod test$Mnon$Mnfinal$Mnsimple;
    public static final ModuleMethod test$Mnon$Mngroup$Mnbegin$Mnsimple;
    public static final ModuleMethod test$Mnon$Mngroup$Mnend$Mnsimple;
    static final ModuleMethod test$Mnon$Mntest$Mnbegin$Mnsimple;
    public static final ModuleMethod test$Mnon$Mntest$Mnend$Mnsimple;
    public static final ModuleMethod test$Mnpassed$Qu;
    public static final ModuleMethod test$Mnread$Mneval$Mnstring;
    public static final ModuleMethod test$Mnresult$Mnalist;
    public static final ModuleMethod test$Mnresult$Mnalist$Ex;
    public static final ModuleMethod test$Mnresult$Mnclear;
    public static final ModuleMethod test$Mnresult$Mnkind;
    public static final Macro test$Mnresult$Mnref;
    public static final ModuleMethod test$Mnresult$Mnremove;
    public static final ModuleMethod test$Mnresult$Mnset$Ex;
    static final Class test$Mnrunner;
    public static final ModuleMethod test$Mnrunner$Mnaux$Mnvalue;
    public static final ModuleMethod test$Mnrunner$Mnaux$Mnvalue$Ex;
    public static final ModuleMethod test$Mnrunner$Mncreate;
    public static Object test$Mnrunner$Mncurrent;
    public static Object test$Mnrunner$Mnfactory;
    public static final ModuleMethod test$Mnrunner$Mnfail$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnfail$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnget;
    public static final ModuleMethod test$Mnrunner$Mngroup$Mnpath;
    public static final ModuleMethod test$Mnrunner$Mngroup$Mnstack;
    public static final ModuleMethod test$Mnrunner$Mngroup$Mnstack$Ex;
    public static final ModuleMethod test$Mnrunner$Mnnull;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mnend$Mnname;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mnend$Mnname$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnfinal;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnfinal$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnbegin;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnbegin$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnend;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnend$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnbegin;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnbegin$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnend;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnend$Ex;
    public static final ModuleMethod test$Mnrunner$Mnpass$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnpass$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnreset;
    public static final ModuleMethod test$Mnrunner$Mnsimple;
    public static final ModuleMethod test$Mnrunner$Mnskip$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnskip$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mntest$Mnname;
    public static final ModuleMethod test$Mnrunner$Mnxfail$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnxfail$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnxpass$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnxpass$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Qu;
    public static final Macro test$Mnskip;
    public static final Macro test$Mnwith$Mnrunner;
    static final SimpleSymbol Lit165 = (SimpleSymbol) new SimpleSymbol("dynamic-wind").readResolve();
    static final SimpleSymbol Lit164 = (SimpleSymbol) new SimpleSymbol("p").readResolve();
    static final SimpleSymbol Lit163 = (SimpleSymbol) new SimpleSymbol("exp").readResolve();
    static final SimpleSymbol Lit162 = (SimpleSymbol) new SimpleSymbol("res").readResolve();
    static final SimpleSymbol Lit161 = (SimpleSymbol) new SimpleSymbol("if").readResolve();
    static final SimpleSymbol Lit160 = (SimpleSymbol) new SimpleSymbol("name").readResolve();
    static final SimpleSymbol Lit159 = (SimpleSymbol) new SimpleSymbol(GetNamedPart.INSTANCEOF_METHOD_NAME).readResolve();
    static final SimpleSymbol Lit158 = (SimpleSymbol) new SimpleSymbol("cond").readResolve();
    static final SimpleSymbol Lit157 = (SimpleSymbol) new SimpleSymbol("actual-error").readResolve();
    static final SimpleSymbol Lit156 = (SimpleSymbol) new SimpleSymbol("<java.lang.Throwable>").readResolve();
    static final SimpleSymbol Lit155 = (SimpleSymbol) new SimpleSymbol("actual-value").readResolve();
    static final SimpleSymbol Lit154 = (SimpleSymbol) new SimpleSymbol("try-catch").readResolve();
    static final SimpleSymbol Lit153 = (SimpleSymbol) new SimpleSymbol("et").readResolve();
    static final SimpleSymbol Lit152 = (SimpleSymbol) new SimpleSymbol("expected-error").readResolve();
    static final SimpleSymbol Lit151 = (SimpleSymbol) new SimpleSymbol("ex").readResolve();
    static final SimpleSymbol Lit150 = (SimpleSymbol) new SimpleSymbol("let*").readResolve();
    static final SimpleSymbol Lit149 = (SimpleSymbol) new SimpleSymbol("r").readResolve();
    static final SimpleSymbol Lit148 = (SimpleSymbol) new SimpleSymbol("saved-runner").readResolve();
    static final SimpleSymbol Lit147 = (SimpleSymbol) new SimpleSymbol("lambda").readResolve();
    static final SimpleSymbol Lit146 = (SimpleSymbol) new SimpleSymbol("test-runner-current").readResolve();
    static final SimpleSymbol Lit145 = (SimpleSymbol) new SimpleSymbol("cons").readResolve();
    static final SimpleSymbol Lit144 = (SimpleSymbol) new SimpleSymbol("let").readResolve();
    static final SimpleSymbol Lit143 = (SimpleSymbol) new SimpleSymbol("runner").readResolve();
    static final SimpleSymbol Lit142 = (SimpleSymbol) new SimpleSymbol("test-read-eval-string").readResolve();
    static final SimpleSymbol Lit141 = (SimpleSymbol) new SimpleSymbol("test-match-name").readResolve();

    public testing() {
        ModuleInfo.register(this);
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
        test$Mnlog$Mnto$Mnfile = Boolean.TRUE;
        test$Mnrunner$Mncurrent = parameters.makeParameter(Boolean.FALSE);
        test$Mnrunner$Mnfactory = parameters.makeParameter(test$Mnrunner$Mnsimple);
    }

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("test-expect-fail").readResolve();
        Lit139 = simpleSymbol;
        SyntaxPattern syntaxPattern = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
        SimpleSymbol simpleSymbol2 = Lit143;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("test-runner-get").readResolve();
        Lit60 = simpleSymbol3;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("%test-runner-fail-list!").readResolve();
        Lit34 = simpleSymbol4;
        SimpleSymbol simpleSymbol5 = (SimpleSymbol) new SimpleSymbol("test-match-all").readResolve();
        Lit131 = simpleSymbol5;
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol("%test-as-specifier").readResolve();
        Lit136 = simpleSymbol6;
        SimpleSymbol simpleSymbol7 = (SimpleSymbol) new SimpleSymbol("%test-runner-fail-list").readResolve();
        Lit33 = simpleSymbol7;
        Lit140 = new SyntaxRules(new Object[]{simpleSymbol}, new SyntaxRule[]{new SyntaxRule(syntaxPattern, "\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$Q\u0011\u0018,\b\u0005\u0011\u00184\b\u0003\u0018<", new Object[]{Lit144, PairWithPosition.make(PairWithPosition.make(simpleSymbol2, PairWithPosition.make(PairWithPosition.make(simpleSymbol3, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3952660), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3952660), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3952652), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3952651), simpleSymbol4, Lit143, Lit145, simpleSymbol5, simpleSymbol6, PairWithPosition.make(PairWithPosition.make(simpleSymbol7, PairWithPosition.make(Lit143, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3964958), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3964934), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3964934)}, 1)}, 1);
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol("test-skip").readResolve();
        Lit137 = simpleSymbol8;
        SyntaxPattern syntaxPattern2 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
        SimpleSymbol simpleSymbol9 = (SimpleSymbol) new SimpleSymbol("%test-runner-skip-list!").readResolve();
        Lit32 = simpleSymbol9;
        SimpleSymbol simpleSymbol10 = (SimpleSymbol) new SimpleSymbol("%test-runner-skip-list").readResolve();
        Lit31 = simpleSymbol10;
        Lit138 = new SyntaxRules(new Object[]{simpleSymbol8}, new SyntaxRule[]{new SyntaxRule(syntaxPattern2, "\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$Q\u0011\u0018,\b\u0005\u0011\u00184\b\u0003\u0018<", new Object[]{Lit144, PairWithPosition.make(PairWithPosition.make(Lit143, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3919892), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3919892), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3919884), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3919883), simpleSymbol9, Lit143, Lit145, Lit131, Lit136, PairWithPosition.make(PairWithPosition.make(simpleSymbol10, PairWithPosition.make(Lit143, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3932190), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3932166), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3932166)}, 1)}, 1);
        SimpleSymbol simpleSymbol11 = (SimpleSymbol) new SimpleSymbol("test-match-any").readResolve();
        Lit134 = simpleSymbol11;
        SyntaxPattern syntaxPattern3 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
        SimpleSymbol simpleSymbol12 = (SimpleSymbol) new SimpleSymbol("%test-match-any").readResolve();
        Lit133 = simpleSymbol12;
        Lit135 = new SyntaxRules(new Object[]{simpleSymbol11}, new SyntaxRule[]{new SyntaxRule(syntaxPattern3, "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\b\u0003", new Object[]{simpleSymbol12, Lit136}, 1)}, 1);
        Object[] objArr = {Lit131};
        SyntaxPattern syntaxPattern4 = new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1);
        SimpleSymbol simpleSymbol13 = (SimpleSymbol) new SimpleSymbol("%test-match-all").readResolve();
        Lit130 = simpleSymbol13;
        Lit132 = new SyntaxRules(objArr, new SyntaxRule[]{new SyntaxRule(syntaxPattern4, "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\b\u0003", new Object[]{simpleSymbol13, Lit136}, 1)}, 1);
        SimpleSymbol simpleSymbol14 = (SimpleSymbol) new SimpleSymbol("test-match-nth").readResolve();
        Lit128 = simpleSymbol14;
        SyntaxPattern syntaxPattern5 = new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1);
        IntNum make = IntNum.make(1);
        Lit13 = make;
        Object[] objArr2 = {Lit128, PairWithPosition.make(make, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3727384)};
        SyntaxPattern syntaxPattern6 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2);
        SimpleSymbol simpleSymbol15 = (SimpleSymbol) new SimpleSymbol("%test-match-nth").readResolve();
        Lit127 = simpleSymbol15;
        Lit129 = new SyntaxRules(new Object[]{simpleSymbol14}, new SyntaxRule[]{new SyntaxRule(syntaxPattern5, "\u0001", "\u0011\u0018\u0004\t\u0003\u0018\f", objArr2, 0), new SyntaxRule(syntaxPattern6, "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", new Object[]{simpleSymbol15}, 0)}, 2);
        SimpleSymbol simpleSymbol16 = (SimpleSymbol) new SimpleSymbol("test-with-runner").readResolve();
        Lit125 = simpleSymbol16;
        Lit126 = new SyntaxRules(new Object[]{simpleSymbol16}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014Y\u0011\u0018\u001c\t\u0010\b\u0011\u0018$\b\u0003A\u0011\u0018\u001c\t\u0010\b\r\u000b\u0018,", new Object[]{Lit144, PairWithPosition.make(PairWithPosition.make(Lit148, PairWithPosition.make(PairWithPosition.make(Lit146, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3657754), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3657754), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3657740), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3657739), Lit165, Lit147, Lit146, PairWithPosition.make(PairWithPosition.make(Lit147, PairWithPosition.make(LList.Empty, PairWithPosition.make(PairWithPosition.make(Lit146, PairWithPosition.make(Lit148, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674156), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674135), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674135), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674132), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674124), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3674124)}, 1)}, 2);
        Lit124 = (SimpleSymbol) new SimpleSymbol("test-apply").readResolve();
        SimpleSymbol simpleSymbol17 = (SimpleSymbol) new SimpleSymbol("test-result-alist!").readResolve();
        Lit52 = simpleSymbol17;
        SimpleSymbol simpleSymbol18 = (SimpleSymbol) new SimpleSymbol("%test-error").readResolve();
        Lit115 = simpleSymbol18;
        Lit123 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u0013\b\u0011\u0018$\u0011\u0018\u001c\u0011\u0018,\b\u000b", new Object[]{Lit150, PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3514382), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3514382), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3514379), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3514378), simpleSymbol17, Lit149, simpleSymbol18, Boolean.TRUE}, 0);
        Lit122 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
        Lit121 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b\b\u0011\u0018$\u0011\u0018\u001c\t\u000b\b\u0013", new Object[]{Lit150, PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3493902), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3493902), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3493899), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3493898), Lit52, Lit149, Lit115}, 0);
        Lit120 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\b", new Object[0], 4);
        SimpleSymbol simpleSymbol19 = (SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve();
        Lit15 = simpleSymbol19;
        SimpleSymbol simpleSymbol20 = (SimpleSymbol) new SimpleSymbol("test-name").readResolve();
        Lit7 = simpleSymbol20;
        Lit119 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b#\b\u0011\u0018<\u0011\u0018$\t\u0013\b\u001b", new Object[]{Lit150, PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3469326), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3469326), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3469323), Lit160, Lit52, Lit149, Lit145, PairWithPosition.make(simpleSymbol19, PairWithPosition.make(simpleSymbol20, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3477545), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3477545), Lit115}, 0);
        Lit118 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\b", new Object[0], 5);
        Lit117 = (SimpleSymbol) new SimpleSymbol("test-error").readResolve();
        Object[] objArr3 = {Lit115};
        SyntaxPattern syntaxPattern7 = new SyntaxPattern("\f\u0018\f\u0007\f\u0002\f\u000f\b", new Object[]{Boolean.TRUE}, 2);
        SimpleSymbol simpleSymbol21 = (SimpleSymbol) new SimpleSymbol("%test-on-test-begin").readResolve();
        Lit86 = simpleSymbol21;
        SimpleSymbol simpleSymbol22 = (SimpleSymbol) new SimpleSymbol("test-result-set!").readResolve();
        Lit78 = simpleSymbol22;
        SimpleSymbol simpleSymbol23 = (SimpleSymbol) new SimpleSymbol("%test-on-test-end").readResolve();
        Lit87 = simpleSymbol23;
        SimpleSymbol simpleSymbol24 = (SimpleSymbol) new SimpleSymbol("%test-report-result").readResolve();
        Lit83 = simpleSymbol24;
        Lit116 = new SyntaxRules(objArr3, new SyntaxRule[]{new SyntaxRule(syntaxPattern7, "\u0001\u0001", "\u0011\u0018\u0004\b)\u0011\u0018\f\b\u00039\u0011\u0018\u0014\t\u0003\u0018\u001cũ\u0011\u0018$\t\u0003\b\u0011\u0018,\u0091\u0011\u00184\t\u0010Q\u0011\u0018\u0014\t\u0003\u0011\u0018<\b\u000b\u0018D\b\u0011\u0018L\u0011\u0018T9\u0011\u0018\u0014\t\u0003\u0018\\\u0018d\u0018l", new Object[]{Lit158, simpleSymbol21, simpleSymbol22, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit152, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3223581), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3223581), PairWithPosition.make(Boolean.TRUE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3223596), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3223580), simpleSymbol23, Lit154, Lit144, PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3239966), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3239966), PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3244041), Lit151, Lit156, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit157, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3252256), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3252256), PairWithPosition.make(Lit151, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3252269), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3252255), PairWithPosition.make(Boolean.TRUE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3256331), PairWithPosition.make(PairWithPosition.make(simpleSymbol24, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3260424), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3260424)}, 0), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004)\u0011\u0018\f\b\u0003\b\u0011\u0018\u00141\b\u0011\u0018\u001c\b\u000b9\u0011\u0018$\t\u0003\u0018,ũ\u0011\u00184\t\u0003\b\u0011\u0018<\u0091\u0011\u0018\u0014\t\u0010Q\u0011\u0018$\t\u0003\u0011\u0018D\b\u0013\u0018L\b\u0011\u0018T\u0011\u0018\\9\u0011\u0018$\t\u0003\u0018d\u0018l\u0018t", new Object[]{Lit161, Lit86, Lit144, Lit153, Lit78, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit152, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3276828), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3276828), PairWithPosition.make(Lit153, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3276843), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3276827), Lit87, Lit154, PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3293213), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3293213), PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3297288), Lit151, Lit156, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit157, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3305503), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3305503), PairWithPosition.make(Lit151, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3305516), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3305502), PairWithPosition.make(PairWithPosition.make(Lit158, PairWithPosition.make(PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("and").readResolve(), PairWithPosition.make(PairWithPosition.make(Lit159, PairWithPosition.make(Lit153, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<gnu.bytecode.ClassType>").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309604), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309601), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309590), PairWithPosition.make(PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve(), Pair.make((SimpleSymbol) new SimpleSymbol("gnu.bytecode.ClassType").readResolve(), Pair.make(Pair.make((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve(), Pair.make((SimpleSymbol) new SimpleSymbol("isSubclass").readResolve(), LList.Empty)), LList.Empty)), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3313673), PairWithPosition.make(Lit153, PairWithPosition.make(Lit156, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3313710), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3313707), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3313672), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3313672), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309590), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309585), PairWithPosition.make(PairWithPosition.make(Lit159, PairWithPosition.make(Lit151, PairWithPosition.make(Lit153, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3317784), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3317781), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3317770), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3317770), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309584), PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("else").readResolve(), PairWithPosition.make(Boolean.TRUE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3321871), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3321865), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3321865), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309584), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309578), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3309578), PairWithPosition.make(PairWithPosition.make(Lit83, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3325959), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 3325959)}, 0)}, 3);
        SimpleSymbol simpleSymbol25 = (SimpleSymbol) new SimpleSymbol("%test-comp2body").readResolve();
        Lit89 = simpleSymbol25;
        SimpleSymbol simpleSymbol26 = (SimpleSymbol) new SimpleSymbol("%test-approximimate=").readResolve();
        Lit91 = simpleSymbol26;
        Lit114 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b#\b\u0011\u0018$\u0011\u0018\u001c)\u0011\u0018,\b\u001b\t\u000b\b\u0013", new Object[]{Lit150, PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2916364), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2916364), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2916361), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2916360), Lit52, Lit149, simpleSymbol25, simpleSymbol26}, 0);
        Lit113 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\b", new Object[0], 5);
        Lit112 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b+\b\u0011\u0018<\u0011\u0018$)\u0011\u0018D\b#\t\u0013\b\u001b", new Object[]{Lit150, PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2891788), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2891788), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2891785), Lit160, Lit52, Lit149, Lit145, PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2900007), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2900007), Lit89, Lit91}, 0);
        Lit111 = new SyntaxPattern("\\\f\u0007\f\u000f\f\u0017\f\u001f\f'\b\f/\b", new Object[0], 6);
        Lit110 = (SimpleSymbol) new SimpleSymbol("test-approximate").readResolve();
        Lit109 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("equal?").readResolve()}, 0);
        Lit108 = (SimpleSymbol) new SimpleSymbol("test-equal").readResolve();
        Lit107 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("eq?").readResolve()}, 0);
        Lit106 = (SimpleSymbol) new SimpleSymbol("test-eq").readResolve();
        Lit105 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("eqv?").readResolve()}, 0);
        Lit104 = (SimpleSymbol) new SimpleSymbol("test-eqv").readResolve();
        SimpleSymbol simpleSymbol27 = (SimpleSymbol) new SimpleSymbol("%test-comp1body").readResolve();
        Lit92 = simpleSymbol27;
        Lit103 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u0013\b\u0011\u0018$\u0011\u0018\u001c\b\u000b", new Object[]{Lit150, PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2781198), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2781198), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2781195), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2781194), Lit52, Lit149, simpleSymbol27}, 0);
        Lit102 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
        Lit101 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b\u001b\b\u0011\u0018<\u0011\u0018$\b\u0013", new Object[]{Lit150, PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2756622), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2756622), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2756619), Lit160, Lit52, Lit149, Lit145, PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2764841), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2764841), Lit92}, 0);
        Lit100 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\b", new Object[0], 4);
        Lit99 = (SimpleSymbol) new SimpleSymbol("test-assert").readResolve();
        SimpleSymbol simpleSymbol28 = (SimpleSymbol) new SimpleSymbol("%test-end").readResolve();
        Lit69 = simpleSymbol28;
        Lit98 = new SyntaxTemplate("\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\b\u000b", new Object[]{simpleSymbol28, Boolean.FALSE}, 0);
        Lit97 = new SyntaxPattern("\u001c\f\u0007\b\f\u000f\b", new Object[0], 2);
        Lit96 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u000b\b\u0013", new Object[]{Lit69}, 0);
        Lit95 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
        Lit94 = (SimpleSymbol) new SimpleSymbol("test-end").readResolve();
        Object[] objArr4 = {Lit92};
        SyntaxPattern syntaxPattern8 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2);
        SimpleSymbol simpleSymbol29 = (SimpleSymbol) new SimpleSymbol("%test-evaluate-with-catch").readResolve();
        Lit84 = simpleSymbol29;
        Lit93 = new SyntaxRules(objArr4, new SyntaxRule[]{new SyntaxRule(syntaxPattern8, "\u0001\u0001", "\u0011\u0018\u0004\t\u0010ű\u0011\u0018\f)\u0011\u0018\u0014\b\u0003\b\u0011\u0018\u0004\t\u0010\b\u0011\u0018\u0004Q\b\u0011\u0018\u001c\b\u0011\u0018$\b\u000b9\u0011\u0018,\t\u0003\u00184\b\u0011\u0018<\t\u0003\u0018D\u0018L", new Object[]{Lit144, Lit161, Lit86, Lit162, simpleSymbol29, Lit78, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2666526), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2666526), PairWithPosition.make(Lit162, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2666539), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2666525), Lit87, PairWithPosition.make(Lit162, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2670622), PairWithPosition.make(PairWithPosition.make(Lit83, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2674696), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2674696)}, 0)}, 2);
        Lit90 = new SyntaxRules(new Object[]{Lit89}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f\b", new Object[0], 4), "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0010Ǳ\u0011\u0018\f)\u0011\u0018\u0014\b\u0003\b\u0011\u0018\u00041\b\u0011\u0018\u001c\b\u00139\u0011\u0018$\t\u0003\u0018,\b\u0011\u0018\u0004Q\b\u0011\u00184\b\u0011\u0018<\b\u001b9\u0011\u0018$\t\u0003\u0018D\b\u0011\u0018L\t\u0003\b\t\u000b\u0018T\u0018\\", new Object[]{Lit144, Lit161, Lit86, Lit163, Lit78, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("expected-value").readResolve(), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2592794), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2592794), PairWithPosition.make(Lit163, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2592809), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2592793), Lit162, Lit84, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2600988), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2600988), PairWithPosition.make(Lit162, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2601001), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2600987), Lit87, PairWithPosition.make(Lit163, PairWithPosition.make(Lit162, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2605094), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2605090), PairWithPosition.make(PairWithPosition.make(Lit83, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2609158), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2609158)}, 0)}, 4);
        Lit88 = (SimpleSymbol) new SimpleSymbol("test-runner-test-name").readResolve();
        Lit85 = new SyntaxRules(new Object[]{Lit84}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\t\u0003\u0018\f", new Object[]{Lit154, PairWithPosition.make(PairWithPosition.make(Lit151, PairWithPosition.make(Lit156, PairWithPosition.make(PairWithPosition.make(Lit78, PairWithPosition.make(PairWithPosition.make(Lit146, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347035), PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit157, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347058), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347058), PairWithPosition.make(Lit151, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347071), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347057), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347035), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347017), PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2351113), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2347017), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2342921), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2342917), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2342917)}, 0)}, 1);
        Lit82 = (SimpleSymbol) new SimpleSymbol("test-passed?").readResolve();
        Lit81 = (SimpleSymbol) new SimpleSymbol("test-result-kind").readResolve();
        Lit80 = (SimpleSymbol) new SimpleSymbol("test-result-remove").readResolve();
        Lit79 = (SimpleSymbol) new SimpleSymbol("test-result-clear").readResolve();
        Lit77 = (SimpleSymbol) new SimpleSymbol("test-on-test-end-simple").readResolve();
        SimpleSymbol simpleSymbol30 = (SimpleSymbol) new SimpleSymbol("test-result-ref").readResolve();
        Lit75 = simpleSymbol30;
        SyntaxPattern syntaxPattern9 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2);
        Object[] objArr5 = {Lit75, PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1933348)};
        SyntaxPattern syntaxPattern10 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
        SimpleSymbol simpleSymbol31 = (SimpleSymbol) new SimpleSymbol("test-result-alist").readResolve();
        Lit51 = simpleSymbol31;
        Lit76 = new SyntaxRules(new Object[]{simpleSymbol30}, new SyntaxRule[]{new SyntaxRule(syntaxPattern9, "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\t\u000b\u0018\f", objArr5, 0), new SyntaxRule(syntaxPattern10, "\u0001\u0001\u0001", "\u0011\u0018\u0004\u0081\b\u0011\u0018\f\b\u0011\u0018\u0014\t\u000b\b\u0011\u0018\u001c\b\u0003\b\u0011\u0018$\u0011\u0018\f\u0011\u0018,\b\u0013", new Object[]{Lit144, Lit164, (SimpleSymbol) new SimpleSymbol("assq").readResolve(), simpleSymbol31, Lit161, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("cdr").readResolve(), PairWithPosition.make(Lit164, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1945619), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1945614)}, 0)}, 3);
        Lit74 = (SimpleSymbol) new SimpleSymbol("test-on-test-begin-simple").readResolve();
        SimpleSymbol simpleSymbol32 = (SimpleSymbol) new SimpleSymbol("test-group-with-cleanup").readResolve();
        Lit72 = simpleSymbol32;
        SyntaxPattern syntaxPattern11 = new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
        SimpleSymbol simpleSymbol33 = (SimpleSymbol) new SimpleSymbol("test-group").readResolve();
        Lit70 = simpleSymbol33;
        Lit73 = new SyntaxRules(new Object[]{simpleSymbol32}, new SyntaxRule[]{new SyntaxRule(syntaxPattern11, "\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\u0011\u0018\u00149\u0011\u0018\u001c\t\u0010\b\u000b\b\u0011\u0018\u001c\t\u0010\b\u0013", new Object[]{simpleSymbol33, Lit165, PairWithPosition.make(Lit147, PairWithPosition.make(LList.Empty, PairWithPosition.make(Boolean.FALSE, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1826831), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1826828), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1826820), Lit147}, 0), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\b\u000b", new Object[]{Lit72, Boolean.FALSE}, 0), new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f#", new Object[0], 5), "\u0001\u0001\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u00039\u0011\u0018\f\t\u000b\b\u0013\t\u001b\"", new Object[]{Lit72, (SimpleSymbol) new SimpleSymbol("begin").readResolve()}, 0)}, 5);
        Object[] objArr6 = {Lit70};
        SyntaxPattern syntaxPattern12 = new SyntaxPattern("\f\u0018\f\u0007\u000b", new Object[0], 2);
        SimpleSymbol simpleSymbol34 = (SimpleSymbol) new SimpleSymbol("%test-should-execute").readResolve();
        Lit62 = simpleSymbol34;
        Lit71 = new SyntaxRules(objArr6, new SyntaxRule[]{new SyntaxRule(syntaxPattern12, "\u0001\u0000", "\u0011\u0018\u0004\u0011\u0018\f\u0099\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\b\u0011\u0018,\u0011\u00184\b\u0003\b\u0011\u0018<\u0011\u0018D\b\u0011\u0018LY\u0011\u0018T\t\u0010\b\u0011\u0018\\\b\u00031\u0011\u0018T\t\u0010\n\b\u0011\u0018T\t\u0010\b\u0011\u0018d\b\u0003", new Object[]{Lit144, PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit146, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1769487), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1769487), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1769484), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1769483), Lit52, Lit149, (SimpleSymbol) new SimpleSymbol("list").readResolve(), Lit145, PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1777707), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1777707), Lit161, PairWithPosition.make(simpleSymbol34, PairWithPosition.make(Lit149, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1781794), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1781772), Lit165, Lit147, (SimpleSymbol) new SimpleSymbol("test-begin").readResolve(), Lit94}, 0)}, 2);
        Lit68 = (SimpleSymbol) new SimpleSymbol("test-on-final-simple").readResolve();
        Lit67 = (SimpleSymbol) new SimpleSymbol("test-on-bad-end-name-simple").readResolve();
        Lit66 = (SimpleSymbol) new SimpleSymbol("test-on-bad-count-simple").readResolve();
        Lit65 = (SimpleSymbol) new SimpleSymbol("test-on-group-end-simple").readResolve();
        Lit64 = (SimpleSymbol) new SimpleSymbol("test-on-group-begin-simple").readResolve();
        Lit63 = (SimpleSymbol) new SimpleSymbol("%test-begin").readResolve();
        Lit61 = (SimpleSymbol) new SimpleSymbol("test-runner-create").readResolve();
        Lit59 = (SimpleSymbol) new SimpleSymbol("test-runner-simple").readResolve();
        Lit58 = (SimpleSymbol) new SimpleSymbol("test-runner-null").readResolve();
        Lit57 = (SimpleSymbol) new SimpleSymbol("%test-null-callback").readResolve();
        Lit56 = (SimpleSymbol) new SimpleSymbol("test-runner-group-path").readResolve();
        Lit55 = (SimpleSymbol) new SimpleSymbol("test-runner-reset").readResolve();
        Lit54 = (SimpleSymbol) new SimpleSymbol("test-runner-aux-value!").readResolve();
        Lit53 = (SimpleSymbol) new SimpleSymbol("test-runner-aux-value").readResolve();
        Lit50 = (SimpleSymbol) new SimpleSymbol("test-runner-on-bad-end-name!").readResolve();
        Lit49 = (SimpleSymbol) new SimpleSymbol("test-runner-on-bad-end-name").readResolve();
        Lit48 = (SimpleSymbol) new SimpleSymbol("test-runner-on-bad-count!").readResolve();
        Lit47 = (SimpleSymbol) new SimpleSymbol("test-runner-on-bad-count").readResolve();
        Lit46 = (SimpleSymbol) new SimpleSymbol("test-runner-on-final!").readResolve();
        Lit45 = (SimpleSymbol) new SimpleSymbol("test-runner-on-final").readResolve();
        Lit44 = (SimpleSymbol) new SimpleSymbol("test-runner-on-group-end!").readResolve();
        Lit43 = (SimpleSymbol) new SimpleSymbol("test-runner-on-group-end").readResolve();
        Lit42 = (SimpleSymbol) new SimpleSymbol("test-runner-on-group-begin!").readResolve();
        Lit41 = (SimpleSymbol) new SimpleSymbol("test-runner-on-group-begin").readResolve();
        Lit40 = (SimpleSymbol) new SimpleSymbol("test-runner-on-test-end!").readResolve();
        Lit39 = (SimpleSymbol) new SimpleSymbol("test-runner-on-test-end").readResolve();
        Lit38 = (SimpleSymbol) new SimpleSymbol("test-runner-on-test-begin!").readResolve();
        Lit37 = (SimpleSymbol) new SimpleSymbol("test-runner-on-test-begin").readResolve();
        Lit36 = (SimpleSymbol) new SimpleSymbol("test-runner-group-stack!").readResolve();
        Lit35 = (SimpleSymbol) new SimpleSymbol("test-runner-group-stack").readResolve();
        Lit30 = (SimpleSymbol) new SimpleSymbol("test-runner-skip-count!").readResolve();
        Lit29 = (SimpleSymbol) new SimpleSymbol("test-runner-skip-count").readResolve();
        Lit28 = (SimpleSymbol) new SimpleSymbol("test-runner-xfail-count!").readResolve();
        Lit27 = (SimpleSymbol) new SimpleSymbol("test-runner-xfail-count").readResolve();
        Lit26 = (SimpleSymbol) new SimpleSymbol("test-runner-xpass-count!").readResolve();
        Lit25 = (SimpleSymbol) new SimpleSymbol("test-runner-xpass-count").readResolve();
        Lit24 = (SimpleSymbol) new SimpleSymbol("test-runner-fail-count!").readResolve();
        Lit23 = (SimpleSymbol) new SimpleSymbol("test-runner-fail-count").readResolve();
        Lit22 = (SimpleSymbol) new SimpleSymbol("test-runner-pass-count!").readResolve();
        Lit21 = (SimpleSymbol) new SimpleSymbol("test-runner-pass-count").readResolve();
        Lit20 = (SimpleSymbol) new SimpleSymbol("test-runner?").readResolve();
        Lit19 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b\b\u0011\u0018$\u0011\u0018\u001c\t#\t\u000b\b\u0013", new Object[]{Lit150, PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2834444), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2834444), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2834441), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2834440), Lit52, Lit149, Lit89}, 0);
        Lit18 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\f'\b", new Object[0], 5);
        Lit17 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b#\b\u0011\u0018<\u0011\u0018$\t+\t\u0013\b\u001b", new Object[]{Lit150, PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2809868), LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2809868), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2809865), Lit160, Lit52, Lit149, Lit145, PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2818087), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2818087), Lit89}, 0);
        Lit16 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\f/\b", new Object[0], 6);
        Lit14 = (SimpleSymbol) new SimpleSymbol("fail").readResolve();
        Lit12 = (SimpleSymbol) new SimpleSymbol("pass").readResolve();
        SimpleSymbol simpleSymbol35 = Lit12;
        SimpleSymbol simpleSymbol36 = (SimpleSymbol) new SimpleSymbol("xpass").readResolve();
        Lit9 = simpleSymbol36;
        Lit11 = PairWithPosition.make(simpleSymbol35, PairWithPosition.make(simpleSymbol36, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2220088), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2220082);
        SimpleSymbol simpleSymbol37 = Lit7;
        SimpleSymbol simpleSymbol38 = (SimpleSymbol) new SimpleSymbol("source-file").readResolve();
        Lit4 = simpleSymbol38;
        SimpleSymbol simpleSymbol39 = (SimpleSymbol) new SimpleSymbol("source-line").readResolve();
        Lit5 = simpleSymbol39;
        SimpleSymbol simpleSymbol40 = (SimpleSymbol) new SimpleSymbol("source-form").readResolve();
        Lit6 = simpleSymbol40;
        Lit10 = PairWithPosition.make(simpleSymbol37, PairWithPosition.make(simpleSymbol38, PairWithPosition.make(simpleSymbol39, PairWithPosition.make(simpleSymbol40, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2072618), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2072606), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2072594), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 2072583);
        Lit8 = PairWithPosition.make(Lit14, PairWithPosition.make(Lit9, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1966107), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm", 1966101);
        Lit3 = (SimpleSymbol) new SimpleSymbol("xfail").readResolve();
        Lit2 = (SimpleSymbol) new SimpleSymbol("skip").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("result-kind").readResolve();
        Lit0 = IntNum.make(0);
        $instance = new testing();
        test$Mnrunner = test$Mnrunner.class;
        testing testingVar = $instance;
        test$Mnrunner$Qu = new ModuleMethod(testingVar, 12, Lit20, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnpass$Mncount = new ModuleMethod(testingVar, 13, Lit21, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnpass$Mncount$Ex = new ModuleMethod(testingVar, 14, Lit22, 8194);
        test$Mnrunner$Mnfail$Mncount = new ModuleMethod(testingVar, 15, Lit23, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnfail$Mncount$Ex = new ModuleMethod(testingVar, 16, Lit24, 8194);
        test$Mnrunner$Mnxpass$Mncount = new ModuleMethod(testingVar, 17, Lit25, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnxpass$Mncount$Ex = new ModuleMethod(testingVar, 18, Lit26, 8194);
        test$Mnrunner$Mnxfail$Mncount = new ModuleMethod(testingVar, 19, Lit27, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnxfail$Mncount$Ex = new ModuleMethod(testingVar, 20, Lit28, 8194);
        test$Mnrunner$Mnskip$Mncount = new ModuleMethod(testingVar, 21, Lit29, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnskip$Mncount$Ex = new ModuleMethod(testingVar, 22, Lit30, 8194);
        $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist = new ModuleMethod(testingVar, 23, Lit31, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist$Ex = new ModuleMethod(testingVar, 24, Lit32, 8194);
        $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist = new ModuleMethod(testingVar, 25, Lit33, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist$Ex = new ModuleMethod(testingVar, 26, Lit34, 8194);
        test$Mnrunner$Mngroup$Mnstack = new ModuleMethod(testingVar, 27, Lit35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mngroup$Mnstack$Ex = new ModuleMethod(testingVar, 28, Lit36, 8194);
        test$Mnrunner$Mnon$Mntest$Mnbegin = new ModuleMethod(testingVar, 29, Lit37, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mntest$Mnbegin$Ex = new ModuleMethod(testingVar, 30, Lit38, 8194);
        test$Mnrunner$Mnon$Mntest$Mnend = new ModuleMethod(testingVar, 31, Lit39, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mntest$Mnend$Ex = new ModuleMethod(testingVar, 32, Lit40, 8194);
        test$Mnrunner$Mnon$Mngroup$Mnbegin = new ModuleMethod(testingVar, 33, Lit41, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mngroup$Mnbegin$Ex = new ModuleMethod(testingVar, 34, Lit42, 8194);
        test$Mnrunner$Mnon$Mngroup$Mnend = new ModuleMethod(testingVar, 35, Lit43, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mngroup$Mnend$Ex = new ModuleMethod(testingVar, 36, Lit44, 8194);
        test$Mnrunner$Mnon$Mnfinal = new ModuleMethod(testingVar, 37, Lit45, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mnfinal$Ex = new ModuleMethod(testingVar, 38, Lit46, 8194);
        test$Mnrunner$Mnon$Mnbad$Mncount = new ModuleMethod(testingVar, 39, Lit47, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mnbad$Mncount$Ex = new ModuleMethod(testingVar, 40, Lit48, 8194);
        test$Mnrunner$Mnon$Mnbad$Mnend$Mnname = new ModuleMethod(testingVar, 41, Lit49, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mnbad$Mnend$Mnname$Ex = new ModuleMethod(testingVar, 42, Lit50, 8194);
        test$Mnresult$Mnalist = new ModuleMethod(testingVar, 43, Lit51, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnalist$Ex = new ModuleMethod(testingVar, 44, Lit52, 8194);
        test$Mnrunner$Mnaux$Mnvalue = new ModuleMethod(testingVar, 45, Lit53, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnaux$Mnvalue$Ex = new ModuleMethod(testingVar, 46, Lit54, 8194);
        test$Mnrunner$Mnreset = new ModuleMethod(testingVar, 47, Lit55, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mngroup$Mnpath = new ModuleMethod(testingVar, 48, Lit56, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Pctest$Mnnull$Mncallback = new ModuleMethod(testingVar, 49, Lit57, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ModuleMethod moduleMethod = new ModuleMethod(testingVar, 50, null, 12291);
        moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:182");
        lambda$Fn1 = moduleMethod;
        ModuleMethod moduleMethod2 = new ModuleMethod(testingVar, 51, null, 12291);
        moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:187");
        lambda$Fn2 = moduleMethod2;
        ModuleMethod moduleMethod3 = new ModuleMethod(testingVar, 52, null, 12291);
        moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:188");
        lambda$Fn3 = moduleMethod3;
        test$Mnrunner$Mnnull = new ModuleMethod(testingVar, 53, Lit58, 0);
        test$Mnrunner$Mnsimple = new ModuleMethod(testingVar, 54, Lit59, 0);
        test$Mnrunner$Mnget = new ModuleMethod(testingVar, 55, Lit60, 0);
        test$Mnrunner$Mncreate = new ModuleMethod(testingVar, 56, Lit61, 0);
        $Prvt$$Pctest$Mnshould$Mnexecute = new ModuleMethod(testingVar, 57, Lit62, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Pctest$Mnbegin = new ModuleMethod(testingVar, 58, Lit63, 8194);
        test$Mnon$Mngroup$Mnbegin$Mnsimple = new ModuleMethod(testingVar, 59, Lit64, 12291);
        test$Mnon$Mngroup$Mnend$Mnsimple = new ModuleMethod(testingVar, 60, Lit65, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnon$Mnbad$Mncount$Mnsimple = new ModuleMethod(testingVar, 61, Lit66, 12291);
        test$Mnon$Mnbad$Mnend$Mnname$Mnsimple = new ModuleMethod(testingVar, 62, Lit67, 12291);
        test$Mnon$Mnfinal$Mnsimple = new ModuleMethod(testingVar, 63, Lit68, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnend = new ModuleMethod(testingVar, 64, Lit69, 8194);
        $Prvt$test$Mngroup = Macro.make(Lit70, Lit71, $instance);
        test$Mngroup$Mnwith$Mncleanup = Macro.make(Lit72, Lit73, $instance);
        test$Mnon$Mntest$Mnbegin$Mnsimple = new ModuleMethod(testingVar, 65, Lit74, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnref = Macro.make(Lit75, Lit76, $instance);
        test$Mnon$Mntest$Mnend$Mnsimple = new ModuleMethod(testingVar, 66, Lit77, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnset$Ex = new ModuleMethod(testingVar, 67, Lit78, 12291);
        test$Mnresult$Mnclear = new ModuleMethod(testingVar, 68, Lit79, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnremove = new ModuleMethod(testingVar, 69, Lit80, 8194);
        test$Mnresult$Mnkind = new ModuleMethod(testingVar, 70, Lit81, -4096);
        test$Mnpassed$Qu = new ModuleMethod(testingVar, 71, Lit82, -4096);
        $Prvt$$Pctest$Mnreport$Mnresult = new ModuleMethod(testingVar, 72, Lit83, 0);
        $Prvt$$Pctest$Mnevaluate$Mnwith$Mncatch = Macro.make(Lit84, Lit85, $instance);
        $Prvt$$Pctest$Mnon$Mntest$Mnbegin = new ModuleMethod(testingVar, 73, Lit86, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnon$Mntest$Mnend = new ModuleMethod(testingVar, 74, Lit87, 8194);
        test$Mnrunner$Mntest$Mnname = new ModuleMethod(testingVar, 75, Lit88, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mncomp2body = Macro.make(Lit89, Lit90, $instance);
        $Prvt$$Pctest$Mnapproximimate$Eq = new ModuleMethod(testingVar, 76, Lit91, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mncomp1body = Macro.make(Lit92, Lit93, $instance);
        SimpleSymbol simpleSymbol41 = Lit94;
        ModuleMethod moduleMethod4 = new ModuleMethod(testingVar, 77, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:660");
        test$Mnend = Macro.make(simpleSymbol41, moduleMethod4, $instance);
        SimpleSymbol simpleSymbol42 = Lit99;
        ModuleMethod moduleMethod5 = new ModuleMethod(testingVar, 78, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod5.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:669");
        test$Mnassert = Macro.make(simpleSymbol42, moduleMethod5, $instance);
        SimpleSymbol simpleSymbol43 = Lit104;
        ModuleMethod moduleMethod6 = new ModuleMethod(testingVar, 79, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod6.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:696");
        test$Mneqv = Macro.make(simpleSymbol43, moduleMethod6, $instance);
        SimpleSymbol simpleSymbol44 = Lit106;
        ModuleMethod moduleMethod7 = new ModuleMethod(testingVar, 80, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod7.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:698");
        test$Mneq = Macro.make(simpleSymbol44, moduleMethod7, $instance);
        SimpleSymbol simpleSymbol45 = Lit108;
        ModuleMethod moduleMethod8 = new ModuleMethod(testingVar, 81, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod8.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:700");
        test$Mnequal = Macro.make(simpleSymbol45, moduleMethod8, $instance);
        SimpleSymbol simpleSymbol46 = Lit110;
        ModuleMethod moduleMethod9 = new ModuleMethod(testingVar, 82, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod9.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:702");
        test$Mnapproximate = Macro.make(simpleSymbol46, moduleMethod9, $instance);
        $Prvt$$Pctest$Mnerror = Macro.make(Lit115, Lit116, $instance);
        SimpleSymbol simpleSymbol47 = Lit117;
        ModuleMethod moduleMethod10 = new ModuleMethod(testingVar, 83, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod10.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:843");
        test$Mnerror = Macro.make(simpleSymbol47, moduleMethod10, $instance);
        test$Mnapply = new ModuleMethod(testingVar, 84, Lit124, -4095);
        test$Mnwith$Mnrunner = Macro.make(Lit125, Lit126, $instance);
        $Prvt$$Pctest$Mnmatch$Mnnth = new ModuleMethod(testingVar, 85, Lit127, 8194);
        test$Mnmatch$Mnnth = Macro.make(Lit128, Lit129, $instance);
        $Prvt$$Pctest$Mnmatch$Mnall = new ModuleMethod(testingVar, 86, Lit130, -4096);
        test$Mnmatch$Mnall = Macro.make(Lit131, Lit132, $instance);
        $Prvt$$Pctest$Mnmatch$Mnany = new ModuleMethod(testingVar, 87, Lit133, -4096);
        test$Mnmatch$Mnany = Macro.make(Lit134, Lit135, $instance);
        $Prvt$$Pctest$Mnas$Mnspecifier = new ModuleMethod(testingVar, 88, Lit136, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnskip = Macro.make(Lit137, Lit138, $instance);
        test$Mnexpect$Mnfail = Macro.make(Lit139, Lit140, $instance);
        test$Mnmatch$Mnname = new ModuleMethod(testingVar, 89, Lit141, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnread$Mneval$Mnstring = new ModuleMethod(testingVar, 90, Lit142, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    static test$Mnrunner $PcTestRunnerAlloc() {
        return new test$Mnrunner();
    }

    public static boolean isTestRunner(Object obj) {
        return obj instanceof test$Mnrunner;
    }

    @Override // gnu.expr.ModuleBody
    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 12:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 13:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 14:
            case 16:
            case 18:
            case 20:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 34:
            case 36:
            case 38:
            case 40:
            case 42:
            case 44:
            case 46:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 59:
            case 61:
            case 62:
            case 64:
            case 67:
            case 69:
            case 70:
            case 71:
            case 72:
            case 74:
            case 84:
            case 85:
            case 86:
            case 87:
            default:
                return super.match1(moduleMethod, obj, callContext);
            case 15:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 17:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 19:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 21:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 23:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 25:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 27:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 29:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 31:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 33:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 35:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 37:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 39:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 41:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 43:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 45:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 47:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 48:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 49:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 57:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 60:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 63:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 65:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 66:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 68:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 73:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 75:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 76:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 77:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 78:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 79:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 80:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 81:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 82:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 83:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 88:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 89:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 90:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
        }
    }

    public static Object testRunnerPassCount(test$Mnrunner obj) {
        return obj.pass$Mncount;
    }

    @Override // gnu.expr.ModuleBody
    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 14:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 16:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 18:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 20:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 22:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 24:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 26:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 28:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 30:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 32:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 34:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 36:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 38:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 40:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 42:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 44:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 46:
                if (obj instanceof test$Mnrunner) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 58:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 64:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 69:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 74:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 85:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object testRunnerFailCount(test$Mnrunner obj) {
        return obj.fail$Mncount;
    }

    public static Object testRunnerXpassCount(test$Mnrunner obj) {
        return obj.xpass$Mncount;
    }

    public static Object testRunnerXfailCount(test$Mnrunner obj) {
        return obj.xfail$Mncount;
    }

    public static Object testRunnerSkipCount(test$Mnrunner obj) {
        return obj.skip$Mncount;
    }

    public static Object testRunnerGroupStack(test$Mnrunner obj) {
        return obj.group$Mnstack;
    }

    public static Object testRunnerOnTestBegin(test$Mnrunner obj) {
        return obj.on$Mntest$Mnbegin;
    }

    public static Object testRunnerOnTestEnd(test$Mnrunner obj) {
        return obj.on$Mntest$Mnend;
    }

    public static Object testRunnerOnGroupBegin(test$Mnrunner obj) {
        return obj.on$Mngroup$Mnbegin;
    }

    public static Object testRunnerOnGroupEnd(test$Mnrunner obj) {
        return obj.on$Mngroup$Mnend;
    }

    public static Object testRunnerOnFinal(test$Mnrunner obj) {
        return obj.on$Mnfinal;
    }

    public static Object testRunnerOnBadCount(test$Mnrunner obj) {
        return obj.on$Mnbad$Mncount;
    }

    public static Object testRunnerOnBadEndName(test$Mnrunner obj) {
        return obj.on$Mnbad$Mnend$Mnname;
    }

    public static Object testResultAlist(test$Mnrunner obj) {
        return obj.result$Mnalist;
    }

    public static Object testRunnerAuxValue(test$Mnrunner obj) {
        return obj.aux$Mnvalue;
    }

    public static void testRunnerReset(Object runner) {
        try {
            ((test$Mnrunner) runner).result$Mnalist = LList.Empty;
            try {
                ((test$Mnrunner) runner).pass$Mncount = Lit0;
                try {
                    ((test$Mnrunner) runner).fail$Mncount = Lit0;
                    try {
                        ((test$Mnrunner) runner).xpass$Mncount = Lit0;
                        try {
                            ((test$Mnrunner) runner).xfail$Mncount = Lit0;
                            try {
                                ((test$Mnrunner) runner).skip$Mncount = Lit0;
                                try {
                                    ((test$Mnrunner) runner).total$Mncount = Lit0;
                                    try {
                                        ((test$Mnrunner) runner).count$Mnlist = LList.Empty;
                                        try {
                                            ((test$Mnrunner) runner).run$Mnlist = Boolean.TRUE;
                                            try {
                                                ((test$Mnrunner) runner).skip$Mnlist = LList.Empty;
                                                try {
                                                    ((test$Mnrunner) runner).fail$Mnlist = LList.Empty;
                                                    try {
                                                        ((test$Mnrunner) runner).skip$Mnsave = LList.Empty;
                                                        try {
                                                            ((test$Mnrunner) runner).fail$Mnsave = LList.Empty;
                                                            try {
                                                                ((test$Mnrunner) runner).group$Mnstack = LList.Empty;
                                                            } catch (ClassCastException e) {
                                                                throw new WrongType(e, "test-runner-group-stack!", 0, runner);
                                                            }
                                                        } catch (ClassCastException e2) {
                                                            throw new WrongType(e2, "%test-runner-fail-save!", 0, runner);
                                                        }
                                                    } catch (ClassCastException e3) {
                                                        throw new WrongType(e3, "%test-runner-skip-save!", 0, runner);
                                                    }
                                                } catch (ClassCastException e4) {
                                                    throw new WrongType(e4, "%test-runner-fail-list!", 0, runner);
                                                }
                                            } catch (ClassCastException e5) {
                                                throw new WrongType(e5, "%test-runner-skip-list!", 0, runner);
                                            }
                                        } catch (ClassCastException e6) {
                                            throw new WrongType(e6, "%test-runner-run-list!", 0, runner);
                                        }
                                    } catch (ClassCastException e7) {
                                        throw new WrongType(e7, "%test-runner-count-list!", 0, runner);
                                    }
                                } catch (ClassCastException e8) {
                                    throw new WrongType(e8, "%test-runner-total-count!", 0, runner);
                                }
                            } catch (ClassCastException e9) {
                                throw new WrongType(e9, "test-runner-skip-count!", 0, runner);
                            }
                        } catch (ClassCastException e10) {
                            throw new WrongType(e10, "test-runner-xfail-count!", 0, runner);
                        }
                    } catch (ClassCastException e11) {
                        throw new WrongType(e11, "test-runner-xpass-count!", 0, runner);
                    }
                } catch (ClassCastException e12) {
                    throw new WrongType(e12, "test-runner-fail-count!", 0, runner);
                }
            } catch (ClassCastException e13) {
                throw new WrongType(e13, "test-runner-pass-count!", 0, runner);
            }
        } catch (ClassCastException e14) {
            throw new WrongType(e14, "test-result-alist!", 0, runner);
        }
    }

    public static LList testRunnerGroupPath(Object runner) {
        try {
            Object testRunnerGroupStack = testRunnerGroupStack((test$Mnrunner) runner);
            try {
                return lists.reverse((LList) testRunnerGroupStack);
            } catch (ClassCastException e) {
                throw new WrongType(e, "reverse", 1, testRunnerGroupStack);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-group-stack", 0, runner);
        }
    }

    static Boolean $PcTestNullCallback(Object runner) {
        return Boolean.FALSE;
    }

    public static test$Mnrunner testRunnerNull() {
        test$Mnrunner runner = $PcTestRunnerAlloc();
        testRunnerReset(runner);
        runner.on$Mngroup$Mnbegin = lambda$Fn1;
        runner.on$Mngroup$Mnend = $Pctest$Mnnull$Mncallback;
        runner.on$Mnfinal = $Pctest$Mnnull$Mncallback;
        runner.on$Mntest$Mnbegin = $Pctest$Mnnull$Mncallback;
        runner.on$Mntest$Mnend = $Pctest$Mnnull$Mncallback;
        runner.on$Mnbad$Mncount = lambda$Fn2;
        runner.on$Mnbad$Mnend$Mnname = lambda$Fn3;
        return runner;
    }

    @Override // gnu.expr.ModuleBody
    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 53:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 54:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 55:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 56:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 72:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    static Boolean lambda1(Object runner, Object name, Object count) {
        return Boolean.FALSE;
    }

    @Override // gnu.expr.ModuleBody
    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 50:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 51:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 52:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 59:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 61:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 62:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 67:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    static Boolean lambda2(Object runner, Object count, Object expected) {
        return Boolean.FALSE;
    }

    static Boolean lambda3(Object runner, Object begin, Object end) {
        return Boolean.FALSE;
    }

    public static test$Mnrunner testRunnerSimple() {
        test$Mnrunner runner = $PcTestRunnerAlloc();
        testRunnerReset(runner);
        runner.on$Mngroup$Mnbegin = test$Mnon$Mngroup$Mnbegin$Mnsimple;
        runner.on$Mngroup$Mnend = test$Mnon$Mngroup$Mnend$Mnsimple;
        runner.on$Mnfinal = test$Mnon$Mnfinal$Mnsimple;
        runner.on$Mntest$Mnbegin = test$Mnon$Mntest$Mnbegin$Mnsimple;
        runner.on$Mntest$Mnend = test$Mnon$Mntest$Mnend$Mnsimple;
        runner.on$Mnbad$Mncount = test$Mnon$Mnbad$Mncount$Mnsimple;
        runner.on$Mnbad$Mnend$Mnname = test$Mnon$Mnbad$Mnend$Mnname$Mnsimple;
        return runner;
    }

    public static Object testRunnerGet() {
        Object r = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        if (r == Boolean.FALSE) {
            misc.error$V("test-runner not initialized - test-begin missing?", new Object[0]);
        }
        return r;
    }

    static Object $PcTestSpecificierMatches(Object spec, Object runner) {
        return Scheme.applyToArgs.apply2(spec, runner);
    }

    public static Object testRunnerCreate() {
        return Scheme.applyToArgs.apply1(((Procedure) test$Mnrunner$Mnfactory).apply0());
    }

    static Object $PcTestAnySpecifierMatches(Object list, Object runner) {
        Boolean result = Boolean.FALSE;
        for (Object l = list; !lists.isNull(l); l = lists.cdr.apply1(l)) {
            if ($PcTestSpecificierMatches(lists.car.apply1(l), runner) != Boolean.FALSE) {
                result = Boolean.TRUE;
            }
        }
        return result;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
        if (r2 != 0) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0019, code lost:
        testResultSet$Ex(r8, gnu.kawa.slib.testing.Lit1, gnu.kawa.slib.testing.Lit2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0022, code lost:
        return java.lang.Boolean.FALSE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003f, code lost:
        if ($PcTestAnySpecifierMatches(((gnu.kawa.slib.test$Mnrunner) r8).skip$Mnlist, r8) == java.lang.Boolean.FALSE) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004f, code lost:
        if ($PcTestAnySpecifierMatches(((gnu.kawa.slib.test$Mnrunner) r8).fail$Mnlist, r8) == java.lang.Boolean.FALSE) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0051, code lost:
        testResultSet$Ex(r8, gnu.kawa.slib.testing.Lit1, gnu.kawa.slib.testing.Lit3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007a, code lost:
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0082, code lost:
        throw new gnu.mapping.WrongType(r3, "%test-runner-fail-list", 0, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:?, code lost:
        return gnu.kawa.slib.testing.Lit3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:?, code lost:
        return java.lang.Boolean.TRUE;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object $PcTestShouldExecute(java.lang.Object r8) {
        /*
            r4 = 1
            r5 = 0
            r0 = r8
            gnu.kawa.slib.test$Mnrunner r0 = (gnu.kawa.slib.test$Mnrunner) r0     // Catch: java.lang.ClassCastException -> L5e
            r3 = r0
            java.lang.Object r1 = $PcTestRunnerRunList(r3)
            java.lang.Boolean r3 = java.lang.Boolean.TRUE
            if (r1 != r3) goto L23
            r2 = r4
        Lf:
            if (r2 == 0) goto L25
        L11:
            int r3 = r2 + 1
            r2 = r3 & 1
            if (r2 == 0) goto L31
            if (r2 == 0) goto L41
        L19:
            gnu.mapping.SimpleSymbol r3 = gnu.kawa.slib.testing.Lit1
            gnu.mapping.SimpleSymbol r4 = gnu.kawa.slib.testing.Lit2
            testResultSet$Ex(r8, r3, r4)
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
        L22:
            return r3
        L23:
            r2 = r5
            goto Lf
        L25:
            java.lang.Object r3 = $PcTestAnySpecifierMatches(r1, r8)
            java.lang.Boolean r6 = java.lang.Boolean.FALSE     // Catch: java.lang.ClassCastException -> L67
            if (r3 == r6) goto L2f
            r2 = r4
            goto L11
        L2f:
            r2 = r5
            goto L11
        L31:
            r0 = r8
            gnu.kawa.slib.test$Mnrunner r0 = (gnu.kawa.slib.test$Mnrunner) r0     // Catch: java.lang.ClassCastException -> L71
            r3 = r0
            java.lang.Object r3 = $PcTestRunnerSkipList(r3)
            java.lang.Object r3 = $PcTestAnySpecifierMatches(r3, r8)
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            if (r3 != r4) goto L19
        L41:
            r0 = r8
            gnu.kawa.slib.test$Mnrunner r0 = (gnu.kawa.slib.test$Mnrunner) r0     // Catch: java.lang.ClassCastException -> L7a
            r3 = r0
            java.lang.Object r3 = $PcTestRunnerFailList(r3)
            java.lang.Object r3 = $PcTestAnySpecifierMatches(r3, r8)
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            if (r3 == r4) goto L5b
            gnu.mapping.SimpleSymbol r3 = gnu.kawa.slib.testing.Lit1
            gnu.mapping.SimpleSymbol r4 = gnu.kawa.slib.testing.Lit3
            testResultSet$Ex(r8, r3, r4)
            gnu.mapping.SimpleSymbol r3 = gnu.kawa.slib.testing.Lit3
            goto L22
        L5b:
            java.lang.Boolean r3 = java.lang.Boolean.TRUE
            goto L22
        L5e:
            r3 = move-exception
            gnu.mapping.WrongType r4 = new gnu.mapping.WrongType
            java.lang.String r6 = "%test-runner-run-list"
            r4.<init>(r3, r6, r5, r8)
            throw r4
        L67:
            r4 = move-exception
            gnu.mapping.WrongType r5 = new gnu.mapping.WrongType
            java.lang.String r6 = "x"
            r7 = -2
            r5.<init>(r4, r6, r7, r3)
            throw r5
        L71:
            r3 = move-exception
            gnu.mapping.WrongType r4 = new gnu.mapping.WrongType
            java.lang.String r6 = "%test-runner-skip-list"
            r4.<init>(r3, r6, r5, r8)
            throw r4
        L7a:
            r3 = move-exception
            gnu.mapping.WrongType r4 = new gnu.mapping.WrongType
            java.lang.String r6 = "%test-runner-fail-list"
            r4.<init>(r3, r6, r5, r8)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.testing.$PcTestShouldExecute(java.lang.Object):java.lang.Object");
    }

    public static void $PcTestBegin(Object suite$Mnname, Object count) {
        if (((Procedure) test$Mnrunner$Mncurrent).apply0() == Boolean.FALSE) {
            ((Procedure) test$Mnrunner$Mncurrent).apply1(testRunnerCreate());
        }
        Object runner = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        try {
            Scheme.applyToArgs.apply4(testRunnerOnGroupBegin((test$Mnrunner) runner), runner, suite$Mnname, count);
            try {
                try {
                    try {
                        ((test$Mnrunner) runner).skip$Mnsave = lists.cons(((test$Mnrunner) runner).skip$Mnlist, ((test$Mnrunner) runner).skip$Mnsave);
                        try {
                            try {
                                try {
                                    ((test$Mnrunner) runner).fail$Mnsave = lists.cons(((test$Mnrunner) runner).fail$Mnlist, ((test$Mnrunner) runner).fail$Mnsave);
                                    try {
                                        try {
                                            try {
                                                ((test$Mnrunner) runner).count$Mnlist = lists.cons(lists.cons(((test$Mnrunner) runner).total$Mncount, count), ((test$Mnrunner) runner).count$Mnlist);
                                                try {
                                                    try {
                                                        ((test$Mnrunner) runner).group$Mnstack = lists.cons(suite$Mnname, testRunnerGroupStack((test$Mnrunner) runner));
                                                    } catch (ClassCastException e) {
                                                        throw new WrongType(e, "test-runner-group-stack", 0, runner);
                                                    }
                                                } catch (ClassCastException e2) {
                                                    throw new WrongType(e2, "test-runner-group-stack!", 0, runner);
                                                }
                                            } catch (ClassCastException e3) {
                                                throw new WrongType(e3, "%test-runner-count-list", 0, runner);
                                            }
                                        } catch (ClassCastException e4) {
                                            throw new WrongType(e4, "%test-runner-total-count", 0, runner);
                                        }
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "%test-runner-count-list!", 0, runner);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "%test-runner-fail-save", 0, runner);
                                }
                            } catch (ClassCastException e7) {
                                throw new WrongType(e7, "%test-runner-fail-list", 0, runner);
                            }
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "%test-runner-fail-save!", 0, runner);
                        }
                    } catch (ClassCastException e9) {
                        throw new WrongType(e9, "%test-runner-skip-save", 0, runner);
                    }
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "%test-runner-skip-list", 0, runner);
                }
            } catch (ClassCastException e11) {
                throw new WrongType(e11, "%test-runner-skip-save!", 0, runner);
            }
        } catch (ClassCastException e12) {
            throw new WrongType(e12, "test-runner-on-group-begin", 0, runner);
        }
    }

    public static Boolean testOnGroupBeginSimple(Object runner, Object suite$Mnname, Object count) {
        try {
            if (lists.isNull(testRunnerGroupStack((test$Mnrunner) runner))) {
                ports.display("%%%% Starting test ");
                ports.display(suite$Mnname);
                Object log$Mnfile$Mnname = strings.isString(Boolean.TRUE) ? Boolean.TRUE : strings.stringAppend(suite$Mnname, ".log");
                try {
                    OutPort log$Mnfile = ports.openOutputFile(Path.valueOf(log$Mnfile$Mnname));
                    ports.display("%%%% Starting test ", log$Mnfile);
                    ports.display(suite$Mnname, log$Mnfile);
                    ports.newline(log$Mnfile);
                    try {
                        ((test$Mnrunner) runner).aux$Mnvalue = log$Mnfile;
                        ports.display("  (Writing full log to \"");
                        ports.display(log$Mnfile$Mnname);
                        ports.display("\")");
                        ports.newline();
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "test-runner-aux-value!", 0, runner);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "open-output-file", 1, log$Mnfile$Mnname);
                }
            }
            try {
                Object log = testRunnerAuxValue((test$Mnrunner) runner);
                if (ports.isOutputPort(log)) {
                    ports.display("Group begin: ", log);
                    ports.display(suite$Mnname, log);
                    ports.newline(log);
                }
                return Boolean.FALSE;
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "test-runner-aux-value", 0, runner);
            }
        } catch (ClassCastException e4) {
            throw new WrongType(e4, "test-runner-group-stack", 0, runner);
        }
    }

    public static Boolean testOnGroupEndSimple(Object runner) {
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (ports.isOutputPort(log)) {
                ports.display("Group end: ", log);
                try {
                    ports.display(lists.car.apply1(testRunnerGroupStack((test$Mnrunner) runner)), log);
                    ports.newline(log);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-group-stack", 0, runner);
                }
            }
            return Boolean.FALSE;
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-aux-value", 0, runner);
        }
    }

    static void $PcTestOnBadCountWrite(Object runner, Object count, Object expected$Mncount, Object port) {
        ports.display("*** Total number of tests was ", port);
        ports.display(count, port);
        ports.display(" but should be ", port);
        ports.display(expected$Mncount, port);
        ports.display(". ***", port);
        ports.newline(port);
        ports.display("*** Discrepancy indicates testsuite error or exceptions. ***", port);
        ports.newline(port);
    }

    public static void testOnBadCountSimple(Object runner, Object count, Object expected$Mncount) {
        $PcTestOnBadCountWrite(runner, count, expected$Mncount, ports.current$Mnoutput$Mnport.apply0());
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (ports.isOutputPort(log)) {
                $PcTestOnBadCountWrite(runner, count, expected$Mncount, log);
            }
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-runner-aux-value", 0, runner);
        }
    }

    public static Object testOnBadEndNameSimple(Object runner, Object begin$Mnname, Object end$Mnname) {
        FString msg = strings.stringAppend($PcTestFormatLine(runner), "test-end ", begin$Mnname, " does not match test-begin ", end$Mnname);
        return misc.error$V(msg, new Object[0]);
    }

    static void $PcTestFinalReport1(Object value, Object label, Object port) {
        if (Scheme.numGrt.apply2(value, Lit0) != Boolean.FALSE) {
            ports.display(label, port);
            ports.display(value, port);
            ports.newline(port);
        }
    }

    static void $PcTestFinalReportSimple(Object runner, Object port) {
        try {
            $PcTestFinalReport1(testRunnerPassCount((test$Mnrunner) runner), "# of expected passes      ", port);
            try {
                $PcTestFinalReport1(testRunnerXfailCount((test$Mnrunner) runner), "# of expected failures    ", port);
                try {
                    $PcTestFinalReport1(testRunnerXpassCount((test$Mnrunner) runner), "# of unexpected successes ", port);
                    try {
                        $PcTestFinalReport1(testRunnerFailCount((test$Mnrunner) runner), "# of unexpected failures  ", port);
                        try {
                            $PcTestFinalReport1(testRunnerSkipCount((test$Mnrunner) runner), "# of skipped tests        ", port);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "test-runner-skip-count", 0, runner);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "test-runner-fail-count", 0, runner);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "test-runner-xpass-count", 0, runner);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "test-runner-xfail-count", 0, runner);
            }
        } catch (ClassCastException e5) {
            throw new WrongType(e5, "test-runner-pass-count", 0, runner);
        }
    }

    public static void testOnFinalSimple(Object runner) {
        $PcTestFinalReportSimple(runner, ports.current$Mnoutput$Mnport.apply0());
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (ports.isOutputPort(log)) {
                $PcTestFinalReportSimple(runner, log);
            }
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-runner-aux-value", 0, runner);
        }
    }

    static Object $PcTestFormatLine(Object runner) {
        try {
            Object line$Mninfo = testResultAlist((test$Mnrunner) runner);
            Object source$Mnfile = lists.assq(Lit4, line$Mninfo);
            Object source$Mnline = lists.assq(Lit5, line$Mninfo);
            String file = source$Mnfile != Boolean.FALSE ? lists.cdr.apply1(source$Mnfile) : "";
            if (source$Mnline == Boolean.FALSE) {
                return "";
            }
            Object[] objArr = new Object[4];
            objArr[0] = file;
            objArr[1] = ":";
            Object apply1 = lists.cdr.apply1(source$Mnline);
            try {
                objArr[2] = numbers.number$To$String((Number) apply1, 10);
                objArr[3] = ": ";
                return strings.stringAppend(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "number->string", 1, apply1);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-result-alist", 0, runner);
        }
    }

    public static Object $PcTestEnd(Object suite$Mnname, Object line$Mninfo) {
        Object r = testRunnerGet();
        try {
            Object groups = testRunnerGroupStack((test$Mnrunner) r);
            Object line = $PcTestFormatLine(r);
            try {
                ((test$Mnrunner) r).result$Mnalist = line$Mninfo;
                if (lists.isNull(groups)) {
                    FString msg = strings.stringAppend(line, "test-end not in a group");
                    misc.error$V(msg, new Object[0]);
                }
                if (suite$Mnname == Boolean.FALSE ? suite$Mnname != Boolean.FALSE : !IsEqual.apply(suite$Mnname, lists.car.apply1(groups))) {
                    try {
                        Scheme.applyToArgs.apply4(testRunnerOnBadEndName((test$Mnrunner) r), r, suite$Mnname, lists.car.apply1(groups));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "test-runner-on-bad-end-name", 0, r);
                    }
                }
                try {
                    Object count$Mnlist = ((test$Mnrunner) r).count$Mnlist;
                    Object expected$Mncount = lists.cdar.apply1(count$Mnlist);
                    Object saved$Mncount = lists.caar.apply1(count$Mnlist);
                    try {
                        Object group$Mncount = AddOp.$Mn.apply2(((test$Mnrunner) r).total$Mncount, saved$Mncount);
                        if (expected$Mncount == Boolean.FALSE ? expected$Mncount != Boolean.FALSE : Scheme.numEqu.apply2(expected$Mncount, group$Mncount) == Boolean.FALSE) {
                            try {
                                Scheme.applyToArgs.apply4(testRunnerOnBadCount((test$Mnrunner) r), r, group$Mncount, expected$Mncount);
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "test-runner-on-bad-count", 0, r);
                            }
                        }
                        try {
                            Scheme.applyToArgs.apply2(testRunnerOnGroupEnd((test$Mnrunner) r), r);
                            try {
                                try {
                                    ((test$Mnrunner) r).group$Mnstack = lists.cdr.apply1(testRunnerGroupStack((test$Mnrunner) r));
                                    try {
                                        try {
                                            ((test$Mnrunner) r).skip$Mnlist = lists.car.apply1(((test$Mnrunner) r).skip$Mnsave);
                                            try {
                                                try {
                                                    ((test$Mnrunner) r).skip$Mnsave = lists.cdr.apply1(((test$Mnrunner) r).skip$Mnsave);
                                                    try {
                                                        try {
                                                            ((test$Mnrunner) r).fail$Mnlist = lists.car.apply1(((test$Mnrunner) r).fail$Mnsave);
                                                            try {
                                                                try {
                                                                    ((test$Mnrunner) r).fail$Mnsave = lists.cdr.apply1(((test$Mnrunner) r).fail$Mnsave);
                                                                    try {
                                                                        ((test$Mnrunner) r).count$Mnlist = lists.cdr.apply1(count$Mnlist);
                                                                        try {
                                                                            if (lists.isNull(testRunnerGroupStack((test$Mnrunner) r))) {
                                                                                try {
                                                                                    return Scheme.applyToArgs.apply2(testRunnerOnFinal((test$Mnrunner) r), r);
                                                                                } catch (ClassCastException e3) {
                                                                                    throw new WrongType(e3, "test-runner-on-final", 0, r);
                                                                                }
                                                                            }
                                                                            return Values.empty;
                                                                        } catch (ClassCastException e4) {
                                                                            throw new WrongType(e4, "test-runner-group-stack", 0, r);
                                                                        }
                                                                    } catch (ClassCastException e5) {
                                                                        throw new WrongType(e5, "%test-runner-count-list!", 0, r);
                                                                    }
                                                                } catch (ClassCastException e6) {
                                                                    throw new WrongType(e6, "%test-runner-fail-save", 0, r);
                                                                }
                                                            } catch (ClassCastException e7) {
                                                                throw new WrongType(e7, "%test-runner-fail-save!", 0, r);
                                                            }
                                                        } catch (ClassCastException e8) {
                                                            throw new WrongType(e8, "%test-runner-fail-save", 0, r);
                                                        }
                                                    } catch (ClassCastException e9) {
                                                        throw new WrongType(e9, "%test-runner-fail-list!", 0, r);
                                                    }
                                                } catch (ClassCastException e10) {
                                                    throw new WrongType(e10, "%test-runner-skip-save", 0, r);
                                                }
                                            } catch (ClassCastException e11) {
                                                throw new WrongType(e11, "%test-runner-skip-save!", 0, r);
                                            }
                                        } catch (ClassCastException e12) {
                                            throw new WrongType(e12, "%test-runner-skip-save", 0, r);
                                        }
                                    } catch (ClassCastException e13) {
                                        throw new WrongType(e13, "%test-runner-skip-list!", 0, r);
                                    }
                                } catch (ClassCastException e14) {
                                    throw new WrongType(e14, "test-runner-group-stack", 0, r);
                                }
                            } catch (ClassCastException e15) {
                                throw new WrongType(e15, "test-runner-group-stack!", 0, r);
                            }
                        } catch (ClassCastException e16) {
                            throw new WrongType(e16, "test-runner-on-group-end", 0, r);
                        }
                    } catch (ClassCastException e17) {
                        throw new WrongType(e17, "%test-runner-total-count", 0, r);
                    }
                } catch (ClassCastException e18) {
                    throw new WrongType(e18, "%test-runner-count-list", 0, r);
                }
            } catch (ClassCastException e19) {
                throw new WrongType(e19, "test-result-alist!", 0, r);
            }
        } catch (ClassCastException e20) {
            throw new WrongType(e20, "test-runner-group-stack", 0, r);
        }
    }

    static Object testOnTestBeginSimple(Object runner) {
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (ports.isOutputPort(log)) {
                try {
                    Object results = testResultAlist((test$Mnrunner) runner);
                    Object source$Mnfile = lists.assq(Lit4, results);
                    Object source$Mnline = lists.assq(Lit5, results);
                    Object source$Mnform = lists.assq(Lit6, results);
                    Object test$Mnname = lists.assq(Lit7, results);
                    ports.display("Test begin:", log);
                    ports.newline(log);
                    if (test$Mnname != Boolean.FALSE) {
                        $PcTestWriteResult1(test$Mnname, log);
                    }
                    if (source$Mnfile != Boolean.FALSE) {
                        $PcTestWriteResult1(source$Mnfile, log);
                    }
                    if (source$Mnline != Boolean.FALSE) {
                        $PcTestWriteResult1(source$Mnline, log);
                    }
                    return source$Mnfile != Boolean.FALSE ? $PcTestWriteResult1(source$Mnform, log) : Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-result-alist", 0, runner);
                }
            }
            return Values.empty;
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-aux-value", 0, runner);
        }
    }

    public static Object testOnTestEndSimple(Object runner) {
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            try {
                Object p = lists.assq(Lit1, testResultAlist((test$Mnrunner) runner));
                Object kind = p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE;
                if (lists.memq(kind, Lit8) != Boolean.FALSE) {
                    try {
                        Object results = testResultAlist((test$Mnrunner) runner);
                        Object source$Mnfile = lists.assq(Lit4, results);
                        Object source$Mnline = lists.assq(Lit5, results);
                        Object test$Mnname = lists.assq(Lit7, results);
                        if (source$Mnfile != Boolean.FALSE || source$Mnline != Boolean.FALSE) {
                            if (source$Mnfile != Boolean.FALSE) {
                                ports.display(lists.cdr.apply1(source$Mnfile));
                            }
                            ports.display(":");
                            if (source$Mnline != Boolean.FALSE) {
                                ports.display(lists.cdr.apply1(source$Mnline));
                            }
                            ports.display(": ");
                        }
                        ports.display(kind == Lit9 ? "XPASS" : "FAIL");
                        if (test$Mnname != Boolean.FALSE) {
                            ports.display(" ");
                            ports.display(lists.cdr.apply1(test$Mnname));
                        }
                        ports.newline();
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "test-result-alist", 0, runner);
                    }
                }
                if (ports.isOutputPort(log)) {
                    ports.display("Test end:", log);
                    ports.newline(log);
                    try {
                        for (Object list = testResultAlist((test$Mnrunner) runner); lists.isPair(list); list = lists.cdr.apply1(list)) {
                            Object pair = lists.car.apply1(list);
                            if (lists.memq(lists.car.apply1(pair), Lit10) == Boolean.FALSE) {
                                $PcTestWriteResult1(pair, log);
                            }
                        }
                        return Values.empty;
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "test-result-alist", 0, runner);
                    }
                }
                return Values.empty;
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "test-result-alist", 0, runner);
            }
        } catch (ClassCastException e4) {
            throw new WrongType(e4, "test-runner-aux-value", 0, runner);
        }
    }

    static Object $PcTestWriteResult1(Object pair, Object port) {
        ports.display("  ", port);
        ports.display(lists.car.apply1(pair), port);
        ports.display(": ", port);
        ports.write(lists.cdr.apply1(pair), port);
        ports.newline(port);
        return Values.empty;
    }

    public static Object testResultSet$Ex(Object runner, Object pname, Object value) {
        try {
            Object alist = testResultAlist((test$Mnrunner) runner);
            Object p = lists.assq(pname, alist);
            if (p != Boolean.FALSE) {
                try {
                    ((Pair) p).setCdr(value);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-cdr!", 1, p);
                }
            }
            try {
                ((test$Mnrunner) runner).result$Mnalist = lists.cons(lists.cons(pname, value), alist);
                return Values.empty;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "test-result-alist!", 0, runner);
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "test-result-alist", 0, runner);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 50:
                return lambda1(obj, obj2, obj3);
            case 51:
                return lambda2(obj, obj2, obj3);
            case 52:
                return lambda3(obj, obj2, obj3);
            case 59:
                return testOnGroupBeginSimple(obj, obj2, obj3);
            case 61:
                testOnBadCountSimple(obj, obj2, obj3);
                return Values.empty;
            case 62:
                return testOnBadEndNameSimple(obj, obj2, obj3);
            case 67:
                return testResultSet$Ex(obj, obj2, obj3);
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static void testResultClear(Object runner) {
        try {
            ((test$Mnrunner) runner).result$Mnalist = LList.Empty;
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist!", 0, runner);
        }
    }

    public static void testResultRemove(Object runner, Object pname) {
        frame frameVar = new frame();
        try {
            Object alist = testResultAlist((test$Mnrunner) runner);
            frameVar.p = lists.assq(pname, alist);
            if (frameVar.p != Boolean.FALSE) {
                try {
                    ((test$Mnrunner) runner).result$Mnalist = frameVar.lambda4loop(alist);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-result-alist!", 0, runner);
                }
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-result-alist", 0, runner);
        }
    }

    /* compiled from: testing.scm */
    /* loaded from: classes.dex */
    public class frame extends ModuleBody {
        Object p;

        public Object lambda4loop(Object r) {
            return r == this.p ? lists.cdr.apply1(r) : lists.cons(lists.car.apply1(r), lambda4loop(lists.cdr.apply1(r)));
        }
    }

    public static Object testResultKind$V(Object[] argsArray) {
        LList rest = LList.makeList(argsArray, 0);
        Object runner = lists.isPair(rest) ? lists.car.apply1(rest) : ((Procedure) test$Mnrunner$Mncurrent).apply0();
        try {
            Object p = lists.assq(Lit1, testResultAlist((test$Mnrunner) runner));
            return p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE;
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, runner);
        }
    }

    @Override // gnu.expr.ModuleBody
    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 70:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 71:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 84:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 86:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 87:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Object isTestPassed$V(Object[] argsArray) {
        LList rest = LList.makeList(argsArray, 0);
        Object runner = lists.isPair(rest) ? lists.car.apply1(rest) : testRunnerGet();
        try {
            Object p = lists.assq(Lit1, testResultAlist((test$Mnrunner) runner));
            return lists.memq(p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE, Lit11);
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, runner);
        }
    }

    public static Object $PcTestReportResult() {
        Object r = testRunnerGet();
        Object result$Mnkind = testResultKind$V(new Object[]{r});
        if (Scheme.isEqv.apply2(result$Mnkind, Lit12) != Boolean.FALSE) {
            try {
                try {
                    ((test$Mnrunner) r).pass$Mncount = AddOp.$Pl.apply2(Lit13, testRunnerPassCount((test$Mnrunner) r));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-pass-count", 0, r);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "test-runner-pass-count!", 0, r);
            }
        } else if (Scheme.isEqv.apply2(result$Mnkind, Lit14) != Boolean.FALSE) {
            try {
                try {
                    ((test$Mnrunner) r).fail$Mncount = AddOp.$Pl.apply2(Lit13, testRunnerFailCount((test$Mnrunner) r));
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "test-runner-fail-count", 0, r);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "test-runner-fail-count!", 0, r);
            }
        } else if (Scheme.isEqv.apply2(result$Mnkind, Lit9) != Boolean.FALSE) {
            try {
                try {
                    ((test$Mnrunner) r).xpass$Mncount = AddOp.$Pl.apply2(Lit13, testRunnerXpassCount((test$Mnrunner) r));
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "test-runner-xpass-count", 0, r);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "test-runner-xpass-count!", 0, r);
            }
        } else if (Scheme.isEqv.apply2(result$Mnkind, Lit3) != Boolean.FALSE) {
            try {
                try {
                    ((test$Mnrunner) r).xfail$Mncount = AddOp.$Pl.apply2(Lit13, testRunnerXfailCount((test$Mnrunner) r));
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "test-runner-xfail-count", 0, r);
                }
            } catch (ClassCastException e8) {
                throw new WrongType(e8, "test-runner-xfail-count!", 0, r);
            }
        } else {
            try {
                try {
                    ((test$Mnrunner) r).skip$Mncount = AddOp.$Pl.apply2(Lit13, testRunnerSkipCount((test$Mnrunner) r));
                } catch (ClassCastException e9) {
                    throw new WrongType(e9, "test-runner-skip-count", 0, r);
                }
            } catch (ClassCastException e10) {
                throw new WrongType(e10, "test-runner-skip-count!", 0, r);
            }
        }
        try {
            try {
                ((test$Mnrunner) r).total$Mncount = AddOp.$Pl.apply2(Lit13, ((test$Mnrunner) r).total$Mncount);
                try {
                    return Scheme.applyToArgs.apply2(testRunnerOnTestEnd((test$Mnrunner) r), r);
                } catch (ClassCastException e11) {
                    throw new WrongType(e11, "test-runner-on-test-end", 0, r);
                }
            } catch (ClassCastException e12) {
                throw new WrongType(e12, "%test-runner-total-count", 0, r);
            }
        } catch (ClassCastException e13) {
            throw new WrongType(e13, "%test-runner-total-count!", 0, r);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 53:
                return testRunnerNull();
            case 54:
                return testRunnerSimple();
            case 55:
                return testRunnerGet();
            case 56:
                return testRunnerCreate();
            case 72:
                return $PcTestReportResult();
            default:
                return super.apply0(moduleMethod);
        }
    }

    static Pair $PcTestSourceLine2(Object form) {
        Object line = std_syntax.syntaxLine(form);
        Object file = std_syntax.syntaxSource(form);
        Object line$Mnpair = line != Boolean.FALSE ? LList.list1(lists.cons(Lit5, line)) : LList.Empty;
        Pair cons = lists.cons(Lit6, Quote.quote(form));
        if (file != Boolean.FALSE) {
            line$Mnpair = lists.cons(lists.cons(Lit4, file), line$Mnpair);
        }
        return lists.cons(cons, line$Mnpair);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean $PcTestOnTestBegin(Object r) {
        $PcTestShouldExecute(r);
        try {
            Scheme.applyToArgs.apply2(testRunnerOnTestBegin((test$Mnrunner) r), r);
            SimpleSymbol simpleSymbol = Lit2;
            try {
                Object p = lists.assq(Lit1, testResultAlist((test$Mnrunner) r));
                return ((simpleSymbol == (p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE) ? 1 : 0) + 1) & 1;
            } catch (ClassCastException e) {
                throw new WrongType(e, "test-result-alist", 0, r);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-on-test-begin", 0, r);
        }
    }

    public static Object $PcTestOnTestEnd(Object r, Object result) {
        SimpleSymbol simpleSymbol;
        SimpleSymbol simpleSymbol2 = Lit1;
        try {
            Object p = lists.assq(Lit1, testResultAlist((test$Mnrunner) r));
            if ((p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE) == Lit3) {
                simpleSymbol = result != Boolean.FALSE ? Lit9 : Lit3;
            } else {
                simpleSymbol = result != Boolean.FALSE ? Lit12 : Lit14;
            }
            return testResultSet$Ex(r, simpleSymbol2, simpleSymbol);
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, r);
        }
    }

    public static Object testRunnerTestName(Object runner) {
        try {
            Object p = lists.assq(Lit7, testResultAlist((test$Mnrunner) runner));
            return p != Boolean.FALSE ? lists.cdr.apply1(p) : "";
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, runner);
        }
    }

    /* compiled from: testing.scm */
    /* loaded from: classes.dex */
    public class frame0 extends ModuleBody {
        Object error;
        final ModuleMethod lambda$Fn4;

        public frame0() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:640");
            this.lambda$Fn4 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 1 ? lambda5(obj, obj2) ? Boolean.TRUE : Boolean.FALSE : super.apply2(moduleMethod, obj, obj2);
        }

        boolean lambda5(Object value, Object expected) {
            Object apply2 = Scheme.numGEq.apply2(value, AddOp.$Mn.apply2(expected, this.error));
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                if (!x) {
                    return x;
                }
                return ((Boolean) Scheme.numLEq.apply2(value, AddOp.$Pl.apply2(expected, this.error))).booleanValue();
            } catch (ClassCastException e) {
                throw new WrongType(e, "x", -2, apply2);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 1) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Procedure $PcTestApproximimate$Eq(Object error) {
        frame0 frame0Var = new frame0();
        frame0Var.error = error;
        return frame0Var.lambda$Fn4;
    }

    static Object lambda16(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(3, null);
        if (Lit95.match(list2, allocVars, 0)) {
            return Lit96.execute(allocVars, TemplateScope.make());
        } else if (Lit97.match(list2, allocVars, 0)) {
            return Lit98.execute(allocVars, TemplateScope.make());
        } else {
            return syntax_case.error("syntax-case", list2);
        }
    }

    static Object lambda17(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(4, null);
        if (Lit100.match(list2, allocVars, 0)) {
            return Lit101.execute(allocVars, TemplateScope.make());
        } else if (Lit102.match(list2, allocVars, 0)) {
            return Lit103.execute(allocVars, TemplateScope.make());
        } else {
            return syntax_case.error("syntax-case", list2);
        }
    }

    static Object $PcTestComp2(Object comp, Object x) {
        Pair list3 = LList.list3(x, LList.list2(Lit15, $PcTestSourceLine2(x)), comp);
        Object[] allocVars = SyntaxPattern.allocVars(6, null);
        if (Lit16.match(list3, allocVars, 0)) {
            return Lit17.execute(allocVars, TemplateScope.make());
        } else if (Lit18.match(list3, allocVars, 0)) {
            return Lit19.execute(allocVars, TemplateScope.make());
        } else {
            return syntax_case.error("syntax-case", list3);
        }
    }

    static Object lambda18(Object x) {
        return $PcTestComp2(Lit105.execute(null, TemplateScope.make()), x);
    }

    static Object lambda19(Object x) {
        return $PcTestComp2(Lit107.execute(null, TemplateScope.make()), x);
    }

    static Object lambda20(Object x) {
        return $PcTestComp2(Lit109.execute(null, TemplateScope.make()), x);
    }

    static Object lambda21(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(6, null);
        if (Lit111.match(list2, allocVars, 0)) {
            return Lit112.execute(allocVars, TemplateScope.make());
        } else if (Lit113.match(list2, allocVars, 0)) {
            return Lit114.execute(allocVars, TemplateScope.make());
        } else {
            return syntax_case.error("syntax-case", list2);
        }
    }

    static Object lambda22(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(5, null);
        if (Lit118.match(list2, allocVars, 0)) {
            return Lit119.execute(allocVars, TemplateScope.make());
        } else if (Lit120.match(list2, allocVars, 0)) {
            return Lit121.execute(allocVars, TemplateScope.make());
        } else if (Lit122.match(list2, allocVars, 0)) {
            return Lit123.execute(allocVars, TemplateScope.make());
        } else {
            return syntax_case.error("syntax-case", list2);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 12:
                return isTestRunner(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 13:
                try {
                    return testRunnerPassCount((test$Mnrunner) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-pass-count", 1, obj);
                }
            case 14:
            case 16:
            case 18:
            case 20:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 34:
            case 36:
            case 38:
            case 40:
            case 42:
            case 44:
            case 46:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 58:
            case 59:
            case 61:
            case 62:
            case 64:
            case 67:
            case 69:
            case 70:
            case 71:
            case 72:
            case 74:
            case 84:
            case 85:
            case 86:
            case 87:
            default:
                return super.apply1(moduleMethod, obj);
            case 15:
                try {
                    return testRunnerFailCount((test$Mnrunner) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "test-runner-fail-count", 1, obj);
                }
            case 17:
                try {
                    return testRunnerXpassCount((test$Mnrunner) obj);
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "test-runner-xpass-count", 1, obj);
                }
            case 19:
                try {
                    return testRunnerXfailCount((test$Mnrunner) obj);
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "test-runner-xfail-count", 1, obj);
                }
            case 21:
                try {
                    return testRunnerSkipCount((test$Mnrunner) obj);
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "test-runner-skip-count", 1, obj);
                }
            case 23:
                try {
                    return ((test$Mnrunner) obj).skip$Mnlist;
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "%test-runner-skip-list", 1, obj);
                }
            case 25:
                try {
                    return ((test$Mnrunner) obj).fail$Mnlist;
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "%test-runner-fail-list", 1, obj);
                }
            case 27:
                try {
                    return testRunnerGroupStack((test$Mnrunner) obj);
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "test-runner-group-stack", 1, obj);
                }
            case 29:
                try {
                    return testRunnerOnTestBegin((test$Mnrunner) obj);
                } catch (ClassCastException e9) {
                    throw new WrongType(e9, "test-runner-on-test-begin", 1, obj);
                }
            case 31:
                try {
                    return testRunnerOnTestEnd((test$Mnrunner) obj);
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "test-runner-on-test-end", 1, obj);
                }
            case 33:
                try {
                    return testRunnerOnGroupBegin((test$Mnrunner) obj);
                } catch (ClassCastException e11) {
                    throw new WrongType(e11, "test-runner-on-group-begin", 1, obj);
                }
            case 35:
                try {
                    return testRunnerOnGroupEnd((test$Mnrunner) obj);
                } catch (ClassCastException e12) {
                    throw new WrongType(e12, "test-runner-on-group-end", 1, obj);
                }
            case 37:
                try {
                    return testRunnerOnFinal((test$Mnrunner) obj);
                } catch (ClassCastException e13) {
                    throw new WrongType(e13, "test-runner-on-final", 1, obj);
                }
            case 39:
                try {
                    return testRunnerOnBadCount((test$Mnrunner) obj);
                } catch (ClassCastException e14) {
                    throw new WrongType(e14, "test-runner-on-bad-count", 1, obj);
                }
            case 41:
                try {
                    return testRunnerOnBadEndName((test$Mnrunner) obj);
                } catch (ClassCastException e15) {
                    throw new WrongType(e15, "test-runner-on-bad-end-name", 1, obj);
                }
            case 43:
                try {
                    return testResultAlist((test$Mnrunner) obj);
                } catch (ClassCastException e16) {
                    throw new WrongType(e16, "test-result-alist", 1, obj);
                }
            case 45:
                try {
                    return testRunnerAuxValue((test$Mnrunner) obj);
                } catch (ClassCastException e17) {
                    throw new WrongType(e17, "test-runner-aux-value", 1, obj);
                }
            case 47:
                testRunnerReset(obj);
                return Values.empty;
            case 48:
                return testRunnerGroupPath(obj);
            case 49:
                return $PcTestNullCallback(obj);
            case 57:
                return $PcTestShouldExecute(obj);
            case 60:
                return testOnGroupEndSimple(obj);
            case 63:
                testOnFinalSimple(obj);
                return Values.empty;
            case 65:
                return testOnTestBeginSimple(obj);
            case 66:
                return testOnTestEndSimple(obj);
            case 68:
                testResultClear(obj);
                return Values.empty;
            case 73:
                return $PcTestOnTestBegin(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 75:
                return testRunnerTestName(obj);
            case 76:
                return $PcTestApproximimate$Eq(obj);
            case 77:
                return lambda16(obj);
            case 78:
                return lambda17(obj);
            case 79:
                return lambda18(obj);
            case 80:
                return lambda19(obj);
            case 81:
                return lambda20(obj);
            case 82:
                return lambda21(obj);
            case 83:
                return lambda22(obj);
            case 88:
                return $PcTestAsSpecifier(obj);
            case 89:
                return testMatchName(obj);
            case 90:
                return testReadEvalString(obj);
        }
    }

    public static Object testApply$V(Object first, Object[] argsArray) {
        frame1 frame1Var = new frame1();
        frame1Var.first = first;
        frame1Var.rest = LList.makeList(argsArray, 0);
        if (isTestRunner(frame1Var.first)) {
            frame1Var.saved$Mnrunner$1 = ((Procedure) test$Mnrunner$Mncurrent).apply0();
            return misc.dynamicWind(frame1Var.lambda$Fn5, frame1Var.lambda$Fn6, frame1Var.lambda$Fn7);
        }
        Object r = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        if (r != Boolean.FALSE) {
            try {
                Object run$Mnlist = ((test$Mnrunner) r).run$Mnlist;
                if (lists.isNull(frame1Var.rest)) {
                    try {
                        try {
                            ((test$Mnrunner) r).run$Mnlist = LList.reverseInPlace((LList) run$Mnlist);
                            return Scheme.applyToArgs.apply1(frame1Var.first);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "reverse!", 1, run$Mnlist);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%test-runner-run-list!", 0, r);
                    }
                }
                try {
                    ((test$Mnrunner) r).run$Mnlist = run$Mnlist == Boolean.TRUE ? LList.list1(frame1Var.first) : lists.cons(frame1Var.first, run$Mnlist);
                    Scheme.apply.apply2(test$Mnapply, frame1Var.rest);
                    try {
                        ((test$Mnrunner) r).run$Mnlist = run$Mnlist;
                        return Values.empty;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "%test-runner-run-list!", 0, r);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "%test-runner-run-list!", 0, r);
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "%test-runner-run-list", 0, r);
            }
        }
        frame1Var.r = testRunnerCreate();
        frame1Var.saved$Mnrunner = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        misc.dynamicWind(frame1Var.lambda$Fn8, frame1Var.lambda$Fn9, frame1Var.lambda$Fn10);
        ApplyToArgs applyToArgs = Scheme.applyToArgs;
        Object obj = frame1Var.r;
        try {
            return applyToArgs.apply2(testRunnerOnFinal((test$Mnrunner) obj), frame1Var.r);
        } catch (ClassCastException e6) {
            throw new WrongType(e6, "test-runner-on-final", 0, obj);
        }
    }

    /* compiled from: testing.scm */
    /* loaded from: classes.dex */
    public class frame1 extends ModuleBody {
        Object first;
        final ModuleMethod lambda$Fn10;
        final ModuleMethod lambda$Fn5 = new ModuleMethod(this, 2, null, 0);
        final ModuleMethod lambda$Fn6 = new ModuleMethod(this, 3, null, 0);
        final ModuleMethod lambda$Fn7;
        final ModuleMethod lambda$Fn8;
        final ModuleMethod lambda$Fn9;
        Object r;
        LList rest;
        Object saved$Mnrunner;
        Object saved$Mnrunner$1;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 4, null, 0);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:897");
            this.lambda$Fn7 = moduleMethod;
            this.lambda$Fn8 = new ModuleMethod(this, 5, null, 0);
            this.lambda$Fn9 = new ModuleMethod(this, 6, null, 0);
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 7, null, 0);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:897");
            this.lambda$Fn10 = moduleMethod2;
        }

        Object lambda6() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.first);
        }

        Object lambda7() {
            return Scheme.apply.apply2(testing.test$Mnapply, this.rest);
        }

        Object lambda10() {
            return Scheme.apply.apply3(testing.test$Mnapply, this.first, this.rest);
        }

        Object lambda9() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.r);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 2:
                    return lambda6();
                case 3:
                    return lambda7();
                case 4:
                    return lambda8();
                case 5:
                    return lambda9();
                case 6:
                    return lambda10();
                case 7:
                    return lambda11();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        Object lambda11() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.saved$Mnrunner);
        }

        Object lambda8() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.saved$Mnrunner$1);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 2:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 3:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 4:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 5:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 6:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 7:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    public static Procedure $PcTestMatchNth(Object n, Object count) {
        frame2 frame2Var = new frame2();
        frame2Var.n = n;
        frame2Var.count = count;
        frame2Var.i = Lit0;
        return frame2Var.lambda$Fn11;
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 14:
                try {
                    ((test$Mnrunner) obj).pass$Mncount = obj2;
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-pass-count!", 1, obj);
                }
            case 16:
                try {
                    ((test$Mnrunner) obj).fail$Mncount = obj2;
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "test-runner-fail-count!", 1, obj);
                }
            case 18:
                try {
                    ((test$Mnrunner) obj).xpass$Mncount = obj2;
                    return Values.empty;
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "test-runner-xpass-count!", 1, obj);
                }
            case 20:
                try {
                    ((test$Mnrunner) obj).xfail$Mncount = obj2;
                    return Values.empty;
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "test-runner-xfail-count!", 1, obj);
                }
            case 22:
                try {
                    ((test$Mnrunner) obj).skip$Mncount = obj2;
                    return Values.empty;
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "test-runner-skip-count!", 1, obj);
                }
            case 24:
                try {
                    ((test$Mnrunner) obj).skip$Mnlist = obj2;
                    return Values.empty;
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "%test-runner-skip-list!", 1, obj);
                }
            case 26:
                try {
                    ((test$Mnrunner) obj).fail$Mnlist = obj2;
                    return Values.empty;
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "%test-runner-fail-list!", 1, obj);
                }
            case 28:
                try {
                    ((test$Mnrunner) obj).group$Mnstack = obj2;
                    return Values.empty;
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "test-runner-group-stack!", 1, obj);
                }
            case 30:
                try {
                    ((test$Mnrunner) obj).on$Mntest$Mnbegin = obj2;
                    return Values.empty;
                } catch (ClassCastException e9) {
                    throw new WrongType(e9, "test-runner-on-test-begin!", 1, obj);
                }
            case 32:
                try {
                    ((test$Mnrunner) obj).on$Mntest$Mnend = obj2;
                    return Values.empty;
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "test-runner-on-test-end!", 1, obj);
                }
            case 34:
                try {
                    ((test$Mnrunner) obj).on$Mngroup$Mnbegin = obj2;
                    return Values.empty;
                } catch (ClassCastException e11) {
                    throw new WrongType(e11, "test-runner-on-group-begin!", 1, obj);
                }
            case 36:
                try {
                    ((test$Mnrunner) obj).on$Mngroup$Mnend = obj2;
                    return Values.empty;
                } catch (ClassCastException e12) {
                    throw new WrongType(e12, "test-runner-on-group-end!", 1, obj);
                }
            case 38:
                try {
                    ((test$Mnrunner) obj).on$Mnfinal = obj2;
                    return Values.empty;
                } catch (ClassCastException e13) {
                    throw new WrongType(e13, "test-runner-on-final!", 1, obj);
                }
            case 40:
                try {
                    ((test$Mnrunner) obj).on$Mnbad$Mncount = obj2;
                    return Values.empty;
                } catch (ClassCastException e14) {
                    throw new WrongType(e14, "test-runner-on-bad-count!", 1, obj);
                }
            case 42:
                try {
                    ((test$Mnrunner) obj).on$Mnbad$Mnend$Mnname = obj2;
                    return Values.empty;
                } catch (ClassCastException e15) {
                    throw new WrongType(e15, "test-runner-on-bad-end-name!", 1, obj);
                }
            case 44:
                try {
                    ((test$Mnrunner) obj).result$Mnalist = obj2;
                    return Values.empty;
                } catch (ClassCastException e16) {
                    throw new WrongType(e16, "test-result-alist!", 1, obj);
                }
            case 46:
                try {
                    ((test$Mnrunner) obj).aux$Mnvalue = obj2;
                    return Values.empty;
                } catch (ClassCastException e17) {
                    throw new WrongType(e17, "test-runner-aux-value!", 1, obj);
                }
            case 58:
                $PcTestBegin(obj, obj2);
                return Values.empty;
            case 64:
                return $PcTestEnd(obj, obj2);
            case 69:
                testResultRemove(obj, obj2);
                return Values.empty;
            case 74:
                return $PcTestOnTestEnd(obj, obj2);
            case 85:
                return $PcTestMatchNth(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    /* compiled from: testing.scm */
    /* loaded from: classes.dex */
    public class frame2 extends ModuleBody {
        Object count;
        Object i;
        final ModuleMethod lambda$Fn11;
        Object n;

        public frame2() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 8, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:903");
            this.lambda$Fn11 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 8 ? lambda12(obj) ? Boolean.TRUE : Boolean.FALSE : super.apply1(moduleMethod, obj);
        }

        boolean lambda12(Object runner) {
            this.i = AddOp.$Pl.apply2(this.i, testing.Lit13);
            Object apply2 = Scheme.numGEq.apply2(this.i, this.n);
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                return x ? ((Boolean) Scheme.numLss.apply2(this.i, AddOp.$Pl.apply2(this.n, this.count))).booleanValue() : x;
            } catch (ClassCastException e) {
                throw new WrongType(e, "x", -2, apply2);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 8) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    /* compiled from: testing.scm */
    /* loaded from: classes.dex */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn12;
        LList pred$Mnlist;

        public frame3() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 9, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:915");
            this.lambda$Fn12 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 9 ? lambda13(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda13(Object runner) {
            Boolean result = Boolean.TRUE;
            for (Object obj = this.pred$Mnlist; !lists.isNull(obj); obj = lists.cdr.apply1(obj)) {
                if (Scheme.applyToArgs.apply2(lists.car.apply1(obj), runner) == Boolean.FALSE) {
                    result = Boolean.FALSE;
                }
            }
            return result;
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 9) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Procedure $PcTestMatchAll$V(Object[] argsArray) {
        frame3 frame3Var = new frame3();
        frame3Var.pred$Mnlist = LList.makeList(argsArray, 0);
        return frame3Var.lambda$Fn12;
    }

    /* compiled from: testing.scm */
    /* loaded from: classes.dex */
    public class frame4 extends ModuleBody {
        final ModuleMethod lambda$Fn13;
        LList pred$Mnlist;

        public frame4() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 10, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:931");
            this.lambda$Fn13 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 10 ? lambda14(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda14(Object runner) {
            Boolean result = Boolean.FALSE;
            for (Object obj = this.pred$Mnlist; !lists.isNull(obj); obj = lists.cdr.apply1(obj)) {
                if (Scheme.applyToArgs.apply2(lists.car.apply1(obj), runner) != Boolean.FALSE) {
                    result = Boolean.TRUE;
                }
            }
            return result;
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 10) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Procedure $PcTestMatchAny$V(Object[] argsArray) {
        frame4 frame4Var = new frame4();
        frame4Var.pred$Mnlist = LList.makeList(argsArray, 0);
        return frame4Var.lambda$Fn13;
    }

    @Override // gnu.expr.ModuleBody
    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 70:
                return testResultKind$V(objArr);
            case 71:
                return isTestPassed$V(objArr);
            case 84:
                Object obj = objArr[0];
                int length = objArr.length - 1;
                Object[] objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return testApply$V(obj, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 86:
                return $PcTestMatchAll$V(objArr);
            case 87:
                return $PcTestMatchAny$V(objArr);
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static Object $PcTestAsSpecifier(Object specifier) {
        if (!misc.isProcedure(specifier)) {
            if (numbers.isInteger(specifier)) {
                return $PcTestMatchNth(Lit13, specifier);
            }
            if (strings.isString(specifier)) {
                return testMatchName(specifier);
            }
            return misc.error$V("not a valid test specifier", new Object[0]);
        }
        return specifier;
    }

    /* compiled from: testing.scm */
    /* loaded from: classes.dex */
    public class frame5 extends ModuleBody {
        final ModuleMethod lambda$Fn14;
        Object name;

        public frame5() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/testing.scm:971");
            this.lambda$Fn14 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 11 ? lambda15(obj) ? Boolean.TRUE : Boolean.FALSE : super.apply1(moduleMethod, obj);
        }

        boolean lambda15(Object runner) {
            return IsEqual.apply(this.name, testing.testRunnerTestName(runner));
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 11) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Procedure testMatchName(Object name) {
        frame5 frame5Var = new frame5();
        frame5Var.name = name;
        return frame5Var.lambda$Fn14;
    }

    public static Object testReadEvalString(Object string) {
        try {
            InPort port = ports.openInputString((CharSequence) string);
            Object form = ports.read(port);
            return ports.isEofObject(readchar.readChar.apply1(port)) ? Eval.eval.apply1(form) : misc.error$V("(not at eof)", new Object[0]);
        } catch (ClassCastException e) {
            throw new WrongType(e, "open-input-string", 1, string);
        }
    }
}
