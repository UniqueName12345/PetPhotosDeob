package gnu.kawa.lispexpr;

import gnu.lists.PairWithPosition;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

/* loaded from: classes.dex */
public class ReaderQuote extends ReadTableEntry {
    Object magicSymbol;
    Object magicSymbol2;
    char next;

    public ReaderQuote(Object magicSymbol) {
        this.magicSymbol = magicSymbol;
    }

    public ReaderQuote(Object magicSymbol, char next, Object magicSymbol2) {
        this.next = next;
        this.magicSymbol = magicSymbol;
        this.magicSymbol2 = magicSymbol2;
    }

    @Override // gnu.kawa.lispexpr.ReadTableEntry
    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        LispReader reader = (LispReader) in;
        String file = reader.getName();
        int line1 = reader.getLineNumber() + 1;
        int column1 = reader.getColumnNumber() + 1;
        Object magic = this.magicSymbol;
        if (this.next != 0) {
            int ch2 = reader.read();
            if (ch2 == this.next) {
                magic = this.magicSymbol2;
            } else if (ch2 >= 0) {
                reader.unread(ch2);
            }
        }
        int line2 = reader.getLineNumber() + 1;
        int column2 = reader.getColumnNumber() + 1;
        Object operand = reader.readObject();
        return PairWithPosition.make(magic, PairWithPosition.make(operand, reader.makeNil(), file, line2, column2), file, line1, column1);
    }
}
