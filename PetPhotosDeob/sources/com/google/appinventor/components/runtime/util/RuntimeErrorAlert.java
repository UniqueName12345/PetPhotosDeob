package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/* loaded from: classes.dex */
public final class RuntimeErrorAlert {
    public static void alert(final Object context, String message, String title, String buttonText) {
        Log.i("RuntimeErrorAlert", "in alert");
        AlertDialog alertDialog = new AlertDialog.Builder((Context) context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(buttonText, new DialogInterface.OnClickListener() { // from class: com.google.appinventor.components.runtime.util.RuntimeErrorAlert.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
            }
        });
        if (message == null) {
            Log.e(RuntimeErrorAlert.class.getName(), "No error message available");
        } else {
            Log.e(RuntimeErrorAlert.class.getName(), message);
        }
        alertDialog.show();
    }
}
