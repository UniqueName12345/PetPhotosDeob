package gnu.kawa.swingviews;

import gnu.kawa.models.Paintable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/* loaded from: classes.dex */
public class SwingPaintable extends JPanel {
    Dimension dim;
    Paintable paintable;

    public SwingPaintable(Paintable paintable) {
        this.paintable = paintable;
        Rectangle2D rect = paintable.getBounds2D();
        int h = (int) Math.ceil(rect.getHeight());
        int w = (int) Math.ceil(rect.getWidth());
        this.dim = new Dimension(w, h);
    }

    public void paint(Graphics g) {
        this.paintable.paint((Graphics2D) g);
    }

    public Dimension getPreferredSize() {
        return this.dim;
    }
}
