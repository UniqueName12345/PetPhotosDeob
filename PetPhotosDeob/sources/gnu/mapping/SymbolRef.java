package gnu.mapping;

import java.lang.ref.WeakReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Namespace.java */
/* loaded from: classes.dex */
public class SymbolRef extends WeakReference {
    SymbolRef next;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SymbolRef(Symbol sym, Namespace ns) {
        super(sym);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Symbol getSymbol() {
        return (Symbol) get();
    }

    public String toString() {
        return "SymbolRef[" + getSymbol() + "]";
    }
}
