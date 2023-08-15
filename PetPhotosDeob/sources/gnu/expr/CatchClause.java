package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Variable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

/* loaded from: classes.dex */
public class CatchClause extends LetExp {
    CatchClause next;

    public CatchClause() {
        super(new Expression[]{QuoteExp.voidExp});
    }

    public CatchClause(Object name, ClassType type) {
        this();
        addDeclaration(name, type);
    }

    public CatchClause(LambdaExp lexp) {
        this();
        Declaration decl = lexp.firstDecl();
        lexp.remove(null, decl);
        add(decl);
        this.body = lexp.body;
    }

    public final CatchClause getNext() {
        return this.next;
    }

    public final void setNext(CatchClause next) {
        this.next = next;
    }

    @Override // gnu.expr.LetExp
    public final Expression getBody() {
        return this.body;
    }

    @Override // gnu.expr.LetExp
    public final void setBody(Expression body) {
        this.body = body;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.LetExp, gnu.expr.Expression
    public boolean mustCompile() {
        return false;
    }

    @Override // gnu.expr.LetExp
    protected Object evalVariable(int i, CallContext ctx) throws Throwable {
        return ctx.value1;
    }

    @Override // gnu.expr.LetExp, gnu.expr.Expression
    public void compile(Compilation comp, Target target) {
        CodeAttr code = comp.getCode();
        Declaration catchDecl = firstDecl();
        Variable catchVar = catchDecl.allocateVariable(code);
        code.enterScope(getVarScope());
        code.emitCatchStart(catchVar);
        this.body.compileWithPosition(comp, target);
        code.emitCatchEnd();
        code.popScope();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.LetExp, gnu.expr.Expression
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.body = visitor.visitAndUpdate(this.body, d);
    }

    @Override // gnu.expr.LetExp, gnu.expr.Expression
    public void print(OutPort out) {
        out.writeSpaceLinear();
        out.startLogicalBlock("(Catch", ")", 2);
        out.writeSpaceFill();
        this.decls.printInfo(out);
        out.writeSpaceLinear();
        this.body.print(out);
        out.endLogicalBlock(")");
    }
}
