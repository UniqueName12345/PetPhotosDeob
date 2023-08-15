package gnu.expr;

import gnu.mapping.OutPort;

/* loaded from: classes.dex */
public class LangExp extends Expression {
    Object hook;

    public Object getLangValue() {
        return this.hook;
    }

    public void setLangValue(Object value) {
        this.hook = value;
    }

    public LangExp() {
    }

    public LangExp(Object value) {
        this.hook = value;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public boolean mustCompile() {
        return false;
    }

    @Override // gnu.expr.Expression
    public void print(OutPort out) {
        out.print("(LangExp ???)");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitLangExp(this, d);
    }

    @Override // gnu.expr.Expression
    public void compile(Compilation comp, Target target) {
        throw new RuntimeException("compile called on LangExp");
    }
}
