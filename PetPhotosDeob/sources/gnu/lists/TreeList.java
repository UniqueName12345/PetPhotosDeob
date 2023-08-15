package gnu.lists;

import androidx.appcompat.widget.ActivityChooserView;
import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.text.Char;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class TreeList extends AbstractSequence implements Appendable, XConsumer, PositionConsumer, Consumable {
    protected static final int BEGIN_ATTRIBUTE_LONG = 61705;
    public static final int BEGIN_ATTRIBUTE_LONG_SIZE = 5;
    protected static final int BEGIN_DOCUMENT = 61712;
    protected static final int BEGIN_ELEMENT_LONG = 61704;
    protected static final int BEGIN_ELEMENT_SHORT = 40960;
    protected static final int BEGIN_ELEMENT_SHORT_INDEX_MAX = 4095;
    public static final int BEGIN_ENTITY = 61714;
    public static final int BEGIN_ENTITY_SIZE = 5;
    static final char BOOL_FALSE = 61696;
    static final char BOOL_TRUE = 61697;
    static final int BYTE_PREFIX = 61440;
    static final int CDATA_SECTION = 61717;
    static final int CHAR_FOLLOWS = 61702;
    static final int COMMENT = 61719;
    protected static final int DOCUMENT_URI = 61720;
    static final int DOUBLE_FOLLOWS = 61701;
    static final int END_ATTRIBUTE = 61706;
    public static final int END_ATTRIBUTE_SIZE = 1;
    protected static final int END_DOCUMENT = 61713;
    protected static final int END_ELEMENT_LONG = 61708;
    protected static final int END_ELEMENT_SHORT = 61707;
    protected static final int END_ENTITY = 61715;
    static final int FLOAT_FOLLOWS = 61700;
    public static final int INT_FOLLOWS = 61698;
    static final int INT_SHORT_ZERO = 49152;
    static final int JOINER = 61718;
    static final int LONG_FOLLOWS = 61699;
    public static final int MAX_CHAR_SHORT = 40959;
    static final int MAX_INT_SHORT = 8191;
    static final int MIN_INT_SHORT = -4096;
    static final char OBJECT_REF_FOLLOWS = 61709;
    static final int OBJECT_REF_SHORT = 57344;
    static final int OBJECT_REF_SHORT_INDEX_MAX = 4095;
    protected static final char POSITION_PAIR_FOLLOWS = 61711;
    static final char POSITION_REF_FOLLOWS = 61710;
    protected static final int PROCESSING_INSTRUCTION = 61716;
    public int attrStart;
    int currentParent;
    public char[] data;
    public int docStart;
    public int gapEnd;
    public int gapStart;
    public Object[] objects;
    public int oindex;

    public TreeList() {
        this.currentParent = -1;
        resizeObjects();
        this.gapEnd = HttpRequestContext.HTTP_OK;
        this.data = new char[this.gapEnd];
    }

    public TreeList(TreeList list, int startPosition, int endPosition) {
        this();
        list.consumeIRange(startPosition, endPosition, this);
    }

    public TreeList(TreeList list) {
        this(list, 0, list.data.length);
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public void clear() {
        this.gapStart = 0;
        this.gapEnd = this.data.length;
        this.attrStart = 0;
        if (this.gapEnd > 1500) {
            this.gapEnd = HttpRequestContext.HTTP_OK;
            this.data = new char[this.gapEnd];
        }
        this.objects = null;
        this.oindex = 0;
        resizeObjects();
    }

    public void ensureSpace(int needed) {
        int avail = this.gapEnd - this.gapStart;
        if (needed > avail) {
            int oldSize = this.data.length;
            int neededSize = (oldSize - avail) + needed;
            int newSize = oldSize * 2;
            if (newSize < neededSize) {
                newSize = neededSize;
            }
            char[] tmp = new char[newSize];
            if (this.gapStart > 0) {
                System.arraycopy(this.data, 0, tmp, 0, this.gapStart);
            }
            int afterGap = oldSize - this.gapEnd;
            if (afterGap > 0) {
                System.arraycopy(this.data, this.gapEnd, tmp, newSize - afterGap, afterGap);
            }
            this.gapEnd = newSize - afterGap;
            this.data = tmp;
        }
    }

    public final void resizeObjects() {
        Object[] tmp;
        if (this.objects == null) {
            tmp = new Object[100];
        } else {
            int oldLength = this.objects.length;
            int newLength = oldLength * 2;
            tmp = new Object[newLength];
            System.arraycopy(this.objects, 0, tmp, 0, oldLength);
        }
        this.objects = tmp;
    }

    public int find(Object arg1) {
        if (this.oindex == this.objects.length) {
            resizeObjects();
        }
        this.objects[this.oindex] = arg1;
        int i = this.oindex;
        this.oindex = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getIntN(int index) {
        return (this.data[index] << 16) | (this.data[index + 1] & LispReader.TOKEN_ESCAPE_CHAR);
    }

    protected final long getLongN(int index) {
        char[] data = this.data;
        return ((data[index] & 65535) << 48) | ((data[index + 1] & 65535) << 32) | ((data[index + 2] & 65535) << 16) | (data[index + 3] & 65535);
    }

    public final void setIntN(int index, int i) {
        this.data[index] = (char) (i >> 16);
        this.data[index + 1] = (char) i;
    }

    @Override // gnu.lists.PositionConsumer
    public void consume(SeqPosition position) {
        ensureSpace(3);
        int index = find(position.copy());
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = POSITION_REF_FOLLOWS;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        ensureSpace(5);
        this.data[this.gapStart] = POSITION_PAIR_FOLLOWS;
        int seq_index = find(seq);
        setIntN(this.gapStart + 1, seq_index);
        setIntN(this.gapStart + 3, ipos);
        this.gapStart += 5;
    }

    public void writeObject(Object v) {
        ensureSpace(3);
        int index = find(v);
        if (index < 4096) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = (char) (OBJECT_REF_SHORT | index);
            return;
        }
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = OBJECT_REF_FOLLOWS;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writeDocumentUri(Object uri) {
        ensureSpace(3);
        int index = find(uri);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61720;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writeComment(char[] chars, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61719;
        setIntN(i2, length);
        int i3 = i2 + 2;
        System.arraycopy(chars, offset, this.data, i3, length);
        this.gapStart = i3 + length;
    }

    public void writeComment(String comment, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61719;
        setIntN(i2, length);
        int i3 = i2 + 2;
        comment.getChars(offset, offset + length, this.data, i3);
        this.gapStart = i3 + length;
    }

    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        ensureSpace(length + 5);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61716;
        int index = find(target);
        setIntN(i2, index);
        setIntN(i2 + 2, length);
        int i3 = i2 + 4;
        System.arraycopy(content, offset, this.data, i3, length);
        this.gapStart = i3 + length;
    }

    public void writeProcessingInstruction(String target, String content, int offset, int length) {
        ensureSpace(length + 5);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61716;
        int index = find(target);
        setIntN(i2, index);
        setIntN(i2 + 2, length);
        int i3 = i2 + 4;
        content.getChars(offset, offset + length, this.data, i3);
        this.gapStart = i3 + length;
    }

    public void startElement(Object type) {
        startElement(find(type));
    }

    public void startDocument() {
        ensureSpace(6);
        this.gapEnd--;
        int p = this.gapStart;
        this.data[p] = 61712;
        if (this.docStart != 0) {
            throw new Error("nested document");
        }
        this.docStart = p + 1;
        setIntN(p + 1, this.gapEnd - this.data.length);
        setIntN(p + 3, this.currentParent != -1 ? this.currentParent - p : -1);
        this.currentParent = p;
        this.gapStart = p + 5;
        this.currentParent = p;
        this.data[this.gapEnd] = 61713;
    }

    public void endDocument() {
        if (this.data[this.gapEnd] != END_DOCUMENT || this.docStart <= 0 || this.data[this.currentParent] != BEGIN_DOCUMENT) {
            throw new Error("unexpected endDocument");
        }
        this.gapEnd++;
        setIntN(this.docStart, (this.gapStart - this.docStart) + 1);
        this.docStart = 0;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61713;
        int parent = getIntN(this.currentParent + 3);
        if (parent < -1) {
            parent += this.currentParent;
        }
        this.currentParent = parent;
    }

    public void beginEntity(Object base) {
        if (this.gapStart == 0) {
            ensureSpace(6);
            this.gapEnd--;
            int p = this.gapStart;
            this.data[p] = 61714;
            setIntN(p + 1, find(base));
            setIntN(p + 3, this.currentParent != -1 ? this.currentParent - p : -1);
            this.gapStart = p + 5;
            this.currentParent = p;
            this.data[this.gapEnd] = 61715;
        }
    }

    public void endEntity() {
        if (this.gapEnd + 1 == this.data.length && this.data[this.gapEnd] == END_ENTITY) {
            if (this.data[this.currentParent] != 61714) {
                throw new Error("unexpected endEntity");
            }
            this.gapEnd++;
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = 61715;
            int parent = getIntN(this.currentParent + 3);
            if (parent < -1) {
                parent += this.currentParent;
            }
            this.currentParent = parent;
        }
    }

    public void startElement(int index) {
        ensureSpace(10);
        this.gapEnd -= 7;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61704;
        setIntN(this.gapStart, this.gapEnd - this.data.length);
        this.gapStart += 2;
        this.data[this.gapEnd] = 61708;
        setIntN(this.gapEnd + 1, index);
        setIntN(this.gapEnd + 3, this.gapStart - 3);
        setIntN(this.gapEnd + 5, this.currentParent);
        this.currentParent = this.gapStart - 3;
    }

    public void setElementName(int elementIndex, int nameIndex) {
        if (this.data[elementIndex] == BEGIN_ELEMENT_LONG) {
            int j = getIntN(elementIndex + 1);
            if (j < 0) {
                elementIndex = this.data.length;
            }
            elementIndex += j;
        }
        if (elementIndex < this.gapEnd) {
            throw new Error("setElementName before gapEnd");
        }
        setIntN(elementIndex + 1, nameIndex);
    }

    public void endElement() {
        if (this.data[this.gapEnd] != END_ELEMENT_LONG) {
            throw new Error("unexpected endElement");
        }
        int index = getIntN(this.gapEnd + 1);
        int begin = getIntN(this.gapEnd + 3);
        int parent = getIntN(this.gapEnd + 5);
        this.currentParent = parent;
        this.gapEnd += 7;
        int offset = this.gapStart - begin;
        int parentOffset = begin - parent;
        if (index < 4095 && offset < 65536 && parentOffset < 65536) {
            this.data[begin] = (char) (BEGIN_ELEMENT_SHORT | index);
            this.data[begin + 1] = (char) offset;
            this.data[begin + 2] = (char) parentOffset;
            this.data[this.gapStart] = 61707;
            this.data[this.gapStart + 1] = (char) offset;
            this.gapStart += 2;
            return;
        }
        this.data[begin] = 61704;
        setIntN(begin + 1, offset);
        this.data[this.gapStart] = 61708;
        setIntN(this.gapStart + 1, index);
        setIntN(this.gapStart + 3, -offset);
        if (parent >= this.gapStart || begin <= this.gapStart) {
            parent -= this.gapStart;
        }
        setIntN(this.gapStart + 5, parent);
        this.gapStart += 7;
    }

    public void startAttribute(Object attrType) {
        startAttribute(find(attrType));
    }

    public void startAttribute(int index) {
        ensureSpace(6);
        this.gapEnd--;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61705;
        if (this.attrStart != 0) {
            throw new Error("nested attribute");
        }
        this.attrStart = this.gapStart;
        setIntN(this.gapStart, index);
        setIntN(this.gapStart + 2, this.gapEnd - this.data.length);
        this.gapStart += 4;
        this.data[this.gapEnd] = 61706;
    }

    public void setAttributeName(int attrIndex, int nameIndex) {
        setIntN(attrIndex + 1, nameIndex);
    }

    public void endAttribute() {
        if (this.attrStart > 0) {
            if (this.data[this.gapEnd] != END_ATTRIBUTE) {
                throw new Error("unexpected endAttribute");
            }
            this.gapEnd++;
            setIntN(this.attrStart + 2, (this.gapStart - this.attrStart) + 1);
            this.attrStart = 0;
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = 61706;
        }
    }

    @Override // java.lang.Appendable
    public Consumer append(char c) {
        write(c);
        return this;
    }

    public void write(int c) {
        ensureSpace(3);
        if (c <= 40959) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = (char) c;
        } else if (c < 65536) {
            char[] cArr2 = this.data;
            int i2 = this.gapStart;
            this.gapStart = i2 + 1;
            cArr2[i2] = 61702;
            char[] cArr3 = this.data;
            int i3 = this.gapStart;
            this.gapStart = i3 + 1;
            cArr3[i3] = (char) c;
        } else {
            Char.print(c, this);
        }
    }

    public void writeBoolean(boolean v) {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = v ? BOOL_TRUE : BOOL_FALSE;
    }

    public void writeByte(int v) {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) (BYTE_PREFIX + (v & 255));
    }

    public void writeInt(int v) {
        ensureSpace(3);
        if (v >= MIN_INT_SHORT && v <= MAX_INT_SHORT) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = (char) (INT_SHORT_ZERO + v);
            return;
        }
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = 61698;
        setIntN(this.gapStart, v);
        this.gapStart += 2;
    }

    public void writeLong(long v) {
        ensureSpace(5);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61699;
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = (char) (v >>> 48);
        char[] cArr3 = this.data;
        int i3 = this.gapStart;
        this.gapStart = i3 + 1;
        cArr3[i3] = (char) (v >>> 32);
        char[] cArr4 = this.data;
        int i4 = this.gapStart;
        this.gapStart = i4 + 1;
        cArr4[i4] = (char) (v >>> 16);
        char[] cArr5 = this.data;
        int i5 = this.gapStart;
        this.gapStart = i5 + 1;
        cArr5[i5] = (char) v;
    }

    public void writeFloat(float v) {
        ensureSpace(3);
        int i = Float.floatToIntBits(v);
        char[] cArr = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr[i2] = 61700;
        char[] cArr2 = this.data;
        int i3 = this.gapStart;
        this.gapStart = i3 + 1;
        cArr2[i3] = (char) (i >>> 16);
        char[] cArr3 = this.data;
        int i4 = this.gapStart;
        this.gapStart = i4 + 1;
        cArr3[i4] = (char) i;
    }

    public void writeDouble(double v) {
        ensureSpace(5);
        long l = Double.doubleToLongBits(v);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61701;
        char[] cArr2 = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr2[i2] = (char) (l >>> 48);
        char[] cArr3 = this.data;
        int i3 = this.gapStart;
        this.gapStart = i3 + 1;
        cArr3[i3] = (char) (l >>> 32);
        char[] cArr4 = this.data;
        int i4 = this.gapStart;
        this.gapStart = i4 + 1;
        cArr4[i4] = (char) (l >>> 16);
        char[] cArr5 = this.data;
        int i5 = this.gapStart;
        this.gapStart = i5 + 1;
        cArr5[i5] = (char) l;
    }

    @Override // gnu.lists.Consumer
    public boolean ignoring() {
        return false;
    }

    public void writeJoiner() {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = 61718;
    }

    public void write(char[] buf, int off, int len) {
        if (len == 0) {
            writeJoiner();
        }
        ensureSpace(len);
        int off2 = off;
        while (len > 0) {
            int off3 = off2 + 1;
            char ch = buf[off2];
            len--;
            if (ch <= 40959) {
                char[] cArr = this.data;
                int i = this.gapStart;
                this.gapStart = i + 1;
                cArr[i] = ch;
            } else {
                write(ch);
                ensureSpace(len);
            }
            off2 = off3;
        }
    }

    public void write(String str) {
        write(str, 0, str.length());
    }

    public void write(CharSequence str, int start, int length) {
        if (length == 0) {
            writeJoiner();
        }
        ensureSpace(length);
        int start2 = start;
        while (length > 0) {
            int start3 = start2 + 1;
            char ch = str.charAt(start2);
            length--;
            if (ch <= 40959) {
                char[] cArr = this.data;
                int i = this.gapStart;
                this.gapStart = i + 1;
                cArr[i] = ch;
            } else {
                write(ch);
                ensureSpace(length);
            }
            start2 = start3;
        }
    }

    public void writeCDATA(char[] chars, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = 61717;
        setIntN(i2, length);
        int i3 = i2 + 2;
        System.arraycopy(chars, offset, this.data, i3, length);
        this.gapStart = i3 + length;
    }

    @Override // java.lang.Appendable
    public Consumer append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        return append(csq, 0, csq.length());
    }

    @Override // java.lang.Appendable
    public Consumer append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        for (int i = start; i < end; i++) {
            append(csq.charAt(i));
        }
        return this;
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List, java.util.Collection
    public boolean isEmpty() {
        int pos = this.gapStart == 0 ? this.gapEnd : 0;
        return pos == this.data.length;
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Sequence, java.util.List, java.util.Collection, com.google.appinventor.components.runtime.util.YailObject
    public int size() {
        int size = 0;
        int i = 0;
        while (true) {
            i = nextPos(i);
            if (i == 0) {
                return size;
            }
            size++;
        }
    }

    @Override // gnu.lists.AbstractSequence
    public int createPos(int index, boolean isAfter) {
        return createRelativePos(0, index, isAfter);
    }

    public final int posToDataIndex(int ipos) {
        if (ipos == -1) {
            return this.data.length;
        }
        int index = ipos >>> 1;
        if ((ipos & 1) != 0) {
            index--;
        }
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if ((ipos & 1) != 0) {
            int index2 = nextDataIndex(index);
            if (index2 < 0) {
                return this.data.length;
            }
            if (index2 == this.gapStart) {
                return index2 + (this.gapEnd - this.gapStart);
            }
            return index2;
        }
        return index;
    }

    @Override // gnu.lists.AbstractSequence
    public int firstChildPos(int ipos) {
        int index = gotoChildrenStart(posToDataIndex(ipos));
        if (index < 0) {
            return 0;
        }
        return index << 1;
    }

    public final int gotoChildrenStart(int index) {
        int index2;
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        if ((datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) || datum == BEGIN_ELEMENT_LONG) {
            index2 = index + 3;
        } else if (datum != BEGIN_DOCUMENT && datum != 61714) {
            return -1;
        } else {
            index2 = index + 5;
        }
        while (true) {
            if (index2 >= this.gapStart) {
                index2 += this.gapEnd - this.gapStart;
            }
            char datum2 = this.data[index2];
            if (datum2 == BEGIN_ATTRIBUTE_LONG) {
                int end = getIntN(index2 + 3);
                if (end < 0) {
                    index2 = this.data.length;
                }
                index2 += end;
            } else if (datum2 == END_ATTRIBUTE || datum2 == JOINER) {
                index2++;
            } else if (datum2 != DOCUMENT_URI) {
                return index2;
            } else {
                index2 += 3;
            }
        }
    }

    @Override // gnu.lists.AbstractSequence
    public int parentPos(int ipos) {
        int index = posToDataIndex(ipos);
        do {
            index = parentOrEntityI(index);
            if (index == -1) {
                return -1;
            }
        } while (this.data[index] == 61714);
        return index << 1;
    }

    public int parentOrEntityPos(int ipos) {
        int index = parentOrEntityI(posToDataIndex(ipos));
        if (index < 0) {
            return -1;
        }
        return index << 1;
    }

    public int parentOrEntityI(int index) {
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        if (datum == BEGIN_DOCUMENT || datum == 61714) {
            int parent_offset = getIntN(index + 3);
            return parent_offset >= -1 ? parent_offset : index + parent_offset;
        } else if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
            char c = this.data[index + 2];
            if (c != 0) {
                return index - c;
            }
            return -1;
        } else if (datum == BEGIN_ELEMENT_LONG) {
            int end_offset = getIntN(index + 1);
            int end_offset2 = end_offset + (end_offset < 0 ? this.data.length : index);
            int parent_offset2 = getIntN(end_offset2 + 5);
            if (parent_offset2 != 0) {
                if (parent_offset2 < 0) {
                    parent_offset2 += end_offset2;
                }
                return parent_offset2;
            }
            return -1;
        } else {
            while (true) {
                if (index == this.gapStart) {
                    index = this.gapEnd;
                }
                if (index != this.data.length) {
                    switch (this.data[index]) {
                        case END_ATTRIBUTE /* 61706 */:
                            index++;
                            break;
                        case END_ELEMENT_SHORT /* 61707 */:
                            return index - this.data[index + 1];
                        case END_ELEMENT_LONG /* 61708 */:
                            int begin_offset = getIntN(index + 3);
                            if (begin_offset < 0) {
                                begin_offset += index;
                            }
                            return begin_offset;
                        case 61709:
                        case 61710:
                        case 61711:
                        case BEGIN_DOCUMENT /* 61712 */:
                        default:
                            index = nextDataIndex(index);
                            if (index >= 0) {
                                break;
                            } else {
                                return -1;
                            }
                        case END_DOCUMENT /* 61713 */:
                            return -1;
                    }
                } else {
                    return -1;
                }
            }
        }
    }

    public int getAttributeCount(int parent) {
        int n = 0;
        int attr = firstAttributePos(parent);
        while (attr != 0 && getNextKind(attr) == 35) {
            n++;
            attr = nextPos(attr);
        }
        return n;
    }

    @Override // gnu.lists.AbstractSequence
    public boolean gotoAttributesStart(TreePosition pos) {
        int index = gotoAttributesStart(pos.ipos >> 1);
        if (index < 0) {
            return false;
        }
        pos.push(this, index << 1);
        return true;
    }

    @Override // gnu.lists.AbstractSequence
    public int firstAttributePos(int ipos) {
        int index = gotoAttributesStart(posToDataIndex(ipos));
        if (index < 0) {
            return 0;
        }
        return index << 1;
    }

    public int gotoAttributesStart(int index) {
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        if ((datum < BEGIN_ELEMENT_SHORT || datum > 45055) && datum != BEGIN_ELEMENT_LONG) {
            return -1;
        }
        return index + 3;
    }

    @Override // gnu.lists.AbstractSequence
    public Object get(int index) {
        int i = 0;
        do {
            index--;
            if (index >= 0) {
                i = nextPos(i);
            } else {
                return getPosNext(i);
            }
        } while (i != 0);
        throw new IndexOutOfBoundsException();
    }

    @Override // gnu.lists.AbstractSequence
    public boolean consumeNext(int ipos, Consumer out) {
        if (!hasNext(ipos)) {
            return false;
        }
        int start = posToDataIndex(ipos);
        int end = nextNodeIndex(start, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (end == start) {
            end = nextDataIndex(start);
        }
        if (end >= 0) {
            consumeIRange(start, end, out);
        }
        return true;
    }

    @Override // gnu.lists.AbstractSequence
    public void consumePosRange(int startPos, int endPos, Consumer out) {
        consumeIRange(posToDataIndex(startPos), posToDataIndex(endPos), out);
    }

    /* JADX WARN: Code restructure failed: missing block: B:96:0x02bc, code lost:
        return r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int consumeIRange(int r17, int r18, gnu.lists.Consumer r19) {
        /*
            Method dump skipped, instructions count: 762
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.TreeList.consumeIRange(int, int, gnu.lists.Consumer):int");
    }

    @Override // gnu.lists.AbstractSequence
    public void toString(String sep, StringBuffer sbuf) {
        int index;
        int pos = 0;
        int limit = this.gapStart;
        boolean seen = false;
        boolean inStartTag = false;
        while (true) {
            if (pos < limit || (pos == this.gapStart && (pos = this.gapEnd) != (limit = this.data.length))) {
                int pos2 = pos + 1;
                char datum = this.data[pos];
                if (datum <= 40959) {
                    int start = pos2 - 1;
                    int lim = limit;
                    while (true) {
                        if (pos2 >= lim) {
                            pos = pos2;
                        } else {
                            int pos3 = pos2 + 1;
                            if (this.data[pos2] > 40959) {
                                pos = pos3 - 1;
                            } else {
                                pos2 = pos3;
                            }
                        }
                    }
                    if (inStartTag) {
                        sbuf.append('>');
                        inStartTag = false;
                    }
                    sbuf.append(this.data, start, pos - start);
                    seen = false;
                } else if (datum >= OBJECT_REF_SHORT && datum <= 61439) {
                    if (inStartTag) {
                        sbuf.append('>');
                        inStartTag = false;
                    }
                    if (seen) {
                        sbuf.append(sep);
                    } else {
                        seen = true;
                    }
                    sbuf.append(this.objects[datum - OBJECT_REF_SHORT]);
                    pos = pos2;
                } else if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
                    if (inStartTag) {
                        sbuf.append('>');
                    }
                    int index2 = datum - BEGIN_ELEMENT_SHORT;
                    if (seen) {
                        sbuf.append(sep);
                    }
                    sbuf.append('<');
                    sbuf.append(this.objects[index2].toString());
                    pos = pos2 + 2;
                    seen = false;
                    inStartTag = true;
                } else if (datum >= 45056 && datum <= 57343) {
                    if (inStartTag) {
                        sbuf.append('>');
                        inStartTag = false;
                    }
                    if (seen) {
                        sbuf.append(sep);
                    } else {
                        seen = true;
                    }
                    sbuf.append(datum - INT_SHORT_ZERO);
                    pos = pos2;
                } else {
                    switch (datum) {
                        case 61696:
                        case 61697:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            if (seen) {
                                sbuf.append(sep);
                            } else {
                                seen = true;
                            }
                            sbuf.append(datum != 61696);
                            pos = pos2;
                            continue;
                        case INT_FOLLOWS /* 61698 */:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            if (seen) {
                                sbuf.append(sep);
                            } else {
                                seen = true;
                            }
                            sbuf.append(getIntN(pos2));
                            pos = pos2 + 2;
                            continue;
                        case LONG_FOLLOWS /* 61699 */:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            if (seen) {
                                sbuf.append(sep);
                            } else {
                                seen = true;
                            }
                            sbuf.append(getLongN(pos2));
                            pos = pos2 + 4;
                            continue;
                        case FLOAT_FOLLOWS /* 61700 */:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            if (seen) {
                                sbuf.append(sep);
                            } else {
                                seen = true;
                            }
                            sbuf.append(Float.intBitsToFloat(getIntN(pos2)));
                            pos = pos2 + 2;
                            continue;
                        case DOUBLE_FOLLOWS /* 61701 */:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            if (seen) {
                                sbuf.append(sep);
                            } else {
                                seen = true;
                            }
                            sbuf.append(Double.longBitsToDouble(getLongN(pos2)));
                            pos = pos2 + 4;
                            continue;
                        case CHAR_FOLLOWS /* 61702 */:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            sbuf.append(this.data, pos2, (datum + 1) - CHAR_FOLLOWS);
                            seen = false;
                            pos = pos2 + 1;
                            continue;
                        case 61703:
                        default:
                            throw new Error("unknown code:" + ((int) datum));
                        case BEGIN_ELEMENT_LONG /* 61704 */:
                            int index3 = getIntN(pos2);
                            pos = pos2 + 2;
                            int index4 = getIntN(index3 + (index3 >= 0 ? pos2 - 1 : this.data.length) + 1);
                            if (inStartTag) {
                                sbuf.append('>');
                            } else if (seen) {
                                sbuf.append(sep);
                            }
                            sbuf.append('<');
                            sbuf.append(this.objects[index4]);
                            seen = false;
                            inStartTag = true;
                            continue;
                        case BEGIN_ATTRIBUTE_LONG /* 61705 */:
                            int index5 = getIntN(pos2);
                            sbuf.append(' ');
                            sbuf.append(this.objects[index5]);
                            sbuf.append("=\"");
                            inStartTag = false;
                            pos = pos2 + 4;
                            continue;
                        case END_ATTRIBUTE /* 61706 */:
                            sbuf.append('\"');
                            inStartTag = true;
                            seen = false;
                            pos = pos2;
                            continue;
                        case END_ELEMENT_SHORT /* 61707 */:
                        case END_ELEMENT_LONG /* 61708 */:
                            if (datum == END_ELEMENT_SHORT) {
                                pos = pos2 + 1;
                                index = this.data[(pos - 2) - this.data[pos2]] - BEGIN_ELEMENT_SHORT;
                            } else {
                                index = getIntN(pos2);
                                pos = pos2 + 6;
                            }
                            if (inStartTag) {
                                sbuf.append("/>");
                            } else {
                                sbuf.append("</");
                                sbuf.append(this.objects[index]);
                                sbuf.append('>');
                            }
                            inStartTag = false;
                            seen = true;
                            continue;
                        case 61709:
                        case 61710:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            if (seen) {
                                sbuf.append(sep);
                            } else {
                                seen = true;
                            }
                            sbuf.append(this.objects[getIntN(pos2)]);
                            pos = pos2 + 2;
                            continue;
                        case 61711:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            if (seen) {
                                sbuf.append(sep);
                            } else {
                                seen = true;
                            }
                            AbstractSequence seq = (AbstractSequence) this.objects[getIntN(pos2)];
                            int ipos = getIntN(pos2 + 2);
                            sbuf.append(seq.getIteratorAtPos(ipos));
                            pos = pos2 + 4;
                            continue;
                        case BEGIN_DOCUMENT /* 61712 */:
                        case BEGIN_ENTITY /* 61714 */:
                            pos = pos2 + 4;
                            continue;
                        case END_DOCUMENT /* 61713 */:
                        case END_ENTITY /* 61715 */:
                            pos = pos2;
                            continue;
                        case PROCESSING_INSTRUCTION /* 61716 */:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            sbuf.append("<?");
                            int index6 = getIntN(pos2);
                            int pos4 = pos2 + 2;
                            sbuf.append(this.objects[index6]);
                            int index7 = getIntN(pos4);
                            pos = pos4 + 2;
                            if (index7 > 0) {
                                sbuf.append(' ');
                                sbuf.append(this.data, pos, index7);
                                pos += index7;
                            }
                            sbuf.append("?>");
                            continue;
                        case CDATA_SECTION /* 61717 */:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            int index8 = getIntN(pos2);
                            int pos5 = pos2 + 2;
                            sbuf.append("<![CDATA[");
                            sbuf.append(this.data, pos5, index8);
                            sbuf.append("]]>");
                            pos = pos5 + index8;
                            continue;
                        case JOINER /* 61718 */:
                            pos = pos2;
                            continue;
                        case COMMENT /* 61719 */:
                            if (inStartTag) {
                                sbuf.append('>');
                                inStartTag = false;
                            }
                            int index9 = getIntN(pos2);
                            int pos6 = pos2 + 2;
                            sbuf.append("<!--");
                            sbuf.append(this.data, pos6, index9);
                            sbuf.append("-->");
                            pos = pos6 + index9;
                            continue;
                        case DOCUMENT_URI /* 61720 */:
                            pos = pos2 + 2;
                            continue;
                    }
                }
            } else {
                return;
            }
        }
    }

    @Override // gnu.lists.AbstractSequence
    public boolean hasNext(int ipos) {
        char ch;
        int index = posToDataIndex(ipos);
        return (index == this.data.length || (ch = this.data[index]) == END_ATTRIBUTE || ch == END_ELEMENT_SHORT || ch == END_ELEMENT_LONG || ch == END_DOCUMENT) ? false : true;
    }

    @Override // gnu.lists.AbstractSequence
    public int getNextKind(int ipos) {
        return getNextKindI(posToDataIndex(ipos));
    }

    public int getNextKindI(int index) {
        if (index == this.data.length) {
            return 0;
        }
        char datum = this.data[index];
        if (datum <= 40959) {
            return 29;
        }
        if (datum < OBJECT_REF_SHORT || datum > 61439) {
            if (datum < BEGIN_ELEMENT_SHORT || datum > 45055) {
                if ((65280 & datum) == BYTE_PREFIX) {
                    return 28;
                }
                if (datum < 45056 || datum > 57343) {
                    switch (datum) {
                        case 61696:
                        case 61697:
                            return 27;
                        case INT_FOLLOWS /* 61698 */:
                            return 22;
                        case LONG_FOLLOWS /* 61699 */:
                            return 24;
                        case FLOAT_FOLLOWS /* 61700 */:
                            return 25;
                        case DOUBLE_FOLLOWS /* 61701 */:
                            return 26;
                        case CHAR_FOLLOWS /* 61702 */:
                        case BEGIN_DOCUMENT /* 61712 */:
                            return 34;
                        case 61703:
                        case 61709:
                        case 61710:
                        case 61711:
                        case JOINER /* 61718 */:
                        default:
                            return 32;
                        case BEGIN_ELEMENT_LONG /* 61704 */:
                            return 33;
                        case BEGIN_ATTRIBUTE_LONG /* 61705 */:
                            return 35;
                        case END_ATTRIBUTE /* 61706 */:
                        case END_ELEMENT_SHORT /* 61707 */:
                        case END_ELEMENT_LONG /* 61708 */:
                        case END_DOCUMENT /* 61713 */:
                        case END_ENTITY /* 61715 */:
                            return 0;
                        case BEGIN_ENTITY /* 61714 */:
                            return getNextKind((index + 5) << 1);
                        case PROCESSING_INSTRUCTION /* 61716 */:
                            return 37;
                        case CDATA_SECTION /* 61717 */:
                            return 31;
                        case COMMENT /* 61719 */:
                            return 36;
                    }
                }
                return 22;
            }
            return 33;
        }
        return 32;
    }

    @Override // gnu.lists.AbstractSequence
    public Object getNextTypeObject(int ipos) {
        int index;
        int index2 = posToDataIndex(ipos);
        while (index2 != this.data.length) {
            char datum = this.data[index2];
            if (datum == 61714) {
                index2 += 5;
            } else {
                if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
                    index = datum - BEGIN_ELEMENT_SHORT;
                } else if (datum == BEGIN_ELEMENT_LONG) {
                    int j = getIntN(index2 + 1);
                    if (j < 0) {
                        index2 = this.data.length;
                    }
                    index = getIntN(j + index2 + 1);
                } else if (datum == BEGIN_ATTRIBUTE_LONG) {
                    index = getIntN(index2 + 1);
                } else if (datum != PROCESSING_INSTRUCTION) {
                    return null;
                } else {
                    index = getIntN(index2 + 1);
                }
                if (index >= 0) {
                    return this.objects[index];
                }
                return null;
            }
        }
        return null;
    }

    @Override // gnu.lists.AbstractSequence
    public String getNextTypeName(int ipos) {
        Object type = getNextTypeObject(ipos);
        if (type == null) {
            return null;
        }
        return type.toString();
    }

    @Override // gnu.lists.AbstractSequence
    public Object getPosPrevious(int ipos) {
        return ((ipos & 1) == 0 || ipos == -1) ? super.getPosPrevious(ipos) : getPosNext(ipos - 3);
    }

    private Object copyToList(int startPosition, int endPosition) {
        return new TreeList(this, startPosition, endPosition);
    }

    public int getPosNextInt(int ipos) {
        int index = posToDataIndex(ipos);
        if (index < this.data.length) {
            char datum = this.data[index];
            if (datum >= 45056 && datum <= 57343) {
                return datum - INT_SHORT_ZERO;
            }
            if (datum == 61698) {
                return getIntN(index + 1);
            }
        }
        return ((Number) getPosNext(ipos)).intValue();
    }

    @Override // gnu.lists.AbstractSequence
    public Object getPosNext(int ipos) {
        int index = posToDataIndex(ipos);
        if (index == this.data.length) {
            return Sequence.eofValue;
        }
        char datum = this.data[index];
        if (datum <= 40959) {
            return Convert.toObject(datum);
        }
        if (datum >= OBJECT_REF_SHORT && datum <= 61439) {
            return this.objects[datum - OBJECT_REF_SHORT];
        }
        if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
            return copyToList(index, this.data[index + 1] + index + 2);
        }
        if (datum >= 45056 && datum <= 57343) {
            return Convert.toObject(datum - INT_SHORT_ZERO);
        }
        switch (datum) {
            case 61696:
            case 61697:
                return Convert.toObject(datum != 61696);
            case INT_FOLLOWS /* 61698 */:
                return Convert.toObject(getIntN(index + 1));
            case LONG_FOLLOWS /* 61699 */:
                return Convert.toObject(getLongN(index + 1));
            case FLOAT_FOLLOWS /* 61700 */:
                return Convert.toObject(Float.intBitsToFloat(getIntN(index + 1)));
            case DOUBLE_FOLLOWS /* 61701 */:
                return Convert.toObject(Double.longBitsToDouble(getLongN(index + 1)));
            case CHAR_FOLLOWS /* 61702 */:
                return Convert.toObject(this.data[index + 1]);
            case 61703:
            case BEGIN_ENTITY /* 61714 */:
            case END_ENTITY /* 61715 */:
            case PROCESSING_INSTRUCTION /* 61716 */:
            case CDATA_SECTION /* 61717 */:
            default:
                throw unsupported("getPosNext, code=" + Integer.toHexString(datum));
            case BEGIN_ELEMENT_LONG /* 61704 */:
                int end_offset = getIntN(index + 1);
                return copyToList(index, end_offset + (end_offset < 0 ? this.data.length : index) + 7);
            case BEGIN_ATTRIBUTE_LONG /* 61705 */:
                int end_offset2 = getIntN(index + 3);
                return copyToList(index, end_offset2 + (end_offset2 < 0 ? this.data.length : index) + 1);
            case END_ATTRIBUTE /* 61706 */:
            case END_ELEMENT_SHORT /* 61707 */:
            case END_ELEMENT_LONG /* 61708 */:
            case END_DOCUMENT /* 61713 */:
                return Sequence.eofValue;
            case 61709:
            case 61710:
                return this.objects[getIntN(index + 1)];
            case 61711:
                AbstractSequence seq = (AbstractSequence) this.objects[getIntN(index + 1)];
                int ipos2 = getIntN(index + 3);
                return seq.getIteratorAtPos(ipos2);
            case BEGIN_DOCUMENT /* 61712 */:
                int end_offset3 = getIntN(index + 1);
                return copyToList(index, end_offset3 + (end_offset3 < 0 ? this.data.length : index) + 1);
            case JOINER /* 61718 */:
                return "";
        }
    }

    public void stringValue(int startIndex, int endIndex, StringBuffer sbuf) {
        int index = startIndex;
        while (index < endIndex && index >= 0) {
            index = stringValue(false, index, sbuf);
        }
    }

    public int stringValue(int index, StringBuffer sbuf) {
        int next = nextNodeIndex(index, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (next > index) {
            stringValue(index, next, sbuf);
            return index;
        }
        return stringValue(false, index, sbuf);
    }

    public int stringValue(boolean inElement, int index, StringBuffer sbuf) {
        Object value = null;
        int doChildren = 0;
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        int index2 = index + 1;
        if (datum <= 40959) {
            sbuf.append(datum);
            return index2;
        }
        if (datum >= OBJECT_REF_SHORT && datum <= 61439) {
            if (0 != 0) {
                sbuf.append(' ');
            }
            value = this.objects[datum - OBJECT_REF_SHORT];
        } else if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
            doChildren = index2 + 2;
            index2 = this.data[index2] + index2 + 1;
        } else if ((65280 & datum) == BYTE_PREFIX) {
            sbuf.append(datum & 255);
            return index2;
        } else if (datum >= 45056 && datum <= 57343) {
            sbuf.append(datum - INT_SHORT_ZERO);
            return index2;
        } else {
            switch (datum) {
                case 61696:
                case 61697:
                    if (0 != 0) {
                        sbuf.append(' ');
                    }
                    sbuf.append(datum != 61696);
                    return index2;
                case INT_FOLLOWS /* 61698 */:
                    if (0 != 0) {
                        sbuf.append(' ');
                    }
                    sbuf.append(getIntN(index2));
                    return index2 + 2;
                case LONG_FOLLOWS /* 61699 */:
                    if (0 != 0) {
                        sbuf.append(' ');
                    }
                    sbuf.append(getLongN(index2));
                    return index2 + 4;
                case FLOAT_FOLLOWS /* 61700 */:
                    if (0 != 0) {
                        sbuf.append(' ');
                    }
                    sbuf.append(Float.intBitsToFloat(getIntN(index2)));
                    return index2 + 2;
                case DOUBLE_FOLLOWS /* 61701 */:
                    if (0 != 0) {
                        sbuf.append(' ');
                    }
                    sbuf.append(Double.longBitsToDouble(getLongN(index2)));
                    return index2 + 4;
                case CHAR_FOLLOWS /* 61702 */:
                    sbuf.append(this.data[index2]);
                    return index2 + 1;
                case 61703:
                case 61709:
                case 61710:
                default:
                    throw new Error("unimplemented: " + Integer.toHexString(datum) + " at:" + index2);
                case BEGIN_ELEMENT_LONG /* 61704 */:
                    doChildren = index2 + 2;
                    int j = getIntN(index2);
                    index2 = j + (j < 0 ? this.data.length : index2 - 1) + 7;
                    break;
                case BEGIN_ATTRIBUTE_LONG /* 61705 */:
                    if (!inElement) {
                        doChildren = index2 + 4;
                    }
                    int end = getIntN(index2 + 2);
                    if (end < 0) {
                        index2 = this.data.length + 1;
                    }
                    index2 += end;
                    break;
                case END_ATTRIBUTE /* 61706 */:
                case END_ELEMENT_SHORT /* 61707 */:
                case END_ELEMENT_LONG /* 61708 */:
                case END_DOCUMENT /* 61713 */:
                case END_ENTITY /* 61715 */:
                    return -1;
                case 61711:
                    AbstractSequence seq = (AbstractSequence) this.objects[getIntN(index2)];
                    int ipos = getIntN(index2 + 2);
                    ((TreeList) seq).stringValue(inElement, ipos >> 1, sbuf);
                    index2 += 4;
                    break;
                case BEGIN_DOCUMENT /* 61712 */:
                case BEGIN_ENTITY /* 61714 */:
                    doChildren = index2 + 4;
                    index2 = nextDataIndex(index2 - 1);
                    break;
                case PROCESSING_INSTRUCTION /* 61716 */:
                    index2 += 2;
                    int length = getIntN(index2);
                    int index3 = index2 + 2;
                    if (inElement || datum == CDATA_SECTION) {
                        sbuf.append(this.data, index3, length);
                    }
                    return index3 + length;
                case CDATA_SECTION /* 61717 */:
                case COMMENT /* 61719 */:
                    int length2 = getIntN(index2);
                    int index32 = index2 + 2;
                    if (inElement) {
                        break;
                    }
                    sbuf.append(this.data, index32, length2);
                    return index32 + length2;
                case JOINER /* 61718 */:
                    break;
                case DOCUMENT_URI /* 61720 */:
                    return index2 + 2;
            }
        }
        if (value != null) {
            sbuf.append(value);
        }
        if (doChildren > 0) {
            do {
                doChildren = stringValue(true, doChildren, sbuf);
            } while (doChildren >= 0);
            return index2;
        }
        return index2;
    }

    @Override // gnu.lists.AbstractSequence
    public int createRelativePos(int istart, int offset, boolean isAfter) {
        if (isAfter) {
            if (offset == 0) {
                if ((istart & 1) == 0) {
                    if (istart == 0) {
                        return 1;
                    }
                } else {
                    return istart;
                }
            }
            offset--;
        }
        if (offset < 0) {
            throw unsupported("backwards createRelativePos");
        }
        int pos = posToDataIndex(istart);
        do {
            offset--;
            if (offset >= 0) {
                pos = nextDataIndex(pos);
            } else {
                if (pos >= this.gapEnd) {
                    pos -= this.gapEnd - this.gapStart;
                }
                return isAfter ? ((pos + 1) << 1) | 1 : pos << 1;
            }
        } while (pos >= 0);
        throw new IndexOutOfBoundsException();
    }

    public final int nextNodeIndex(int pos, int limit) {
        if ((Integer.MIN_VALUE | limit) == -1) {
            limit = this.data.length;
        }
        while (true) {
            if (pos == this.gapStart) {
                pos = this.gapEnd;
            }
            if (pos < limit) {
                char datum = this.data[pos];
                if (datum <= 40959 || ((datum >= OBJECT_REF_SHORT && datum <= 61439) || ((datum >= 45056 && datum <= 57343) || (65280 & datum) == BYTE_PREFIX))) {
                    pos++;
                } else if (datum < BEGIN_ELEMENT_SHORT || datum > 45055) {
                    switch (datum) {
                        case BEGIN_ELEMENT_LONG /* 61704 */:
                        case BEGIN_ATTRIBUTE_LONG /* 61705 */:
                        case END_ATTRIBUTE /* 61706 */:
                        case END_ELEMENT_SHORT /* 61707 */:
                        case END_ELEMENT_LONG /* 61708 */:
                        case BEGIN_DOCUMENT /* 61712 */:
                        case END_DOCUMENT /* 61713 */:
                        case END_ENTITY /* 61715 */:
                        case PROCESSING_INSTRUCTION /* 61716 */:
                        case COMMENT /* 61719 */:
                            break;
                        case 61709:
                        case 61710:
                        case 61711:
                        case CDATA_SECTION /* 61717 */:
                        default:
                            pos = nextDataIndex(pos);
                            continue;
                        case BEGIN_ENTITY /* 61714 */:
                            pos += 5;
                            continue;
                        case JOINER /* 61718 */:
                            pos++;
                            continue;
                        case DOCUMENT_URI /* 61720 */:
                            pos += 3;
                            continue;
                    }
                }
            }
        }
        return pos;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0072 A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0097  */
    @Override // gnu.lists.AbstractSequence
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int nextMatching(int r15, gnu.lists.ItemPredicate r16, int r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 402
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.TreeList.nextMatching(int, gnu.lists.ItemPredicate, int, boolean):int");
    }

    @Override // gnu.lists.AbstractSequence
    public int nextPos(int position) {
        int index = posToDataIndex(position);
        if (index == this.data.length) {
            return 0;
        }
        if (index >= this.gapEnd) {
            index -= this.gapEnd - this.gapStart;
        }
        return (index << 1) + 3;
    }

    public final int nextDataIndex(int pos) {
        int pos2;
        if (pos == this.gapStart) {
            pos = this.gapEnd;
        }
        if (pos == this.data.length) {
            return -1;
        }
        int pos3 = pos + 1;
        char datum = this.data[pos];
        if (datum > 40959 && ((datum < OBJECT_REF_SHORT || datum > 61439) && (datum < 45056 || datum > 57343))) {
            if (datum >= BEGIN_ELEMENT_SHORT && datum <= 45055) {
                return this.data[pos3] + pos3 + 1;
            }
            switch (datum) {
                case 61696:
                case 61697:
                case JOINER /* 61718 */:
                    return pos3;
                case INT_FOLLOWS /* 61698 */:
                case FLOAT_FOLLOWS /* 61700 */:
                case 61709:
                case 61710:
                    return pos3 + 2;
                case LONG_FOLLOWS /* 61699 */:
                case DOUBLE_FOLLOWS /* 61701 */:
                    return pos3 + 4;
                case CHAR_FOLLOWS /* 61702 */:
                    return pos3 + 1;
                case 61703:
                default:
                    throw new Error("unknown code:" + Integer.toHexString(datum));
                case BEGIN_ELEMENT_LONG /* 61704 */:
                    int j = getIntN(pos3);
                    return j + (j < 0 ? this.data.length : pos3 - 1) + 7;
                case BEGIN_ATTRIBUTE_LONG /* 61705 */:
                    int j2 = getIntN(pos3 + 2);
                    return j2 + (j2 < 0 ? this.data.length : pos3 - 1) + 1;
                case END_ATTRIBUTE /* 61706 */:
                case END_ELEMENT_SHORT /* 61707 */:
                case END_ELEMENT_LONG /* 61708 */:
                case END_DOCUMENT /* 61713 */:
                case END_ENTITY /* 61715 */:
                    return -1;
                case 61711:
                    return pos3 + 4;
                case BEGIN_DOCUMENT /* 61712 */:
                    int j3 = getIntN(pos3);
                    return j3 + (j3 < 0 ? this.data.length : pos3 - 1) + 1;
                case BEGIN_ENTITY /* 61714 */:
                    int j4 = pos3 + 4;
                    while (true) {
                        if (j4 == this.gapStart) {
                            j4 = this.gapEnd;
                        }
                        if (j4 == this.data.length) {
                            return -1;
                        }
                        if (this.data[j4] == END_ENTITY) {
                            return j4 + 1;
                        }
                        j4 = nextDataIndex(j4);
                    }
                case PROCESSING_INSTRUCTION /* 61716 */:
                    pos2 = pos3 + 2;
                    break;
                case CDATA_SECTION /* 61717 */:
                case COMMENT /* 61719 */:
                    pos2 = pos3;
                    break;
            }
            return pos2 + 2 + getIntN(pos2);
        }
        return pos3;
    }

    public Object documentUriOfPos(int pos) {
        int index = posToDataIndex(pos);
        if (index != this.data.length && this.data[index] == BEGIN_DOCUMENT) {
            int next = index + 5;
            if (next == this.gapStart) {
                next = this.gapEnd;
            }
            if (next >= this.data.length || this.data[next] != DOCUMENT_URI) {
                return null;
            }
            return this.objects[getIntN(next + 1)];
        }
        return null;
    }

    @Override // gnu.lists.AbstractSequence
    public int compare(int ipos1, int ipos2) {
        int i1 = posToDataIndex(ipos1);
        int i2 = posToDataIndex(ipos2);
        if (i1 < i2) {
            return -1;
        }
        return i1 > i2 ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.lists.AbstractSequence
    public int getIndexDifference(int ipos1, int ipos0) {
        int i0 = posToDataIndex(ipos0);
        int i1 = posToDataIndex(ipos1);
        boolean negate = false;
        if (i0 > i1) {
            negate = true;
            i1 = i0;
            i0 = i1;
        }
        int i = 0;
        while (i0 < i1) {
            i0 = nextDataIndex(i0);
            i++;
        }
        return negate ? -i : i;
    }

    @Override // gnu.lists.AbstractSequence, java.util.List, java.util.Collection
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // gnu.lists.AbstractSequence, gnu.lists.Consumable
    public void consume(Consumer out) {
        consumeIRange(0, this.data.length, out);
    }

    public void statistics() {
        PrintWriter out = new PrintWriter(System.out);
        statistics(out);
        out.flush();
    }

    public void statistics(PrintWriter out) {
        out.print("data array length: ");
        out.println(this.data.length);
        out.print("data array gap: ");
        out.println(this.gapEnd - this.gapStart);
        out.print("object array length: ");
        out.println(this.objects.length);
    }

    public void dump() {
        PrintWriter out = new PrintWriter(System.out);
        dump(out);
        out.flush();
    }

    public void dump(PrintWriter out) {
        out.println(getClass().getName() + " @" + Integer.toHexString(System.identityHashCode(this)) + " gapStart:" + this.gapStart + " gapEnd:" + this.gapEnd + " length:" + this.data.length);
        dump(out, 0, this.data.length);
    }

    public void dump(PrintWriter out, int start, int limit) {
        int toskip = 0;
        int i = start;
        while (i < limit) {
            if (i < this.gapStart || i >= this.gapEnd) {
                char c = this.data[i];
                out.print("" + i + ": 0x" + Integer.toHexString(c) + '=' + ((int) ((short) c)));
                toskip--;
                if (toskip < 0) {
                    if (c <= 40959) {
                        if (c >= ' ' && c < 127) {
                            out.print("='" + c + "'");
                        } else if (c == '\n') {
                            out.print("='\\n'");
                        } else {
                            out.print("='\\u" + Integer.toHexString(c) + "'");
                        }
                    } else if (c >= OBJECT_REF_SHORT && c <= 61439) {
                        int ch = c - OBJECT_REF_SHORT;
                        Object obj = this.objects[ch];
                        out.print("=Object#" + ch + '=' + obj + ':' + obj.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(obj)));
                    } else if (c >= BEGIN_ELEMENT_SHORT && c <= 45055) {
                        int ch2 = c - BEGIN_ELEMENT_SHORT;
                        out.print("=BEGIN_ELEMENT_SHORT end:" + (this.data[i + 1] + i) + " index#" + ch2 + "=<" + this.objects[ch2] + '>');
                        toskip = 2;
                    } else if (c >= 45056 && c <= 57343) {
                        out.print("= INT_SHORT:" + (c - INT_SHORT_ZERO));
                    } else {
                        switch (c) {
                            case 61696:
                                out.print("= false");
                                break;
                            case 61697:
                                out.print("= true");
                                break;
                            case INT_FOLLOWS /* 61698 */:
                                out.print("=INT_FOLLOWS value:" + getIntN(i + 1));
                                toskip = 2;
                                break;
                            case LONG_FOLLOWS /* 61699 */:
                                long l = getLongN(i + 1);
                                out.print("=LONG_FOLLOWS value:" + l);
                                toskip = 4;
                                break;
                            case FLOAT_FOLLOWS /* 61700 */:
                                out.write("=FLOAT_FOLLOWS value:" + Float.intBitsToFloat(getIntN(i + 1)));
                                toskip = 2;
                                break;
                            case DOUBLE_FOLLOWS /* 61701 */:
                                long l2 = getLongN(i + 1);
                                out.print("=DOUBLE_FOLLOWS value:" + Double.longBitsToDouble(l2));
                                toskip = 4;
                                break;
                            case CHAR_FOLLOWS /* 61702 */:
                                out.print("=CHAR_FOLLOWS");
                                toskip = 1;
                                break;
                            case BEGIN_ELEMENT_LONG /* 61704 */:
                                int j = getIntN(i + 1);
                                int j2 = j + (j < 0 ? this.data.length : i);
                                out.print("=BEGIN_ELEMENT_LONG end:");
                                out.print(j2);
                                int j3 = getIntN(j2 + 1);
                                out.print(" -> #");
                                out.print(j3);
                                if (j3 >= 0 && j3 + 1 < this.objects.length) {
                                    out.print("=<" + this.objects[j3] + '>');
                                } else {
                                    out.print("=<out-of-bounds>");
                                }
                                toskip = 2;
                                break;
                            case BEGIN_ATTRIBUTE_LONG /* 61705 */:
                                int j4 = getIntN(i + 1);
                                out.print("=BEGIN_ATTRIBUTE name:" + j4 + "=" + this.objects[j4]);
                                int j5 = getIntN(i + 3);
                                out.print(" end:" + (j5 + (j5 < 0 ? this.data.length : i)));
                                toskip = 4;
                                break;
                            case END_ATTRIBUTE /* 61706 */:
                                out.print("=END_ATTRIBUTE");
                                break;
                            case END_ELEMENT_SHORT /* 61707 */:
                                out.print("=END_ELEMENT_SHORT begin:");
                                int j6 = i - this.data[i + 1];
                                out.print(j6);
                                int j7 = this.data[j6] - BEGIN_ELEMENT_SHORT;
                                out.print(" -> #");
                                out.print(j7);
                                out.print("=<");
                                out.print(this.objects[j7]);
                                out.print('>');
                                toskip = 1;
                                break;
                            case END_ELEMENT_LONG /* 61708 */:
                                int j8 = getIntN(i + 1);
                                out.print("=END_ELEMENT_LONG name:" + j8 + "=<" + this.objects[j8] + '>');
                                int j9 = getIntN(i + 3);
                                if (j9 < 0) {
                                    j9 += i;
                                }
                                out.print(" begin:" + j9);
                                int j10 = getIntN(i + 5);
                                if (j10 < 0) {
                                    j10 += i;
                                }
                                out.print(" parent:" + j10);
                                toskip = 6;
                                break;
                            case 61709:
                            case 61710:
                                toskip = 2;
                                break;
                            case 61711:
                                out.print("=POSITION_PAIR_FOLLOWS seq:");
                                int j11 = getIntN(i + 1);
                                out.print(j11);
                                out.print('=');
                                Object seq = this.objects[j11];
                                out.print(seq == null ? null : seq.getClass().getName());
                                out.print('@');
                                if (seq == null) {
                                    out.print("null");
                                } else {
                                    out.print(Integer.toHexString(System.identityHashCode(seq)));
                                }
                                out.print(" ipos:");
                                out.print(getIntN(i + 3));
                                toskip = 4;
                                break;
                            case BEGIN_DOCUMENT /* 61712 */:
                                int j12 = getIntN(i + 1);
                                int length = j12 < 0 ? this.data.length : i;
                                out.print("=BEGIN_DOCUMENT end:");
                                out.print(j12 + length);
                                out.print(" parent:");
                                out.print(getIntN(i + 3));
                                toskip = 4;
                                break;
                            case END_DOCUMENT /* 61713 */:
                                out.print("=END_DOCUMENT");
                                break;
                            case BEGIN_ENTITY /* 61714 */:
                                int j13 = getIntN(i + 1);
                                out.print("=BEGIN_ENTITY base:");
                                out.print(j13);
                                out.print(" parent:");
                                out.print(getIntN(i + 3));
                                toskip = 4;
                                break;
                            case END_ENTITY /* 61715 */:
                                out.print("=END_ENTITY");
                                break;
                            case PROCESSING_INSTRUCTION /* 61716 */:
                                out.print("=PROCESSING_INSTRUCTION: ");
                                out.print(this.objects[getIntN(i + 1)]);
                                out.print(" '");
                                int j14 = getIntN(i + 3);
                                out.write(this.data, i + 5, j14);
                                out.print('\'');
                                toskip = j14 + 4;
                                break;
                            case CDATA_SECTION /* 61717 */:
                                out.print("=CDATA: '");
                                int j15 = getIntN(i + 1);
                                out.write(this.data, i + 3, j15);
                                out.print('\'');
                                toskip = j15 + 2;
                                break;
                            case JOINER /* 61718 */:
                                out.print("= joiner");
                                break;
                            case COMMENT /* 61719 */:
                                out.print("=COMMENT: '");
                                int j16 = getIntN(i + 1);
                                out.write(this.data, i + 3, j16);
                                out.print('\'');
                                toskip = j16 + 2;
                                break;
                            case DOCUMENT_URI /* 61720 */:
                                out.print("=DOCUMENT_URI: ");
                                out.print(this.objects[getIntN(i + 1)]);
                                toskip = 2;
                                break;
                        }
                    }
                }
                out.println();
                if (1 != 0 && toskip > 0) {
                    i += toskip;
                    toskip = 0;
                }
            }
            i++;
        }
    }
}
