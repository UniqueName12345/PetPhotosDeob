package com.google.appinventor.components.runtime.util.theme;

import android.app.ActionBar;
import android.text.Html;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.ImageViewUtil;

/* loaded from: classes.dex */
public class HoneycombThemeHelper implements ThemeHelper {
    private final AppInventorCompatActivity activity;

    public HoneycombThemeHelper(AppInventorCompatActivity activity) {
        this.activity = activity;
    }

    @Override // com.google.appinventor.components.runtime.util.theme.ThemeHelper
    public void requestActionBar() {
        if (!this.activity.getWindow().hasFeature(8)) {
            this.activity.getWindow().requestFeature(8);
        }
    }

    @Override // com.google.appinventor.components.runtime.util.theme.ThemeHelper
    public boolean setActionBarVisible(boolean visible) {
        ActionBar actionBar = this.activity.getActionBar();
        if (actionBar == null) {
            if (this.activity instanceof Form) {
                ((Form) this.activity).dispatchErrorOccurredEvent((Form) this.activity, "ActionBar", ErrorMessages.ERROR_ACTIONBAR_NOT_SUPPORTED, new Object[0]);
            }
            return false;
        }
        if (visible) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
        return true;
    }

    @Override // com.google.appinventor.components.runtime.util.theme.ThemeHelper
    public boolean hasActionBar() {
        return this.activity.getActionBar() != null;
    }

    @Override // com.google.appinventor.components.runtime.util.theme.ThemeHelper
    public void setTitle(String title) {
        ActionBar actionBar = this.activity.getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override // com.google.appinventor.components.runtime.util.theme.ThemeHelper
    public void setActionBarAnimation(boolean enabled) {
    }

    @Override // com.google.appinventor.components.runtime.util.theme.ThemeHelper
    public void setTitle(String title, boolean black) {
        ActionBar actionBar = this.activity.getActionBar();
        if (actionBar != null) {
            if (black) {
                actionBar.setTitle(Html.fromHtml("<font color=\"black\">" + title + "</font>"));
                ImageViewUtil.setMenuButtonColor(this.activity, -16777216);
                return;
            }
            actionBar.setTitle(title);
            ImageViewUtil.setMenuButtonColor(this.activity, -1);
        }
    }
}
