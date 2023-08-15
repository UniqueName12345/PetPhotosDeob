package gnu.kawa.lispexpr;

import gnu.mapping.Procedure;
import gnu.text.Char;
import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.io.Reader;

/* loaded from: classes.dex */
public class ReaderMacro extends ReaderMisc {
    Procedure procedure;

    public ReaderMacro(Procedure procedure, boolean nonTerminating) {
        super(nonTerminating ? 6 : 5);
        this.procedure = procedure;
    }

    public ReaderMacro(Procedure procedure) {
        super(5);
        this.procedure = procedure;
    }

    public boolean isNonTerminating() {
        return this.kind == 6;
    }

    public Procedure getProcedure() {
        return this.procedure;
    }

    @Override // gnu.kawa.lispexpr.ReadTableEntry
    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        Reader reader = in.getPort();
        try {
            return this.procedure.apply2(reader, Char.make(ch));
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
