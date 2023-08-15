package kawa.lib;

import androidx.fragment.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Strings;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.Char;

/* compiled from: strings.scm */
/* loaded from: classes.dex */
public class strings extends ModuleBody {
    public static final ModuleMethod $make$string$;
    public static final ModuleMethod list$Mn$Grstring;
    public static final ModuleMethod make$Mnstring;
    public static final ModuleMethod string$Eq$Qu;
    public static final ModuleMethod string$Gr$Eq$Qu;
    public static final ModuleMethod string$Gr$Qu;
    public static final ModuleMethod string$Ls$Eq$Qu;
    public static final ModuleMethod string$Ls$Qu;
    public static final ModuleMethod string$Mn$Grlist;
    public static final ModuleMethod string$Mnappend;
    public static final ModuleMethod string$Mnappend$Slshared;
    public static final ModuleMethod string$Mncapitalize;
    public static final ModuleMethod string$Mncapitalize$Ex;
    public static final ModuleMethod string$Mncopy;
    public static final ModuleMethod string$Mndowncase$Ex;
    public static final ModuleMethod string$Mnfill$Ex;
    public static final ModuleMethod string$Mnlength;
    public static final ModuleMethod string$Mnref;
    public static final ModuleMethod string$Mnset$Ex;
    public static final ModuleMethod string$Mnupcase$Ex;
    public static final ModuleMethod string$Qu;
    public static final ModuleMethod substring;
    static final SimpleSymbol Lit22 = (SimpleSymbol) new SimpleSymbol("string-append/shared").readResolve();
    static final SimpleSymbol Lit21 = (SimpleSymbol) new SimpleSymbol("string-append").readResolve();
    static final SimpleSymbol Lit20 = (SimpleSymbol) new SimpleSymbol("string-capitalize").readResolve();
    static final SimpleSymbol Lit19 = (SimpleSymbol) new SimpleSymbol("string-capitalize!").readResolve();
    static final SimpleSymbol Lit18 = (SimpleSymbol) new SimpleSymbol("string-downcase!").readResolve();
    static final SimpleSymbol Lit17 = (SimpleSymbol) new SimpleSymbol("string-upcase!").readResolve();
    static final SimpleSymbol Lit16 = (SimpleSymbol) new SimpleSymbol("string-fill!").readResolve();
    static final SimpleSymbol Lit15 = (SimpleSymbol) new SimpleSymbol("string-copy").readResolve();
    static final SimpleSymbol Lit14 = (SimpleSymbol) new SimpleSymbol("list->string").readResolve();
    static final SimpleSymbol Lit13 = (SimpleSymbol) new SimpleSymbol("string->list").readResolve();
    static final SimpleSymbol Lit12 = (SimpleSymbol) new SimpleSymbol("substring").readResolve();
    static final SimpleSymbol Lit11 = (SimpleSymbol) new SimpleSymbol("string>=?").readResolve();
    static final SimpleSymbol Lit10 = (SimpleSymbol) new SimpleSymbol("string<=?").readResolve();
    static final SimpleSymbol Lit9 = (SimpleSymbol) new SimpleSymbol("string>?").readResolve();
    static final SimpleSymbol Lit8 = (SimpleSymbol) new SimpleSymbol("string<?").readResolve();
    static final SimpleSymbol Lit7 = (SimpleSymbol) new SimpleSymbol("string=?").readResolve();
    static final SimpleSymbol Lit6 = (SimpleSymbol) new SimpleSymbol("string-set!").readResolve();
    static final SimpleSymbol Lit5 = (SimpleSymbol) new SimpleSymbol("string-ref").readResolve();
    static final SimpleSymbol Lit4 = (SimpleSymbol) new SimpleSymbol("string-length").readResolve();
    static final SimpleSymbol Lit3 = (SimpleSymbol) new SimpleSymbol("$make$string$").readResolve();
    static final SimpleSymbol Lit2 = (SimpleSymbol) new SimpleSymbol("make-string").readResolve();
    static final SimpleSymbol Lit1 = (SimpleSymbol) new SimpleSymbol("string?").readResolve();
    static final Char Lit0 = Char.make(32);
    public static final strings $instance = new strings();

    static {
        strings stringsVar = $instance;
        string$Qu = new ModuleMethod(stringsVar, 1, Lit1, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnstring = new ModuleMethod(stringsVar, 2, Lit2, 8193);
        $make$string$ = new ModuleMethod(stringsVar, 4, Lit3, -4096);
        string$Mnlength = new ModuleMethod(stringsVar, 5, Lit4, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnref = new ModuleMethod(stringsVar, 6, Lit5, 8194);
        string$Mnset$Ex = new ModuleMethod(stringsVar, 7, Lit6, 12291);
        string$Eq$Qu = new ModuleMethod(stringsVar, 8, Lit7, 8194);
        string$Ls$Qu = new ModuleMethod(stringsVar, 9, Lit8, 8194);
        string$Gr$Qu = new ModuleMethod(stringsVar, 10, Lit9, 8194);
        string$Ls$Eq$Qu = new ModuleMethod(stringsVar, 11, Lit10, 8194);
        string$Gr$Eq$Qu = new ModuleMethod(stringsVar, 12, Lit11, 8194);
        substring = new ModuleMethod(stringsVar, 13, Lit12, 12291);
        string$Mn$Grlist = new ModuleMethod(stringsVar, 14, Lit13, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Grstring = new ModuleMethod(stringsVar, 15, Lit14, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mncopy = new ModuleMethod(stringsVar, 16, Lit15, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnfill$Ex = new ModuleMethod(stringsVar, 17, Lit16, 8194);
        string$Mnupcase$Ex = new ModuleMethod(stringsVar, 18, Lit17, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mndowncase$Ex = new ModuleMethod(stringsVar, 19, Lit18, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mncapitalize$Ex = new ModuleMethod(stringsVar, 20, Lit19, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mncapitalize = new ModuleMethod(stringsVar, 21, Lit20, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnappend = new ModuleMethod(stringsVar, 22, Lit21, -4096);
        string$Mnappend$Slshared = new ModuleMethod(stringsVar, 23, Lit22, -4096);
        $instance.run();
    }

    public strings() {
        ModuleInfo.register(this);
    }

    public static CharSequence makeString(int i) {
        return makeString(i, Lit0);
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
    }

    public static boolean isString(Object x) {
        return x instanceof CharSequence;
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
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 17:
            default:
                return super.match1(moduleMethod, obj, callContext);
            case 5:
                if (obj instanceof CharSequence) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 14:
                if (obj instanceof CharSequence) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 15:
                if (obj instanceof LList) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 16:
                if (obj instanceof CharSequence) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 18:
                if (obj instanceof CharSeq) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 19:
                if (obj instanceof CharSeq) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 20:
                if (obj instanceof CharSeq) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 21:
                if (obj instanceof CharSequence) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
        }
    }

    public static CharSequence makeString(int n, Object ch) {
        try {
            return new FString(n, ((Char) ch).charValue());
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.lists.FString.<init>(int,char)", 2, ch);
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 3:
            case 4:
            case 5:
            case 7:
            case 13:
            case 14:
            case 15:
            case 16:
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
            case 6:
                if (obj instanceof CharSequence) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                }
                return -786431;
            case 8:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 9:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 11:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 12:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 17:
                if (obj instanceof CharSeq) {
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
        }
    }

    public static CharSequence $make$string$(Object... args) {
        int n = args.length;
        FString str = new FString(n);
        for (int i = 0; i < n; i++) {
            str.setCharAt(i, ((Char) args[i]).charValue());
        }
        return str;
    }

    @Override // gnu.expr.ModuleBody
    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 4:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 22:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 23:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static int stringLength(CharSequence str) {
        return str.length();
    }

    public static char stringRef(CharSequence string, int k) {
        return string.charAt(k);
    }

    @Override // gnu.expr.ModuleBody
    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 7:
                if (obj instanceof CharSeq) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    if (obj3 instanceof Char) {
                        callContext.value3 = obj3;
                        callContext.proc = moduleMethod;
                        callContext.pc = 3;
                        return 0;
                    }
                    return -786429;
                }
                return -786431;
            case 13:
                if (obj instanceof CharSequence) {
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                }
                return -786431;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static boolean isString$Eq(Object x, Object y) {
        return x.toString().equals(y.toString());
    }

    public static boolean isString$Ls(Object x, Object y) {
        return x.toString().compareTo(y.toString()) < 0;
    }

    public static boolean isString$Gr(Object x, Object y) {
        return x.toString().compareTo(y.toString()) > 0;
    }

    public static boolean isString$Ls$Eq(Object x, Object y) {
        return x.toString().compareTo(y.toString()) <= 0;
    }

    public static boolean isString$Gr$Eq(Object x, Object y) {
        return x.toString().compareTo(y.toString()) >= 0;
    }

    public static CharSequence substring(CharSequence str, int start, int end) {
        return new FString(str, start, end - start);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 7:
                try {
                    try {
                        try {
                            ((CharSeq) obj).setCharAt(((Number) obj2).intValue(), ((Char) obj3).charValue());
                            return Values.empty;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, obj2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 1, obj);
                }
            case 13:
                try {
                    try {
                        try {
                            return substring((CharSequence) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
                        } catch (ClassCastException e4) {
                            throw new WrongType(e4, "substring", 3, obj3);
                        }
                    } catch (ClassCastException e5) {
                        throw new WrongType(e5, "substring", 2, obj2);
                    }
                } catch (ClassCastException e6) {
                    throw new WrongType(e6, "substring", 1, obj);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static LList string$To$List(CharSequence str) {
        LList result = LList.Empty;
        int i = stringLength(str);
        while (true) {
            LList result2 = result;
            i--;
            if (i < 0) {
                return result2;
            }
            result = new Pair(Char.make(stringRef(str, i)), result2);
        }
    }

    public static CharSequence list$To$String(LList list) {
        int len = lists.length(list);
        CharSequence result = new FString(len);
        for (int i = 0; i < len; i++) {
            try {
                Pair pair = (Pair) list;
                try {
                    FString fString = (CharSeq) result;
                    Object car = pair.getCar();
                    try {
                        fString.setCharAt(i, ((Char) car).charValue());
                        Object cdr = pair.getCdr();
                        try {
                            list = (LList) cdr;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "list", -2, cdr);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-set!", 2, car);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-set!", 0, result);
                }
            } catch (ClassCastException e4) {
                throw new WrongType(e4, "pair", -2, list);
            }
        }
        return result;
    }

    public static FString stringCopy(CharSequence str) {
        return new FString(str);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 2:
                try {
                    return makeString(((Number) obj).intValue(), obj2);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-string", 1, obj);
                }
            case 3:
            case 4:
            case 5:
            case 7:
            case 13:
            case 14:
            case 15:
            case 16:
            default:
                return super.apply2(moduleMethod, obj, obj2);
            case 6:
                try {
                    try {
                        return Char.make(stringRef((CharSequence) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 2, obj2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "string-ref", 1, obj);
                }
            case 8:
                return isString$Eq(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            case 9:
                return isString$Ls(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            case 10:
                return isString$Gr(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            case 11:
                return isString$Ls$Eq(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            case 12:
                return isString$Gr$Eq(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            case 17:
                try {
                    try {
                        ((CharSeq) obj).fill(((Char) obj2).charValue());
                        return Values.empty;
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "string-fill!", 2, obj2);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "string-fill!", 1, obj);
                }
        }
    }

    /*  JADX ERROR: NullPointerException in pass: MarkMethodsForInline
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.isRegister()" because "arg" is null
        	at jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(RegisterArg.java:173)
        	at jadx.core.dex.instructions.args.InsnArg.isSameVar(InsnArg.java:269)
        	at jadx.core.dex.visitors.MarkMethodsForInline.isSyntheticAccessPattern(MarkMethodsForInline.java:118)
        	at jadx.core.dex.visitors.MarkMethodsForInline.inlineMth(MarkMethodsForInline.java:86)
        	at jadx.core.dex.visitors.MarkMethodsForInline.process(MarkMethodsForInline.java:53)
        	at jadx.core.dex.visitors.MarkMethodsForInline.visit(MarkMethodsForInline.java:37)
        */
    public static java.lang.CharSequence stringUpcase$Ex(gnu.lists.CharSeq r0) {
        /*
            gnu.lists.Strings.makeUpperCase(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lib.strings.stringUpcase$Ex(gnu.lists.CharSeq):java.lang.CharSequence");
    }

    /*  JADX ERROR: NullPointerException in pass: MarkMethodsForInline
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.isRegister()" because "arg" is null
        	at jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(RegisterArg.java:173)
        	at jadx.core.dex.instructions.args.InsnArg.isSameVar(InsnArg.java:269)
        	at jadx.core.dex.visitors.MarkMethodsForInline.isSyntheticAccessPattern(MarkMethodsForInline.java:118)
        	at jadx.core.dex.visitors.MarkMethodsForInline.inlineMth(MarkMethodsForInline.java:86)
        	at jadx.core.dex.visitors.MarkMethodsForInline.process(MarkMethodsForInline.java:53)
        	at jadx.core.dex.visitors.MarkMethodsForInline.visit(MarkMethodsForInline.java:37)
        */
    public static java.lang.CharSequence stringDowncase$Ex(gnu.lists.CharSeq r0) {
        /*
            gnu.lists.Strings.makeLowerCase(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lib.strings.stringDowncase$Ex(gnu.lists.CharSeq):java.lang.CharSequence");
    }

    /*  JADX ERROR: NullPointerException in pass: MarkMethodsForInline
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.isRegister()" because "arg" is null
        	at jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(RegisterArg.java:173)
        	at jadx.core.dex.instructions.args.InsnArg.isSameVar(InsnArg.java:269)
        	at jadx.core.dex.visitors.MarkMethodsForInline.isSyntheticAccessPattern(MarkMethodsForInline.java:118)
        	at jadx.core.dex.visitors.MarkMethodsForInline.inlineMth(MarkMethodsForInline.java:86)
        	at jadx.core.dex.visitors.MarkMethodsForInline.process(MarkMethodsForInline.java:53)
        	at jadx.core.dex.visitors.MarkMethodsForInline.visit(MarkMethodsForInline.java:37)
        */
    public static java.lang.CharSequence stringCapitalize$Ex(gnu.lists.CharSeq r0) {
        /*
            gnu.lists.Strings.makeCapitalize(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lib.strings.stringCapitalize$Ex(gnu.lists.CharSeq):java.lang.CharSequence");
    }

    public static CharSequence stringCapitalize(CharSequence str) {
        FString copy = stringCopy(str);
        Strings.makeCapitalize(copy);
        return copy;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: InlineMethods
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to process method for inline: kawa.lib.strings.stringDowncase$Ex(gnu.lists.CharSeq):java.lang.CharSequence
        	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:76)
        	at jadx.core.dex.visitors.InlineMethods.visit(InlineMethods.java:51)
        Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.isRegister()" because "arg" is null
        	at jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(RegisterArg.java:173)
        	at jadx.core.dex.instructions.args.InsnArg.isSameVar(InsnArg.java:269)
        	at jadx.core.dex.visitors.MarkMethodsForInline.isSyntheticAccessPattern(MarkMethodsForInline.java:118)
        	at jadx.core.dex.visitors.MarkMethodsForInline.inlineMth(MarkMethodsForInline.java:86)
        	at jadx.core.dex.visitors.MarkMethodsForInline.process(MarkMethodsForInline.java:53)
        	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:65)
        	... 1 more
        */
    @Override // gnu.expr.ModuleBody
    public java.lang.Object apply1(gnu.expr.ModuleMethod r6, java.lang.Object r7) {
        /*
            r5 = this;
            r4 = 1
            int r1 = r6.selector
            switch(r1) {
                case 1: goto Lb;
                case 2: goto L17;
                case 3: goto L6;
                case 4: goto L6;
                case 5: goto L24;
                case 6: goto L6;
                case 7: goto L6;
                case 8: goto L6;
                case 9: goto L6;
                case 10: goto L6;
                case 11: goto L6;
                case 12: goto L6;
                case 13: goto L6;
                case 14: goto L2f;
                case 15: goto L36;
                case 16: goto L3d;
                case 17: goto L6;
                case 18: goto L44;
                case 19: goto L4b;
                case 20: goto L52;
                case 21: goto L59;
                default: goto L6;
            }
        L6:
            java.lang.Object r1 = super.apply1(r6, r7)
        La:
            return r1
        Lb:
            boolean r1 = isString(r7)
            if (r1 == 0) goto L14
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            goto La
        L14:
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            goto La
        L17:
            r0 = r7
            java.lang.Number r0 = (java.lang.Number) r0     // Catch: java.lang.ClassCastException -> L60
            r1 = r0
            int r1 = r1.intValue()     // Catch: java.lang.ClassCastException -> L60
            java.lang.CharSequence r1 = makeString(r1)
            goto La
        L24:
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7     // Catch: java.lang.ClassCastException -> L69
            int r1 = stringLength(r7)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            goto La
        L2f:
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7     // Catch: java.lang.ClassCastException -> L72
            gnu.lists.LList r1 = string$To$List(r7)
            goto La
        L36:
            gnu.lists.LList r7 = (gnu.lists.LList) r7     // Catch: java.lang.ClassCastException -> L7b
            java.lang.CharSequence r1 = list$To$String(r7)
            goto La
        L3d:
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7     // Catch: java.lang.ClassCastException -> L84
            gnu.lists.FString r1 = stringCopy(r7)
            goto La
        L44:
            gnu.lists.CharSeq r7 = (gnu.lists.CharSeq) r7     // Catch: java.lang.ClassCastException -> L8d
            java.lang.CharSequence r1 = stringUpcase$Ex(r7)
            goto La
        L4b:
            gnu.lists.CharSeq r7 = (gnu.lists.CharSeq) r7     // Catch: java.lang.ClassCastException -> L96
            java.lang.CharSequence r1 = stringDowncase$Ex(r7)
            goto La
        L52:
            gnu.lists.CharSeq r7 = (gnu.lists.CharSeq) r7     // Catch: java.lang.ClassCastException -> L9f
            java.lang.CharSequence r1 = stringCapitalize$Ex(r7)
            goto La
        L59:
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7     // Catch: java.lang.ClassCastException -> La8
            java.lang.CharSequence r1 = stringCapitalize(r7)
            goto La
        L60:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "make-string"
            r2.<init>(r1, r3, r4, r7)
            throw r2
        L69:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "string-length"
            r2.<init>(r1, r3, r4, r7)
            throw r2
        L72:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "string->list"
            r2.<init>(r1, r3, r4, r7)
            throw r2
        L7b:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "list->string"
            r2.<init>(r1, r3, r4, r7)
            throw r2
        L84:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "string-copy"
            r2.<init>(r1, r3, r4, r7)
            throw r2
        L8d:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "string-upcase!"
            r2.<init>(r1, r3, r4, r7)
            throw r2
        L96:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "string-downcase!"
            r2.<init>(r1, r3, r4, r7)
            throw r2
        L9f:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "string-capitalize!"
            r2.<init>(r1, r3, r4, r7)
            throw r2
        La8:
            r1 = move-exception
            gnu.mapping.WrongType r2 = new gnu.mapping.WrongType
            java.lang.String r3 = "string-capitalize"
            r2.<init>(r1, r3, r4, r7)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lib.strings.apply1(gnu.expr.ModuleMethod, java.lang.Object):java.lang.Object");
    }

    public static FString stringAppend(Object... args) {
        FString str = new FString();
        str.addAllStrings(args, 0);
        return str;
    }

    public static CharSequence stringAppend$SlShared(Object... args) {
        FString fstr;
        if (args.length == 0) {
            return new FString();
        }
        Object arg1 = args[0];
        if (arg1 instanceof FString) {
            try {
                fstr = (FString) arg1;
            } catch (ClassCastException e) {
                throw new WrongType(e, "fstr", -2, arg1);
            }
        } else {
            try {
                fstr = stringCopy((CharSequence) arg1);
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-copy", 0, arg1);
            }
        }
        fstr.addAllStrings(args, 1);
        return fstr;
    }

    @Override // gnu.expr.ModuleBody
    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 4:
                return $make$string$(objArr);
            case 22:
                return stringAppend(objArr);
            case 23:
                return stringAppend$SlShared(objArr);
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }
}
