package gnu.kawa.lispexpr;

import gnu.expr.QuoteExp;
import gnu.lists.FVector;
import gnu.mapping.InPort;
import gnu.mapping.Values;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.Vector;

/* loaded from: classes.dex */
public class ReaderVector extends ReadTableEntry {
    char close;

    public ReaderVector(char close) {
        this.close = close;
    }

    @Override // gnu.kawa.lispexpr.ReadTableEntry
    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        return readVector((LispReader) in, in.getPort(), count, this.close);
    }

    public static FVector readVector(LispReader lexer, LineBufferedReader port, int count, char close) throws IOException, SyntaxException {
        char saveReadState = ' ';
        if (port instanceof InPort) {
            saveReadState = ((InPort) port).readState;
            ((InPort) port).readState = close == ']' ? '[' : '(';
        }
        try {
            Vector vec = new Vector();
            ReadTable rtable = ReadTable.getCurrent();
            while (true) {
                int ch = lexer.read();
                if (ch < 0) {
                    lexer.eofError("unexpected EOF in vector");
                }
                if (ch == close) {
                    break;
                }
                Object value = lexer.readValues(ch, rtable);
                if (value instanceof Values) {
                    Object[] values = ((Values) value).getValues();
                    for (Object obj : values) {
                        vec.addElement(obj);
                    }
                } else {
                    if (value == QuoteExp.voidExp) {
                        value = Values.empty;
                    }
                    vec.addElement(value);
                }
            }
            Object[] objs = new Object[vec.size()];
            vec.copyInto(objs);
            return new FVector(objs);
        } finally {
            if (port instanceof InPort) {
                ((InPort) port).readState = saveReadState;
            }
        }
    }
}
