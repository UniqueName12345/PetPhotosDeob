package com.google.appinventor.components.annotations;

import com.google.appinventor.components.common.OptionList;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes.dex */
public @interface Options {
    Class<? extends OptionList<?>> value();
}
