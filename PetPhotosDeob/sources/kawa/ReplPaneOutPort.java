package kawa;

import gnu.kawa.models.Paintable;
import gnu.kawa.models.Viewable;
import gnu.mapping.OutPort;
import gnu.text.Path;
import java.awt.Component;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/* loaded from: classes.dex */
public class ReplPaneOutPort extends OutPort {
    ReplDocument document;
    String str;
    AttributeSet style;
    TextPaneWriter tout;

    public ReplPaneOutPort(ReplDocument document, String path, AttributeSet style) {
        this(new TextPaneWriter(document, style), document, path, style);
    }

    ReplPaneOutPort(TextPaneWriter tout, ReplDocument document, String path, AttributeSet style) {
        super(tout, true, true, Path.valueOf(path));
        this.str = "";
        this.tout = tout;
        this.document = document;
        this.style = style;
    }

    public void write(String str, MutableAttributeSet style) {
        flush();
        this.document.write(str, style);
        setColumnNumber(1);
    }

    public synchronized void write(Component c) {
        SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
        StyleConstants.setComponent(simpleAttributeSet, c);
        write(" ", simpleAttributeSet);
    }

    @Override // gnu.mapping.OutPort, java.io.PrintWriter
    public void print(Object v) {
        if (v instanceof Component) {
            write((Component) v);
        } else if (v instanceof Paintable) {
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
            simpleAttributeSet.addAttribute("$ename", ReplPane.PaintableElementName);
            simpleAttributeSet.addAttribute(ReplPane.PaintableAttribute, v);
            write(" ", simpleAttributeSet);
        } else if (v instanceof Viewable) {
            SimpleAttributeSet simpleAttributeSet2 = new SimpleAttributeSet();
            simpleAttributeSet2.addAttribute("$ename", ReplPane.ViewableElementName);
            simpleAttributeSet2.addAttribute(ReplPane.ViewableAttribute, v);
            write(" ", simpleAttributeSet2);
        } else {
            super.print(v);
        }
    }
}
