package gnu.expr;

/* compiled from: BlockExp.java */
/* loaded from: classes.dex */
class BlockExitException extends RuntimeException {
    ExitExp exit;
    Object result;

    public BlockExitException(ExitExp exit, Object result) {
        this.exit = exit;
        this.result = result;
    }
}
