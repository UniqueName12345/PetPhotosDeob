package gnu.expr;

/* loaded from: classes.dex */
public abstract class ExpExpVisitor<D> extends ExpVisitor<Expression, D> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // gnu.expr.ExpVisitor
    protected /* bridge */ /* synthetic */ Expression defaultValue(Expression x0, Object x1) {
        return defaultValue(x0, (Expression) x1);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression update(Expression exp, Expression r) {
        return r;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // gnu.expr.ExpVisitor
    protected Expression defaultValue(Expression r, D d) {
        return r;
    }
}
