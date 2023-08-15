package gnu.expr;

import gnu.mapping.OutPort;
import gnu.text.SourceMessages;

/* loaded from: classes.dex */
public class ErrorExp extends Expression {
    String message;

    public ErrorExp(String message) {
        this.message = message;
    }

    public ErrorExp(String message, SourceMessages messages) {
        messages.error('e', message);
        this.message = message;
    }

    public ErrorExp(String message, Compilation comp) {
        comp.getMessages().error('e', message);
        this.message = message;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public boolean mustCompile() {
        return false;
    }

    @Override // gnu.expr.Expression
    public void print(OutPort out) {
        out.startLogicalBlock("(Error", false, ")");
        out.writeSpaceLinear();
        out.print(this.message);
        out.endLogicalBlock(")");
    }

    @Override // gnu.expr.Expression
    public void compile(Compilation comp, Target target) {
        throw new Error(comp.getFileName() + ":" + comp.getLineNumber() + ": internal error: compiling error expression: " + this.message);
    }
}
