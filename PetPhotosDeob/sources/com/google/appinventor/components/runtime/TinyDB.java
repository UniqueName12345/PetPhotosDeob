package com.google.appinventor.components.runtime;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kawa.lang.SyntaxForms;
import org.json.JSONException;

@DesignerComponent(category = ComponentCategory.STORAGE, description = "TinyDB is a non-visible component that stores data for an app. <p> Apps created with App Inventor are initialized each time they run: If an app sets the value of a variable and the user then quits the app, the value of that variable will not be remembered the next time the app is run. In contrast, TinyDB is a <em> persistent </em> data store for the app, that is, the data stored there will be available each time the app is run. An example might be a game that saves the high score and retrieves it each time the game is played. </<p> <p> Data items are strings stored under <em>tags</em> . To store a data item, you specify the tag it should be stored under.  Subsequently, you can retrieve the data that was stored under a given tag. </p><p> There is only one data store per app. Even if you have multiple TinyDB components, they will use the same data store. To get the effect of separate stores, use different keys. Also each app has its own data store. You cannot use TinyDB to pass data between two different apps on the phone, although you <em>can</em> use TinyDb to shares data between the different screens of a multi-screen app. </p> <p>When you are developing apps using the AI Companion, all the apps using that companion will share the same TinyDb.  That sharing will disappear once the apps are packaged.  But, during development, you should be careful to clear the TinyDb each time you start working on a new app.</p>", iconName = "images/tinyDB.png", nonVisible = SyntaxForms.DEBUGGING, version = 2)
@SimpleObject
/* loaded from: classes.dex */
public class TinyDB extends AndroidNonvisibleComponent implements Component, Deleteable, ObservableDataSource<String, YailList> {
    public static final String DEFAULT_NAMESPACE = "TinyDB1";
    private Context context;
    private HashSet<DataSourceChangeListener> dataSourceObservers;
    private String namespace;
    private final SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
    private SharedPreferences sharedPreferences;

    public TinyDB(ComponentContainer container) {
        super(container.$form());
        this.dataSourceObservers = new HashSet<>();
        this.context = container.$context();
        this.sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: com.google.appinventor.components.runtime.TinyDB.1
            @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                TinyDB.this.notifyDataObservers(key, TinyDB.this.GetValue(key, null));
            }
        };
        Namespace(DEFAULT_NAMESPACE);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Namespace for storing data.")
    @DesignerProperty(defaultValue = DEFAULT_NAMESPACE, editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING)
    public void Namespace(String namespace) {
        this.namespace = namespace;
        if (this.sharedPreferences != null) {
            this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this.sharedPreferenceChangeListener);
        }
        this.sharedPreferences = this.context.getSharedPreferences(namespace, 0);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this.sharedPreferenceChangeListener);
    }

    @SimpleProperty(description = "Namespace for storing data.")
    public String Namespace() {
        return this.namespace;
    }

    @SimpleFunction(description = "Store the given value under the given tag.  The storage persists on the phone when the app is restarted.")
    public void StoreValue(String tag, Object valueToStore) {
        SharedPreferences.Editor sharedPrefsEditor = this.sharedPreferences.edit();
        try {
            sharedPrefsEditor.putString(tag, JsonUtil.getJsonRepresentation(valueToStore));
            sharedPrefsEditor.commit();
        } catch (JSONException e) {
            throw new YailRuntimeError("Value failed to convert to JSON.", "JSON Creation Error.");
        }
    }

    @SimpleFunction(description = "Retrieve the value stored under the given tag. If there's no such tag, then return valueIfTagNotThere.")
    public Object GetValue(String tag, Object valueIfTagNotThere) {
        try {
            String value = this.sharedPreferences.getString(tag, "");
            if (value.length() == 0) {
                return valueIfTagNotThere;
            }
            Object valueIfTagNotThere2 = JsonUtil.getObjectFromJson(value, true);
            return valueIfTagNotThere2;
        } catch (JSONException e) {
            throw new YailRuntimeError("Value failed to convert from JSON.", "JSON Creation Error.");
        }
    }

    @SimpleFunction(description = "Return a list of all the tags in the data store.")
    public Object GetTags() {
        List<String> keyList = new ArrayList<>();
        java.util.Map<String, ?> keyValues = this.sharedPreferences.getAll();
        keyList.addAll(keyValues.keySet());
        Collections.sort(keyList);
        return keyList;
    }

    @SimpleFunction(description = "Clear the entire data store.")
    public void ClearAll() {
        SharedPreferences.Editor sharedPrefsEditor = this.sharedPreferences.edit();
        sharedPrefsEditor.clear();
        sharedPrefsEditor.commit();
        notifyDataObservers((String) null, (Object) null);
    }

    @SimpleFunction(description = "Clear the entry with the given tag.")
    public void ClearTag(String tag) {
        SharedPreferences.Editor sharedPrefsEditor = this.sharedPreferences.edit();
        sharedPrefsEditor.remove(tag);
        sharedPrefsEditor.commit();
    }

    @Override // com.google.appinventor.components.runtime.Deleteable
    public void onDelete() {
        SharedPreferences.Editor sharedPrefsEditor = this.sharedPreferences.edit();
        sharedPrefsEditor.clear();
        sharedPrefsEditor.commit();
        notifyDataObservers((String) null, (Object) null);
    }

    @Override // com.google.appinventor.components.runtime.DataSource
    public YailList getDataValue(String key) {
        Object value = GetValue(key, YailList.makeEmptyList());
        return value instanceof YailList ? (YailList) value : YailList.makeEmptyList();
    }

    @Override // com.google.appinventor.components.runtime.ObservableDataSource
    public void addDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.add(dataComponent);
    }

    @Override // com.google.appinventor.components.runtime.ObservableDataSource
    public void removeDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.remove(dataComponent);
    }

    @Override // com.google.appinventor.components.runtime.ObservableDataSource
    public void notifyDataObservers(String key, Object newValue) {
        Log.i("Tag", "Notified: " + this.dataSourceObservers.size() + " observers.");
        Iterator<DataSourceChangeListener> it = this.dataSourceObservers.iterator();
        while (it.hasNext()) {
            DataSourceChangeListener dataComponent = it.next();
            dataComponent.onDataSourceValueChange(this, key, newValue);
        }
    }
}
