package com.google.appinventor.components.runtime.util;

import android.util.Log;
import com.google.appinventor.components.runtime.CloudDB;
import java.util.List;
import org.json.JSONException;
import redis.clients.jedis.JedisPubSub;

/* loaded from: classes.dex */
public class CloudDBJedisListener extends JedisPubSub {
    private static final boolean DEBUG = false;
    private static String LOG_TAG = "CloudDB";
    public CloudDB cloudDB;
    private Thread myThread = Thread.currentThread();

    public CloudDBJedisListener(CloudDB thisCloudDB) {
        this.cloudDB = thisCloudDB;
    }

    public void onSubscribe(String channel, int subscribedChannels) {
    }

    public void onMessage(String channel, String message) {
        try {
            List<Object> data = (List) JsonUtil.getObjectFromJson(message, false);
            String tag = (String) data.get(0);
            List<Object> valueList = (List) data.get(1);
            for (Object value : valueList) {
                String retValue = JsonUtil.getJsonRepresentationIfValueFileName(this.cloudDB.getForm(), value);
                if (retValue == null) {
                    this.cloudDB.DataChanged(tag, value);
                } else {
                    this.cloudDB.DataChanged(tag, retValue);
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "onMessage: JSONException", e);
            this.cloudDB.CloudDBError("System Error: " + e.getMessage());
        }
    }

    public void terminate() {
        this.myThread.interrupt();
    }
}
