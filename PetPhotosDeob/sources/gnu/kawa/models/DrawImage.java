package gnu.kawa.models;

import gnu.mapping.WrappedException;
import gnu.text.Path;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import javax.imageio.ImageIO;

/* loaded from: classes.dex */
public class DrawImage extends Model implements Paintable, Serializable {
    String description;
    BufferedImage image;
    Path src;

    public DrawImage() {
    }

    @Override // gnu.kawa.models.Viewable
    public void makeView(Display display, Object where) {
        display.addImage(this, where);
    }

    void loadImage() {
        if (this.image == null) {
            try {
                this.image = ImageIO.read(this.src.openInputStream());
            } catch (Throwable ex) {
                throw WrappedException.wrapIfNeeded(ex);
            }
        }
    }

    public DrawImage(BufferedImage image) {
        this.image = image;
    }

    @Override // gnu.kawa.models.Paintable
    public void paint(Graphics2D graphics) {
        loadImage();
        graphics.drawImage(this.image, (AffineTransform) null, (ImageObserver) null);
    }

    @Override // gnu.kawa.models.Paintable
    public Rectangle2D getBounds2D() {
        loadImage();
        int w = this.image.getWidth();
        int h = this.image.getHeight();
        return new Rectangle2D.Float(0.0f, 0.0f, w, h);
    }

    @Override // gnu.kawa.models.Paintable
    public Paintable transform(AffineTransform tr) {
        return new WithTransform(this, tr);
    }

    public Image getImage() {
        loadImage();
        return this.image;
    }

    public Path getSrc() {
        return this.src;
    }

    public void setSrc(Path src) {
        this.src = src;
    }
}
