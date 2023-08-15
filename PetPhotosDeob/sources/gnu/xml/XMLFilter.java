package gnu.xml;

import gnu.expr.Keyword;
import gnu.lists.AbstractSequence;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.lists.UnescapedData;
import gnu.lists.XConsumer;
import gnu.mapping.Symbol;
import gnu.text.Char;
import gnu.text.LineBufferedReader;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import java.util.List;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/* loaded from: classes.dex */
public class XMLFilter implements DocumentHandler, ContentHandler, SourceLocator, XConsumer, PositionConsumer {
    public static final int COPY_NAMESPACES_INHERIT = 2;
    public static final int COPY_NAMESPACES_PRESERVE = 1;
    private static final int SAW_KEYWORD = 1;
    private static final int SAW_WORD = 2;
    String attrLocalName;
    String attrPrefix;
    Consumer base;
    String currentNamespacePrefix;
    protected int ignoringLevel;
    LineBufferedReader in;
    boolean inStartTag;
    SourceLocator locator;
    private SourceMessages messages;
    boolean mismatchReported;
    NamespaceBinding namespaceBindings;
    protected int nesting;
    public Consumer out;
    protected int stringizingLevel;
    TreeList tlist;
    Object[] workStack;
    public transient int copyNamespacesMode = 1;
    int previous = 0;
    protected int stringizingElementNesting = -1;
    int[] startIndexes = null;
    int attrCount = -1;
    public boolean namespacePrefixes = false;
    MappingInfo[] mappingTable = new MappingInfo[128];
    int mappingTableMask = this.mappingTable.length - 1;

    public void setSourceLocator(LineBufferedReader in) {
        this.in = in;
        this.locator = this;
    }

    public void setSourceLocator(SourceLocator locator) {
        this.locator = locator;
    }

    public void setMessages(SourceMessages messages) {
        this.messages = messages;
    }

    public NamespaceBinding findNamespaceBinding(String prefix, String uri, NamespaceBinding oldBindings) {
        NamespaceBinding namespaces;
        int hash = uri == null ? 0 : uri.hashCode();
        if (prefix != null) {
            hash ^= prefix.hashCode();
        }
        int bucket = hash & this.mappingTableMask;
        for (MappingInfo info = this.mappingTable[bucket]; info != null; info = info.nextInBucket) {
            if (info.tagHash == hash && info.prefix == prefix && (namespaces = info.namespaces) != null && namespaces.getNext() == this.namespaceBindings && namespaces.getPrefix() == prefix && info.uri == uri) {
                return info.namespaces;
            }
        }
        MappingInfo info2 = new MappingInfo();
        info2.nextInBucket = this.mappingTable[bucket];
        this.mappingTable[bucket] = info2;
        info2.tagHash = hash;
        info2.prefix = prefix;
        info2.local = uri;
        info2.uri = uri;
        if (uri == "") {
            uri = null;
        }
        info2.namespaces = new NamespaceBinding(prefix, uri, oldBindings);
        return info2.namespaces;
    }

    public MappingInfo lookupNamespaceBinding(String prefix, char[] uriChars, int uriStart, int uriLength, int uriHash, NamespaceBinding oldBindings) {
        NamespaceBinding namespaces;
        int hash = prefix == null ? uriHash : prefix.hashCode() ^ uriHash;
        int bucket = hash & this.mappingTableMask;
        for (MappingInfo info = this.mappingTable[bucket]; info != null; info = info.nextInBucket) {
            if (info.tagHash == hash && info.prefix == prefix && (namespaces = info.namespaces) != null && namespaces.getNext() == this.namespaceBindings && namespaces.getPrefix() == prefix && MappingInfo.equals(info.uri, uriChars, uriStart, uriLength)) {
                return info;
            }
        }
        MappingInfo info2 = new MappingInfo();
        info2.nextInBucket = this.mappingTable[bucket];
        this.mappingTable[bucket] = info2;
        String uri = new String(uriChars, uriStart, uriLength).intern();
        info2.tagHash = hash;
        info2.prefix = prefix;
        info2.local = uri;
        info2.uri = uri;
        if (uri == "") {
            uri = null;
        }
        info2.namespaces = new NamespaceBinding(prefix, uri, oldBindings);
        return info2;
    }

    @Override // gnu.lists.Consumer
    public void endAttribute() {
        if (this.attrLocalName != null) {
            if (this.previous == 1) {
                this.previous = 0;
                return;
            }
            if (this.stringizingElementNesting >= 0) {
                this.ignoringLevel--;
            }
            int i = this.stringizingLevel - 1;
            this.stringizingLevel = i;
            if (i == 0) {
                if (this.attrLocalName == "id" && this.attrPrefix == "xml") {
                    int valStart = this.startIndexes[this.attrCount - 1] + 5;
                    int valEnd = this.tlist.gapStart;
                    char[] data = this.tlist.data;
                    int i2 = valStart;
                    while (i2 < valEnd) {
                        int i3 = i2 + 1;
                        char datum = data[i2];
                        if ((65535 & datum) > 40959 || datum == '\t' || datum == '\r' || datum == '\n' || (datum == ' ' && (i3 == valEnd || data[i3] == ' '))) {
                            StringBuffer sbuf = new StringBuffer();
                            this.tlist.stringValue(valStart, valEnd, sbuf);
                            this.tlist.gapStart = valStart;
                            this.tlist.write(TextUtils.replaceWhitespace(sbuf.toString(), true));
                            break;
                        }
                        i2 = i3;
                    }
                }
                this.attrLocalName = null;
                this.attrPrefix = null;
                if (this.currentNamespacePrefix == null || this.namespacePrefixes) {
                    this.tlist.endAttribute();
                }
                if (this.currentNamespacePrefix != null) {
                    int attrStart = this.startIndexes[this.attrCount - 1];
                    int uriStart = attrStart;
                    int uriEnd = this.tlist.gapStart;
                    int uriLength = uriEnd - uriStart;
                    char[] data2 = this.tlist.data;
                    int uriHash = 0;
                    int i4 = uriStart;
                    while (true) {
                        if (i4 >= uriEnd) {
                            break;
                        }
                        char datum2 = data2[i4];
                        if ((65535 & datum2) > 40959) {
                            StringBuffer sbuf2 = new StringBuffer();
                            this.tlist.stringValue(uriStart, uriEnd, sbuf2);
                            uriHash = sbuf2.hashCode();
                            uriStart = 0;
                            uriLength = sbuf2.length();
                            data2 = new char[sbuf2.length()];
                            sbuf2.getChars(0, uriLength, data2, 0);
                            break;
                        }
                        uriHash = (uriHash * 31) + datum2;
                        i4++;
                    }
                    this.tlist.gapStart = attrStart;
                    String prefix = this.currentNamespacePrefix == "" ? null : this.currentNamespacePrefix;
                    MappingInfo info = lookupNamespaceBinding(prefix, data2, uriStart, uriLength, uriHash, this.namespaceBindings);
                    this.namespaceBindings = info.namespaces;
                    this.currentNamespacePrefix = null;
                }
            }
        }
    }

    private String resolve(String prefix, boolean isAttribute) {
        if (isAttribute && prefix == null) {
            return "";
        }
        String uri = this.namespaceBindings.resolve(prefix);
        if (uri == null) {
            if (prefix != null) {
                error('e', "unknown namespace prefix '" + prefix + '\'');
            }
            return "";
        }
        return uri;
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x02f1, code lost:
        r0 = r9.type;
        r9.namespaces = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x02f7, code lost:
        if (r0 != null) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x02f9, code lost:
        r29 = new gnu.xml.XName(r9.qname, r3);
        r9.type = r29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void closeStartTag() {
        /*
            Method dump skipped, instructions count: 1220
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xml.XMLFilter.closeStartTag():void");
    }

    protected boolean checkWriteAtomic() {
        this.previous = 0;
        if (this.ignoringLevel > 0) {
            return false;
        }
        closeStartTag();
        return true;
    }

    @Override // gnu.lists.Consumer
    public void write(int v) {
        if (checkWriteAtomic()) {
            this.base.write(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void writeBoolean(boolean v) {
        if (checkWriteAtomic()) {
            this.base.writeBoolean(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void writeFloat(float v) {
        if (checkWriteAtomic()) {
            this.base.writeFloat(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void writeDouble(double v) {
        if (checkWriteAtomic()) {
            this.base.writeDouble(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void writeInt(int v) {
        if (checkWriteAtomic()) {
            this.base.writeInt(v);
        }
    }

    @Override // gnu.lists.Consumer
    public void writeLong(long v) {
        if (checkWriteAtomic()) {
            this.base.writeLong(v);
        }
    }

    public void writeDocumentUri(Object uri) {
        if (this.nesting == 2 && (this.base instanceof TreeList)) {
            ((TreeList) this.base).writeDocumentUri(uri);
        }
    }

    @Override // gnu.lists.PositionConsumer
    public void consume(SeqPosition position) {
        writePosition(position.sequence, position.ipos);
    }

    @Override // gnu.lists.PositionConsumer
    public void writePosition(AbstractSequence seq, int ipos) {
        if (this.ignoringLevel <= 0) {
            if (this.stringizingLevel > 0 && this.previous == 2) {
                if (this.stringizingElementNesting < 0) {
                    write(32);
                }
                this.previous = 0;
            }
            seq.consumeNext(ipos, this);
            if (this.stringizingLevel > 0 && this.stringizingElementNesting < 0) {
                this.previous = 2;
            }
        }
    }

    @Override // gnu.lists.Consumer
    public void writeObject(Object v) {
        if (this.ignoringLevel <= 0) {
            if (v instanceof SeqPosition) {
                SeqPosition pos = (SeqPosition) v;
                writePosition(pos.sequence, pos.getPos());
            } else if (v instanceof TreeList) {
                ((TreeList) v).consume(this);
            } else if ((v instanceof List) && !(v instanceof CharSeq)) {
                List<Object> seq = (List) v;
                int i = 0;
                for (Object obj : seq) {
                    writeObject(obj);
                    i++;
                }
            } else if (v instanceof Keyword) {
                Keyword k = (Keyword) v;
                startAttribute(k.asSymbol());
                this.previous = 1;
            } else {
                closeStartTag();
                if (v instanceof UnescapedData) {
                    this.base.writeObject(v);
                    this.previous = 0;
                    return;
                }
                if (this.previous == 2) {
                    write(32);
                }
                TextUtils.textValue(v, this);
                this.previous = 2;
            }
        }
    }

    public XMLFilter(Consumer out) {
        this.base = out;
        this.out = out;
        if (out instanceof NodeTree) {
            this.tlist = (NodeTree) out;
        } else {
            this.tlist = new TreeList();
        }
        this.namespaceBindings = NamespaceBinding.predefinedXML;
    }

    @Override // gnu.lists.Consumer
    public void write(char[] data, int start, int length) {
        if (length == 0) {
            writeJoiner();
        } else if (checkWriteAtomic()) {
            this.base.write(data, start, length);
        }
    }

    @Override // gnu.lists.Consumer
    public void write(String str) {
        write(str, 0, str.length());
    }

    @Override // gnu.lists.Consumer
    public void write(CharSequence str, int start, int length) {
        if (length == 0) {
            writeJoiner();
        } else if (checkWriteAtomic()) {
            this.base.write(str, start, length);
        }
    }

    final boolean inElement() {
        int i = this.nesting;
        while (i > 0 && this.workStack[i - 1] == null) {
            i -= 2;
        }
        return i != 0;
    }

    public void linefeedFromParser() {
        if (inElement() && checkWriteAtomic()) {
            this.base.write(10);
        }
    }

    public void textFromParser(char[] data, int start, int length) {
        if (!inElement()) {
            for (int i = 0; i != length; i++) {
                if (!Character.isWhitespace(data[start + i])) {
                    error('e', "text at document level");
                    return;
                }
            }
        } else if (length > 0 && checkWriteAtomic()) {
            this.base.write(data, start, length);
        }
    }

    protected void writeJoiner() {
        this.previous = 0;
        if (this.ignoringLevel == 0) {
            ((TreeList) this.base).writeJoiner();
        }
    }

    @Override // gnu.lists.XConsumer
    public void writeCDATA(char[] data, int start, int length) {
        if (checkWriteAtomic()) {
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeCDATA(data, start, length);
            } else {
                write(data, start, length);
            }
        }
    }

    protected void startElementCommon() {
        closeStartTag();
        if (this.stringizingLevel == 0) {
            ensureSpaceInWorkStack(this.nesting);
            this.workStack[this.nesting] = this.namespaceBindings;
            this.tlist.startElement(0);
            this.base = this.tlist;
            this.attrCount = 0;
        } else {
            if (this.previous == 2 && this.stringizingElementNesting < 0) {
                write(32);
            }
            this.previous = 0;
            if (this.stringizingElementNesting < 0) {
                this.stringizingElementNesting = this.nesting;
            }
        }
        this.nesting += 2;
    }

    public void emitStartElement(char[] data, int start, int count) {
        closeStartTag();
        MappingInfo info = lookupTag(data, start, count);
        startElementCommon();
        ensureSpaceInWorkStack(this.nesting - 1);
        this.workStack[this.nesting - 1] = info;
    }

    @Override // gnu.lists.Consumer
    public void startElement(Object type) {
        NamespaceBinding inherited;
        startElementCommon();
        if (this.stringizingLevel == 0) {
            ensureSpaceInWorkStack(this.nesting - 1);
            this.workStack[this.nesting - 1] = type;
            if (this.copyNamespacesMode == 0) {
                this.namespaceBindings = NamespaceBinding.predefinedXML;
            } else if (this.copyNamespacesMode == 1 || this.nesting == 2) {
                this.namespaceBindings = type instanceof XName ? ((XName) type).getNamespaceNodes() : NamespaceBinding.predefinedXML;
            } else {
                int i = 2;
                while (true) {
                    if (i == this.nesting) {
                        inherited = null;
                        break;
                    } else if (this.workStack[i + 1] == null) {
                        i += 2;
                    } else {
                        inherited = (NamespaceBinding) this.workStack[i];
                        break;
                    }
                }
                if (inherited == null) {
                    this.namespaceBindings = type instanceof XName ? ((XName) type).getNamespaceNodes() : NamespaceBinding.predefinedXML;
                } else if (this.copyNamespacesMode == 2) {
                    this.namespaceBindings = inherited;
                } else if (type instanceof XName) {
                    NamespaceBinding preserved = ((XName) type).getNamespaceNodes();
                    NamespaceBinding join = NamespaceBinding.commonAncestor(inherited, preserved);
                    if (join == inherited) {
                        this.namespaceBindings = preserved;
                    } else {
                        this.namespaceBindings = mergeHelper(inherited, preserved);
                    }
                } else {
                    this.namespaceBindings = inherited;
                }
            }
        }
    }

    private NamespaceBinding mergeHelper(NamespaceBinding list, NamespaceBinding node) {
        if (node == NamespaceBinding.predefinedXML) {
            return list;
        }
        NamespaceBinding list2 = mergeHelper(list, node.next);
        String uri = node.uri;
        if (list2 == null) {
            if (uri == null) {
                return list2;
            }
            list2 = NamespaceBinding.predefinedXML;
        }
        String prefix = node.prefix;
        String found = list2.resolve(prefix);
        return (found != null ? found.equals(uri) : uri == null) ? list2 : findNamespaceBinding(prefix, uri, list2);
    }

    private boolean startAttributeCommon() {
        if (this.stringizingElementNesting >= 0) {
            this.ignoringLevel++;
        }
        int i = this.stringizingLevel;
        this.stringizingLevel = i + 1;
        if (i > 0) {
            return false;
        }
        if (this.attrCount < 0) {
            this.attrCount = 0;
        }
        ensureSpaceInWorkStack(this.nesting + this.attrCount);
        ensureSpaceInStartIndexes(this.attrCount);
        this.startIndexes[this.attrCount] = this.tlist.gapStart;
        this.attrCount++;
        return true;
    }

    @Override // gnu.lists.Consumer
    public void startAttribute(Object attrType) {
        this.previous = 0;
        if (attrType instanceof Symbol) {
            Symbol sym = (Symbol) attrType;
            String local = sym.getLocalPart();
            this.attrLocalName = local;
            this.attrPrefix = sym.getPrefix();
            String uri = sym.getNamespaceURI();
            if (uri == "http://www.w3.org/2000/xmlns/" || (uri == "" && local == "xmlns")) {
                error('e', "arttribute name cannot be 'xmlns' or in xmlns namespace");
            }
        }
        if (this.nesting == 2 && this.workStack[1] == null) {
            error('e', "attribute not allowed at document level");
        }
        if (this.attrCount < 0 && this.nesting > 0) {
            error('e', "attribute '" + attrType + "' follows non-attribute content");
        }
        if (startAttributeCommon()) {
            this.workStack[(this.nesting + this.attrCount) - 1] = attrType;
            if (this.nesting == 0) {
                this.base.startAttribute(attrType);
            } else {
                this.tlist.startAttribute(0);
            }
        }
    }

    public void emitStartAttribute(char[] data, int start, int count) {
        if (this.attrLocalName != null) {
            endAttribute();
        }
        if (startAttributeCommon()) {
            MappingInfo info = lookupTag(data, start, count);
            this.workStack[(this.nesting + this.attrCount) - 1] = info;
            String prefix = info.prefix;
            String local = info.local;
            this.attrLocalName = local;
            this.attrPrefix = prefix;
            if (prefix != null) {
                if (prefix == "xmlns") {
                    this.currentNamespacePrefix = local;
                }
            } else if (local == "xmlns" && prefix == null) {
                this.currentNamespacePrefix = "";
            }
            if (this.currentNamespacePrefix == null || this.namespacePrefixes) {
                this.tlist.startAttribute(0);
            }
        }
    }

    public void emitEndAttributes() {
        if (this.attrLocalName != null) {
            endAttribute();
        }
        closeStartTag();
    }

    public void emitEndElement(char[] data, int start, int length) {
        if (this.attrLocalName != null) {
            error('e', "unclosed attribute");
            endAttribute();
        }
        if (!inElement()) {
            error('e', "unmatched end element");
            return;
        }
        if (data != null) {
            MappingInfo info = lookupTag(data, start, length);
            Object old = this.workStack[this.nesting - 1];
            if ((old instanceof MappingInfo) && !this.mismatchReported) {
                MappingInfo mold = (MappingInfo) old;
                if (info.local != mold.local || info.prefix != mold.prefix) {
                    StringBuffer sbuf = new StringBuffer("</");
                    sbuf.append(data, start, length);
                    sbuf.append("> matching <");
                    String oldPrefix = mold.prefix;
                    if (oldPrefix != null) {
                        sbuf.append(oldPrefix);
                        sbuf.append(':');
                    }
                    sbuf.append(mold.local);
                    sbuf.append('>');
                    error('e', sbuf.toString());
                    this.mismatchReported = true;
                }
            }
        }
        closeStartTag();
        if (this.nesting > 0) {
            endElement();
        }
    }

    @Override // gnu.lists.Consumer
    public void endElement() {
        closeStartTag();
        this.nesting -= 2;
        this.previous = 0;
        if (this.stringizingLevel == 0) {
            this.namespaceBindings = (NamespaceBinding) this.workStack[this.nesting];
            this.workStack[this.nesting] = null;
            this.workStack[this.nesting + 1] = null;
            this.base.endElement();
        } else if (this.stringizingElementNesting == this.nesting) {
            this.stringizingElementNesting = -1;
            this.previous = 2;
        }
    }

    public void emitEntityReference(char[] name, int start, int length) {
        char c0 = name[start];
        char ch = '?';
        if (length == 2 && name[start + 1] == 't') {
            if (c0 == 'l') {
                ch = '<';
            } else if (c0 == 'g') {
                ch = '>';
            }
        } else if (length == 3) {
            if (c0 == 'a' && name[start + 1] == 'm' && name[start + 2] == 'p') {
                ch = '&';
            }
        } else if (length == 4) {
            char c1 = name[start + 1];
            char c2 = name[start + 2];
            char c3 = name[start + 3];
            if (c0 == 'q' && c1 == 'u' && c2 == 'o' && c3 == 't') {
                ch = '\"';
            } else if (c0 == 'a' && c1 == 'p' && c2 == 'o' && c3 == 's') {
                ch = '\'';
            }
        }
        write(ch);
    }

    public void emitCharacterReference(int value, char[] name, int start, int length) {
        if (value >= 65536) {
            Char.print(value, this);
        } else {
            write(value);
        }
    }

    protected void checkValidComment(char[] chars, int offset, int length) {
        int i = length;
        boolean sawHyphen = true;
        while (true) {
            i--;
            if (i >= 0) {
                boolean curHyphen = chars[offset + i] == '-';
                if (sawHyphen && curHyphen) {
                    error('e', "consecutive or final hyphen in XML comment");
                    return;
                }
                sawHyphen = curHyphen;
            } else {
                return;
            }
        }
    }

    @Override // gnu.lists.XConsumer
    public void writeComment(char[] chars, int start, int length) {
        checkValidComment(chars, start, length);
        commentFromParser(chars, start, length);
    }

    public void commentFromParser(char[] chars, int start, int length) {
        if (this.stringizingLevel == 0) {
            closeStartTag();
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeComment(chars, start, length);
            }
        } else if (this.stringizingElementNesting < 0) {
            this.base.write(chars, start, length);
        }
    }

    @Override // gnu.lists.XConsumer
    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        String target2 = TextUtils.replaceWhitespace(target, true);
        int i = offset + length;
        while (true) {
            i--;
            if (i < offset) {
                break;
            }
            char ch = content[i];
            while (true) {
                if (ch == '>' && i - 1 >= offset) {
                    ch = content[i];
                    if (ch == '?') {
                        error('e', "'?>' is not allowed in a processing-instruction");
                        break;
                    }
                }
            }
        }
        if ("xml".equalsIgnoreCase(target2)) {
            error('e', "processing-instruction target may not be 'xml' (ignoring case)");
        }
        if (!XName.isNCName(target2)) {
            error('e', "processing-instruction target '" + target2 + "' is not a valid Name");
        }
        processingInstructionCommon(target2, content, offset, length);
    }

    void processingInstructionCommon(String target, char[] content, int offset, int length) {
        if (this.stringizingLevel == 0) {
            closeStartTag();
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeProcessingInstruction(target, content, offset, length);
            }
        } else if (this.stringizingElementNesting < 0) {
            this.base.write(content, offset, length);
        }
    }

    public void processingInstructionFromParser(char[] buffer, int tstart, int tlength, int dstart, int dlength) {
        if (tlength != 3 || inElement() || buffer[tstart] != 'x' || buffer[tstart + 1] != 'm' || buffer[tstart + 2] != 'l') {
            String target = new String(buffer, tstart, tlength);
            processingInstructionCommon(target, buffer, dstart, dlength);
        }
    }

    @Override // org.xml.sax.DocumentHandler, org.xml.sax.ContentHandler, gnu.lists.Consumer
    public void startDocument() {
        closeStartTag();
        if (this.stringizingLevel > 0) {
            writeJoiner();
            return;
        }
        if (this.nesting == 0) {
            this.base.startDocument();
        } else {
            writeJoiner();
        }
        ensureSpaceInWorkStack(this.nesting);
        this.workStack[this.nesting] = this.namespaceBindings;
        this.workStack[this.nesting + 1] = null;
        this.nesting += 2;
    }

    @Override // org.xml.sax.DocumentHandler, org.xml.sax.ContentHandler, gnu.lists.Consumer
    public void endDocument() {
        if (this.stringizingLevel > 0) {
            writeJoiner();
            return;
        }
        this.nesting -= 2;
        this.namespaceBindings = (NamespaceBinding) this.workStack[this.nesting];
        this.workStack[this.nesting] = null;
        this.workStack[this.nesting + 1] = null;
        if (this.nesting == 0) {
            this.base.endDocument();
        } else {
            writeJoiner();
        }
    }

    public void emitDoctypeDecl(char[] buffer, int target, int tlength, int data, int dlength) {
    }

    @Override // gnu.lists.XConsumer
    public void beginEntity(Object baseUri) {
        if (this.base instanceof XConsumer) {
            ((XConsumer) this.base).beginEntity(baseUri);
        }
    }

    @Override // gnu.lists.XConsumer
    public void endEntity() {
        if (this.base instanceof XConsumer) {
            ((XConsumer) this.base).endEntity();
        }
    }

    @Override // java.lang.Appendable
    public XMLFilter append(char c) {
        write(c);
        return this;
    }

    @Override // java.lang.Appendable
    public XMLFilter append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        append(csq, 0, csq.length());
        return this;
    }

    @Override // java.lang.Appendable
    public XMLFilter append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        write(csq, start, end - start);
        return this;
    }

    MappingInfo lookupTag(Symbol qname) {
        String local = qname.getLocalPart();
        String prefix = qname.getPrefix();
        if (prefix == "") {
            prefix = null;
        }
        String uri = qname.getNamespaceURI();
        int hash = MappingInfo.hash(prefix, local);
        int index = hash & this.mappingTableMask;
        MappingInfo first = this.mappingTable[index];
        for (MappingInfo info = first; info != null; info = info.nextInBucket) {
            if (qname == info.qname) {
                return info;
            }
            if (local == info.local && info.qname == null && ((uri == info.uri || info.uri == null) && prefix == info.prefix)) {
                info.uri = uri;
                info.qname = qname;
                return info;
            }
        }
        MappingInfo info2 = new MappingInfo();
        info2.qname = qname;
        info2.prefix = prefix;
        info2.uri = uri;
        info2.local = local;
        info2.tagHash = hash;
        info2.nextInBucket = first;
        this.mappingTable[index] = first;
        return info2;
    }

    MappingInfo lookupTag(char[] data, int start, int length) {
        int hash = 0;
        int prefixHash = 0;
        int colon = -1;
        for (int i = 0; i < length; i++) {
            char ch = data[start + i];
            if (ch == ':' && colon < 0) {
                colon = i;
                prefixHash = hash;
                hash = 0;
            } else {
                hash = (hash * 31) + ch;
            }
        }
        int hash2 = hash ^ prefixHash;
        int index = hash2 & this.mappingTableMask;
        MappingInfo first = this.mappingTable[index];
        for (MappingInfo info = first; info != null; info = info.nextInBucket) {
            if (hash2 == info.tagHash && info.match(data, start, length)) {
                return info;
            }
        }
        MappingInfo info2 = new MappingInfo();
        info2.tagHash = hash2;
        if (colon >= 0) {
            info2.prefix = new String(data, start, colon).intern();
            int colon2 = colon + 1;
            int lstart = start + colon2;
            info2.local = new String(data, lstart, length - colon2).intern();
        } else {
            info2.prefix = null;
            info2.local = new String(data, start, length).intern();
        }
        info2.nextInBucket = first;
        this.mappingTable[index] = first;
        return info2;
    }

    private void ensureSpaceInWorkStack(int oldSize) {
        if (this.workStack == null) {
            this.workStack = new Object[20];
        } else if (oldSize >= this.workStack.length) {
            Object[] tmpn = new Object[this.workStack.length * 2];
            System.arraycopy(this.workStack, 0, tmpn, 0, oldSize);
            this.workStack = tmpn;
        }
    }

    private void ensureSpaceInStartIndexes(int oldSize) {
        if (this.startIndexes == null) {
            this.startIndexes = new int[20];
        } else if (oldSize >= this.startIndexes.length) {
            int[] tmpn = new int[this.startIndexes.length * 2];
            System.arraycopy(this.startIndexes, 0, tmpn, 0, oldSize);
            this.startIndexes = tmpn;
        }
    }

    public static String duplicateAttributeMessage(Symbol attrSymbol, Object elementName) {
        StringBuffer sbuf = new StringBuffer("duplicate attribute: ");
        String uri = attrSymbol.getNamespaceURI();
        if (uri != null && uri.length() > 0) {
            sbuf.append('{');
            sbuf.append('}');
            sbuf.append(uri);
        }
        sbuf.append(attrSymbol.getLocalPart());
        if (elementName != null) {
            sbuf.append(" in <");
            sbuf.append(elementName);
            sbuf.append('>');
        }
        return sbuf.toString();
    }

    public void error(char severity, String message) {
        if (this.messages == null) {
            throw new RuntimeException(message);
        }
        if (this.locator != null) {
            this.messages.error(severity, this.locator, message);
        } else {
            this.messages.error(severity, message);
        }
    }

    @Override // gnu.lists.Consumer
    public boolean ignoring() {
        return this.ignoringLevel > 0;
    }

    @Override // org.xml.sax.DocumentHandler, org.xml.sax.ContentHandler
    public void setDocumentLocator(Locator locator) {
        if (locator instanceof SourceLocator) {
            this.locator = (SourceLocator) locator;
        }
    }

    @Override // org.xml.sax.ContentHandler
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
        startElement(Symbol.make(namespaceURI, localName));
        int numAttributes = atts.getLength();
        for (int i = 0; i < numAttributes; i++) {
            startAttribute(Symbol.make(atts.getURI(i), atts.getLocalName(i)));
            write(atts.getValue(i));
            endAttribute();
        }
    }

    @Override // org.xml.sax.ContentHandler
    public void endElement(String namespaceURI, String localName, String qName) {
        endElement();
    }

    @Override // org.xml.sax.DocumentHandler
    public void startElement(String name, AttributeList atts) {
        startElement(name.intern());
        int attrLength = atts.getLength();
        for (int i = 0; i < attrLength; i++) {
            String name2 = atts.getName(i);
            String name3 = name2.intern();
            atts.getType(i);
            String value = atts.getValue(i);
            startAttribute(name3);
            write(value);
            endAttribute();
        }
    }

    @Override // org.xml.sax.DocumentHandler
    public void endElement(String name) throws SAXException {
        endElement();
    }

    @Override // org.xml.sax.DocumentHandler, org.xml.sax.ContentHandler
    public void characters(char[] ch, int start, int length) throws SAXException {
        write(ch, start, length);
    }

    @Override // org.xml.sax.DocumentHandler, org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        write(ch, start, length);
    }

    @Override // org.xml.sax.DocumentHandler, org.xml.sax.ContentHandler
    public void processingInstruction(String target, String data) {
        char[] chars = data.toCharArray();
        processingInstructionCommon(target, chars, 0, chars.length);
    }

    @Override // org.xml.sax.ContentHandler
    public void startPrefixMapping(String prefix, String uri) {
        this.namespaceBindings = findNamespaceBinding(prefix.intern(), uri.intern(), this.namespaceBindings);
    }

    @Override // org.xml.sax.ContentHandler
    public void endPrefixMapping(String prefix) {
        this.namespaceBindings = this.namespaceBindings.getNext();
    }

    @Override // org.xml.sax.ContentHandler
    public void skippedEntity(String name) {
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public String getPublicId() {
        return null;
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public String getSystemId() {
        if (this.in == null) {
            return null;
        }
        return this.in.getName();
    }

    @Override // gnu.text.SourceLocator
    public String getFileName() {
        if (this.in == null) {
            return null;
        }
        return this.in.getName();
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public int getLineNumber() {
        int line;
        if (this.in != null && (line = this.in.getLineNumber()) >= 0) {
            return line + 1;
        }
        return -1;
    }

    @Override // gnu.text.SourceLocator, org.xml.sax.Locator
    public int getColumnNumber() {
        int col;
        if (this.in == null || (col = this.in.getColumnNumber()) <= 0) {
            return -1;
        }
        return col;
    }

    @Override // gnu.text.SourceLocator
    public boolean isStableSourceLocation() {
        return false;
    }
}
