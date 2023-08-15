package gnu.bytecode;

/* loaded from: classes.dex */
public class ExitableBlock {
    CodeAttr code;
    TryState currentTryState;
    Label endLabel;
    TryState initialTryState;
    ExitableBlock nextCase;
    ExitableBlock outer;
    Type resultType;
    Variable resultVariable;
    int switchCase;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExitableBlock(Type resultType, CodeAttr code, boolean runFinallyBlocks) {
        this.code = code;
        this.resultType = resultType;
        this.initialTryState = code.try_stack;
        if (runFinallyBlocks && resultType != null) {
            code.pushScope();
            Variable var = code.addLocal(resultType);
            this.resultVariable = var;
            code.emitStoreDefaultValue(var);
            int i = code.exitableBlockLevel + 1;
            code.exitableBlockLevel = i;
            this.switchCase = i;
        }
        this.endLabel = new Label(code);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void finish() {
        if (this.resultVariable != null && this.code.reachableHere()) {
            this.code.emitStore(this.resultVariable);
        }
        this.endLabel.define(this.code);
        if (this.resultVariable != null) {
            this.code.emitLoad(this.resultVariable);
            this.code.popScope();
            CodeAttr codeAttr = this.code;
            codeAttr.exitableBlockLevel--;
        }
    }

    public void exit() {
        if (this.resultVariable != null) {
            this.code.emitStore(this.resultVariable);
        }
        exit(TryState.outerHandler(this.code.try_stack, this.initialTryState));
    }

    public Label exitIsGoto() {
        if (TryState.outerHandler(this.code.try_stack, this.initialTryState) == this.initialTryState) {
            return this.endLabel;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void exit(TryState activeTry) {
        if (activeTry == this.initialTryState) {
            this.code.emitGoto(this.endLabel);
        } else if (this.code.useJsr()) {
            for (TryState stack = this.code.try_stack; stack != this.initialTryState; stack = stack.previous) {
                if (stack.finally_subr != null && stack.finally_ret_addr == null) {
                    this.code.emitJsr(stack.finally_subr);
                }
            }
            this.code.emitGoto(this.endLabel);
        } else {
            if (this.currentTryState == null) {
                linkCase(activeTry);
            }
            if (activeTry.saved_result != null) {
                this.code.emitStoreDefaultValue(activeTry.saved_result);
            }
            this.code.emitPushInt(this.switchCase);
            this.code.emitPushNull();
            this.code.emitGoto(activeTry.finally_subr);
        }
    }

    void linkCase(TryState tryState) {
        if (this.currentTryState != tryState) {
            if (this.currentTryState != null) {
                throw new Error();
            }
            this.nextCase = tryState.exitCases;
            tryState.exitCases = this;
            this.currentTryState = tryState;
        }
    }
}
