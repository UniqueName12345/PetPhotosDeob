package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;

/* loaded from: classes.dex */
public abstract class KCharacterData extends KNode implements CharacterData {
    public KCharacterData(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    public int getLength() {
        StringBuffer sbuf = new StringBuffer();
        NodeTree tlist = (NodeTree) this.sequence;
        tlist.stringValue(tlist.posToDataIndex(this.ipos), sbuf);
        return sbuf.length();
    }

    public String getData() {
        return getNodeValue();
    }

    @Override // org.w3c.dom.CharacterData
    public void setData(String data) throws DOMException {
        throw new DOMException((short) 7, "setData not supported");
    }

    @Override // org.w3c.dom.CharacterData
    public String substringData(int offset, int count) throws DOMException {
        String data = getData();
        if (offset < 0 || count < 0 || offset + count >= data.length()) {
            throw new DOMException((short) 1, "invalid index to substringData");
        }
        return data.substring(offset, count);
    }

    @Override // org.w3c.dom.CharacterData
    public void appendData(String data) throws DOMException {
        throw new DOMException((short) 7, "appendData not supported");
    }

    @Override // org.w3c.dom.CharacterData
    public void insertData(int offset, String data) throws DOMException {
        replaceData(offset, 0, data);
    }

    @Override // org.w3c.dom.CharacterData
    public void deleteData(int offset, int count) throws DOMException {
        replaceData(offset, count, "");
    }

    @Override // org.w3c.dom.CharacterData
    public void replaceData(int offset, int count, String arg) throws DOMException {
        throw new DOMException((short) 7, "replaceData not supported");
    }
}
