package gnu.kawa.lispexpr;

import gnu.lists.Sequence;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

/* loaded from: classes.dex */
public class ReaderIgnoreRestOfLine extends ReadTableEntry {
    static ReaderIgnoreRestOfLine instance = new ReaderIgnoreRestOfLine();

    public static ReaderIgnoreRestOfLine getInstance() {
        return instance;
    }

    @Override // gnu.kawa.lispexpr.ReadTableEntry
    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        int ch2;
        do {
            ch2 = in.read();
            if (ch2 < 0) {
                return Sequence.eofValue;
            }
            if (ch2 == 10) {
                break;
            }
        } while (ch2 != 13);
        return Values.empty;
    }
}
