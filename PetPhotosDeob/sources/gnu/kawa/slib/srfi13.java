package gnu.kawa.slib;

import androidx.core.view.InputDeviceCompat;
import androidx.core.view.PointerIconCompat;
import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import com.google.appinventor.components.runtime.util.ScreenDensityUtil;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.BitwiseOp;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.LispEscapeFormat;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.functions.NumberCompare;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.Telnet;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.rnrs.unicode;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;
import kawa.standard.call_with_values;

/* compiled from: srfi13.scm */
/* loaded from: classes.dex */
public class srfi13 extends ModuleBody {
    public static final ModuleMethod $Pccheck$Mnbounds;
    public static final ModuleMethod $Pcfinish$Mnstring$Mnconcatenate$Mnreverse;
    public static final ModuleMethod $Pckmp$Mnsearch;
    public static final ModuleMethod $Pcmultispan$Mnrepcopy$Ex;
    public static final ModuleMethod $Pcstring$Mncompare;
    public static final ModuleMethod $Pcstring$Mncompare$Mnci;
    public static final ModuleMethod $Pcstring$Mncopy$Ex;
    public static final ModuleMethod $Pcstring$Mnhash;
    public static final ModuleMethod $Pcstring$Mnmap;
    public static final ModuleMethod $Pcstring$Mnmap$Ex;
    public static final ModuleMethod $Pcstring$Mnprefix$Mnci$Qu;
    public static final ModuleMethod $Pcstring$Mnprefix$Mnlength;
    public static final ModuleMethod $Pcstring$Mnprefix$Mnlength$Mnci;
    public static final ModuleMethod $Pcstring$Mnprefix$Qu;
    public static final ModuleMethod $Pcstring$Mnsuffix$Mnci$Qu;
    public static final ModuleMethod $Pcstring$Mnsuffix$Mnlength;
    public static final ModuleMethod $Pcstring$Mnsuffix$Mnlength$Mnci;
    public static final ModuleMethod $Pcstring$Mnsuffix$Qu;
    public static final ModuleMethod $Pcstring$Mntitlecase$Ex;
    public static final ModuleMethod $Pcsubstring$Slshared;
    public static final srfi13 $instance;
    static final IntNum Lit0;
    static final IntNum Lit1;
    static final IntNum Lit10;
    static final SimpleSymbol Lit11;
    static final Char Lit12;
    static final IntNum Lit13;
    static final SimpleSymbol Lit14;
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit16;
    static final SimpleSymbol Lit17;
    static final SimpleSymbol Lit18;
    static final SimpleSymbol Lit19;
    static final IntNum Lit2;
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
    static final IntNum Lit3;
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
    static final IntNum Lit4;
    static final SimpleSymbol Lit40;
    static final SimpleSymbol Lit41;
    static final SyntaxRules Lit42;
    static final SimpleSymbol Lit43;
    static final SyntaxRules Lit44;
    static final IntNum Lit5;
    static final IntNum Lit6;
    static final IntNum Lit7;
    static final IntNum Lit8;
    static final IntNum Lit9;
    public static final ModuleMethod check$Mnsubstring$Mnspec;
    public static final ModuleMethod kmp$Mnstep;
    static final ModuleMethod lambda$Fn100;
    static final ModuleMethod lambda$Fn105;
    static final ModuleMethod lambda$Fn106;
    static final ModuleMethod lambda$Fn111;
    static final ModuleMethod lambda$Fn116;
    static final ModuleMethod lambda$Fn117;
    static final ModuleMethod lambda$Fn122;
    static final ModuleMethod lambda$Fn123;
    static final ModuleMethod lambda$Fn128;
    static final ModuleMethod lambda$Fn133;
    static final ModuleMethod lambda$Fn138;
    static final ModuleMethod lambda$Fn163;
    static final ModuleMethod lambda$Fn166;
    static final ModuleMethod lambda$Fn17;
    static final ModuleMethod lambda$Fn18;
    static final ModuleMethod lambda$Fn210;
    static final ModuleMethod lambda$Fn216;
    static final ModuleMethod lambda$Fn220;
    static final ModuleMethod lambda$Fn27;
    static final ModuleMethod lambda$Fn5;
    static final ModuleMethod lambda$Fn72;
    static final ModuleMethod lambda$Fn73;
    static final ModuleMethod lambda$Fn78;
    static final ModuleMethod lambda$Fn83;
    static final ModuleMethod lambda$Fn84;
    static final ModuleMethod lambda$Fn89;
    static final ModuleMethod lambda$Fn90;
    static final ModuleMethod lambda$Fn95;
    public static final Macro let$Mnstring$Mnstart$Plend;
    public static final Macro let$Mnstring$Mnstart$Plend2;
    static final Location loc$$Cloptional;
    static final Location loc$base;
    static final Location loc$bound;
    static final Location loc$c$Eq;
    static final Location loc$char$Mncased$Qu;
    static final Location loc$char$Mnset;
    static final Location loc$char$Mnset$Mncontains$Qu;
    static final Location loc$char$Mnset$Qu;
    static final Location loc$check$Mnarg;
    static final Location loc$criterion;
    static final Location loc$delim;
    static final Location loc$end;
    static final Location loc$final;
    static final Location loc$grammar;
    static final Location loc$let$Mnoptionals$St;
    static final Location loc$make$Mnfinal;
    static final Location loc$p$Mnstart;
    static final Location loc$rest;
    static final Location loc$s$Mnend;
    static final Location loc$s$Mnstart;
    static final Location loc$start;
    static final Location loc$token$Mnchars;
    public static final ModuleMethod make$Mnkmp$Mnrestart$Mnvector;
    public static final ModuleMethod reverse$Mnlist$Mn$Grstring;
    public static final ModuleMethod string$Eq;
    public static final ModuleMethod string$Gr;
    public static final ModuleMethod string$Gr$Eq;
    public static final ModuleMethod string$Ls;
    public static final ModuleMethod string$Ls$Eq;
    public static final ModuleMethod string$Ls$Gr;
    public static final ModuleMethod string$Mn$Grlist;
    public static final ModuleMethod string$Mnany;
    public static final ModuleMethod string$Mnappend$Slshared;
    public static final ModuleMethod string$Mnci$Eq;
    public static final ModuleMethod string$Mnci$Gr;
    public static final ModuleMethod string$Mnci$Gr$Eq;
    public static final ModuleMethod string$Mnci$Ls;
    public static final ModuleMethod string$Mnci$Ls$Eq;
    public static final ModuleMethod string$Mnci$Ls$Gr;
    public static final ModuleMethod string$Mncompare;
    public static final ModuleMethod string$Mncompare$Mnci;
    public static final ModuleMethod string$Mnconcatenate;
    public static final ModuleMethod string$Mnconcatenate$Mnreverse;
    public static final ModuleMethod string$Mnconcatenate$Mnreverse$Slshared;
    public static final ModuleMethod string$Mnconcatenate$Slshared;
    public static final ModuleMethod string$Mncontains;
    public static final ModuleMethod string$Mncontains$Mnci;
    public static final ModuleMethod string$Mncopy;
    public static final ModuleMethod string$Mncopy$Ex;
    public static final ModuleMethod string$Mncount;
    public static final ModuleMethod string$Mndelete;
    public static final ModuleMethod string$Mndowncase;
    public static final ModuleMethod string$Mndowncase$Ex;
    public static final ModuleMethod string$Mndrop;
    public static final ModuleMethod string$Mndrop$Mnright;
    public static final ModuleMethod string$Mnevery;
    public static final ModuleMethod string$Mnfill$Ex;
    public static final ModuleMethod string$Mnfilter;
    public static final ModuleMethod string$Mnfold;
    public static final ModuleMethod string$Mnfold$Mnright;
    public static final ModuleMethod string$Mnfor$Mneach;
    public static final ModuleMethod string$Mnfor$Mneach$Mnindex;
    public static final ModuleMethod string$Mnhash;
    public static final ModuleMethod string$Mnhash$Mnci;
    public static final ModuleMethod string$Mnindex;
    public static final ModuleMethod string$Mnindex$Mnright;
    public static final ModuleMethod string$Mnjoin;
    public static final ModuleMethod string$Mnkmp$Mnpartial$Mnsearch;
    public static final ModuleMethod string$Mnmap;
    public static final ModuleMethod string$Mnmap$Ex;
    public static final ModuleMethod string$Mnnull$Qu;
    public static final ModuleMethod string$Mnpad;
    public static final ModuleMethod string$Mnpad$Mnright;
    public static final ModuleMethod string$Mnparse$Mnfinal$Mnstart$Plend;
    public static final ModuleMethod string$Mnparse$Mnstart$Plend;
    public static final ModuleMethod string$Mnprefix$Mnci$Qu;
    public static final ModuleMethod string$Mnprefix$Mnlength;
    public static final ModuleMethod string$Mnprefix$Mnlength$Mnci;
    public static final ModuleMethod string$Mnprefix$Qu;
    public static final ModuleMethod string$Mnreplace;
    public static final ModuleMethod string$Mnreverse;
    public static final ModuleMethod string$Mnreverse$Ex;
    public static final ModuleMethod string$Mnskip;
    public static final ModuleMethod string$Mnskip$Mnright;
    public static final ModuleMethod string$Mnsuffix$Mnci$Qu;
    public static final ModuleMethod string$Mnsuffix$Mnlength;
    public static final ModuleMethod string$Mnsuffix$Mnlength$Mnci;
    public static final ModuleMethod string$Mnsuffix$Qu;
    public static final ModuleMethod string$Mntabulate;
    public static final ModuleMethod string$Mntake;
    public static final ModuleMethod string$Mntake$Mnright;
    public static final ModuleMethod string$Mntitlecase;
    public static final ModuleMethod string$Mntitlecase$Ex;
    public static final ModuleMethod string$Mntokenize;
    public static final ModuleMethod string$Mntrim;
    public static final ModuleMethod string$Mntrim$Mnboth;
    public static final ModuleMethod string$Mntrim$Mnright;
    public static final ModuleMethod string$Mnunfold;
    public static final ModuleMethod string$Mnunfold$Mnright;
    public static final ModuleMethod string$Mnupcase;
    public static final ModuleMethod string$Mnupcase$Ex;
    public static final ModuleMethod string$Mnxcopy$Ex;
    public static final ModuleMethod substring$Mnspec$Mnok$Qu;
    public static final ModuleMethod substring$Slshared;
    public static final ModuleMethod xsubstring;
    static final SimpleSymbol Lit150 = (SimpleSymbol) new SimpleSymbol("receive").readResolve();
    static final SimpleSymbol Lit149 = (SimpleSymbol) new SimpleSymbol("string-join").readResolve();
    static final SimpleSymbol Lit148 = (SimpleSymbol) new SimpleSymbol("%multispan-repcopy!").readResolve();
    static final SimpleSymbol Lit147 = (SimpleSymbol) new SimpleSymbol("string-xcopy!").readResolve();
    static final SimpleSymbol Lit146 = (SimpleSymbol) new SimpleSymbol("xsubstring").readResolve();
    static final SimpleSymbol Lit145 = (SimpleSymbol) new SimpleSymbol("string-tokenize").readResolve();
    static final SimpleSymbol Lit144 = (SimpleSymbol) new SimpleSymbol("string-replace").readResolve();
    static final SimpleSymbol Lit143 = (SimpleSymbol) new SimpleSymbol("%finish-string-concatenate-reverse").readResolve();
    static final SimpleSymbol Lit142 = (SimpleSymbol) new SimpleSymbol("string-concatenate-reverse/shared").readResolve();
    static final SimpleSymbol Lit141 = (SimpleSymbol) new SimpleSymbol("string-concatenate-reverse").readResolve();
    static final SimpleSymbol Lit140 = (SimpleSymbol) new SimpleSymbol("string-concatenate").readResolve();
    static final SimpleSymbol Lit139 = (SimpleSymbol) new SimpleSymbol("string-concatenate/shared").readResolve();
    static final SimpleSymbol Lit138 = (SimpleSymbol) new SimpleSymbol("string-append/shared").readResolve();
    static final SimpleSymbol Lit137 = (SimpleSymbol) new SimpleSymbol("string->list").readResolve();
    static final SimpleSymbol Lit136 = (SimpleSymbol) new SimpleSymbol("reverse-list->string").readResolve();
    static final SimpleSymbol Lit135 = (SimpleSymbol) new SimpleSymbol("string-reverse!").readResolve();
    static final SimpleSymbol Lit134 = (SimpleSymbol) new SimpleSymbol("string-reverse").readResolve();
    static final SimpleSymbol Lit133 = (SimpleSymbol) new SimpleSymbol("string-null?").readResolve();
    static final SimpleSymbol Lit132 = (SimpleSymbol) new SimpleSymbol("string-kmp-partial-search").readResolve();
    static final SimpleSymbol Lit131 = (SimpleSymbol) new SimpleSymbol("kmp-step").readResolve();
    static final SimpleSymbol Lit130 = (SimpleSymbol) new SimpleSymbol("make-kmp-restart-vector").readResolve();
    static final SimpleSymbol Lit129 = (SimpleSymbol) new SimpleSymbol("%kmp-search").readResolve();
    static final SimpleSymbol Lit128 = (SimpleSymbol) new SimpleSymbol("string-contains-ci").readResolve();
    static final SimpleSymbol Lit127 = (SimpleSymbol) new SimpleSymbol("string-contains").readResolve();
    static final SimpleSymbol Lit126 = (SimpleSymbol) new SimpleSymbol("%string-copy!").readResolve();
    static final SimpleSymbol Lit125 = (SimpleSymbol) new SimpleSymbol("string-copy!").readResolve();
    static final SimpleSymbol Lit124 = (SimpleSymbol) new SimpleSymbol("string-fill!").readResolve();
    static final SimpleSymbol Lit123 = (SimpleSymbol) new SimpleSymbol("string-count").readResolve();
    static final SimpleSymbol Lit122 = (SimpleSymbol) new SimpleSymbol("string-skip-right").readResolve();
    static final SimpleSymbol Lit121 = (SimpleSymbol) new SimpleSymbol("string-skip").readResolve();
    static final SimpleSymbol Lit120 = (SimpleSymbol) new SimpleSymbol("string-index-right").readResolve();
    static final SimpleSymbol Lit119 = (SimpleSymbol) new SimpleSymbol("string-index").readResolve();
    static final SimpleSymbol Lit118 = (SimpleSymbol) new SimpleSymbol("string-filter").readResolve();
    static final SimpleSymbol Lit117 = (SimpleSymbol) new SimpleSymbol("string-delete").readResolve();
    static final SimpleSymbol Lit116 = (SimpleSymbol) new SimpleSymbol("string-pad").readResolve();
    static final SimpleSymbol Lit115 = (SimpleSymbol) new SimpleSymbol("string-pad-right").readResolve();
    static final SimpleSymbol Lit114 = (SimpleSymbol) new SimpleSymbol("string-trim-both").readResolve();
    static final SimpleSymbol Lit113 = (SimpleSymbol) new SimpleSymbol("string-trim-right").readResolve();
    static final SimpleSymbol Lit112 = (SimpleSymbol) new SimpleSymbol("string-trim").readResolve();
    static final SimpleSymbol Lit111 = (SimpleSymbol) new SimpleSymbol("string-drop-right").readResolve();
    static final SimpleSymbol Lit110 = (SimpleSymbol) new SimpleSymbol("string-drop").readResolve();
    static final SimpleSymbol Lit109 = (SimpleSymbol) new SimpleSymbol("string-take-right").readResolve();
    static final SimpleSymbol Lit108 = (SimpleSymbol) new SimpleSymbol("string-take").readResolve();
    static final SimpleSymbol Lit107 = (SimpleSymbol) new SimpleSymbol("string-titlecase").readResolve();
    static final SimpleSymbol Lit106 = (SimpleSymbol) new SimpleSymbol("string-titlecase!").readResolve();
    static final SimpleSymbol Lit105 = (SimpleSymbol) new SimpleSymbol("%string-titlecase!").readResolve();
    static final SimpleSymbol Lit104 = (SimpleSymbol) new SimpleSymbol("string-downcase!").readResolve();
    static final SimpleSymbol Lit103 = (SimpleSymbol) new SimpleSymbol("string-downcase").readResolve();
    static final SimpleSymbol Lit102 = (SimpleSymbol) new SimpleSymbol("string-upcase!").readResolve();
    static final SimpleSymbol Lit101 = (SimpleSymbol) new SimpleSymbol("string-upcase").readResolve();
    static final SimpleSymbol Lit100 = (SimpleSymbol) new SimpleSymbol("string-hash-ci").readResolve();
    static final SimpleSymbol Lit99 = (SimpleSymbol) new SimpleSymbol("string-hash").readResolve();
    static final SimpleSymbol Lit98 = (SimpleSymbol) new SimpleSymbol("%string-hash").readResolve();
    static final SimpleSymbol Lit97 = (SimpleSymbol) new SimpleSymbol("string-ci>=").readResolve();
    static final SimpleSymbol Lit96 = (SimpleSymbol) new SimpleSymbol("string-ci<=").readResolve();
    static final SimpleSymbol Lit95 = (SimpleSymbol) new SimpleSymbol("string-ci>").readResolve();
    static final SimpleSymbol Lit94 = (SimpleSymbol) new SimpleSymbol("string-ci<").readResolve();
    static final SimpleSymbol Lit93 = (SimpleSymbol) new SimpleSymbol("string-ci<>").readResolve();
    static final SimpleSymbol Lit92 = (SimpleSymbol) new SimpleSymbol("string-ci=").readResolve();
    static final SimpleSymbol Lit91 = (SimpleSymbol) new SimpleSymbol("string>=").readResolve();
    static final SimpleSymbol Lit90 = (SimpleSymbol) new SimpleSymbol("string<=").readResolve();
    static final SimpleSymbol Lit89 = (SimpleSymbol) new SimpleSymbol("string>").readResolve();
    static final SimpleSymbol Lit88 = (SimpleSymbol) new SimpleSymbol("string<").readResolve();
    static final SimpleSymbol Lit87 = (SimpleSymbol) new SimpleSymbol("string<>").readResolve();
    static final SimpleSymbol Lit86 = (SimpleSymbol) new SimpleSymbol("string=").readResolve();
    static final SimpleSymbol Lit85 = (SimpleSymbol) new SimpleSymbol("string-compare-ci").readResolve();
    static final SimpleSymbol Lit84 = (SimpleSymbol) new SimpleSymbol("string-compare").readResolve();
    static final SimpleSymbol Lit83 = (SimpleSymbol) new SimpleSymbol("%string-compare-ci").readResolve();
    static final SimpleSymbol Lit82 = (SimpleSymbol) new SimpleSymbol("%string-compare").readResolve();
    static final SimpleSymbol Lit81 = (SimpleSymbol) new SimpleSymbol("%string-suffix-ci?").readResolve();
    static final SimpleSymbol Lit80 = (SimpleSymbol) new SimpleSymbol("%string-prefix-ci?").readResolve();
    static final SimpleSymbol Lit79 = (SimpleSymbol) new SimpleSymbol("%string-suffix?").readResolve();
    static final SimpleSymbol Lit78 = (SimpleSymbol) new SimpleSymbol("%string-prefix?").readResolve();
    static final SimpleSymbol Lit77 = (SimpleSymbol) new SimpleSymbol("string-suffix-ci?").readResolve();
    static final SimpleSymbol Lit76 = (SimpleSymbol) new SimpleSymbol("string-prefix-ci?").readResolve();
    static final SimpleSymbol Lit75 = (SimpleSymbol) new SimpleSymbol("string-suffix?").readResolve();
    static final SimpleSymbol Lit74 = (SimpleSymbol) new SimpleSymbol("string-prefix?").readResolve();
    static final SimpleSymbol Lit73 = (SimpleSymbol) new SimpleSymbol("string-suffix-length-ci").readResolve();
    static final SimpleSymbol Lit72 = (SimpleSymbol) new SimpleSymbol("string-prefix-length-ci").readResolve();
    static final SimpleSymbol Lit71 = (SimpleSymbol) new SimpleSymbol("string-suffix-length").readResolve();
    static final SimpleSymbol Lit70 = (SimpleSymbol) new SimpleSymbol("string-prefix-length").readResolve();
    static final SimpleSymbol Lit69 = (SimpleSymbol) new SimpleSymbol("%string-suffix-length-ci").readResolve();
    static final SimpleSymbol Lit68 = (SimpleSymbol) new SimpleSymbol("%string-prefix-length-ci").readResolve();
    static final SimpleSymbol Lit67 = (SimpleSymbol) new SimpleSymbol("%string-suffix-length").readResolve();
    static final SimpleSymbol Lit66 = (SimpleSymbol) new SimpleSymbol("%string-prefix-length").readResolve();
    static final SimpleSymbol Lit65 = (SimpleSymbol) new SimpleSymbol("string-tabulate").readResolve();
    static final SimpleSymbol Lit64 = (SimpleSymbol) new SimpleSymbol("string-any").readResolve();
    static final SimpleSymbol Lit63 = (SimpleSymbol) new SimpleSymbol("string-every").readResolve();
    static final SimpleSymbol Lit62 = (SimpleSymbol) new SimpleSymbol("string-for-each-index").readResolve();
    static final SimpleSymbol Lit61 = (SimpleSymbol) new SimpleSymbol("string-for-each").readResolve();
    static final SimpleSymbol Lit60 = (SimpleSymbol) new SimpleSymbol("string-unfold-right").readResolve();
    static final SimpleSymbol Lit59 = (SimpleSymbol) new SimpleSymbol("string-unfold").readResolve();
    static final SimpleSymbol Lit58 = (SimpleSymbol) new SimpleSymbol("string-fold-right").readResolve();
    static final SimpleSymbol Lit57 = (SimpleSymbol) new SimpleSymbol("string-fold").readResolve();
    static final SimpleSymbol Lit56 = (SimpleSymbol) new SimpleSymbol("%string-map!").readResolve();
    static final SimpleSymbol Lit55 = (SimpleSymbol) new SimpleSymbol("string-map!").readResolve();
    static final SimpleSymbol Lit54 = (SimpleSymbol) new SimpleSymbol("%string-map").readResolve();
    static final SimpleSymbol Lit53 = (SimpleSymbol) new SimpleSymbol("string-map").readResolve();
    static final SimpleSymbol Lit52 = (SimpleSymbol) new SimpleSymbol("string-copy").readResolve();
    static final SimpleSymbol Lit51 = (SimpleSymbol) new SimpleSymbol("%substring/shared").readResolve();
    static final SimpleSymbol Lit50 = (SimpleSymbol) new SimpleSymbol("substring/shared").readResolve();
    static final SimpleSymbol Lit49 = (SimpleSymbol) new SimpleSymbol("check-substring-spec").readResolve();
    static final SimpleSymbol Lit48 = (SimpleSymbol) new SimpleSymbol("substring-spec-ok?").readResolve();
    static final SimpleSymbol Lit47 = (SimpleSymbol) new SimpleSymbol("string-parse-final-start+end").readResolve();
    static final SimpleSymbol Lit46 = (SimpleSymbol) new SimpleSymbol("%check-bounds").readResolve();
    static final SimpleSymbol Lit45 = (SimpleSymbol) new SimpleSymbol("string-parse-start+end").readResolve();

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame55 extends ModuleBody {
        Object char$Mn$Grint;
    }

    static {
        Object[] objArr = {(SimpleSymbol) new SimpleSymbol("l-s-s+e2").readResolve()};
        SyntaxPattern syntaxPattern = new SyntaxPattern("\f\u0018L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\f/\f7\f?\rG@\b\b", new Object[0], 9);
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("let-string-start+end").readResolve();
        Lit41 = simpleSymbol;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("rest").readResolve();
        Lit27 = simpleSymbol2;
        Lit44 = new SyntaxRules(objArr, new SyntaxRule[]{new SyntaxRule(syntaxPattern, "\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003", "\u0011\u0018\u00041\b\u0011\u0018\f\b#\b\u0011\u0018\u00141\t\u0003\t\u000b\u0018\u001c\u0011\u0018\f\t+\t;\b\u0011\u0018\u0014!\t\u0013\b\u001b\u0011\u0018\f\t3\u0011\u0018$\bEC", new Object[]{(SimpleSymbol) new SimpleSymbol("let").readResolve(), (SimpleSymbol) new SimpleSymbol("procv").readResolve(), simpleSymbol, PairWithPosition.make(simpleSymbol2, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 553003), Lit27}, 1)}, 9);
        Lit43 = (SimpleSymbol) new SimpleSymbol("let-string-start+end2").readResolve();
        Lit42 = new SyntaxRules(new Object[]{Lit41}, new SyntaxRule[]{new SyntaxRule(new SyntaxPattern("\f\u0018,\f\u0007\f\u000f\b\f\u0017\f\u001f\f'\r/(\b\b", new Object[0], 6), "\u0001\u0001\u0001\u0001\u0001\u0003", "\u0011\u0018\u0004!\t\u0003\b\u000bI\u0011\u0018\f\t\u0013\t\u001b\b#\b-+", new Object[]{Lit150, Lit47}, 1), new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0007\f\u000f\f\u0017\b\f\u001f\f'\f/\r70\b\b", new Object[0], 7), "\u0001\u0001\u0001\u0001\u0001\u0001\u0003", "\u0011\u0018\u00041\t\u0013\t\u0003\b\u000bI\u0011\u0018\f\t\u001b\t#\b+\b53", new Object[]{Lit150, Lit45}, 1)}, 7);
        Lit40 = (SimpleSymbol) new SimpleSymbol("grammar").readResolve();
        Lit39 = (SimpleSymbol) new SimpleSymbol("delim").readResolve();
        Lit38 = (SimpleSymbol) new SimpleSymbol("token-chars").readResolve();
        Lit37 = (SimpleSymbol) new SimpleSymbol("final").readResolve();
        Lit36 = (SimpleSymbol) new SimpleSymbol("s-end").readResolve();
        Lit35 = (SimpleSymbol) new SimpleSymbol("s-start").readResolve();
        Lit34 = (SimpleSymbol) new SimpleSymbol("p-start").readResolve();
        Lit33 = (SimpleSymbol) new SimpleSymbol("end").readResolve();
        Lit32 = (SimpleSymbol) new SimpleSymbol("start").readResolve();
        Lit31 = (SimpleSymbol) new SimpleSymbol("c=").readResolve();
        Lit30 = (SimpleSymbol) new SimpleSymbol("char-set").readResolve();
        Lit29 = (SimpleSymbol) new SimpleSymbol("criterion").readResolve();
        Lit28 = (SimpleSymbol) new SimpleSymbol("char-cased?").readResolve();
        Lit26 = (SimpleSymbol) new SimpleSymbol("bound").readResolve();
        Lit25 = (SimpleSymbol) new SimpleSymbol("char-set-contains?").readResolve();
        Lit24 = (SimpleSymbol) new SimpleSymbol("char-set?").readResolve();
        Lit23 = (SimpleSymbol) new SimpleSymbol("make-final").readResolve();
        Lit22 = (SimpleSymbol) new SimpleSymbol("base").readResolve();
        Lit21 = (SimpleSymbol) new SimpleSymbol("let-optionals*").readResolve();
        Lit20 = (SimpleSymbol) new SimpleSymbol(":optional").readResolve();
        Lit19 = (SimpleSymbol) new SimpleSymbol("check-arg").readResolve();
        Lit18 = (SimpleSymbol) new SimpleSymbol("suffix").readResolve();
        Lit17 = (SimpleSymbol) new SimpleSymbol("prefix").readResolve();
        Lit16 = (SimpleSymbol) new SimpleSymbol("strict-infix").readResolve();
        Lit15 = (SimpleSymbol) new SimpleSymbol("infix").readResolve();
        Lit14 = (SimpleSymbol) new SimpleSymbol("graphic").readResolve();
        Lit13 = IntNum.make(-1);
        Lit12 = Char.make(32);
        Lit11 = (SimpleSymbol) new SimpleSymbol("whitespace").readResolve();
        Lit10 = IntNum.make(4194304);
        Lit9 = IntNum.make(4194304);
        Lit8 = IntNum.make(4194304);
        Lit7 = IntNum.make(4194304);
        Lit6 = IntNum.make(37);
        Lit5 = IntNum.make(65536);
        Lit4 = IntNum.make(4096);
        Lit3 = IntNum.make(40);
        Lit2 = IntNum.make(4096);
        Lit1 = IntNum.make(1);
        Lit0 = IntNum.make(0);
        $instance = new srfi13();
        loc$check$Mnarg = ThreadLocation.getInstance(Lit19, null);
        loc$$Cloptional = ThreadLocation.getInstance(Lit20, null);
        loc$let$Mnoptionals$St = ThreadLocation.getInstance(Lit21, null);
        loc$base = ThreadLocation.getInstance(Lit22, null);
        loc$make$Mnfinal = ThreadLocation.getInstance(Lit23, null);
        loc$char$Mnset$Qu = ThreadLocation.getInstance(Lit24, null);
        loc$char$Mnset$Mncontains$Qu = ThreadLocation.getInstance(Lit25, null);
        loc$bound = ThreadLocation.getInstance(Lit26, null);
        loc$rest = ThreadLocation.getInstance(Lit27, null);
        loc$char$Mncased$Qu = ThreadLocation.getInstance(Lit28, null);
        loc$criterion = ThreadLocation.getInstance(Lit29, null);
        loc$char$Mnset = ThreadLocation.getInstance(Lit30, null);
        loc$c$Eq = ThreadLocation.getInstance(Lit31, null);
        loc$start = ThreadLocation.getInstance(Lit32, null);
        loc$end = ThreadLocation.getInstance(Lit33, null);
        loc$p$Mnstart = ThreadLocation.getInstance(Lit34, null);
        loc$s$Mnstart = ThreadLocation.getInstance(Lit35, null);
        loc$s$Mnend = ThreadLocation.getInstance(Lit36, null);
        loc$final = ThreadLocation.getInstance(Lit37, null);
        loc$token$Mnchars = ThreadLocation.getInstance(Lit38, null);
        loc$delim = ThreadLocation.getInstance(Lit39, null);
        loc$grammar = ThreadLocation.getInstance(Lit40, null);
        let$Mnstring$Mnstart$Plend = Macro.make(Lit41, Lit42, $instance);
        let$Mnstring$Mnstart$Plend2 = Macro.make(Lit43, Lit44, $instance);
        srfi13 srfi13Var = $instance;
        string$Mnparse$Mnstart$Plend = new ModuleMethod(srfi13Var, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SOURCE, Lit45, 12291);
        $Pccheck$Mnbounds = new ModuleMethod(srfi13Var, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_FULLSCREEN, Lit46, 16388);
        string$Mnparse$Mnfinal$Mnstart$Plend = new ModuleMethod(srfi13Var, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION, Lit47, 12291);
        substring$Mnspec$Mnok$Qu = new ModuleMethod(srfi13Var, 197, Lit48, 12291);
        check$Mnsubstring$Mnspec = new ModuleMethod(srfi13Var, 198, Lit49, 16388);
        ModuleMethod moduleMethod = new ModuleMethod(srfi13Var, 199, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:223");
        lambda$Fn5 = moduleMethod;
        substring$Slshared = new ModuleMethod(srfi13Var, HttpRequestContext.HTTP_OK, Lit50, -4094);
        $Pcsubstring$Slshared = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_CAMERA_NO_IMAGE_RETURNED, Lit51, 12291);
        string$Mncopy = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_NO_CAMERA_PERMISSION, Lit52, -4095);
        string$Mnmap = new ModuleMethod(srfi13Var, 203, Lit53, -4094);
        $Pcstring$Mnmap = new ModuleMethod(srfi13Var, 204, Lit54, 16388);
        string$Mnmap$Ex = new ModuleMethod(srfi13Var, 205, Lit55, -4094);
        $Pcstring$Mnmap$Ex = new ModuleMethod(srfi13Var, 206, Lit56, 16388);
        string$Mnfold = new ModuleMethod(srfi13Var, 207, Lit57, -4093);
        string$Mnfold$Mnright = new ModuleMethod(srfi13Var, 208, Lit58, -4093);
        ModuleMethod moduleMethod2 = new ModuleMethod(srfi13Var, 209, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:377");
        lambda$Fn17 = moduleMethod2;
        string$Mnunfold = new ModuleMethod(srfi13Var, 210, Lit59, -4092);
        ModuleMethod moduleMethod3 = new ModuleMethod(srfi13Var, 211, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:422");
        lambda$Fn18 = moduleMethod3;
        string$Mnunfold$Mnright = new ModuleMethod(srfi13Var, 212, Lit60, -4092);
        string$Mnfor$Mneach = new ModuleMethod(srfi13Var, 213, Lit61, -4094);
        string$Mnfor$Mneach$Mnindex = new ModuleMethod(srfi13Var, 214, Lit62, -4094);
        string$Mnevery = new ModuleMethod(srfi13Var, 215, Lit63, -4094);
        string$Mnany = new ModuleMethod(srfi13Var, 216, Lit64, -4094);
        ModuleMethod moduleMethod4 = new ModuleMethod(srfi13Var, 217, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:535");
        lambda$Fn27 = moduleMethod4;
        string$Mntabulate = new ModuleMethod(srfi13Var, 218, Lit65, 8194);
        $Pcstring$Mnprefix$Mnlength = new ModuleMethod(srfi13Var, 219, Lit66, 24582);
        $Pcstring$Mnsuffix$Mnlength = new ModuleMethod(srfi13Var, 220, Lit67, 24582);
        $Pcstring$Mnprefix$Mnlength$Mnci = new ModuleMethod(srfi13Var, 221, Lit68, 24582);
        $Pcstring$Mnsuffix$Mnlength$Mnci = new ModuleMethod(srfi13Var, 222, Lit69, 24582);
        string$Mnprefix$Mnlength = new ModuleMethod(srfi13Var, YaVersion.YOUNG_ANDROID_VERSION, Lit70, -4094);
        string$Mnsuffix$Mnlength = new ModuleMethod(srfi13Var, 224, Lit71, -4094);
        string$Mnprefix$Mnlength$Mnci = new ModuleMethod(srfi13Var, 225, Lit72, -4094);
        string$Mnsuffix$Mnlength$Mnci = new ModuleMethod(srfi13Var, 226, Lit73, -4094);
        string$Mnprefix$Qu = new ModuleMethod(srfi13Var, 227, Lit74, -4094);
        string$Mnsuffix$Qu = new ModuleMethod(srfi13Var, 228, Lit75, -4094);
        string$Mnprefix$Mnci$Qu = new ModuleMethod(srfi13Var, 229, Lit76, -4094);
        string$Mnsuffix$Mnci$Qu = new ModuleMethod(srfi13Var, 230, Lit77, -4094);
        $Pcstring$Mnprefix$Qu = new ModuleMethod(srfi13Var, 231, Lit78, 24582);
        $Pcstring$Mnsuffix$Qu = new ModuleMethod(srfi13Var, 232, Lit79, 24582);
        $Pcstring$Mnprefix$Mnci$Qu = new ModuleMethod(srfi13Var, 233, Lit80, 24582);
        $Pcstring$Mnsuffix$Mnci$Qu = new ModuleMethod(srfi13Var, 234, Lit81, 24582);
        $Pcstring$Mncompare = new ModuleMethod(srfi13Var, 235, Lit82, 36873);
        $Pcstring$Mncompare$Mnci = new ModuleMethod(srfi13Var, 236, Lit83, 36873);
        string$Mncompare = new ModuleMethod(srfi13Var, 237, Lit84, -4091);
        string$Mncompare$Mnci = new ModuleMethod(srfi13Var, 238, Lit85, -4091);
        ModuleMethod moduleMethod5 = new ModuleMethod(srfi13Var, 239, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod5.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:756");
        lambda$Fn72 = moduleMethod5;
        ModuleMethod moduleMethod6 = new ModuleMethod(srfi13Var, 240, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod6.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:758");
        lambda$Fn73 = moduleMethod6;
        string$Eq = new ModuleMethod(srfi13Var, LispEscapeFormat.ESCAPE_NORMAL, Lit86, -4094);
        ModuleMethod moduleMethod7 = new ModuleMethod(srfi13Var, LispEscapeFormat.ESCAPE_ALL, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod7.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:767");
        lambda$Fn78 = moduleMethod7;
        string$Ls$Gr = new ModuleMethod(srfi13Var, 243, Lit87, -4094);
        ModuleMethod moduleMethod8 = new ModuleMethod(srfi13Var, 244, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod8.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:778");
        lambda$Fn83 = moduleMethod8;
        ModuleMethod moduleMethod9 = new ModuleMethod(srfi13Var, 245, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod9.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:779");
        lambda$Fn84 = moduleMethod9;
        string$Ls = new ModuleMethod(srfi13Var, 246, Lit88, -4094);
        ModuleMethod moduleMethod10 = new ModuleMethod(srfi13Var, 247, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod10.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:788");
        lambda$Fn89 = moduleMethod10;
        ModuleMethod moduleMethod11 = new ModuleMethod(srfi13Var, 248, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod11.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:789");
        lambda$Fn90 = moduleMethod11;
        string$Gr = new ModuleMethod(srfi13Var, 249, Lit89, -4094);
        ModuleMethod moduleMethod12 = new ModuleMethod(srfi13Var, 250, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod12.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:801");
        lambda$Fn95 = moduleMethod12;
        string$Ls$Eq = new ModuleMethod(srfi13Var, Telnet.WILL, Lit90, -4094);
        ModuleMethod moduleMethod13 = new ModuleMethod(srfi13Var, Telnet.WONT, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod13.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:810");
        lambda$Fn100 = moduleMethod13;
        string$Gr$Eq = new ModuleMethod(srfi13Var, Telnet.DO, Lit91, -4094);
        ModuleMethod moduleMethod14 = new ModuleMethod(srfi13Var, Telnet.DONT, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod14.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:820");
        lambda$Fn105 = moduleMethod14;
        ModuleMethod moduleMethod15 = new ModuleMethod(srfi13Var, 255, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod15.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:822");
        lambda$Fn106 = moduleMethod15;
        string$Mnci$Eq = new ModuleMethod(srfi13Var, 256, Lit92, -4094);
        ModuleMethod moduleMethod16 = new ModuleMethod(srfi13Var, InputDeviceCompat.SOURCE_KEYBOARD, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod16.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:831");
        lambda$Fn111 = moduleMethod16;
        string$Mnci$Ls$Gr = new ModuleMethod(srfi13Var, 258, Lit93, -4094);
        ModuleMethod moduleMethod17 = new ModuleMethod(srfi13Var, 259, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod17.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:842");
        lambda$Fn116 = moduleMethod17;
        ModuleMethod moduleMethod18 = new ModuleMethod(srfi13Var, 260, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod18.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:843");
        lambda$Fn117 = moduleMethod18;
        string$Mnci$Ls = new ModuleMethod(srfi13Var, 261, Lit94, -4094);
        ModuleMethod moduleMethod19 = new ModuleMethod(srfi13Var, 262, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod19.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:852");
        lambda$Fn122 = moduleMethod19;
        ModuleMethod moduleMethod20 = new ModuleMethod(srfi13Var, 263, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod20.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:853");
        lambda$Fn123 = moduleMethod20;
        string$Mnci$Gr = new ModuleMethod(srfi13Var, 264, Lit95, -4094);
        ModuleMethod moduleMethod21 = new ModuleMethod(srfi13Var, 265, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod21.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:865");
        lambda$Fn128 = moduleMethod21;
        string$Mnci$Ls$Eq = new ModuleMethod(srfi13Var, 266, Lit96, -4094);
        ModuleMethod moduleMethod22 = new ModuleMethod(srfi13Var, 267, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod22.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:874");
        lambda$Fn133 = moduleMethod22;
        string$Mnci$Gr$Eq = new ModuleMethod(srfi13Var, 268, Lit97, -4094);
        $Pcstring$Mnhash = new ModuleMethod(srfi13Var, 269, Lit98, 20485);
        string$Mnhash = new ModuleMethod(srfi13Var, 270, Lit99, -4095);
        ModuleMethod moduleMethod23 = new ModuleMethod(srfi13Var, 271, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod23.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:922");
        lambda$Fn138 = moduleMethod23;
        string$Mnhash$Mnci = new ModuleMethod(srfi13Var, 272, Lit100, -4095);
        string$Mnupcase = new ModuleMethod(srfi13Var, 273, Lit101, -4095);
        string$Mnupcase$Ex = new ModuleMethod(srfi13Var, 274, Lit102, -4095);
        string$Mndowncase = new ModuleMethod(srfi13Var, 275, Lit103, -4095);
        string$Mndowncase$Ex = new ModuleMethod(srfi13Var, 276, Lit104, -4095);
        $Pcstring$Mntitlecase$Ex = new ModuleMethod(srfi13Var, 277, Lit105, 12291);
        string$Mntitlecase$Ex = new ModuleMethod(srfi13Var, 278, Lit106, -4095);
        string$Mntitlecase = new ModuleMethod(srfi13Var, 279, Lit107, -4095);
        string$Mntake = new ModuleMethod(srfi13Var, 280, Lit108, 8194);
        string$Mntake$Mnright = new ModuleMethod(srfi13Var, 281, Lit109, 8194);
        string$Mndrop = new ModuleMethod(srfi13Var, 282, Lit110, 8194);
        string$Mndrop$Mnright = new ModuleMethod(srfi13Var, 283, Lit111, 8194);
        string$Mntrim = new ModuleMethod(srfi13Var, 284, Lit112, -4095);
        string$Mntrim$Mnright = new ModuleMethod(srfi13Var, 285, Lit113, -4095);
        string$Mntrim$Mnboth = new ModuleMethod(srfi13Var, 286, Lit114, -4095);
        ModuleMethod moduleMethod24 = new ModuleMethod(srfi13Var, 287, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod24.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1047");
        lambda$Fn163 = moduleMethod24;
        string$Mnpad$Mnright = new ModuleMethod(srfi13Var, 288, Lit115, -4094);
        ModuleMethod moduleMethod25 = new ModuleMethod(srfi13Var, 289, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod25.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1059");
        lambda$Fn166 = moduleMethod25;
        string$Mnpad = new ModuleMethod(srfi13Var, 290, Lit116, -4094);
        string$Mndelete = new ModuleMethod(srfi13Var, 291, Lit117, -4094);
        string$Mnfilter = new ModuleMethod(srfi13Var, 292, Lit118, -4094);
        string$Mnindex = new ModuleMethod(srfi13Var, 293, Lit119, -4094);
        string$Mnindex$Mnright = new ModuleMethod(srfi13Var, 294, Lit120, -4094);
        string$Mnskip = new ModuleMethod(srfi13Var, 295, Lit121, -4094);
        string$Mnskip$Mnright = new ModuleMethod(srfi13Var, 296, Lit122, -4094);
        string$Mncount = new ModuleMethod(srfi13Var, 297, Lit123, -4094);
        string$Mnfill$Ex = new ModuleMethod(srfi13Var, 298, Lit124, -4094);
        string$Mncopy$Ex = new ModuleMethod(srfi13Var, 299, Lit125, 20483);
        $Pcstring$Mncopy$Ex = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET, Lit126, 20485);
        string$Mncontains = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_EXCEPTION, Lit127, -4094);
        string$Mncontains$Mnci = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN, Lit128, -4094);
        $Pckmp$Mnsearch = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED, Lit129, 28679);
        make$Mnkmp$Mnrestart$Mnvector = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED, Lit130, -4095);
        kmp$Mnstep = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED, Lit131, 24582);
        string$Mnkmp$Mnpartial$Mnsearch = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED, Lit132, -4092);
        string$Mnnull$Qu = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED, Lit133, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnreverse = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED, Lit134, -4095);
        string$Mnreverse$Ex = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED, Lit135, -4095);
        reverse$Mnlist$Mn$Grstring = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED, Lit136, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mn$Grlist = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED, Lit137, -4095);
        string$Mnappend$Slshared = new ModuleMethod(srfi13Var, ErrorMessages.ERROR_TWITTER_SEARCH_FAILED, Lit138, -4096);
        string$Mnconcatenate$Slshared = new ModuleMethod(srfi13Var, 315, Lit139, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnconcatenate = new ModuleMethod(srfi13Var, 316, Lit140, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnconcatenate$Mnreverse = new ModuleMethod(srfi13Var, 317, Lit141, -4095);
        string$Mnconcatenate$Mnreverse$Slshared = new ModuleMethod(srfi13Var, 318, Lit142, -4095);
        $Pcfinish$Mnstring$Mnconcatenate$Mnreverse = new ModuleMethod(srfi13Var, 319, Lit143, 16388);
        string$Mnreplace = new ModuleMethod(srfi13Var, ScreenDensityUtil.DEFAULT_NORMAL_SHORT_DIMENSION, Lit144, -4092);
        string$Mntokenize = new ModuleMethod(srfi13Var, 321, Lit145, -4095);
        ModuleMethod moduleMethod26 = new ModuleMethod(srfi13Var, 322, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod26.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1738");
        lambda$Fn210 = moduleMethod26;
        xsubstring = new ModuleMethod(srfi13Var, 323, Lit146, -4094);
        ModuleMethod moduleMethod27 = new ModuleMethod(srfi13Var, 324, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod27.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1779");
        lambda$Fn216 = moduleMethod27;
        ModuleMethod moduleMethod28 = new ModuleMethod(srfi13Var, 325, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod28.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1785");
        lambda$Fn220 = moduleMethod28;
        string$Mnxcopy$Ex = new ModuleMethod(srfi13Var, 326, Lit147, -4092);
        $Pcmultispan$Mnrepcopy$Ex = new ModuleMethod(srfi13Var, 327, Lit148, 28679);
        string$Mnjoin = new ModuleMethod(srfi13Var, 328, Lit149, -4095);
        $instance.run();
    }

    public srfi13() {
        ModuleInfo.register(this);
    }

    public static Object stringCopy$Ex(Object obj, int i, CharSequence charSequence, int i2) {
        return stringCopy$Ex(obj, i, charSequence, i2, charSequence.length());
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    public static Object stringParseStart$PlEnd(Object proc, Object s, Object args) {
        boolean x;
        frame frameVar = new frame();
        frameVar.proc = proc;
        frameVar.s = s;
        if (!strings.isString(frameVar.s)) {
            misc.error$V("Non-string value", new Object[]{frameVar.proc, frameVar.s});
        }
        Object obj = frameVar.s;
        try {
            frameVar.slen = strings.stringLength((CharSequence) obj);
            if (lists.isPair(args)) {
                Object apply1 = lists.car.apply1(args);
                frameVar.args = lists.cdr.apply1(args);
                frameVar.start = apply1;
                boolean x2 = numbers.isInteger(frameVar.start);
                if (!x2 ? x2 : !(!(x = numbers.isExact(frameVar.start)) ? !x : Scheme.numGEq.apply2(frameVar.start, Lit0) == Boolean.FALSE)) {
                    return call_with_values.callWithValues(frameVar.lambda$Fn1, frameVar.lambda$Fn2);
                }
                return misc.error$V("Illegal substring START spec", new Object[]{frameVar.proc, frameVar.start, frameVar.s});
            }
            return misc.values(LList.Empty, Lit0, Integer.valueOf(frameVar.slen));
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, obj);
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SOURCE /* 194 */:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION /* 196 */:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 197:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case ErrorMessages.ERROR_CAMERA_NO_IMAGE_RETURNED /* 201 */:
                if (obj instanceof CharSequence) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                }
                return -786431;
            case 277:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 299:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                if (obj3 instanceof CharSequence) {
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                }
                return -786429;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame extends ModuleBody {
        Object args;
        final ModuleMethod lambda$Fn1 = new ModuleMethod(this, 1, null, 0);
        final ModuleMethod lambda$Fn2;
        Object proc;
        Object s;
        int slen;
        Object start;

        public frame() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 2, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:150");
            this.lambda$Fn2 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 1 ? lambda1() : super.apply0(moduleMethod);
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

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 2 ? lambda2(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda2(Object end, Object args) {
            return Scheme.numLEq.apply2(this.start, end) != Boolean.FALSE ? misc.values(args, this.start, end) : misc.error$V("Illegal substring START/END spec", new Object[]{this.proc, this.start, end, this.s});
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 2) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda1() {
            boolean x;
            if (lists.isPair(this.args)) {
                Object end = lists.car.apply1(this.args);
                Object args = lists.cdr.apply1(this.args);
                boolean x2 = numbers.isInteger(end);
                return (!x2 ? x2 : !(!(x = numbers.isExact(end)) ? x : Scheme.numLEq.apply2(end, Integer.valueOf(this.slen)) != Boolean.FALSE)) ? misc.error$V("Illegal substring END spec", new Object[]{this.proc, end, this.s}) : misc.values(end, args);
            }
            return misc.values(Integer.valueOf(this.slen), this.args);
        }
    }

    public static Object $PcCheckBounds(Object proc, CharSequence s, int start, int end) {
        if (start < 0) {
            return misc.error$V("Illegal substring START spec", new Object[]{proc, Integer.valueOf(start), s});
        }
        if (start > end) {
            return misc.error$V("Illegal substring START/END spec", new Object[0]);
        }
        return end > strings.stringLength(s) ? misc.error$V("Illegal substring END spec", new Object[]{proc, Integer.valueOf(end), s}) : Values.empty;
    }

    @Override // gnu.expr.ModuleBody
    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        switch (moduleMethod.selector) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_FULLSCREEN /* 195 */:
                callContext.value1 = obj;
                if (obj2 instanceof CharSequence) {
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                }
                return -786430;
            case 198:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 204:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 206:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 299:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                if (obj3 instanceof CharSequence) {
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                }
                return -786429;
            case 319:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            default:
                return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame0 extends ModuleBody {
        Object args;
        final ModuleMethod lambda$Fn3 = new ModuleMethod(this, 3, null, 0);
        final ModuleMethod lambda$Fn4;
        Object proc;
        Object s;

        public frame0() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 4, null, 12291);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:174");
            this.lambda$Fn4 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 3 ? lambda3() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 3) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 4 ? lambda4(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda3() {
            return srfi13.stringParseStart$PlEnd(this.proc, this.s, this.args);
        }

        Object lambda4(Object rest, Object start, Object end) {
            return lists.isPair(rest) ? misc.error$V("Extra arguments to procedure", new Object[]{this.proc, rest}) : misc.values(start, end);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 4) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static Object stringParseFinalStart$PlEnd(Object proc, Object s, Object args) {
        frame0 frame0Var = new frame0();
        frame0Var.proc = proc;
        frame0Var.s = s;
        frame0Var.args = args;
        return call_with_values.callWithValues(frame0Var.lambda$Fn3, frame0Var.lambda$Fn4);
    }

    public static boolean isSubstringSpecOk(Object s, Object start, Object end) {
        boolean x = strings.isString(s);
        if (x) {
            boolean x2 = numbers.isInteger(start);
            if (x2) {
                boolean x3 = numbers.isExact(start);
                if (x3) {
                    boolean x4 = numbers.isInteger(end);
                    if (x4) {
                        boolean x5 = numbers.isExact(end);
                        if (x5) {
                            Object apply2 = Scheme.numLEq.apply2(Lit0, start);
                            try {
                                boolean x6 = ((Boolean) apply2).booleanValue();
                                if (x6) {
                                    Object apply22 = Scheme.numLEq.apply2(start, end);
                                    try {
                                        boolean x7 = ((Boolean) apply22).booleanValue();
                                        if (x7) {
                                            try {
                                                return ((Boolean) Scheme.numLEq.apply2(end, Integer.valueOf(strings.stringLength((CharSequence) s)))).booleanValue();
                                            } catch (ClassCastException e) {
                                                throw new WrongType(e, "string-length", 1, s);
                                            }
                                        }
                                        return x7;
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "x", -2, apply22);
                                    }
                                }
                                return x6;
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "x", -2, apply2);
                            }
                        }
                        return x5;
                    }
                    return x4;
                }
                return x3;
            }
            return x2;
        }
        return x;
    }

    public static Object checkSubstringSpec(Object proc, Object s, Object start, Object end) {
        return !isSubstringSpecOk(s, start, end) ? misc.error$V("Illegal substring spec.", new Object[]{proc, s, start, end}) : Values.empty;
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0007, code lost:
        if (r0 != false) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0024, code lost:
        return kawa.lib.misc.error$V("Illegal substring spec.", new java.lang.Object[]{r5, r6, java.lang.Integer.valueOf(r7), java.lang.Integer.valueOf(r8)});
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static java.lang.Object $PcCheckSubstringSpec(java.lang.Object r5, java.lang.CharSequence r6, int r7, int r8) {
        /*
            r1 = 1
            r2 = 0
            if (r7 >= 0) goto L25
            r0 = r1
        L5:
            if (r0 == 0) goto L27
            if (r0 == 0) goto L2e
        L9:
            java.lang.String r3 = "Illegal substring spec."
            r4 = 4
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r4[r2] = r5
            r4[r1] = r6
            r1 = 2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)
            r4[r1] = r2
            r1 = 3
            java.lang.Integer r2 = java.lang.Integer.valueOf(r8)
            r4[r1] = r2
            java.lang.Object r1 = kawa.lib.misc.error$V(r3, r4)
        L24:
            return r1
        L25:
            r0 = r2
            goto L5
        L27:
            if (r7 <= r8) goto L31
            r0 = r1
        L2a:
            if (r0 == 0) goto L33
            if (r0 != 0) goto L9
        L2e:
            gnu.mapping.Values r1 = gnu.mapping.Values.empty
            goto L24
        L31:
            r0 = r2
            goto L2a
        L33:
            int r3 = kawa.lib.strings.stringLength(r6)
            if (r8 <= r3) goto L2e
            goto L9
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi13.$PcCheckSubstringSpec(java.lang.Object, java.lang.CharSequence, int, int):java.lang.Object");
    }

    public static Object substring$SlShared$V(Object s, Object start, Object[] argsArray) {
        frame1 frame1Var = new frame1();
        frame1Var.start = start;
        LList maybe$Mnend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), strings.string$Qu, s, substring$Slshared);
            try {
                frame1Var.slen = strings.stringLength((CharSequence) s);
                try {
                    Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), lambda$Fn5, frame1Var.start, substring$Slshared);
                    try {
                        CharSequence charSequence = (CharSequence) s;
                        Object obj = frame1Var.start;
                        try {
                            int intValue = ((Number) obj).intValue();
                            try {
                                Object apply4 = Scheme.applyToArgs.apply4(loc$$Cloptional.get(), maybe$Mnend, Integer.valueOf(frame1Var.slen), frame1Var.lambda$Fn6);
                                try {
                                    return $PcSubstring$SlShared(charSequence, intValue, ((Number) apply4).intValue());
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "%substring/shared", 2, apply4);
                                }
                            } catch (UnboundLocationException e2) {
                                e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 226, 10);
                                throw e2;
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "%substring/shared", 1, obj);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "%substring/shared", 0, s);
                    }
                } catch (UnboundLocationException e5) {
                    e5.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", YaVersion.YOUNG_ANDROID_VERSION, 5);
                    throw e5;
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-length", 1, s);
            }
        } catch (UnboundLocationException e7) {
            e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 221, 3);
            throw e7;
        }
    }

    @Override // gnu.expr.ModuleBody
    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case HttpRequestContext.HTTP_OK /* 200 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_CAMERA_NO_IMAGE_RETURNED /* 201 */:
            case 204:
            case 206:
            case 209:
            case 211:
            case 217:
            case 218:
            case 239:
            case 240:
            case LispEscapeFormat.ESCAPE_ALL /* 242 */:
            case 244:
            case 245:
            case 247:
            case 248:
            case 250:
            case Telnet.WONT /* 252 */:
            case Telnet.DONT /* 254 */:
            case 255:
            case InputDeviceCompat.SOURCE_KEYBOARD /* 257 */:
            case 259:
            case 260:
            case 262:
            case 263:
            case 265:
            case 267:
            case 271:
            case 277:
            case 280:
            case 281:
            case 282:
            case 283:
            case 287:
            case 289:
            case 300:
            case ErrorMessages.ERROR_TWITTER_UNSUPPORTED_LOGIN_FUNCTION /* 301 */:
            case ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED /* 309 */:
            case ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED /* 312 */:
            case 315:
            case 316:
            case 319:
            case 322:
            case 324:
            case 325:
            default:
                return super.matchN(moduleMethod, objArr, callContext);
            case ErrorMessages.ERROR_NO_CAMERA_PERMISSION /* 202 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 203:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 205:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 207:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 208:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 210:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 212:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 213:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 214:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 215:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 216:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 219:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 220:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 221:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 222:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case YaVersion.YOUNG_ANDROID_VERSION /* 223 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 224:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 225:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 226:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 227:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 228:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 229:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 230:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 231:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 232:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 233:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 234:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 235:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 236:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 237:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 238:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case LispEscapeFormat.ESCAPE_NORMAL /* 241 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 243:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 246:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 249:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case Telnet.WILL /* 251 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case Telnet.DO /* 253 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 256:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 258:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 261:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 264:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 266:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 268:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 269:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 270:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 272:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 273:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 274:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 275:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 276:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 278:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 279:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 284:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 285:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 286:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 288:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 290:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 291:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 292:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 293:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 294:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 295:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 296:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 297:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 298:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 299:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET /* 302 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_EXCEPTION /* 303 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN /* 304 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED /* 305 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED /* 306 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED /* 307 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED /* 308 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED /* 310 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED /* 311 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED /* 313 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ErrorMessages.ERROR_TWITTER_SEARCH_FAILED /* 314 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 317:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 318:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case ScreenDensityUtil.DEFAULT_NORMAL_SHORT_DIMENSION /* 320 */:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 321:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 323:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 326:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 327:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 328:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame1 extends ModuleBody {
        final ModuleMethod lambda$Fn6;
        int slen;
        Object start;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 5, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:227");
            this.lambda$Fn6 = moduleMethod;
        }

        static boolean lambda5(Object start) {
            boolean x = numbers.isInteger(start);
            if (x) {
                boolean x2 = numbers.isExact(start);
                return x2 ? ((Boolean) Scheme.numLEq.apply2(srfi13.Lit0, start)).booleanValue() : x2;
            }
            return x;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 5 ? lambda6(obj) ? Boolean.TRUE : Boolean.FALSE : super.apply1(moduleMethod, obj);
        }

        boolean lambda6(Object end) {
            boolean x = numbers.isInteger(end);
            if (x) {
                boolean x2 = numbers.isExact(end);
                if (x2) {
                    Object apply2 = Scheme.numLEq.apply2(this.start, end);
                    try {
                        boolean x3 = ((Boolean) apply2).booleanValue();
                        return x3 ? ((Boolean) Scheme.numLEq.apply2(end, Integer.valueOf(this.slen))).booleanValue() : x3;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "x", -2, apply2);
                    }
                }
                return x2;
            }
            return x;
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 5) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 199:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 209:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 211:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 217:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 239:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 240:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case LispEscapeFormat.ESCAPE_ALL /* 242 */:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 244:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 245:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 247:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 248:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 250:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Telnet.WONT /* 252 */:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Telnet.DONT /* 254 */:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 255:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case InputDeviceCompat.SOURCE_KEYBOARD /* 257 */:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 259:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 260:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 262:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 263:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 265:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 267:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 271:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 287:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 289:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED /* 309 */:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED /* 312 */:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 315:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 316:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 322:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 324:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 325:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object $PcSubstring$SlShared(CharSequence s, int start, int end) {
        boolean x = numbers.isZero(Integer.valueOf(start));
        if (x) {
            if (end == strings.stringLength(s)) {
                return s;
            }
        } else if (x) {
            return s;
        }
        return strings.substring(s, start, end);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame2 extends ModuleBody {
        final ModuleMethod lambda$Fn7 = new ModuleMethod(this, 6, null, 0);
        final ModuleMethod lambda$Fn8 = new ModuleMethod(this, 7, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 6 ? lambda7() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 7 ? lambda8(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 6) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 7) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda7() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncopy, this.s, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda8(Object start, Object end) {
            Object obj = this.s;
            try {
                try {
                    try {
                        return strings.substring((CharSequence) obj, ((Number) start).intValue(), ((Number) end).intValue());
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "substring", 3, end);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "substring", 2, start);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "substring", 1, obj);
            }
        }
    }

    public static Object stringCopy$V(Object s, Object[] argsArray) {
        frame2 frame2Var = new frame2();
        frame2Var.s = s;
        frame2Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame2Var.lambda$Fn7, frame2Var.lambda$Fn8);
    }

    public static Object stringMap$V(Object proc, Object s, Object[] argsArray) {
        frame3 frame3Var = new frame3();
        frame3Var.proc = proc;
        frame3Var.s = s;
        frame3Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame3Var.proc, string$Mnmap);
            return call_with_values.callWithValues(frame3Var.lambda$Fn9, frame3Var.lambda$Fn10);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 271, 3);
            throw e;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame3 extends ModuleBody {
        LList maybe$Mnstart$Plend;
        Object proc;
        Object s;
        final ModuleMethod lambda$Fn9 = new ModuleMethod(this, 8, null, 0);
        final ModuleMethod lambda$Fn10 = new ModuleMethod(this, 9, null, 8194);

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 8 ? lambda9() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 9 ? lambda10(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 8) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 9) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda9() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnmap, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda10(Object start, Object end) {
            return srfi13.$PcStringMap(this.proc, this.s, start, end);
        }
    }

    public static Object $PcStringMap(Object proc, Object s, Object start, Object end) {
        Object len = AddOp.$Mn.apply2(end, start);
        try {
            CharSequence ans = strings.makeString(((Number) len).intValue());
            Object i = AddOp.$Mn.apply2(end, Lit1);
            Object apply2 = AddOp.$Mn.apply2(len, Lit1);
            while (Scheme.numLss.apply2(apply2, Lit0) == Boolean.FALSE) {
                try {
                    CharSeq charSeq = (CharSeq) ans;
                    try {
                        int intValue = ((Number) apply2).intValue();
                        try {
                            try {
                                Object apply22 = Scheme.applyToArgs.apply2(proc, Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())));
                                try {
                                    charSeq.setCharAt(intValue, ((Char) apply22).charValue());
                                    i = AddOp.$Mn.apply2(i, Lit1);
                                    Object j = AddOp.$Mn.apply2(apply2, Lit1);
                                    apply2 = j;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-set!", 3, apply22);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 2, i);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 1, s);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-set!", 2, apply2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-set!", 1, ans);
                }
            }
            return ans;
        } catch (ClassCastException e6) {
            throw new WrongType(e6, "make-string", 1, len);
        }
    }

    public static Object stringMap$Ex$V(Object proc, Object s, Object[] argsArray) {
        frame4 frame4Var = new frame4();
        frame4Var.proc = proc;
        frame4Var.s = s;
        frame4Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame4Var.proc, string$Mnmap$Ex);
            return call_with_values.callWithValues(frame4Var.lambda$Fn11, frame4Var.lambda$Fn12);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 285, 3);
            throw e;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame4 extends ModuleBody {
        final ModuleMethod lambda$Fn11 = new ModuleMethod(this, 10, null, 0);
        final ModuleMethod lambda$Fn12 = new ModuleMethod(this, 11, null, 8194);
        LList maybe$Mnstart$Plend;
        Object proc;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 10 ? lambda11() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 11 ? lambda12(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
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

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 11) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda11() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnmap$Ex, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda12(Object start, Object end) {
            return srfi13.$PcStringMap$Ex(this.proc, this.s, start, end);
        }
    }

    public static Object $PcStringMap$Ex(Object proc, Object s, Object start, Object end) {
        for (Object i = AddOp.$Mn.apply2(end, Lit1); Scheme.numLss.apply2(i, start) == Boolean.FALSE; i = AddOp.$Mn.apply2(i, Lit1)) {
            try {
                CharSeq charSeq = (CharSeq) s;
                try {
                    int intValue = ((Number) i).intValue();
                    try {
                        try {
                            Object apply2 = Scheme.applyToArgs.apply2(proc, Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())));
                            try {
                                charSeq.setCharAt(intValue, ((Char) apply2).charValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-set!", 3, apply2);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "string-ref", 1, s);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "string-set!", 2, i);
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "string-set!", 1, s);
            }
        }
        return Values.empty;
    }

    public static Object stringFold$V(Object kons, Object knil, Object s, Object[] argsArray) {
        frame5 frame5Var = new frame5();
        frame5Var.kons = kons;
        frame5Var.knil = knil;
        frame5Var.s = s;
        frame5Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame5Var.kons, string$Mnfold);
            return call_with_values.callWithValues(frame5Var.lambda$Fn13, frame5Var.lambda$Fn14);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 295, 3);
            throw e;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame5 extends ModuleBody {
        Object knil;
        Object kons;
        final ModuleMethod lambda$Fn13 = new ModuleMethod(this, 12, null, 0);
        final ModuleMethod lambda$Fn14 = new ModuleMethod(this, 13, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 12 ? lambda13() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 13 ? lambda14(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 12) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 13) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda13() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfold, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda14(Object start, Object end) {
            Object v = this.knil;
            Object obj = start;
            while (Scheme.numLss.apply2(obj, end) != Boolean.FALSE) {
                ApplyToArgs applyToArgs = Scheme.applyToArgs;
                Object obj2 = this.kons;
                Object obj3 = this.s;
                try {
                    try {
                        v = applyToArgs.apply3(obj2, Char.make(strings.stringRef((CharSequence) obj3, ((Number) obj).intValue())), v);
                        Object i = AddOp.$Pl.apply2(obj, srfi13.Lit1);
                        obj = i;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, obj);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, obj3);
                }
            }
            return v;
        }
    }

    public static Object stringFoldRight$V(Object kons, Object knil, Object s, Object[] argsArray) {
        frame6 frame6Var = new frame6();
        frame6Var.kons = kons;
        frame6Var.knil = knil;
        frame6Var.s = s;
        frame6Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame6Var.kons, string$Mnfold$Mnright);
            return call_with_values.callWithValues(frame6Var.lambda$Fn15, frame6Var.lambda$Fn16);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET, 3);
            throw e;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame6 extends ModuleBody {
        Object knil;
        Object kons;
        final ModuleMethod lambda$Fn15 = new ModuleMethod(this, 14, null, 0);
        final ModuleMethod lambda$Fn16 = new ModuleMethod(this, 15, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 14 ? lambda15() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 15 ? lambda16(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 14) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 15) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda15() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfold$Mnright, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda16(Object start, Object end) {
            Object v = this.knil;
            Object apply2 = AddOp.$Mn.apply2(end, srfi13.Lit1);
            while (Scheme.numGEq.apply2(apply2, start) != Boolean.FALSE) {
                ApplyToArgs applyToArgs = Scheme.applyToArgs;
                Object obj = this.kons;
                Object obj2 = this.s;
                try {
                    try {
                        v = applyToArgs.apply3(obj, Char.make(strings.stringRef((CharSequence) obj2, ((Number) apply2).intValue())), v);
                        Object i = AddOp.$Mn.apply2(apply2, srfi13.Lit1);
                        apply2 = i;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, apply2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, obj2);
                }
            }
            return v;
        }
    }

    public static Object stringUnfold$V(Object p, Object f, Object g, Object seed, Object[] argsArray) {
        Object valueOf;
        Object obj;
        Object obj2;
        LList base$Plmake$Mnfinal = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, p, string$Mnunfold);
            try {
                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, f, string$Mnunfold);
                try {
                    Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, g, string$Mnunfold);
                    ApplyToArgs applyToArgs = Scheme.applyToArgs;
                    try {
                        Object obj3 = loc$let$Mnoptionals$St.get();
                        try {
                            try {
                                try {
                                    try {
                                        Object apply2 = Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply3(loc$base.get(), "", strings.isString(loc$base.get()) ? Boolean.TRUE : Boolean.FALSE), Scheme.applyToArgs.apply3(loc$make$Mnfinal.get(), lambda$Fn17, misc.isProcedure(loc$make$Mnfinal.get()) ? Boolean.TRUE : Boolean.FALSE));
                                        Object obj4 = LList.Empty;
                                        int i = 0;
                                        CharSequence makeString = strings.makeString(40);
                                        int i2 = 40;
                                        int i3 = 0;
                                        loop0: while (true) {
                                            valueOf = Integer.valueOf(i3);
                                            while (Scheme.applyToArgs.apply2(p, seed) == Boolean.FALSE) {
                                                Object c = Scheme.applyToArgs.apply2(f, seed);
                                                seed = Scheme.applyToArgs.apply2(g, seed);
                                                if (Scheme.numLss.apply2(valueOf, Integer.valueOf(i2)) != Boolean.FALSE) {
                                                    try {
                                                        try {
                                                            try {
                                                                ((CharSeq) makeString).setCharAt(((Number) valueOf).intValue(), ((Char) c).charValue());
                                                                valueOf = AddOp.$Pl.apply2(valueOf, Lit1);
                                                            } catch (ClassCastException e) {
                                                                throw new WrongType(e, "string-set!", 3, c);
                                                            }
                                                        } catch (ClassCastException e2) {
                                                            throw new WrongType(e2, "string-set!", 2, valueOf);
                                                        }
                                                    } catch (ClassCastException e3) {
                                                        throw new WrongType(e3, "string-set!", 1, makeString);
                                                    }
                                                } else {
                                                    int nchars2 = i2 + i;
                                                    Object min = numbers.min(Lit2, Integer.valueOf(nchars2));
                                                    try {
                                                        int chunk$Mnlen2 = ((Number) min).intValue();
                                                        CharSequence new$Mnchunk = strings.makeString(chunk$Mnlen2);
                                                        try {
                                                            try {
                                                                ((CharSeq) new$Mnchunk).setCharAt(0, ((Char) c).charValue());
                                                                obj4 = lists.cons(makeString, obj4);
                                                                i += i2;
                                                                i3 = 1;
                                                                i2 = chunk$Mnlen2;
                                                                makeString = new$Mnchunk;
                                                            } catch (ClassCastException e4) {
                                                                throw new WrongType(e4, "string-set!", 3, c);
                                                            }
                                                        } catch (ClassCastException e5) {
                                                            throw new WrongType(e5, "string-set!", 1, new$Mnchunk);
                                                        }
                                                    } catch (ClassCastException e6) {
                                                        throw new WrongType(e6, "chunk-len2", -2, min);
                                                    }
                                                }
                                            }
                                            break loop0;
                                        }
                                        try {
                                            Object apply22 = Scheme.applyToArgs.apply2(loc$make$Mnfinal.get(), seed);
                                            try {
                                                int flen = strings.stringLength((CharSequence) apply22);
                                                try {
                                                    try {
                                                        int base$Mnlen = strings.stringLength((CharSequence) loc$base.get());
                                                        Object apply23 = AddOp.$Pl.apply2(Integer.valueOf(base$Mnlen + i), valueOf);
                                                        try {
                                                            int j = ((Number) apply23).intValue();
                                                            CharSequence ans = strings.makeString(j + flen);
                                                            try {
                                                                $PcStringCopy$Ex(ans, j, (CharSequence) apply22, 0, flen);
                                                                Object apply24 = AddOp.$Mn.apply2(Integer.valueOf(j), valueOf);
                                                                try {
                                                                    int j2 = ((Number) apply24).intValue();
                                                                    try {
                                                                        try {
                                                                            $PcStringCopy$Ex(ans, j2, makeString, 0, ((Number) valueOf).intValue());
                                                                            Object j3 = Integer.valueOf(j2);
                                                                            while (lists.isPair(obj4)) {
                                                                                Object chunk = lists.car.apply1(obj4);
                                                                                obj4 = lists.cdr.apply1(obj4);
                                                                                try {
                                                                                    int chunk$Mnlen = strings.stringLength((CharSequence) chunk);
                                                                                    j3 = AddOp.$Mn.apply2(j3, Integer.valueOf(chunk$Mnlen));
                                                                                    try {
                                                                                        try {
                                                                                            $PcStringCopy$Ex(ans, ((Number) j3).intValue(), (CharSequence) chunk, 0, chunk$Mnlen);
                                                                                        } catch (ClassCastException e7) {
                                                                                            throw new WrongType(e7, "%string-copy!", 2, chunk);
                                                                                        }
                                                                                    } catch (ClassCastException e8) {
                                                                                        throw new WrongType(e8, "%string-copy!", 1, j3);
                                                                                    }
                                                                                } catch (ClassCastException e9) {
                                                                                    throw new WrongType(e9, "string-length", 1, chunk);
                                                                                }
                                                                            }
                                                                            try {
                                                                                try {
                                                                                    $PcStringCopy$Ex(ans, 0, (CharSequence) loc$base.get(), 0, base$Mnlen);
                                                                                    return applyToArgs.apply4(obj3, base$Plmake$Mnfinal, apply2, ans);
                                                                                } catch (ClassCastException e10) {
                                                                                    throw new WrongType(e10, "%string-copy!", 2, obj2);
                                                                                }
                                                                            } catch (UnboundLocationException e11) {
                                                                                e11.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_NXT_UNABLE_TO_DOWNLOAD_FILE, 29);
                                                                                throw e11;
                                                                            }
                                                                        } catch (ClassCastException e12) {
                                                                            throw new WrongType(e12, "%string-copy!", 4, valueOf);
                                                                        }
                                                                    } catch (ClassCastException e13) {
                                                                        throw new WrongType(e13, "%string-copy!", 2, makeString);
                                                                    }
                                                                } catch (ClassCastException e14) {
                                                                    throw new WrongType(e14, "j", -2, apply24);
                                                                }
                                                            } catch (ClassCastException e15) {
                                                                throw new WrongType(e15, "%string-copy!", 2, apply22);
                                                            }
                                                        } catch (ClassCastException e16) {
                                                            throw new WrongType(e16, "j", -2, apply23);
                                                        }
                                                    } catch (ClassCastException e17) {
                                                        throw new WrongType(e17, "string-length", 1, obj);
                                                    }
                                                } catch (UnboundLocationException e18) {
                                                    e18.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_NXT_NOT_CONNECTED_TO_ROBOT, 38);
                                                    throw e18;
                                                }
                                            } catch (ClassCastException e19) {
                                                throw new WrongType(e19, "string-length", 1, apply22);
                                            }
                                        } catch (UnboundLocationException e20) {
                                            e20.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 400, 20);
                                            throw e20;
                                        }
                                    } catch (UnboundLocationException e21) {
                                        e21.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 377, 46);
                                        throw e21;
                                    }
                                } catch (UnboundLocationException e22) {
                                    e22.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 377, 6);
                                    throw e22;
                                }
                            } catch (UnboundLocationException e23) {
                                e23.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 376, 57);
                                throw e23;
                            }
                        } catch (UnboundLocationException e24) {
                            e24.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 376, 20);
                            throw e24;
                        }
                    } catch (UnboundLocationException e25) {
                        e25.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 375, 3);
                        throw e25;
                    }
                } catch (UnboundLocationException e26) {
                    e26.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 374, 3);
                    throw e26;
                }
            } catch (UnboundLocationException e27) {
                e27.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 373, 3);
                throw e27;
            }
        } catch (UnboundLocationException e28) {
            e28.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 372, 3);
            throw e28;
        }
    }

    static String lambda17(Object x) {
        return "";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Object stringUnfoldRight$V(Object p, Object f, Object g, Object seed, Object[] argsArray) {
        Object obj;
        Object obj2;
        LList base$Plmake$Mnfinal = LList.makeList(argsArray, 0);
        ApplyToArgs applyToArgs = Scheme.applyToArgs;
        try {
            Object obj3 = loc$let$Mnoptionals$St.get();
            try {
                try {
                    try {
                        try {
                            Object apply2 = Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply3(loc$base.get(), "", strings.isString(loc$base.get()) ? Boolean.TRUE : Boolean.FALSE), Scheme.applyToArgs.apply3(loc$make$Mnfinal.get(), lambda$Fn18, misc.isProcedure(loc$make$Mnfinal.get()) ? Boolean.TRUE : Boolean.FALSE));
                            Object obj4 = LList.Empty;
                            Object obj5 = Lit0;
                            CharSequence makeString = strings.makeString(40);
                            Object obj6 = Lit3;
                            Object i = Lit3;
                            while (Scheme.applyToArgs.apply2(p, seed) == Boolean.FALSE) {
                                Object c = Scheme.applyToArgs.apply2(f, seed);
                                seed = Scheme.applyToArgs.apply2(g, seed);
                                if (Scheme.numGrt.apply2(i, Lit0) != Boolean.FALSE) {
                                    i = AddOp.$Mn.apply2(i, Lit1);
                                    try {
                                        try {
                                            try {
                                                ((CharSeq) makeString).setCharAt(((Number) i).intValue(), ((Char) c).charValue());
                                            } catch (ClassCastException e) {
                                                throw new WrongType(e, "string-set!", 3, c);
                                            }
                                        } catch (ClassCastException e2) {
                                            throw new WrongType(e2, "string-set!", 2, i);
                                        }
                                    } catch (ClassCastException e3) {
                                        throw new WrongType(e3, "string-set!", 1, makeString);
                                    }
                                } else {
                                    Object nchars2 = AddOp.$Pl.apply2(obj6, obj5);
                                    Object chunk$Mnlen2 = numbers.min(Lit4, nchars2);
                                    try {
                                        CharSequence new$Mnchunk = strings.makeString(((Number) chunk$Mnlen2).intValue());
                                        i = AddOp.$Mn.apply2(chunk$Mnlen2, Lit1);
                                        try {
                                            try {
                                                try {
                                                    ((CharSeq) new$Mnchunk).setCharAt(((Number) i).intValue(), ((Char) c).charValue());
                                                    obj4 = lists.cons(makeString, obj4);
                                                    obj5 = AddOp.$Pl.apply2(obj5, obj6);
                                                    obj6 = chunk$Mnlen2;
                                                    makeString = new$Mnchunk;
                                                } catch (ClassCastException e4) {
                                                    throw new WrongType(e4, "string-set!", 3, c);
                                                }
                                            } catch (ClassCastException e5) {
                                                throw new WrongType(e5, "string-set!", 2, i);
                                            }
                                        } catch (ClassCastException e6) {
                                            throw new WrongType(e6, "string-set!", 1, new$Mnchunk);
                                        }
                                    } catch (ClassCastException e7) {
                                        throw new WrongType(e7, "make-string", 1, chunk$Mnlen2);
                                    }
                                }
                            }
                            try {
                                Object apply22 = Scheme.applyToArgs.apply2(loc$make$Mnfinal.get(), seed);
                                try {
                                    int flen = strings.stringLength((CharSequence) apply22);
                                    try {
                                        try {
                                            int base$Mnlen = strings.stringLength((CharSequence) loc$base.get());
                                            Object chunk$Mnused = AddOp.$Mn.apply2(obj6, i);
                                            Object j = AddOp.$Pl.apply2(AddOp.$Pl.apply2(Integer.valueOf(base$Mnlen), obj5), chunk$Mnused);
                                            Object apply23 = AddOp.$Pl.apply2(j, Integer.valueOf(flen));
                                            try {
                                                CharSequence ans = strings.makeString(((Number) apply23).intValue());
                                                try {
                                                    $PcStringCopy$Ex(ans, 0, (CharSequence) apply22, 0, flen);
                                                    try {
                                                        try {
                                                            try {
                                                                $PcStringCopy$Ex(ans, flen, makeString, ((Number) i).intValue(), obj6.intValue());
                                                                Object j2 = AddOp.$Pl.apply2(Integer.valueOf(flen), chunk$Mnused);
                                                                while (lists.isPair(obj4)) {
                                                                    Object chunk = lists.car.apply1(obj4);
                                                                    obj4 = lists.cdr.apply1(obj4);
                                                                    try {
                                                                        int chunk$Mnlen = strings.stringLength((CharSequence) chunk);
                                                                        try {
                                                                            try {
                                                                                $PcStringCopy$Ex(ans, ((Number) j2).intValue(), (CharSequence) chunk, 0, chunk$Mnlen);
                                                                                j2 = AddOp.$Pl.apply2(j2, Integer.valueOf(chunk$Mnlen));
                                                                            } catch (ClassCastException e8) {
                                                                                throw new WrongType(e8, "%string-copy!", 2, chunk);
                                                                            }
                                                                        } catch (ClassCastException e9) {
                                                                            throw new WrongType(e9, "%string-copy!", 1, j2);
                                                                        }
                                                                    } catch (ClassCastException e10) {
                                                                        throw new WrongType(e10, "string-length", 1, chunk);
                                                                    }
                                                                }
                                                                try {
                                                                    try {
                                                                        try {
                                                                            $PcStringCopy$Ex(ans, ((Number) j2).intValue(), (CharSequence) loc$base.get(), 0, base$Mnlen);
                                                                            return applyToArgs.apply4(obj3, base$Plmake$Mnfinal, apply2, ans);
                                                                        } catch (ClassCastException e11) {
                                                                            throw new WrongType(e11, "%string-copy!", 2, obj2);
                                                                        }
                                                                    } catch (UnboundLocationException e12) {
                                                                        e12.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 463, 30);
                                                                        throw e12;
                                                                    }
                                                                } catch (ClassCastException e13) {
                                                                    throw new WrongType(e13, "%string-copy!", 1, j2);
                                                                }
                                                            } catch (ClassCastException e14) {
                                                                throw new WrongType(e14, "%string-copy!", 4, obj6);
                                                            }
                                                        } catch (ClassCastException e15) {
                                                            throw new WrongType(e15, "%string-copy!", 3, i);
                                                        }
                                                    } catch (ClassCastException e16) {
                                                        throw new WrongType(e16, "%string-copy!", 2, makeString);
                                                    }
                                                } catch (ClassCastException e17) {
                                                    throw new WrongType(e17, "%string-copy!", 2, apply22);
                                                }
                                            } catch (ClassCastException e18) {
                                                throw new WrongType(e18, "make-string", 1, apply23);
                                            }
                                        } catch (ClassCastException e19) {
                                            throw new WrongType(e19, "string-length", 1, obj);
                                        }
                                    } catch (UnboundLocationException e20) {
                                        e20.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 449, 31);
                                        throw e20;
                                    }
                                } catch (ClassCastException e21) {
                                    throw new WrongType(e21, "string-length", 1, apply22);
                                }
                            } catch (UnboundLocationException e22) {
                                e22.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 447, 20);
                                throw e22;
                            }
                        } catch (UnboundLocationException e23) {
                            e23.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_NXT_INVALID_SENSOR_TYPE, 46);
                            throw e23;
                        }
                    } catch (UnboundLocationException e24) {
                        e24.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_NXT_INVALID_SENSOR_TYPE, 6);
                        throw e24;
                    }
                } catch (UnboundLocationException e25) {
                    e25.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_NXT_INVALID_REGULATION_MODE, 57);
                    throw e25;
                }
            } catch (UnboundLocationException e26) {
                e26.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_NXT_INVALID_REGULATION_MODE, 20);
                throw e26;
            }
        } catch (UnboundLocationException e27) {
            e27.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_NXT_INVALID_MOTOR_MODE, 3);
            throw e27;
        }
    }

    static String lambda18(Object x) {
        return "";
    }

    public static Object stringForEach$V(Object proc, Object s, Object[] argsArray) {
        frame7 frame7Var = new frame7();
        frame7Var.proc = proc;
        frame7Var.s = s;
        frame7Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame7Var.proc, string$Mnfor$Mneach);
            return call_with_values.callWithValues(frame7Var.lambda$Fn19, frame7Var.lambda$Fn20);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 468, 3);
            throw e;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame7 extends ModuleBody {
        final ModuleMethod lambda$Fn19 = new ModuleMethod(this, 16, null, 0);
        final ModuleMethod lambda$Fn20 = new ModuleMethod(this, 17, null, 8194);
        LList maybe$Mnstart$Plend;
        Object proc;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 16 ? lambda19() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 17 ? lambda20(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 16) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 17) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda19() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfor$Mneach, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda20(Object start, Object end) {
            for (Object i = start; Scheme.numLss.apply2(i, end) != Boolean.FALSE; i = AddOp.$Pl.apply2(i, srfi13.Lit1)) {
                ApplyToArgs applyToArgs = Scheme.applyToArgs;
                Object obj = this.proc;
                Object obj2 = this.s;
                try {
                    try {
                        applyToArgs.apply2(obj, Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, i);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, obj2);
                }
            }
            return Values.empty;
        }
    }

    public static Object stringForEachIndex$V(Object proc, Object s, Object[] argsArray) {
        frame8 frame8Var = new frame8();
        frame8Var.proc = proc;
        frame8Var.s = s;
        frame8Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame8Var.proc, string$Mnfor$Mneach$Mnindex);
            return call_with_values.callWithValues(frame8Var.lambda$Fn21, frame8Var.lambda$Fn22);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 476, 3);
            throw e;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame8 extends ModuleBody {
        final ModuleMethod lambda$Fn21 = new ModuleMethod(this, 18, null, 0);
        final ModuleMethod lambda$Fn22 = new ModuleMethod(this, 19, null, 8194);
        LList maybe$Mnstart$Plend;
        Object proc;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 18 ? lambda21() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 19 ? lambda22(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 18) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 19) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda21() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfor$Mneach$Mnindex, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda22(Object start, Object end) {
            for (Object i = start; Scheme.numLss.apply2(i, end) != Boolean.FALSE; i = AddOp.$Pl.apply2(i, srfi13.Lit1)) {
                Scheme.applyToArgs.apply2(this.proc, i);
            }
            return Values.empty;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame9 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn23 = new ModuleMethod(this, 20, null, 0);
        final ModuleMethod lambda$Fn24 = new ModuleMethod(this, 21, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 20 ? lambda23() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 21 ? lambda24(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 20) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 21) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda23() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnevery, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda24(Object start, Object end) {
            if (characters.isChar(this.criterion)) {
                Object i = start;
                while (true) {
                    Object apply2 = Scheme.numGEq.apply2(i, end);
                    try {
                        boolean x = ((Boolean) apply2).booleanValue();
                        if (x) {
                            return x ? Boolean.TRUE : Boolean.FALSE;
                        }
                        Object obj = this.criterion;
                        try {
                            Char r5 = (Char) obj;
                            Object obj2 = this.s;
                            try {
                                try {
                                    boolean x2 = characters.isChar$Eq(r5, Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())));
                                    if (!x2) {
                                        return x2 ? Boolean.TRUE : Boolean.FALSE;
                                    }
                                    i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 1, obj);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "x", -2, apply2);
                    }
                }
            } else {
                try {
                    if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                        Object i2 = start;
                        while (true) {
                            Object apply22 = Scheme.numGEq.apply2(i2, end);
                            try {
                                boolean x3 = ((Boolean) apply22).booleanValue();
                                if (x3) {
                                    return x3 ? Boolean.TRUE : Boolean.FALSE;
                                }
                                ApplyToArgs applyToArgs = Scheme.applyToArgs;
                                try {
                                    Object obj3 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                                    Object obj4 = this.criterion;
                                    Object obj5 = this.s;
                                    try {
                                        try {
                                            Object x4 = applyToArgs.apply3(obj3, obj4, Char.make(strings.stringRef((CharSequence) obj5, ((Number) i2).intValue())));
                                            if (x4 == Boolean.FALSE) {
                                                return x4;
                                            }
                                            i2 = AddOp.$Pl.apply2(i2, srfi13.Lit1);
                                        } catch (ClassCastException e5) {
                                            throw new WrongType(e5, "string-ref", 2, i2);
                                        }
                                    } catch (ClassCastException e6) {
                                        throw new WrongType(e6, "string-ref", 1, obj5);
                                    }
                                } catch (UnboundLocationException e7) {
                                    e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 492, 9);
                                    throw e7;
                                }
                            } catch (ClassCastException e8) {
                                throw new WrongType(e8, "x", -2, apply22);
                            }
                        }
                    } else if (misc.isProcedure(this.criterion)) {
                        Object apply23 = Scheme.numEqu.apply2(start, end);
                        try {
                            boolean x5 = ((Boolean) apply23).booleanValue();
                            if (x5) {
                                return x5 ? Boolean.TRUE : Boolean.FALSE;
                            }
                            Object i3 = start;
                            while (true) {
                                Object obj6 = this.s;
                                try {
                                    try {
                                        char c = strings.stringRef((CharSequence) obj6, ((Number) i3).intValue());
                                        Object i1 = AddOp.$Pl.apply2(i3, srfi13.Lit1);
                                        if (Scheme.numEqu.apply2(i1, end) != Boolean.FALSE) {
                                            return Scheme.applyToArgs.apply2(this.criterion, Char.make(c));
                                        }
                                        Object x6 = Scheme.applyToArgs.apply2(this.criterion, Char.make(c));
                                        if (x6 == Boolean.FALSE) {
                                            return x6;
                                        }
                                        i3 = i1;
                                    } catch (ClassCastException e9) {
                                        throw new WrongType(e9, "string-ref", 2, i3);
                                    }
                                } catch (ClassCastException e10) {
                                    throw new WrongType(e10, "string-ref", 1, obj6);
                                }
                            }
                        } catch (ClassCastException e11) {
                            throw new WrongType(e11, "x", -2, apply23);
                        }
                    } else {
                        return misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnevery, this.criterion});
                    }
                } catch (UnboundLocationException e12) {
                    e12.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 489, 5);
                    throw e12;
                }
            }
        }
    }

    public static Object stringEvery$V(Object criterion, Object s, Object[] argsArray) {
        frame9 frame9Var = new frame9();
        frame9Var.criterion = criterion;
        frame9Var.s = s;
        frame9Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame9Var.lambda$Fn23, frame9Var.lambda$Fn24);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame10 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn25 = new ModuleMethod(this, 22, null, 0);
        final ModuleMethod lambda$Fn26 = new ModuleMethod(this, 23, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 22 ? lambda25() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 23 ? lambda26(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 22) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 23) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda25() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnany, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda26(Object start, Object end) {
            Boolean bool;
            if (characters.isChar(this.criterion)) {
                Object i = start;
                while (true) {
                    Object apply2 = Scheme.numLss.apply2(i, end);
                    try {
                        boolean x = ((Boolean) apply2).booleanValue();
                        if (!x) {
                            bool = x ? Boolean.TRUE : Boolean.FALSE;
                        } else {
                            Object obj = this.criterion;
                            try {
                                Char r5 = (Char) obj;
                                Object obj2 = this.s;
                                try {
                                    try {
                                        boolean x2 = characters.isChar$Eq(r5, Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())));
                                        if (x2) {
                                            bool = x2 ? Boolean.TRUE : Boolean.FALSE;
                                        } else {
                                            i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                        }
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "string-ref", 2, i);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "string-ref", 1, obj2);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "char=?", 1, obj);
                            }
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "x", -2, apply2);
                    }
                }
                return bool;
            }
            try {
                if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                    Object i2 = start;
                    while (true) {
                        Object apply22 = Scheme.numLss.apply2(i2, end);
                        try {
                            boolean x3 = ((Boolean) apply22).booleanValue();
                            if (!x3) {
                                return x3 ? Boolean.TRUE : Boolean.FALSE;
                            }
                            ApplyToArgs applyToArgs = Scheme.applyToArgs;
                            try {
                                Object obj3 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                                Object obj4 = this.criterion;
                                Object obj5 = this.s;
                                try {
                                    try {
                                        Object x4 = applyToArgs.apply3(obj3, obj4, Char.make(strings.stringRef((CharSequence) obj5, ((Number) i2).intValue())));
                                        if (x4 == Boolean.FALSE) {
                                            i2 = AddOp.$Pl.apply2(i2, srfi13.Lit1);
                                        } else {
                                            return x4;
                                        }
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "string-ref", 2, i2);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "string-ref", 1, obj5);
                                }
                            } catch (UnboundLocationException e7) {
                                e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_BLUETOOTH_END_OF_STREAM, 9);
                                throw e7;
                            }
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "x", -2, apply22);
                        }
                    }
                } else if (misc.isProcedure(this.criterion)) {
                    Object apply23 = Scheme.numLss.apply2(start, end);
                    try {
                        boolean x5 = ((Boolean) apply23).booleanValue();
                        if (!x5) {
                            return x5 ? Boolean.TRUE : Boolean.FALSE;
                        }
                        Object i3 = start;
                        while (true) {
                            Object obj6 = this.s;
                            try {
                                try {
                                    char c = strings.stringRef((CharSequence) obj6, ((Number) i3).intValue());
                                    Object i1 = AddOp.$Pl.apply2(i3, srfi13.Lit1);
                                    if (Scheme.numEqu.apply2(i1, end) != Boolean.FALSE) {
                                        return Scheme.applyToArgs.apply2(this.criterion, Char.make(c));
                                    }
                                    Object x6 = Scheme.applyToArgs.apply2(this.criterion, Char.make(c));
                                    if (x6 != Boolean.FALSE) {
                                        return x6;
                                    }
                                    i3 = i1;
                                } catch (ClassCastException e9) {
                                    throw new WrongType(e9, "string-ref", 2, i3);
                                }
                            } catch (ClassCastException e10) {
                                throw new WrongType(e10, "string-ref", 1, obj6);
                            }
                        }
                    } catch (ClassCastException e11) {
                        throw new WrongType(e11, "x", -2, apply23);
                    }
                } else {
                    return misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnany, this.criterion});
                }
            } catch (UnboundLocationException e12) {
                e12.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_BLUETOOTH_NOT_CONNECTED_TO_DEVICE, 5);
                throw e12;
            }
        }
    }

    public static Object stringAny$V(Object criterion, Object s, Object[] argsArray) {
        frame10 frame10Var = new frame10();
        frame10Var.criterion = criterion;
        frame10Var.s = s;
        frame10Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame10Var.lambda$Fn25, frame10Var.lambda$Fn26);
    }

    public static CharSequence stringTabulate(Object proc, int len) {
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, proc, string$Mntabulate);
            try {
                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), lambda$Fn27, Integer.valueOf(len), string$Mntabulate);
                CharSequence s = strings.makeString(len);
                for (int i = len - 1; i >= 0; i--) {
                    try {
                        CharSeq charSeq = (CharSeq) s;
                        Object apply2 = Scheme.applyToArgs.apply2(proc, Integer.valueOf(i));
                        try {
                            charSeq.setCharAt(i, ((Char) apply2).charValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, apply2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 1, s);
                    }
                }
                return s;
            } catch (UnboundLocationException e3) {
                e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 535, 3);
                throw e3;
            }
        } catch (UnboundLocationException e4) {
            e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 534, 3);
            throw e4;
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 218:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 280:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 281:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 282:
                if (obj instanceof CharSequence) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 283:
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

    static boolean lambda27(Object val) {
        boolean x = numbers.isInteger(val);
        if (x) {
            boolean x2 = numbers.isExact(val);
            return x2 ? ((Boolean) Scheme.numLEq.apply2(Lit0, val)).booleanValue() : x2;
        }
        return x;
    }

    public static Object $PcStringPrefixLength(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        Object delta = numbers.min(AddOp.$Mn.apply2(end1, start1), AddOp.$Mn.apply2(end2, start2));
        Object end12 = AddOp.$Pl.apply2(start1, delta);
        boolean x = s1 == s2;
        if (x) {
            if (Scheme.numEqu.apply2(start1, start2) != Boolean.FALSE) {
                return delta;
            }
        } else if (x) {
            return delta;
        }
        Object i = start1;
        Object obj = start2;
        while (true) {
            Object apply2 = Scheme.numGEq.apply2(i, end12);
            try {
                boolean x2 = ((Boolean) apply2).booleanValue();
                if (!x2) {
                    try {
                        try {
                            try {
                                try {
                                    if (!characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s1, ((Number) i).intValue())), Char.make(strings.stringRef((CharSequence) s2, ((Number) obj).intValue())))) {
                                        break;
                                    }
                                    i = AddOp.$Pl.apply2(i, Lit1);
                                    Object j = AddOp.$Pl.apply2(obj, Lit1);
                                    obj = j;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, obj);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, s2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-ref", 1, s1);
                    }
                } else if (x2) {
                    break;
                } else {
                    i = AddOp.$Pl.apply2(i, Lit1);
                    Object j2 = AddOp.$Pl.apply2(obj, Lit1);
                    obj = j2;
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "x", -2, apply2);
            }
        }
        return AddOp.$Mn.apply2(i, start1);
    }

    public static Object $PcStringSuffixLength(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        Object delta = numbers.min(AddOp.$Mn.apply2(end1, start1), AddOp.$Mn.apply2(end2, start2));
        Object start12 = AddOp.$Mn.apply2(end1, delta);
        boolean x = s1 == s2;
        if (x) {
            if (Scheme.numEqu.apply2(end1, end2) != Boolean.FALSE) {
                return delta;
            }
        } else if (x) {
            return delta;
        }
        Object i = AddOp.$Mn.apply2(end1, Lit1);
        Object apply2 = AddOp.$Mn.apply2(end2, Lit1);
        while (true) {
            Object apply22 = Scheme.numLss.apply2(i, start12);
            try {
                boolean x2 = ((Boolean) apply22).booleanValue();
                if (!x2) {
                    try {
                        try {
                            try {
                                try {
                                    if (!characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s1, ((Number) i).intValue())), Char.make(strings.stringRef((CharSequence) s2, ((Number) apply2).intValue())))) {
                                        break;
                                    }
                                    i = AddOp.$Mn.apply2(i, Lit1);
                                    Object j = AddOp.$Mn.apply2(apply2, Lit1);
                                    apply2 = j;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, apply2);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, s2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-ref", 1, s1);
                    }
                } else if (x2) {
                    break;
                } else {
                    i = AddOp.$Mn.apply2(i, Lit1);
                    Object j2 = AddOp.$Mn.apply2(apply2, Lit1);
                    apply2 = j2;
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "x", -2, apply22);
            }
        }
        return AddOp.$Mn.apply2(AddOp.$Mn.apply2(end1, i), Lit1);
    }

    public static int $PcStringPrefixLengthCi(Object s1, int start1, int end1, Object s2, int start2, int end2) {
        Object min = numbers.min(Integer.valueOf(end1 - start1), Integer.valueOf(end2 - start2));
        try {
            int delta = ((Number) min).intValue();
            int end12 = start1 + delta;
            boolean x = s1 == s2;
            if (x) {
                if (start1 == start2) {
                    return delta;
                }
            } else if (x) {
                return delta;
            }
            int i = start1;
            while (true) {
                boolean x2 = i >= end12;
                if (!x2) {
                    try {
                        try {
                            if (!unicode.isCharCi$Eq(Char.make(strings.stringRef((CharSequence) s1, i)), Char.make(strings.stringRef((CharSequence) s2, start2)))) {
                                break;
                            }
                            int j = start2 + 1;
                            i++;
                            start2 = j;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-ref", 1, s2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 1, s1);
                    }
                } else if (x2) {
                    break;
                } else {
                    int j2 = start2 + 1;
                    i++;
                    start2 = j2;
                }
            }
            return i - start1;
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "delta", -2, min);
        }
    }

    public static int $PcStringSuffixLengthCi(Object s1, int start1, int end1, Object s2, int start2, int end2) {
        Object min = numbers.min(Integer.valueOf(end1 - start1), Integer.valueOf(end2 - start2));
        try {
            int delta = ((Number) min).intValue();
            int start12 = end1 - delta;
            boolean x = s1 == s2;
            if (x) {
                if (end1 == end2) {
                    return delta;
                }
            } else if (x) {
                return delta;
            }
            int i = end1 - 1;
            int i2 = end2 - 1;
            while (true) {
                boolean x2 = i < start12;
                if (!x2) {
                    try {
                        try {
                            if (!unicode.isCharCi$Eq(Char.make(strings.stringRef((CharSequence) s1, i)), Char.make(strings.stringRef((CharSequence) s2, i2)))) {
                                break;
                            }
                            i2--;
                            i--;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-ref", 1, s2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 1, s1);
                    }
                } else if (x2) {
                    break;
                } else {
                    i2--;
                    i--;
                }
            }
            return (end1 - i) - 1;
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "delta", -2, min);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame11 extends ModuleBody {
        final ModuleMethod lambda$Fn28 = new ModuleMethod(this, 26, null, 0);
        final ModuleMethod lambda$Fn29 = new ModuleMethod(this, 27, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 26 ? lambda28() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 27 ? lambda29(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 26) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 27) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda28() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Mnlength, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda29(Object rest, Object start1, Object end1) {
            frame12 frame12Var = new frame12();
            frame12Var.staticLink = this;
            frame12Var.rest = rest;
            frame12Var.start1 = start1;
            frame12Var.end1 = end1;
            return call_with_values.callWithValues(frame12Var.lambda$Fn30, frame12Var.lambda$Fn31);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame12 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn30 = new ModuleMethod(this, 24, null, 0);
        final ModuleMethod lambda$Fn31 = new ModuleMethod(this, 25, null, 8194);
        Object rest;
        Object start1;
        frame11 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 24 ? lambda30() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 25 ? lambda31(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 24) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 25) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda30() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Mnlength, this.staticLink.s2, this.rest);
        }

        Object lambda31(Object start2, Object end2) {
            return srfi13.$PcStringPrefixLength(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    public static Object stringPrefixLength$V(Object s1, Object s2, Object[] argsArray) {
        frame11 frame11Var = new frame11();
        frame11Var.s1 = s1;
        frame11Var.s2 = s2;
        frame11Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame11Var.lambda$Fn28, frame11Var.lambda$Fn29);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame13 extends ModuleBody {
        final ModuleMethod lambda$Fn32 = new ModuleMethod(this, 30, null, 0);
        final ModuleMethod lambda$Fn33 = new ModuleMethod(this, 31, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 30 ? lambda32() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 31 ? lambda33(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 30) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 31) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda32() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Mnlength, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda33(Object rest, Object start1, Object end1) {
            frame14 frame14Var = new frame14();
            frame14Var.staticLink = this;
            frame14Var.rest = rest;
            frame14Var.start1 = start1;
            frame14Var.end1 = end1;
            return call_with_values.callWithValues(frame14Var.lambda$Fn34, frame14Var.lambda$Fn35);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame14 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn34 = new ModuleMethod(this, 28, null, 0);
        final ModuleMethod lambda$Fn35 = new ModuleMethod(this, 29, null, 8194);
        Object rest;
        Object start1;
        frame13 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 28 ? lambda34() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 29 ? lambda35(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 28) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 29) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda34() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Mnlength, this.staticLink.s2, this.rest);
        }

        Object lambda35(Object start2, Object end2) {
            return srfi13.$PcStringSuffixLength(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    public static Object stringSuffixLength$V(Object s1, Object s2, Object[] argsArray) {
        frame13 frame13Var = new frame13();
        frame13Var.s1 = s1;
        frame13Var.s2 = s2;
        frame13Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame13Var.lambda$Fn32, frame13Var.lambda$Fn33);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame15 extends ModuleBody {
        final ModuleMethod lambda$Fn36 = new ModuleMethod(this, 34, null, 0);
        final ModuleMethod lambda$Fn37 = new ModuleMethod(this, 35, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 34 ? lambda36() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 35 ? lambda37(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 34) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 35) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda36() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Mnlength$Mnci, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda37(Object rest, Object start1, Object end1) {
            frame16 frame16Var = new frame16();
            frame16Var.staticLink = this;
            frame16Var.rest = rest;
            frame16Var.start1 = start1;
            frame16Var.end1 = end1;
            return call_with_values.callWithValues(frame16Var.lambda$Fn38, frame16Var.lambda$Fn39);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame16 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn38 = new ModuleMethod(this, 32, null, 0);
        final ModuleMethod lambda$Fn39 = new ModuleMethod(this, 33, null, 8194);
        Object rest;
        Object start1;
        frame15 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 32 ? lambda38() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 33 ? Integer.valueOf(lambda39(obj, obj2)) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 32) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 33) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda38() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Mnlength$Mnci, this.staticLink.s2, this.rest);
        }

        int lambda39(Object start2, Object end2) {
            Object obj = this.staticLink.s1;
            Object obj2 = this.start1;
            try {
                int intValue = ((Number) obj2).intValue();
                Object obj3 = this.end1;
                try {
                    try {
                        try {
                            return srfi13.$PcStringPrefixLengthCi(obj, intValue, ((Number) obj3).intValue(), this.staticLink.s2, ((Number) start2).intValue(), ((Number) end2).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%string-prefix-length-ci", 5, end2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%string-prefix-length-ci", 4, start2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "%string-prefix-length-ci", 2, obj3);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "%string-prefix-length-ci", 1, obj2);
            }
        }
    }

    public static Object stringPrefixLengthCi$V(Object s1, Object s2, Object[] argsArray) {
        frame15 frame15Var = new frame15();
        frame15Var.s1 = s1;
        frame15Var.s2 = s2;
        frame15Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame15Var.lambda$Fn36, frame15Var.lambda$Fn37);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame17 extends ModuleBody {
        final ModuleMethod lambda$Fn40 = new ModuleMethod(this, 38, null, 0);
        final ModuleMethod lambda$Fn41 = new ModuleMethod(this, 39, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 38 ? lambda40() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 39 ? lambda41(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 38) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 39) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda40() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Mnlength$Mnci, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda41(Object rest, Object start1, Object end1) {
            frame18 frame18Var = new frame18();
            frame18Var.staticLink = this;
            frame18Var.rest = rest;
            frame18Var.start1 = start1;
            frame18Var.end1 = end1;
            return call_with_values.callWithValues(frame18Var.lambda$Fn42, frame18Var.lambda$Fn43);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame18 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn42 = new ModuleMethod(this, 36, null, 0);
        final ModuleMethod lambda$Fn43 = new ModuleMethod(this, 37, null, 8194);
        Object rest;
        Object start1;
        frame17 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 36 ? lambda42() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 37 ? Integer.valueOf(lambda43(obj, obj2)) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 36) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 37) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda42() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Mnlength$Mnci, this.staticLink.s2, this.rest);
        }

        int lambda43(Object start2, Object end2) {
            Object obj = this.staticLink.s1;
            Object obj2 = this.start1;
            try {
                int intValue = ((Number) obj2).intValue();
                Object obj3 = this.end1;
                try {
                    try {
                        try {
                            return srfi13.$PcStringSuffixLengthCi(obj, intValue, ((Number) obj3).intValue(), this.staticLink.s2, ((Number) start2).intValue(), ((Number) end2).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%string-suffix-length-ci", 5, end2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%string-suffix-length-ci", 4, start2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "%string-suffix-length-ci", 2, obj3);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "%string-suffix-length-ci", 1, obj2);
            }
        }
    }

    public static Object stringSuffixLengthCi$V(Object s1, Object s2, Object[] argsArray) {
        frame17 frame17Var = new frame17();
        frame17Var.s1 = s1;
        frame17Var.s2 = s2;
        frame17Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame17Var.lambda$Fn40, frame17Var.lambda$Fn41);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame19 extends ModuleBody {
        final ModuleMethod lambda$Fn44 = new ModuleMethod(this, 42, null, 0);
        final ModuleMethod lambda$Fn45 = new ModuleMethod(this, 43, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 42 ? lambda44() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 43 ? lambda45(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 42) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 43) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda44() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Qu, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda45(Object rest, Object start1, Object end1) {
            frame20 frame20Var = new frame20();
            frame20Var.staticLink = this;
            frame20Var.rest = rest;
            frame20Var.start1 = start1;
            frame20Var.end1 = end1;
            return call_with_values.callWithValues(frame20Var.lambda$Fn46, frame20Var.lambda$Fn47);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame20 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn46 = new ModuleMethod(this, 40, null, 0);
        final ModuleMethod lambda$Fn47 = new ModuleMethod(this, 41, null, 8194);
        Object rest;
        Object start1;
        frame19 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 40 ? lambda46() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 41 ? lambda47(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 40) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 41) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda46() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Qu, this.staticLink.s2, this.rest);
        }

        Object lambda47(Object start2, Object end2) {
            return srfi13.$PcStringPrefix$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    public static Object isStringPrefix$V(Object s1, Object s2, Object[] argsArray) {
        frame19 frame19Var = new frame19();
        frame19Var.s1 = s1;
        frame19Var.s2 = s2;
        frame19Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame19Var.lambda$Fn44, frame19Var.lambda$Fn45);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame21 extends ModuleBody {
        final ModuleMethod lambda$Fn48 = new ModuleMethod(this, 46, null, 0);
        final ModuleMethod lambda$Fn49 = new ModuleMethod(this, 47, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 46 ? lambda48() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 47 ? lambda49(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 46) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 47) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda48() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Qu, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda49(Object rest, Object start1, Object end1) {
            frame22 frame22Var = new frame22();
            frame22Var.staticLink = this;
            frame22Var.rest = rest;
            frame22Var.start1 = start1;
            frame22Var.end1 = end1;
            return call_with_values.callWithValues(frame22Var.lambda$Fn50, frame22Var.lambda$Fn51);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame22 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn50 = new ModuleMethod(this, 44, null, 0);
        final ModuleMethod lambda$Fn51 = new ModuleMethod(this, 45, null, 8194);
        Object rest;
        Object start1;
        frame21 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 44 ? lambda50() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 45 ? lambda51(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 44) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 45) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda50() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Qu, this.staticLink.s2, this.rest);
        }

        Object lambda51(Object start2, Object end2) {
            return srfi13.$PcStringSuffix$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    public static Object isStringSuffix$V(Object s1, Object s2, Object[] argsArray) {
        frame21 frame21Var = new frame21();
        frame21Var.s1 = s1;
        frame21Var.s2 = s2;
        frame21Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame21Var.lambda$Fn48, frame21Var.lambda$Fn49);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame23 extends ModuleBody {
        final ModuleMethod lambda$Fn52 = new ModuleMethod(this, 50, null, 0);
        final ModuleMethod lambda$Fn53 = new ModuleMethod(this, 51, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 50 ? lambda52() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 51 ? lambda53(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 50) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 51) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda52() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnprefix$Mnci$Qu, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda53(Object rest, Object start1, Object end1) {
            frame24 frame24Var = new frame24();
            frame24Var.staticLink = this;
            frame24Var.rest = rest;
            frame24Var.start1 = start1;
            frame24Var.end1 = end1;
            return call_with_values.callWithValues(frame24Var.lambda$Fn54, frame24Var.lambda$Fn55);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame24 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn54 = new ModuleMethod(this, 48, null, 0);
        final ModuleMethod lambda$Fn55 = new ModuleMethod(this, 49, null, 8194);
        Object rest;
        Object start1;
        frame23 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 48 ? lambda54() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 49 ? lambda55(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 48) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 49) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda54() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnprefix$Mnci$Qu, this.staticLink.s2, this.rest);
        }

        Object lambda55(Object start2, Object end2) {
            return srfi13.$PcStringPrefixCi$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    public static Object isStringPrefixCi$V(Object s1, Object s2, Object[] argsArray) {
        frame23 frame23Var = new frame23();
        frame23Var.s1 = s1;
        frame23Var.s2 = s2;
        frame23Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame23Var.lambda$Fn52, frame23Var.lambda$Fn53);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame25 extends ModuleBody {
        final ModuleMethod lambda$Fn56 = new ModuleMethod(this, 54, null, 0);
        final ModuleMethod lambda$Fn57 = new ModuleMethod(this, 55, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 54 ? lambda56() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 55 ? lambda57(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 54) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 55) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda56() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnsuffix$Mnci$Qu, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda57(Object rest, Object start1, Object end1) {
            frame26 frame26Var = new frame26();
            frame26Var.staticLink = this;
            frame26Var.rest = rest;
            frame26Var.start1 = start1;
            frame26Var.end1 = end1;
            return call_with_values.callWithValues(frame26Var.lambda$Fn58, frame26Var.lambda$Fn59);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame26 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn58 = new ModuleMethod(this, 52, null, 0);
        final ModuleMethod lambda$Fn59 = new ModuleMethod(this, 53, null, 8194);
        Object rest;
        Object start1;
        frame25 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 52 ? lambda58() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 53 ? lambda59(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 52) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 53) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda58() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnsuffix$Mnci$Qu, this.staticLink.s2, this.rest);
        }

        Object lambda59(Object start2, Object end2) {
            return srfi13.$PcStringSuffixCi$Qu(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2);
        }
    }

    public static Object isStringSuffixCi$V(Object s1, Object s2, Object[] argsArray) {
        frame25 frame25Var = new frame25();
        frame25Var.s1 = s1;
        frame25Var.s2 = s2;
        frame25Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame25Var.lambda$Fn56, frame25Var.lambda$Fn57);
    }

    public static Object $PcStringPrefix$Qu(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        Object len1 = AddOp.$Mn.apply2(end1, start1);
        Object apply2 = Scheme.numLEq.apply2(len1, AddOp.$Mn.apply2(end2, start2));
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (x) {
                return Scheme.numEqu.apply2($PcStringPrefixLength(s1, start1, end1, s2, start2, end2), len1);
            }
            return x ? Boolean.TRUE : Boolean.FALSE;
        } catch (ClassCastException e) {
            throw new WrongType(e, "x", -2, apply2);
        }
    }

    public static Object $PcStringSuffix$Qu(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        Object len1 = AddOp.$Mn.apply2(end1, start1);
        Object apply2 = Scheme.numLEq.apply2(len1, AddOp.$Mn.apply2(end2, start2));
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (x) {
                return Scheme.numEqu.apply2(len1, $PcStringSuffixLength(s1, start1, end1, s2, start2, end2));
            }
            return x ? Boolean.TRUE : Boolean.FALSE;
        } catch (ClassCastException e) {
            throw new WrongType(e, "x", -2, apply2);
        }
    }

    public static Object $PcStringPrefixCi$Qu(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        Object len1 = AddOp.$Mn.apply2(end1, start1);
        Object apply2 = Scheme.numLEq.apply2(len1, AddOp.$Mn.apply2(end2, start2));
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            }
            try {
                try {
                    try {
                        try {
                            return Scheme.numEqu.apply2(len1, Integer.valueOf($PcStringPrefixLengthCi(s1, ((Number) start1).intValue(), ((Number) end1).intValue(), s2, ((Number) start2).intValue(), ((Number) end2).intValue())));
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%string-prefix-length-ci", 5, end2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%string-prefix-length-ci", 4, start2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "%string-prefix-length-ci", 2, end1);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "%string-prefix-length-ci", 1, start1);
            }
        } catch (ClassCastException e5) {
            throw new WrongType(e5, "x", -2, apply2);
        }
    }

    public static Object $PcStringSuffixCi$Qu(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2) {
        Object len1 = AddOp.$Mn.apply2(end1, start1);
        Object apply2 = Scheme.numLEq.apply2(len1, AddOp.$Mn.apply2(end2, start2));
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            }
            try {
                try {
                    try {
                        try {
                            return Scheme.numEqu.apply2(len1, Integer.valueOf($PcStringSuffixLengthCi(s1, ((Number) start1).intValue(), ((Number) end1).intValue(), s2, ((Number) start2).intValue(), ((Number) end2).intValue())));
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%string-suffix-length-ci", 5, end2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%string-suffix-length-ci", 4, start2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "%string-suffix-length-ci", 2, end1);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "%string-suffix-length-ci", 1, start1);
            }
        } catch (ClassCastException e5) {
            throw new WrongType(e5, "x", -2, apply2);
        }
    }

    public static Object $PcStringCompare(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2, Object proc$Ls, Object proc$Eq, Object proc$Gr) {
        Object size1 = AddOp.$Mn.apply2(end1, start1);
        Object size2 = AddOp.$Mn.apply2(end2, start2);
        Object match = $PcStringPrefixLength(s1, start1, end1, s2, start2, end2);
        if (Scheme.numEqu.apply2(match, size1) != Boolean.FALSE) {
            ApplyToArgs applyToArgs = Scheme.applyToArgs;
            if (Scheme.numEqu.apply2(match, size2) == Boolean.FALSE) {
                proc$Eq = proc$Ls;
            }
            return applyToArgs.apply2(proc$Eq, end1);
        }
        ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
        if (Scheme.numEqu.apply2(match, size2) == Boolean.FALSE) {
            try {
                CharSequence charSequence = (CharSequence) s1;
                Object apply2 = AddOp.$Pl.apply2(start1, match);
                try {
                    Char make = Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue()));
                    try {
                        CharSequence charSequence2 = (CharSequence) s2;
                        Object apply22 = AddOp.$Pl.apply2(start2, match);
                        try {
                            if (characters.isChar$Ls(make, Char.make(strings.stringRef(charSequence2, ((Number) apply22).intValue())))) {
                                proc$Gr = proc$Ls;
                            }
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-ref", 2, apply22);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 1, s2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-ref", 2, apply2);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "string-ref", 1, s1);
            }
        }
        return applyToArgs2.apply2(proc$Gr, AddOp.$Pl.apply2(match, start1));
    }

    public static Object $PcStringCompareCi(Object s1, Object start1, Object end1, Object s2, Object start2, Object end2, Object proc$Ls, Object proc$Eq, Object proc$Gr) {
        Object size1 = AddOp.$Mn.apply2(end1, start1);
        Object size2 = AddOp.$Mn.apply2(end2, start2);
        try {
            try {
                try {
                    try {
                        int match = $PcStringPrefixLengthCi(s1, ((Number) start1).intValue(), ((Number) end1).intValue(), s2, ((Number) start2).intValue(), ((Number) end2).intValue());
                        if (Scheme.numEqu.apply2(Integer.valueOf(match), size1) != Boolean.FALSE) {
                            ApplyToArgs applyToArgs = Scheme.applyToArgs;
                            if (Scheme.numEqu.apply2(Integer.valueOf(match), size2) == Boolean.FALSE) {
                                proc$Eq = proc$Ls;
                            }
                            return applyToArgs.apply2(proc$Eq, end1);
                        }
                        ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
                        if (Scheme.numEqu.apply2(Integer.valueOf(match), size2) == Boolean.FALSE) {
                            try {
                                CharSequence charSequence = (CharSequence) s1;
                                Object apply2 = AddOp.$Pl.apply2(start1, Integer.valueOf(match));
                                try {
                                    Char make = Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue()));
                                    try {
                                        CharSequence charSequence2 = (CharSequence) s2;
                                        Object apply22 = AddOp.$Pl.apply2(start2, Integer.valueOf(match));
                                        try {
                                            if (unicode.isCharCi$Ls(make, Char.make(strings.stringRef(charSequence2, ((Number) apply22).intValue())))) {
                                                proc$Gr = proc$Ls;
                                            }
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "string-ref", 2, apply22);
                                        }
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "string-ref", 1, s2);
                                    }
                                } catch (ClassCastException e3) {
                                    throw new WrongType(e3, "string-ref", 2, apply2);
                                }
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "string-ref", 1, s1);
                            }
                        }
                        return applyToArgs2.apply2(proc$Gr, AddOp.$Pl.apply2(start1, Integer.valueOf(match)));
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "%string-prefix-length-ci", 5, end2);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "%string-prefix-length-ci", 4, start2);
                }
            } catch (ClassCastException e7) {
                throw new WrongType(e7, "%string-prefix-length-ci", 2, end1);
            }
        } catch (ClassCastException e8) {
            throw new WrongType(e8, "%string-prefix-length-ci", 1, start1);
        }
    }

    public static Object stringCompare$V(Object s1, Object s2, Object proc$Ls, Object proc$Eq, Object proc$Gr, Object[] argsArray) {
        frame27 frame27Var = new frame27();
        frame27Var.s1 = s1;
        frame27Var.s2 = s2;
        frame27Var.proc$Ls = proc$Ls;
        frame27Var.proc$Eq = proc$Eq;
        frame27Var.proc$Gr = proc$Gr;
        frame27Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame27Var.proc$Ls, string$Mncompare);
            try {
                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame27Var.proc$Eq, string$Mncompare);
                try {
                    Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame27Var.proc$Gr, string$Mncompare);
                    return call_with_values.callWithValues(frame27Var.lambda$Fn60, frame27Var.lambda$Fn61);
                } catch (UnboundLocationException e) {
                    e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 728, 3);
                    throw e;
                }
            } catch (UnboundLocationException e2) {
                e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 727, 3);
                throw e2;
            }
        } catch (UnboundLocationException e3) {
            e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 726, 3);
            throw e3;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame27 extends ModuleBody {
        final ModuleMethod lambda$Fn60 = new ModuleMethod(this, 58, null, 0);
        final ModuleMethod lambda$Fn61 = new ModuleMethod(this, 59, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object proc$Eq;
        Object proc$Gr;
        Object proc$Ls;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 58 ? lambda60() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 59 ? lambda61(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 58) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 59) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda60() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mncompare, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda61(Object rest, Object start1, Object end1) {
            frame28 frame28Var = new frame28();
            frame28Var.staticLink = this;
            frame28Var.rest = rest;
            frame28Var.start1 = start1;
            frame28Var.end1 = end1;
            return call_with_values.callWithValues(frame28Var.lambda$Fn62, frame28Var.lambda$Fn63);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame28 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn62 = new ModuleMethod(this, 56, null, 0);
        final ModuleMethod lambda$Fn63 = new ModuleMethod(this, 57, null, 8194);
        Object rest;
        Object start1;
        frame27 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 56 ? lambda62() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 57 ? lambda63(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 56) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 57) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda62() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncompare, this.staticLink.s2, this.rest);
        }

        Object lambda63(Object start2, Object end2) {
            return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, this.staticLink.proc$Ls, this.staticLink.proc$Eq, this.staticLink.proc$Gr);
        }
    }

    public static Object stringCompareCi$V(Object s1, Object s2, Object proc$Ls, Object proc$Eq, Object proc$Gr, Object[] argsArray) {
        frame29 frame29Var = new frame29();
        frame29Var.s1 = s1;
        frame29Var.s2 = s2;
        frame29Var.proc$Ls = proc$Ls;
        frame29Var.proc$Eq = proc$Eq;
        frame29Var.proc$Gr = proc$Gr;
        frame29Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame29Var.proc$Ls, string$Mncompare$Mnci);
            try {
                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame29Var.proc$Eq, string$Mncompare$Mnci);
                try {
                    Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), misc.procedure$Qu, frame29Var.proc$Gr, string$Mncompare$Mnci);
                    return call_with_values.callWithValues(frame29Var.lambda$Fn64, frame29Var.lambda$Fn65);
                } catch (UnboundLocationException e) {
                    e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 736, 3);
                    throw e;
                }
            } catch (UnboundLocationException e2) {
                e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 735, 3);
                throw e2;
            }
        } catch (UnboundLocationException e3) {
            e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 734, 3);
            throw e3;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame29 extends ModuleBody {
        final ModuleMethod lambda$Fn64 = new ModuleMethod(this, 62, null, 0);
        final ModuleMethod lambda$Fn65 = new ModuleMethod(this, 63, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object proc$Eq;
        Object proc$Gr;
        Object proc$Ls;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 62 ? lambda64() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 63 ? lambda65(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 62) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 63) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda64() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mncompare$Mnci, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda65(Object rest, Object start1, Object end1) {
            frame30 frame30Var = new frame30();
            frame30Var.staticLink = this;
            frame30Var.rest = rest;
            frame30Var.start1 = start1;
            frame30Var.end1 = end1;
            return call_with_values.callWithValues(frame30Var.lambda$Fn66, frame30Var.lambda$Fn67);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame30 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn66 = new ModuleMethod(this, 60, null, 0);
        final ModuleMethod lambda$Fn67 = new ModuleMethod(this, 61, null, 8194);
        Object rest;
        Object start1;
        frame29 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 60 ? lambda66() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 61 ? lambda67(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 60) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 61) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda66() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncompare$Mnci, this.staticLink.s2, this.rest);
        }

        Object lambda67(Object start2, Object end2) {
            return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, this.staticLink.proc$Ls, this.staticLink.proc$Eq, this.staticLink.proc$Gr);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame31 extends ModuleBody {
        final ModuleMethod lambda$Fn68 = new ModuleMethod(this, 66, null, 0);
        final ModuleMethod lambda$Fn69 = new ModuleMethod(this, 67, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 66 ? lambda68() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 67 ? lambda69(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 66) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 67) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda68() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda69(Object rest, Object start1, Object end1) {
            frame32 frame32Var = new frame32();
            frame32Var.staticLink = this;
            frame32Var.rest = rest;
            frame32Var.start1 = start1;
            frame32Var.end1 = end1;
            return call_with_values.callWithValues(frame32Var.lambda$Fn70, frame32Var.lambda$Fn71);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame32 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn70 = new ModuleMethod(this, 64, null, 0);
        final ModuleMethod lambda$Fn71 = new ModuleMethod(this, 65, null, 8194);
        Object rest;
        Object start1;
        frame31 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 64 ? lambda70() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 65 ? lambda71(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 64) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 65) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda70() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda71(Object start2, Object end2) {
            Object apply2 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(end2, start2));
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                boolean x2 = this.staticLink.s1 == this.staticLink.s2;
                if (x2) {
                    Object apply22 = Scheme.numEqu.apply2(this.start1, start2);
                    try {
                        x2 = ((Boolean) apply22).booleanValue();
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "x", -2, apply22);
                    }
                }
                return x2 ? x2 ? Boolean.TRUE : Boolean.FALSE : srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn72, misc.values, srfi13.lambda$Fn73);
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "x", -2, apply2);
            }
        }

        static Boolean lambda72(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda73(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object string$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame31 frame31Var = new frame31();
        frame31Var.s1 = s1;
        frame31Var.s2 = s2;
        frame31Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame31Var.lambda$Fn68, frame31Var.lambda$Fn69);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame33 extends ModuleBody {
        final ModuleMethod lambda$Fn74 = new ModuleMethod(this, 70, null, 0);
        final ModuleMethod lambda$Fn75 = new ModuleMethod(this, 71, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 70 ? lambda74() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 71 ? lambda75(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 70) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 71) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda74() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Ls$Gr, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda75(Object rest, Object start1, Object end1) {
            frame34 frame34Var = new frame34();
            frame34Var.staticLink = this;
            frame34Var.rest = rest;
            frame34Var.start1 = start1;
            frame34Var.end1 = end1;
            return call_with_values.callWithValues(frame34Var.lambda$Fn76, frame34Var.lambda$Fn77);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame34 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn76 = new ModuleMethod(this, 68, null, 0);
        final ModuleMethod lambda$Fn77 = new ModuleMethod(this, 69, null, 8194);
        Object rest;
        Object start1;
        frame33 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 68 ? lambda76() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 69 ? lambda77(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 68) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 69) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda76() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Ls$Gr, this.staticLink.s2, this.rest);
        }

        Object lambda77(Object start2, Object end2) {
            int i = 1;
            Object apply2 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(end2, start2));
            try {
                int i2 = ((apply2 != Boolean.FALSE ? 1 : 0) + 1) & 1;
                if (i2 != 0) {
                    return i2 != 0 ? Boolean.TRUE : Boolean.FALSE;
                }
                int i3 = this.staticLink.s1 == this.staticLink.s2 ? 1 : 0;
                if (i3 != 0) {
                    Object apply22 = Scheme.numEqu.apply2(this.start1, start2);
                    try {
                        if (apply22 == Boolean.FALSE) {
                            i = 0;
                        }
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "x", -2, apply22);
                    }
                } else {
                    i = i3;
                }
                int i4 = (i + 1) & 1;
                return i4 != 0 ? srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, srfi13.lambda$Fn78, misc.values) : i4 != 0 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "x", -2, apply2);
            }
        }

        static Boolean lambda78(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object string$Ls$Gr$V(Object s1, Object s2, Object[] argsArray) {
        frame33 frame33Var = new frame33();
        frame33Var.s1 = s1;
        frame33Var.s2 = s2;
        frame33Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame33Var.lambda$Fn74, frame33Var.lambda$Fn75);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame35 extends ModuleBody {
        final ModuleMethod lambda$Fn79 = new ModuleMethod(this, 74, null, 0);
        final ModuleMethod lambda$Fn80 = new ModuleMethod(this, 75, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 74 ? lambda79() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 75 ? lambda80(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 74) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 75) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda79() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Ls, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda80(Object rest, Object start1, Object end1) {
            frame36 frame36Var = new frame36();
            frame36Var.staticLink = this;
            frame36Var.rest = rest;
            frame36Var.start1 = start1;
            frame36Var.end1 = end1;
            return call_with_values.callWithValues(frame36Var.lambda$Fn81, frame36Var.lambda$Fn82);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame36 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn81 = new ModuleMethod(this, 72, null, 0);
        final ModuleMethod lambda$Fn82 = new ModuleMethod(this, 73, null, 8194);
        Object rest;
        Object start1;
        frame35 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 72 ? lambda81() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 73 ? lambda82(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 72) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 73) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda81() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Ls, this.staticLink.s2, this.rest);
        }

        Object lambda82(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (!x ? x : Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE) {
                return Scheme.numLss.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, srfi13.lambda$Fn83, srfi13.lambda$Fn84);
        }

        static Boolean lambda83(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda84(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object string$Ls$V(Object s1, Object s2, Object[] argsArray) {
        frame35 frame35Var = new frame35();
        frame35Var.s1 = s1;
        frame35Var.s2 = s2;
        frame35Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame35Var.lambda$Fn79, frame35Var.lambda$Fn80);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame37 extends ModuleBody {
        final ModuleMethod lambda$Fn85 = new ModuleMethod(this, 78, null, 0);
        final ModuleMethod lambda$Fn86 = new ModuleMethod(this, 79, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 78 ? lambda85() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 79 ? lambda86(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 78) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 79) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda85() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Gr, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda86(Object rest, Object start1, Object end1) {
            frame38 frame38Var = new frame38();
            frame38Var.staticLink = this;
            frame38Var.rest = rest;
            frame38Var.start1 = start1;
            frame38Var.end1 = end1;
            return call_with_values.callWithValues(frame38Var.lambda$Fn87, frame38Var.lambda$Fn88);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame38 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn87 = new ModuleMethod(this, 76, null, 0);
        final ModuleMethod lambda$Fn88 = new ModuleMethod(this, 77, null, 8194);
        Object rest;
        Object start1;
        frame37 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 76 ? lambda87() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 77 ? lambda88(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 76) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 77) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda87() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Gr, this.staticLink.s2, this.rest);
        }

        Object lambda88(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (!x ? x : Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE) {
                return Scheme.numGrt.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn89, srfi13.lambda$Fn90, misc.values);
        }

        static Boolean lambda89(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda90(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object string$Gr$V(Object s1, Object s2, Object[] argsArray) {
        frame37 frame37Var = new frame37();
        frame37Var.s1 = s1;
        frame37Var.s2 = s2;
        frame37Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame37Var.lambda$Fn85, frame37Var.lambda$Fn86);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame39 extends ModuleBody {
        final ModuleMethod lambda$Fn91 = new ModuleMethod(this, 82, null, 0);
        final ModuleMethod lambda$Fn92 = new ModuleMethod(this, 83, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 82 ? lambda91() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 83 ? lambda92(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 82) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 83) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda91() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Ls$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda92(Object rest, Object start1, Object end1) {
            frame40 frame40Var = new frame40();
            frame40Var.staticLink = this;
            frame40Var.rest = rest;
            frame40Var.start1 = start1;
            frame40Var.end1 = end1;
            return call_with_values.callWithValues(frame40Var.lambda$Fn93, frame40Var.lambda$Fn94);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame40 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn93 = new ModuleMethod(this, 80, null, 0);
        final ModuleMethod lambda$Fn94 = new ModuleMethod(this, 81, null, 8194);
        Object rest;
        Object start1;
        frame39 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 80 ? lambda93() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 81 ? lambda94(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 80) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 81) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda93() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Ls$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda94(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (!x ? x : Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE) {
                return Scheme.numLEq.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, misc.values, srfi13.lambda$Fn95);
        }

        static Boolean lambda95(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object string$Ls$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame39 frame39Var = new frame39();
        frame39Var.s1 = s1;
        frame39Var.s2 = s2;
        frame39Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame39Var.lambda$Fn91, frame39Var.lambda$Fn92);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame41 extends ModuleBody {
        final ModuleMethod lambda$Fn96 = new ModuleMethod(this, 86, null, 0);
        final ModuleMethod lambda$Fn97 = new ModuleMethod(this, 87, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 86 ? lambda96() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 87 ? lambda97(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 86) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 87) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda96() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Gr$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda97(Object rest, Object start1, Object end1) {
            frame42 frame42Var = new frame42();
            frame42Var.staticLink = this;
            frame42Var.rest = rest;
            frame42Var.start1 = start1;
            frame42Var.end1 = end1;
            return call_with_values.callWithValues(frame42Var.lambda$Fn98, frame42Var.lambda$Fn99);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame42 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn98 = new ModuleMethod(this, 84, null, 0);
        final ModuleMethod lambda$Fn99 = new ModuleMethod(this, 85, null, 8194);
        Object rest;
        Object start1;
        frame41 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 84 ? lambda98() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 85 ? lambda99(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 84) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 85) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda98() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Gr$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda99(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (!x ? x : Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE) {
                return Scheme.numGEq.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompare(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn100, misc.values, misc.values);
        }

        static Boolean lambda100(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object string$Gr$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame41 frame41Var = new frame41();
        frame41Var.s1 = s1;
        frame41Var.s2 = s2;
        frame41Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame41Var.lambda$Fn96, frame41Var.lambda$Fn97);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame43 extends ModuleBody {
        final ModuleMethod lambda$Fn101 = new ModuleMethod(this, 90, null, 0);
        final ModuleMethod lambda$Fn102 = new ModuleMethod(this, 91, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 90 ? lambda101() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 91 ? lambda102(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 90) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 91) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda101() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda102(Object rest, Object start1, Object end1) {
            frame44 frame44Var = new frame44();
            frame44Var.staticLink = this;
            frame44Var.rest = rest;
            frame44Var.start1 = start1;
            frame44Var.end1 = end1;
            return call_with_values.callWithValues(frame44Var.lambda$Fn103, frame44Var.lambda$Fn104);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame44 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn103 = new ModuleMethod(this, 88, null, 0);
        final ModuleMethod lambda$Fn104 = new ModuleMethod(this, 89, null, 8194);
        Object rest;
        Object start1;
        frame43 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 88 ? lambda103() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 89 ? lambda104(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 88) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 89) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda103() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda104(Object start2, Object end2) {
            Object apply2 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(end2, start2));
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                boolean x2 = this.staticLink.s1 == this.staticLink.s2;
                if (x2) {
                    Object apply22 = Scheme.numEqu.apply2(this.start1, start2);
                    try {
                        x2 = ((Boolean) apply22).booleanValue();
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "x", -2, apply22);
                    }
                }
                return x2 ? x2 ? Boolean.TRUE : Boolean.FALSE : srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn105, misc.values, srfi13.lambda$Fn106);
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "x", -2, apply2);
            }
        }

        static Boolean lambda105(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda106(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object stringCi$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame43 frame43Var = new frame43();
        frame43Var.s1 = s1;
        frame43Var.s2 = s2;
        frame43Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame43Var.lambda$Fn101, frame43Var.lambda$Fn102);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame45 extends ModuleBody {
        final ModuleMethod lambda$Fn107 = new ModuleMethod(this, 94, null, 0);
        final ModuleMethod lambda$Fn108 = new ModuleMethod(this, 95, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 94 ? lambda107() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 95 ? lambda108(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 94) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 95) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda107() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Ls$Gr, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda108(Object rest, Object start1, Object end1) {
            frame46 frame46Var = new frame46();
            frame46Var.staticLink = this;
            frame46Var.rest = rest;
            frame46Var.start1 = start1;
            frame46Var.end1 = end1;
            return call_with_values.callWithValues(frame46Var.lambda$Fn109, frame46Var.lambda$Fn110);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame46 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn109 = new ModuleMethod(this, 92, null, 0);
        final ModuleMethod lambda$Fn110 = new ModuleMethod(this, 93, null, 8194);
        Object rest;
        Object start1;
        frame45 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 92 ? lambda109() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 93 ? lambda110(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 92) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 93) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda109() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Ls$Gr, this.staticLink.s2, this.rest);
        }

        Object lambda110(Object start2, Object end2) {
            int i = 1;
            Object apply2 = Scheme.numEqu.apply2(AddOp.$Mn.apply2(this.end1, this.start1), AddOp.$Mn.apply2(end2, start2));
            try {
                int i2 = ((apply2 != Boolean.FALSE ? 1 : 0) + 1) & 1;
                if (i2 != 0) {
                    return i2 != 0 ? Boolean.TRUE : Boolean.FALSE;
                }
                int i3 = this.staticLink.s1 == this.staticLink.s2 ? 1 : 0;
                if (i3 != 0) {
                    Object apply22 = Scheme.numEqu.apply2(this.start1, start2);
                    try {
                        if (apply22 == Boolean.FALSE) {
                            i = 0;
                        }
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "x", -2, apply22);
                    }
                } else {
                    i = i3;
                }
                int i4 = (i + 1) & 1;
                return i4 != 0 ? srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, srfi13.lambda$Fn111, misc.values) : i4 != 0 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "x", -2, apply2);
            }
        }

        static Boolean lambda111(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object stringCi$Ls$Gr$V(Object s1, Object s2, Object[] argsArray) {
        frame45 frame45Var = new frame45();
        frame45Var.s1 = s1;
        frame45Var.s2 = s2;
        frame45Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame45Var.lambda$Fn107, frame45Var.lambda$Fn108);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame47 extends ModuleBody {
        final ModuleMethod lambda$Fn112 = new ModuleMethod(this, 98, null, 0);
        final ModuleMethod lambda$Fn113 = new ModuleMethod(this, 99, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 98 ? lambda112() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 99 ? lambda113(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 98) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 99) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda112() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Ls, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda113(Object rest, Object start1, Object end1) {
            frame48 frame48Var = new frame48();
            frame48Var.staticLink = this;
            frame48Var.rest = rest;
            frame48Var.start1 = start1;
            frame48Var.end1 = end1;
            return call_with_values.callWithValues(frame48Var.lambda$Fn114, frame48Var.lambda$Fn115);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame48 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn114 = new ModuleMethod(this, 96, null, 0);
        final ModuleMethod lambda$Fn115 = new ModuleMethod(this, 97, null, 8194);
        Object rest;
        Object start1;
        frame47 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 96 ? lambda114() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 97 ? lambda115(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 96) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 97) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda114() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Ls, this.staticLink.s2, this.rest);
        }

        Object lambda115(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (!x ? x : Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE) {
                return Scheme.numLss.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, srfi13.lambda$Fn116, srfi13.lambda$Fn117);
        }

        static Boolean lambda116(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda117(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object stringCi$Ls$V(Object s1, Object s2, Object[] argsArray) {
        frame47 frame47Var = new frame47();
        frame47Var.s1 = s1;
        frame47Var.s2 = s2;
        frame47Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame47Var.lambda$Fn112, frame47Var.lambda$Fn113);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame49 extends ModuleBody {
        final ModuleMethod lambda$Fn118 = new ModuleMethod(this, 102, null, 0);
        final ModuleMethod lambda$Fn119 = new ModuleMethod(this, 103, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 102 ? lambda118() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 103 ? lambda119(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 102) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 103) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda118() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Gr, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda119(Object rest, Object start1, Object end1) {
            frame50 frame50Var = new frame50();
            frame50Var.staticLink = this;
            frame50Var.rest = rest;
            frame50Var.start1 = start1;
            frame50Var.end1 = end1;
            return call_with_values.callWithValues(frame50Var.lambda$Fn120, frame50Var.lambda$Fn121);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame50 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn120 = new ModuleMethod(this, 100, null, 0);
        final ModuleMethod lambda$Fn121 = new ModuleMethod(this, 101, null, 8194);
        Object rest;
        Object start1;
        frame49 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 100 ? lambda120() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 101 ? lambda121(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 100) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 101) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda120() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Gr, this.staticLink.s2, this.rest);
        }

        Object lambda121(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (!x ? x : Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE) {
                return Scheme.numGrt.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn122, srfi13.lambda$Fn123, misc.values);
        }

        static Boolean lambda122(Object i) {
            return Boolean.FALSE;
        }

        static Boolean lambda123(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object stringCi$Gr$V(Object s1, Object s2, Object[] argsArray) {
        frame49 frame49Var = new frame49();
        frame49Var.s1 = s1;
        frame49Var.s2 = s2;
        frame49Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame49Var.lambda$Fn118, frame49Var.lambda$Fn119);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame51 extends ModuleBody {
        final ModuleMethod lambda$Fn124 = new ModuleMethod(this, 106, null, 0);
        final ModuleMethod lambda$Fn125 = new ModuleMethod(this, 107, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 106 ? lambda124() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 107 ? lambda125(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 106) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 107) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda124() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Ls$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda125(Object rest, Object start1, Object end1) {
            frame52 frame52Var = new frame52();
            frame52Var.staticLink = this;
            frame52Var.rest = rest;
            frame52Var.start1 = start1;
            frame52Var.end1 = end1;
            return call_with_values.callWithValues(frame52Var.lambda$Fn126, frame52Var.lambda$Fn127);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame52 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn126 = new ModuleMethod(this, 104, null, 0);
        final ModuleMethod lambda$Fn127 = new ModuleMethod(this, 105, null, 8194);
        Object rest;
        Object start1;
        frame51 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 104 ? lambda126() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 105 ? lambda127(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 104) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 105) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda126() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Ls$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda127(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (!x ? x : Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE) {
                return Scheme.numLEq.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, misc.values, misc.values, srfi13.lambda$Fn128);
        }

        static Boolean lambda128(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object stringCi$Ls$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame51 frame51Var = new frame51();
        frame51Var.s1 = s1;
        frame51Var.s2 = s2;
        frame51Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame51Var.lambda$Fn124, frame51Var.lambda$Fn125);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame53 extends ModuleBody {
        final ModuleMethod lambda$Fn129 = new ModuleMethod(this, 110, null, 0);
        final ModuleMethod lambda$Fn130 = new ModuleMethod(this, 111, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object s1;
        Object s2;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 110 ? lambda129() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 111 ? lambda130(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 110) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 111) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda129() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnci$Gr$Eq, this.s1, this.maybe$Mnstarts$Plends);
        }

        Object lambda130(Object rest, Object start1, Object end1) {
            frame54 frame54Var = new frame54();
            frame54Var.staticLink = this;
            frame54Var.rest = rest;
            frame54Var.start1 = start1;
            frame54Var.end1 = end1;
            return call_with_values.callWithValues(frame54Var.lambda$Fn131, frame54Var.lambda$Fn132);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame54 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn131 = new ModuleMethod(this, 108, null, 0);
        final ModuleMethod lambda$Fn132 = new ModuleMethod(this, 109, null, 8194);
        Object rest;
        Object start1;
        frame53 staticLink;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 108 ? lambda131() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 109 ? lambda132(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 108) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 109) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda131() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnci$Gr$Eq, this.staticLink.s2, this.rest);
        }

        Object lambda132(Object start2, Object end2) {
            boolean x = this.staticLink.s1 == this.staticLink.s2;
            if (!x ? x : Scheme.numEqu.apply2(this.start1, start2) != Boolean.FALSE) {
                return Scheme.numGEq.apply2(this.end1, end2);
            }
            return srfi13.$PcStringCompareCi(this.staticLink.s1, this.start1, this.end1, this.staticLink.s2, start2, end2, srfi13.lambda$Fn133, misc.values, misc.values);
        }

        static Boolean lambda133(Object i) {
            return Boolean.FALSE;
        }
    }

    public static Object stringCi$Gr$Eq$V(Object s1, Object s2, Object[] argsArray) {
        frame53 frame53Var = new frame53();
        frame53Var.s1 = s1;
        frame53Var.s2 = s2;
        frame53Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame53Var.lambda$Fn129, frame53Var.lambda$Fn130);
    }

    public static Object $PcStringHash(Object s, Object char$To$Int, Object bound, Object start, Object end) {
        frame55 closureEnv = new frame55();
        closureEnv.char$Mn$Grint = char$To$Int;
        Object obj = Lit5;
        while (Scheme.numGEq.apply2(obj, bound) == Boolean.FALSE) {
            obj = AddOp.$Pl.apply2(obj, obj);
        }
        Object mask = AddOp.$Mn.apply2(obj, Lit1);
        Object obj2 = Lit0;
        Object i = start;
        while (Scheme.numGEq.apply2(i, end) == Boolean.FALSE) {
            Object i2 = AddOp.$Pl.apply2(i, Lit1);
            try {
                try {
                    obj2 = BitwiseOp.and.apply2(mask, AddOp.$Pl.apply2(MultiplyOp.$St.apply2(Lit6, obj2), Scheme.applyToArgs.apply2(closureEnv.char$Mn$Grint, Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())))));
                    i = i2;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, i);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, s);
            }
        }
        return DivideOp.modulo.apply2(obj2, bound);
    }

    public static Object stringHash$V(Object s, Object[] argsArray) {
        Object obj;
        Object obj2;
        Object obj3;
        frame56 frame56Var = new frame56();
        frame56Var.s = s;
        LList maybe$Mnbound$Plstart$Plend = LList.makeList(argsArray, 0);
        ApplyToArgs applyToArgs = Scheme.applyToArgs;
        try {
            Object obj4 = loc$let$Mnoptionals$St.get();
            ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
            ApplyToArgs applyToArgs3 = Scheme.applyToArgs;
            try {
                Object obj5 = loc$bound.get();
                IntNum intNum = Lit7;
                try {
                    boolean x = numbers.isInteger(loc$bound.get());
                    if (x) {
                        try {
                            boolean x2 = numbers.isExact(loc$bound.get());
                            if (x2) {
                                try {
                                    obj = Scheme.numLEq.apply2(Lit0, loc$bound.get());
                                } catch (UnboundLocationException e) {
                                    e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 909, 19);
                                    throw e;
                                }
                            } else {
                                obj = x2 ? Boolean.TRUE : Boolean.FALSE;
                            }
                        } catch (UnboundLocationException e2) {
                            e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_PERMISSION_DENIED, 21);
                            throw e2;
                        }
                    } else {
                        obj = x ? Boolean.TRUE : Boolean.FALSE;
                    }
                    try {
                        Object apply2 = applyToArgs2.apply2(applyToArgs3.apply3(obj5, intNum, obj), loc$rest.get());
                        try {
                            try {
                                if (numbers.isZero((Number) loc$bound.get())) {
                                    obj3 = Lit8;
                                } else {
                                    try {
                                        obj3 = loc$bound.get();
                                    } catch (UnboundLocationException e3) {
                                        e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 911, 18);
                                        throw e3;
                                    }
                                }
                                frame56Var.bound = obj3;
                                return applyToArgs.apply4(obj4, maybe$Mnbound$Plstart$Plend, apply2, call_with_values.callWithValues(frame56Var.lambda$Fn134, frame56Var.lambda$Fn135));
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "zero?", 1, obj2);
                            }
                        } catch (UnboundLocationException e5) {
                            e5.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 911, 29);
                            throw e5;
                        }
                    } catch (UnboundLocationException e6) {
                        e6.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 910, 7);
                        throw e6;
                    }
                } catch (UnboundLocationException e7) {
                    e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_ACTIONBAR_NOT_SUPPORTED, 72);
                    throw e7;
                }
            } catch (UnboundLocationException e8) {
                e8.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_ACTIONBAR_NOT_SUPPORTED, 42);
                throw e8;
            }
        } catch (UnboundLocationException e9) {
            e9.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_ACTIONBAR_NOT_SUPPORTED, 3);
            throw e9;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame56 extends ModuleBody {
        Object bound;
        final ModuleMethod lambda$Fn134 = new ModuleMethod(this, 112, null, 0);
        final ModuleMethod lambda$Fn135 = new ModuleMethod(this, 113, null, 8194);
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 112 ? lambda134() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 113 ? lambda135(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 112) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 113) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda134() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnhash, this.s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 912, 55);
                throw e;
            }
        }

        Object lambda135(Object start, Object end) {
            return srfi13.$PcStringHash(this.s, characters.char$Mn$Grinteger, this.bound, start, end);
        }
    }

    public static Object stringHashCi$V(Object s, Object[] argsArray) {
        Object obj;
        Object obj2;
        Object obj3;
        frame57 frame57Var = new frame57();
        frame57Var.s = s;
        LList maybe$Mnbound$Plstart$Plend = LList.makeList(argsArray, 0);
        ApplyToArgs applyToArgs = Scheme.applyToArgs;
        try {
            Object obj4 = loc$let$Mnoptionals$St.get();
            ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
            ApplyToArgs applyToArgs3 = Scheme.applyToArgs;
            try {
                Object obj5 = loc$bound.get();
                IntNum intNum = Lit9;
                try {
                    boolean x = numbers.isInteger(loc$bound.get());
                    if (x) {
                        try {
                            boolean x2 = numbers.isExact(loc$bound.get());
                            if (x2) {
                                try {
                                    obj = Scheme.numLEq.apply2(Lit0, loc$bound.get());
                                } catch (UnboundLocationException e) {
                                    e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 918, 19);
                                    throw e;
                                }
                            } else {
                                obj = x2 ? Boolean.TRUE : Boolean.FALSE;
                            }
                        } catch (UnboundLocationException e2) {
                            e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 917, 21);
                            throw e2;
                        }
                    } else {
                        obj = x ? Boolean.TRUE : Boolean.FALSE;
                    }
                    try {
                        Object apply2 = applyToArgs2.apply2(applyToArgs3.apply3(obj5, intNum, obj), loc$rest.get());
                        try {
                            try {
                                if (numbers.isZero((Number) loc$bound.get())) {
                                    obj3 = Lit10;
                                } else {
                                    try {
                                        obj3 = loc$bound.get();
                                    } catch (UnboundLocationException e3) {
                                        e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 920, 18);
                                        throw e3;
                                    }
                                }
                                frame57Var.bound = obj3;
                                return applyToArgs.apply4(obj4, maybe$Mnbound$Plstart$Plend, apply2, call_with_values.callWithValues(frame57Var.lambda$Fn136, frame57Var.lambda$Fn137));
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "zero?", 1, obj2);
                            }
                        } catch (UnboundLocationException e5) {
                            e5.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 920, 29);
                            throw e5;
                        }
                    } catch (UnboundLocationException e6) {
                        e6.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 919, 7);
                        throw e6;
                    }
                } catch (UnboundLocationException e7) {
                    e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 916, 72);
                    throw e7;
                }
            } catch (UnboundLocationException e8) {
                e8.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 916, 42);
                throw e8;
            }
        } catch (UnboundLocationException e9) {
            e9.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 916, 3);
            throw e9;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame57 extends ModuleBody {
        Object bound;
        final ModuleMethod lambda$Fn136 = new ModuleMethod(this, 114, null, 0);
        final ModuleMethod lambda$Fn137 = new ModuleMethod(this, 115, null, 8194);
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 114 ? lambda136() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 115 ? lambda137(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 114) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 115) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda136() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnhash$Mnci, this.s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 921, 58);
                throw e;
            }
        }

        static int lambda138(Object c) {
            try {
                return unicode.charDowncase((Char) c).intValue();
            } catch (ClassCastException e) {
                throw new WrongType(e, "char-downcase", 1, c);
            }
        }

        Object lambda137(Object start, Object end) {
            return srfi13.$PcStringHash(this.s, srfi13.lambda$Fn138, this.bound, start, end);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame58 extends ModuleBody {
        final ModuleMethod lambda$Fn139 = new ModuleMethod(this, 116, null, 0);
        final ModuleMethod lambda$Fn140 = new ModuleMethod(this, 117, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 116 ? lambda139() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 117 ? lambda140(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 116) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 117) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda139() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnupcase, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda140(Object start, Object end) {
            return srfi13.$PcStringMap(unicode.char$Mnupcase, this.s, start, end);
        }
    }

    public static Object stringUpcase$V(Object s, Object[] argsArray) {
        frame58 frame58Var = new frame58();
        frame58Var.s = s;
        frame58Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame58Var.lambda$Fn139, frame58Var.lambda$Fn140);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame59 extends ModuleBody {
        final ModuleMethod lambda$Fn141 = new ModuleMethod(this, 118, null, 0);
        final ModuleMethod lambda$Fn142 = new ModuleMethod(this, 119, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 118 ? lambda141() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 119 ? lambda142(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 118) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 119) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda141() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnupcase$Ex, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda142(Object start, Object end) {
            return srfi13.$PcStringMap$Ex(unicode.char$Mnupcase, this.s, start, end);
        }
    }

    public static Object stringUpcase$Ex$V(Object s, Object[] argsArray) {
        frame59 frame59Var = new frame59();
        frame59Var.s = s;
        frame59Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame59Var.lambda$Fn141, frame59Var.lambda$Fn142);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame60 extends ModuleBody {
        final ModuleMethod lambda$Fn143 = new ModuleMethod(this, 120, null, 0);
        final ModuleMethod lambda$Fn144 = new ModuleMethod(this, 121, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 120 ? lambda143() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 121 ? lambda144(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 120) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 121) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda143() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mndowncase, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda144(Object start, Object end) {
            return srfi13.$PcStringMap(unicode.char$Mndowncase, this.s, start, end);
        }
    }

    public static Object stringDowncase$V(Object s, Object[] argsArray) {
        frame60 frame60Var = new frame60();
        frame60Var.s = s;
        frame60Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame60Var.lambda$Fn143, frame60Var.lambda$Fn144);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame61 extends ModuleBody {
        final ModuleMethod lambda$Fn145 = new ModuleMethod(this, 122, null, 0);
        final ModuleMethod lambda$Fn146 = new ModuleMethod(this, 123, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 122 ? lambda145() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 123 ? lambda146(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 122) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 123) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda145() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mndowncase$Ex, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda146(Object start, Object end) {
            return srfi13.$PcStringMap$Ex(unicode.char$Mndowncase, this.s, start, end);
        }
    }

    public static Object stringDowncase$Ex$V(Object s, Object[] argsArray) {
        frame61 frame61Var = new frame61();
        frame61Var.s = s;
        frame61Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame61Var.lambda$Fn145, frame61Var.lambda$Fn146);
    }

    public static Object $PcStringTitlecase$Ex(Object s, Object start, Object end) {
        Object i = start;
        while (true) {
            try {
                Object temp = stringIndex$V(s, loc$char$Mncased$Qu.get(), new Object[]{i, end});
                if (temp == Boolean.FALSE) {
                    return Values.empty;
                }
                try {
                    CharSeq charSeq = (CharSeq) s;
                    try {
                        int intValue = ((Number) temp).intValue();
                        try {
                            try {
                                Char charTitlecase = unicode.charTitlecase(Char.make(strings.stringRef((CharSequence) s, ((Number) temp).intValue())));
                                try {
                                    charSeq.setCharAt(intValue, charTitlecase.charValue());
                                    Object i1 = AddOp.$Pl.apply2(temp, Lit1);
                                    try {
                                        Object temp2 = stringSkip$V(s, loc$char$Mncased$Qu.get(), new Object[]{i1, end});
                                        if (temp2 == Boolean.FALSE) {
                                            return stringDowncase$Ex$V(s, new Object[]{i1, end});
                                        }
                                        stringDowncase$Ex$V(s, new Object[]{i1, temp2});
                                        i = AddOp.$Pl.apply2(temp2, Lit1);
                                    } catch (UnboundLocationException e) {
                                        e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 959, 31);
                                        throw e;
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "string-set!", 3, charTitlecase);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-ref", 2, temp);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-ref", 1, s);
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "string-set!", 2, temp);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "string-set!", 1, s);
                }
            } catch (UnboundLocationException e7) {
                e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 955, 28);
                throw e7;
            }
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame62 extends ModuleBody {
        final ModuleMethod lambda$Fn147 = new ModuleMethod(this, 124, null, 0);
        final ModuleMethod lambda$Fn148 = new ModuleMethod(this, 125, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 124 ? lambda147() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 125 ? lambda148(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 124) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 125) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda147() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntitlecase$Ex, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda148(Object start, Object end) {
            return srfi13.$PcStringTitlecase$Ex(this.s, start, end);
        }
    }

    public static Object stringTitlecase$Ex$V(Object s, Object[] argsArray) {
        frame62 frame62Var = new frame62();
        frame62Var.s = s;
        frame62Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame62Var.lambda$Fn147, frame62Var.lambda$Fn148);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame63 extends ModuleBody {
        final ModuleMethod lambda$Fn149 = new ModuleMethod(this, 126, null, 0);
        final ModuleMethod lambda$Fn150 = new ModuleMethod(this, 127, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 126 ? lambda149() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 127 ? lambda150(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 126) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 127) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda149() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntitlecase$Ex, this.s, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda150(Object start, Object end) {
            Object obj = this.s;
            try {
                try {
                    try {
                        CharSequence ans = strings.substring((CharSequence) obj, ((Number) start).intValue(), ((Number) end).intValue());
                        srfi13.$PcStringTitlecase$Ex(ans, srfi13.Lit0, AddOp.$Mn.apply2(end, start));
                        return ans;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "substring", 3, end);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "substring", 2, start);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "substring", 1, obj);
            }
        }
    }

    public static Object stringTitlecase$V(Object s, Object[] argsArray) {
        frame63 frame63Var = new frame63();
        frame63Var.s = s;
        frame63Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame63Var.lambda$Fn149, frame63Var.lambda$Fn150);
    }

    public static Object stringTake(Object s, Object n) {
        frame64 frame64Var = new frame64();
        frame64Var.s = s;
        frame64Var.n = n;
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), strings.string$Qu, frame64Var.s, string$Mntake);
            try {
                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), frame64Var.lambda$Fn151, frame64Var.n, string$Mntake);
                Object obj = frame64Var.s;
                try {
                    CharSequence charSequence = (CharSequence) obj;
                    Object obj2 = frame64Var.n;
                    try {
                        return $PcSubstring$SlShared(charSequence, 0, ((Number) obj2).intValue());
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "%substring/shared", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "%substring/shared", 0, obj);
                }
            } catch (UnboundLocationException e3) {
                e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 996, 3);
                throw e3;
            }
        } catch (UnboundLocationException e4) {
            e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 995, 3);
            throw e4;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame64 extends ModuleBody {
        final ModuleMethod lambda$Fn151;
        Object n;
        Object s;

        public frame64() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 128, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:996");
            this.lambda$Fn151 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 128 ? lambda151(obj) ? Boolean.TRUE : Boolean.FALSE : super.apply1(moduleMethod, obj);
        }

        boolean lambda151(Object val) {
            boolean x = numbers.isInteger(this.n);
            if (x) {
                boolean x2 = numbers.isExact(this.n);
                if (!x2) {
                    return x2;
                }
                NumberCompare numberCompare = Scheme.numLEq;
                IntNum intNum = srfi13.Lit0;
                Object obj = this.n;
                Object obj2 = this.s;
                try {
                    return ((Boolean) numberCompare.apply3(intNum, obj, Integer.valueOf(strings.stringLength((CharSequence) obj2)))).booleanValue();
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-length", 1, obj2);
                }
            }
            return x;
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 128) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object stringTakeRight(Object s, Object n) {
        frame65 frame65Var = new frame65();
        frame65Var.n = n;
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), strings.string$Qu, s, string$Mntake$Mnright);
            try {
                frame65Var.len = strings.stringLength((CharSequence) s);
                try {
                    Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), frame65Var.lambda$Fn152, frame65Var.n, string$Mntake$Mnright);
                    try {
                        CharSequence charSequence = (CharSequence) s;
                        Object apply2 = AddOp.$Mn.apply2(Integer.valueOf(frame65Var.len), frame65Var.n);
                        try {
                            return $PcSubstring$SlShared(charSequence, ((Number) apply2).intValue(), frame65Var.len);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%substring/shared", 1, apply2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%substring/shared", 0, s);
                    }
                } catch (UnboundLocationException e3) {
                    e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1004, 5);
                    throw e3;
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "string-length", 1, s);
            }
        } catch (UnboundLocationException e5) {
            e5.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1002, 3);
            throw e5;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame65 extends ModuleBody {
        final ModuleMethod lambda$Fn152;
        int len;
        Object n;

        public frame65() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 129, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1004");
            this.lambda$Fn152 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 129 ? lambda152(obj) ? Boolean.TRUE : Boolean.FALSE : super.apply1(moduleMethod, obj);
        }

        boolean lambda152(Object val) {
            boolean x = numbers.isInteger(this.n);
            if (x) {
                boolean x2 = numbers.isExact(this.n);
                return x2 ? ((Boolean) Scheme.numLEq.apply3(srfi13.Lit0, this.n, Integer.valueOf(this.len))).booleanValue() : x2;
            }
            return x;
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 129) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object stringDrop(CharSequence s, Object n) {
        frame66 frame66Var = new frame66();
        frame66Var.n = n;
        frame66Var.len = strings.stringLength(s);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), frame66Var.lambda$Fn153, frame66Var.n, string$Mndrop);
            Object obj = frame66Var.n;
            try {
                return $PcSubstring$SlShared(s, ((Number) obj).intValue(), frame66Var.len);
            } catch (ClassCastException e) {
                throw new WrongType(e, "%substring/shared", 1, obj);
            }
        } catch (UnboundLocationException e2) {
            e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", PointerIconCompat.TYPE_ALIAS, 5);
            throw e2;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame66 extends ModuleBody {
        final ModuleMethod lambda$Fn153;
        int len;
        Object n;

        public frame66() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 130, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1010");
            this.lambda$Fn153 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 130 ? lambda153(obj) ? Boolean.TRUE : Boolean.FALSE : super.apply1(moduleMethod, obj);
        }

        boolean lambda153(Object val) {
            boolean x = numbers.isInteger(this.n);
            if (x) {
                boolean x2 = numbers.isExact(this.n);
                return x2 ? ((Boolean) Scheme.numLEq.apply3(srfi13.Lit0, this.n, Integer.valueOf(this.len))).booleanValue() : x2;
            }
            return x;
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 130) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object stringDropRight(CharSequence s, Object n) {
        frame67 frame67Var = new frame67();
        frame67Var.n = n;
        frame67Var.len = strings.stringLength(s);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), frame67Var.lambda$Fn154, frame67Var.n, string$Mndrop$Mnright);
            Object apply2 = AddOp.$Mn.apply2(Integer.valueOf(frame67Var.len), frame67Var.n);
            try {
                return $PcSubstring$SlShared(s, 0, ((Number) apply2).intValue());
            } catch (ClassCastException e) {
                throw new WrongType(e, "%substring/shared", 2, apply2);
            }
        } catch (UnboundLocationException e2) {
            e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, 5);
            throw e2;
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 218:
                try {
                    return stringTabulate(obj, ((Number) obj2).intValue());
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-tabulate", 2, obj2);
                }
            case 280:
                return stringTake(obj, obj2);
            case 281:
                return stringTakeRight(obj, obj2);
            case 282:
                try {
                    return stringDrop((CharSequence) obj, obj2);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-drop", 1, obj);
                }
            case 283:
                try {
                    return stringDropRight((CharSequence) obj, obj2);
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-drop-right", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame67 extends ModuleBody {
        final ModuleMethod lambda$Fn154;
        int len;
        Object n;

        public frame67() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 131, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1016");
            this.lambda$Fn154 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 131 ? lambda154(obj) ? Boolean.TRUE : Boolean.FALSE : super.apply1(moduleMethod, obj);
        }

        boolean lambda154(Object val) {
            boolean x = numbers.isInteger(this.n);
            if (x) {
                boolean x2 = numbers.isExact(this.n);
                return x2 ? ((Boolean) Scheme.numLEq.apply3(srfi13.Lit0, this.n, Integer.valueOf(this.len))).booleanValue() : x2;
            }
            return x;
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 131) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object stringTrim$V(Object s, Object[] argsArray) {
        frame68 frame68Var = new frame68();
        frame68Var.s = s;
        LList criterion$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            try {
                try {
                    try {
                        return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), criterion$Plstart$Plend, Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply2(loc$criterion.get(), GetNamedPart.getNamedPart.apply2(loc$char$Mnset.get(), Lit11)), loc$rest.get()), call_with_values.callWithValues(frame68Var.lambda$Fn155, frame68Var.lambda$Fn156));
                    } catch (UnboundLocationException e) {
                        e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1022, 72);
                        throw e;
                    }
                } catch (UnboundLocationException e2) {
                    e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1022, 51);
                    throw e2;
                }
            } catch (UnboundLocationException e3) {
                e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1022, 40);
                throw e3;
            }
        } catch (UnboundLocationException e4) {
            e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1022, 3);
            throw e4;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame68 extends ModuleBody {
        final ModuleMethod lambda$Fn155 = new ModuleMethod(this, 132, null, 0);
        final ModuleMethod lambda$Fn156 = new ModuleMethod(this, 133, null, 8194);
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 132 ? lambda155() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 133 ? lambda156(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 132) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 133) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda155() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntrim, this.s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1023, 53);
                throw e;
            }
        }

        Object lambda156(Object start, Object end) {
            try {
                Object temp = srfi13.stringSkip$V(this.s, srfi13.loc$criterion.get(), new Object[]{start, end});
                if (temp != Boolean.FALSE) {
                    Object obj = this.s;
                    try {
                        try {
                            try {
                                return srfi13.$PcSubstring$SlShared((CharSequence) obj, ((Number) temp).intValue(), ((Number) end).intValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "%substring/shared", 2, end);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%substring/shared", 1, temp);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "%substring/shared", 0, obj);
                    }
                }
                return "";
            } catch (UnboundLocationException e4) {
                e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1024, 29);
                throw e4;
            }
        }
    }

    public static Object stringTrimRight$V(Object s, Object[] argsArray) {
        frame69 frame69Var = new frame69();
        frame69Var.s = s;
        LList criterion$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            try {
                try {
                    try {
                        return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), criterion$Plstart$Plend, Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply2(loc$criterion.get(), GetNamedPart.getNamedPart.apply2(loc$char$Mnset.get(), Lit11)), loc$rest.get()), call_with_values.callWithValues(frame69Var.lambda$Fn157, frame69Var.lambda$Fn158));
                    } catch (UnboundLocationException e) {
                        e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1029, 72);
                        throw e;
                    }
                } catch (UnboundLocationException e2) {
                    e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1029, 51);
                    throw e2;
                }
            } catch (UnboundLocationException e3) {
                e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1029, 40);
                throw e3;
            }
        } catch (UnboundLocationException e4) {
            e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1029, 3);
            throw e4;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame69 extends ModuleBody {
        final ModuleMethod lambda$Fn157 = new ModuleMethod(this, 134, null, 0);
        final ModuleMethod lambda$Fn158 = new ModuleMethod(this, 135, null, 8194);
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 134 ? lambda157() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 135 ? lambda158(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 134) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 135) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda157() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntrim$Mnright, this.s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1030, 59);
                throw e;
            }
        }

        Object lambda158(Object start, Object end) {
            try {
                Object temp = srfi13.stringSkipRight$V(this.s, srfi13.loc$criterion.get(), new Object[]{start, end});
                if (temp != Boolean.FALSE) {
                    Object obj = this.s;
                    try {
                        CharSequence charSequence = (CharSequence) obj;
                        Object apply2 = AddOp.$Pl.apply2(srfi13.Lit1, temp);
                        try {
                            return srfi13.$PcSubstring$SlShared(charSequence, 0, ((Number) apply2).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%substring/shared", 2, apply2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%substring/shared", 0, obj);
                    }
                }
                return "";
            } catch (UnboundLocationException e3) {
                e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1031, 35);
                throw e3;
            }
        }
    }

    public static Object stringTrimBoth$V(Object s, Object[] argsArray) {
        frame70 frame70Var = new frame70();
        frame70Var.s = s;
        LList criterion$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            try {
                try {
                    try {
                        return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), criterion$Plstart$Plend, Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply2(loc$criterion.get(), GetNamedPart.getNamedPart.apply2(loc$char$Mnset.get(), Lit11)), loc$rest.get()), call_with_values.callWithValues(frame70Var.lambda$Fn159, frame70Var.lambda$Fn160));
                    } catch (UnboundLocationException e) {
                        e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1036, 72);
                        throw e;
                    }
                } catch (UnboundLocationException e2) {
                    e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1036, 51);
                    throw e2;
                }
            } catch (UnboundLocationException e3) {
                e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1036, 40);
                throw e3;
            }
        } catch (UnboundLocationException e4) {
            e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1036, 3);
            throw e4;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame70 extends ModuleBody {
        final ModuleMethod lambda$Fn159 = new ModuleMethod(this, 136, null, 0);
        final ModuleMethod lambda$Fn160 = new ModuleMethod(this, 137, null, 8194);
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 136 ? lambda159() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 137 ? lambda160(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 136) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 137) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda159() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntrim$Mnboth, this.s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1037, 58);
                throw e;
            }
        }

        Object lambda160(Object start, Object end) {
            try {
                Object temp = srfi13.stringSkip$V(this.s, srfi13.loc$criterion.get(), new Object[]{start, end});
                if (temp != Boolean.FALSE) {
                    Object obj = this.s;
                    try {
                        CharSequence charSequence = (CharSequence) obj;
                        try {
                            int intValue = ((Number) temp).intValue();
                            try {
                                Object apply2 = AddOp.$Pl.apply2(srfi13.Lit1, srfi13.stringSkipRight$V(this.s, srfi13.loc$criterion.get(), new Object[]{temp, end}));
                                try {
                                    return srfi13.$PcSubstring$SlShared(charSequence, intValue, ((Number) apply2).intValue());
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "%substring/shared", 2, apply2);
                                }
                            } catch (UnboundLocationException e2) {
                                e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1040, 58);
                                throw e2;
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "%substring/shared", 1, temp);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "%substring/shared", 0, obj);
                    }
                }
                return "";
            } catch (UnboundLocationException e5) {
                e5.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1038, 29);
                throw e5;
            }
        }
    }

    public static Object stringPadRight$V(Object s, Object n, Object[] argsArray) {
        frame71 frame71Var = new frame71();
        frame71Var.s = s;
        frame71Var.n = n;
        LList char$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            try {
                return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), char$Plstart$Plend, Scheme.applyToArgs.apply2(Invoke.make.apply3(LangPrimType.charType, Lit12, characters.isChar(LangPrimType.charType) ? Boolean.TRUE : Boolean.FALSE), loc$rest.get()), call_with_values.callWithValues(frame71Var.lambda$Fn161, frame71Var.lambda$Fn162));
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1045, 63);
                throw e;
            }
        } catch (UnboundLocationException e2) {
            e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1045, 3);
            throw e2;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame71 extends ModuleBody {
        final ModuleMethod lambda$Fn161 = new ModuleMethod(this, 138, null, 0);
        final ModuleMethod lambda$Fn162 = new ModuleMethod(this, 139, null, 8194);
        Object n;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 138 ? lambda161() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 139 ? lambda162(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 138) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 139) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda161() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnpad$Mnright, this.s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1046, 58);
                throw e;
            }
        }

        static boolean lambda163(Object n) {
            boolean x = numbers.isInteger(n);
            if (x) {
                boolean x2 = numbers.isExact(n);
                return x2 ? ((Boolean) Scheme.numLEq.apply2(srfi13.Lit0, n)).booleanValue() : x2;
            }
            return x;
        }

        Object lambda162(Object start, Object end) {
            try {
                Scheme.applyToArgs.apply4(srfi13.loc$check$Mnarg.get(), srfi13.lambda$Fn163, this.n, srfi13.string$Mnpad$Mnright);
                Object len = AddOp.$Mn.apply2(end, start);
                if (Scheme.numLEq.apply2(this.n, len) != Boolean.FALSE) {
                    Object obj = this.s;
                    try {
                        CharSequence charSequence = (CharSequence) obj;
                        try {
                            int intValue = ((Number) start).intValue();
                            Object apply2 = AddOp.$Pl.apply2(start, this.n);
                            try {
                                return srfi13.$PcSubstring$SlShared(charSequence, intValue, ((Number) apply2).intValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "%substring/shared", 2, apply2);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%substring/shared", 1, start);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "%substring/shared", 0, obj);
                    }
                }
                Object obj2 = this.n;
                try {
                    CharSequence ans = strings.makeString(((Number) obj2).intValue(), LangPrimType.charType);
                    Object obj3 = this.s;
                    try {
                        try {
                            try {
                                srfi13.$PcStringCopy$Ex(ans, 0, (CharSequence) obj3, ((Number) start).intValue(), ((Number) end).intValue());
                                return ans;
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "%string-copy!", 4, end);
                            }
                        } catch (ClassCastException e5) {
                            throw new WrongType(e5, "%string-copy!", 3, start);
                        }
                    } catch (ClassCastException e6) {
                        throw new WrongType(e6, "%string-copy!", 2, obj3);
                    }
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "make-string", 1, obj2);
                }
            } catch (UnboundLocationException e8) {
                e8.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1047, 7);
                throw e8;
            }
        }
    }

    public static Object stringPad$V(Object s, Object n, Object[] argsArray) {
        frame72 frame72Var = new frame72();
        frame72Var.s = s;
        frame72Var.n = n;
        LList char$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            try {
                return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), char$Plstart$Plend, Scheme.applyToArgs.apply2(Invoke.make.apply3(LangPrimType.charType, Lit12, characters.isChar(LangPrimType.charType) ? Boolean.TRUE : Boolean.FALSE), loc$rest.get()), call_with_values.callWithValues(frame72Var.lambda$Fn164, frame72Var.lambda$Fn165));
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1057, 63);
                throw e;
            }
        } catch (UnboundLocationException e2) {
            e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1057, 3);
            throw e2;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame72 extends ModuleBody {
        final ModuleMethod lambda$Fn164 = new ModuleMethod(this, 140, null, 0);
        final ModuleMethod lambda$Fn165 = new ModuleMethod(this, 141, null, 8194);
        Object n;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 140 ? lambda164() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 141 ? lambda165(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 140) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 141) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda164() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnpad, this.s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1058, 52);
                throw e;
            }
        }

        static boolean lambda166(Object n) {
            boolean x = numbers.isInteger(n);
            if (x) {
                boolean x2 = numbers.isExact(n);
                return x2 ? ((Boolean) Scheme.numLEq.apply2(srfi13.Lit0, n)).booleanValue() : x2;
            }
            return x;
        }

        Object lambda165(Object start, Object end) {
            try {
                Scheme.applyToArgs.apply4(srfi13.loc$check$Mnarg.get(), srfi13.lambda$Fn166, this.n, srfi13.string$Mnpad);
                Object len = AddOp.$Mn.apply2(end, start);
                if (Scheme.numLEq.apply2(this.n, len) != Boolean.FALSE) {
                    Object obj = this.s;
                    try {
                        CharSequence charSequence = (CharSequence) obj;
                        Object apply2 = AddOp.$Mn.apply2(end, this.n);
                        try {
                            try {
                                return srfi13.$PcSubstring$SlShared(charSequence, ((Number) apply2).intValue(), ((Number) end).intValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "%substring/shared", 2, end);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%substring/shared", 1, apply2);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "%substring/shared", 0, obj);
                    }
                }
                Object obj2 = this.n;
                try {
                    CharSequence ans = strings.makeString(((Number) obj2).intValue(), LangPrimType.charType);
                    Object apply22 = AddOp.$Mn.apply2(this.n, len);
                    try {
                        int intValue = ((Number) apply22).intValue();
                        Object obj3 = this.s;
                        try {
                            try {
                                try {
                                    srfi13.$PcStringCopy$Ex(ans, intValue, (CharSequence) obj3, ((Number) start).intValue(), ((Number) end).intValue());
                                    return ans;
                                } catch (ClassCastException e4) {
                                    throw new WrongType(e4, "%string-copy!", 4, end);
                                }
                            } catch (ClassCastException e5) {
                                throw new WrongType(e5, "%string-copy!", 3, start);
                            }
                        } catch (ClassCastException e6) {
                            throw new WrongType(e6, "%string-copy!", 2, obj3);
                        }
                    } catch (ClassCastException e7) {
                        throw new WrongType(e7, "%string-copy!", 1, apply22);
                    }
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "make-string", 1, obj2);
                }
            } catch (UnboundLocationException e9) {
                e9.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1059, 7);
                throw e9;
            }
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame73 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn167 = new ModuleMethod(this, 145, null, 0);
        final ModuleMethod lambda$Fn168 = new ModuleMethod(this, 146, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 145 ? lambda167() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 146 ? lambda168(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 145) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 146) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda167() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mndelete, this.s, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda168(Object start, Object end) {
            Object error$V;
            frame74 frame74Var = new frame74();
            frame74Var.staticLink = this;
            if (misc.isProcedure(this.criterion)) {
                Object slen = AddOp.$Mn.apply2(end, start);
                try {
                    frame74Var.temp = strings.makeString(((Number) slen).intValue());
                    Object ans$Mnlen = srfi13.stringFold$V(frame74Var.lambda$Fn169, srfi13.Lit0, this.s, new Object[]{start, end});
                    if (Scheme.numEqu.apply2(ans$Mnlen, slen) != Boolean.FALSE) {
                        return frame74Var.temp;
                    }
                    try {
                        return strings.substring(frame74Var.temp, 0, ((Number) ans$Mnlen).intValue());
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "substring", 3, ans$Mnlen);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "make-string", 1, slen);
                }
            }
            try {
                if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                    error$V = this.criterion;
                } else if (characters.isChar(this.criterion)) {
                    try {
                        error$V = Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset.get(), this.criterion);
                    } catch (UnboundLocationException e3) {
                        e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1097, 26);
                        throw e3;
                    }
                } else {
                    error$V = misc.error$V("string-delete criterion not predicate, char or char-set", new Object[]{this.criterion});
                }
                frame74Var.cset = error$V;
                Object len = srfi13.stringFold$V(frame74Var.lambda$Fn170, srfi13.Lit0, this.s, new Object[]{start, end});
                try {
                    frame74Var.ans = strings.makeString(((Number) len).intValue());
                    srfi13.stringFold$V(frame74Var.lambda$Fn171, srfi13.Lit0, this.s, new Object[]{start, end});
                    return frame74Var.ans;
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "make-string", 1, len);
                }
            } catch (UnboundLocationException e5) {
                e5.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1096, 22);
                throw e5;
            }
        }
    }

    public static Object stringDelete$V(Object criterion, Object s, Object[] argsArray) {
        frame73 frame73Var = new frame73();
        frame73Var.criterion = criterion;
        frame73Var.s = s;
        frame73Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame73Var.lambda$Fn167, frame73Var.lambda$Fn168);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame74 extends ModuleBody {
        CharSequence ans;
        Object cset;
        final ModuleMethod lambda$Fn169;
        final ModuleMethod lambda$Fn170;
        final ModuleMethod lambda$Fn171;
        frame73 staticLink;
        CharSequence temp;

        public frame74() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 142, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1089");
            this.lambda$Fn169 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 143, null, 8194);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1099");
            this.lambda$Fn170 = moduleMethod2;
            ModuleMethod moduleMethod3 = new ModuleMethod(this, 144, null, 8194);
            moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1104");
            this.lambda$Fn171 = moduleMethod3;
        }

        Object lambda169(Object c, Object i) {
            if (Scheme.applyToArgs.apply2(this.staticLink.criterion, c) == Boolean.FALSE) {
                CharSequence charSequence = this.temp;
                try {
                    try {
                        try {
                            ((CharSeq) charSequence).setCharAt(((Number) i).intValue(), ((Char) c).charValue());
                            return AddOp.$Pl.apply2(i, srfi13.Lit1);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, c);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, i);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 1, charSequence);
                }
            }
            return i;
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 142:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 143:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 144:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }

        Object lambda170(Object c, Object i) {
            try {
                return Scheme.applyToArgs.apply3(srfi13.loc$char$Mnset$Mncontains$Qu.get(), this.cset, c) != Boolean.FALSE ? i : AddOp.$Pl.apply2(i, srfi13.Lit1);
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1099, 45);
                throw e;
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 142:
                    return lambda169(obj, obj2);
                case 143:
                    return lambda170(obj, obj2);
                case 144:
                    return lambda171(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }

        Object lambda171(Object c, Object i) {
            try {
                if (Scheme.applyToArgs.apply3(srfi13.loc$char$Mnset$Mncontains$Qu.get(), this.cset, c) == Boolean.FALSE) {
                    CharSequence charSequence = this.ans;
                    try {
                        try {
                            try {
                                ((CharSeq) charSequence).setCharAt(((Number) i).intValue(), ((Char) c).charValue());
                                return AddOp.$Pl.apply2(i, srfi13.Lit1);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-set!", 3, c);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-set!", 2, i);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "string-set!", 1, charSequence);
                    }
                }
                return i;
            } catch (UnboundLocationException e4) {
                e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1104, 35);
                throw e4;
            }
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame75 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn172 = new ModuleMethod(this, 150, null, 0);
        final ModuleMethod lambda$Fn173 = new ModuleMethod(this, 151, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 150 ? lambda172() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 151 ? lambda173(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 150) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 151) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda172() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfilter, this.s, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda173(Object start, Object end) {
            Object error$V;
            frame76 frame76Var = new frame76();
            frame76Var.staticLink = this;
            if (misc.isProcedure(this.criterion)) {
                Object slen = AddOp.$Mn.apply2(end, start);
                try {
                    frame76Var.temp = strings.makeString(((Number) slen).intValue());
                    Object ans$Mnlen = srfi13.stringFold$V(frame76Var.lambda$Fn174, srfi13.Lit0, this.s, new Object[]{start, end});
                    if (Scheme.numEqu.apply2(ans$Mnlen, slen) != Boolean.FALSE) {
                        return frame76Var.temp;
                    }
                    try {
                        return strings.substring(frame76Var.temp, 0, ((Number) ans$Mnlen).intValue());
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "substring", 3, ans$Mnlen);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "make-string", 1, slen);
                }
            }
            try {
                if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                    error$V = this.criterion;
                } else if (characters.isChar(this.criterion)) {
                    try {
                        error$V = Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset.get(), this.criterion);
                    } catch (UnboundLocationException e3) {
                        e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1125, 26);
                        throw e3;
                    }
                } else {
                    error$V = misc.error$V("string-delete criterion not predicate, char or char-set", new Object[]{this.criterion});
                }
                frame76Var.cset = error$V;
                Object len = srfi13.stringFold$V(frame76Var.lambda$Fn175, srfi13.Lit0, this.s, new Object[]{start, end});
                try {
                    frame76Var.ans = strings.makeString(((Number) len).intValue());
                    srfi13.stringFold$V(frame76Var.lambda$Fn176, srfi13.Lit0, this.s, new Object[]{start, end});
                    return frame76Var.ans;
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "make-string", 1, len);
                }
            } catch (UnboundLocationException e5) {
                e5.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1124, 22);
                throw e5;
            }
        }
    }

    public static Object stringFilter$V(Object criterion, Object s, Object[] argsArray) {
        frame75 frame75Var = new frame75();
        frame75Var.criterion = criterion;
        frame75Var.s = s;
        frame75Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame75Var.lambda$Fn172, frame75Var.lambda$Fn173);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame76 extends ModuleBody {
        CharSequence ans;
        Object cset;
        final ModuleMethod lambda$Fn174;
        final ModuleMethod lambda$Fn175;
        final ModuleMethod lambda$Fn176;
        frame75 staticLink;
        CharSequence temp;

        public frame76() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 147, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1116");
            this.lambda$Fn174 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 148, null, 8194);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1128");
            this.lambda$Fn175 = moduleMethod2;
            ModuleMethod moduleMethod3 = new ModuleMethod(this, 149, null, 8194);
            moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1133");
            this.lambda$Fn176 = moduleMethod3;
        }

        Object lambda174(Object c, Object i) {
            if (Scheme.applyToArgs.apply2(this.staticLink.criterion, c) == Boolean.FALSE) {
                return i;
            }
            CharSequence charSequence = this.temp;
            try {
                try {
                    try {
                        ((CharSeq) charSequence).setCharAt(((Number) i).intValue(), ((Char) c).charValue());
                        return AddOp.$Pl.apply2(i, srfi13.Lit1);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-set!", 3, c);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-set!", 2, i);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "string-set!", 1, charSequence);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 147:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 148:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 149:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }

        Object lambda175(Object c, Object i) {
            try {
                if (Scheme.applyToArgs.apply3(srfi13.loc$char$Mnset$Mncontains$Qu.get(), this.cset, c) == Boolean.FALSE) {
                    return i;
                }
                return AddOp.$Pl.apply2(i, srfi13.Lit1);
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1128, 45);
                throw e;
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 147:
                    return lambda174(obj, obj2);
                case 148:
                    return lambda175(obj, obj2);
                case 149:
                    return lambda176(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }

        Object lambda176(Object c, Object i) {
            try {
                if (Scheme.applyToArgs.apply3(srfi13.loc$char$Mnset$Mncontains$Qu.get(), this.cset, c) == Boolean.FALSE) {
                    return i;
                }
                CharSequence charSequence = this.ans;
                try {
                    try {
                        try {
                            ((CharSeq) charSequence).setCharAt(((Number) i).intValue(), ((Char) c).charValue());
                            return AddOp.$Pl.apply2(i, srfi13.Lit1);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, c);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, i);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 1, charSequence);
                }
            } catch (UnboundLocationException e4) {
                e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1133, 35);
                throw e4;
            }
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame77 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn177 = new ModuleMethod(this, 152, null, 0);
        final ModuleMethod lambda$Fn178 = new ModuleMethod(this, 153, null, 8194);
        LList maybe$Mnstart$Plend;
        Object str;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 152 ? lambda177() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 153 ? lambda178(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 152) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 153) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda177() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnindex, this.str, this.maybe$Mnstart$Plend);
        }

        Object lambda178(Object start, Object end) {
            if (characters.isChar(this.criterion)) {
                Object i = start;
                while (true) {
                    Object apply2 = Scheme.numLss.apply2(i, end);
                    try {
                        boolean x = ((Boolean) apply2).booleanValue();
                        if (!x) {
                            if (x) {
                                Object i2 = Boolean.TRUE;
                                return i2;
                            }
                            Object i3 = Boolean.FALSE;
                            return i3;
                        }
                        Object obj = this.criterion;
                        try {
                            Char r3 = (Char) obj;
                            Object obj2 = this.str;
                            try {
                                try {
                                    if (!characters.isChar$Eq(r3, Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())))) {
                                        i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                    } else {
                                        return i;
                                    }
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 1, obj);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "x", -2, apply2);
                    }
                }
            } else {
                try {
                    if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                        Object i4 = start;
                        while (true) {
                            Object apply22 = Scheme.numLss.apply2(i4, end);
                            try {
                                boolean x2 = ((Boolean) apply22).booleanValue();
                                if (!x2) {
                                    if (x2) {
                                        Object i5 = Boolean.TRUE;
                                        return i5;
                                    }
                                    Object i6 = Boolean.FALSE;
                                    return i6;
                                }
                                ApplyToArgs applyToArgs = Scheme.applyToArgs;
                                try {
                                    Object obj3 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                                    Object obj4 = this.criterion;
                                    Object obj5 = this.str;
                                    try {
                                        try {
                                            if (applyToArgs.apply3(obj3, obj4, Char.make(strings.stringRef((CharSequence) obj5, ((Number) i4).intValue()))) == Boolean.FALSE) {
                                                i4 = AddOp.$Pl.apply2(i4, srfi13.Lit1);
                                            } else {
                                                return i4;
                                            }
                                        } catch (ClassCastException e5) {
                                            throw new WrongType(e5, "string-ref", 2, i4);
                                        }
                                    } catch (ClassCastException e6) {
                                        throw new WrongType(e6, "string-ref", 1, obj5);
                                    }
                                } catch (UnboundLocationException e7) {
                                    e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1162, 9);
                                    throw e7;
                                }
                            } catch (ClassCastException e8) {
                                throw new WrongType(e8, "x", -2, apply22);
                            }
                        }
                    } else if (misc.isProcedure(this.criterion)) {
                        Object i7 = start;
                        while (true) {
                            Object apply23 = Scheme.numLss.apply2(i7, end);
                            try {
                                boolean x3 = ((Boolean) apply23).booleanValue();
                                if (!x3) {
                                    if (x3) {
                                        Object i8 = Boolean.TRUE;
                                        return i8;
                                    }
                                    Object i9 = Boolean.FALSE;
                                    return i9;
                                }
                                ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
                                Object obj6 = this.criterion;
                                Object obj7 = this.str;
                                try {
                                    try {
                                        if (applyToArgs2.apply2(obj6, Char.make(strings.stringRef((CharSequence) obj7, ((Number) i7).intValue()))) == Boolean.FALSE) {
                                            i7 = AddOp.$Pl.apply2(i7, srfi13.Lit1);
                                        } else {
                                            return i7;
                                        }
                                    } catch (ClassCastException e9) {
                                        throw new WrongType(e9, "string-ref", 2, i7);
                                    }
                                } catch (ClassCastException e10) {
                                    throw new WrongType(e10, "string-ref", 1, obj7);
                                }
                            } catch (ClassCastException e11) {
                                throw new WrongType(e11, "x", -2, apply23);
                            }
                        }
                    } else {
                        Object i10 = misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnindex, this.criterion});
                        return i10;
                    }
                } catch (UnboundLocationException e12) {
                    e12.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1159, 5);
                    throw e12;
                }
            }
        }
    }

    public static Object stringIndex$V(Object str, Object criterion, Object[] argsArray) {
        frame77 frame77Var = new frame77();
        frame77Var.str = str;
        frame77Var.criterion = criterion;
        frame77Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame77Var.lambda$Fn177, frame77Var.lambda$Fn178);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame78 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn179 = new ModuleMethod(this, 154, null, 0);
        final ModuleMethod lambda$Fn180 = new ModuleMethod(this, 155, null, 8194);
        LList maybe$Mnstart$Plend;
        Object str;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 154 ? lambda179() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 155 ? lambda180(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 154) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 155) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda179() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnindex$Mnright, this.str, this.maybe$Mnstart$Plend);
        }

        Object lambda180(Object start, Object end) {
            if (characters.isChar(this.criterion)) {
                Object i = AddOp.$Mn.apply2(end, srfi13.Lit1);
                while (true) {
                    Object apply2 = Scheme.numGEq.apply2(i, start);
                    try {
                        boolean x = ((Boolean) apply2).booleanValue();
                        if (!x) {
                            if (x) {
                                Object i2 = Boolean.TRUE;
                                return i2;
                            }
                            Object i3 = Boolean.FALSE;
                            return i3;
                        }
                        Object obj = this.criterion;
                        try {
                            Char r3 = (Char) obj;
                            Object obj2 = this.str;
                            try {
                                try {
                                    if (!characters.isChar$Eq(r3, Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())))) {
                                        i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                                    } else {
                                        return i;
                                    }
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 1, obj);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "x", -2, apply2);
                    }
                }
            } else {
                try {
                    if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                        Object i4 = AddOp.$Mn.apply2(end, srfi13.Lit1);
                        while (true) {
                            Object apply22 = Scheme.numGEq.apply2(i4, start);
                            try {
                                boolean x2 = ((Boolean) apply22).booleanValue();
                                if (!x2) {
                                    if (x2) {
                                        Object i5 = Boolean.TRUE;
                                        return i5;
                                    }
                                    Object i6 = Boolean.FALSE;
                                    return i6;
                                }
                                ApplyToArgs applyToArgs = Scheme.applyToArgs;
                                try {
                                    Object obj3 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                                    Object obj4 = this.criterion;
                                    Object obj5 = this.str;
                                    try {
                                        try {
                                            if (applyToArgs.apply3(obj3, obj4, Char.make(strings.stringRef((CharSequence) obj5, ((Number) i4).intValue()))) == Boolean.FALSE) {
                                                i4 = AddOp.$Mn.apply2(i4, srfi13.Lit1);
                                            } else {
                                                return i4;
                                            }
                                        } catch (ClassCastException e5) {
                                            throw new WrongType(e5, "string-ref", 2, i4);
                                        }
                                    } catch (ClassCastException e6) {
                                        throw new WrongType(e6, "string-ref", 1, obj5);
                                    }
                                } catch (UnboundLocationException e7) {
                                    e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1182, 9);
                                    throw e7;
                                }
                            } catch (ClassCastException e8) {
                                throw new WrongType(e8, "x", -2, apply22);
                            }
                        }
                    } else if (misc.isProcedure(this.criterion)) {
                        Object i7 = AddOp.$Mn.apply2(end, srfi13.Lit1);
                        while (true) {
                            Object apply23 = Scheme.numGEq.apply2(i7, start);
                            try {
                                boolean x3 = ((Boolean) apply23).booleanValue();
                                if (!x3) {
                                    if (x3) {
                                        Object i8 = Boolean.TRUE;
                                        return i8;
                                    }
                                    Object i9 = Boolean.FALSE;
                                    return i9;
                                }
                                ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
                                Object obj6 = this.criterion;
                                Object obj7 = this.str;
                                try {
                                    try {
                                        if (applyToArgs2.apply2(obj6, Char.make(strings.stringRef((CharSequence) obj7, ((Number) i7).intValue()))) == Boolean.FALSE) {
                                            i7 = AddOp.$Mn.apply2(i7, srfi13.Lit1);
                                        } else {
                                            return i7;
                                        }
                                    } catch (ClassCastException e9) {
                                        throw new WrongType(e9, "string-ref", 2, i7);
                                    }
                                } catch (ClassCastException e10) {
                                    throw new WrongType(e10, "string-ref", 1, obj7);
                                }
                            } catch (ClassCastException e11) {
                                throw new WrongType(e11, "x", -2, apply23);
                            }
                        }
                    } else {
                        Object i10 = misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnindex$Mnright, this.criterion});
                        return i10;
                    }
                } catch (UnboundLocationException e12) {
                    e12.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1179, 5);
                    throw e12;
                }
            }
        }
    }

    public static Object stringIndexRight$V(Object str, Object criterion, Object[] argsArray) {
        frame78 frame78Var = new frame78();
        frame78Var.str = str;
        frame78Var.criterion = criterion;
        frame78Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame78Var.lambda$Fn179, frame78Var.lambda$Fn180);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame79 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn181 = new ModuleMethod(this, 156, null, 0);
        final ModuleMethod lambda$Fn182 = new ModuleMethod(this, 157, null, 8194);
        LList maybe$Mnstart$Plend;
        Object str;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 156 ? lambda181() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 157 ? lambda182(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 156) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 157) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda181() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnskip, this.str, this.maybe$Mnstart$Plend);
        }

        Object lambda182(Object start, Object end) {
            if (characters.isChar(this.criterion)) {
                Object i = start;
                while (true) {
                    Object apply2 = Scheme.numLss.apply2(i, end);
                    try {
                        boolean x = ((Boolean) apply2).booleanValue();
                        if (!x) {
                            if (x) {
                                Object i2 = Boolean.TRUE;
                                return i2;
                            }
                            Object i3 = Boolean.FALSE;
                            return i3;
                        }
                        Object obj = this.criterion;
                        try {
                            Char r3 = (Char) obj;
                            Object obj2 = this.str;
                            try {
                                try {
                                    if (!characters.isChar$Eq(r3, Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())))) {
                                        return i;
                                    }
                                    i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 1, obj);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "x", -2, apply2);
                    }
                }
            } else {
                try {
                    if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                        Object i4 = start;
                        while (true) {
                            Object apply22 = Scheme.numLss.apply2(i4, end);
                            try {
                                boolean x2 = ((Boolean) apply22).booleanValue();
                                if (!x2) {
                                    if (x2) {
                                        Object i5 = Boolean.TRUE;
                                        return i5;
                                    }
                                    Object i6 = Boolean.FALSE;
                                    return i6;
                                }
                                ApplyToArgs applyToArgs = Scheme.applyToArgs;
                                try {
                                    Object obj3 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                                    Object obj4 = this.criterion;
                                    Object obj5 = this.str;
                                    try {
                                        try {
                                            if (applyToArgs.apply3(obj3, obj4, Char.make(strings.stringRef((CharSequence) obj5, ((Number) i4).intValue()))) == Boolean.FALSE) {
                                                return i4;
                                            }
                                            i4 = AddOp.$Pl.apply2(i4, srfi13.Lit1);
                                        } catch (ClassCastException e5) {
                                            throw new WrongType(e5, "string-ref", 2, i4);
                                        }
                                    } catch (ClassCastException e6) {
                                        throw new WrongType(e6, "string-ref", 1, obj5);
                                    }
                                } catch (UnboundLocationException e7) {
                                    e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1203, 9);
                                    throw e7;
                                }
                            } catch (ClassCastException e8) {
                                throw new WrongType(e8, "x", -2, apply22);
                            }
                        }
                    } else if (!misc.isProcedure(this.criterion)) {
                        Object i7 = misc.error$V("Second param is neither char-set, char, or predicate procedure.", new Object[]{srfi13.string$Mnskip, this.criterion});
                        return i7;
                    } else {
                        Object i8 = start;
                        while (true) {
                            Object apply23 = Scheme.numLss.apply2(i8, end);
                            try {
                                boolean x3 = ((Boolean) apply23).booleanValue();
                                if (!x3) {
                                    if (x3) {
                                        Object i9 = Boolean.TRUE;
                                        return i9;
                                    }
                                    Object i10 = Boolean.FALSE;
                                    return i10;
                                }
                                ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
                                Object obj6 = this.criterion;
                                Object obj7 = this.str;
                                try {
                                    try {
                                        if (applyToArgs2.apply2(obj6, Char.make(strings.stringRef((CharSequence) obj7, ((Number) i8).intValue()))) == Boolean.FALSE) {
                                            return i8;
                                        }
                                        i8 = AddOp.$Pl.apply2(i8, srfi13.Lit1);
                                    } catch (ClassCastException e9) {
                                        throw new WrongType(e9, "string-ref", 2, i8);
                                    }
                                } catch (ClassCastException e10) {
                                    throw new WrongType(e10, "string-ref", 1, obj7);
                                }
                            } catch (ClassCastException e11) {
                                throw new WrongType(e11, "x", -2, apply23);
                            }
                        }
                    }
                } catch (UnboundLocationException e12) {
                    e12.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1200, 5);
                    throw e12;
                }
            }
        }
    }

    public static Object stringSkip$V(Object str, Object criterion, Object[] argsArray) {
        frame79 frame79Var = new frame79();
        frame79Var.str = str;
        frame79Var.criterion = criterion;
        frame79Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame79Var.lambda$Fn181, frame79Var.lambda$Fn182);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame80 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn183 = new ModuleMethod(this, 158, null, 0);
        final ModuleMethod lambda$Fn184 = new ModuleMethod(this, 159, null, 8194);
        LList maybe$Mnstart$Plend;
        Object str;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 158 ? lambda183() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 159 ? lambda184(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 158) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 159) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda183() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnskip$Mnright, this.str, this.maybe$Mnstart$Plend);
        }

        Object lambda184(Object start, Object end) {
            if (characters.isChar(this.criterion)) {
                Object i = AddOp.$Mn.apply2(end, srfi13.Lit1);
                while (true) {
                    Object apply2 = Scheme.numGEq.apply2(i, start);
                    try {
                        boolean x = ((Boolean) apply2).booleanValue();
                        if (!x) {
                            if (x) {
                                Object i2 = Boolean.TRUE;
                                return i2;
                            }
                            Object i3 = Boolean.FALSE;
                            return i3;
                        }
                        Object obj = this.criterion;
                        try {
                            Char r3 = (Char) obj;
                            Object obj2 = this.str;
                            try {
                                try {
                                    if (!characters.isChar$Eq(r3, Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())))) {
                                        return i;
                                    }
                                    i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj2);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 1, obj);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "x", -2, apply2);
                    }
                }
            } else {
                try {
                    if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                        Object i4 = AddOp.$Mn.apply2(end, srfi13.Lit1);
                        while (true) {
                            Object apply22 = Scheme.numGEq.apply2(i4, start);
                            try {
                                boolean x2 = ((Boolean) apply22).booleanValue();
                                if (!x2) {
                                    if (x2) {
                                        Object i5 = Boolean.TRUE;
                                        return i5;
                                    }
                                    Object i6 = Boolean.FALSE;
                                    return i6;
                                }
                                ApplyToArgs applyToArgs = Scheme.applyToArgs;
                                try {
                                    Object obj3 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                                    Object obj4 = this.criterion;
                                    Object obj5 = this.str;
                                    try {
                                        try {
                                            if (applyToArgs.apply3(obj3, obj4, Char.make(strings.stringRef((CharSequence) obj5, ((Number) i4).intValue()))) == Boolean.FALSE) {
                                                return i4;
                                            }
                                            i4 = AddOp.$Mn.apply2(i4, srfi13.Lit1);
                                        } catch (ClassCastException e5) {
                                            throw new WrongType(e5, "string-ref", 2, i4);
                                        }
                                    } catch (ClassCastException e6) {
                                        throw new WrongType(e6, "string-ref", 1, obj5);
                                    }
                                } catch (UnboundLocationException e7) {
                                    e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1225, 9);
                                    throw e7;
                                }
                            } catch (ClassCastException e8) {
                                throw new WrongType(e8, "x", -2, apply22);
                            }
                        }
                    } else if (!misc.isProcedure(this.criterion)) {
                        Object i7 = misc.error$V("CRITERION param is neither char-set or char.", new Object[]{srfi13.string$Mnskip$Mnright, this.criterion});
                        return i7;
                    } else {
                        Object i8 = AddOp.$Mn.apply2(end, srfi13.Lit1);
                        while (true) {
                            Object apply23 = Scheme.numGEq.apply2(i8, start);
                            try {
                                boolean x3 = ((Boolean) apply23).booleanValue();
                                if (!x3) {
                                    if (x3) {
                                        Object i9 = Boolean.TRUE;
                                        return i9;
                                    }
                                    Object i10 = Boolean.FALSE;
                                    return i10;
                                }
                                ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
                                Object obj6 = this.criterion;
                                Object obj7 = this.str;
                                try {
                                    try {
                                        if (applyToArgs2.apply2(obj6, Char.make(strings.stringRef((CharSequence) obj7, ((Number) i8).intValue()))) == Boolean.FALSE) {
                                            return i8;
                                        }
                                        i8 = AddOp.$Mn.apply2(i8, srfi13.Lit1);
                                    } catch (ClassCastException e9) {
                                        throw new WrongType(e9, "string-ref", 2, i8);
                                    }
                                } catch (ClassCastException e10) {
                                    throw new WrongType(e10, "string-ref", 1, obj7);
                                }
                            } catch (ClassCastException e11) {
                                throw new WrongType(e11, "x", -2, apply23);
                            }
                        }
                    }
                } catch (UnboundLocationException e12) {
                    e12.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1222, 5);
                    throw e12;
                }
            }
        }
    }

    public static Object stringSkipRight$V(Object str, Object criterion, Object[] argsArray) {
        frame80 frame80Var = new frame80();
        frame80Var.str = str;
        frame80Var.criterion = criterion;
        frame80Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame80Var.lambda$Fn183, frame80Var.lambda$Fn184);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame81 extends ModuleBody {
        Object criterion;
        final ModuleMethod lambda$Fn185 = new ModuleMethod(this, ComponentConstants.TEXTBOX_PREFERRED_WIDTH, null, 0);
        final ModuleMethod lambda$Fn186 = new ModuleMethod(this, 161, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 160 ? lambda185() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 161 ? lambda186(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 160) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 161) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda185() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncount, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda186(Object start, Object end) {
            if (characters.isChar(this.criterion)) {
                Object count = srfi13.Lit0;
                Object i = start;
                while (Scheme.numGEq.apply2(i, end) == Boolean.FALSE) {
                    Object i2 = AddOp.$Pl.apply2(i, srfi13.Lit1);
                    Object obj = this.criterion;
                    try {
                        Char r4 = (Char) obj;
                        Object obj2 = this.s;
                        try {
                            try {
                                if (characters.isChar$Eq(r4, Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())))) {
                                    count = AddOp.$Pl.apply2(count, srfi13.Lit1);
                                }
                                i = i2;
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, i);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, obj2);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "char=?", 1, obj);
                    }
                }
                return count;
            }
            try {
                if (Scheme.applyToArgs.apply2(srfi13.loc$char$Mnset$Qu.get(), this.criterion) != Boolean.FALSE) {
                    Object count2 = srfi13.Lit0;
                    Object i3 = start;
                    while (Scheme.numGEq.apply2(i3, end) == Boolean.FALSE) {
                        Object i4 = AddOp.$Pl.apply2(i3, srfi13.Lit1);
                        ApplyToArgs applyToArgs = Scheme.applyToArgs;
                        try {
                            Object obj3 = srfi13.loc$char$Mnset$Mncontains$Qu.get();
                            Object obj4 = this.criterion;
                            Object obj5 = this.s;
                            try {
                                try {
                                    if (applyToArgs.apply3(obj3, obj4, Char.make(strings.stringRef((CharSequence) obj5, ((Number) i3).intValue()))) != Boolean.FALSE) {
                                        count2 = AddOp.$Pl.apply2(count2, srfi13.Lit1);
                                    }
                                    i3 = i4;
                                } catch (ClassCastException e4) {
                                    throw new WrongType(e4, "string-ref", 2, i3);
                                }
                            } catch (ClassCastException e5) {
                                throw new WrongType(e5, "string-ref", 1, obj5);
                            }
                        } catch (UnboundLocationException e6) {
                            e6.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1248, 16);
                            throw e6;
                        }
                    }
                    return count2;
                } else if (misc.isProcedure(this.criterion)) {
                    Object count3 = srfi13.Lit0;
                    Object i5 = start;
                    while (Scheme.numGEq.apply2(i5, end) == Boolean.FALSE) {
                        Object i6 = AddOp.$Pl.apply2(i5, srfi13.Lit1);
                        ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
                        Object obj6 = this.criterion;
                        Object obj7 = this.s;
                        try {
                            try {
                                if (applyToArgs2.apply2(obj6, Char.make(strings.stringRef((CharSequence) obj7, ((Number) i5).intValue()))) != Boolean.FALSE) {
                                    count3 = AddOp.$Pl.apply2(count3, srfi13.Lit1);
                                }
                                i5 = i6;
                            } catch (ClassCastException e7) {
                                throw new WrongType(e7, "string-ref", 2, i5);
                            }
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "string-ref", 1, obj7);
                        }
                    }
                    return count3;
                } else {
                    return misc.error$V("CRITERION param is neither char-set or char.", new Object[]{srfi13.string$Mncount, this.criterion});
                }
            } catch (UnboundLocationException e9) {
                e9.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1246, 5);
                throw e9;
            }
        }
    }

    public static Object stringCount$V(Object s, Object criterion, Object[] argsArray) {
        frame81 frame81Var = new frame81();
        frame81Var.s = s;
        frame81Var.criterion = criterion;
        frame81Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame81Var.lambda$Fn185, frame81Var.lambda$Fn186);
    }

    public static Object stringFill$Ex$V(Object s, Object obj, Object[] argsArray) {
        frame82 frame82Var = new frame82();
        frame82Var.s = s;
        frame82Var.f2char = obj;
        frame82Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), characters.char$Qu, frame82Var.f2char, string$Mnfill$Ex);
            return call_with_values.callWithValues(frame82Var.lambda$Fn187, frame82Var.lambda$Fn188);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1270, 3);
            throw e;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame82 extends ModuleBody {

        /* renamed from: char  reason: not valid java name */
        Object f2char;
        final ModuleMethod lambda$Fn187 = new ModuleMethod(this, 162, null, 0);
        final ModuleMethod lambda$Fn188 = new ModuleMethod(this, 163, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 162 ? lambda187() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 163 ? lambda188(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 162) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 163) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda187() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnfill$Ex, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda188(Object start, Object end) {
            for (Object i = AddOp.$Mn.apply2(end, srfi13.Lit1); Scheme.numLss.apply2(i, start) == Boolean.FALSE; i = AddOp.$Mn.apply2(i, srfi13.Lit1)) {
                Object obj = this.s;
                try {
                    CharSeq charSeq = (CharSeq) obj;
                    try {
                        int intValue = ((Number) i).intValue();
                        Object obj2 = this.f2char;
                        try {
                            charSeq.setCharAt(intValue, ((Char) obj2).charValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, obj2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, i);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 1, obj);
                }
            }
            return Values.empty;
        }
    }

    public static Object stringCopy$Ex(Object to, int tstart, CharSequence from, int fstart, int fend) {
        $PcCheckBounds(string$Mncopy$Ex, from, fstart, fend);
        try {
            $PcCheckSubstringSpec(string$Mncopy$Ex, (CharSequence) to, tstart, (fend - fstart) + tstart);
            try {
                return $PcStringCopy$Ex((CharSequence) to, tstart, from, fstart, fend);
            } catch (ClassCastException e) {
                throw new WrongType(e, "%string-copy!", 0, to);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "%check-substring-spec", 1, to);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SOURCE /* 194 */:
                return stringParseStart$PlEnd(obj, obj2, obj3);
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_DURATION /* 196 */:
                return stringParseFinalStart$PlEnd(obj, obj2, obj3);
            case 197:
                return isSubstringSpecOk(obj, obj2, obj3) ? Boolean.TRUE : Boolean.FALSE;
            case ErrorMessages.ERROR_CAMERA_NO_IMAGE_RETURNED /* 201 */:
                try {
                    try {
                        try {
                            return $PcSubstring$SlShared((CharSequence) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%substring/shared", 3, obj3);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%substring/shared", 2, obj2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "%substring/shared", 1, obj);
                }
            case 277:
                return $PcStringTitlecase$Ex(obj, obj2, obj3);
            case 299:
                try {
                    try {
                        return stringCopy$Ex(obj, ((Number) obj2).intValue(), (CharSequence) obj3, 0);
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-copy!", 3, obj3);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-copy!", 2, obj2);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static Object $PcStringCopy$Ex(CharSequence to, int tstart, CharSequence from, int fstart, int fend) {
        if (fstart > tstart) {
            int j = tstart;
            for (int i = fstart; i < fend; i++) {
                try {
                    ((CharSeq) to).setCharAt(j, strings.stringRef(from, i));
                    j++;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-set!", 1, to);
                }
            }
            return Values.empty;
        }
        int j2 = (tstart - 1) + (fend - fstart);
        for (int i2 = fend - 1; i2 >= fstart; i2--) {
            try {
                ((CharSeq) to).setCharAt(j2, strings.stringRef(from, i2));
                j2--;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-set!", 1, to);
            }
        }
        return Values.empty;
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame83 extends ModuleBody {
        final ModuleMethod lambda$Fn189 = new ModuleMethod(this, 166, null, 0);
        final ModuleMethod lambda$Fn190 = new ModuleMethod(this, 167, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object pattern;
        Object text;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 166 ? lambda189() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 167 ? lambda190(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 166) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 167) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda189() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mncontains, this.text, this.maybe$Mnstarts$Plends);
        }

        Object lambda190(Object rest, Object tStart, Object tEnd) {
            frame84 frame84Var = new frame84();
            frame84Var.staticLink = this;
            frame84Var.rest = rest;
            frame84Var.t$Mnstart = tStart;
            frame84Var.t$Mnend = tEnd;
            return call_with_values.callWithValues(frame84Var.lambda$Fn191, frame84Var.lambda$Fn192);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame84 extends ModuleBody {
        final ModuleMethod lambda$Fn191 = new ModuleMethod(this, 164, null, 0);
        final ModuleMethod lambda$Fn192 = new ModuleMethod(this, 165, null, 8194);
        Object rest;
        frame83 staticLink;
        Object t$Mnend;
        Object t$Mnstart;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 164 ? lambda191() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 165 ? lambda192(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 164) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 165) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda191() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncontains, this.staticLink.pattern, this.rest);
        }

        Object lambda192(Object p$Mnstart, Object p$Mnend) {
            return srfi13.$PcKmpSearch(this.staticLink.pattern, this.staticLink.text, characters.char$Eq$Qu, p$Mnstart, p$Mnend, this.t$Mnstart, this.t$Mnend);
        }
    }

    public static Object stringContains$V(Object text, Object pattern, Object[] argsArray) {
        frame83 frame83Var = new frame83();
        frame83Var.text = text;
        frame83Var.pattern = pattern;
        frame83Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame83Var.lambda$Fn189, frame83Var.lambda$Fn190);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame85 extends ModuleBody {
        final ModuleMethod lambda$Fn193 = new ModuleMethod(this, 170, null, 0);
        final ModuleMethod lambda$Fn194 = new ModuleMethod(this, 171, null, 12291);
        LList maybe$Mnstarts$Plends;
        Object pattern;
        Object text;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 170 ? lambda193() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 171 ? lambda194(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 170) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 171) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda193() {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mncontains$Mnci, this.text, this.maybe$Mnstarts$Plends);
        }

        Object lambda194(Object rest, Object tStart, Object tEnd) {
            frame86 frame86Var = new frame86();
            frame86Var.staticLink = this;
            frame86Var.rest = rest;
            frame86Var.t$Mnstart = tStart;
            frame86Var.t$Mnend = tEnd;
            return call_with_values.callWithValues(frame86Var.lambda$Fn195, frame86Var.lambda$Fn196);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame86 extends ModuleBody {
        final ModuleMethod lambda$Fn195 = new ModuleMethod(this, 168, null, 0);
        final ModuleMethod lambda$Fn196 = new ModuleMethod(this, 169, null, 8194);
        Object rest;
        frame85 staticLink;
        Object t$Mnend;
        Object t$Mnstart;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 168 ? lambda195() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 169 ? lambda196(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 168) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 169) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda195() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mncontains$Mnci, this.staticLink.pattern, this.rest);
        }

        Object lambda196(Object p$Mnstart, Object p$Mnend) {
            return srfi13.$PcKmpSearch(this.staticLink.pattern, this.staticLink.text, unicode.char$Mnci$Eq$Qu, p$Mnstart, p$Mnend, this.t$Mnstart, this.t$Mnend);
        }
    }

    public static Object stringContainsCi$V(Object text, Object pattern, Object[] argsArray) {
        frame85 frame85Var = new frame85();
        frame85Var.text = text;
        frame85Var.pattern = pattern;
        frame85Var.maybe$Mnstarts$Plends = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame85Var.lambda$Fn193, frame85Var.lambda$Fn194);
    }

    public static Object $PcKmpSearch(Object pattern, Object text, Object c$Eq, Object p$Mnstart, Object p$Mnend, Object t$Mnstart, Object t$Mnend) {
        Object plen = AddOp.$Mn.apply2(p$Mnend, p$Mnstart);
        Object rv = makeKmpRestartVector$V(pattern, new Object[]{c$Eq, p$Mnstart, p$Mnend});
        Number pi = Lit0;
        Object apply2 = AddOp.$Mn.apply2(t$Mnend, t$Mnstart);
        Object ti = t$Mnstart;
        Object obj = plen;
        while (true) {
            Number number = pi;
            if (Scheme.numEqu.apply2(number, plen) != Boolean.FALSE) {
                return AddOp.$Mn.apply2(ti, plen);
            }
            Object apply22 = Scheme.numLEq.apply2(obj, apply2);
            try {
                boolean x = ((Boolean) apply22).booleanValue();
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                ApplyToArgs applyToArgs = Scheme.applyToArgs;
                try {
                    try {
                        Char make = Char.make(strings.stringRef((CharSequence) text, ((Number) ti).intValue()));
                        try {
                            CharSequence charSequence = (CharSequence) pattern;
                            Object apply23 = AddOp.$Pl.apply2(p$Mnstart, number);
                            try {
                                if (applyToArgs.apply3(c$Eq, make, Char.make(strings.stringRef(charSequence, ((Number) apply23).intValue()))) != Boolean.FALSE) {
                                    ti = AddOp.$Pl.apply2(Lit1, ti);
                                    pi = AddOp.$Pl.apply2(Lit1, number);
                                    apply2 = AddOp.$Mn.apply2(apply2, Lit1);
                                    obj = AddOp.$Mn.apply2(obj, Lit1);
                                } else {
                                    try {
                                        try {
                                            pi = vectors.vectorRef((FVector) rv, number.intValue());
                                            if (Scheme.numEqu.apply2(pi, Lit13) != Boolean.FALSE) {
                                                ti = AddOp.$Pl.apply2(ti, Lit1);
                                                pi = Lit0;
                                                apply2 = AddOp.$Mn.apply2(apply2, Lit1);
                                                obj = plen;
                                            } else {
                                                obj = AddOp.$Mn.apply2(plen, pi);
                                            }
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "vector-ref", 2, number);
                                        }
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "vector-ref", 1, rv);
                                    }
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-ref", 2, apply23);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-ref", 1, pattern);
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "string-ref", 2, ti);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "string-ref", 1, text);
                }
            } catch (ClassCastException e7) {
                throw new WrongType(e7, "x", -2, apply22);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x0258, code lost:
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0259, code lost:
        r13.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1418, 18);
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0262, code lost:
        throw r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x0263, code lost:
        r14 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0271, code lost:
        throw new gnu.mapping.WrongType(r14, "string-ref", 1, r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0272, code lost:
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0280, code lost:
        throw new gnu.mapping.WrongType(r13, "string-ref", 2, r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0281, code lost:
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x028d, code lost:
        throw new gnu.mapping.WrongType(r13, "vector-set!", 2, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x02e0, code lost:
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x02ec, code lost:
        throw new gnu.mapping.WrongType(r13, "vector-set!", 2, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00d6, code lost:
        r5 = gnu.kawa.functions.AddOp.$Pl.apply2(gnu.kawa.slib.srfi13.Lit1, r4);
        r21 = kawa.standard.Scheme.applyToArgs;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00e2, code lost:
        r22 = gnu.kawa.slib.srfi13.loc$c$Eq.get();
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00e6, code lost:
        r13 = r17.pattern;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ea, code lost:
        r13 = (java.lang.CharSequence) r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ec, code lost:
        r15 = gnu.kawa.functions.AddOp.$Pl.apply2(r16, gnu.kawa.slib.srfi13.Lit1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0114, code lost:
        if (r21.apply3(r22, gnu.text.Char.make(kawa.lib.strings.stringRef(r13, ((java.lang.Number) r15).intValue())), gnu.text.Char.make(r3)) != java.lang.Boolean.FALSE) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x011e, code lost:
        r10.set(((java.lang.Number) r5).intValue(), gnu.kawa.slib.srfi13.Lit0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0123, code lost:
        r6 = gnu.kawa.slib.srfi13.Lit0;
        r8 = gnu.kawa.functions.AddOp.$Pl.apply2(r16, gnu.kawa.slib.srfi13.Lit1);
        r4 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0186, code lost:
        r5 = gnu.kawa.functions.AddOp.$Pl.apply2(gnu.kawa.slib.srfi13.Lit1, r4);
        r7 = gnu.kawa.functions.AddOp.$Pl.apply2(gnu.kawa.slib.srfi13.Lit1, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x019e, code lost:
        r10.set(((java.lang.Number) r5).intValue(), r7);
        r8 = gnu.kawa.functions.AddOp.$Pl.apply2(r16, gnu.kawa.slib.srfi13.Lit1);
        r6 = r7;
        r4 = r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object makeKmpRestartVector$V(java.lang.Object r24, java.lang.Object[] r25) {
        /*
            Method dump skipped, instructions count: 762
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi13.makeKmpRestartVector$V(java.lang.Object, java.lang.Object[]):java.lang.Object");
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame87 extends ModuleBody {
        final ModuleMethod lambda$Fn197;
        Object pattern;

        public frame87() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 172, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1399");
            this.lambda$Fn197 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 172 ? lambda197(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda197(Object args) {
            return srfi13.stringParseStart$PlEnd(srfi13.make$Mnkmp$Mnrestart$Mnvector, this.pattern, args);
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 172) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object kmpStep(Object pat, Object rv, Object c, Object i, Object c$Eq, Object p$Mnstart) {
        do {
            ApplyToArgs applyToArgs = Scheme.applyToArgs;
            try {
                CharSequence charSequence = (CharSequence) pat;
                Object apply2 = AddOp.$Pl.apply2(i, p$Mnstart);
                try {
                    if (applyToArgs.apply3(c$Eq, c, Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue()))) != Boolean.FALSE) {
                        return AddOp.$Pl.apply2(i, Lit1);
                    }
                    try {
                        try {
                            i = vectors.vectorRef((FVector) rv, ((Number) i).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "vector-ref", 2, i);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "vector-ref", 1, rv);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-ref", 2, apply2);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "string-ref", 1, pat);
            }
        } while (Scheme.numEqu.apply2(i, Lit13) == Boolean.FALSE);
        return Lit0;
    }

    public static Object stringKmpPartialSearch$V(Object pat, Object rv, Object s, Object i, Object[] argsArray) {
        Object obj;
        frame88 frame88Var = new frame88();
        frame88Var.s = s;
        LList c$Eq$Plp$Mnstart$Pls$Mnstart$Pls$Mnend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), vectors.vector$Qu, rv, string$Mnkmp$Mnpartial$Mnsearch);
            ApplyToArgs applyToArgs = Scheme.applyToArgs;
            try {
                Object obj2 = loc$let$Mnoptionals$St.get();
                ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
                try {
                    try {
                        Object apply3 = Scheme.applyToArgs.apply3(loc$c$Eq.get(), characters.char$Eq$Qu, misc.isProcedure(loc$c$Eq.get()) ? Boolean.TRUE : Boolean.FALSE);
                        ApplyToArgs applyToArgs3 = Scheme.applyToArgs;
                        try {
                            Object obj3 = loc$p$Mnstart.get();
                            IntNum intNum = Lit0;
                            try {
                                boolean x = numbers.isInteger(loc$p$Mnstart.get());
                                if (x) {
                                    try {
                                        boolean x2 = numbers.isExact(loc$p$Mnstart.get());
                                        if (x2) {
                                            try {
                                                obj = Scheme.numLEq.apply2(Lit0, loc$p$Mnstart.get());
                                            } catch (UnboundLocationException e) {
                                                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1467, 64);
                                                throw e;
                                            }
                                        } else {
                                            obj = x2 ? Boolean.TRUE : Boolean.FALSE;
                                        }
                                    } catch (UnboundLocationException e2) {
                                        e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1467, 49);
                                        throw e2;
                                    }
                                } else {
                                    obj = x ? Boolean.TRUE : Boolean.FALSE;
                                }
                                try {
                                    try {
                                        Object apply32 = applyToArgs2.apply3(apply3, applyToArgs3.apply3(obj3, intNum, obj), Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply2(loc$s$Mnstart.get(), loc$s$Mnend.get()), frame88Var.lambda$Fn198));
                                        try {
                                            frame88Var.patlen = vectors.vectorLength((FVector) rv);
                                            try {
                                                Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), frame88Var.lambda$Fn199, i, string$Mnkmp$Mnpartial$Mnsearch);
                                                try {
                                                    Object si = loc$s$Mnstart.get();
                                                    Object vi = i;
                                                    while (true) {
                                                        if (Scheme.numEqu.apply2(vi, Integer.valueOf(frame88Var.patlen)) != Boolean.FALSE) {
                                                            vi = AddOp.$Mn.apply1(si);
                                                            break;
                                                        }
                                                        try {
                                                            if (Scheme.numEqu.apply2(si, loc$s$Mnend.get()) != Boolean.FALSE) {
                                                                break;
                                                            }
                                                            Object obj4 = frame88Var.s;
                                                            try {
                                                                try {
                                                                    char c = strings.stringRef((CharSequence) obj4, ((Number) si).intValue());
                                                                    si = AddOp.$Pl.apply2(si, Lit1);
                                                                    while (true) {
                                                                        ApplyToArgs applyToArgs4 = Scheme.applyToArgs;
                                                                        try {
                                                                            Object obj5 = loc$c$Eq.get();
                                                                            Char make = Char.make(c);
                                                                            try {
                                                                                CharSequence charSequence = (CharSequence) pat;
                                                                                try {
                                                                                    Object apply2 = AddOp.$Pl.apply2(vi, loc$p$Mnstart.get());
                                                                                    try {
                                                                                        if (applyToArgs4.apply3(obj5, make, Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue()))) != Boolean.FALSE) {
                                                                                            vi = AddOp.$Pl.apply2(vi, Lit1);
                                                                                            break;
                                                                                        }
                                                                                        try {
                                                                                            try {
                                                                                                vi = vectors.vectorRef((FVector) rv, ((Number) vi).intValue());
                                                                                                if (Scheme.numEqu.apply2(vi, Lit13) != Boolean.FALSE) {
                                                                                                    vi = Lit0;
                                                                                                    break;
                                                                                                }
                                                                                            } catch (ClassCastException e3) {
                                                                                                throw new WrongType(e3, "vector-ref", 2, vi);
                                                                                            }
                                                                                        } catch (ClassCastException e4) {
                                                                                            throw new WrongType(e4, "vector-ref", 1, rv);
                                                                                        }
                                                                                    } catch (ClassCastException e5) {
                                                                                        throw new WrongType(e5, "string-ref", 2, apply2);
                                                                                    }
                                                                                } catch (UnboundLocationException e6) {
                                                                                    e6.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1484, 42);
                                                                                    throw e6;
                                                                                }
                                                                            } catch (ClassCastException e7) {
                                                                                throw new WrongType(e7, "string-ref", 1, pat);
                                                                            }
                                                                        } catch (UnboundLocationException e8) {
                                                                            e8.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1484, 14);
                                                                            throw e8;
                                                                        }
                                                                    }
                                                                } catch (ClassCastException e9) {
                                                                    throw new WrongType(e9, "string-ref", 2, si);
                                                                }
                                                            } catch (ClassCastException e10) {
                                                                throw new WrongType(e10, "string-ref", 1, obj4);
                                                            }
                                                        } catch (UnboundLocationException e11) {
                                                            e11.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1479, 15);
                                                            throw e11;
                                                        }
                                                    }
                                                    return applyToArgs.apply4(obj2, c$Eq$Plp$Mnstart$Pls$Mnstart$Pls$Mnend, apply32, vi);
                                                } catch (UnboundLocationException e12) {
                                                    e12.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1476, 7);
                                                    throw e12;
                                                }
                                            } catch (UnboundLocationException e13) {
                                                e13.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1472, 7);
                                                throw e13;
                                            }
                                        } catch (ClassCastException e14) {
                                            throw new WrongType(e14, "vector-length", 1, rv);
                                        }
                                    } catch (UnboundLocationException e15) {
                                        e15.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1468, 16);
                                        throw e15;
                                    }
                                } catch (UnboundLocationException e16) {
                                    e16.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1468, 7);
                                    throw e16;
                                }
                            } catch (UnboundLocationException e17) {
                                e17.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1467, 32);
                                throw e17;
                            }
                        } catch (UnboundLocationException e18) {
                            e18.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1467, 6);
                            throw e18;
                        }
                    } catch (UnboundLocationException e19) {
                        e19.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1466, 34);
                        throw e19;
                    }
                } catch (UnboundLocationException e20) {
                    e20.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1466, 6);
                    throw e20;
                }
            } catch (UnboundLocationException e21) {
                e21.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1465, 3);
                throw e21;
            }
        } catch (UnboundLocationException e22) {
            e22.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1464, 3);
            throw e22;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame88 extends ModuleBody {
        final ModuleMethod lambda$Fn198;
        final ModuleMethod lambda$Fn199;
        int patlen;
        Object s;

        public frame88() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 173, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1468");
            this.lambda$Fn198 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 174, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1472");
            this.lambda$Fn199 = moduleMethod2;
        }

        Object lambda198(Object args) {
            return srfi13.stringParseStart$PlEnd(srfi13.string$Mnkmp$Mnpartial$Mnsearch, this.s, args);
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 173:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 174:
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
                case 173:
                    return lambda198(obj);
                case 174:
                    return lambda199(obj) ? Boolean.TRUE : Boolean.FALSE;
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda199(Object i) {
            boolean x = numbers.isInteger(i);
            if (x) {
                boolean x2 = numbers.isExact(i);
                if (x2) {
                    Object apply2 = Scheme.numLEq.apply2(srfi13.Lit0, i);
                    try {
                        boolean x3 = ((Boolean) apply2).booleanValue();
                        return x3 ? ((Boolean) Scheme.numLss.apply2(i, Integer.valueOf(this.patlen))).booleanValue() : x3;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "x", -2, apply2);
                    }
                }
                return x2;
            }
            return x;
        }
    }

    public static boolean isStringNull(Object s) {
        try {
            return numbers.isZero(Integer.valueOf(strings.stringLength((CharSequence) s)));
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, s);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame89 extends ModuleBody {
        final ModuleMethod lambda$Fn200 = new ModuleMethod(this, 175, null, 0);
        final ModuleMethod lambda$Fn201 = new ModuleMethod(this, 176, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 175 ? lambda200() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 176 ? lambda201(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 175) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 176) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda200() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnreverse, this.s, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda201(Object start, Object end) {
            Object len = AddOp.$Mn.apply2(end, start);
            try {
                CharSequence ans = strings.makeString(((Number) len).intValue());
                Object apply2 = AddOp.$Mn.apply2(len, srfi13.Lit1);
                Object i = start;
                while (Scheme.numLss.apply2(apply2, srfi13.Lit0) == Boolean.FALSE) {
                    try {
                        CharSeq charSeq = (CharSeq) ans;
                        try {
                            int intValue = ((Number) apply2).intValue();
                            Object obj = this.s;
                            try {
                                try {
                                    charSeq.setCharAt(intValue, strings.stringRef((CharSequence) obj, ((Number) i).intValue()));
                                    i = AddOp.$Pl.apply2(i, srfi13.Lit1);
                                    Object j = AddOp.$Mn.apply2(apply2, srfi13.Lit1);
                                    apply2 = j;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-set!", 2, apply2);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-set!", 1, ans);
                    }
                }
                return ans;
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "make-string", 1, len);
            }
        }
    }

    public static Object stringReverse$V(Object s, Object[] argsArray) {
        frame89 frame89Var = new frame89();
        frame89Var.s = s;
        frame89Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame89Var.lambda$Fn200, frame89Var.lambda$Fn201);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame90 extends ModuleBody {
        final ModuleMethod lambda$Fn202 = new ModuleMethod(this, 177, null, 0);
        final ModuleMethod lambda$Fn203 = new ModuleMethod(this, 178, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 177 ? lambda202() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 178 ? lambda203(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 177) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 178) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda202() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnreverse$Ex, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda203(Object start, Object end) {
            Object i = AddOp.$Mn.apply2(end, srfi13.Lit1);
            Object obj = start;
            while (Scheme.numLEq.apply2(i, obj) == Boolean.FALSE) {
                Object obj2 = this.s;
                try {
                    try {
                        char ci = strings.stringRef((CharSequence) obj2, ((Number) i).intValue());
                        Object obj3 = this.s;
                        try {
                            CharSeq charSeq = (CharSeq) obj3;
                            try {
                                int intValue = ((Number) i).intValue();
                                Object obj4 = this.s;
                                try {
                                    try {
                                        charSeq.setCharAt(intValue, strings.stringRef((CharSequence) obj4, ((Number) obj).intValue()));
                                        Object obj5 = this.s;
                                        try {
                                            try {
                                                ((CharSeq) obj5).setCharAt(((Number) obj).intValue(), ci);
                                                i = AddOp.$Mn.apply2(i, srfi13.Lit1);
                                                Object j = AddOp.$Pl.apply2(obj, srfi13.Lit1);
                                                obj = j;
                                            } catch (ClassCastException e) {
                                                throw new WrongType(e, "string-set!", 2, obj);
                                            }
                                        } catch (ClassCastException e2) {
                                            throw new WrongType(e2, "string-set!", 1, obj5);
                                        }
                                    } catch (ClassCastException e3) {
                                        throw new WrongType(e3, "string-ref", 2, obj);
                                    }
                                } catch (ClassCastException e4) {
                                    throw new WrongType(e4, "string-ref", 1, obj4);
                                }
                            } catch (ClassCastException e5) {
                                throw new WrongType(e5, "string-set!", 2, i);
                            }
                        } catch (ClassCastException e6) {
                            throw new WrongType(e6, "string-set!", 1, obj3);
                        }
                    } catch (ClassCastException e7) {
                        throw new WrongType(e7, "string-ref", 2, i);
                    }
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "string-ref", 1, obj2);
                }
            }
            return Values.empty;
        }
    }

    public static Object stringReverse$Ex$V(Object s, Object[] argsArray) {
        frame90 frame90Var = new frame90();
        frame90Var.s = s;
        frame90Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame90Var.lambda$Fn202, frame90Var.lambda$Fn203);
    }

    public static CharSequence reverseList$To$String(Object clist) {
        try {
            int len = lists.length((LList) clist);
            CharSequence s = strings.makeString(len);
            Object valueOf = Integer.valueOf(len - 1);
            while (lists.isPair(clist)) {
                try {
                    CharSeq charSeq = (CharSeq) s;
                    try {
                        int intValue = ((Number) valueOf).intValue();
                        Object apply1 = lists.car.apply1(clist);
                        try {
                            charSeq.setCharAt(intValue, ((Char) apply1).charValue());
                            valueOf = AddOp.$Mn.apply2(valueOf, Lit1);
                            clist = lists.cdr.apply1(clist);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, apply1);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, valueOf);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 1, s);
                }
            }
            return s;
        } catch (ClassCastException e4) {
            throw new WrongType(e4, PropertyTypeConstants.PROPERTY_TYPE_LENGTH, 1, clist);
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame91 extends ModuleBody {
        final ModuleMethod lambda$Fn204 = new ModuleMethod(this, 179, null, 0);
        final ModuleMethod lambda$Fn205 = new ModuleMethod(this, 180, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 179 ? lambda204() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 180 ? lambda205(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 179) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 180) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda204() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mn$Grlist, this.s, this.maybe$Mnstart$Plend);
        }

        Object lambda205(Object start, Object end) {
            Object i = AddOp.$Mn.apply2(end, srfi13.Lit1);
            Object obj = LList.Empty;
            while (Scheme.numLss.apply2(i, start) == Boolean.FALSE) {
                Object i2 = AddOp.$Mn.apply2(i, srfi13.Lit1);
                Object obj2 = this.s;
                try {
                    try {
                        obj = lists.cons(Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())), obj);
                        i = i2;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, i);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, obj2);
                }
            }
            return obj;
        }
    }

    public static Object string$To$List$V(Object s, Object[] argsArray) {
        frame91 frame91Var = new frame91();
        frame91Var.s = s;
        frame91Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        return call_with_values.callWithValues(frame91Var.lambda$Fn204, frame91Var.lambda$Fn205);
    }

    public static Object stringAppend$SlShared$V(Object[] argsArray) {
        LList strings = LList.makeList(argsArray, 0);
        return stringConcatenate$SlShared(strings);
    }

    public static Object stringConcatenate$SlShared(Object strings) {
        Object obj = Lit0;
        Boolean first = Boolean.FALSE;
        while (lists.isPair(strings)) {
            Object string = lists.car.apply1(strings);
            Object tail = lists.cdr.apply1(strings);
            try {
                int slen = strings.stringLength((CharSequence) string);
                if (numbers.isZero(Integer.valueOf(slen))) {
                    strings = tail;
                } else {
                    obj = AddOp.$Pl.apply2(obj, Integer.valueOf(slen));
                    if (first == Boolean.FALSE) {
                        first = strings;
                    }
                    strings = tail;
                }
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, string);
            }
        }
        try {
            if (numbers.isZero((Number) obj)) {
                return "";
            }
            NumberCompare numberCompare = Scheme.numEqu;
            Object apply1 = lists.car.apply1(first);
            try {
                if (numberCompare.apply2(obj, Integer.valueOf(strings.stringLength((CharSequence) apply1))) != Boolean.FALSE) {
                    return lists.car.apply1(first);
                }
                try {
                    CharSequence ans = strings.makeString(((Number) obj).intValue());
                    IntNum intNum = Lit0;
                    Object strings2 = first;
                    while (lists.isPair(strings2)) {
                        Object s = lists.car.apply1(strings2);
                        try {
                            int slen2 = strings.stringLength((CharSequence) s);
                            try {
                                try {
                                    $PcStringCopy$Ex(ans, intNum.intValue(), (CharSequence) s, 0, slen2);
                                    strings2 = lists.cdr.apply1(strings2);
                                    Object i = AddOp.$Pl.apply2(intNum, Integer.valueOf(slen2));
                                    intNum = i;
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "%string-copy!", 2, s);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "%string-copy!", 1, intNum);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-length", 1, s);
                        }
                    }
                    return ans;
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "make-string", 1, obj);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-length", 1, apply1);
            }
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "zero?", 1, obj);
        }
    }

    public static CharSequence stringConcatenate(Object strings) {
        IntNum intNum = Lit0;
        Object strings2 = strings;
        while (lists.isPair(strings2)) {
            Object strings3 = lists.cdr.apply1(strings2);
            AddOp addOp = AddOp.$Pl;
            Object apply1 = lists.car.apply1(strings2);
            try {
                Object i = addOp.apply2(intNum, Integer.valueOf(strings.stringLength((CharSequence) apply1)));
                intNum = i;
                strings2 = strings3;
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, apply1);
            }
        }
        try {
            CharSequence ans = strings.makeString(intNum.intValue());
            Object obj = Lit0;
            while (lists.isPair(strings)) {
                Object s = lists.car.apply1(strings);
                try {
                    int slen = strings.stringLength((CharSequence) s);
                    try {
                        try {
                            $PcStringCopy$Ex(ans, ((Number) obj).intValue(), (CharSequence) s, 0, slen);
                            obj = AddOp.$Pl.apply2(obj, Integer.valueOf(slen));
                            strings = lists.cdr.apply1(strings);
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%string-copy!", 2, s);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "%string-copy!", 1, obj);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "string-length", 1, s);
                }
            }
            return ans;
        } catch (ClassCastException e5) {
            throw new WrongType(e5, "make-string", 1, intNum);
        }
    }

    public static Object stringConcatenateReverse$V(Object string$Mnlist, Object[] argsArray) {
        Object obj;
        Object obj2;
        Object obj3;
        LList maybe$Mnfinal$Plend = LList.makeList(argsArray, 0);
        ApplyToArgs applyToArgs = Scheme.applyToArgs;
        try {
            Object obj4 = loc$let$Mnoptionals$St.get();
            ApplyToArgs applyToArgs2 = Scheme.applyToArgs;
            try {
                try {
                    Object apply3 = Scheme.applyToArgs.apply3(loc$final.get(), "", strings.isString(loc$final.get()) ? Boolean.TRUE : Boolean.FALSE);
                    ApplyToArgs applyToArgs3 = Scheme.applyToArgs;
                    try {
                        Object obj5 = loc$end.get();
                        try {
                            try {
                                Integer valueOf = Integer.valueOf(strings.stringLength((CharSequence) loc$final.get()));
                                try {
                                    boolean x = numbers.isInteger(loc$end.get());
                                    if (x) {
                                        try {
                                            boolean x2 = numbers.isExact(loc$end.get());
                                            if (x2) {
                                                try {
                                                    try {
                                                        try {
                                                            obj2 = Scheme.numLEq.apply3(Lit0, loc$end.get(), Integer.valueOf(strings.stringLength((CharSequence) loc$final.get())));
                                                        } catch (ClassCastException e) {
                                                            throw new WrongType(e, "string-length", 1, obj3);
                                                        }
                                                    } catch (UnboundLocationException e2) {
                                                        e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1621, 36);
                                                        throw e2;
                                                    }
                                                } catch (UnboundLocationException e3) {
                                                    e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1621, 17);
                                                    throw e3;
                                                }
                                            } else {
                                                obj2 = x2 ? Boolean.TRUE : Boolean.FALSE;
                                            }
                                        } catch (UnboundLocationException e4) {
                                            e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1620, 19);
                                            throw e4;
                                        }
                                    } else {
                                        obj2 = x ? Boolean.TRUE : Boolean.FALSE;
                                    }
                                    Object apply2 = applyToArgs2.apply2(apply3, applyToArgs3.apply3(obj5, valueOf, obj2));
                                    Object obj6 = Lit0;
                                    Object obj7 = string$Mnlist;
                                    while (true) {
                                        Object sum = obj6;
                                        if (!lists.isPair(obj7)) {
                                            try {
                                                try {
                                                    return applyToArgs.apply4(obj4, maybe$Mnfinal$Plend, apply2, $PcFinishStringConcatenateReverse(sum, string$Mnlist, loc$final.get(), loc$end.get()));
                                                } catch (UnboundLocationException e5) {
                                                    e5.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1627, 65);
                                                    throw e5;
                                                }
                                            } catch (UnboundLocationException e6) {
                                                e6.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1627, 59);
                                                throw e6;
                                            }
                                        }
                                        AddOp addOp = AddOp.$Pl;
                                        Object apply1 = lists.car.apply1(obj7);
                                        try {
                                            obj6 = addOp.apply2(sum, Integer.valueOf(strings.stringLength((CharSequence) apply1)));
                                            obj7 = lists.cdr.apply1(obj7);
                                        } catch (ClassCastException e7) {
                                            throw new WrongType(e7, "string-length", 1, apply1);
                                        }
                                    }
                                } catch (UnboundLocationException e8) {
                                    e8.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1619, 21);
                                    throw e8;
                                }
                            } catch (ClassCastException e9) {
                                throw new WrongType(e9, "string-length", 1, obj);
                            }
                        } catch (UnboundLocationException e10) {
                            e10.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1618, 28);
                            throw e10;
                        }
                    } catch (UnboundLocationException e11) {
                        e11.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1618, 8);
                        throw e11;
                    }
                } catch (UnboundLocationException e12) {
                    e12.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1617, 55);
                    throw e12;
                }
            } catch (UnboundLocationException e13) {
                e13.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1617, 36);
                throw e13;
            }
        } catch (UnboundLocationException e14) {
            e14.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1617, 3);
            throw e14;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:128:0x0205, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0206, code lost:
        r9.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1652, 56);
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x020f, code lost:
        throw r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0210, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0211, code lost:
        r9.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1652, 62);
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x021a, code lost:
        throw r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x012a, code lost:
        if (r13.apply2(r3, java.lang.Integer.valueOf(kawa.lib.strings.stringLength((java.lang.CharSequence) r9))) != java.lang.Boolean.FALSE) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x012c, code lost:
        r9 = kawa.lib.lists.car.apply1(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0133, code lost:
        if (r8 == false) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0141, code lost:
        r9 = $PcFinishStringConcatenateReverse(r3, r6, gnu.kawa.slib.srfi13.loc$final.get(), gnu.kawa.slib.srfi13.loc$end.get());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object stringConcatenateReverse$SlShared$V(java.lang.Object r20, java.lang.Object[] r21) {
        /*
            Method dump skipped, instructions count: 539
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi13.stringConcatenateReverse$SlShared$V(java.lang.Object, java.lang.Object[]):java.lang.Object");
    }

    public static Object $PcFinishStringConcatenateReverse(Object len, Object string$Mnlist, Object obj, Object end) {
        Object apply2 = AddOp.$Pl.apply2(end, len);
        try {
            CharSequence ans = strings.makeString(((Number) apply2).intValue());
            try {
                try {
                    try {
                        $PcStringCopy$Ex(ans, ((Number) len).intValue(), (CharSequence) obj, 0, ((Number) end).intValue());
                        Object i = len;
                        while (lists.isPair(string$Mnlist)) {
                            Object s = lists.car.apply1(string$Mnlist);
                            Object lis = lists.cdr.apply1(string$Mnlist);
                            try {
                                int slen = strings.stringLength((CharSequence) s);
                                i = AddOp.$Mn.apply2(i, Integer.valueOf(slen));
                                try {
                                    try {
                                        $PcStringCopy$Ex(ans, ((Number) i).intValue(), (CharSequence) s, 0, slen);
                                        string$Mnlist = lis;
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "%string-copy!", 2, s);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "%string-copy!", 1, i);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-length", 1, s);
                            }
                        }
                        return ans;
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "%string-copy!", 4, end);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "%string-copy!", 2, obj);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "%string-copy!", 1, len);
            }
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "make-string", 1, apply2);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        switch (moduleMethod.selector) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_FULLSCREEN /* 195 */:
                try {
                    try {
                        try {
                            return $PcCheckBounds(obj, (CharSequence) obj2, ((Number) obj3).intValue(), ((Number) obj4).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "%check-bounds", 4, obj4);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%check-bounds", 3, obj3);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "%check-bounds", 2, obj2);
                }
            case 198:
                return checkSubstringSpec(obj, obj2, obj3, obj4);
            case 204:
                return $PcStringMap(obj, obj2, obj3, obj4);
            case 206:
                return $PcStringMap$Ex(obj, obj2, obj3, obj4);
            case 299:
                try {
                    try {
                        try {
                            return stringCopy$Ex(obj, ((Number) obj2).intValue(), (CharSequence) obj3, ((Number) obj4).intValue());
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-copy!", 4, obj4);
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "string-copy!", 3, obj3);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "string-copy!", 2, obj2);
                }
            case 319:
                return $PcFinishStringConcatenateReverse(obj, obj2, obj3, obj4);
            default:
                return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }
    }

    public static Object stringReplace$V(Object s1, Object s2, Object start1, Object end1, Object[] argsArray) {
        frame92 frame92Var = new frame92();
        frame92Var.s1 = s1;
        frame92Var.s2 = s2;
        frame92Var.start1 = start1;
        frame92Var.end1 = end1;
        frame92Var.maybe$Mnstart$Plend = LList.makeList(argsArray, 0);
        checkSubstringSpec(string$Mnreplace, frame92Var.s1, frame92Var.start1, frame92Var.end1);
        return call_with_values.callWithValues(frame92Var.lambda$Fn206, frame92Var.lambda$Fn207);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame92 extends ModuleBody {
        Object end1;
        final ModuleMethod lambda$Fn206 = new ModuleMethod(this, 181, null, 0);
        final ModuleMethod lambda$Fn207 = new ModuleMethod(this, 182, null, 8194);
        LList maybe$Mnstart$Plend;
        Object s1;
        Object s2;
        Object start1;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 181 ? lambda206() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 182 ? lambda207(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 181) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 182) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda206() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnreplace, this.s2, this.maybe$Mnstart$Plend);
        }

        CharSequence lambda207(Object start2, Object end2) {
            Object obj = this.s1;
            try {
                int slen1 = strings.stringLength((CharSequence) obj);
                Object sublen2 = AddOp.$Mn.apply2(end2, start2);
                Object alen = AddOp.$Pl.apply2(AddOp.$Mn.apply2(Integer.valueOf(slen1), AddOp.$Mn.apply2(this.end1, this.start1)), sublen2);
                try {
                    CharSequence ans = strings.makeString(((Number) alen).intValue());
                    Object obj2 = this.s1;
                    try {
                        CharSequence charSequence = (CharSequence) obj2;
                        Object obj3 = this.start1;
                        try {
                            srfi13.$PcStringCopy$Ex(ans, 0, charSequence, 0, ((Number) obj3).intValue());
                            Object obj4 = this.start1;
                            try {
                                int intValue = ((Number) obj4).intValue();
                                Object obj5 = this.s2;
                                try {
                                    try {
                                        try {
                                            srfi13.$PcStringCopy$Ex(ans, intValue, (CharSequence) obj5, ((Number) start2).intValue(), ((Number) end2).intValue());
                                            Object apply2 = AddOp.$Pl.apply2(this.start1, sublen2);
                                            try {
                                                int intValue2 = ((Number) apply2).intValue();
                                                Object obj6 = this.s1;
                                                try {
                                                    CharSequence charSequence2 = (CharSequence) obj6;
                                                    Object obj7 = this.end1;
                                                    try {
                                                        srfi13.$PcStringCopy$Ex(ans, intValue2, charSequence2, ((Number) obj7).intValue(), slen1);
                                                        return ans;
                                                    } catch (ClassCastException e) {
                                                        throw new WrongType(e, "%string-copy!", 3, obj7);
                                                    }
                                                } catch (ClassCastException e2) {
                                                    throw new WrongType(e2, "%string-copy!", 2, obj6);
                                                }
                                            } catch (ClassCastException e3) {
                                                throw new WrongType(e3, "%string-copy!", 1, apply2);
                                            }
                                        } catch (ClassCastException e4) {
                                            throw new WrongType(e4, "%string-copy!", 4, end2);
                                        }
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "%string-copy!", 3, start2);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "%string-copy!", 2, obj5);
                                }
                            } catch (ClassCastException e7) {
                                throw new WrongType(e7, "%string-copy!", 1, obj4);
                            }
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "%string-copy!", 4, obj3);
                        }
                    } catch (ClassCastException e9) {
                        throw new WrongType(e9, "%string-copy!", 2, obj2);
                    }
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "make-string", 1, alen);
                }
            } catch (ClassCastException e11) {
                throw new WrongType(e11, "string-length", 1, obj);
            }
        }
    }

    public static Object stringTokenize$V(Object s, Object[] argsArray) {
        frame93 frame93Var = new frame93();
        frame93Var.s = s;
        LList token$Mnchars$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                return Scheme.applyToArgs.apply4(loc$let$Mnoptionals$St.get(), token$Mnchars$Plstart$Plend, Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply3(loc$token$Mnchars.get(), GetNamedPart.getNamedPart.apply2(loc$char$Mnset.get(), Lit14), Scheme.applyToArgs.apply2(loc$char$Mnset$Qu.get(), loc$token$Mnchars.get())), loc$rest.get()), call_with_values.callWithValues(frame93Var.lambda$Fn208, frame93Var.lambda$Fn209));
                            } catch (UnboundLocationException e) {
                                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1695, 75);
                                throw e;
                            }
                        } catch (UnboundLocationException e2) {
                            e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1695, 61);
                            throw e2;
                        }
                    } catch (UnboundLocationException e3) {
                        e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1695, 50);
                        throw e3;
                    }
                } catch (UnboundLocationException e4) {
                    e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1695, 33);
                    throw e4;
                }
            } catch (UnboundLocationException e5) {
                e5.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1695, 20);
                throw e5;
            }
        } catch (UnboundLocationException e6) {
            e6.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1694, 3);
            throw e6;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame93 extends ModuleBody {
        final ModuleMethod lambda$Fn208 = new ModuleMethod(this, 183, null, 0);
        final ModuleMethod lambda$Fn209 = new ModuleMethod(this, 184, null, 8194);
        Object s;

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 183 ? lambda208() : super.apply0(moduleMethod);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 184 ? lambda209(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector == 183) {
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            }
            return super.match0(moduleMethod, callContext);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 184) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        Object lambda208() {
            try {
                return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mntokenize, this.s, srfi13.loc$rest.get());
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1696, 57);
                throw e;
            }
        }

        Object lambda209(Object start, Object end) {
            Object temp;
            Object obj = LList.Empty;
            Object i = end;
            while (true) {
                Object apply2 = Scheme.numLss.apply2(start, i);
                try {
                    boolean x = ((Boolean) apply2).booleanValue();
                    if (x) {
                        try {
                            temp = srfi13.stringIndexRight$V(this.s, srfi13.loc$token$Mnchars.get(), new Object[]{start, i});
                        } catch (UnboundLocationException e) {
                            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1698, 48);
                            throw e;
                        }
                    } else {
                        temp = x ? Boolean.TRUE : Boolean.FALSE;
                    }
                    if (temp == Boolean.FALSE) {
                        return obj;
                    }
                    Object tend = AddOp.$Pl.apply2(srfi13.Lit1, temp);
                    try {
                        Object temp2 = srfi13.stringSkipRight$V(this.s, srfi13.loc$token$Mnchars.get(), new Object[]{start, temp});
                        if (temp2 != Boolean.FALSE) {
                            Object obj2 = this.s;
                            try {
                                CharSequence charSequence = (CharSequence) obj2;
                                Object apply22 = AddOp.$Pl.apply2(srfi13.Lit1, temp2);
                                try {
                                    try {
                                        obj = lists.cons(strings.substring(charSequence, ((Number) apply22).intValue(), ((Number) tend).intValue()), obj);
                                        i = temp2;
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "substring", 3, tend);
                                    }
                                } catch (ClassCastException e3) {
                                    throw new WrongType(e3, "substring", 2, apply22);
                                }
                            } catch (ClassCastException e4) {
                                throw new WrongType(e4, "substring", 1, obj2);
                            }
                        } else {
                            Object obj3 = this.s;
                            try {
                                try {
                                    try {
                                        return lists.cons(strings.substring((CharSequence) obj3, ((Number) start).intValue(), ((Number) tend).intValue()), obj);
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "substring", 3, tend);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "substring", 2, start);
                                }
                            } catch (ClassCastException e7) {
                                throw new WrongType(e7, "substring", 1, obj3);
                            }
                        }
                    } catch (UnboundLocationException e8) {
                        e8.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", ErrorMessages.ERROR_BAD_VALUE_FOR_TEXT_RECEIVING, 34);
                        throw e8;
                    }
                } catch (ClassCastException e9) {
                    throw new WrongType(e9, "x", -2, apply2);
                }
            }
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame94 extends ModuleBody {
        Object from;
        final ModuleMethod lambda$Fn211;
        final ModuleMethod lambda$Fn212 = new ModuleMethod(this, 185, null, 0);
        final ModuleMethod lambda$Fn213;
        final ModuleMethod lambda$Fn214;
        final ModuleMethod lambda$Fn215;
        LList maybe$Mnto$Plstart$Plend;
        Object s;

        public frame94() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 186, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1744");
            this.lambda$Fn214 = moduleMethod;
            this.lambda$Fn213 = new ModuleMethod(this, 187, null, 8194);
            this.lambda$Fn211 = new ModuleMethod(this, 188, null, 0);
            ModuleMethod moduleMethod2 = new ModuleMethod(this, FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG, null, 12291);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1740");
            this.lambda$Fn215 = moduleMethod2;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 185:
                    return lambda212();
                case 186:
                case 187:
                default:
                    return super.apply0(moduleMethod);
                case 188:
                    return lambda211();
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 187 ? lambda213(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 185:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 186:
                case 187:
                default:
                    return super.match0(moduleMethod, callContext);
                case 188:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 187) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        static boolean lambda210(Object val) {
            boolean x = numbers.isInteger(val);
            return x ? numbers.isExact(val) : x;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 189 ? lambda215(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda215(Object to, Object start, Object end) {
            Object slen = AddOp.$Mn.apply2(end, start);
            Object anslen = AddOp.$Mn.apply2(to, this.from);
            try {
                if (numbers.isZero((Number) anslen)) {
                    return "";
                }
                try {
                    if (numbers.isZero((Number) slen)) {
                        return misc.error$V("Cannot replicate empty (sub)string", new Object[]{srfi13.xsubstring, this.s, this.from, to, start, end});
                    }
                    if (Scheme.numEqu.apply2(srfi13.Lit1, slen) != Boolean.FALSE) {
                        try {
                            int intValue = ((Number) anslen).intValue();
                            Object obj = this.s;
                            try {
                                try {
                                    return strings.makeString(intValue, Char.make(strings.stringRef((CharSequence) obj, ((Number) start).intValue())));
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, start);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, obj);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "make-string", 1, anslen);
                        }
                    }
                    Object apply2 = DivideOp.$Sl.apply2(this.from, slen);
                    try {
                        double doubleValue = numbers.floor(LangObjType.coerceRealNum(apply2)).doubleValue();
                        Object apply22 = DivideOp.$Sl.apply2(to, slen);
                        try {
                            if (doubleValue == numbers.floor(LangObjType.coerceRealNum(apply22)).doubleValue()) {
                                Object obj2 = this.s;
                                try {
                                    CharSequence charSequence = (CharSequence) obj2;
                                    Object apply23 = AddOp.$Pl.apply2(start, DivideOp.modulo.apply2(this.from, slen));
                                    try {
                                        int intValue2 = ((Number) apply23).intValue();
                                        Object apply24 = AddOp.$Pl.apply2(start, DivideOp.modulo.apply2(to, slen));
                                        try {
                                            return strings.substring(charSequence, intValue2, ((Number) apply24).intValue());
                                        } catch (ClassCastException e4) {
                                            throw new WrongType(e4, "substring", 3, apply24);
                                        }
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "substring", 2, apply23);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "substring", 1, obj2);
                                }
                            }
                            try {
                                CharSequence ans = strings.makeString(((Number) anslen).intValue());
                                srfi13.$PcMultispanRepcopy$Ex(ans, srfi13.Lit0, this.s, this.from, to, start, end);
                                return ans;
                            } catch (ClassCastException e7) {
                                throw new WrongType(e7, "make-string", 1, anslen);
                            }
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "floor", 1, apply22);
                        }
                    } catch (ClassCastException e9) {
                        throw new WrongType(e9, "floor", 1, apply2);
                    }
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "zero?", 1, slen);
                }
            } catch (ClassCastException e11) {
                throw new WrongType(e11, "zero?", 1, anslen);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 189) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda211() {
            if (lists.isPair(this.maybe$Mnto$Plstart$Plend)) {
                return call_with_values.callWithValues(this.lambda$Fn212, this.lambda$Fn213);
            }
            try {
                Object apply4 = Scheme.applyToArgs.apply4(srfi13.loc$check$Mnarg.get(), strings.string$Qu, this.s, srfi13.xsubstring);
                try {
                    int slen = strings.stringLength((CharSequence) apply4);
                    return misc.values(AddOp.$Pl.apply2(this.from, Integer.valueOf(slen)), srfi13.Lit0, Integer.valueOf(slen));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-length", 1, apply4);
                }
            } catch (UnboundLocationException e2) {
                e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1749, 36);
                throw e2;
            }
        }

        Object lambda212() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.xsubstring, this.s, lists.cdr.apply1(this.maybe$Mnto$Plstart$Plend));
        }

        Object lambda213(Object start, Object end) {
            Object to = lists.car.apply1(this.maybe$Mnto$Plstart$Plend);
            try {
                Scheme.applyToArgs.apply4(srfi13.loc$check$Mnarg.get(), this.lambda$Fn214, to, srfi13.xsubstring);
                return misc.values(to, start, end);
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1744, 6);
                throw e;
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 186 ? lambda214(obj) ? Boolean.TRUE : Boolean.FALSE : super.apply1(moduleMethod, obj);
        }

        boolean lambda214(Object val) {
            boolean x = numbers.isInteger(val);
            if (!x) {
                return x;
            }
            boolean x2 = numbers.isExact(val);
            if (!x2) {
                return x2;
            }
            return ((Boolean) Scheme.numLEq.apply2(this.from, val)).booleanValue();
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 186) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object xsubstring$V(Object s, Object from, Object[] argsArray) {
        frame94 frame94Var = new frame94();
        frame94Var.s = s;
        frame94Var.from = from;
        frame94Var.maybe$Mnto$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), lambda$Fn210, frame94Var.from, xsubstring);
            return call_with_values.callWithValues(frame94Var.lambda$Fn211, frame94Var.lambda$Fn215);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1738, 3);
            throw e;
        }
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame95 extends ModuleBody {
        final ModuleMethod lambda$Fn221;
        LList maybe$Mnsto$Plstart$Plend;
        Object s;
        Object sfrom;
        Object target;
        Object tstart;
        final ModuleMethod lambda$Fn218 = new ModuleMethod(this, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SEEK, null, 0);
        final ModuleMethod lambda$Fn219 = new ModuleMethod(this, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PLAY, null, 8194);
        final ModuleMethod lambda$Fn217 = new ModuleMethod(this, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE, null, 0);

        public frame95() {
            ModuleMethod moduleMethod = new ModuleMethod(this, FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_STOP, null, 12291);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm:1781");
            this.lambda$Fn221 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SEEK /* 190 */:
                    return lambda218();
                case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PLAY /* 191 */:
                default:
                    return super.apply0(moduleMethod);
                case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE /* 192 */:
                    return lambda217();
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 191 ? lambda219(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_SEEK /* 190 */:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PLAY /* 191 */:
                default:
                    return super.match0(moduleMethod, callContext);
                case FullScreenVideoUtil.FULLSCREEN_VIDEO_ACTION_PAUSE /* 192 */:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 191) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }

        static boolean lambda216(Object val) {
            boolean x = numbers.isInteger(val);
            return x ? numbers.isExact(val) : x;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 193 ? lambda221(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda221(Object sto, Object start, Object end) {
            Object tocopy = AddOp.$Mn.apply2(sto, this.sfrom);
            Object tend = AddOp.$Pl.apply2(this.tstart, tocopy);
            Object slen = AddOp.$Mn.apply2(end, start);
            srfi13.checkSubstringSpec(srfi13.string$Mnxcopy$Ex, this.target, this.tstart, tend);
            try {
                boolean x = numbers.isZero((Number) tocopy);
                if (x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    if (numbers.isZero((Number) slen)) {
                        return misc.error$V("Cannot replicate empty (sub)string", new Object[]{srfi13.string$Mnxcopy$Ex, this.target, this.tstart, this.s, this.sfrom, sto, start, end});
                    }
                    if (Scheme.numEqu.apply2(srfi13.Lit1, slen) != Boolean.FALSE) {
                        Object obj = this.target;
                        Object obj2 = this.s;
                        try {
                            try {
                                return srfi13.stringFill$Ex$V(obj, Char.make(strings.stringRef((CharSequence) obj2, ((Number) start).intValue())), new Object[]{this.tstart, tend});
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, start);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, obj2);
                        }
                    }
                    Object apply2 = DivideOp.$Sl.apply2(this.sfrom, slen);
                    try {
                        double doubleValue = numbers.floor(LangObjType.coerceRealNum(apply2)).doubleValue();
                        Object apply22 = DivideOp.$Sl.apply2(sto, slen);
                        try {
                            if (doubleValue == numbers.floor(LangObjType.coerceRealNum(apply22)).doubleValue()) {
                                Object obj3 = this.target;
                                try {
                                    CharSequence charSequence = (CharSequence) obj3;
                                    Object obj4 = this.tstart;
                                    try {
                                        int intValue = ((Number) obj4).intValue();
                                        Object obj5 = this.s;
                                        try {
                                            CharSequence charSequence2 = (CharSequence) obj5;
                                            Object apply23 = AddOp.$Pl.apply2(start, DivideOp.modulo.apply2(this.sfrom, slen));
                                            try {
                                                int intValue2 = ((Number) apply23).intValue();
                                                Object apply24 = AddOp.$Pl.apply2(start, DivideOp.modulo.apply2(sto, slen));
                                                try {
                                                    return srfi13.$PcStringCopy$Ex(charSequence, intValue, charSequence2, intValue2, ((Number) apply24).intValue());
                                                } catch (ClassCastException e3) {
                                                    throw new WrongType(e3, "%string-copy!", 4, apply24);
                                                }
                                            } catch (ClassCastException e4) {
                                                throw new WrongType(e4, "%string-copy!", 3, apply23);
                                            }
                                        } catch (ClassCastException e5) {
                                            throw new WrongType(e5, "%string-copy!", 2, obj5);
                                        }
                                    } catch (ClassCastException e6) {
                                        throw new WrongType(e6, "%string-copy!", 1, obj4);
                                    }
                                } catch (ClassCastException e7) {
                                    throw new WrongType(e7, "%string-copy!", 0, obj3);
                                }
                            }
                            return srfi13.$PcMultispanRepcopy$Ex(this.target, this.tstart, this.s, this.sfrom, sto, start, end);
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "floor", 1, apply22);
                        }
                    } catch (ClassCastException e9) {
                        throw new WrongType(e9, "floor", 1, apply2);
                    }
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "zero?", 1, slen);
                }
            } catch (ClassCastException e11) {
                throw new WrongType(e11, "zero?", 1, tocopy);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 193) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }

        Object lambda217() {
            if (lists.isPair(this.maybe$Mnsto$Plstart$Plend)) {
                return call_with_values.callWithValues(this.lambda$Fn218, this.lambda$Fn219);
            }
            Object obj = this.s;
            try {
                int slen = strings.stringLength((CharSequence) obj);
                return misc.values(AddOp.$Pl.apply2(this.sfrom, Integer.valueOf(slen)), srfi13.Lit0, Integer.valueOf(slen));
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, obj);
            }
        }

        Object lambda218() {
            return srfi13.stringParseFinalStart$PlEnd(srfi13.string$Mnxcopy$Ex, this.s, lists.cdr.apply1(this.maybe$Mnsto$Plstart$Plend));
        }

        Object lambda219(Object start, Object end) {
            Object sto = lists.car.apply1(this.maybe$Mnsto$Plstart$Plend);
            try {
                Scheme.applyToArgs.apply4(srfi13.loc$check$Mnarg.get(), srfi13.lambda$Fn220, sto, srfi13.string$Mnxcopy$Ex);
                return misc.values(sto, start, end);
            } catch (UnboundLocationException e) {
                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1785, 6);
                throw e;
            }
        }

        static boolean lambda220(Object val) {
            boolean x = numbers.isInteger(val);
            return x ? numbers.isExact(val) : x;
        }
    }

    public static Object stringXcopy$Ex$V(Object target, Object tstart, Object s, Object sfrom, Object[] argsArray) {
        frame95 frame95Var = new frame95();
        frame95Var.target = target;
        frame95Var.tstart = tstart;
        frame95Var.s = s;
        frame95Var.sfrom = sfrom;
        frame95Var.maybe$Mnsto$Plstart$Plend = LList.makeList(argsArray, 0);
        try {
            Scheme.applyToArgs.apply4(loc$check$Mnarg.get(), lambda$Fn216, frame95Var.sfrom, string$Mnxcopy$Ex);
            return call_with_values.callWithValues(frame95Var.lambda$Fn217, frame95Var.lambda$Fn221);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1779, 3);
            throw e;
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 199:
                return frame1.lambda5(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 209:
                return lambda17(obj);
            case 211:
                return lambda18(obj);
            case 217:
                return lambda27(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 239:
                return frame32.lambda72(obj);
            case 240:
                return frame32.lambda73(obj);
            case LispEscapeFormat.ESCAPE_ALL /* 242 */:
                return frame34.lambda78(obj);
            case 244:
                return frame36.lambda83(obj);
            case 245:
                return frame36.lambda84(obj);
            case 247:
                return frame38.lambda89(obj);
            case 248:
                return frame38.lambda90(obj);
            case 250:
                return frame40.lambda95(obj);
            case Telnet.WONT /* 252 */:
                return frame42.lambda100(obj);
            case Telnet.DONT /* 254 */:
                return frame44.lambda105(obj);
            case 255:
                return frame44.lambda106(obj);
            case InputDeviceCompat.SOURCE_KEYBOARD /* 257 */:
                return frame46.lambda111(obj);
            case 259:
                return frame48.lambda116(obj);
            case 260:
                return frame48.lambda117(obj);
            case 262:
                return frame50.lambda122(obj);
            case 263:
                return frame50.lambda123(obj);
            case 265:
                return frame52.lambda128(obj);
            case 267:
                return frame54.lambda133(obj);
            case 271:
                return Integer.valueOf(frame57.lambda138(obj));
            case 287:
                return frame71.lambda163(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 289:
                return frame72.lambda166(obj) ? Boolean.TRUE : Boolean.FALSE;
            case ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED /* 309 */:
                return isStringNull(obj) ? Boolean.TRUE : Boolean.FALSE;
            case ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED /* 312 */:
                return reverseList$To$String(obj);
            case 315:
                return stringConcatenate$SlShared(obj);
            case 316:
                return stringConcatenate(obj);
            case 322:
                return frame94.lambda210(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 324:
                return frame95.lambda216(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 325:
                return frame95.lambda220(obj) ? Boolean.TRUE : Boolean.FALSE;
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static Object $PcMultispanRepcopy$Ex(Object target, Object tstart, Object s, Object sfrom, Object sto, Object start, Object end) {
        Object slen = AddOp.$Mn.apply2(end, start);
        Object i0 = AddOp.$Pl.apply2(start, DivideOp.modulo.apply2(sfrom, slen));
        Object total$Mnchars = AddOp.$Mn.apply2(sto, sfrom);
        try {
            try {
                try {
                    try {
                        try {
                            $PcStringCopy$Ex((CharSequence) target, ((Number) tstart).intValue(), (CharSequence) s, ((Number) i0).intValue(), ((Number) end).intValue());
                            Object ncopied = AddOp.$Mn.apply2(end, i0);
                            Object nleft = AddOp.$Mn.apply2(total$Mnchars, ncopied);
                            Object i = AddOp.$Pl.apply2(tstart, ncopied);
                            for (Object nspans = DivideOp.quotient.apply2(nleft, slen); !numbers.isZero((Number) nspans); nspans = AddOp.$Mn.apply2(nspans, Lit1)) {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        $PcStringCopy$Ex((CharSequence) target, ((Number) i).intValue(), (CharSequence) s, ((Number) start).intValue(), ((Number) end).intValue());
                                                        i = AddOp.$Pl.apply2(i, slen);
                                                    } catch (ClassCastException e) {
                                                        throw new WrongType(e, "%string-copy!", 4, end);
                                                    }
                                                } catch (ClassCastException e2) {
                                                    throw new WrongType(e2, "%string-copy!", 3, start);
                                                }
                                            } catch (ClassCastException e3) {
                                                throw new WrongType(e3, "%string-copy!", 2, s);
                                            }
                                        } catch (ClassCastException e4) {
                                            throw new WrongType(e4, "%string-copy!", 1, i);
                                        }
                                    } catch (ClassCastException e5) {
                                        throw new WrongType(e5, "%string-copy!", 0, target);
                                    }
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "zero?", 1, nspans);
                                }
                            }
                            try {
                                CharSequence charSequence = (CharSequence) target;
                                try {
                                    int intValue = ((Number) i).intValue();
                                    try {
                                        CharSequence charSequence2 = (CharSequence) s;
                                        try {
                                            int intValue2 = ((Number) start).intValue();
                                            Object apply2 = AddOp.$Pl.apply2(start, AddOp.$Mn.apply2(total$Mnchars, AddOp.$Mn.apply2(i, tstart)));
                                            try {
                                                return $PcStringCopy$Ex(charSequence, intValue, charSequence2, intValue2, ((Number) apply2).intValue());
                                            } catch (ClassCastException e7) {
                                                throw new WrongType(e7, "%string-copy!", 4, apply2);
                                            }
                                        } catch (ClassCastException e8) {
                                            throw new WrongType(e8, "%string-copy!", 3, start);
                                        }
                                    } catch (ClassCastException e9) {
                                        throw new WrongType(e9, "%string-copy!", 2, s);
                                    }
                                } catch (ClassCastException e10) {
                                    throw new WrongType(e10, "%string-copy!", 1, i);
                                }
                            } catch (ClassCastException e11) {
                                throw new WrongType(e11, "%string-copy!", 0, target);
                            }
                        } catch (ClassCastException e12) {
                            throw new WrongType(e12, "%string-copy!", 4, end);
                        }
                    } catch (ClassCastException e13) {
                        throw new WrongType(e13, "%string-copy!", 3, i0);
                    }
                } catch (ClassCastException e14) {
                    throw new WrongType(e14, "%string-copy!", 2, s);
                }
            } catch (ClassCastException e15) {
                throw new WrongType(e15, "%string-copy!", 1, tstart);
            }
        } catch (ClassCastException e16) {
            throw new WrongType(e16, "%string-copy!", 0, target);
        }
    }

    public static Object stringJoin$V(Object strings, Object[] argsArray) {
        Object obj;
        Object error$V;
        LList delim$Plgrammar = LList.makeList(argsArray, 0);
        ApplyToArgs applyToArgs = Scheme.applyToArgs;
        try {
            Object obj2 = loc$let$Mnoptionals$St.get();
            try {
                try {
                    try {
                        Object apply2 = Scheme.applyToArgs.apply2(Scheme.applyToArgs.apply3(loc$delim.get(), " ", strings.isString(loc$delim.get()) ? Boolean.TRUE : Boolean.FALSE), Scheme.applyToArgs.apply2(loc$grammar.get(), Lit15));
                        if (lists.isPair(strings)) {
                            try {
                                Object tmp = loc$grammar.get();
                                Object x = Scheme.isEqv.apply2(tmp, Lit15);
                                if (x == Boolean.FALSE ? Scheme.isEqv.apply2(tmp, Lit16) == Boolean.FALSE : x == Boolean.FALSE) {
                                    if (Scheme.isEqv.apply2(tmp, Lit17) == Boolean.FALSE) {
                                        if (Scheme.isEqv.apply2(tmp, Lit18) != Boolean.FALSE) {
                                            try {
                                                error$V = lists.cons(lists.car.apply1(strings), lambda222buildit(lists.cdr.apply1(strings), LList.list1(loc$delim.get())));
                                            } catch (UnboundLocationException e) {
                                                e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1870, 53);
                                                throw e;
                                            }
                                        } else {
                                            Object[] objArr = new Object[2];
                                            try {
                                                objArr[0] = loc$grammar.get();
                                                objArr[1] = string$Mnjoin;
                                                error$V = misc.error$V("Illegal join grammar", objArr);
                                            } catch (UnboundLocationException e2) {
                                                e2.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1873, 9);
                                                throw e2;
                                            }
                                        }
                                    } else {
                                        error$V = lambda222buildit(strings, LList.Empty);
                                    }
                                } else {
                                    error$V = lists.cons(lists.car.apply1(strings), lambda222buildit(lists.cdr.apply1(strings), LList.Empty));
                                }
                                obj = stringConcatenate(error$V);
                            } catch (UnboundLocationException e3) {
                                e3.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1862, 14);
                                throw e3;
                            }
                        } else if (lists.isNull(strings)) {
                            try {
                                if (loc$grammar.get() == Lit16) {
                                    obj = misc.error$V("Empty list cannot be joined with STRICT-INFIX grammar.", new Object[]{string$Mnjoin});
                                } else {
                                    obj = "";
                                }
                            } catch (UnboundLocationException e4) {
                                e4.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1880, 13);
                                throw e4;
                            }
                        } else {
                            obj = misc.error$V("STRINGS parameter not list.", new Object[]{strings, string$Mnjoin});
                        }
                        return applyToArgs.apply4(obj2, delim$Plgrammar, apply2, obj);
                    } catch (UnboundLocationException e5) {
                        e5.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1853, 6);
                        throw e5;
                    }
                } catch (UnboundLocationException e6) {
                    e6.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1852, 54);
                    throw e6;
                }
            } catch (UnboundLocationException e7) {
                e7.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1852, 34);
                throw e7;
            }
        } catch (UnboundLocationException e8) {
            e8.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1852, 3);
            throw e8;
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case HttpRequestContext.HTTP_OK /* 200 */:
                Object obj = objArr[0];
                Object obj2 = objArr[1];
                int length = objArr.length - 2;
                Object[] objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return substring$SlShared$V(obj, obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case ErrorMessages.ERROR_CAMERA_NO_IMAGE_RETURNED /* 201 */:
            case 204:
            case 206:
            case 209:
            case 211:
            case 217:
            case 218:
            case 239:
            case 240:
            case LispEscapeFormat.ESCAPE_ALL /* 242 */:
            case 244:
            case 245:
            case 247:
            case 248:
            case 250:
            case Telnet.WONT /* 252 */:
            case Telnet.DONT /* 254 */:
            case 255:
            case InputDeviceCompat.SOURCE_KEYBOARD /* 257 */:
            case 259:
            case 260:
            case 262:
            case 263:
            case 265:
            case 267:
            case 271:
            case 277:
            case 280:
            case 281:
            case 282:
            case 283:
            case 287:
            case 289:
            case 300:
            case ErrorMessages.ERROR_TWITTER_UNSUPPORTED_LOGIN_FUNCTION /* 301 */:
            case ErrorMessages.ERROR_TWITTER_REQUEST_DIRECT_MESSAGES_FAILED /* 309 */:
            case ErrorMessages.ERROR_TWITTER_STOP_FOLLOWING_FAILED /* 312 */:
            case 315:
            case 316:
            case 319:
            case 322:
            case 324:
            case 325:
            default:
                return super.applyN(moduleMethod, objArr);
            case ErrorMessages.ERROR_NO_CAMERA_PERMISSION /* 202 */:
                Object obj3 = objArr[0];
                int length2 = objArr.length - 1;
                Object[] objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return stringCopy$V(obj3, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 1];
                }
            case 203:
                Object obj4 = objArr[0];
                Object obj5 = objArr[1];
                int length3 = objArr.length - 2;
                Object[] objArr4 = new Object[length3];
                while (true) {
                    length3--;
                    if (length3 < 0) {
                        return stringMap$V(obj4, obj5, objArr4);
                    }
                    objArr4[length3] = objArr[length3 + 2];
                }
            case 205:
                Object obj6 = objArr[0];
                Object obj7 = objArr[1];
                int length4 = objArr.length - 2;
                Object[] objArr5 = new Object[length4];
                while (true) {
                    length4--;
                    if (length4 < 0) {
                        return stringMap$Ex$V(obj6, obj7, objArr5);
                    }
                    objArr5[length4] = objArr[length4 + 2];
                }
            case 207:
                Object obj8 = objArr[0];
                Object obj9 = objArr[1];
                Object obj10 = objArr[2];
                int length5 = objArr.length - 3;
                Object[] objArr6 = new Object[length5];
                while (true) {
                    length5--;
                    if (length5 < 0) {
                        return stringFold$V(obj8, obj9, obj10, objArr6);
                    }
                    objArr6[length5] = objArr[length5 + 3];
                }
            case 208:
                Object obj11 = objArr[0];
                Object obj12 = objArr[1];
                Object obj13 = objArr[2];
                int length6 = objArr.length - 3;
                Object[] objArr7 = new Object[length6];
                while (true) {
                    length6--;
                    if (length6 < 0) {
                        return stringFoldRight$V(obj11, obj12, obj13, objArr7);
                    }
                    objArr7[length6] = objArr[length6 + 3];
                }
            case 210:
                Object obj14 = objArr[0];
                Object obj15 = objArr[1];
                Object obj16 = objArr[2];
                Object obj17 = objArr[3];
                int length7 = objArr.length - 4;
                Object[] objArr8 = new Object[length7];
                while (true) {
                    length7--;
                    if (length7 < 0) {
                        return stringUnfold$V(obj14, obj15, obj16, obj17, objArr8);
                    }
                    objArr8[length7] = objArr[length7 + 4];
                }
            case 212:
                Object obj18 = objArr[0];
                Object obj19 = objArr[1];
                Object obj20 = objArr[2];
                Object obj21 = objArr[3];
                int length8 = objArr.length - 4;
                Object[] objArr9 = new Object[length8];
                while (true) {
                    length8--;
                    if (length8 < 0) {
                        return stringUnfoldRight$V(obj18, obj19, obj20, obj21, objArr9);
                    }
                    objArr9[length8] = objArr[length8 + 4];
                }
            case 213:
                Object obj22 = objArr[0];
                Object obj23 = objArr[1];
                int length9 = objArr.length - 2;
                Object[] objArr10 = new Object[length9];
                while (true) {
                    length9--;
                    if (length9 < 0) {
                        return stringForEach$V(obj22, obj23, objArr10);
                    }
                    objArr10[length9] = objArr[length9 + 2];
                }
            case 214:
                Object obj24 = objArr[0];
                Object obj25 = objArr[1];
                int length10 = objArr.length - 2;
                Object[] objArr11 = new Object[length10];
                while (true) {
                    length10--;
                    if (length10 < 0) {
                        return stringForEachIndex$V(obj24, obj25, objArr11);
                    }
                    objArr11[length10] = objArr[length10 + 2];
                }
            case 215:
                Object obj26 = objArr[0];
                Object obj27 = objArr[1];
                int length11 = objArr.length - 2;
                Object[] objArr12 = new Object[length11];
                while (true) {
                    length11--;
                    if (length11 < 0) {
                        return stringEvery$V(obj26, obj27, objArr12);
                    }
                    objArr12[length11] = objArr[length11 + 2];
                }
            case 216:
                Object obj28 = objArr[0];
                Object obj29 = objArr[1];
                int length12 = objArr.length - 2;
                Object[] objArr13 = new Object[length12];
                while (true) {
                    length12--;
                    if (length12 < 0) {
                        return stringAny$V(obj28, obj29, objArr13);
                    }
                    objArr13[length12] = objArr[length12 + 2];
                }
            case 219:
                return $PcStringPrefixLength(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 220:
                return $PcStringSuffixLength(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 221:
                Object obj30 = objArr[0];
                Object obj31 = objArr[1];
                try {
                    int intValue = ((Number) obj31).intValue();
                    Object obj32 = objArr[2];
                    try {
                        int intValue2 = ((Number) obj32).intValue();
                        Object obj33 = objArr[3];
                        Object obj34 = objArr[4];
                        try {
                            int intValue3 = ((Number) obj34).intValue();
                            Object obj35 = objArr[5];
                            try {
                                return Integer.valueOf($PcStringPrefixLengthCi(obj30, intValue, intValue2, obj33, intValue3, ((Number) obj35).intValue()));
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "%string-prefix-length-ci", 6, obj35);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "%string-prefix-length-ci", 5, obj34);
                        }
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "%string-prefix-length-ci", 3, obj32);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "%string-prefix-length-ci", 2, obj31);
                }
            case 222:
                Object obj36 = objArr[0];
                Object obj37 = objArr[1];
                try {
                    int intValue4 = ((Number) obj37).intValue();
                    Object obj38 = objArr[2];
                    try {
                        int intValue5 = ((Number) obj38).intValue();
                        Object obj39 = objArr[3];
                        Object obj40 = objArr[4];
                        try {
                            int intValue6 = ((Number) obj40).intValue();
                            Object obj41 = objArr[5];
                            try {
                                return Integer.valueOf($PcStringSuffixLengthCi(obj36, intValue4, intValue5, obj39, intValue6, ((Number) obj41).intValue()));
                            } catch (ClassCastException e5) {
                                throw new WrongType(e5, "%string-suffix-length-ci", 6, obj41);
                            }
                        } catch (ClassCastException e6) {
                            throw new WrongType(e6, "%string-suffix-length-ci", 5, obj40);
                        }
                    } catch (ClassCastException e7) {
                        throw new WrongType(e7, "%string-suffix-length-ci", 3, obj38);
                    }
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "%string-suffix-length-ci", 2, obj37);
                }
            case YaVersion.YOUNG_ANDROID_VERSION /* 223 */:
                Object obj42 = objArr[0];
                Object obj43 = objArr[1];
                int length13 = objArr.length - 2;
                Object[] objArr14 = new Object[length13];
                while (true) {
                    length13--;
                    if (length13 < 0) {
                        return stringPrefixLength$V(obj42, obj43, objArr14);
                    }
                    objArr14[length13] = objArr[length13 + 2];
                }
            case 224:
                Object obj44 = objArr[0];
                Object obj45 = objArr[1];
                int length14 = objArr.length - 2;
                Object[] objArr15 = new Object[length14];
                while (true) {
                    length14--;
                    if (length14 < 0) {
                        return stringSuffixLength$V(obj44, obj45, objArr15);
                    }
                    objArr15[length14] = objArr[length14 + 2];
                }
            case 225:
                Object obj46 = objArr[0];
                Object obj47 = objArr[1];
                int length15 = objArr.length - 2;
                Object[] objArr16 = new Object[length15];
                while (true) {
                    length15--;
                    if (length15 < 0) {
                        return stringPrefixLengthCi$V(obj46, obj47, objArr16);
                    }
                    objArr16[length15] = objArr[length15 + 2];
                }
            case 226:
                Object obj48 = objArr[0];
                Object obj49 = objArr[1];
                int length16 = objArr.length - 2;
                Object[] objArr17 = new Object[length16];
                while (true) {
                    length16--;
                    if (length16 < 0) {
                        return stringSuffixLengthCi$V(obj48, obj49, objArr17);
                    }
                    objArr17[length16] = objArr[length16 + 2];
                }
            case 227:
                Object obj50 = objArr[0];
                Object obj51 = objArr[1];
                int length17 = objArr.length - 2;
                Object[] objArr18 = new Object[length17];
                while (true) {
                    length17--;
                    if (length17 < 0) {
                        return isStringPrefix$V(obj50, obj51, objArr18);
                    }
                    objArr18[length17] = objArr[length17 + 2];
                }
            case 228:
                Object obj52 = objArr[0];
                Object obj53 = objArr[1];
                int length18 = objArr.length - 2;
                Object[] objArr19 = new Object[length18];
                while (true) {
                    length18--;
                    if (length18 < 0) {
                        return isStringSuffix$V(obj52, obj53, objArr19);
                    }
                    objArr19[length18] = objArr[length18 + 2];
                }
            case 229:
                Object obj54 = objArr[0];
                Object obj55 = objArr[1];
                int length19 = objArr.length - 2;
                Object[] objArr20 = new Object[length19];
                while (true) {
                    length19--;
                    if (length19 < 0) {
                        return isStringPrefixCi$V(obj54, obj55, objArr20);
                    }
                    objArr20[length19] = objArr[length19 + 2];
                }
            case 230:
                Object obj56 = objArr[0];
                Object obj57 = objArr[1];
                int length20 = objArr.length - 2;
                Object[] objArr21 = new Object[length20];
                while (true) {
                    length20--;
                    if (length20 < 0) {
                        return isStringSuffixCi$V(obj56, obj57, objArr21);
                    }
                    objArr21[length20] = objArr[length20 + 2];
                }
            case 231:
                return $PcStringPrefix$Qu(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 232:
                return $PcStringSuffix$Qu(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 233:
                return $PcStringPrefixCi$Qu(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 234:
                return $PcStringSuffixCi$Qu(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 235:
                return $PcStringCompare(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7], objArr[8]);
            case 236:
                return $PcStringCompareCi(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7], objArr[8]);
            case 237:
                Object obj58 = objArr[0];
                Object obj59 = objArr[1];
                Object obj60 = objArr[2];
                Object obj61 = objArr[3];
                Object obj62 = objArr[4];
                int length21 = objArr.length - 5;
                Object[] objArr22 = new Object[length21];
                while (true) {
                    length21--;
                    if (length21 < 0) {
                        return stringCompare$V(obj58, obj59, obj60, obj61, obj62, objArr22);
                    }
                    objArr22[length21] = objArr[length21 + 5];
                }
            case 238:
                Object obj63 = objArr[0];
                Object obj64 = objArr[1];
                Object obj65 = objArr[2];
                Object obj66 = objArr[3];
                Object obj67 = objArr[4];
                int length22 = objArr.length - 5;
                Object[] objArr23 = new Object[length22];
                while (true) {
                    length22--;
                    if (length22 < 0) {
                        return stringCompareCi$V(obj63, obj64, obj65, obj66, obj67, objArr23);
                    }
                    objArr23[length22] = objArr[length22 + 5];
                }
            case LispEscapeFormat.ESCAPE_NORMAL /* 241 */:
                Object obj68 = objArr[0];
                Object obj69 = objArr[1];
                int length23 = objArr.length - 2;
                Object[] objArr24 = new Object[length23];
                while (true) {
                    length23--;
                    if (length23 < 0) {
                        return string$Eq$V(obj68, obj69, objArr24);
                    }
                    objArr24[length23] = objArr[length23 + 2];
                }
            case 243:
                Object obj70 = objArr[0];
                Object obj71 = objArr[1];
                int length24 = objArr.length - 2;
                Object[] objArr25 = new Object[length24];
                while (true) {
                    length24--;
                    if (length24 < 0) {
                        return string$Ls$Gr$V(obj70, obj71, objArr25);
                    }
                    objArr25[length24] = objArr[length24 + 2];
                }
            case 246:
                Object obj72 = objArr[0];
                Object obj73 = objArr[1];
                int length25 = objArr.length - 2;
                Object[] objArr26 = new Object[length25];
                while (true) {
                    length25--;
                    if (length25 < 0) {
                        return string$Ls$V(obj72, obj73, objArr26);
                    }
                    objArr26[length25] = objArr[length25 + 2];
                }
            case 249:
                Object obj74 = objArr[0];
                Object obj75 = objArr[1];
                int length26 = objArr.length - 2;
                Object[] objArr27 = new Object[length26];
                while (true) {
                    length26--;
                    if (length26 < 0) {
                        return string$Gr$V(obj74, obj75, objArr27);
                    }
                    objArr27[length26] = objArr[length26 + 2];
                }
            case Telnet.WILL /* 251 */:
                Object obj76 = objArr[0];
                Object obj77 = objArr[1];
                int length27 = objArr.length - 2;
                Object[] objArr28 = new Object[length27];
                while (true) {
                    length27--;
                    if (length27 < 0) {
                        return string$Ls$Eq$V(obj76, obj77, objArr28);
                    }
                    objArr28[length27] = objArr[length27 + 2];
                }
            case Telnet.DO /* 253 */:
                Object obj78 = objArr[0];
                Object obj79 = objArr[1];
                int length28 = objArr.length - 2;
                Object[] objArr29 = new Object[length28];
                while (true) {
                    length28--;
                    if (length28 < 0) {
                        return string$Gr$Eq$V(obj78, obj79, objArr29);
                    }
                    objArr29[length28] = objArr[length28 + 2];
                }
            case 256:
                Object obj80 = objArr[0];
                Object obj81 = objArr[1];
                int length29 = objArr.length - 2;
                Object[] objArr30 = new Object[length29];
                while (true) {
                    length29--;
                    if (length29 < 0) {
                        return stringCi$Eq$V(obj80, obj81, objArr30);
                    }
                    objArr30[length29] = objArr[length29 + 2];
                }
            case 258:
                Object obj82 = objArr[0];
                Object obj83 = objArr[1];
                int length30 = objArr.length - 2;
                Object[] objArr31 = new Object[length30];
                while (true) {
                    length30--;
                    if (length30 < 0) {
                        return stringCi$Ls$Gr$V(obj82, obj83, objArr31);
                    }
                    objArr31[length30] = objArr[length30 + 2];
                }
            case 261:
                Object obj84 = objArr[0];
                Object obj85 = objArr[1];
                int length31 = objArr.length - 2;
                Object[] objArr32 = new Object[length31];
                while (true) {
                    length31--;
                    if (length31 < 0) {
                        return stringCi$Ls$V(obj84, obj85, objArr32);
                    }
                    objArr32[length31] = objArr[length31 + 2];
                }
            case 264:
                Object obj86 = objArr[0];
                Object obj87 = objArr[1];
                int length32 = objArr.length - 2;
                Object[] objArr33 = new Object[length32];
                while (true) {
                    length32--;
                    if (length32 < 0) {
                        return stringCi$Gr$V(obj86, obj87, objArr33);
                    }
                    objArr33[length32] = objArr[length32 + 2];
                }
            case 266:
                Object obj88 = objArr[0];
                Object obj89 = objArr[1];
                int length33 = objArr.length - 2;
                Object[] objArr34 = new Object[length33];
                while (true) {
                    length33--;
                    if (length33 < 0) {
                        return stringCi$Ls$Eq$V(obj88, obj89, objArr34);
                    }
                    objArr34[length33] = objArr[length33 + 2];
                }
            case 268:
                Object obj90 = objArr[0];
                Object obj91 = objArr[1];
                int length34 = objArr.length - 2;
                Object[] objArr35 = new Object[length34];
                while (true) {
                    length34--;
                    if (length34 < 0) {
                        return stringCi$Gr$Eq$V(obj90, obj91, objArr35);
                    }
                    objArr35[length34] = objArr[length34 + 2];
                }
            case 269:
                return $PcStringHash(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4]);
            case 270:
                Object obj92 = objArr[0];
                int length35 = objArr.length - 1;
                Object[] objArr36 = new Object[length35];
                while (true) {
                    length35--;
                    if (length35 < 0) {
                        return stringHash$V(obj92, objArr36);
                    }
                    objArr36[length35] = objArr[length35 + 1];
                }
            case 272:
                Object obj93 = objArr[0];
                int length36 = objArr.length - 1;
                Object[] objArr37 = new Object[length36];
                while (true) {
                    length36--;
                    if (length36 < 0) {
                        return stringHashCi$V(obj93, objArr37);
                    }
                    objArr37[length36] = objArr[length36 + 1];
                }
            case 273:
                Object obj94 = objArr[0];
                int length37 = objArr.length - 1;
                Object[] objArr38 = new Object[length37];
                while (true) {
                    length37--;
                    if (length37 < 0) {
                        return stringUpcase$V(obj94, objArr38);
                    }
                    objArr38[length37] = objArr[length37 + 1];
                }
            case 274:
                Object obj95 = objArr[0];
                int length38 = objArr.length - 1;
                Object[] objArr39 = new Object[length38];
                while (true) {
                    length38--;
                    if (length38 < 0) {
                        return stringUpcase$Ex$V(obj95, objArr39);
                    }
                    objArr39[length38] = objArr[length38 + 1];
                }
            case 275:
                Object obj96 = objArr[0];
                int length39 = objArr.length - 1;
                Object[] objArr40 = new Object[length39];
                while (true) {
                    length39--;
                    if (length39 < 0) {
                        return stringDowncase$V(obj96, objArr40);
                    }
                    objArr40[length39] = objArr[length39 + 1];
                }
            case 276:
                Object obj97 = objArr[0];
                int length40 = objArr.length - 1;
                Object[] objArr41 = new Object[length40];
                while (true) {
                    length40--;
                    if (length40 < 0) {
                        return stringDowncase$Ex$V(obj97, objArr41);
                    }
                    objArr41[length40] = objArr[length40 + 1];
                }
            case 278:
                Object obj98 = objArr[0];
                int length41 = objArr.length - 1;
                Object[] objArr42 = new Object[length41];
                while (true) {
                    length41--;
                    if (length41 < 0) {
                        return stringTitlecase$Ex$V(obj98, objArr42);
                    }
                    objArr42[length41] = objArr[length41 + 1];
                }
            case 279:
                Object obj99 = objArr[0];
                int length42 = objArr.length - 1;
                Object[] objArr43 = new Object[length42];
                while (true) {
                    length42--;
                    if (length42 < 0) {
                        return stringTitlecase$V(obj99, objArr43);
                    }
                    objArr43[length42] = objArr[length42 + 1];
                }
            case 284:
                Object obj100 = objArr[0];
                int length43 = objArr.length - 1;
                Object[] objArr44 = new Object[length43];
                while (true) {
                    length43--;
                    if (length43 < 0) {
                        return stringTrim$V(obj100, objArr44);
                    }
                    objArr44[length43] = objArr[length43 + 1];
                }
            case 285:
                Object obj101 = objArr[0];
                int length44 = objArr.length - 1;
                Object[] objArr45 = new Object[length44];
                while (true) {
                    length44--;
                    if (length44 < 0) {
                        return stringTrimRight$V(obj101, objArr45);
                    }
                    objArr45[length44] = objArr[length44 + 1];
                }
            case 286:
                Object obj102 = objArr[0];
                int length45 = objArr.length - 1;
                Object[] objArr46 = new Object[length45];
                while (true) {
                    length45--;
                    if (length45 < 0) {
                        return stringTrimBoth$V(obj102, objArr46);
                    }
                    objArr46[length45] = objArr[length45 + 1];
                }
            case 288:
                Object obj103 = objArr[0];
                Object obj104 = objArr[1];
                int length46 = objArr.length - 2;
                Object[] objArr47 = new Object[length46];
                while (true) {
                    length46--;
                    if (length46 < 0) {
                        return stringPadRight$V(obj103, obj104, objArr47);
                    }
                    objArr47[length46] = objArr[length46 + 2];
                }
            case 290:
                Object obj105 = objArr[0];
                Object obj106 = objArr[1];
                int length47 = objArr.length - 2;
                Object[] objArr48 = new Object[length47];
                while (true) {
                    length47--;
                    if (length47 < 0) {
                        return stringPad$V(obj105, obj106, objArr48);
                    }
                    objArr48[length47] = objArr[length47 + 2];
                }
            case 291:
                Object obj107 = objArr[0];
                Object obj108 = objArr[1];
                int length48 = objArr.length - 2;
                Object[] objArr49 = new Object[length48];
                while (true) {
                    length48--;
                    if (length48 < 0) {
                        return stringDelete$V(obj107, obj108, objArr49);
                    }
                    objArr49[length48] = objArr[length48 + 2];
                }
            case 292:
                Object obj109 = objArr[0];
                Object obj110 = objArr[1];
                int length49 = objArr.length - 2;
                Object[] objArr50 = new Object[length49];
                while (true) {
                    length49--;
                    if (length49 < 0) {
                        return stringFilter$V(obj109, obj110, objArr50);
                    }
                    objArr50[length49] = objArr[length49 + 2];
                }
            case 293:
                Object obj111 = objArr[0];
                Object obj112 = objArr[1];
                int length50 = objArr.length - 2;
                Object[] objArr51 = new Object[length50];
                while (true) {
                    length50--;
                    if (length50 < 0) {
                        return stringIndex$V(obj111, obj112, objArr51);
                    }
                    objArr51[length50] = objArr[length50 + 2];
                }
            case 294:
                Object obj113 = objArr[0];
                Object obj114 = objArr[1];
                int length51 = objArr.length - 2;
                Object[] objArr52 = new Object[length51];
                while (true) {
                    length51--;
                    if (length51 < 0) {
                        return stringIndexRight$V(obj113, obj114, objArr52);
                    }
                    objArr52[length51] = objArr[length51 + 2];
                }
            case 295:
                Object obj115 = objArr[0];
                Object obj116 = objArr[1];
                int length52 = objArr.length - 2;
                Object[] objArr53 = new Object[length52];
                while (true) {
                    length52--;
                    if (length52 < 0) {
                        return stringSkip$V(obj115, obj116, objArr53);
                    }
                    objArr53[length52] = objArr[length52 + 2];
                }
            case 296:
                Object obj117 = objArr[0];
                Object obj118 = objArr[1];
                int length53 = objArr.length - 2;
                Object[] objArr54 = new Object[length53];
                while (true) {
                    length53--;
                    if (length53 < 0) {
                        return stringSkipRight$V(obj117, obj118, objArr54);
                    }
                    objArr54[length53] = objArr[length53 + 2];
                }
            case 297:
                Object obj119 = objArr[0];
                Object obj120 = objArr[1];
                int length54 = objArr.length - 2;
                Object[] objArr55 = new Object[length54];
                while (true) {
                    length54--;
                    if (length54 < 0) {
                        return stringCount$V(obj119, obj120, objArr55);
                    }
                    objArr55[length54] = objArr[length54 + 2];
                }
            case 298:
                Object obj121 = objArr[0];
                Object obj122 = objArr[1];
                int length55 = objArr.length - 2;
                Object[] objArr56 = new Object[length55];
                while (true) {
                    length55--;
                    if (length55 < 0) {
                        return stringFill$Ex$V(obj121, obj122, objArr56);
                    }
                    objArr56[length55] = objArr[length55 + 2];
                }
            case 299:
                int length56 = objArr.length - 3;
                Object obj123 = objArr[0];
                Object obj124 = objArr[1];
                try {
                    int intValue7 = ((Number) obj124).intValue();
                    Object obj125 = objArr[2];
                    try {
                        CharSequence charSequence = (CharSequence) obj125;
                        if (length56 <= 0) {
                            return stringCopy$Ex(obj123, intValue7, charSequence, 0);
                        }
                        int i = length56 - 1;
                        Object obj126 = objArr[3];
                        try {
                            int intValue8 = ((Number) obj126).intValue();
                            if (i <= 0) {
                                return stringCopy$Ex(obj123, intValue7, charSequence, intValue8);
                            }
                            int i2 = i - 1;
                            Object obj127 = objArr[4];
                            try {
                                return stringCopy$Ex(obj123, intValue7, charSequence, intValue8, ((Number) obj127).intValue());
                            } catch (ClassCastException e9) {
                                throw new WrongType(e9, "string-copy!", 5, obj127);
                            }
                        } catch (ClassCastException e10) {
                            throw new WrongType(e10, "string-copy!", 4, obj126);
                        }
                    } catch (ClassCastException e11) {
                        throw new WrongType(e11, "string-copy!", 3, obj125);
                    }
                } catch (ClassCastException e12) {
                    throw new WrongType(e12, "string-copy!", 2, obj124);
                }
            case ErrorMessages.ERROR_TWITTER_BLANK_CONSUMER_KEY_OR_SECRET /* 302 */:
                Object obj128 = objArr[0];
                try {
                    CharSequence charSequence2 = (CharSequence) obj128;
                    Object obj129 = objArr[1];
                    try {
                        int intValue9 = ((Number) obj129).intValue();
                        Object obj130 = objArr[2];
                        try {
                            CharSequence charSequence3 = (CharSequence) obj130;
                            Object obj131 = objArr[3];
                            try {
                                int intValue10 = ((Number) obj131).intValue();
                                Object obj132 = objArr[4];
                                try {
                                    return $PcStringCopy$Ex(charSequence2, intValue9, charSequence3, intValue10, ((Number) obj132).intValue());
                                } catch (ClassCastException e13) {
                                    throw new WrongType(e13, "%string-copy!", 5, obj132);
                                }
                            } catch (ClassCastException e14) {
                                throw new WrongType(e14, "%string-copy!", 4, obj131);
                            }
                        } catch (ClassCastException e15) {
                            throw new WrongType(e15, "%string-copy!", 3, obj130);
                        }
                    } catch (ClassCastException e16) {
                        throw new WrongType(e16, "%string-copy!", 2, obj129);
                    }
                } catch (ClassCastException e17) {
                    throw new WrongType(e17, "%string-copy!", 1, obj128);
                }
            case ErrorMessages.ERROR_TWITTER_EXCEPTION /* 303 */:
                Object obj133 = objArr[0];
                Object obj134 = objArr[1];
                int length57 = objArr.length - 2;
                Object[] objArr57 = new Object[length57];
                while (true) {
                    length57--;
                    if (length57 < 0) {
                        return stringContains$V(obj133, obj134, objArr57);
                    }
                    objArr57[length57] = objArr[length57 + 2];
                }
            case ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN /* 304 */:
                Object obj135 = objArr[0];
                Object obj136 = objArr[1];
                int length58 = objArr.length - 2;
                Object[] objArr58 = new Object[length58];
                while (true) {
                    length58--;
                    if (length58 < 0) {
                        return stringContainsCi$V(obj135, obj136, objArr58);
                    }
                    objArr58[length58] = objArr[length58 + 2];
                }
            case ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED /* 305 */:
                return $PcKmpSearch(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6]);
            case ErrorMessages.ERROR_TWITTER_SET_STATUS_FAILED /* 306 */:
                Object obj137 = objArr[0];
                int length59 = objArr.length - 1;
                Object[] objArr59 = new Object[length59];
                while (true) {
                    length59--;
                    if (length59 < 0) {
                        return makeKmpRestartVector$V(obj137, objArr59);
                    }
                    objArr59[length59] = objArr[length59 + 1];
                }
            case ErrorMessages.ERROR_TWITTER_REQUEST_MENTIONS_FAILED /* 307 */:
                return kmpStep(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case ErrorMessages.ERROR_TWITTER_REQUEST_FOLLOWERS_FAILED /* 308 */:
                Object obj138 = objArr[0];
                Object obj139 = objArr[1];
                Object obj140 = objArr[2];
                Object obj141 = objArr[3];
                int length60 = objArr.length - 4;
                Object[] objArr60 = new Object[length60];
                while (true) {
                    length60--;
                    if (length60 < 0) {
                        return stringKmpPartialSearch$V(obj138, obj139, obj140, obj141, objArr60);
                    }
                    objArr60[length60] = objArr[length60 + 4];
                }
            case ErrorMessages.ERROR_TWITTER_DIRECT_MESSAGE_FAILED /* 310 */:
                Object obj142 = objArr[0];
                int length61 = objArr.length - 1;
                Object[] objArr61 = new Object[length61];
                while (true) {
                    length61--;
                    if (length61 < 0) {
                        return stringReverse$V(obj142, objArr61);
                    }
                    objArr61[length61] = objArr[length61 + 1];
                }
            case ErrorMessages.ERROR_TWITTER_FOLLOW_FAILED /* 311 */:
                Object obj143 = objArr[0];
                int length62 = objArr.length - 1;
                Object[] objArr62 = new Object[length62];
                while (true) {
                    length62--;
                    if (length62 < 0) {
                        return stringReverse$Ex$V(obj143, objArr62);
                    }
                    objArr62[length62] = objArr[length62 + 1];
                }
            case ErrorMessages.ERROR_TWITTER_REQUEST_FRIEND_TIMELINE_FAILED /* 313 */:
                Object obj144 = objArr[0];
                int length63 = objArr.length - 1;
                Object[] objArr63 = new Object[length63];
                while (true) {
                    length63--;
                    if (length63 < 0) {
                        return string$To$List$V(obj144, objArr63);
                    }
                    objArr63[length63] = objArr[length63 + 1];
                }
            case ErrorMessages.ERROR_TWITTER_SEARCH_FAILED /* 314 */:
                return stringAppend$SlShared$V(objArr);
            case 317:
                Object obj145 = objArr[0];
                int length64 = objArr.length - 1;
                Object[] objArr64 = new Object[length64];
                while (true) {
                    length64--;
                    if (length64 < 0) {
                        return stringConcatenateReverse$V(obj145, objArr64);
                    }
                    objArr64[length64] = objArr[length64 + 1];
                }
            case 318:
                Object obj146 = objArr[0];
                int length65 = objArr.length - 1;
                Object[] objArr65 = new Object[length65];
                while (true) {
                    length65--;
                    if (length65 < 0) {
                        return stringConcatenateReverse$SlShared$V(obj146, objArr65);
                    }
                    objArr65[length65] = objArr[length65 + 1];
                }
            case ScreenDensityUtil.DEFAULT_NORMAL_SHORT_DIMENSION /* 320 */:
                Object obj147 = objArr[0];
                Object obj148 = objArr[1];
                Object obj149 = objArr[2];
                Object obj150 = objArr[3];
                int length66 = objArr.length - 4;
                Object[] objArr66 = new Object[length66];
                while (true) {
                    length66--;
                    if (length66 < 0) {
                        return stringReplace$V(obj147, obj148, obj149, obj150, objArr66);
                    }
                    objArr66[length66] = objArr[length66 + 4];
                }
            case 321:
                Object obj151 = objArr[0];
                int length67 = objArr.length - 1;
                Object[] objArr67 = new Object[length67];
                while (true) {
                    length67--;
                    if (length67 < 0) {
                        return stringTokenize$V(obj151, objArr67);
                    }
                    objArr67[length67] = objArr[length67 + 1];
                }
            case 323:
                Object obj152 = objArr[0];
                Object obj153 = objArr[1];
                int length68 = objArr.length - 2;
                Object[] objArr68 = new Object[length68];
                while (true) {
                    length68--;
                    if (length68 < 0) {
                        return xsubstring$V(obj152, obj153, objArr68);
                    }
                    objArr68[length68] = objArr[length68 + 2];
                }
            case 326:
                Object obj154 = objArr[0];
                Object obj155 = objArr[1];
                Object obj156 = objArr[2];
                Object obj157 = objArr[3];
                int length69 = objArr.length - 4;
                Object[] objArr69 = new Object[length69];
                while (true) {
                    length69--;
                    if (length69 < 0) {
                        return stringXcopy$Ex$V(obj154, obj155, obj156, obj157, objArr69);
                    }
                    objArr69[length69] = objArr[length69 + 4];
                }
            case 327:
                return $PcMultispanRepcopy$Ex(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6]);
            case 328:
                Object obj158 = objArr[0];
                int length70 = objArr.length - 1;
                Object[] objArr70 = new Object[length70];
                while (true) {
                    length70--;
                    if (length70 < 0) {
                        return stringJoin$V(obj158, objArr70);
                    }
                    objArr70[length70] = objArr[length70 + 1];
                }
        }
    }

    public static Object lambda222buildit(Object lis, Object obj) {
        frame96 frame96Var = new frame96();
        frame96Var.f3final = obj;
        return frame96Var.lambda223recur(lis);
    }

    /* compiled from: srfi13.scm */
    /* loaded from: classes.dex */
    public class frame96 extends ModuleBody {

        /* renamed from: final  reason: not valid java name */
        Object f3final;

        public Object lambda223recur(Object lis) {
            if (lists.isPair(lis)) {
                try {
                    return lists.cons(srfi13.loc$delim.get(), lists.cons(lists.car.apply1(lis), lambda223recur(lists.cdr.apply1(lis))));
                } catch (UnboundLocationException e) {
                    e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/srfi13.scm", 1857, 13);
                    throw e;
                }
            }
            return this.f3final;
        }
    }
}
