package gnu.kawa.lispexpr;

import gnu.expr.Compilation;
import gnu.expr.PrimProcedure;
import gnu.expr.Special;
import gnu.kawa.xml.CommentConstructor;
import gnu.kawa.xml.MakeAttribute;
import gnu.kawa.xml.MakeCDATA;
import gnu.kawa.xml.MakeProcInst;
import gnu.kawa.xml.MakeText;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import gnu.xml.XName;
import java.io.IOException;

/* loaded from: classes.dex */
public class ReaderXmlElement extends ReadTableEntry {
    static final String DEFAULT_ELEMENT_NAMESPACE = "[default-element-namespace]";

    @Override // gnu.kawa.lispexpr.ReadTableEntry
    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        LispReader reader = (LispReader) in;
        return readXMLConstructor(reader, reader.readUnicodeChar(), false);
    }

    public static Pair quote(Object obj) {
        Symbol q = Namespace.EmptyNamespace.getSymbol(LispLanguage.quote_sym);
        return LList.list2(q, obj);
    }

    public static Object readQNameExpression(LispReader reader, int ch, boolean forElement) throws IOException, SyntaxException {
        reader.getName();
        int line = reader.getLineNumber() + 1;
        int column = reader.getColumnNumber();
        reader.tokenBufferLength = 0;
        if (XName.isNameStart(ch)) {
            int colon = -1;
            while (true) {
                reader.tokenBufferAppend(ch);
                ch = reader.readUnicodeChar();
                if (ch == 58 && colon < 0) {
                    colon = reader.tokenBufferLength;
                } else if (!XName.isNamePart(ch)) {
                    break;
                }
            }
            reader.unread(ch);
            if (colon >= 0 || forElement) {
                int llen = (reader.tokenBufferLength - colon) - 1;
                String local = new String(reader.tokenBuffer, colon + 1, llen).intern();
                String prefix = colon < 0 ? DEFAULT_ELEMENT_NAMESPACE : new String(reader.tokenBuffer, 0, colon).intern();
                Symbol psym = Namespace.EmptyNamespace.getSymbol(prefix);
                return new Pair(ResolveNamespace.resolveQName, PairWithPosition.make(psym, new Pair(local, LList.Empty), reader.getName(), line, column));
            }
            Symbol symbol = Namespace.getDefaultSymbol(reader.tokenBufferString().intern());
            return quote(symbol);
        } else if (ch == 123 || ch == 40) {
            return readEscapedExpression(reader, ch);
        } else {
            reader.error("missing element name");
            return null;
        }
    }

    static Object readEscapedExpression(LispReader reader, int ch) throws IOException, SyntaxException {
        if (ch == 40) {
            reader.unread(ch);
            return reader.readObject();
        }
        LineBufferedReader port = reader.getPort();
        char saveReadState = reader.pushNesting('{');
        int startLine = port.getLineNumber();
        int startColumn = port.getColumnNumber();
        try {
            PrimProcedure valuesMake = new PrimProcedure(Compilation.typeValues.getDeclaredMethod("values", 1));
            Pair list = reader.makePair(valuesMake, startLine, startColumn);
            Pair last = list;
            ReadTable readTable = ReadTable.getCurrent();
            while (true) {
                int line = port.getLineNumber();
                int column = port.getColumnNumber();
                int ch2 = port.read();
                if (ch2 == 125) {
                    break;
                }
                if (ch2 < 0) {
                    reader.eofError("unexpected EOF in list starting here", startLine + 1, startColumn);
                }
                ReadTableEntry entry = readTable.lookup(ch2);
                Object value = reader.readValues(ch2, entry, readTable);
                if (value != Values.empty) {
                    Pair pair = reader.makePair(reader.handlePostfix(value, readTable, line, column), line, column);
                    reader.setCdr(last, pair);
                    last = pair;
                }
            }
            reader.tokenBufferLength = 0;
            if (last == list.getCdr()) {
                return last.getCar();
            }
            return list;
        } finally {
            reader.popNesting(saveReadState);
        }
    }

    static Object readXMLConstructor(LispReader reader, int next, boolean inElementContent) throws IOException, SyntaxException {
        int startLine = reader.getLineNumber() + 1;
        int startColumn = reader.getColumnNumber() - 2;
        if (next == 33) {
            int next2 = reader.read();
            if (next2 == 45 && (next2 = reader.peek()) == 45) {
                reader.skip();
                if (!reader.readDelimited("-->")) {
                    reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file in XML comment starting here - expected \"-->\"");
                }
                String str = reader.tokenBufferString();
                return LList.list2(CommentConstructor.commentConstructor, str);
            } else if (next2 == 91 && (next2 = reader.read()) == 67 && (next2 = reader.read()) == 68 && (next2 = reader.read()) == 65 && (next2 = reader.read()) == 84 && (next2 = reader.read()) == 65 && (next2 = reader.read()) == 91) {
                if (!reader.readDelimited("]]>")) {
                    reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file in CDATA starting here - expected \"]]>\"");
                }
                String str2 = reader.tokenBufferString();
                return LList.list2(MakeCDATA.makeCDATA, str2);
            } else {
                reader.error('f', reader.getName(), startLine, startColumn, "'<!' must be followed by '--' or '[CDATA['");
                while (next2 >= 0 && next2 != 62 && next2 != 10 && next2 != 13) {
                    next2 = reader.read();
                }
                return null;
            }
        } else if (next == 63) {
            int next3 = reader.readUnicodeChar();
            if (next3 < 0 || !XName.isNameStart(next3)) {
                reader.error("missing target after '<?'");
            }
            do {
                reader.tokenBufferAppend(next3);
                next3 = reader.readUnicodeChar();
            } while (XName.isNamePart(next3));
            String target = reader.tokenBufferString();
            int nspaces = 0;
            while (next3 >= 0 && Character.isWhitespace(next3)) {
                nspaces++;
                next3 = reader.read();
            }
            reader.unread(next3);
            char saveReadState = reader.pushNesting('?');
            try {
                if (!reader.readDelimited("?>")) {
                    reader.error('f', reader.getName(), startLine, startColumn, "unexpected end-of-file looking for \"?>\"");
                }
                if (nspaces == 0 && reader.tokenBufferLength > 0) {
                    reader.error("target must be followed by space or '?>'");
                }
                String content = reader.tokenBufferString();
                return LList.list3(MakeProcInst.makeProcInst, target, content);
            } finally {
                reader.popNesting(saveReadState);
            }
        } else {
            return readElementConstructor(reader, next);
        }
    }

    public static Object readElementConstructor(LispReader reader, int ch) throws IOException, SyntaxException {
        int ch2;
        int startLine = reader.getLineNumber() + 1;
        int startColumn = reader.getColumnNumber() - 2;
        Object tag = readQNameExpression(reader, ch, true);
        String startTag = reader.tokenBufferLength == 0 ? null : reader.tokenBufferString();
        Pair make = PairWithPosition.make(tag, LList.Empty, reader.getName(), startLine, startColumn);
        Pair resultTail = make;
        LList namespaceList = LList.Empty;
        while (true) {
            boolean sawSpace = false;
            ch2 = reader.readUnicodeChar();
            while (ch2 >= 0 && Character.isWhitespace(ch2)) {
                ch2 = reader.read();
                sawSpace = true;
            }
            if (ch2 < 0 || ch2 == 62 || ch2 == 47) {
                break;
            }
            if (!sawSpace) {
                reader.error("missing space before attribute");
            }
            Object attrName = readQNameExpression(reader, ch2, false);
            int lineNumber = reader.getLineNumber() + 1;
            int columnNumber = (reader.getColumnNumber() + 1) - reader.tokenBufferLength;
            String definingNamespace = null;
            if (reader.tokenBufferLength >= 5 && reader.tokenBuffer[0] == 'x' && reader.tokenBuffer[1] == 'm' && reader.tokenBuffer[2] == 'l' && reader.tokenBuffer[3] == 'n' && reader.tokenBuffer[4] == 's') {
                if (reader.tokenBufferLength == 5) {
                    definingNamespace = "";
                } else if (reader.tokenBuffer[5] == ':') {
                    definingNamespace = new String(reader.tokenBuffer, 6, reader.tokenBufferLength - 6);
                }
            }
            int ch3 = skipSpace(reader, 32);
            if (ch3 != 61) {
                reader.error("missing '=' after attribute");
            }
            int ch4 = skipSpace(reader, 32);
            PairWithPosition attrList = PairWithPosition.make(MakeAttribute.makeAttribute, LList.Empty, reader.getName(), startLine, startColumn);
            PairWithPosition attrPair = PairWithPosition.make(attrName, LList.Empty, reader.getName(), startLine, startColumn);
            reader.setCdr(attrList, attrPair);
            readContent(reader, (char) ch4, attrPair);
            if (definingNamespace != null) {
                namespaceList = new PairWithPosition(attrPair, Pair.make(definingNamespace, attrPair.getCdr()), namespaceList);
            } else {
                Pair pair = PairWithPosition.make(attrList, reader.makeNil(), null, -1, -1);
                resultTail.setCdrBackdoor(pair);
                resultTail = pair;
            }
        }
        boolean empty = false;
        if (ch2 == 47) {
            ch2 = reader.read();
            if (ch2 == 62) {
                empty = true;
            } else {
                reader.unread(ch2);
            }
        }
        if (!empty) {
            if (ch2 != 62) {
                reader.error("missing '>' after start element");
                return Boolean.FALSE;
            }
            readContent(reader, '<', resultTail);
            int ch5 = reader.readUnicodeChar();
            if (XName.isNameStart(ch5)) {
                reader.tokenBufferLength = 0;
                while (true) {
                    reader.tokenBufferAppend(ch5);
                    ch5 = reader.readUnicodeChar();
                    if (!XName.isNamePart(ch5) && ch5 != 58) {
                        break;
                    }
                }
                String endTag = reader.tokenBufferString();
                if (startTag == null || !endTag.equals(startTag)) {
                    reader.error('e', reader.getName(), reader.getLineNumber() + 1, reader.getColumnNumber() - reader.tokenBufferLength, startTag == null ? "computed start tag closed by '</" + endTag + ">'" : "'<" + startTag + ">' closed by '</" + endTag + ">'");
                }
                reader.tokenBufferLength = 0;
            }
            if (skipSpace(reader, ch5) != 62) {
                reader.error("missing '>' after end element");
            }
        }
        return PairWithPosition.make(MakeXmlElement.makeXml, Pair.make(LList.reverseInPlace(namespaceList), make), reader.getName(), startLine, startColumn);
    }

    public static Pair readContent(LispReader reader, char delimiter, Pair resultTail) throws IOException, SyntaxException {
        reader.tokenBufferLength = 0;
        boolean prevWasEnclosed = false;
        while (true) {
            Object item = null;
            String text = null;
            int line = reader.getLineNumber() + 1;
            int column = reader.getColumnNumber();
            int next = reader.read();
            if (next < 0) {
                reader.error("unexpected end-of-file");
                item = Special.eof;
            } else if (next == delimiter) {
                if (delimiter == '<') {
                    if (reader.tokenBufferLength > 0) {
                        text = reader.tokenBufferString();
                        reader.tokenBufferLength = 0;
                    }
                    int next2 = reader.read();
                    if (next2 == 47) {
                        item = Special.eof;
                    } else {
                        item = readXMLConstructor(reader, next2, true);
                    }
                } else if (reader.checkNext(delimiter)) {
                    reader.tokenBufferAppend(delimiter);
                } else {
                    item = Special.eof;
                }
                prevWasEnclosed = false;
            } else if (next == 38) {
                int next3 = reader.read();
                if (next3 == 35) {
                    readCharRef(reader);
                } else if (next3 == 40 || next3 == 123) {
                    if (reader.tokenBufferLength > 0 || prevWasEnclosed) {
                        text = reader.tokenBufferString();
                    }
                    reader.tokenBufferLength = 0;
                    item = readEscapedExpression(reader, next3);
                } else {
                    item = readEntity(reader, next3);
                    if (prevWasEnclosed && reader.tokenBufferLength == 0) {
                        text = "";
                    }
                }
                prevWasEnclosed = true;
            } else {
                if (delimiter != '<' && (next == 9 || next == 10 || next == 13)) {
                    next = 32;
                }
                if (next == 60) {
                    reader.error('e', "'<' must be quoted in a direct attribute value");
                }
                reader.tokenBufferAppend((char) next);
            }
            if (item != null && reader.tokenBufferLength > 0) {
                text = reader.tokenBufferString();
                reader.tokenBufferLength = 0;
            }
            if (text != null) {
                Pair tnode = Pair.list2(MakeText.makeText, text);
                Pair make = PairWithPosition.make(tnode, reader.makeNil(), null, -1, -1);
                resultTail.setCdrBackdoor(make);
                resultTail = make;
            }
            if (item != Special.eof) {
                if (item != null) {
                    Pair pair = PairWithPosition.make(item, reader.makeNil(), null, line, column);
                    resultTail.setCdrBackdoor(pair);
                    resultTail = pair;
                }
            } else {
                return resultTail;
            }
        }
    }

    static void readCharRef(LispReader reader) throws IOException, SyntaxException {
        int base;
        int next = reader.read();
        if (next == 120) {
            base = 16;
            next = reader.read();
        } else {
            base = 10;
        }
        int value = 0;
        while (next >= 0) {
            char ch = (char) next;
            int digit = Character.digit(ch, base);
            if (digit < 0 || value >= 134217728) {
                break;
            }
            value = (value * base) + digit;
            next = reader.read();
        }
        if (next != 59) {
            reader.unread(next);
            reader.error("invalid character reference");
        } else if ((value > 0 && value <= 55295) || ((value >= 57344 && value <= 65533) || (value >= 65536 && value <= 1114111))) {
            reader.tokenBufferAppend(value);
        } else {
            reader.error("invalid character value " + value);
        }
    }

    static Object readEntity(LispReader reader, int next) throws IOException, SyntaxException {
        int saveLength = reader.tokenBufferLength;
        while (next >= 0) {
            char ch = (char) next;
            if (!XName.isNamePart(ch)) {
                break;
            }
            reader.tokenBufferAppend(ch);
            next = reader.read();
        }
        if (next != 59) {
            reader.unread(next);
            reader.error("invalid entity reference");
            return "?";
        }
        String ref = new String(reader.tokenBuffer, saveLength, reader.tokenBufferLength - saveLength);
        reader.tokenBufferLength = saveLength;
        namedEntity(reader, ref);
        return null;
    }

    public static void namedEntity(LispReader reader, String name) {
        String name2 = name.intern();
        char ch = '?';
        if (name2 == "lt") {
            ch = '<';
        } else if (name2 == "gt") {
            ch = '>';
        } else if (name2 == "amp") {
            ch = '&';
        } else if (name2 == "quot") {
            ch = '\"';
        } else if (name2 == "apos") {
            ch = '\'';
        } else {
            reader.error("unknown enity reference: '" + name2 + "'");
        }
        reader.tokenBufferAppend(ch);
    }

    static int skipSpace(LispReader reader, int ch) throws IOException, SyntaxException {
        while (ch >= 0 && Character.isWhitespace(ch)) {
            ch = reader.readUnicodeChar();
        }
        return ch;
    }
}
