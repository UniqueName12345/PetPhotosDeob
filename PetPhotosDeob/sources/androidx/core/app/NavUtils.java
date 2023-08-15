package androidx.core.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import gnu.expr.Declaration;

/* loaded from: classes.dex */
public final class NavUtils {
    public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
    private static final String TAG = "NavUtils";

    public static boolean shouldUpRecreateTask(@NonNull Activity sourceActivity, @NonNull Intent targetIntent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return sourceActivity.shouldUpRecreateTask(targetIntent);
        }
        String action = sourceActivity.getIntent().getAction();
        return (action == null || action.equals("android.intent.action.MAIN")) ? false : true;
    }

    public static void navigateUpFromSameTask(@NonNull Activity sourceActivity) {
        Intent upIntent = getParentActivityIntent(sourceActivity);
        if (upIntent == null) {
            throw new IllegalArgumentException("Activity " + sourceActivity.getClass().getSimpleName() + " does not have a parent activity name specified. (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data>  element in your manifest?)");
        }
        navigateUpTo(sourceActivity, upIntent);
    }

    public static void navigateUpTo(@NonNull Activity sourceActivity, @NonNull Intent upIntent) {
        if (Build.VERSION.SDK_INT >= 16) {
            sourceActivity.navigateUpTo(upIntent);
            return;
        }
        upIntent.addFlags(Declaration.PUBLIC_ACCESS);
        sourceActivity.startActivity(upIntent);
        sourceActivity.finish();
    }

    @Nullable
    public static Intent getParentActivityIntent(@NonNull Activity sourceActivity) {
        Intent result;
        Intent result2;
        if (Build.VERSION.SDK_INT < 16 || (result2 = sourceActivity.getParentActivityIntent()) == null) {
            String parentName = getParentActivityName(sourceActivity);
            if (parentName == null) {
                return null;
            }
            ComponentName target = new ComponentName(sourceActivity, parentName);
            try {
                String grandparent = getParentActivityName(sourceActivity, target);
                if (grandparent == null) {
                    result = Intent.makeMainActivity(target);
                } else {
                    result = new Intent().setComponent(target);
                }
                return result;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "getParentActivityIntent: bad parentActivityName '" + parentName + "' in manifest");
                return null;
            }
        }
        return result2;
    }

    @Nullable
    public static Intent getParentActivityIntent(@NonNull Context context, @NonNull Class<?> sourceActivityClass) throws PackageManager.NameNotFoundException {
        String parentActivity = getParentActivityName(context, new ComponentName(context, sourceActivityClass));
        if (parentActivity == null) {
            return null;
        }
        ComponentName target = new ComponentName(context, parentActivity);
        String grandparent = getParentActivityName(context, target);
        if (grandparent == null) {
            return Intent.makeMainActivity(target);
        }
        return new Intent().setComponent(target);
    }

    @Nullable
    public static Intent getParentActivityIntent(@NonNull Context context, @NonNull ComponentName componentName) throws PackageManager.NameNotFoundException {
        String parentActivity = getParentActivityName(context, componentName);
        if (parentActivity == null) {
            return null;
        }
        ComponentName target = new ComponentName(componentName.getPackageName(), parentActivity);
        String grandparent = getParentActivityName(context, target);
        if (grandparent == null) {
            return Intent.makeMainActivity(target);
        }
        return new Intent().setComponent(target);
    }

    @Nullable
    public static String getParentActivityName(@NonNull Activity sourceActivity) {
        try {
            return getParentActivityName(sourceActivity, sourceActivity.getComponentName());
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Nullable
    public static String getParentActivityName(@NonNull Context context, @NonNull ComponentName componentName) throws PackageManager.NameNotFoundException {
        String parentActivity;
        String result;
        PackageManager pm = context.getPackageManager();
        ActivityInfo info = pm.getActivityInfo(componentName, 128);
        if (Build.VERSION.SDK_INT < 16 || (result = info.parentActivityName) == null) {
            if (info.metaData != null && (parentActivity = info.metaData.getString(PARENT_ACTIVITY)) != null) {
                if (parentActivity.charAt(0) == '.') {
                    parentActivity = context.getPackageName() + parentActivity;
                }
                return parentActivity;
            }
            return null;
        }
        return result;
    }

    private NavUtils() {
    }
}
