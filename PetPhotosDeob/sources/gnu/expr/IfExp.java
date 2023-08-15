package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Label;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Values;

/* loaded from: classes.dex */
public class IfExp extends Expression {
    Expression else_clause;
    Expression test;
    Expression then_clause;

    public IfExp(Expression i, Expression t, Expression e) {
        this.test = i;
        this.then_clause = t;
        this.else_clause = e;
    }

    public Expression getTest() {
        return this.test;
    }

    public Expression getThenClause() {
        return this.then_clause;
    }

    public Expression getElseClause() {
        return this.else_clause;
    }

    protected final Language getLanguage() {
        return Language.getDefaultLanguage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public boolean mustCompile() {
        return false;
    }

    @Override // gnu.expr.Expression, gnu.mapping.Procedure
    public void apply(CallContext ctx) throws Throwable {
        if (getLanguage().isTrue(this.test.eval(ctx))) {
            this.then_clause.apply(ctx);
        } else if (this.else_clause != null) {
            this.else_clause.apply(ctx);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Expression select(boolean truth) {
        return truth ? this.then_clause : this.else_clause == null ? QuoteExp.voidExp : this.else_clause;
    }

    @Override // gnu.expr.Expression
    public void compile(Compilation comp, Target target) {
        compile(this.test, this.then_clause, this.else_clause == null ? QuoteExp.voidExp : this.else_clause, comp, target);
    }

    public static void compile(Expression test, Expression then_clause, Expression else_clause, Compilation comp, Target target) {
        boolean falseInherited;
        boolean trueInherited;
        Label trueLabel;
        Language language = comp.getLanguage();
        CodeAttr code = comp.getCode();
        Label falseLabel = null;
        if ((target instanceof ConditionalTarget) && (else_clause instanceof QuoteExp)) {
            falseInherited = true;
            Object value = ((QuoteExp) else_clause).getValue();
            if (language.isTrue(value)) {
                falseLabel = ((ConditionalTarget) target).ifTrue;
            } else {
                falseLabel = ((ConditionalTarget) target).ifFalse;
            }
        } else {
            if ((else_clause instanceof ExitExp) && (((ExitExp) else_clause).result instanceof QuoteExp)) {
                BlockExp block = ((ExitExp) else_clause).block;
                if ((block.exitTarget instanceof IgnoreTarget) && (falseLabel = block.exitableBlock.exitIsGoto()) != null) {
                    falseInherited = true;
                }
            }
            falseInherited = false;
        }
        if (falseLabel == null) {
            falseLabel = new Label(code);
        }
        if (test == then_clause && (target instanceof ConditionalTarget) && (then_clause instanceof ReferenceExp)) {
            trueInherited = true;
            trueLabel = ((ConditionalTarget) target).ifTrue;
        } else {
            trueInherited = false;
            trueLabel = new Label(code);
        }
        ConditionalTarget ctarget = new ConditionalTarget(trueLabel, falseLabel, language);
        if (trueInherited) {
            ctarget.trueBranchComesFirst = false;
        }
        test.compile(comp, ctarget);
        code.emitIfThen();
        if (!trueInherited) {
            trueLabel.define(code);
            Variable callContextSave = comp.callContextVar;
            then_clause.compileWithPosition(comp, target);
            comp.callContextVar = callContextSave;
        }
        if (!falseInherited) {
            code.emitElse();
            falseLabel.define(code);
            Variable callContextSave2 = comp.callContextVar;
            if (else_clause == null) {
                comp.compileConstant(Values.empty, target);
            } else {
                else_clause.compileWithPosition(comp, target);
            }
            comp.callContextVar = callContextSave2;
        } else {
            code.setUnreachable();
        }
        code.emitFi();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitIfExp(this, d);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.expr.Expression
    public <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.test = visitor.visitAndUpdate(this.test, d);
        if (visitor.exitValue == null) {
            this.then_clause = visitor.visitAndUpdate(this.then_clause, d);
        }
        if (visitor.exitValue == null && this.else_clause != null) {
            this.else_clause = visitor.visitAndUpdate(this.else_clause, d);
        }
    }

    @Override // gnu.expr.Expression
    public Type getType() {
        Type t1 = this.then_clause.getType();
        Type t2 = this.else_clause == null ? Type.voidType : this.else_clause.getType();
        return Language.unionType(t1, t2);
    }

    @Override // gnu.expr.Expression
    public void print(OutPort out) {
        out.startLogicalBlock("(If ", false, ")");
        out.setIndentation(-2, false);
        this.test.print(out);
        out.writeSpaceLinear();
        this.then_clause.print(out);
        if (this.else_clause != null) {
            out.writeSpaceLinear();
            this.else_clause.print(out);
        }
        out.endLogicalBlock(")");
    }
}
