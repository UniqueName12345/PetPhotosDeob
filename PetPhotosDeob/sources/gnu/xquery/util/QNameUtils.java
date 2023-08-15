package gnu.xquery.util;

import gnu.kawa.xml.KElement;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.kawa.xml.XStringType;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.Path;
import gnu.text.URIPath;
import gnu.xml.NamespaceBinding;
import gnu.xml.TextUtils;
import gnu.xml.XName;
import java.net.URISyntaxException;

/* loaded from: classes.dex */
public class QNameUtils {
    public static Object resolveQNameUsingElement(Object qname, KElement node) {
        String prefix;
        String localPart;
        Object qname2 = KNode.atomicValue(qname);
        if (qname2 != Values.empty && qname2 != null) {
            if ((qname2 instanceof Values) || (!(qname2 instanceof String) && !(qname2 instanceof UntypedAtomic))) {
                throw new RuntimeException("bad argument to QName");
            }
            String name = TextUtils.replaceWhitespace(qname2.toString(), true);
            int colon = name.indexOf(58);
            if (colon < 0) {
                prefix = null;
                localPart = name;
            } else {
                prefix = name.substring(0, colon).intern();
                localPart = name.substring(colon + 1);
            }
            String uri = node.lookupNamespaceURI(prefix);
            if (uri == null) {
                if (prefix == null) {
                    uri = "";
                } else {
                    throw new RuntimeException("unknown namespace for '" + name + "'");
                }
            }
            if (!validNCName(localPart) || (prefix != null && !validNCName(prefix))) {
                throw new RuntimeException("invalid QName syntax '" + name + "'");
            }
            if (prefix == null) {
                prefix = "";
            }
            Object qname3 = Symbol.make(uri, localPart, prefix);
            return qname3;
        }
        return qname2;
    }

    public static Object resolveQName(Object qname, NamespaceBinding constructorNamespaces, NamespaceBinding prologNamespaces) {
        String prefix;
        String localPart;
        Object qname2 = KNode.atomicValue(qname);
        if (!(qname2 instanceof Symbol)) {
            if ((qname2 instanceof Values) || (!(qname2 instanceof String) && !(qname2 instanceof UntypedAtomic))) {
                throw new RuntimeException("bad argument to QName");
            }
            String name = TextUtils.replaceWhitespace(qname2.toString(), true);
            int colon = name.indexOf(58);
            if (colon < 0) {
                localPart = name;
                prefix = null;
            } else {
                prefix = name.substring(0, colon).intern();
                localPart = name.substring(colon + 1);
            }
            if (!validNCName(localPart) || (prefix != null && !validNCName(prefix))) {
                throw new RuntimeException("invalid QName syntax '" + name + "'");
            }
            String uri = resolvePrefix(prefix, constructorNamespaces, prologNamespaces);
            if (prefix == null) {
                prefix = "";
            }
            Object qname3 = Symbol.make(uri, localPart, prefix);
            return qname3;
        }
        return qname2;
    }

    public static String lookupPrefix(String prefix, NamespaceBinding constructorNamespaces, NamespaceBinding prologNamespaces) {
        String uri;
        NamespaceBinding ns = constructorNamespaces;
        while (true) {
            if (ns == null) {
                uri = prologNamespaces.resolve(prefix);
                break;
            } else if (ns.getPrefix() != prefix) {
                ns = ns.getNext();
            } else {
                uri = ns.getUri();
                break;
            }
        }
        if (uri == null && prefix == null) {
            return "";
        }
        return uri;
    }

    public static String resolvePrefix(String prefix, NamespaceBinding constructorNamespaces, NamespaceBinding prologNamespaces) {
        String uri = lookupPrefix(prefix, constructorNamespaces, prologNamespaces);
        if (uri == null) {
            throw new RuntimeException("unknown namespace prefix '" + prefix + "'");
        }
        return uri;
    }

    public static boolean validNCName(String name) {
        return XName.isName(name);
    }

    public static Symbol makeQName(Object paramURI, String paramQName) {
        String localPart;
        String prefix;
        paramURI = (paramURI == null || paramURI == Values.empty) ? "" : "";
        int colon = paramQName.indexOf(58);
        String namespaceURI = (String) paramURI;
        if (colon < 0) {
            localPart = paramQName;
            prefix = "";
        } else {
            localPart = paramQName.substring(colon + 1);
            prefix = paramQName.substring(0, colon).intern();
        }
        if (!validNCName(localPart) || (colon >= 0 && !validNCName(prefix))) {
            throw new IllegalArgumentException("invalid QName syntax '" + paramQName + "'");
        }
        if (colon >= 0 && namespaceURI.length() == 0) {
            throw new IllegalArgumentException("empty uri for '" + paramQName + "'");
        }
        return Symbol.make(namespaceURI, localPart, prefix);
    }

    public static Object localNameFromQName(Object name) {
        if (name != Values.empty && name != null) {
            if (!(name instanceof Symbol)) {
                throw new WrongType("local-name-from-QName", 1, name, "xs:QName");
            }
            return XStringType.makeNCName(((Symbol) name).getName());
        }
        return name;
    }

    public static Object prefixFromQName(Object name) {
        if (name != Values.empty && name != null) {
            if (name instanceof Symbol) {
                String prefix = ((Symbol) name).getPrefix();
                if (prefix == null || prefix.length() == 0) {
                    return Values.empty;
                }
                return XStringType.makeNCName(prefix);
            }
            throw new WrongType("prefix-from-QName", 1, name, "xs:QName");
        }
        return name;
    }

    public static Object namespaceURIFromQName(Object name) {
        if (name == Values.empty || name == null) {
            return name;
        }
        try {
            return URIPath.makeURI(((Symbol) name).getNamespaceURI());
        } catch (ClassCastException e) {
            throw new WrongType("namespace-uri", 1, name, "xs:QName");
        }
    }

    public static Object namespaceURIForPrefix(Object prefix, Object element) {
        String str;
        KNode el = KNode.coerce(element);
        if (el == null) {
            throw new WrongType("namespace-uri-for-prefix", 2, element, "node()");
        }
        if (prefix == null || prefix == Values.empty) {
            str = null;
        } else if (!(prefix instanceof String) && !(prefix instanceof UntypedAtomic)) {
            throw new WrongType("namespace-uri-for-prefix", 1, element, "xs:string");
        } else {
            str = prefix.toString().intern();
            if (str == "") {
                str = null;
            }
        }
        String uri = el.lookupNamespaceURI(str);
        if (uri == null) {
            return Values.empty;
        }
        return uri;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Object resolveURI(Object relative, Object base) throws URISyntaxException {
        if (relative instanceof KNode) {
            relative = KNode.atomicValue(relative);
        }
        if (base instanceof KNode) {
            base = KNode.atomicValue(base);
        }
        if (relative != Values.empty && relative != null) {
            boolean z = relative instanceof UntypedAtomic;
            Object relative2 = relative;
            if (z) {
                relative2 = relative.toString();
            }
            boolean z2 = base instanceof UntypedAtomic;
            Object base2 = base;
            if (z2) {
                base2 = base.toString();
            }
            Path baseP = base2 instanceof Path ? (Path) base2 : URIPath.makeURI(base2);
            if (relative2 instanceof Path) {
                return baseP.resolve((Path) relative2);
            }
            return baseP.resolve(relative2.toString());
        }
        return relative;
    }
}
