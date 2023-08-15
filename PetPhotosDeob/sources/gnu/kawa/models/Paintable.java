package gnu.kawa.models;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/* loaded from: classes.dex */
public interface Paintable {
    Rectangle2D getBounds2D();

    void paint(Graphics2D graphics2D);

    Paintable transform(AffineTransform affineTransform);
}
