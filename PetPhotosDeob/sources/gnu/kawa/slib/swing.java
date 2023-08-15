package gnu.kawa.slib;

import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.runtime.Component;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.models.Paintable;
import gnu.kawa.models.WithTransform;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.swingviews.SwingDisplay;
import gnu.kawa.swingviews.SwingPaintable;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.UnboundLocationException;
import gnu.mapping.WrongType;
import gnu.math.Complex;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import kawa.lib.numbers;
import kawa.standard.Scheme;

/* compiled from: swing.scm */
/* loaded from: classes.dex */
public class swing extends ModuleBody {
    public static final Color color$Mnred = null;
    public static final ModuleMethod composite$Mnsrc;
    public static final ModuleMethod composite$Mnsrc$Mnover;
    public static final ModuleMethod draw;
    public static final ModuleMethod fill;
    public static final ModuleMethod make$Mnaction$Mnlistener;
    public static final ModuleMethod menu;
    public static final ModuleMethod menubar;
    public static final ModuleMethod menuitem;
    public static final ModuleMethod polygon;
    public static final ModuleMethod rotation;
    public static final ModuleMethod scroll;
    public static final ModuleMethod with$Mncomposite;
    public static final ModuleMethod with$Mnpaint;
    public static final ModuleMethod with$Mntransform;
    static final SimpleSymbol Lit26 = (SimpleSymbol) new SimpleSymbol("scroll").readResolve();
    static final SimpleSymbol Lit25 = (SimpleSymbol) new SimpleSymbol("polygon").readResolve();
    static final SimpleSymbol Lit24 = (SimpleSymbol) new SimpleSymbol("menuitem").readResolve();
    static final SimpleSymbol Lit23 = (SimpleSymbol) new SimpleSymbol("menu").readResolve();
    static final SimpleSymbol Lit22 = (SimpleSymbol) new SimpleSymbol("menubar").readResolve();
    static final SimpleSymbol Lit21 = (SimpleSymbol) new SimpleSymbol("with-transform").readResolve();
    static final SimpleSymbol Lit20 = (SimpleSymbol) new SimpleSymbol("rotation").readResolve();
    static final SimpleSymbol Lit19 = (SimpleSymbol) new SimpleSymbol("composite-src").readResolve();
    static final SimpleSymbol Lit18 = (SimpleSymbol) new SimpleSymbol("composite-src-over").readResolve();
    static final SimpleSymbol Lit17 = (SimpleSymbol) new SimpleSymbol("with-composite").readResolve();
    static final SimpleSymbol Lit16 = (SimpleSymbol) new SimpleSymbol("with-paint").readResolve();
    static final SimpleSymbol Lit15 = (SimpleSymbol) new SimpleSymbol("draw").readResolve();
    static final SimpleSymbol Lit14 = (SimpleSymbol) new SimpleSymbol("fill").readResolve();
    static final SimpleSymbol Lit13 = (SimpleSymbol) new SimpleSymbol("make-action-listener").readResolve();
    static final SimpleSymbol Lit12 = (SimpleSymbol) new SimpleSymbol("gnu.kawa.models.WithComposite").readResolve();
    static final SimpleSymbol Lit11 = (SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.WithPaint>").readResolve();
    static final SimpleSymbol Lit10 = (SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.DrawShape>").readResolve();
    static final SimpleSymbol Lit9 = (SimpleSymbol) new SimpleSymbol("<gnu.kawa.models.FillShape>").readResolve();
    static final Keyword Lit8 = Keyword.make("h");
    static final Keyword Lit7 = Keyword.make("w");
    static final Keyword Lit6 = Keyword.make("accesskey");
    static final Keyword Lit5 = Keyword.make("disabled");
    static final Keyword Lit4 = Keyword.make("oncommand");
    static final Keyword Lit3 = Keyword.make("default");
    static final Keyword Lit2 = Keyword.make("image");
    static final Keyword Lit1 = Keyword.make("label");
    static final SimpleSymbol Lit0 = (SimpleSymbol) new SimpleSymbol("make").readResolve();
    public static final swing $instance = new swing();
    static final Location loc$$Lsgnu$Dtkawa$Dtmodels$DtFillShape$Gr = ThreadLocation.getInstance(Lit9, null);
    static final Location loc$$Lsgnu$Dtkawa$Dtmodels$DtDrawShape$Gr = ThreadLocation.getInstance(Lit10, null);
    static final Location loc$$Lsgnu$Dtkawa$Dtmodels$DtWithPaint$Gr = ThreadLocation.getInstance(Lit11, null);
    static final Location loc$gnu$Dtkawa$Dtmodels$DtWithComposite = ThreadLocation.getInstance(Lit12, null);
    public static final Location button = StaticFieldLocation.make("gnu.kawa.slib.gui", "button");
    public static final Location Button = StaticFieldLocation.make("gnu.kawa.slib.gui", "Button");
    public static final Location Image = StaticFieldLocation.make("gnu.kawa.slib.gui", Component.LISTVIEW_KEY_IMAGE);
    public static final Location image$Mnread = StaticFieldLocation.make("gnu.kawa.slib.gui", "image$Mnread");
    public static final Location image$Mnwidth = StaticFieldLocation.make("gnu.kawa.slib.gui", "image$Mnwidth");
    public static final Location image$Mnheight = StaticFieldLocation.make("gnu.kawa.slib.gui", "image$Mnheight");
    public static final Location Label = StaticFieldLocation.make("gnu.kawa.slib.gui", "Label");
    public static final Location Text = StaticFieldLocation.make("gnu.kawa.slib.gui", "Text");
    public static final Location Row = StaticFieldLocation.make("gnu.kawa.slib.gui", "Row");
    public static final Location Column = StaticFieldLocation.make("gnu.kawa.slib.gui", "Column");
    public static final Location set$Mncontent = StaticFieldLocation.make("gnu.kawa.slib.gui", "set$Mncontent");
    public static final Location Window = StaticFieldLocation.make("gnu.kawa.slib.gui", "Window");
    public static final Location run$Mnapplication = StaticFieldLocation.make("gnu.kawa.slib.gui", "run$Mnapplication");

    public swing() {
        ModuleInfo.register(this);
    }

    public static Composite compositeSrc() {
        return compositeSrc(1.0f);
    }

    public static Composite compositeSrcOver() {
        return compositeSrcOver(1.0f);
    }

    @Override // gnu.expr.ModuleBody
    public final void run(CallContext $ctx) {
        Consumer consumer = $ctx.consumer;
        color$Mnred = Color.red;
    }

    static {
        swing swingVar = $instance;
        make$Mnaction$Mnlistener = new ModuleMethod(swingVar, 1, Lit13, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fill = new ModuleMethod(swingVar, 2, Lit14, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        draw = new ModuleMethod(swingVar, 3, Lit15, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        with$Mnpaint = new ModuleMethod(swingVar, 4, Lit16, 8194);
        with$Mncomposite = new ModuleMethod(swingVar, 5, Lit17, -4096);
        composite$Mnsrc$Mnover = new ModuleMethod(swingVar, 6, Lit18, 4096);
        composite$Mnsrc = new ModuleMethod(swingVar, 8, Lit19, 4096);
        rotation = new ModuleMethod(swingVar, 10, Lit20, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        with$Mntransform = new ModuleMethod(swingVar, 11, Lit21, 8194);
        menubar = new ModuleMethod(swingVar, 12, Lit22, -4096);
        menu = new ModuleMethod(swingVar, 13, Lit23, -4096);
        menuitem = new ModuleMethod(swingVar, 14, Lit24, -4096);
        polygon = new ModuleMethod(swingVar, 15, Lit25, -4095);
        scroll = new ModuleMethod(swingVar, 16, Lit26, -4095);
        $instance.run();
    }

    public static ActionListener makeActionListener(Object proc) {
        return SwingDisplay.makeActionListener(proc);
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
                if (obj instanceof Shape) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 3:
                if (obj instanceof Shape) {
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                }
                return -786431;
            case 4:
            case 5:
            case 7:
            case 9:
            default:
                return super.match1(moduleMethod, obj, callContext);
            case 6:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 8:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
        }
    }

    public static Paintable fill(Shape shape) {
        try {
            return (Paintable) Invoke.make.apply2(loc$$Lsgnu$Dtkawa$Dtmodels$DtFillShape$Gr.get(), shape);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/swing.scm", 19, 9);
            throw e;
        }
    }

    public static Paintable draw(Shape shape) {
        try {
            return (Paintable) Invoke.make.apply2(loc$$Lsgnu$Dtkawa$Dtmodels$DtDrawShape$Gr.get(), shape);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/swing.scm", 22, 9);
            throw e;
        }
    }

    public static Object withPaint(Color paint, Paintable pic) {
        try {
            return Invoke.make.apply3(loc$$Lsgnu$Dtkawa$Dtmodels$DtWithPaint$Gr.get(), pic, paint);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/swing.scm", 26, 10);
            throw e;
        }
    }

    @Override // gnu.expr.ModuleBody
    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 4:
                if (obj instanceof Color) {
                    callContext.value1 = obj;
                    if (obj2 instanceof Paintable) {
                        callContext.value2 = obj2;
                        callContext.proc = moduleMethod;
                        callContext.pc = 2;
                        return 0;
                    }
                    return -786430;
                }
                return -786431;
            case 11:
                if (obj instanceof AffineTransform) {
                    callContext.value1 = obj;
                    if (obj2 instanceof Paintable) {
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

    public static Object withComposite(Object... arguments) {
        try {
            return Scheme.applyToArgs.apply2(GetNamedPart.getNamedPart.apply2(loc$gnu$Dtkawa$Dtmodels$DtWithComposite.get(), Lit0), arguments);
        } catch (UnboundLocationException e) {
            e.setLine("/u2/home/jis/ai2-kawa/gnu/kawa/slib/swing.scm", 29, 4);
            throw e;
        }
    }

    @Override // gnu.expr.ModuleBody
    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 5:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            default:
                return super.matchN(moduleMethod, objArr, callContext);
            case 12:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
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
            case 15:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 16:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
        }
    }

    public static Composite compositeSrcOver(float alpha) {
        return AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    }

    @Override // gnu.expr.ModuleBody
    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 6:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 7:
            default:
                return super.match0(moduleMethod, callContext);
            case 8:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
        }
    }

    public static Composite compositeSrc(float alpha) {
        return AlphaComposite.getInstance(AlphaComposite.SRC, alpha);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 6:
                return compositeSrcOver();
            case 7:
            default:
                return super.apply0(moduleMethod);
            case 8:
                return compositeSrc();
        }
    }

    public static AffineTransform rotation(double theta) {
        return AffineTransform.getRotateInstance(theta);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return makeActionListener(obj);
            case 2:
                try {
                    return fill((Shape) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "fill", 1, obj);
                }
            case 3:
                try {
                    return draw((Shape) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "draw", 1, obj);
                }
            case 4:
            case 5:
            case 7:
            case 9:
            default:
                return super.apply1(moduleMethod, obj);
            case 6:
                try {
                    return compositeSrcOver(((Number) obj).floatValue());
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "composite-src-over", 1, obj);
                }
            case 8:
                try {
                    return compositeSrc(((Number) obj).floatValue());
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "composite-src", 1, obj);
                }
            case 10:
                try {
                    return rotation(((Number) obj).doubleValue());
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "rotation", 1, obj);
                }
        }
    }

    public static WithTransform withTransform(AffineTransform transform, Paintable pic) {
        return new WithTransform(pic, transform);
    }

    @Override // gnu.expr.ModuleBody
    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 4:
                try {
                    try {
                        return withPaint((Color) obj, (Paintable) obj2);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "with-paint", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "with-paint", 1, obj);
                }
            case 11:
                try {
                    try {
                        return withTransform((AffineTransform) obj, (Paintable) obj2);
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "with-transform", 2, obj2);
                    }
                } catch (ClassCastException e4) {
                    throw new WrongType(e4, "with-transform", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static JMenuBar menubar(Object... args) {
        JMenuBar menubar2 = new JMenuBar();
        for (Object arg : args) {
            menubar2.add((JMenu) arg);
        }
        return menubar2;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static javax.swing.JMenu menu(java.lang.Object... r6) {
        /*
            javax.swing.JMenu r2 = new javax.swing.JMenu
            r2.<init>()
            int r3 = r6.length
            r1 = 0
        L7:
            if (r1 >= r3) goto L34
            r0 = r6[r1]
            gnu.expr.Keyword r5 = gnu.kawa.slib.swing.Lit1
            if (r0 != r5) goto L23
            r4 = 1
        L10:
            if (r4 == 0) goto L25
            int r5 = r1 + 1
            if (r5 >= r3) goto L27
        L16:
            int r5 = r1 + 1
            r5 = r6[r5]
            if (r5 != 0) goto L2f
            r5 = 0
        L1d:
            r2.setText(r5)
            int r1 = r1 + 2
            goto L7
        L23:
            r4 = 0
            goto L10
        L25:
            if (r4 != 0) goto L16
        L27:
            javax.swing.JMenuItem r0 = (javax.swing.JMenuItem) r0
            r2.add(r0)
            int r1 = r1 + 1
            goto L7
        L2f:
            java.lang.String r5 = r5.toString()
            goto L1d
        L34:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.swing.menu(java.lang.Object[]):javax.swing.JMenu");
    }

    public static JMenuItem menuitem$V(Object[] argsArray) {
        Object searchForKeyword = Keyword.searchForKeyword(argsArray, 0, Lit1, null);
        String label = searchForKeyword == null ? null : searchForKeyword.toString();
        Keyword.searchForKeyword(argsArray, 0, Lit2, null);
        Keyword.searchForKeyword(argsArray, 0, Lit3, null);
        Object oncommand = Keyword.searchForKeyword(argsArray, 0, Lit4, null);
        Object disabled = Keyword.searchForKeyword(argsArray, 0, Lit5, Boolean.FALSE);
        Keyword.searchForKeyword(argsArray, 0, Lit6, null);
        JMenuItem menuitem2 = new JMenuItem();
        if (disabled != Boolean.FALSE) {
            menuitem2.setEnabled(false);
        }
        if (label != null) {
            menuitem2.setText(label);
        }
        if (oncommand != null) {
            menuitem2.addActionListener(makeActionListener(oncommand));
        }
        return menuitem2;
    }

    public static Object polygon(Complex initial, Object... more$Mnpoints) {
        GeneralPath path = new GeneralPath();
        path.moveTo(numbers.realPart(initial).doubleValue(), numbers.imagPart(initial).doubleValue());
        for (Object obj : more$Mnpoints) {
            try {
                Complex pt = (Complex) obj;
                path.lineTo(numbers.realPart(pt).doubleValue(), numbers.imagPart(pt).doubleValue());
            } catch (ClassCastException e) {
                throw new WrongType(e, "pt", -2, obj);
            }
        }
        path.closePath();
        return path;
    }

    public static JScrollPane scroll$V(Object contents, Object[] argsArray) {
        Object w = Keyword.searchForKeyword(argsArray, 0, Lit7, Boolean.FALSE);
        Object h = Keyword.searchForKeyword(argsArray, 0, Lit8, Boolean.FALSE);
        if (contents instanceof Paintable) {
            try {
                contents = new SwingPaintable((Paintable) contents);
            } catch (ClassCastException e) {
                throw new WrongType(e, "gnu.kawa.swingviews.SwingPaintable.<init>(gnu.kawa.models.Paintable)", 1, contents);
            }
        }
        try {
            JScrollPane scr = new JScrollPane((java.awt.Component) contents);
            try {
                try {
                    scr.setPreferredSize(new Dimension(((Number) w).intValue(), ((Number) h).intValue()));
                    return scr;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "java.awt.Dimension.<init>(int,int)", 2, h);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "java.awt.Dimension.<init>(int,int)", 1, w);
            }
        } catch (ClassCastException e4) {
            throw new WrongType(e4, "javax.swing.JScrollPane.<init>(java.awt.Component)", 1, contents);
        }
    }

    @Override // gnu.expr.ModuleBody
    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 5:
                return withComposite(objArr);
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            default:
                return super.applyN(moduleMethod, objArr);
            case 12:
                return menubar(objArr);
            case 13:
                return menu(objArr);
            case 14:
                return menuitem$V(objArr);
            case 15:
                Object obj = objArr[0];
                try {
                    Complex complex = (Complex) obj;
                    int length = objArr.length - 1;
                    Object[] objArr2 = new Object[length];
                    while (true) {
                        length--;
                        if (length < 0) {
                            return polygon(complex, objArr2);
                        }
                        objArr2[length] = objArr[length + 1];
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "polygon", 1, obj);
                }
            case 16:
                Object obj2 = objArr[0];
                int length2 = objArr.length - 1;
                Object[] objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return scroll$V(obj2, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 1];
                }
        }
    }
}
