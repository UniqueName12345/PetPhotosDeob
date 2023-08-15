package com.google.appinventor.components.runtime.util;

import androidx.annotation.NonNull;
import java.util.Iterator;

/* loaded from: classes.dex */
public interface YailObject<T> extends Iterable<T> {
    Object getObject(int i);

    boolean isEmpty();

    @Override // java.lang.Iterable
    @NonNull
    Iterator<T> iterator();

    int size();
}
