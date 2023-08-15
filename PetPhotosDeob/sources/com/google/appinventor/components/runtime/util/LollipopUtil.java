package com.google.appinventor.components.runtime.util;

import android.telephony.PhoneNumberUtils;
import java.util.Locale;

/* loaded from: classes.dex */
public final class LollipopUtil {
    private LollipopUtil() {
    }

    public static String formatNumber(String number) {
        return PhoneNumberUtils.formatNumber(number, Locale.getDefault().getCountry());
    }
}
