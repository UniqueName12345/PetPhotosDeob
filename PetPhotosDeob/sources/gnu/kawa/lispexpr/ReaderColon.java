package gnu.kawa.lispexpr;

import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

/* loaded from: classes.dex */
public class ReaderColon extends ReadTableEntry {
    @Override // gnu.kawa.lispexpr.ReadTableEntry
    public int getKind() {
        return 6;
    }

    @Override // gnu.kawa.lispexpr.ReadTableEntry
    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        LispReader reader = (LispReader) in;
        ReadTable rtable = ReadTable.getCurrent();
        int startPos = reader.tokenBufferLength;
        if (ch == rtable.postfixLookupOperator) {
            int next = reader.read();
            if (next == 58) {
                return rtable.makeSymbol("::");
            }
            reader.tokenBufferAppend(ch);
            ch = next;
        }
        return reader.readAndHandleToken(ch, startPos, rtable);
    }
}
