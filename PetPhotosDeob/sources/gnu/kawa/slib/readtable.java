package gnu.kawa.slib;

import androidx.core.view.InputDeviceCompat;
import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.lispexpr.ReadTableEntry;
import gnu.kawa.lispexpr.ReaderDispatch;
import gnu.kawa.lispexpr.ReaderDispatchMacro;
import gnu.kawa.lispexpr.ReaderMacro;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.Char;

/* compiled from: readtable.scm */
/* loaded from: classes.dex */
public class readtable extends ModuleBody {
    public static final ModuleMethod current$Mnreadtable;
    public static final ModuleMethod define$Mnreader$Mnctor;
    public static final ModuleMethod get$Mndispatch$Mnmacro$Mntable;
    public static final ModuleMethod make$Mndispatch$Mnmacro$Mncharacter;
    public static final ModuleMethod readtable$Qu;
    public static final ModuleMethod set$Mndispatch$Mnmacro$Mncharacter;
    public static final ModuleMethod set$Mnmacro$Mncharacter;
    static final SimpleSymbol Lit6 = (SimpleSymbol) new SimpleSymbol("define-reader-ctor").readResolve();
    static final SimpleSymbol Lit5 = (SimpleSymbol) new SimpleSymbol("get-dispatch-macro-table").readResolve();
    static final SimpleSymbol Lit4 = (SimpleSymbol) new SimpleSymbol("set-dispatch-macro-character").readResolve();
    static final SimpleSymbol Lit3 = (SimpleSymbol) new SimpleSymbol("make-dispatch-macro-character").readResolve();
    static final SimpleSymbol Lit2 = (SimpleSymbol) new SimpleSymbol("set-macro-character").readResolve();
    static final SimpleSymbol Lit1 = (SimpleSymbol) new SimpleSymbol("readtable?").readResolve();
    static final SimpleSymbol Lit0 = (SimpleSymbol) new SimpleSymbol("current-readtable").readResolve();
    public static final readtable $instance = new readtable();

    static {
        readtable readtableVar = $instance;
        current$Mnreadtable = new ModuleMethod(readtableVar, 1, Lit0, 0);
        readtable$Qu = new ModuleMethod(readtableVar, 2, Lit1, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        set$Mnmacro$Mncharacter = new ModuleMethod(readtableVar, 3, Lit2, InputDeviceCompat.SOURCE_STYLUS);
        make$Mndispatch$Mnmacro$Mncharacter = new ModuleMethod(readtableVar, 6, Lit3, 12289);
        set$Mndispatch$Mnmacro$Mncharacter = new ModuleMethod(readtableVar, 9, Lit4, 16387);
        get$Mndispatch$Mnmacro$Mntable = new ModuleMethod(readtableVar, 11, Lit5, 12290);
        define$Mnreader$Mnctor = new ModuleMethod(readtableVar, 13, Lit6, 12290);
        $instance.run();
    }

    public readtable() {
        ModuleInfo.register(this);
    }

    public static ReadTable currentReadtable() {
        return ReadTable.getCurrent();
    }

    public static void defineReaderCtor(Symbol symbol, Procedure procedure) {
        defineReaderCtor(symbol, procedure, currentReadtable());
    }

    public static Object getDispatchMacroTable(char c, char c2) {
        return getDispatchMacroTable(c, c2, currentReadtable());
    }

    public static void makeDispatchMacroCharacter(char c) {
        makeDispatchMacroCharacter(c, false);
    }

    public static void makeDispatchMacroCharacter(char c, boolean z) {
        makeDispatchMacroCharacter(c, z, currentReadtable());
    }

    public static void setDispatchMacroCharacter(char c, char c2, Object obj) {
        setDispatchMacroCharacter(c, c2, obj, currentReadtable());
    }

    public static void setMacroCharacter(char c, Object obj) {
        setMacroCharacter(c, obj, false);
    }

    public static void setMacroCharacter(char c, Object obj, boolean z) {
        setMacroCharacter(c, obj, z, currentReadtable());
    }

    @Override // gnu.expr.ModuleBody
    public Object apply0(ModuleMethod moduleMethod) {
        return moduleMethod.selector == 1 ? currentReadtable() : super.apply0(moduleMethod);
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
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    public static boolean isReadtable(Object obj) {
        return obj instanceof ReadTable;
    }

    @Override // gnu.expr.ModuleBody
    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 6:
                if (obj instanceof Char) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static void setMacroCharacter(char c, Object function, boolean non$Mnterminating, ReadTable readtable) {
        try {
            readtable.set(c, new ReaderMacro((Procedure) function, non$Mnterminating));
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.kawa.lispexpr.ReaderMacro.<init>(gnu.mapping.Procedure,boolean)", 1, function);
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 3:
                if (obj instanceof Char) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 6:
                if (obj instanceof Char) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 11:
                if (obj instanceof Char) {
                    callContext.value1 = obj;
                    if (obj2 instanceof Char) {
                        callContext.value2 = obj2;
                        callContext.proc = moduleMethod;
                        callContext.pc = 2;
                        return 0;
                    }
                    return -786430;
                }
                return -786431;
            case 13:
                if (obj instanceof Symbol) {
                    callContext.value1 = obj;
                    if (obj2 instanceof Procedure) {
                        callContext.value2 = obj2;
                        callContext.proc = moduleMethod;
                        callContext.pc = 2;
                        return 0;
                    }
                    return -786430;
                }
                return -786431;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 3:
                if (obj instanceof Char) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                }
                return -786431;
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            case 6:
                if (obj instanceof Char) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    if (obj3 instanceof ReadTable) {
                        callContext.value3 = obj3;
                        callContext.proc = moduleMethod;
                        callContext.pc = 3;
                        return 0;
                    }
                    return -786429;
                }
                return -786431;
            case 9:
                if (obj instanceof Char) {
                    callContext.value1 = obj;
                    if (obj2 instanceof Char) {
                        callContext.value2 = obj2;
                        callContext.value3 = obj3;
                        callContext.proc = moduleMethod;
                        callContext.pc = 3;
                        return 0;
                    }
                    return -786430;
                }
                return -786431;
            case 11:
                if (obj instanceof Char) {
                    callContext.value1 = obj;
                    if (obj2 instanceof Char) {
                        callContext.value2 = obj2;
                        if (obj3 instanceof ReadTable) {
                            callContext.value3 = obj3;
                            callContext.proc = moduleMethod;
                            callContext.pc = 3;
                            return 0;
                        }
                        return -786429;
                    }
                    return -786430;
                }
                return -786431;
            case 13:
                if (obj instanceof Symbol) {
                    callContext.value1 = obj;
                    if (obj2 instanceof Procedure) {
                        callContext.value2 = obj2;
                        if (obj3 instanceof ReadTable) {
                            callContext.value3 = obj3;
                            callContext.proc = moduleMethod;
                            callContext.pc = 3;
                            return 0;
                        }
                        return -786429;
                    }
                    return -786430;
                }
                return -786431;
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 3:
                if (obj instanceof Char) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    if (obj4 instanceof ReadTable) {
                        callContext.value4 = obj4;
                        callContext.proc = moduleMethod;
                        callContext.pc = 4;
                        return 0;
                    }
                    return -786428;
                }
                return -786431;
            case 9:
                if (obj instanceof Char) {
                    callContext.value1 = obj;
                    if (obj2 instanceof Char) {
                        callContext.value2 = obj2;
                        callContext.value3 = obj3;
                        if (obj4 instanceof ReadTable) {
                            callContext.value4 = obj4;
                            callContext.proc = moduleMethod;
                            callContext.pc = 4;
                            return 0;
                        }
                        return -786428;
                    }
                    return -786430;
                }
                return -786431;
            default:
                return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
    }

    public static void makeDispatchMacroCharacter(char c, boolean non$Mnterminating, ReadTable readtable) {
        readtable.set(c, new ReaderDispatch(non$Mnterminating));
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 2:
                return isReadtable(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 6:
                try {
                    makeDispatchMacroCharacter(((Char) obj).charValue());
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-dispatch-macro-character", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static void setDispatchMacroCharacter(char disp$Mnchar, char sub$Mnchar, Object function, ReadTable readtable) {
        ReadTableEntry lookup = readtable.lookup(disp$Mnchar);
        try {
            ReaderDispatch entry = (ReaderDispatch) lookup;
            try {
                entry.set(sub$Mnchar, new ReaderDispatchMacro((Procedure) function));
            } catch (ClassCastException e) {
                throw new WrongType(e, "gnu.kawa.lispexpr.ReaderDispatchMacro.<init>(gnu.mapping.Procedure)", 1, function);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "entry", -2, lookup);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        switch (moduleMethod.selector) {
            case 3:
                try {
                    try {
                        try {
                            setMacroCharacter(((Char) obj).charValue(), obj2, obj3 != Boolean.FALSE, (ReadTable) obj4);
                            return Values.empty;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "set-macro-character", 4, obj4);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "set-macro-character", 3, obj3);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "set-macro-character", 1, obj);
                }
            case 9:
                try {
                    try {
                        try {
                            setDispatchMacroCharacter(((Char) obj).charValue(), ((Char) obj2).charValue(), obj3, (ReadTable) obj4);
                            return Values.empty;
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "set-dispatch-macro-character", 4, obj4);
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "set-dispatch-macro-character", 2, obj2);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "set-dispatch-macro-character", 1, obj);
                }
            default:
                return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }
    }

    public static Object getDispatchMacroTable(char disp$Mnchar, char sub$Mnchar, ReadTable readtable) {
        ReadTableEntry lookup = readtable.lookup(disp$Mnchar);
        try {
            ReaderDispatch disp$Mnentry = (ReaderDispatch) lookup;
            ReadTableEntry sub$Mnentry = disp$Mnentry.lookup(sub$Mnchar);
            return sub$Mnentry == null ? Boolean.FALSE : sub$Mnentry;
        } catch (ClassCastException e) {
            throw new WrongType(e, "disp-entry", -2, lookup);
        }
    }

    public static void defineReaderCtor(Symbol key, Procedure proc, ReadTable readtable) {
        readtable.putReaderCtor(key == null ? null : key.toString(), proc);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 3:
                try {
                    setMacroCharacter(((Char) obj).charValue(), obj2);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-macro-character", 1, obj);
                }
            case 6:
                try {
                    try {
                        makeDispatchMacroCharacter(((Char) obj).charValue(), obj2 != Boolean.FALSE);
                        return Values.empty;
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "make-dispatch-macro-character", 2, obj2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "make-dispatch-macro-character", 1, obj);
                }
            case 11:
                try {
                    try {
                        return getDispatchMacroTable(((Char) obj).charValue(), ((Char) obj2).charValue());
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "get-dispatch-macro-table", 2, obj2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "get-dispatch-macro-table", 1, obj);
                }
            case 13:
                try {
                    try {
                        defineReaderCtor((Symbol) obj, (Procedure) obj2);
                        return Values.empty;
                    } catch (ClassCastException e6) {
                        throw new WrongType(e6, "define-reader-ctor", 2, obj2);
                    }
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "define-reader-ctor", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 3:
                try {
                    try {
                        setMacroCharacter(((Char) obj).charValue(), obj2, obj3 != Boolean.FALSE);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "set-macro-character", 3, obj3);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "set-macro-character", 1, obj);
                }
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
            case 6:
                try {
                    try {
                        try {
                            makeDispatchMacroCharacter(((Char) obj).charValue(), obj2 != Boolean.FALSE, (ReadTable) obj3);
                            return Values.empty;
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "make-dispatch-macro-character", 3, obj3);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "make-dispatch-macro-character", 2, obj2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "make-dispatch-macro-character", 1, obj);
                }
            case 9:
                try {
                    try {
                        setDispatchMacroCharacter(((Char) obj).charValue(), ((Char) obj2).charValue(), obj3);
                        return Values.empty;
                    } catch (ClassCastException e6) {
                        throw new WrongType(e6, "set-dispatch-macro-character", 2, obj2);
                    }
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "set-dispatch-macro-character", 1, obj);
                }
            case 11:
                try {
                    try {
                        try {
                            return getDispatchMacroTable(((Char) obj).charValue(), ((Char) obj2).charValue(), (ReadTable) obj3);
                        } catch (ClassCastException e8) {
                            throw new WrongType(e8, "get-dispatch-macro-table", 3, obj3);
                        }
                    } catch (ClassCastException e9) {
                        throw new WrongType(e9, "get-dispatch-macro-table", 2, obj2);
                    }
                } catch (ClassCastException e10) {
                    throw new WrongType(e10, "get-dispatch-macro-table", 1, obj);
                }
            case 13:
                try {
                    try {
                        try {
                            defineReaderCtor((Symbol) obj, (Procedure) obj2, (ReadTable) obj3);
                            return Values.empty;
                        } catch (ClassCastException e11) {
                            throw new WrongType(e11, "define-reader-ctor", 3, obj3);
                        }
                    } catch (ClassCastException e12) {
                        throw new WrongType(e12, "define-reader-ctor", 2, obj2);
                    }
                } catch (ClassCastException e13) {
                    throw new WrongType(e13, "define-reader-ctor", 1, obj);
                }
        }
    }
}
