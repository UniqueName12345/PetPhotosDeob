package gnu.expr;

import gnu.bytecode.Type;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

/* loaded from: classes.dex */
public class ExitExp extends Expression {
    BlockExp block;
    Expression result;

    public ExitExp(Expression result, BlockExp block) {
        this.result = result;
        this.block = block;
    }

    public ExitExp(BlockExp block) {
        this.result = QuoteExp.voidExp;
        this.block = block;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public boolean mustCompile() {
        return false;
    }

    @Override // gnu.expr.Expression, gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        throw new BlockExitException(this, this.result.eval(ctx));
    }

    @Override // gnu.expr.Expression
    public void compile(Compilation comp, Target target) {
        comp.getCode();
        Expression res = this.result == null ? QuoteExp.voidExp : this.result;
        res.compileWithPosition(comp, this.block.exitTarget);
        this.block.exitableBlock.exit();
    }

    @Override // gnu.expr.Expression
    protected Expression deepCopy(IdentityHashTable mapper) {
        Expression res = deepCopy(this.result, mapper);
        if (res == null && this.result != null) {
            return null;
        }
        Object b = mapper.get(this.block);
        ExitExp copy = new ExitExp(res, b == null ? this.block : (BlockExp) b);
        copy.flags = getFlags();
        return copy;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitExitExp(this, d);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.result = visitor.visitAndUpdate(this.result, d);
    }

    @Override // gnu.expr.Expression
    public void print(OutPort out) {
        out.startLogicalBlock("(Exit", false, ")");
        out.writeSpaceFill();
        if (this.block == null || this.block.label == null) {
            out.print("<unknown>");
        } else {
            out.print(this.block.label.getName());
        }
        if (this.result != null) {
            out.writeSpaceLinear();
            this.result.print(out);
        }
        out.endLogicalBlock(")");
    }

    @Override // gnu.expr.Expression
    public Type getType() {
        return Type.neverReturnsType;
    }
}
