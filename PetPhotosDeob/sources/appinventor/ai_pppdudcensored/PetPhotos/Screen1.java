package appinventor.ai_pppdudcensored.PetPhotos;

import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Image;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.Sharing;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.Web;
import com.google.appinventor.components.runtime.errors.PermissionException;
import com.google.appinventor.components.runtime.errors.StopBlocksExecution;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.RetValManager;
import com.google.appinventor.components.runtime.util.RuntimeErrorAlert;
import com.google.youngandroid.runtime;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lang.Promise;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.require;

/* compiled from: Screen1.yail */
/* loaded from: classes.dex */
public class Screen1 extends Form implements Runnable {
    static final SimpleSymbol Lit0;
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit11;
    static final SimpleSymbol Lit12;
    static final SimpleSymbol Lit13;
    static final SimpleSymbol Lit14;
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit16;
    static final FString Lit17;
    static final SimpleSymbol Lit18;
    static final SimpleSymbol Lit19;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit20;
    static final IntNum Lit21;
    static final SimpleSymbol Lit22;
    static final SimpleSymbol Lit23;
    static final IntNum Lit24;
    static final FString Lit25;
    static final FString Lit26;
    static final SimpleSymbol Lit27;
    static final SimpleSymbol Lit28;
    static final FString Lit29;
    static final SimpleSymbol Lit3;
    static final FString Lit30;
    static final SimpleSymbol Lit31;
    static final SimpleSymbol Lit32;
    static final FString Lit33;
    static final FString Lit34;
    static final SimpleSymbol Lit35;
    static final FString Lit36;
    static final FString Lit37;
    static final SimpleSymbol Lit38;
    static final FString Lit39;
    static final SimpleSymbol Lit4;
    static final FString Lit40;
    static final SimpleSymbol Lit41;
    static final FString Lit42;
    static final SimpleSymbol Lit43;
    static final SimpleSymbol Lit44;
    static final PairWithPosition Lit45;
    static final SimpleSymbol Lit46;
    static final SimpleSymbol Lit47;
    static final SimpleSymbol Lit48;
    static final FString Lit49;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit50;
    static final FString Lit51;
    static final PairWithPosition Lit52;
    static final SimpleSymbol Lit53;
    static final FString Lit54;
    static final SimpleSymbol Lit55;
    static final FString Lit56;
    static final SimpleSymbol Lit57;
    static final SimpleSymbol Lit58;
    static final SimpleSymbol Lit59;
    static final IntNum Lit6;
    static final PairWithPosition Lit60;
    static final SimpleSymbol Lit61;
    static final FString Lit62;
    static final SimpleSymbol Lit63;
    static final FString Lit64;
    static final FString Lit65;
    static final FString Lit66;
    static final SimpleSymbol Lit67;
    static final SimpleSymbol Lit68;
    static final PairWithPosition Lit69;
    static final SimpleSymbol Lit7;
    static final IntNum Lit70;
    static final PairWithPosition Lit71;
    static final SimpleSymbol Lit8;
    static final SimpleSymbol Lit9;
    public static Screen1 Screen1;
    static final ModuleMethod lambda$Fn1 = null;
    static final ModuleMethod lambda$Fn10 = null;
    static final ModuleMethod lambda$Fn11 = null;
    static final ModuleMethod lambda$Fn12 = null;
    static final ModuleMethod lambda$Fn13 = null;
    static final ModuleMethod lambda$Fn14 = null;
    static final ModuleMethod lambda$Fn15 = null;
    static final ModuleMethod lambda$Fn16 = null;
    static final ModuleMethod lambda$Fn2 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public Button Button1;
    public final ModuleMethod Button1$Click;
    public Button Button2;
    public final ModuleMethod Button2$Click;
    public Button Button4;
    public final ModuleMethod Button4$Click;
    public HorizontalArrangement Horizontal_Arrangement1;
    public HorizontalArrangement Horizontal_Arrangement2;
    public Image Image1;
    public Label Label1;
    public Label Label2;
    public Sharing Sharing1;
    public TextBox Text_Box1;
    public Web Web1;
    public final ModuleMethod Web1$GotText;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public final ModuleMethod android$Mnlog$Mnform;
    public LList components$Mnto$Mncreate;
    public final ModuleMethod dispatchEvent;
    public final ModuleMethod dispatchGenericEvent;
    public LList events$Mnto$Mnregister;
    public LList form$Mndo$Mnafter$Mncreation;
    public Environment form$Mnenvironment;
    public Symbol form$Mnname$Mnsymbol;
    public final ModuleMethod get$Mnsimple$Mnname;
    public Environment global$Mnvar$Mnenvironment;
    public LList global$Mnvars$Mnto$Mncreate;
    public final ModuleMethod is$Mnbound$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod lookup$Mnhandler;
    public final ModuleMethod lookup$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod onCreate;
    public final ModuleMethod process$Mnexception;
    public final ModuleMethod send$Mnerror;
    static final SimpleSymbol Lit91 = (SimpleSymbol) new SimpleSymbol("any").readResolve();
    static final SimpleSymbol Lit90 = (SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve();
    static final SimpleSymbol Lit89 = (SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve();
    static final SimpleSymbol Lit88 = (SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve();
    static final SimpleSymbol Lit87 = (SimpleSymbol) new SimpleSymbol("send-error").readResolve();
    static final SimpleSymbol Lit86 = (SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve();
    static final SimpleSymbol Lit85 = (SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve();
    static final SimpleSymbol Lit84 = (SimpleSymbol) new SimpleSymbol("add-to-components").readResolve();
    static final SimpleSymbol Lit83 = (SimpleSymbol) new SimpleSymbol("add-to-events").readResolve();
    static final SimpleSymbol Lit82 = (SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve();
    static final SimpleSymbol Lit81 = (SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve();
    static final SimpleSymbol Lit80 = (SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve();
    static final SimpleSymbol Lit79 = (SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve();
    static final SimpleSymbol Lit78 = (SimpleSymbol) new SimpleSymbol("android-log-form").readResolve();
    static final SimpleSymbol Lit77 = (SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve();
    static final FString Lit76 = new FString("com.google.appinventor.components.runtime.Sharing");
    static final FString Lit75 = new FString("com.google.appinventor.components.runtime.Sharing");
    static final SimpleSymbol Lit74 = (SimpleSymbol) new SimpleSymbol("GotText").readResolve();
    static final SimpleSymbol Lit73 = (SimpleSymbol) new SimpleSymbol("Web1$GotText").readResolve();
    static final PairWithPosition Lit72 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("key").readResolve(), PairWithPosition.make(Lit91, PairWithPosition.make(Lit91, LList.Empty, "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 422251), "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 422247), "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 422242);

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit7 = simpleSymbol;
        Lit71 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("list").readResolve(), PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 422200), "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 422194);
        Lit70 = IntNum.make(1);
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit9 = simpleSymbol2;
        Lit69 = PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 422182);
        Lit68 = (SimpleSymbol) new SimpleSymbol("$responseContent").readResolve();
        Lit67 = (SimpleSymbol) new SimpleSymbol("JsonTextDecodeWithDictionaries").readResolve();
        Lit66 = new FString("com.google.appinventor.components.runtime.Web");
        Lit65 = new FString("com.google.appinventor.components.runtime.Web");
        Lit64 = new FString("com.google.appinventor.components.runtime.Label");
        Lit63 = (SimpleSymbol) new SimpleSymbol("Label2").readResolve();
        Lit62 = new FString("com.google.appinventor.components.runtime.Label");
        Lit61 = (SimpleSymbol) new SimpleSymbol("Button4$Click").readResolve();
        Lit60 = PairWithPosition.make(Lit9, LList.Empty, "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 348266);
        Lit59 = (SimpleSymbol) new SimpleSymbol("Picture").readResolve();
        Lit58 = (SimpleSymbol) new SimpleSymbol("ShareMessage").readResolve();
        Lit57 = (SimpleSymbol) new SimpleSymbol("Sharing1").readResolve();
        Lit56 = new FString("com.google.appinventor.components.runtime.Button");
        Lit55 = (SimpleSymbol) new SimpleSymbol("Button4").readResolve();
        Lit54 = new FString("com.google.appinventor.components.runtime.Button");
        Lit53 = (SimpleSymbol) new SimpleSymbol("Button2$Click").readResolve();
        Lit52 = PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, LList.Empty, "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 311488), "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 311482);
        Lit51 = new FString("com.google.appinventor.components.runtime.Button");
        Lit50 = (SimpleSymbol) new SimpleSymbol("Button2").readResolve();
        Lit49 = new FString("com.google.appinventor.components.runtime.Button");
        Lit48 = (SimpleSymbol) new SimpleSymbol("Click").readResolve();
        Lit47 = (SimpleSymbol) new SimpleSymbol("Button1$Click").readResolve();
        Lit46 = (SimpleSymbol) new SimpleSymbol("Get").readResolve();
        Lit45 = PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, LList.Empty, "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 274624), "/tmp/1692043838131_0.4387258950825239-0/youngandroidproject/../src/appinventor/ai_pppdudcensored/PetPhotos/Screen1.yail", 274618);
        Lit44 = (SimpleSymbol) new SimpleSymbol("Url").readResolve();
        Lit43 = (SimpleSymbol) new SimpleSymbol("Web1").readResolve();
        Lit42 = new FString("com.google.appinventor.components.runtime.Button");
        Lit41 = (SimpleSymbol) new SimpleSymbol("Button1").readResolve();
        Lit40 = new FString("com.google.appinventor.components.runtime.Button");
        Lit39 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit38 = (SimpleSymbol) new SimpleSymbol("Horizontal_Arrangement1").readResolve();
        Lit37 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit36 = new FString("com.google.appinventor.components.runtime.TextBox");
        Lit35 = (SimpleSymbol) new SimpleSymbol("Text_Box1").readResolve();
        Lit34 = new FString("com.google.appinventor.components.runtime.TextBox");
        Lit33 = new FString("com.google.appinventor.components.runtime.Label");
        Lit32 = (SimpleSymbol) new SimpleSymbol("Text").readResolve();
        Lit31 = (SimpleSymbol) new SimpleSymbol("Label1").readResolve();
        Lit30 = new FString("com.google.appinventor.components.runtime.Label");
        Lit29 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit28 = (SimpleSymbol) new SimpleSymbol("Visible").readResolve();
        Lit27 = (SimpleSymbol) new SimpleSymbol("Horizontal_Arrangement2").readResolve();
        Lit26 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
        Lit25 = new FString("com.google.appinventor.components.runtime.Image");
        Lit24 = IntNum.make(-2);
        Lit23 = (SimpleSymbol) new SimpleSymbol("Width").readResolve();
        Lit22 = (SimpleSymbol) new SimpleSymbol("ScalePictureToFit").readResolve();
        Lit21 = IntNum.make(-1050);
        Lit20 = (SimpleSymbol) new SimpleSymbol("Height").readResolve();
        Lit19 = (SimpleSymbol) new SimpleSymbol("AlternateText").readResolve();
        Lit18 = (SimpleSymbol) new SimpleSymbol("Image1").readResolve();
        Lit17 = new FString("com.google.appinventor.components.runtime.Image");
        Lit16 = (SimpleSymbol) new SimpleSymbol("TitleVisible").readResolve();
        Lit15 = (SimpleSymbol) new SimpleSymbol("Title").readResolve();
        Lit14 = (SimpleSymbol) new SimpleSymbol("Theme").readResolve();
        Lit13 = (SimpleSymbol) new SimpleSymbol("Sizing").readResolve();
        Lit12 = (SimpleSymbol) new SimpleSymbol("ShowStatusBar").readResolve();
        Lit11 = (SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve();
        Lit10 = (SimpleSymbol) new SimpleSymbol("Scrollable").readResolve();
        Lit8 = (SimpleSymbol) new SimpleSymbol("AppName").readResolve();
        Lit6 = IntNum.make(3);
        Lit5 = (SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve();
        Lit4 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve();
        Lit3 = (SimpleSymbol) new SimpleSymbol("ActionBar").readResolve();
        Lit2 = (SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("getMessage").readResolve();
        Lit0 = (SimpleSymbol) new SimpleSymbol("Screen1").readResolve();
    }

    public Screen1() {
        ModuleInfo.register(this);
        frame frameVar = new frame();
        frameVar.$main = this;
        this.get$Mnsimple$Mnname = new ModuleMethod(frameVar, 1, Lit77, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frameVar, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frameVar, 3, Lit78, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frameVar, 4, Lit79, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frameVar, 5, Lit80, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frameVar, 7, Lit81, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frameVar, 8, Lit82, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frameVar, 9, Lit83, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frameVar, 10, Lit84, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frameVar, 11, Lit85, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frameVar, 12, Lit86, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frameVar, 13, Lit87, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frameVar, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frameVar, 15, Lit88, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frameVar, 16, Lit89, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frameVar, 17, Lit90, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frameVar, 18, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime8043461690708621934.scm:634");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frameVar, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frameVar, 20, null, 0);
        lambda$Fn3 = new ModuleMethod(frameVar, 21, null, 0);
        lambda$Fn4 = new ModuleMethod(frameVar, 22, null, 0);
        lambda$Fn5 = new ModuleMethod(frameVar, 23, null, 0);
        lambda$Fn6 = new ModuleMethod(frameVar, 24, null, 0);
        lambda$Fn7 = new ModuleMethod(frameVar, 25, null, 0);
        lambda$Fn8 = new ModuleMethod(frameVar, 26, null, 0);
        lambda$Fn9 = new ModuleMethod(frameVar, 27, null, 0);
        lambda$Fn10 = new ModuleMethod(frameVar, 28, null, 0);
        this.Button1$Click = new ModuleMethod(frameVar, 29, Lit47, 0);
        lambda$Fn11 = new ModuleMethod(frameVar, 30, null, 0);
        lambda$Fn12 = new ModuleMethod(frameVar, 31, null, 0);
        this.Button2$Click = new ModuleMethod(frameVar, 32, Lit53, 0);
        lambda$Fn13 = new ModuleMethod(frameVar, 33, null, 0);
        lambda$Fn14 = new ModuleMethod(frameVar, 34, null, 0);
        this.Button4$Click = new ModuleMethod(frameVar, 35, Lit61, 0);
        lambda$Fn15 = new ModuleMethod(frameVar, 36, null, 0);
        lambda$Fn16 = new ModuleMethod(frameVar, 37, null, 0);
        this.Web1$GotText = new ModuleMethod(frameVar, 38, Lit73, 16388);
    }

    public Object lookupInFormEnvironment(Symbol symbol) {
        return lookupInFormEnvironment(symbol, Boolean.FALSE);
    }

    @Override // java.lang.Runnable
    public void run() {
        CallContext callContext = CallContext.getInstance();
        Consumer consumer = callContext.consumer;
        callContext.consumer = VoidConsumer.instance;
        try {
            run(callContext);
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        ModuleBody.runCleanup(callContext, th, consumer);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        Object find = require.find("com.google.youngandroid.runtime");
        try {
            ((Runnable) find).run();
            this.$Stdebug$Mnform$St = Boolean.FALSE;
            this.form$Mnenvironment = Environment.make(Lit0.toString());
            FString stringAppend = strings.stringAppend(Lit0.toString(), "-global-vars");
            this.global$Mnvar$Mnenvironment = Environment.make(stringAppend == null ? null : stringAppend.toString());
            Screen1 = null;
            this.form$Mnname$Mnsymbol = Lit0;
            this.events$Mnto$Mnregister = LList.Empty;
            this.components$Mnto$Mncreate = LList.Empty;
            this.global$Mnvars$Mnto$Mncreate = LList.Empty;
            this.form$Mndo$Mnafter$Mncreation = LList.Empty;
            Object find2 = require.find("com.google.youngandroid.runtime");
            try {
                ((Runnable) find2).run();
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit3, Boolean.TRUE, Lit4);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit5, Lit6, Lit7);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit8, "Pet Photos", Lit9);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit10, Boolean.TRUE, Lit4);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit11, Boolean.TRUE, Lit4);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit12, Boolean.FALSE, Lit4);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit13, "Responsive", Lit9);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit14, "AppTheme.Light.DarkActionBar", Lit9);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "Screen1", Lit9);
                    Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Boolean.FALSE, Lit4), $result);
                } else {
                    addToFormDoAfterCreation(new Promise(lambda$Fn2));
                }
                this.Image1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit17, Lit18, lambda$Fn3), $result);
                } else {
                    addToComponents(Lit0, Lit25, Lit18, lambda$Fn4);
                }
                this.Horizontal_Arrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit26, Lit27, lambda$Fn5), $result);
                } else {
                    addToComponents(Lit0, Lit29, Lit27, lambda$Fn6);
                }
                this.Label1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit27, Lit30, Lit31, lambda$Fn7), $result);
                } else {
                    addToComponents(Lit27, Lit33, Lit31, lambda$Fn8);
                }
                this.Text_Box1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit27, Lit34, Lit35, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit27, Lit36, Lit35, Boolean.FALSE);
                }
                this.Horizontal_Arrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit37, Lit38, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit39, Lit38, Boolean.FALSE);
                }
                this.Button1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit38, Lit40, Lit41, lambda$Fn9), $result);
                } else {
                    addToComponents(Lit38, Lit42, Lit41, lambda$Fn10);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit47, this.Button1$Click);
                } else {
                    addToFormEnvironment(Lit47, this.Button1$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button1", "Click");
                } else {
                    addToEvents(Lit41, Lit48);
                }
                this.Button2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit38, Lit49, Lit50, lambda$Fn11), $result);
                } else {
                    addToComponents(Lit38, Lit51, Lit50, lambda$Fn12);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit53, this.Button2$Click);
                } else {
                    addToFormEnvironment(Lit53, this.Button2$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "Click");
                } else {
                    addToEvents(Lit50, Lit48);
                }
                this.Button4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit38, Lit54, Lit55, lambda$Fn13), $result);
                } else {
                    addToComponents(Lit38, Lit56, Lit55, lambda$Fn14);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit61, this.Button4$Click);
                } else {
                    addToFormEnvironment(Lit61, this.Button4$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button4", "Click");
                } else {
                    addToEvents(Lit55, Lit48);
                }
                this.Label2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit62, Lit63, lambda$Fn15), $result);
                } else {
                    addToComponents(Lit0, Lit64, Lit63, lambda$Fn16);
                }
                this.Web1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit65, Lit43, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit66, Lit43, Boolean.FALSE);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit73, this.Web1$GotText);
                } else {
                    addToFormEnvironment(Lit73, this.Web1$GotText);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Web1", "GotText");
                } else {
                    addToEvents(Lit43, Lit74);
                }
                this.Sharing1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit75, Lit57, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit76, Lit57, Boolean.FALSE);
                }
                runtime.initRuntime();
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Runnable.run()", 1, find2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "java.lang.Runnable.run()", 1, find);
        }
    }

    static Object lambda3() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit3, Boolean.TRUE, Lit4);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit5, Lit6, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit8, "Pet Photos", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit10, Boolean.TRUE, Lit4);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit11, Boolean.TRUE, Lit4);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit12, Boolean.FALSE, Lit4);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit13, "Responsive", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit14, "AppTheme.Light.DarkActionBar", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "Screen1", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Boolean.FALSE, Lit4);
    }

    static Object lambda4() {
        runtime.setAndCoerceProperty$Ex(Lit18, Lit19, "Photo of pet", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit18, Lit20, Lit21, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit18, Lit22, Boolean.TRUE, Lit4);
        return runtime.setAndCoerceProperty$Ex(Lit18, Lit23, Lit24, Lit7);
    }

    static Object lambda5() {
        runtime.setAndCoerceProperty$Ex(Lit18, Lit19, "Photo of pet", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit18, Lit20, Lit21, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit18, Lit22, Boolean.TRUE, Lit4);
        return runtime.setAndCoerceProperty$Ex(Lit18, Lit23, Lit24, Lit7);
    }

    static Object lambda6() {
        return runtime.setAndCoerceProperty$Ex(Lit27, Lit28, Boolean.FALSE, Lit4);
    }

    static Object lambda7() {
        return runtime.setAndCoerceProperty$Ex(Lit27, Lit28, Boolean.FALSE, Lit4);
    }

    static Object lambda8() {
        return runtime.setAndCoerceProperty$Ex(Lit31, Lit32, "API Key: ", Lit9);
    }

    static Object lambda9() {
        return runtime.setAndCoerceProperty$Ex(Lit31, Lit32, "API Key: ", Lit9);
    }

    static Object lambda10() {
        return runtime.setAndCoerceProperty$Ex(Lit41, Lit32, "Get Cat Photo", Lit9);
    }

    static Object lambda11() {
        return runtime.setAndCoerceProperty$Ex(Lit41, Lit32, "Get Cat Photo", Lit9);
    }

    public Object Button1$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit43, Lit44, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("https://api.thecatapi.com/v1/images/search?api_key=", runtime.getProperty$1(Lit35, Lit32)), Lit45, "join"), Lit9);
        return runtime.callComponentMethod(Lit43, Lit46, LList.Empty, LList.Empty);
    }

    static Object lambda12() {
        return runtime.setAndCoerceProperty$Ex(Lit50, Lit32, "Get Dog Photo", Lit9);
    }

    static Object lambda13() {
        return runtime.setAndCoerceProperty$Ex(Lit50, Lit32, "Get Dog Photo", Lit9);
    }

    public Object Button2$Click() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit43, Lit44, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("https://api.thedogapi.com/v1/images/search?api_key=", runtime.getProperty$1(Lit35, Lit32)), Lit52, "join"), Lit9);
        return runtime.callComponentMethod(Lit43, Lit46, LList.Empty, LList.Empty);
    }

    static Object lambda14() {
        return runtime.setAndCoerceProperty$Ex(Lit55, Lit32, "Share link", Lit9);
    }

    static Object lambda15() {
        return runtime.setAndCoerceProperty$Ex(Lit55, Lit32, "Share link", Lit9);
    }

    public Object Button4$Click() {
        runtime.setThisForm();
        return runtime.callComponentMethod(Lit57, Lit58, LList.list1(runtime.getProperty$1(Lit18, Lit59)), Lit60);
    }

    static Object lambda16() {
        runtime.setAndCoerceProperty$Ex(Lit63, Lit32, "Using an API key widens the selection of images available.", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit63, Lit28, Boolean.FALSE, Lit4);
        return runtime.setAndCoerceProperty$Ex(Lit63, Lit23, Lit24, Lit7);
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit63, Lit32, "Using an API key widens the selection of images available.", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit63, Lit28, Boolean.FALSE, Lit4);
        return runtime.setAndCoerceProperty$Ex(Lit63, Lit23, Lit24, Lit7);
    }

    public Object Web1$GotText(Object $url, Object $responseCode, Object $responseType, Object $responseContent) {
        runtime.sanitizeComponentData($url);
        runtime.sanitizeComponentData($responseCode);
        runtime.sanitizeComponentData($responseType);
        Object $responseContent2 = runtime.sanitizeComponentData($responseContent);
        runtime.setThisForm();
        SimpleSymbol simpleSymbol = Lit18;
        SimpleSymbol simpleSymbol2 = Lit59;
        ModuleMethod moduleMethod = runtime.yail$Mndictionary$Mnlookup;
        ModuleMethod moduleMethod2 = runtime.yail$Mnlist$Mnget$Mnitem;
        SimpleSymbol simpleSymbol3 = Lit43;
        SimpleSymbol simpleSymbol4 = Lit67;
        if ($responseContent2 instanceof Package) {
            $responseContent2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit68), " is not bound in the current context"), "Unbound Variable");
        }
        return runtime.setAndCoerceProperty$Ex(simpleSymbol, simpleSymbol2, runtime.callYailPrimitive(moduleMethod, LList.list3("url", runtime.callYailPrimitive(moduleMethod2, LList.list2(runtime.callComponentMethod(simpleSymbol3, simpleSymbol4, LList.list1($responseContent2), Lit69), Lit70), Lit71, "select list item"), "not found"), Lit72, "dictionary lookup"), Lit9);
    }

    /* compiled from: Screen1.yail */
    /* loaded from: classes.dex */
    public class frame extends ModuleBody {
        Screen1 $main;

        @Override // gnu.expr.ModuleBody
        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    if (obj instanceof Screen1) {
                        callContext.value1 = obj;
                        callContext.proc = moduleMethod;
                        callContext.pc = 1;
                        return 0;
                    }
                    return -786431;
                case 3:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 4:
                case 6:
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    return super.match1(moduleMethod, obj, callContext);
                case 5:
                    if (obj instanceof Symbol) {
                        callContext.value1 = obj;
                        callContext.proc = moduleMethod;
                        callContext.pc = 1;
                        return 0;
                    }
                    return -786431;
                case 7:
                    if (obj instanceof Symbol) {
                        callContext.value1 = obj;
                        callContext.proc = moduleMethod;
                        callContext.pc = 1;
                        return 0;
                    }
                    return -786431;
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
                case 14:
                    if (obj instanceof Screen1) {
                        callContext.value1 = obj;
                        callContext.proc = moduleMethod;
                        callContext.pc = 1;
                        return 0;
                    }
                    return -786431;
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 4:
                    if (obj instanceof Symbol) {
                        callContext.value1 = obj;
                        callContext.value2 = obj2;
                        callContext.proc = moduleMethod;
                        callContext.pc = 2;
                        return 0;
                    }
                    return -786431;
                case 5:
                    if (obj instanceof Symbol) {
                        callContext.value1 = obj;
                        callContext.value2 = obj2;
                        callContext.proc = moduleMethod;
                        callContext.pc = 2;
                        return 0;
                    }
                    return -786431;
                case 6:
                case 7:
                case 10:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
                case 8:
                    if (obj instanceof Symbol) {
                        callContext.value1 = obj;
                        callContext.value2 = obj2;
                        callContext.proc = moduleMethod;
                        callContext.pc = 2;
                        return 0;
                    }
                    return -786431;
                case 9:
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
                case 17:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 10:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                case 15:
                    if (obj instanceof Screen1) {
                        callContext.value1 = obj;
                        if (obj2 instanceof Component) {
                            callContext.value2 = obj2;
                            if (obj3 instanceof String) {
                                callContext.value3 = obj3;
                                if (obj4 instanceof String) {
                                    callContext.value4 = obj4;
                                    callContext.proc = moduleMethod;
                                    callContext.pc = 4;
                                    return 0;
                                }
                                return -786428;
                            }
                            return -786429;
                        }
                        return -786430;
                    }
                    return -786431;
                case 16:
                    if (obj instanceof Screen1) {
                        callContext.value1 = obj;
                        if (obj2 instanceof Component) {
                            callContext.value2 = obj2;
                            if (obj3 instanceof String) {
                                callContext.value3 = obj3;
                                callContext.value4 = obj4;
                                callContext.proc = moduleMethod;
                                callContext.pc = 4;
                                return 0;
                            }
                            return -786429;
                        }
                        return -786430;
                    }
                    return -786431;
                case 38:
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

        @Override // gnu.expr.ModuleBody
        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 1:
                    return this.$main.getSimpleName(obj);
                case 2:
                    try {
                        this.$main.onCreate((Bundle) obj);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "onCreate", 1, obj);
                    }
                case 3:
                    this.$main.androidLogForm(obj);
                    return Values.empty;
                case 4:
                case 6:
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    return super.apply1(moduleMethod, obj);
                case 5:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 7:
                    try {
                        return this.$main.isBoundInFormEnvironment((Symbol) obj) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "is-bound-in-form-environment", 1, obj);
                    }
                case 12:
                    this.$main.addToFormDoAfterCreation(obj);
                    return Values.empty;
                case 13:
                    this.$main.sendError(obj);
                    return Values.empty;
                case 14:
                    this.$main.processException(obj);
                    return Values.empty;
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            switch (moduleMethod.selector) {
                case 10:
                    this.$main.addToComponents(obj, obj2, obj3, obj4);
                    return Values.empty;
                case 15:
                    try {
                        try {
                            try {
                                try {
                                    return this.$main.dispatchEvent((Component) obj, (String) obj2, (String) obj3, (Object[]) obj4) ? Boolean.TRUE : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "dispatchEvent", 4, obj4);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "dispatchEvent", 3, obj3);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "dispatchEvent", 2, obj2);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "dispatchEvent", 1, obj);
                    }
                case 16:
                    try {
                        try {
                            try {
                                try {
                                    this.$main.dispatchGenericEvent((Component) obj, (String) obj2, obj3 != Boolean.FALSE, (Object[]) obj4);
                                    return Values.empty;
                                } catch (ClassCastException e5) {
                                    throw new WrongType(e5, "dispatchGenericEvent", 4, obj4);
                                }
                            } catch (ClassCastException e6) {
                                throw new WrongType(e6, "dispatchGenericEvent", 3, obj3);
                            }
                        } catch (ClassCastException e7) {
                            throw new WrongType(e7, "dispatchGenericEvent", 2, obj2);
                        }
                    } catch (ClassCastException e8) {
                        throw new WrongType(e8, "dispatchGenericEvent", 1, obj);
                    }
                case 38:
                    return this.$main.Web1$GotText(obj, obj2, obj3, obj4);
                default:
                    return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 4:
                    try {
                        this.$main.addToFormEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "add-to-form-environment", 1, obj);
                    }
                case 5:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj, obj2);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 6:
                case 7:
                case 10:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                default:
                    return super.apply2(moduleMethod, obj, obj2);
                case 8:
                    try {
                        this.$main.addToGlobalVarEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "add-to-global-var-environment", 1, obj);
                    }
                case 9:
                    this.$main.addToEvents(obj, obj2);
                    return Values.empty;
                case 11:
                    this.$main.addToGlobalVars(obj, obj2);
                    return Values.empty;
                case 17:
                    return this.$main.lookupHandler(obj, obj2);
            }
        }

        @Override // gnu.expr.ModuleBody
        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 18:
                    return Screen1.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Screen1.lambda3();
                case 21:
                    return Screen1.lambda4();
                case 22:
                    return Screen1.lambda5();
                case 23:
                    return Screen1.lambda6();
                case 24:
                    return Screen1.lambda7();
                case 25:
                    return Screen1.lambda8();
                case 26:
                    return Screen1.lambda9();
                case 27:
                    return Screen1.lambda10();
                case 28:
                    return Screen1.lambda11();
                case 29:
                    return this.$main.Button1$Click();
                case 30:
                    return Screen1.lambda12();
                case 31:
                    return Screen1.lambda13();
                case 32:
                    return this.$main.Button2$Click();
                case 33:
                    return Screen1.lambda14();
                case 34:
                    return Screen1.lambda15();
                case 35:
                    return this.$main.Button4$Click();
                case 36:
                    return Screen1.lambda16();
                case 37:
                    return Screen1.lambda17();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        @Override // gnu.expr.ModuleBody
        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 18:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 19:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 20:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 21:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 22:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 23:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 24:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 25:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 26:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 27:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 28:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 29:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 30:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 31:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 32:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 33:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 34:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 35:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 36:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 37:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    public String getSimpleName(Object object) {
        return object.getClass().getSimpleName();
    }

    @Override // com.google.appinventor.components.runtime.Form, com.google.appinventor.components.runtime.AppInventorCompatActivity, android.app.Activity
    public void onCreate(Bundle icicle) {
        AppInventorCompatActivity.setClassicModeFromYail(false);
        super.onCreate(icicle);
    }

    public void androidLogForm(Object message) {
    }

    public void addToFormEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.form$Mnenvironment, object));
        this.form$Mnenvironment.put(name, object);
    }

    public Object lookupInFormEnvironment(Symbol name, Object default$Mnvalue) {
        int i = ((this.form$Mnenvironment == null ? 1 : 0) + 1) & 1;
        if (i != 0) {
            if (!this.form$Mnenvironment.isBound(name)) {
                return default$Mnvalue;
            }
        } else if (i == 0) {
            return default$Mnvalue;
        }
        return this.form$Mnenvironment.get(name);
    }

    public boolean isBoundInFormEnvironment(Symbol name) {
        return this.form$Mnenvironment.isBound(name);
    }

    public void addToGlobalVarEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.global$Mnvar$Mnenvironment, object));
        this.global$Mnvar$Mnenvironment.put(name, object);
    }

    public void addToEvents(Object component$Mnname, Object event$Mnname) {
        this.events$Mnto$Mnregister = lists.cons(lists.cons(component$Mnname, event$Mnname), this.events$Mnto$Mnregister);
    }

    public void addToComponents(Object container$Mnname, Object component$Mntype, Object component$Mnname, Object init$Mnthunk) {
        this.components$Mnto$Mncreate = lists.cons(LList.list4(container$Mnname, component$Mntype, component$Mnname, init$Mnthunk), this.components$Mnto$Mncreate);
    }

    public void addToGlobalVars(Object var, Object val$Mnthunk) {
        this.global$Mnvars$Mnto$Mncreate = lists.cons(LList.list2(var, val$Mnthunk), this.global$Mnvars$Mnto$Mncreate);
    }

    public void addToFormDoAfterCreation(Object thunk) {
        this.form$Mndo$Mnafter$Mncreation = lists.cons(thunk, this.form$Mndo$Mnafter$Mncreation);
    }

    public void sendError(Object error) {
        RetValManager.sendError(error == null ? null : error.toString());
    }

    public void processException(Object ex) {
        Object apply1 = Scheme.applyToArgs.apply1(GetNamedPart.getNamedPart.apply2(ex, Lit1));
        RuntimeErrorAlert.alert(this, apply1 == null ? null : apply1.toString(), ex instanceof YailRuntimeError ? ((YailRuntimeError) ex).getErrorType() : "Runtime Error", "End Application");
    }

    @Override // com.google.appinventor.components.runtime.Form, com.google.appinventor.components.runtime.HandlesEventDispatching
    public boolean dispatchEvent(Component componentObject, String registeredComponentName, String eventName, Object[] args) {
        SimpleSymbol registeredObject = misc.string$To$Symbol(registeredComponentName);
        if (isBoundInFormEnvironment(registeredObject)) {
            if (lookupInFormEnvironment(registeredObject) == componentObject) {
                Object handler = lookupHandler(registeredComponentName, eventName);
                try {
                    Scheme.apply.apply2(handler, LList.makeList(args, 0));
                    return true;
                } catch (PermissionException exception) {
                    exception.printStackTrace();
                    boolean x = this == componentObject;
                    if (!x ? x : IsEqual.apply(eventName, "PermissionNeeded")) {
                        processException(exception);
                    } else {
                        PermissionDenied(componentObject, eventName, exception.getPermissionNeeded());
                    }
                    return false;
                } catch (StopBlocksExecution e) {
                    return false;
                } catch (Throwable exception2) {
                    androidLogForm(exception2.getMessage());
                    exception2.printStackTrace();
                    processException(exception2);
                    return false;
                }
            }
            return false;
        }
        EventDispatcher.unregisterEventForDelegation(this, registeredComponentName, eventName);
        return false;
    }

    @Override // com.google.appinventor.components.runtime.Form, com.google.appinventor.components.runtime.HandlesEventDispatching
    public void dispatchGenericEvent(Component componentObject, String eventName, boolean notAlreadyHandled, Object[] args) {
        SimpleSymbol handler$Mnsymbol = misc.string$To$Symbol(strings.stringAppend("any$", getSimpleName(componentObject), "$", eventName));
        Object handler = lookupInFormEnvironment(handler$Mnsymbol);
        if (handler != Boolean.FALSE) {
            try {
                Scheme.apply.apply2(handler, lists.cons(componentObject, lists.cons(notAlreadyHandled ? Boolean.TRUE : Boolean.FALSE, LList.makeList(args, 0))));
            } catch (PermissionException exception) {
                exception.printStackTrace();
                boolean x = this == componentObject;
                if (!x ? x : IsEqual.apply(eventName, "PermissionNeeded")) {
                    processException(exception);
                } else {
                    PermissionDenied(componentObject, eventName, exception.getPermissionNeeded());
                }
            } catch (StopBlocksExecution e) {
            } catch (Throwable exception2) {
                androidLogForm(exception2.getMessage());
                exception2.printStackTrace();
                processException(exception2);
            }
        }
    }

    public Object lookupHandler(Object componentName, Object eventName) {
        return lookupInFormEnvironment(misc.string$To$Symbol(EventDispatcher.makeFullEventName(componentName == null ? null : componentName.toString(), eventName != null ? eventName.toString() : null)));
    }

    @Override // com.google.appinventor.components.runtime.Form
    public void $define() {
        Object obj;
        Language.setDefaults(Scheme.getInstance());
        try {
            run();
        } catch (Exception exception) {
            androidLogForm(exception.getMessage());
            processException(exception);
        }
        Screen1 = this;
        addToFormEnvironment(Lit0, this);
        LList events = this.events$Mnto$Mnregister;
        Object obj2 = events;
        while (obj2 != LList.Empty) {
            try {
                Pair arg0 = (Pair) obj2;
                Object event$Mninfo = arg0.getCar();
                Object apply1 = lists.car.apply1(event$Mninfo);
                String obj3 = apply1 == null ? null : apply1.toString();
                Object apply12 = lists.cdr.apply1(event$Mninfo);
                EventDispatcher.registerEventForDelegation(this, obj3, apply12 == null ? null : apply12.toString());
                obj2 = arg0.getCdr();
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, obj2);
            }
        }
        try {
            LList components = lists.reverse(this.components$Mnto$Mncreate);
            addToGlobalVars(Lit2, lambda$Fn1);
            Object reverse = lists.reverse(this.form$Mndo$Mnafter$Mncreation);
            while (reverse != LList.Empty) {
                try {
                    Pair arg02 = (Pair) reverse;
                    misc.force(arg02.getCar());
                    reverse = arg02.getCdr();
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "arg0", -2, reverse);
                }
            }
            Object obj4 = components;
            while (obj4 != LList.Empty) {
                try {
                    Pair arg03 = (Pair) obj4;
                    Object component$Mninfo = arg03.getCar();
                    Object component$Mnname = lists.caddr.apply1(component$Mninfo);
                    lists.cadddr.apply1(component$Mninfo);
                    Object component$Mntype = lists.cadr.apply1(component$Mninfo);
                    try {
                        Object component$Mncontainer = lookupInFormEnvironment((Symbol) lists.car.apply1(component$Mninfo));
                        Object component$Mnobject = Invoke.make.apply2(component$Mntype, component$Mncontainer);
                        SlotSet.set$Mnfield$Ex.apply3(this, component$Mnname, component$Mnobject);
                        try {
                            addToFormEnvironment((Symbol) component$Mnname, component$Mnobject);
                            obj4 = arg03.getCdr();
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "add-to-form-environment", 0, component$Mnname);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "lookup-in-form-environment", 0, obj);
                    }
                } catch (ClassCastException e5) {
                    throw new WrongType(e5, "arg0", -2, obj4);
                }
            }
            LList var$Mnval$Mnpairs = lists.reverse(this.global$Mnvars$Mnto$Mncreate);
            Object obj5 = var$Mnval$Mnpairs;
            while (obj5 != LList.Empty) {
                try {
                    Pair arg04 = (Pair) obj5;
                    Object var$Mnval = arg04.getCar();
                    Object var = lists.car.apply1(var$Mnval);
                    Object val$Mnthunk = lists.cadr.apply1(var$Mnval);
                    try {
                        addToGlobalVarEnvironment((Symbol) var, Scheme.applyToArgs.apply1(val$Mnthunk));
                        obj5 = arg04.getCdr();
                    } catch (ClassCastException e6) {
                        throw new WrongType(e6, "add-to-global-var-environment", 0, var);
                    }
                } catch (ClassCastException e7) {
                    throw new WrongType(e7, "arg0", -2, obj5);
                }
            }
            Object obj6 = components;
            while (obj6 != LList.Empty) {
                try {
                    Pair arg05 = (Pair) obj6;
                    Object component$Mninfo2 = arg05.getCar();
                    lists.caddr.apply1(component$Mninfo2);
                    Object init$Mnthunk = lists.cadddr.apply1(component$Mninfo2);
                    if (init$Mnthunk != Boolean.FALSE) {
                        Scheme.applyToArgs.apply1(init$Mnthunk);
                    }
                    obj6 = arg05.getCdr();
                } catch (ClassCastException e8) {
                    throw new WrongType(e8, "arg0", -2, obj6);
                }
            }
            Object obj7 = components;
            while (obj7 != LList.Empty) {
                try {
                    Pair arg06 = (Pair) obj7;
                    Object component$Mninfo3 = arg06.getCar();
                    Object component$Mnname2 = lists.caddr.apply1(component$Mninfo3);
                    lists.cadddr.apply1(component$Mninfo3);
                    callInitialize(SlotGet.field.apply2(this, component$Mnname2));
                    obj7 = arg06.getCdr();
                } catch (ClassCastException e9) {
                    throw new WrongType(e9, "arg0", -2, obj7);
                }
            }
        } catch (YailRuntimeError exception2) {
            processException(exception2);
        }
    }

    public static SimpleSymbol lambda1symbolAppend$V(Object[] argsArray) {
        LList symbols = LList.makeList(argsArray, 0);
        Apply apply = Scheme.apply;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Object obj = LList.Empty;
        LList lList = symbols;
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

    static Object lambda2() {
        return null;
    }
}
