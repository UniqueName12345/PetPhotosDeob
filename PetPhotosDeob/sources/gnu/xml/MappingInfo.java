package gnu.xml;

import gnu.mapping.Symbol;

/* compiled from: XMLFilter.java */
/* loaded from: classes.dex */
final class MappingInfo {
    int index = -1;
    String local;
    NamespaceBinding namespaces;
    MappingInfo nextInBucket;
    String prefix;
    Symbol qname;
    int tagHash;
    XName type;
    String uri;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int hash(String prefix, String local) {
        int hash = local.hashCode();
        if (prefix != null) {
            return hash ^ prefix.hashCode();
        }
        return hash;
    }

    static int hash(char[] data, int start, int length) {
        int hash = 0;
        int prefixHash = 0;
        int colonPos = -1;
        for (int i = 0; i < length; i++) {
            char ch = data[start + i];
            if (ch == ':' && colonPos < 0) {
                colonPos = i;
                prefixHash = hash;
                hash = 0;
            } else {
                hash = (hash * 31) + ch;
            }
        }
        return prefixHash ^ hash;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean match(char[] data, int start, int length) {
        if (this.prefix != null) {
            int localLength = this.local.length();
            int prefixLength = this.prefix.length();
            return length == (prefixLength + 1) + localLength && data[prefixLength] == ':' && equals(this.prefix, data, start, prefixLength) && equals(this.local, data, (start + prefixLength) + 1, localLength);
        }
        return equals(this.local, data, start, length);
    }

    static boolean equals(String tag, StringBuffer sbuf) {
        int length = sbuf.length();
        if (tag.length() != length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (sbuf.charAt(i) != tag.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean equals(String tag, char[] data, int start, int length) {
        if (tag.length() != length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (data[start + i] != tag.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
