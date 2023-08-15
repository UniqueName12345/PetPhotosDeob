package gnu.kawa.swingviews;

import gnu.kawa.models.Button;
import javax.swing.DefaultButtonModel;

/* compiled from: SwingButton.java */
/* loaded from: classes.dex */
class SwModel extends DefaultButtonModel {
    Button model;

    public SwModel(Button model) {
        this.model = model;
        setActionCommand(model.getText());
    }
}
