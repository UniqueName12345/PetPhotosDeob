package com.google.appinventor.components.annotations;

import com.google.appinventor.components.annotations.androidmanifest.IntentFilterElement;
import com.google.appinventor.components.annotations.androidmanifest.ProviderElement;

/* loaded from: classes.dex */
public @interface UsesQueries {
    IntentFilterElement[] intents() default {};

    String[] packageNames() default {};

    ProviderElement[] providers() default {};
}
