package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.util.Log;
import com.google.appinventor.components.runtime.NearField;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.nio.charset.Charset;
import java.util.Locale;

/* loaded from: classes.dex */
public class GingerbreadUtil {
    private GingerbreadUtil() {
    }

    public static CookieHandler newCookieManager() {
        return new CookieManager();
    }

    public static boolean clearCookies(CookieHandler cookieHandler) {
        if (cookieHandler instanceof CookieManager) {
            CookieManager cookieManager = (CookieManager) cookieHandler;
            CookieStore cookieStore = cookieManager.getCookieStore();
            if (cookieStore != null) {
                cookieStore.removeAll();
                return true;
            }
        }
        return false;
    }

    public static NfcAdapter newNfcAdapter(Context context) {
        return NfcAdapter.getDefaultAdapter(context);
    }

    public static void enableNFCWriteMode(Activity activity, NfcAdapter nfcAdapter, String textToWrite) {
        NdefRecord textRecord = createTextRecord(textToWrite, true);
        NdefMessage msg = new NdefMessage(new NdefRecord[]{textRecord});
        nfcAdapter.enableForegroundNdefPush(activity, msg);
    }

    public static void disableNFCAdapter(Activity activity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundNdefPush(activity);
    }

    public static NdefRecord createTextRecord(String payload, boolean encodeInUtf8) {
        byte[] langBytes = Locale.getDefault().getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = payload.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : 128;
        char status = (char) (langBytes.length + utfBit);
        byte[] data = new byte[langBytes.length + 1 + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, langBytes.length + 1, textBytes.length);
        NdefRecord record = new NdefRecord((short) 1, NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }

    public static void resolveNFCIntent(Intent intent, NearField nfc) {
        NdefMessage[] msgs;
        String action = intent.getAction();
        if ("android.nfc.action.NDEF_DISCOVERED".equals(action)) {
            if (nfc.ReadMode()) {
                Parcelable[] rawMsgs = intent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
                if (rawMsgs != null) {
                    msgs = new NdefMessage[rawMsgs.length];
                    for (int i = 0; i < rawMsgs.length; i++) {
                        msgs[i] = (NdefMessage) rawMsgs[i];
                    }
                } else {
                    byte[] empty = new byte[0];
                    NdefRecord record = new NdefRecord((short) 5, empty, empty, empty);
                    NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
                    msgs = new NdefMessage[]{msg};
                }
                byte[] payload = msgs[0].getRecords()[0].getPayload();
                String message = new String(payload).substring(3);
                nfc.TagRead(message);
                return;
            }
            Tag detectedTag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
            NdefMessage msg2 = null;
            if (nfc.WriteType() == 1) {
                NdefRecord textRecord = createTextRecord(nfc.TextToWrite(), true);
                msg2 = new NdefMessage(new NdefRecord[]{textRecord});
            }
            if (writeNFCTag(msg2, detectedTag)) {
                nfc.TagWritten();
                return;
            }
            return;
        }
        Log.e("nearfield", "Unknown intent " + intent);
    }

    public static boolean writeNFCTag(NdefMessage message, Tag tag) {
        boolean z = false;
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                if (ndef.isWritable() && ndef.getMaxSize() >= size) {
                    ndef.writeNdefMessage(message);
                    z = true;
                }
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        z = true;
                    } catch (IOException e) {
                    }
                }
            }
        } catch (Exception e2) {
        }
        return z;
    }
}
