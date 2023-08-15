package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Format;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;

/* compiled from: genwrite.scm */
/* loaded from: classes.dex */
public class genwrite extends ModuleBody {
    public static final ModuleMethod generic$Mnwrite;
    public static final ModuleMethod reverse$Mnstring$Mnappend;
    static final SimpleSymbol Lit35 = (SimpleSymbol) new SimpleSymbol("reverse-string-append").readResolve();
    static final SimpleSymbol Lit34 = (SimpleSymbol) new SimpleSymbol("generic-write").readResolve();
    static final SimpleSymbol Lit33 = (SimpleSymbol) new SimpleSymbol(LispLanguage.unquotesplicing_sym).readResolve();
    static final SimpleSymbol Lit32 = (SimpleSymbol) new SimpleSymbol(LispLanguage.unquote_sym).readResolve();
    static final SimpleSymbol Lit31 = (SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve();
    static final SimpleSymbol Lit30 = (SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve();
    static final SimpleSymbol Lit29 = (SimpleSymbol) new SimpleSymbol("pp-DO").readResolve();
    static final SimpleSymbol Lit28 = (SimpleSymbol) new SimpleSymbol("pp-BEGIN").readResolve();
    static final SimpleSymbol Lit27 = (SimpleSymbol) new SimpleSymbol("pp-LET").readResolve();
    static final SimpleSymbol Lit26 = (SimpleSymbol) new SimpleSymbol("pp-AND").readResolve();
    static final SimpleSymbol Lit25 = (SimpleSymbol) new SimpleSymbol("pp-CASE").readResolve();
    static final SimpleSymbol Lit24 = (SimpleSymbol) new SimpleSymbol("pp-COND").readResolve();
    static final SimpleSymbol Lit23 = (SimpleSymbol) new SimpleSymbol("pp-IF").readResolve();
    static final SimpleSymbol Lit22 = (SimpleSymbol) new SimpleSymbol("pp-LAMBDA").readResolve();
    static final SimpleSymbol Lit21 = (SimpleSymbol) new SimpleSymbol("pp-expr-list").readResolve();
    static final SimpleSymbol Lit20 = (SimpleSymbol) new SimpleSymbol("pp-expr").readResolve();
    static final IntNum Lit19 = IntNum.make(2);
    static final IntNum Lit18 = IntNum.make(50);
    static final IntNum Lit17 = IntNum.make(1);
    static final IntNum Lit16 = IntNum.make(8);
    static final IntNum Lit15 = IntNum.make(7);
    static final SimpleSymbol Lit14 = (SimpleSymbol) new SimpleSymbol("do").readResolve();
    static final SimpleSymbol Lit13 = (SimpleSymbol) new SimpleSymbol("begin").readResolve();
    static final SimpleSymbol Lit12 = (SimpleSymbol) new SimpleSymbol("let").readResolve();
    static final SimpleSymbol Lit11 = (SimpleSymbol) new SimpleSymbol("or").readResolve();
    static final SimpleSymbol Lit10 = (SimpleSymbol) new SimpleSymbol("and").readResolve();
    static final SimpleSymbol Lit9 = (SimpleSymbol) new SimpleSymbol("case").readResolve();
    static final SimpleSymbol Lit8 = (SimpleSymbol) new SimpleSymbol("cond").readResolve();
    static final SimpleSymbol Lit7 = (SimpleSymbol) new SimpleSymbol("set!").readResolve();
    static final SimpleSymbol Lit6 = (SimpleSymbol) new SimpleSymbol("if").readResolve();
    static final SimpleSymbol Lit5 = (SimpleSymbol) new SimpleSymbol("define").readResolve();
    static final SimpleSymbol Lit4 = (SimpleSymbol) new SimpleSymbol("letrec").readResolve();
    static final SimpleSymbol Lit3 = (SimpleSymbol) new SimpleSymbol("let*").readResolve();
    static final SimpleSymbol Lit2 = (SimpleSymbol) new SimpleSymbol("lambda").readResolve();
    static final IntNum Lit1 = IntNum.make(0);
    static final Char Lit0 = Char.make(10);
    public static final genwrite $instance = new genwrite();

    static {
        genwrite genwriteVar = $instance;
        generic$Mnwrite = new ModuleMethod(genwriteVar, 12, Lit34, 16388);
        reverse$Mnstring$Mnappend = new ModuleMethod(genwriteVar, 13, Lit35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public genwrite() {
        ModuleInfo.register(this);
    }

    public static Object genericWrite(Object obj, Object isDisplay, Object width, Object output) {
        frame closureEnv = new frame();
        closureEnv.display$Qu = isDisplay;
        closureEnv.width = width;
        closureEnv.output = output;
        if (closureEnv.width != Boolean.FALSE) {
            CharSequence makeString = strings.makeString(1, Lit0);
            IntNum intNum = Lit1;
            frame0 frame0Var = new frame0();
            frame0Var.staticLink = closureEnv;
            Procedure procedure = frame0Var.pp$Mnexpr;
            Procedure procedure2 = frame0Var.pp$Mnexpr$Mnlist;
            Procedure procedure3 = frame0Var.pp$MnLAMBDA;
            Procedure procedure4 = frame0Var.pp$MnIF;
            Procedure procedure5 = frame0Var.pp$MnCOND;
            Procedure procedure6 = frame0Var.pp$MnCASE;
            Procedure procedure7 = frame0Var.pp$MnAND;
            Procedure procedure8 = frame0Var.pp$MnLET;
            Procedure procedure9 = frame0Var.pp$MnBEGIN;
            frame0Var.pp$MnDO = frame0Var.pp$MnDO;
            frame0Var.pp$MnBEGIN = procedure9;
            frame0Var.pp$MnLET = procedure8;
            frame0Var.pp$MnAND = procedure7;
            frame0Var.pp$MnCASE = procedure6;
            frame0Var.pp$MnCOND = procedure5;
            frame0Var.pp$MnIF = procedure4;
            frame0Var.pp$MnLAMBDA = procedure3;
            frame0Var.pp$Mnexpr$Mnlist = procedure2;
            frame0Var.pp$Mnexpr = procedure;
            return closureEnv.lambda4out(makeString, frame0Var.lambda7pr(obj, intNum, Lit1, frame0Var.pp$Mnexpr));
        }
        return closureEnv.lambda5wr(obj, Lit1);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        return moduleMethod.selector == 12 ? genericWrite(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
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

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    /* compiled from: genwrite.scm */
    /* loaded from: classes.dex */
    public class frame extends ModuleBody {
        Object display$Qu;
        Object output;
        Object width;

        public static Object lambda1isReadMacro(Object l) {
            Object x;
            Object x2;
            Object head = lists.car.apply1(l);
            Object tail = lists.cdr.apply1(l);
            Object x3 = Scheme.isEqv.apply2(head, genwrite.Lit30);
            if (x3 == Boolean.FALSE ? (x = Scheme.isEqv.apply2(head, genwrite.Lit31)) == Boolean.FALSE ? (x2 = Scheme.isEqv.apply2(head, genwrite.Lit32)) == Boolean.FALSE ? Scheme.isEqv.apply2(head, genwrite.Lit33) == Boolean.FALSE : x2 == Boolean.FALSE : x == Boolean.FALSE : x3 == Boolean.FALSE) {
                return Boolean.FALSE;
            }
            boolean x4 = lists.isPair(tail);
            return x4 ? lists.isNull(lists.cdr.apply1(tail)) ? Boolean.TRUE : Boolean.FALSE : x4 ? Boolean.TRUE : Boolean.FALSE;
        }

        public static Object lambda2readMacroBody(Object l) {
            return lists.cadr.apply1(l);
        }

        public static Object lambda3readMacroPrefix(Object l) {
            Object head = lists.car.apply1(l);
            lists.cdr.apply1(l);
            return Scheme.isEqv.apply2(head, genwrite.Lit30) != Boolean.FALSE ? "'" : Scheme.isEqv.apply2(head, genwrite.Lit31) != Boolean.FALSE ? "`" : Scheme.isEqv.apply2(head, genwrite.Lit32) != Boolean.FALSE ? "," : Scheme.isEqv.apply2(head, genwrite.Lit33) != Boolean.FALSE ? ",@" : Values.empty;
        }

        public Object lambda4out(Object str, Object col) {
            if (col != Boolean.FALSE) {
                Object x = Scheme.applyToArgs.apply2(this.output, str);
                if (x != Boolean.FALSE) {
                    try {
                        return AddOp.$Pl.apply2(col, Integer.valueOf(strings.stringLength((CharSequence) str)));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-length", 1, str);
                    }
                }
                return x;
            }
            return col;
        }

        public Object lambda5wr(Object obj, Object col) {
            if (lists.isPair(obj)) {
                if (lambda1isReadMacro(obj) != Boolean.FALSE) {
                    return lambda5wr(lambda2readMacroBody(obj), lambda4out(lambda3readMacroPrefix(obj), col));
                }
                obj = obj;
            } else if (!lists.isNull(obj)) {
                if (vectors.isVector(obj)) {
                    try {
                        LList vector$To$List = vectors.vector$To$List((FVector) obj);
                        col = lambda4out("#", col);
                        obj = vector$To$List;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "vector->list", 1, obj);
                    }
                } else {
                    Object[] objArr = new Object[2];
                    objArr[0] = this.display$Qu != Boolean.FALSE ? "~a" : "~s";
                    objArr[1] = obj;
                    return lambda4out(Format.formatToString(0, objArr), col);
                }
            }
            if (lists.isPair(obj)) {
                Object l = lists.cdr.apply1(obj);
                if (col != Boolean.FALSE) {
                    col = lambda5wr(lists.car.apply1(obj), lambda4out("(", col));
                }
                while (col != Boolean.FALSE) {
                    if (!lists.isPair(l)) {
                        if (lists.isNull(l)) {
                            return lambda4out(")", col);
                        }
                        return lambda4out(")", lambda5wr(l, lambda4out(" . ", col)));
                    }
                    Object l2 = lists.cdr.apply1(l);
                    col = lambda5wr(lists.car.apply1(l), lambda4out(" ", col));
                    l = l2;
                }
                return col;
            }
            return lambda4out("()", col);
        }
    }

    /* compiled from: genwrite.scm */
    /* loaded from: classes.dex */
    public class frame0 extends ModuleBody {
        frame staticLink;
        Procedure pp$Mnexpr = new ModuleMethod(this, 2, genwrite.Lit20, 12291);
        Procedure pp$Mnexpr$Mnlist = new ModuleMethod(this, 3, genwrite.Lit21, 12291);
        Procedure pp$MnLAMBDA = new ModuleMethod(this, 4, genwrite.Lit22, 12291);
        Procedure pp$MnIF = new ModuleMethod(this, 5, genwrite.Lit23, 12291);
        Procedure pp$MnCOND = new ModuleMethod(this, 6, genwrite.Lit24, 12291);
        Procedure pp$MnCASE = new ModuleMethod(this, 7, genwrite.Lit25, 12291);
        Procedure pp$MnAND = new ModuleMethod(this, 8, genwrite.Lit26, 12291);
        Procedure pp$MnLET = new ModuleMethod(this, 9, genwrite.Lit27, 12291);
        Procedure pp$MnBEGIN = new ModuleMethod(this, 10, genwrite.Lit28, 12291);
        Procedure pp$MnDO = new ModuleMethod(this, 11, genwrite.Lit29, 12291);

        public Object lambda6indent(Object to, Object col) {
            Object n;
            if (col != Boolean.FALSE) {
                if (Scheme.numLss.apply2(to, col) != Boolean.FALSE) {
                    Object x = this.staticLink.lambda4out(strings.makeString(1, genwrite.Lit0), col);
                    if (x != Boolean.FALSE) {
                        col = genwrite.Lit1;
                        n = to;
                    } else {
                        return x;
                    }
                } else {
                    n = AddOp.$Mn.apply2(to, col);
                }
                while (true) {
                    Object obj = n;
                    if (Scheme.numGrt.apply2(obj, genwrite.Lit1) == Boolean.FALSE) {
                        break;
                    } else if (Scheme.numGrt.apply2(obj, genwrite.Lit15) != Boolean.FALSE) {
                        n = AddOp.$Mn.apply2(obj, genwrite.Lit16);
                        col = this.staticLink.lambda4out("        ", col);
                    } else {
                        try {
                            col = this.staticLink.lambda4out(strings.substring("        ", 0, ((Number) obj).intValue()), col);
                            break;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "substring", 3, obj);
                        }
                    }
                }
                return col;
            }
            return col;
        }

        public Object lambda7pr(Object obj, Object col, Object extra, Object pp$Mnpair) {
            frame1 frame1Var = new frame1();
            frame1Var.staticLink = this;
            boolean x = lists.isPair(obj);
            if (!x ? vectors.isVector(obj) : x) {
                LList lList = LList.Empty;
                frame1Var.left = numbers.min(AddOp.$Pl.apply2(AddOp.$Mn.apply2(AddOp.$Mn.apply2(this.staticLink.width, col), extra), genwrite.Lit17), genwrite.Lit18);
                frame1Var.result = lList;
                genwrite.genericWrite(obj, this.staticLink.display$Qu, Boolean.FALSE, frame1Var.lambda$Fn1);
                if (Scheme.numGrt.apply2(frame1Var.left, genwrite.Lit1) != Boolean.FALSE) {
                    return this.staticLink.lambda4out(genwrite.reverseStringAppend(frame1Var.result), col);
                }
                if (lists.isPair(obj)) {
                    return Scheme.applyToArgs.apply4(pp$Mnpair, obj, col, extra);
                }
                try {
                    return lambda10ppList(vectors.vector$To$List((FVector) obj), this.staticLink.lambda4out("#", col), extra, this.pp$Mnexpr);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "vector->list", 1, obj);
                }
            }
            return this.staticLink.lambda5wr(obj, col);
        }

        public Object lambda8ppExpr(Object expr, Object col, Object extra) {
            Object x;
            Object x2;
            Object proc;
            if (frame.lambda1isReadMacro(expr) != Boolean.FALSE) {
                return lambda7pr(frame.lambda2readMacroBody(expr), this.staticLink.lambda4out(frame.lambda3readMacroPrefix(expr), col), extra, this.pp$Mnexpr);
            }
            Object head = lists.car.apply1(expr);
            if (misc.isSymbol(head)) {
                Object x3 = Scheme.isEqv.apply2(head, genwrite.Lit2);
                if (x3 == Boolean.FALSE ? (x = Scheme.isEqv.apply2(head, genwrite.Lit3)) == Boolean.FALSE ? (x2 = Scheme.isEqv.apply2(head, genwrite.Lit4)) == Boolean.FALSE ? Scheme.isEqv.apply2(head, genwrite.Lit5) == Boolean.FALSE : x2 == Boolean.FALSE : x == Boolean.FALSE : x3 == Boolean.FALSE) {
                    Object x4 = Scheme.isEqv.apply2(head, genwrite.Lit6);
                    if (x4 == Boolean.FALSE ? Scheme.isEqv.apply2(head, genwrite.Lit7) != Boolean.FALSE : x4 != Boolean.FALSE) {
                        proc = this.pp$MnIF;
                    } else if (Scheme.isEqv.apply2(head, genwrite.Lit8) != Boolean.FALSE) {
                        proc = this.pp$MnCOND;
                    } else if (Scheme.isEqv.apply2(head, genwrite.Lit9) != Boolean.FALSE) {
                        proc = this.pp$MnCASE;
                    } else {
                        Object x5 = Scheme.isEqv.apply2(head, genwrite.Lit10);
                        proc = (x5 == Boolean.FALSE ? Scheme.isEqv.apply2(head, genwrite.Lit11) != Boolean.FALSE : x5 != Boolean.FALSE) ? this.pp$MnAND : Scheme.isEqv.apply2(head, genwrite.Lit12) != Boolean.FALSE ? this.pp$MnLET : Scheme.isEqv.apply2(head, genwrite.Lit13) != Boolean.FALSE ? this.pp$MnBEGIN : Scheme.isEqv.apply2(head, genwrite.Lit14) != Boolean.FALSE ? this.pp$MnDO : Boolean.FALSE;
                    }
                } else {
                    proc = this.pp$MnLAMBDA;
                }
                if (proc != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply4(proc, expr, col, extra);
                }
                try {
                    return strings.stringLength(((Symbol) head).toString()) > 5 ? lambda12ppGeneral(expr, col, extra, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, this.pp$Mnexpr) : lambda9ppCall(expr, col, extra, this.pp$Mnexpr);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "symbol->string", 1, head);
                }
            }
            return lambda10ppList(expr, col, extra, this.pp$Mnexpr);
        }

        @Override // gnu.expr.ModuleBody
        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 2:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 3:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 4:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 5:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 6:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 7:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 8:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 9:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 10:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 11:
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

        public Object lambda9ppCall(Object expr, Object col, Object extra, Object pp$Mnitem) {
            Object col$St = this.staticLink.lambda5wr(lists.car.apply1(expr), this.staticLink.lambda4out("(", col));
            if (col == Boolean.FALSE) {
                return col;
            }
            return lambda11ppDown(lists.cdr.apply1(expr), col$St, AddOp.$Pl.apply2(col$St, genwrite.Lit17), extra, pp$Mnitem);
        }

        public Object lambda10ppList(Object l, Object col, Object extra, Object pp$Mnitem) {
            Object col2 = this.staticLink.lambda4out("(", col);
            return lambda11ppDown(l, col2, col2, extra, pp$Mnitem);
        }

        public Object lambda11ppDown(Object l, Object obj, Object col2, Object obj2, Object pp$Mnitem) {
            while (obj != Boolean.FALSE) {
                if (lists.isPair(l)) {
                    Object rest = lists.cdr.apply1(l);
                    Object extra = lists.isNull(rest) ? AddOp.$Pl.apply2(obj2, genwrite.Lit17) : genwrite.Lit1;
                    Object col = lambda7pr(lists.car.apply1(l), lambda6indent(col2, obj), extra, pp$Mnitem);
                    l = rest;
                    obj = col;
                } else if (lists.isNull(l)) {
                    return this.staticLink.lambda4out(")", obj);
                } else {
                    return this.staticLink.lambda4out(")", lambda7pr(l, lambda6indent(col2, this.staticLink.lambda4out(".", lambda6indent(col2, obj))), AddOp.$Pl.apply2(obj2, genwrite.Lit17), pp$Mnitem));
                }
            }
            return obj;
        }

        public Object lambda12ppGeneral(Object expr, Object col, Object extra, Object named$Qu, Object pp$Mn1, Object pp$Mn2, Object pp$Mn3) {
            Object apply2;
            Object apply22;
            Object col$St$St;
            Object obj;
            Object obj2;
            Object obj3;
            Object head = lists.car.apply1(expr);
            Object rest = lists.cdr.apply1(expr);
            Object col$St = this.staticLink.lambda5wr(head, this.staticLink.lambda4out("(", col));
            if (named$Qu == Boolean.FALSE ? named$Qu != Boolean.FALSE : lists.isPair(rest)) {
                Object name = lists.car.apply1(rest);
                rest = lists.cdr.apply1(rest);
                col$St$St = this.staticLink.lambda5wr(name, this.staticLink.lambda4out(" ", col$St));
                apply2 = AddOp.$Pl.apply2(col, genwrite.Lit19);
                apply22 = AddOp.$Pl.apply2(col$St$St, genwrite.Lit17);
            } else {
                apply2 = AddOp.$Pl.apply2(col, genwrite.Lit19);
                apply22 = AddOp.$Pl.apply2(col$St, genwrite.Lit17);
                col$St$St = col$St;
            }
            if (pp$Mn1 == Boolean.FALSE ? pp$Mn1 != Boolean.FALSE : lists.isPair(rest)) {
                Object val1 = lists.car.apply1(rest);
                rest = lists.cdr.apply1(rest);
                col$St$St = lambda7pr(val1, lambda6indent(apply22, col$St$St), lists.isNull(rest) ? AddOp.$Pl.apply2(extra, genwrite.Lit17) : genwrite.Lit1, pp$Mn1);
            }
            if (pp$Mn2 == Boolean.FALSE ? pp$Mn2 != Boolean.FALSE : lists.isPair(rest)) {
                Object val12 = lists.car.apply1(rest);
                Object rest2 = lists.cdr.apply1(rest);
                obj = lambda7pr(val12, lambda6indent(apply22, col$St$St), lists.isNull(rest2) ? AddOp.$Pl.apply2(extra, genwrite.Lit17) : genwrite.Lit1, pp$Mn2);
                obj2 = apply2;
                obj3 = rest2;
            } else {
                obj = col$St$St;
                obj2 = apply2;
                obj3 = rest;
            }
            return lambda11ppDown(obj3, obj, obj2, extra, pp$Mn3);
        }

        public Object lambda13ppExprList(Object l, Object col, Object extra) {
            return lambda10ppList(l, col, extra, this.pp$Mnexpr);
        }

        public Object lambda14pp$MnLAMBDA(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr$Mnlist, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda15pp$MnIF(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda16pp$MnCOND(Object expr, Object col, Object extra) {
            return lambda9ppCall(expr, col, extra, this.pp$Mnexpr$Mnlist);
        }

        public Object lambda17pp$MnCASE(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr, Boolean.FALSE, this.pp$Mnexpr$Mnlist);
        }

        public Object lambda18pp$MnAND(Object expr, Object col, Object extra) {
            return lambda9ppCall(expr, col, extra, this.pp$Mnexpr);
        }

        public Object lambda19pp$MnLET(Object expr, Object col, Object extra) {
            Object rest = lists.cdr.apply1(expr);
            boolean x = lists.isPair(rest);
            boolean named$Qu = x ? misc.isSymbol(lists.car.apply1(rest)) : x;
            return lambda12ppGeneral(expr, col, extra, named$Qu ? Boolean.TRUE : Boolean.FALSE, this.pp$Mnexpr$Mnlist, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda20pp$MnBEGIN(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, this.pp$Mnexpr);
        }

        @Override // gnu.expr.ModuleBody
        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            switch (moduleMethod.selector) {
                case 2:
                    return lambda8ppExpr(obj, obj2, obj3);
                case 3:
                    return lambda13ppExprList(obj, obj2, obj3);
                case 4:
                    return lambda14pp$MnLAMBDA(obj, obj2, obj3);
                case 5:
                    return lambda15pp$MnIF(obj, obj2, obj3);
                case 6:
                    return lambda16pp$MnCOND(obj, obj2, obj3);
                case 7:
                    return lambda17pp$MnCASE(obj, obj2, obj3);
                case 8:
                    return lambda18pp$MnAND(obj, obj2, obj3);
                case 9:
                    return lambda19pp$MnLET(obj, obj2, obj3);
                case 10:
                    return lambda20pp$MnBEGIN(obj, obj2, obj3);
                case 11:
                    return lambda21pp$MnDO(obj, obj2, obj3);
                default:
                    return super.apply3(moduleMethod, obj, obj2, obj3);
            }
        }

        public Object lambda21pp$MnDO(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr$Mnlist, this.pp$Mnexpr$Mnlist, this.pp$Mnexpr);
        }
    }

    /* compiled from: genwrite.scm */
    /* loaded from: classes.dex */
    public class frame1 extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        Object left;
        Object result;
        frame0 staticLink;

        public frame1() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/genwrite.scm:72");
            this.lambda$Fn1 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 1 ? lambda22(obj) ? Boolean.TRUE : Boolean.FALSE : super.apply1(moduleMethod, obj);
        }

        boolean lambda22(Object str) {
            this.result = lists.cons(str, this.result);
            try {
                this.left = AddOp.$Mn.apply2(this.left, Integer.valueOf(strings.stringLength((CharSequence) str)));
                return ((Boolean) Scheme.numGrt.apply2(this.left, genwrite.Lit1)).booleanValue();
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, str);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector == 1) {
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            }
            return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object reverseStringAppend(Object l) {
        return lambda23revStringAppend(l, Lit1);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        return moduleMethod.selector == 13 ? reverseStringAppend(obj) : super.apply1(moduleMethod, obj);
    }

    @Override // gnu.expr.ModuleBody
    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        if (moduleMethod.selector == 13) {
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
        return super.match1(moduleMethod, obj, callContext);
    }

    public static Object lambda23revStringAppend(Object l, Object i) {
        if (lists.isPair(l)) {
            Object str = lists.car.apply1(l);
            try {
                int len = strings.stringLength((CharSequence) str);
                Object result = lambda23revStringAppend(lists.cdr.apply1(l), AddOp.$Pl.apply2(i, Integer.valueOf(len)));
                Object obj = Lit1;
                try {
                    Object apply2 = AddOp.$Mn.apply2(AddOp.$Mn.apply2(Integer.valueOf(strings.stringLength((CharSequence) result)), i), Integer.valueOf(len));
                    while (Scheme.numLss.apply2(obj, Integer.valueOf(len)) != Boolean.FALSE) {
                        try {
                            try {
                                try {
                                    try {
                                        ((CharSeq) result).setCharAt(((Number) apply2).intValue(), strings.stringRef((CharSequence) str, ((Number) obj).intValue()));
                                        obj = AddOp.$Pl.apply2(obj, Lit17);
                                        Object k = AddOp.$Pl.apply2(apply2, Lit17);
                                        apply2 = k;
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "string-ref", 2, obj);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "string-ref", 1, str);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-set!", 2, apply2);
                            }
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "string-set!", 1, result);
                        }
                    }
                    return result;
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-length", 1, result);
                }
            } catch (ClassCastException e6) {
                throw new WrongType(e6, "string-length", 1, str);
            }
        }
        try {
            return strings.makeString(((Number) i).intValue());
        } catch (ClassCastException e7) {
            throw new WrongType(e7, "make-string", 1, i);
        }
    }
}
