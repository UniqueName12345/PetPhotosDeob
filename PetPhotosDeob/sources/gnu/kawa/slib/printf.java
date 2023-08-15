package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.runtime.Component;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.Special;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.IsEqv;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.Complex;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.ports;
import kawa.lib.rnrs.unicode;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: printf.scm */
/* loaded from: classes.dex */
public class printf extends ModuleBody {
    public static final printf $instance;
    static final IntNum Lit0;
    static final IntNum Lit1;
    static final PairWithPosition Lit10;
    static final Char Lit11;
    static final Char Lit12;
    static final Char Lit13;
    static final IntNum Lit14;
    static final IntNum Lit15;
    static final IntNum Lit16;
    static final IntNum Lit17;
    static final Char Lit18;
    static final Char Lit19;
    static final PairWithPosition Lit2;
    static final Char Lit20;
    static final Char Lit21;
    static final Char Lit22;
    static final Char Lit23;
    static final Char Lit24;
    static final Char Lit25;
    static final Char Lit26;
    static final Char Lit27;
    static final Char Lit28;
    static final Char Lit29;
    static final Char Lit3;
    static final Char Lit30;
    static final Char Lit31;
    static final Char Lit32;
    static final PairWithPosition Lit33;
    static final Char Lit4;
    static final Char Lit5;
    static final Char Lit6;
    static final IntNum Lit7;
    static final Char Lit8;
    static final Char Lit9;
    public static final ModuleMethod fprintf;
    public static final ModuleMethod printf;
    public static final ModuleMethod sprintf;
    public static final boolean stdio$Clhex$Mnupper$Mncase$Qu = false;
    public static final ModuleMethod stdio$Cliprintf;
    public static final ModuleMethod stdio$Clparse$Mnfloat;
    public static final ModuleMethod stdio$Clround$Mnstring;
    static final SimpleSymbol Lit72 = (SimpleSymbol) new SimpleSymbol("fprintf").readResolve();
    static final SimpleSymbol Lit71 = (SimpleSymbol) new SimpleSymbol("stdio:iprintf").readResolve();
    static final SimpleSymbol Lit70 = (SimpleSymbol) new SimpleSymbol("stdio:round-string").readResolve();
    static final SimpleSymbol Lit69 = (SimpleSymbol) new SimpleSymbol("stdio:parse-float").readResolve();
    static final SimpleSymbol Lit68 = (SimpleSymbol) new SimpleSymbol("sprintf").readResolve();
    static final SimpleSymbol Lit67 = (SimpleSymbol) new SimpleSymbol("pad").readResolve();
    static final Char Lit66 = Char.make(42);
    static final Char Lit65 = Char.make(63);
    static final SimpleSymbol Lit64 = (SimpleSymbol) new SimpleSymbol("format-real").readResolve();
    static final PairWithPosition Lit63 = PairWithPosition.make("i", LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1634315);
    static final FVector Lit62 = FVector.make("y", "z", "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y");
    static final IntNum Lit61 = IntNum.make(3);
    static final IntNum Lit60 = IntNum.make(-10);
    static final IntNum Lit59 = IntNum.make(6);
    static final Char Lit58 = Char.make(75);
    static final Char Lit57 = Char.make(107);
    static final Char Lit56 = Char.make(71);
    static final Char Lit55 = Char.make(103);
    static final Char Lit54 = Char.make(69);
    static final Char Lit53 = Char.make(66);
    static final Char Lit52 = Char.make(98);
    static final Char Lit51 = Char.make(88);
    static final IntNum Lit50 = IntNum.make(16);
    static final Char Lit49 = Char.make(120);
    static final IntNum Lit48 = IntNum.make(8);
    static final Char Lit47 = Char.make(79);
    static final Char Lit46 = Char.make(111);
    static final IntNum Lit45 = IntNum.make(10);
    static final Char Lit44 = Char.make(85);
    static final Char Lit43 = Char.make(117);
    static final Char Lit42 = Char.make(73);
    static final Char Lit41 = Char.make(68);
    static final Char Lit40 = Char.make(65);
    static final Char Lit39 = Char.make(97);
    static final Char Lit38 = Char.make(83);
    static final Char Lit37 = Char.make(115);
    static final Char Lit36 = Char.make(67);
    static final Char Lit35 = Char.make(99);
    static final SimpleSymbol Lit34 = (SimpleSymbol) new SimpleSymbol("printf").readResolve();

    static {
        Char r0 = Lit35;
        Char r1 = Lit37;
        Char r2 = Lit39;
        Char make = Char.make(100);
        Lit12 = make;
        Char make2 = Char.make(105);
        Lit3 = make2;
        Char r5 = Lit43;
        Char r6 = Lit46;
        Char r7 = Lit49;
        Char r8 = Lit52;
        Char make3 = Char.make(102);
        Lit25 = make3;
        Char make4 = Char.make(101);
        Lit13 = make4;
        Lit33 = PairWithPosition.make(r0, PairWithPosition.make(r1, PairWithPosition.make(r2, PairWithPosition.make(make, PairWithPosition.make(make2, PairWithPosition.make(r5, PairWithPosition.make(r6, PairWithPosition.make(r7, PairWithPosition.make(r8, PairWithPosition.make(make3, PairWithPosition.make(make4, PairWithPosition.make(Lit55, PairWithPosition.make(Lit57, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1781780), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1781776), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1781772), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1781768), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777704), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777700), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777696), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777692), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777688), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777684), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777680), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777676), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 1777671);
        Lit32 = Char.make(104);
        Lit31 = Char.make(76);
        Lit30 = Char.make(108);
        Lit29 = Char.make(32);
        Lit28 = Char.make(37);
        Lit27 = Char.make(12);
        Lit26 = Char.make(70);
        Lit24 = Char.make(9);
        Lit23 = Char.make(84);
        Lit22 = Char.make(116);
        Lit21 = Char.make(10);
        Lit20 = Char.make(78);
        Lit19 = Char.make(110);
        Lit18 = Char.make(92);
        Lit17 = IntNum.make(-1);
        Lit16 = IntNum.make(9);
        Lit15 = IntNum.make(5);
        Lit14 = IntNum.make(2);
        Lit11 = Char.make(46);
        Lit10 = PairWithPosition.make(Lit13, PairWithPosition.make(Lit37, PairWithPosition.make(Lit25, PairWithPosition.make(Lit12, PairWithPosition.make(Lit30, PairWithPosition.make(Lit54, PairWithPosition.make(Lit38, PairWithPosition.make(Lit26, PairWithPosition.make(Lit41, PairWithPosition.make(Lit31, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266284), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266280), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266276), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266272), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266268), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266264), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266260), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266256), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266252), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 266247);
        Lit9 = Char.make(48);
        Lit8 = Char.make(35);
        Lit7 = IntNum.make(1);
        Lit6 = Char.make(43);
        Lit5 = Char.make(45);
        Lit4 = Char.make(64);
        Lit2 = PairWithPosition.make(Lit6, PairWithPosition.make(Lit5, LList.Empty, "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 446503), "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm", 446498);
        Lit1 = IntNum.make(0);
        Lit0 = IntNum.make(-15);
        $instance = new printf();
        printf printfVar = $instance;
        stdio$Clparse$Mnfloat = new ModuleMethod(printfVar, 22, Lit69, 8194);
        stdio$Clround$Mnstring = new ModuleMethod(printfVar, 23, Lit70, 12291);
        stdio$Cliprintf = new ModuleMethod(printfVar, 24, Lit71, -4094);
        fprintf = new ModuleMethod(printfVar, 25, Lit72, -4094);
        printf = new ModuleMethod(printfVar, 26, Lit34, -4095);
        sprintf = new ModuleMethod(printfVar, 27, Lit68, -4094);
        $instance.run();
    }

    public printf() {
        ModuleInfo.register(this);
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
        stdio$Clhex$Mnupper$Mncase$Qu = strings.isString$Eq("-F", numbers.number$To$String(Lit0, 16));
    }

    public static Object stdio$ClParseFloat(Object str, Object proc) {
        frame frameVar = new frame();
        frameVar.str = str;
        frameVar.proc = proc;
        Object obj = frameVar.str;
        try {
            frameVar.n = strings.stringLength((CharSequence) obj);
            return frameVar.lambda4real(Lit1, frameVar.lambda$Fn1);
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, obj);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        return moduleMethod.selector == 22 ? stdio$ClParseFloat(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
    }

    @Override // gnu.expr.ModuleBody
    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector == 22) {
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
        return super.match2(moduleMethod, obj, obj2, callContext);
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        int n;
        Object proc;
        Object str;

        public frame() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 12, null, 16388);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:106");
            this.lambda$Fn1 = moduleMethod;
        }

        public static Boolean lambda1parseError() {
            return Boolean.FALSE;
        }

        public Object lambda2sign(Object i, Object cont) {
            if (Scheme.numLss.apply2(i, Integer.valueOf(this.n)) != Boolean.FALSE) {
                Object obj = this.str;
                try {
                    try {
                        char c = strings.stringRef((CharSequence) obj, ((Number) i).intValue());
                        Object x = Scheme.isEqv.apply2(Char.make(c), printf.Lit5);
                        if (x == Boolean.FALSE ? Scheme.isEqv.apply2(Char.make(c), printf.Lit6) != Boolean.FALSE : x != Boolean.FALSE) {
                            return Scheme.applyToArgs.apply3(cont, AddOp.$Pl.apply2(i, printf.Lit7), Char.make(c));
                        }
                        return Scheme.applyToArgs.apply3(cont, i, printf.Lit6);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, i);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, obj);
                }
            }
            return Values.empty;
        }

        public Object lambda3digits(Object i, Object cont) {
            Object substring;
            Object j = i;
            while (true) {
                Object apply2 = Scheme.numGEq.apply2(j, Integer.valueOf(this.n));
                try {
                    boolean x = ((Boolean) apply2).booleanValue();
                    if (!x) {
                        Object obj = this.str;
                        try {
                            try {
                                boolean x2 = unicode.isCharNumeric(Char.make(strings.stringRef((CharSequence) obj, ((Number) j).intValue())));
                                if (!x2) {
                                    Char r5 = printf.Lit8;
                                    Object obj2 = this.str;
                                    try {
                                        try {
                                            if (!characters.isChar$Eq(r5, Char.make(strings.stringRef((CharSequence) obj2, ((Number) j).intValue())))) {
                                                break;
                                            }
                                            j = AddOp.$Pl.apply2(j, printf.Lit7);
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "string-ref", 2, j);
                                        }
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "string-ref", 1, obj2);
                                    }
                                } else if (!x2) {
                                    break;
                                } else {
                                    j = AddOp.$Pl.apply2(j, printf.Lit7);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-ref", 2, j);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-ref", 1, obj);
                        }
                    } else if (x) {
                        break;
                    } else {
                        j = AddOp.$Pl.apply2(j, printf.Lit7);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "x", -2, apply2);
                }
            }
            ApplyToArgs applyToArgs = Scheme.applyToArgs;
            if (Scheme.numEqu.apply2(i, j) != Boolean.FALSE) {
                substring = Component.TYPEFACE_DEFAULT;
            } else {
                Object obj3 = this.str;
                try {
                    try {
                        try {
                            substring = strings.substring((CharSequence) obj3, ((Number) i).intValue(), ((Number) j).intValue());
                        } catch (ClassCastException e6) {
                            throw new WrongType(e6, "substring", 3, j);
                        }
                    } catch (ClassCastException e7) {
                        throw new WrongType(e7, "substring", 2, i);
                    }
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "substring", 1, obj3);
                }
            }
            return applyToArgs.apply3(cont, j, substring);
        }

        /* JADX WARN: Code restructure failed: missing block: B:28:0x00a1, code lost:
            if (kawa.standard.Scheme.isEqv.apply2(gnu.text.Char.make(r1), gnu.kawa.slib.printf.Lit11) == java.lang.Boolean.FALSE) goto L29;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r11, r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:68:?, code lost:
            return lambda1parseError();
         */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0069  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x007f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.Object lambda4real(java.lang.Object r10, java.lang.Object r11) {
            /*
                Method dump skipped, instructions count: 238
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.frame.lambda4real(java.lang.Object, java.lang.Object):java.lang.Object");
        }

        @Override // gnu.expr.ModuleBody
        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            return moduleMethod.selector == 12 ? lambda5(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }

        Object lambda5(Object i, Object sgn, Object digs, Object ex) {
            frame0 frame0Var = new frame0();
            frame0Var.staticLink = this;
            frame0Var.sgn = sgn;
            frame0Var.digs = digs;
            frame0Var.ex = ex;
            if (Scheme.numEqu.apply2(i, Integer.valueOf(this.n)) != Boolean.FALSE) {
                return Scheme.applyToArgs.apply4(this.proc, frame0Var.sgn, frame0Var.digs, frame0Var.ex);
            }
            Object obj = this.str;
            try {
                try {
                    if (lists.memv(Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue())), printf.Lit2) != Boolean.FALSE) {
                        return lambda4real(i, frame0Var.lambda$Fn2);
                    }
                    IsEqv isEqv = Scheme.isEqv;
                    Object obj2 = this.str;
                    try {
                        try {
                            if (isEqv.apply2(Char.make(strings.stringRef((CharSequence) obj2, ((Number) i).intValue())), printf.Lit4) != Boolean.FALSE) {
                                Object obj3 = this.str;
                                try {
                                    frame0Var.num = numbers.string$To$Number((CharSequence) obj3, 10);
                                    if (frame0Var.num != Boolean.FALSE) {
                                        Object obj4 = frame0Var.num;
                                        try {
                                            return printf.stdio$ClParseFloat(numbers.number$To$String(numbers.realPart((Complex) obj4), 10), frame0Var.lambda$Fn3);
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "real-part", 1, obj4);
                                        }
                                    }
                                    return lambda1parseError();
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "string->number", 1, obj3);
                                }
                            }
                            return Boolean.FALSE;
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-ref", 1, obj2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-ref", 2, i);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-ref", 1, obj);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            if (moduleMethod.selector == 12) {
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
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame6 extends ModuleBody {
        Object cont;
        final ModuleMethod lambda$Fn11;
        frame staticLink;

        public frame6() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 5, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:67");
            this.lambda$Fn11 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 5 ? lambda15(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda15(Object i, Object sgn) {
            frame7 frame7Var = new frame7();
            frame7Var.staticLink = this;
            frame7Var.sgn = sgn;
            return this.staticLink.lambda3digits(i, frame7Var.lambda$Fn12);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 5) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame7 extends ModuleBody {
        final ModuleMethod lambda$Fn12;
        Object sgn;
        frame6 staticLink;

        public frame7() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 4, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:69");
            this.lambda$Fn12 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 4 ? lambda16(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda16(Object i, Object digs) {
            Object string$To$Number;
            ApplyToArgs applyToArgs = Scheme.applyToArgs;
            Object obj = this.staticLink.cont;
            Char r3 = printf.Lit5;
            Object obj2 = this.sgn;
            try {
                if (characters.isChar$Eq(r3, (Char) obj2)) {
                    try {
                        string$To$Number = AddOp.$Mn.apply1(numbers.string$To$Number((CharSequence) digs, 10));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string->number", 1, digs);
                    }
                } else {
                    try {
                        string$To$Number = numbers.string$To$Number((CharSequence) digs, 10);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string->number", 1, digs);
                    }
                }
                return applyToArgs.apply3(obj, i, string$To$Number);
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "char=?", 2, obj2);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 4) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame2 extends ModuleBody {
        Object cont;
        final ModuleMethod lambda$Fn5;
        final ModuleMethod lambda$Fn6;
        frame staticLink;

        public frame2() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 10, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:81");
            this.lambda$Fn6 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:78");
            this.lambda$Fn5 = moduleMethod2;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 11 ? lambda9(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda9(Object i) {
            return this.staticLink.lambda2sign(i, this.lambda$Fn6);
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

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 10 ? lambda10(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda10(Object i, Object sgn) {
            frame3 frame3Var = new frame3();
            frame3Var.staticLink = this;
            frame3Var.sgn = sgn;
            return this.staticLink.lambda3digits(i, frame3Var.lambda$Fn7);
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 10) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn7;
        Object sgn;
        frame2 staticLink;

        public frame3() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 9, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:84");
            this.lambda$Fn7 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 9 ? lambda11(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0053, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r4, gnu.kawa.functions.AddOp.$Pl.apply2(r8, gnu.kawa.slib.printf.Lit7));
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0054, code lost:
            if (r1 == false) goto L16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:?, code lost:
            return kawa.standard.Scheme.applyToArgs.apply2(r4, r8);
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0043, code lost:
            if (kawa.lib.characters.isChar$Eq(r5, gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r2, ((java.lang.Number) r8).intValue()))) != false) goto L13;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        java.lang.Object lambda11(java.lang.Object r8, java.lang.Object r9) {
            /*
                r7 = this;
                gnu.kawa.slib.printf$frame4 r2 = new gnu.kawa.slib.printf$frame4
                r2.<init>()
                r2.staticLink = r7
                r2.idigs = r9
                gnu.expr.ModuleMethod r4 = r2.lambda$Fn8
                gnu.kawa.functions.NumberCompare r2 = kawa.standard.Scheme.numLss
                gnu.kawa.slib.printf$frame2 r3 = r7.staticLink
                gnu.kawa.slib.printf$frame r3 = r3.staticLink
                int r3 = r3.n
                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
                java.lang.Object r3 = r2.apply2(r8, r3)
                r0 = r3
                java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch: java.lang.ClassCastException -> L5d
                r2 = r0
                boolean r1 = r2.booleanValue()     // Catch: java.lang.ClassCastException -> L5d
                if (r1 == 0) goto L54
                gnu.text.Char r5 = gnu.kawa.slib.printf.Lit11
                gnu.kawa.slib.printf$frame2 r2 = r7.staticLink
                gnu.kawa.slib.printf$frame r2 = r2.staticLink
                java.lang.Object r2 = r2.str
                java.lang.CharSequence r2 = (java.lang.CharSequence) r2     // Catch: java.lang.ClassCastException -> L67
                r0 = r8
                java.lang.Number r0 = (java.lang.Number) r0     // Catch: java.lang.ClassCastException -> L71
                r3 = r0
                int r3 = r3.intValue()     // Catch: java.lang.ClassCastException -> L71
                char r2 = kawa.lib.strings.stringRef(r2, r3)
                gnu.text.Char r2 = gnu.text.Char.make(r2)
                boolean r2 = kawa.lib.characters.isChar$Eq(r5, r2)
                if (r2 == 0) goto L56
            L45:
                gnu.kawa.functions.ApplyToArgs r2 = kawa.standard.Scheme.applyToArgs
                gnu.kawa.functions.AddOp r3 = gnu.kawa.functions.AddOp.$Pl
                gnu.math.IntNum r5 = gnu.kawa.slib.printf.Lit7
                java.lang.Object r3 = r3.apply2(r8, r5)
                java.lang.Object r2 = r2.apply2(r4, r3)
            L53:
                return r2
            L54:
                if (r1 != 0) goto L45
            L56:
                gnu.kawa.functions.ApplyToArgs r2 = kawa.standard.Scheme.applyToArgs
                java.lang.Object r2 = r2.apply2(r4, r8)
                goto L53
            L5d:
                r2 = move-exception
                gnu.mapping.WrongType r4 = new gnu.mapping.WrongType
                java.lang.String r5 = "x"
                r6 = -2
                r4.<init>(r2, r5, r6, r3)
                throw r4
            L67:
                r3 = move-exception
                gnu.mapping.WrongType r4 = new gnu.mapping.WrongType
                java.lang.String r5 = "string-ref"
                r6 = 1
                r4.<init>(r3, r5, r6, r2)
                throw r4
            L71:
                r2 = move-exception
                gnu.mapping.WrongType r3 = new gnu.mapping.WrongType
                java.lang.String r4 = "string-ref"
                r5 = 2
                r3.<init>(r2, r4, r5, r8)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.frame3.lambda11(java.lang.Object, java.lang.Object):java.lang.Object");
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
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame4 extends ModuleBody {
        Object idigs;
        final ModuleMethod lambda$Fn8;
        final ModuleMethod lambda$Fn9;
        frame3 staticLink;

        public frame4() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 7, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:90");
            this.lambda$Fn9 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 8, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:87");
            this.lambda$Fn8 = moduleMethod2;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 8 ? lambda12(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda12(Object i) {
            return this.staticLink.staticLink.staticLink.lambda3digits(i, this.lambda$Fn9);
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

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 7 ? lambda13(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda13(Object i, Object fdigs) {
            frame5 frame5Var = new frame5();
            frame5Var.staticLink = this;
            frame5Var.fdigs = fdigs;
            ModuleMethod moduleMethod = frame5Var.lambda$Fn10;
            frame closureEnv = this.staticLink.staticLink.staticLink;
            frame6 frame6Var = new frame6();
            frame6Var.staticLink = closureEnv;
            frame6Var.cont = moduleMethod;
            if (Scheme.numGEq.apply2(i, Integer.valueOf(this.staticLink.staticLink.staticLink.n)) != Boolean.FALSE) {
                return Scheme.applyToArgs.apply3(frame6Var.cont, i, printf.Lit1);
            }
            Object obj = this.staticLink.staticLink.staticLink.str;
            try {
                try {
                    if (lists.memv(Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue())), printf.Lit10) != Boolean.FALSE) {
                        return this.staticLink.staticLink.staticLink.lambda2sign(AddOp.$Pl.apply2(i, printf.Lit7), frame6Var.lambda$Fn11);
                    }
                    return Scheme.applyToArgs.apply3(frame6Var.cont, i, printf.Lit1);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, i);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, obj);
            }
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
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame5 extends ModuleBody {
        Object fdigs;
        final ModuleMethod lambda$Fn10;
        frame4 staticLink;

        public frame5() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 6, null, 8194);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:92");
            this.lambda$Fn10 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 6 ? lambda14(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda14(Object i, Object ex) {
            FString digs = strings.stringAppend(Component.TYPEFACE_DEFAULT, this.staticLink.idigs, this.fdigs);
            int ndigs = strings.stringLength(digs);
            Object obj = printf.Lit7;
            AddOp addOp = AddOp.$Pl;
            Object obj2 = this.staticLink.idigs;
            try {
                Object ex2 = addOp.apply2(ex, Integer.valueOf(strings.stringLength((CharSequence) obj2)));
                while (Scheme.numGEq.apply2(obj, Integer.valueOf(ndigs)) == Boolean.FALSE) {
                    try {
                        if (characters.isChar$Eq(printf.Lit9, Char.make(strings.stringRef(digs, ((Number) obj).intValue())))) {
                            obj = AddOp.$Pl.apply2(obj, printf.Lit7);
                            ex2 = AddOp.$Mn.apply2(ex2, printf.Lit7);
                        } else {
                            ApplyToArgs applyToArgs = Scheme.applyToArgs;
                            Object[] objArr = new Object[5];
                            objArr[0] = this.staticLink.staticLink.staticLink.cont;
                            objArr[1] = i;
                            objArr[2] = this.staticLink.staticLink.sgn;
                            Object apply2 = AddOp.$Mn.apply2(obj, printf.Lit7);
                            try {
                                objArr[3] = strings.substring(digs, ((Number) apply2).intValue(), ndigs);
                                objArr[4] = ex2;
                                return applyToArgs.applyN(objArr);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 2, apply2);
                            }
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 2, obj);
                    }
                }
                return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.staticLink.staticLink.cont, i, this.staticLink.staticLink.sgn, Component.TYPEFACE_DEFAULT, printf.Lit7});
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "string-length", 1, obj2);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector == 6) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            }
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame0 extends ModuleBody {
        Object digs;
        Object ex;
        final ModuleMethod lambda$Fn2;
        final ModuleMethod lambda$Fn3;
        Object num;
        Object sgn;
        frame staticLink;

        public frame0() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 2, null, 16388);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:111");
            this.lambda$Fn2 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 3, null, 12291);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:123");
            this.lambda$Fn3 = moduleMethod2;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            return moduleMethod.selector == 2 ? lambda6(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x0060, code lost:
            return kawa.standard.Scheme.applyToArgs.applyN(new java.lang.Object[]{r8.staticLink.proc, r8.sgn, r8.digs, r8.ex, r10, r11, r12});
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x0061, code lost:
            if (r1 == false) goto L16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:?, code lost:
            return gnu.kawa.slib.printf.frame.lambda1parseError();
         */
        /* JADX WARN: Code restructure failed: missing block: B:9:0x0038, code lost:
            if (kawa.lib.rnrs.unicode.isCharCi$Eq(r4, gnu.text.Char.make(kawa.lib.strings.stringRef((java.lang.CharSequence) r2, ((java.lang.Number) r9).intValue()))) != false) goto L13;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        java.lang.Object lambda6(java.lang.Object r9, java.lang.Object r10, java.lang.Object r11, java.lang.Object r12) {
            /*
                r8 = this;
                r7 = 2
                r6 = 1
                gnu.kawa.functions.NumberCompare r2 = kawa.standard.Scheme.numEqu
                gnu.kawa.slib.printf$frame r3 = r8.staticLink
                int r3 = r3.n
                int r3 = r3 + (-1)
                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
                java.lang.Object r3 = r2.apply2(r9, r3)
                r0 = r3
                java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch: java.lang.ClassCastException -> L68
                r2 = r0
                boolean r1 = r2.booleanValue()     // Catch: java.lang.ClassCastException -> L68
                if (r1 == 0) goto L61
                gnu.text.Char r4 = gnu.kawa.slib.printf.Lit3
                gnu.kawa.slib.printf$frame r2 = r8.staticLink
                java.lang.Object r2 = r2.str
                java.lang.CharSequence r2 = (java.lang.CharSequence) r2     // Catch: java.lang.ClassCastException -> L72
                r0 = r9
                java.lang.Number r0 = (java.lang.Number) r0     // Catch: java.lang.ClassCastException -> L7b
                r3 = r0
                int r3 = r3.intValue()     // Catch: java.lang.ClassCastException -> L7b
                char r2 = kawa.lib.strings.stringRef(r2, r3)
                gnu.text.Char r2 = gnu.text.Char.make(r2)
                boolean r2 = kawa.lib.rnrs.unicode.isCharCi$Eq(r4, r2)
                if (r2 == 0) goto L63
            L3a:
                gnu.kawa.functions.ApplyToArgs r2 = kawa.standard.Scheme.applyToArgs
                r3 = 7
                java.lang.Object[] r3 = new java.lang.Object[r3]
                r4 = 0
                gnu.kawa.slib.printf$frame r5 = r8.staticLink
                java.lang.Object r5 = r5.proc
                r3[r4] = r5
                java.lang.Object r4 = r8.sgn
                r3[r6] = r4
                java.lang.Object r4 = r8.digs
                r3[r7] = r4
                r4 = 3
                java.lang.Object r5 = r8.ex
                r3[r4] = r5
                r4 = 4
                r3[r4] = r10
                r4 = 5
                r3[r4] = r11
                r4 = 6
                r3[r4] = r12
                java.lang.Object r2 = r2.applyN(r3)
            L60:
                return r2
            L61:
                if (r1 != 0) goto L3a
            L63:
                java.lang.Boolean r2 = gnu.kawa.slib.printf.frame.lambda1parseError()
                goto L60
            L68:
                r2 = move-exception
                gnu.mapping.WrongType r4 = new gnu.mapping.WrongType
                java.lang.String r5 = "x"
                r6 = -2
                r4.<init>(r2, r5, r6, r3)
                throw r4
            L72:
                r3 = move-exception
                gnu.mapping.WrongType r4 = new gnu.mapping.WrongType
                java.lang.String r5 = "string-ref"
                r4.<init>(r3, r5, r6, r2)
                throw r4
            L7b:
                r2 = move-exception
                gnu.mapping.WrongType r3 = new gnu.mapping.WrongType
                java.lang.String r4 = "string-ref"
                r3.<init>(r2, r4, r7, r9)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.frame0.lambda6(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        @Override // gnu.expr.ModuleBody
        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            if (moduleMethod.selector == 2) {
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

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 3 ? lambda7(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda7(Object sgn, Object digs, Object ex) {
            frame1 frame1Var = new frame1();
            frame1Var.staticLink = this;
            frame1Var.sgn = sgn;
            frame1Var.digs = digs;
            frame1Var.ex = ex;
            Object obj = this.num;
            try {
                return printf.stdio$ClParseFloat(numbers.number$To$String(numbers.imagPart((Complex) obj), 10), frame1Var.lambda$Fn4);
            } catch (ClassCastException e) {
                throw new WrongType(e, "imag-part", 1, obj);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 3) {
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

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame1 extends ModuleBody {
        Object digs;
        Object ex;
        final ModuleMethod lambda$Fn4;
        Object sgn;
        frame0 staticLink;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, null, 12291);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:126");
            this.lambda$Fn4 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 1 ? lambda8(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda8(Object im$Mnsgn, Object im$Mndigs, Object im$Mnex) {
            return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.staticLink.proc, this.sgn, this.digs, this.ex, im$Mnsgn, im$Mndigs, im$Mnex});
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector == 1) {
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

    /* JADX WARN: Code restructure failed: missing block: B:44:0x0112, code lost:
        if (r8 != false) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x017d, code lost:
        if ((((java.lang.Number) r1.lambda17dig(r15)).intValue() & 1) != 0) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0195, code lost:
        if (r8 != false) goto L96;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object stdio$ClRoundString(java.lang.CharSequence r14, java.lang.Object r15, java.lang.Object r16) {
        /*
            Method dump skipped, instructions count: 644
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.stdio$ClRoundString(java.lang.CharSequence, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    @Override // gnu.expr.ModuleBody
    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        if (moduleMethod.selector == 23) {
            try {
                return stdio$ClRoundString((CharSequence) obj, obj2, obj3);
            } catch (ClassCastException e) {
                throw new WrongType(e, "stdio:round-string", 1, obj);
            }
        }
        return super.apply3(moduleMethod, obj, obj2, obj3);
    }

    @Override // gnu.expr.ModuleBody
    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        if (moduleMethod.selector == 23) {
            if (obj instanceof CharSequence) {
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            }
            return -786431;
        }
        return super.match3(moduleMethod, obj, obj2, obj3, callContext);
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame8 extends ModuleBody {
        CharSequence str;

        public Object lambda17dig(Object i) {
            try {
                char c = strings.stringRef(this.str, ((Number) i).intValue());
                return unicode.isCharNumeric(Char.make(c)) ? numbers.string$To$Number(strings.$make$string$(Char.make(c)), 10) : printf.Lit1;
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-ref", 2, i);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x0302, code lost:
        if (r7 == false) goto L513;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0304, code lost:
        r4 = (java.lang.CharSequence) r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0306, code lost:
        r10 = r12.precision;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0311, code lost:
        r4 = kawa.lib.strings.substring(r4, 0, ((java.lang.Number) r10).intValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x032b, code lost:
        if (kawa.standard.Scheme.numLEq.apply2(r12.width, java.lang.Integer.valueOf(kawa.lib.strings.stringLength((java.lang.CharSequence) r4))) == java.lang.Boolean.FALSE) goto L473;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x032d, code lost:
        r7 = r2.lambda21out$St(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0333, code lost:
        if (r7 == java.lang.Boolean.FALSE) goto L471;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0335, code lost:
        r1 = kawa.lib.lists.cdr.apply1(r12.args);
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x038e, code lost:
        if (kawa.lib.numbers.isNegative(gnu.kawa.lispexpr.LangObjType.coerceRealNum(r10)) != false) goto L347;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0390, code lost:
        r12.pr = gnu.kawa.slib.printf.Lit1;
        r10 = r12.lambda$Fn13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x03a9, code lost:
        if (kawa.lib.numbers.isNegative(gnu.kawa.lispexpr.LangObjType.coerceRealNum(r9)) != false) goto L355;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x03b7, code lost:
        if (kawa.standard.Scheme.numGrt.apply2(r12.width, r12.pr) == java.lang.Boolean.FALSE) goto L365;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x03b9, code lost:
        r11 = kawa.standard.Scheme.applyToArgs;
        r13 = r2.out;
        r10 = gnu.kawa.functions.AddOp.$Mn.apply2(r12.width, r12.pr);
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x03cf, code lost:
        r11.apply2(r13, kawa.lib.strings.makeString(((java.lang.Number) r10).intValue(), gnu.kawa.slib.printf.Lit29));
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0412, code lost:
        if (kawa.standard.Scheme.numGEq.apply2(r12.precision, java.lang.Integer.valueOf(kawa.lib.strings.stringLength((java.lang.CharSequence) r4))) == java.lang.Boolean.FALSE) goto L513;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x041a, code lost:
        if (r12.left$Mnadjust == java.lang.Boolean.FALSE) goto L490;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x0424, code lost:
        r10 = gnu.kawa.functions.AddOp.$Mn.apply2(r12.width, java.lang.Integer.valueOf(kawa.lib.strings.stringLength((java.lang.CharSequence) r4)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0438, code lost:
        r4 = gnu.lists.LList.list2(r4, kawa.lib.strings.makeString(((java.lang.Number) r10).intValue(), gnu.kawa.slib.printf.Lit29));
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x044c, code lost:
        r10 = gnu.kawa.functions.AddOp.$Mn.apply2(r12.width, java.lang.Integer.valueOf(kawa.lib.strings.stringLength((java.lang.CharSequence) r4)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0458, code lost:
        r10 = ((java.lang.Number) r10).intValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x0464, code lost:
        if (r12.leading$Mn0s == java.lang.Boolean.FALSE) goto L500;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x0466, code lost:
        r9 = gnu.kawa.slib.printf.Lit9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x0468, code lost:
        r4 = gnu.lists.LList.list2(kawa.lib.strings.makeString(r10, r9), r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x0472, code lost:
        r9 = gnu.kawa.slib.printf.Lit29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x04b9, code lost:
        if (r7 == java.lang.Boolean.FALSE) goto L424;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x04bf, code lost:
        if (r12.left$Mnadjust == java.lang.Boolean.FALSE) goto L427;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x04c1, code lost:
        r10 = r12.lambda$Fn14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x04c5, code lost:
        r10 = r12.pr;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x04cf, code lost:
        if (kawa.lib.numbers.isNegative(gnu.kawa.lispexpr.LangObjType.coerceRealNum(r10)) == false) goto L433;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x04d1, code lost:
        r12.pr = r12.width;
        r10 = r12.lambda$Fn15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x04d9, code lost:
        r10 = r12.lambda$Fn16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x04df, code lost:
        if (r7 == java.lang.Boolean.FALSE) goto L367;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x04e5, code lost:
        if (r12.left$Mnadjust == java.lang.Boolean.FALSE) goto L379;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x04fb, code lost:
        if (kawa.standard.Scheme.numGrt.apply2(r12.width, gnu.kawa.functions.AddOp.$Mn.apply2(r12.precision, r12.pr)) == java.lang.Boolean.FALSE) goto L365;
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x04fd, code lost:
        r11 = kawa.standard.Scheme.applyToArgs;
        r13 = r2.out;
        r10 = gnu.kawa.functions.AddOp.$Mn.apply2(r12.width, gnu.kawa.functions.AddOp.$Mn.apply2(r12.precision, r12.pr));
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x051d, code lost:
        r11.apply2(r13, kawa.lib.strings.makeString(((java.lang.Number) r10).intValue(), gnu.kawa.slib.printf.Lit29));
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x0528, code lost:
        r9 = r12.os;
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x052c, code lost:
        if (r9 == java.lang.Boolean.FALSE) goto L413;
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x052e, code lost:
        r9 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x0533, code lost:
        if (((r9 + 1) & 1) != 0) goto L365;
     */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x0535, code lost:
        r10 = kawa.standard.Scheme.numLEq;
        r11 = r12.width;
        r9 = r12.os;
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x054b, code lost:
        if (r10.apply2(r11, java.lang.Integer.valueOf(kawa.lib.strings.stringLength((java.lang.CharSequence) r9))) == java.lang.Boolean.FALSE) goto L392;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x054d, code lost:
        kawa.standard.Scheme.applyToArgs.apply2(r2.out, r12.os);
     */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x0558, code lost:
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:241:0x055a, code lost:
        r11 = kawa.standard.Scheme.applyToArgs;
        r13 = r2.out;
        r10 = gnu.kawa.functions.AddOp.$Mn;
        r14 = r12.width;
        r9 = r12.os;
     */
    /* JADX WARN: Code restructure failed: missing block: B:243:0x0566, code lost:
        r10 = r10.apply2(r14, java.lang.Integer.valueOf(kawa.lib.strings.stringLength((java.lang.CharSequence) r9)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x0586, code lost:
        if (r11.apply2(r13, kawa.lib.strings.makeString(((java.lang.Number) r10).intValue(), gnu.kawa.slib.printf.Lit29)) == java.lang.Boolean.FALSE) goto L365;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x0588, code lost:
        kawa.standard.Scheme.applyToArgs.apply2(r2.out, r12.os);
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x07e5, code lost:
        if (kawa.lib.rnrs.unicode.isCharCi$Eq((gnu.text.Char) r9, gnu.kawa.slib.printf.Lit55) != false) goto L196;
     */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x07e7, code lost:
        r12.precision = gnu.kawa.slib.printf.Lit7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:373:0x07ed, code lost:
        if (r7 != false) goto L196;
     */
    /* JADX WARN: Code restructure failed: missing block: B:422:0x08b4, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:424:0x08bd, code lost:
        throw new gnu.mapping.WrongType(r9, "substring", 1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:425:0x08be, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:427:0x08c7, code lost:
        throw new gnu.mapping.WrongType(r9, "substring", 3, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:428:0x08c8, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:430:0x08d1, code lost:
        throw new gnu.mapping.WrongType(r9, "string-length", 1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:431:0x08d2, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:433:0x08db, code lost:
        throw new gnu.mapping.WrongType(r9, "string-length", 1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:434:0x08dc, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:436:0x08e5, code lost:
        throw new gnu.mapping.WrongType(r9, "make-string", 1, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:437:0x08e6, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:439:0x08ef, code lost:
        throw new gnu.mapping.WrongType(r9, "string-length", 1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:440:0x08f0, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:442:0x08f9, code lost:
        throw new gnu.mapping.WrongType(r9, "make-string", 1, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:446:0x0904, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:448:0x090d, code lost:
        throw new gnu.mapping.WrongType(r9, "negative?", 1, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:452:0x0918, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:454:0x0921, code lost:
        throw new gnu.mapping.WrongType(r9, "make-string", 1, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:455:0x0922, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:457:0x092b, code lost:
        throw new gnu.mapping.WrongType(r9, "make-string", 1, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:458:0x092c, code lost:
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:460:0x0935, code lost:
        throw new gnu.mapping.WrongType(r10, "x", -2, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:461:0x0936, code lost:
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:463:0x093f, code lost:
        throw new gnu.mapping.WrongType(r10, "string-length", 1, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:464:0x0940, code lost:
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:466:0x0949, code lost:
        throw new gnu.mapping.WrongType(r10, "string-length", 1, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:467:0x094a, code lost:
        r9 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:469:0x0953, code lost:
        throw new gnu.mapping.WrongType(r9, "make-string", 1, r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:643:?, code lost:
        return r7;
     */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0348  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0360  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x037a  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0384  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x039f  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03e2  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x03fc  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0475  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x048d  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x04a9  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x04b3  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x04b7  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x04dd  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x05af  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x05cb  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0619  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x06b7  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x071a  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0744  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x07d1  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x07f0  */
    /* JADX WARN: Removed duplicated region for block: B:383:0x080b  */
    /* JADX WARN: Removed duplicated region for block: B:384:0x0816  */
    /* JADX WARN: Removed duplicated region for block: B:485:0x0724 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:548:0x0075 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:568:0x0075 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:577:0x0075 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:581:0x065a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:584:0x0631 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:589:0x0075 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:593:0x06eb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:595:0x06d9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:597:0x0075 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:609:0x0764 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object stdio$ClIprintf$V(java.lang.Object r17, java.lang.Object r18, java.lang.Object[] r19) {
        /*
            Method dump skipped, instructions count: 2438
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.stdio$ClIprintf$V(java.lang.Object, java.lang.Object, java.lang.Object[]):java.lang.Object");
    }

    @Override // gnu.expr.ModuleBody
    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 24:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 25:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 26:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 27:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame9 extends ModuleBody {
        LList args;
        Object fc;
        int fl;
        Object format$Mnstring;
        Object out;
        Object pos;

        public Object lambda18mustAdvance() {
            this.pos = AddOp.$Pl.apply2(printf.Lit7, this.pos);
            if (Scheme.numGEq.apply2(this.pos, Integer.valueOf(this.fl)) != Boolean.FALSE) {
                return lambda20incomplete();
            }
            Object obj = this.format$Mnstring;
            try {
                CharSequence charSequence = (CharSequence) obj;
                Object obj2 = this.pos;
                try {
                    this.fc = Char.make(strings.stringRef(charSequence, ((Number) obj2).intValue()));
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, obj2);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, obj);
            }
        }

        public boolean lambda19isEndOfFormat() {
            return ((Boolean) Scheme.numGEq.apply2(this.pos, Integer.valueOf(this.fl))).booleanValue();
        }

        public Object lambda20incomplete() {
            return misc.error$V(printf.Lit34, new Object[]{"conversion specification incomplete", this.format$Mnstring});
        }

        public Object lambda21out$St(Object strs) {
            if (strings.isString(strs)) {
                return Scheme.applyToArgs.apply2(this.out, strs);
            }
            while (true) {
                boolean x = lists.isNull(strs);
                if (x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                Object x2 = Scheme.applyToArgs.apply2(this.out, lists.car.apply1(strs));
                if (x2 == Boolean.FALSE) {
                    return x2;
                }
                strs = lists.cdr.apply1(strs);
            }
        }
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame10 extends ModuleBody {
        Object alternate$Mnform;
        Object args;
        Object blank;
        final ModuleMethod lambda$Fn13;
        final ModuleMethod lambda$Fn14;
        final ModuleMethod lambda$Fn15;
        final ModuleMethod lambda$Fn16;
        Object leading$Mn0s;
        Object left$Mnadjust;
        Object os;
        Procedure pad = new ModuleMethod(this, 15, printf.Lit67, -4095);
        Object pr;
        Object precision;
        Object signed;
        frame9 staticLink;
        Object width;

        public frame10() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 16, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:472");
            this.lambda$Fn13 = moduleMethod;
            ModuleMethod moduleMethod2 = new ModuleMethod(this, 17, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod2.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:476");
            this.lambda$Fn14 = moduleMethod2;
            ModuleMethod moduleMethod3 = new ModuleMethod(this, 18, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod3.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:484");
            this.lambda$Fn15 = moduleMethod3;
            ModuleMethod moduleMethod4 = new ModuleMethod(this, 19, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod4.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:494");
            this.lambda$Fn16 = moduleMethod4;
        }

        public Object lambda22readFormatNumber() {
            if (Scheme.isEqv.apply2(printf.Lit66, this.staticLink.fc) != Boolean.FALSE) {
                this.staticLink.lambda18mustAdvance();
                Object ans = lists.car.apply1(this.args);
                this.args = lists.cdr.apply1(this.args);
                return ans;
            }
            Object c = this.staticLink.fc;
            Object obj = printf.Lit1;
            while (true) {
                Object obj2 = this.staticLink.fc;
                try {
                    if (!unicode.isCharNumeric((Char) obj2)) {
                        Object ans2 = obj;
                        return ans2;
                    }
                    this.staticLink.lambda18mustAdvance();
                    Object c2 = this.staticLink.fc;
                    obj = AddOp.$Pl.apply2(MultiplyOp.$St.apply2(obj, printf.Lit45), numbers.string$To$Number(strings.$make$string$(c instanceof Object[] ? (Object[]) c : new Object[]{c}), 10));
                    c = c2;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "char-numeric?", 1, obj2);
                }
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            if (moduleMethod.selector != 15) {
                return super.applyN(moduleMethod, objArr);
            }
            Object obj = objArr[0];
            int length = objArr.length - 1;
            Object[] objArr2 = new Object[length];
            while (true) {
                length--;
                if (length < 0) {
                    return lambda23pad$V(obj, objArr2);
                }
                objArr2[length] = objArr[length + 1];
            }
        }

        public Object lambda23pad$V(Object pre, Object[] argsArray) {
            LList strs = LList.makeList(argsArray, 0);
            try {
                Object valueOf = Integer.valueOf(strings.stringLength((CharSequence) pre));
                Object obj = strs;
                while (Scheme.numGEq.apply2(valueOf, this.width) == Boolean.FALSE) {
                    if (lists.isNull(obj)) {
                        if (this.left$Mnadjust != Boolean.FALSE) {
                            Object[] objArr = new Object[2];
                            objArr[0] = strs;
                            Object apply2 = AddOp.$Mn.apply2(this.width, valueOf);
                            try {
                                objArr[1] = LList.list1(strings.makeString(((Number) apply2).intValue(), printf.Lit29));
                                return lists.cons(pre, append.append$V(objArr));
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "make-string", 1, apply2);
                            }
                        } else if (this.leading$Mn0s != Boolean.FALSE) {
                            Object apply22 = AddOp.$Mn.apply2(this.width, valueOf);
                            try {
                                return lists.cons(pre, lists.cons(strings.makeString(((Number) apply22).intValue(), printf.Lit9), strs));
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "make-string", 1, apply22);
                            }
                        } else {
                            Object apply23 = AddOp.$Mn.apply2(this.width, valueOf);
                            try {
                                return lists.cons(strings.makeString(((Number) apply23).intValue(), printf.Lit29), lists.cons(pre, strs));
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "make-string", 1, apply23);
                            }
                        }
                    }
                    AddOp addOp = AddOp.$Pl;
                    Object apply1 = lists.car.apply1(obj);
                    try {
                        valueOf = addOp.apply2(valueOf, Integer.valueOf(strings.stringLength((CharSequence) apply1)));
                        obj = lists.cdr.apply1(obj);
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-length", 1, apply1);
                    }
                }
                return lists.cons(pre, strs);
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "string-length", 1, pre);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            if (moduleMethod.selector == 15) {
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            }
            return super.matchN(moduleMethod, objArr, callContext);
        }

        public Object lambda24integerConvert(Object s, Object radix, Object fixcase) {
            Object obj;
            CharSequence s2;
            String pre;
            Object obj2;
            Object obj3 = this.precision;
            try {
                if (numbers.isNegative(LangObjType.coerceRealNum(obj3))) {
                    obj = s;
                } else {
                    this.leading$Mn0s = Boolean.FALSE;
                    Object obj4 = this.precision;
                    try {
                        boolean x = numbers.isZero((Number) obj4);
                        if (!x ? x : Scheme.isEqv.apply2(printf.Lit1, s) != Boolean.FALSE) {
                            s = "";
                        }
                        obj = s;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "zero?", 1, obj4);
                    }
                }
                if (misc.isSymbol(obj)) {
                    try {
                        s2 = ((Symbol) obj).toString();
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "symbol->string", 1, obj);
                    }
                } else if (numbers.isNumber(obj)) {
                    try {
                        try {
                            s2 = numbers.number$To$String((Number) obj, ((Number) radix).intValue());
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "number->string", 2, radix);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "number->string", 1, obj);
                    }
                } else {
                    try {
                        int i = ((obj != Boolean.FALSE ? 1 : 0) + 1) & 1;
                        s2 = (i == 0 ? lists.isNull(obj) : i != 0) ? Component.TYPEFACE_DEFAULT : strings.isString(obj) ? obj : Component.TYPEFACE_SANSSERIF;
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "x", -2, obj);
                    }
                }
                if (fixcase != Boolean.FALSE) {
                    s2 = Scheme.applyToArgs.apply2(fixcase, s2);
                }
                if (IsEqual.apply("", s2)) {
                    pre = "";
                } else {
                    try {
                        if (Scheme.isEqv.apply2(printf.Lit5, Char.make(strings.stringRef((CharSequence) s2, 0))) != Boolean.FALSE) {
                            try {
                                try {
                                    s2 = strings.substring((CharSequence) s2, 1, strings.stringLength((CharSequence) s2));
                                    pre = "-";
                                } catch (ClassCastException e6) {
                                    throw new WrongType(e6, "string-length", 1, s2);
                                }
                            } catch (ClassCastException e7) {
                                throw new WrongType(e7, "substring", 1, s2);
                            }
                        } else if (this.signed != Boolean.FALSE) {
                            pre = "+";
                        } else if (this.blank != Boolean.FALSE) {
                            pre = " ";
                        } else if (this.alternate$Mnform != Boolean.FALSE) {
                            pre = Scheme.isEqv.apply2(radix, printf.Lit48) != Boolean.FALSE ? Component.TYPEFACE_DEFAULT : Scheme.isEqv.apply2(radix, printf.Lit50) != Boolean.FALSE ? "0x" : "";
                        } else {
                            pre = "";
                        }
                    } catch (ClassCastException e8) {
                        throw new WrongType(e8, "string-ref", 1, s2);
                    }
                }
                Object[] objArr = new Object[2];
                try {
                    if (Scheme.numLss.apply2(Integer.valueOf(strings.stringLength(s2)), this.precision) != Boolean.FALSE) {
                        try {
                            Object apply2 = AddOp.$Mn.apply2(this.precision, Integer.valueOf(strings.stringLength(s2)));
                            try {
                                obj2 = strings.makeString(((Number) apply2).intValue(), printf.Lit9);
                            } catch (ClassCastException e9) {
                                throw new WrongType(e9, "make-string", 1, apply2);
                            }
                        } catch (ClassCastException e10) {
                            throw new WrongType(e10, "string-length", 1, s2);
                        }
                    } else {
                        obj2 = "";
                    }
                    objArr[0] = obj2;
                    objArr[1] = s2;
                    return lambda23pad$V(pre, objArr);
                } catch (ClassCastException e11) {
                    throw new WrongType(e11, "string-length", 1, s2);
                }
            } catch (ClassCastException e12) {
                throw new WrongType(e12, "negative?", 1, obj3);
            }
        }

        Object lambda25(Object s) {
            try {
                this.pr = AddOp.$Pl.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                return Scheme.applyToArgs.apply2(this.staticLink.out, s);
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, s);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 16:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 17:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 18:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 19:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        boolean lambda26(Object s) {
            Object obj;
            Special special = Special.undefined;
            try {
                Object sl = AddOp.$Mn.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                try {
                    if (numbers.isNegative(LangObjType.coerceRealNum(sl))) {
                        ApplyToArgs applyToArgs = Scheme.applyToArgs;
                        Object obj2 = this.staticLink.out;
                        try {
                            CharSequence charSequence = (CharSequence) s;
                            Object obj3 = this.pr;
                            try {
                                applyToArgs.apply2(obj2, strings.substring(charSequence, 0, ((Number) obj3).intValue()));
                                obj = printf.Lit1;
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 3, obj3);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 1, s);
                        }
                    } else {
                        Scheme.applyToArgs.apply2(this.staticLink.out, s);
                        obj = sl;
                    }
                    this.pr = obj;
                    try {
                        return numbers.isPositive(LangObjType.coerceRealNum(sl));
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "positive?", 1, sl);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "negative?", 1, sl);
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "string-length", 1, s);
            }
        }

        Boolean lambda27(Object s) {
            try {
                this.pr = AddOp.$Mn.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                if (this.os == Boolean.FALSE) {
                    Scheme.applyToArgs.apply2(this.staticLink.out, s);
                } else {
                    Object obj = this.pr;
                    try {
                        if (numbers.isNegative(LangObjType.coerceRealNum(obj))) {
                            Scheme.applyToArgs.apply2(this.staticLink.out, this.os);
                            this.os = Boolean.FALSE;
                            Scheme.applyToArgs.apply2(this.staticLink.out, s);
                        } else {
                            this.os = strings.stringAppend(this.os, s);
                        }
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "negative?", 1, obj);
                    }
                }
                return Boolean.TRUE;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-length", 1, s);
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 16:
                    return lambda25(obj);
                case 17:
                    return lambda26(obj) ? Boolean.TRUE : Boolean.FALSE;
                case 18:
                    return lambda27(obj);
                case 19:
                    return lambda28(obj) ? Boolean.TRUE : Boolean.FALSE;
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda28(Object s) {
            Special special = Special.undefined;
            try {
                Object sl = AddOp.$Mn.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                try {
                    if (numbers.isNegative(LangObjType.coerceRealNum(sl))) {
                        Object[] objArr = new Object[2];
                        objArr[0] = this.os;
                        try {
                            CharSequence charSequence = (CharSequence) s;
                            Object obj = this.pr;
                            try {
                                objArr[1] = strings.substring(charSequence, 0, ((Number) obj).intValue());
                                this.os = strings.stringAppend(objArr);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 3, obj);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 1, s);
                        }
                    } else {
                        this.os = strings.stringAppend(this.os, s);
                    }
                    this.pr = sl;
                    try {
                        return numbers.isPositive(LangObjType.coerceRealNum(sl));
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "positive?", 1, sl);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "negative?", 1, sl);
                }
            } catch (ClassCastException e5) {
                throw new WrongType(e5, "string-length", 1, s);
            }
        }
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame11 extends ModuleBody {
        Object fc;
        Procedure format$Mnreal = new ModuleMethod(this, 13, printf.Lit64, -4092);
        final ModuleMethod lambda$Fn17;
        frame10 staticLink;

        public frame11() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 14, null, -4093);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:401");
            this.lambda$Fn17 = moduleMethod;
        }

        public Object lambda29f(Object digs, Object exp, Object strip$Mn0s) {
            IntNum i0;
            Object x;
            Object obj;
            try {
                Object digs2 = printf.stdio$ClRoundString((CharSequence) digs, AddOp.$Pl.apply2(exp, this.staticLink.precision), strip$Mn0s != Boolean.FALSE ? exp : strip$Mn0s);
                if (Scheme.numGEq.apply2(exp, printf.Lit1) != Boolean.FALSE) {
                    try {
                        if (numbers.isZero((Number) exp)) {
                            i0 = printf.Lit1;
                        } else {
                            try {
                                if (characters.isChar$Eq(printf.Lit9, Char.make(strings.stringRef((CharSequence) digs2, 0)))) {
                                    i0 = printf.Lit7;
                                } else {
                                    i0 = printf.Lit1;
                                }
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 1, digs2);
                            }
                        }
                        Object i1 = numbers.max(printf.Lit7, AddOp.$Pl.apply2(printf.Lit7, exp));
                        try {
                            try {
                                try {
                                    CharSequence idigs = strings.substring((CharSequence) digs2, i0.intValue(), ((Number) i1).intValue());
                                    try {
                                        try {
                                            try {
                                                CharSequence fdigs = strings.substring((CharSequence) digs2, ((Number) i1).intValue(), strings.stringLength((CharSequence) digs2));
                                                boolean x2 = strings.isString$Eq(fdigs, "");
                                                return lists.cons(idigs, (!x2 ? !x2 : this.staticLink.alternate$Mnform != Boolean.FALSE) ? LList.Empty : LList.list2(".", fdigs));
                                            } catch (ClassCastException e2) {
                                                throw new WrongType(e2, "string-length", 1, digs2);
                                            }
                                        } catch (ClassCastException e3) {
                                            throw new WrongType(e3, "substring", 2, i1);
                                        }
                                    } catch (ClassCastException e4) {
                                        throw new WrongType(e4, "substring", 1, digs2);
                                    }
                                } catch (ClassCastException e5) {
                                    throw new WrongType(e5, "substring", 3, i1);
                                }
                            } catch (ClassCastException e6) {
                                throw new WrongType(e6, "substring", 2, i0);
                            }
                        } catch (ClassCastException e7) {
                            throw new WrongType(e7, "substring", 1, digs2);
                        }
                    } catch (ClassCastException e8) {
                        throw new WrongType(e8, "zero?", 1, exp);
                    }
                }
                Object obj2 = this.staticLink.precision;
                try {
                    if (numbers.isZero((Number) obj2)) {
                        return LList.list1(this.staticLink.alternate$Mnform != Boolean.FALSE ? "0." : Component.TYPEFACE_DEFAULT);
                    }
                    if (strip$Mn0s != Boolean.FALSE) {
                        boolean x3 = strings.isString$Eq(digs2, "");
                        if (x3) {
                            obj = LList.list1(Component.TYPEFACE_DEFAULT);
                        } else {
                            obj = x3 ? Boolean.TRUE : Boolean.FALSE;
                        }
                        x = obj;
                    } else {
                        x = strip$Mn0s;
                    }
                    if (x == Boolean.FALSE) {
                        Object min = numbers.min(this.staticLink.precision, AddOp.$Mn.apply2(printf.Lit17, exp));
                        try {
                            return LList.list3("0.", strings.makeString(((Number) min).intValue(), printf.Lit9), digs2);
                        } catch (ClassCastException e9) {
                            throw new WrongType(e9, "make-string", 1, min);
                        }
                    }
                    return x;
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "zero?", 1, obj2);
                }
            } catch (ClassCastException e11) {
                throw new WrongType(e11, "stdio:round-string", 0, digs);
            }
        }

        public Object lambda30formatReal$V(Object signed$Qu, Object sgn, Object digs, Object exp, Object[] argsArray) {
            Object obj;
            Object obj2;
            Object obj3;
            Object digs2;
            IntNum istrt;
            CharSequence fdigs;
            Pair list1;
            String str;
            Object obj4;
            LList rest = LList.makeList(argsArray, 0);
            if (!lists.isNull(rest)) {
                return append.append$V(new Object[]{lambda30formatReal$V(signed$Qu, sgn, digs, exp, new Object[0]), Scheme.apply.apply3(this.format$Mnreal, Boolean.TRUE, rest), printf.Lit63});
            }
            try {
                String str2 = characters.isChar$Eq(printf.Lit5, (Char) sgn) ? "-" : signed$Qu != Boolean.FALSE ? "+" : this.staticLink.blank != Boolean.FALSE ? " " : "";
                Object x = Scheme.isEqv.apply2(this.fc, printf.Lit13);
                try {
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    if (x == Boolean.FALSE ? Scheme.isEqv.apply2(this.fc, printf.Lit54) == Boolean.FALSE : x == Boolean.FALSE) {
                                                        Object x2 = Scheme.isEqv.apply2(this.fc, printf.Lit25);
                                                        if (x2 == Boolean.FALSE ? Scheme.isEqv.apply2(this.fc, printf.Lit26) != Boolean.FALSE : x2 != Boolean.FALSE) {
                                                            obj = lambda29f(digs, exp, Boolean.FALSE);
                                                        } else {
                                                            Object x3 = Scheme.isEqv.apply2(this.fc, printf.Lit55);
                                                            if (x3 == Boolean.FALSE ? Scheme.isEqv.apply2(this.fc, printf.Lit56) == Boolean.FALSE : x3 == Boolean.FALSE) {
                                                                if (Scheme.isEqv.apply2(this.fc, printf.Lit57) != Boolean.FALSE) {
                                                                    obj2 = "";
                                                                } else if (Scheme.isEqv.apply2(this.fc, printf.Lit58) != Boolean.FALSE) {
                                                                    obj2 = " ";
                                                                } else {
                                                                    obj = Values.empty;
                                                                }
                                                                try {
                                                                    Object i = numbers.isNegative(LangObjType.coerceRealNum(exp)) ? DivideOp.quotient.apply2(AddOp.$Mn.apply2(exp, printf.Lit61), printf.Lit61) : DivideOp.quotient.apply2(AddOp.$Mn.apply2(exp, printf.Lit7), printf.Lit61);
                                                                    Object apply3 = Scheme.numLss.apply3(printf.Lit17, AddOp.$Pl.apply2(i, printf.Lit48), Integer.valueOf(vectors.vectorLength(printf.Lit62)));
                                                                    try {
                                                                        boolean x4 = ((Boolean) apply3).booleanValue();
                                                                        Object uind = x4 ? i : x4 ? Boolean.TRUE : Boolean.FALSE;
                                                                        if (uind != Boolean.FALSE) {
                                                                            Object exp2 = AddOp.$Mn.apply2(exp, MultiplyOp.$St.apply2(printf.Lit61, uind));
                                                                            this.staticLink.precision = numbers.max(printf.Lit1, AddOp.$Mn.apply2(this.staticLink.precision, exp2));
                                                                            Object[] objArr = new Object[2];
                                                                            objArr[0] = lambda29f(digs, exp2, Boolean.FALSE);
                                                                            FVector fVector = printf.Lit62;
                                                                            Object apply2 = AddOp.$Pl.apply2(uind, printf.Lit48);
                                                                            try {
                                                                                objArr[1] = LList.list2(obj2, vectors.vectorRef(fVector, ((Number) apply2).intValue()));
                                                                                obj = append.append$V(objArr);
                                                                            } catch (ClassCastException e) {
                                                                                throw new WrongType(e, "vector-ref", 2, apply2);
                                                                            }
                                                                        }
                                                                    } catch (ClassCastException e2) {
                                                                        throw new WrongType(e2, "x", -2, apply3);
                                                                    }
                                                                } catch (ClassCastException e3) {
                                                                    throw new WrongType(e3, "negative?", 1, exp);
                                                                }
                                                            }
                                                            Object obj5 = this.staticLink.alternate$Mnform;
                                                            try {
                                                                int i2 = ((obj5 != Boolean.FALSE ? 1 : 0) + 1) & 1;
                                                                this.staticLink.alternate$Mnform = Boolean.FALSE;
                                                                if (Scheme.numLEq.apply3(AddOp.$Mn.apply2(printf.Lit7, this.staticLink.precision), exp, this.staticLink.precision) != Boolean.FALSE) {
                                                                    this.staticLink.precision = AddOp.$Mn.apply2(this.staticLink.precision, exp);
                                                                    obj = lambda29f(digs, exp, i2 != 0 ? Boolean.TRUE : Boolean.FALSE);
                                                                } else {
                                                                    this.staticLink.precision = AddOp.$Mn.apply2(this.staticLink.precision, printf.Lit7);
                                                                    obj3 = i2 != 0 ? Boolean.TRUE : Boolean.FALSE;
                                                                }
                                                            } catch (ClassCastException e4) {
                                                                throw new WrongType(e4, "strip-0s", -2, obj5);
                                                            }
                                                        }
                                                        return lists.cons(str2, obj);
                                                    }
                                                    obj3 = Boolean.FALSE;
                                                    LList.chain1(LList.chain1(LList.chain4(list1, str, fdigs, unicode.isCharUpperCase((Char) obj4) ? "E" : "e", numbers.isNegative(LangObjType.coerceRealNum(exp)) ? "-" : "+"), Scheme.numLss.apply3(printf.Lit60, exp, printf.Lit45) != Boolean.FALSE ? Component.TYPEFACE_DEFAULT : ""), numbers.number$To$String(numbers.abs((Number) exp), 10));
                                                    obj = list1;
                                                    return lists.cons(str2, obj);
                                                } catch (ClassCastException e5) {
                                                    throw new WrongType(e5, "abs", 1, exp);
                                                }
                                            } catch (ClassCastException e6) {
                                                throw new WrongType(e6, "negative?", 1, exp);
                                            }
                                        } catch (ClassCastException e7) {
                                            throw new WrongType(e7, "char-upper-case?", 1, obj4);
                                        }
                                        list1 = LList.list1(strings.substring((CharSequence) digs2, istrt.intValue(), istrt.intValue() + 1));
                                        boolean x5 = strings.isString$Eq(fdigs, "");
                                        str = (!x5 ? !x5 : this.staticLink.alternate$Mnform != Boolean.FALSE) ? "" : ".";
                                        obj4 = this.fc;
                                    } catch (ClassCastException e8) {
                                        throw new WrongType(e8, "substring", 2, istrt);
                                    }
                                } catch (ClassCastException e9) {
                                    throw new WrongType(e9, "substring", 1, digs2);
                                }
                                fdigs = strings.substring((CharSequence) digs2, istrt.intValue() + 1, strings.stringLength((CharSequence) digs2));
                                if (!numbers.isZero(istrt)) {
                                    exp = AddOp.$Mn.apply2(exp, printf.Lit7);
                                }
                            } catch (ClassCastException e10) {
                                throw new WrongType(e10, "string-length", 1, digs2);
                            }
                        } catch (ClassCastException e11) {
                            throw new WrongType(e11, "substring", 1, digs2);
                        }
                        istrt = characters.isChar$Eq(printf.Lit9, Char.make(strings.stringRef((CharSequence) digs2, 0))) ? printf.Lit7 : printf.Lit1;
                    } catch (ClassCastException e12) {
                        throw new WrongType(e12, "string-ref", 1, digs2);
                    }
                    CharSequence charSequence = (CharSequence) digs;
                    Object apply22 = AddOp.$Pl.apply2(printf.Lit7, this.staticLink.precision);
                    if (obj3 != Boolean.FALSE) {
                        obj3 = printf.Lit1;
                    }
                    digs2 = printf.stdio$ClRoundString(charSequence, apply22, obj3);
                } catch (ClassCastException e13) {
                    throw new WrongType(e13, "stdio:round-string", 0, digs);
                }
            } catch (ClassCastException e14) {
                throw new WrongType(e14, "char=?", 2, sgn);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 13:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 14:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                default:
                    return super.matchN(moduleMethod, objArr, callContext);
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            switch (moduleMethod.selector) {
                case 13:
                    Object obj = objArr[0];
                    Object obj2 = objArr[1];
                    Object obj3 = objArr[2];
                    Object obj4 = objArr[3];
                    int length = objArr.length - 4;
                    Object[] objArr2 = new Object[length];
                    while (true) {
                        length--;
                        if (length < 0) {
                            return lambda30formatReal$V(obj, obj2, obj3, obj4, objArr2);
                        }
                        objArr2[length] = objArr[length + 4];
                    }
                case 14:
                    Object obj5 = objArr[0];
                    Object obj6 = objArr[1];
                    Object obj7 = objArr[2];
                    int length2 = objArr.length - 3;
                    Object[] objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lambda31$V(obj5, obj6, obj7, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 3];
                    }
                default:
                    return super.applyN(moduleMethod, objArr);
            }
        }

        Object lambda31$V(Object sgn, Object digs, Object expon, Object[] argsArray) {
            LList imag = LList.makeList(argsArray, 0);
            return Scheme.apply.apply2(this.staticLink.pad, Scheme.apply.applyN(new Object[]{this.format$Mnreal, this.staticLink.signed, sgn, digs, expon, imag}));
        }
    }

    public static Object fprintf$V(Object port, Object format, Object[] argsArray) {
        frame12 frame12Var = new frame12();
        frame12Var.port = port;
        LList args = LList.makeList(argsArray, 0);
        frame12Var.cnt = Lit1;
        Scheme.apply.apply4(stdio$Cliprintf, frame12Var.lambda$Fn18, format, args);
        return frame12Var.cnt;
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame12 extends ModuleBody {
        Object cnt;
        final ModuleMethod lambda$Fn18;
        Object port;

        public frame12() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 20, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:546");
            this.lambda$Fn18 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 20 ? lambda32(obj) : super.apply1(moduleMethod, obj);
        }

        Boolean lambda32(Object x) {
            if (!strings.isString(x)) {
                this.cnt = AddOp.$Pl.apply2(printf.Lit7, this.cnt);
                ports.display(x, this.port);
                return Boolean.TRUE;
            }
            try {
                this.cnt = AddOp.$Pl.apply2(Integer.valueOf(strings.stringLength((CharSequence) x)), this.cnt);
                ports.display(x, this.port);
                return Boolean.TRUE;
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, x);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 20) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object printf$V(Object format, Object[] argsArray) {
        LList args = LList.makeList(argsArray, 0);
        return Scheme.apply.apply4(fprintf, ports.current$Mnoutput$Mnport.apply0(), format, args);
    }

    public static Object sprintf$V(Object str, Object format, Object[] argsArray) {
        Object error$V;
        frame13 frame13Var = new frame13();
        frame13Var.str = str;
        LList args = LList.makeList(argsArray, 0);
        frame13Var.cnt = Lit1;
        if (strings.isString(frame13Var.str)) {
            error$V = frame13Var.str;
        } else if (numbers.isNumber(frame13Var.str)) {
            Object obj = frame13Var.str;
            try {
                error$V = strings.makeString(((Number) obj).intValue());
            } catch (ClassCastException e) {
                throw new WrongType(e, "make-string", 1, obj);
            }
        } else if (frame13Var.str == Boolean.FALSE) {
            error$V = strings.makeString(100);
        } else {
            error$V = misc.error$V(Lit68, new Object[]{"first argument not understood", frame13Var.str});
        }
        frame13Var.s = error$V;
        Object obj2 = frame13Var.s;
        try {
            frame13Var.end = Integer.valueOf(strings.stringLength((CharSequence) obj2));
            Scheme.apply.apply4(stdio$Cliprintf, frame13Var.lambda$Fn19, format, args);
            if (strings.isString(frame13Var.str)) {
                return frame13Var.cnt;
            }
            if (Scheme.isEqv.apply2(frame13Var.end, frame13Var.cnt) != Boolean.FALSE) {
                return frame13Var.s;
            }
            Object obj3 = frame13Var.s;
            try {
                CharSequence charSequence = (CharSequence) obj3;
                Object obj4 = frame13Var.cnt;
                try {
                    return strings.substring(charSequence, 0, ((Number) obj4).intValue());
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "substring", 3, obj4);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "substring", 1, obj3);
            }
        } catch (ClassCastException e4) {
            throw new WrongType(e4, "string-length", 1, obj2);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 24:
                Object obj = objArr[0];
                Object obj2 = objArr[1];
                int length = objArr.length - 2;
                Object[] objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stdio$ClIprintf$V(obj, obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 25:
                Object obj3 = objArr[0];
                Object obj4 = objArr[1];
                int length2 = objArr.length - 2;
                Object[] objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return fprintf$V(obj3, obj4, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 2];
                }
            case 26:
                Object obj5 = objArr[0];
                int length3 = objArr.length - 1;
                Object[] objArr4 = new Object[length3];
                while (true) {
                    length3--;
                    if (length3 < 0) {
                        return printf$V(obj5, objArr4);
                    }
                    objArr4[length3] = objArr[length3 + 1];
                }
            case 27:
                Object obj6 = objArr[0];
                Object obj7 = objArr[1];
                int length4 = objArr.length - 2;
                Object[] objArr5 = new Object[length4];
                while (true) {
                    length4--;
                    if (length4 < 0) {
                        return sprintf$V(obj6, obj7, objArr5);
                    }
                    objArr5[length4] = objArr[length4 + 2];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    /* compiled from: printf.scm */
    /* loaded from: classes.dex */
    public class frame13 extends ModuleBody {
        Object cnt;
        Object end;
        final ModuleMethod lambda$Fn19;
        Object s;
        Object str;

        public frame13() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 21, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/printf.scm:564");
            this.lambda$Fn19 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 21 ? lambda33(obj) ? Boolean.TRUE : Boolean.FALSE : super.apply1(moduleMethod, obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        boolean lambda33(Object x) {
            char charValue;
            if (!strings.isString(x)) {
                if ((this.str != Boolean.FALSE ? Scheme.numGEq.apply2(this.cnt, this.end) : this.str) == Boolean.FALSE) {
                    Object obj = this.str;
                    try {
                        int i = ((obj != Boolean.FALSE ? 1 : 0) + 1) & 1;
                        if (i == 0 ? i != 0 : Scheme.numGEq.apply2(this.cnt, this.end) != Boolean.FALSE) {
                            this.s = strings.stringAppend(this.s, strings.makeString(100));
                            Object obj2 = this.s;
                            try {
                                this.end = Integer.valueOf(strings.stringLength((CharSequence) obj2));
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-length", 1, obj2);
                            }
                        }
                        Object obj3 = this.s;
                        try {
                            CharSeq charSeq = (CharSeq) obj3;
                            Object obj4 = this.cnt;
                            try {
                                int intValue = ((Number) obj4).intValue();
                                if (characters.isChar(x)) {
                                    try {
                                        charValue = ((Char) x).charValue();
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "string-set!", 3, x);
                                    }
                                } else {
                                    charValue = '?';
                                }
                                charSeq.setCharAt(intValue, charValue);
                                this.cnt = AddOp.$Pl.apply2(this.cnt, printf.Lit7);
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-set!", 2, obj4);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-set!", 1, obj3);
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "x", -2, obj);
                    }
                }
            } else {
                if (this.str == Boolean.FALSE) {
                    try {
                        if (Scheme.numGEq.apply2(AddOp.$Mn.apply2(this.end, this.cnt), Integer.valueOf(strings.stringLength((CharSequence) x))) == Boolean.FALSE) {
                            Object[] objArr = new Object[2];
                            Object obj5 = this.s;
                            try {
                                CharSequence charSequence = (CharSequence) obj5;
                                Object obj6 = this.cnt;
                                try {
                                    objArr[0] = strings.substring(charSequence, 0, ((Number) obj6).intValue());
                                    objArr[1] = x;
                                    this.s = strings.stringAppend(objArr);
                                    Object obj7 = this.s;
                                    try {
                                        this.cnt = Integer.valueOf(strings.stringLength((CharSequence) obj7));
                                        this.end = this.cnt;
                                    } catch (ClassCastException e6) {
                                        throw new WrongType(e6, "string-length", 1, obj7);
                                    }
                                } catch (ClassCastException e7) {
                                    throw new WrongType(e7, "substring", 3, obj6);
                                }
                            } catch (ClassCastException e8) {
                                throw new WrongType(e8, "substring", 1, obj5);
                            }
                        }
                    } catch (ClassCastException e9) {
                        throw new WrongType(e9, "string-length", 1, x);
                    }
                }
                Object[] objArr2 = new Object[2];
                try {
                    objArr2[0] = Integer.valueOf(strings.stringLength((CharSequence) x));
                    objArr2[1] = AddOp.$Mn.apply2(this.end, this.cnt);
                    Object lend = numbers.min(objArr2);
                    IntNum intNum = printf.Lit1;
                    while (Scheme.numGEq.apply2(intNum, lend) == Boolean.FALSE) {
                        Object obj8 = this.s;
                        try {
                            CharSeq charSeq2 = (CharSeq) obj8;
                            Object obj9 = this.cnt;
                            try {
                                try {
                                    try {
                                        charSeq2.setCharAt(((Number) obj9).intValue(), strings.stringRef((CharSequence) x, intNum.intValue()));
                                        this.cnt = AddOp.$Pl.apply2(this.cnt, printf.Lit7);
                                        Object i2 = AddOp.$Pl.apply2(intNum, printf.Lit7);
                                        intNum = i2;
                                    } catch (ClassCastException e10) {
                                        throw new WrongType(e10, "string-ref", 2, intNum);
                                    }
                                } catch (ClassCastException e11) {
                                    throw new WrongType(e11, "string-ref", 1, x);
                                }
                            } catch (ClassCastException e12) {
                                throw new WrongType(e12, "string-set!", 2, obj9);
                            }
                        } catch (ClassCastException e13) {
                            throw new WrongType(e13, "string-set!", 1, obj8);
                        }
                    }
                } catch (ClassCastException e14) {
                    throw new WrongType(e14, "string-length", 1, x);
                }
            }
            return ((this.str != Boolean.FALSE ? Scheme.numGEq.apply2(this.cnt, this.end) != Boolean.FALSE ? 1 : 0 : this.str != Boolean.FALSE ? 1 : 0) + 1) & 1;
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 21) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }
}
