package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;

/* loaded from: classes.dex */
public class KElement extends KNode implements Element {
    public KElement(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 1;
    }

    @Override // org.w3c.dom.Element
    public String getTagName() {
        return this.sequence.getNextTypeName(this.ipos);
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public String getNodeValue() {
        return null;
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public boolean hasAttributes() {
        return ((NodeTree) this.sequence).posHasAttributes(this.ipos);
    }

    @Override // org.w3c.dom.Element
    public String getAttribute(String name) {
        if (name == null) {
            name = "";
        }
        NodeTree nodes = (NodeTree) this.sequence;
        int attr = nodes.getAttribute(this.ipos, null, name);
        return attr == 0 ? "" : KNode.getNodeValue(nodes, attr);
    }

    @Override // org.w3c.dom.Element
    public void setAttribute(String name, String value) throws DOMException {
        throw new DOMException((short) 7, "setAttribute not supported");
    }

    @Override // org.w3c.dom.Element
    public void setIdAttribute(String name, boolean isId) throws DOMException {
        throw new DOMException((short) 7, "setIdAttribute not supported");
    }

    @Override // org.w3c.dom.Element
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
        throw new DOMException((short) 7, "setIdAttributeNS not supported");
    }

    @Override // org.w3c.dom.Element
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
        throw new DOMException((short) 7, "setIdAttributeNode not supported");
    }

    @Override // org.w3c.dom.Element
    public void removeAttribute(String name) throws DOMException {
        throw new DOMException((short) 7, "removeAttribute not supported");
    }

    @Override // org.w3c.dom.Element
    public KAttr getAttributeNode(String name) {
        if (name == null) {
            name = "";
        }
        NodeTree nodes = (NodeTree) this.sequence;
        int attr = nodes.getAttribute(this.ipos, null, name);
        if (attr == 0) {
            return null;
        }
        return new KAttr(nodes, attr);
    }

    @Override // org.w3c.dom.Element
    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        throw new DOMException((short) 7, "setAttributeNode not supported");
    }

    @Override // org.w3c.dom.Element
    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
        throw new DOMException((short) 7, "removeAttributeNode not supported");
    }

    @Override // org.w3c.dom.Element
    public String getAttributeNS(String namespaceURI, String localName) {
        if (namespaceURI == null) {
            namespaceURI = "";
        }
        if (localName == null) {
            localName = "";
        }
        NodeTree nodes = (NodeTree) this.sequence;
        int attr = nodes.getAttribute(this.ipos, namespaceURI, localName);
        return attr == 0 ? "" : getNodeValue(nodes, attr);
    }

    @Override // org.w3c.dom.Element
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
        throw new DOMException((short) 7, "setAttributeNS not supported");
    }

    @Override // org.w3c.dom.Element
    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
        throw new DOMException((short) 7, "removeAttributeNS not supported");
    }

    @Override // org.w3c.dom.Element
    public KAttr getAttributeNodeNS(String namespaceURI, String localName) {
        if (namespaceURI == null) {
            namespaceURI = "";
        }
        if (localName == null) {
            localName = "";
        }
        NodeTree nodes = (NodeTree) this.sequence;
        int attr = nodes.getAttribute(this.ipos, namespaceURI, localName);
        if (attr == 0) {
            return null;
        }
        return new KAttr(nodes, attr);
    }

    @Override // org.w3c.dom.Element
    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        throw new DOMException((short) 7, "setAttributeNodeNS not supported");
    }

    @Override // org.w3c.dom.Element
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        throw new UnsupportedOperationException("getElementsByTagNameNS not implemented yet");
    }

    @Override // org.w3c.dom.Element
    public boolean hasAttribute(String name) {
        NodeTree nodeTree = (NodeTree) this.sequence;
        int i = this.ipos;
        if (name == null) {
            name = "";
        }
        int attr = nodeTree.getAttribute(i, null, name);
        return attr != 0;
    }

    @Override // org.w3c.dom.Element
    public boolean hasAttributeNS(String namespaceURI, String localName) {
        if (namespaceURI == null) {
            namespaceURI = "";
        }
        if (localName == null) {
            localName = "";
        }
        int attr = ((NodeTree) this.sequence).getAttribute(this.ipos, namespaceURI, localName);
        return attr != 0;
    }

    @Override // org.w3c.dom.Element
    public TypeInfo getSchemaTypeInfo() {
        return null;
    }
}
