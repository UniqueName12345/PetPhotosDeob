package gnu.kawa.models;

import com.google.appinventor.components.common.PropertyTypeConstants;
import java.io.Serializable;

/* loaded from: classes.dex */
public class Label extends Model implements Viewable, Serializable {
    String text;

    public Label() {
    }

    public Label(String text) {
        this.text = text;
    }

    @Override // gnu.kawa.models.Viewable
    public void makeView(Display display, Object where) {
        display.addLabel(this, where);
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
        notifyListeners(PropertyTypeConstants.PROPERTY_TYPE_TEXT);
    }
}
