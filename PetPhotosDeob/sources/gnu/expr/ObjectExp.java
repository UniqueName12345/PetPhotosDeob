package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;

/* loaded from: classes.dex */
public class ObjectExp extends ClassExp {
    public ObjectExp() {
        super(true);
    }

    @Override // gnu.expr.ClassExp, gnu.expr.LambdaExp, gnu.expr.Expression
    public Type getType() {
        return this.type;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.ClassExp, gnu.expr.LambdaExp, gnu.expr.ScopeExp, gnu.expr.Expression
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitObjectExp(this, d);
    }

    @Override // gnu.expr.ClassExp, gnu.expr.LambdaExp, gnu.expr.Expression
    public void compile(Compilation comp, Target target) {
        Variable closureEnv;
        compileMembers(comp);
        CodeAttr code = comp.getCode();
        code.emitNew(this.type);
        code.emitDup(1);
        Method init = Compilation.getConstructor(this.type, this);
        if (this.closureEnvField != null) {
            LambdaExp caller = outerLambda();
            if (Compilation.defaultCallConvention < 2) {
                closureEnv = getOwningLambda().heapFrame;
            } else {
                closureEnv = caller.heapFrame != null ? caller.heapFrame : caller.closureEnv;
            }
            if (closureEnv == null) {
                code.emitPushThis();
            } else {
                code.emitLoad(closureEnv);
            }
        }
        code.emitInvokeSpecial(init);
        target.compileFromStack(comp, getCompiledClassType(comp));
    }
}
