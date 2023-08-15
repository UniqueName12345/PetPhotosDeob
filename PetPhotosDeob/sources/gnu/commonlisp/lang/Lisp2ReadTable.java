package gnu.commonlisp.lang;

import gnu.kawa.lispexpr.ReadTable;

/* compiled from: Lisp2.java */
/* loaded from: classes.dex */
class Lisp2ReadTable extends ReadTable {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // gnu.kawa.lispexpr.ReadTable
    public Object makeSymbol(String name) {
        return Lisp2.asSymbol(name.intern());
    }
}
