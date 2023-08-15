package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.CDATASection;

/* loaded from: classes.dex */
public class KCDATASection extends KText implements CDATASection {
    public KCDATASection(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    @Override // gnu.kawa.xml.KText, gnu.kawa.xml.KNode, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 4;
    }

    @Override // gnu.kawa.xml.KText, gnu.kawa.xml.KNode, org.w3c.dom.Node
    public String getNodeName() {
        return "#cdata-section";
    }

    @Override // gnu.kawa.xml.KCharacterData, org.w3c.dom.CharacterData
    public String getData() {
        return getNodeValue();
    }

    @Override // gnu.kawa.xml.KCharacterData, org.w3c.dom.CharacterData
    public int getLength() {
        StringBuffer sbuf = new StringBuffer();
        NodeTree tlist = (NodeTree) this.sequence;
        tlist.stringValue(tlist.posToDataIndex(this.ipos), sbuf);
        return sbuf.length();
    }
}
