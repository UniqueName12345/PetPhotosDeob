package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.ports;
import kawa.lib.rnrs.unicode;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: pregexp.scm */
/* loaded from: classes.dex */
public class pregexp extends ModuleBody {
    public static Char $Stpregexp$Mncomment$Mnchar$St;
    public static Object $Stpregexp$Mnnul$Mnchar$Mnint$St;
    public static Object $Stpregexp$Mnreturn$Mnchar$St;
    public static Object $Stpregexp$Mnspace$Mnsensitive$Qu$St;
    public static Object $Stpregexp$Mntab$Mnchar$St;
    public static IntNum $Stpregexp$Mnversion$St;
    public static final pregexp $instance;
    static final IntNum Lit0;
    static final Char Lit1;
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit100;
    static final SimpleSymbol Lit101;
    static final SimpleSymbol Lit102;
    static final SimpleSymbol Lit103;
    static final SimpleSymbol Lit104;
    static final SimpleSymbol Lit105;
    static final PairWithPosition Lit106;
    static final SimpleSymbol Lit107;
    static final PairWithPosition Lit108;
    static final SimpleSymbol Lit109;
    static final Char Lit11;
    static final SimpleSymbol Lit110;
    static final SimpleSymbol Lit111;
    static final SimpleSymbol Lit112;
    static final Char Lit113;
    static final SimpleSymbol Lit114;
    static final SimpleSymbol Lit115;
    static final PairWithPosition Lit116;
    static final SimpleSymbol Lit12;
    static final Char Lit13;
    static final SimpleSymbol Lit14;
    static final Char Lit15;
    static final IntNum Lit16;
    static final SimpleSymbol Lit17;
    static final Char Lit18;
    static final Char Lit19;
    static final Char Lit2;
    static final SimpleSymbol Lit20;
    static final SimpleSymbol Lit21;
    static final SimpleSymbol Lit22;
    static final SimpleSymbol Lit23;
    static final Char Lit24;
    static final Char Lit25;
    static final SimpleSymbol Lit26;
    static final Char Lit27;
    static final SimpleSymbol Lit28;
    static final Char Lit29;
    static final Char Lit3;
    static final SimpleSymbol Lit30;
    static final Char Lit31;
    static final PairWithPosition Lit32;
    static final Char Lit33;
    static final Char Lit34;
    static final Char Lit35;
    static final SimpleSymbol Lit36;
    static final Char Lit37;
    static final PairWithPosition Lit38;
    static final Char Lit39;
    static final SimpleSymbol Lit4;
    static final Char Lit40;
    static final SimpleSymbol Lit41;
    static final Char Lit42;
    static final PairWithPosition Lit43;
    static final Char Lit44;
    static final SimpleSymbol Lit45;
    static final Char Lit46;
    static final Char Lit47;
    static final Char Lit48;
    static final PairWithPosition Lit49;
    static final SimpleSymbol Lit5;
    static final Char Lit50;
    static final PairWithPosition Lit51;
    static final Char Lit52;
    static final PairWithPosition Lit53;
    static final Char Lit54;
    static final PairWithPosition Lit55;
    static final PairWithPosition Lit56;
    static final SimpleSymbol Lit57;
    static final Char Lit58;
    static final Char Lit59;
    static final Char Lit6;
    static final SimpleSymbol Lit60;
    static final SimpleSymbol Lit61;
    static final Char Lit62;
    static final PairWithPosition Lit63;
    static final SimpleSymbol Lit64;
    static final Char Lit65;
    static final Char Lit66;
    static final Char Lit67;
    static final SimpleSymbol Lit68;
    static final SimpleSymbol Lit69;
    static final Char Lit7;
    static final SimpleSymbol Lit70;
    static final SimpleSymbol Lit71;
    static final SimpleSymbol Lit72;
    static final IntNum Lit73;
    static final SimpleSymbol Lit74;
    static final SimpleSymbol Lit75;
    static final SimpleSymbol Lit76;
    static final Char Lit77;
    static final Char Lit78;
    static final SimpleSymbol Lit79;
    static final IntNum Lit8;
    static final SimpleSymbol Lit80;
    static final SimpleSymbol Lit81;
    static final SimpleSymbol Lit82;
    static final SimpleSymbol Lit83;
    static final Char Lit84;
    static final SimpleSymbol Lit85;
    static final SimpleSymbol Lit86;
    static final SimpleSymbol Lit87;
    static final SimpleSymbol Lit88;
    static final SimpleSymbol Lit89;
    static final Char Lit9;
    static final SimpleSymbol Lit90;
    static final SimpleSymbol Lit91;
    static final SimpleSymbol Lit92;
    static final SimpleSymbol Lit93;
    static final SimpleSymbol Lit94;
    static final SimpleSymbol Lit95;
    static final Char Lit96;
    static final Char Lit97;
    static final Char Lit98;
    static final SimpleSymbol Lit99;
    static final ModuleMethod lambda$Fn1;
    static final ModuleMethod lambda$Fn10;
    static final ModuleMethod lambda$Fn6;
    static final ModuleMethod lambda$Fn7;
    static final ModuleMethod lambda$Fn8;
    static final ModuleMethod lambda$Fn9;
    public static final ModuleMethod pregexp;
    public static final ModuleMethod pregexp$Mnat$Mnword$Mnboundary$Qu;
    public static final ModuleMethod pregexp$Mnchar$Mnword$Qu;
    public static final ModuleMethod pregexp$Mncheck$Mnif$Mnin$Mnchar$Mnclass$Qu;
    public static final ModuleMethod pregexp$Mnerror;
    public static final ModuleMethod pregexp$Mninvert$Mnchar$Mnlist;
    public static final ModuleMethod pregexp$Mnlist$Mnref;
    public static final ModuleMethod pregexp$Mnmake$Mnbackref$Mnlist;
    public static final ModuleMethod pregexp$Mnmatch;
    public static final ModuleMethod pregexp$Mnmatch$Mnpositions;
    public static final ModuleMethod pregexp$Mnmatch$Mnpositions$Mnaux;
    public static final ModuleMethod pregexp$Mnquote;
    public static final ModuleMethod pregexp$Mnread$Mnbranch;
    public static final ModuleMethod pregexp$Mnread$Mnchar$Mnlist;
    public static final ModuleMethod pregexp$Mnread$Mncluster$Mntype;
    public static final ModuleMethod pregexp$Mnread$Mnescaped$Mnchar;
    public static final ModuleMethod pregexp$Mnread$Mnescaped$Mnnumber;
    public static final ModuleMethod pregexp$Mnread$Mnnums;
    public static final ModuleMethod pregexp$Mnread$Mnpattern;
    public static final ModuleMethod pregexp$Mnread$Mnpiece;
    public static final ModuleMethod pregexp$Mnread$Mnposix$Mnchar$Mnclass;
    public static final ModuleMethod pregexp$Mnread$Mnsubpattern;
    public static final ModuleMethod pregexp$Mnreplace;
    public static final ModuleMethod pregexp$Mnreplace$Mnaux;
    public static final ModuleMethod pregexp$Mnreplace$St;
    public static final ModuleMethod pregexp$Mnreverse$Ex;
    public static final ModuleMethod pregexp$Mnsplit;
    public static final ModuleMethod pregexp$Mnstring$Mnmatch;
    public static final ModuleMethod pregexp$Mnwrap$Mnquantifier$Mnif$Mnany;
    static final SimpleSymbol Lit135 = (SimpleSymbol) new SimpleSymbol("pregexp-quote").readResolve();
    static final SimpleSymbol Lit134 = (SimpleSymbol) new SimpleSymbol("pregexp-replace*").readResolve();
    static final SimpleSymbol Lit133 = (SimpleSymbol) new SimpleSymbol("pregexp-replace").readResolve();
    static final SimpleSymbol Lit132 = (SimpleSymbol) new SimpleSymbol("pregexp-split").readResolve();
    static final SimpleSymbol Lit131 = (SimpleSymbol) new SimpleSymbol("pregexp-match").readResolve();
    static final SimpleSymbol Lit130 = (SimpleSymbol) new SimpleSymbol("pregexp").readResolve();
    static final SimpleSymbol Lit129 = (SimpleSymbol) new SimpleSymbol("pregexp-replace-aux").readResolve();
    static final SimpleSymbol Lit128 = (SimpleSymbol) new SimpleSymbol("pregexp-make-backref-list").readResolve();
    static final SimpleSymbol Lit127 = (SimpleSymbol) new SimpleSymbol("pregexp-list-ref").readResolve();
    static final SimpleSymbol Lit126 = (SimpleSymbol) new SimpleSymbol("pregexp-at-word-boundary?").readResolve();
    static final SimpleSymbol Lit125 = (SimpleSymbol) new SimpleSymbol("pregexp-char-word?").readResolve();
    static final SimpleSymbol Lit124 = (SimpleSymbol) new SimpleSymbol("pregexp-string-match").readResolve();
    static final SimpleSymbol Lit123 = (SimpleSymbol) new SimpleSymbol("pregexp-invert-char-list").readResolve();
    static final SimpleSymbol Lit122 = (SimpleSymbol) new SimpleSymbol("pregexp-read-escaped-char").readResolve();
    static final SimpleSymbol Lit121 = (SimpleSymbol) new SimpleSymbol("pregexp-read-escaped-number").readResolve();
    static final SimpleSymbol Lit120 = (SimpleSymbol) new SimpleSymbol("pregexp-read-branch").readResolve();
    static final SimpleSymbol Lit119 = (SimpleSymbol) new SimpleSymbol("pregexp-read-pattern").readResolve();
    static final SimpleSymbol Lit118 = (SimpleSymbol) new SimpleSymbol("pregexp-error").readResolve();
    static final SimpleSymbol Lit117 = (SimpleSymbol) new SimpleSymbol("pregexp-reverse!").readResolve();

    static {
        Char make = Char.make(92);
        Lit19 = make;
        Char make2 = Char.make(46);
        Lit13 = make2;
        Char make3 = Char.make(63);
        Lit47 = make3;
        Char make4 = Char.make(42);
        Lit65 = make4;
        Char make5 = Char.make(43);
        Lit66 = make5;
        Char make6 = Char.make(124);
        Lit7 = make6;
        Char make7 = Char.make(94);
        Lit9 = make7;
        Char make8 = Char.make(36);
        Lit11 = make8;
        Char make9 = Char.make(91);
        Lit15 = make9;
        Char make10 = Char.make(93);
        Lit46 = make10;
        Char make11 = Char.make(123);
        Lit67 = make11;
        Char make12 = Char.make(125);
        Lit78 = make12;
        Char make13 = Char.make(40);
        Lit18 = make13;
        Char make14 = Char.make(41);
        Lit6 = make14;
        Lit116 = PairWithPosition.make(make, PairWithPosition.make(make2, PairWithPosition.make(make3, PairWithPosition.make(make4, PairWithPosition.make(make5, PairWithPosition.make(make6, PairWithPosition.make(make7, PairWithPosition.make(make8, PairWithPosition.make(make9, PairWithPosition.make(make10, PairWithPosition.make(make11, PairWithPosition.make(make12, PairWithPosition.make(make13, PairWithPosition.make(make14, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153977), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153973), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153969), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153965), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153961), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153957), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149885), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149881), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149877), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149873), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149869), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149865), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149861), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149856);
        Lit115 = (SimpleSymbol) new SimpleSymbol("pattern-must-be-compiled-or-string-regexp").readResolve();
        Lit114 = (SimpleSymbol) new SimpleSymbol("pregexp-match-positions").readResolve();
        Lit113 = Char.make(38);
        Lit112 = (SimpleSymbol) new SimpleSymbol("identity").readResolve();
        Lit111 = (SimpleSymbol) new SimpleSymbol("fk").readResolve();
        Lit110 = (SimpleSymbol) new SimpleSymbol("greedy-quantifier-operand-could-be-empty").readResolve();
        Lit109 = (SimpleSymbol) new SimpleSymbol(":no-backtrack").readResolve();
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(":between").readResolve();
        Lit68 = simpleSymbol;
        Boolean bool = Boolean.FALSE;
        IntNum make15 = IntNum.make(0);
        Lit73 = make15;
        Boolean bool2 = Boolean.FALSE;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol(":any").readResolve();
        Lit14 = simpleSymbol2;
        Lit108 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(bool, PairWithPosition.make(make15, PairWithPosition.make(bool2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338881), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338878), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338876), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338873), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338863);
        Lit107 = (SimpleSymbol) new SimpleSymbol(":neg-lookbehind").readResolve();
        Lit106 = PairWithPosition.make(Lit68, PairWithPosition.make(Boolean.FALSE, PairWithPosition.make(Lit73, PairWithPosition.make(Boolean.FALSE, PairWithPosition.make(Lit14, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302017), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302014), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302012), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302009), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2301999);
        Lit105 = (SimpleSymbol) new SimpleSymbol(":lookbehind").readResolve();
        Lit104 = (SimpleSymbol) new SimpleSymbol(":neg-lookahead").readResolve();
        Lit103 = (SimpleSymbol) new SimpleSymbol(":lookahead").readResolve();
        Lit102 = (SimpleSymbol) new SimpleSymbol("non-existent-backref").readResolve();
        Lit101 = (SimpleSymbol) new SimpleSymbol("pregexp-match-positions-aux").readResolve();
        Lit100 = (SimpleSymbol) new SimpleSymbol(":sub").readResolve();
        Lit99 = (SimpleSymbol) new SimpleSymbol("pregexp-check-if-in-char-class?").readResolve();
        Lit98 = Char.make(102);
        Lit97 = Char.make(101);
        Lit96 = Char.make(99);
        Lit95 = (SimpleSymbol) new SimpleSymbol(":xdigit").readResolve();
        Lit94 = (SimpleSymbol) new SimpleSymbol(":upper").readResolve();
        Lit93 = (SimpleSymbol) new SimpleSymbol(":punct").readResolve();
        Lit92 = (SimpleSymbol) new SimpleSymbol(":print").readResolve();
        Lit91 = (SimpleSymbol) new SimpleSymbol(":lower").readResolve();
        Lit90 = (SimpleSymbol) new SimpleSymbol(":graph").readResolve();
        Lit89 = (SimpleSymbol) new SimpleSymbol(":cntrl").readResolve();
        Lit88 = (SimpleSymbol) new SimpleSymbol(":blank").readResolve();
        Lit87 = (SimpleSymbol) new SimpleSymbol(":ascii").readResolve();
        Lit86 = (SimpleSymbol) new SimpleSymbol(":alpha").readResolve();
        Lit85 = (SimpleSymbol) new SimpleSymbol(":alnum").readResolve();
        Lit84 = Char.make(95);
        Lit83 = (SimpleSymbol) new SimpleSymbol(":char-range").readResolve();
        Lit82 = (SimpleSymbol) new SimpleSymbol(":one-of-chars").readResolve();
        Lit81 = (SimpleSymbol) new SimpleSymbol("character-class-ended-too-soon").readResolve();
        Lit80 = (SimpleSymbol) new SimpleSymbol("pregexp-read-char-list").readResolve();
        Lit79 = (SimpleSymbol) new SimpleSymbol(":none-of-chars").readResolve();
        Lit77 = Char.make(44);
        Lit76 = (SimpleSymbol) new SimpleSymbol("pregexp-read-nums").readResolve();
        Lit75 = (SimpleSymbol) new SimpleSymbol("left-brace-must-be-followed-by-number").readResolve();
        Lit74 = (SimpleSymbol) new SimpleSymbol("pregexp-wrap-quantifier-if-any").readResolve();
        Lit72 = (SimpleSymbol) new SimpleSymbol("next-i").readResolve();
        Lit71 = (SimpleSymbol) new SimpleSymbol("at-most").readResolve();
        Lit70 = (SimpleSymbol) new SimpleSymbol("at-least").readResolve();
        Lit69 = (SimpleSymbol) new SimpleSymbol("minimal?").readResolve();
        Lit64 = (SimpleSymbol) new SimpleSymbol("pregexp-read-subpattern").readResolve();
        Lit63 = PairWithPosition.make(Lit100, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 942102);
        Lit62 = Char.make(120);
        Lit61 = (SimpleSymbol) new SimpleSymbol(":case-insensitive").readResolve();
        Lit60 = (SimpleSymbol) new SimpleSymbol(":case-sensitive").readResolve();
        Lit59 = Char.make(105);
        Lit58 = Char.make(45);
        Lit57 = (SimpleSymbol) new SimpleSymbol("pregexp-read-cluster-type").readResolve();
        Lit56 = PairWithPosition.make(Lit107, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 876575);
        Lit55 = PairWithPosition.make(Lit105, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 872479);
        Lit54 = Char.make(60);
        Lit53 = PairWithPosition.make(Lit109, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 860188);
        Lit52 = Char.make(62);
        Lit51 = PairWithPosition.make(Lit104, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 856092);
        Lit50 = Char.make(33);
        Lit49 = PairWithPosition.make(Lit103, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 851996);
        Lit48 = Char.make(61);
        Lit45 = (SimpleSymbol) new SimpleSymbol("pregexp-read-posix-char-class").readResolve();
        Lit44 = Char.make(58);
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol(":neg-char").readResolve();
        Lit17 = simpleSymbol3;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol(":word").readResolve();
        Lit41 = simpleSymbol4;
        Lit43 = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(simpleSymbol4, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 696359), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 696348);
        Lit42 = Char.make(87);
        Lit40 = Char.make(119);
        Lit39 = Char.make(116);
        SimpleSymbol simpleSymbol5 = Lit17;
        SimpleSymbol simpleSymbol6 = (SimpleSymbol) new SimpleSymbol(":space").readResolve();
        Lit36 = simpleSymbol6;
        Lit38 = PairWithPosition.make(simpleSymbol5, PairWithPosition.make(simpleSymbol6, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 684071), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 684060);
        Lit37 = Char.make(83);
        Lit35 = Char.make(115);
        Lit34 = Char.make(114);
        Lit33 = Char.make(110);
        SimpleSymbol simpleSymbol7 = Lit17;
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol(":digit").readResolve();
        Lit30 = simpleSymbol8;
        Lit32 = PairWithPosition.make(simpleSymbol7, PairWithPosition.make(simpleSymbol8, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 667687), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm", 667676);
        Lit31 = Char.make(68);
        Lit29 = Char.make(100);
        Lit28 = (SimpleSymbol) new SimpleSymbol(":not-wbdry").readResolve();
        Lit27 = Char.make(66);
        Lit26 = (SimpleSymbol) new SimpleSymbol(":wbdry").readResolve();
        Lit25 = Char.make(98);
        Lit24 = Char.make(10);
        Lit23 = (SimpleSymbol) new SimpleSymbol(":empty").readResolve();
        Lit22 = (SimpleSymbol) new SimpleSymbol("backslash").readResolve();
        Lit21 = (SimpleSymbol) new SimpleSymbol("pregexp-read-piece").readResolve();
        Lit20 = (SimpleSymbol) new SimpleSymbol(":backref").readResolve();
        Lit16 = IntNum.make(2);
        Lit12 = (SimpleSymbol) new SimpleSymbol(":eos").readResolve();
        Lit10 = (SimpleSymbol) new SimpleSymbol(":bos").readResolve();
        Lit8 = IntNum.make(1);
        Lit5 = (SimpleSymbol) new SimpleSymbol(":seq").readResolve();
        Lit4 = (SimpleSymbol) new SimpleSymbol(":or").readResolve();
        Lit3 = Char.make(32);
        Lit2 = Char.make(97);
        Lit1 = Char.make(59);
        Lit0 = IntNum.make(20050502);
        $instance = new pregexp();
        pregexp pregexpVar = $instance;
        ModuleMethod moduleMethod = new ModuleMethod(pregexpVar, 16, Lit117, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:47");
        pregexp$Mnreverse$Ex = moduleMethod;
        ModuleMethod moduleMethod2 = new ModuleMethod(pregexpVar, 17, Lit118, -4096);
        moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:57");
        pregexp$Mnerror = moduleMethod2;
        ModuleMethod moduleMethod3 = new ModuleMethod(pregexpVar, 18, Lit119, 12291);
        moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:65");
        pregexp$Mnread$Mnpattern = moduleMethod3;
        ModuleMethod moduleMethod4 = new ModuleMethod(pregexpVar, 19, Lit120, 12291);
        moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:79");
        pregexp$Mnread$Mnbranch = moduleMethod4;
        ModuleMethod moduleMethod5 = new ModuleMethod(pregexpVar, 20, Lit21, 12291);
        moduleMethod5.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:91");
        pregexp$Mnread$Mnpiece = moduleMethod5;
        ModuleMethod moduleMethod6 = new ModuleMethod(pregexpVar, 21, Lit121, 12291);
        moduleMethod6.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:138");
        pregexp$Mnread$Mnescaped$Mnnumber = moduleMethod6;
        ModuleMethod moduleMethod7 = new ModuleMethod(pregexpVar, 22, Lit122, 12291);
        moduleMethod7.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:155");
        pregexp$Mnread$Mnescaped$Mnchar = moduleMethod7;
        ModuleMethod moduleMethod8 = new ModuleMethod(pregexpVar, 23, Lit45, 12291);
        moduleMethod8.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:174");
        pregexp$Mnread$Mnposix$Mnchar$Mnclass = moduleMethod8;
        ModuleMethod moduleMethod9 = new ModuleMethod(pregexpVar, 24, Lit57, 12291);
        moduleMethod9.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:200");
        pregexp$Mnread$Mncluster$Mntype = moduleMethod9;
        ModuleMethod moduleMethod10 = new ModuleMethod(pregexpVar, 25, Lit64, 12291);
        moduleMethod10.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:233");
        pregexp$Mnread$Mnsubpattern = moduleMethod10;
        ModuleMethod moduleMethod11 = new ModuleMethod(pregexpVar, 26, Lit74, 12291);
        moduleMethod11.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:254");
        pregexp$Mnwrap$Mnquantifier$Mnif$Mnany = moduleMethod11;
        ModuleMethod moduleMethod12 = new ModuleMethod(pregexpVar, 27, Lit76, 12291);
        moduleMethod12.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:300");
        pregexp$Mnread$Mnnums = moduleMethod12;
        ModuleMethod moduleMethod13 = new ModuleMethod(pregexpVar, 28, Lit123, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod13.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:323");
        pregexp$Mninvert$Mnchar$Mnlist = moduleMethod13;
        ModuleMethod moduleMethod14 = new ModuleMethod(pregexpVar, 29, Lit80, 12291);
        moduleMethod14.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:330");
        pregexp$Mnread$Mnchar$Mnlist = moduleMethod14;
        ModuleMethod moduleMethod15 = new ModuleMethod(pregexpVar, 30, Lit124, 24582);
        moduleMethod15.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:368");
        pregexp$Mnstring$Mnmatch = moduleMethod15;
        ModuleMethod moduleMethod16 = new ModuleMethod(pregexpVar, 31, Lit125, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod16.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:379");
        pregexp$Mnchar$Mnword$Qu = moduleMethod16;
        ModuleMethod moduleMethod17 = new ModuleMethod(pregexpVar, 32, Lit126, 12291);
        moduleMethod17.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:387");
        pregexp$Mnat$Mnword$Mnboundary$Qu = moduleMethod17;
        ModuleMethod moduleMethod18 = new ModuleMethod(pregexpVar, 33, Lit99, 8194);
        moduleMethod18.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:399");
        pregexp$Mncheck$Mnif$Mnin$Mnchar$Mnclass$Qu = moduleMethod18;
        ModuleMethod moduleMethod19 = new ModuleMethod(pregexpVar, 34, Lit127, 8194);
        moduleMethod19.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:429");
        pregexp$Mnlist$Mnref = moduleMethod19;
        ModuleMethod moduleMethod20 = new ModuleMethod(pregexpVar, 35, Lit128, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod20.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:448");
        pregexp$Mnmake$Mnbackref$Mnlist = moduleMethod20;
        ModuleMethod moduleMethod21 = new ModuleMethod(pregexpVar, 36, null, 0);
        moduleMethod21.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:463");
        lambda$Fn1 = moduleMethod21;
        ModuleMethod moduleMethod22 = new ModuleMethod(pregexpVar, 37, null, 0);
        moduleMethod22.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:551");
        lambda$Fn6 = moduleMethod22;
        ModuleMethod moduleMethod23 = new ModuleMethod(pregexpVar, 38, null, 0);
        moduleMethod23.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:556");
        lambda$Fn7 = moduleMethod23;
        ModuleMethod moduleMethod24 = new ModuleMethod(pregexpVar, 39, null, 0);
        moduleMethod24.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:564");
        lambda$Fn8 = moduleMethod24;
        ModuleMethod moduleMethod25 = new ModuleMethod(pregexpVar, 40, null, 0);
        moduleMethod25.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:573");
        lambda$Fn9 = moduleMethod25;
        ModuleMethod moduleMethod26 = new ModuleMethod(pregexpVar, 41, null, 0);
        moduleMethod26.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:578");
        lambda$Fn10 = moduleMethod26;
        ModuleMethod moduleMethod27 = new ModuleMethod(pregexpVar, 42, Lit101, 24582);
        moduleMethod27.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:459");
        pregexp$Mnmatch$Mnpositions$Mnaux = moduleMethod27;
        ModuleMethod moduleMethod28 = new ModuleMethod(pregexpVar, 43, Lit129, 16388);
        moduleMethod28.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:639");
        pregexp$Mnreplace$Mnaux = moduleMethod28;
        ModuleMethod moduleMethod29 = new ModuleMethod(pregexpVar, 44, Lit130, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod29.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:665");
        pregexp = moduleMethod29;
        ModuleMethod moduleMethod30 = new ModuleMethod(pregexpVar, 45, Lit114, -4094);
        moduleMethod30.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:670");
        pregexp$Mnmatch$Mnpositions = moduleMethod30;
        ModuleMethod moduleMethod31 = new ModuleMethod(pregexpVar, 46, Lit131, -4094);
        moduleMethod31.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:690");
        pregexp$Mnmatch = moduleMethod31;
        ModuleMethod moduleMethod32 = new ModuleMethod(pregexpVar, 47, Lit132, 8194);
        moduleMethod32.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:700");
        pregexp$Mnsplit = moduleMethod32;
        ModuleMethod moduleMethod33 = new ModuleMethod(pregexpVar, 48, Lit133, 12291);
        moduleMethod33.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:723");
        pregexp$Mnreplace = moduleMethod33;
        ModuleMethod moduleMethod34 = new ModuleMethod(pregexpVar, 49, Lit134, 12291);
        moduleMethod34.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:736");
        pregexp$Mnreplace$St = moduleMethod34;
        ModuleMethod moduleMethod35 = new ModuleMethod(pregexpVar, 50, Lit135, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod35.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:764");
        pregexp$Mnquote = moduleMethod35;
        $instance.run();
    }

    public pregexp() {
        ModuleInfo.register(this);
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
        $Stpregexp$Mnversion$St = Lit0;
        $Stpregexp$Mncomment$Mnchar$St = Lit1;
        $Stpregexp$Mnnul$Mnchar$Mnint$St = Integer.valueOf(Lit2.intValue() - 97);
        $Stpregexp$Mnreturn$Mnchar$St = Char.make(((Number) $Stpregexp$Mnnul$Mnchar$Mnint$St).intValue() + 13);
        $Stpregexp$Mntab$Mnchar$St = Char.make(((Number) $Stpregexp$Mnnul$Mnchar$Mnint$St).intValue() + 9);
        $Stpregexp$Mnspace$Mnsensitive$Qu$St = Boolean.TRUE;
    }

    public static Object pregexpReverse$Ex(Object s) {
        Object obj = LList.Empty;
        while (!lists.isNull(s)) {
            Object d = lists.cdr.apply1(s);
            try {
                ((Pair) s).setCdr(obj);
                obj = s;
                s = d;
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, s);
            }
        }
        return obj;
    }

    @Override // gnu.expr.ModuleBody
    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 16:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 31:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 35:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 44:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 50:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object pregexpError$V(Object[] argsArray) {
        LList whatever = LList.makeList(argsArray, 0);
        ports.display("Error:");
        Object obj = whatever;
        while (obj != LList.Empty) {
            try {
                Pair arg0 = (Pair) obj;
                Object x = arg0.getCar();
                ports.display(Lit3);
                ports.write(x);
                obj = arg0.getCdr();
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, obj);
            }
        }
        ports.newline();
        return misc.error$V("pregexp-error", new Object[0]);
    }

    @Override // gnu.expr.ModuleBody
    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 17:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 30:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 42:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 45:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 46:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0080 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object pregexpReadPattern(java.lang.Object r9, java.lang.Object r10, java.lang.Object r11) {
        /*
            r8 = 2
            r7 = 1
            gnu.kawa.functions.NumberCompare r4 = kawa.standard.Scheme.numGEq
            java.lang.Object r4 = r4.apply2(r10, r11)
            java.lang.Boolean r5 = java.lang.Boolean.FALSE
            if (r4 == r5) goto L1d
            gnu.mapping.SimpleSymbol r4 = gnu.kawa.slib.pregexp.Lit4
            gnu.mapping.SimpleSymbol r5 = gnu.kawa.slib.pregexp.Lit5
            gnu.lists.Pair r5 = gnu.lists.LList.list1(r5)
            gnu.lists.Pair r4 = gnu.lists.LList.list2(r4, r5)
            gnu.lists.Pair r4 = gnu.lists.LList.list2(r4, r10)
        L1c:
            return r4
        L1d:
            gnu.lists.LList r1 = gnu.lists.LList.Empty
        L1f:
            gnu.kawa.functions.NumberCompare r4 = kawa.standard.Scheme.numGEq
            java.lang.Object r5 = r4.apply2(r10, r11)
            r0 = r5
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch: java.lang.ClassCastException -> L95
            r4 = r0
            boolean r3 = r4.booleanValue()     // Catch: java.lang.ClassCastException -> L95
            if (r3 == 0) goto L40
            if (r3 == 0) goto L5c
        L31:
            gnu.mapping.SimpleSymbol r4 = gnu.kawa.slib.pregexp.Lit4
            java.lang.Object r5 = pregexpReverse$Ex(r1)
            gnu.lists.Pair r4 = kawa.lib.lists.cons(r4, r5)
            gnu.lists.Pair r4 = gnu.lists.LList.list2(r4, r10)
            goto L1c
        L40:
            r0 = r9
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0     // Catch: java.lang.ClassCastException -> L9f
            r4 = r0
            r0 = r10
            java.lang.Number r0 = (java.lang.Number) r0     // Catch: java.lang.ClassCastException -> La8
            r5 = r0
            int r5 = r5.intValue()     // Catch: java.lang.ClassCastException -> La8
            char r4 = kawa.lib.strings.stringRef(r4, r5)
            gnu.text.Char r4 = gnu.text.Char.make(r4)
            gnu.text.Char r5 = gnu.kawa.slib.pregexp.Lit6
            boolean r4 = kawa.lib.characters.isChar$Eq(r4, r5)
            if (r4 != 0) goto L31
        L5c:
            r0 = r9
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0     // Catch: java.lang.ClassCastException -> Lb1
            r4 = r0
            r0 = r10
            java.lang.Number r0 = (java.lang.Number) r0     // Catch: java.lang.ClassCastException -> Lba
            r5 = r0
            int r5 = r5.intValue()     // Catch: java.lang.ClassCastException -> Lba
            char r4 = kawa.lib.strings.stringRef(r4, r5)
            gnu.text.Char r4 = gnu.text.Char.make(r4)
            gnu.text.Char r5 = gnu.kawa.slib.pregexp.Lit7
            boolean r4 = kawa.lib.characters.isChar$Eq(r4, r5)
            if (r4 == 0) goto L80
            gnu.kawa.functions.AddOp r4 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r5 = gnu.kawa.slib.pregexp.Lit8
            java.lang.Object r10 = r4.apply2(r10, r5)
        L80:
            java.lang.Object r2 = pregexpReadBranch(r9, r10, r11)
            gnu.expr.GenericProc r4 = kawa.lib.lists.car
            java.lang.Object r4 = r4.apply1(r2)
            gnu.lists.Pair r1 = kawa.lib.lists.cons(r4, r1)
            gnu.expr.GenericProc r4 = kawa.lib.lists.cadr
            java.lang.Object r10 = r4.apply1(r2)
            goto L1f
        L95:
            r4 = move-exception
            gnu.mapping.WrongType r6 = new gnu.mapping.WrongType
            java.lang.String r7 = "x"
            r8 = -2
            r6.<init>(r4, r7, r8, r5)
            throw r6
        L9f:
            r4 = move-exception
            gnu.mapping.WrongType r5 = new gnu.mapping.WrongType
            java.lang.String r6 = "string-ref"
            r5.<init>(r4, r6, r7, r9)
            throw r5
        La8:
            r4 = move-exception
            gnu.mapping.WrongType r5 = new gnu.mapping.WrongType
            java.lang.String r6 = "string-ref"
            r5.<init>(r4, r6, r8, r10)
            throw r5
        Lb1:
            r4 = move-exception
            gnu.mapping.WrongType r5 = new gnu.mapping.WrongType
            java.lang.String r6 = "string-ref"
            r5.<init>(r4, r6, r7, r9)
            throw r5
        Lba:
            r4 = move-exception
            gnu.mapping.WrongType r5 = new gnu.mapping.WrongType
            java.lang.String r6 = "string-ref"
            r5.<init>(r4, r6, r8, r10)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadPattern(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    @Override // gnu.expr.ModuleBody
    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 18:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 19:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 20:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 21:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 22:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 23:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 24:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 25:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 26:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 27:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 28:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            case 29:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 32:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 48:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 49:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
        }
    }

    public static Object pregexpReadBranch(Object s, Object i, Object n) {
        Object obj = LList.Empty;
        while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
            try {
                try {
                    char c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                    boolean x = characters.isChar$Eq(Char.make(c), Lit7);
                    if (x) {
                        if (x) {
                            return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(obj)), i);
                        }
                        Object vv = pregexpReadPiece(s, i, n);
                        obj = lists.cons(lists.car.apply1(vv), obj);
                        i = lists.cadr.apply1(vv);
                    } else if (characters.isChar$Eq(Char.make(c), Lit6)) {
                        return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(obj)), i);
                    } else {
                        Object vv2 = pregexpReadPiece(s, i, n);
                        obj = lists.cons(lists.car.apply1(vv2), obj);
                        i = lists.cadr.apply1(vv2);
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, i);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, s);
            }
        }
        return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(obj)), i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:128:?, code lost:
        return pregexpWrapQuantifierIfAny(gnu.lists.LList.list2(gnu.text.Char.make(r1), gnu.kawa.functions.AddOp.$Pl.apply2(r14, gnu.kawa.slib.pregexp.Lit8)), r13, r15);
     */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01c0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object pregexpReadPiece(java.lang.Object r13, java.lang.Object r14, java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 610
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadPiece(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpReadEscapedNumber(Object s, Object i, Object n) {
        Object apply2 = Scheme.numLss.apply2(AddOp.$Pl.apply2(i, Lit8), n);
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            }
            try {
                CharSequence charSequence = (CharSequence) s;
                Object apply22 = AddOp.$Pl.apply2(i, Lit8);
                try {
                    char c = strings.stringRef(charSequence, ((Number) apply22).intValue());
                    boolean x2 = unicode.isCharNumeric(Char.make(c));
                    if (!x2) {
                        return x2 ? Boolean.TRUE : Boolean.FALSE;
                    }
                    Object i2 = AddOp.$Pl.apply2(i, Lit16);
                    Pair r = LList.list1(Char.make(c));
                    while (Scheme.numGEq.apply2(i2, n) == Boolean.FALSE) {
                        try {
                            try {
                                char c2 = strings.stringRef((CharSequence) s, ((Number) i2).intValue());
                                if (unicode.isCharNumeric(Char.make(c2))) {
                                    i2 = AddOp.$Pl.apply2(i2, Lit8);
                                    r = lists.cons(Char.make(c2), r);
                                } else {
                                    Object pregexpReverse$Ex = pregexpReverse$Ex(r);
                                    try {
                                        return LList.list2(numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex), 10), i2);
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "list->string", 1, pregexpReverse$Ex);
                                    }
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 2, i2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 1, s);
                        }
                    }
                    Object pregexpReverse$Ex2 = pregexpReverse$Ex(r);
                    try {
                        return LList.list2(numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex2), 10), i2);
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "list->string", 1, pregexpReverse$Ex2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-ref", 2, apply22);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-ref", 1, s);
            }
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "x", -2, apply2);
        }
    }

    public static Object pregexpReadEscapedChar(Object s, Object i, Object n) {
        Object apply2 = Scheme.numLss.apply2(AddOp.$Pl.apply2(i, Lit8), n);
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            }
            try {
                CharSequence charSequence = (CharSequence) s;
                Object apply22 = AddOp.$Pl.apply2(i, Lit8);
                try {
                    char c = strings.stringRef(charSequence, ((Number) apply22).intValue());
                    return Scheme.isEqv.apply2(Char.make(c), Lit25) != Boolean.FALSE ? LList.list2(Lit26, AddOp.$Pl.apply2(i, Lit16)) : Scheme.isEqv.apply2(Char.make(c), Lit27) != Boolean.FALSE ? LList.list2(Lit28, AddOp.$Pl.apply2(i, Lit16)) : Scheme.isEqv.apply2(Char.make(c), Lit29) != Boolean.FALSE ? LList.list2(Lit30, AddOp.$Pl.apply2(i, Lit16)) : Scheme.isEqv.apply2(Char.make(c), Lit31) != Boolean.FALSE ? LList.list2(Lit32, AddOp.$Pl.apply2(i, Lit16)) : Scheme.isEqv.apply2(Char.make(c), Lit33) != Boolean.FALSE ? LList.list2(Lit24, AddOp.$Pl.apply2(i, Lit16)) : Scheme.isEqv.apply2(Char.make(c), Lit34) != Boolean.FALSE ? LList.list2($Stpregexp$Mnreturn$Mnchar$St, AddOp.$Pl.apply2(i, Lit16)) : Scheme.isEqv.apply2(Char.make(c), Lit35) != Boolean.FALSE ? LList.list2(Lit36, AddOp.$Pl.apply2(i, Lit16)) : Scheme.isEqv.apply2(Char.make(c), Lit37) != Boolean.FALSE ? LList.list2(Lit38, AddOp.$Pl.apply2(i, Lit16)) : Scheme.isEqv.apply2(Char.make(c), Lit39) != Boolean.FALSE ? LList.list2($Stpregexp$Mntab$Mnchar$St, AddOp.$Pl.apply2(i, Lit16)) : Scheme.isEqv.apply2(Char.make(c), Lit40) != Boolean.FALSE ? LList.list2(Lit41, AddOp.$Pl.apply2(i, Lit16)) : Scheme.isEqv.apply2(Char.make(c), Lit42) != Boolean.FALSE ? LList.list2(Lit43, AddOp.$Pl.apply2(i, Lit16)) : LList.list2(Char.make(c), AddOp.$Pl.apply2(i, Lit16));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, apply22);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, s);
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "x", -2, apply2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0086, code lost:
        if (r5 != false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00b3, code lost:
        if (kawa.lib.characters.isChar$Eq(gnu.text.Char.make(kawa.lib.strings.stringRef(r12, ((java.lang.Number) r7).intValue())), gnu.kawa.slib.pregexp.Lit46) != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00b5, code lost:
        r6 = pregexpReverse$Ex(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00bb, code lost:
        r3 = kawa.lib.misc.string$To$Symbol(kawa.lib.strings.list$To$String((gnu.lists.LList) r6));
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00c5, code lost:
        if (r2 == java.lang.Boolean.FALSE) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00c7, code lost:
        r3 = gnu.lists.LList.list2(gnu.kawa.slib.pregexp.Lit17, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0115, code lost:
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x011d, code lost:
        throw new gnu.mapping.WrongType(r7, "list->string", 1, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:?, code lost:
        return pregexpError$V(new java.lang.Object[]{gnu.kawa.slib.pregexp.Lit45});
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:?, code lost:
        return gnu.lists.LList.list2(r3, gnu.kawa.functions.AddOp.$Pl.apply2(r13, gnu.kawa.slib.pregexp.Lit16));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object pregexpReadPosixCharClass(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 286
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadPosixCharClass(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpReadClusterType(Object s, Object i, Object n) {
        char c;
        try {
            try {
                if (Scheme.isEqv.apply2(Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())), Lit47) != Boolean.FALSE) {
                    Object i2 = AddOp.$Pl.apply2(i, Lit8);
                    try {
                        try {
                            char tmp = strings.stringRef((CharSequence) s, ((Number) i2).intValue());
                            if (Scheme.isEqv.apply2(Char.make(tmp), Lit44) != Boolean.FALSE) {
                                return LList.list2(LList.Empty, AddOp.$Pl.apply2(i2, Lit8));
                            }
                            if (Scheme.isEqv.apply2(Char.make(tmp), Lit48) != Boolean.FALSE) {
                                return LList.list2(Lit49, AddOp.$Pl.apply2(i2, Lit8));
                            }
                            if (Scheme.isEqv.apply2(Char.make(tmp), Lit50) != Boolean.FALSE) {
                                return LList.list2(Lit51, AddOp.$Pl.apply2(i2, Lit8));
                            }
                            if (Scheme.isEqv.apply2(Char.make(tmp), Lit52) != Boolean.FALSE) {
                                return LList.list2(Lit53, AddOp.$Pl.apply2(i2, Lit8));
                            }
                            if (Scheme.isEqv.apply2(Char.make(tmp), Lit54) != Boolean.FALSE) {
                                try {
                                    CharSequence charSequence = (CharSequence) s;
                                    Object apply2 = AddOp.$Pl.apply2(i2, Lit8);
                                    try {
                                        char tmp2 = strings.stringRef(charSequence, ((Number) apply2).intValue());
                                        return LList.list2(Scheme.isEqv.apply2(Char.make(tmp2), Lit48) != Boolean.FALSE ? Lit55 : Scheme.isEqv.apply2(Char.make(tmp2), Lit50) != Boolean.FALSE ? Lit56 : pregexpError$V(new Object[]{Lit57}), AddOp.$Pl.apply2(i2, Lit16));
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "string-ref", 2, apply2);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "string-ref", 1, s);
                                }
                            }
                            LList lList = LList.Empty;
                            Boolean bool = Boolean.FALSE;
                            while (true) {
                                try {
                                    try {
                                        c = strings.stringRef((CharSequence) s, ((Number) i2).intValue());
                                        if (Scheme.isEqv.apply2(Char.make(c), Lit58) == Boolean.FALSE) {
                                            if (Scheme.isEqv.apply2(Char.make(c), Lit59) == Boolean.FALSE) {
                                                if (Scheme.isEqv.apply2(Char.make(c), Lit62) == Boolean.FALSE) {
                                                    break;
                                                }
                                                $Stpregexp$Mnspace$Mnsensitive$Qu$St = bool;
                                                i2 = AddOp.$Pl.apply2(i2, Lit8);
                                                bool = Boolean.FALSE;
                                            } else {
                                                i2 = AddOp.$Pl.apply2(i2, Lit8);
                                                lList = lists.cons(bool != Boolean.FALSE ? Lit60 : Lit61, lList);
                                                bool = Boolean.FALSE;
                                            }
                                        } else {
                                            i2 = AddOp.$Pl.apply2(i2, Lit8);
                                            bool = Boolean.TRUE;
                                        }
                                    } catch (ClassCastException e3) {
                                        throw new WrongType(e3, "string-ref", 2, i2);
                                    }
                                } catch (ClassCastException e4) {
                                    throw new WrongType(e4, "string-ref", 1, s);
                                }
                            }
                            return Scheme.isEqv.apply2(Char.make(c), Lit44) != Boolean.FALSE ? LList.list2(lList, AddOp.$Pl.apply2(i2, Lit8)) : pregexpError$V(new Object[]{Lit57});
                        } catch (ClassCastException e5) {
                            throw new WrongType(e5, "string-ref", 2, i2);
                        }
                    } catch (ClassCastException e6) {
                        throw new WrongType(e6, "string-ref", 1, s);
                    }
                }
                return LList.list2(Lit63, i);
            } catch (ClassCastException e7) {
                throw new WrongType(e7, "string-ref", 2, i);
            }
        } catch (ClassCastException e8) {
            throw new WrongType(e8, "string-ref", 1, s);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0054, code lost:
        if (kawa.lib.lists.isNull(r1) == false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0062, code lost:
        return gnu.lists.LList.list2(r8, gnu.kawa.functions.AddOp.$Pl.apply2(r7, gnu.kawa.slib.pregexp.Lit8));
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0063, code lost:
        if (r9 == false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0072, code lost:
        r2 = kawa.lib.lists.cdr.apply1(r1);
        r4 = gnu.lists.LList.list2(kawa.lib.lists.car.apply1(r1), r8);
        r8 = r4;
        r1 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:?, code lost:
        return pregexpError$V(new java.lang.Object[]{gnu.kawa.slib.pregexp.Lit64});
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x004e, code lost:
        if (kawa.lib.characters.isChar$Eq(gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r15, ((java.lang.Number) r7).intValue())), gnu.kawa.slib.pregexp.Lit6) != false) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object pregexpReadSubpattern(java.lang.Object r15, java.lang.Object r16, java.lang.Object r17) {
        /*
            java.lang.Object r5 = gnu.kawa.slib.pregexp.$Stpregexp$Mnspace$Mnsensitive$Qu$St
            java.lang.Object r3 = pregexpReadClusterType(r15, r16, r17)
            gnu.expr.GenericProc r10 = kawa.lib.lists.car
            java.lang.Object r1 = r10.apply1(r3)
            gnu.expr.GenericProc r10 = kawa.lib.lists.cadr
            java.lang.Object r16 = r10.apply1(r3)
            java.lang.Object r6 = pregexpReadPattern(r15, r16, r17)
            gnu.kawa.slib.pregexp.$Stpregexp$Mnspace$Mnsensitive$Qu$St = r5
            gnu.expr.GenericProc r10 = kawa.lib.lists.car
            java.lang.Object r8 = r10.apply1(r6)
            gnu.expr.GenericProc r10 = kawa.lib.lists.cadr
            java.lang.Object r7 = r10.apply1(r6)
            gnu.kawa.functions.NumberCompare r10 = kawa.standard.Scheme.numLss
            r0 = r17
            java.lang.Object r11 = r10.apply2(r7, r0)
            r0 = r11
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch: java.lang.ClassCastException -> L85
            r10 = r0
            boolean r9 = r10.booleanValue()     // Catch: java.lang.ClassCastException -> L85
            if (r9 == 0) goto L63
            java.lang.CharSequence r15 = (java.lang.CharSequence) r15     // Catch: java.lang.ClassCastException -> L8f
            r0 = r7
            java.lang.Number r0 = (java.lang.Number) r0     // Catch: java.lang.ClassCastException -> L99
            r10 = r0
            int r10 = r10.intValue()     // Catch: java.lang.ClassCastException -> L99
            char r10 = kawa.lib.strings.stringRef(r15, r10)
            gnu.text.Char r10 = gnu.text.Char.make(r10)
            gnu.text.Char r11 = gnu.kawa.slib.pregexp.Lit6
            boolean r10 = kawa.lib.characters.isChar$Eq(r10, r11)
            if (r10 == 0) goto L65
        L50:
            boolean r10 = kawa.lib.lists.isNull(r1)
            if (r10 == 0) goto L72
            gnu.kawa.functions.AddOp r10 = gnu.kawa.functions.AddOp.$Pl
            gnu.math.IntNum r11 = gnu.kawa.slib.pregexp.Lit8
            java.lang.Object r10 = r10.apply2(r7, r11)
            gnu.lists.Pair r10 = gnu.lists.LList.list2(r8, r10)
        L62:
            return r10
        L63:
            if (r9 != 0) goto L50
        L65:
            r10 = 1
            java.lang.Object[] r10 = new java.lang.Object[r10]
            r11 = 0
            gnu.mapping.SimpleSymbol r12 = gnu.kawa.slib.pregexp.Lit64
            r10[r11] = r12
            java.lang.Object r10 = pregexpError$V(r10)
            goto L62
        L72:
            gnu.expr.GenericProc r10 = kawa.lib.lists.cdr
            java.lang.Object r2 = r10.apply1(r1)
            gnu.expr.GenericProc r10 = kawa.lib.lists.car
            java.lang.Object r10 = r10.apply1(r1)
            gnu.lists.Pair r4 = gnu.lists.LList.list2(r10, r8)
            r8 = r4
            r1 = r2
            goto L50
        L85:
            r10 = move-exception
            gnu.mapping.WrongType r12 = new gnu.mapping.WrongType
            java.lang.String r13 = "x"
            r14 = -2
            r12.<init>(r10, r13, r14, r11)
            throw r12
        L8f:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            java.lang.String r12 = "string-ref"
            r13 = 1
            r11.<init>(r10, r12, r13, r15)
            throw r11
        L99:
            r10 = move-exception
            gnu.mapping.WrongType r11 = new gnu.mapping.WrongType
            java.lang.String r12 = "string-ref"
            r13 = 2
            r11.<init>(r10, r12, r13, r7)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadSubpattern(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:159:0x01b1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x020b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object pregexpWrapQuantifierIfAny(java.lang.Object r13, java.lang.Object r14, java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 711
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpWrapQuantifierIfAny(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object pregexpReadNums(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 321
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadNums(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpInvertCharList(Object vv) {
        Object apply1 = lists.car.apply1(vv);
        try {
            ((Pair) apply1).setCar(Lit79);
            return vv;
        } catch (ClassCastException e) {
            throw new WrongType(e, "set-car!", 1, apply1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x014d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0113 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object pregexpReadCharList(java.lang.Object r13, java.lang.Object r14, java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 559
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadCharList(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpStringMatch(Object s1, Object s, Object i, Object n, Object sk, Object fk) {
        try {
            int n1 = strings.stringLength((CharSequence) s1);
            if (Scheme.numGrt.apply2(Integer.valueOf(n1), n) != Boolean.FALSE) {
                return Scheme.applyToArgs.apply1(fk);
            }
            Object obj = Lit73;
            Object obj2 = i;
            while (Scheme.numGEq.apply2(obj, Integer.valueOf(n1)) == Boolean.FALSE) {
                if (Scheme.numGEq.apply2(obj2, n) != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply1(fk);
                }
                try {
                    try {
                        try {
                            try {
                                if (characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s1, ((Number) obj).intValue())), Char.make(strings.stringRef((CharSequence) s, ((Number) obj2).intValue())))) {
                                    obj = AddOp.$Pl.apply2(obj, Lit8);
                                    Object k = AddOp.$Pl.apply2(obj2, Lit8);
                                    obj2 = k;
                                } else {
                                    return Scheme.applyToArgs.apply1(fk);
                                }
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, obj2);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, s);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "string-ref", 2, obj);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "string-ref", 1, s1);
                }
            }
            return Scheme.applyToArgs.apply2(sk, obj2);
        } catch (ClassCastException e5) {
            throw new WrongType(e5, "string-length", 1, s1);
        }
    }

    public static boolean isPregexpCharWord(Object c) {
        try {
            boolean x = unicode.isCharAlphabetic((Char) c);
            if (x) {
                return x;
            }
            try {
                boolean x2 = unicode.isCharNumeric((Char) c);
                if (x2) {
                    return x2;
                }
                try {
                    return characters.isChar$Eq((Char) c, Lit84);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "char=?", 1, c);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "char-numeric?", 1, c);
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "char-alphabetic?", 1, c);
        }
    }

    public static Object isPregexpAtWordBoundary(Object s, Object i, Object n) {
        Boolean x;
        Object apply2 = Scheme.numEqu.apply2(i, Lit73);
        try {
            boolean x2 = ((Boolean) apply2).booleanValue();
            if (x2) {
                return x2 ? Boolean.TRUE : Boolean.FALSE;
            }
            Object apply22 = Scheme.numGEq.apply2(i, n);
            try {
                boolean x3 = ((Boolean) apply22).booleanValue();
                if (x3) {
                    return x3 ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    try {
                        char c$Sli = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                        try {
                            CharSequence charSequence = (CharSequence) s;
                            Object apply23 = AddOp.$Mn.apply2(i, Lit8);
                            try {
                                char c$Sli$Mn1 = strings.stringRef(charSequence, ((Number) apply23).intValue());
                                Object c$Sli$Slw$Qu = isPregexpCheckIfInCharClass(Char.make(c$Sli), Lit41);
                                Object c$Sli$Mn1$Slw$Qu = isPregexpCheckIfInCharClass(Char.make(c$Sli$Mn1), Lit41);
                                if (c$Sli$Slw$Qu != Boolean.FALSE) {
                                    x = c$Sli$Mn1$Slw$Qu != Boolean.FALSE ? Boolean.FALSE : Boolean.TRUE;
                                } else {
                                    x = c$Sli$Slw$Qu;
                                }
                                if (x != Boolean.FALSE) {
                                    return x;
                                }
                                try {
                                    int i2 = ((c$Sli$Slw$Qu != Boolean.FALSE ? 1 : 0) + 1) & 1;
                                    return i2 != 0 ? c$Sli$Mn1$Slw$Qu : i2 != 0 ? Boolean.TRUE : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "x", -2, c$Sli$Slw$Qu);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 2, apply23);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 1, s);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-ref", 2, i);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-ref", 1, s);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "x", -2, apply22);
            }
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "x", -2, apply2);
        }
    }

    public static Object isPregexpCheckIfInCharClass(Object c, Object char$Mnclass) {
        boolean x;
        if (Scheme.isEqv.apply2(char$Mnclass, Lit14) != Boolean.FALSE) {
            try {
                return characters.isChar$Eq((Char) c, Lit24) ? Boolean.FALSE : Boolean.TRUE;
            } catch (ClassCastException e) {
                throw new WrongType(e, "char=?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit85) != Boolean.FALSE) {
            try {
                boolean x2 = unicode.isCharAlphabetic((Char) c);
                if (x2) {
                    return x2 ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    return unicode.isCharNumeric((Char) c) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "char-numeric?", 1, c);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "char-alphabetic?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit86) != Boolean.FALSE) {
            try {
                return unicode.isCharAlphabetic((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "char-alphabetic?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit87) != Boolean.FALSE) {
            try {
                return ((Char) c).intValue() < 128 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit88) != Boolean.FALSE) {
            try {
                boolean x3 = characters.isChar$Eq((Char) c, Lit3);
                if (x3) {
                    return x3 ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    Char r7 = (Char) c;
                    Object obj = $Stpregexp$Mntab$Mnchar$St;
                    try {
                        return characters.isChar$Eq(r7, (Char) obj) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e6) {
                        throw new WrongType(e6, "char=?", 2, obj);
                    }
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "char=?", 1, c);
                }
            } catch (ClassCastException e8) {
                throw new WrongType(e8, "char=?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit89) != Boolean.FALSE) {
            try {
                return ((Char) c).intValue() < 32 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e9) {
                throw new WrongType(e9, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit30) != Boolean.FALSE) {
            try {
                return unicode.isCharNumeric((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e10) {
                throw new WrongType(e10, "char-numeric?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit90) != Boolean.FALSE) {
            try {
                x = ((Char) c).intValue() >= 32;
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    return unicode.isCharWhitespace((Char) c) ? Boolean.FALSE : Boolean.TRUE;
                } catch (ClassCastException e11) {
                    throw new WrongType(e11, "char-whitespace?", 1, c);
                }
            } catch (ClassCastException e12) {
                throw new WrongType(e12, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit91) != Boolean.FALSE) {
            try {
                return unicode.isCharLowerCase((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e13) {
                throw new WrongType(e13, "char-lower-case?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit92) != Boolean.FALSE) {
            try {
                return ((Char) c).intValue() >= 32 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e14) {
                throw new WrongType(e14, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit93) != Boolean.FALSE) {
            try {
                x = ((Char) c).intValue() >= 32;
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    int i = ((unicode.isCharWhitespace((Char) c) ? 1 : 0) + 1) & 1;
                    if (i == 0) {
                        return i != 0 ? Boolean.TRUE : Boolean.FALSE;
                    }
                    try {
                        int i2 = ((unicode.isCharAlphabetic((Char) c) ? 1 : 0) + 1) & 1;
                        if (i2 == 0) {
                            return i2 != 0 ? Boolean.TRUE : Boolean.FALSE;
                        }
                        try {
                            return unicode.isCharNumeric((Char) c) ? Boolean.FALSE : Boolean.TRUE;
                        } catch (ClassCastException e15) {
                            throw new WrongType(e15, "char-numeric?", 1, c);
                        }
                    } catch (ClassCastException e16) {
                        throw new WrongType(e16, "char-alphabetic?", 1, c);
                    }
                } catch (ClassCastException e17) {
                    throw new WrongType(e17, "char-whitespace?", 1, c);
                }
            } catch (ClassCastException e18) {
                throw new WrongType(e18, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit36) != Boolean.FALSE) {
            try {
                return unicode.isCharWhitespace((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e19) {
                throw new WrongType(e19, "char-whitespace?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit94) != Boolean.FALSE) {
            try {
                return unicode.isCharUpperCase((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e20) {
                throw new WrongType(e20, "char-upper-case?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit41) != Boolean.FALSE) {
            try {
                boolean x4 = unicode.isCharAlphabetic((Char) c);
                if (x4) {
                    return x4 ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    boolean x5 = unicode.isCharNumeric((Char) c);
                    if (x5) {
                        return x5 ? Boolean.TRUE : Boolean.FALSE;
                    }
                    try {
                        return characters.isChar$Eq((Char) c, Lit84) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e21) {
                        throw new WrongType(e21, "char=?", 1, c);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "char-numeric?", 1, c);
                }
            } catch (ClassCastException e23) {
                throw new WrongType(e23, "char-alphabetic?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit95) != Boolean.FALSE) {
            try {
                boolean x6 = unicode.isCharNumeric((Char) c);
                if (x6) {
                    return x6 ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    boolean x7 = unicode.isCharCi$Eq((Char) c, Lit2);
                    if (x7) {
                        return x7 ? Boolean.TRUE : Boolean.FALSE;
                    }
                    try {
                        boolean x8 = unicode.isCharCi$Eq((Char) c, Lit25);
                        if (x8) {
                            return x8 ? Boolean.TRUE : Boolean.FALSE;
                        }
                        try {
                            boolean x9 = unicode.isCharCi$Eq((Char) c, Lit96);
                            if (x9) {
                                return x9 ? Boolean.TRUE : Boolean.FALSE;
                            }
                            try {
                                boolean x10 = unicode.isCharCi$Eq((Char) c, Lit29);
                                if (x10) {
                                    return x10 ? Boolean.TRUE : Boolean.FALSE;
                                }
                                try {
                                    boolean x11 = unicode.isCharCi$Eq((Char) c, Lit97);
                                    if (x11) {
                                        return x11 ? Boolean.TRUE : Boolean.FALSE;
                                    }
                                    try {
                                        return unicode.isCharCi$Eq((Char) c, Lit98) ? Boolean.TRUE : Boolean.FALSE;
                                    } catch (ClassCastException e24) {
                                        throw new WrongType(e24, "char-ci=?", 1, c);
                                    }
                                } catch (ClassCastException e25) {
                                    throw new WrongType(e25, "char-ci=?", 1, c);
                                }
                            } catch (ClassCastException e26) {
                                throw new WrongType(e26, "char-ci=?", 1, c);
                            }
                        } catch (ClassCastException e27) {
                            throw new WrongType(e27, "char-ci=?", 1, c);
                        }
                    } catch (ClassCastException e28) {
                        throw new WrongType(e28, "char-ci=?", 1, c);
                    }
                } catch (ClassCastException e29) {
                    throw new WrongType(e29, "char-ci=?", 1, c);
                }
            } catch (ClassCastException e30) {
                throw new WrongType(e30, "char-numeric?", 1, c);
            }
        } else {
            return pregexpError$V(new Object[]{Lit99});
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 33:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 34:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 47:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object pregexpListRef(Object s, Object i) {
        Object obj = Lit73;
        while (!lists.isNull(s)) {
            if (Scheme.numEqu.apply2(obj, i) != Boolean.FALSE) {
                return lists.car.apply1(s);
            }
            s = lists.cdr.apply1(s);
            obj = AddOp.$Pl.apply2(obj, Lit8);
        }
        return Boolean.FALSE;
    }

    public static Object pregexpMakeBackrefList(Object re) {
        return lambda1sub(re);
    }

    public static Object lambda1sub(Object re) {
        if (lists.isPair(re)) {
            Object car$Mnre = lists.car.apply1(re);
            Object sub$Mncdr$Mnre = lambda1sub(lists.cdr.apply1(re));
            return Scheme.isEqv.apply2(car$Mnre, Lit100) != Boolean.FALSE ? lists.cons(lists.cons(re, Boolean.FALSE), sub$Mncdr$Mnre) : append.append$V(new Object[]{lambda1sub(car$Mnre), sub$Mncdr$Mnre});
        }
        return LList.Empty;
    }

    /* compiled from: pregexp.scm */
    /* loaded from: classes.dex */
    public class frame extends ModuleBody {
        Object backrefs;
        Object case$Mnsensitive$Qu;
        Procedure identity;
        Object n;
        Object s;
        Object sn;
        Object start;

        public frame() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 15, pregexp.Lit112, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:460");
            this.identity = moduleMethod;
        }

        public static Object lambda2identity(Object x) {
            return x;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 15 ? lambda2identity(obj) : super.apply1(moduleMethod, obj);
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 15) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }

        static Boolean lambda4() {
            return Boolean.FALSE;
        }

        /* JADX WARN: Code restructure failed: missing block: B:101:0x02c9, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit17) == java.lang.Boolean.FALSE) goto L150;
         */
        /* JADX WARN: Code restructure failed: missing block: B:103:0x02d7, code lost:
            if (kawa.standard.Scheme.numGEq.apply2(r6.i, r15.n) == java.lang.Boolean.FALSE) goto L148;
         */
        /* JADX WARN: Code restructure failed: missing block: B:107:0x0301, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit5) == java.lang.Boolean.FALSE) goto L154;
         */
        /* JADX WARN: Code restructure failed: missing block: B:110:0x031d, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit4) == java.lang.Boolean.FALSE) goto L158;
         */
        /* JADX WARN: Code restructure failed: missing block: B:113:0x0337, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit20) == java.lang.Boolean.FALSE) goto L191;
         */
        /* JADX WARN: Code restructure failed: missing block: B:114:0x0339, code lost:
            r8 = gnu.kawa.slib.pregexp.pregexpListRef(r15.backrefs, kawa.lib.lists.cadr.apply1(r6.re$1));
         */
        /* JADX WARN: Code restructure failed: missing block: B:115:0x0349, code lost:
            if (r8 == java.lang.Boolean.FALSE) goto L190;
         */
        /* JADX WARN: Code restructure failed: missing block: B:116:0x034b, code lost:
            r7 = kawa.lib.lists.cdr.apply1(r8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:118:0x0353, code lost:
            if (r7 == java.lang.Boolean.FALSE) goto L188;
         */
        /* JADX WARN: Code restructure failed: missing block: B:119:0x0355, code lost:
            r1 = r15.s;
         */
        /* JADX WARN: Code restructure failed: missing block: B:120:0x0357, code lost:
            r1 = (java.lang.CharSequence) r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:121:0x0359, code lost:
            r3 = kawa.lib.lists.car.apply1(r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:122:0x035f, code lost:
            r4 = ((java.lang.Number) r3).intValue();
         */
        /* JADX WARN: Code restructure failed: missing block: B:123:0x0367, code lost:
            r3 = kawa.lib.lists.cdr.apply1(r7);
         */
        /* JADX WARN: Code restructure failed: missing block: B:126:0x0389, code lost:
            gnu.kawa.slib.pregexp.pregexpError$V(new java.lang.Object[]{gnu.kawa.slib.pregexp.Lit101, gnu.kawa.slib.pregexp.Lit102, r6.re$1});
            r7 = java.lang.Boolean.FALSE;
         */
        /* JADX WARN: Code restructure failed: missing block: B:129:0x03b7, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit100) == java.lang.Boolean.FALSE) goto L195;
         */
        /* JADX WARN: Code restructure failed: missing block: B:132:0x03d7, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit103) == java.lang.Boolean.FALSE) goto L203;
         */
        /* JADX WARN: Code restructure failed: missing block: B:134:0x03ed, code lost:
            if (lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r15.identity, gnu.kawa.slib.pregexp.lambda$Fn6) == java.lang.Boolean.FALSE) goto L201;
         */
        /* JADX WARN: Code restructure failed: missing block: B:138:0x040f, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit104) == java.lang.Boolean.FALSE) goto L211;
         */
        /* JADX WARN: Code restructure failed: missing block: B:140:0x0425, code lost:
            if (lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r15.identity, gnu.kawa.slib.pregexp.lambda$Fn7) == java.lang.Boolean.FALSE) goto L209;
         */
        /* JADX WARN: Code restructure failed: missing block: B:144:0x0447, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit105) == java.lang.Boolean.FALSE) goto L219;
         */
        /* JADX WARN: Code restructure failed: missing block: B:145:0x0449, code lost:
            r11 = r15.n;
            r12 = r15.sn;
            r15.n = r6.i;
            r15.sn = r6.i;
            r10 = lambda3sub(gnu.lists.LList.list4(gnu.kawa.slib.pregexp.Lit5, gnu.kawa.slib.pregexp.Lit106, kawa.lib.lists.cadr.apply1(r6.re$1), gnu.kawa.slib.pregexp.Lit12), gnu.kawa.slib.pregexp.Lit73, r15.identity, gnu.kawa.slib.pregexp.lambda$Fn8);
            r15.n = r11;
            r15.sn = r12;
         */
        /* JADX WARN: Code restructure failed: missing block: B:146:0x0477, code lost:
            if (r10 == java.lang.Boolean.FALSE) goto L217;
         */
        /* JADX WARN: Code restructure failed: missing block: B:150:0x0499, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit107) == java.lang.Boolean.FALSE) goto L227;
         */
        /* JADX WARN: Code restructure failed: missing block: B:151:0x049b, code lost:
            r11 = r15.n;
            r12 = r15.sn;
            r15.n = r6.i;
            r15.sn = r6.i;
            r10 = lambda3sub(gnu.lists.LList.list4(gnu.kawa.slib.pregexp.Lit5, gnu.kawa.slib.pregexp.Lit108, kawa.lib.lists.cadr.apply1(r6.re$1), gnu.kawa.slib.pregexp.Lit12), gnu.kawa.slib.pregexp.Lit73, r15.identity, gnu.kawa.slib.pregexp.lambda$Fn9);
            r15.n = r11;
            r15.sn = r12;
         */
        /* JADX WARN: Code restructure failed: missing block: B:152:0x04c9, code lost:
            if (r10 == java.lang.Boolean.FALSE) goto L225;
         */
        /* JADX WARN: Code restructure failed: missing block: B:156:0x04eb, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit109) == java.lang.Boolean.FALSE) goto L235;
         */
        /* JADX WARN: Code restructure failed: missing block: B:157:0x04ed, code lost:
            r10 = lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r15.identity, gnu.kawa.slib.pregexp.lambda$Fn10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:158:0x0501, code lost:
            if (r10 == java.lang.Boolean.FALSE) goto L233;
         */
        /* JADX WARN: Code restructure failed: missing block: B:161:0x0517, code lost:
            r14 = kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit60);
         */
        /* JADX WARN: Code restructure failed: missing block: B:162:0x0521, code lost:
            if (r14 == java.lang.Boolean.FALSE) goto L271;
         */
        /* JADX WARN: Code restructure failed: missing block: B:164:0x0525, code lost:
            if (r14 == java.lang.Boolean.FALSE) goto L241;
         */
        /* JADX WARN: Code restructure failed: missing block: B:165:0x0527, code lost:
            r6.old = r15.case$Mnsensitive$Qu;
            r15.case$Mnsensitive$Qu = kawa.standard.Scheme.isEqv.apply2(kawa.lib.lists.car.apply1(r6.re$1), gnu.kawa.slib.pregexp.Lit60);
         */
        /* JADX WARN: Code restructure failed: missing block: B:167:0x055b, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit61) != java.lang.Boolean.FALSE) goto L239;
         */
        /* JADX WARN: Code restructure failed: missing block: B:169:0x0567, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit68) == java.lang.Boolean.FALSE) goto L269;
         */
        /* JADX WARN: Code restructure failed: missing block: B:170:0x0569, code lost:
            r1 = kawa.lib.lists.cadr.apply1(r6.re$1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:172:0x0573, code lost:
            if (r1 == java.lang.Boolean.FALSE) goto L264;
         */
        /* JADX WARN: Code restructure failed: missing block: B:173:0x0575, code lost:
            r1 = 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:174:0x0576, code lost:
            r6.maximal$Qu = (r1 + 1) & 1;
            r6.p = kawa.lib.lists.caddr.apply1(r6.re$1);
            r6.q = kawa.lib.lists.cadddr.apply1(r6.re$1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:175:0x0592, code lost:
            if (r6.maximal$Qu == false) goto L263;
         */
        /* JADX WARN: Code restructure failed: missing block: B:176:0x0594, code lost:
            r1 = r6.q;
         */
        /* JADX WARN: Code restructure failed: missing block: B:178:0x0598, code lost:
            if (r1 == java.lang.Boolean.FALSE) goto L258;
         */
        /* JADX WARN: Code restructure failed: missing block: B:179:0x059a, code lost:
            r1 = 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:180:0x059b, code lost:
            r1 = (r1 + 1) & 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:181:0x059f, code lost:
            r6.could$Mnloop$Mninfinitely$Qu = r1;
            r6.re = kawa.lib.lists.car.apply1(kawa.lib.lists.cddddr.apply1(r6.re$1));
         */
        /* JADX WARN: Code restructure failed: missing block: B:182:0x05bb, code lost:
            r1 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:183:0x05bd, code lost:
            r1 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:184:0x05bf, code lost:
            r1 = r6.maximal$Qu;
         */
        /* JADX WARN: Code restructure failed: missing block: B:187:0x05dc, code lost:
            if (kawa.standard.Scheme.numGEq.apply2(r6.i, r15.n) == java.lang.Boolean.FALSE) goto L277;
         */
        /* JADX WARN: Code restructure failed: missing block: B:196:0x060a, code lost:
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:198:0x0613, code lost:
            throw new gnu.mapping.WrongType(r2, "string-ref", 1, r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:199:0x0614, code lost:
            r1 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:201:0x061d, code lost:
            throw new gnu.mapping.WrongType(r1, "string-ref", 2, r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:202:0x061e, code lost:
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:204:0x0627, code lost:
            throw new gnu.mapping.WrongType(r2, "string-ref", 1, r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:205:0x0628, code lost:
            r1 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:207:0x0631, code lost:
            throw new gnu.mapping.WrongType(r1, "string-ref", 2, r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:208:0x0632, code lost:
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:210:0x063b, code lost:
            throw new gnu.mapping.WrongType(r2, "substring", 1, r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:211:0x063c, code lost:
            r1 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:213:0x0645, code lost:
            throw new gnu.mapping.WrongType(r1, "substring", 2, r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:214:0x0646, code lost:
            r1 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:216:0x064f, code lost:
            throw new gnu.mapping.WrongType(r1, "substring", 3, r3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:217:0x0650, code lost:
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:219:0x0659, code lost:
            throw new gnu.mapping.WrongType(r2, "maximal?", -2, r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:220:0x065a, code lost:
            r2 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:222:0x0663, code lost:
            throw new gnu.mapping.WrongType(r2, "could-loop-infinitely?", -2, r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:254:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, gnu.kawa.functions.AddOp.$Pl.apply2(r6.i, gnu.kawa.slib.pregexp.Lit8));
         */
        /* JADX WARN: Code restructure failed: missing block: B:256:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, gnu.kawa.functions.AddOp.$Pl.apply2(r6.i, gnu.kawa.slib.pregexp.Lit8));
         */
        /* JADX WARN: Code restructure failed: missing block: B:257:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:258:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:259:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:260:?, code lost:
            return gnu.kawa.slib.pregexp.pregexpError$V(new java.lang.Object[]{gnu.kawa.slib.pregexp.Lit101});
         */
        /* JADX WARN: Code restructure failed: missing block: B:261:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:262:?, code lost:
            return r6.lambda5loupOneOfChars(kawa.lib.lists.cdr.apply1(r6.re$1));
         */
        /* JADX WARN: Code restructure failed: missing block: B:263:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:264:?, code lost:
            return lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r6.lambda$Fn2, r6.lambda$Fn3);
         */
        /* JADX WARN: Code restructure failed: missing block: B:265:?, code lost:
            return r6.lambda6loupSeq(kawa.lib.lists.cdr.apply1(r6.re$1), r6.i);
         */
        /* JADX WARN: Code restructure failed: missing block: B:266:?, code lost:
            return r6.lambda7loupOr(kawa.lib.lists.cdr.apply1(r6.re$1));
         */
        /* JADX WARN: Code restructure failed: missing block: B:267:?, code lost:
            return gnu.kawa.slib.pregexp.pregexpStringMatch(kawa.lib.strings.substring(r1, r4, ((java.lang.Number) r3).intValue()), r15.s, r6.i, r15.n, r6.lambda$Fn4, r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:268:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r6.i);
         */
        /* JADX WARN: Code restructure failed: missing block: B:269:?, code lost:
            return lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r6.lambda$Fn5, r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:270:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r6.i);
         */
        /* JADX WARN: Code restructure failed: missing block: B:271:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:272:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:273:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r6.i);
         */
        /* JADX WARN: Code restructure failed: missing block: B:274:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r6.i);
         */
        /* JADX WARN: Code restructure failed: missing block: B:275:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:276:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:277:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r6.i);
         */
        /* JADX WARN: Code restructure failed: missing block: B:278:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r6.sk, r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:279:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:280:?, code lost:
            return lambda3sub(kawa.lib.lists.cadr.apply1(r6.re$1), r6.i, r6.lambda$Fn11, r6.lambda$Fn12);
         */
        /* JADX WARN: Code restructure failed: missing block: B:281:?, code lost:
            return r6.lambda8loupP(gnu.kawa.slib.pregexp.Lit73, r6.i);
         */
        /* JADX WARN: Code restructure failed: missing block: B:282:?, code lost:
            return gnu.kawa.slib.pregexp.pregexpError$V(new java.lang.Object[]{gnu.kawa.slib.pregexp.Lit101});
         */
        /* JADX WARN: Code restructure failed: missing block: B:283:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply1(r6.fk);
         */
        /* JADX WARN: Code restructure failed: missing block: B:284:?, code lost:
            return gnu.kawa.slib.pregexp.pregexpError$V(new java.lang.Object[]{gnu.kawa.slib.pregexp.Lit101});
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x0148, code lost:
            r14 = ((kawa.lib.lists.isPair(r6.re$1) ? 1 : 0) + 1) & 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x0152, code lost:
            if (r14 == 0) goto L282;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x0160, code lost:
            if (kawa.standard.Scheme.numLss.apply2(r6.i, r15.n) == java.lang.Boolean.FALSE) goto L90;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0162, code lost:
            r1 = r15.s;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x0164, code lost:
            r1 = (java.lang.CharSequence) r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x0166, code lost:
            r3 = r6.i;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x0180, code lost:
            if (gnu.kawa.slib.pregexp.isPregexpCheckIfInCharClass(gnu.text.Char.make(kawa.lib.strings.stringRef(r1, ((java.lang.Number) r3).intValue())), r6.re$1) == java.lang.Boolean.FALSE) goto L80;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x01a5, code lost:
            if (r14 != 0) goto L70;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x01a7, code lost:
            r14 = kawa.lib.lists.isPair(r6.re$1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:60:0x01ad, code lost:
            if (r14 == false) goto L281;
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x01af, code lost:
            r14 = kawa.standard.Scheme.isEqv.apply2(kawa.lib.lists.car.apply1(r6.re$1), gnu.kawa.slib.pregexp.Lit83);
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x01c1, code lost:
            if (r14 == java.lang.Boolean.FALSE) goto L279;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x01cf, code lost:
            if (kawa.standard.Scheme.numLss.apply2(r6.i, r15.n) == java.lang.Boolean.FALSE) goto L124;
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x01d1, code lost:
            r1 = r15.s;
         */
        /* JADX WARN: Code restructure failed: missing block: B:66:0x01d3, code lost:
            r1 = (java.lang.CharSequence) r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x01d5, code lost:
            r3 = r6.i;
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x01df, code lost:
            r8 = kawa.lib.strings.stringRef(r1, ((java.lang.Number) r3).intValue());
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x01e7, code lost:
            if (r15.case$Mnsensitive$Qu == java.lang.Boolean.FALSE) goto L115;
         */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x01e9, code lost:
            r9 = kawa.lib.characters.char$Ls$Eq$Qu;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x01eb, code lost:
            r14 = r9.apply2(kawa.lib.lists.cadr.apply1(r6.re$1), gnu.text.Char.make(r8));
         */
        /* JADX WARN: Code restructure failed: missing block: B:73:0x01fd, code lost:
            if (r14 == java.lang.Boolean.FALSE) goto L113;
         */
        /* JADX WARN: Code restructure failed: missing block: B:75:0x0211, code lost:
            if (r9.apply2(gnu.text.Char.make(r8), kawa.lib.lists.caddr.apply1(r6.re$1)) == java.lang.Boolean.FALSE) goto L111;
         */
        /* JADX WARN: Code restructure failed: missing block: B:79:0x0233, code lost:
            if (r14 != java.lang.Boolean.FALSE) goto L96;
         */
        /* JADX WARN: Code restructure failed: missing block: B:81:0x023b, code lost:
            if (kawa.lib.lists.isPair(r6.re$1) == false) goto L273;
         */
        /* JADX WARN: Code restructure failed: missing block: B:82:0x023d, code lost:
            r13 = kawa.lib.lists.car.apply1(r6.re$1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:83:0x024f, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit83) == java.lang.Boolean.FALSE) goto L134;
         */
        /* JADX WARN: Code restructure failed: missing block: B:85:0x025d, code lost:
            if (kawa.standard.Scheme.numGEq.apply2(r6.i, r15.n) == java.lang.Boolean.FALSE) goto L132;
         */
        /* JADX WARN: Code restructure failed: missing block: B:87:0x0269, code lost:
            if (r14 == false) goto L124;
         */
        /* JADX WARN: Code restructure failed: missing block: B:89:0x026d, code lost:
            r9 = kawa.lib.rnrs.unicode.char$Mnci$Ls$Eq$Qu;
         */
        /* JADX WARN: Code restructure failed: missing block: B:91:0x0273, code lost:
            if (r14 != java.lang.Boolean.FALSE) goto L109;
         */
        /* JADX WARN: Code restructure failed: missing block: B:95:0x0297, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(r13, gnu.kawa.slib.pregexp.Lit82) == java.lang.Boolean.FALSE) goto L142;
         */
        /* JADX WARN: Code restructure failed: missing block: B:97:0x02a5, code lost:
            if (kawa.standard.Scheme.numGEq.apply2(r6.i, r15.n) == java.lang.Boolean.FALSE) goto L140;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:36:0x010f  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0132  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0196  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x019b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.Object lambda3sub(java.lang.Object r16, java.lang.Object r17, java.lang.Object r18, java.lang.Object r19) {
            /*
                Method dump skipped, instructions count: 1636
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.frame.lambda3sub(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }
    }

    public static Object pregexpMatchPositionsAux(Object re, Object s, Object sn, Object start, Object n, Object i) {
        frame frameVar = new frame();
        frameVar.s = s;
        frameVar.sn = sn;
        frameVar.start = start;
        frameVar.n = n;
        Procedure procedure = frameVar.identity;
        Object pregexpMakeBackrefList = pregexpMakeBackrefList(re);
        frameVar.case$Mnsensitive$Qu = Boolean.TRUE;
        frameVar.backrefs = pregexpMakeBackrefList;
        frameVar.identity = procedure;
        frameVar.lambda3sub(re, i, frameVar.identity, lambda$Fn1);
        Object arg0 = frameVar.backrefs;
        Object obj = LList.Empty;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                obj = Pair.make(lists.cdr.apply1(arg02.getCar()), obj);
                arg0 = arg03;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        LList backrefs = LList.reverseInPlace(obj);
        Object x = lists.car.apply1(backrefs);
        return x != Boolean.FALSE ? backrefs : x;
    }

    @Override // gnu.expr.ModuleBody
    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 36:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 37:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 38:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 39:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 40:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 41:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    /* compiled from: pregexp.scm */
    /* loaded from: classes.dex */
    public class frame0 extends ModuleBody {
        boolean could$Mnloop$Mninfinitely$Qu;
        Object fk;
        Object i;
        final ModuleMethod lambda$Fn11;
        final ModuleMethod lambda$Fn12;
        final ModuleMethod lambda$Fn2;
        final ModuleMethod lambda$Fn3;
        final ModuleMethod lambda$Fn4;
        final ModuleMethod lambda$Fn5;
        boolean maximal$Qu;
        Object old;
        Object p;
        Object q;
        Object re;
        Object re$1;
        Object sk;
        frame staticLink;

        public frame0() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 9, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:513");
            this.lambda$Fn2 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 10, null, 0);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:514");
            this.lambda$Fn3 = moduleMethod2;
            ModuleMethod moduleMethod3 = new ModuleMethod(this, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:541");
            this.lambda$Fn4 = moduleMethod3;
            ModuleMethod moduleMethod4 = new ModuleMethod(this, 12, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:545");
            this.lambda$Fn5 = moduleMethod4;
            ModuleMethod moduleMethod5 = new ModuleMethod(this, 13, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod5.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:587");
            this.lambda$Fn11 = moduleMethod5;
            ModuleMethod moduleMethod6 = new ModuleMethod(this, 14, null, 0);
            moduleMethod6.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:590");
            this.lambda$Fn12 = moduleMethod6;
        }

        public Object lambda5loupOneOfChars(Object chars) {
            frame1 frame1Var = new frame1();
            frame1Var.staticLink = this;
            frame1Var.chars = chars;
            return lists.isNull(frame1Var.chars) ? Scheme.applyToArgs.apply1(this.fk) : this.staticLink.lambda3sub(lists.car.apply1(frame1Var.chars), this.i, this.sk, frame1Var.lambda$Fn13);
        }

        Object lambda9(Object i1) {
            return Scheme.applyToArgs.apply1(this.fk);
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 9:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 10:
                default:
                    return super.match1(moduleMethod, obj, callContext);
                case 11:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 12:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 13:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
            }
        }

        Object lambda10() {
            return Scheme.applyToArgs.apply2(this.sk, AddOp.$Pl.apply2(this.i, pregexp.Lit8));
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 10:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 14:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public Object lambda6loupSeq(Object res, Object i) {
            frame2 frame2Var = new frame2();
            frame2Var.staticLink = this;
            frame2Var.res = res;
            return lists.isNull(frame2Var.res) ? Scheme.applyToArgs.apply2(this.sk, i) : this.staticLink.lambda3sub(lists.car.apply1(frame2Var.res), i, frame2Var.lambda$Fn14, this.fk);
        }

        public Object lambda7loupOr(Object res) {
            frame3 frame3Var = new frame3();
            frame3Var.staticLink = this;
            frame3Var.res = res;
            return lists.isNull(frame3Var.res) ? Scheme.applyToArgs.apply1(this.fk) : this.staticLink.lambda3sub(lists.car.apply1(frame3Var.res), this.i, frame3Var.lambda$Fn15, frame3Var.lambda$Fn16);
        }

        Object lambda11(Object i) {
            return Scheme.applyToArgs.apply2(this.sk, i);
        }

        Object lambda12(Object i1) {
            Object assv = lists.assv(this.re$1, this.staticLink.backrefs);
            try {
                ((Pair) assv).setCdr(lists.cons(this.i, i1));
                return Scheme.applyToArgs.apply2(this.sk, i1);
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, assv);
            }
        }

        static Boolean lambda13() {
            return Boolean.FALSE;
        }

        static Boolean lambda14() {
            return Boolean.FALSE;
        }

        static Boolean lambda15() {
            return Boolean.FALSE;
        }

        static Boolean lambda16() {
            return Boolean.FALSE;
        }

        static Boolean lambda17() {
            return Boolean.FALSE;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 9:
                    return lambda9(obj);
                case 10:
                default:
                    return super.apply1(moduleMethod, obj);
                case 11:
                    return lambda11(obj);
                case 12:
                    return lambda12(obj);
                case 13:
                    return lambda18(obj);
            }
        }

        Object lambda18(Object i1) {
            this.staticLink.case$Mnsensitive$Qu = this.old;
            return Scheme.applyToArgs.apply2(this.sk, i1);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 10:
                    return lambda10();
                case 14:
                    return lambda19();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        Object lambda19() {
            this.staticLink.case$Mnsensitive$Qu = this.old;
            return Scheme.applyToArgs.apply1(this.fk);
        }

        public Object lambda8loupP(Object k, Object i) {
            frame4 frame4Var = new frame4();
            frame4Var.staticLink = this;
            frame4Var.k = k;
            frame4Var.i = i;
            if (Scheme.numLss.apply2(frame4Var.k, this.p) != Boolean.FALSE) {
                return this.staticLink.lambda3sub(this.re, frame4Var.i, frame4Var.lambda$Fn17, this.fk);
            }
            frame4Var.q = this.q != Boolean.FALSE ? AddOp.$Mn.apply2(this.q, this.p) : this.q;
            return frame4Var.lambda24loupQ(pregexp.Lit73, frame4Var.i);
        }
    }

    /* compiled from: pregexp.scm */
    /* loaded from: classes.dex */
    public class frame1 extends ModuleBody {
        Object chars;
        final ModuleMethod lambda$Fn13;
        frame0 staticLink;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, null, 0);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:508");
            this.lambda$Fn13 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 1 ? lambda20() : super.apply0(moduleMethod);
        }

        Object lambda20() {
            return this.staticLink.lambda5loupOneOfChars(lists.cdr.apply1(this.chars));
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 1) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }
    }

    /* compiled from: pregexp.scm */
    /* loaded from: classes.dex */
    public class frame2 extends ModuleBody {
        final ModuleMethod lambda$Fn14;
        Object res;
        frame0 staticLink;

        public frame2() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:519");
            this.lambda$Fn14 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 2 ? lambda21(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda21(Object i1) {
            return this.staticLink.lambda6loupSeq(lists.cdr.apply1(this.res), i1);
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 2) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    /* compiled from: pregexp.scm */
    /* loaded from: classes.dex */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn15;
        final ModuleMethod lambda$Fn16;
        Object res;
        frame0 staticLink;

        public frame3() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 3, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:526");
            this.lambda$Fn15 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 4, null, 0);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:529");
            this.lambda$Fn16 = moduleMethod2;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 3 ? lambda22(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda22(Object i1) {
            Object x = Scheme.applyToArgs.apply2(this.staticLink.sk, i1);
            return x != Boolean.FALSE ? x : this.staticLink.lambda7loupOr(lists.cdr.apply1(this.res));
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 3) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 4 ? lambda23() : super.apply0(moduleMethod);
        }

        Object lambda23() {
            return this.staticLink.lambda7loupOr(lists.cdr.apply1(this.res));
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 4) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 36:
                return frame.lambda4();
            case 37:
                return frame0.lambda13();
            case 38:
                return frame0.lambda14();
            case 39:
                return frame0.lambda15();
            case 40:
                return frame0.lambda16();
            case 41:
                return frame0.lambda17();
            default:
                return super.apply0(moduleMethod);
        }
    }

    /* compiled from: pregexp.scm */
    /* loaded from: classes.dex */
    public class frame4 extends ModuleBody {
        Object i;
        Object k;
        final ModuleMethod lambda$Fn17;
        Object q;
        frame0 staticLink;

        public frame4() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 8, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:602");
            this.lambda$Fn17 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 8 ? lambda25(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda25(Object i1) {
            if (!this.staticLink.could$Mnloop$Mninfinitely$Qu ? this.staticLink.could$Mnloop$Mninfinitely$Qu : Scheme.numEqu.apply2(i1, this.i) != Boolean.FALSE) {
                pregexp.pregexpError$V(new Object[]{pregexp.Lit101, pregexp.Lit110});
            }
            return this.staticLink.lambda8loupP(AddOp.$Pl.apply2(this.k, pregexp.Lit8), i1);
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

        public Object lambda24loupQ(Object k, Object i) {
            frame5 frame5Var = new frame5();
            frame5Var.staticLink = this;
            frame5Var.k = k;
            frame5Var.i = i;
            frame5Var.fk = frame5Var.fk;
            if (this.q == Boolean.FALSE ? this.q != Boolean.FALSE : Scheme.numGEq.apply2(frame5Var.k, this.q) != Boolean.FALSE) {
                return frame5Var.lambda26fk();
            }
            if (this.staticLink.maximal$Qu) {
                return this.staticLink.staticLink.lambda3sub(this.staticLink.re, frame5Var.i, frame5Var.lambda$Fn18, frame5Var.fk);
            }
            Object x = frame5Var.lambda26fk();
            return x == Boolean.FALSE ? this.staticLink.staticLink.lambda3sub(this.staticLink.re, frame5Var.i, frame5Var.lambda$Fn19, frame5Var.fk) : x;
        }
    }

    /* compiled from: pregexp.scm */
    /* loaded from: classes.dex */
    public class frame5 extends ModuleBody {
        Procedure fk;
        Object i;
        Object k;
        final ModuleMethod lambda$Fn18;
        final ModuleMethod lambda$Fn19;
        frame4 staticLink;

        public frame5() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 5, pregexp.Lit111, 0);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:612");
            this.fk = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 6, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:617");
            this.lambda$Fn18 = moduleMethod2;
            ModuleMethod moduleMethod3 = new ModuleMethod(this, 7, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/pregexp.scm:628");
            this.lambda$Fn19 = moduleMethod3;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 5 ? lambda26fk() : super.apply0(moduleMethod);
        }

        public Object lambda26fk() {
            return Scheme.applyToArgs.apply2(this.staticLink.staticLink.sk, this.i);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 5) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        Object lambda27(Object i1) {
            if (!this.staticLink.staticLink.could$Mnloop$Mninfinitely$Qu ? this.staticLink.staticLink.could$Mnloop$Mninfinitely$Qu : Scheme.numEqu.apply2(i1, this.i) != Boolean.FALSE) {
                pregexp.pregexpError$V(new Object[]{pregexp.Lit101, pregexp.Lit110});
            }
            Object x = this.staticLink.lambda24loupQ(AddOp.$Pl.apply2(this.k, pregexp.Lit8), i1);
            return x != Boolean.FALSE ? x : lambda26fk();
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
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

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 6:
                    return lambda27(obj);
                case 7:
                    return lambda28(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        Object lambda28(Object i1) {
            return this.staticLink.lambda24loupQ(AddOp.$Pl.apply2(this.k, pregexp.Lit8), i1);
        }
    }

    public static Object pregexpReplaceAux(Object str, Object ins, Object n, Object backrefs) {
        Object br;
        Number i = Lit73;
        Object obj = "";
        while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
            try {
                try {
                    char c = strings.stringRef((CharSequence) ins, i.intValue());
                    if (characters.isChar$Eq(Char.make(c), Lit19)) {
                        Object br$Mni = pregexpReadEscapedNumber(ins, i, n);
                        if (br$Mni != Boolean.FALSE) {
                            br = lists.car.apply1(br$Mni);
                        } else {
                            try {
                                CharSequence charSequence = (CharSequence) ins;
                                Object apply2 = AddOp.$Pl.apply2(i, Lit8);
                                try {
                                    br = characters.isChar$Eq(Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue())), Lit113) ? Lit73 : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, apply2);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, ins);
                            }
                        }
                        if (br$Mni != Boolean.FALSE) {
                            i = lists.cadr.apply1(br$Mni);
                        } else {
                            i = br != Boolean.FALSE ? AddOp.$Pl.apply2(i, Lit16) : AddOp.$Pl.apply2(i, Lit8);
                        }
                        if (br == Boolean.FALSE) {
                            try {
                                try {
                                    char c2 = strings.stringRef((CharSequence) ins, ((Number) i).intValue());
                                    i = AddOp.$Pl.apply2(i, Lit8);
                                    if (!characters.isChar$Eq(Char.make(c2), Lit11)) {
                                        obj = strings.stringAppend(obj, strings.$make$string$(Char.make(c2)));
                                    }
                                } catch (ClassCastException e3) {
                                    throw new WrongType(e3, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "string-ref", 1, ins);
                            }
                        } else {
                            Object backref = pregexpListRef(backrefs, br);
                            if (backref != Boolean.FALSE) {
                                Object[] objArr = new Object[2];
                                objArr[0] = obj;
                                try {
                                    CharSequence charSequence2 = (CharSequence) str;
                                    Object apply1 = lists.car.apply1(backref);
                                    try {
                                        int intValue = ((Number) apply1).intValue();
                                        Object apply12 = lists.cdr.apply1(backref);
                                        try {
                                            objArr[1] = strings.substring(charSequence2, intValue, ((Number) apply12).intValue());
                                            obj = strings.stringAppend(objArr);
                                        } catch (ClassCastException e5) {
                                            throw new WrongType(e5, "substring", 3, apply12);
                                        }
                                    } catch (ClassCastException e6) {
                                        throw new WrongType(e6, "substring", 2, apply1);
                                    }
                                } catch (ClassCastException e7) {
                                    throw new WrongType(e7, "substring", 1, str);
                                }
                            } else {
                                continue;
                            }
                        }
                    } else {
                        i = AddOp.$Pl.apply2(i, Lit8);
                        obj = strings.stringAppend(obj, strings.$make$string$(Char.make(c)));
                    }
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "string-ref", 2, i);
                }
            } catch (ClassCastException e9) {
                throw new WrongType(e9, "string-ref", 1, ins);
            }
        }
        return obj;
    }

    @Override // gnu.expr.ModuleBody
    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        return moduleMethod.selector == 43 ? pregexpReplaceAux(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
    }

    @Override // gnu.expr.ModuleBody
    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector == 43) {
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.value4 = obj4;
            callContext.proc = moduleMethod;
            callContext.pc = 4;
            return 0;
        }
        return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
    }

    public static Pair pregexp(Object s) {
        $Stpregexp$Mnspace$Mnsensitive$Qu$St = Boolean.TRUE;
        try {
            return LList.list2(Lit100, lists.car.apply1(pregexpReadPattern(s, Lit73, Integer.valueOf(strings.stringLength((CharSequence) s)))));
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, s);
        }
    }

    public static Object pregexpMatchPositions$V(Object pat, Object str, Object[] argsArray) {
        Object start;
        LList opt$Mnargs = LList.makeList(argsArray, 0);
        if (strings.isString(pat)) {
            pat = pregexp(pat);
        } else if (!lists.isPair(pat)) {
            pregexpError$V(new Object[]{Lit114, Lit115, pat});
        }
        try {
            int str$Mnlen = strings.stringLength((CharSequence) str);
            if (lists.isNull(opt$Mnargs)) {
                start = Lit73;
            } else {
                start = lists.car.apply1(opt$Mnargs);
                Object apply1 = lists.cdr.apply1(opt$Mnargs);
                try {
                    opt$Mnargs = (LList) apply1;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "opt-args", -2, apply1);
                }
            }
            Object end = lists.isNull(opt$Mnargs) ? Integer.valueOf(str$Mnlen) : lists.car.apply1(opt$Mnargs);
            Object i = start;
            while (true) {
                Object apply2 = Scheme.numLEq.apply2(i, end);
                try {
                    boolean x = ((Boolean) apply2).booleanValue();
                    if (!x) {
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    }
                    Object x2 = pregexpMatchPositionsAux(pat, str, Integer.valueOf(str$Mnlen), start, end, i);
                    if (x2 != Boolean.FALSE) {
                        return x2;
                    }
                    i = AddOp.$Pl.apply2(i, Lit8);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "x", -2, apply2);
                }
            }
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string-length", 1, str);
        }
    }

    public static Object pregexpMatch$V(Object pat, Object str, Object[] argsArray) {
        LList opt$Mnargs = LList.makeList(argsArray, 0);
        Object ix$Mnprs = Scheme.apply.apply4(pregexp$Mnmatch$Mnpositions, pat, str, opt$Mnargs);
        if (ix$Mnprs == Boolean.FALSE) {
            return ix$Mnprs;
        }
        Object obj = LList.Empty;
        Object arg0 = ix$Mnprs;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                Object ix$Mnpr = arg02.getCar();
                if (ix$Mnpr != Boolean.FALSE) {
                    try {
                        CharSequence charSequence = (CharSequence) str;
                        Object apply1 = lists.car.apply1(ix$Mnpr);
                        try {
                            int intValue = ((Number) apply1).intValue();
                            Object apply12 = lists.cdr.apply1(ix$Mnpr);
                            try {
                                ix$Mnpr = strings.substring(charSequence, intValue, ((Number) apply12).intValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 3, apply12);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 2, apply1);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "substring", 1, str);
                    }
                }
                obj = Pair.make(ix$Mnpr, obj);
                arg0 = arg03;
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "arg0", -2, arg0);
            }
        }
        return LList.reverseInPlace(obj);
    }

    @Override // gnu.expr.ModuleBody
    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 17:
                return pregexpError$V(objArr);
            case 30:
                return pregexpStringMatch(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 42:
                return pregexpMatchPositionsAux(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 45:
                Object obj = objArr[0];
                Object obj2 = objArr[1];
                int length = objArr.length - 2;
                Object[] objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return pregexpMatchPositions$V(obj, obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 46:
                Object obj3 = objArr[0];
                Object obj4 = objArr[1];
                int length2 = objArr.length - 2;
                Object[] objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return pregexpMatch$V(obj3, obj4, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 2];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Integer] */
    public static Object pregexpSplit(Object pat, Object str) {
        try {
            int n = strings.stringLength((CharSequence) str);
            IntNum intNum = Lit73;
            LList lList = LList.Empty;
            Boolean bool = Boolean.FALSE;
            while (Scheme.numGEq.apply2(intNum, Integer.valueOf(n)) == Boolean.FALSE) {
                Object temp = pregexpMatchPositions$V(pat, str, new Object[]{intNum, Integer.valueOf(n)});
                if (temp == Boolean.FALSE) {
                    ?? valueOf = Integer.valueOf(n);
                    try {
                        try {
                            lList = lists.cons(strings.substring((CharSequence) str, intNum.intValue(), n), lList);
                            bool = Boolean.FALSE;
                            intNum = valueOf;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "substring", 2, intNum);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "substring", 1, str);
                    }
                } else {
                    Object jk = lists.car.apply1(temp);
                    Object j = lists.car.apply1(jk);
                    Object k = lists.cdr.apply1(jk);
                    if (Scheme.numEqu.apply2(j, k) != Boolean.FALSE) {
                        Object i = AddOp.$Pl.apply2(k, Lit8);
                        try {
                            CharSequence charSequence = (CharSequence) str;
                            try {
                                int intValue = intNum.intValue();
                                Object apply2 = AddOp.$Pl.apply2(j, Lit8);
                                try {
                                    lList = lists.cons(strings.substring(charSequence, intValue, ((Number) apply2).intValue()), lList);
                                    bool = Boolean.TRUE;
                                    intNum = i;
                                } catch (ClassCastException e3) {
                                    throw new WrongType(e3, "substring", 3, apply2);
                                }
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "substring", 2, intNum);
                            }
                        } catch (ClassCastException e5) {
                            throw new WrongType(e5, "substring", 1, str);
                        }
                    } else {
                        Object apply22 = Scheme.numEqu.apply2(j, intNum);
                        try {
                            boolean x = ((Boolean) apply22).booleanValue();
                            if (!x) {
                                if (x) {
                                    bool = Boolean.FALSE;
                                    intNum = k;
                                }
                                lList = lists.cons(strings.substring((CharSequence) str, intNum.intValue(), ((Number) j).intValue()), lList);
                                bool = Boolean.FALSE;
                                intNum = k;
                            } else {
                                if (bool != Boolean.FALSE) {
                                    bool = Boolean.FALSE;
                                    intNum = k;
                                }
                                try {
                                    try {
                                        try {
                                            lList = lists.cons(strings.substring((CharSequence) str, intNum.intValue(), ((Number) j).intValue()), lList);
                                            bool = Boolean.FALSE;
                                            intNum = k;
                                        } catch (ClassCastException e6) {
                                            throw new WrongType(e6, "substring", 3, j);
                                        }
                                    } catch (ClassCastException e7) {
                                        throw new WrongType(e7, "substring", 2, intNum);
                                    }
                                } catch (ClassCastException e8) {
                                    throw new WrongType(e8, "substring", 1, str);
                                }
                            }
                        } catch (ClassCastException e9) {
                            throw new WrongType(e9, "x", -2, apply22);
                        }
                    }
                }
            }
            return pregexpReverse$Ex(lList);
        } catch (ClassCastException e10) {
            throw new WrongType(e10, "string-length", 1, str);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 33:
                return isPregexpCheckIfInCharClass(obj, obj2);
            case 34:
                return pregexpListRef(obj, obj2);
            case 47:
                return pregexpSplit(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static Object pregexpReplace(Object pat, Object str, Object ins) {
        try {
            int n = strings.stringLength((CharSequence) str);
            Object pp = pregexpMatchPositions$V(pat, str, new Object[]{Lit73, Integer.valueOf(n)});
            if (pp != Boolean.FALSE) {
                try {
                    int ins$Mnlen = strings.stringLength((CharSequence) ins);
                    Object m$Mni = lists.caar.apply1(pp);
                    Object m$Mnn = lists.cdar.apply1(pp);
                    Object[] objArr = new Object[3];
                    try {
                        try {
                            objArr[0] = strings.substring((CharSequence) str, 0, ((Number) m$Mni).intValue());
                            objArr[1] = pregexpReplaceAux(str, ins, Integer.valueOf(ins$Mnlen), pp);
                            try {
                                try {
                                    objArr[2] = strings.substring((CharSequence) str, ((Number) m$Mnn).intValue(), n);
                                    return strings.stringAppend(objArr);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "substring", 2, m$Mnn);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "substring", 1, str);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "substring", 3, m$Mni);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "substring", 1, str);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-length", 1, ins);
                }
            }
            return str;
        } catch (ClassCastException e6) {
            throw new WrongType(e6, "string-length", 1, str);
        }
    }

    public static Object pregexpReplace$St(Object pat, Object str, Object ins) {
        if (strings.isString(pat)) {
            pat = pregexp(pat);
        }
        try {
            int n = strings.stringLength((CharSequence) str);
            try {
                int ins$Mnlen = strings.stringLength((CharSequence) ins);
                IntNum intNum = Lit73;
                Object obj = "";
                while (Scheme.numGEq.apply2(intNum, Integer.valueOf(n)) == Boolean.FALSE) {
                    Object pp = pregexpMatchPositions$V(pat, str, new Object[]{intNum, Integer.valueOf(n)});
                    if (pp == Boolean.FALSE) {
                        if (Scheme.numEqu.apply2(intNum, Lit73) == Boolean.FALSE) {
                            Object[] objArr = new Object[2];
                            objArr[0] = obj;
                            try {
                                try {
                                    objArr[1] = strings.substring((CharSequence) str, intNum.intValue(), n);
                                    str = strings.stringAppend(objArr);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "substring", 2, intNum);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "substring", 1, str);
                            }
                        }
                        return str;
                    }
                    Object i = lists.cdar.apply1(pp);
                    Object[] objArr2 = new Object[3];
                    objArr2[0] = obj;
                    try {
                        CharSequence charSequence = (CharSequence) str;
                        try {
                            int intValue = intNum.intValue();
                            Object apply1 = lists.caar.apply1(pp);
                            try {
                                objArr2[1] = strings.substring(charSequence, intValue, ((Number) apply1).intValue());
                                objArr2[2] = pregexpReplaceAux(str, ins, Integer.valueOf(ins$Mnlen), pp);
                                obj = strings.stringAppend(objArr2);
                                intNum = i;
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "substring", 3, apply1);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "substring", 2, intNum);
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "substring", 1, str);
                    }
                }
                return obj;
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-length", 1, ins);
            }
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "string-length", 1, str);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 18:
                return pregexpReadPattern(obj, obj2, obj3);
            case 19:
                return pregexpReadBranch(obj, obj2, obj3);
            case 20:
                return pregexpReadPiece(obj, obj2, obj3);
            case 21:
                return pregexpReadEscapedNumber(obj, obj2, obj3);
            case 22:
                return pregexpReadEscapedChar(obj, obj2, obj3);
            case 23:
                return pregexpReadPosixCharClass(obj, obj2, obj3);
            case 24:
                return pregexpReadClusterType(obj, obj2, obj3);
            case 25:
                return pregexpReadSubpattern(obj, obj2, obj3);
            case 26:
                return pregexpWrapQuantifierIfAny(obj, obj2, obj3);
            case 27:
                return pregexpReadNums(obj, obj2, obj3);
            case 28:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
            case 29:
                return pregexpReadCharList(obj, obj2, obj3);
            case 32:
                return isPregexpAtWordBoundary(obj, obj2, obj3);
            case 48:
                return pregexpReplace(obj, obj2, obj3);
            case 49:
                return pregexpReplace$St(obj, obj2, obj3);
        }
    }

    public static Object pregexpQuote(Object s) {
        try {
            Integer valueOf = Integer.valueOf(strings.stringLength((CharSequence) s) - 1);
            LList lList = LList.Empty;
            while (Scheme.numLss.apply2(valueOf, Lit73) == Boolean.FALSE) {
                Object i = AddOp.$Mn.apply2(valueOf, Lit8);
                try {
                    try {
                        char c = strings.stringRef((CharSequence) s, valueOf.intValue());
                        Pair r = lists.memv(Char.make(c), Lit116) != Boolean.FALSE ? lists.cons(Lit19, lists.cons(Char.make(c), lList)) : lists.cons(Char.make(c), lList);
                        lList = r;
                        valueOf = i;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, valueOf);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, s);
                }
            }
            try {
                return strings.list$To$String(lList);
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "list->string", 1, lList);
            }
        } catch (ClassCastException e4) {
            throw new WrongType(e4, "string-length", 1, s);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 16:
                return pregexpReverse$Ex(obj);
            case 28:
                return pregexpInvertCharList(obj);
            case 31:
                return isPregexpCharWord(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 35:
                return pregexpMakeBackrefList(obj);
            case 44:
                return pregexp(obj);
            case 50:
                return pregexpQuote(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}
