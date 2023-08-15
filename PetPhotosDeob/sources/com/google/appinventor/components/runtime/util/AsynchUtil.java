package com.google.appinventor.components.runtime.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;

/* loaded from: classes.dex */
public class AsynchUtil {
    private static final String LOG_TAG = AsynchUtil.class.getSimpleName();

    public static void runAsynchronously(Runnable call) {
        Thread thread = new Thread(call);
        thread.start();
    }

    public static void runAsynchronously(final Handler androidUIHandler, final Runnable call, final Runnable callback) {
        Runnable runnable = new Runnable() { // from class: com.google.appinventor.components.runtime.util.AsynchUtil.1
            @Override // java.lang.Runnable
            public void run() {
                call.run();
                if (callback != null) {
                    androidUIHandler.post(new Runnable() { // from class: com.google.appinventor.components.runtime.util.AsynchUtil.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            callback.run();
                        }
                    });
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public static boolean isUiThread() {
        return Looper.getMainLooper().equals(Looper.myLooper());
    }

    public static <T> void finish(Synchronizer<T> result, Continuation<T> continuation) {
        Log.d(LOG_TAG, "Waiting for synchronizer result");
        result.waitfor();
        if (result.getThrowable() == null) {
            continuation.call(result.getResult());
            return;
        }
        Throwable e = result.getThrowable();
        if (e instanceof RuntimeException) {
            throw ((RuntimeException) e);
        }
        throw new YailRuntimeError(e.toString(), e.getClass().getSimpleName());
    }
}
