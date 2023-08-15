package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import gnu.mapping.Procedure;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class ContinuationUtil {
    private ContinuationUtil() {
    }

    public static <T> Continuation<T> wrap(final Procedure procedure, final Class<T> clazz) {
        return new Continuation<T>() { // from class: com.google.appinventor.components.runtime.util.ContinuationUtil.1
            @Override // com.google.appinventor.components.runtime.util.Continuation
            public void call(T arg) {
                try {
                    if (clazz == Void.class) {
                        procedure.apply0();
                    } else {
                        procedure.apply1(arg);
                    }
                } catch (Throwable e) {
                    throw new YailRuntimeError(e.getMessage(), e.getClass().getSimpleName());
                }
            }
        };
    }

    public static <T> void callWithContinuation(final Callable<T> block, final Continuation<T> continuation) {
        AsynchUtil.runAsynchronously(new Runnable() { // from class: com.google.appinventor.components.runtime.util.ContinuationUtil.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    continuation.call(block.call());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static <T> T callWithContinuationSync(final Callable<T> block) {
        final Synchronizer<T> result = new Synchronizer<>();
        callWithContinuation(new Callable<T>() { // from class: com.google.appinventor.components.runtime.util.ContinuationUtil.3
            @Override // java.util.concurrent.Callable
            public T call() {
                try {
                    return (T) block.call();
                } catch (Throwable t) {
                    result.caught(t);
                    return null;
                }
            }
        }, new Continuation<T>() { // from class: com.google.appinventor.components.runtime.util.ContinuationUtil.4
            @Override // com.google.appinventor.components.runtime.util.Continuation
            public void call(T value) {
                if (Synchronizer.this.getThrowable() != null) {
                    Synchronizer.this.wakeup(value);
                }
            }
        });
        Throwable error = result.getThrowable();
        if (error != null) {
            if (error instanceof RuntimeException) {
                throw ((RuntimeException) error);
            }
            throw new RuntimeException("Exception in call", error);
        }
        return result.getResult();
    }
}
