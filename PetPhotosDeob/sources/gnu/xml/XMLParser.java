package gnu.xml;

import gnu.lists.Consumer;
import gnu.text.LineBufferedReader;
import gnu.text.LineInputStreamReader;
import gnu.text.Path;
import gnu.text.SourceMessages;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class XMLParser {
    private static final int ATTRIBUTE_SEEN_EQ_STATE = 11;
    private static final int ATTRIBUTE_SEEN_NAME_STATE = 8;
    static final String BAD_ENCODING_SYNTAX = "bad 'encoding' declaration";
    static final String BAD_STANDALONE_SYNTAX = "bad 'standalone' declaration";
    private static final int BEGIN_ELEMENT_STATE = 2;
    private static final int DOCTYPE_NAME_SEEN_STATE = 16;
    private static final int DOCTYPE_SEEN_STATE = 13;
    private static final int END_ELEMENT_STATE = 4;
    private static final int EXPECT_NAME_MODIFIER = 1;
    private static final int EXPECT_RIGHT_STATE = 27;
    private static final int INIT_LEFT_QUEST_STATE = 30;
    private static final int INIT_LEFT_STATE = 34;
    private static final int INIT_STATE = 0;
    private static final int INIT_TEXT_STATE = 31;
    private static final int INVALID_VERSION_DECL = 35;
    private static final int MAYBE_ATTRIBUTE_STATE = 10;
    private static final int PREV_WAS_CR_STATE = 28;
    private static final int SAW_AMP_SHARP_STATE = 26;
    private static final int SAW_AMP_STATE = 25;
    private static final int SAW_ENTITY_REF = 6;
    private static final int SAW_EOF_ERROR = 37;
    private static final int SAW_ERROR = 36;
    private static final int SAW_LEFT_EXCL_MINUS_STATE = 22;
    private static final int SAW_LEFT_EXCL_STATE = 20;
    private static final int SAW_LEFT_QUEST_STATE = 21;
    private static final int SAW_LEFT_SLASH_STATE = 19;
    private static final int SAW_LEFT_STATE = 14;
    private static final int SKIP_SPACES_MODIFIER = 2;
    private static final int TEXT_STATE = 1;

    public static void parse(Object uri, SourceMessages messages, Consumer out) throws IOException {
        parse(Path.openInputStream(uri), uri, messages, out);
    }

    public static LineInputStreamReader XMLStreamReader(InputStream strm) throws IOException {
        LineInputStreamReader in = new LineInputStreamReader(strm);
        int b1 = in.getByte();
        int b2 = b1 < 0 ? -1 : in.getByte();
        int b3 = b2 < 0 ? -1 : in.getByte();
        if (b1 == 239 && b2 == 187 && b3 == 191) {
            in.resetStart(3);
            in.setCharset("UTF-8");
        } else if (b1 == 255 && b2 == 254 && b3 != 0) {
            in.resetStart(2);
            in.setCharset("UTF-16LE");
        } else if (b1 == 254 && b2 == 255 && b3 != 0) {
            in.resetStart(2);
            in.setCharset("UTF-16BE");
        } else {
            int b4 = b3 >= 0 ? in.getByte() : -1;
            if (b1 == 76 && b2 == 111 && b3 == 167 && b4 == 148) {
                throw new RuntimeException("XMLParser: EBCDIC encodings not supported");
            }
            in.resetStart(0);
            if ((b1 == 60 && ((b2 == 63 && b3 == 120 && b4 == 109) || (b2 == 0 && b3 == 63 && b4 == 0))) || (b1 == 0 && b2 == 60 && b3 == 0 && b4 == 63)) {
                char[] buffer = in.buffer;
                if (buffer == null) {
                    buffer = new char[8192];
                    in.buffer = buffer;
                }
                int pos = 0;
                int quote = 0;
                while (true) {
                    int b = in.getByte();
                    if (b != 0) {
                        if (b < 0) {
                            break;
                        }
                        int pos2 = pos + 1;
                        buffer[pos] = (char) (b & 255);
                        if (quote == 0) {
                            if (b == 62) {
                                pos = pos2;
                                break;
                            } else if (b == 39 || b == 34) {
                                quote = b;
                            }
                        } else if (b == quote) {
                            quote = 0;
                        }
                        pos = pos2;
                    }
                }
                in.pos = 0;
                in.limit = pos;
            } else {
                in.setCharset("UTF-8");
            }
        }
        in.setKeepFullLines(false);
        return in;
    }

    public static void parse(InputStream strm, Object uri, SourceMessages messages, Consumer out) throws IOException {
        LineInputStreamReader in = XMLStreamReader(strm);
        if (uri != null) {
            in.setName(uri);
        }
        parse((LineBufferedReader) in, messages, out);
        in.close();
    }

    public static void parse(LineBufferedReader in, SourceMessages messages, Consumer out) throws IOException {
        XMLFilter filter = new XMLFilter(out);
        filter.setMessages(messages);
        filter.setSourceLocator(in);
        filter.startDocument();
        Path uri = in.getPath();
        if (uri != null) {
            filter.writeDocumentUri(uri);
        }
        parse(in, filter);
        filter.endDocument();
    }

    public static void parse(LineBufferedReader in, SourceMessages messages, XMLFilter filter) throws IOException {
        filter.setMessages(messages);
        filter.setSourceLocator(in);
        filter.startDocument();
        Path uri = in.getPath();
        if (uri != null) {
            filter.writeDocumentUri(uri);
        }
        parse(in, filter);
        filter.endDocument();
        in.close();
    }

    /* JADX WARN: Code restructure failed: missing block: B:163:0x023d, code lost:
        if (r5 != 0) goto L247;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0243, code lost:
        if (r22 != 8) goto L256;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0245, code lost:
        r17 = "missing or invalid attribute name";
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0247, code lost:
        r22 = 36;
        r18 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0250, code lost:
        if (r22 == 2) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0255, code lost:
        if (r22 != 4) goto L260;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0257, code lost:
        r17 = "missing or invalid element name";
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x025a, code lost:
        r17 = "missing or invalid name";
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x028b, code lost:
        r26.pos = r19;
        r27.error('e', "invalid character reference");
        r22 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x05c2, code lost:
        r17 = "junk at end of xml declaration";
        r18 = r6;
        r22 = 36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:572:0x0878, code lost:
        r18 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:810:?, code lost:
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x05f4 A[LOOP:18: B:419:0x05fa->B:418:0x05f4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:583:0x0806 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:586:0x006a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:622:0x0029 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:803:0x0023 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void parse(gnu.text.LineBufferedReader r26, gnu.xml.XMLFilter r27) {
        /*
            Method dump skipped, instructions count: 2260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xml.XMLParser.parse(gnu.text.LineBufferedReader, gnu.xml.XMLFilter):void");
    }
}
