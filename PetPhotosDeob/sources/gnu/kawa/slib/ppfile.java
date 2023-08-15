package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.text.Char;
import gnu.text.Path;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.ports;
import kawa.lib.rnrs.unicode;
import kawa.standard.Scheme;
import kawa.standard.readchar;

/* compiled from: ppfile.scm */
/* loaded from: classes.dex */
public class ppfile extends ModuleBody {
    static final ModuleMethod lambda$Fn3;
    public static final ModuleMethod pprint$Mnfile;
    public static final ModuleMethod pprint$Mnfilter$Mnfile;
    static final SimpleSymbol Lit3 = (SimpleSymbol) new SimpleSymbol("pprint-file").readResolve();
    static final SimpleSymbol Lit2 = (SimpleSymbol) new SimpleSymbol("pprint-filter-file").readResolve();
    static final Char Lit1 = Char.make(10);
    static final Char Lit0 = Char.make(59);
    public static final ppfile $instance = new ppfile();

    static {
        ppfile ppfileVar = $instance;
        pprint$Mnfilter$Mnfile = new ModuleMethod(ppfileVar, 3, Lit2, -4094);
        ModuleMethod moduleMethod = new ModuleMethod(ppfileVar, 4, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/ppfile.scm:70");
        lambda$Fn3 = moduleMethod;
        pprint$Mnfile = new ModuleMethod(ppfileVar, 5, Lit3, 8193);
        $instance.run();
    }

    public ppfile() {
        ModuleInfo.register(this);
    }

    public static Object pprintFile(Object obj) {
        return pprintFile(obj, ports.current$Mnoutput$Mnport.apply0());
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    public static Object pprintFilterFile$V(Object inport, Object filter, Object[] argsArray) {
        frame frameVar = new frame();
        frameVar.filter = filter;
        frameVar.optarg = LList.makeList(argsArray, 0);
        Procedure fun = frameVar.lambda$Fn1;
        if (ports.isInputPort(inport)) {
            return frameVar.lambda1(inport);
        }
        try {
            return ports.callWithInputFile(Path.valueOf(inport), fun);
        } catch (ClassCastException e) {
            throw new WrongType(e, "call-with-input-file", 1, inport);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        if (moduleMethod.selector != 3) {
            return super.applyN(moduleMethod, objArr);
        }
        Object obj = objArr[0];
        Object obj2 = objArr[1];
        int length = objArr.length - 2;
        Object[] objArr2 = new Object[length];
        while (true) {
            length--;
            if (length < 0) {
                return pprintFilterFile$V(obj, obj2, objArr2);
            }
            objArr2[length] = objArr[length + 2];
        }
    }

    @Override // gnu.expr.ModuleBody
    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        if (moduleMethod.selector == 3) {
            callContext.values = objArr;
            callContext.proc = moduleMethod;
            callContext.pc = 5;
            return 0;
        }
        return super.matchN(moduleMethod, objArr, callContext);
    }

    /* compiled from: ppfile.scm */
    /* loaded from: classes.dex */
    public class frame extends ModuleBody {
        Object filter;
        final ModuleMethod lambda$Fn1;
        LList optarg;

        public frame() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/ppfile.scm:27");
            this.lambda$Fn1 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 2 ? lambda1(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda1(Object port) {
            frame0 frame0Var = new frame0();
            frame0Var.staticLink = this;
            frame0Var.port = port;
            Procedure fun = frame0Var.lambda$Fn2;
            Object outport = lists.isNull(this.optarg) ? ports.current$Mnoutput$Mnport.apply0() : lists.car.apply1(this.optarg);
            if (ports.isOutputPort(outport)) {
                return frame0Var.lambda2(outport);
            }
            try {
                return ports.callWithOutputFile(Path.valueOf(outport), fun);
            } catch (ClassCastException e) {
                throw new WrongType(e, "call-with-output-file", 1, outport);
            }
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

    /* compiled from: ppfile.scm */
    /* loaded from: classes.dex */
    public class frame0 extends ModuleBody {
        final ModuleMethod lambda$Fn2;
        Object port;
        frame staticLink;

        public frame0() {
            ModuleMethod moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/u2/home/jis/ai2-kawa/gnu/kawa/slib/ppfile.scm:34");
            this.lambda$Fn2 = moduleMethod;
        }

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 1 ? lambda2(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda2(Object export) {
            Object c = readchar.peekChar.apply1(this.port);
            while (true) {
                boolean x = ports.isEofObject(c);
                if (x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                }
                try {
                    if (unicode.isCharWhitespace((Char) c)) {
                        ports.display(readchar.readChar.apply1(this.port), export);
                        c = readchar.peekChar.apply1(this.port);
                    } else {
                        try {
                            if (characters.isChar$Eq(ppfile.Lit0, (Char) c)) {
                                while (true) {
                                    boolean x2 = ports.isEofObject(c);
                                    if (x2) {
                                        return x2 ? Boolean.TRUE : Boolean.FALSE;
                                    }
                                    try {
                                        if (characters.isChar$Eq(ppfile.Lit1, (Char) c)) {
                                            ports.display(readchar.readChar.apply1(this.port), export);
                                            c = readchar.peekChar.apply1(this.port);
                                            break;
                                        }
                                        ports.display(readchar.readChar.apply1(this.port), export);
                                        c = readchar.peekChar.apply1(this.port);
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "char=?", 2, c);
                                    }
                                }
                            } else {
                                Object obj = this.port;
                                try {
                                    Object o = ports.read((InPort) obj);
                                    boolean x3 = ports.isEofObject(o);
                                    if (x3) {
                                        return x3 ? Boolean.TRUE : Boolean.FALSE;
                                    }
                                    pp.prettyPrint(Scheme.applyToArgs.apply2(this.staticLink.filter, o), export);
                                    c = readchar.peekChar.apply1(this.port);
                                    if (Scheme.isEqv.apply2(ppfile.Lit1, c) != Boolean.FALSE) {
                                        readchar.readChar.apply1(this.port);
                                        c = readchar.peekChar.apply1(this.port);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "read", 1, obj);
                                }
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 2, c);
                        }
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "char-whitespace?", 1, c);
                }
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

    public static Object pprintFile(Object ifile, Object oport) {
        return pprintFilterFile$V(ifile, lambda$Fn3, new Object[]{oport});
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 4:
                return lambda3(obj);
            case 5:
                return pprintFile(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        return moduleMethod.selector == 5 ? pprintFile(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
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

    static Object lambda3(Object x) {
        return x;
    }

    @Override // gnu.expr.ModuleBody
    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 4:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }
}
