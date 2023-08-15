package gnu.kawa.lispexpr;

import gnu.mapping.Namespace;

/* compiled from: LispPackage.java */
/* loaded from: classes.dex */
class NamespaceUse {
    Namespace imported;
    Namespace importing;
    NamespaceUse nextImported;
    NamespaceUse nextImporting;

    NamespaceUse() {
    }
}
