package com.google.appinventor.components.runtime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.appinventor.components.annotations.Asset;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.common.FileScope;
import com.google.appinventor.components.common.HorizontalAlignment;
import com.google.appinventor.components.common.Permission;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.common.ScreenAnimation;
import com.google.appinventor.components.common.ScreenOrientation;
import com.google.appinventor.components.common.VerticalAlignment;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.collect.Lists;
import com.google.appinventor.components.runtime.collect.Maps;
import com.google.appinventor.components.runtime.collect.Sets;
import com.google.appinventor.components.runtime.errors.PermissionException;
import com.google.appinventor.components.runtime.multidex.MultiDex;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.AlignmentUtil;
import com.google.appinventor.components.runtime.util.AnimationUtil;
import com.google.appinventor.components.runtime.util.BulkPermissionRequest;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;
import com.google.appinventor.components.runtime.util.FullScreenVideoUtil;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.OnInitializeListener;
import com.google.appinventor.components.runtime.util.PaintUtil;
import com.google.appinventor.components.runtime.util.ScreenDensityUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import kawa.lang.SyntaxForms;
import org.json.JSONException;

@UsesPermissions({"android.permission.INTERNET"})
@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "Top-level component containing all other components in the program", showOnPalette = JSONzip.probe, version = 30)
@SimpleObject
/* loaded from: classes.dex */
public class Form extends AppInventorCompatActivity implements Component, ComponentContainer, HandlesEventDispatching, ViewTreeObserver.OnGlobalLayoutListener {
    public static final String APPINVENTOR_URL_SCHEME = "appinventor";
    private static final String ARGUMENT_NAME = "APP_INVENTOR_START";
    public static final String ASSETS_PREFIX = "file:///android_asset/";
    private static final String LOG_TAG = "Form";
    public static final int MAX_PERMISSION_NONCE = 100000;
    private static final String RESULT_NAME = "APP_INVENTOR_RESULT";
    private static final int SWITCH_FORM_REQUEST_CODE = 1;
    protected static Form activeForm;
    private static boolean applicationIsBeingClosed;
    private static boolean sCompatibilityMode;
    private static boolean showListsAsJson;
    private String aboutScreen;
    private AlignmentUtil alignmentSetter;
    private int backgroundColor;
    private Drawable backgroundDrawable;
    private boolean bigDefaultText;
    private float compatScalingFactor;
    private float deviceDensity;
    private int formHeight;
    protected String formName;
    private int formWidth;
    private FrameLayout frameLayout;
    private FullScreenVideoUtil fullScreenVideoUtil;
    private boolean highContrast;
    private HorizontalAlignment horizontalAlignment;
    private String nextFormName;
    private ProgressDialog progress;
    private ScaledFrameLayout scaleLayout;
    private boolean screenInitialized;
    private boolean scrollable;
    private boolean usesDarkTheme;
    private boolean usesDefaultBackground;
    private VerticalAlignment verticalAlignment;
    private LinearLayout viewLayout;
    private static final int DEFAULT_PRIMARY_COLOR_DARK = PaintUtil.hexStringToInt(ComponentConstants.DEFAULT_PRIMARY_DARK_COLOR);
    private static final int DEFAULT_ACCENT_COLOR = PaintUtil.hexStringToInt(ComponentConstants.DEFAULT_ACCENT_COLOR);
    private static int nextRequestCode = 2;
    private static long minimumToastWait = 10000000000L;
    private static boolean _initialized = false;
    private List<Component> allChildren = new ArrayList();
    protected final Handler androidUIHandler = new Handler();
    private boolean showStatusBar = true;
    private boolean showTitle = true;
    protected String title = "";
    private String backgroundImagePath = "";
    private ScreenAnimation openAnimType = ScreenAnimation.Default;
    private ScreenAnimation closeAnimType = ScreenAnimation.Default;
    private int primaryColor = DEFAULT_PRIMARY_COLOR;
    private int primaryColorDark = DEFAULT_PRIMARY_COLOR_DARK;
    private int accentColor = DEFAULT_ACCENT_COLOR;
    private final Set<String> permissions = new HashSet();
    private FileScope defaultFileScope = FileScope.App;
    private final HashMap<Integer, ActivityResultListener> activityResultMap = Maps.newHashMap();
    private final java.util.Map<Integer, Set<ActivityResultListener>> activityResultMultiMap = Maps.newHashMap();
    private final Set<OnStopListener> onStopListeners = Sets.newHashSet();
    private final Set<OnClearListener> onClearListeners = Sets.newHashSet();
    private final Set<OnNewIntentListener> onNewIntentListeners = Sets.newHashSet();
    private final Set<OnResumeListener> onResumeListeners = Sets.newHashSet();
    private final Set<OnOrientationChangeListener> onOrientationChangeListeners = Sets.newHashSet();
    private final Set<OnPauseListener> onPauseListeners = Sets.newHashSet();
    private final Set<OnDestroyListener> onDestroyListeners = Sets.newHashSet();
    private final Set<OnInitializeListener> onInitializeListeners = Sets.newHashSet();
    private final Set<OnCreateOptionsMenuListener> onCreateOptionsMenuListeners = Sets.newHashSet();
    private final Set<OnOptionsItemSelectedListener> onOptionsItemSelectedListeners = Sets.newHashSet();
    private final HashMap<Integer, PermissionResultHandler> permissionHandlers = Maps.newHashMap();
    private final Random permissionRandom = new Random();
    protected String startupValue = "";
    private long lastToastTime = System.nanoTime() - minimumToastWait;
    private boolean actionBarEnabled = false;
    private boolean keyboardShown = false;
    private LinkedHashMap<Integer, PercentStorageRecord> dimChanges = new LinkedHashMap<>();

    /* loaded from: classes.dex */
    public static class PercentStorageRecord {
        AndroidViewComponent component;
        Dim dim;
        int length;

        /* loaded from: classes.dex */
        public enum Dim {
            HEIGHT,
            WIDTH
        }

        public PercentStorageRecord(AndroidViewComponent component, int length, Dim dim) {
            this.component = component;
            this.length = length;
            this.dim = dim;
        }
    }

    /* loaded from: classes.dex */
    private static class MultiDexInstaller extends AsyncTask<Form, Void, Boolean> {
        Form ourForm;

        private MultiDexInstaller() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Boolean doInBackground(Form... form) {
            this.ourForm = form[0];
            Log.d(Form.LOG_TAG, "Doing Full MultiDex Install");
            MultiDex.install(this.ourForm, true);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Boolean v) {
            this.ourForm.onCreateFinish();
        }
    }

    @Override // com.google.appinventor.components.runtime.AppInventorCompatActivity, android.app.Activity
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        String className = getClass().getName();
        int lastDot = className.lastIndexOf(46);
        this.formName = className.substring(lastDot + 1);
        Log.d(LOG_TAG, "Form " + this.formName + " got onCreate");
        activeForm = this;
        Log.i(LOG_TAG, "activeForm is now " + activeForm.formName);
        this.deviceDensity = getResources().getDisplayMetrics().density;
        Log.d(LOG_TAG, "deviceDensity = " + this.deviceDensity);
        this.compatScalingFactor = ScreenDensityUtil.computeCompatibleScaling(this);
        Log.i(LOG_TAG, "compatScalingFactor = " + this.compatScalingFactor);
        this.viewLayout = new LinearLayout(this, 0);
        this.alignmentSetter = new AlignmentUtil(this.viewLayout);
        this.progress = null;
        if (!_initialized && this.formName.equals("Screen1")) {
            Log.d(LOG_TAG, "MULTI: _initialized = " + _initialized + " formName = " + this.formName);
            _initialized = true;
            if (ReplApplication.installed) {
                Log.d(LOG_TAG, "MultiDex already installed.");
                onCreateFinish();
                return;
            }
            this.progress = ProgressDialog.show(this, "Please Wait...", "Installation Finishing");
            this.progress.show();
            new MultiDexInstaller().execute(this);
            return;
        }
        Log.d(LOG_TAG, "NO MULTI: _initialized = " + _initialized + " formName = " + this.formName);
        _initialized = true;
        onCreateFinish();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onCreateFinish() {
        Log.d(LOG_TAG, "onCreateFinish called " + System.currentTimeMillis());
        if (this.progress != null) {
            this.progress.dismiss();
        }
        populatePermissions();
        defaultPropertyValues();
        Intent startIntent = getIntent();
        if (startIntent != null && startIntent.hasExtra(ARGUMENT_NAME)) {
            this.startupValue = startIntent.getStringExtra(ARGUMENT_NAME);
        }
        this.fullScreenVideoUtil = new FullScreenVideoUtil(this, this.androidUIHandler);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        int softInputMode = params.softInputMode;
        getWindow().setSoftInputMode(softInputMode | 16);
        $define();
        Initialize();
    }

    private void populatePermissions() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 4096);
            Collections.addAll(this.permissions, packageInfo.requestedPermissions);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception while attempting to learn permissions.", e);
        }
    }

    private void defaultPropertyValues() {
        if (isRepl()) {
            ActionBar(this.actionBarEnabled);
        } else {
            ActionBar(this.themeHelper.hasActionBar());
        }
        Scrollable(false);
        HighContrast(false);
        BigDefaultText(false);
        Sizing("Responsive");
        AboutScreen("");
        BackgroundImage("");
        AlignHorizontal(1);
        AlignVertical(1);
        Title("");
        ShowStatusBar(true);
        TitleVisible(true);
        ShowListsAsJson(true);
        ActionBar(false);
        AccentColor(DEFAULT_ACCENT_COLOR);
        PrimaryColor(DEFAULT_PRIMARY_COLOR);
        PrimaryColorDark(DEFAULT_PRIMARY_COLOR_DARK);
        Theme(ComponentConstants.DEFAULT_THEME);
        ScreenOrientation("unspecified");
        BackgroundColor(0);
        OpenScreenAnimationAbstract(ScreenAnimation.Default);
        CloseScreenAnimationAbstract(ScreenAnimation.Default);
        DefaultFileScope(FileScope.App);
    }

    @Override // com.google.appinventor.components.runtime.AppInventorCompatActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(LOG_TAG, "onConfigurationChanged() called");
        final int newOrientation = newConfig.orientation;
        if (newOrientation == 2 || newOrientation == 1) {
            this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.1
                @Override // java.lang.Runnable
                public void run() {
                    boolean dispatchEventNow = false;
                    if (Form.this.frameLayout != null) {
                        if (newOrientation == 2) {
                            if (Form.this.frameLayout.getWidth() >= Form.this.frameLayout.getHeight()) {
                                dispatchEventNow = true;
                            }
                        } else if (Form.this.frameLayout.getHeight() >= Form.this.frameLayout.getWidth()) {
                            dispatchEventNow = true;
                        }
                    }
                    if (dispatchEventNow) {
                        Form.this.recomputeLayout();
                        final FrameLayout savedLayout = Form.this.frameLayout;
                        Form.this.androidUIHandler.postDelayed(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (savedLayout != null) {
                                    savedLayout.invalidate();
                                }
                            }
                        }, 100L);
                        Form.this.ScreenOrientationChanged();
                        return;
                    }
                    Form.this.androidUIHandler.post(this);
                }
            });
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        int totalHeight = this.scaleLayout.getRootView().getHeight();
        int scaledHeight = this.scaleLayout.getHeight();
        int heightDiff = totalHeight - scaledHeight;
        float diffPercent = heightDiff / totalHeight;
        Log.d(LOG_TAG, "onGlobalLayout(): diffPercent = " + diffPercent);
        if (diffPercent < 0.25d) {
            Log.d(LOG_TAG, "keyboard hidden!");
            if (this.keyboardShown) {
                this.keyboardShown = false;
                if (sCompatibilityMode) {
                    this.scaleLayout.setScale(this.compatScalingFactor);
                    this.scaleLayout.invalidate();
                    return;
                }
                return;
            }
            return;
        }
        Log.d(LOG_TAG, "keyboard shown!");
        this.keyboardShown = true;
        if (this.scaleLayout != null) {
            this.scaleLayout.setScale(1.0f);
            this.scaleLayout.invalidate();
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (!BackPressed()) {
            AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnimType);
            super.onBackPressed();
        }
    }

    @SimpleEvent(description = "Device back button pressed.")
    public boolean BackPressed() {
        return EventDispatcher.dispatchEvent(this, "BackPressed", new Object[0]);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResultListener[] activityResultListenerArr;
        String resultString;
        Log.i(LOG_TAG, "Form " + this.formName + " got onActivityResult, requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (requestCode == 1) {
            if (data != null && data.hasExtra(RESULT_NAME)) {
                resultString = data.getStringExtra(RESULT_NAME);
            } else {
                resultString = "";
            }
            Object decodedResult = decodeJSONStringForForm(resultString, "other screen closed");
            OtherScreenClosed(this.nextFormName, decodedResult);
            return;
        }
        ActivityResultListener component = this.activityResultMap.get(Integer.valueOf(requestCode));
        if (component != null) {
            component.resultReturned(requestCode, resultCode, data);
        }
        Set<ActivityResultListener> listeners = this.activityResultMultiMap.get(Integer.valueOf(requestCode));
        if (listeners != null) {
            for (ActivityResultListener listener : (ActivityResultListener[]) listeners.toArray(new ActivityResultListener[0])) {
                listener.resultReturned(requestCode, resultCode, data);
            }
        }
    }

    private static Object decodeJSONStringForForm(String jsonString, String functionName) {
        Log.i(LOG_TAG, "decodeJSONStringForForm -- decoding JSON representation:" + jsonString);
        Object valueFromJSON = "";
        try {
            valueFromJSON = JsonUtil.getObjectFromJson(jsonString, true);
            Log.i(LOG_TAG, "decodeJSONStringForForm -- got decoded JSON:" + valueFromJSON.toString());
            return valueFromJSON;
        } catch (JSONException e) {
            activeForm.dispatchErrorOccurredEvent(activeForm, functionName, ErrorMessages.ERROR_SCREEN_BAD_VALUE_RECEIVED, jsonString);
            return valueFromJSON;
        }
    }

    public int registerForActivityResult(ActivityResultListener listener) {
        int requestCode = generateNewRequestCode();
        this.activityResultMap.put(Integer.valueOf(requestCode), listener);
        return requestCode;
    }

    public void registerForActivityResult(ActivityResultListener listener, int requestCode) {
        Set<ActivityResultListener> listeners = this.activityResultMultiMap.get(Integer.valueOf(requestCode));
        if (listeners == null) {
            listeners = Sets.newHashSet();
            this.activityResultMultiMap.put(Integer.valueOf(requestCode), listeners);
        }
        listeners.add(listener);
    }

    public void unregisterForActivityResult(ActivityResultListener listener) {
        List<Integer> keysToDelete = Lists.newArrayList();
        for (Map.Entry<Integer, ActivityResultListener> mapEntry : this.activityResultMap.entrySet()) {
            if (listener.equals(mapEntry.getValue())) {
                keysToDelete.add(mapEntry.getKey());
            }
        }
        for (Integer key : keysToDelete) {
            this.activityResultMap.remove(key);
        }
        Iterator<Map.Entry<Integer, Set<ActivityResultListener>>> it = this.activityResultMultiMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Set<ActivityResultListener>> entry = it.next();
            entry.getValue().remove(listener);
            if (entry.getValue().size() == 0) {
                it.remove();
            }
        }
    }

    void ReplayFormOrientation() {
        Log.d(LOG_TAG, "ReplayFormOrientation()");
        LinkedHashMap<Integer, PercentStorageRecord> temp = (LinkedHashMap) this.dimChanges.clone();
        this.dimChanges.clear();
        for (PercentStorageRecord r : temp.values()) {
            if (r.dim == PercentStorageRecord.Dim.HEIGHT) {
                r.component.Height(r.length);
            } else {
                r.component.Width(r.length);
            }
        }
    }

    private Integer generateHashCode(AndroidViewComponent component, PercentStorageRecord.Dim dim) {
        return dim == PercentStorageRecord.Dim.HEIGHT ? Integer.valueOf((component.hashCode() * 2) + 1) : Integer.valueOf(component.hashCode() * 2);
    }

    public void registerPercentLength(AndroidViewComponent component, int length, PercentStorageRecord.Dim dim) {
        PercentStorageRecord r = new PercentStorageRecord(component, length, dim);
        Integer key = generateHashCode(component, dim);
        this.dimChanges.put(key, r);
    }

    public void unregisterPercentLength(AndroidViewComponent component, PercentStorageRecord.Dim dim) {
        this.dimChanges.remove(generateHashCode(component, dim));
    }

    private static int generateNewRequestCode() {
        int i = nextRequestCode;
        nextRequestCode = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "Form " + this.formName + " got onResume");
        activeForm = this;
        if (applicationIsBeingClosed) {
            closeApplication();
            return;
        }
        for (OnResumeListener onResumeListener : this.onResumeListeners) {
            onResumeListener.onResume();
        }
    }

    public void registerForOnResume(OnResumeListener component) {
        this.onResumeListeners.add(component);
    }

    public void registerForOnOrientationChange(OnOrientationChangeListener component) {
        this.onOrientationChangeListeners.add(component);
    }

    public void registerForOnInitialize(OnInitializeListener component) {
        this.onInitializeListeners.add(component);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(LOG_TAG, "Form " + this.formName + " got onNewIntent " + intent);
        for (OnNewIntentListener onNewIntentListener : this.onNewIntentListeners) {
            onNewIntentListener.onNewIntent(intent);
        }
    }

    public void registerForOnNewIntent(OnNewIntentListener component) {
        this.onNewIntentListeners.add(component);
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "Form " + this.formName + " got onPause");
        for (OnPauseListener onPauseListener : this.onPauseListeners) {
            onPauseListener.onPause();
        }
    }

    public void registerForOnPause(OnPauseListener component) {
        this.onPauseListeners.add(component);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.AppInventorCompatActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "Form " + this.formName + " got onStop");
        for (OnStopListener onStopListener : this.onStopListeners) {
            onStopListener.onStop();
        }
    }

    public void registerForOnStop(OnStopListener component) {
        this.onStopListeners.add(component);
    }

    public void registerForOnClear(OnClearListener component) {
        this.onClearListeners.add(component);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.appinventor.components.runtime.AppInventorCompatActivity, android.app.Activity
    public void onDestroy() {
        Log.i(LOG_TAG, "Form " + this.formName + " got onDestroy");
        EventDispatcher.removeDispatchDelegate(this);
        for (OnDestroyListener onDestroyListener : this.onDestroyListeners) {
            onDestroyListener.onDestroy();
        }
        super.onDestroy();
    }

    public void registerForOnDestroy(OnDestroyListener component) {
        this.onDestroyListeners.add(component);
    }

    public void registerForOnCreateOptionsMenu(OnCreateOptionsMenuListener component) {
        this.onCreateOptionsMenuListeners.add(component);
    }

    public void registerForOnOptionsItemSelected(OnOptionsItemSelectedListener component) {
        this.onOptionsItemSelectedListeners.add(component);
    }

    @Override // android.app.Activity
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG /* 189 */:
                return this.fullScreenVideoUtil.createFullScreenVideoDialog();
            default:
                return super.onCreateDialog(id);
        }
    }

    @Override // android.app.Activity
    public void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case FullScreenVideoUtil.FULLSCREEN_VIDEO_DIALOG_FLAG /* 189 */:
                this.fullScreenVideoUtil.prepareFullScreenVideoDialog(dialog);
                return;
            default:
                super.onPrepareDialog(id, dialog);
                return;
        }
    }

    protected void $define() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.appinventor.components.runtime.HandlesEventDispatching
    public boolean canDispatchEvent(Component component, String eventName) {
        boolean canDispatch = this.screenInitialized || (component == this && eventName.equals("Initialize"));
        if (canDispatch) {
            activeForm = this;
        }
        return canDispatch;
    }

    public boolean dispatchEvent(Component component, String componentName, String eventName, Object[] args) {
        throw new UnsupportedOperationException();
    }

    public void dispatchGenericEvent(Component component, String eventName, boolean notAlreadyHandled, Object[] args) {
        throw new UnsupportedOperationException();
    }

    @SimpleEvent(description = "The Initialize event is run when the Screen starts and is only run once per screen.")
    public void Initialize() {
        this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.2
            @Override // java.lang.Runnable
            public void run() {
                if (Form.this.frameLayout != null && Form.this.frameLayout.getWidth() != 0 && Form.this.frameLayout.getHeight() != 0) {
                    EventDispatcher.dispatchEvent(Form.this, "Initialize", new Object[0]);
                    if (Form.sCompatibilityMode) {
                        Form.this.Sizing("Fixed");
                    } else {
                        Form.this.Sizing("Responsive");
                    }
                    Form.this.screenInitialized = true;
                    for (OnInitializeListener onInitializeListener : Form.this.onInitializeListeners) {
                        onInitializeListener.onInitialize();
                    }
                    if (Form.activeForm instanceof ReplForm) {
                        ((ReplForm) Form.activeForm).HandleReturnValues();
                        return;
                    }
                    return;
                }
                Form.this.androidUIHandler.post(this);
            }
        });
    }

    @SimpleEvent(description = "Screen orientation changed")
    public void ScreenOrientationChanged() {
        for (OnOrientationChangeListener onOrientationChangeListener : this.onOrientationChangeListeners) {
            onOrientationChangeListener.onOrientationChange();
        }
        EventDispatcher.dispatchEvent(this, "ScreenOrientationChanged", new Object[0]);
    }

    @SimpleEvent(description = "Event raised when an error occurs. Only some errors will raise this condition.  For those errors, the system will show a notification by default.  You can use this event handler to prescribe an error behavior different than the default.")
    public void ErrorOccurred(Component component, String functionName, int errorNumber, String message) {
        String componentType = component.getClass().getName();
        Log.e(LOG_TAG, "Form " + this.formName + " ErrorOccurred, errorNumber = " + errorNumber + ", componentType = " + componentType.substring(componentType.lastIndexOf(".") + 1) + ", functionName = " + functionName + ", messages = " + message);
        if (!EventDispatcher.dispatchEvent(this, "ErrorOccurred", component, functionName, Integer.valueOf(errorNumber), message) && this.screenInitialized) {
            new Notifier(this).ShowAlert("Error " + errorNumber + ": " + message);
        }
    }

    public void ErrorOccurredDialog(Component component, String functionName, int errorNumber, String message, String title, String buttonText) {
        String componentType = component.getClass().getName();
        Log.e(LOG_TAG, "Form " + this.formName + " ErrorOccurred, errorNumber = " + errorNumber + ", componentType = " + componentType.substring(componentType.lastIndexOf(".") + 1) + ", functionName = " + functionName + ", messages = " + message);
        if (!EventDispatcher.dispatchEvent(this, "ErrorOccurred", component, functionName, Integer.valueOf(errorNumber), message) && this.screenInitialized) {
            new Notifier(this).ShowMessageDialog("Error " + errorNumber + ": " + message, title, buttonText);
        }
    }

    public void dispatchPermissionDeniedEvent(Component component, String functionName, PermissionException exception) {
        exception.printStackTrace();
        dispatchPermissionDeniedEvent(component, functionName, exception.getPermissionNeeded());
    }

    public void dispatchPermissionDeniedEvent(final Component component, final String functionName, final String permissionName) {
        runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.3
            @Override // java.lang.Runnable
            public void run() {
                Form.this.PermissionDenied(component, functionName, permissionName);
            }
        });
    }

    @Override // com.google.appinventor.components.runtime.HandlesEventDispatching
    public void dispatchErrorOccurredEvent(final Component component, final String functionName, final int errorNumber, final Object... messageArgs) {
        runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.4
            @Override // java.lang.Runnable
            public void run() {
                String message = ErrorMessages.formatMessage(errorNumber, messageArgs);
                Form.this.ErrorOccurred(component, functionName, errorNumber, message);
            }
        });
    }

    public void dispatchErrorOccurredEventDialog(final Component component, final String functionName, final int errorNumber, final Object... messageArgs) {
        runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.5
            @Override // java.lang.Runnable
            public void run() {
                String message = ErrorMessages.formatMessage(errorNumber, messageArgs);
                Form.this.ErrorOccurredDialog(component, functionName, errorNumber, message, "Error in " + functionName, "Dismiss");
            }
        });
    }

    public void runtimeFormErrorOccurredEvent(String functionName, int errorNumber, String message) {
        Log.d("FORM_RUNTIME_ERROR", "functionName is " + functionName);
        Log.d("FORM_RUNTIME_ERROR", "errorNumber is " + errorNumber);
        Log.d("FORM_RUNTIME_ERROR", "message is " + message);
        dispatchErrorOccurredEvent(activeForm, functionName, errorNumber, message);
    }

    @SimpleEvent
    public void PermissionDenied(Component component, String functionName, @Options(Permission.class) String permissionName) {
        if (permissionName.startsWith("android.permission.")) {
            permissionName = permissionName.replace("android.permission.", "");
        }
        if (!EventDispatcher.dispatchEvent(this, "PermissionDenied", component, functionName, permissionName)) {
            dispatchErrorOccurredEvent(component, functionName, ErrorMessages.ERROR_PERMISSION_DENIED, permissionName);
        }
    }

    @SimpleEvent(description = "Event to handle when the app user has granted a needed permission. This event is only run when permission is granted in response to the AskForPermission method.")
    public void PermissionGranted(@Options(Permission.class) String permissionName) {
        if (permissionName.startsWith("android.permission.")) {
            permissionName = permissionName.replace("android.permission.", "");
        }
        EventDispatcher.dispatchEvent(this, "PermissionGranted", permissionName);
    }

    @SimpleFunction(description = "Ask the user to grant access to a dangerous permission.")
    public void AskForPermission(@Options(Permission.class) String permissionName) {
        if (!permissionName.contains(".")) {
            permissionName = "android.permission." + permissionName;
        }
        askPermission(permissionName, new PermissionResultHandler() { // from class: com.google.appinventor.components.runtime.Form.6
            @Override // com.google.appinventor.components.runtime.PermissionResultHandler
            public void HandlePermissionResponse(String permission, boolean granted) {
                if (granted) {
                    Form.this.PermissionGranted(permission);
                } else {
                    Form.this.PermissionDenied(Form.this, "RequestPermission", permission);
                }
            }
        });
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "When checked, we will use high contrast mode")
    public boolean HighContrast() {
        return this.highContrast;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void HighContrast(boolean highContrast) {
        this.highContrast = highContrast;
        setHighContrastRecursive(this, highContrast);
        recomputeLayout();
    }

    private static void setHighContrastRecursive(ComponentContainer container, boolean enabled) {
        for (Component child : container.getChildren()) {
            if (child instanceof ComponentContainer) {
                setHighContrastRecursive((ComponentContainer) child, enabled);
            } else if (child instanceof AccessibleComponent) {
                ((AccessibleComponent) child).setHighContrast(enabled);
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "When checked, we will use high contrast mode")
    public boolean BigDefaultText() {
        return this.bigDefaultText;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void BigDefaultText(boolean bigDefaultText) {
        this.bigDefaultText = bigDefaultText;
        setBigDefaultTextRecursive(this, bigDefaultText);
        recomputeLayout();
    }

    private static void setBigDefaultTextRecursive(ComponentContainer container, boolean enabled) {
        for (Component child : container.getChildren()) {
            if (child instanceof ComponentContainer) {
                setBigDefaultTextRecursive((ComponentContainer) child, enabled);
            } else if (child instanceof AccessibleComponent) {
                ((AccessibleComponent) child).setLargeFont(enabled);
            }
        }
    }

    public void AskForPermissionAbstract(Permission permission) {
        AskForPermission(permission.toUnderlyingValue());
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "When checked, there will be a vertical scrollbar on the screen, and the height of the application can exceed the physical height of the device. When unchecked, the application height is constrained to the height of the device.")
    public boolean Scrollable() {
        return this.scrollable;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void Scrollable(boolean scrollable) {
        if (this.scrollable != scrollable || this.frameLayout == null) {
            this.scrollable = scrollable;
            recomputeLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recomputeLayout() {
        Log.d(LOG_TAG, "recomputeLayout called");
        if (this.frameLayout != null) {
            this.frameLayout.removeAllViews();
        }
        boolean needsTitleBar = this.titleBar != null && this.titleBar.getParent() == this.frameWithTitle;
        this.frameWithTitle.removeAllViews();
        if (needsTitleBar) {
            this.frameWithTitle.addView(this.titleBar, new ViewGroup.LayoutParams(-1, -2));
        }
        if (this.scrollable) {
            this.frameLayout = new ScrollView(this);
            if (Build.VERSION.SDK_INT >= 24) {
                ((ScrollView) this.frameLayout).setFillViewport(true);
            }
        } else {
            this.frameLayout = new FrameLayout(this);
        }
        this.frameLayout.addView(this.viewLayout.getLayoutManager(), new ViewGroup.LayoutParams(-1, -1));
        setBackground(this.frameLayout);
        Log.d(LOG_TAG, "About to create a new ScaledFrameLayout");
        this.scaleLayout = new ScaledFrameLayout(this);
        this.scaleLayout.addView(this.frameLayout, new ViewGroup.LayoutParams(-1, -1));
        this.frameWithTitle.addView(this.scaleLayout, new ViewGroup.LayoutParams(-1, -1));
        this.frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
        this.scaleLayout.requestLayout();
        this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.7
            @Override // java.lang.Runnable
            public void run() {
                if (Form.this.frameLayout != null && Form.this.frameLayout.getWidth() != 0 && Form.this.frameLayout.getHeight() != 0) {
                    if (Form.sCompatibilityMode) {
                        Form.this.Sizing("Fixed");
                    } else {
                        Form.this.Sizing("Responsive");
                    }
                    Form.this.ReplayFormOrientation();
                    Form.this.frameWithTitle.requestLayout();
                    return;
                }
                Form.this.androidUIHandler.post(this);
            }
        });
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.DEFAULT_VALUE_COLOR_WHITE, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void BackgroundColor(int argb) {
        if (argb == 0) {
            this.usesDefaultBackground = true;
        } else {
            this.usesDefaultBackground = false;
            this.backgroundColor = argb;
        }
        setBackground(this.frameLayout);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The screen background image.")
    public String BackgroundImage() {
        return this.backgroundImagePath;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The screen background image.")
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void BackgroundImage(@Asset String path) {
        if (path == null) {
            path = "";
        }
        this.backgroundImagePath = path;
        try {
            this.backgroundDrawable = MediaUtil.getBitmapDrawable(this, this.backgroundImagePath);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to load " + this.backgroundImagePath);
            this.backgroundDrawable = null;
        }
        setBackground(this.frameLayout);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "App", editorType = PropertyTypeConstants.PROPERTY_TYPE_FILESCOPE)
    public void DefaultFileScope(FileScope scope) {
        this.defaultFileScope = scope;
    }

    public FileScope DefaultFileScope() {
        return this.defaultFileScope;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The caption for the form, which apears in the title bar")
    public String Title() {
        return getTitle().toString();
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void Title(String title) {
        this.title = title;
        if (this.titleBar != null) {
            this.titleBar.setText(title);
        }
        setTitle(title);
        updateTitle();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Information about the screen.  It appears when \"About this Application\" is selected from the system menu. Use it to inform people about your app.  In multiple screen apps, each screen has its own AboutScreen info.")
    public String AboutScreen() {
        return this.aboutScreen;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_TEXTAREA)
    public void AboutScreen(String aboutScreen) {
        this.aboutScreen = aboutScreen;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The title bar is the top gray bar on the screen. This property reports whether the title bar is visible.")
    public boolean TitleVisible() {
        return this.showTitle;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void TitleVisible(boolean show) {
        if (show != this.showTitle) {
            this.showTitle = show;
            if (this.actionBarEnabled) {
                this.actionBarEnabled = this.themeHelper.setActionBarVisible(show);
            } else {
                maybeShowTitleBar();
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The status bar is the topmost bar on the screen. This property reports whether the status bar is visible.")
    public boolean ShowStatusBar() {
        return this.showStatusBar;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    @DesignerProperty(defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void ShowStatusBar(boolean show) {
        if (show != this.showStatusBar) {
            if (show) {
                getWindow().addFlags(2048);
                getWindow().clearFlags(1024);
            } else {
                getWindow().addFlags(1024);
                getWindow().clearFlags(2048);
            }
            this.showStatusBar = show;
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The requested screen orientation, specified as a text value.  Commonly used values are landscape, portrait, sensor, user and unspecified.  See the Android developer documentation for ActivityInfo.Screen_Orientation for the complete list of possible settings.")
    @Options(ScreenOrientation.class)
    public String ScreenOrientation() {
        return ScreenOrientationAbstract().toUnderlyingValue();
    }

    public ScreenOrientation ScreenOrientationAbstract() {
        switch (getRequestedOrientation()) {
            case -1:
                return ScreenOrientation.Unspecified;
            case 0:
                return ScreenOrientation.Landscape;
            case 1:
                return ScreenOrientation.Portrait;
            case 2:
                return ScreenOrientation.User;
            case 3:
                return ScreenOrientation.Behind;
            case 4:
                return ScreenOrientation.Sensor;
            case 5:
                return ScreenOrientation.NoSensor;
            case 6:
                return ScreenOrientation.SensorLandscape;
            case 7:
                return ScreenOrientation.SensorPortrait;
            case 8:
                return ScreenOrientation.ReverseLandscape;
            case 9:
                return ScreenOrientation.ReversePortrait;
            case 10:
                return ScreenOrientation.FullSensor;
            default:
                return ScreenOrientation.Unspecified;
        }
    }

    public void ScreenOrientationAbstract(ScreenOrientation orientation) {
        int orientationConst = orientation.getOrientationConstant();
        if (orientationConst > 5 && SdkLevel.getLevel() < 9) {
            dispatchErrorOccurredEvent(this, "ScreenOrientation", ErrorMessages.ERROR_INVALID_SCREEN_ORIENTATION, orientation);
        } else {
            setRequestedOrientation(orientationConst);
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    @SuppressLint({"SourceLockedOrientationActivity"})
    @DesignerProperty(defaultValue = "unspecified", editorType = PropertyTypeConstants.PROPERTY_TYPE_SCREEN_ORIENTATION)
    public void ScreenOrientation(@Options(ScreenOrientation.class) String screenOrientation) {
        ScreenOrientation orientation = ScreenOrientation.fromUnderlyingValue(screenOrientation);
        if (orientation == null) {
            dispatchErrorOccurredEvent(this, "ScreenOrientation", ErrorMessages.ERROR_INVALID_SCREEN_ORIENTATION, screenOrientation);
        } else {
            ScreenOrientationAbstract(orientation);
        }
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "False", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void ActionBar(boolean enabled) {
        if (SdkLevel.getLevel() >= 11 && this.actionBarEnabled != enabled) {
            setActionBarEnabled(enabled);
            if (enabled) {
                hideTitleBar();
                this.actionBarEnabled = this.themeHelper.setActionBarVisible(this.showTitle);
            } else {
                maybeShowTitleBar();
                this.actionBarEnabled = this.themeHelper.setActionBarVisible(false);
            }
            this.actionBarEnabled = enabled;
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A number that encodes how contents of the screen are aligned  horizontally. The choices are: 1 = left aligned, 3 = horizontally centered,  2 = right aligned.")
    @Options(HorizontalAlignment.class)
    public int AlignHorizontal() {
        return this.horizontalAlignment.toUnderlyingValue().intValue();
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_SANSSERIF, editorType = PropertyTypeConstants.PROPERTY_TYPE_HORIZONTAL_ALIGNMENT)
    public void AlignHorizontal(@Options(HorizontalAlignment.class) int alignment) {
        HorizontalAlignment align = HorizontalAlignment.fromUnderlyingValue(Integer.valueOf(alignment));
        if (align == null) {
            dispatchErrorOccurredEvent(this, "HorizontalAlignment", ErrorMessages.ERROR_BAD_VALUE_FOR_HORIZONTAL_ALIGNMENT, Integer.valueOf(alignment));
        } else {
            AlignHorizontalAbstract(align);
        }
    }

    public HorizontalAlignment AlignHorizontalAbstract() {
        return this.horizontalAlignment;
    }

    public void AlignHorizontalAbstract(HorizontalAlignment alignment) {
        this.alignmentSetter.setHorizontalAlignment(alignment);
        this.horizontalAlignment = alignment;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A number that encodes how the contents of the arrangement are aligned vertically. The choices are: 1 = aligned at the top, 2 = vertically centered, 3 = aligned at the bottom. Vertical alignment has no effect if the screen is scrollable.")
    @Options(VerticalAlignment.class)
    public int AlignVertical() {
        return AlignVerticalAbstract().toUnderlyingValue().intValue();
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = Component.TYPEFACE_SANSSERIF, editorType = PropertyTypeConstants.PROPERTY_TYPE_VERTICAL_ALIGNMENT)
    public void AlignVertical(@Options(VerticalAlignment.class) int alignment) {
        VerticalAlignment align = VerticalAlignment.fromUnderlyingValue(Integer.valueOf(alignment));
        if (align == null) {
            dispatchErrorOccurredEvent(this, "VerticalAlignment", ErrorMessages.ERROR_BAD_VALUE_FOR_VERTICAL_ALIGNMENT, Integer.valueOf(alignment));
        } else {
            AlignVerticalAbstract(align);
        }
    }

    public VerticalAlignment AlignVerticalAbstract() {
        return this.verticalAlignment;
    }

    public void AlignVerticalAbstract(VerticalAlignment alignment) {
        this.alignmentSetter.setVerticalAlignment(alignment);
        this.verticalAlignment = alignment;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The animation for switching to another screen. Valid options are default, fade, zoom, slidehorizontal, slidevertical, and none")
    @Options(ScreenAnimation.class)
    public String OpenScreenAnimation() {
        if (this.openAnimType != null) {
            return this.openAnimType.toUnderlyingValue();
        }
        return null;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "default", editorType = PropertyTypeConstants.PROPERTY_TYPE_SCREEN_ANIMATION)
    public void OpenScreenAnimation(@Options(ScreenAnimation.class) String animType) {
        ScreenAnimation anim = ScreenAnimation.fromUnderlyingValue(animType);
        if (anim == null) {
            dispatchErrorOccurredEvent(this, "Screen", ErrorMessages.ERROR_SCREEN_INVALID_ANIMATION, animType);
        } else {
            OpenScreenAnimationAbstract(anim);
        }
    }

    public ScreenAnimation OpenScreenAnimationAbstract() {
        return this.openAnimType;
    }

    public void OpenScreenAnimationAbstract(ScreenAnimation animType) {
        this.openAnimType = animType;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The animation for closing current screen and returning  to the previous screen. Valid options are default, fade, zoom, slidehorizontal, slidevertical, and none")
    @Options(ScreenAnimation.class)
    public String CloseScreenAnimation() {
        if (this.closeAnimType != null) {
            return CloseScreenAnimationAbstract().toUnderlyingValue();
        }
        return null;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "default", editorType = PropertyTypeConstants.PROPERTY_TYPE_SCREEN_ANIMATION)
    public void CloseScreenAnimation(@Options(ScreenAnimation.class) String animType) {
        ScreenAnimation anim = ScreenAnimation.fromUnderlyingValue(animType);
        if (anim == null) {
            dispatchErrorOccurredEvent(this, "Screen", ErrorMessages.ERROR_SCREEN_INVALID_ANIMATION, animType);
        } else {
            CloseScreenAnimationAbstract(anim);
        }
    }

    public ScreenAnimation CloseScreenAnimationAbstract() {
        return this.closeAnimType;
    }

    public void CloseScreenAnimationAbstract(ScreenAnimation animType) {
        this.closeAnimType = animType;
    }

    @SimpleProperty(userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void Icon(String name) {
    }

    @SimpleProperty(description = "An integer value which must be incremented each time a new Android Application Package File (APK) is created for the Google Play Store.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = Component.TYPEFACE_SANSSERIF, editorType = PropertyTypeConstants.PROPERTY_TYPE_NON_NEGATIVE_INTEGER)
    public void VersionCode(int vCode) {
    }

    @SimpleProperty(description = "A string which can be changed to allow Google Play Store users to distinguish between different versions of the App.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "1.0", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void VersionName(String vName) {
    }

    @SimpleProperty(description = "If set to fixed,  screen layouts will be created for a single fixed-size screen and autoscaled. If set to responsive, screen layouts will use the actual resolution of the device.  See the documentation on responsive design in App Inventor for more information. This property appears on Screen1 only and controls the sizing for all screens in the app.", userVisible = JSONzip.probe)
    @DesignerProperty(alwaysSend = SyntaxForms.DEBUGGING, defaultValue = "Responsive", editorType = PropertyTypeConstants.PROPERTY_TYPE_SIZING)
    public void Sizing(String value) {
        Log.d(LOG_TAG, "Sizing(" + value + ")");
        this.formWidth = (int) (getResources().getDisplayMetrics().widthPixels / this.deviceDensity);
        this.formHeight = (int) (getResources().getDisplayMetrics().heightPixels / this.deviceDensity);
        if (value.equals("Fixed")) {
            sCompatibilityMode = true;
            this.formWidth = (int) (this.formWidth / this.compatScalingFactor);
            this.formHeight = (int) (this.formHeight / this.compatScalingFactor);
        } else {
            sCompatibilityMode = false;
        }
        this.scaleLayout.setScale(sCompatibilityMode ? this.compatScalingFactor : 1.0f);
        if (this.frameLayout != null) {
            this.frameLayout.invalidate();
        }
        Log.d(LOG_TAG, "formWidth = " + this.formWidth + " formHeight = " + this.formHeight);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "If false, lists will be converted to strings using Lisp notation, i.e., as symbols separated by spaces, e.g., (a 1 b2 (c d). If true, lists will appear as in Json or Python, e.g.  [\"a\", 1, \"b\", 2, [\"c\", \"d\"]].  This property appears only in Screen 1, and the value for Screen 1 determines the behavior for all screens. The property defaults to \"true\" meaning that the App Inventor programmer must explicitly set it to \"false\" if Lisp syntax is desired. In older versions of App Inventor, this setting defaulted to false. Older projects should not have been affected by this default settings update.", userVisible = JSONzip.probe)
    @DesignerProperty(alwaysSend = SyntaxForms.DEBUGGING, defaultValue = "True", editorType = PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN)
    public void ShowListsAsJson(boolean asJson) {
        showListsAsJson = asJson;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, userVisible = JSONzip.probe)
    public boolean ShowListsAsJson() {
        return showListsAsJson;
    }

    @SimpleProperty(description = "This is the display name of the installed application in the phone.If the AppName is blank, it will be set to the name of the project when the project is built.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void AppName(String aName) {
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "This is the primary color used for Material UI elements, such as the ActionBar.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = ComponentConstants.DEFAULT_PRIMARY_COLOR, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void PrimaryColor(int color) {
        setPrimaryColor(color);
    }

    @SimpleProperty
    public int PrimaryColor() {
        return this.primaryColor;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "This is the primary color used for darker elements in Material UI.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = ComponentConstants.DEFAULT_PRIMARY_DARK_COLOR, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void PrimaryColorDark(int color) {
        this.primaryColorDark = color;
    }

    @SimpleProperty
    public int PrimaryColorDark() {
        return this.primaryColorDark;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "This is the accent color used for highlights and other user interface accents.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = ComponentConstants.DEFAULT_ACCENT_COLOR, editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR)
    public void AccentColor(int color) {
        this.accentColor = color;
    }

    @SimpleProperty
    public int AccentColor() {
        return this.accentColor;
    }

    @SimpleProperty(description = "Sets the theme used by the application.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = ComponentConstants.DEFAULT_THEME, editorType = PropertyTypeConstants.PROPERTY_TYPE_THEME)
    public void Theme(String theme) {
        if (SdkLevel.getLevel() < 11) {
            this.backgroundColor = -1;
            setBackground(this.frameLayout);
            return;
        }
        if (this.usesDefaultBackground) {
            if (theme.equalsIgnoreCase("AppTheme") && !isClassicMode()) {
                this.backgroundColor = -16777216;
            } else {
                this.backgroundColor = -1;
            }
            setBackground(this.frameLayout);
        }
        this.usesDarkTheme = false;
        if (theme.equals(ComponentConstants.DEFAULT_THEME)) {
            setAppInventorTheme(AppInventorCompatActivity.Theme.CLASSIC);
        } else if (theme.equals("AppTheme.Light.DarkActionBar")) {
            setAppInventorTheme(AppInventorCompatActivity.Theme.DEVICE_DEFAULT);
        } else if (theme.equals("AppTheme.Light")) {
            setAppInventorTheme(AppInventorCompatActivity.Theme.BLACK_TITLE_TEXT);
        } else if (theme.equals("AppTheme")) {
            this.usesDarkTheme = true;
            setAppInventorTheme(AppInventorCompatActivity.Theme.DARK);
        }
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Screen width (x-size).")
    public int Width() {
        Log.d(LOG_TAG, "Form.Width = " + this.formWidth);
        return this.formWidth;
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Screen height (y-size).")
    public int Height() {
        Log.d(LOG_TAG, "Form.Height = " + this.formHeight);
        return this.formHeight;
    }

    @SimpleProperty(description = "A URL to use to populate the Tutorial Sidebar while editing a project. Used as a teaching aid.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void TutorialURL(String url) {
    }

    @SimpleProperty(description = "A JSON string representing the subset for the screen. Authors of template apps can use this to control what components, designer properties, and blocks are available in the project.", userVisible = JSONzip.probe)
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_SUBSET_JSON)
    public void BlocksToolkit(String json) {
    }

    @SimpleProperty(description = "The platform the app is running on, for example \"Android\" or \"iOS\".")
    public String Platform() {
        return "Android";
    }

    @SimpleProperty(description = "The dotted version number of the platform, such as 4.2.2 or 10.0. This is platform specific and there is no guarantee that it has a particular format.")
    public String PlatformVersion() {
        return Build.VERSION.RELEASE;
    }

    public static void switchForm(String nextFormName) {
        if (activeForm != null) {
            activeForm.startNewForm(nextFormName, null);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static void switchFormWithStartValue(String nextFormName, Object startValue) {
        Log.i(LOG_TAG, "Open another screen with start value:" + nextFormName);
        if (activeForm != null) {
            activeForm.startNewForm(nextFormName, startValue);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    protected void startNewForm(String nextFormName, Object startupValue) {
        String jValue;
        Log.i(LOG_TAG, "startNewForm:" + nextFormName);
        Intent activityIntent = new Intent();
        activityIntent.setClassName(this, getPackageName() + "." + nextFormName);
        String functionName = startupValue == null ? "open another screen" : "open another screen with start value";
        if (startupValue != null) {
            Log.i(LOG_TAG, "StartNewForm about to JSON encode:" + startupValue);
            jValue = jsonEncodeForForm(startupValue, functionName);
            Log.i(LOG_TAG, "StartNewForm got JSON encoding:" + jValue);
        } else {
            jValue = "";
        }
        activityIntent.putExtra(ARGUMENT_NAME, jValue);
        this.nextFormName = nextFormName;
        Log.i(LOG_TAG, "about to start new form" + nextFormName);
        try {
            Log.i(LOG_TAG, "startNewForm starting activity:" + activityIntent);
            startActivityForResult(activityIntent, 1);
            AnimationUtil.ApplyOpenScreenAnimation(this, this.openAnimType);
        } catch (ActivityNotFoundException e) {
            dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_SCREEN_NOT_FOUND, nextFormName);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static String jsonEncodeForForm(Object value, String functionName) {
        String jsonResult = "";
        Log.i(LOG_TAG, "jsonEncodeForForm -- creating JSON representation:" + value.toString());
        try {
            jsonResult = JsonUtil.getJsonRepresentation(value);
            Log.i(LOG_TAG, "jsonEncodeForForm -- got JSON representation:" + jsonResult);
            return jsonResult;
        } catch (JSONException e) {
            activeForm.dispatchErrorOccurredEvent(activeForm, functionName, ErrorMessages.ERROR_SCREEN_BAD_VALUE_FOR_SENDING, value.toString());
            return jsonResult;
        }
    }

    @SimpleEvent(description = "Event raised when another screen has closed and control has returned to this screen.")
    public void OtherScreenClosed(String otherScreenName, Object result) {
        Log.i(LOG_TAG, "Form " + this.formName + " OtherScreenClosed, otherScreenName = " + otherScreenName + ", result = " + result.toString());
        EventDispatcher.dispatchEvent(this, "OtherScreenClosed", otherScreenName, result);
    }

    @Override // com.google.appinventor.components.runtime.Component
    public HandlesEventDispatching getDispatchDelegate() {
        return this;
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public Activity $context() {
        return this;
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public Form $form() {
        return this;
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public void $add(AndroidViewComponent component) {
        this.viewLayout.add(component);
        this.allChildren.add(component);
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public List<? extends Component> getChildren() {
        return this.allChildren;
    }

    public float deviceDensity() {
        return this.deviceDensity;
    }

    public float compatScalingFactor() {
        return this.compatScalingFactor;
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public void setChildWidth(final AndroidViewComponent component, final int width) {
        int cWidth = Width();
        if (cWidth == 0) {
            this.androidUIHandler.postDelayed(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.8
                @Override // java.lang.Runnable
                public void run() {
                    System.err.println("(Form)Width not stable yet... trying again");
                    Form.this.setChildWidth(component, width);
                }
            }, 100L);
        }
        System.err.println("Form.setChildWidth(): width = " + width + " parent Width = " + cWidth + " child = " + component);
        if (width <= -1000) {
            width = ((-(width + 1000)) * cWidth) / 100;
        }
        component.setLastWidth(width);
        ViewUtil.setChildWidthForVerticalLayout(component.getView(), width);
    }

    @Override // com.google.appinventor.components.runtime.ComponentContainer
    public void setChildHeight(final AndroidViewComponent component, final int height) {
        int cHeight = Height();
        if (cHeight == 0) {
            this.androidUIHandler.postDelayed(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.9
                @Override // java.lang.Runnable
                public void run() {
                    System.err.println("(Form)Height not stable yet... trying again");
                    Form.this.setChildHeight(component, height);
                }
            }, 100L);
        }
        if (height <= -1000) {
            height = (Height() * (-(height + 1000))) / 100;
        }
        component.setLastHeight(height);
        ViewUtil.setChildHeightForVerticalLayout(component.getView(), height);
    }

    public static Form getActiveForm() {
        return activeForm;
    }

    public static String getStartText() {
        if (activeForm != null) {
            return activeForm.startupValue;
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static Object getStartValue() {
        if (activeForm != null) {
            return decodeJSONStringForForm(activeForm.startupValue, "get start value");
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static void finishActivity() {
        if (activeForm != null) {
            activeForm.closeForm(null);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static void finishActivityWithResult(Object result) {
        if (activeForm != null) {
            if (activeForm instanceof ReplForm) {
                ((ReplForm) activeForm).setResult(result);
                activeForm.closeForm(null);
                return;
            }
            String jString = jsonEncodeForForm(result, "close screen with value");
            Intent resultIntent = new Intent();
            resultIntent.putExtra(RESULT_NAME, jString);
            activeForm.closeForm(resultIntent);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    public static void finishActivityWithTextResult(String result) {
        if (activeForm != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(RESULT_NAME, result);
            activeForm.closeForm(resultIntent);
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    protected void closeForm(Intent resultIntent) {
        if (resultIntent != null) {
            setResult(-1, resultIntent);
        }
        finish();
        AnimationUtil.ApplyCloseScreenAnimation(this, this.closeAnimType);
    }

    public static void finishApplication() {
        if (activeForm != null) {
            activeForm.closeApplicationFromBlocks();
            return;
        }
        throw new IllegalStateException("activeForm is null");
    }

    protected void closeApplicationFromBlocks() {
        closeApplication();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeApplicationFromMenu() {
        closeApplication();
    }

    private void closeApplication() {
        applicationIsBeingClosed = true;
        finish();
        if (this.formName.equals("Screen1")) {
            System.exit(0);
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        addExitButtonToMenu(menu);
        addAboutInfoToMenu(menu);
        for (OnCreateOptionsMenuListener onCreateOptionsMenuListener : this.onCreateOptionsMenuListeners) {
            onCreateOptionsMenuListener.onCreateOptionsMenu(menu);
        }
        return true;
    }

    public void addExitButtonToMenu(Menu menu) {
        MenuItem stopApplicationItem = menu.add(0, 0, 1, "Stop this application").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.google.appinventor.components.runtime.Form.10
            @Override // android.view.MenuItem.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem item) {
                Form.this.showExitApplicationNotification();
                return true;
            }
        });
        stopApplicationItem.setIcon(17301594);
    }

    public void addAboutInfoToMenu(Menu menu) {
        MenuItem aboutAppItem = menu.add(0, 0, 2, "About this application").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: com.google.appinventor.components.runtime.Form.11
            @Override // android.view.MenuItem.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem item) {
                Form.this.showAboutApplicationNotification();
                return true;
            }
        });
        aboutAppItem.setIcon(17301651);
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        for (OnOptionsItemSelectedListener onOptionsItemSelectedListener : this.onOptionsItemSelectedListeners) {
            if (onOptionsItemSelectedListener.onOptionsItemSelected(item)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showExitApplicationNotification() {
        Runnable stopApplication = new Runnable() { // from class: com.google.appinventor.components.runtime.Form.12
            @Override // java.lang.Runnable
            public void run() {
                Form.this.closeApplicationFromMenu();
            }
        };
        Runnable doNothing = new Runnable() { // from class: com.google.appinventor.components.runtime.Form.13
            @Override // java.lang.Runnable
            public void run() {
            }
        };
        Notifier.twoButtonDialog(this, "Stop this application and exit? You'll need to relaunch the application to use it again.", "Stop application?", "Stop and exit", "Don't stop", false, stopApplication, doNothing, doNothing);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAboutApplicationNotification() {
        String message = this.aboutScreen + "<p><small><em>Invented with MIT App Inventor<br>appinventor.mit.edu</em></small></p>";
        Notifier.oneButtonAlert(this, message.replaceAll("\\n", "<br>"), "About this app", "Got it");
    }

    public void clear() {
        Log.d(LOG_TAG, "Form " + this.formName + " clear called");
        this.viewLayout.getLayoutManager().removeAllViews();
        if (this.frameLayout != null) {
            this.frameLayout.removeAllViews();
            this.frameLayout = null;
        }
        defaultPropertyValues();
        this.onStopListeners.clear();
        this.onNewIntentListeners.clear();
        this.onResumeListeners.clear();
        this.onOrientationChangeListeners.clear();
        this.onPauseListeners.clear();
        this.onDestroyListeners.clear();
        this.onInitializeListeners.clear();
        this.onCreateOptionsMenuListeners.clear();
        this.onOptionsItemSelectedListeners.clear();
        this.screenInitialized = false;
        for (OnClearListener onClearListener : this.onClearListeners) {
            onClearListener.onClear();
        }
        this.onClearListeners.clear();
        System.err.println("Form.clear() About to do moby GC!");
        System.gc();
        this.dimChanges.clear();
    }

    public void deleteComponent(Object component) {
        if (component instanceof OnStopListener) {
            this.onStopListeners.remove(component);
        }
        if (component instanceof OnNewIntentListener) {
            this.onNewIntentListeners.remove(component);
        }
        if (component instanceof OnResumeListener) {
            this.onResumeListeners.remove(component);
        }
        if (component instanceof OnOrientationChangeListener) {
            this.onOrientationChangeListeners.remove(component);
        }
        if (component instanceof OnPauseListener) {
            this.onPauseListeners.remove(component);
        }
        if (component instanceof OnDestroyListener) {
            this.onDestroyListeners.remove(component);
        }
        if (component instanceof OnInitializeListener) {
            this.onInitializeListeners.remove(component);
        }
        if (component instanceof OnCreateOptionsMenuListener) {
            this.onCreateOptionsMenuListeners.remove(component);
        }
        if (component instanceof OnOptionsItemSelectedListener) {
            this.onOptionsItemSelectedListeners.remove(component);
        }
        if (component instanceof Deleteable) {
            ((Deleteable) component).onDelete();
        }
    }

    public void dontGrabTouchEventsForComponent() {
        this.frameLayout.requestDisallowInterceptTouchEvent(true);
    }

    protected boolean toastAllowed() {
        long now = System.nanoTime();
        if (now > this.lastToastTime + minimumToastWait) {
            this.lastToastTime = now;
            return true;
        }
        return false;
    }

    public void callInitialize(Object component) throws Throwable {
        try {
            Method method = component.getClass().getMethod("Initialize", null);
            try {
                Log.i(LOG_TAG, "calling Initialize method for Object " + component.toString());
                method.invoke(component, null);
            } catch (InvocationTargetException e) {
                Log.i(LOG_TAG, "invoke exception: " + e.getMessage());
                throw e.getTargetException();
            }
        } catch (NoSuchMethodException e2) {
        } catch (SecurityException e3) {
            Log.i(LOG_TAG, "Security exception " + e3.getMessage());
        }
    }

    public synchronized Bundle fullScreenVideoAction(int action, VideoPlayer source, Object data) {
        return this.fullScreenVideoUtil.performAction(action, source, data);
    }

    private void setBackground(View bgview) {
        Drawable setDraw;
        Drawable setDraw2 = this.backgroundDrawable;
        if (this.backgroundImagePath != "" && setDraw2 != null) {
            setDraw = this.backgroundDrawable.getConstantState().newDrawable();
            setDraw.setColorFilter(this.backgroundColor != 0 ? this.backgroundColor : -1, PorterDuff.Mode.DST_OVER);
        } else {
            setDraw = new ColorDrawable(this.backgroundColor != 0 ? this.backgroundColor : -1);
        }
        ViewUtil.setBackgroundImage(bgview, setDraw);
        bgview.invalidate();
    }

    public static boolean getCompatibilityMode() {
        return sCompatibilityMode;
    }

    @SimpleFunction(description = "Hide the onscreen soft keyboard.")
    public void HideKeyboard() {
        View view = getCurrentFocus();
        if (view == null) {
            view = this.frameLayout;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void updateTitle() {
        this.themeHelper.setTitle(this.title);
    }

    @Override // com.google.appinventor.components.runtime.AppInventorCompatActivity
    protected void maybeShowTitleBar() {
        if (this.showTitle) {
            super.maybeShowTitleBar();
        } else {
            super.hideTitleBar();
        }
    }

    public boolean isDarkTheme() {
        return this.usesDarkTheme;
    }

    public boolean isDeniedPermission(String permission) {
        return Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, permission) == -1;
    }

    public void assertPermission(String permission) {
        if (isDeniedPermission(permission)) {
            throw new PermissionException(permission);
        }
    }

    public void askPermission(final String permission, final PermissionResultHandler responseRequestor) {
        if (isDeniedPermission(permission)) {
            this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.14
                @Override // java.lang.Runnable
                public void run() {
                    int nonce = Form.this.permissionRandom.nextInt(Form.MAX_PERMISSION_NONCE);
                    Log.d(Form.LOG_TAG, "askPermission: permission = " + permission + " requestCode = " + nonce);
                    Form.this.permissionHandlers.put(Integer.valueOf(nonce), responseRequestor);
                    ActivityCompat.requestPermissions(form, new String[]{permission}, nonce);
                }
            });
        } else {
            responseRequestor.HandlePermissionResponse(permission, true);
        }
    }

    public void askPermission(final BulkPermissionRequest request) {
        final List<String> permissionsToAsk = request.getPermissions();
        Iterator<String> it = permissionsToAsk.iterator();
        while (it.hasNext()) {
            if (!isDeniedPermission(it.next())) {
                it.remove();
            }
        }
        if (permissionsToAsk.size() == 0) {
            request.onGranted();
        } else {
            this.androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.Form.15
                @Override // java.lang.Runnable
                public void run() {
                    final Iterator<String> it2 = permissionsToAsk.iterator();
                    PermissionResultHandler handler = new PermissionResultHandler() { // from class: com.google.appinventor.components.runtime.Form.15.1
                        final List<String> deniedPermissions = new ArrayList();

                        @Override // com.google.appinventor.components.runtime.PermissionResultHandler
                        public void HandlePermissionResponse(String permission, boolean granted) {
                            if (!granted) {
                                this.deniedPermissions.add(permission);
                            }
                            if (it2.hasNext()) {
                                Form.this.askPermission((String) it2.next(), this);
                            } else if (this.deniedPermissions.size() == 0) {
                                request.onGranted();
                            } else {
                                request.onDenied((String[]) this.deniedPermissions.toArray(new String[0]));
                            }
                        }
                    };
                    Form.this.askPermission(it2.next(), handler);
                }
            });
        }
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionResultHandler responder = this.permissionHandlers.get(Integer.valueOf(requestCode));
        if (responder == null) {
            Log.e(LOG_TAG, "Received permission response which we cannot match.");
            return;
        }
        if (grantResults.length > 0) {
            if (grantResults[0] == 0) {
                responder.HandlePermissionResponse(permissions[0], true);
            } else {
                responder.HandlePermissionResponse(permissions[0], false);
            }
        } else {
            Log.d(LOG_TAG, "onRequestPermissionsResult: grantResults.length = " + grantResults.length + " requestCode = " + requestCode);
        }
        this.permissionHandlers.remove(Integer.valueOf(requestCode));
    }

    public boolean doesAppDeclarePermission(String permissionName) {
        return this.permissions.contains(permissionName);
    }

    public String getAssetPath(String asset) {
        return ASSETS_PREFIX + asset;
    }

    public String getCachePath(String cache) {
        return "file://" + new java.io.File(getCacheDir(), cache).getAbsolutePath();
    }

    public String getDefaultPath(String name) {
        return FileUtil.resolveFileName(this, name, this.defaultFileScope);
    }

    public String getPrivatePath(String fileName) {
        return "file://" + new java.io.File(getFilesDir(), fileName).getAbsolutePath();
    }

    public InputStream openAsset(String asset) throws IOException {
        return openAssetInternal(getAssetPath(asset));
    }

    public String getAssetPathForExtension(Component component, String asset) throws FileNotFoundException {
        String extPkgName = component.getClass().getPackage().getName();
        return ASSETS_PREFIX + extPkgName + "/" + asset;
    }

    public InputStream openAssetForExtension(Component component, String asset) throws IOException {
        return openAssetInternal(getAssetPathForExtension(component, asset));
    }

    @VisibleForTesting
    InputStream openAssetInternal(String path) throws IOException {
        if (path.startsWith(ASSETS_PREFIX)) {
            AssetManager am = getAssets();
            return am.open(path.substring(ASSETS_PREFIX.length()));
        } else if (path.startsWith("file:")) {
            return FileUtil.openFile(this, URI.create(path));
        } else {
            return FileUtil.openFile(this, path);
        }
    }
}
