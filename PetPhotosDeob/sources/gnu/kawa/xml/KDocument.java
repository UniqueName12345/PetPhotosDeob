package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/* loaded from: classes.dex */
public class KDocument extends KNode implements org.w3c.dom.Document {
    public KDocument(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public String getNodeName() {
        return "#document";
    }

    @Override // org.w3c.dom.Document
    public DOMImplementation getImplementation() {
        throw new UnsupportedOperationException("getImplementation not implemented");
    }

    @Override // org.w3c.dom.Document
    public DocumentType getDoctype() {
        return null;
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public Node getParentNode() {
        return null;
    }

    @Override // org.w3c.dom.Document
    public KElement getDocumentElement() {
        int child = ((NodeTree) this.sequence).posFirstChild(this.ipos);
        while (child != -1) {
            if (this.sequence.getNextKind(child) == 36) {
                child = this.sequence.nextPos(child);
            } else {
                return (KElement) make((NodeTree) this.sequence, child);
            }
        }
        return null;
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 9;
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public String getNodeValue() {
        return null;
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public String getTextContent() {
        return null;
    }

    @Override // gnu.kawa.xml.KNode
    protected void getTextContent(StringBuffer sbuf) {
    }

    @Override // org.w3c.dom.Document
    public Element createElement(String tagName) {
        throw new UnsupportedOperationException("createElement not implemented");
    }

    @Override // org.w3c.dom.Document
    public DocumentFragment createDocumentFragment() {
        throw new UnsupportedOperationException("createDocumentFragment not implemented");
    }

    @Override // org.w3c.dom.Document
    public Text createTextNode(String data) {
        throw new UnsupportedOperationException("createTextNode not implemented");
    }

    @Override // org.w3c.dom.Document
    public Comment createComment(String data) {
        throw new UnsupportedOperationException("createComment not implemented");
    }

    @Override // org.w3c.dom.Document
    public CDATASection createCDATASection(String data) {
        throw new UnsupportedOperationException("createCDATASection not implemented");
    }

    @Override // org.w3c.dom.Document
    public ProcessingInstruction createProcessingInstruction(String target, String data) {
        throw new UnsupportedOperationException("createProcessingInstruction not implemented");
    }

    @Override // org.w3c.dom.Document
    public Attr createAttribute(String name) {
        throw new UnsupportedOperationException("createAttribute not implemented");
    }

    @Override // org.w3c.dom.Document
    public EntityReference createEntityReference(String name) {
        throw new UnsupportedOperationException("createEntityReference implemented");
    }

    @Override // org.w3c.dom.Document
    public Node importNode(Node importedNode, boolean deep) {
        throw new UnsupportedOperationException("importNode not implemented");
    }

    @Override // org.w3c.dom.Document
    public Element createElementNS(String namespaceURI, String qualifiedName) {
        throw new UnsupportedOperationException("createElementNS not implemented");
    }

    @Override // org.w3c.dom.Document
    public Attr createAttributeNS(String namespaceURI, String qualifiedName) {
        throw new UnsupportedOperationException("createAttributeNS not implemented");
    }

    @Override // org.w3c.dom.Document
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        throw new UnsupportedOperationException("getElementsByTagNameNS not implemented yet");
    }

    @Override // org.w3c.dom.Document
    public Element getElementById(String elementId) {
        return null;
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public boolean hasAttributes() {
        return false;
    }

    @Override // org.w3c.dom.Document
    public String getInputEncoding() {
        return null;
    }

    @Override // org.w3c.dom.Document
    public String getXmlEncoding() {
        return null;
    }

    @Override // org.w3c.dom.Document
    public boolean getXmlStandalone() {
        return false;
    }

    @Override // org.w3c.dom.Document
    public void setXmlStandalone(boolean xmlStandalone) {
    }

    @Override // org.w3c.dom.Document
    public String getXmlVersion() {
        return "1.1";
    }

    @Override // org.w3c.dom.Document
    public void setXmlVersion(String xmlVersion) {
    }

    @Override // org.w3c.dom.Document
    public boolean getStrictErrorChecking() {
        return false;
    }

    @Override // org.w3c.dom.Document
    public void setStrictErrorChecking(boolean strictErrorChecking) {
    }

    @Override // org.w3c.dom.Document
    public String getDocumentURI() {
        return null;
    }

    @Override // org.w3c.dom.Document
    public void setDocumentURI(String documentURI) {
    }

    @Override // org.w3c.dom.Document
    public Node renameNode(Node n, String namespaceURI, String qualifiedname) throws DOMException {
        throw new DOMException((short) 9, "renameNode not implemented");
    }

    @Override // org.w3c.dom.Document
    public Node adoptNode(Node source) throws DOMException {
        throw new DOMException((short) 9, "adoptNode not implemented");
    }

    @Override // org.w3c.dom.Document
    public void normalizeDocument() {
    }

    @Override // org.w3c.dom.Document
    public DOMConfiguration getDomConfig() {
        throw new DOMException((short) 9, "getDomConfig not implemented");
    }
}
