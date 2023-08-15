package com.google.appinventor.components.runtime.util;

import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class KitkatUtil {
    private KitkatUtil() {
    }

    public static List<SmsMessage> getMessagesFromIntent(Intent intent) {
        List<SmsMessage> result = new ArrayList<>();
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        if (messages != null && messages.length >= 0) {
            Collections.addAll(result, messages);
        }
        return result;
    }

    public static int getMinWidth(TextView view) {
        return Build.VERSION.SDK_INT >= 16 ? view.getMinWidth() : view.getWidth();
    }

    public static int getMinHeight(TextView view) {
        return Build.VERSION.SDK_INT >= 16 ? view.getMinHeight() : view.getHeight();
    }
}
