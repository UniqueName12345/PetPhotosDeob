package com.google.appinventor.components.runtime.repackaged.org.json;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

/* loaded from: classes2.dex */
public class Property {
    public static JSONObject toJSONObject(Properties properties) throws JSONException {
        JSONObject jo = new JSONObject();
        if (properties != null && !properties.isEmpty()) {
            Enumeration enumProperties = properties.propertyNames();
            while (enumProperties.hasMoreElements()) {
                String name = (String) enumProperties.nextElement();
                jo.put(name, properties.getProperty(name));
            }
        }
        return jo;
    }

    public static Properties toProperties(JSONObject jo) throws JSONException {
        Properties properties = new Properties();
        if (jo != null) {
            Iterator keys = jo.keys();
            while (keys.hasNext()) {
                String name = keys.next().toString();
                properties.put(name, jo.getString(name));
            }
        }
        return properties;
    }
}
