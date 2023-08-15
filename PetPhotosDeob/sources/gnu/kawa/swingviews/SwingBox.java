package gnu.kawa.swingviews;

import gnu.kawa.models.Display;
import gnu.kawa.models.Model;
import gnu.kawa.models.ModelListener;
import gnu.kawa.models.Viewable;
import javax.swing.Box;

/* compiled from: SwingDisplay.java */
/* loaded from: classes.dex */
class SwingBox extends Box implements ModelListener {
    gnu.kawa.models.Box model;

    public SwingBox(gnu.kawa.models.Box model, Display display) {
        super(model.getAxis());
        model.addListener(this);
        Viewable cellSpacing = model.getCellSpacing();
        int n = model.getComponentCount();
        for (int i = 0; i < n; i++) {
            if (i > 0 && cellSpacing != null) {
                cellSpacing.makeView(display, this);
            }
            model.getComponent(i).makeView(display, this);
        }
    }

    @Override // gnu.kawa.models.ModelListener
    public void modelUpdated(Model model, Object key) {
    }
}
