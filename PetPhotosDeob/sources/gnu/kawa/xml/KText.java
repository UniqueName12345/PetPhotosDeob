package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.DOMException;
import org.w3c.dom.Text;

/* loaded from: classes.dex */
public class KText extends KCharacterData implements Text {
    public KText(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    public static KText make(String text) {
        NodeTree tree = new NodeTree();
        tree.append((CharSequence) text);
        return new KText(tree, 0);
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 3;
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public String getNodeName() {
        return "#text";
    }

    @Override // org.w3c.dom.Text
    public Text splitText(int offset) throws DOMException {
        throw new DOMException((short) 7, "splitText not supported");
    }

    @Override // org.w3c.dom.Text
    public String getWholeText() {
        throw new UnsupportedOperationException("getWholeText not implemented yet");
    }

    @Override // org.w3c.dom.Text
    public Text replaceWholeText(String content) throws DOMException {
        throw new DOMException((short) 7, "splitText not supported");
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public boolean hasAttributes() {
        return false;
    }

    @Override // org.w3c.dom.Text
    public boolean isElementContentWhitespace() {
        return false;
    }
}
