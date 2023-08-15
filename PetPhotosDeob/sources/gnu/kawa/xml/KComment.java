package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.Comment;

/* loaded from: classes.dex */
public class KComment extends KCharacterData implements Comment {
    public KComment(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 8;
    }

    @Override // gnu.kawa.xml.KNode, org.w3c.dom.Node
    public String getNodeName() {
        return "#comment";
    }

    public static KComment valueOf(String text) {
        NodeTree tree = new NodeTree();
        tree.writeComment(text, 0, text.length());
        return new KComment(tree, 0);
    }
}
