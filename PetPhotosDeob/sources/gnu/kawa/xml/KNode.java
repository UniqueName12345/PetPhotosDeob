package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreePosition;
import gnu.mapping.CharArrayOutPort;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.text.Path;
import gnu.xml.NodeTree;
import gnu.xml.XMLPrinter;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

/* loaded from: classes.dex */
public abstract class KNode extends SeqPosition implements Node, Consumable {
    public abstract short getNodeType();

    public KNode(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    public static Object atomicValue(Object value) {
        if (value instanceof KNode) {
            KNode node = (KNode) value;
            return ((NodeTree) node.sequence).typedValue(node.ipos);
        }
        return value;
    }

    public static KNode coerce(Object value) {
        if (value instanceof KNode) {
            return (KNode) value;
        }
        if (value instanceof NodeTree) {
            NodeTree ntree = (NodeTree) value;
            return make(ntree, ntree.startPos());
        }
        if ((value instanceof SeqPosition) && !(value instanceof TreePosition)) {
            SeqPosition seqp = (SeqPosition) value;
            if (seqp.sequence instanceof NodeTree) {
                return make((NodeTree) seqp.sequence, seqp.ipos);
            }
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static KNode make(NodeTree seq, int ipos) {
        int index = seq.posToDataIndex(ipos);
        while (index < seq.data.length && seq.data[index] == 61714) {
            index += 5;
            if (index == seq.gapStart) {
                index = seq.gapEnd;
            }
            ipos = index << 1;
        }
        int kind = seq.getNextKindI(seq.posToDataIndex(ipos));
        switch (kind) {
            case 0:
                if (!seq.isEmpty()) {
                    return null;
                }
                break;
            case 31:
                return new KCDATASection(seq, ipos);
            case 33:
                return new KElement(seq, ipos);
            case 34:
                return new KDocument(seq, ipos);
            case 35:
                return new KAttr(seq, ipos);
            case 36:
                return new KComment(seq, ipos);
            case 37:
                return new KProcessingInstruction(seq, ipos);
        }
        return new KText(seq, ipos);
    }

    @Override // gnu.lists.SeqPosition
    public KNode copy() {
        return make((NodeTree) this.sequence, this.sequence.copyPos(getPos()));
    }

    public static KNode make(NodeTree seq) {
        return make(seq, 0);
    }

    @Override // org.w3c.dom.Node
    public boolean isSupported(String feature, String version) {
        return false;
    }

    public String getNodeName() {
        return this.sequence.getNextTypeName(this.ipos);
    }

    public Symbol getNodeSymbol() {
        Object type = ((NodeTree) this.sequence).getNextTypeObject(this.ipos);
        if (type == null) {
            return null;
        }
        if (type instanceof Symbol) {
            return (Symbol) type;
        }
        return Namespace.EmptyNamespace.getSymbol(type.toString().intern());
    }

    public Object getNodeNameObject() {
        return ((NodeTree) this.sequence).getNextTypeObject(this.ipos);
    }

    @Override // org.w3c.dom.Node
    public String getNamespaceURI() {
        return ((NodeTree) this.sequence).posNamespaceURI(this.ipos);
    }

    @Override // org.w3c.dom.Node
    public String getPrefix() {
        return ((NodeTree) this.sequence).posPrefix(this.ipos);
    }

    @Override // org.w3c.dom.Node
    public String getLocalName() {
        return ((NodeTree) this.sequence).posLocalName(this.ipos);
    }

    public static String getNodeValue(NodeTree seq, int ipos) {
        StringBuffer sbuf = new StringBuffer();
        getNodeValue(seq, ipos, sbuf);
        return sbuf.toString();
    }

    public static void getNodeValue(NodeTree seq, int ipos, StringBuffer sbuf) {
        seq.stringValue(seq.posToDataIndex(ipos), sbuf);
    }

    public String getNodeValue() {
        StringBuffer sbuf = new StringBuffer();
        getNodeValue(sbuf);
        return sbuf.toString();
    }

    public void getNodeValue(StringBuffer sbuf) {
        getNodeValue((NodeTree) this.sequence, this.ipos, sbuf);
    }

    @Override // org.w3c.dom.Node
    public boolean hasChildNodes() {
        return ((NodeTree) this.sequence).posFirstChild(this.ipos) >= 0;
    }

    public String getTextContent() {
        StringBuffer sbuf = new StringBuffer();
        getTextContent(sbuf);
        return sbuf.toString();
    }

    protected void getTextContent(StringBuffer sbuf) {
        getNodeValue(sbuf);
    }

    public Node getParentNode() {
        int parent = this.sequence.parentPos(this.ipos);
        if (parent == -1) {
            return null;
        }
        return make((NodeTree) this.sequence, parent);
    }

    @Override // org.w3c.dom.Node
    public Node getPreviousSibling() {
        int previous;
        int parent = this.sequence.parentPos(this.ipos);
        if (parent == -1) {
            parent = 0;
        }
        int index = ((NodeTree) this.sequence).posToDataIndex(this.ipos);
        int child = this.sequence.firstChildPos(parent);
        do {
            previous = child;
            child = this.sequence.nextPos(child);
            if (child == 0) {
                break;
            }
        } while (((NodeTree) this.sequence).posToDataIndex(child) != index);
        if (previous == 0) {
            return null;
        }
        return make((NodeTree) this.sequence, previous);
    }

    @Override // org.w3c.dom.Node
    public Node getNextSibling() {
        int next = ((NodeTree) this.sequence).nextPos(this.ipos);
        if (next == 0) {
            return null;
        }
        return make((NodeTree) this.sequence, next);
    }

    @Override // org.w3c.dom.Node
    public Node getFirstChild() {
        int child = ((NodeTree) this.sequence).posFirstChild(this.ipos);
        return make((NodeTree) this.sequence, child);
    }

    @Override // org.w3c.dom.Node
    public Node getLastChild() {
        int last = 0;
        int child = this.sequence.firstChildPos(this.ipos);
        while (child != 0) {
            last = child;
            child = this.sequence.nextPos(child);
        }
        if (last == 0) {
            return null;
        }
        return make((NodeTree) this.sequence, last);
    }

    @Override // org.w3c.dom.Node
    public NodeList getChildNodes() {
        Nodes nodes = new SortedNodes();
        int child = this.sequence.firstChildPos(this.ipos);
        while (child != 0) {
            nodes.writePosition(this.sequence, child);
            child = this.sequence.nextPos(child);
        }
        return nodes;
    }

    public NodeList getElementsByTagName(String tagname) {
        throw new UnsupportedOperationException("getElementsByTagName not implemented yet");
    }

    @Override // org.w3c.dom.Node
    public void setNodeValue(String nodeValue) throws DOMException {
        throw new DOMException((short) 7, "setNodeValue not supported");
    }

    @Override // org.w3c.dom.Node
    public void setPrefix(String prefix) throws DOMException {
        throw new DOMException((short) 7, "setPrefix not supported");
    }

    @Override // org.w3c.dom.Node
    public Node insertBefore(Node newChild, Node refChild) throws DOMException {
        throw new DOMException((short) 7, "insertBefore not supported");
    }

    @Override // org.w3c.dom.Node
    public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
        throw new DOMException((short) 7, "replaceChild not supported");
    }

    @Override // org.w3c.dom.Node
    public Node removeChild(Node oldChild) throws DOMException {
        throw new DOMException((short) 7, "removeChild not supported");
    }

    @Override // org.w3c.dom.Node
    public Node appendChild(Node newChild) throws DOMException {
        throw new DOMException((short) 7, "appendChild not supported");
    }

    @Override // org.w3c.dom.Node
    public void setTextContent(String textContent) throws DOMException {
        throw new DOMException((short) 7, "setTextContent not supported");
    }

    @Override // org.w3c.dom.Node
    public Node cloneNode(boolean deep) {
        if (!deep) {
            throw new UnsupportedOperationException("shallow cloneNode not implemented");
        }
        NodeTree tree = new NodeTree();
        ((NodeTree) this.sequence).consumeNext(this.ipos, tree);
        return make(tree);
    }

    @Override // org.w3c.dom.Node
    public org.w3c.dom.Document getOwnerDocument() {
        int kind = this.sequence.getNextKind(this.ipos);
        if (kind == 34) {
            return new KDocument((NodeTree) this.sequence, 0);
        }
        return null;
    }

    @Override // org.w3c.dom.Node
    public NamedNodeMap getAttributes() {
        throw new UnsupportedOperationException("getAttributes not implemented yet");
    }

    @Override // org.w3c.dom.Node
    public void normalize() {
    }

    public boolean hasAttributes() {
        return false;
    }

    @Override // org.w3c.dom.Node
    public boolean isDefaultNamespace(String namespaceURI) {
        return ((NodeTree) this.sequence).posIsDefaultNamespace(this.ipos, namespaceURI);
    }

    @Override // org.w3c.dom.Node
    public String lookupNamespaceURI(String prefix) {
        return ((NodeTree) this.sequence).posLookupNamespaceURI(this.ipos, prefix);
    }

    @Override // org.w3c.dom.Node
    public String lookupPrefix(String namespaceURI) {
        return ((NodeTree) this.sequence).posLookupPrefix(this.ipos, namespaceURI);
    }

    @Override // org.w3c.dom.Node
    public String getBaseURI() {
        Path uri = ((NodeTree) this.sequence).baseUriOfPos(this.ipos, true);
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    public Path baseURI() {
        return ((NodeTree) this.sequence).baseUriOfPos(this.ipos, true);
    }

    @Override // org.w3c.dom.Node
    public short compareDocumentPosition(Node other) throws DOMException {
        if (!(other instanceof KNode)) {
            throw new DOMException((short) 9, "other Node is a " + other.getClass().getName());
        }
        KNode n = (KNode) other;
        AbstractSequence nseq = n.sequence;
        return (short) (this.sequence == nseq ? nseq.compare(this.ipos, n.ipos) : this.sequence.stableCompare(nseq));
    }

    @Override // org.w3c.dom.Node
    public boolean isSameNode(Node node) {
        if (node instanceof KNode) {
            KNode n = (KNode) node;
            if (this.sequence == n.sequence) {
                return this.sequence.equals(this.ipos, n.ipos);
            }
            return false;
        }
        return false;
    }

    @Override // org.w3c.dom.Node
    public boolean isEqualNode(Node node) {
        throw new UnsupportedOperationException("getAttributesisEqualNode not implemented yet");
    }

    @Override // gnu.lists.SeqPosition
    public String toString() {
        CharArrayOutPort wr = new CharArrayOutPort();
        XMLPrinter xp = new XMLPrinter(wr);
        ((NodeTree) this.sequence).consumeNext(this.ipos, xp);
        xp.close();
        wr.close();
        return wr.toString();
    }

    @Override // org.w3c.dom.Node
    public Object getFeature(String feature, String version) {
        return null;
    }

    @Override // gnu.lists.Consumable
    public void consume(Consumer out) {
        if (out instanceof PositionConsumer) {
            ((PositionConsumer) out).consume(this);
        } else {
            ((NodeTree) this.sequence).consumeNext(this.ipos, out);
        }
    }

    @Override // org.w3c.dom.Node
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        throw new UnsupportedOperationException("setUserData not implemented yet");
    }

    @Override // org.w3c.dom.Node
    public Object getUserData(String key) {
        return null;
    }
}
