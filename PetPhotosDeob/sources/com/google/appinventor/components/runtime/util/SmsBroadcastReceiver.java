package com.google.appinventor.components.runtime.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.Texting;
import java.util.List;

/* loaded from: classes.dex */
public class SmsBroadcastReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 8647;
    public static final String TAG = "SmsBroadcastReceiver";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive");
        String phone = getPhoneNumber(intent);
        String msg = getMessage(intent);
        Log.i(TAG, "Received " + phone + " : " + msg);
        int receivingEnabled = Texting.isReceivingEnabled(context);
        if (receivingEnabled == 1) {
            Log.i(TAG, context.getApplicationInfo().packageName + " Receiving is not enabled, ignoring message.");
        } else if ((receivingEnabled == 2 || isRepl(context)) && !Texting.isRunning()) {
            Log.i(TAG, context.getApplicationInfo().packageName + " Texting isn't running, and either receivingEnabled is FOREGROUND or we are the repl.");
        } else {
            Texting.handledReceivedMessage(context, phone, msg);
            if (Texting.isRunning()) {
                Log.i(TAG, context.getApplicationInfo().packageName + " App in Foreground, delivering message.");
                return;
            }
            Log.i(TAG, context.getApplicationInfo().packageName + " Texting isn't running, but receivingEnabled == 2, sending notification.");
            sendNotification(context, phone, msg);
        }
    }

    private String getPhoneNumber(Intent intent) {
        String phone = "";
        try {
            if (intent.getAction().equals("com.google.android.apps.googlevoice.SMS_RECEIVED")) {
                return PhoneNumberUtils.formatNumber(intent.getExtras().getString(Texting.PHONE_NUMBER_TAG));
            }
            if (SdkLevel.getLevel() >= 19) {
                List<SmsMessage> messages = KitkatUtil.getMessagesFromIntent(intent);
                for (SmsMessage smsMsg : messages) {
                    if (smsMsg != null) {
                        String phone2 = smsMsg.getOriginatingAddress();
                        if (SdkLevel.getLevel() >= 21) {
                            phone = LollipopUtil.formatNumber(phone2);
                        } else {
                            phone = PhoneNumberUtils.formatNumber(phone2);
                        }
                    }
                }
                return phone;
            }
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            for (Object pdu : pdus) {
                phone = PhoneNumberUtils.formatNumber(SmsMessage.createFromPdu((byte[]) pdu).getOriginatingAddress());
            }
            return phone;
        } catch (NullPointerException e) {
            Log.w(TAG, "Unable to retrieve originating address from SmsMessage", e);
            return "";
        }
    }

    private String getMessage(Intent intent) {
        try {
            if (intent.getAction().equals("com.google.android.apps.googlevoice.SMS_RECEIVED")) {
                String msg = intent.getExtras().getString(Texting.MESSAGE_TAG);
                return msg;
            } else if (SdkLevel.getLevel() >= 19) {
                StringBuilder sb = new StringBuilder();
                List<SmsMessage> messages = KitkatUtil.getMessagesFromIntent(intent);
                for (SmsMessage smsMsg : messages) {
                    if (smsMsg != null) {
                        sb.append(smsMsg.getMessageBody());
                    }
                }
                String msg2 = sb.toString();
                return msg2;
            } else {
                StringBuilder sb2 = new StringBuilder();
                Object[] pdus = (Object[]) intent.getExtras().get("pdus");
                for (Object pdu : pdus) {
                    sb2.append(SmsMessage.createFromPdu((byte[]) pdu).getMessageBody());
                }
                String msg3 = sb2.toString();
                return msg3;
            }
        } catch (NullPointerException e) {
            Log.w(TAG, "Unable to retrieve message body from SmsMessage", e);
            return "";
        }
    }

    private void sendNotification(Context context, String phone, String msg) {
        String classname;
        Intent newIntent;
        Log.i(TAG, "sendingNotification " + phone + ":" + msg);
        String packageName = context.getPackageName();
        Log.i(TAG, "Package name : " + packageName);
        try {
            classname = packageName + ".Screen1";
            newIntent = new Intent(context, Class.forName(classname));
        } catch (ClassNotFoundException e) {
            e = e;
        }
        try {
            newIntent.setAction("android.intent.action.MAIN");
            newIntent.addCategory("android.intent.category.LAUNCHER");
            newIntent.addFlags(805306368);
            PendingIntent activity = PendingIntent.getActivity(context, 0, newIntent, 201326592);
            NotificationManager nm = (NotificationManager) context.getSystemService("notification");
            Notification note = new NotificationCompat.Builder(context).setSmallIcon(17301648).setTicker(phone + " : " + msg).setWhen(System.currentTimeMillis()).setAutoCancel(true).setDefaults(1).setContentTitle("Sms from " + phone).setContentText(msg).setContentIntent(activity).setNumber(Texting.getCachedMsgCount()).build();
            nm.notify(null, NOTIFICATION_ID, note);
            Log.i(TAG, "Notification sent, classname: " + classname);
        } catch (ClassNotFoundException e2) {
            e = e2;
            e.printStackTrace();
        }
    }

    private boolean isRepl(Context context) {
        try {
            String packageName = context.getPackageName();
            String classname = packageName + ".Screen1";
            Class appClass = Class.forName(classname);
            Class superClass = appClass.getSuperclass();
            return superClass.equals(ReplForm.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
