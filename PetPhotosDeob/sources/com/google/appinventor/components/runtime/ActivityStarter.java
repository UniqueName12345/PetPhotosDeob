package com.google.appinventor.components.runtime;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.repackaged.org.json.zip.JSONzip;
import com.google.appinventor.components.runtime.util.AnimationUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.NougatUtil;
import com.google.appinventor.components.runtime.util.YailList;
import kawa.lang.SyntaxForms;

@DesignerComponent(category = ComponentCategory.CONNECTIVITY, description = "A component that can launch an activity using the <code>StartActivity</code> method. \n<p>Activities that can be launched include:<ul> <li> Starting another App Inventor for Android app. \n To do so, first      find out the <em>class</em> of the other application by      downloading the source code and using a file explorer or unzip      utility to find a file named      \"youngandroidproject/project.properties\".  \n The first line of      the file will start with \"main=\" and be followed by the class      name; for example,      <code>main=com.gmail.Bitdiddle.Ben.HelloPurr.Screen1</code>.       (The first components indicate that it was created by      Ben.Bitdiddle@gmail.com.)  \n To make your      <code>ActivityStarter</code> launch this application, set the      following properties: <ul>\n      <li> <code>ActivityPackage</code> to the class name, dropping the           last component (for example,           <code>com.gmail.Bitdiddle.Ben.HelloPurr</code>)</li>\n      <li> <code>ActivityClass</code> to the entire class name (for           example,           <code>com.gmail.Bitdiddle.Ben.HelloPurr.Screen1</code>)</li>      </ul></li> \n<li> Starting the camera application by setting the following      properties:<ul> \n     <li> <code>Action: android.intent.action.MAIN</code> </li> \n     <li> <code>ActivityPackage: com.android.camera</code> </li> \n     <li> <code>ActivityClass: com.android.camera.Camera</code></li>\n      </ul></li>\n<li> Performing web search.  Assuming the term you want to search      for is \"vampire\" (feel free to substitute your own choice), \n     set the properties to:\n<ul><code>     <li>Action: android.intent.action.WEB_SEARCH</li>      <li>ExtraKey: query</li>      <li>ExtraValue: vampire</li>      <li>ActivityPackage: com.google.android.providers.enhancedgooglesearch</li>     <li>ActivityClass: com.google.android.providers.enhancedgooglesearch.Launcher</li>      </code></ul></li> \n<li> Opening a browser to a specified web page.  Assuming the page you      want to go to is \"www.facebook.com\" (feel free to substitute      your own choice), set the properties to:\n<ul><code>      <li>Action: android.intent.action.VIEW</li>      <li>DataUri: http://www.facebook.com</li> </code> </ul> </li> </ul></p>", designerHelpDescription = "A component that can launch an activity using the <code>StartActivity</code> method.<p>Activities that can be launched include: <ul> \n<li> starting other App Inventor for Android apps </li> \n<li> starting the camera application </li> \n<li> performing web search </li> \n<li> opening a browser to a specified web page</li> \n<li> opening the map application to a specified location</li></ul> \nYou can also launch activities that return text data.  See the documentation on using the Activity Starter for examples.</p>", iconName = "images/activityStarter.png", nonVisible = SyntaxForms.DEBUGGING, version = 6)
@SimpleObject
/* loaded from: classes.dex */
public class ActivityStarter extends AndroidNonvisibleComponent implements ActivityResultListener, Component, Deleteable {
    private static final String LOG_TAG = "ActivityStarter";
    private String action;
    private String activityClass;
    private String activityPackage;
    private final ComponentContainer container;
    private String dataType;
    private String dataUri;
    private String extraKey;
    private String extraValue;
    private YailList extras;
    private int requestCode;
    private String result;
    private Intent resultIntent;
    private String resultName;

    public ActivityStarter(ComponentContainer container) {
        super(container.$form());
        this.container = container;
        this.result = "";
        Action("android.intent.action.MAIN");
        ActivityPackage("");
        ActivityClass("");
        DataUri("");
        DataType("");
        ExtraKey("");
        ExtraValue("");
        Extras(new YailList());
        ResultName("");
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Action() {
        return this.action;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void Action(String action) {
        this.action = action.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the extra key that will be passed to the activity.\nDEPRECATED: New code should use Extras property instead.")
    public String ExtraKey() {
        return this.extraKey;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ExtraKey(String extraKey) {
        this.extraKey = extraKey.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the extra value that will be passed to the activity.\nDEPRECATED: New code should use Extras property instead.")
    public String ExtraValue() {
        return this.extraValue;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ExtraValue(String extraValue) {
        this.extraValue = extraValue.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ResultName() {
        return this.resultName;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ResultName(String resultName) {
        this.resultName = resultName.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Result() {
        return this.result;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String DataUri() {
        return this.dataUri;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void DataUri(String dataUri) {
        this.dataUri = dataUri.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String DataType() {
        return this.dataType;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void DataType(String dataType) {
        this.dataType = dataType.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ActivityPackage() {
        return this.activityPackage;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ActivityPackage(String activityPackage) {
        this.activityPackage = activityPackage.trim();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ActivityClass() {
        return this.activityClass;
    }

    @SimpleProperty
    @DesignerProperty(defaultValue = "", editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void ActivityClass(String activityClass) {
        this.activityClass = activityClass.trim();
    }

    @SimpleEvent(description = "Event raised after this ActivityStarter returns.")
    public void AfterActivity(String result) {
        EventDispatcher.dispatchEvent(this, "AfterActivity", result);
    }

    @SimpleEvent(description = "Event raised if this ActivityStarter returns because the activity was canceled.")
    public void ActivityCanceled() {
        EventDispatcher.dispatchEvent(this, "ActivityCanceled", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ResultType() {
        String resultType;
        return (this.resultIntent == null || (resultType = this.resultIntent.getType()) == null) ? "" : resultType;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ResultUri() {
        String resultUri;
        return (this.resultIntent == null || (resultUri = this.resultIntent.getDataString()) == null) ? "" : resultUri;
    }

    @SimpleProperty
    public void Extras(YailList pairs) {
        Object[] array;
        for (Object pair : pairs.toArray()) {
            boolean isYailList = pair instanceof YailList;
            boolean isPair = isYailList ? ((YailList) pair).size() == 2 : false;
            if (!isYailList || !isPair) {
                throw new YailRuntimeError("Argument to Extras should be a list of pairs", "ActivityStarter Error");
            }
        }
        this.extras = pairs;
    }

    @SimpleProperty
    public YailList Extras() {
        return this.extras;
    }

    @SimpleFunction(description = "Returns the name of the activity that corresponds to this ActivityStarter, or an empty string if no corresponding activity can be found.")
    public String ResolveActivity() {
        Intent intent = buildActivityIntent();
        PackageManager pm = this.container.$context().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
        return (resolveInfo == null || resolveInfo.activityInfo == null) ? "" : resolveInfo.activityInfo.name;
    }

    @SimpleFunction(description = "Start the activity corresponding to this ActivityStarter.")
    public void StartActivity() {
        this.resultIntent = null;
        this.result = "";
        Intent intent = buildActivityIntent();
        if (this.requestCode == 0) {
            this.requestCode = this.form.registerForActivityResult(this);
        }
        if (intent == null) {
            this.form.dispatchErrorOccurredEvent(this, "StartActivity", ErrorMessages.ERROR_ACTIVITY_STARTER_NO_ACTION_INFO, new Object[0]);
            return;
        }
        try {
            this.container.$context().startActivityForResult(intent, this.requestCode);
            String openAnim = this.container.$form().OpenScreenAnimation();
            AnimationUtil.ApplyOpenScreenAnimation(this.container.$context(), openAnim);
        } catch (ActivityNotFoundException e) {
            this.form.dispatchErrorOccurredEvent(this, "StartActivity", ErrorMessages.ERROR_ACTIVITY_STARTER_NO_CORRESPONDING_ACTIVITY, new Object[0]);
        }
    }

    private Intent buildActivityIntent() {
        Object[] array;
        Uri uri = this.dataUri.length() != 0 ? Uri.parse(this.dataUri) : null;
        Intent intent = new Intent(this.action);
        if (uri != null && this.dataUri.toLowerCase().startsWith("file://")) {
            Log.d(LOG_TAG, "Using file://");
            java.io.File file = new java.io.File(uri.getPath());
            if (file.isFile()) {
                Log.d(LOG_TAG, "It's a file");
                uri = NougatUtil.getPackageUri(this.form, file);
                intent = new Intent(this.action);
                intent.setFlags(1);
                Log.d(LOG_TAG, "added permissions");
            }
        }
        if (TextUtils.isEmpty(Action())) {
            return null;
        }
        if (this.dataType.length() != 0) {
            if (uri != null) {
                intent.setDataAndType(uri, this.dataType);
            } else {
                intent.setType(this.dataType);
            }
        } else {
            intent.setData(uri);
        }
        if (this.activityPackage.length() != 0 || this.activityClass.length() != 0) {
            ComponentName component = new ComponentName(this.activityPackage, this.activityClass);
            intent.setComponent(component);
        } else if (Action().equals("android.intent.action.MAIN")) {
            return null;
        }
        if (this.extraKey.length() != 0 && this.extraValue.length() != 0) {
            Log.i(LOG_TAG, "Adding extra, key = " + this.extraKey + " value = " + this.extraValue);
            intent.putExtra(this.extraKey, this.extraValue);
        }
        for (Object extra : this.extras.toArray()) {
            YailList castExtra = (YailList) extra;
            String key = castExtra.getString(0);
            Object value = castExtra.getObject(1);
            Log.i(LOG_TAG, "Adding extra, key = " + key + " value = " + value);
            if (key.length() != 0) {
                if (value instanceof YailList) {
                    Log.i(LOG_TAG, "Adding extra list, key = " + key + " value = " + value);
                    intent.putExtra(key, ((YailList) value).toStringArray());
                } else {
                    String stringValue = castExtra.getString(1);
                    Log.i(LOG_TAG, "Adding extra string, key = " + key + " value = " + stringValue);
                    intent.putExtra(key, stringValue);
                }
            }
        }
        return intent;
    }

    @Override // com.google.appinventor.components.runtime.ActivityResultListener
    public void resultReturned(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode) {
            Log.i(LOG_TAG, "resultReturned - resultCode = " + resultCode);
            if (resultCode == -1) {
                this.resultIntent = data;
                if (this.resultName.length() != 0 && this.resultIntent != null && this.resultIntent.hasExtra(this.resultName)) {
                    this.result = this.resultIntent.getStringExtra(this.resultName);
                } else {
                    this.result = "";
                }
                AfterActivity(this.result);
            } else if (resultCode == 0) {
                ActivityCanceled();
            }
        }
    }

    @SimpleEvent(description = "The ActivityError event is no longer used. Please use the Screen.ErrorOccurred event instead.", userVisible = JSONzip.probe)
    public void ActivityError(String message) {
    }

    @Override // com.google.appinventor.components.runtime.Deleteable
    public void onDelete() {
        this.form.unregisterForActivityResult(this);
    }
}
