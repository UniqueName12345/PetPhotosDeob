package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/* loaded from: classes.dex */
public class OptionHelper {
    private static final java.util.Map<String, java.util.Map<String, Method>> componentMethods = new HashMap();

    public static <T> Object optionListFromValue(Component c, String func, T value) {
        Options annotation;
        Method calledFunc = getMethod(c, func);
        if (calledFunc != null && (annotation = (Options) calledFunc.getAnnotation(Options.class)) != null) {
            Class<?> optionListClass = annotation.value();
            try {
                Method fromValue = optionListClass.getMethod("fromUnderlyingValue", value.getClass());
                Object abstractVal = fromValue.invoke(optionListClass, value);
                return abstractVal != null ? abstractVal : value;
            } catch (IllegalAccessException e) {
                return value;
            } catch (NoSuchMethodException e2) {
                return value;
            } catch (InvocationTargetException e3) {
                return value;
            }
        }
        return value;
    }

    public static Object[] optionListsFromValues(Component c, String func, Object... args) {
        Method calledFunc;
        if (args.length != 0 && (calledFunc = getMethod(c, func)) != null) {
            Annotation[][] paramAnnotations = calledFunc.getParameterAnnotations();
            int i = 0;
            for (Annotation[] annotations : paramAnnotations) {
                int length = annotations.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        Annotation annotation = annotations[i2];
                        if (annotation.annotationType() != Options.class) {
                            i2++;
                        } else {
                            Options castAnnotation = (Options) annotation;
                            Class<?> optionListClass = castAnnotation.value();
                            try {
                                Method fromValue = optionListClass.getMethod("fromUnderlyingValue", args[i].getClass());
                                Object abstractVal = fromValue.invoke(optionListClass, args[i]);
                                if (abstractVal != null) {
                                    args[i] = abstractVal;
                                }
                            } catch (IllegalAccessException e) {
                            } catch (NoSuchMethodException e2) {
                            } catch (InvocationTargetException e3) {
                            }
                        }
                    }
                }
                i++;
            }
        }
        return args;
    }

    private static Method getMethod(Component c, String func) {
        Class<?> componentClass = c.getClass();
        String componentKey = componentClass.getSimpleName();
        java.util.Map<String, Method> methodMap = componentMethods.get(componentKey);
        if (methodMap == null) {
            methodMap = populateMap(componentClass);
            componentMethods.put(componentKey, methodMap);
        }
        return methodMap.get(func);
    }

    private static java.util.Map<String, Method> populateMap(Class<?> clazz) {
        java.util.Map<String, Method> methodMap = new HashMap<>();
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if ((m.getModifiers() & 1) != 0) {
                String methodKey = m.getName();
                SimpleEvent event = (SimpleEvent) m.getAnnotation(SimpleEvent.class);
                if (event != null) {
                    methodMap.put(methodKey, m);
                } else if (m.getReturnType() != Void.TYPE) {
                    SimpleFunction func = (SimpleFunction) m.getAnnotation(SimpleFunction.class);
                    if (func != null) {
                        methodMap.put(methodKey, m);
                    } else {
                        SimpleProperty prop = (SimpleProperty) m.getAnnotation(SimpleProperty.class);
                        if (prop != null) {
                            methodMap.put(methodKey, m);
                        }
                    }
                }
            }
        }
        return methodMap;
    }
}
