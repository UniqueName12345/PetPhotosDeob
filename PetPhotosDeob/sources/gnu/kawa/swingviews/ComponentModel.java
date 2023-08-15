package gnu.kawa.swingviews;

import gnu.kawa.models.Display;
import gnu.kawa.models.Model;
import java.awt.Component;

/* compiled from: SwingDisplay.java */
/* loaded from: classes.dex */
class ComponentModel extends Model {
    Component component;

    public ComponentModel(Component component) {
        this.component = component;
    }

    @Override // gnu.kawa.models.Viewable
    public void makeView(Display display, Object where) {
        display.addView(this.component, where);
    }
}
