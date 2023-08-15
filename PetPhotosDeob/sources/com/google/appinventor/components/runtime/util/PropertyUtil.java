package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.SimplePropertyCopier;
import com.google.appinventor.components.runtime.Component;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class PropertyUtil {
    public static Component copyComponentProperties(Component source, Component target) throws Throwable {
        if (!source.getClass().equals(target.getClass())) {
            throw new IllegalArgumentException("Source and target classes must be identical");
        }
        Class componentClass = source.getClass();
        Method[] componentMethods = componentClass.getMethods();
        for (Method componentMethod : componentMethods) {
            if (componentMethod.isAnnotationPresent(SimpleProperty.class) && componentMethod.getParameterTypes().length == 1) {
                try {
                    String propertyName = componentMethod.getName();
                    Method propertyCopierMethod = getPropertyCopierMethod("Copy" + propertyName, componentClass);
                    if (propertyCopierMethod != null) {
                        propertyCopierMethod.invoke(target, source);
                    } else {
                        Method propertyGetterMethod = componentClass.getMethod(propertyName, new Class[0]);
                        Class propertySetterParameterType = componentMethod.getParameterTypes()[0];
                        if (propertyGetterMethod.isAnnotationPresent(SimpleProperty.class) && propertySetterParameterType.isAssignableFrom(propertyGetterMethod.getReturnType())) {
                            Object propertyValue = propertyGetterMethod.invoke(source, new Object[0]);
                            componentMethod.invoke(target, propertyValue);
                        }
                    }
                } catch (NoSuchMethodException e) {
                } catch (InvocationTargetException e2) {
                    throw e2.getCause();
                }
            }
        }
        return target;
    }

    private static Method getPropertyCopierMethod(String copierMethodName, Class componentClass) {
        do {
            try {
                Method propertyCopierMethod = componentClass.getMethod(copierMethodName, componentClass);
                if (propertyCopierMethod.isAnnotationPresent(SimplePropertyCopier.class)) {
                    return propertyCopierMethod;
                }
            } catch (NoSuchMethodException e) {
            }
            componentClass = componentClass.getSuperclass();
        } while (componentClass != null);
        return null;
    }
}
