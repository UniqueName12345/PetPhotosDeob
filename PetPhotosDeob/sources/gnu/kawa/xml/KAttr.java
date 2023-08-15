package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

/* loaded from: classes.dex */
public class KAttr extends KNode implements Attr {
    public KAttr(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    @Override // org.w3c.dom.Attr
    public String getName() {
        return this.sequence.getNextTypeName(this.ipos);
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 2;
    }

    @Override // org.w3c.dom.Attr
    public String getValue() {
        return getNodeValue();
    }

    public static Object getObjectValue(NodeTree sequence, int ipos) {
        return sequence.getPosNext(ipos + 10);
    }

    public Object getObjectValue() {
        return getObjectValue((NodeTree) this.sequence, this.ipos);
    }

    @Override // org.w3c.dom.Attr
    public void setValue(String value) throws DOMException {
        throw new DOMException((short) 7, "setValue not supported");
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public Node getParentNode() {
        return null;
    }

    @Override // org.w3c.dom.Attr
    public Element getOwnerElement() {
        return (Element) super.getParentNode();
    }

    @Override // org.w3c.dom.Attr
    public boolean getSpecified() {
        return true;
    }

    @Override // org.w3c.dom.Attr
    public TypeInfo getSchemaTypeInfo() {
        return null;
    }

    @Override // org.w3c.dom.Attr
    public boolean isId() {
        return false;
    }
}
