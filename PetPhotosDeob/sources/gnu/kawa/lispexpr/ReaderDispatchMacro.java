package gnu.kawa.lispexpr;

import gnu.mapping.Procedure;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.io.Reader;

/* loaded from: classes.dex */
public class ReaderDispatchMacro extends ReaderMisc {
    Procedure procedure;

    public ReaderDispatchMacro(Procedure procedure) {
        super(5);
        this.procedure = procedure;
    }

    public Procedure getProcedure() {
        return this.procedure;
    }

    @Override // gnu.kawa.lispexpr.ReadTableEntry
    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        Reader reader = in.getPort();
        try {
            return this.procedure.apply3(reader, Char.make(ch), IntNum.make(count));
        } catch (SyntaxException ex) {
            throw ex;
        } catch (IOException ex2) {
            throw ex2;
        } catch (Throwable ex3) {
            in.fatal("reader macro '" + this.procedure + "' threw: " + ex3);
            return null;
        }
    }
}
