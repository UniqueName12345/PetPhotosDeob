package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.ExitableBlock;
import gnu.bytecode.Label;
import gnu.bytecode.Type;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;

/* loaded from: classes.dex */
public class BlockExp extends Expression {
    Expression body;
    Expression exitBody;
    Target exitTarget;
    ExitableBlock exitableBlock;
    Declaration label;

    public void setBody(Expression body) {
        this.body = body;
    }

    public void setBody(Expression body, Expression exitBody) {
        this.body = body;
        this.exitBody = exitBody;
    }

    public void setLabel(Declaration label) {
        this.label = label;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public boolean mustCompile() {
        return false;
    }

    @Override // gnu.expr.Expression, gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        Object obj;
        try {
            obj = this.body.eval(ctx);
        } catch (BlockExitException ex) {
            if (ex.exit.block != this) {
                throw ex;
            }
            obj = ex.exit.result;
            if (this.exitBody != null) {
                obj = this.exitBody.eval(ctx);
            }
        }
        ctx.writeValue(obj);
    }

    @Override // gnu.expr.Expression
    public void compile(Compilation comp, Target target) {
        CodeAttr code = comp.getCode();
        Type rtype = (this.exitBody == null && (target instanceof StackTarget)) ? target.getType() : null;
        ExitableBlock bl = code.startExitableBlock(rtype, true);
        this.exitableBlock = bl;
        this.exitTarget = this.exitBody == null ? target : Target.Ignore;
        this.body.compileWithPosition(comp, target);
        if (this.exitBody != null) {
            Label doneLabel = new Label(code);
            code.emitGoto(doneLabel);
            code.endExitableBlock();
            this.exitBody.compileWithPosition(comp, target);
            doneLabel.define(code);
        } else {
            code.endExitableBlock();
        }
        this.exitableBlock = null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitBlockExp(this, d);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.body = visitor.visitAndUpdate(this.body, d);
        if (visitor.exitValue == null && this.exitBody != null) {
            this.exitBody = visitor.visitAndUpdate(this.exitBody, d);
        }
    }

    @Override // gnu.expr.Expression
    public void print(OutPort out) {
        out.startLogicalBlock("(Block", ")", 2);
        if (this.label != null) {
            out.print(' ');
            out.print(this.label.getName());
        }
        out.writeSpaceLinear();
        this.body.print(out);
        if (this.exitBody != null) {
            out.writeSpaceLinear();
            out.print("else ");
            this.exitBody.print(out);
        }
        out.endLogicalBlock(")");
    }
}
