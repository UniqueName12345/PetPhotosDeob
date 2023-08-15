package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
/* loaded from: classes.dex */
public interface DataSource<K, V> {
    V getDataValue(K k);
}
