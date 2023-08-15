package gnu.expr;

/* loaded from: classes.dex */
public class ResolveNames extends ExpExpVisitor<Void> {
    protected NameLookup lookup;

    public ResolveNames() {
    }

    public ResolveNames(Compilation comp) {
        setContext(comp);
        this.lookup = comp.lexical;
    }

    public void resolveModule(ModuleExp exp) {
        Compilation saveComp = Compilation.setSaveCurrent(this.comp);
        try {
            push(exp);
            exp.visitChildren(this, null);
        } finally {
            Compilation.restoreCurrent(saveComp);
        }
    }

    protected void push(ScopeExp exp) {
        this.lookup.push(exp);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitScopeExp(ScopeExp exp, Void ignored) {
        visitDeclarationTypes(exp);
        push(exp);
        exp.visitChildren(this, ignored);
        this.lookup.pop(exp);
        return exp;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitLetExp(LetExp exp, Void ignored) {
        visitDeclarationTypes(exp);
        exp.visitInitializers(this, ignored);
        push(exp);
        exp.body = visit(exp.body, ignored);
        this.lookup.pop(exp);
        return exp;
    }

    public Declaration lookup(Expression exp, Object symbol, boolean function) {
        return this.lookup.lookup(symbol, function);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitReferenceExp(ReferenceExp exp, Void ignored) {
        Declaration decl;
        if (exp.getBinding() == null && (decl = lookup(exp, exp.getSymbol(), exp.isProcedureName())) != null) {
            exp.setBinding(decl);
        }
        return exp;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ExpVisitor
    public Expression visitSetExp(SetExp exp, Void ignored) {
        if (exp.binding == null) {
            Declaration decl = lookup(exp, exp.getSymbol(), exp.isFuncDef());
            if (decl != null) {
                decl.setCanWrite(true);
            }
            exp.binding = decl;
        }
        return (Expression) super.visitSetExp(exp, (SetExp) ignored);
    }
}
