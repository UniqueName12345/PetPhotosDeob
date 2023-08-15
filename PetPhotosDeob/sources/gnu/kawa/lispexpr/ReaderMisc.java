package gnu.kawa.lispexpr;

/* loaded from: classes.dex */
public class ReaderMisc extends ReadTableEntry {
    int kind;

    public ReaderMisc(int kind) {
        this.kind = kind;
    }

    @Override // gnu.kawa.lispexpr.ReadTableEntry
    public int getKind() {
        return this.kind;
    }
}
